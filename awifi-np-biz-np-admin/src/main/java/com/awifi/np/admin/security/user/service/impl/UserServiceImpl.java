package com.awifi.np.admin.security.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.awifi.np.admin.security.role.service.RoleService;
import com.awifi.np.admin.security.user.dao.UserDao;
import com.awifi.np.admin.security.user.model.User;
import com.awifi.np.admin.security.user.service.UserService;
import com.awifi.np.admin.suit.service.SuitService;
import com.awifi.np.biz.api.client.dbcenter.location.util.LocationClient;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.EncryUtil;
import com.awifi.np.biz.common.util.FormatUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月11日 下午4:52:24
 * 创建作者：周颖
 * 文件名称：UserServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    
    /**用户dao*/
    @Resource(name = "userDao")
    private UserDao userDao;
   
    /**角色服务层*/
    @Resource(name = "roleService")
    private RoleService roleService;
    
    /**套服务层*/
    @Resource(name = "suitService")
    private SuitService suitService;
   
    /**
     * 根据用户名名密码获取用户信息
     * @param userName 用户名
     * @param password 密码
     * @return 用户信息
     * @author 周颖  
     * @date 2017年1月11日 下午8:03:24
     */
    public User getByNameAndPwd(String userName,String password){
        User user = userDao.getByNameAndPwd(userName, password);//通过用户名密码查找用户信息
        if(user == null){//如果为空
            //throw new BizException("E2010001", MessageUtil.getMessage("E2010001"));//账户名与密码不匹配!
            return null;
        }
        List<Long> roleList = roleService.getIdsById(user.getId());//获取用户角色
        String roleIds = CastUtil.listToString(roleList, ',');//转成字符串
        user.setRoleIds(roleIds);//set角色ids
        if(StringUtils.isBlank(user.getSuitCode())){//套码为空 
            Long[] roleIdsArray = (Long[]) roleList.toArray(new Long[roleList.size()]);
            String suitCode = suitService.getCodeById(roleIdsArray);//根据角色获取套码
            if(StringUtils.isBlank(suitCode)){//如果套码还为空
                throw new BizException("E2000005", MessageUtil.getMessage("E2000005"));//套码不允许为空!
            }
            user.setSuitCode(suitCode);//设置用户套码
        }
        return user;
    }
    
    
    /**
     * 补全用户信息
     * @param id 用户主键
     * @return 用户信息
     * @author 周颖  
     * @date 2017年2月23日 下午3:06:26
     */
    public User getById(Long id){
        if(id == null){
            return null;
        }
        return userDao.getById(id);
    }
    
    /**
     * 更新用户信息
     * @param user 用户
     * @return 结果
     * @author 周颖  
     * @date 2017年2月23日 下午6:58:19
     */
    public int updateById(User user){
        return userDao.updateById(user);
    }
    
    /**
     * 更新密码
     * @param id 用户id
     * @param password 密码
     * @author 许小满  
     * @date 2017年2月23日 下午3:04:42
     */
    public void updatePwdById(Long id, String password){
        userDao.updatePwdById(id, password);
    }
    
    /**
     * 管理员账号列表
     * @param curUser 登陆账号
     * @param page 页面
     * @param roleId 角色id
     * @param provinceId 省id
     * @param cityId 市id
     * @param areaId 区县id
     * @param userName 账号关键字
     * @author 周颖  
     * @throws Exception 
     * @date 2017年5月5日 上午10:09:12
     */
    public void getListByParams(SessionUser curUser, Page<User> page, Long roleId, Long provinceId, Long cityId, Long areaId, String userName) throws Exception{
        Long provinceIdLong = curUser.getProvinceId();
        if(provinceIdLong != null){
            provinceId = provinceIdLong;
        }
        Long cityIdLong = curUser.getCityId();
        if(cityIdLong != null){
            cityId = cityIdLong;
        }
        Long areaIdLong = curUser.getAreaId();
        if(areaIdLong != null){
            areaId = areaIdLong;
        }
        int count = userDao.getCountByParams(roleId,provinceId,cityId,areaId,userName);
        page.setTotalRecord(count);
        if(count <= 0){//直接返回
            return;
        }
        List<User> userList = userDao.getListByParams(roleId,provinceId,cityId,areaId,userName, page.getBegin(), page.getPageSize());
        Map<Long,String> locationCacheMap = new HashMap<Long,String>();//用于减少重复地区查询的次数
        Map<String,String> roleMap = null;
        for(User user : userList){
            roleMap = roleService.getIdsAndNamesByUserId(user.getId());
            user.setRoleIds(roleMap.get("roleIds"));
            user.setRoleNames(roleMap.get("roleNames"));
            this.formatLocation(user, locationCacheMap);
        }
        page.setRecords(userList);
    }
    
    /**
     * 补充地区属性
     * @param user 用户
     * @param locationCacheMap 地区缓存
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年5月5日 下午4:14:55
     */
    private void formatLocation(User user, Map<Long,String> locationCacheMap) throws Exception{
        String nameParam = "name";//地区名称
        Long provinceId = user.getProvinceId();//省id
        String province = null;//省
        if(provinceId != null){
            province = locationCacheMap.get(provinceId);//省
            if(StringUtils.isBlank(province)){//为空时，调用接口获取
                province = LocationClient.getByIdAndParam(provinceId, nameParam);//从缓存中拿
                locationCacheMap.put(provinceId, province);//存到map 减少查询次数
            }
            user.setProvince(province);//省
        }
        Long cityId = user.getCityId();//市id
        String city = null;//市
        if(cityId != null){
            city = locationCacheMap.get(cityId);//市
            if(StringUtils.isBlank(city)){//为空时，调用接口获取
                city = LocationClient.getByIdAndParam(cityId, nameParam);//从缓存中拿
                locationCacheMap.put(cityId, city);//存到map 减少查询次数
            }
            user.setCity(city);//市
        }
        Long areaId = user.getAreaId();//区id
        String area = null;//区
        if(areaId != null){
            area = locationCacheMap.get(areaId);//区
            if(StringUtils.isBlank(area)){//为空时，调用接口获取
                area = LocationClient.getByIdAndParam(areaId, nameParam);//从缓存中拿
                locationCacheMap.put(areaId, area);//存到map 减少查询次数
            }
            user.setArea(area);//区
        }
        user.setLocationFullName(FormatUtil.locationFullName(province, city, area));//地区全路径
    }
    
    /**
     * 判断管理员账号是否存在
     * @param userName 账号
     * @return true 存在
     * @author 周颖  
     * @date 2017年5月8日 下午1:54:23
     */
    public boolean isUserNameExist(String userName){
        int count = userDao.getNumByUserName(userName);
        return count > 0 ;
    }
    
    /**
     * 添加管理员
     * @param user 管理员
     * @author 周颖  
     * @date 2017年5月8日 下午2:06:24
     */
    public void add(User user){
        String password = getDefaultPassword();//账号初始密码
        user.setPassword(password);
        userDao.add(user);
        Long userId = user.getId();
        roleService.addUserRole(userId,user.getRoleIds());//维护账号角色关系
    }
    
    /**
     * 获取账号默认密码（md5加密）
     * @return 加密后的默认密码
     * @author 周颖  
     * @date 2017年2月4日 下午1:54:34
     */
    public String getDefaultPassword(){
        String password = SysConfigUtil.getParamValue("default_password");
        if (StringUtils.isBlank(password)){
            return StringUtils.EMPTY;
        }
        return EncryUtil.getMd5Str(password);
    }
    
    /**
     * 修改管理员
     * @param user 管理员
     * @author 周颖  
     * @date 2017年5月8日 下午2:37:03
     */
    public void update(User user){
        userDao.update(user);
        Long userId = user.getId();
        roleService.deleteUserRole(userId);//删除原先账号维护的角色
        roleService.addUserRole(userId,user.getRoleIds());//维护账号角色关系
    }
    
    /**
     * 管理员详情
     * @param id 主键id
     * @return 详情
     * @author 周颖  
     * @throws Exception 
     * @date 2017年5月8日 下午3:29:10
     */
    public User getUserById(Long id) throws Exception{
        User user = userDao.getUserById(id);
        user.setId(id);
        Map<String,String> roleMap = roleService.getIdsAndNamesByUserId(id);//角色补充
        user.setRoleIds(roleMap.get("roleIds"));
        user.setRoleNames(roleMap.get("roleNames"));
        this.formatLocation(user, new HashMap<Long,String>());//地区属性补充
        String merchantIds = user.getMerchantIds();
        if(StringUtils.isNotBlank(merchantIds)){
            Long[] merchantIdsArray = CastUtil.toLongArray(merchantIds.split(","));
            int maxLength = merchantIdsArray.length;
            String merchantName = null;
            StringBuffer merchantNames = new StringBuffer();
            for(int i=0;i<maxLength;i++){
                merchantName = MerchantClient.getNameByIdCache(merchantIdsArray[i]);
                if(StringUtils.isBlank(merchantName)){
                    continue;
                }
                merchantNames.append(merchantName);
                if(i<maxLength-1){
                    merchantNames.append(",");
                }
            }
            user.setMerchantNames(merchantNames.toString());
        }
        return user;
    }
    
    /**
     * 删除管理员
     * @param id 主键id
     * @author 周颖  
     * @date 2017年5月8日 下午7:15:50
     */
    public void delete(Long id){
        userDao.delete(id);
    }
    
    /**
     * 重置密码
     * @param id 账号id
     * @author 周颖  
     * @date 2017年5月8日 下午7:20:10
     */
    public void resetPassword(Long id){
        String password = getDefaultPassword();//账号初始密码
        userDao.resetPassword(id,password);
    }
    
    /**
     * 更新用户默认套码
     * @param userId 用户id
     * @param suitCode 套码
     * @author 周颖  
     * @date 2017年5月9日 下午3:04:32
     */
    public void updateSuitById(Long userId, String suitCode){
        userDao.updateSuitById(userId,suitCode);
    }
}
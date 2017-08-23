package com.awifi.np.biz.toe.admin.security.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.EncryUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.toe.admin.security.role.service.ToeRoleService;
import com.awifi.np.biz.toe.admin.security.user.dao.ToeUserDao;
import com.awifi.np.biz.toe.admin.security.user.model.ToeUser;
import com.awifi.np.biz.toe.admin.security.user.service.ToeUserService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月3日 上午9:41:08
 * 创建作者：周颖
 * 文件名称：ToeUserServiceImpl.java
 * 版本：  v1.0
 * 功能：toe用户实现类
 * 修改记录：
 */
@Service("toeUserService")
public class ToeUserServiceImpl implements ToeUserService {

    /**toe用户dao*/
    @Resource(name = "toeUserDao")
    private ToeUserDao toeUserDao;
    
    /**toe角色服务*/
    @Resource(name = "toeRoleService")
    private ToeRoleService toeRoleService; 
    
    /**
     * 根据用户获取对应商户id
     * @param userName 账号
     * @return 商户id
     * @author 周颖  
     * @date 2017年2月3日 上午9:42:00
     */
    public Long getMerIdByUserName(String userName){
        return toeUserDao.getMerIdByUserName(userName); 
    }
    
    /**
     * 判断该账号是否已经存在
     * @param userName 账号
     * @return true 存在 false 不存在
     * @author 周颖  
     * @date 2017年2月4日 上午11:22:15
     */
    public boolean isUserNameExist(String userName){
        int count = toeUserDao.getNumByUserName(userName);
        return count > 0 ? true : false;
    }
    
    /**
     * 新建账号
     * @param user 账号
     * @return 账号id
     * @author 周颖  
     * @date 2017年2月4日 下午2:06:22
     */
    public Long add(ToeUser user){
        String password = getDefaultPassword();//账号初始密码
        user.setPassword(password);
        toeUserDao.add(user);
        return user.getId();//返回主键
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
     * 维护账号和商户的关系
     * @param userId 账号id
     * @param merchantId 商户id
     * @author 周颖  
     * @date 2017年2月4日 下午2:25:33
     */
    public void addUserMerchant(Long userId, Long merchantId){
        toeUserDao.addUserMerchant(userId,merchantId);
    }
    
    /**
     * 通过商户id查找账号
     * @param merchantId 商户id
     * @return 账号
     * @author 周颖  
     * @date 2017年2月6日 上午9:04:54
     */
    public ToeUser getByMerchantId(Long merchantId){
        return toeUserDao.getByMerchantId(merchantId);
    }
    
    /**
     * 通过商户id获取账号id
     * @param merchantId 商户id
     * @return 账号id
     * @author 周颖  
     * @date 2017年2月7日 下午2:10:54
     */
    public Long getIdByMerchantId(Long merchantId){
        return toeUserDao.getIdByMerchantId(merchantId);
    }
    
    /**
     * 通过商户id修改账号信息
     * @param merchantId 商户id
     * @param user 账号
     * @return 账号id
     * @author 周颖  
     * @date 2017年2月7日 下午2:04:24
     */
    public Long update(Long merchantId,ToeUser user){
        Long userId = getIdByMerchantId(merchantId);//获取账号id
        if(userId == null){//账号id为空
            return null;
        }
        user.setId(userId);
        toeUserDao.update(user);//更新账号
        return userId;//返回账号id
    }
    
    /**
     * 根据用户名密码获取用户信息
     * @param userName 用户名
     * @param password 密码
     * @return 用户信息
     * @author 周颖  
     * @date 2017年4月5日 上午9:28:19
     */
    public ToeUser getByNameAndPwd(String userName, String password){
        ToeUser user = toeUserDao.getByNameAndPwd(userName,password);
        if(user == null){
            return null;
        }
        List<Long> roleList = toeRoleService.getIdsById(user.getId());
        if(roleList == null || roleList.isEmpty()){
            throw new BizException("E2010003", MessageUtil.getMessage("E2010003"));//角色转换错误！
        }
        String roleIds = CastUtil.listToString(roleList, ',');//转成字符串
        user.setRoleIds(roleIds);//set角色ids
        return user;
    }
    
    /**
     * 通过用户id获取用户信息
     * @param id 用户主键id
     * @return 用户信息
     * @author 周颖  
     * @date 2017年4月5日 上午10:40:01
     */
    public ToeUser getById(Long id){
        if(id == null){
            return null;
        }
        return toeUserDao.getById(id);
    }
    
    /**
     * 更新用户信息
     * @param toeUser 用户
     * @return 是否保存成功
     * @author 周颖  
     * @date 2017年4月5日 上午11:24:30
     */
    public int updateById(ToeUser toeUser){
        return toeUserDao.updateById(toeUser);
    }
    
    /**
     * 更新用户密码
     * @param id 用户主键id
     * @param password 用户密码
     * @author 周颖  
     * @date 2017年4月5日 下午1:54:14
     */
    public void updatePwdById(Long id, String password){
        toeUserDao.updatePwdById(id,password);
    }
    
    /**
     * 更新用户项目归属
     * @param merchantId 商户id
     * @param projectId 项目id
     * @author 周颖  
     * @date 2017年4月6日 下午1:09:27
     */
    public void updateProject(Long merchantId, Long projectId){
        Long userId = getIdByMerchantId(merchantId);//获取账号id
        if(userId != null){//账号不为空,更新
            toeUserDao.updateProject(userId,projectId);
        }
    }
    
    /**
     * 重置商户账号密码
     * @param merchantId 商户id 
     * @author 周颖  
     * @date 2017年4月6日 下午4:37:11
     */
    public void resetPassword(Long merchantId){
        Long id = getIdByMerchantId(merchantId);
        if(id == null){
            throw new BizException("E2200004", MessageUtil.getMessage("E2200004",merchantId));//通过商户id(merchantId[{0}])找不到对应的账号!
        }
        String password = getDefaultPassword();
        int count = toeUserDao.updatePwdById(id,password);
        if(count <= 0){
            throw new BizException("E2200004", MessageUtil.getMessage("E2200004",merchantId));//通过商户id(merchantId[{0}])找不到对应的账号!
        }
    }
    
    /**
     * 批量获取商户的账号
     * @param merchantIdList 商户ids
     * @return key商户id value 账号
     * @author 周颖  
     * @date 2017年4月10日 下午3:00:06
     */
    @SuppressWarnings("rawtypes")
    public Map<Long, String> getNameByMerchantIds(List<Long> merchantIdList){
        int maxSize = merchantIdList.size();
        Long[] merchantIds = (Long[])merchantIdList.toArray(new Long[maxSize]);
        List<Map> dataList = toeUserDao.getNameByMerchantIds(merchantIds);
        Map<Long,String> accountMap = new HashMap<Long,String>();
        for(Map dataMap : dataList){//封装
            Long id = CastUtil.toLong(dataMap.get("customer_id"));//商户id
            String userName = (String)dataMap.get("user_name");//账号
            accountMap.put(id, userName);
        }
        return accountMap;
    }

    /**
     * 根据用户名查询id和username
     * @param userNames 用户名
     * @return map
     * @author 王冬冬  
     * @date 2017年5月15日 下午2:15:47
     */
    public Map<Long, String> getIdAndUserNameByUsernames(String userNames) {
        String[] names=userNames.split(",");
        List<Map> dataList = toeUserDao.getIdAndUserNameByUsernames(names);
        Map<Long,String> resultMap = new HashMap<Long,String>();
        for(Map dataMap : dataList){//封装
            Long id = CastUtil.toLong(dataMap.get("customer_id"));//商户id
            String userName = (String)dataMap.get("user_name");//账号
            resultMap.put(id, userName);
        }
        return resultMap;
    }

    
    /**
     * @param userNamesSet 商户账号集合
     * @return map
     * @author 王冬冬  
     * @date 2017年5月16日 下午7:16:59
     */
    public Map<String, Long> getUserNameAndIdByUsernames(Set<String> userNamesSet) {
        String [] names=new String[userNamesSet.size()];
        int i=0;
        for(String userName:userNamesSet){
            names[i]=userName;
            i++;
        }
        List<Map> dataList = toeUserDao.getIdAndUserNameByUsernames(names);
        Map<String,Long> resultMap = new HashMap<String,Long>();
        for(Map dataMap : dataList){//封装
            Long id = CastUtil.toLong(dataMap.get("customer_id"));//商户id
            String userName = (String)dataMap.get("user_name");//账号
            resultMap.put(userName,id);
        }
        return resultMap;
    }
}
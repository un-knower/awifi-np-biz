package com.awifi.np.biz.toe.admin.usrmgr.staticuser.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.security.permission.util.PermissionUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.toe.admin.usrmgr.staticuser.dao.StaticUserDao;
import com.awifi.np.biz.toe.admin.usrmgr.staticuser.model.StaticUser;
import com.awifi.np.biz.toe.admin.usrmgr.staticuser.service.StaticUserService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月8日 下午7:15:20
 * 创建作者：周颖
 * 文件名称：StaticUserServiceImpl.java
 * 版本：  v1.0
 * 功能：静态用户服务层实现类
 * 修改记录：
 */
@Service("staticUserService")
public class StaticUserServiceImpl implements StaticUserService {

    /**静态用户dao*/
    @Resource(name = "staticUserDao")
    private StaticUserDao staticUserDao;
    
    /**
     * 静态用户列表
     * @param page 页面
     * @param keywords [用户名|手机号|护照|身份证]模糊查询
     * @param merchantId 商户id
     * @param userType 用户类型
     * @param sessionUser 当前登陆账号
     * @author 周颖  
     * @throws Exception 
     * @date 2017年2月9日 上午9:15:14
     */
    public void getListByParam(Page<StaticUser> page, String keywords, Long merchantId, Integer userType, SessionUser sessionUser) throws Exception{
        String cascadeLabel = null;
        Long[] merchantIds = null;
        if(PermissionUtil.isSuperAdmin(sessionUser)){//超管 可以看全部
            
        }else if(PermissionUtil.isMerchant(sessionUser)){//登陆账号是商户  按商户层级关系[cascadeLabel]做数据权限做数据权限 
            Long curMerchantId = sessionUser.getMerchantId();//商户id
            if(curMerchantId == null){//为空 抛异常 当前组织id[{0}]对应的账号必须维护商户id[merchantId]!
                throw new BizException("E2000022", MessageUtil.getMessage("E2000022"));
            }
            cascadeLabel = sessionUser.getCascadeLabel();//商户层级
            if(StringUtils.isBlank(cascadeLabel)){//为空 抛异常 当前组织id[{0}]对应的账号必须维护商户层级[cascadeLabel]!
                throw new BizException("E2000039", MessageUtil.getMessage("E2000039"));
            }
        }else if(PermissionUtil.isMerchants(sessionUser)){// 属性含多个商户
            String[] merchantIdsArray = sessionUser.getMerchantIds().split(",");
            merchantIds = CastUtil.toLongArray(merchantIdsArray);
        }else if(PermissionUtil.isProject(sessionUser) || PermissionUtil.isLocation(sessionUser)){//地区或项目管理员 强制选择一个商户
            if(merchantId == null){
                throw new BizException("E2000059", MessageUtil.getMessage("E2000059"));//请选择一个商户!
            }
        }else{//没有维护属性 可视为超管
            
        }
        int count = staticUserDao.getCountByParam(keywords,merchantId,userType,cascadeLabel,merchantIds);//获取总数
        page.setTotalRecord(count);
        if(count <= 0){//小于等于0 直接返回
            return;
        }
        List<StaticUser> staticUserList = staticUserDao.getListByParam(keywords,merchantId,userType,cascadeLabel,merchantIds,page.getBegin(),page.getPageSize());//获取列表
        Map<Long,String> merchantNameMap = new HashMap<Long,String>();//缓存商户名称
        for(StaticUser staticUser : staticUserList){//循环列表
            Long merchantIdLong = staticUser.getMerchantId();
            String merchantName = merchantNameMap.get(merchantIdLong);
            if(StringUtils.isBlank(merchantName)){
                merchantName = MerchantClient.getNameByIdCache(merchantIdLong);
                merchantNameMap.put(merchantIdLong, merchantName);
            }
            staticUser.setMerchantName(merchantName);//补全商户名称
        }
        page.setRecords(staticUserList);
    }
    
    /**
     * 判断用户名是否存在
     * @param merchantId 商户id
     * @param userName 用户名
     * @return true 存在 false 不存在
     * @author 周颖  
     * @date 2017年2月9日 下午3:56:40
     */
    public boolean isUserNameExist(Long merchantId, String userName){
        int count = staticUserDao.getNumByUserName(merchantId,userName);
        return count > 0 ? true : false; 
    }
    
    /**
     * 判断手机号是否存在
     * @param merchantId 商户id
     * @param cellphone 手机号
     * @return true 存在 false 不存在
     * @author 周颖  
     * @date 2017年2月9日 下午4:05:13
     */
    public boolean isCellphoneExist(Long merchantId, String cellphone){
        int count = staticUserDao.getNumByCellphone(merchantId,cellphone);
        return count > 0 ? true : false; 
    }
    
    /**
     * 判断手机号是否存在
     * @param id 用户id
     * @param merchantId 商户id
     * @param cellphone 手机号
     * @return true 存在 false 不存在
     * @author 周颖  
     * @date 2017年2月9日 下午7:15:57
     */
    public boolean isCellphoneExist(Long id, Long merchantId, String cellphone){
        int count = staticUserDao.getNumByParam(id,merchantId,cellphone);
        return count > 0 ? true : false; 
    }
    
    /**
     * 新增用户
     * @param staticUser 用户
     * @author 周颖  
     * @date 2017年2月9日 下午4:23:21
     */
    public void add(StaticUser staticUser){
        staticUserDao.add(staticUser);
    }
    
    /**
     * 编辑用户
     * @param staticUser 用户
     * @author 周颖  
     * @date 2017年2月9日 下午7:38:52
     */
    public void update(StaticUser staticUser){
        staticUserDao.update(staticUser);
    }
    
    /**
     * 静态用户详情
     * @param id 主键
     * @return 详情
     * @author 周颖  
     * @throws Exception 
     * @date 2017年2月10日 上午8:33:32
     */
    public StaticUser getById(Long id) throws Exception{
        StaticUser staticUser = staticUserDao.getById(id);
        Long merchantId = staticUser.getMerchantId();
        if(merchantId != null){
            staticUser.setMerchantName(MerchantClient.getNameByIdCache(staticUser.getMerchantId()));
        }
        return staticUser;
    }
    
    /**
     * 删除单条用户
     * @param id 用户主键id
     * @author 周颖  
     * @date 2017年2月10日 上午9:18:59
     */
    public void delete(Long id){
        staticUserDao.delete(id);
    }
    
    /**
     * 批量删除用户
     * @param ids 用户ids
     * @author 周颖  
     * @date 2017年2月10日 上午9:26:20
     */
    public void batchDelete(String ids){
        String[] idsString = ids.split(",");
        Long[] idsLong = CastUtil.toLongArray(idsString);
        staticUserDao.batchDelete(idsLong);
    }
    
    /**
     * 一键删除用户 商户下的所有用户
     * @param merchantId 商户id
     * @author 周颖  
     * @date 2017年2月10日 上午9:58:15
     */
    public void deleteByMerchantId(Long merchantId){
        staticUserDao.deleteByMerchantId(merchantId);
    }
    
    /**
     * 通过 用户名、密码 获取用户id
     * @param merchantId 商户id
     * @param userName 用户名
     * @param password 密码
     * @return 用户表主键id
     * @author 许小满  
     * @date 2016年3月10日 下午8:21:39
     */
    public Long getIdByUserNameAndPwd(Long merchantId, String userName, String password){
        return staticUserDao.getIdByUserNameAndPwd(merchantId, userName, password);
    }
    
    /**
     * 更新密码
     * @param id 用户表主键id
     * @param password 密码
     * @author 许小满  
     * @date 2016年3月10日 下午10:32:31
     */
    public void updatePwd(Long id, String password){
        staticUserDao.updatePwd(id, password);
    }
    
    /**
     * 通过手机号更新密码
     * @param merchantId 商户id
     * @param cellphone 手机号
     * @param password 密码
     * @author 许小满  
     * @date 2016年3月11日 上午9:17:56
     */
    public void updatePwdByCellphone(Long merchantId, String cellphone, String password){
        staticUserDao.updatePwdByCellphone(merchantId, cellphone, password);
    }
    
    /**
     * 获取静态用户对象
     * @param customerId 客户id
     * @param userName 用户名
     * @param password 密码
     * @return 静态用户对象
     * @author 许小满  
     * @date 2016年7月25日 下午12:37:17
     */
    public StaticUser getStaticUser(Long customerId, String userName, String password){
        return staticUserDao.getStaticUser(customerId, userName, password);
    }
}
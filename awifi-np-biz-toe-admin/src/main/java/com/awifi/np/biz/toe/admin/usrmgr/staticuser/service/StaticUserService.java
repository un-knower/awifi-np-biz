package com.awifi.np.biz.toe.admin.usrmgr.staticuser.service;

import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.toe.admin.usrmgr.staticuser.model.StaticUser;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月8日 下午7:15:00
 * 创建作者：周颖
 * 文件名称：StaticUserService.java
 * 版本：  v1.0
 * 功能：静态用户服务层
 * 修改记录：
 */
public interface StaticUserService {

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
    void getListByParam(Page<StaticUser> page, String keywords, Long merchantId, Integer userType, SessionUser sessionUser) throws Exception;

    /**
     * 判断用户名是否存在
     * @param merchantId 商户id
     * @param userName 用户名
     * @return true 存在 false 不存在
     * @author 周颖  
     * @date 2017年2月9日 下午3:56:40
     */
    boolean isUserNameExist(Long merchantId, String userName);

    /**
     * 判断手机号是否存在
     * @param merchantId 商户id
     * @param cellphone 手机号
     * @return true 存在 false 不存在
     * @author 周颖  
     * @date 2017年2月9日 下午4:05:13
     */
    boolean isCellphoneExist(Long merchantId, String cellphone);

    /**
     * 判断手机号是否存在
     * @param id 用户id
     * @param merchantId 商户id
     * @param cellphone 手机号
     * @return true 存在 false 不存在
     * @author 周颖  
     * @date 2017年2月9日 下午7:15:57
     */
    boolean isCellphoneExist(Long id, Long merchantId, String cellphone);
    
    /**
     * 新增用户
     * @param staticUser 用户
     * @author 周颖  
     * @date 2017年2月9日 下午4:23:21
     */
    void add(StaticUser staticUser);

    /**
     * 编辑用户
     * @param staticUser 用户
     * @author 周颖  
     * @date 2017年2月9日 下午7:38:52
     */
    void update(StaticUser staticUser);

    /**
     * 静态用户详情
     * @param id 主键
     * @return 详情
     * @author 周颖  
     * @throws Exception 
     * @date 2017年2月10日 上午8:33:32
     */
    StaticUser getById(Long id) throws Exception;

    /**
     * 删除单条用户
     * @param id 用户主键id
     * @author 周颖  
     * @date 2017年2月10日 上午9:18:59
     */
    void delete(Long id);

    /**
     * 批量删除用户
     * @param ids 用户ids
     * @author 周颖  
     * @date 2017年2月10日 上午9:26:20
     */
    void batchDelete(String ids);

    /**
     * 一键删除用户 商户下的所有用户
     * @param merchantId 商户id
     * @author 周颖  
     * @date 2017年2月10日 上午9:58:15
     */
    void deleteByMerchantId(Long merchantId);
    
    /**
     * 通过 用户名、密码 获取用户id
     * @param merchantId 商户id
     * @param userName 用户名
     * @param password 密码
     * @return 用户表主键id
     * @author 许小满  
     * @date 2016年3月10日 下午8:21:39
     */
    Long getIdByUserNameAndPwd(Long merchantId, String userName, String password);
    
    /**
     * 更新密码
     * @param id 用户表主键id
     * @param password 密码
     * @author 许小满  
     * @date 2016年3月10日 下午10:32:31
     */
    void updatePwd(Long id, String password);
    
    /**
     * 通过手机号更新密码
     * @param merchantId 商户id
     * @param cellphone 手机号
     * @param password 密码
     * @author 许小满  
     * @date 2016年3月11日 上午9:17:56
     */
    void updatePwdByCellphone(Long merchantId, String cellphone, String password);
    
    /**
     * 获取静态用户对象
     * @param customerId 客户id
     * @param userName 用户名
     * @param password 密码
     * @return 静态用户对象
     * @author 许小满  
     * @date 2016年7月25日 下午12:37:17
     */
    StaticUser getStaticUser(Long customerId, String userName, String password);
}
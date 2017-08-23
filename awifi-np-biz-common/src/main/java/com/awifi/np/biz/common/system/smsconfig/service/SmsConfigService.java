/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月3日 下午4:12:33
* 创建作者：周颖
* 文件名称：SmsConfigService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.common.system.smsconfig.service;

import java.util.Map;

import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.system.smsconfig.model.SmsConfig;

public interface SmsConfigService {
    
    /**
     * 商户短信配置列表
     * @param user 登陆用户
     * @param page 页面信息
     * @param merchantId 商户id
     * @author 周颖  
     * @throws Exception 
     * @date 2017年5月4日 上午8:48:37
     */
    void getListByParam(SessionUser user, Page<Map<String, Object>> page, Long merchantId) throws Exception;

    /**
     * 判断商户是否已经配置
     * @param merchantId 商户id
     * @return true 已配置
     * @author 周颖  
     * @date 2017年5月3日 下午4:25:26
     */
    boolean isExist(Long merchantId);

    /**
     * 添加短信配置
     * @param merchantId 商户id
     * @param smsContent 短信内容
     * @param codeLength 验证码长度
     * @author 周颖  
     * @date 2017年5月3日 下午4:30:27
     */
    void add(Long merchantId, String smsContent, Integer codeLength);

    /**
     * 编辑短信配置
     * @param id 配置主键
     * @param smsContent 短信内容
     * @param codeLength 验证码长度
     * @author 周颖  
     * @date 2017年5月3日 下午4:37:35
     */
    void update(Long id, String smsContent, Integer codeLength);

    /**
     * 短信配置详情
     * @param id 主键id
     * @return 详情
     * @author 周颖  
     * @throws Exception 
     * @date 2017年5月3日 下午4:44:53
     */
    Map<String,Object> getById(Long id) throws Exception;
    
    /**
     * 获取该客户的短信配置信息
     * @param merchantId 商户id
     * @return 客户短信配置信息
     * @author ZhouYing 
     * @date 2016年12月6日 上午11:08:22
     */
    SmsConfig getByCustomerId(Long merchantId);

    /**
     * 短信配置删除
     * @param id 主键id
     * @author 周颖  
     * @date 2017年5月4日 上午10:56:42
     */
    void delete(Long id);
}

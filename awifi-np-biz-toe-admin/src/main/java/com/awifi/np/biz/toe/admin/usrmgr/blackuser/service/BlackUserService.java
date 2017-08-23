package com.awifi.np.biz.toe.admin.usrmgr.blackuser.service;

import java.util.List;

import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.toe.admin.usrmgr.blackuser.model.BlackUser;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月13日 上午8:51:19
 * 创建作者：周颖
 * 文件名称：BlackUserService.java
 * 版本：  v1.0
 * 功能：黑名单服务层
 * 修改记录：
 */
public interface BlackUserService {

    /**
     * 黑名单列表
     * @param sessionUser 当前登陆账号
     * @param page 页面
     * @param merchantId 商户id
     * @param matchRule 匹配规则
     * @param keywords 关键字
     * @author 周颖  
     * @throws Exception 
     * @date 2017年2月13日 上午9:20:15
     */
    void getListByParam(SessionUser sessionUser, Page<BlackUser> page, Long merchantId, Integer matchRule, String keywords) throws Exception;

    /**
     * 判断手机号是否加黑名单
     * @param merchantId 商户id
     * @param cellphone 手机号
     * @return true 存在 false 不存在
     * @author 周颖  
     * @date 2017年2月13日 上午11:20:35
     */
    boolean isCellphoneExist(Long merchantId, String cellphone);

    /**
     * 添加黑名单
     * @param blackUser 黑名单
     * @author 周颖  
     * @date 2017年2月13日 下午1:45:57
     */
    void add(BlackUser blackUser);

    /**
     * 删除黑名单
     * @param id 黑名单主键id
     * @author 周颖  
     * @date 2017年2月13日 下午1:58:53
     */
    void delete(Long id);
    
    /**
     * 获取指定客户下所有的匹配规则
     * @param merchantId 商户id
     * @return 匹配规则
     * @author 许小满  
     * @date 2016年10月31日 下午12:25:34
     */
    List<Integer> getMatchRulesByMerchantId(Long merchantId);
    
    /**
     * 判断是否黑名单
     * @param cellphone 手机号
     * @param merchantId 商户id
     * @return yes 是黑名单 、no 不是黑名单
     * @author ZhouYing  
     * @date 2016年6月14日 上午9:11:23
     */
    String isBlackForRule1(String cellphone, Long merchantId);
    
    /**
     * 判断是否黑名单 - 模糊匹配
     * @param cellphone 手机号
     * @param merchantId 商户id
     * @return yes 是黑名单 、no 不是黑名单
     * @author 许小满  
     * @date 2016年10月31日 下午2:23:54
     */
    String isBlackForRule2(String cellphone, Long merchantId);
    
    /**
     * 获取指定客户下的所有手机号
     * @param merchantId 商户id
     * @param matchRule 匹配规则：1 精确、2 模糊
     * @return 手机号
     * @author 许小满  
     * @date 2016年10月31日 下午12:36:23
     */
    List<String> getCellphonesByMerchantId(Long merchantId, Integer matchRule);
}
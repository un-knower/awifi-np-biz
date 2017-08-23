package com.awifi.np.biz.appsrv.app.service;

import java.util.Map;

import com.awifi.np.biz.appsrv.app.model.App;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.security.user.model.SessionUser;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017年7月10日 下午2:33:54
 * 创建作者：许尚敏
 * 文件名称：AppService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface AppService {

    /**
     * 应用管理—应用添加
     * @param app 实体类
     * @author 许尚敏  
     * @date 2017年7月10日 下午3:11:24
     */
    void add(App app);

    
    /**
     * 应用管理—应用编辑
     * @param app 实体类
     * @author 许尚敏  
     * @date 2017年7月11日 上午10:45:20
     */
    void update(App app);

    /**
     * 应用管理--分页查询接口
     * @param sessionUser sessionUser
     * @param page page
     * @param appName 应用名称
     * @param status 状态
     * @author 季振宇  
     * @date Jul 10, 2017 3:50:14 PM
     */
    void getListByParam(SessionUser sessionUser,Page<App> page, String appName, Integer status);

    /**
     * 应用管理--应用列表-详情接口
     * @param id 应用id
     * @return 应用详情
     * @author 季振宇  
     * @date Jul 12, 2017 10:06:05 AM
     */
    App getById(Long id);
    
    /**
     * 应用管理--删除应用接口
     * @param id 应用id
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jul 12, 2017 11:05:04 AM
     */
    void delete(Long id) throws Exception;
    
    /**
     * @param appId 应用id
     * @return app信息
     * @author 王冬冬  
     * @date 2017年7月12日 上午9:34:38
     */
    App queryAppByAppId(String appId);

    /**
     * 第三方单点登录url
     * @param merchantId 商户Id
     * @param appId 应用id
     * @return map
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年7月12日 下午3:18:57
     */
    Map<String, String> getSsoUrl(Long merchantId, String appId) throws Exception;


    /**
     * 关联配置
     * @param merchantId 商户id
     * @param queryMerchantId 关联商户id
     * @param appId 应用id
     * @return
     * @author 王冬冬  
     * @param page 
     * @throws Exception 
     * @date 2017年7月12日 下午3:18:59
     */
    void getMerchantListByParam(Long merchantId,Long queryMerchantId,String appId, Page<Map> page) throws Exception;


    /**
     * 通过商户id[merchantId]、应用id[appId]查询公众号信息
     * @param merchantId 商户id
     * @param appId 应用Id
     * @return map
     * @author 王冬冬  
     * @throws Exception 
     * @date 2017年7月13日 下午3:44:45
     */
    Map<String, Object> getByParams(Long merchantId, String appId) throws Exception;

    /**
     * 获取access_token
     * @param appId appId
     * @param timestamp 时间戳
     * @param token 令牌
     * @return 结果
     * @author 周颖  
     * @date 2017年7月14日 下午3:16:54
     */
    Map<String, Object> getAccessToken(String appId, String timestamp,String token);
}

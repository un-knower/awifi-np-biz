package com.awifi.np.biz.api.client.npadmin.service;

import java.util.List;
import java.util.Map;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月13日 上午8:52:25
 * 创建作者：亢燕翔
 * 文件名称：DataInterfaceService.java
 * 版本：  v1.0
 * 功能：  管理系统数据接口服务层
 * 修改记录：
 */
public interface NPAdminApiService {

    /**
     * 调用管理系统数据接口
     * @param accessToken 令牌
     * @param serviceCode 服务编号
     * @param interfaceCode 接口编号
     * @param params 参数
     * @return json
     * @throws Exception 异常
     * @author 许小满  
     * @date 2017年2月17日 下午4:48:21
     */
    Map<String, Object> dataInterface(String accessToken, String serviceCode, String interfaceCode, String params) throws Exception;

    /**
     * 管理系统显示接口
     * @param serviceCode 服务code
     * @param suitCode 套码
     * @param templateCode 模板code
     * @param accessToken access_token
     * @return json
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月17日 上午11:01:54
     */
    Map<String, Object> viewInterface(String serviceCode, String suitCode, String templateCode,String accessToken) throws Exception;

    /**
     * 推送接口注册信息
     * @param params 请求参数
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月20日 上午11:23:12
     */
    void pushInterfaces(String params) throws Exception;

    Map<String,Object> getInterfacesByParam(String params) throws Exception;
}

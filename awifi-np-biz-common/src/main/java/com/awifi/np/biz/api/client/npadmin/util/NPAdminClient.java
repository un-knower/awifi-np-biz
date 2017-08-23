package com.awifi.np.biz.api.client.npadmin.util;

import java.util.Map;

import com.awifi.np.biz.api.client.npadmin.service.NPAdminApiService;
import com.awifi.np.biz.common.util.BeanUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月13日 上午8:52:16
 * 创建作者：亢燕翔
 * 文件名称：DataInterfaceClient.java
 * 版本：  v1.0
 * 功能：  管理系统数据接口client
 * 修改记录：
 */
public class NPAdminClient {

    /** 管理系统数据接口服务层  */
    private static NPAdminApiService npAdminApiService;
    
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
    public static Map<String, Object> dataInterface(String accessToken, String serviceCode, String interfaceCode, String params) throws Exception{
        return getNPAdminApiService().dataInterface(accessToken, serviceCode, interfaceCode, params);
    }
    
    /**
     * 管理平台显示接口
     * @param serviceCode 服务code
     * @param suitCode 套码
     * @param templateCode 模板code
     * @param accessToken access_token
     * @return json
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月17日 下午5:13:41
     */
    public static Map<String, Object> viewInterface(String serviceCode, String suitCode, String templateCode,String accessToken) throws Exception{
        return getNPAdminApiService().viewInterface(serviceCode,suitCode,templateCode,accessToken);
    }
    
    /**
     * 推送接口注册信息
     * @param params 请求参数
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月20日 上午11:22:31
     */
    public static void pushInterfaces(String params) throws Exception {
        getNPAdminApiService().pushInterfaces(params);
    }   
    
    /**
     * 获取dataInterfaceService实例
     * @return dataInterfaceService
     * @author 亢燕翔  
     * @date 2017年1月13日 上午9:08:01
     */
    private static NPAdminApiService getNPAdminApiService(){
        if(null == npAdminApiService){
            npAdminApiService = (NPAdminApiService) BeanUtil.getBean("npAdminApiService");
        }
        return npAdminApiService;
    }

	/**
	 * 查询权限
	 * @param params 查询参数
	 * @return map
	 * @throws Exception 异常
	 * @author 王冬冬  
	 * @date 2017年4月13日 下午3:26:51
	 */
    public static Map<String,Object> getInterfacesByParam(String params) throws Exception {
    	return  getNPAdminApiService().getInterfacesByParam(params);
    }
}
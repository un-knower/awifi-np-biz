package com.awifi.np.biz.api.client.npadmin.service.impl;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.npadmin.service.NPAdminApiService;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.FormatUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月13日 上午8:52:37
 * 创建作者：亢燕翔
 * 文件名称：DataInterfaceServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Service("npAdminApiService")
public class NPAdminApiServiceImpl implements NPAdminApiService{

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
    public Map<String, Object> dataInterface(String accessToken, String serviceCode, String interfaceCode, String params) throws Exception {
        Map<String, String> parameterMap = new HashMap<String, String>();//参数map
        parameterMap.put("access_token", accessToken);//令牌
        parameterMap.put("servicecode", serviceCode);//服务编号
        parameterMap.put("interfacecode", interfaceCode);//接口编号
        parameterMap.put("params", params);//参数
        String interfaceUrl = SysConfigUtil.getParamValue("np_admin_data_interface_url"); //接口url
//        String interfaceUrl="http://localhost:8080/mocksrv/admin/call";
        String interfaceParam = HttpRequest.getParams(parameterMap);//接口参数
        ByteBuffer byteBuffer = HttpRequest.sendGetRequest(interfaceUrl, interfaceParam);
        return FormatUtil.formatNPAdminByteBuffer(interfaceUrl, interfaceParam, byteBuffer);
    }

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
    public Map<String, Object> viewInterface(String serviceCode, String suitCode, String templateCode,String accessToken) throws Exception{
        Map<String, String> parameterMap = new HashMap<String, String>();
        parameterMap.put("servicecode", serviceCode);
        parameterMap.put("suitcode", suitCode);
        parameterMap.put("templatecode", templateCode);
        parameterMap.put("access_token", accessToken);
        String interfaceUrl = SysConfigUtil.getParamValue("np_admin_view_interface_url"); //接口url
        String interfaceParam = HttpRequest.getParams(parameterMap);//接口参数
        ByteBuffer byteBuffer = HttpRequest.sendGetRequest(interfaceUrl, interfaceParam);
        return FormatUtil.formatNPAdminByteBuffer(interfaceUrl, interfaceParam, byteBuffer);
    }

    /**
     * 推送接口注册信息
     * @param params 请求参数
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月20日 上午11:23:34
     */
    public void pushInterfaces(String params) throws Exception {
        String url = SysConfigUtil.getParamValue("np_admin_register_interface_url");//接口url
        ByteBuffer byteBuffer = HttpRequest.sendPostRequest(url, null, params);
        FormatUtil.formatNPAdminByteBuffer(url, params, byteBuffer);
    }

    /**
     * 根据参数查询角色对应的接口权限
     * @param params 参数
     * @return map
     * @author 王冬冬  
     * @date 2017年4月13日 下午4:56:04
     */
    @Override
	public Map<String,Object> getInterfacesByParam(String params) throws Exception {
    	String url = SysConfigUtil.getParamValue("np_admin_query_interface_url");//接口url
        //String url="http://192.168.3.39:8080/externalapi/query/interface";
        ByteBuffer byteBuffer = HttpRequest.sendGetRequest(url,params);
        return FormatUtil.formatNPAdminByteBuffer(url, params, byteBuffer);
    }
}
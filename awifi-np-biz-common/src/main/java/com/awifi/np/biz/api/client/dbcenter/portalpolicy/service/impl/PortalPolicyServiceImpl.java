/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月2日 上午9:30:41
* 创建作者：周颖
* 文件名称：PortalPolicyServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.portalpolicy.service.impl;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.api.client.dbcenter.portalpolicy.service.PortalPolicyService;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;

@Service("portalPolicyService")
public class PortalPolicyServiceImpl implements PortalPolicyService {

    /**
     * 查看商户无感知配置
     * @param merchantId 商户无感知
     * @return 无感知
     * @author 周颖  
     * @throws Exception 
     * @date 2017年5月16日 下午1:45:08
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getByMerchantId(Long merchantId) throws Exception{
        String url = SysConfigUtil.getParamValue("dbc_getportalpolicy_url");//获取请求地址
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("merchantId", merchantId);//入参
        String paramString = "params="+URLEncoder.encode(JsonUtil.toJson(paramMap), "UTF-8");
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);//返回成功的数据
        Map<String,Object> portalPolicyMap = (Map<String, Object>) returnMap.get("rs");
        return portalPolicyMap;
    }
    
    /**
     * 添加无感知
     * @param params 参数
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年5月17日 上午9:06:57
     */
    public void add(Map<String, Object> params) throws Exception{
        String url = SysConfigUtil.getParamValue("dbc_addportalpolicy_url");//获取请求地址
        CenterHttpRequest.sendPostRequest(url, JsonUtil.toJson(params));//返回成功的数据
    }
    
    /**
     * 编辑无感知
     * @param params 参数
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年5月17日 上午9:11:47
     */
    public void update(Map<String, Object> params) throws Exception{
        String url = SysConfigUtil.getParamValue("dbc_updateportalpolicy_url");//获取请求地址
        CenterHttpRequest.sendPutRequest(url, JsonUtil.toJson(params));//返回成功的数据
    }
    
    /**
     * 删除无感知
     * @param id 主键id
     * @author 周颖  
     * @throws Exception 
     * @date 2017年6月2日 下午1:51:29
     */
    public void delete(Long id) throws Exception{
        String url = SysConfigUtil.getParamValue("dbc_deleteportalpolicy_url");//获取请求地址
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);//入参
        String paramString = "params="+URLEncoder.encode(JsonUtil.toJson(paramMap), "UTF-8");
        CenterHttpRequest.sendDeleteRequest(url, paramString);//返回成功的数据
    }
}

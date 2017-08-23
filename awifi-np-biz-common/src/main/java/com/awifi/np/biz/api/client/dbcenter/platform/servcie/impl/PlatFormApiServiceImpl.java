package com.awifi.np.biz.api.client.dbcenter.platform.servcie.impl;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.api.client.dbcenter.platform.servcie.PlatFormApiServcie;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;

@Service(value="platFormApiServcie")
public class PlatFormApiServiceImpl implements PlatFormApiServcie{

    /**
     * 开放平台接口PlatformService
     */
    
    @Override
    public Integer queryPlatformCountByParam(Map<String, Object> params) throws Exception {
        // TODO Auto-generated method stub
        String interfaceParam="params="+URLEncoder.encode(JsonUtil.toJson(params), "UTF-8");
        String url=SysConfigUtil.getParamValue("np_biz_dbc_platform_queryPlatformCountByParam_url");
        Map<String,Object> map=CenterHttpRequest.sendGetRequest(url, interfaceParam);
        Integer count=(Integer) map.get("rs");
        return count;
    }

    @Override
    public Map<String,Object> queryPlatformListByParam(Map<String, Object> params) throws Exception {
        String interfaceParam="params="+URLEncoder.encode(JsonUtil.toJson(params), "UTF-8");
        String url=SysConfigUtil.getParamValue("np_biz_dbc_platform_queryPlatformListByParam_url");
        Map<String,Object> map=CenterHttpRequest.sendGetRequest(url, interfaceParam);
        return map;
    }

    @Override
    public Map<String,Object> queryPlatformById(Map<String, Object> param) throws Exception {
        String interfaceParam="params="+URLEncoder.encode(JsonUtil.toJson(param), "UTF-8");
        String url=SysConfigUtil.getParamValue("np_biz_dbc_platform_queryPlatformById_url");
        Map<String,Object> map=CenterHttpRequest.sendGetRequest(url, interfaceParam);
        return map;
    }

    @Override
    public Map<String, Object> editPlatForm(Map<String, Object> params) throws Exception {
        String url=SysConfigUtil.getParamValue("np_biz_dbc_platform_editPlatForm_url");
        return CenterHttpRequest.sendPutRequest(url, JsonUtil.toJson(params));
    }

    @Override
    public void addPlatForm(Map<String,Object> params) throws Exception {
        String url=SysConfigUtil.getParamValue("np_biz_dbc_platform_addPlatForm_url");
        CenterHttpRequest.sendPostRequest(url, JsonUtil.toJson(params));
    }

    @Override
    public void deletePaltform(Long id) throws Exception {
        String url=SysConfigUtil.getParamValue("np_biz_dbc_platform_deletePaltform_url");//省分平台删除操作url
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);// 主键
        params.put("globalKey", "");
        params.put("globalValue", "");
        params.put("globalStandby", "");
        String interfaceParam = "params="+ URLEncoder.encode(JsonUtil.toJson(params), "UTF-8");
        CenterHttpRequest.sendDeleteRequest(url, interfaceParam);
    }

}

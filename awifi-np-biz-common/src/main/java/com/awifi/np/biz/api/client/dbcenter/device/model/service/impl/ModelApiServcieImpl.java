package com.awifi.np.biz.api.client.dbcenter.device.model.service.impl;

import java.net.URLEncoder;
import java.util.Map;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.awifi.np.biz.api.client.dbcenter.device.model.service.ModelApiServcie;
import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;

/**
 * modelServcie接口
 *
 */

@Service(value="modelApiServcie")
public class ModelApiServcieImpl implements ModelApiServcie{
    @Override
    public int queryCountByParam(Map<String, Object> params) throws Exception {
        // TODO Auto-generated method stub
        String url=SysConfigUtil.getParamValue("dbc_ModelQueryCount_url");
        String interfaceParams="params="+URLEncoder.encode(JsonUtil.toJson(params), "UTF-8");
        Map<String,Object> returnCount=CenterHttpRequest.sendGetRequest(url, interfaceParams);
        int count=(int) returnCount.get("rs");
        return count;
    }

    @Override
    public String queryListByParam(Map<String,Object> params) throws Exception {
        // TODO Auto-generated method stub
        String url = SysConfigUtil.getParamValue("dbc_ModelQueryList_url");;
        String interfaceParams="params="+URLEncoder.encode(JsonUtil.toJson(params), "UTF-8");
        Map<String,Object> returnList=CenterHttpRequest.sendGetRequest(url, interfaceParams);
        JSONArray array=  (JSONArray) returnList.get("rs");
        return array.toJSONString();
    }
    
    
    

}

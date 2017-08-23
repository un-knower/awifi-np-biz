/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月26日 下午2:36:31
* 创建作者：范立松
* 文件名称：IotApiServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.iot.service.impl;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.api.client.dbcenter.iot.service.IotApiService;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;

@SuppressWarnings("unchecked")
@Service("iotApiService")
public class IotApiServiceImpl implements IotApiService {

    /**
     * 分页查询物联网设备列表
     * @author 范立松  
     * @date 2017年4月26日 下午2:57:21
     */
    @Override
    public List<Map<String, Object>> getIotList(Map<String, Object> paramsMap) throws Exception {
        // TODO 填入数据中心查询物联网设备信息的地址
        String url = SysConfigUtil.getParamValue("--");// 获取请求地址
        String paramString = "params=" + URLEncoder.encode(JsonUtil.toJson(paramsMap), "UTF-8");
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);
        return (List<Map<String, Object>>) returnMap.get("rs");// 物联网设备信息列表
    }

    /**
     * 删除物联网设备信息
     * @author 范立松  
     * @date 2017年4月26日 下午2:56:57
     */
    @Override
    public void removeIotByIds(Map<String, Object> paramsMap) throws Exception {
        // TODO 填入数据中心删除物联网设备的地址
        String url = SysConfigUtil.getParamValue("--");// 获取请求地址
        CenterHttpRequest.sendDeleteRequest(url, JsonUtil.toJson(paramsMap));
    }

    /**
     * 查询物联网设备数量
     * @author 范立松  
     * @date 2017年4月26日 下午2:57:08
     */
    @Override
    public int countIotByParam(Map<String, Object> paramsMap) throws Exception {
        // TODO 填入数据中心查询物联网设备数量的地址
        String url = SysConfigUtil.getParamValue("--");// 获取请求地址
        String paramString = "params=" + URLEncoder.encode(JsonUtil.toJson(paramsMap), "UTF-8");
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);
        return (Integer) returnMap.get("rs");// 物联网设备数量
    }

}

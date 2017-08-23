/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月20日 上午9:29:04
* 创建作者：范立松
* 文件名称：FreeAuthApiServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.freeauth.service.impl;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.freeauth.service.FreeAuthApiService;
import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;

@Service("freeAuthApiService")
@SuppressWarnings("unchecked")
public class FreeAuthApiServiceImpl implements FreeAuthApiService {

    /**
     * 添加设备区域信息
     * @author 范立松  
     * @date 2017年4月21日 下午2:43:39
     */
    @Override
    public void addDeviceArea(Map<String, Object> paramsMap) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_adddevicearea_url");// 获取请求地址
        CenterHttpRequest.sendPostRequest(url, JsonUtil.toJson(paramsMap));
    }

    /**
     * 修改设备区域信息
     * @author 范立松  
     * @date 2017年4月21日 下午2:43:41
     */
    @Override
    public void updateDeviceArea(Map<String, Object> paramsMap) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_updatedevicearea_url");// 获取请求地址
        CenterHttpRequest.sendPutRequest(url, JsonUtil.toJson(paramsMap));
    }

    /**
     * 分页查询设备区域列表
     * @author 范立松  
     * @date 2017年4月25日 下午4:24:34
     */
    @Override
    public List<Map<String, Object>> getDeviceAreaList(Map<String, Object> paramsMap) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_getdevicearea_url");// 获取请求地址
        String paramString = "params=" + URLEncoder.encode(JsonUtil.toJson(paramsMap), "UTF-8");
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);
        return (List<Map<String, Object>>) returnMap.get("rs");// 设备区域信息列表
    }

    /**
     * 根据区域id删除区域和区域设备关系
     * @author 范立松  
     * @date 2017年4月25日 下午4:24:48
     */
    @Override
    public void removeDeviceAreaById(Map<String, Object> paramsMap) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_deleteareaandrel_url");// 获取请求地址
        String paramString = "params=" + URLEncoder.encode(JsonUtil.toJson(paramsMap), "UTF-8");
        CenterHttpRequest.sendDeleteRequest(url, paramString);
    }

    /**
     * 批量添加设备与区域关联信息
     * @author 范立松  
     * @date 2017年4月25日 下午4:25:00
     */
    @Override
    public void addDeviceAreaRel(List<Map<String, Object>> paramsMap) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_adddevicearearel_url");// 获取请求地址
        CenterHttpRequest.sendPostRequest(url, JsonUtil.toJson(paramsMap));
    }

    /**
     * 根据设备id删除区域和设备关系
     * @author 范立松  
     * @date 2017年4月25日 下午4:25:15
     */
    @Override
    public void removeRelByDevId(List<Map<String, Object>> paramsMap) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_deletedevicearearel_url");// 获取请求地址
        String paramString = "params=" + URLEncoder.encode(JsonUtil.toJson(paramsMap), "UTF-8");
        CenterHttpRequest.sendDeleteRequest(url, paramString);
    }

    /**
     * 根据区域id查询设备与区域关系
     * @author 范立松  
     * @date 2017年4月25日 下午4:25:28
     */
    @Override
    public List<Map<String, Object>> getRelListByAreaId(Map<String, Object> paramsMap) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_getdevicearearel_url");// 获取请求地址
        String paramString = "params=" + URLEncoder.encode(JsonUtil.toJson(paramsMap), "UTF-8");
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);
        return (List<Map<String, Object>>) returnMap.get("rs");// 设备与区域关系列表
    }

    /**
     * 按条件查询设备区域记录数
     * @author 范立松  
     * @date 2017年4月25日 下午4:25:41
     */
    @Override
    public int queryAreaCountByParam(Map<String, Object> paramsMap) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_countdevicearea_url");// 获取请求地址
        String paramString = "params=" + URLEncoder.encode(JsonUtil.toJson(paramsMap), "UTF-8");
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);
        return (Integer) returnMap.get("rs");// 设备区域数量
    }

    /**
     * 按条件查询设备与区域关联记录数
     * @author 范立松  
     * @date 2017年4月26日 下午5:21:15
     */
    @Override
    public int queryRelCountByParam(Map<String, Object> paramsMap) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_countdevicearearel_url");// 获取请求地址
        String paramString = "params=" + URLEncoder.encode(JsonUtil.toJson(paramsMap), "UTF-8");
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);
        return (Integer) returnMap.get("rs");// 设备与区域关联数量
    }

    /**
     * 按条件查询设备与区域关联时可选择的设备
     * @author 范立松  
     * @date 2017年5月17日 上午10:03:31
     */
    @Override
    public List<Map<String, Object>> getChooseableDeviceList(Map<String, Object> paramsMap) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_getchooseabledevice_url");// 获取请求地址
        String paramString = "params=" + URLEncoder.encode(JsonUtil.toJson(paramsMap), "UTF-8");
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);
        return (List<Map<String, Object>>) returnMap.get("rs");// 可选择的设备列表
    }

    /**
     * 按条件查询可选择的设备数量
     * @author 范立松  
     * @date 2017年5月17日 上午10:04:52
     */
    @Override
    public int queryChooseableDeviceCount(Map<String, Object> paramsMap) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_countchooseabledevice_url");// 获取请求地址
        String paramString = "params=" + URLEncoder.encode(JsonUtil.toJson(paramsMap), "UTF-8");
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);
        return (Integer) returnMap.get("rs");// 可选择的设备数量
    }

}

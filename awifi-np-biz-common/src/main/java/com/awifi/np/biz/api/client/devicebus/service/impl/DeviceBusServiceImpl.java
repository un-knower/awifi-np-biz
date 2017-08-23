package com.awifi.np.biz.api.client.devicebus.service.impl;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.biz.api.client.dbcenter.device.device.model.DeviceOwner;
import com.awifi.np.biz.api.client.dbcenter.device.entity.model.EntityInfo;
import com.awifi.np.biz.api.client.devicebus.service.DeviceBusService;
import com.awifi.np.biz.common.base.constants.Constants;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.FormatUtil;
import com.awifi.np.biz.common.util.JsonUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月6日 下午5:12:24
 * 创建作者：亢燕翔
 * 文件名称：DeviceBusServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Service("deviceBusService")
public class DeviceBusServiceImpl implements DeviceBusService{
    
    /**
     * 设备激活
     * @param data data
     * @author 亢燕翔  
     * @date 2017年2月6日 下午5:14:58
     */
    public void setFatAPRegister(String data){
        String url = SysConfigUtil.getParamValue("devicebus_register_url");//接口url
        StringBuilder params = new StringBuilder();
        params.append("data").append('=').append(data);
        String interfaceParam = params.toString();
        ByteBuffer byteBuffer = HttpRequest.sendPostRequest(url, interfaceParam, null);
        FormatUtil.formatBusByteBuffer(url, interfaceParam, byteBuffer);
    }

    /**
     * 设置ssid
     * @param devMac 设备MAC
     * @param ssid SSID
     * @throws Exception 
     * @date 2017年2月8日 下午2:39:13
     */
    public void setFatAPSSID(String devMac, String ssid) {
        //请求参数封装
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("platform", Constants.NP);//调用者所在平台   
        paramMap.put("version", "1.0");//接口版本
        paramMap.put("devMac", devMac);//设备mac
        paramMap.put("hostname", "aWiFi");//设备型号,可为空
        paramMap.put("expiredTime", Integer.parseInt(SysConfigUtil.getParamValue("devicebus_expired_time")));//任务过期时间，单位秒  最大值一天
        paramMap.put("ssid", ssid);//新ssid名称
        String url = SysConfigUtil.getParamValue("devicebus_editssid_url");//接口url
        StringBuilder params = new StringBuilder();
        params.append("data").append('=').append(JsonUtil.toJson(paramMap));
        String interfaceParam = params.toString();
        ByteBuffer byteBuffer = HttpRequest.sendPostRequest(url, interfaceParam, null);
        FormatUtil.formatBusByteBuffer(url, interfaceParam, byteBuffer);
    }

    /**
     * 设备放通
     * @param data data
     * @author 亢燕翔  
     * @date 2017年2月15日 下午6:15:08
     */
    public void setFatAPEscape(String data){
        String url = SysConfigUtil.getParamValue("devicebus_fatapescape_url");//接口url
        StringBuilder params = new StringBuilder();
        params.append("data").append('=').append(data);
        String interfaceParam = params.toString();
        ByteBuffer byteBuffer = HttpRequest.sendPostRequest(url, interfaceParam, null);
        FormatUtil.formatBusByteBuffer(url, interfaceParam, byteBuffer);
    }

    /**
     * Chinanet开关接口
     * @param data data
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月16日 上午9:05:05
     */
    public void setFatAPSSIDSwitch(String data){
        String url = SysConfigUtil.getParamValue("devicebus_fatapssidswitch_url");//接口url
        StringBuilder params = new StringBuilder();
        params.append("data").append('=').append(data);
        String interfaceParam = params.toString();
        ByteBuffer byteBuffer = HttpRequest.sendPostRequest(url, interfaceParam, null);
        FormatUtil.formatBusByteBuffer(url, interfaceParam, byteBuffer);
    }

    /**
     * aWiFi开关接口
     * @param data data
     * @author 亢燕翔  
     * @date 2017年2月16日 上午9:14:27
     */
    
    public void setFatAPAWiFiSwitch(String data){
        String url = SysConfigUtil.getParamValue("devicebus_fatapawifiswitch_url");//接口url
        StringBuilder params = new StringBuilder();
        params.append("data").append('=').append(data);
        String interfaceParam = params.toString();
        ByteBuffer byteBuffer = HttpRequest.sendPostRequest(url, interfaceParam, null);
        FormatUtil.formatBusByteBuffer(url, interfaceParam, byteBuffer);
    }

    /**
     * LAN口认证开关接口
     * @param data data
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月16日 上午9:20:57
     */
    
    public void setFatAPLANSwitch(String data){
        String url = SysConfigUtil.getParamValue("devicebus_fataplanswitch_url");//接口url
        StringBuilder params = new StringBuilder();
        params.append("data").append('=').append(data);
        String interfaceParam = params.toString();
        ByteBuffer byteBuffer = HttpRequest.sendPostRequest(url, interfaceParam, null);
        FormatUtil.formatBusByteBuffer(url, interfaceParam, byteBuffer);
    }

    /**
     * 闲时下线接口
     * @param data data
     * @author 亢燕翔  
     * @date 2017年2月16日 上午9:29:29
     */
    public void setFatAPClientTimeout(String data){
        String url = SysConfigUtil.getParamValue("devicebus_fattimeout_url");//接口url
        StringBuilder params = new StringBuilder();
        params.append("data").append('=').append(data);
        String interfaceParam = params.toString();
        ByteBuffer byteBuffer = HttpRequest.sendPostRequest(url, interfaceParam, null);
        FormatUtil.formatBusByteBuffer(url, interfaceParam, byteBuffer);
    }

    /**
     * @param deviceOwnersList 设备绑定列表
     * @author 王冬冬  
     * @date 2017年5月17日 下午1:51:10
     */
    public void setFatAPSSID(List<DeviceOwner> deviceOwnersList){
        
        String url = SysConfigUtil.getParamValue("devicebus_editssid_url");//接口url
        //请求参数封装
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("platform", Constants.NP);//调用者所在平台   
        paramMap.put("version", "1.0");//接口版本
       
        paramMap.put("hostname", "aWiFi");//设备型号,可为空
        paramMap.put("expiredTime", Integer.parseInt(SysConfigUtil.getParamValue("devicebus_expired_time")));//任务过期时间，单位秒  最大值一天
        for(DeviceOwner owner:deviceOwnersList){
            paramMap.put("devMac", owner.getMac());//设备mac
            paramMap.put("ssid", owner.getSsid());//新ssid名称
            
            StringBuilder params = new StringBuilder();
            params.append("data").append('=').append(JsonUtil.toJson(paramMap));
            String interfaceParam = params.toString();
            ByteBuffer byteBuffer = HttpRequest.sendPostRequest(url, interfaceParam, null);
            FormatUtil.formatBusByteBuffer(url, interfaceParam, byteBuffer);
        } 
    }

    /**
     * @param devMac 设备mac
     * @return map
     * @author 王冬冬  
     * @date 2017年5月17日 下午1:51:10
     */
    public Map getDeviceData(String devMac){
        //请求参数封装
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("platform", Constants.NP);//调用者所在平台   
        paramMap.put("version", "1.0");//接口版本
        List<String> devmacList=new ArrayList<String>();
        if(!StringUtils.isBlank(devMac)&&!"undefined".equalsIgnoreCase(devMac)){
            devmacList.add(devMac);
        }
        paramMap.put("devMacList", devmacList);//设备mac
       
        String url = SysConfigUtil.getParamValue("devicebus_DeviceData_url");//接口url
        StringBuilder params = new StringBuilder();
        params.append("data").append('=').append(JsonUtil.toJson(paramMap));
        String interfaceParam = params.toString();
        ByteBuffer byteBuffer = HttpRequest.sendPostRequest(url, interfaceParam, null);
        Map<String,Object> result= FormatUtil.formatBusByteBuffer(url, interfaceParam, byteBuffer);
        Map json=(Map) result.get("data");
        return json;
    }

    /**
     * @param devMac 设备mac
     * @return map
     * @author 王冬冬  
     * @date 2017年6月6日 上午9:30:00
     */
    public Map getMerchantDeviceData(String devMac){
        //请求参数封装
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("platform", Constants.NP);//调用者所在平台   
        paramMap.put("version", "1.0");//接口版本
        paramMap.put("devMac", devMac);//设备mac
       
        String url = SysConfigUtil.getParamValue("devicebus_DeviceMerchants_url");//接口url
        StringBuilder params = new StringBuilder();
        params.append("data").append('=').append(JsonUtil.toJson(paramMap));
        String interfaceParam = params.toString();
        ByteBuffer byteBuffer = HttpRequest.sendPostRequest(url, interfaceParam, null);
        Map<String,Object> result= FormatUtil.formatBusByteBuffer(url, interfaceParam, byteBuffer);
        Map json=(Map) result.get("data");
        return json;
    }

    
    /**
     * @param devMac 设备mac
     * @param macList mac列表
     * @return 调用结果
     * @author 王冬冬  
     * @date 2017年8月2日 下午2:25:26
     */
    public String setWhiteUser(String devMac,List<String> macList) {
        String url = SysConfigUtil.getParamValue("devicebus_whiteuser_url");//接口url
        //请求参数封装
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("platform", Constants.NP);//调用者所在平台   
        paramMap.put("version", "1.0");//接口版本
        paramMap.put("devMac", devMac);//设备mac
        paramMap.put("expiredTime", Integer.parseInt(SysConfigUtil.getParamValue("devicebus_expired_time")));//任务过期时间，单位秒  最大值一天
        paramMap.put("userMacList", macList);//新ssid名称
//        String url = SysConfigUtil.getParamValue("devicebus_editssid_url");//接口url
        StringBuilder params = new StringBuilder();
        params.append("data").append('=').append(JsonUtil.toJson(paramMap));
        String interfaceParam = params.toString();
        ByteBuffer byteBuffer = HttpRequest.sendPostRequest(url,interfaceParam, null);
        String rs=JSONObject.toJSONString(FormatUtil.formatBusByteBuffer(url,interfaceParam, byteBuffer));
        return rs;
    }
}

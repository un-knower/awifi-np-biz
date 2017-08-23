package com.awifi.np.biz.api.client.devicebus.util;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.biz.api.client.dbcenter.device.device.model.DeviceOwner;
import com.awifi.np.biz.api.client.dbcenter.device.entity.model.EntityInfo;
import com.awifi.np.biz.api.client.devicebus.service.DeviceBusService;
import com.awifi.np.biz.common.util.BeanUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月6日 下午5:11:48
 * 创建作者：亢燕翔
 * 文件名称：DeviceBusClient.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class DeviceBusClient {

    /**设备总线*/
    private static DeviceBusService deviceBusService;
    
    /**
     * 设备激活
     * @param data 请求参数
     * @author 亢燕翔  
     * @date 2017年2月6日 下午5:14:20
     */
    public static void setFatAPRegister(String data){
        getDeviceBusService().setFatAPRegister(data);
    }
    
    /**
     * 设置ssid
     * @param devMac 设备mac
     * @param ssid 热点
     * @author 亢燕翔  
     * @date 2017年2月8日 下午2:37:12
     */
    public static void setFatAPSSID(String devMac, String ssid){
        getDeviceBusService().setFatAPSSID(devMac,ssid);
    }

    /**
     * 设备放通
     * @param data 请求参数
     * @author 亢燕翔  
     * @date 2017年2月15日 下午6:13:59
     */
    public static void setFatAPEscape(String data){
        getDeviceBusService().setFatAPEscape(data);
    }

    /**
     * Chinanet开关接口
     * @param data 请求参数
     * @author 亢燕翔  
     * @date 2017年2月16日 上午9:04:21
     */
    public static void setFatAPSSIDSwitch(String data){
        getDeviceBusService().setFatAPSSIDSwitch(data);
    }

    /**
     * aWiFi开关接口
     * @param data 请求参数
     * @author 亢燕翔  
     * @date 2017年2月16日 上午9:13:01
     */
    public static void setFatAPAWiFiSwitch(String data){
        getDeviceBusService().setFatAPAWiFiSwitch(data);
    }

    /**
     * LAN口认证开关接口
     * @param data 请求参数
     * @author 亢燕翔  
     * @date 2017年2月16日 上午9:20:07
     */
    public static void setFatAPLANSwitch(String data){
        getDeviceBusService().setFatAPLANSwitch(data);
    }

    /**
     * 闲时下线接口
     * @param data 请求参数
     * @author 亢燕翔  
     * @date 2017年2月16日 上午9:28:40
     */
    public static void setFatAPClientTimeout(String data){
        getDeviceBusService().setFatAPClientTimeout(data);
    }
    
    /**
     * 获取deviceBusService实例
     * @return deviceBusService
     * @author 亢燕翔  
     * @date 2017年2月6日 下午5:13:36
     */
    private static DeviceBusService getDeviceBusService(){
        if(deviceBusService == null ){
            deviceBusService = (DeviceBusService) BeanUtil.getBean("deviceBusService");
        }
        return deviceBusService;
    }
    /**
     * @param deviceOwnersList 设备绑定列表
     * @author 王冬冬  
     * @date 2017年5月17日 下午1:55:00
     */
    public static void setFatAPSSID(List<DeviceOwner> deviceOwnersList){
        getDeviceBusService().setFatAPSSID(deviceOwnersList);
    }

    /**
     * @param devMac 设备监控实体
     * @return map
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年6月6日 上午9:28:36
     */
    public static Map getDeviceData(String devMac) throws Exception {
        return getDeviceBusService().getDeviceData(devMac);
    }

    /**
     * @param devMac 设备mac
     * @return map
     * @throws UnsupportedEncodingException 异常
     * @author 王冬冬  
     * @date 2017年6月6日 上午9:28:39
     */
    public static Map getMerchantDeviceData(String devMac) throws UnsupportedEncodingException {
        return getDeviceBusService().getMerchantDeviceData(devMac);
    }

    /**
     * 设备白名单
     * @param devMac 设备mac
     * @param macList 白名单mac列表
     * @return map
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年8月2日 下午2:32:12
     */
    public static String setWhiteUser(String devMac, List<String> macList) throws Exception{
        return getDeviceBusService().setWhiteUser(devMac,macList);
    }
}

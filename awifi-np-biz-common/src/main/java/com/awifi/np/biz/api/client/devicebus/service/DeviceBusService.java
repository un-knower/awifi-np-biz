package com.awifi.np.biz.api.client.devicebus.service;

import java.util.List;
import java.util.Map;

import com.awifi.np.biz.api.client.dbcenter.device.device.model.DeviceOwner;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月6日 下午5:12:06
 * 创建作者：亢燕翔
 * 文件名称：DeviceBusService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface DeviceBusService {

    /**
     * 设备激活
     * @param data 请求参数
     * @author 亢燕翔  
     * @date 2017年2月6日 下午5:14:44
     */
    void setFatAPRegister(String data);

    /**
     * 设置ssid
     * @param devMac 设备mac
     * @param ssid 热点
     * @author 亢燕翔  
     * @date 2017年2月8日 下午2:37:43
     */
    void setFatAPSSID(String devMac, String ssid);

    /**
     * 设备放通
     * @param data 请求参数
     * @author 亢燕翔  
     * @date 2017年2月15日 下午6:14:49
     */
    void setFatAPEscape(String data);

    /**
     * Chinanet开关接口
     * @param data 请求参数
     * @author 亢燕翔  
     * @date 2017年2月16日 上午9:04:50
     */
    void setFatAPSSIDSwitch(String data);

    /**
     * aWiFi开关接口
     * @param data 请求参数
     * @author 亢燕翔  
     * @date 2017年2月16日 上午9:14:10
     */
    void setFatAPAWiFiSwitch(String data);

    /**
     * LAN口认证开关接口
     * @param data 请求参数
     * @author 亢燕翔  
     * @date 2017年2月16日 上午9:20:37
     */
    void setFatAPLANSwitch(String data);
    
    /**
     * 闲时下线接口
     * @param data 请求参数
     * @author 亢燕翔  
     * @date 2017年2月16日 上午9:29:07
     */
    void setFatAPClientTimeout(String data);

    /**
     * @param deviceOwnersList 设备绑定列表
     * @author 王冬冬  
     * @throws Exception 异常
     * @date 2017年5月17日 下午1:51:10
     */
    void setFatAPSSID(List<DeviceOwner> deviceOwnersList);

    /**
     * @param devMac 设备mac
     * @return 设备数据
     * @author 王冬冬  
     * @date 2017年8月2日 下午2:25:23
     */
    Map getDeviceData(String devMac);

    /**
     * @param devMac 设备mac
     * @return 设备商户信息
     * @author 王冬冬  
     * @date 2017年8月2日 下午2:25:26
     */
    Map getMerchantDeviceData(String devMac);
    /**
     * @param devMac 设备mac
     * @param macList mac列表
     * @return 调用结果
     * @author 王冬冬  
     * @date 2017年8月2日 下午2:25:26
     */
    String setWhiteUser(String devMac, List<String> macList);
}

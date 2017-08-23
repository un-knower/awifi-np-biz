package com.awifi.np.biz.devsrv.device.service;

import java.util.List;
import java.util.Map;

import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月3日 下午2:27:51
 * 创建作者：亢燕翔
 * 文件名称：DeviceService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface DeviceService {

    /**
     * 批量修改ssid
     * @param ssid 热点
     * @param bodyParam 请求体参数
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月8日 上午11:19:08
     */
    void batchUpdateSSID(String ssid, List<Map<String, Object>> bodyParam) throws Exception;
    
    /**
     * 设备详情
     * @param deviceid 设备id
     * @return device
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月10日 下午3:53:41
     */
    Device getByDevId(String deviceid) throws Exception;

    /**
     * 设备放通
     * @param bodyParam 请求体参数
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月15日 下午5:05:41
     */
    void batchEscape(List<Map<String, Object>> bodyParam) throws Exception;

    /**
     * Chinanet开关接口
     * @param bodyParam 请求体参数
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月15日 下午6:17:45
     */
    void batchChinanetSsidSwitch(List<Map<String, Object>> bodyParam) throws Exception;

    /**
     * aWiFi开关接口
     * @param bodyParam 请求体参数
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月16日 上午9:07:46
     */
    void batchAwifiSsidSwitch(List<Map<String, Object>> bodyParam) throws Exception;

    /**
     * LAN口认证开关接口
     * @param bodyParam 请求体参数
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月16日 上午9:17:12
     */
    void batchLanSwitch(List<Map<String, Object>> bodyParam) throws Exception;

    /**
     * 闲时下线接口
     * @param bodyParam 请求体参数
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月16日 上午9:23:08
     */
    void batchClientTimeout(List<Map<String, Object>> bodyParam) throws Exception;

    /**
     * 设备解绑
     * @param bodyParam 请求体参数 
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月21日 下午7:34:19
     */
    void unbind(Map<String, Object> bodyParam) throws Exception;

    /**批量excel导入更新ssid
     * @param successList 参数map
     * @author 王冬冬  
     * @throws Exception 异常
     * @date 2017年5月2日 下午2:42:19
     */
    void batchUpdateSSID(List<Map<String, Object>> successList) throws Exception;

}

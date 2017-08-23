package com.awifi.np.biz.devsrv.device.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;
import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.api.client.dbcenter.device.util.DevUtil;
import com.awifi.np.biz.api.client.devicebus.util.DeviceBusClient;
import com.awifi.np.biz.common.base.constants.Constants;
import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.ms.util.StringUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.RegexUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.devsrv.device.service.DeviceService;
import com.awifi.np.biz.toe.admin.device.service.SfTerminalConfigService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月3日 下午2:28:22
 * 创建作者：亢燕翔
 * 文件名称：DeviceServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Service("deviceService")
public class DeviceServiceImpl implements DeviceService{
    
    /**省分平台业务层*/
    @Resource(name = "sfTerminalConfigService")
    private SfTerminalConfigService sfTerminalConfigService;

    /**
     * 批量修改ssid
     * @param ssid 热点
     * @param bodyParam 请求体参数
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年2月8日 上午11:19:35
     */
    public void batchUpdateSSID(String ssid, List<Map<String, Object>> bodyParam) throws Exception {
        /*参数校验*/
        //1.ssid名称参数校验
        if(!RegexUtil.match(ssid, RegexConstants.SSID_NAME_PATTERN)){
            throw new ValidException("E2300001",MessageUtil.getMessage("E2300001"));//设备SSID必须以"aWiFi-"为开头!
        }
        
        //2.数据集参数校验
        String deviceId = null;//设备id
        Integer entityType = null;//设备类型
        String devMac = null;//设备mac
        int maxSize = bodyParam.size();
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        for(int i=0; i<maxSize; i++){
            bodyMap = bodyParam.get(i);
            //设备id
            deviceId = (String) bodyMap.get("deviceId");
            ValidUtil.valid("设备id[deviceId]", deviceId, "required");
            //设备类型
            entityType = CastUtil.toInteger(bodyMap.get("entityType"));
            ValidUtil.valid("设备类型[entityType]", entityType, "{'required':true,'numeric':true}");
            DevUtil.isFatAP(entityType.toString());
            //设备mac
            devMac = (String) bodyMap.get("devMac");
            ValidUtil.valid("设备mac[devMac]", devMac, "required");
        }
        
        /*修改ssid*/
        for(int j=0; j<maxSize; j++){
            bodyMap = bodyParam.get(j);
            DeviceBusClient.setFatAPSSID((String) bodyMap.get("devMac"),ssid);//设备总线设置ssid
//            DeviceClient.updateSSID((String) bodyMap.get("deviceId"),ssid);//数据中心修改ssid
        }
    }

    /**
     * 设备详情
     * @param deviceid 设备id
     * @return device
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年2月10日 下午3:54:24
     */
    public Device getByDevId(String deviceid) throws Exception {
        return DeviceClient.getByDevId(deviceid);
    }

    /**
     * 设备放通
     * @param bodyParam 请求体参数
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年2月15日 下午5:06:09
     */
    public void batchEscape(List<Map<String, Object>> bodyParam) throws Exception {
        for(Map<String, Object> paramMap : bodyParam){
            //接受参数
            String deviceId = (String) paramMap.get("deviceId");//设备id
            Integer entityType = CastUtil.toInteger(paramMap.get("entityType"));//设备类型
            String devMac = (String) paramMap.get("devMac");//设备mac
            String devSwitch = (String) paramMap.get("devSwitch");//设备开关(ON代表开启、OFF代表关闭)
            //参数校验
            ValidUtil.valid("设备id[deviceId]", deviceId, "required");
            ValidUtil.valid("设备类型[entityType]", entityType, "required");
            ValidUtil.valid("设备mac[devMac]", devMac, "{'required':true, 'regex':'"+RegexConstants.MAC_PATTERN+"'}");
            ValidUtil.valid("设备开关[devSwitch]", devSwitch, "required");
            DevUtil.isFatAP(entityType.toString());
            devSwitch = devSwitch.equalsIgnoreCase("ON") ? "OK" : "FAIL";
            
            String data = getBatchEscapeData(deviceId,devMac,devSwitch);//获取设备放通参数
            DeviceBusClient.setFatAPEscape(data);//请求设备总线接口
        }
    }

    /**
     * 获取设备放通参数
     * @param deviceId 设备id
     * @param devMac 设备mac
     * @param devSwitch 设备开关
     * @return data
     * @author 亢燕翔  
     * @date 2017年2月15日 下午5:20:38
     */
    private String getBatchEscapeData(String deviceId, String devMac, String devSwitch) {
        Map<String, Object> busParams = new HashMap<String, Object>();
        busParams.put("platform", Constants.NP);//调用者所在平台
        busParams.put("version", "1.0");//接口版本目前只有1.0版本
        busParams.put("devMac", devMac);//设备mac
        busParams.put("expiredTime", Integer.parseInt(SysConfigUtil.getParamValue("devicebus_expired_time")));//任务过期时间，单位秒
        busParams.put("release", devSwitch);//OK是进入逃生模式，FAIL是退出逃生
        if(devSwitch.equals("OK")){
            busParams.put("time", Integer.parseInt(SysConfigUtil.getParamValue("devicebus_fatapescape_time")));//任务过期时间，单位秒
        }
        return JsonUtil.toJson(busParams);
    }

    /**
     * Chinanet开关接口
     * @param bodyParam 请求体参数
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月16日 上午8:58:40
     */
    public void batchChinanetSsidSwitch(List<Map<String, Object>> bodyParam) throws Exception {
        for(Map<String, Object> paramMap : bodyParam){
            //接受参数
            String deviceId = (String) paramMap.get("deviceId");//设备id
            Integer entityType = CastUtil.toInteger(paramMap.get("entityType"));//设备类型
            String devMac = (String) paramMap.get("devMac");//设备mac
            String devSwitch = (String) paramMap.get("devSwitch");//设备开关(ON代表开启、OFF代表关闭)
            //参数校验
            ValidUtil.valid("设备id[deviceId]", deviceId, "required");
            ValidUtil.valid("设备类型[entityType]", entityType, "required");
            ValidUtil.valid("设备mac[devMac]", devMac, "{'required':true, 'regex':'"+RegexConstants.MAC_PATTERN+"'}");
            ValidUtil.valid("设备开关[devSwitch]", devSwitch, "required");
            DevUtil.isFatAP(entityType.toString());
            String data = getBatchChinanetSsidSwitchData(deviceId,devMac,devSwitch);//获取Chinanet开关接口参数
            DeviceBusClient.setFatAPSSIDSwitch(data);//请求设备总线接口
        }
    }

    /**
     * 获取Chinanet开关接口参数
     * @param deviceId 设备id
     * @param devMac 设备mac
     * @param devSwitch 设备开关
     * @return data
     * @author 亢燕翔  
     * @date 2017年2月16日 上午9:02:44
     */
    private String getBatchChinanetSsidSwitchData(String deviceId, String devMac, String devSwitch) {
        Map<String, Object> busParams = new HashMap<String, Object>();
        busParams.put("platform", Constants.NP);//调用者所在平台
        busParams.put("version", "1.0");//接口版本目前只有1.0版本
        busParams.put("devMac", devMac);//设备mac
        busParams.put("expiredTime", Integer.parseInt(SysConfigUtil.getParamValue("devicebus_expired_time")));//任务过期时间，单位秒
        busParams.put("wifiswitch", devSwitch);//ON开OFF关
        return JsonUtil.toJson(busParams);
    }

    /**
     * aWiFi开关接口
     * @param bodyParam 请求体参数
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月16日 上午9:08:07
     */
    public void batchAwifiSsidSwitch(List<Map<String, Object>> bodyParam) throws Exception {
        for(Map<String, Object> paramMap : bodyParam){
            //接受参数
            String deviceId = (String) paramMap.get("deviceId");//设备id
            Integer entityType = CastUtil.toInteger(paramMap.get("entityType"));//设备类型
            String devMac = (String) paramMap.get("devMac");//设备mac
            String devSwitch = (String) paramMap.get("devSwitch");//设备开关(ON代表开启、OFF代表关闭)
            //参数校验
            ValidUtil.valid("设备id[deviceId]", deviceId, "required");
            ValidUtil.valid("设备类型[entityType]", entityType, "required");
            ValidUtil.valid("设备mac[devMac]", devMac, "{'required':true, 'regex':'"+RegexConstants.MAC_PATTERN+"'}");
            ValidUtil.valid("设备开关[devSwitch]", devSwitch, "required");
            DevUtil.isFatAP(entityType.toString());
            
            String data = getBatchAwifiSsidSwitchData(deviceId,devMac,devSwitch);//获取aWiFi开关接口参数
            DeviceBusClient.setFatAPAWiFiSwitch(data);//请求设备总线接口
        }
    }

    /**
     * 获取aWiFi开关接口参数
     * @param deviceId 设备id
     * @param devMac 设备mac
     * @param devSwitch 设备开关
     * @return data
     * @author 亢燕翔  
     * @date 2017年2月16日 上午9:12:24
     */
    private String getBatchAwifiSsidSwitchData(String deviceId, String devMac, String devSwitch) {
        Map<String, Object> busParams = new HashMap<String, Object>();
        busParams.put("platform", Constants.NP);//调用者所在平台
        busParams.put("version", "1.0");//接口版本目前只有1.0版本
        busParams.put("devMac", devMac);//设备mac
        busParams.put("expiredTime", Integer.parseInt(SysConfigUtil.getParamValue("devicebus_expired_time")));//任务过期时间，单位秒
        busParams.put("awifiswitch", devSwitch);//ON开OFF关
        return JsonUtil.toJson(busParams);
    }

    /**
     * LAN口认证开关接口
     * @param bodyParam 请求体参数
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月16日 上午9:17:35
     */
    public void batchLanSwitch(List<Map<String, Object>> bodyParam) throws Exception {
        for(Map<String, Object> paramMap : bodyParam){
            //接受参数
            String deviceId = (String) paramMap.get("deviceId");//设备id
            Integer entityType = CastUtil.toInteger(paramMap.get("entityType"));//设备类型
            String devMac = (String) paramMap.get("devMac");//设备mac
            String devSwitch = (String) paramMap.get("devSwitch");//设备开关(ON代表开启、OFF代表关闭)
            //参数校验
            ValidUtil.valid("设备id[deviceId]", deviceId, "required");
            ValidUtil.valid("设备类型[entityType]", entityType, "required");
            ValidUtil.valid("设备mac[devMac]", devMac, "{'required':true, 'regex':'"+RegexConstants.MAC_PATTERN+"'}");
            ValidUtil.valid("设备开关[devSwitch]", devSwitch, "required");
            DevUtil.isFatAP(entityType.toString());
            
            String data = getBatchLanSwitchData(deviceId,devMac,devSwitch);//获取LAN口认证开关接口参数
            DeviceBusClient.setFatAPLANSwitch(data);//请求设备总线接口
        }
    }

    /**
     * 获取LAN口认证开关接口参数
     * @param deviceId 设备id
     * @param devMac 设备mac
     * @param devSwitch 设备开关
     * @return data
     * @author 亢燕翔  
     * @date 2017年2月16日 上午9:19:18
     */
    private String getBatchLanSwitchData(String deviceId, String devMac, String devSwitch) {
        Map<String, Object> busParams = new HashMap<String, Object>();
        busParams.put("platform", Constants.NP);//调用者所在平台
        busParams.put("version", "1.0");//接口版本目前只有1.0版本
        busParams.put("devMac", devMac);//设备mac
        busParams.put("expiredTime", Integer.parseInt(SysConfigUtil.getParamValue("devicebus_expired_time")));//任务过期时间，单位秒
        busParams.put("lanswitch", devSwitch);//ON开OFF关
        return JsonUtil.toJson(busParams);
    }

    /**
     * 闲时下线接口
     * @param bodyParam 请求体参数
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月16日 上午9:23:34
     */
    public void batchClientTimeout(List<Map<String, Object>> bodyParam) throws Exception {
        for(Map<String, Object> paramMap : bodyParam){
            //接受参数
            String deviceId = (String) paramMap.get("deviceId");//设备id
            Integer entityType = CastUtil.toInteger(paramMap.get("entityType"));//设备类型
            String devMac = (String) paramMap.get("devMac");//设备mac
            
            Float time = CastUtil.toFloat(paramMap.get("time"));//闲时下线时间
            if(time==null){
                time=CastUtil.toFloat("0.5");
            }
            //参数校验
            ValidUtil.valid("设备id[deviceId]", deviceId, "required");
            ValidUtil.valid("设备类型[entityType]", entityType, "required");
            ValidUtil.valid("设备mac[devMac]", devMac, "{'required':true, 'regex':'"+RegexConstants.MAC_PATTERN+"'}");
//            ValidUtil.valid("闲时下线时间[time]", time, "required");
           
            if(!RegexUtil.match(time.toString(), RegexConstants.HOUR_PATTERN) || time <= 0.0 ||time > 24.0){
                throw new ValidException("E2301309", MessageUtil.getMessage("E2301309"));//{0}不符合正则规范!
            }
            
            Integer secondsTime = timeHourToSeconds(time);//把时间从小时转为秒
            DevUtil.isFatAP(entityType.toString());//判断是否是胖ap
            String data = getBtchClientTimeoutData(deviceId,devMac,secondsTime);//获取闲时下线接口参数
            DeviceBusClient.setFatAPClientTimeout(data);//请求设备总线接口
        }
    }

    /**
     * 把时间从小时转为秒
     * @param time 时间（小时）
     * @return 时间（秒）
     * @author 亢燕翔  
     * @date 2017年2月28日 下午7:10:09
     */
    private Integer timeHourToSeconds(Float time) {
        Float seconds = time * 60 * 60;
        return CastUtil.toInteger(seconds);
    }

    /**
     * 获取闲时下线接口参数
     * @param deviceId 设备id
     * @param devMac 设备mac
     * @param time 闲时下线时间，单位秒
     * @return data
     * @author 亢燕翔  
     * @date 2017年2月16日 上午9:26:31
     */
    private String getBtchClientTimeoutData(String deviceId, String devMac, Integer time) {
        Map<String, Object> busParams = new HashMap<String, Object>();
        busParams.put("platform", SysConfigUtil.getParamValue("appid_pub"));//调用者所在平台
        busParams.put("version", "1.0");//接口版本目前只有1.0版本
        busParams.put("devMac", devMac);//设备mac
        busParams.put("expiredTime", Integer.parseInt(SysConfigUtil.getParamValue("devicebus_expired_time")));//任务过期时间，单位秒
        busParams.put("time", time);//闲时下线时间，单位秒  前端传入单位为小时  请求设备总线为秒
        return JsonUtil.toJson(busParams);
    }

    /**
     * 设备解绑
     * @param bodyParam 请求体参数
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月21日 下午7:34:42
     */
    @SuppressWarnings("unchecked")
    public void unbind(Map<String, Object> bodyParam) throws Exception {
        List<String> deviceList = (List<String>) bodyParam.get("deviceIds");
        ValidUtil.valid("设备Id集合[deviceIds]", deviceList, "arrayNotBlank");//数组内部是否存在null
        for(String deviceId : deviceList){
            DeviceClient.unbind(deviceId);
        }
    }

    
    /**
     * 批量修改ssid(xls导入)
     * @param paramList 参数列表
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年5月3日 上午9:28:03
     */
    public void batchUpdateSSID(List<Map<String, Object>> paramList) throws Exception {
        for(int j=0; j<paramList.size(); j++){
            Map<String,Object> bodyMap = paramList.get(j);
            DeviceBusClient.setFatAPSSID((String) bodyMap.get("devMac"),(String) bodyMap.get("ssid"));//设备总线设置ssid
        }
    }
    
}

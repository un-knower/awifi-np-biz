package com.awifi.np.biz.toe.admin.device.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;
import com.awifi.np.biz.api.client.dbcenter.device.device.model.DeviceOwner;
import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.api.client.devicebus.util.DeviceBusClient;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.DateUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.toe.admin.device.dao.SfTerminalConfigDao;
import com.awifi.np.biz.toe.admin.device.model.SfTerminalConfig;
import com.awifi.np.biz.toe.admin.device.service.SfTerminalConfigService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月6日 下午4:20:20
 * 创建作者：亢燕翔
 * 文件名称：SfTerminalConfigServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Service("sfTerminalConfigService")
public class SfTerminalConfigServiceImpl implements SfTerminalConfigService {

    /**省分持久层*/
    @Resource(name = "sfTerminalConfigDao")
    private SfTerminalConfigDao sfTerminalConfigDao;
    
    /**
     * 省分设备激活
     * @param provinceId 省id
     * @param deviceOwnersList 数据集合
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月6日 下午4:30:59
     */
    public void setFatAPRegister(Long provinceId, List<DeviceOwner> deviceOwnersList) throws Exception {
        
        //1.通过省ID查询省分平台定制终端配置信息
        SfTerminalConfig sfTerminal = sfTerminalConfigDao.getByProvinceId(provinceId);
        if(null == sfTerminal){
            throw new BizException("E2400002", MessageUtil.getMessage("E2400002",provinceId));//通过省id[{0}]未找到对应的省分平台定制终端配置信息!
        }
        
        //2.遍历上传数据，通过设备mac查询设备ID
        String devMac = null;//设备mac
        String deviceId = null;//设备id
        for(DeviceOwner deviceOwners : deviceOwnersList){
            devMac = deviceOwners.getMac();
            deviceId = getDeviceIdByDevMac(devMac);
            if(StringUtils.isBlank(deviceId)){
                throw new BizException("E2400003", MessageUtil.getMessage("E2400003",devMac));//通过设备MAC[{0}]未找到设备信息!
            }
            
        //3.封装数据，调用设备总线接口    
            String data = formtData(devMac,deviceId,sfTerminal);
            DeviceBusClient.setFatAPRegister(data);
        }
    }

    /**
     * 数据统一转为json
     * @param devMac 设备mac
     * @param deviceId 设备id
     * @param sfTerminal 省分实体类
     * @return json
     * @author 亢燕翔  
     * @date 2017年2月6日 下午5:10:20
     */
    private String formtData(String devMac, String deviceId, SfTerminalConfig sfTerminal) {
        //认证
        SfTerminalConfig auths = new SfTerminalConfig();
        List<SfTerminalConfig> aythList = new ArrayList<SfTerminalConfig>();
        auths.setHostname(sfTerminal.getAuthHostName());
        auths.setHttp_port("80");
        auths.setPath("/api10/");
        auths.setSsl_available("no");
        auths.setSsl_port("443");
        aythList.add(auths);
        
        //平台
        SfTerminalConfig platforms = new SfTerminalConfig();
        List<SfTerminalConfig> platformList = new ArrayList<SfTerminalConfig>();
        platforms.setHostname(sfTerminal.getPlatformHostName());
        platforms.setHttp_port("80");
        platforms.setPath("/api10/");
        platforms.setSsl_available("no");
        platforms.setSsl_port("443");
        platformList.add(platforms);
        
        //portal
        SfTerminalConfig portals = new SfTerminalConfig();
        List<SfTerminalConfig> portalsList = new ArrayList<SfTerminalConfig>();
        portals.setHostname(sfTerminal.getPortalHostName());
        portals.setHttp_port("80");
        portals.setPath("/api10/");
        portals.setSsl_available("no");
        portals.setSsl_port("443");
        portalsList.add(portals);
        
        /**
         * 1.servers map
         */
        Map<String, Object> serversMap = new HashMap<String, Object>();
        serversMap.put("auths", aythList);
        serversMap.put("platforms", platformList);//调用者所在平台
        serversMap.put("portals", portalsList);
        
        /**
         * 2.services map
         */
        Map<String, Object> servicesMap = new HashMap<String, Object>();
        servicesMap.put("account", "1");//用户
        servicesMap.put("active_date", DateUtil.getNow());//激活时间
        servicesMap.put("device_id", deviceId);//设备id
        servicesMap.put("servers", serversMap);
        
        /**
         * 3.data map
         */
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("devMac", devMac);//设备mac
        dataMap.put("expiredTime", Integer.parseInt(SysConfigUtil.getParamValue("devicebus_expired_time")));//任务过期时间
        dataMap.put("platform", SysConfigUtil.getParamValue("appid_pub"));//平台
        dataMap.put("version", "1.0");//版本
        dataMap.put("services", servicesMap);
        
        return JsonUtil.toJson(dataMap);
    }

    /**
     * 通过设备mac查找设备id
     * @param devMac 设备mac
     * @return deviceId 设备id
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月6日 下午4:53:17
     */
    private String getDeviceIdByDevMac(String devMac) throws Exception {
        Map<String, Object> dbParams = new HashMap<String, Object>();
        dbParams.put("macAddr", devMac);
        dbParams.put("pageNum", 1);
        dbParams.put("pageSize", 5);//此处因为只获取设备id查询数量控制在5个
        List<Device> deviceList = DeviceClient.getListByParam(JsonUtil.toJson(dbParams));//调用数据中心接口
        if(!deviceList.isEmpty()){//循环获取设备
            for(Device device : deviceList){
                if(StringUtils.isNotBlank(device.getDeviceId())){
                    return device.getDeviceId();
                }
            }
        }
        return null;
    }

}

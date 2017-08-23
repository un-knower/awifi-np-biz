package com.awifi.np.biz.api.client.dbcenter.device.device.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;
import com.awifi.np.biz.api.client.dbcenter.device.device.service.DeviceApiService;
import com.awifi.np.biz.api.client.dbcenter.device.device.service.DeviceManageApiService;
import com.awifi.np.biz.api.client.dbcenter.device.device.service.DeviceStatApiService;
import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubEntity;
import com.awifi.np.biz.common.base.constants.RedisConstants;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.BeanUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月3日 下午4:37:36
 * 创建作者：亢燕翔
 * 文件名称：DeviceClient.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class DeviceClient {

    /**商户设备*/
    private static DeviceApiService deviceApiService;

    /**
     * 设备管理服务
     */
    private static DeviceManageApiService deviceManageApiService;

    /**商户统计*/
    private static DeviceStatApiService deviceStatApiService;

    /**
     * 获取虚拟设备总记录数
     * @param params 参数
     * @return count
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月3日 下午4:58:15
     */
    public static int getCountByParam(String params) throws Exception {
        return getDeviceApiService().getCountByParam(params);
    }

    /**
     * 获取虚拟设备列表
     * @param params 参数
     * @return map
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月3日 下午5:31:05
     */
    public static List<Device> getListByParam(String params) throws Exception {
        return getDeviceApiService().getListByParam(params);
    }

    /**
     * 设备绑定（归属）
     * @param params 参数
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月6日 下午3:10:22
     */
    public static void setOwner(String params) throws Exception {
        getDeviceApiService().setOwner(params);
    }

    /**
     * 设备过户
     * @param deviceId 设备id
     * @param merchantId 商户id
     * @param belongTo 设备归属
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月7日 下午4:49:09
     */
    public static void transfer(String deviceId, Long merchantId, String belongTo) throws Exception {
        getDeviceApiService().transfer(deviceId, merchantId, belongTo);
    }

    /**
     * 修改ssid
     * @param deviceId 设备id
     * @param ssid 热点
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月8日 下午2:58:15
     */
    public static void updateSSID(String deviceId, String ssid) throws Exception {
        getDeviceApiService().updateSSID(deviceId, ssid);
    }

    /**
     * 设备详情
     * @param deviceId 设备id
     * @return device
     * @author 亢燕翔 
     * @throws Exception 异常
     * @date 2017年2月10日 下午3:54:49
     */
    public static Device getByDevId(String deviceId) throws Exception {
        return getDeviceApiService().getByDevId(deviceId);
    }

    /**
     * 设备详情（从缓存中获取）
     * @param deviceId 设备id
     * @return device
     * @throws Exception 异常
     * @author 许小满  
     * @date 2017年5月9日 下午11:55:28
     */
    public static Device getByDevIdCache(String deviceId) throws Exception {
        Device device = getDeviceFromCache(deviceId);//缓存中获取
        if (device != null) {//不为空，直接返回
            return device;
        }
        device = getByDevId(deviceId);//从接口中获取
        putDeviceToCache(device);//缓存
        return device;
    }

    /**
     * 从缓存中获取设备信息
     * @param deviceId 设备id
     * @return 设备实体类
     * @author 许小满  
     * @date 2017年5月10日 下午8:19:58
     */
    private static Device getDeviceFromCache(String deviceId) {
        String key = RedisConstants.DEVICE + deviceId;
        List<String> columnList = RedisUtil.hmget(key, "merchantId", "merchantName", "entityType", "entityName",
                "belongTo", "devMac", "ssid", "cvlan", "pvlan");
        if (columnList == null || columnList.size() <= 0) {
            return null;
        }
        String merchantId = columnList.get(0);//商户id
        if (StringUtils.isBlank(merchantId)) {
            return null;
        }

        String merchantName = columnList.get(1);//商户名称
        String entityType = columnList.get(2);//设备类型
        String entityName = columnList.get(3);//实体设备名称
        String belongTo = columnList.get(4);//设备归属
        String devMac = columnList.get(5);//设备MAC
        String ssid = columnList.get(6);//SSID
        String cvlan = columnList.get(7);//cvlan
        String pvlan = columnList.get(8);//pvlan

        Device device = new Device();
        device.setDeviceId(deviceId);//设备id
        device.setMerchantId(Long.parseLong(merchantId));//商户id
        device.setMerchantName(StringUtils.defaultString(merchantName));//商户名称
        device.setEntityType(StringUtils.isNoneBlank(entityType) ? Integer.parseInt(entityType) : null);//设备类型
        device.setEntityName(StringUtils.defaultString(entityName));//实体设备名称
        device.setBelongTo(StringUtils.defaultString(belongTo));//设备归属
        device.setDevMac(StringUtils.defaultString(devMac));//设备MAC
        device.setSsid(StringUtils.defaultString(ssid));//SSID
        device.setCvlan(StringUtils.defaultString(cvlan));//cvlan
        device.setPvlan(StringUtils.defaultString(pvlan));//pvlan
        return device;
    }

    /**
     * 将设备缓存至redis中
     * @param device 设备实体
     * @author 许小满  
     * @date 2017年5月11日 下午4:49:55
     */
    private static void putDeviceToCache(Device device) {
        if (device == null) {
            return;
        }
        String deviceId = device.getDeviceId();//设备id
        Long merchantId = device.getMerchantId();//商户id
        Integer entityType = device.getEntityType();//设备类型

        Map<String, String> map = new HashMap<String, String>(8);
        map.put("merchantId", merchantId != null ? merchantId.toString() : StringUtils.EMPTY);//设备id
        map.put("merchantName", StringUtils.defaultString(device.getMerchantName()));//商户名称
        map.put("entityType", entityType != null ? entityType.toString() : StringUtils.EMPTY);//设备类型
        map.put("entityName", StringUtils.defaultString(device.getEntityName()));//实体设备名称
        map.put("belongTo", StringUtils.defaultString(device.getBelongTo()));//设备归属
        map.put("devMac", StringUtils.defaultString(device.getDevMac()));//设备MAC
        map.put("ssid", StringUtils.defaultString(device.getSsid()));//SSID
        map.put("cvlan", StringUtils.defaultString(device.getCvlan()));//cvlan
        map.put("pvlan", StringUtils.defaultString(device.getPvlan()));//pvlan

        String key = RedisConstants.DEVICE + deviceId;
        RedisUtil.hmset(key, map, RedisConstants.DEVICE_TIME);
    }

    /**
     * 设备绑定
     * @param paramMap 参数
     * @throws Exception 异常
     * @author 许小满  
     * @date 2017年5月22日 下午7:21:01
     */
    public static void bind(Map<String, Object> paramMap) throws Exception {
        getDeviceApiService().bind(paramMap);
    }

    /**
     * 设备解绑
     * @param deviceId 设备id
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月21日 下午7:42:58
     */
    public static void unbind(String deviceId) throws Exception {
        getDeviceApiService().unbind(deviceId);
    }

    /**
     * 获取deviceApiService
     * @return deviceApiService
     * @author 亢燕翔  
     * @date 2017年2月3日 下午4:57:13
     */
    private static DeviceApiService getDeviceApiService() {
        if (deviceApiService == null) {
            deviceApiService = (DeviceApiService) BeanUtil.getBean("deviceApiService");
        }
        return deviceApiService;
    }

    /**
     * 获取deviceManageApiService
     * @return DeviceManageApiService
     */
    private static DeviceManageApiService getDeviceManageApiService() {
        if (deviceManageApiService == null) {
            deviceManageApiService = (DeviceManageApiService) BeanUtil.getBean("deviceManageApiService");
        }
        return deviceManageApiService;
    }

    /**
     * 获取deviceStatApiService
     * @return deviceStatApiService
     */
    private static DeviceStatApiService getDeviceStatApiService() {
        if (deviceStatApiService == null) {
            deviceStatApiService = (DeviceStatApiService) BeanUtil.getBean("deviceStatApiService");
        }
        return deviceStatApiService;
    }

    /**
     * 通过ids对定制终端进行批量审核
     * @param params params
     * @throws Exception exception
     */
    public static void updateFlowStsByIds(Map<String, Object> params) throws Exception {
        getDeviceManageApiService().updateEntityFlowSts(params);
    }

    /**
     * 通过ids对定制终端进行批量删除操作
     * @param ids ids
     * @throws Exception exception
     */
    public static void deleteEntityByIds(String[] ids) throws Exception {
        getDeviceManageApiService().deleteEntityByIds(ids);
    }

    /**
     * 对excel解析信息进行批量插入操作
     * @param sublist subList
     * @throws Exception exception
     */
    public static void addEntityBatch(List<CenterPubEntity> sublist) throws Exception {
        getDeviceManageApiService().addEntityBatch(sublist);
    }

    /**
     * 对项目型瘦ap的excel文件进行批量操作
     * @param subList subList
     * @throws Exception 异常
     */
    public static void addPFitAPBatch(List<CenterPubEntity> subList) throws Exception {
        getDeviceManageApiService().addPFitAPBatch(subList);
    }

    /**
     * @param param 参数
     * @return boolean
     * @author 王冬冬  
     * @throws Exception 
     * @date 2017年4月11日 下午4:09:13
     */
    public static boolean merchantExist(String param) throws Exception {
        return getDeviceApiService().merchantExist(param);
    }

    /**
     * @param merchant 商户参数
     * @throws Exception 数据中心异常
     * @author 王冬冬  
     * @date 2017年4月11日 下午4:18:38
     */
    public static void createMerchant(String merchant) throws Exception {
        getDeviceApiService().createMerchant(merchant);
    }

    /**
     * 返回一键放通开关信息
     * @param merchantId 商户id
     * @param statType 统计类型
     * @return map
     * @author 王冬冬  
     * @throws Exception 异常
     * @date 2017年5月4日 上午11:12:41
     */
    public static Map<String, Object> statSwitchByParam(Long merchantId, String statType) throws Exception {
        return getDeviceApiService().statSwitchByParam(merchantId, statType);
    }

    /**
     * 更新一条实体设备
     * @throws Exception 异常
     * @param entity 更新参数
     */
    public static void updateEntity(CenterPubEntity entity) throws Exception {
        getDeviceManageApiService().updateEntity(entity);
    }

    /**
     * 更新设备信息
     * @param params params
     * @throws Exception exception
     */
    public static void updateEntity(Map<String, Object> params) throws Exception {
        getDeviceManageApiService().updateEntity(params);
    }

    /**
     * 逻辑删除一台或者多台虚拟设备
     * @param ids id集合
     * @throws Exception 异常
     */
    public static void deleteDeviceByDeviceIds(String[] ids) throws Exception {
        getDeviceManageApiService().deleteDeviceByDeviceIds(ids);
    }

    /**
     * 批量删除awifi热点
     * @param ids
     * @throws Exception
     * @author 范涌涛  
     * @date 2017年5月18日 下午9:41:07
     */
    public static void deleteHotareaByIds(String[] ids) throws Exception {
        getDeviceManageApiService().deleteHotareaByIds(ids);
    }

    /**
     * 
     * @param reqMap
     * @throws Exception
     * @author 范涌涛  
     * @date 2017年5月18日 下午10:38:59
     */
    public static void updateHotarea(Map<String, Object> reqMap) throws Exception {
        getDeviceManageApiService().updateHotarea(reqMap);
    }

    public static void addHotareaBatch(List<CenterPubEntity> paramList) throws Exception {
        getDeviceManageApiService().addHotareaBatch(paramList);
    }

    /**
     * 根据projectids查询设备和商户数
     * @param params 参数
     * @return list
     * @author 王冬冬  
     * @throws Exception 
     * @date 2017年5月5日 下午4:55:20
     */
    public static List<Map<String, Object>> getMerchantCountByProjectIds(String params) throws Exception {
        return getDeviceStatApiService().queryEntityAndMerchantCountByProjectIds(params);
    }

    /**
     * 设备绑定
     * @param params 参数
     * @author 王冬冬  
     * @throws Exception 异常
     * @date 2017年5月26日 下午3:04:28
     */
    public static void bind(String params) throws Exception {
        getDeviceApiService().bind(params);
    }

    /**
     * 查询nasip过滤列表
     * @param paramsMap 请求参数
     * @return nasip过滤列表
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年6月22日 下午2:11:10
     */
    public static List<Map<String, Object>> getNasFilterList(Map<String, Object> paramsMap) throws Exception {
        return getDeviceManageApiService().getNasFilterList(paramsMap);
    }

    /**
     * 查询nasip过滤总数
     * @param paramsMap 请求参数
     * @return nasip过滤总数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年6月22日 下午2:11:10
     */
    public static int countNasFilterByParam(Map<String, Object> paramsMap) throws Exception {
        return getDeviceManageApiService().countNasFilterByParam(paramsMap);
    }

    /**
     * 添加nasip过滤
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年6月22日 下午2:11:10
     */
    public static void addNasFilter(Map<String, Object> paramsMap) throws Exception {
        getDeviceManageApiService().addNasFilter(paramsMap);
    }

    /**
     * 更新nasip过滤
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年6月22日 下午2:11:10
     */
    public static void updateNasFilter(Map<String, Object> paramsMap) throws Exception {
        getDeviceManageApiService().updateNasFilter(paramsMap);
    }

    /**
     * 删除nasip过滤
     * @param nasIpList nasIp列表
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年6月22日 下午2:11:10
     */
    public static void removeNasFilter(List<String> nasIpList) throws Exception {
        getDeviceManageApiService().removeNasFilter(nasIpList);
    }
    
    /**
     * 根据批次号修改实体设备的流程状态
     * @param paramsMap 条件
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年7月19日 下午8:35:19
     */
    public static void updateByBatch(Map<String, Object> paramsMap) throws Exception {
        getDeviceManageApiService().updateByBatch(paramsMap);
    }
    
    /**
     * 根据批次号，逻辑删除一台或多台设备
     * @param paramsMap 条件
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年7月19日 下午8:36:54
     */
    public static void deleteEntityByBatch(Map<String, Object> paramsMap) throws Exception {
        getDeviceManageApiService().deleteEntityByBatch(paramsMap);
    }

    /**
     * 推送经纬度接口
     * @param params 入参
     * @author 周颖  
     * @date 2017年8月15日 下午2:25:45
     */
    public static void pushLatitudeLongitude(String params){
        getDeviceApiService().pushLatitudeLongitude(params);
    }
    
    /**
     * 编辑或者删除设备经纬度
     * @param params 参数
     * @author 周颖  
     * @date 2017年8月16日 上午9:39:41
     */
    public static void editLatitudeLongitude(String params){
        getDeviceApiService().editLatitudeLongitude(params);
    }
}

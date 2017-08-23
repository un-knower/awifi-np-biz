package com.awifi.np.biz.api.client.dbcenter.device.device.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;
import com.awifi.np.biz.api.client.dbcenter.device.device.service.DeviceApiService;
import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.common.base.constants.RedisConstants;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.DateUtil;
import com.awifi.np.biz.common.util.FormatUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.NumberUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月3日 下午4:38:09
 * 创建作者：亢燕翔
 * 文件名称：DeviceApiServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("deviceApiService")
public class DeviceApiServiceImpl implements DeviceApiService {

    /**
     * 获取虚拟设备总记录数
     * @param params 参数
     * @return count
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年2月3日 下午5:23:46
     */
    public int getCountByParam(String params) throws Exception {
        Map<String, String> parameterMap = new HashMap<String, String>();//参数map
        parameterMap.put("params", params);
        String interfaceParam = HttpRequest.getParams(parameterMap);//接口参数
        String url = SysConfigUtil.getParamValue("dbc_getdeviceCount_url");//获取数据中心虚拟设备总记录数接口地址
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, interfaceParam);
        return (int) returnMap.get("rs");//总条数
    }

    /**
     * 获取虚拟设备列表
     * @param params 参数
     * @return list
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年2月3日 下午5:32:01
     */
    public List<Device> getListByParam(String params) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_getdevicelist_url");//获取数据中心虚拟设备列表接口地址
        Map<String, String> parameterMap = new HashMap<String, String>();//参数map
        parameterMap.put("params", params);
        String interfaceParam = HttpRequest.getParams(parameterMap);//接口参数
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, interfaceParam);
        List recordsList = (List) returnMap.get("rs");//获取数据集
        int maxSize = recordsList.size();//计算数据集合最大长度
        Device device = null;
        Map<String, Object> recordsMap = null;
        List<Device> deviceList = new ArrayList<Device>();
        for(int i=0; i<maxSize; i++){
            device = new Device();
            recordsMap = (Map<String, Object>) recordsList.get(i);
            Long merchantId = CastUtil.toLong(recordsMap.get("merchantId"));//商户id
            device.setMerchantId(merchantId);
            device.setMerchantName((String) recordsMap.get("merchantName"));//商户名称
            device.setBroadbandAccount((String) recordsMap.get("broadbandAccount"));//宽带账号
            device.setDeviceId((String) recordsMap.get("deviceId"));//设备id
            device.setEntityName((String)recordsMap.get("entityName"));//设备名称
            device.setDevMac((String)recordsMap.get("macAddr"));//设备mac
            device.setDevIp((String)recordsMap.get("ipAddr"));//设备ip
            device.setSsid((String)recordsMap.get("ssid"));//热点
            String entityType = (String) recordsMap.get("entityType");//设备类型
            device.setEntityType(entityType != null ? Integer.parseInt(entityType) : null);
            Long bindDateLong = CastUtil.toLong(recordsMap.get("bindDate"));//绑定时间
            device.setBindTime(bindDateLong != null ? DateUtil.formatTimestamp(bindDateLong) : StringUtils.EMPTY);
            
            Long provinceId = CastUtil.toLong(recordsMap.get("province"));//省id
            device.setProvinceId(provinceId);//省id
            device.setProvince((String) recordsMap.get("provinceText"));//省
            device.setCityId(CastUtil.toLong(recordsMap.get("city")));//市id
            device.setCity((String) recordsMap.get("cityText"));//市
            device.setAreaId(CastUtil.toLong(recordsMap.get("county")));//区县id
            device.setArea((String) recordsMap.get("countyText"));//区县
            
            device.setChinaNetSwitch(FormatUtil.formatDevSwitch(CastUtil.toInteger(recordsMap.get("chinaNetSwitch"))));//chinaNet开关（0，1）
            device.setOnekeySwitch(FormatUtil.formatDevSwitch(CastUtil.toInteger(recordsMap.get("onekeySwitch"))));//一键放通开关（0，1）
            device.setAwifiSwitch(FormatUtil.formatDevSwitch(CastUtil.toInteger(recordsMap.get("awifiSwitch"))));//awifi开关（0，1）
            device.setLanSwitch(FormatUtil.formatDevSwitch(CastUtil.toInteger(recordsMap.get("lanSwitch"))));//lan口开关
            Integer offlineTime=CastUtil.toInteger(recordsMap.get("offlineTime"));
            device.setOfflineTime(NumberUtil.decimalFormat(offlineTime==null?null:offlineTime/60.0f));//闲时下线,数据库单位为分，换成小时
            deviceList.add(device);
        }
        return deviceList;
    }

    /**
     * 设备绑定（归属）
     * @param params 参数
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年2月6日 下午3:11:38
     */
    public void setOwner(String params) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_getdeviceowner_url");//获取数据中心设备绑定接口
        CenterHttpRequest.sendPutRequest(url, params);
    }

    /**
     * 设备过户
     * @param deviceId  设备id
     * @param merchantId 商户id
     * @param belongTo 设备归属
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年2月17日 下午4:58:57
     */
    public void transfer(String deviceId, Long merchantId, String belongTo) throws Exception {
        //1.设备解绑
        unbind(deviceId);
        //2.设备绑定
        bind(deviceId, merchantId, belongTo);
    }

    /**
     * 设备解绑
     * @param deviceId 设备id
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年2月7日 下午4:52:14
     */
    public void unbind(String deviceId) throws Exception {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("deviceId", deviceId);
        String url = SysConfigUtil.getParamValue("dbc_unbind_url");//获取数据中心设备解绑接口地址
        CenterHttpRequest.sendPutRequest(url, JsonUtil.toJson(paramMap));
        RedisUtil.del(RedisConstants.DEVICE + deviceId);//删除设备redis缓存
    }

    /**
     * 修改ssid
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年2月8日 下午2:59:05
     */
    @Override
    public void updateSSID(String deviceId, String ssid) throws Exception {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("deviceId", deviceId);
        paramMap.put("ssid", ssid);
        String url = SysConfigUtil.getParamValue("dbc_updatessid_url");//获取数据中心修改ssid接口地址
        CenterHttpRequest.sendPutRequest(url, JsonUtil.toJson(paramMap));
    }

    /**
     * 设备详情
     * @param deviceid 设备id
     * @return device
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年2月10日 下午3:55:44
     */
    public Device getByDevId(String deviceid) throws Exception {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("deviceId", deviceid);
        Map<String, String> parameterMap = new HashMap<String, String>();//参数map
        parameterMap.put("params", JsonUtil.toJson(paramMap));
        String interfaceParam = HttpRequest.getParams(parameterMap);//接口参数
        String url = SysConfigUtil.getParamValue("dbc_getbydevId_url");//获取数据中心设备详情接口地址
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, interfaceParam);
        List recordsList = (List) returnMap.get("rs");//获取数据集
        Device device = new Device();
        if(recordsList.isEmpty()){
            return device;
        }
        Map<String, Object> recordsMap = (Map<String, Object>) recordsList.get(0);
        device.setMerchantId(CastUtil.toLong(recordsMap.get("merchantId")));//商户id
        device.setMerchantName((String) recordsMap.get("merchantName"));//商户名称
        device.setDeviceId((String) recordsMap.get("deviceId"));//设备id
        device.setEntityType(CastUtil.toInteger(recordsMap.get("entityType")));//设备类型
        device.setEntityName((String) recordsMap.get("entityName"));//实体设备名称
        device.setBelongTo((String) recordsMap.get("belongTo"));//设备归属
        device.setCorporation((String) recordsMap.get("corporation"));//厂家id
        device.setCorporationDsp((String) recordsMap.get("corporationText"));//厂家中文名称
        device.setModel((String) recordsMap.get("model"));//型号id
        device.setModelDsp((String) recordsMap.get("modelText"));//型号中文名称
        device.setDevMac((String) recordsMap.get("macAddr"));//设备MAC
        device.setSsid((String) recordsMap.get("ssid"));//SSID
        device.setOnlineNum(CastUtil.toInteger(recordsMap.get("onlineNum")));//在线用户数
        device.setFwVersion((String) recordsMap.get("fwVersion"));//固件版本号
        device.setPinCode((String) recordsMap.get("pinCode"));//PING码
        device.setCvlan((String) recordsMap.get("cvlan"));//cvlan
        device.setPvlan((String) recordsMap.get("pvlan"));//pvlan
        
        device.setProvinceId(CastUtil.toLong(recordsMap.get("deviceProvince")));//省id
        device.setProvince((String) recordsMap.get("deviceProvinceText"));//省
        device.setCityId(CastUtil.toLong(recordsMap.get("deviceCity")));//市id
        device.setCity((String) recordsMap.get("deviceCityText"));//市
        device.setAreaId(CastUtil.toLong(recordsMap.get("deviceCounty")));//区县id
        device.setArea((String) recordsMap.get("deviceCountyText"));//区县
        device.setAddress((String) recordsMap.get("address"));//详细地址
        
        device.setStatus(CastUtil.toInteger(recordsMap.get("status")));//设备状态
        Long bindDateLong = CastUtil.toLong(recordsMap.get("bindDate"));//绑定时间
        device.setBindTime(bindDateLong!=null ? DateUtil.formatTimestamp(bindDateLong) : null);//绑定时间
        device.setRemark((String) recordsMap.get("remarks"));//备注
        
        
        device.setChinaNetSwitch(FormatUtil.formatDevSwitch(CastUtil.toInteger(recordsMap.get("chinaNetSwitch"))));//chinaNet开关（0，1）
        device.setOnekeySwitch(FormatUtil.formatDevSwitch(CastUtil.toInteger(recordsMap.get("onekeySwitch"))));//一键放通开关（0，1）
        device.setAwifiSwitch(FormatUtil.formatDevSwitch(CastUtil.toInteger(recordsMap.get("awifiSwitch"))));//awifi开关（0，1）
        device.setLanSwitch(FormatUtil.formatDevSwitch(CastUtil.toInteger(recordsMap.get("lanSwitch"))));//lan口开关
        Integer offlineTime=CastUtil.toInteger(recordsMap.get("offlineTime"));

        device.setLongitude((BigDecimal)recordsMap.get("longitude"));//经度
        device.setLatitude((BigDecimal)recordsMap.get("latiude"));//纬度

        device.setOfflineTime(NumberUtil.decimalFormat(offlineTime==null?null:offlineTime/60.0f));//闲时下线,数据库单位为分，换成小时
        return device;
    }
    
    /**
     * 设备绑定
     * @param deviceId 设备id
     * @param merchantId 商户id
     * @param belongTo 设备归属
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年2月7日 下午4:53:04
     */
    private void bind(String deviceId, Long merchantId, String belongTo) throws Exception {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("deviceId", deviceId);
        paramMap.put("merchantId", merchantId);
        paramMap.put("Belongto", belongTo);
        String url = SysConfigUtil.getParamValue("dbc_bind_url");//获取数据中心设备绑定接口地址
        CenterHttpRequest.sendPutRequest(url, JsonUtil.toJson(paramMap));
    }
    
    /**（胖ap）设备绑定
     * @param params 参数
     * @author 王冬冬  
     * @throws Exception 异常
     * @date 2017年5月17日 上午8:51:38
     */
    public void bind(String params) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_bind_url");//获取数据中心设备绑定接口地址
        Map<String,Object> map=JsonUtil.fromJson(params, Map.class);
        String belongTo=(String) map.get("belongTo");
//        List<DeviceOwner> deviceOwnersList=JSONObject.parseArray((String)map.get("deviceList"), DeviceOwner.class);
        List<Map> deviceOwnersList=JsonUtil.fromJson((String)map.get("deviceList"), List.class);

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("belongTo", belongTo);
        
        for(Map owner:deviceOwnersList){
            paramMap.put("deviceId",owner.get("deviceId"));
            paramMap.put("merchantId",owner.get("merchantId"));
            paramMap.put("broadbandAccount",owner.get("broadAccount"));
            paramMap.put("ssid",owner.get("ssid"));
            paramMap.put("province",owner.get("provinceId"));
            paramMap.put("city",owner.get("cityId"));
            paramMap.put("country",owner.get("countryId"));
//            paramMap.put("city",owner.get(""));
            paramMap.put("address",owner.get("merchantAddress"));
//            paramMap.put("attrInfo1",owner.getMerchantName());
            CenterHttpRequest.sendPutRequest(url, JsonUtil.toJson(paramMap));
        }
    }
    
    /**
     * 设备绑定
     * @param paramMap 参数
     * @throws Exception 异常
     * @author 许小满  
     * @date 2017年5月22日 下午7:21:01
     */
    public void bind(Map<String,Object> paramMap) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_bind_url");//获取数据中心设备绑定接口地址
        CenterHttpRequest.sendPutRequest(url, JsonUtil.toJson(paramMap));
    }

	/**
	 * 判断商户是否已存在
	 * @author 王冬冬  
	 * @date 2017年4月11日 下午4:14:19
	 */
    @Override
	public boolean merchantExist(String json) throws Exception {
    	String url = SysConfigUtil.getParamValue("dbc_bind_url");//获取数据中心设备绑定接口地址
    	CenterHttpRequest.sendGetRequest(url,json);
    	return true;
    }

	/**
	 * 创建商户
	 * @author 王冬冬  
	 * @throws Exception 
	 * @date 2017年4月11日 下午4:19:42
	 */
    @Override
	public void createMerchant(String merchant) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_bind_url");//获取数据中心设备绑定接口地址
        CenterHttpRequest.sendPostRequest(url, merchant);
		
    }

    
    /**
     * 查询一键放通开关数
     * @param merchantId 商户id
     * @param statType 统计类型
     * @return map
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年5月4日 下午4:11:16
     */
    public Map<String, Object> statSwitchByParam(Long merchantId,String statType) throws Exception {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("merchantId", merchantId);
        paramMap.put("statType",statType);
        
        Map<String, String> parameterMap = new HashMap<String, String>();//参数map
        parameterMap.put("params", JsonUtil.toJson(paramMap));
        String interfaceParam = HttpRequest.getParams(parameterMap);//接口参数
        String url = SysConfigUtil.getParamValue("dbc_deviceswitchcount_url");//获取数据中心设备绑定接口地址
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, interfaceParam);
        Map records = (Map) returnMap.get("rs");//获取数据集
        return records;
    }
    
    /**
     * 推送经纬度接口
     * {
        "deviceId":"sss",  --虚拟设备编号,必填
        "longitude":12.34  --经度,必填
        "latiude":11.22    --维度,必填
        }
     * @param params 入参
     * @author 周颖  
     * @date 2017年8月15日 下午2:25:45
     */
    public void pushLatitudeLongitude(String params){
        String url = SysConfigUtil.getParamValue("dbc_pushlatitudeandlongitude_url");//获取请求地址
        CenterHttpRequest.sendPutRequest(url, params);//返回成功的数据
    }
    
    /**
     * 编辑或者删除经纬度
     * {
        "deviceId":"sss",  --虚拟设备编号,必填
        "longitude":12.34,  --经度,不传会将经度置空值，即删除经度
        "latiude":11.22    --维度，不传会将纬度置空值，即删除纬度
        }
     * @param params 入参
     * @author 周颖  
     * @date 2017年8月16日 上午9:40:58
     */
    public void editLatitudeLongitude(String params){
        String url = SysConfigUtil.getParamValue("dbc_editlatitudeandlongitude_url");//获取请求地址
        CenterHttpRequest.sendPutRequest(url, params);//返回成功的数据
    }
}
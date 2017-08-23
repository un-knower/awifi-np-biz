package com.awifi.np.biz.api.client.dbcenter.device.device.service;

import java.util.List;
import java.util.Map;

import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月3日 下午4:37:53
 * 创建作者：亢燕翔
 * 文件名称：DeviceApiService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface DeviceApiService {
    
    /**
     * 获取虚拟设备总记录数
     * @param params 参数
     * @return count
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月3日 下午5:23:15
     */
    int getCountByParam(String params) throws Exception;

    /**
     * 获取虚拟设备列表
     * @param params 参数
     * @return map
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月3日 下午5:31:43
     */
    List<Device> getListByParam(String params) throws Exception;

    /**
     * 设备绑定（归属）
     * @param params 参数
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月6日 下午3:11:15
     */
    void setOwner(String params) throws Exception;
    
    /**
     * 设备过户
     * @param deviceId 设备id
     * @param merchantId 商户id
     * @param belongTo 设备归属
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月7日 下午4:50:08
     */
    void transfer(String deviceId, Long merchantId, String belongTo) throws Exception;

    /**
     * 修改ssid
     * @param deviceId 设备id
     * @param ssid 热点
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月8日 下午2:58:45
     */
    void updateSSID(String deviceId, String ssid) throws Exception;

    /**
     * 设备详情
     * @param deviceid 设备id
     * @return device
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月10日 下午3:55:16
     */
    Device getByDevId(String deviceid) throws Exception;

    /**
     * 设备id
     * @param deviceId 设备id
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月21日 下午7:43:37
     */
    void unbind(String deviceId) throws Exception;
    
    /**（胖ap）设备绑定
     * @param params 参数
     * @author 王冬冬  
     * @throws Exception 异常
     * @date 2017年5月17日 上午8:51:38
     */
    void bind(String params) throws Exception;
    
    /**
     * 设备绑定
     * @param paramMap 参数
     * @throws Exception 异常
     * @author 许小满  
     * @date 2017年5月22日 下午7:21:01
     */
    void bind(Map<String,Object> paramMap) throws Exception;

	/**
	 * 商户是否存在
	 * @param param 参数
	 * @return boolean
	 * @author 王冬冬  
	 * @throws Exception 数据中心异常
	 * @date 2017年4月11日 下午4:08:47
	 */
    boolean merchantExist(String param) throws Exception;

	/**
	 * @param merchant 商户
	 * @throws Exception 异常
	 * @author 王冬冬  
	 * @date 2017年4月11日 下午4:21:38
	 */
    void createMerchant(String merchant) throws Exception;

    /**
     * @param merchantId 商户id
     * @return map 
     * @throws Exception 异常
     * @author 王冬冬  
     * @param statType 
     * @date 2017年5月4日 下午1:49:34
     */
    Map<String, Object> statSwitchByParam(Long merchantId, String statType) throws Exception;

    /**
     * 推送经纬度接口
     * @param params 入参
     * @author 周颖  
     * @date 2017年8月15日 下午2:25:45
     */
    void pushLatitudeLongitude(String params);

    /**
     * 编辑或者删除经纬度
     * @param params 入参
     * @author 周颖  
     * @date 2017年8月16日 上午9:40:58
     */
    void editLatitudeLongitude(String params);
}

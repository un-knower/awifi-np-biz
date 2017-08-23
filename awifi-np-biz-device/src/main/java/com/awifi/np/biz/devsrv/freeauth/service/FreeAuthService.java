/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月20日 上午9:15:32
* 创建作者：范立松
* 文件名称：FreeAuthService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.freeauth.service;

import java.util.List;
import java.util.Map;

import com.awifi.np.biz.common.base.model.Page;

public interface FreeAuthService {

    /**
     * 添加设备区域信息
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月21日 下午2:25:22
     */
    void addDeviceArea(Map<String, Object> paramsMap) throws Exception;

    /**
     * 修改设备区域信息
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月21日 下午2:25:22
     */
    void updateDeviceArea(Map<String, Object> paramsMap) throws Exception;

    /**
     * 分页查询设备区域列表
     * @param page 区域列表
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午5:17:38
     */
    void getDeviceAreaList(Page<Map<String, Object>> page, Map<String, Object> paramsMap) throws Exception;

    /**
     * 根据区域id删除区域和区域设备关系
     * @param ids 区域id列表
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月21日 下午2:25:22
     */
    void removeDeviceAreaById(String[] ids) throws Exception;

    /**
     * 批量添加设备与区域关联信息
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月21日 下午2:25:22
     */
    void addDeviceAreaRel(List<Map<String, Object>> paramsMap) throws Exception;

    /**
     * 根据设备id删除区域和设备关系
     * @param ids 设备id列表
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月21日 下午2:25:22
     */
    void removeRelByDevId(String[] ids) throws Exception;

    /**
     * 根据区域id查询设备与区域关系
     * @param page 区域信息
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午5:17:38
     */
    void getRelListByAreaId(Page<Map<String, Object>> page, Map<String, Object> paramsMap) throws Exception;
    
    /**
     * 分页查询设备与区域关联时可选择的设备列表
     * @param page 区域信息
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午5:17:38
     */
    void getChooseableDeviceList(Page<Map<String, Object>> page, Map<String, Object> paramsMap) throws Exception;
    
    /**
     * 根据deviceName获取deviceId
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年6月3日 上午9:51:19
     */
    void getDeviceIdByDeviceName(Map<String, Object> paramsMap) throws Exception;

}

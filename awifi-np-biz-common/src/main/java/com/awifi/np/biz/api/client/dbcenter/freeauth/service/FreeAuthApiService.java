/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月20日 上午9:28:48
* 创建作者：范立松
* 文件名称：FreeAuthApiService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.freeauth.service;

import java.util.List;
import java.util.Map;

public interface FreeAuthApiService {

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
     * @param paramsMap 请求参数
     * @return 区域信息列表
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午5:17:38
     */
    List<Map<String, Object>> getDeviceAreaList(Map<String, Object> paramsMap) throws Exception;

    /**
     * 根据区域id删除区域和区域设备关系
     * @param paramsMap 区域id
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月21日 下午2:25:22
     */
    void removeDeviceAreaById(Map<String, Object> paramsMap) throws Exception;

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
     * @param paramsMap 设备id
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月21日 下午2:25:22
     */
    void removeRelByDevId(List<Map<String, Object>> paramsMap) throws Exception;

    /**
     * 根据区域id查询设备与区域关系
     * @param paramsMap 请求参数
     * @return 设备与区域关系列表
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午5:17:38
     */
    List<Map<String, Object>> getRelListByAreaId(Map<String, Object> paramsMap) throws Exception;

    /**
     * 按条件查询设备区域记录数
     * @param paramsMap 请求参数
     * @return 记录数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月25日 下午4:21:33
     */
    int queryAreaCountByParam(Map<String, Object> paramsMap) throws Exception;

    /**
     * 按条件查询设备与区域关联记录数
     * @param paramsMap 请求参数
     * @return 记录数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月25日 下午4:21:33
     */
    int queryRelCountByParam(Map<String, Object> paramsMap) throws Exception;

    /**
     * 分页查询设备与区域关联时可选择的设备列表
     * @param paramsMap 请求参数
     * @return 设备列表
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午5:17:38
     */
    List<Map<String, Object>> getChooseableDeviceList(Map<String, Object> paramsMap) throws Exception;

    /**
     * 按条件查询设备与区域关联时可选择的设备数量
     * @param paramsMap 请求参数
     * @return 记录数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月25日 下午4:21:33
     */
    int queryChooseableDeviceCount(Map<String, Object> paramsMap) throws Exception;

}

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月20日 上午9:16:12
* 创建作者：范立松
* 文件名称：FreeAuthServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.freeauth.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;
import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.api.client.dbcenter.freeauth.util.FreeAuthClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.enums.Status;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.devsrv.freeauth.service.FreeAuthService;

@Service("freeAuthService")
public class FreeAuthServiceImpl implements FreeAuthService {

    /**
     * 添加设备区域信息
     * @author 范立松  
     * @date 2017年4月25日 下午2:27:43
     */
    @Override
    public void addDeviceArea(Map<String, Object> paramsMap) throws Exception {
        FreeAuthClient.addDeviceArea(paramsMap);
    }

    /**
     * 修改设备区域信息
     * @author 范立松  
     * @date 2017年4月25日 下午2:27:45
     */
    @Override
    public void updateDeviceArea(Map<String, Object> paramsMap) throws Exception {
        FreeAuthClient.updateDeviceArea(paramsMap);
    }

    /**
     * 分页查询设备区域列表
     * @author 范立松  
     * @date 2017年4月25日 下午2:27:48
     */
    @Override
    public void getDeviceAreaList(Page<Map<String, Object>> page, Map<String, Object> paramsMap) throws Exception {
        getDeviceIdByDeviceName(paramsMap);// 根据deviceName获取deviceId
        // 查询总记录条数
        int total = FreeAuthClient.queryAreaCountByParam(paramsMap);
        page.setTotalRecord(total);
        if (total > 0) {
            List<Map<String, Object>> dataList = FreeAuthClient.getDeviceAreaList(paramsMap);
            page.setRecords(dataList);
        }
    }

    /**
     * 根据区域id删除区域和区域设备关系
     * @author 范立松  
     * @date 2017年4月25日 下午2:27:50
     */
    @Override
    public void removeDeviceAreaById(String[] ids) throws Exception {
        for (String deviceAreaId : ids) {
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("id", deviceAreaId);
            FreeAuthClient.removeDeviceAreaById(paramsMap);
        }
    }

    /**
     * 批量添加设备与区域关联信息
     * @author 范立松  
     * @date 2017年4月25日 下午2:27:56
     */
    @Override
    public void addDeviceAreaRel(List<Map<String, Object>> paramsMap) throws Exception {
        FreeAuthClient.addDeviceAreaRel(paramsMap);
    }

    /**
     * 根据设备id删除区域和设备关系
     * @author 范立松  
     * @date 2017年4月25日 下午2:27:59
     */
    @Override
    public void removeRelByDevId(String[] ids) throws Exception {
        List<Map<String, Object>> paramsMap = new ArrayList<>();
        for (String deviceId : ids) {
            Map<String, Object> param = new HashMap<>();
            param.put("deviceId", deviceId);
            paramsMap.add(param);
        }
        FreeAuthClient.removeRelByDevId(paramsMap);
    }

    /**
     * 根据区域id查询设备与区域关系
     * @author 范立松  
     * @date 2017年4月25日 下午2:28:01
     */
    @Override
    public void getRelListByAreaId(Page<Map<String, Object>> page, Map<String, Object> paramsMap) throws Exception {
        // 查询总记录条数
        int total = FreeAuthClient.queryRelCountByParam(paramsMap);
        page.setTotalRecord(total);
        if (total > 0) {
            List<Map<String, Object>> dataList = FreeAuthClient.getRelListByAreaId(paramsMap);
            page.setRecords(dataList);
        }
    }

    /**
     * 分页查询设备与区域关联时可选择的设备列表
     * @author 范立松  
     * @date 2017年5月17日 上午10:14:10
     */
    @Override
    public void getChooseableDeviceList(Page<Map<String, Object>> page, Map<String, Object> paramsMap)
            throws Exception {
        // 查询总记录条数
        int total = FreeAuthClient.queryChooseableDeviceCount(paramsMap);
        page.setTotalRecord(total);
        if (total > 0) {
            List<Map<String, Object>> dataList = FreeAuthClient.getChooseableDeviceList(paramsMap);
            page.setRecords(dataList);
        }
    }

    /**
     * 根据deviceName获取deviceId
     * @author 范立松  
     * @date 2017年6月3日 上午9:52:44
     */
    @Override
    public void getDeviceIdByDeviceName(Map<String, Object> paramsMap) throws Exception {
        String deviceName = CastUtil.toString(paramsMap.get("deviceName"));// 设备名称
        if (StringUtils.isNotBlank(deviceName)) {
            Map<String, Object> deviceParamMap = new HashMap<String, Object>(4);
            deviceParamMap.put("entityName", deviceName);
            deviceParamMap.put("status", Status.normal.getValue());
            int totalRecord = DeviceClient.getCountByParam(JsonUtil.toJson(deviceParamMap));// 获取数据中心该商户设备总记录数
            if (totalRecord != 1) {
                throw new BizException("E2301013", MessageUtil.getMessage("E2301013"));// 未查询到或查询到多条该deviceName对应的设备记录
            }
            deviceParamMap.put("pageNum", 1);
            deviceParamMap.put("pageSize", 1);
            List<Device> devices = DeviceClient.getListByParam(JsonUtil.toJson(deviceParamMap));// 根据参数调数据中心获取设备信息
            if (devices != null && devices.size() > 0) {
                String deviceId = devices.get(0).getDeviceId();// 设备id
                if (StringUtils.isNotBlank(deviceId)) {
                    paramsMap.put("deviceName", deviceId);
                }
            }
        }
    }
}

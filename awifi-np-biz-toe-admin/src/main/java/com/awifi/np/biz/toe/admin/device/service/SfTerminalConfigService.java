package com.awifi.np.biz.toe.admin.device.service;

import java.util.List;

import com.awifi.np.biz.api.client.dbcenter.device.device.model.DeviceOwner;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月6日 下午4:20:02
 * 创建作者：亢燕翔
 * 文件名称：SfTerminalConfigService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface SfTerminalConfigService {

    /**
     * 省分设备激活
     * @param provinceId 省id
     * @param deviceOwnersList 数据集
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月6日 下午4:30:29
     */
    void setFatAPRegister(Long provinceId, List<DeviceOwner> deviceOwnersList) throws Exception;

}

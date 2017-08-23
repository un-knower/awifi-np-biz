package com.awifi.np.biz.api.client.dbcenter.device.entity.service;

import java.util.List;

import com.awifi.np.biz.api.client.dbcenter.device.entity.model.EntityInfo;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月8日 上午9:09:33
 * 创建作者：亢燕翔
 * 文件名称：EntityApiService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface EntityApiService {

    /**
     * 设备监控查询总数
     * @param params 请求参数
     * @return count
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月8日 上午9:54:48
     */
    int getEntityInfoCountByMerId(String params) throws Exception;
    
    /**
     * 设备监控列表
     * @param params 请求参数
     * @return list
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月8日 上午10:02:59
     */
    List<EntityInfo> getEntityInfoListByMerId(String params) throws Exception;
    
    /**
     * 编辑设备
     * @param params 请求参数
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月10日 下午3:39:56
     */
    void update(String params) throws Exception;

}

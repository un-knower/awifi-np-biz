package com.awifi.np.biz.api.client.dbcenter.device.device.service;

import java.util.List;
import java.util.Map;

import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubEntity;

public interface DeviceManageApiService {
    /**
     * 修改实体设备的流程状态
     * @param params 修改参数
     * @throws Exception 异常
     */
    void updateEntityFlowSts(Map<String, Object> params) throws Exception;

    /**
     * 逻辑删除一台或者多台设备
     * @param ids id集合
     * @throws Exception 异常
     */
    void deleteEntityByIds(String[] ids) throws Exception;

    /**
     * 插入一批设备
     * @param sublist 待插入设备集合
     * @throws Exception 异常
     */
    void addEntityBatch(List<CenterPubEntity> sublist) throws Exception;

    /**
     * 批量新增项目型瘦ap
     * @param subList 待插入项目型瘦ap集合
     * @throws Exception 异常
     */
    void addPFitAPBatch(List<CenterPubEntity> subList) throws Exception;

    /**
     * 更新实体设备
     * @param entity 设备参数
     * @throws Exception 异常
     */
    void updateEntity(CenterPubEntity entity) throws Exception;

    /**
     * 更新设备信息
     * @author 范立松  
     * @date 2017年5月9日 下午7:34:56
     */
    void updateEntity(Map<String, Object> params) throws Exception;
    
    /**
     * 根据批次号修改实体设备的流程状态
     * @param params 条件
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年7月19日 下午8:08:00
     */
    void updateByBatch(Map<String, Object> params) throws Exception;
    
    /**
     * 根据批次号，逻辑删除一台或多台设备
     * @param params 参数
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年7月19日 下午8:06:56
     */
    void deleteEntityByBatch(Map<String, Object> params) throws Exception;
    
    /**
     * 逻辑删除一台或者多台虚拟设备
     * @param ids id的集合
     * @throws Exception 异常
     */
    public void deleteDeviceByDeviceIds(String[] ids) throws Exception;

    /**
     * 逻辑删除一台或者多台虚拟设备
     * @param ids id的集合
     * @throws Exception 异常
     */

    void deleteHotareaByIds(String[] ids) throws Exception;

    /**
     * 
     * @param reqMap
     * @throws Exception
     * @author 范涌涛  
     * @date 2017年5月18日 下午10:38:59
     */
    void updateHotarea(Map<String, Object> reqMap) throws Exception;

    /**
     * @param subList
     * @throws Exception
     * @author 范涌涛  
     * @date 2017年5月18日 下午10:39:09
     */
    void addHotareaBatch(List<CenterPubEntity> subList) throws Exception;
    
    /**
     * 查询nasip过滤列表
     * @param paramsMap 请求参数
     * @return nasip过滤列表
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年6月22日 下午2:11:10
     */
    List<Map<String, Object>> getNasFilterList(Map<String, Object> paramsMap) throws Exception;
    
    /**
     * 查询nasip过滤总数
     * @param paramsMap 请求参数
     * @return nasip过滤总数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年6月22日 下午2:11:10
     */
    int countNasFilterByParam(Map<String, Object> paramsMap) throws Exception;

    /**
     * 添加nasip过滤
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年6月22日 下午2:11:10
     */
    void addNasFilter(Map<String, Object> paramsMap) throws Exception;

    /**
     * 更新nasip过滤
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年6月22日 下午2:11:10
     */
    void updateNasFilter(Map<String, Object> paramsMap) throws Exception;

    /**
     * 删除nasip过滤
     * @param nasIpList nasIp列表
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年6月22日 下午2:11:10
     */
    void removeNasFilter(List<String> nasIpList) throws Exception;
    
}

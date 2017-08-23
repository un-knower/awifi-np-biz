package com.awifi.np.biz.api.client.dbcenter.fatap.servcie;

import java.util.List;
import java.util.Map;

import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubEntity;
import com.awifi.np.biz.common.base.model.Page;

@SuppressWarnings({"rawtypes"})
public interface DeviceQueryApiService {
    
    /**
     * 条件查询设备按批次分组 分页总条数
     * @param params 条件
     * @return 总数
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年7月19日 下午7:59:53
     */
    Integer queryEntityCountByParamGroup(Map<String, Object> params)throws Exception;
    
    /**
     * 条件查询设备按批次分组 信息--分页
     * @param params 条件
     * @return 设备信息
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年7月19日 下午7:55:31
     */
    List<CenterPubEntity> queryEntityListByParamGroup(Map<String, Object> params)throws Exception;
    
    /**
     * 通过条件查询设备以及虚拟信息记录数
     * @param params 条件
     * @return int
     * @throws Exception 异常
     */
    Integer queryEntityCountByParam(Map<String, Object> params)throws Exception;
    
    /**
     * 通过条件查询设备和虚拟信息
     * @param params 查询条件
     * @return list
     * @throws Exception 异常
     */
    List<CenterPubEntity> queryEntityInfoListByParam(Map<String,Object> params)throws Exception;
    
    /**
     * 条件查不同型号设备数量
     * @param params 查询条件
     * @return long
     * @throws Exception 异常
     */
    Long queryEntityCountByParamGroupByModel(Map<String,Object> params) throws Exception;
    
    /**
     * 根据主键查询设备和虚拟信息
     * @param id 主键
     * @return entity
     * @throws Exception 异常
     */
    CenterPubEntity queryEntityInfoById(String id) throws Exception;
    
    /**
     * 根据ID查awifi热点信息
     * @param id
     * @return
     * @throws Exception
     * @author 范涌涛  
     * @date 2017年5月18日 下午10:05:44
     */
    Map<String,Object> queryHotareaInfoById(Long id) throws Exception;
    
    Page queryHotareaInfoListByParam(Map<String,Object> reqParam) throws Exception;
    
    Page queryChinaNetHotareaInfoListByParam(Map<String,Object> reqParam) throws Exception;
    
    Map queryChinaHotareaInfoById(Long id) throws Exception;
    
    Page queryHotFitapInfoListByParam(Map<String, Object> paramMap) throws Exception;
}

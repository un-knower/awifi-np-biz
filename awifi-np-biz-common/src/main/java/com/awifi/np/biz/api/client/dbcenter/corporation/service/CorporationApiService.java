/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月12日 下午7:09:07
* 创建作者：范涌涛
* 文件名称：CorporationApiService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.corporation.service;


import java.util.List;
import java.util.Map;
import com.awifi.np.biz.api.client.dbcenter.corporation.model.Corporation;
import com.awifi.np.biz.common.base.model.Page;

@SuppressWarnings({"rawtypes"})
public interface CorporationApiService {
    
    /**
     * 根据条件分页查询厂商
     * @param reqParam 查询条件
     * @return 厂商列表
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月17日 下午8:01:01
     */
    List<Corporation> queryCorpListByParam(Map<String,Object> reqParam) throws Exception;
    
    /**
     * 查询符合条件的厂商总数
     * @param reqParam 查询条件
     * @return 条数
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月17日 下午8:02:04
     */
    int queryCorpCountByParam(Map<String,Object> reqParam) throws Exception;
    
    /**
     * 根据厂商ID查厂商
     * @param corpId 厂商ID
     * @return 厂商信息
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年7月12日 下午3:07:34
     */
    Map queryCorpById(Long corpId) throws Exception;
    /**
     * 添加厂商
     * @param reqParam 厂商信息
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月17日 下午9:22:52
     */
    void addCorperation(Map<String, Object> reqParam) throws Exception;
    
    /**
     * 修改厂商
     * @param reqParam 厂商参数
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月21日 下午5:45:48
     */
    void updateCorporation(Map<String,Object> reqParam) throws Exception;
    
    /**
     * 删除厂商
     * @param reqParam 厂商参数
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月21日 下午5:46:43
     */
    void deleteCorporation(Map<String,Object> reqParam) throws Exception;
    
    /**
     * 查询特定厂商下的型号
     * @param reqParam 查询型号参数
     * @param corpId 厂商ID
     * @return 型号列表
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月21日 下午5:47:21
     */
    Page queryModelList(String reqParam,Long corpId) throws Exception;
    
    /**
     * 根据型号ID查询型号
     * @param modelId 型号ID
     * @return 查询结果
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月21日 下午5:48:22
     */
    Map<String,Object> queryModelById(Long modelId) throws Exception;
    
    /**
     * 添加型号
     * @param reqParam 型号信息
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月21日 下午5:49:14
     */
    void addModel(Map<String,Object> reqParam) throws Exception;
    
    /**
     * 更新型号信息
     * @param reqParam 型号信息
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月21日 下午5:50:24
     */
    void updateModel(Map<String,Object> reqParam) throws Exception;
    
    /**
     * 删除型号
     * @param modelId 型号ID
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月21日 下午5:51:56
     */
    void deleteModel(Long modelId) throws Exception;
    
    /**
     * 根据条件查询厂商信息
     * @param params 参数
     * @return json
     * @throws Exception exception
     */
    public String queryListByParam2(Map<String,Object> params)throws Exception;
    
    /**
     * 根据型号查询类型
     * @param map 参数
     * @param corpText 厂商名称
     * @return 型号
     * @throws Exception 异常
     */
    String getEntityType(Map<String,Object> map,String corpText)throws Exception;

    /**
     * 根据型号ID和厂商ID查设备类型
     * @param modelid 型号ID
     * @param corpId 厂商ID
     * @return 设备类型entityType
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年7月12日 下午3:08:55
     */
    String getModelType(Long modelid,Long corpId)throws Exception;
}

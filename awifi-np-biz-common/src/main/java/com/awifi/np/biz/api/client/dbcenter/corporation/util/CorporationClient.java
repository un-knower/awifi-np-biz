/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月12日 下午8:33:00
* 创建作者：范涌涛
* 文件名称：CorporationClient.java
* 版本：  v1.0
* 功能：厂家型号数据中心接口封装类
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.corporation.util;

import java.util.List;
import java.util.Map;
import com.awifi.np.biz.api.client.dbcenter.corporation.model.Corporation;
import com.awifi.np.biz.api.client.dbcenter.corporation.service.CorporationApiService;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.util.BeanUtil;

@SuppressWarnings({"rawtypes"})
public class CorporationClient {
    
    /**corporationApiService 厂商服务*/
    private static CorporationApiService corporationApiService;
    
    /**
     * 获取厂商bean
     * @return bean
     * @author 范涌涛  
     * @date 2017年4月17日 下午7:55:47
     */
    public static CorporationApiService getCorporationApiService() {
        if (corporationApiService == null) {
            corporationApiService = (CorporationApiService) BeanUtil.getBean("corporationApiService");
        }
        return corporationApiService;
    }
    
    /**
     * 分页查询厂商
     * @param reqParam 查询条件
     * @return 符合条件的厂商信息
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月17日 下午7:54:06
     */
    public static List<Corporation> queryListByParam(Map<String,Object> reqParam) throws Exception {
    	return getCorporationApiService().queryCorpListByParam(reqParam);
    }
    /**
     * 查询符合条件的记录条数
     * @param reqParam 查询条件
     * @return 记录数
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月17日 下午7:42:39
     */
    public static int queryCountByParam(Map<String,Object> reqParam) throws Exception {
    	return getCorporationApiService().queryCorpCountByParam(reqParam);
    }
    
    public static Map queryCorpById(Long id) throws Exception {
        return getCorporationApiService().queryCorpById(id);
    }
    /**
     * 添加厂商
     * @param reqParam 查询参数
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月21日 下午6:03:31
     */
    public static void addCorperation(Map<String, Object> reqParam) throws Exception {
        getCorporationApiService().addCorperation(reqParam);
    }
    
    /**
     * 更新厂商
     * @param reqParam 厂商参数
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月21日 下午6:05:21
     */
    public static void updateCorporation(Map<String,Object> reqParam) throws Exception {
        getCorporationApiService().updateCorporation(reqParam);
    }
    
    /**
     * 删除厂商
     * @param reqParam 厂商参数
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月21日 下午6:05:50
     */
    public static void deleteCorporation(Map<String,Object> reqParam) throws Exception {
        getCorporationApiService().deleteCorporation(reqParam);
    }
    
    /**
     * 查询特定厂商下的型号列表
     * @param reqParam 
     * @param corpId 厂商ID
     * @return 型号列表
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月21日 下午6:06:32
     */
    public static Page queryModelList(String reqParam,Long corpId) throws Exception {
        return getCorporationApiService().queryModelList(reqParam,corpId);
    }
    
    /**
     * 根据型号ID查询型号信息
     * @param modelId 型号ID
     * @return 型号信息
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月21日 下午6:14:26
     */
    public static Map<String,Object> queryModelById(Long modelId) throws Exception {
        return getCorporationApiService().queryModelById(modelId);
    } 
    
    /**
     * 添加型号到特定厂商
     * @param reqParam 型号信息
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月21日 下午6:15:31
     */
    public static void addModel(Map<String,Object> reqParam) throws Exception {
        getCorporationApiService().addModel(reqParam);
    }
    
    /**
     * 更新型号信息
     * @param reqParam 型号信息
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月21日 下午6:16:08
     */
    public static void updateModel(Map<String,Object> reqParam) throws Exception {
        getCorporationApiService().updateModel(reqParam);
    }
    
    /**
     * 删除型号
     * @param modelId 型号ID
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月21日 下午6:16:36
     */
    public static void deleteModel(Long modelId) throws Exception {
        getCorporationApiService().deleteModel(modelId);
    }
    
    /**
     * 
     * 得到json类型的厂家信息
     * @param params 参数
     * @return 全部厂家信息
     * @throws Exception 异常
     */
    public static String queryListByParam2(Map<String,Object> params)throws Exception{
        return getCorporationApiService().queryListByParam2(params);
    }
    
    /**
     * 根据型号查询类型
     * @param map 参数
     * @param corpText 厂商名称
     * @return 型号
     * @throws Exception 异常
     */
    public static String getModelType(Map<String,Object> map,String corpText)throws Exception{
        return getCorporationApiService().getEntityType(map, corpText);
    }
    /**
     * 根据型号id获取类型
     * @param id 主键
     * @return string
     * @throws Exception 异常
     */
    public static String getModelType(Long modelId,Long corpId) throws Exception {
        return getCorporationApiService().getModelType(modelId,corpId);
    }
} 

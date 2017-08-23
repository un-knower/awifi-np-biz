package com.awifi.np.biz.statsrv.stat.service;

import java.util.List;
import java.util.Map;

import com.awifi.np.biz.common.base.model.Page;

public interface PortalPVStatService {
    /**
     * Portal页面-商户维度-统计接口
     * @param page page
     * @param paramsMap 参数
     * @author 许尚敏  
     * @throws Exception 
     * @date 2017年7月27日 上午11:04:25
     */
    @SuppressWarnings("rawtypes")
    void getByMerchant(Page page, Map<String,Object> paramsMap) throws Exception;
    
    /**
     * Portal页面-商户维度-折线趋势图接口
     * @param paramsMap 参数
     * @return 符合条件的记录
     * @author 许尚敏  
     * @throws Exception 
     * @date 2017年7月27日 下午3:07:25
     */
    Map<String, List<String>> getTrendByMerchant(Map<String,Object> paramsMap) throws Exception;
    
    /**
     * 
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @param provinceId 省Id
     * @param cityId 市id
     * @param areaId 区id
     * @param entityType 设备类型
     * @param timeUnit 时间单位
     * @return list
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年7月27日 下午2:30:25
     */
    Map<String, Object> getTrendByArea(String beginDate, String endDate, Integer provinceId, Integer cityId,
            Integer areaId, String entityType, char timeUnit) throws Exception;
    
    /**
     * 获取数据中心接口的入参
     * @param beginDate 开始时间
     * @param endDate 结束时间
     * @param provinceId 省Id
     * @param cityId 市id
     * @param areaId 区id
     * @param entityType 设备类型
     * @param timeUnit 时间单位
     * @return 参数map
     * @author 王冬冬  
     * @date 2017年7月27日 下午3:17:39
     */
    Map<String, Object> getDbParams(String beginDate, String endDate, Integer provinceId, Integer cityId,
            Integer areaId, String entityType, char timeUnit);

    /**
     * 
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @param provinceId 省id
     * @param cityId 市id
     * @param areaId 区id
     * @param bizType 业务类型类型
     * @return list
     * @throws Exception 异常
     * @author 王冬冬  
     * @param hasTotal 是否需要返回总计
     * @date 2017年7月27日 下午2:30:25
     */
    List<Map<String, Object>> getByArea(String beginDate, String endDate, Long provinceId, Long cityId,
            Long areaId, String bizType, Boolean hasTotal) throws Exception;
    /**
     * 
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @param provinceId 省id
     * @param cityId 市id
     * @param areaId 区id
     * @param bizType 业务类型类型
     * @return list
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年7月27日 下午2:30:25
     */
    List<Map<String, Object>> exportByArea(String beginDate, String endDate, Long provinceId, Long cityId,
            Long areaId, String bizType) throws Exception;;

}

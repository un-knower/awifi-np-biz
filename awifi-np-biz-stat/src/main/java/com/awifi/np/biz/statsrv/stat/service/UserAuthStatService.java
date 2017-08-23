/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:Jul 27, 2017 3:51:31 PM
* 创建作者：季振宇
* 文件名称：UserAuthStatService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.statsrv.stat.service;

import java.util.List;
import java.util.Map;

import com.awifi.np.biz.common.base.model.Page;

public interface UserAuthStatService {
    
    /**
     * 用户认证-商户维度-折线趋势图服务
     * @param params 入参
     * @return 结果
     * @throws Exception 异常
     * @author 季振宇  
     * @date Aug 1, 2017 11:26:37 AM
     */
    Map<String, Object> getTrendByMerchant(String params) throws Exception;
    
    /**
     * 用户认证-商户维度-统计接口
     * @param page page
     * @param params 入参
     * @throws Exception 异常
     * @author 季振宇  
     * @date Aug 1, 2017 11:25:20 AM
     */
    void getByMerchant(Page<Map<String,Object>> page,Map<String, Object> params) throws Exception;

    /**
     * 用户认证-地区维度-折线趋势图接口
     * @param dateScope 统计日期范围，不允许为空，day代表按日统计、month代表按月统计
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @param yearMonth yearMonth
     * @param provinceId 省Id
     * @param cityId 市id
     * @param areaId 区id
     * @param bizType 业务类型
     * @return list
     * @throws Exception 异常
     * @author 梁聪
     * @date 2017年7月31日 下午2:30:25
     */
    Map<String,Object> getTrendByArea(String dateScope, String beginDate, String endDate, String yearMonth,
                                      Integer provinceId, Integer cityId, Integer areaId, String bizType) throws Exception;

    /**
     * 用户认证-地区维度-统计接口
     * @param dateScope 统计日期范围，不允许为空，day代表按日统计、month代表按月统计
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @param yearMonth yearMonth
     * @param provinceId 省Id
     * @param cityId 市id
     * @param country 区id
     * @param bizType 业务类型
     * @param hasTotal 是否需要返回总计
     * @param areaId 省市区id
     * @return list
     * @throws Exception 异常
     * @author 梁聪
     * @date 2017年7月31日 下午2:30:25
     */
    List<Map<String,Object>> getByArea(String dateScope, String beginDate, String endDate,String yearMonth, Integer provinceId,Integer cityId,
                                 Integer country, String bizType, boolean hasTotal,String areaId) throws Exception;
}

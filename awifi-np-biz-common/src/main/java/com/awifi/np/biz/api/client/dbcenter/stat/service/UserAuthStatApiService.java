/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年7月31日 上午9:00:50
 * 创建作者：梁聪
 * 文件名称：UserAuthStatApiService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.api.client.dbcenter.stat.service;

import java.util.List;
import java.util.Map;

public interface UserAuthStatApiService {

    /**
     * 用户认证-地区维度-折线趋势图接口
     * @param params 参数
     * @return 结果
     * @author 梁聪
     * @throws Exception 异常
     * @date 2017年7月31日 上午9:20:58
     */
    Map<String,Object> getTrendByArea(String params) throws Exception;

    /**
     * 用户认证-地区维度-统计接口
     * @param params 参数
     * @param hasTotal 是否需要返回总计
     * @param areaId 为省市区的拼接组合
     * @return 结果
     * @author 梁聪
     * @throws Exception 异常
     * @date 2017年7月31日 上午9:20:58
     */
    List<Map<String,Object>> getByArea(String params,boolean hasTotal,String areaId) throws Exception;

    /**
     * 用户认证-商户维度-折线趋势图接口
     * @param params 参数
     * @return 结果
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jul 31, 2017 4:39:14 PM
     */
    Map<String, Object> getTrendByMerchant(String params) throws Exception;
    
    /**
     * 用户认证-商户维度-统计接口总计
     * @param params 参数
     * @return 结果
     * @throws Exception 异常
     * @author 季振宇  
     * @date Aug 1, 2017 9:05:45 AM
     */
    Map<String, Object> getTotalCountByMerchant(String params) throws Exception;
    
    /**
     * 用户认证-商户维度-统计接口分页总数
     * @param params 参数
     * @return 结果
     * @throws Exception 异常
     * @author 季振宇  
     * @date Aug 1, 2017 9:05:50 AM
     */
    Map<String, Object> getCountByMerchant(String params) throws Exception;
    
    /**
     * 用户认证-商户维度-统计接口分页列表
     * @param params 参数
     * @return 结果
     * @throws Exception 异常
     * @author 季振宇  
     * @date Aug 1, 2017 9:05:53 AM
     */
    Map<String, Object> getListByMerchant(String params) throws Exception;

}

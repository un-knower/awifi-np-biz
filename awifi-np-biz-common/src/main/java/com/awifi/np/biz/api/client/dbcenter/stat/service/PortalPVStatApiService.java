/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月28日 上午9:01:10
* 创建作者：周颖
* 文件名称：DeviceTrendStatApiService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.stat.service;

import java.util.List;
import java.util.Map;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017年7月31日 上午10:30:01
 * 创建作者：许尚敏
 * 文件名称：PortalPVStatApiService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface PortalPVStatApiService {

    /**
     * Portal页面-商户维度-折线趋势图接口
     * @param params 参数
     * @return 结果
     * @author 许尚敏 
     * @throws Exception 
     * @date 2017年7月31日 上午10:30:58
     */
    List<Map<String, Object>> getTrendByMerchant(Map<String, Object> params) throws Exception;
    
    /**
     * Portal页面-商户维度-总计
     * @param params 参数
     * @return 结果
     * @author 许尚敏 
     * @throws Exception 
     * @date 2017年7月31日 上午10:30:58
     */
    Map<String, Object> getTotalCountByMerchant(Map<String, Object> params) throws Exception;
    
    /**
     * Portal页面-商户维度-分页数
     * @param params 参数
     * @return 结果
     * @author 许尚敏 
     * @throws Exception 
     * @date 2017年7月31日 上午10:30:58
     */
    int getCountByMerchant(Map<String, Object> params) throws Exception;
    
    /**
     * Portal页面-商户维度-列表
     * @param params 参数
     * @return 结果
     * @author 许尚敏 
     * @throws Exception 
     * @date 2017年7月31日 上午10:30:58
     */
    List<Map<String, Object>> getListByMerchant(Map<String, Object> params) throws Exception;
    
    /**
     * Portal页面-地区维度-折线趋势图接口
     * @param params 参数
     * @return list
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年8月15日 下午4:13:40
     */
    List<Map<String, Object>> getTrendByArea(String params) throws Exception;

    /**
     * Portal页面-地区维度-列表接口
     * @param params 参数
     * @return list
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年8月15日 下午4:13:40
     */
    List<Map<String, Object>> getByArea(String params) throws Exception;
}

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月28日 上午9:00:50
* 创建作者：周颖
* 文件名称：DeviceTrendStatClient.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.stat.util;

import java.util.List;
import java.util.Map;
import com.awifi.np.biz.api.client.dbcenter.stat.service.PortalPVStatApiService;
import com.awifi.np.biz.common.util.BeanUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017年7月31日 上午10:39:08
 * 创建作者：许尚敏
 * 文件名称：PortalPVStatClient.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class PortalPVStatClient {

    /***/
    private static PortalPVStatApiService portalPVStatApiService;
    
    /**
     * 获取portalPVStatApiService实例
     * @return portalPVStatApiService
     * @author 许尚敏  
     * @date 2017年7月31日 上午10:39:08
     */
    private static PortalPVStatApiService getPortalPVStatApiService(){
        if(portalPVStatApiService == null){
            portalPVStatApiService = (PortalPVStatApiService) BeanUtil.getBean("portalPVStatApiService");
        }
        return portalPVStatApiService;
    }
   
    /**
     * Portal页面-商户维度-折线趋势图接口
     * @param params 参数
     * @return 结果
     * @author 许尚敏  
     * @throws Exception 
     * @date 2017年7月31日 上午10:39:08
     */
    public static List<Map<String, Object>> getTrendByMerchant(Map<String,Object> params) throws Exception{
        return getPortalPVStatApiService().getTrendByMerchant(params);
    }
    
    /**
     * Portal页面-商户维度-总计
     * @param params 参数
     * @return 结果
     * @author 许尚敏  
     * @throws Exception 
     * @date 2017年7月31日 上午11:07:08
     */
    public static Map<String, Object> getTotalCountByMerchant(Map<String,Object> params) throws Exception{
        return getPortalPVStatApiService().getTotalCountByMerchant(params);
    }
    
    /**
     * Portal页面-商户维度-分页数
     * @param params 参数
     * @return 结果
     * @author 许尚敏  
     * @throws Exception 
     * @date 2017年7月31日 上午11:07:08
     */
    public static int getCountByMerchant(Map<String,Object> params) throws Exception{
        return getPortalPVStatApiService().getCountByMerchant(params);
    }
    
    /**
     * Portal页面-商户维度-列表
     * @param params 参数
     * @return 结果
     * @author 许尚敏  
     * @throws Exception 
     * @date 2017年7月31日 上午11:07:08
     */
    public static List<Map<String, Object>> getListByMerchant(Map<String,Object> params) throws Exception{
        return getPortalPVStatApiService().getListByMerchant(params);
    }

    /**
     * Portal页面-地区维度-趋势图
     * @param params 参数
     * @return list
     * @author 王冬冬  
     * @throws Exception 异常
     * @date 2017年8月15日 下午4:11:52
     */
    public static List<Map<String, Object>> getTrendByArea(String params) throws Exception {
        return getPortalPVStatApiService().getTrendByArea(params);
    }


    /**
     * Portal页面-地区维度-列表
     * @param params 参数
     * @return list
     * @author 王冬冬  
     * @throws Exception 异常
     * @date 2017年8月15日 下午4:11:52
     */
    public static List<Map<String, Object>> getByArea(String params) throws Exception {
        return getPortalPVStatApiService().getByArea(params);
    }
}

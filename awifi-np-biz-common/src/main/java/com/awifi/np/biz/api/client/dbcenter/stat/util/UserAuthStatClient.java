/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年7月31日 上午9:00:50
 * 创建作者：梁聪
 * 文件名称：UserAuthStatClient.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.api.client.dbcenter.stat.util;

import com.awifi.np.biz.api.client.dbcenter.stat.service.UserAuthStatApiService;
import com.awifi.np.biz.common.util.BeanUtil;

import java.util.List;
import java.util.Map;

public class UserAuthStatClient {

    /**deviceTrendStatApiService */
    private static UserAuthStatApiService userAuthStatApiService;

    /**
     * 获取deviceTrendStatApiService实例
     * @return deviceTrendStatApiService
     * @author 周颖
     * @date 2017年7月28日 上午9:22:03
     */
    private static UserAuthStatApiService getUserAuthStatApiService(){
        if(userAuthStatApiService == null){
            userAuthStatApiService = (UserAuthStatApiService) BeanUtil.getBean("userAuthStatApiService");
        }
        return userAuthStatApiService;
    }

    /**
     * 用户认证-地区维度-折线趋势图接口
     * @param params 参数
     * @return 结果
     * @author 梁聪
     * @throws Exception 异常
     * @date 2017年7月31日 上午9:20:58
     */
    public static Map<String,Object> getTrendByArea(String params) throws Exception{
        return getUserAuthStatApiService().getTrendByArea(params);
    }

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
    public static List<Map<String,Object>> getByArea(String params,boolean hasTotal,String areaId) throws Exception{
        return getUserAuthStatApiService().getByArea(params,hasTotal,areaId);
    }
    
    /**
     * 用户认证-商户维度-折线趋势图接口
     * @param params 参数
     * @return 结果
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jul 31, 2017 4:47:24 PM
     */
    public static Map<String, Object> getTrendByMerchant(String params) throws Exception {
        return getUserAuthStatApiService().getTrendByMerchant(params);
    }
    
    /**
     * 用户认证-商户维度-统计接口总计
     * @param params 参数
     * @return 结果
     * @throws Exception 异常
     * @author 季振宇  
     * @date Aug 1, 2017 9:15:22 AM
     */
    public static Map<String, Object> getTotalCountByMerchant(String params) throws Exception {
        return getUserAuthStatApiService().getTotalCountByMerchant(params);
    }
    
    /**
     * 用户认证-商户维度-统计接口分页总数
     * @param params 参数
     * @return 结果
     * @throws Exception 异常
     * @author 季振宇  
     * @date Aug 1, 2017 9:15:27 AM
     */
    public static Map<String, Object> getCountByMerchant(String params) throws Exception {
        return getUserAuthStatApiService().getCountByMerchant(params);
    }
    
    /**
     * 用户认证-商户维度-统计接口分页列表
     * @param params 参数
     * @return 结果
     * @throws Exception 异常
     * @author 季振宇  
     * @date Aug 1, 2017 9:15:31 AM
     */
    public static Map<String, Object> getListByMerchant(String params) throws Exception {
        return getUserAuthStatApiService().getListByMerchant(params);
    }
}

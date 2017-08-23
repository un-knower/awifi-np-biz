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

import com.awifi.np.biz.api.client.dbcenter.stat.model.DeviceTrend;
import com.awifi.np.biz.api.client.dbcenter.stat.service.DeviceTrendStatApiService;
import com.awifi.np.biz.common.util.BeanUtil;

public class DeviceTrendStatClient {

    /***/
    private static DeviceTrendStatApiService deviceTrendStatApiService;
    
    /**
     * 获取deviceTrendStatApiService实例
     * @return deviceTrendStatApiService
     * @author 周颖  
     * @date 2017年7月28日 上午9:22:03
     */
    private static DeviceTrendStatApiService getDeviceTrendStatApiService(){
        if(deviceTrendStatApiService == null){
            deviceTrendStatApiService = (DeviceTrendStatApiService) BeanUtil.getBean("deviceTrendStatApiService");
        }
        return deviceTrendStatApiService;
    }
   
    /**
     * 按周统计
     * @param params 参数
     * @param hasTotal 返回值是否有总计
     * @return 结果
     * @author 周颖  
     * @throws Exception 
     * @date 2017年7月28日 上午9:20:58
     */
    public static List<DeviceTrend> getByWeek(Map<String,Object> params,boolean hasTotal) throws Exception{
        return getDeviceTrendStatApiService().getByWeek(params,hasTotal);
    }
    
    /**
     * 按月统计
     * @param params 参数
     * @param hasTotal 返回值是否有总计
     * @return 结果
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年7月31日 下午2:34:20
     */
    public static List<DeviceTrend> getByMonth(Map<String,Object> params,boolean hasTotal) throws Exception{
        return getDeviceTrendStatApiService().getByMonth(params,hasTotal);
    }
}

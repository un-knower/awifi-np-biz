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

import com.awifi.np.biz.api.client.dbcenter.stat.model.DeviceTrend;

public interface DeviceTrendStatApiService {

    /**
     * 按周统计
     * @param params 参数
     * @param hasTotal 返回值是否有总计
     * @return 结果
     * @author 周颖  
     * @throws Exception 
     * @date 2017年7月28日 上午9:20:58
     */
    List<DeviceTrend> getByWeek(Map<String, Object> params,boolean hasTotal) throws Exception;

    /**
     * 按月统计
     * @param params 参数
     * @param hasTotal 返回值是否有总计
     * @return 结果
     * @author 周颖  
     * @throws Exception 
     * @date 2017年7月31日 下午2:34:48
     */
    List<DeviceTrend> getByMonth(Map<String, Object> params,boolean hasTotal) throws Exception;

}

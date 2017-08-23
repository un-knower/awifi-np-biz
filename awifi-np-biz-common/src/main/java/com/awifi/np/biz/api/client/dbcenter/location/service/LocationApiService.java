package com.awifi.np.biz.api.client.dbcenter.location.service;

import java.util.List;
import java.util.Map;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月22日 上午8:50:33
 * 创建作者：周颖
 * 文件名称：LocationApiService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface LocationApiService {

    /**
     * 获取全部地区信息
     * @author 周颖 
     * @return map 
     * @throws Exception 
     * @date 2017年1月22日 上午10:05:34
     */
    Map<Long,Map<String,Object>> getAllLocation() throws Exception;

    /**
     * 地区 key为地区名称，value为该地区属性
     * @return map
     * @author 周颖  
     * @throws Exception 
     * @date 2017年4月10日 上午9:56:08
     */
    Map<String, List<Map<String, Object>>> getLocationMap() throws Exception;
}

package com.awifi.np.biz.devicebindsrv.system.location.service;

import java.util.List;
import java.util.Map;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月22日 上午8:47:24
 * 创建作者：周颖
 * 文件名称：LocationService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface LocationService {

    /**
     * 获取省信息
     * @return list
     * @author 周颖  
     * @throws Exception 
     * @date 2017年1月22日 下午2:26:43
     */
    List<Map<String, Object>> getProvinces() throws Exception;

    /**
     * 获取市信息
     * @param provinceId 省id
     * @return list
     * @throws Exception 
     * @author 周颖  
     * @date 2017年1月22日 下午3:05:38
     */
    List<Map<String,Object>> getCities(String provinceId) throws Exception;
    
    /**
     * 获取市信息
     * @param cityId 市id
     * @return list
     * @throws Exception 
     * @author 周颖  
     * @date 2017年1月22日 下午3:05:38
     */
    List<Map<String,Object>> getAreas(String cityId) throws Exception;
}

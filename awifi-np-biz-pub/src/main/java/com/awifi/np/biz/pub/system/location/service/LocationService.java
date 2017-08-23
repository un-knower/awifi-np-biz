package com.awifi.np.biz.pub.system.location.service;

import java.util.List;
import java.util.Map;

import com.awifi.np.biz.common.security.user.model.SessionUser;

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
     * @param sessionUser 当前登陆账号
     * @return list
     * @author 周颖  
     * @throws Exception 
     * @date 2017年1月22日 下午2:26:43
     */
    List<Map<String, Object>> getProvinces(SessionUser sessionUser) throws Exception;

    /**
     * 获取市信息
     * @param sessionUser 当前登陆账号
     * @param provinceId 省id
     * @return list
     * @throws Exception 
     * @author 周颖  
     * @date 2017年1月22日 下午3:05:38
     */
    List<Map<String,Object>> getCities(SessionUser sessionUser,String provinceId) throws Exception;
    
    /**
     * 获取市信息
     * @param sessionUser 当前登陆账号
     * @param cityId 市id
     * @return list
     * @throws Exception 
     * @author 周颖  
     * @date 2017年1月22日 下午3:05:38
     */
    List<Map<String,Object>> getAreas(SessionUser sessionUser,String cityId) throws Exception;
}

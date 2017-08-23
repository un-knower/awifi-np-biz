package com.awifi.np.admin.platform.service;
/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月12日 下午2:47:02
 * 创建作者：周颖
 * 文件名称：PlatformService.java
 * 版本：  v1.0
 * 功能：平台业务层
 * 修改记录：
 */
public interface PlatformService {

    /**
     * 根据平台id获取平台key
     * @param appId 平台id
     * @return 平台key
     * @author 周颖  
     * @date 2017年1月12日 下午2:59:11
     */
    String getKeyByAppId(String appId);
}

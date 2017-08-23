package com.awifi.np.admin.service.service;

import java.util.List;
import java.util.Map;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月12日 下午3:55:50
 * 创建作者：周颖
 * 文件名称：ServiceService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface ServiceService {

    /**
     * 获取一级菜单
     * @param appId 平台id
     * @param roleIds 角色id
     * @return 一级菜单
     * @author 周颖  
     * @date 2017年1月12日 下午8:32:07
     */
    List<Map<String,Object>> getTopMenus(String appId,Long[] roleIds);
}

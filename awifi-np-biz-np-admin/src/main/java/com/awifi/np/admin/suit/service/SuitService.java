package com.awifi.np.admin.suit.service;

import java.util.List;
import java.util.Map;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月11日 下午4:55:23
 * 创建作者：周颖
 * 文件名称：SuitService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface SuitService {

    /**
     * 根据角色获取套码
     * @param roleIds 角色
     * @return 套码
     * @author 周颖  
     * @date 2017年1月11日 下午8:01:44
     */
    String getCodeById(Long[] roleIds);

    /**
     * 获取当前登陆账号所有套码
     * @param userId 用户id
     * @return 套码
     * @author 周颖  
     * @date 2017年5月9日 下午2:18:24
     */
    List<Map<String, Object>> getSuitCodesByUserId(Long userId);
}

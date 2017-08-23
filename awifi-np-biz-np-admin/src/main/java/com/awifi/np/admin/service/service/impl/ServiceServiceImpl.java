package com.awifi.np.admin.service.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.awifi.np.admin.service.dao.ServiceDao;
import com.awifi.np.admin.service.service.ServiceService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月12日 下午4:05:05
 * 创建作者：周颖
 * 文件名称：ServiceServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Service("serviceService")
public class ServiceServiceImpl implements ServiceService {

    /**服务dao*/
    @Resource(name = "serviceDao")
    private ServiceDao serviceDao;
    
    /**
     * 获取一级菜单
     * @param appId 平台id
     * @param roleIds 角色id
     * @return 一级菜单
     * @author 周颖  
     * @date 2017年1月12日 下午8:32:07
     */
    public List<Map<String,Object>> getTopMenus(String appId,Long[] roleIds){
        return serviceDao.getTopMenus(appId,roleIds);
    }
}
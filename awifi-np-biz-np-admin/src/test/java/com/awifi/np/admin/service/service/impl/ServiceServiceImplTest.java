package com.awifi.np.admin.service.service.impl;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.awifi.np.admin.service.dao.ServiceDao;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月16日 下午2:51:26
 * 创建作者：周颖
 * 文件名称：ServiceServiceImplTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class ServiceServiceImplTest {

    /**注入被测试类*/
    @InjectMocks
    private ServiceServiceImpl serviceServiceImpl;
    
    /**mock*/
    @Mock(name = "serviceDao")
    private ServiceDao serviceDao;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }
    
    /**
     * testGetIdsById
     * @author 周颖  
     * @date 2017年1月16日 下午2:49:04
     */
    @Test
    public void testGetIdsById() {
        List<Map<String,Object>> menus = new ArrayList<Map<String,Object>>();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("url", "index");
        menus.add(map);
        Long[] roleIds = {1L,2L};
        when(serviceDao.getTopMenus(anyString(), anyObject())).thenReturn(menus);
        List<Map<String,Object>> result = serviceServiceImpl.getTopMenus("appId", roleIds);
        Assert.assertNotNull(result);        
    }
}
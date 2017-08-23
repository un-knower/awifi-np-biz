package com.awifi.np.admin.suit.service.impl;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.awifi.np.admin.suit.dao.SuitDao;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月16日 下午3:11:23
 * 创建作者：周颖
 * 文件名称：SuitServiceImplTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class SuitServiceImplTest {

    /**注入被测试类*/
    @InjectMocks
    private SuitServiceImpl suitServiceImpl;
    
    /**mock*/
    @Mock(name = "suitDao")
    private SuitDao suitDao;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }
    
    /**
     * 测试失败 角色为空
     * @author 周颖  
     * @date 2017年1月16日 下午4:50:02
     */
    @Test
    public void testGetCodeByIdFail() {
        Long[] roleIds = null;
        String result = suitServiceImpl.getCodeById(roleIds);
        Assert.assertNotNull(result);
    }
    
    /**
     * 测试成功
     * @author 周颖  
     * @date 2017年1月16日 下午4:50:17
     */
    @Test
    public void testGetCodeByIdOK() {
        Long[] roleIds = {1L};
        when(suitDao.getCodeById(anyObject())).thenReturn("suitCode");
        
        String result = suitServiceImpl.getCodeById(roleIds);
        Assert.assertNotNull(result);
    }
    
    /**
     * 获取当前登陆账号所有套码
     * @author 方志伟  
     * @date 2017年6月23日 下午4:18:42
     */
    @Test
    public void testGetSuitCodesByUserId() {
        suitServiceImpl.getSuitCodesByUserId(1L);
    }
}
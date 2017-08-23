/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月27日 下午3:26:26
* 创建作者：王冬冬
* 文件名称：RoleControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.usrsrv.role.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.security.role.service.RoleService;


@RunWith(PowerMockRunner.class)
public class RoleControllerTest {
    /**被测试类*/
    @InjectMocks
    private RoleController roleController;
    
    /**
     * 用户service
     */
    @Mock
    private RoleService roleService;
    
    /**httpRequest*/
    private MockHttpServletRequest httpRequest;
    
    /**httpResponse*/
    private MockHttpServletResponse httpResponse;
    
    
    //private MockServletContext mockServletContext;
    
    /**初始化*/
    @Before
    public void before(){
        httpRequest = new MockHttpServletRequest();
        //mockServletContext = new MockServletContext();
        MockitoAnnotations.initMocks(this);
    }
   
    
   
    /**
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年5月27日 下午4:07:38
     */
    @Test
    public void testGetPermisionByRoleId() throws Exception {
        List list=new ArrayList();
        PowerMockito.when(roleService.getPermisionByRoleId(1L)).thenReturn(list);
        Map result= roleController.getPermisionByRoleId("xxxx", httpRequest, 1L);
        Assert.assertEquals("0",result.get("code"));
    }
}

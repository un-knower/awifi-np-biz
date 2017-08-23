package com.awifi.np.biz.mersrv.security.role.controller;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

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
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;

import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.toe.admin.security.org.util.OrgUtil;
import com.awifi.np.biz.toe.admin.security.role.model.ToeRole;
import com.awifi.np.biz.toe.admin.security.role.service.ToeRoleService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月16日 上午10:03:46
 * 创建作者：周颖
 * 文件名称：RoleControllerTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest({SessionUtil.class,OrgUtil.class})
public class RoleControllerTest {

    /**被测试类*/
    @InjectMocks
    private RoleController roleController;
    
    /**toe角色服务层*/
    @Mock(name = "toeRoleService")
    private ToeRoleService toeRoleService;
    
    /**httpRequest*/
    private MockHttpServletRequest httpRequest;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        httpRequest = new MockHttpServletRequest();
        PowerMockito.mockStatic(SessionUtil.class);
        PowerMockito.mockStatic(OrgUtil.class);
    }
    
    /**
     * 通过组织id获取角色列表
     * @author 周颖  
     * @date 2017年2月16日 上午10:14:45
     */
    @Test
    public void testGetListByOrgId() {
        List<ToeRole> roleList = new ArrayList<ToeRole>();
        SessionUser sessionUser = new SessionUser();
        PowerMockito.when(SessionUtil.getCurSessionUser(anyObject())).thenReturn(sessionUser);
        PowerMockito.when(OrgUtil.getCurOrgId(httpRequest)).thenReturn(1L);
        when(toeRoleService.getListByOrgId(anyObject(),anyObject(),anyLong())).thenReturn(roleList);
        
        Map<String,Object> result = roleController.getListByOrgId("access_token", httpRequest, 1L);
        Assert.assertNotNull(result);
        SessionUtil.getCurSessionUser(anyObject());
        OrgUtil.getCurOrgId(httpRequest);
        verify(toeRoleService).getListByOrgId(anyObject(),anyObject(),anyLong());
    }
}
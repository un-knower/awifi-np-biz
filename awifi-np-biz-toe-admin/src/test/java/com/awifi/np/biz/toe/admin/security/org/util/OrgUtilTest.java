package com.awifi.np.biz.toe.admin.security.org.util;

import static org.mockito.Matchers.anyObject;

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
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.toe.admin.security.role.service.ToeRoleService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月22日 下午6:59:59
 * 创建作者：亢燕翔
 * 文件名称：OrgUtilTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings("static-access")
@RunWith(PowerMockRunner.class)
@PrepareForTest({SysConfigUtil.class, MessageUtil.class, BeanUtil.class, SessionUtil.class})
public class OrgUtilTest {

    /**被测试类*/
    @InjectMocks
    private OrgUtil orgUtil;
    
    /**请求*/
    private MockHttpServletRequest request;
    
    /**角色业务层*/
    @Mock(name = "toeRoleService")
    private ToeRoleService toeRoleService;
    /**
     * 初始化
     * @author 亢燕翔  
     * @date 2017年3月22日 下午6:58:20
     */
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.mockStatic(SessionUtil.class);
        request = new MockHttpServletRequest();
    }
    
    /**
     * 获取组织ID
     * @author 亢燕翔  
     * @date 2017年3月22日 下午7:13:16
     */
    @Test
    public void testGetCurOrgId(){
        SessionUser sessionUser = new SessionUser();
        sessionUser.setMerchantId(15L);
        sessionUser.setProvinceId(31L);
        sessionUser.setCityId(313L);
        sessionUser.setAreaId(4512L);
        PowerMockito.when(SessionUtil.getCurSessionUser(anyObject())).thenReturn(sessionUser);
        PowerMockito.when(BeanUtil.getBean(anyObject())).thenReturn(toeRoleService);
        orgUtil.getCurOrgId(request);
    }
    
    /**
     * 获取组织ID
     * orgId != null
     * @author 方志伟  
     * @date 2017年6月21日 上午10:12:24
     */
    @Test
    public void testGetCurOrgIdNotNull(){
        SessionUser sessionUser = new SessionUser();
        sessionUser.setMerchantId(15L);
        sessionUser.setProvinceId(31L);
        sessionUser.setCityId(313L);
        sessionUser.setAreaId(4512L);
        sessionUser.setOrgId(1L);
        PowerMockito.when(SessionUtil.getCurSessionUser(anyObject())).thenReturn(sessionUser);
        PowerMockito.when(BeanUtil.getBean(anyObject())).thenReturn(toeRoleService);
        orgUtil.getCurOrgId(request);
    }
    
    /**
     * 测试是否是超级管理员
     * @author 亢燕翔  
     * @date 2017年3月22日 下午7:12:43
     */
    @Test
    public void testIsSuperAdmin(){
        SessionUser sessionUser = new SessionUser();
        sessionUser.setId(1L);
        orgUtil.isSuperAdmin(sessionUser);
    }
    
    /**
     * 测试是否是超级管理员
     * @author 亢燕翔  
     * @date 2017年3月22日 下午7:13:00
     */
    @Test
    public void testIsSuperAdminUserId(){
        orgUtil.isSuperAdmin(1L);
    }
    
    /**
     * 测试 判断组织是否为"爱WiFi运营中心"
     * @author 方志伟  
     * @date 2017年6月21日 上午10:04:35
     */
    @Test
    public void testIsAwifi(){
        orgUtil.isAwifi(1L);
    }
    
    /**
     * 测试判断组织是否为“爱wifi运营中心”
     * 当orgId == null
     * @author 方志伟  
     * @date 2017年6月21日 上午10:04:38
     */
    @Test
    public void testIsAwifiNull(){
        orgUtil.isAwifi(null);
    }
    
    /**
     * 测试 判断是否是"电信"
     * @author 方志伟  
     * @date 2017年6月21日 上午10:04:41
     */
    @Test
    public void testIsTelecom(){
        orgUtil.isTelecom(1L);
    }
    
    /**
     * 测试 判断是否是"项目"
     * @author 方志伟  
     * @date 2017年6月21日 上午10:04:45
     */
    @Test
    public void testIsProject(){
        orgUtil.isProject(1L);
    }
    
    /**
     * 测试判断组织是否为"其它"
     * @author 方志伟  
     * @date 2017年6月21日 上午10:04:48
     */
    @Test
    public void testIsOther(){
        orgUtil.isOther(1L);
    }
}

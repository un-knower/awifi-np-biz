package com.awifi.np.biz.portalsrv.security.permission.controller;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.security.permission.service.PermissionService;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.MessageUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class,SysConfigUtil.class})
public class PermissionControllerTest {

    /**被测试类*/
    @InjectMocks
    private PermissionController permissionController;
    
    /**mock模板服务*/
    @Mock(name = "permissionService")
    private PermissionService permissionService;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
    }
    
    /**
     * 参数合法性验证
     * @author 许小满  
     * @date 2017年2月9日 上午11:10:15
     */
    @Test(expected=ValidException.class)
    public void testCheckViewParam(){
        String accessToken = "x";
        String params = "{'interfaceCode':'/xxx:GET','userInfo':{'roleIds':'1,,3'}}";
        permissionController.check(accessToken, params);
    }
    
    /**
     * 测试权限接口校验失败的情况
     * @author 许小满  
     * @date 2017年2月9日 上午11:10:15
     */
   /* @Test(expected=BizException.class)
    public void testCheckViewBizException(){
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("error");
        when(permissionService.check(anyObject(), anyString(), anyString())).thenReturn(false);
        String accessToken = "x";
        String params = "{'interfaceCode':'/xxx:GET','userInfo':{'roleIds':'1,2,3'}}";
        permissionController.check(accessToken, params);
    }*/
    
    /**
     * 测试权限接口校验成功的情况
     * @author 许小满  
     * @date 2017年2月9日 上午11:10:15
     */
   /* @Test
    public void testCheckViewOK(){
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("error");
        when(permissionService.check(anyObject(), anyString(), anyString())).thenReturn(true);
        String accessToken = "x";
        String params = "{'interfaceCode':'/xxx:GET','userInfo':{'roleIds':'1,2,3'}}";
        Map<String,Object> resultMap = permissionController.check(accessToken, params);
        String code = (String)resultMap.get("code");
        Assert.assertEquals(code, "0");
    }*/
    
    /**
     * 测试 获取某一角色关联的权限接口  成功清情况
     * @author 许小满  
     * @date 2017年2月16日 下午3:37:27
     */
    @Test
    public void getCodesByRoleIdForAm(){
        String roleId = "1";//角色id
        List<String> codes = new ArrayList<String>();
        codes.add("/xxx:GET");
        when(permissionService.getCodesByRoleId(anyString(), anyLong())).thenReturn(codes);
        Map<String,Object> resultMap = permissionController.getCodesByRoleIdForAm(roleId);
        String code = (String)resultMap.get("code");
        Assert.assertEquals(code, "0");
    }
    
    /**
     * 测试 权限批量更新接口  成功清情况
     * @author 许小满  
     * @date 2017年2月16日 下午3:37:27
     */
    @Test
    public void batchAddRolePermissionForAm(){
        String roleId = "1";//角色id
        String[] codes = {"/xxx:GET", "/xxx:POST"};//权限（接口）编号数据
        Mockito.doNothing().when(permissionService).batchAddRolePermission(anyString(), anyLong(), anyObject());
        Map<String,Object> resultMap = permissionController.batchAddRolePermissionForAm(roleId, codes);
        String code = (String)resultMap.get("code");
        Assert.assertEquals(code, "0");
    }
    
    /**
     * 推送接口注册信息 
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年3月22日 上午10:01:14
     */
    @Test
    public void pushForAm() throws Exception{
        PowerMockito.when(SysConfigUtil.getParamValue("servicecode_project")).thenReturn("servicecode");
        PowerMockito.when(SysConfigUtil.getParamValue("servicekey_project")).thenReturn("servicekey");
        Mockito.doNothing().when(permissionService).pushInterfaces(anyString(), anyString());
       
        permissionController.pushForAm();
    }
}

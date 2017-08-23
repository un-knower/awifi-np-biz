package com.awifi.np.biz.appsrv.app.controller;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import java.util.HashMap;
import java.util.Map;
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
import org.springframework.mock.web.MockHttpServletRequest;

import com.awifi.np.biz.appsrv.app.model.App;
import com.awifi.np.biz.appsrv.app.service.AppService;
import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;

import org.junit.Assert;


@RunWith(PowerMockRunner.class)
@PrepareForTest({SessionUtil.class,RegexConstants.class,JsonUtil.class,SysConfigUtil.class,ValidUtil.class,CastUtil.class,Page.class,SessionUser.class})
public class AppControllerTest {
    /**被测试类*/
    @InjectMocks
    private AppController appController;
    
    /**应用接口服务*/
    @Mock(name = "appService")
    private AppService appService;
    
    /**请求*/
    private MockHttpServletRequest request;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(RegexConstants.class);
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(ValidUtil.class);
        PowerMockito.mockStatic(CastUtil.class);
        PowerMockito.mockStatic(Page.class);
        PowerMockito.mockStatic(SessionUser.class);
        PowerMockito.mockStatic(SessionUtil.class);
    }
    
    /**
     * 应用管理—应用添加接口
     * @throws Exception 
     * @author 许尚敏
     * @date 2017年7月12日 上午9:53:22
     */
    @Test
    public void testAdd() throws Exception{
        Map<String,Object> bodyParam = new HashMap<String, Object>();
        bodyParam.put("appName", "appName");
        bodyParam.put("appParam", "appParam");
        bodyParam.put("company", "company");
        bodyParam.put("businessLicense", "businessLicense");
        bodyParam.put("contactPerson", "contactPerson");
        bodyParam.put("contactWay", "contactWay");
        bodyParam.put("status", 1);
        bodyParam.put("remark", "remark");
        Mockito.doNothing().when(appService).add(anyObject());
        appController.add("", bodyParam);
    }

    /**
     * 应用管理—应用编辑接口
     * @throws Exception 
     * @author 许尚敏
     * @date 2017年7月12日 上午10:05:22
     */
    @Test
    public void testUpdate() throws Exception{
        Map<String,Object> bodyParam = new HashMap<String, Object>();
        bodyParam.put("appName", "appName");
        bodyParam.put("appParam", "appParam");
        bodyParam.put("company", "company");
        bodyParam.put("businessLicense", "businessLicense");
        bodyParam.put("contactPerson", "contactPerson");
        bodyParam.put("contactWay", "contactWay");
        bodyParam.put("status", 1);
        bodyParam.put("remark", "remark");
        Mockito.doNothing().when(appService).add(anyObject());
        appController.update("", 1L, bodyParam);
    }

    /**
     * 测试应用管理-分页查询接口
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jul 14, 2017 10:42:04 AM
     */
    @Test
    public void testGetListByParam () throws Exception {
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("appName", "appName");
        paramsMap.put("status", "status");
        paramsMap.put("pageNo", 1);
        paramsMap.put("pageSize", 2);
        PowerMockito.when(JsonUtil.fromJson(anyObject(), anyObject())).thenReturn(paramsMap);
        PowerMockito.when(SysConfigUtil.getParamValue("page_maxsize")).thenReturn("50");
        PowerMockito.doNothing().when(ValidUtil.class,"valid",anyString(),anyObject(),anyString());
        PowerMockito.when(CastUtil.toInteger(paramsMap.get(anyString()))).thenReturn(1);
        
        Page<App> page = new Page<>();
        page.setPageSize(10);
        SessionUser sessionUser = new SessionUser();
        PowerMockito.when(SessionUtil.getCurSessionUser(request)).thenReturn(sessionUser);
        PowerMockito.doNothing().when(appService).getListByParam(sessionUser, page, "appName", 1);
        
        Map<String,Object> map = appController.getListByParam(request, "access_token", "{'pageSize':2,'pageNo':'1','appName':'appName','status':'1'}");
        Assert.assertNotNull(map);        
    }
    
    /**
     * 测试应用管理-应用列表-详情查询
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jul 14, 2017 10:24:45 AM
     */
    @Test
    public void testGetById () throws Exception {
        App app = new App();
        PowerMockito.when(appService.getById(Mockito.anyLong())).thenReturn(app);
        Map<String, Object> map = appController.getById("access_token",2L);
        Assert.assertNotNull(map);
    }
    
    /**
     * 测试应用管理-应用删除
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jul 14, 2017 10:19:56 AM
     */
    @Test
    public void testDelete () throws Exception {
        Map<String, Object> map = appController.delete("access_token",2L);
        Assert.assertNotNull(map);
    }
}

package com.awifi.np.biz.projsrv.project.controller;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.template.service.TemplateService;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.toe.admin.project.service.ProjectService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月17日 下午2:08:52
 * 创建作者：亢燕翔
 * 文件名称：ProjectControllerTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings({ "unchecked" })
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class,JsonUtil.class,RedisUtil.class,SysConfigUtil.class,SessionUtil.class})
@PowerMockIgnore({"javax.management.*"})
public class ProjectControllerTest {

    /**被测试类*/
    @InjectMocks
    private ProjectController projectController;
    
    /**项目服务*/
    @Mock(name = "projectService")
    private ProjectService projectService;
    
    /**模板服务*/
    @Mock(name = "templateService")
    private TemplateService templateService;
    
    /**请求*/
    private MockHttpServletRequest request;
    
    /**响应*/
    private MockHttpServletResponse response;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(RedisUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
    }
    
    /**
     * 测试获取veiw成功情况 
     * @author 亢燕翔  
     * @date 2017年1月17日 下午3:35:17
     */
    @Test
    public void testViewOk(){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("id", 1);
        dataMap.put("userName", "superadmin");
        dataMap.put("roleIds", "1");
        dataMap.put("provinceId", 31);
        dataMap.put("cityId", 383);
        dataMap.put("areaId", 3232);
        dataMap.put("merchantId", 1110);
        dataMap.put("suitCode", "S001");
        resultMap.put("code", 0);
        resultMap.put("data", dataMap);
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("100");
        request.setAttribute("userInfo",resultMap);
        Map<String, Object> map = projectController.view(request, "001", "access_token");
        Assert.assertNotNull(map);
    }
    
    /**
     * 测试列表成功情况
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年1月17日 下午2:36:24
     */
    @Test
    public void testListOk() throws Exception {
        PowerMockito.mockStatic(SessionUtil.class);
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("100");
        String params = "{'projectName':'projectName','provinceId':1L,'cityId':1L,'areaId':1L,'areaId':1L,'pageNo':1,'pageSize':10}";
        Map<String, Object> resultMap = projectController.list(request, "aaa", params);
        Assert.assertNotNull(resultMap);
    }
    
    /**
     * 测试添加项目
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年1月17日 下午2:45:20
     */
    @Test
    public void testAdd() throws Exception{
        projectController.add("access_token", new HashMap<String, Object>());
    }
    
    /**
     * 测试项目详情
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年1月17日 下午2:46:24
     */
    @Test
    public void testShow() throws Exception{
        projectController.getById("access_token", 14L);
    }
    
    /**
     * 测试删除项目
     * @author 亢燕翔  
     * @date 2017年1月17日 下午2:47:39
     */
    @Test
    public void testDelete(){
        projectController.delete("access_token", 14L);
    }
    
    /**
     * 测试编辑项目 
     * @author 亢燕翔  
     * @date 2017年1月17日 下午2:49:10
     */
    @Test
    public void testUpdate(){
        projectController.update("access_token", 14L, new HashMap<String, Object>());
    }
    
    /**
     * 测试导出项目
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月13日 下午8:16:03
     */
    @Test
    public void testExport() throws Exception{
        String params = "{'projectName':'projectName','provinceId':1L,'cityId':1L,'areaId':1L,'areaId':1L,'pageNo':1,'pageSize':10}";
        PowerMockito.mockStatic(SessionUtil.class);
        projectController.export(request, response, anyObject(), params);
    }
}

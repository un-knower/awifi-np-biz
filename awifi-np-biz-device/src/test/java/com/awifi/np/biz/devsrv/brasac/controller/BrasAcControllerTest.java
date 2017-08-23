/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月20日 下午2:32:04
* 创建作者：范立松
* 文件名称：BrasAcControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.brasac.controller;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.devsrv.brasac.service.BrasAcService;

@RunWith(PowerMockRunner.class)
@SuppressWarnings("unchecked")
@PrepareForTest({ SysConfigUtil.class, CastUtil.class, ValidUtil.class, SessionUtil.class, CastUtil.class,
        JsonUtil.class, MessageUtil.class })
public class BrasAcControllerTest {

    /**被测试类*/
    @InjectMocks
    private BrasAcController brasAcController;

    /** 套餐配置业务层 */
    @Mock(name = "brasAcService")
    private BrasAcService brasAcService;

    /**httpRequest*/
    private MockHttpServletRequest request;

    /**初始化*/
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(CastUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(ValidUtil.class);
        PowerMockito.mockStatic(SessionUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
        SessionUser sessionUser = new SessionUser();
        sessionUser.setUserName("admin");
        PowerMockito.when(SessionUtil.getCurSessionUser(anyObject())).thenReturn(sessionUser);
    }

    /**
     * 测试提交审核
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testSubmitAudit() throws Exception {
        List<String> idList = new ArrayList<>();
        idList.add("50021");
        Map<String, Object> resultMap = brasAcController.submitAudit("accessToken", request, idList);
        Assert.assertNotNull(resultMap);
    }

    /**
     * 测试根据设备id查询
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testQueryBrasAcById() throws Exception {
        Map<String, Object> resultMap = brasAcController.queryBrasAcById("accessToken", "500001");
        Assert.assertNotNull(resultMap);
    }

    /**
     * 测试更新bras
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testUpdateBras() throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(33);
        PowerMockito.when(CastUtil.toLong(anyObject())).thenReturn(33L);
        PowerMockito.when(CastUtil.toString(anyObject())).thenReturn("192.168.1.10");
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("error");
        Map<String, Object> resultMap = brasAcController.updateBras("accessToken", paramsMap, "50021", request);
        Assert.assertNotNull(resultMap);
    }

    /**
     * 测试更新ac
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testUpdateAc() throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(33);
        PowerMockito.when(CastUtil.toLong(anyObject())).thenReturn(33L);
        PowerMockito.when(CastUtil.toString(anyObject())).thenReturn("192.168.1.10");
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("error");
        Map<String, Object> resultMap = brasAcController.updateAc("accessToken", paramsMap, "50021", request);
        Assert.assertNotNull(resultMap);
    }

    /**
     * 测试入库查询
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testQueryBrasAcAuditList() throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("pageSize", 10);
        PowerMockito.when(JsonUtil.fromJson(anyString(), anyObject())).thenReturn(paramsMap);
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(null);
        PowerMockito.when(SysConfigUtil.getParamValue("page_maxsize")).thenReturn("10");
        Map<String, Object> resultMap = brasAcController.queryBrasAcAuditList("accessToken", "", request);
        Assert.assertNotNull(resultMap);
    }

    /**
     * 测试入库查询
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testQueryBrasAcList() throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("pageSize", 10);
        PowerMockito.when(JsonUtil.fromJson(anyString(), anyObject())).thenReturn(paramsMap);
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(null);
        PowerMockito.when(SysConfigUtil.getParamValue("page_maxsize")).thenReturn("10");
        Map<String, Object> resultMap = brasAcController.queryBrasAcList("accessToken", "", request);
        Assert.assertNotNull(resultMap);
    }

    /**
     * 测试添加bras
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testAddBras() throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(33);
        PowerMockito.when(CastUtil.toString(anyObject())).thenReturn("192.168.1.10");
        Map<String, Object> resultMap = brasAcController.addBras("accessToken", paramsMap, request);
        Assert.assertNotNull(resultMap);
    }

    /**
     * 测试添加ac
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testAddAc() throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(33);
        PowerMockito.when(CastUtil.toString(anyObject())).thenReturn("192.168.1.10");
        Map<String, Object> resultMap = brasAcController.addAc("accessToken", paramsMap, request);
        Assert.assertNotNull(resultMap);
    }

    /**
     * 测试删除设备
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年6月16日 上午9:29:22
     */
    @Test
    public void testRemoveBrasAc() throws Exception {
        Map<String, Object> resultMap = brasAcController.removeBrasAc("accessToken", "1,2");
        Assert.assertNotNull(resultMap);
    }

}

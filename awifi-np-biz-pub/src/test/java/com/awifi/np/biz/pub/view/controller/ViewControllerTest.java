package com.awifi.np.biz.pub.view.controller;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

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

import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.redis.util.RedisAdminUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.template.service.TemplateService;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月13日 上午11:20:29
 * 创建作者：周颖
 * 文件名称：IndexControllerTest.java
 * 版本：  v1.0
 * 功能：IndexController测试类
 * 修改记录：
 */
@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class,JsonUtil.class,RedisAdminUtil.class,SysConfigUtil.class})
@PowerMockIgnore({"javax.management.*"})
public class ViewControllerTest {

    /**被测试类*/
    @InjectMocks
    private ViewController viewController;
    
    /**mock模板服务*/
    @Mock(name = "templateService")
    private TemplateService templateService;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(RedisAdminUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
    }
    
    /***
     * 测试access_token失效
     * @author 周颖  
     * @date 2017年1月13日 下午1:59:21
     */
    @Test(expected=BizException.class)
    public void testIndexViewBizException() {
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("value");
        PowerMockito.when(RedisAdminUtil.get(anyString())).thenReturn(null);
        Map<String,Object> result = viewController.indexView("a", "b");
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        SysConfigUtil.getParamValue(anyString());
        RedisAdminUtil.get(anyString());
    }
    
    /**
     * 获取首页页面成功
     * @author 周颖  
     * @date 2017年1月13日 下午2:24:57
     */
    @Test
    public void testIndexViewOK() {
        Map<String,Object> value = new HashMap<String,Object>();
        Map<String, Object> userInfo = new HashMap<String,Object>();
        userInfo.put("suitCode", "S_001");
        value.put("userInfo", userInfo);
        
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("value");
        PowerMockito.when(RedisAdminUtil.get(anyString())).thenReturn("result");
        PowerMockito.when(JsonUtil.fromJson(anyString(),anyObject())).thenReturn(value);
        when(templateService.getByCode(anyString(),anyString(),anyString())).thenReturn("template");
        
        Map<String,Object> result = viewController.indexView("a", "b");
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        SysConfigUtil.getParamValue(anyString());
        RedisAdminUtil.get(anyString());
        JsonUtil.fromJson(anyString(),anyObject());
    }
}
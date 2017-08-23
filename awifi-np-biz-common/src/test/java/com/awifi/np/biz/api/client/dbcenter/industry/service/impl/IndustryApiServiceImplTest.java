package com.awifi.np.biz.api.client.dbcenter.industry.service.impl;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月27日 下午3:57:03
 * 创建作者：周颖
 * 文件名称：IndustryApiServiceImplTest.java
 * 版本：  v1.0
 * 功能：行业测试类
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({CenterHttpRequest.class,SysConfigUtil.class,JsonUtil.class})
public class IndustryApiServiceImplTest {

    /**
     * 被测试类
     */
    @InjectMocks
    private IndustryApiServiceImpl industryApiServiceImpl;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(CenterHttpRequest.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(JsonUtil.class);
    }
    
    /**
     * 获取所有行业
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月27日 下午4:06:07
     */
    @Test
    public void testGetAllIndustry() throws Exception {
        Map<String, Object> returnMap = new HashMap<String,Object>();
        PowerMockito.when(CenterHttpRequest.sendGetRequest(anyString(),anyString())).thenReturn(returnMap);
        PowerMockito.when(JsonUtil.toJson(anyObject())).thenReturn("result");
        
        industryApiServiceImpl.getAllIndustry();
        PowerMockito.verifyStatic();
        CenterHttpRequest.sendGetRequest(anyString(),anyString());
        JsonUtil.toJson(anyObject());
    }
}
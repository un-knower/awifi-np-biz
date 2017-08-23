package com.awifi.np.biz.portalsrv.security.security.controller;

import static org.mockito.Matchers.anyString;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有：爱WiFi无线运营中心
 * 创建时间：2017年6月5日 下午3:46:33
 * 创建作者：方志伟
 * 文件名称：SecurityControllerTest.java
 * 版本：v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class})
public class SecurityControllerTest {
	/**被测试类*/
    @InjectMocks
	private SecurityController securityController;
	
	/**初始化*/
    @Before
	public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(MessageUtil.class);
    }
	
	/**
	 * 测试参数合法性
	 * @author 方志伟
	 * @date 2017年6月5日 下午4:08:40
	 */
    @Test(expected=ValidException.class)
	public void testCheckParmsOk(){
        String accessToken = "x";
        String params = "{'interfaceCode':'','params':{}}";
        securityController.check(accessToken, params);
    }
	
	/**
	 * 测试权限接口
	 * @author 方志伟
	 * @date 2017年6月5日 下午4:09:46
	 */
    @Test
	public void testCheckViewOk(){
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("error");
        String accessToken = "x";
        String params = "{'interfaceCode':'/xxx:GET','params':{}}";
        Map<String,Object> resultMap = securityController.check(accessToken, params);
        String code = (String)resultMap.get("code");
        Assert.assertEquals(code, "0");
    }
}

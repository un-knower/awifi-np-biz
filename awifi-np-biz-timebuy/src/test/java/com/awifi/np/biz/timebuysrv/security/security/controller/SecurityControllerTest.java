package com.awifi.np.biz.timebuysrv.security.security.controller;

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
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月9日 下午7:44:49
 * 创建作者：许小满
 * 文件名称：SecurityControllerTest.java
 * 版本：  v1.0
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
     * 参数合法性验证
     * @author 许小满  
     * @date 2017年2月9日 上午11:10:15
     */
    @Test(expected=ValidException.class)
    public void testCheckViewParam(){
        String accessToken = "x";
        String params = "{'interfaceCode':'','params':{}}";
        securityController.check(accessToken, params);
    }
    
    /**
     * 测试权限接口校验成功的情况
     * @author 许小满  
     * @date 2017年2月9日 上午11:10:15
     */
    @Test
    public void testCheckViewOK(){
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("error");
        String accessToken = "x";
        String params = "{'interfaceCode':'/xxx:GET','params':{}}";
        Map<String,Object> resultMap = securityController.check(accessToken, params);
        String code = (String)resultMap.get("code");
        Assert.assertEquals(code, "0");
    }
    
}

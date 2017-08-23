package com.awifi.np.biz.common.exception.handler;

import static org.mockito.Matchers.anyObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONException;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.InterfaceException;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.system.log.service.ExceptionLogService;
import com.awifi.np.biz.common.system.log.util.ExceptionLogUtil;
import com.awifi.np.biz.common.util.FormatUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月9日 下午2:30:04
 * 创建作者：周颖
 * 文件名称：ExceptionHandlerTest.java
 * 版本：  v1.0
 * 功能：异常处理测试类
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({FormatUtil.class,MessageUtil.class,ExceptionLogUtil.class})
@PowerMockIgnore({"javax.management.*"})
public class ExceptionHandlerTest {

    /**
     * 测试类
     */
    @InjectMocks
    private ExceptionHandler exceptionHandler;
    
    /**mock*/
    private MockHttpServletRequest httpRequest;
    
    /**mock*/
    private  MockHttpServletResponse httpResponse;
    
    /**异常服务*/
    @Mock(name = "exceptionLogService")
    private ExceptionLogService exceptionLogService;
    
    /**
     * 初始化
     * @author 周颖  
     * @date 2017年1月9日 下午3:02:29
     */
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        httpRequest = new MockHttpServletRequest();
        httpResponse = new MockHttpServletResponse();
        PowerMockito.mockStatic(FormatUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(ExceptionLogUtil.class);
    }
    
    
    /**
     * InterfaceException 接口异常
     * @author 周颖  
     * @date 2017年1月9日 下午2:36:42
     */
    @SuppressWarnings("unused")
    @Test
    public void testInterfaceException() {
        httpRequest.setRequestURI("/test");
        PowerMockito.when(FormatUtil.formatRequestParam(httpRequest)).thenReturn("abd");
        Mockito.doNothing().when(exceptionLogService).saveExceptionLog(anyObject());
        Object handler = new Object();
        InterfaceException ex = new InterfaceException("aaa", "aaa", "aaa", "aaa");
        InterfaceException exe = new InterfaceException("aaa", "aaa", new Exception());
        InterfaceException exi = new InterfaceException("aaa", "aaa", "aaa");
        InterfaceException exie = new InterfaceException("aaa", "aaa", "aaa", new Exception());
        InterfaceException exiie = new InterfaceException("aaa", "aaa", "aaa", "aaa", new Exception());
        InterfaceException exiii = new InterfaceException("aaa", "aaa", "aaa", "aaa", "aaa");
        InterfaceException exiiie = new InterfaceException("aaa", "aaa", "aaa", "aaa", "aaa", new Exception());
        ModelAndView result = exceptionHandler.resolveException(httpRequest, httpResponse,handler,ex);
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        FormatUtil.formatRequestParam(anyObject());
    }
    
    /**
     * BizException 业务异常
     * @author 周颖  
     * @date 2017年1月9日 下午3:01:30
     */
    @Test
    public void testBizException() {
        httpRequest.setRequestURI("/test");
        PowerMockito.when(FormatUtil.formatRequestParam(httpRequest)).thenReturn("abd");
        Mockito.doNothing().when(exceptionLogService).saveExceptionLog(anyObject());
        Object handler = new Object();
        BizException ex = new BizException("code","msg");
        ModelAndView result = exceptionHandler.resolveException(httpRequest, httpResponse,handler,ex);
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        FormatUtil.formatRequestParam(anyObject());
    }
    
    /**
     * ValidException 校验异常
     * @author 周颖  
     * @date 2017年1月9日 下午3:01:30
     */
    @Test
    public void testValidException(){
        httpRequest.setRequestURI("/test");
        PowerMockito.when(FormatUtil.formatRequestParam(httpRequest)).thenReturn("abd");
        Mockito.doNothing().when(exceptionLogService).saveExceptionLog(anyObject());
        Object handler = new Object();
        ValidException ex = new ValidException("code","message");
        ModelAndView result = exceptionHandler.resolveException(httpRequest, httpResponse,handler,ex);
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        FormatUtil.formatRequestParam(anyObject());
    }
    
    /**
     * JSONException json转化异常
     * @author 周颖  
     * @date 2017年1月9日 下午3:01:30
     */
    @Test
    public void testJSONException(){
        httpRequest.setRequestURI("/test");
        PowerMockito.when(FormatUtil.formatRequestParam(httpRequest)).thenReturn("abd");
        Mockito.doNothing().when(exceptionLogService).saveExceptionLog(anyObject());
        Object handler = new Object();
        JSONException ex = new JSONException();
        ModelAndView result = exceptionHandler.resolveException(httpRequest, httpResponse,handler,ex);
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        FormatUtil.formatRequestParam(anyObject());
    }
    
    /**
     * OtherException 其他异常
     * @author 周颖  
     * @date 2017年1月9日 下午3:01:30
     */
    @Test
    public void testOtherException(){
        httpRequest.setRequestURI("/test");
        PowerMockito.when(FormatUtil.formatRequestParam(httpRequest)).thenReturn("abd");
        Mockito.doNothing().when(exceptionLogService).saveExceptionLog(anyObject());
        Object handler = new Object();
        NullPointerException ex = new NullPointerException();
        ModelAndView result = exceptionHandler.resolveException(httpRequest, httpResponse,handler,ex);
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        FormatUtil.formatRequestParam(anyObject());
    }
}
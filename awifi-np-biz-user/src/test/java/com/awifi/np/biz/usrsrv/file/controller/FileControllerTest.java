package com.awifi.np.biz.usrsrv.file.controller;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.util.IOUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月15日 下午3:35:32
 * 创建作者：周颖
 * 文件名称：FileControllerTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({IOUtil.class,MessageUtil.class})
@PowerMockIgnore({"javax.management.*"})
public class FileControllerTest {

    /**被测试类*/
    @InjectMocks
    private FileController fileController;
    
    /**httpRequest*/
    private MockHttpServletRequest httpRequest;
    
    /**httpResponse*/
    private MockHttpServletResponse httpResponse;
    
    
    //private MockServletContext mockServletContext;
    
    /**初始化*/
    @Before
    public void before(){
        httpRequest = new MockHttpServletRequest();
        //mockServletContext = new MockServletContext();
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(IOUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
    }
   
    
    /**
     * 模板下载失败
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月15日 下午4:21:17
     */
    @Test(expected=BizException.class)
    public void testTemplateBizException() throws Exception {
        PowerMockito.doNothing().when(IOUtil.class, "download",anyObject(),anyObject(),anyObject());
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("error");

        fileController.template("accessToken", "fileName", httpRequest, httpResponse);
        PowerMockito.verifyStatic();
        IOUtil.download(anyObject(),anyObject(),anyObject());
        MessageUtil.getMessage(anyString());
    }

   /* @Test
    public void testTemplate() throws Exception {
        //mockServletContext.setContextPath("E:/np/workspace(np)/awifi-np-biz/awifi-np-biz-user/src/main/webapp/");
        when(mockServletContext.getRealPath("file/template/")).thenReturn("E:/np/workspace(np)/awifi-np-biz/awifi-np-biz-user/src/main/webapp/file/template/");
        when(httpRequest.getServletContext()).thenReturn(mockServletContext);
        //httpRequest.setServletPath("E:/np/workspace(np)/awifi-np-biz/awifi-np-biz-user/src/main/webapp/");
        PowerMockito.doNothing().when(IOUtil.class, "download",anyObject(),anyObject(),anyObject());
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("error");

        fileController.template("accessToken", "staticUserTemplate.xls", httpRequest, httpResponse);
        PowerMockito.verifyStatic();
        IOUtil.download(anyObject(),anyObject(),anyObject());
        MessageUtil.getMessage(anyString());
    }*/
}
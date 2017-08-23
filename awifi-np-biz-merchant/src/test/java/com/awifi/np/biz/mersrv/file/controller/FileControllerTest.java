package com.awifi.np.biz.mersrv.file.controller;

import static org.mockito.Matchers.anyObject;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.awifi.np.biz.api.client.dbcenter.portalpolicy.util.PortalPolicyClient;
import com.awifi.np.biz.common.util.CastUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017年6月20日 下午2:20:36
 * 创建作者：许尚敏
 * 文件名称：FileControllerTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({File.class})
public class FileControllerTest {
    /**被测试类*/
    @InjectMocks
    private FileController fileController;
    
    /**请求*/
    private MockHttpServletRequest httpRequest;
    
    /**请求*/
    private MockHttpServletResponse httpResponse;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        httpRequest = new MockHttpServletRequest();
        httpResponse = new MockHttpServletResponse();
    }
    
    /**
     * 模板下载单元测试
     * @author 许尚敏
     * @date 2017年6月20日 下午2:21:22
     */
    @Test(expected=NullPointerException.class)
    public void testTemplate(){
        fileController.template("", "", httpRequest, httpResponse);
        PowerMockito.verifyStatic();
    }

    /**
     * 模板下载单元测试
     * @throws Exception 异常
     * @author 许尚敏
     * @date 2017年6月20日 下午2:21:22
     */
    @Test(expected=NullPointerException.class)
    public void testTemplateException() throws Exception{
        fileController.template("", "merchantTemplate.xls", httpRequest, httpResponse);
        PowerMockito.verifyStatic();
    }
}

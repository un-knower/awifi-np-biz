package com.awifi.np.biz.devsrv.file.controller;

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
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月14日 下午2:35:19
 * 创建作者：亢燕翔
 * 文件名称：FileControllerTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class,JsonUtil.class,RedisUtil.class,SysConfigUtil.class,SessionUtil.class})
@PowerMockIgnore({"javax.management.*"})
public class FileControllerTest {

    /**被测试类*/
    @InjectMocks
    private FileController fileController;
    
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
     * 测试导出文件
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月14日 下午2:42:06
     */
    @Test(expected=BizException.class)
    public void testTemplate() throws Exception{
        String accessToken = "XXX";
        String filename = "hotareaTemplate.xls";
        fileController.template(request, response, accessToken, filename);
    }
    
    
}

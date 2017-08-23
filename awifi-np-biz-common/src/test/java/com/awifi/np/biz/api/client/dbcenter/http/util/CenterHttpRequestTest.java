package com.awifi.np.biz.api.client.dbcenter.http.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.token.util.TokenClient;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月22日 上午10:33:48
 * 创建作者：亢燕翔
 * 文件名称：CenterHttpRequestTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
//@SuppressWarnings("static-access")
@RunWith(PowerMockRunner.class)
@PrepareForTest({SysConfigUtil.class, MessageUtil.class, TokenClient.class})
public class CenterHttpRequestTest {

    /** 被测试类  */
    @InjectMocks
    private CenterHttpRequest centerHttpRequest; 
    
    /**
     * 初始化 
     * @author 亢燕翔  
     * @date 2017年1月12日 上午9:35:31
     */
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(TokenClient.class);
    }
 
    /**
     * 测试get
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月22日 上午11:03:03
     */
    /*@Test(expected = Exception.class)
    public void testSendGetRequest() throws Exception{
        String params = "xxx";
        centerHttpRequest.sendGetRequest("http://baidu.com", params);
    }*/
    
    /**
     * 测试delete
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月22日 上午11:03:03
     */
    /*@Test(expected = Exception.class)
    public void testSendDeleteRequest() throws Exception{
        String params = "xxx";
        centerHttpRequest.sendDeleteRequest("http://baidu.com", params);
    }*/
    
    /**
     * 测试post
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月22日 上午11:03:03
     */
    /*@Test(expected = Exception.class)
    public void testSendPostRequest() throws Exception{
        String params = "xxx";
        centerHttpRequest.sendPostRequest("http://baidu.com", params);
    }*/
    
    /**
     * 测试put
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月22日 上午11:03:03
     */
    @Test
    public void testSendPutRequest() throws Exception{
        
    }
    /*@Test(expected = Exception.class)
    public void testSendPutRequest() throws Exception{
        String params = "xxx";
        centerHttpRequest.sendPutRequest("http://baidu.com", params);
    }*/
}

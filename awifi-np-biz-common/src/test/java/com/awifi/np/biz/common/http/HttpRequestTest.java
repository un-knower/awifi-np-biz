package com.awifi.np.biz.common.http;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月12日 上午9:30:00
 * 创建作者：亢燕翔
 * 文件名称：HttpRequestTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
//@SuppressWarnings("static-access")
public class HttpRequestTest {

    /** 被测试类  */
    @InjectMocks
    private HttpRequest httpRequest; 
    
    /**
     * 初始化 
     * @author 亢燕翔  
     * @date 2017年1月12日 上午9:35:31
     */
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }
    
    /**
     * 测试发送get请求 
     * @author 亢燕翔  
     * @date 2017年1月12日 上午9:35:16
     */
    @Test
    public void testSendGetRequest(){
        //ByteBuffer byteBuffer = httpRequest.sendGetRequest("http://baidu.com", null);
        //Assert.assertNotNull(byteBuffer);
    }
    
    /**
     * 测试发送post请求
     * @author 亢燕翔  
     * @date 2017年1月12日 上午9:40:42
     */
    @Test
    public void testSendPostRequest(){
        //httpRequest.sendPostRequest("http://baidu.com", anyString(), anyString());
    }
    
    /**
     * 测试发送put请求
     * @author 亢燕翔  
     * @date 2017年1月12日 上午9:40:42
     */
    @Test
    public void testSendPutRequest(){
        //httpRequest.sendPutRequest("http://baidu.com", anyString(), anyString());
    }
    
    /**
     * 测试发送delete请求
     * @author 亢燕翔  
     * @date 2017年1月12日 上午9:40:42
     */
    /*@Test(expected=InterfaceException.class)
    public void testSendDeleteRequest(){
        httpRequest.sendDeleteRequest("http://baidu.com", anyString());
    }*/
 
   /**
    * 测试发送delete请求
    * @author 亢燕翔  
    * @date 2017年1月12日 上午9:40:42
    */
    @Test
    public void testSendPostBodyRequest(){
        //httpRequest.sendPostRequest("http://baidu.com", anyString(), "aaa=111&bbb=222");
    }
}

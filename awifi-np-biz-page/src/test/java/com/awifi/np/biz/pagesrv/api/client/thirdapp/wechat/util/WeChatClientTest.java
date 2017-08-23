/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月27日 下午4:36:27
* 创建作者：周颖
* 文件名称：WeChatClientTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.api.client.thirdapp.wechat.util;

import static org.mockito.Matchers.anyString;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.pagesrv.api.client.thirdapp.wechat.service.WeChatApiService;

@RunWith(PowerMockRunner.class)
@PrepareForTest(BeanUtil.class)
public class WeChatClientTest {

    /**被测试类*/
    @InjectMocks
    private WeChatClient weChatClient;
    
    /**mock*/
    @Mock
    private static WeChatApiService weChatApiService;
    
    /**
     * 初始化
     * @author 周颖  
     * @date 2017年3月23日 上午9:11:43
     */
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.when(BeanUtil.getBean(anyString())).thenReturn(weChatApiService);
    }
    
    /***
     * 呼起微信
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年6月27日 下午4:47:10
     */
    @SuppressWarnings("static-access")
    @Test
    public void testCallWechat() throws Exception {
        weChatClient.callWechat("appId", "redisKey", "token", "shopId", "userMac", "devMac", "devId", "ssid", 
                "secretkey", "userPhone", "authType", "forceAttention", "wechatAuthUrl");
    }
}

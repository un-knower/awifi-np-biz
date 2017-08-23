/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月27日 下午5:05:34
* 创建作者：周颖
* 文件名称：ThirdAuthClientTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.api.client.thirdauth.util;

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
import com.awifi.np.biz.pagesrv.api.client.thirdauth.service.ThirdAuthService;

@RunWith(PowerMockRunner.class)
@PrepareForTest(BeanUtil.class)
public class ThirdAuthClientTest {

    /**被测试类*/
    @InjectMocks
    private ThirdAuthClient thirdAuthClient;
    
    /**mock*/
    @Mock
    private static ThirdAuthService thirdAuthService;
    
    /**
     * 初始化
     * @author 周颖  
     * @date 2017年3月23日 上午9:11:43
     */
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.when(BeanUtil.getBean(anyString())).thenReturn(thirdAuthService);
    }
    
    /**
     * 静态用户名认证
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年6月27日 下午5:11:39
     */
    @SuppressWarnings("static-access")
    @Test
    public void testStaticUserAuth() throws Exception {
        thirdAuthClient.staticUserAuth("interfaceUrl", "userName", "password");
    }
}

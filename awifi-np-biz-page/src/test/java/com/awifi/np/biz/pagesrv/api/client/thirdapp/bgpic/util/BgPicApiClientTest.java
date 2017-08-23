/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月27日 下午2:58:15
* 创建作者：周颖
* 文件名称：BgPicApiClientTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.api.client.thirdapp.bgpic.util;

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
import com.awifi.np.biz.pagesrv.api.client.thirdapp.bgpic.service.BgPicApiService;

@RunWith(PowerMockRunner.class)
@PrepareForTest(BeanUtil.class)
public class BgPicApiClientTest {

    /**被测试类*/
    @InjectMocks
    private BgPicApiClient bgPicApiClient;
    
    /**mock*/
    @Mock
    private static BgPicApiService bgPicApiService;
    
    /**
     * 初始化
     * @author 周颖  
     * @date 2017年3月23日 上午9:11:43
     */
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.when(BeanUtil.getBean(anyString())).thenReturn(bgPicApiService);
    }
    
    /**
     * 获取背景图片
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年6月27日 下午3:04:09
     */
    @SuppressWarnings("static-access")
    @Test
    public void testGetBgPic() throws Exception {
        bgPicApiClient.getBgPic("redisKey", "interfaceUrl");
    }
}

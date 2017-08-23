package com.awifi.np.biz.pagesrv.auth.netdef.controller;

import static org.mockito.Matchers.anyObject;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import com.awifi.np.biz.common.redis.util.RedisUtil;


/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017年6月30日 上午11:28:07
 * 创建作者：许尚敏
 * 文件名称：NetDefControllerTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({StringUtils.class,RedisUtil.class})
public class NetDefControllerTest {
    /**被测试类*/
    @InjectMocks
    private NetDefController netDefController;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(StringUtils.class);
        PowerMockito.mockStatic(RedisUtil.class);
    }
    
    /**
     * 防蹭网码校验测试
     * @author 许尚敏
     * @date 2017年6月30日 下午2:05:16
     */
    @Test
    public void testCheck() {
        PowerMockito.when(StringUtils.isBlank(anyObject())).thenReturn(false);
        PowerMockito.when(RedisUtil.get(anyObject())).thenReturn("netDefCode");
        netDefController.check("{\"deviceId\":\"deviceId\",\"netDefCode\":\"netDefCode\"}");
    }
    
    /**
     * 防蹭网码校验测试
     * @throws Exception 
     * @author 许尚敏
     * @date 2017年6月30日 下午2:05:16
     */
    @Test(expected=NullPointerException.class)
    public void testCheckBizException() throws Exception{
        PowerMockito.when(StringUtils.isBlank(anyObject())).thenReturn(true);
        PowerMockito.when(RedisUtil.get(anyObject())).thenReturn("");
        netDefController.check("{\"deviceId\":\"deviceId\",\"netDefCode\":\"netDefCode\"}");
    }
    
    /**
     * 防蹭网码校验测试
     * @throws Exception 
     * @author 许尚敏
     * @date 2017年6月30日 下午2:05:16
     */
    @Test(expected=NullPointerException.class)
    public void testCheckValidException() throws Exception{
        PowerMockito.when(StringUtils.isBlank(anyObject())).thenReturn(false);
        PowerMockito.when(RedisUtil.get(anyObject())).thenReturn("deviceId");
        netDefController.check("{\"deviceId\":\"deviceId\",\"netDefCode\":\"netDefCode\"}");
    }

}

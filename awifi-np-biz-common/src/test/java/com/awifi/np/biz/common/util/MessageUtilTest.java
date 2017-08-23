package com.awifi.np.biz.common.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月23日 上午9:26:56
 * 创建作者：亢燕翔
 * 文件名称：MessageUtilTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings("static-access")
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class, BeanUtil.class})
public class MessageUtilTest {

    /**被测试类*/
    @InjectMocks
    private MessageUtil messageUtil;
    
    /**消息源*/
    @Mock(name="messageSource")
    private ReloadableResourceBundleMessageSource messageSource;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(BeanUtil.class);
    }
    
    /**
     * 测试获取消息字符串形式
     * @author 亢燕翔  
     * @date 2017年3月23日 上午10:24:17
     */
    @Test
    public void testGetMessageString(){
        messageUtil.getMessage("xxx");
    }
    
    /**
     * 测试获取消息object形式
     * @author 亢燕翔  
     * @date 2017年3月23日 上午10:25:31
     */
    @Test
    public void testGetMessageObject(){
        Object obj = "";
        PowerMockito.when(BeanUtil.getBean("messageSource")).thenReturn(messageSource);
        messageUtil.getMessage("xxx", obj);
    }
    
    /**
     * 测试获取消息数组形式
     * @author 亢燕翔  
     * @date 2017年3月23日 上午10:25:36
     */
    @Test
    public void testGetMessageObjectList(){
        Object[] obj = {};
        messageUtil.getMessage("xxx", obj);
    }
    
    
}

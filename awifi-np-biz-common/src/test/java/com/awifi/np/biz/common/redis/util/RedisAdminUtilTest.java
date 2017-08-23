package com.awifi.np.biz.common.redis.util;

import static org.mockito.Matchers.anyString;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.common.redis.command.RedisAdminService;
import com.awifi.np.biz.common.util.BeanUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月24日 上午9:24:15
 * 创建作者：周颖
 * 文件名称：RedisAdminUtilTest.java
 * 版本：  v1.0
 * 功能：redisAdminUtil
 * 修改记录：
 */
@SuppressWarnings("static-access")
@RunWith(PowerMockRunner.class)
@PrepareForTest(BeanUtil.class)
@PowerMockIgnore({"javax.management.*"})
public class RedisAdminUtilTest {

    /**
     * 测试类
     */
    @InjectMocks
    private RedisAdminUtil redisAdminUtil;
    
    /**
     * mock
     */
    @Mock
    private RedisAdminService redisAdminService;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.when(BeanUtil.getBean(anyString())).thenReturn(redisAdminService);
    }
    
    /**
     * get
     * @author 周颖  
     * @date 2017年3月24日 上午9:29:47
     */
    @Test
    public void testSet() {
        redisAdminUtil.set("key", "value", 1);
    }

    /**
     * set
     * @author 周颖  
     * @date 2017年3月24日 上午9:29:56
     */
    @Test
    public void testGet() {
        redisAdminUtil.get("key");
    }

    /**
     * delete
     * @author 周颖  
     * @date 2017年3月24日 上午9:30:05
     */
    @Test
    public void testDelete() {
        redisAdminUtil.delete("key");
    }
}

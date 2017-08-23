package com.awifi.np.biz.common.redis.util;

import static org.mockito.Matchers.anyString;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.common.redis.command.RedisService;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月22日 下午1:46:22
 * 创建作者：亢燕翔
 * 文件名称：RedisUtilTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings("static-access")
@RunWith(PowerMockRunner.class)
@PrepareForTest({SysConfigUtil.class, MessageUtil.class, BeanUtil.class})
public class RedisUtilTest {

    /**被测试类*/
    @InjectMocks
    private RedisUtil redisUtil;
    
    /**redis业务层*/
    @Mock(name = "redisService")
    private RedisService redisService;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.when(BeanUtil.getBean(anyString())).thenReturn(redisService);
    }
    
    /**
     * 获取Redis实例
     * @author 亢燕翔  
     * @date 2017年3月22日 下午1:49:53
     */
    @Test
    public void testGetRedisService(){
        redisUtil.getRedisService();
    }
    
    /**
     * set 操作
     * @author 亢燕翔  
     * @date 2017年3月22日 下午1:54:15
     */
    @Test
    public void testSet(){
        redisUtil.set("key", "value", 60);
    }
    
    /**
     * get 操作
     * @author 亢燕翔  
     * @date 2017年3月22日 下午1:54:11
     */
    @Test
    public void testGet(){
        redisUtil.get("xxx");
    }
    
    /**
     * 获取所有keys
     * @author 亢燕翔  
     * @date 2017年3月22日 下午1:54:02
     */
    @Test
    public void testKeys(){
        redisUtil.keys("xxx");
    }
    
    /**
     * 批量删除key
     * @author 亢燕翔  
     * @date 2017年3月22日 下午1:54:53
     */
    @Test
    public void testDelBatch(){
        redisUtil.delBatch(new HashSet<String>());
    }
    
    /**
     * 批量添加key
     * @author 亢燕翔  
     * @date 2017年3月22日 下午1:55:52
     */
    @Test
    public void testSetBatch(){
        redisUtil.setBatch(new HashMap<String, String>(), 60);
    }
    
    /**
     * 批量获取value
     * @author 亢燕翔  
     * @date 2017年3月22日 下午1:56:33
     */
    @Test
    public void testGetAllWithKeyBatch(){
        redisUtil.getBatch("xxx");
    }
    
    /**
     * 批量Hash键值设置操作
     * @author 亢燕翔  
     * @date 2017年3月22日 下午1:57:22
     */
    @Test
    public void testHmsetBatch(){
        redisUtil.hmsetBatch(new HashMap<String, Map<String,String>>(), 60);
    }
    
    /**
     * 批量Hash键值get操作
     * @author 亢燕翔  
     * @date 2017年3月22日 下午1:57:58
     */
    @Test
    public void testhgetAllBatch(){
        redisUtil.hgetAllBatch("xxx");
    }
    
    /**
     * Hash键值 字段 get操作
     * @author 亢燕翔  
     * @date 2017年3月22日 下午1:58:38
     */
    @Test
    public void testhmget(){
        redisUtil.hmget("xxx", "xxx");
    }
    
    /**
     * Hash键值设置操作
     * @author 亢燕翔  
     * @date 2017年3月22日 下午1:59:37
     */
    @Test
    public void testHmset(){
        redisUtil.hmset("xxx", new HashMap<String, String>(), 60);
    }
    
    /**
     * Hash键值 字段 get操作 -- 单条数据模糊匹配
     * @author 亢燕翔  
     * @date 2017年3月22日 下午2:00:07
     */
    @Test
    public void testHmgetLike(){
        redisUtil.hmgetLike("xxx", "xxx");
    }
    
    /**
     * key是否存在
     * @author 亢燕翔  
     * @date 2017年3月22日 下午2:01:01
     */
    @Test
    public void testExist(){
        redisUtil.exist("xxx");
    }
}

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月6日 下午5:28:24
* 创建作者：方志伟
* 文件名称：CustomerConfigServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.toe.admin.merchant.customerconfig.service.impl;

import static org.mockito.Matchers.anyString;

import java.util.HashMap;
import java.util.Map;

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

import com.awifi.np.biz.api.client.auth.util.AuthClient;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.toe.admin.merchant.customerconfig.dao.CustomerConfigMapper;

@RunWith(PowerMockRunner.class)
@PrepareForTest({JsonUtil.class,RedisUtil.class,Map.class,AuthClient.class})
@PowerMockIgnore({"javax.management.*"})
public class CustomerConfigServiceImplTest {
	/**被测试类*/
    @InjectMocks
    private CustomerConfigServiceImpl customerConfigServiceImpl;
    /**持久层*/
    @Mock(name = "customerConfigMapper")
    private CustomerConfigMapper customerConfigMapper;
    /**初始化*/
    @Before
    public void before(){
    	MockitoAnnotations.initMocks(this);
    	PowerMockito.mockStatic(JsonUtil.class);
    	PowerMockito.mockStatic(RedisUtil.class);
    	PowerMockito.mockStatic(Map.class);
    	PowerMockito.mockStatic(AuthClient.class);
    }
    
    /**
     * 测试通过客户id获取第三方静态用户名认证地址
     * 优先从redis缓存中读取
     * @throws Exception 异常
     * @author 方志伟  
     * @date 2017年6月6日 下午6:52:25
     */
    @Test
    public void testGetStaticUserAuthUrlCache() throws Exception{
    	Map<String, Object> redisMap = new HashMap<String, Object>();
    	PowerMockito.when(RedisUtil.get(anyString())).thenReturn("{'result':'1'}");
    	customerConfigServiceImpl.getStaticUserAuthUrlCache(1L);
    }
    
    /**
     * 测试通过客户id获取第三方静态用户名认证地址
     * @throws Exception 异常
     * @author 方志伟  
     * @date 2017年6月6日 下午7:28:47
     */
    @Test
    public void testGetStaticUserAuthUrl() throws Exception{
    	Map<String, Object> redisMap = new HashMap<String, Object>();
    	customerConfigServiceImpl.getStaticUserAuthUrlCache(1L);
    }
}

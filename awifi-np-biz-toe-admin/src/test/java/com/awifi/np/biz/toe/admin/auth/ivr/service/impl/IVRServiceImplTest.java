/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月6日 下午3:03:06
* 创建作者：方志伟
* 文件名称：IVRServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.toe.admin.auth.ivr.service.impl;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import com.awifi.np.biz.toe.admin.auth.ivr.dao.IVRDao;

@RunWith(PowerMockRunner.class)
@PrepareForTest({JsonUtil.class,RedisUtil.class,Map.class,AuthClient.class,StringUtils.class})
@PowerMockIgnore({"javax.management.*"})
public class IVRServiceImplTest {
    /**被测试类*/
    @InjectMocks
    private IVRServiceImpl ivrServiceImpl;
    /**持久层*/
    @Mock(name = "ivrDao")
    private IVRDao ivrDao;
    /**初始化*/
    @Before
    public void before(){
    	MockitoAnnotations.initMocks(this);
    	PowerMockito.mockStatic(JsonUtil.class);
    	PowerMockito.mockStatic(RedisUtil.class);
    	PowerMockito.mockStatic(Map.class);
    	PowerMockito.mockStatic(AuthClient.class);
    	PowerMockito.mockStatic(StringUtils.class);
    }
    
    /**
     * 测试轮循 查看是否放行成功或放行失败(当result = 1时)
     * @throws Exception 异常
     * @author 方志伟  
     * @date 2017年6月6日 下午3:23:54
     */
    @Test
    public void testPollOk() throws Exception{
    	Map<String, Object> redisMap = new HashMap<String, Object>();
    	PowerMockito.when(RedisUtil.get(anyString())).thenReturn("{'result':'1'}");
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	resultMap.put("result", "1");
    	PowerMockito.when(JsonUtil.fromJson(anyString(),anyObject())).thenReturn(resultMap);
    	ivrServiceImpl.poll("18656878221", redisMap);
    }
    
    /**
     * 测试轮循 查看是否放行成功或放行失败
     * @throws Exception 异常
     * @author 方志伟  
     * @date 2017年6月21日 下午7:57:36
     */
    @Test
    public void testPollOkNull() throws Exception{
        Map<String, Object> redisMap = new HashMap<String, Object>();
        PowerMockito.when(RedisUtil.get(anyString())).thenReturn("{'result':'1'}");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", "1");
        PowerMockito.when(StringUtils.isBlank(anyObject())).thenReturn(true);
        PowerMockito.when(JsonUtil.fromJson(anyString(),anyObject())).thenReturn(resultMap);
        ivrServiceImpl.poll("18656878221", redisMap);
    }
    
    /**
     * 测试轮循 查看是否放行成功或放行失败(当result = 2时)
     * @throws Exception 异常
     * @author 方志伟  
     * @date 2017年6月6日 下午4:29:37
     */
    @Test
    public void testPollOk01() throws Exception{
    	Map<String, Object> redisMap = new HashMap<String, Object>();
    	PowerMockito.when(RedisUtil.get(anyString())).thenReturn("{'result':'1'}");
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	resultMap.put("result", "2");
    	PowerMockito.when(JsonUtil.fromJson(anyString(),anyObject())).thenReturn(resultMap);
    	ivrServiceImpl.poll("18656878221", redisMap);
    }
    
    /**
     * 测试轮循 查看是否放行成功或放行失败(当result = 3时)
     * @throws Exception 异常
     * @author 方志伟  
     * @date 2017年6月6日 下午4:35:15
     */
    @Test
    public void testPollOk02() throws Exception{
    	Map<String, Object> redisMap = new HashMap<String, Object>();
    	PowerMockito.when(RedisUtil.get(anyString())).thenReturn("{'result':'1'}");
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	resultMap.put("result", "3");
    	PowerMockito.when(JsonUtil.fromJson(anyString(),anyObject())).thenReturn(resultMap);
    	ivrServiceImpl.poll("18656878221", redisMap);
    }
    
    /**
     * 测试轮循 查看是否放行成功或放行失败(当result不等于1,2,3时)
     * @throws Exception 异常
     * @author 方志伟  
     * @date 2017年6月6日 下午4:35:15
     */
    @Test
    public void testPollOk03() throws Exception{
    	Map<String, Object> redisMap = new HashMap<String, Object>();
    	PowerMockito.when(RedisUtil.get(anyString())).thenReturn("{'result':'1'}");
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	resultMap.put("result", "4");
    	PowerMockito.when(JsonUtil.fromJson(anyString(),anyObject())).thenReturn(resultMap);
    	ivrServiceImpl.poll("18656878221", redisMap);
    }
    
    /**
     * 测试IVR语音认证-保存参数及日志
     * @throws Exception 异常
     * @author 方志伟  
     * @date 2017年6月6日 下午4:39:43
     */
    @Test
    public void testSaveOk() throws Exception{
    	ivrServiceImpl.save("redisKey", "redisValue", "18656878221", "userMac", 1, 2L, "cascadeLabel");
    }
    
    /**
     * 测试校验短信网关手机号 放行
     * @throws Exception 异常
     * @author 方志伟  
     * @date 2017年6月6日 下午5:06:02
     */
    @Test
    public void testIvr01() throws Exception{ 
    	Map<String, String> redisMap = new HashMap<String, String>();
    	redisMap.put("np_biz_ivr_18656878221", "redisValue");
    	PowerMockito.when(RedisUtil.get(anyString())).thenReturn("{'result':'1'}");
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	resultMap.put("result", "1");
    	PowerMockito.when(JsonUtil.fromJson(anyString(),anyObject())).thenReturn(resultMap);
    	ivrServiceImpl.ivr("18656878221", "publicUserIp", "publicUserPort", "userAgent");
    }
    
    /**
     * 测试校验短信网关手机号 放行
     * @throws Exception 异常
     * @author 方志伟  
     * @date 2017年6月21日 下午8:00:30
     */
    @Test
    public void testIvr02() throws Exception{ 
        Map<String, String> redisMap = new HashMap<String, String>();
        redisMap.put("np_biz_ivr_18656878221", "redisValue");
        PowerMockito.when(StringUtils.isBlank(anyObject())).thenReturn(true);
        PowerMockito.when(RedisUtil.get(anyString())).thenReturn("{'result':'1'}");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", "2");
        resultMap.put("result", "3");
        PowerMockito.when(JsonUtil.fromJson(anyString(),anyObject())).thenReturn(resultMap);
        ivrServiceImpl.ivr("18656878221", "publicUserIp", "publicUserPort", "userAgent");
    }
}

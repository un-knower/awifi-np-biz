package com.awifi.np.biz.devsrv.hotarea.controller;

import static org.mockito.Matchers.anyObject;

import java.util.Map;

import org.junit.Assert;
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

import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.devsrv.hotarea.service.HotareaService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月14日 下午2:44:06
 * 创建作者：亢燕翔
 * 文件名称：HotareaController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class,JsonUtil.class,RedisUtil.class,SysConfigUtil.class,SessionUtil.class})
@PowerMockIgnore({"javax.management.*"})
public class HotareaControllerTest {

    
    /**被测试类*/
    @InjectMocks
    private HotareaController hotareaController;
    
    /**热点服务*/
    @Mock(name = "hotareaService")
    private HotareaService hotareaService;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(RedisUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
    }
 
    /**
     * 测试删除热点
     * @throws Exception 异常 
     * @author 亢燕翔  
     * @date 2017年2月14日 下午2:53:13
     */
    @Test
    public void testDeleteByDevMacs() throws Exception{
        String accessToken = "XXX";
        Map<String, Object> resultMap = hotareaController.deleteByDevMacs(accessToken, anyObject());
        Assert.assertNotNull(resultMap);
    }
    
}

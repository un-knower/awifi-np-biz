package com.awifi.np.biz.devsrv.entity.controller;

import java.util.HashMap;
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
import com.awifi.np.biz.common.security.permission.util.PermissionUtil;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.devsrv.entity.service.EntityService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月14日 下午2:30:27
 * 创建作者：亢燕翔
 * 文件名称：EntityControllerTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class,JsonUtil.class,RedisUtil.class,SysConfigUtil.class,PermissionUtil.class,SessionUtil.class})
@PowerMockIgnore({"javax.management.*"})
public class EntityControllerTest {
    
    /**被测试类*/
    @InjectMocks
    private EntityController entityController;
    
    /**实体设备服务*/
    @Mock(name = "deviceService")
    private EntityService entityService;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(RedisUtil.class);
        PowerMockito.mockStatic(PermissionUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
    }
    
    /**
     * 测试更新设备
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月14日 下午2:33:06
     */
    @Test
    public void testUpdate() throws Exception{
        String accessToken = "XXX";
        String deviceid = "XXX";
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("id", 1);
        dataMap.put("userName", "superadmin");
        dataMap.put("roleIds", "1");
        Map<String, Object> resultMap = entityController.update(accessToken, deviceid, dataMap);
        Assert.assertNotNull(resultMap);
    }
    
}

package com.awifi.np.biz.devsrv.entity.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.api.client.dbcenter.device.entity.util.EntityClient;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.common.util.EncryUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月24日 下午4:00:53
 * 创建作者：亢燕翔
 * 文件名称：EntityServiceImplTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class, SysConfigUtil.class,EncryUtil.class,EntityClient.class,JsonUtil.class,BeanUtil.class,DeviceClient.class })
public class EntityServiceImplTest {

    /** 被测试类 */
    @InjectMocks
    private EntityServiceImpl entityServiceImpl;
    
    /** 初始化 */
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(EncryUtil.class);
        PowerMockito.mockStatic(EntityClient.class);
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.mockStatic(DeviceClient.class);
    }
    
    /**
     * 测试编辑设备
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月24日 下午4:04:06
     */
    @Test
    public void testUpdate() throws Exception{
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("provinceId", 31);
        dataMap.put("cityId", 31);
        dataMap.put("areaId", 31);
        entityServiceImpl.update("xxx",dataMap);
    }
    
}

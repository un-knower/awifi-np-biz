package com.awifi.np.biz.merdevsrv.entity.service.impl;

import static org.mockito.Matchers.anyObject;

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

import com.awifi.np.biz.api.client.dbcenter.device.entity.model.EntityInfo;
import com.awifi.np.biz.api.client.dbcenter.device.entity.util.EntityClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.EncryUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月24日 下午4:49:10
 * 创建作者：亢燕翔
 * 文件名称：EntityServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class, SysConfigUtil.class,EncryUtil.class,EntityClient.class,ValidUtil.class })
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
        PowerMockito.mockStatic(ValidUtil.class);
    }
    
    /**
     * 测试设备监控列表
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月24日 下午5:15:37
     */
    @Test
    public void testGetEntityInfoListByMerId() throws Exception{
        PowerMockito.when(SysConfigUtil.getParamValue(anyObject())).thenReturn("100");
        PowerMockito.when(EntityClient.getEntityInfoCountByMerId(anyObject())).thenReturn(100);
        SessionUser sessionUser = new SessionUser();
        sessionUser.setId(2L);
        sessionUser.setMerchantId(41L);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("deviceId", "deviceId");
        dataMap.put("entityType", 31);
        dataMap.put("devMac", "1C184A0F8DDD");
        dataMap.put("devSwitch", "ON");
        dataMap.put("pageNo", "2");
        dataMap.put("pageSize", "10");
        Page<EntityInfo> page = new Page<EntityInfo>();
        entityServiceImpl.getEntityInfoListByMerId(sessionUser, JsonUtil.toJson(dataMap), page);
    }
    
}

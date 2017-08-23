package com.awifi.np.biz.merdevsrv.hotarea.service.impl;

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

import com.awifi.np.biz.api.client.dbcenter.device.hotarea.model.Hotarea;
import com.awifi.np.biz.api.client.dbcenter.device.hotarea.util.HotareaClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.EncryUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月24日 下午5:19:41
 * 创建作者：亢燕翔
 * 文件名称：HotareaServiceImplTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class, SysConfigUtil.class,EncryUtil.class,HotareaClient.class,ValidUtil.class })
public class HotareaServiceImplTest {

    /** 被测试类 */
    @InjectMocks
    private HotareaServiceImpl hotareaServiceImpl;
    
    /** 初始化 */
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(EncryUtil.class);
        PowerMockito.mockStatic(HotareaClient.class);
        PowerMockito.mockStatic(ValidUtil.class);
    }
    
    /**
     * 测试热点分页列表
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月24日 下午5:25:07
     */
    @Test
    public void testGetListByParam() throws Exception{
        PowerMockito.when(SysConfigUtil.getParamValue(anyObject())).thenReturn("100");
        PowerMockito.when(HotareaClient.getCountByParam(anyObject())).thenReturn(100);
        SessionUser sessionUser = new SessionUser();
        sessionUser.setId(2L);
        Page<Hotarea> page = new Page<Hotarea>();
        page.setPageSize(10);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("deviceId", "deviceId");
        dataMap.put("entityType", 31);
        dataMap.put("devMac", "1C184A0F8DDD");
        dataMap.put("devSwitch", "ON");
        dataMap.put("pageSize", "10");
        dataMap.put("pageNo", "10");
        //hotareaServiceImpl.getListByParam(sessionUser, page, JsonUtil.toJson(dataMap));
    }
    
}

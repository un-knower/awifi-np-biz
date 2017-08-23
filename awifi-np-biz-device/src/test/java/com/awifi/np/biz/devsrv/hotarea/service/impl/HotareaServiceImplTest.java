package com.awifi.np.biz.devsrv.hotarea.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.device.hotarea.util.HotareaClient;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.EncryUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月24日 下午4:08:12
 * 创建作者：亢燕翔
 * 文件名称：HotareaServiceImplTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class, SysConfigUtil.class,EncryUtil.class,HotareaClient.class })
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
    }
    
    /**
     * 测试删除热点
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月24日 下午4:11:53
     */
    @Test
    public void testDeleteByDevMacs() throws Exception{
        hotareaServiceImpl.deleteByDevMacs("31,12,15");
    }
    
}

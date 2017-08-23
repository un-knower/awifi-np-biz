package com.awifi.np.biz.common.system.sysconfig.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.common.system.sysconfig.service.SysConfigService;
import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月22日 下午7:26:54
 * 创建作者：亢燕翔
 * 文件名称：SysConfigUtilTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class, BeanUtil.class})
public class SysConfigUtilTest {

    /**被测试类*/
    @InjectMocks
    private SysConfigUtil sysConfigUtil;
    
    /**系统配置业务层*/
    @Mock(name="sysConfigService")
    private SysConfigService sysConfigService;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.when(BeanUtil.getBean("sysConfigService")).thenReturn(sysConfigService);
    }
    
    /**
     * 通过key获取value
     * @author 亢燕翔  
     * @date 2017年3月22日 下午7:37:57
     */
    @SuppressWarnings("static-access")
    @Test
    public void testGetParamValue(){
        sysConfigUtil.getParamValue("sysConfigService");
    }
    
}

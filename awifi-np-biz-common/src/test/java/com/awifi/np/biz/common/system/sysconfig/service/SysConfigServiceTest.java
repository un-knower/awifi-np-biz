package com.awifi.np.biz.common.system.sysconfig.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.system.sysconfig.dao.SysConfigDao;
import com.awifi.np.biz.common.system.sysconfig.service.impl.SysConfigServiceImpl;
import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月22日 下午7:42:50
 * 创建作者：亢燕翔
 * 文件名称：SysConfigServiceTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class, BeanUtil.class, RedisUtil.class})
public class SysConfigServiceTest {
    
    /**测试类*/
    @InjectMocks
    private SysConfigServiceImpl sysConfigService;
    
    /**系统参数持久层*/
    @Mock(name="sysConfigDao")
    private SysConfigDao sysConfigDao;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.mockStatic(RedisUtil.class);
    }
 
    /**
     * 测试通过key获取value
     * @author 亢燕翔  
     * @date 2017年3月22日 下午7:55:54
     */
    @Test
    public void testGetParamValueNull(){
        sysConfigService.getParamValue("xxx");
    }
    
}

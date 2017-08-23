package com.awifi.np.biz.api.client.dbcenter.device.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月20日 上午10:45:53
 * 创建作者：亢燕翔
 * 文件名称：DevUtilTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings("static-access")
@RunWith(PowerMockRunner.class)
@PrepareForTest({CenterHttpRequest.class,SysConfigUtil.class,JsonUtil.class})
public class DevUtilTest {
    
    /**被测试类*/
    @InjectMocks
    private DevUtil devUtil;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(CenterHttpRequest.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(JsonUtil.class);   
    }
    
    /**
     * 异常测试
     * @author 亢燕翔  
     * @date 2017年3月22日 下午4:47:53
     */
    @Test(expected=Exception.class)
    public void testIsFatAPException(){
        devUtil.isFatAP("13");
    }
    
    /**
     * 测试entityType为空
     * @author 亢燕翔  
     * @date 2017年3月22日 下午4:47:18
     */
    @Test(expected=Exception.class)
    public void testIsFatAPNull(){
        devUtil.isFatAP(null);
    }
    
    /**
     * 测试正常情况
     * @author 亢燕翔  
     * @date 2017年3月22日 下午4:47:38
     */
    @Test
    public void testIsFatAP(){
        devUtil.isFatAP("31");
    }
}

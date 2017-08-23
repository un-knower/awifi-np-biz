/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月27日 上午10:13:28
* 创建作者：周颖
* 文件名称：SmsConfigUtilTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.common.system.smsconfig.util;

import static org.mockito.Matchers.anyString;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.common.system.smsconfig.service.SmsConfigService;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.BeanUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SysConfigUtil.class,BeanUtil.class})
public class SmsConfigUtilTest {

    /**被测试类*/
    @InjectMocks
    private SmsConfigUtil smsConfigUtil;
    
    /**mock*/
    @Mock(name="smsConfigService")
    private SmsConfigService smsConfigService;
    
    /**
     * 初始化
     * @author 周颖  
     * @date 2017年6月27日 上午10:26:42
     */
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.when(BeanUtil.getBean(anyString())).thenReturn(smsConfigService);
    }
    
    /**
     * 短信配置
     * @author 周颖  
     * @date 2017年6月27日 上午10:26:49
     */
    @SuppressWarnings("static-access")
    @Test
    public void testGetSmsConfigWithDefault() {
        PowerMockito.when(SysConfigUtil.getParamValue("sms_authcode_default_length")).thenReturn("4");
        PowerMockito.when(SysConfigUtil.getParamValue("sms_authcode_default_content")).thenReturn("短信内容");
        smsConfigUtil.getSmsConfigWithDefault(1L);
        PowerMockito.verifyStatic();
        SysConfigUtil.getParamValue("sms_authcode_default_length");
        SysConfigUtil.getParamValue("sms_authcode_default_content");
    }
}

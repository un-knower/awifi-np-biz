/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月20日 上午10:25:45
* 创建作者：方志伟
* 文件名称：SmsServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.toe.admin.system.sms.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.toe.admin.system.sms.dao.SmsDao;

@RunWith(PowerMockRunner.class)
public class SmsServiceImplTest {
    /**被测试类*/
    @InjectMocks
    private SmsServiceImpl smsServiceImpl;
    /**持久层*/
    @Mock(name = "smsDao")
    private SmsDao smsDao;
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }
    
    /**
     * 测试保存短信记录
     * @author 方志伟  
     * @date 2017年6月20日 上午10:28:46
     */
    @Test
    public void testAdd(){
        smsServiceImpl.add(2L, "18656878779", "awifi");
    }
}

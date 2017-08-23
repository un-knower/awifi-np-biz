/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月20日 上午10:12:10
* 创建作者：方志伟
* 文件名称：ApplicationServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.toe.admin.thirdapp.application.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.toe.admin.thirdapp.application.dao.ApplicationDao;

@RunWith(PowerMockRunner.class)
public class ApplicationServiceImplTest {
    /**被测试类*/
    @InjectMocks
    private ApplicationServiceImpl applicationServiceImpl;
    /**持久层*/
    @Mock(name = "ApplicationDao")
    private ApplicationDao applicationDao;
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }
    
    /**
     * 测试通过appid获取应用表信息
     * @author 方志伟  
     * @date 2017年6月20日 上午10:18:01
     */
    @Test
    public void testGetByAppid(){
        applicationServiceImpl.getByAppid("awwasw2111aa");
    }
}

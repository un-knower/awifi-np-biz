/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月6日 下午7:51:24
* 创建作者：方志伟
* 文件名称：CustomerConfigUtilTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.toe.admin.merchant.customerconfig.util;

import static org.mockito.Matchers.anyObject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.toe.admin.merchant.customerconfig.service.CustomerConfigService;

@SuppressWarnings("static-access")	
@RunWith(PowerMockRunner.class)
@PrepareForTest({BeanUtil.class})
public class CustomerConfigUtilTest {
	/**被测试类*/
    @InjectMocks
    private CustomerConfigUtil customerConfigUtil;
    
    /**业务层*/
    @Mock(name = "customerConfigService")
    private CustomerConfigService customerConfigService;
    
    /** 初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(BeanUtil.class);
    }
    /**
     * 通过客户id获取第三方静态用户名认证地址
     * @author 方志伟  
     * @date 2017年6月6日 下午8:13:08
     */
    @Test
    public void testGetStaticUserAuthUrlCache(){
    	PowerMockito.when(BeanUtil.getBean(anyObject())).thenReturn(customerConfigService);
    	customerConfigUtil.getStaticUserAuthUrlCache(anyObject());
    }
    
}

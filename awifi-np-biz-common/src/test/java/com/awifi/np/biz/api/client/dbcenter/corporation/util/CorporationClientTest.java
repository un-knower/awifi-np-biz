/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月17日 上午11:38:16
* 创建作者：范涌涛
* 文件名称：CorporationClientTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.corporation.util;

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

import com.awifi.np.biz.api.client.dbcenter.corporation.service.CorporationApiService;
import com.awifi.np.biz.api.client.dbcenter.industry.util.IndustryClient;
import com.awifi.np.biz.api.client.dbcenter.location.util.LocationClient;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.BeanUtil;

@SuppressWarnings("static-access")
@RunWith(PowerMockRunner.class)
@PrepareForTest({BeanUtil.class})
public class CorporationClientTest {
    
	/**被测试类*/
    @InjectMocks
    private CorporationClient corporationClient;
    
    @Mock(name = "corporationApiService")
    private CorporationApiService coporationApiSerice;
    
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.when(BeanUtil.getBean(anyString())).thenReturn(coporationApiSerice);
    }
    
    @Test
    public void testQueryListByParam() throws Exception{
    
    	corporationClient.queryListByParam(null);
    
    }
    
    @Test
    public void testQueryCountByParam() throws Exception{
    	corporationClient.queryCountByParam(null);
    }
    

    
}

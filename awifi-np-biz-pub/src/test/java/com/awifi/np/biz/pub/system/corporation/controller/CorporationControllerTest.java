/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月13日 下午2:58:17
* 创建作者：范涌涛
* 文件名称：CorporationControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.pub.system.corporation.controller;


import static org.mockito.Matchers.anyObject;


import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import com.awifi.np.biz.api.client.dbcenter.corporation.util.CorporationClient;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.pub.system.corperation.controller.CorpController;
import com.awifi.np.biz.pub.system.corperation.service.CorpService;






@SuppressWarnings("rawtypes")
@RunWith(PowerMockRunner.class)
@PrepareForTest({SysConfigUtil.class,CorporationClient.class,JsonUtil.class,CastUtil.class})
@PowerMockIgnore({"javax.management.*"})
public class CorporationControllerTest {

    /**被测试类*/
    @InjectMocks
    private CorpController corpController;

    /**厂商服务层*/
    @Mock(name = "corpService")
    private CorpService corpService;
    
//    /**httpRequest*/
//    private MockHttpServletRequest httpRequest;
    /**初始化**/
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
//        httpRequest = new MockHttpServletRequest();
        PowerMockito.mockStatic(CorporationClient.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(CastUtil.class);
    }

    /**
     * 测试分页查询方法
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月18日 上午10:26:50
     */
    @Test
    public void testQueryListByParam() throws Exception {
        System.out.println("junit test:Corporation.queryListByParam()");
        
        Mockito.doNothing().when(corpService).queryListByParam(anyObject(), anyObject());
        Map result  = corpController.queryCorpList("accessToken", "{\"pageSize\":10,\"pageNo\":1,\"corpName\":\"h3c\"}");
        Assert.assertNotNull(result);
    }
}

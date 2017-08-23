/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月14日 下午5:59:22
* 创建作者：范涌涛
* 文件名称：CorpServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.pub.system.corporation.service.impl;

//import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;


import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
//import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.corporation.model.Corporation;
import com.awifi.np.biz.api.client.dbcenter.corporation.util.CorporationClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.pub.system.corperation.service.impl.CorpServiceImpl;


@SuppressWarnings({"rawtypes","unchecked"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({SysConfigUtil.class,CorporationClient.class,JsonUtil.class,CastUtil.class})
@PowerMockIgnore({"javax.management.*"})
public class CorpServiceImplTest {

    /**被测试类**/
    @InjectMocks
    private CorpServiceImpl corpService;


    /**初始化**/
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
//      httpRequest = new MockHttpServletRequest();
        PowerMockito.mockStatic(CorporationClient.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(CastUtil.class);
    }
    
    /**
     * 分页查询厂商测试方法
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月18日 上午10:22:56
     */
    @Test
    public void testQueryListByParam() throws Exception{
        Page page = new Page<Corporation>();
        Map reqMap= new HashMap<String,Integer>();
        reqMap.put("pageSize", 10);
        reqMap.put("pageNo", 1);
        PowerMockito.when(CorporationClient.queryCountByParam(anyObject())).thenReturn(1);
        PowerMockito.when(CorporationClient.queryListByParam(anyObject())).thenReturn(null);
        PowerMockito.when(JsonUtil.fromJson(anyString(), anyObject())).thenReturn(reqMap);
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(1);
        corpService.queryListByParam("{\"pageSize\":10,\"pageNo\":1,\"corpName\":\"h3c\"}", page);
        System.out.println(page);
        Assert.assertNotNull(page);
    }
    

}

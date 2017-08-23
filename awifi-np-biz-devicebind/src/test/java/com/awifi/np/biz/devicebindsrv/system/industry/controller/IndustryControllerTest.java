/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:Jun 22, 2017 5:20:35 PM
* 创建作者：季振宇
* 文件名称：IndustryControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devicebindsrv.system.industry.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.devicebindsrv.system.industry.service.IndustryService;

@RunWith(PowerMockRunner.class)
@PrepareForTest({})
public class IndustryControllerTest {

    /**被测试类*/
    @InjectMocks
    private IndustryController industryController;
    
    /**行业服务层*/
    @Mock(name = "industryService")
    private IndustryService industryService;
    
    /**
     * 初始化
     * @author 季振宇  
     * @date Jun 21, 2017 3:01:29 PM
     */
    @Before
    public void before () {
        MockitoAnnotations.initMocks(this);
    }
    
    /**
     * 测试获取行业列表
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 22, 2017 5:22:45 PM
     */
    @SuppressWarnings("rawtypes")
    @Test
    public void testGetListByParam() throws Exception {
        List<Map<String,String>> industryList = new ArrayList<>();
        PowerMockito.when(industryService.getListByParam(Mockito.anyString())).thenReturn(industryList);
        
        Map map = industryController.getListByParam("parentCode");
        Assert.assertNotNull(map);
        
    }

}

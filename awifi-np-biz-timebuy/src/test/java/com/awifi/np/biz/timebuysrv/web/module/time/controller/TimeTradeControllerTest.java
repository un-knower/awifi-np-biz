/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月25日 上午11:02:45
* 创建作者：张智威
* 文件名称：TimePackageControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.web.module.time.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.timebuysrv.merchant.service.MerchantExtendsService;
import com.awifi.np.biz.timebuysrv.third.access.service.AccessAuthService;
import com.awifi.np.biz.timebuysrv.util.ExcelUtil;
import com.awifi.np.biz.timebuysrv.web.module.time.biz.TimeTradeBiz;
import com.awifi.np.biz.timebuysrv.web.module.time.service.TimeBuyService;
import com.awifi.np.biz.timebuysrv.web.module.time.service.TimePackageService;
@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class,RedisUtil.class,DeviceClient.class})
@PowerMockIgnore({"javax.management.*"})
public class TimeTradeControllerTest {
    /**
     * 被测试类
     */
    @InjectMocks
    private TimeTradeController timeController;
    
    /**
     * MerchantNewsService
     */
    @Mock
    private TimePackageService timePackageService;
    @Mock
    private TimeBuyService timeBuyService;
    
    @Mock
    private MerchantExtendsService merchantExtendsService;
   
    @Mock
    private AccessAuthService accessAuthService;
    
    /**
     * request
     */
    private MockHttpServletRequest request;
    TimeTradeBiz timeChangeBiz;
    /**
     * @throws java.lang.Exception
     * @author  张智威
     * @date 2017-4-25 11:05:30
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        request = new MockHttpServletRequest();
        PowerMockito.mockStatic(MessageUtil.class);
     
        timeController = new TimeTradeController();
        
        timeChangeBiz=new  TimeTradeBiz();
        
        timeController.setTimeChangeBiz(timeChangeBiz);
        
        PowerMockito.mockStatic(RedisUtil.class);
        PowerMockito.when(RedisUtil.get(Mockito.anyString())).thenReturn("ok");
    }

    
    /**
     * 
     * @throws Exception
     * @author 张智威  
     * @date 2017年4月25日 上午11:05:42
     */
    @After
    public void tearDown() throws Exception {
        
            
    }
    
    @Test
    public void testGetInfo() throws Exception {
        
    }
  /*  @Test
    public void testGetInfo() throws Exception {

        
        ByteArrayOutputStream   baos=new   ByteArrayOutputStream();
       
        
        List<Map<String,String>> dataList =new ArrayList<>();

        Map<String,String> map =new HashMap<String ,String>();
        map.put("a","1");

        dataList.add(map);
        LinkedHashMap titleMap =new LinkedHashMap();
        titleMap.put("a","你好");
        try {
            ExcelUtil.getExcelBookFromMap(dataList,titleMap).write(baos);
            ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
            org.springframework.mock.web.MockMultipartHttpServletRequest a;
           MultipartFile file123 = new MockMultipartFile("file","1.gif","image/jpeg",swapStream);
            MockMultipartHttpServletRequest request123 = new MockMultipartHttpServletRequest() ;
            request123.addFile(file123);
            request123.addParameter("days", "1");
            timeController.uploadSubmit(file123, 123l, request123);
        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }*/
}




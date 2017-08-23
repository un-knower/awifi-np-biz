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

import static org.mockito.Matchers.anyObject;

import java.util.HashMap;
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

import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.timebuysrv.web.core.Constants;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionDTO;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionUser;
import com.awifi.np.biz.timebuysrv.web.module.time.model.TimePackage;
import com.awifi.np.biz.timebuysrv.web.module.time.model.UserTimeInfo;
import com.awifi.np.biz.timebuysrv.web.module.time.service.TimeBuyService;
import com.awifi.np.biz.timebuysrv.web.module.time.service.TimePackageService;
import com.awifi.np.biz.timebuysrv.web.module.time.service.WifiService;
@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class,RedisUtil.class})
@PowerMockIgnore({"javax.management.*"})
public class TimePackageControllerTest {
    /**
     * 被测试类
     */
    @InjectMocks
    private TimePackageController timePackageController;
    
    /**
     * MerchantNewsService
     */
    @Mock
    private TimePackageService timePackageService;
    @Mock
    private TimeBuyService timeBuyService;
   
    @Mock
    private WifiService wifiService;
    
    /**
     * request
     */
    private MockHttpServletRequest request;

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
        timePackageController = new TimePackageController();
        timePackageService = Mockito.mock(TimePackageService.class);
        timeBuyService =  Mockito.mock(TimeBuyService.class);
        timePackageController.setTimePackageService(timePackageService);
        timePackageController.setTimeBuyService(timeBuyService);
        timePackageController.setWifiService(wifiService);
         sessionDTO =new SessionDTO();
        Device device =new Device();
        device.setMerchantId(123123123l);
        SessionUser sessionUser =new SessionUser();
        sessionUser.setId(720l);
        sessionDTO.setMerchant(device);
        sessionDTO.setSessionUser(sessionUser);
        UserTimeInfo  userTimeInfo =new UserTimeInfo();
        userTimeInfo.setCanGetFreePkg(true);
        sessionDTO.setTimeInfo(userTimeInfo);
        request.getSession().setAttribute(Constants.SESSION_DTO, sessionDTO);
        //request.setParameter("hello", "hello");
        
        PowerMockito.mockStatic(RedisUtil.class);
        PowerMockito.when(RedisUtil.get(Mockito.anyString())).thenReturn("ok");
    }

    SessionDTO sessionDTO;
    /**
     * 
     * @throws Exception
     * @author 张智威  
     * @date 2017年4月25日 上午11:05:42
     */
    @After
    public void tearDown() throws Exception {
        
            
    }

    /**
     * Test method for {@link com.awifi.np.biz.timebuysrv.merchant.controller.MerchantNewsController#add(javax.servlet.http.HttpServletRequest)}.
     */
    @Test
    public void testGetFreepackage() throws Exception {
        /*request.setAttribute(Constants.SESSION_DTO, sessionDTO);
        SessionDTO sessionDTO = (SessionDTO)request.getAttribute(Constants.SESSION_DTO);*/
        Mockito.when(timeBuyService.getFreePkgAndUpdateTime(Mockito.anyLong(),Mockito.anyLong())).thenReturn(new HashMap());
        timePackageController.getFreePackage(request);
        Mockito.when(wifiService.accessAccountAuth4MD5(Mockito.any(SessionDTO.class),Mockito.anyBoolean())).thenReturn(null);
      
       // wifiService.accessAccountAuth4MD5(sessionDTO);
    }
    
    @Test
    public void testSave() throws Exception {
       
        /*request.setAttribute(Constants.SESSION_DTO, sessionDTO);
        SessionDTO sessionDTO = (SessionDTO)request.getAttribute(Constants.SESSION_DTO);*/
        Map<String,Object> bodyParam = new HashMap<String, Object>();
        bodyParam.put("merchantId", "720");
        bodyParam.put("hours", "1");
//        request.setParameter("merchantId", "720");
//        request.setParameter("day", "1");
        Mockito.when(timePackageService.queryFreePkgByMerId(Mockito.anyLong())).thenReturn(new TimePackage());
        
        Mockito.when(timePackageService.add(Mockito.any(TimePackage.class))).thenReturn(1);
        Mockito.when(timePackageService.update(Mockito.any(TimePackage.class))).thenReturn(1);


        timePackageController.saveMerchantFreePackage(bodyParam, request);
        
        Mockito.when(timePackageService.queryFreePkgByMerId(Mockito.anyLong())).thenReturn(null);
        timePackageController.saveMerchantFreePackage(bodyParam, request);
    }
}




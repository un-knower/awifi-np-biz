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

import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;
import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.timebuysrv.merchant.service.MerchantExtendsService;
import com.awifi.np.biz.timebuysrv.third.access.service.AccessAuthService;
import com.awifi.np.biz.timebuysrv.util.ExcelUtil;
import com.awifi.np.biz.timebuysrv.web.core.Constants;
import com.awifi.np.biz.timebuysrv.web.module.time.model.PortalParam;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionDTO;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionUser;
import com.awifi.np.biz.timebuysrv.web.module.time.model.TimePackage;
import com.awifi.np.biz.timebuysrv.web.module.time.model.UserTimeInfo;
import com.awifi.np.biz.timebuysrv.web.module.time.service.TimeBuyService;
import com.awifi.np.biz.timebuysrv.web.module.time.service.TimePackageService;
@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class,RedisUtil.class,DeviceClient.class})
@PowerMockIgnore({"javax.management.*"})
public class TimeControllerTest {
    /**
     * 被测试类
     */
    @InjectMocks
    private TimeController timeController;
    
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
        PowerMockito.mockStatic(DeviceClient.class);
        timeController = new TimeController();
        timePackageService = Mockito.mock(TimePackageService.class);
        timeBuyService =  Mockito.mock(TimeBuyService.class);
        merchantExtendsService = Mockito.mock(MerchantExtendsService.class);
        timeController.setTimeBuyService(timeBuyService);
        //timeController.setMerchantExtendsService(merchantExtendsService);
        //timeController.setAccessAuthService(accessAuthService);
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
    public void testPostindex() throws Exception {
//        Mockito.when(timeBuyService.getFreePkgAndUpdateTime(Mockito.anyLong(),Mockito.anyLong())).thenReturn(new HashMap());
//       
//        Mockito.when(accessAuthService.accessAccountAuth4MD5(Mockito.any())).thenReturn(null);
        PortalParam portalParam = new PortalParam();
        
        PowerMockito.mockStatic(DeviceClient.class);
        PowerMockito.when(DeviceClient.getByDevId(Mockito.anyString())).thenReturn(new Device());
        
        portalParam.setDeviceId("FAT_123123123123asdfasdf");
        portalParam.setLogId("123");
        portalParam.setDeviceId("FAT_AP_201506022fd30ebd");
        portalParam.setMobilePhone("13958173965");
        portalParam.setUserMac("123456123456");
        portalParam.setUserIP("192.168.1.15");
        portalParam.setGwAddress("192.168.1.15");
        portalParam.setGwPort("80");
        portalParam.setNasName("nas");
        portalParam.setUserType("NEW_USER");//EXEMPT_AUTH_USER
        portalParam.setToken("123");
        portalParam.setUrl("www.163.com");
        timeController.index(portalParam,request);
    }
    
   
}




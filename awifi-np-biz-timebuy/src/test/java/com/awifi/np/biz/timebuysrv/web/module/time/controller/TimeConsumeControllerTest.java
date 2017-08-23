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

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;
import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.timebuysrv.third.access.service.AccessAuthService;
import com.awifi.np.biz.timebuysrv.web.core.Constants;
import com.awifi.np.biz.timebuysrv.web.module.time.model.PortalParam;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionDTO;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionUser;
import com.awifi.np.biz.timebuysrv.web.module.time.model.TimePackage;
import com.awifi.np.biz.timebuysrv.web.module.time.model.UserConsume;
import com.awifi.np.biz.timebuysrv.web.module.time.model.UserTimeInfo;
import com.awifi.np.biz.timebuysrv.web.module.time.service.TimeBuyService;
import com.awifi.np.biz.timebuysrv.web.module.time.service.TimePackageService;
import com.awifi.np.biz.timebuysrv.web.module.time.service.UserConsumeService;
@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class,RedisUtil.class,DeviceClient.class})
@PowerMockIgnore({"javax.management.*"})
public class TimeConsumeControllerTest {
    /**
     * 被测试类
     */
    @InjectMocks
    private TimeConsumeController timeConsumeController;
    
    /**
     * MerchantNewsService
     */
    @Mock
    private UserConsumeService userConsumeService;
 
    
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
        timeConsumeController = new TimeConsumeController();
        userConsumeService = Mockito.mock(UserConsumeService.class);
//        timeBuyService =  Mockito.mock(TimeBuyService.class);
//        timeConsumeController.setTimeBuyService(timeBuyService);
//        timeConsumeController.setAccessAuthService(accessAuthService);
        timeConsumeController.setUserConsumeService(userConsumeService);
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
    public void testGetList() throws Exception {
//        Mockito.when(timeBuyService.getFreePkgAndUpdateTime(Mockito.anyLong(),Mockito.anyLong())).thenReturn(new HashMap());
//       
//        Mockito.when(accessAuthService.accessAccountAuth4MD5(Mockito.any())).thenReturn(null);
        
        PowerMockito.mockStatic(DeviceClient.class);
        PowerMockito.when(DeviceClient.getByDevId(Mockito.anyString())).thenReturn(new Device());
        
        
        PowerMockito.when(userConsumeService.queryCountByParam(Mockito.anyMap())).thenReturn(1);
        
        List<UserConsume> userConsumes =new ArrayList<UserConsume>();
        PowerMockito.when(userConsumeService.queryListByParam(Mockito.any(UserConsume.class),Mockito.anyInt(),Mockito.anyInt())).thenReturn(userConsumes);
        request.setParameter("pageSize", "1");
        request.setParameter("pageNo", "1");
        Map<String,Object> result = timeConsumeController.getList(request);
        
        assertEquals ((String)result.get("code"),"0");
    }
}




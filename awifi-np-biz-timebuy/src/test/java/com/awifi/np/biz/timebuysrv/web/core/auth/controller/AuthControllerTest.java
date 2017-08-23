/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月18日 下午4:47:17
* 创建作者：尤小平
* 文件名称：AuthControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.web.core.auth.controller;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;

import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;
import com.awifi.np.biz.timebuysrv.third.access.bean.AuthResult;
import com.awifi.np.biz.timebuysrv.web.core.Constants;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionDTO;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionUser;
import com.awifi.np.biz.timebuysrv.web.module.time.model.UserTimeInfo;
import com.awifi.np.biz.timebuysrv.web.module.time.service.TimeBuyService;
import com.awifi.np.biz.timebuysrv.web.module.time.service.WifiService;

public class AuthControllerTest {
    /**
     * 被测试类
     */
    @InjectMocks
    private AuthController authController;

    /**
     * AccessAuthService
     */
    @Mock
    private WifiService mockWifiService;

    /**
     * TimeBuyService
     */
    @Mock
    private TimeBuyService mockUserWifiTime;

    /**
     * request
     */
    private MockHttpServletRequest request;
    
    /**
     * setUp.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年4月18日 下午5:22:02
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        request = new MockHttpServletRequest();
        
        
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
        
    }
    SessionDTO sessionDTO;
    /**
     * tearDown.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年4月18日 下午5:22:23
     */
    @After
    public void tearDown() throws Exception {
        authController = null;
        request = null;
    }

    /**
     * 测试网络正式放通.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年4月18日 下午5:22:32
     */
    @SuppressWarnings("rawtypes")
    @Test
    public void testAuth() throws Exception {
        request.setParameter("deviceId", "abc");
        request.setParameter("merchantId", "11");
        request.setParameter("terMac", "mobile mac");
        request.setParameter("apMac", "fff");
        request.setParameter("callback", "function()");
        request.setParameter("username", "18969101234");
        request.setParameter("paytype", "0");

        Map<String, Object> result = new HashMap<String, Object>();
        Mockito.when(mockUserWifiTime.isVipUser(any(String.class))).thenReturn(true);
        authController.setUserWifiTime(mockUserWifiTime);

        Mockito.when(mockWifiService.accessAccountAuth4MD5(any(SessionDTO.class),Mockito.anyBoolean())).thenReturn(result);
        authController.setWifiService(mockWifiService);
        SessionDTO dto =new SessionDTO();
        dto.setAuthResult(new AuthResult());
        request.getSession().setAttribute(Constants.SESSION_DTO, dto);
        Map actual = authController.auth(request);
        
        assertNotNull(actual);
       // Mockito.verify(mockUserWifiTime).isVipUser(any(String.class));
        Mockito.verify(mockWifiService).accessAccountAuth4MD5(any(SessionDTO.class),Mockito.anyBoolean());
    }

    /**
     * 测试临时放通成功.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年4月21日 下午4:40:49
     */
    @SuppressWarnings("rawtypes")
    @Test
    public void testAuthTemporary() throws Exception {
        authController = new AuthController() {
            @Override
            protected String getTimesFromRedis(String key) {
                return "2";
            }
        };

        request.getSession().setAttribute("SessionDeviceId", "abc");
        request.getSession().setAttribute("SessionTerMac", "mobile mac");
        request.getSession().setAttribute("SessionApMac", "fff");
        request.getSession().setAttribute("SessionMerchantId", 11L);

        request.setParameter("deviceId", "abc");
        request.setParameter("terMac", "mobile mac");
        request.setParameter("apMac", "fff");
        request.setParameter("callback", "function()");
        request.setParameter("username", "18969101234");
        request.setParameter("paytype", "1");

        Map<String, Object> result = new HashMap<String, Object>();
        Mockito.when(mockUserWifiTime.isVipUser(any(String.class))).thenReturn(true);
        authController.setUserWifiTime(mockUserWifiTime);

        Mockito.when(mockWifiService.accessAccountAuth4MD5(any(SessionDTO.class),Mockito.anyBoolean())).thenReturn(result);
        
        SessionDTO dto =new SessionDTO();
        dto.setAuthResult(new AuthResult());
        request.getSession().setAttribute(Constants.SESSION_DTO, dto);
        authController.setWifiService(mockWifiService);

        Map actual = authController.auth(request);

        assertNotNull(actual);
      //  Mockito.verify(mockUserWifiTime).isVipUser(any(String.class));
        Mockito.verify(mockWifiService).accessAccountAuth4MD5(any(SessionDTO.class),Mockito.anyBoolean());
    }

    /**
     * 测试临时放通失败的情况：已超过当日认证次数.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年4月21日 下午4:40:49
     */
    @SuppressWarnings("rawtypes")
    @Test
    public void testAuthTemporaryFail() throws Exception {
        authController = new AuthController() {
            @Override
            protected String getTimesFromRedis(String key) {
                return "3";
            }
        };

        request.setParameter("deviceId", "abc");
        request.setParameter("merchantId", "11");
        request.setParameter("terMac", "mobile mac");
        request.setParameter("apMac", "fff");
        request.setParameter("callback", "function()");
        request.setParameter("username", "18969101234");
        request.setParameter("paytype", "1");

        Mockito.when(mockUserWifiTime.isVipUser(any(String.class))).thenReturn(true);
        authController.setUserWifiTime(mockUserWifiTime);

        Map actual = authController.auth(request);

        assertNotNull(actual);
        //Mockito.verify(mockUserWifiTime).isVipUser(any(String.class));
    }
}

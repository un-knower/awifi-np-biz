/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月9日 上午10:13:40
* 创建作者：张智威
* 文件名称：AccessAuthServiceTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.third.auth.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

import java.util.HashMap;
import java.util.Map;

import com.awifi.np.biz.timebuysrv.web.module.time.model.WifiRecord;
import com.awifi.np.biz.timebuysrv.web.module.time.service.WifiRecordService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;

import com.awifi.np.biz.common.exception.InterfaceException;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.timebuysrv.third.access.bean.AuthLoginParam;
import com.awifi.np.biz.timebuysrv.third.access.bean.AuthResult;
import com.awifi.np.biz.timebuysrv.third.access.service.AccessAuthService;
import com.awifi.np.biz.timebuysrv.util.MD5;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SysConfigUtil.class})
public class AccessAuthServiceTest {
    /**
     * AccessAuthService
     */
    @Autowired
    private AccessAuthService accessAuthService;

    /**
     * WifiRecordService
     */
    private WifiRecordService wifiRecordService;

    /**
     * setUp.
     * 
     * @throws Exception
     *             异常
     * @author 尤小平
     * @date 2017年5月16日 下午8:36:20
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(SysConfigUtil.class);
        accessAuthService = new AccessAuthService();
        wifiRecordService = Mockito.mock(WifiRecordService.class);
        accessAuthService.setWifiRecordService(wifiRecordService);
    }

    /**
     * 测试接入认证成功的情况.
     * 
     * @throws Exception
     *             异常
     * @author 尤小平
     * @date 2017年5月16日 下午8:35:37
     */
    @Test
    public void testAuthLoginSuccess() throws Exception {
        String httpresult = "{\"resultCode\":\"0\",\"data\":\"testdata\",\"message\":\"testmessage\"}";
        accessAuthService = new AccessAuthService() {
            @Override
            protected String sendAuthLoginGet(AuthLoginParam authLoginParam, String param) {
                return httpresult;
            }
        };

        AuthLoginParam authLoginParam = new AuthLoginParam();
        authLoginParam.setLogId("123123123");
        authLoginParam.setDeviceId("FAT_AP_201506022fd30ebd-835f-4443-be04-700ac4257917");
        authLoginParam.setUsermac("4C8D79844D3E");// 4C8D79844D3E
        authLoginParam.setPlatform("msp");
        authLoginParam.setAuthType("account");
        authLoginParam.setPublicuserip("192.168.1.12");
        authLoginParam.setPublicuserport("80");
        authLoginParam.setUsername("13958173965");
        authLoginParam.setPassword(MD5.md5("123456"));
        authLoginParam.setUserId(10L);
        authLoginParam.setMerchantId(2L);

        Mockito.when(wifiRecordService.save(any(WifiRecord.class))).thenReturn(1L);
        accessAuthService.setWifiRecordService(wifiRecordService);

        AuthResult authResult = accessAuthService.authLogin(authLoginParam);

        Assert.assertEquals("testdata", authResult.getData());
        Mockito.verify(wifiRecordService).save(any(WifiRecord.class));
    }

    /**
     * 测试认证失败的情况.
     * 
     * @throws Exception
     *             异常
     * @author 尤小平
     * @date 2017年5月16日 下午8:36:31
     */
    @Test(expected = InterfaceException.class)
    public void testAuthLoginForFail() throws Exception {
        String httpresult = "{\"resultCode\":\"1\",\"data\":\"testdata\",\"message\":\"testmessage\"}";
        accessAuthService = new AccessAuthService() {
            @Override
            protected String sendAuthLoginGet(AuthLoginParam authLoginParam, String param) {
                return httpresult;
            }
        };

        AuthLoginParam authLoginParam = new AuthLoginParam();
        authLoginParam.setLogId("123123123");
        authLoginParam.setDeviceId("AC_201506022fd30ebd-835f-4443-be04-700ac4257917");
        authLoginParam.setUsermac("4C8D79844D3E");// 4C8D79844D3E
        authLoginParam.setPlatform("msp");
        authLoginParam.setAuthType("account");
        authLoginParam.setPublicuserip("192.168.1.12");
        authLoginParam.setPublicuserport("80");
        authLoginParam.setUsername("13958173965");
        authLoginParam.setPassword(MD5.md5("123456"));

        Mockito.when(wifiRecordService.save(any(WifiRecord.class))).thenReturn(1L);
        accessAuthService.setWifiRecordService(wifiRecordService);

        accessAuthService.authLogin(authLoginParam);

        Mockito.verify(wifiRecordService).save(any(WifiRecord.class));
    }

    /**
     * 测试认证时连接失败的情况.
     * 
     * @throws Exception
     *             异常
     * @author 尤小平
     * @date 2017年5月16日 下午8:36:59
     */
    @Test(expected = InterfaceException.class)
    public void testAuthLoginFor400() throws Exception {
        String httpresult = "400";
        accessAuthService = new AccessAuthService() {
            @Override
            protected String sendAuthLoginGet(AuthLoginParam authLoginParam, String param) {
                return httpresult;
            }
        };

        AuthLoginParam authLoginParam = new AuthLoginParam();
        authLoginParam.setLogId("123123123");
        authLoginParam.setDeviceId("FAT_AP_201506022fd30ebd-835f-4443-be04-700ac4257917");
        authLoginParam.setUsermac("4C8D79844D3E");// 4C8D79844D3E
        authLoginParam.setPlatform("msp");
        authLoginParam.setAuthType("account");
        authLoginParam.setPublicuserip("192.168.1.12");
        authLoginParam.setPublicuserport("80");
        authLoginParam.setUsername("13958173965");
        authLoginParam.setPassword(MD5.md5("123456"));

        accessAuthService.authLogin(authLoginParam);
    }

    /**
     * 测试踢人下线接口, 成功的情况.
     * 
     * @author 尤小平
     * @throws Exception
     * @date 2017年5月17日 上午9:49:38
     */
    @Test
    public void testKickuserForSuccess() throws Exception {
        accessAuthService = new AccessAuthService() {
            @Override
            protected Map<String,Object> sendKickUserOff(String param, int level)
                    throws Exception {
                Map<String,Object> authResult = new HashMap<>();
                authResult.put("resultCode", "0");
                authResult.put("data", "testdata");
                return authResult;
            }
        };

        String usermac = "mac";
        Map<String,Object> authResult = accessAuthService.kickuser(usermac, "");

        Assert.assertNotNull(authResult);
        Assert.assertEquals("testdata", authResult.get("data"));
    }

    /**
     * 测试踢人下线接口, 踢失败的情况.
     * 
     * @author 尤小平
     * @throws Exception
     * @date 2017年5月17日 上午9:49:58
     */
    @Test(expected = InterfaceException.class)
    public void testKickuserForFail() throws Exception {

        accessAuthService = new AccessAuthService() {
            @Override
            protected Map<String,Object> sendKickUserOff(String param, int level)
                    throws Exception {
                Map<String,Object> authResult = new HashMap<>();
                authResult.put("resultCode", "1");
                authResult.put("message", "error message");
                return authResult;
            }
        };

        PowerMockito.when(SysConfigUtil.class, "getParamValue", anyString()).thenReturn("url");

        String usermac = "mac";
        accessAuthService.kickuser(usermac, "");
    }

    /**
     * 测试踢人下线接口, 连接失败的情况.
     * 
     * @author 尤小平
     * @throws Exception
     * @date 2017年5月17日 上午9:50:04
     */
    @Test(expected = InterfaceException.class)
    public void testKickuserFor400() throws Exception {

        accessAuthService = new AccessAuthService() {

            @Override
            protected Map<String,Object> sendKickUserOff(String param, int level)
                    throws Exception {
                throw new InterfaceException("network not work","aheeee");
            }
        };

        String usermac = "mac";
        accessAuthService.kickuser(usermac, "");
    }

    /*
     * @Test public void testAuthLogin() throws Exception{
     * PowerMockito.mockStatic(ConfigUtil.class);
     * PowerMockito.when(ConfigUtil.getConfig(Mockito.anyString())).thenReturn(
     * "http://192.168.41.52/auth-service/auth/login");
     * 
     * AuthLoginParam authLoginParam = new AuthLoginParam();
     * authLoginParam.setLogId("123123123"); authLoginParam.setDeviceId(
     * "FAT_AP_201506022fd30ebd-835f-4443-be04-700ac4257917");
     * authLoginParam.setUsermac("4C8D79844D3E");//4C8D79844D3E
     * authLoginParam.setPlatform("msp"); authLoginParam.setAuthType("account");
     * authLoginParam.setPublicuserip("192.168.1.12");
     * authLoginParam.setPublicuserport("80");
     * authLoginParam.setUsername("13958173965");
     * authLoginParam.setPassword(MD5.md5("123456"));
     * 
     * WifiRecord record = new WifiRecord();
     * Mockito.when(wifiRecordService.save(any(WifiRecord.class))).thenReturn(1L
     * ); AuthResult authResult = accessAuthService.authLogin(authLoginParam);
     * 
     * Mockito.verify(wifiRecordService).save(any(WifiRecord.class)); }
     */
}

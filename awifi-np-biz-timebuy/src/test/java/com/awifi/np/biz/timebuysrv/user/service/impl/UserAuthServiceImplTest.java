/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月17日 上午10:54:51
* 创建作者：尤小平
* 文件名称：UserAuthServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.user.service.impl;

import static org.mockito.Matchers.any;

import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.api.client.dbcenter.user.model.PubUser;
import com.awifi.np.biz.api.client.dbcenter.user.model.PubUserAuth;
import com.awifi.np.biz.api.client.dbcenter.user.util.UserAuthClient;
import com.awifi.np.biz.api.client.dbcenter.user.util.UserBaseClient;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.timebuysrv.user.model.SysUser;
import com.awifi.np.biz.timebuysrv.user.service.IThirdUserService;
import com.awifi.np.biz.timebuysrv.util.CaptchaUtil;
import com.awifi.np.biz.timebuysrv.util.MsCommonUtil;
import com.awifi.np.biz.timebuysrv.web.log.ResultDTO;
import com.awifi.np.biz.timebuysrv.web.module.time.model.PortalParam;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionDTO;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionUser;
import com.awifi.np.biz.timebuysrv.web.module.time.service.TimeBuyService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ UserAuthClient.class, UserBaseClient.class, MerchantClient.class, RedisUtil.class, MsCommonUtil.class,
        MessageUtil.class, CaptchaUtil.class })
public class UserAuthServiceImplTest {
    /**
     * 被测试类
     */
    private UserAuthServiceImpl service;

    /**
     * UserBaseServiceImpl
     */
    @Mock
    private UserBaseServiceImpl mockUserBaseService;

    /**
     * TimeBuyService
     */
    @Mock
    private TimeBuyService mockTimeBuyService;

    /**
     * IThirdUserService
     */
    @Mock
    private IThirdUserService mockThirdUserService;

    /**
     * HttpServletRequest
     */
    @Mock
    private HttpServletRequest request;

    /**
     * setUp.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月17日 下午3:38:16
     */
    @Before
    public void setUp() throws Exception {
        service = new UserAuthServiceImpl();
        service.setUserBaseService(mockUserBaseService);
        service.setTimeBuyService(mockTimeBuyService);
        service.setThirdUserService(mockThirdUserService);
        request = new MockHttpServletRequest();
        PowerMockito.mockStatic(UserAuthClient.class);
        PowerMockito.mockStatic(UserBaseClient.class);
        PowerMockito.mockStatic(MerchantClient.class);
        PowerMockito.mockStatic(RedisUtil.class);
        PowerMockito.mockStatic(MsCommonUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(CaptchaUtil.class);
    }

    /**
     * tearDown.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月17日 下午3:38:22
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * 测试根据手机号码获取用户信息.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月17日 下午3:38:28
     */
    @Test
    public void testGetUserByLogName() throws Exception {
        String telphone = "13612345678";
        PubUserAuth userAuth = new PubUserAuth();
        PowerMockito.when(UserAuthClient.getUserByLogName(any(String.class))).thenReturn(userAuth);
        PubUserAuth pubUserAuth = service.getUserByLogName(telphone);

        Assert.assertEquals(userAuth, pubUserAuth);
        PowerMockito.verifyStatic();
        UserAuthClient.getUserByLogName(any(String.class));
    }

    /**
     * 测试保存注册.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月17日 下午3:38:34
     */
    @Test
    public void testSaveReg() throws Exception {
        List<PubUserAuth> list = new ArrayList<PubUserAuth>();
        PubUserAuth pubUserAuth = new PubUserAuth();
        pubUserAuth.setUserId(2L);
        list.add(pubUserAuth);
        String username = "username";
        String password = "password";

        PowerMockito.when(UserAuthClient.addUserAuth(any(PubUserAuth.class))).thenReturn(2L);
        PowerMockito.when(UserAuthClient.queryUserAuthByParam(any(PubUserAuth.class))).thenReturn(list);
        PowerMockito.when(mockUserBaseService.addSysUser(any(SysUser.class))).thenReturn(1);
        PowerMockito.when(MsCommonUtil.getCenterToken(any(String.class))).thenReturn("token");

        Long actual = service.saveReg(username, password);

        Assert.assertEquals(2, (long) actual);
        PowerMockito.verifyStatic();
        UserAuthClient.addUserAuth(any(PubUserAuth.class));
        UserAuthClient.queryUserAuthByParam(any(PubUserAuth.class));
        mockUserBaseService.addSysUser(any(SysUser.class));
        MsCommonUtil.getCenterToken(any(String.class));
    }

    /**
     * 测试登录获取用户信息并做相关保存操作.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月17日 下午3:38:41
     */
    @Test
    public void testSaveLoginSuccess() throws Exception {
        List<PubUserAuth> list = new ArrayList<PubUserAuth>();
        PubUserAuth pubUserAuth = new PubUserAuth();
        pubUserAuth.setUserId(2L);
        list.add(pubUserAuth);
        PowerMockito.when(UserAuthClient.queryUserAuthByParam(any(PubUserAuth.class))).thenReturn(list);
        PubUser pubUser = new PubUser();
        PowerMockito.when(UserBaseClient.queryByUserId(any(Long.class))).thenReturn(pubUser);
        Merchant merchant = new Merchant();
        merchant.setUserId(2L);
        merchant.setStatus(1);
        PowerMockito.when(MerchantClient.getByIdCache(any(Long.class))).thenReturn(merchant);

        String username = "";
        String deviceId = "";
        String terMac = "";
        String apMac = "";
        Long merchantId = 0L;
        Map<String, Object> actual = service.saveLogin(request, username, deviceId, terMac, apMac, merchantId);

        Assert.assertNotNull(actual);
        PowerMockito.verifyStatic();
        UserAuthClient.queryUserAuthByParam(any(PubUserAuth.class));
        UserBaseClient.queryByUserId(any(Long.class));
        MerchantClient.getByIdCache(any(Long.class));
    }

    /**
     * 测试登录获取用户信息并做相关保存，失败的情况.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月17日 下午3:38:46
     */
    @Test(expected = BizException.class)
    public void testSaveLoginFailA() throws Exception {
        List<PubUserAuth> list = new ArrayList<PubUserAuth>();
        PubUserAuth pubUserAuth = new PubUserAuth();
        pubUserAuth.setUserId(2L);
        list.add(pubUserAuth);
        PowerMockito.when(UserAuthClient.queryUserAuthByParam(any(PubUserAuth.class))).thenReturn(list);
        PubUser pubUser = null;
        PowerMockito.when(UserBaseClient.queryByUserId(any(Long.class))).thenReturn(pubUser);
        PowerMockito.when(MessageUtil.getMessage(any(String.class))).thenReturn("用户不存在");

        String username = "";
        String deviceId = "";
        String terMac = "";
        String apMac = "";
        Long merchantId = 0L;
        service.saveLogin(request, username, deviceId, terMac, apMac, merchantId);

        PowerMockito.verifyStatic();
        UserAuthClient.queryUserAuthByParam(any(PubUserAuth.class));
        UserBaseClient.queryByUserId(any(Long.class));
        MessageUtil.getMessage(any(String.class));
    }

    /**
     * 测试登录获取用户信息并做相关保存，失败的情况.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月17日 下午3:38:52
     */
    @Test(expected = BizException.class)
    public void testSaveLoginFailB() throws Exception {
        List<PubUserAuth> list = null;
        PowerMockito.when(UserAuthClient.queryUserAuthByParam(any(PubUserAuth.class))).thenReturn(list);
        PowerMockito.when(MessageUtil.getMessage(any(String.class))).thenReturn("用户不存在");

        String username = "";
        String deviceId = "";
        String terMac = "";
        String apMac = "";
        Long merchantId = 0L;
        service.saveLogin(request, username, deviceId, terMac, apMac, merchantId);

        PowerMockito.verifyStatic();
        UserAuthClient.queryUserAuthByParam(any(PubUserAuth.class));
        MessageUtil.getMessage(any(String.class));
    }

    /**
     * 测试判断是否为vip用户.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月17日 下午3:38:56
     */
    @Test
    public void testIsFreeUser() throws Exception {
        PowerMockito.when(mockTimeBuyService.isVipUser(any(String.class))).thenReturn(true);

        boolean actual = service.isFreeUser("username");

        Assert.assertTrue(actual);
        PowerMockito.verifyStatic();
        mockTimeBuyService.isVipUser(any(String.class));
    }

    /**
     * 测试根据手机号码和验证码登录，成功的情况.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月17日 下午3:39:01
     */
    @Test
    public void testLoginByPhoneAndSmsSuccessA() throws Exception {
        SessionDTO sessionDTO = new SessionDTO();
        String phone = "phone";
        String captcha = "captcha";
        SessionUser sessionUser = new SessionUser();
        PortalParam portalParam = new PortalParam();
        portalParam.setUserType("EXEMPT_AUTH_USER");
        portalParam.setMobilePhone(phone);
        sessionDTO.setSessionUser(sessionUser);
        sessionDTO.setPortalParam(portalParam);

        ResultDTO actual = service.loginByPhoneAndSms(sessionDTO, phone, captcha, request);

        Assert.assertNotNull(actual);
    }

    /**
     * 测试根据手机号码和验证码登录，免认证用户一键登录的情况.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月17日 下午3:39:06
     */
    @Test
    public void testLoginByPhoneAndSmsSuccessB() throws Exception {
        SessionDTO sessionDTO = new SessionDTO();
        String phone = "phone";
        String captcha = "";
        SessionUser sessionUser = new SessionUser();
        PortalParam portalParam = new PortalParam();
        portalParam.setUserType("EXEMPT_AUTH_USER");
        portalParam.setMobilePhone(phone);
        sessionDTO.setSessionUser(sessionUser);
        sessionDTO.setPortalParam(portalParam);

        ResultDTO actual = service.loginByPhoneAndSms(sessionDTO, phone, captcha, request);

        Assert.assertNotNull(actual);
    }

    /**
     * 测试根据手机号码和验证码登录，session为空的情况.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月17日 下午3:39:11
     */
    @Test(expected = BizException.class)
    public void testLoginByPhoneAndSmsFailA() throws Exception {
        String phone = "phone";
        String captcha = "captcha";

        service.loginByPhoneAndSms(null, phone, captcha, request);
    }

    /**
     * 测试根据手机号码和验证码登录，新用户一键登录的情况.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月17日 下午3:39:15
     */
    @Test(expected = BizException.class)
    public void testLoginByPhoneAndSmsFailB() throws Exception {
        SessionDTO sessionDTO = new SessionDTO();
        String phone = "phone";
        String captcha = "";
        SessionUser sessionUser = new SessionUser();
        PortalParam portalParam = new PortalParam();
        portalParam.setUserType("NEW_USER");
        portalParam.setMobilePhone(phone);
        sessionDTO.setSessionUser(sessionUser);
        sessionDTO.setPortalParam(portalParam);

        service.loginByPhoneAndSms(sessionDTO, phone, captcha, request);
    }
}

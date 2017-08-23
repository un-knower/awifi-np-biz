package com.awifi.np.biz.common.test;

import static org.mockito.Matchers.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CenterHttpRequest.class,BeanUtil.class,CastUtil.class,ValidUtil.class,MessageUtil.class,JsonUtil.class,RedisUtil.class,SysConfigUtil.class,SessionUtil.class})
@PowerMockIgnore({"javax.management.*"})//忽略一些Mock异常
public abstract class MockBase {
	/**
	 * 请求
	 **/
    public MockHttpServletRequest request;
    /**
     * 响应
     */
    public MockHttpServletResponse response;
    /**
     * token
     */
    public String access_token = "";
	/**初始化*/
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(RedisUtil.class);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(CastUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(ValidUtil.class);
        PowerMockito.mockStatic(SessionUtil.class);
        PowerMockito.mockStatic(CenterHttpRequest.class);
        SessionUser sessionUser = new SessionUser();
        sessionUser.setUserName("admin");
        PowerMockito.when(SessionUtil.getCurSessionUser(anyObject())).thenReturn(sessionUser);
    }
}

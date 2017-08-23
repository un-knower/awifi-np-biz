/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年5月5日 上午10:36:36
 * 创建作者：尤小平
 * 文件名称：UserBaseControllerTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.timebuysrv.user.controller;

import static org.mockito.Matchers.any;

import com.awifi.np.biz.api.client.dbcenter.user.model.PubUser;
import com.awifi.np.biz.timebuysrv.user.service.UserBaseService;
import com.awifi.np.biz.timebuysrv.web.core.Constants;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionDTO;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionUser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Map;

@SuppressWarnings("rawtypes")
public class UserBaseControllerTest {
    /**
     * 被测试类
     */
    @InjectMocks
    private UserBaseController controller;

    /**
     * UserBaseService
     */
    private UserBaseService mockUserBaseService;

    /**
     * MockHttpServletRequest
     */
    private MockHttpServletRequest request;

    /**
     * setUp.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月5日 下午4:40:54
     */
    @Before
    public void setUp() throws Exception {
        controller = new UserBaseController(){
            @Override
            protected void throwValidException(String code, String message) {
                return;
            }
        };
        mockUserBaseService = Mockito.mock(UserBaseService.class);
        controller.setUserBaseService(mockUserBaseService);
        request = new MockHttpServletRequest();
        request.setParameter("faceInfo", "faceInfo");
        request.setParameter("userNick", "userNick");
        request.setParameter("sex", "男");
        request.setParameter("address", "address");
        request.setParameter("birthday", "2017-05-01");
        request.setParameter("telphone", "18969901234");
    }

    /**
     * tearDown.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月5日 下午4:41:04
     */
    @After
    public void tearDown() throws Exception {
        controller = null;
        mockUserBaseService = null;
        request = null;
    }

    /**
     * 测试根据用户id查询用户基本信息.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月5日 下午4:41:51
     */
    @Test
    public void testView() throws Exception {

        PubUser pubUser = new PubUser();
        pubUser.setTelphone("18969101234");
        Mockito.when(mockUserBaseService.getByUseId(any(Long.class))).thenReturn(pubUser);

        Map actual = controller.view("2");

        Assert.assertEquals("18969101234", ((PubUser)actual.get("data")).getTelphone()+"");
        Mockito.verify(mockUserBaseService).getByUseId(any(Long.class));
    }

    /**
     * 测试根据session查询用户基本信息.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月5日 下午4:42:06
     */
    @Test
    public void testGetUserInfo() throws Exception{
        SessionDTO sessionDTO = new SessionDTO();
        SessionUser sessionUser = new SessionUser();
        sessionUser.setId(2L);
        sessionDTO.setSessionUser(sessionUser);
        request.getSession().setAttribute(Constants.SESSION_DTO, sessionDTO);
        
        PubUser pubUser = new PubUser();
        pubUser.setId(10L);
        Mockito.when(mockUserBaseService.getByUseId(any(Long.class))).thenReturn(pubUser);

        Map actual = controller.getUserInfo(request);

        Assert.assertEquals(10+"", ((PubUser) actual.get("data")).getId()+"");
        Mockito.verify(mockUserBaseService).getByUseId(any(Long.class));
    }

    /**
     * 测试根据session查询用户基本信息.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月5日 下午4:42:27
     */
    @Test
    public void testGetUserInfoForSessionNull() throws Exception{
        //测试sessionUser.getId() == null的情况
        SessionDTO sessionDTO = new SessionDTO();
        SessionUser sessionUser = new SessionUser();
        sessionUser.setId(0L);
        sessionDTO.setSessionUser(sessionUser);
        request.getSession().setAttribute(Constants.SESSION_DTO, sessionDTO);
        
        Map actual = controller.getUserInfo(request);

        Assert.assertNull(((PubUser) actual.get("data")));
        
        
        //测试sessionUser == null的情况
        sessionUser = null;
        sessionDTO.setSessionUser(sessionUser);
        request.getSession().setAttribute(Constants.SESSION_DTO, sessionDTO);
        
        actual = controller.getUserInfo(request);

        Assert.assertNull(((PubUser) actual.get("data")));

        
      //测试sessionDTO == null的情况
        sessionDTO = null;
        request.getSession().setAttribute(Constants.SESSION_DTO, sessionDTO);
        
        actual = controller.getUserInfo(request);

        Assert.assertNull(((PubUser) actual.get("data")));
    }

    /**
     * 测试添加用户基本信息.
     * 
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月5日 下午4:42:34
     */
    @Test
    public void testAdd() throws Exception {
        Mockito.when(mockUserBaseService.add(any(PubUser.class))).thenReturn(2L);

        Map actual = controller.add(request);

        Assert.assertEquals(2L, actual.get("data"));
    }

    /**
     * 测试根据用户id更新用户基本信息.
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月5日 下午4:42:38
     */
    @Test
    public void testUpdate() throws Exception {
        Map actual = controller.update("2", request);

        Assert.assertNull(actual.get("data"));
    }

}

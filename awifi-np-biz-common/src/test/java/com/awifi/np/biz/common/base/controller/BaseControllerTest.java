package com.awifi.np.biz.common.base.controller;

import static org.mockito.Matchers.anyObject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;

import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月22日 下午1:30:28
 * 创建作者：亢燕翔
 * 文件名称：BaseControllerTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageUtil.class,JsonUtil.class,RedisUtil.class,SysConfigUtil.class,SessionUtil.class})
@PowerMockIgnore({"javax.management.*"})
public class BaseControllerTest {

    /**被测试类*/
    @InjectMocks
    private BaseController baseController;
    
    /**请求*/
    private MockHttpServletRequest request;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        request = new MockHttpServletRequest();
    }
    
    /**
     * 设置分页信息
     * @author 亢燕翔  
     * @date 2017年3月22日 下午1:40:16
     */
    @SuppressWarnings("rawtypes")
    @Test
    public void testSetPageInfo(){
        baseController.setPageInfo(new Page());
    }
    
    /**
     * 返回客户端成功信息
     * @author 亢燕翔  
     * @date 2017年3月22日 下午1:40:28
     */
    @Test
    public void testSuccessMsg(){
        baseController.successMsg();
    }
    
    /**
     * 返回客户端失败信息
     * @author 范立松  
     * @date 2017年5月12日 下午5:39:54
     */
    @Test
    public void testFailMsg(){
        baseController.failMsg(anyObject(),anyObject());
    }
    
    /**
     * 返回客户端data数据
     * @author 亢燕翔  
     * @date 2017年3月22日 下午1:40:36
     */
    @Test
    public void testSuccessMsgData(){
        baseController.successMsg(anyObject());
    }
    
    /**
     * 返回客户端模板数据
     * @author 亢燕翔  
     * @date 2017年3月22日 下午1:40:47
     */
    @Test
    public void testSuccessMsgTemplate(){
        baseController.successMsg("xxx");
    }
    
    /**
     * 异常处理
     * @author 亢燕翔  
     * @date 2017年3月22日 下午1:40:55
     */
    @Test
    public void testHandle(){
        Exception e = new Exception();
        baseController.handle(request, e);
    }
}

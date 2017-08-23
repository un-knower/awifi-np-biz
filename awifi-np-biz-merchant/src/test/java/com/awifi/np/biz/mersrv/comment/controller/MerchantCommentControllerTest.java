package com.awifi.np.biz.mersrv.comment.controller;

import static org.mockito.Matchers.anyObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.common.security.permission.util.PermissionUtil;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.RegexUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.mws.comment.service.MerchantCommentPicService;
import com.awifi.np.biz.mws.comment.service.MerchantCommentReService;
import com.awifi.np.biz.mws.comment.service.MerchantCommentService;

@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest({SysConfigUtil.class,JsonUtil.class,CastUtil.class,SessionUtil.class, 
        ValidUtil.class,PermissionUtil.class,MessageUtil.class,RegexUtil.class,MerchantClient.class,MessageUtil.class})
public class MerchantCommentControllerTest {
    /**被测试类*/
    @InjectMocks
    private MerchantCommentController merchantCommentController;
    
    /**商户评论-回复接口服务*/
    @Mock(name = "merchantCommentReService")
    private MerchantCommentReService merchantCommentReService;
    /**
     * 商户评论接口服务
     */
    @Mock(name = "merchantCommentService")
    private MerchantCommentService merchantCommentService;
    /**
     * 商户评论接口服务
     */
    @Mock(name = "merchantCommentPicService")
    private MerchantCommentPicService merchantCommentPicService;
    /**请求*/
    private MockHttpServletRequest httpRequest;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        httpRequest = new MockHttpServletRequest();
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(CastUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(ValidUtil.class);
        PowerMockito.mockStatic(SessionUtil.class);
        PowerMockito.mockStatic(PermissionUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(RegexUtil.class);
        PowerMockito.mockStatic(MerchantClient.class);
    }
    
    /**
     * 商户评论接口单元测试
     * @throws Exception 
     * @author 方志伟  
     * @date 2017年6月16日 下午5:13:22
     */
    @Test
    public void testAdd() throws Exception{
        Map<String,Object> bodyParam = new HashMap<String, Object>();
        ArrayList<String> picUrl = new ArrayList<String>();
        bodyParam.put("userPhone", "18656878779");
        bodyParam.put("content", "contentTest");
        picUrl.add("cn.bing.com");
        picUrl.add("wwww.baidu.com");
        bodyParam.put("commentPicUrl", picUrl);
        Map<String, Object> result = merchantCommentController.add(1L, bodyParam, httpRequest);
        Assert.assertNotNull(result);
    }
    
    /**
     * 商户评论-回复接口测试
     * @throws Exception 
     * @author 许尚敏
     * @date 2017年6月16日 下午5:10:15
     */
    @Test
    public void testAddReply() throws Exception{
        Map<String, Object> bodyParam = new HashMap<String, Object>();
        bodyParam.put("commentId", 71278);
        bodyParam.put("commentUserId", 0);
        bodyParam.put("commentUserName", "游客 *.*.1.101");
        bodyParam.put("userPhone", "18758125127");
        bodyParam.put("replyToUserId", 120);
        bodyParam.put("replyToUserName", "189****1753");
        bodyParam.put("content", "345345xxx");
        Mockito.doNothing().when(merchantCommentReService).addReply(anyObject());
        Map<String,Object> actual = merchantCommentController.addReply(2L, bodyParam, httpRequest);
        Assert.assertNotNull(actual);
        PowerMockito.verifyStatic();
    }
    
    /**
     * 测试获取评论列表
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 19, 2017 2:47:04 PM
     */
    @Test
    public void testGetListByParam () throws Exception {
        PowerMockito.when(SysConfigUtil.getParamValue("page_maxsize")).thenReturn("10");
        PowerMockito.doNothing().when(ValidUtil.class,"valid",anyObject(),anyObject(),anyObject());
        Mockito.doNothing().when(merchantCommentService).getListByParam(anyObject(), anyObject());
        
        Map<String,Object> result = merchantCommentController.getListByParam(62L,2,1);
        Assert.assertNotNull(result);
    }
}

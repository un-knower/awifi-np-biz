/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月19日 上午8:49:52
* 创建作者：方志伟
* 文件名称：MerchantCommentServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mws.comment.service.impl;


import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyInt;

import java.util.ArrayList;
import java.util.List;

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

import com.awifi.np.biz.api.client.dbcenter.user.util.UserAuthClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.mws.comment.dao.MerchantCommentDao;
import com.awifi.np.biz.mws.comment.model.MerchantComment;
import com.awifi.np.biz.mws.comment.model.MerchantCommentReply;
import com.awifi.np.biz.mws.comment.service.MerchantCommentPicService;
import com.awifi.np.biz.mws.comment.service.MerchantCommentReService;

@SuppressWarnings("rawtypes")
@RunWith(PowerMockRunner.class)
@PrepareForTest({BeanUtil.class,UserAuthClient.class})
@PowerMockIgnore({"javax.management.*"})
public class MerchantCommentServiceImplTest {
    /**被测试类*/
    @InjectMocks
    private MerchantCommentServiceImpl merchantCommentServiceImpl;
    /**持久层*/
    @Mock(name = "mervhantCommentDao")
    private MerchantCommentDao merchantCommentDao;
    
    /**评论图片Dao*/
    @Mock (name = "merchantCommentPicService")
    private MerchantCommentPicService merchantCommentPicService;
    
    /**评论回复Dao*/
    @Mock (name = "merchantCommentReService")
    private MerchantCommentReService merchantCommentReService;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.mockStatic(UserAuthClient.class);
    }
    
    /**
     * @throws Exception 异常
     * @author 方志伟  
     * @date 2017年6月19日 上午10:19:46
     */
    @Test
    public void testAdd() throws Exception{
        merchantCommentServiceImpl.add(1L, "18656878889", "testAdd", "192.168.3.11");
    }
    
    /**
     * 获取评论列表
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 19, 2017 7:22:50 PM
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetListByParam () throws Exception {
        Mockito.when(merchantCommentDao.getCountByParam(anyLong())).thenReturn(34);
        List<MerchantComment> commentList = new ArrayList<>();
        MerchantComment merchantComment = new MerchantComment();
        merchantComment.setCommentId(1L);
        merchantComment.setCommentUserId(10086L);
        merchantComment.setCommentUserName("awifi");
        merchantComment.setContent("content");
        merchantComment.setCreateDate("2017-06-20 15:30:00");
        commentList.add(merchantComment);
        
        Page page = new Page();
        page.setPageSize(30);
        PowerMockito.when(merchantCommentDao.getListByParam(anyLong(), anyInt(), anyInt())).thenReturn(commentList);
        
        List<String> picUrls = new ArrayList<>();
        picUrls.add("http://www.baidu.com");
        picUrls.add("http://www.163.com");
        PowerMockito.when(merchantCommentPicService.getPicListByCommentId(anyLong())).thenReturn(picUrls);
        
        MerchantCommentReply merchantCommentReply = new MerchantCommentReply();
        merchantCommentReply.setCommentReplyId(1L);
        merchantCommentReply.setCreateDate("2017-06-20 15:30:00");
        merchantCommentReply.setContent("content");
        merchantCommentReply.setReplyToUserId(2);
        merchantCommentReply.setReplyToUserName("ReplyToUserName");
        merchantCommentReply.setReplyUserId(3);
        merchantCommentReply.setReplyUserName("ReplyUserName");
        List<MerchantCommentReply> list = new ArrayList<>();
        list.add(merchantCommentReply);
        PowerMockito.when(merchantCommentReService.getReplyListByCommentId(anyLong())).thenReturn(list);
        
        merchantCommentServiceImpl.getListByParam(page, anyLong());
    }
}

package com.awifi.np.biz.mws.comment.service.impl;

import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import com.awifi.np.biz.common.util.DateUtil;
import com.awifi.np.biz.mws.comment.dao.MerchantCommentReplyDao;

@RunWith(PowerMockRunner.class)
public class MerchantCommentReServiceImplTest{

    /**被测试类*/
    @InjectMocks
    private MerchantCommentReServiceImpl merchantCommentReServiceImpl;
    
    /**评论回复Dao*/
    @Mock
    private MerchantCommentReplyDao merchantCommentReplyDao;
    
    /**
     * 初始化
     * @author 许尚敏  
     * @date 2017年6月19日 上午9:35:25
     */
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }
    
    /**
     * 数据接口
     * @throws Exception 异常
     * @author 许尚敏  
     * @date 2017年6月19日 上午9:35:25
     */
    @Test(expected=NullPointerException.class)
    public void testAddReply() throws Exception{
        Map<String, Object> replyParam = new HashMap<String, Object>();
        replyParam.put("merchantId", "2");
        replyParam.put("commentId", "71278");
        replyParam.put("commentUserId", "0");
        replyParam.put("commentUserName", "游客 *.*.1.101");
        replyParam.put("replyUserId", "554");
        replyParam.put("replyUserName", "18758125127");
        replyParam.put("userPhone", "18758125127");
        replyParam.put("content", "345345xxx");
        replyParam.put("createDate", DateUtil.getNow());
        replyParam.put("replyToUserId", "120");
        replyParam.put("replyToUserName", "18912341753");
        merchantCommentReServiceImpl.addReply(replyParam);
        PowerMockito.verifyStatic();
    }

    /**
     * 测试获取评论回复列表
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 19, 2017 3:56:06 PM
     */
    @Test
    public void testGetReplyListByCommentId () throws Exception {
        merchantCommentReServiceImpl.getReplyListByCommentId(10086L);
    }
}

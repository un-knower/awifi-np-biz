/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月19日 上午10:37:49
* 创建作者：方志伟
* 文件名称：MerchantCommentPicServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mws.comment.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.user.util.UserAuthClient;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.mws.comment.dao.MerchantCommentPicDao;

@RunWith(PowerMockRunner.class)
@PrepareForTest({BeanUtil.class,UserAuthClient.class,SysConfigUtil.class})
@PowerMockIgnore({"javax.management.*"})
public class MerchantCommentPicServiceImplTest {
    /**被测试类*/
    @InjectMocks
    private MerchantCommentPicServiceImpl merchantCommentPicServiceImpl;
    /**持久层*/
    @Mock(name = "mervhantCommentPicDao")
    private MerchantCommentPicDao merchantCommentPicDao;
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.mockStatic(UserAuthClient.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
    }
    
    /**
     * @throws Exception 异常
     * @author 方志伟  
     * @date 2017年6月19日 上午10:52:11
     */
    @Test
    public void testAdd() throws Exception{
        ArrayList<String> list = new ArrayList<String>();
        list.add("cn.bing.com");
        list.add("www.baidu.com");
        list.add("www.apple.com.cn");
        merchantCommentPicServiceImpl.add(list, 1L);
    }
    
    /**
     * 测试获取评论图片列表
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jun 19, 2017 3:53:41 PM
     */
    @Test
    public void testGetPicListByCommentId () throws Exception {
        PowerMockito.when(SysConfigUtil.getParamValue("mws_domain")).thenReturn("http://beta-51awifi.com");
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("345");
        PowerMockito.when(merchantCommentPicDao.getPicListByCommentId(405L)).thenReturn(list);
        merchantCommentPicServiceImpl.getPicListByCommentId(405L);
    }
}

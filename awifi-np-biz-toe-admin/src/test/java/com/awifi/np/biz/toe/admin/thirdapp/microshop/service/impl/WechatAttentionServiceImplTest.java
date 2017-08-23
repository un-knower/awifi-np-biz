/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月20日 上午9:49:48
* 创建作者：方志伟
* 文件名称：WechatAttentionServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.toe.admin.thirdapp.microshop.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.toe.admin.thirdapp.microshop.dao.WechatAttentionServiceDao;

@RunWith(PowerMockRunner.class)
public class WechatAttentionServiceImplTest {
    /**被测试类*/
    @InjectMocks
    private WechatAttentionServiceImpl wechatAttentionServiceImpl;
    /**持久层*/
    @Mock(name = "wechatAttentionServiceDao")
    private WechatAttentionServiceDao wechatAttentionServiceDao;
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }
    
    /**
     * 测试 新增微信关注记录
     * 如果已存在，自动跳过
     * @author 方志伟  
     * @date 2017年6月20日 上午9:59:57
     */
    @Test
    public void testAdd(){
        wechatAttentionServiceImpl.add("1", "18656878779", "123456789");
    }
    
    @Test
    public void testAddWithCheck(){
        wechatAttentionServiceImpl.addWithCheck("shopeId", "18656878779", "userMac");
    }
    
    /**
     * 测试通过shopId和UserMac获取关注信息
     * @author 方志伟  
     * @date 2017年6月20日 上午10:05:58
     */
    @Test
    public void testGetAttentionFlag(){
        wechatAttentionServiceImpl.getAttentionFlag("2", "987654342");
    }
}

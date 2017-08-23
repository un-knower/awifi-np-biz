package com.awifi.np.biz.pub.system.industry.service.impl;

import static org.mockito.Matchers.anyObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.industry.util.IndustryClient;
import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月21日 下午8:10:34
 * 创建作者：周颖
 * 文件名称：IndustryServiceImplTest.java
 * 版本：  v1.0
 * 功能：行业实现类测试类
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({IndustryClient.class,MerchantClient.class,RedisUtil.class})
@PowerMockIgnore({"javax.management.*"})
public class IndustryServiceImplTest {

    /**被测试类*/
    @InjectMocks
    private IndustryServiceImpl industryServiceImpl;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(IndustryClient.class);
        PowerMockito.mockStatic(MerchantClient.class);
        PowerMockito.mockStatic(RedisUtil.class);
    }
    
    /**
     * 父code不为空
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月22日 上午9:03:07
     */
    @Test
    public void testGetListByParamParentCode() throws Exception {
        Map<String,String> redisMap = null;
        PowerMockito.when(RedisUtil.getBatch("np_biz_industry_parentCode_*_2")).thenReturn(redisMap);
        PowerMockito.doNothing().when(IndustryClient.class,"cache");
        
        SessionUser sessionUser = new SessionUser();
        List<Map<String, String>> result = industryServiceImpl.getListByParam(sessionUser, "parentCode");
        Assert.assertNull(result);
        PowerMockito.verifyStatic();
        //RedisUtil.getAllWithKeyBatch(anyObject());
        IndustryClient.cache();
    }
    
    /**
     * 父code为空 组织不为4
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月22日 上午9:03:07
     */
    @Test
    public void testGetListByParamOrgId() throws Exception {
        Map<String,String> redisMap = new HashMap<String,String>();
        redisMap.put("np_biz_industry_OCAB10_1", "餐饮");
        redisMap.put("np_biz_industry_OCAB11_1", "酒店住宿");
        PowerMockito.when(RedisUtil.getBatch("np_biz_industry_*_1")).thenReturn(redisMap);
        PowerMockito.doNothing().when(IndustryClient.class,"cache");
        
        SessionUser sessionUser = new SessionUser();
        List<Map<String, String>> result = industryServiceImpl.getListByParam(sessionUser, null);
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        RedisUtil.getBatch("np_biz_industry_*_1");
        IndustryClient.cache();
    }
    
    /**
     * 父code为空 组织为4
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月22日 上午9:03:07
     */
    @Test
    public void testGetListByParamProject() throws Exception {
        Merchant merchant = new Merchant();
        merchant.setPriIndustryCode("OCAB10");
        merchant.setPriIndustry("餐饮");
        PowerMockito.when(MerchantClient.getById(anyObject())).thenReturn(merchant);
        
        SessionUser sessionUser = new SessionUser();
        sessionUser.setMerchantId(1L);
        List<Map<String, String>> result = industryServiceImpl.getListByParam(sessionUser, null);
        Assert.assertNotNull(result);
        PowerMockito.verifyStatic();
        MerchantClient.getById(anyObject());
    }
}
package com.awifi.np.biz.api.client.dbcenter.merchant.util;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.industry.util.IndustryClient;
import com.awifi.np.biz.api.client.dbcenter.location.util.LocationClient;
import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.api.client.dbcenter.merchant.service.MerchantApiService;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.BeanUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月22日 下午5:25:27
 * 创建作者：周颖
 * 文件名称：MerchantClientTest.java
 * 版本：  v1.0
 * 功能：商户测试类
 * 修改记录：
 */
@SuppressWarnings("static-access")
@RunWith(PowerMockRunner.class)
@PrepareForTest({RedisUtil.class, BeanUtil.class,LocationClient.class,IndustryClient.class})
public class MerchantClientTest {

    /**被测试类*/
    @InjectMocks
    private MerchantClient merchantClient;
    
    /**
     * 商户服务
     */
    @Mock(name = "merchantApiService")
    private MerchantApiService merchantApiService;
    
    /**
     * 初始化
     * @author 周颖  
     * @date 2017年3月23日 上午9:11:43
     */
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(RedisUtil.class);
        PowerMockito.mockStatic(LocationClient.class);
        PowerMockito.mockStatic(IndustryClient.class);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.when(BeanUtil.getBean(anyString())).thenReturn(merchantApiService);
    }
    
    /**
     * 商户列表总数
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年3月23日 上午9:11:54
     */
    @Test
    public void testGetCountByParam() throws Exception {
        merchantClient.getCountByParam(null);
    }
    
    /**
     * 商户列表
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年3月23日 上午9:12:19
     */
    @Test
    public void testGetListByParam() throws Exception {
        merchantClient.getListByParam(null);
    }
    
    /**
     * 商户详情
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年3月23日 上午9:12:37
     */
    @Test
    public void testGetById() throws Exception {
        merchantClient.getById(1L);
    }

    /**
     * 添加商户
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年3月23日 上午9:12:54
     */
    @Test
    public void testAdd() throws Exception {
        merchantClient.add(new Merchant(), "industryCode");
    }

    /**
     * 获取商户名称  缓存为空
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年3月23日 上午9:13:11
     */
    @Test
    public void testGetNameByIdNull() throws Exception {
        PowerMockito.when(RedisUtil.hmget(anyString(),anyString())).thenReturn(null);
        Merchant merchant = new Merchant();
        merchant.setId(53525L);
        merchant.setCascadeLevel(1);
        merchant.setParentId(0L);
        merchant.setProjectId(1L);
        when(merchantClient.getById(1L)).thenReturn(merchant);
        merchantClient.getNameByIdCache(1L);
        PowerMockito.verifyStatic();
        RedisUtil.hmget(anyString(),anyString());
    }
    
    /**
     * 获取商户名称  缓存为空
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年3月23日 上午9:13:11
     */
    @Test
    public void testGetNameByIdMerchantNameAll() throws Exception {
        PowerMockito.when(RedisUtil.hmget(anyString(),anyString())).thenReturn(null);
        Merchant merchant = new Merchant();
        merchant.setId(53525L);
        merchant.setProvinceId(31L);
        merchant.setCityId(383L);
        merchant.setAreaId(3220L);
        merchant.setPriIndustryCode("priCode");
        merchant.setSecIndustryCode("secCode");
        PowerMockito.when(LocationClient.getByIdAndParam(anyLong(),anyString())).thenReturn("location");
        PowerMockito.when(IndustryClient.getNameByCode(anyString())).thenReturn("industry");
        when(merchantClient.getById(1L)).thenReturn(merchant);
        merchantClient.getNameByIdCache(1L);
        PowerMockito.verifyStatic();
        RedisUtil.hmget(anyString(),anyString());
        LocationClient.getByIdAndParam(anyLong(),anyString());
        IndustryClient.getNameByCode(anyString());
    }
    
    /**
     * 获取商户名称 缓存中存在
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年3月23日 上午9:15:15
     */
    @Test
    public void testGetNameById() throws Exception {
        List<String> columnList = new ArrayList<String>();
        columnList.add("customerName");
        PowerMockito.when(RedisUtil.hmget(anyString(),anyString())).thenReturn(columnList);
        merchantClient.getNameByIdCache(1L);
        PowerMockito.verifyStatic();
        RedisUtil.hmget(anyString(),anyString());
    }

    /**
     * 更新商户
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年3月23日 上午10:39:22
     */
    @Test
    public void testUpdate() throws Exception {
        merchantClient.update(new Merchant(), "industryCode");
    }

}

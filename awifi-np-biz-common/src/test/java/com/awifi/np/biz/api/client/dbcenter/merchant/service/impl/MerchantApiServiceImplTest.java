package com.awifi.np.biz.api.client.dbcenter.merchant.service.impl;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月27日 下午2:00:24
 * 创建作者：周颖
 * 文件名称：MerchantApiServiceImplTest.java
 * 版本：  v1.0
 * 功能：商户测试类
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({CenterHttpRequest.class,SysConfigUtil.class,CastUtil.class,JsonUtil.class,RedisUtil.class})
public class MerchantApiServiceImplTest {

    /**被测试类*/
    @InjectMocks
    private MerchantApiServiceImpl merchantApiServiceImpl;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(CenterHttpRequest.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(CastUtil.class);
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(RedisUtil.class);
    }
    
    /**
     * 总数接口
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月27日 下午2:27:03
     */
    @Test
    public void testGetCountByParam() throws Exception {
        Map<String,Object> paramsMap = getParam();
        Map<String, Object> returnMap = new HashMap<String,Object>();
        returnMap.put("rs", 1);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(anyString(),anyString())).thenReturn(returnMap);
        PowerMockito.when(JsonUtil.toJson(anyObject())).thenReturn("result");
        
        merchantApiServiceImpl.getCountByParam(paramsMap);
        PowerMockito.verifyStatic();
        CenterHttpRequest.sendGetRequest(anyString(),anyString());
        JsonUtil.toJson(anyObject());
    }

    /**
     * 列表接口
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月27日 下午2:37:02
     */
    @Test
    public void testGetListByParam() throws Exception {
        Map<String,Object> paramsMap = getParam();
        Map<String, Object> returnMap = new HashMap<String,Object>();
        returnMap.put("id", 543217L);
        returnMap.put("merchantName", "test");
        returnMap.put("merchantType", 2);
        returnMap.put("cascadeLabel", "543217");
        returnMap.put("cascadeLevel", 1);
        returnMap.put("parentId", 0);
        returnMap.put("merchantProject", 4);
        returnMap.put("industry", "OCAB1202");
        returnMap.put("storeScope", "1");
        List<Map<String, Object>> merchantList = new ArrayList<Map<String,Object>>();
        merchantList.add(returnMap);
        returnMap = new HashMap<String,Object>();
        returnMap.put("rs", merchantList);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(anyString(),anyString())).thenReturn(returnMap);
        PowerMockito.when(JsonUtil.toJson(anyObject())).thenReturn("result");
        
        merchantApiServiceImpl.getListByParam(paramsMap);
        PowerMockito.verifyStatic();
        CenterHttpRequest.sendGetRequest(anyString(),anyString());
        JsonUtil.toJson(anyObject());
    }

    /**
     * 初始化入参
     * @return 入参
     * @author 周颖  
     * @date 2017年2月27日 下午2:37:21
     */
    public Map<String,Object> getParam(){
        Map<String,Object> paramsMap = new HashMap<String,Object>();
        paramsMap.put("id", 543217L);
        paramsMap.put("type", "nextAllWithThis");
        paramsMap.put("industryId", "OCAB1202");
        paramsMap.put("projectId", 4L);
        paramsMap.put("notEqualProjectId", 1L);
        return paramsMap;
    }
    
    /**
     * 商户详情接口
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月27日 下午3:03:52
     */
    @Test
    public void testGetById() throws Exception {
        Map<String, Object> returnMap = new HashMap<String,Object>();
        Map<String, Object> merchantMap = new HashMap<String,Object>();
        merchantMap.put("id", 543217L);
        merchantMap.put("merchantName", "test");
        merchantMap.put("merchantType", 2);
        merchantMap.put("cascadeLabel", "543217");
        merchantMap.put("cascadeLevel", 1);
        merchantMap.put("parentId", 0);
        merchantMap.put("merchantProject", 4);
        merchantMap.put("industry", "OCAB1202");
        merchantMap.put("storeScope", "1");
        returnMap.put("rs", merchantMap);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(anyString(),anyString())).thenReturn(returnMap);
        PowerMockito.when(JsonUtil.toJson(anyObject())).thenReturn("result");
        
        merchantApiServiceImpl.getById(543217L);
        PowerMockito.verifyStatic();
        CenterHttpRequest.sendGetRequest(anyString(),anyString());
        JsonUtil.toJson(anyObject());
    }

    /**
     * 添加商户
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月27日 下午3:10:29
     */
    @Test
    public void testAdd() throws Exception {
        Map<String, Object> returnMap = new HashMap<String,Object>();
        returnMap.put("rs", 1L);
        PowerMockito.when(CenterHttpRequest.sendPostRequest(anyString(),anyString())).thenReturn(returnMap);
        
        Merchant merchant = new Merchant();
        merchant.setStoreScope(1);
        merchant.setProjectId(1L);
        merchant.getMerchantType();
        merchant.setParentName("xxx");
        merchant.getParentName();
        merchant.setAccount("xxx");
        merchant.getAccount();
        merchant.setRoleIds("xxx");
        merchant.getRoleIds();
        merchant.setRoleNames("xxx");
        merchant.getRoleNames();
        merchant.setProjectName("xxx");
        merchant.getProjectName();
        merchant.setLocationFullName("xxx");
        merchant.getLocationFullName();
        merchant.getStoreTypeDsp();
        merchant.getStoreLevelDsp();
        merchant.getStoreStarDsp();
        merchant.getStoreScopeDsp();
        merchant.getConnectTypeDsp();
        merchant.setCreateDate("xxx");
        merchant.getCreateDate();
        merchant.setUpdateDate("xxx");
        merchant.getUpdateDate();
        merchantApiServiceImpl.add(merchant, "industryCode");
        PowerMockito.verifyStatic();
        CenterHttpRequest.sendPostRequest(anyString(),anyString());
    }

    /**
     * 编辑商户
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月27日 下午3:15:27
     */
    @Test
    public void testUpdate() throws Exception {
        PowerMockito.when(CenterHttpRequest.sendPutRequest(anyString(),anyString())).thenReturn(null);
        
        Merchant merchant = new Merchant();
        merchant.setStoreScope(1);
        merchant.setProjectId(1L);
        merchantApiServiceImpl.update(merchant, "industryCode");
        PowerMockito.verifyStatic();
        CenterHttpRequest.sendPutRequest(anyString(),anyString());
    }
}
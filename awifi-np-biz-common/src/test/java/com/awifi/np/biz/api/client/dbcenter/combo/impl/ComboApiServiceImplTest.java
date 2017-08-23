/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月19日 下午7:51:53
* 创建作者：范立松
* 文件名称：ComboApiServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.combo.impl;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.combo.service.impl.ComboApiServiceImpl;
import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ CenterHttpRequest.class, SysConfigUtil.class, JsonUtil.class, URLEncoder.class })
public class ComboApiServiceImplTest {

    /**被测试类*/
    @InjectMocks
    private ComboApiServiceImpl comboApiServiceImpl;

    /**初始化
     * @throws Exception */
    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(CenterHttpRequest.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(URLEncoder.class);
        PowerMockito.when(JsonUtil.toJson(anyObject())).thenReturn("params");
        PowerMockito.when(URLEncoder.encode(anyObject(), anyString())).thenReturn("params");
    }

    /**
     * 测试查询所有套餐
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testGetComboList() throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("rs", new ArrayList<Map<String, Object>>());
        PowerMockito.when(CenterHttpRequest.sendGetRequest(anyString(), anyString())).thenReturn(returnMap);
        comboApiServiceImpl.getComboList();
    }

    /**
     * 测试添加套餐信息
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testAddCombo() throws Exception {
        comboApiServiceImpl.addCombo(anyObject());
    }

    /**
     * 测试查询套餐数量
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testCountComboByParam() throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("rs", 1);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(anyString(), anyString())).thenReturn(returnMap);
        comboApiServiceImpl.countComboByParam(anyObject());
    }

    /**
     * 测试删除套餐信息
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testRemoveCombo() throws Exception {
        comboApiServiceImpl.removeCombo(anyObject());
    }

    /**
     * 测试查询套餐配置数量
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testCountComboManageByParam() throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("rs", 1);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(anyString(), anyString())).thenReturn(returnMap);
        comboApiServiceImpl.countComboManageByParam(anyObject());
    }

    /**
     * 测试分页查询套餐与商户关系列表
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testGetComboManageList() throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("rs", new ArrayList<Map<String, Object>>());
        PowerMockito.when(CenterHttpRequest.sendGetRequest(anyString(), anyString())).thenReturn(returnMap);
        comboApiServiceImpl.getComboManageList(anyObject());
    }

    /**
     * 测试添加套餐配置信息
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testAddComboManage() throws Exception {
        comboApiServiceImpl.addComboManage(anyObject());
    }

    /**
     * 测试删除套餐配置信息
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testRemoveComboManage() throws Exception {
        comboApiServiceImpl.removeComboManage(anyObject());
    }
    
    /**
     * 测试套餐配置续时
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testContinueComboManage() throws Exception {
        comboApiServiceImpl.continueComboManage(anyObject());
    }

}

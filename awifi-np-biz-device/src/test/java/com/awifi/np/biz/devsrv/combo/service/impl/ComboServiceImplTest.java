/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月19日 下午5:19:12
* 创建作者：范立松
* 文件名称：ComboConfigServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.combo.service.impl;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

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

import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.api.client.dbcenter.combo.util.ComboClient;

@RunWith(PowerMockRunner.class)
@SuppressWarnings({ "rawtypes", "unchecked" })
@PrepareForTest({ MessageUtil.class, ComboClient.class, ValidUtil.class })
public class ComboServiceImplTest {

    /**被测试类*/
    @InjectMocks
    private ComboServiceImpl comboServiceImpl;

    /**初始化*/
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(ComboClient.class);
        PowerMockito.mockStatic(ValidUtil.class);
    }

    /**
     * 测试查询所有套餐
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testGetComboList() throws Exception {
        when(ComboClient.getComboList()).thenReturn(new ArrayList<>());
        Page page = new Page<>();
        page.setPageSize(10);
        comboServiceImpl.getComboList(page);
    }

    /**
     * 测试添加套餐信息
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testAddCombo() throws Exception {
        when(ComboClient.countComboByParam(anyObject())).thenReturn(0);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("downBand", 1);
        paramsMap.put("onlineTimeout", 32);
        comboServiceImpl.addCombo(paramsMap);
    }

    /**
     * 测试添加套餐信息(异常)
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test(expected = BizException.class)
    public void testAddComboException() throws Exception {
        when(ComboClient.countComboByParam(anyObject())).thenReturn(1);
        PowerMockito.doNothing().when(ValidUtil.class, "valid", anyObject(), anyObject(), anyObject());
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("error");
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("downBand", 1);
        paramsMap.put("onlineTimeout", 32);
        comboServiceImpl.addCombo(paramsMap);
    }

    /**
     * 测试删除套餐信息
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testRemoveCombo() throws Exception {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("id", 1);
        comboServiceImpl.removeCombo(paramsMap);
    }

    /**
     * 测试分页查询套餐与商户关系列表
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testGetComboManageList() throws Exception {
        when(ComboClient.countComboByParam(anyObject())).thenReturn(1);
        Page page = new Page<>();
        page.setPageSize(10);
        comboServiceImpl.getComboManageList(page, anyObject());
    }

    /**
     * 测试删除套餐配置信息
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testRemoveComboManage() throws Exception {
        String[] ids = {"1","2"};
        comboServiceImpl.removeComboManage(ids);
    }

    /**
     * 测试添加套餐配置信息
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testAddComboManage() throws Exception {
        when(ComboClient.countComboManageByParam(anyObject())).thenReturn(0);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("accountId", 1);
        comboServiceImpl.addCombo(paramsMap);
        comboServiceImpl.addComboManage(paramsMap);
    }

    /**
     * 测试添加套餐配置信息(异常)
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test(expected = BizException.class)
    public void testAddComboManageException() throws Exception {
        when(ComboClient.countComboManageByParam(anyObject())).thenReturn(1);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("accountId", 1);
        comboServiceImpl.addCombo(paramsMap);
        comboServiceImpl.addComboManage(paramsMap);
    }
    
    /**
     * 测试套餐配置续时
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testContinueComboManage() throws Exception {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("id", 1);
        comboServiceImpl.continueComboManage(paramsMap);
    }

}

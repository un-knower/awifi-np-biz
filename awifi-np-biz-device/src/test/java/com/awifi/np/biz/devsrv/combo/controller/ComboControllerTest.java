/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月19日 下午2:33:08
* 创建作者：范立松
* 文件名称：ComboController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.combo.controller;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.devsrv.combo.service.ComboService;

@RunWith(PowerMockRunner.class)
@SuppressWarnings("unchecked")
@PrepareForTest({ MessageUtil.class, JsonUtil.class, SysConfigUtil.class, CastUtil.class, ValidUtil.class })
public class ComboControllerTest {

    /**被测试类*/
    @InjectMocks
    private ComboController comboController;

    /** 套餐配置业务层 */
    @Mock(name = "comboService")
    private ComboService comboService;

    /**初始化*/
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(CastUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
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
        Map<String, Object> resultMap = comboController.getComboList("accessToken");
        Assert.assertNotNull(resultMap);
    }

    /**
     * 测试添加套餐信息
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月19日 下午2:59:46
     */
    @Test
    public void testAddCombo() throws Exception {
        Map<String, Object> bodyParam = new HashMap<String, Object>();
        bodyParam.put("downBand", 1);
        bodyParam.put("onlineTimeout", 32);
        Map<String, Object> resultMap = comboController.addCombo("accessToken", bodyParam);
        Assert.assertNotNull(resultMap);
    }

    /**
     * 测试删除套餐信息
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月19日 下午2:59:46
     */
    @Test
    public void testRemoveCombo() throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("id", 30);
        PowerMockito.when(JsonUtil.fromJson(anyString(), anyObject())).thenReturn(paramsMap);
        Map<String, Object> resultMap = comboController.removeCombo("accessToken", "");
        Assert.assertNotNull(resultMap);
    }

    /**
     * 测试分页查询套餐与商户关系列表
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月19日 下午2:59:46
     */
    @Test
    public void testGetComboManageList() throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("accountId", 23);
        paramsMap.put("pageSize", 10);
        PowerMockito.when(JsonUtil.fromJson(anyString(), anyObject())).thenReturn(paramsMap);
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(null);
        PowerMockito.when(SysConfigUtil.getParamValue("page_maxsize")).thenReturn("10");
        Map<String, Object> resultMap = comboController.getComboManageList("accessToken", "");
        Assert.assertNotNull(resultMap);
    }

    /**
     * 添加套餐配置信息
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月19日 下午2:59:46
     */
    @Test
    public void testAddComboManage() throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("accountId", 23);
        paramsMap.put("packageId", 7);
        paramsMap.put("expiredDate", "2017-03-31");
        paramsMap.put("remarks", "remarks");
        Map<String, Object> resultMap = comboController.addComboManage("accessToken", paramsMap);
        Assert.assertNotNull(resultMap);
    }

    /**
     * 删除套餐配置信息
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月19日 下午2:59:46
     */
    @Test
    public void testRemoveComboManage() throws Exception {
        String ids = "1,2";
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("error");
        PowerMockito.doNothing().when(ValidUtil.class, "valid", anyObject(), anyObject(), anyObject());
        Map<String, Object> resultMap = comboController.removeComboManage("accessToken", ids);
        Assert.assertNotNull(resultMap);
    }
    
    /**
     * 添加修改套餐配置信息
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月19日 下午2:59:46
     */
    @Test
    public void testUpdateComboManage() throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("expiredDate", "2017-05-31");
        paramsMap.put("remarks", "remarks");
        Map<String, Object> resultMap = comboController.updateComboManage("accessToken", "68", paramsMap);
        Assert.assertNotNull(resultMap);
    }

}

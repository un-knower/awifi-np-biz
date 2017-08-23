/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月19日 下午7:20:25
* 创建作者：范立松
* 文件名称：ComboClientTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.combo.util;

import static org.mockito.Matchers.anyObject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.combo.service.ComboApiService;
import com.awifi.np.biz.common.util.BeanUtil;

@SuppressWarnings("static-access")
@RunWith(PowerMockRunner.class)
@PrepareForTest({ BeanUtil.class })
public class ComboClientTest {

    /**被测试类*/
    @InjectMocks
    private ComboClient comboClient;

    /**
     * 配置服务
     */
    @Mock(name = "comboApiService")
    private ComboApiService comboApiService;

    /**初始化*/
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.when(BeanUtil.getBean(anyObject())).thenReturn(comboApiService);
    }

    /**
     * 测试查询所有套餐
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testGetComboList() throws Exception {
        comboClient.getComboList();
    }

    /**
     * 测试添加套餐信息
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testAddCombo() throws Exception {
        comboClient.addCombo(anyObject());
    }

    /**
     * 测试查询套餐数量
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testCountComboByParam() throws Exception {
        comboClient.countComboByParam(anyObject());
    }

    /**
     * 测试删除套餐信息
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testRemoveCombo() throws Exception {
        comboClient.removeCombo(anyObject());
    }

    /**
     * 测试查询套餐配置数量
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testCountComboManageByParam() throws Exception {
        comboClient.countComboManageByParam(anyObject());
    }

    /**
     * 测试分页查询套餐与商户关系列表
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testGetComboManageList() throws Exception {
        comboClient.getComboManageList(anyObject());
    }

    /**
     * 测试添加套餐配置信息
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testAddComboManage() throws Exception {
        comboClient.addComboManage(anyObject());
    }

    /**
     * 测试删除套餐配置信息
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testRemoveComboManage() throws Exception {
        comboClient.removeComboManage(anyObject());
    }

    /**
     * 测试获取bean对象
     * @author 范立松  
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testGetComboConfigApiService() {
        comboClient.getComboApiService();
    }
    
    /**
     * 测试套餐配置续时
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testContinueComboManage() throws Exception {
        comboClient.continueComboManage(anyObject());
    }

}

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月26日 下午5:14:19
* 创建作者：范立松
* 文件名称：FreeAuthApiServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.freeauth.impl;

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

import com.awifi.np.biz.api.client.dbcenter.freeauth.service.impl.FreeAuthApiServiceImpl;
import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ CenterHttpRequest.class, SysConfigUtil.class, JsonUtil.class, URLEncoder.class })
public class FreeAuthApiServiceImplTest {

    /**被测试类*/
    @InjectMocks
    private FreeAuthApiServiceImpl freeAuthApiServiceImpl;

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
     * 测试添加设备区域信息
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testAddDeviceArea() throws Exception {
        freeAuthApiServiceImpl.addDeviceArea(anyObject());
    }

    /**
     * 测试修改设备区域信息
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testUpdateDeviceArea() throws Exception {
        freeAuthApiServiceImpl.updateDeviceArea(anyObject());
    }

    /**
     * 测试分页设备查询区域列表
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testGetDeviceAreaList() throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("rs", new ArrayList<Map<String, Object>>());
        PowerMockito.when(CenterHttpRequest.sendGetRequest(anyString(), anyString())).thenReturn(returnMap);
        freeAuthApiServiceImpl.getDeviceAreaList(anyObject());
    }

    /**
     * 测试根据区域id删除设备区域信息以及设备与区域关联关系
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testRemoveDeviceAreaById() throws Exception {
        freeAuthApiServiceImpl.removeDeviceAreaById(anyObject());
    }

    /**
     * 测试批量添加设备与区域关联信息
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testAddDeviceAreaRel() throws Exception {
        freeAuthApiServiceImpl.addDeviceAreaRel(anyObject());
    }

    /**
     * 测试根据设备id删除设备与区域关系
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testRemoveRelByDevId() throws Exception {
        freeAuthApiServiceImpl.removeRelByDevId(anyObject());
    }

    /**
     * 测试分页设备查询区域列表
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testGetRelListByAreaId() throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("rs", new ArrayList<Map<String, Object>>());
        PowerMockito.when(CenterHttpRequest.sendGetRequest(anyString(), anyString())).thenReturn(returnMap);
        freeAuthApiServiceImpl.getRelListByAreaId(anyObject());
    }

    /**
     * 测试按条件查询设备区域记录数
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testQueryAreaCountByParam() throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("rs", 1);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(anyString(), anyString())).thenReturn(returnMap);
        freeAuthApiServiceImpl.queryAreaCountByParam(anyObject());
    }

    /**
     * 测试按条件查询设备与区域关联记录数
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testQueryRelCountByParam() throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("rs", 1);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(anyString(), anyString())).thenReturn(returnMap);
        freeAuthApiServiceImpl.queryRelCountByParam(anyObject());
    }

    /**
     * 测试分页查询设备与区域关联时可选择的设备列表
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年6月16日 上午11:17:05
     */
    @Test
    public void testGetChooseableDeviceList() throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("rs", new ArrayList<Map<String, Object>>());
        PowerMockito.when(CenterHttpRequest.sendGetRequest(anyString(), anyString())).thenReturn(returnMap);
        freeAuthApiServiceImpl.getChooseableDeviceList(anyObject());
    }

    /**
     * 测试按条件查询设备与区域关联时可选择的设备数量
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年6月16日 上午11:17:05
     */
    @Test
    public void testQueryChooseableDeviceCount() throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("rs", 1);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(anyString(), anyString())).thenReturn(returnMap);
        freeAuthApiServiceImpl.queryChooseableDeviceCount(anyObject());
    }

}

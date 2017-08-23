/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月2日 上午10:57:59
* 创建作者：范立松
* 文件名称：IotApiServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.iot.impl;

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

import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.api.client.dbcenter.iot.service.impl.IotApiServiceImpl;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ CenterHttpRequest.class, SysConfigUtil.class, JsonUtil.class, URLEncoder.class })
public class IotApiServiceImplTest {

    /**被测试类*/
    @InjectMocks
    private IotApiServiceImpl iotApiServiceImpl;

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
     * 测试分页设备查询区域列表
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testGetIotList() throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("rs", new ArrayList<Map<String, Object>>());
        PowerMockito.when(CenterHttpRequest.sendGetRequest(anyString(), anyString())).thenReturn(returnMap);
        iotApiServiceImpl.getIotList(anyObject());
    }

    /**
     * 测试根据区域id删除设备区域信息以及设备与区域关联关系
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testRemoveDeviceAreaById() throws Exception {
        iotApiServiceImpl.removeIotByIds(anyObject());
    }

    /**
     * 测试按条件查询设备区域记录数
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testCountIotByParam() throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("rs", 1);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(anyString(), anyString())).thenReturn(returnMap);
        iotApiServiceImpl.countIotByParam(anyObject());
    }

}

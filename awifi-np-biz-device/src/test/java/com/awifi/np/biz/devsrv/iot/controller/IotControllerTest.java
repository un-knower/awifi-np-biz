/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月2日 上午9:42:55
* 创建作者：范立松
* 文件名称：IotControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.iot.controller;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.devsrv.iot.service.IotService;

@RunWith(PowerMockRunner.class)
@SuppressWarnings("unchecked")
@PrepareForTest({ JsonUtil.class, SysConfigUtil.class, CastUtil.class, ValidUtil.class, MessageUtil.class })
public class IotControllerTest {

    /**被测试类*/
    @InjectMocks
    private IotController iotController;

    /** 物联网设备管理业务层 */
    @Mock(name = "iotService")
    private IotService iotService;

    /**初始化*/
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(CastUtil.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(ValidUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
    }

    /**
     * 测试分页查询物联网设备列表
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test
    public void testGetIotList() throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("pageSize", 10);
        PowerMockito.when(JsonUtil.fromJson(anyString(), anyObject())).thenReturn(paramsMap);
        PowerMockito.when(CastUtil.toInteger(anyObject())).thenReturn(null);
        PowerMockito.when(SysConfigUtil.getParamValue("page_maxsize")).thenReturn("10");
        Map<String, Object> resultMap = iotController.getIotList("accessToken", "params");
        Assert.assertNotNull(resultMap);
    }

    /**
     * 测试根据物联网设备id删除设备信息
     * @throws Exception  异常
     * @author 范立松  
     * @date 2017年4月26日 下午3:51:01
     */
    @Test(expected = ValidException.class)
    public void testRemoveIotById() throws Exception {
        List<String> idList = new ArrayList<>();
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("error");
        Map<String, Object> resultMap = iotController.removeIotById("accessToken", idList);
        Assert.assertNotNull(resultMap);
    }

}

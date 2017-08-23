package com.awifi.np.biz.api.client.dbcenter.device.hotarea.service.impl;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

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

import com.awifi.np.biz.api.client.dbcenter.device.hotarea.model.Hotarea;
import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月20日 上午10:36:17
 * 创建作者：亢燕翔
 * 文件名称：HotareaApiServiceImplTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({CenterHttpRequest.class,SysConfigUtil.class,JsonUtil.class})
public class HotareaApiServiceImplTest {

    /**被测试类*/
    @InjectMocks
    private HotareaApiServiceImpl hotareaApiServiceImpl;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(CenterHttpRequest.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(JsonUtil.class);
    }
    
    /**
     * 测试实体
     * @author 亢燕翔  
     * @date 2017年3月22日 下午4:40:21
     */
    @Test
    public void testModel(){
        Hotarea hotarea = new Hotarea();
        hotarea.getStatusDsp();
        hotarea.getId();
        hotarea.getMerchantName();
        hotarea.getHotareaName();
        hotarea.getDevMac();
        hotarea.getStatus();
        hotarea.toString();
    }
    
    /**
     * 测试热点管理获取总数
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年3月20日 上午10:38:44
     */
    @Test
    public void testgetCountByParam() throws Exception{
        Map<String, Object> returnMap = new HashMap<String,Object>();
        returnMap.put("rs", 10);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(anyString(),anyString())).thenReturn(returnMap);
        hotareaApiServiceImpl.getCountByParam("xxx");
    }
    
    /**
     * 测试热点管理列表
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月20日 上午10:41:46
     */
    @Test
    public void testGetListByParam() throws Exception{
        List<Object> list = new ArrayList<Object>();
        Map<String, Object> map = new HashMap<String,Object>();
        list.add(map);
        Map<String, Object> returnMap = new HashMap<String,Object>();
        returnMap.put("rs", list);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(anyString(),anyString())).thenReturn(returnMap);
        hotareaApiServiceImpl.getListByParam("xxx");
    }
    
    /**
     * 测试批量导入热点
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月20日 上午10:43:28
     */
    @Test
    public void testBatchAddRelation() throws Exception{
        hotareaApiServiceImpl.batchAddRelation(anyObject());
    }

    /**
     * 测试删除热点
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月20日 上午10:44:24
     */
    @Test
    public void testDeleteByDevMacs() throws Exception{
        hotareaApiServiceImpl.deleteByDevMacs(anyObject());
    }
    
}


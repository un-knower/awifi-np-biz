/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月20日 下午6:29:48
* 创建作者：范立松
* 文件名称：BrasAcServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.brasac.service.impl;

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

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubEntity;
import com.awifi.np.biz.api.client.dbcenter.fatap.util.DeviceQueryClient;
import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.enums.FlowSts;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.HttpRequest;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;

@RunWith(PowerMockRunner.class)
@SuppressWarnings({ "rawtypes", "unchecked" })
@PrepareForTest({ CastUtil.class, DeviceClient.class, DeviceQueryClient.class, MessageUtil.class, JsonUtil.class,
        CenterHttpRequest.class, SysConfigUtil.class, HttpRequest.class, FlowSts.class })
public class BrasAcServiceImplTest {

    /**被测试类*/
    @InjectMocks
    private BrasAcServiceImpl brasAcServiceImpl;

    /**初始化*/
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(CastUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(DeviceClient.class);
        PowerMockito.mockStatic(DeviceQueryClient.class);
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(CenterHttpRequest.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(HttpRequest.class);
        PowerMockito.mockStatic(FlowSts.class);
    }

    /**
     * 测试提交审核
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testBatchUpdateFlowSts() throws Exception {
        brasAcServiceImpl.batchUpdateFlowSts(anyObject());
    }

    /**
     * 测试根据设备id查询
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testQueryBrasAcById() throws Exception {
        CenterPubEntity entity = new CenterPubEntity();
        entity.setIpAddr("ip");
        entity.setEntityName("name");
        entity.setMaxBw(0L);
        entity.setMaxCapc(0L);
        entity.setMaxDevconn(0L);
        entity.setMaxStaconn(0L);
        List<Map<String, Object>> recordList = new ArrayList<>();
        Map<String, Object> record = new HashMap<>();
        recordList.add(record);
        PowerMockito.when(DeviceQueryClient.queryEntityInfoById(anyObject())).thenReturn(entity);
        PowerMockito.when(DeviceClient.countNasFilterByParam(anyObject())).thenReturn(1);
        PowerMockito.when(DeviceClient.getNasFilterList(anyObject())).thenReturn(recordList);
        brasAcServiceImpl.queryBrasAcById(anyObject());
    }

    /**
     * 测试入库查询
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testQueryBrasAcList() throws Exception {
        PowerMockito.when(DeviceQueryClient.queryEntityCountByParam(anyObject())).thenReturn(10);
        Page page = new Page<>();
        page.setPageSize(10);
        brasAcServiceImpl.queryBrasAcList(page, anyObject());
    }

    /**
     * 测试添加白名单
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testAddWhiteList() throws Exception {
        CenterPubEntity entity = new CenterPubEntity();
        entity.setIpAddr("ip");
        entity.setEntityName("name");
        entity.setEntityType(11);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resultCode", "000");
        PowerMockito.when(DeviceQueryClient.queryEntityInfoById(anyObject())).thenReturn(entity);
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("url");
        PowerMockito.when(HttpRequest.sendPost(anyString(), anyString())).thenReturn(jsonObject.toJSONString());
        List<String> params = new ArrayList<>();
        params.add("50021");
        brasAcServiceImpl.addWhiteList(params, anyObject());
    }

    /**
     * 测试添加白名单
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testAddWhiteList1() throws Exception {
        CenterPubEntity entity = new CenterPubEntity();
        entity.setId("id");
        entity.setIpAddr("ip");
        entity.setEntityName("name");
        entity.setEntityType(11);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resultCode", "000");
        PowerMockito.when(JsonUtil.toJson(anyObject()))
                .thenReturn("{\"id\":\"id\",\"ipAddr\":\"ip\",\"entityName\":\"name\",\"entityType\":11}");
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("url");
        PowerMockito.when(HttpRequest.sendPost(anyString(), anyString())).thenReturn(jsonObject.toJSONString());
        Map<String, Object> paramsMap = new HashMap<>();
        brasAcServiceImpl.addWhiteList(paramsMap, anyObject());
    }

    /**
     * 测试更新bras
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testUpdateBras() throws Exception {
        CenterPubEntity entity = new CenterPubEntity();
        entity.setOutTypeId("AWIFI");
        entity.setEntityName("entityName");
        entity.setIpAddr("ipAddr");
        entity.setFlowSts(2);
        PowerMockito.when(DeviceQueryClient.queryEntityInfoById(anyObject())).thenReturn(entity);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("id", "id");
        paramsMap.put("entityName", "entityName");
        paramsMap.put("ipAddr", "ipAddr");
        paramsMap.put("macAddr", "macAddr");
        brasAcServiceImpl.updateBras(paramsMap);
    }

    /**
     * 测试更新ac
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testUpdateAc() throws Exception {
        CenterPubEntity entity = new CenterPubEntity();
        entity.setOutTypeId("AWIFI");
        entity.setEntityName("entityName");
        entity.setIpAddr("ipAddr");
        entity.setFlowSts(2);
        PowerMockito.when(DeviceQueryClient.queryEntityInfoById(anyObject())).thenReturn(entity);
        PowerMockito.when(CastUtil.toString(anyObject())).thenReturn("192.168.1.1");
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("id", "id");
        paramsMap.put("entityName", "entityName");
        paramsMap.put("ipAddr", "ipAddr");
        paramsMap.put("macAddr", "macAddr");
        brasAcServiceImpl.updateAc(paramsMap);
    }

    /**
     * 测试添加bras
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testAddBras() throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("ipAddr", "ipAddr");
        brasAcServiceImpl.addBras(paramsMap);
    }

    /**
     * 测试添加ac
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test
    public void testAddAc() throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("ipAddr", "ipAddr");
        brasAcServiceImpl.addAc(paramsMap);
    }

    /**
     * 测试添加ac
     * @author 范立松  
     * @throws Exception 异常
     * @date 2017年4月19日 下午2:41:52
     */
    @Test(expected = BizException.class)
    public void testCheckMacExist() throws Exception {
        PowerMockito.when(DeviceQueryClient.queryEntityCountByParam(anyObject())).thenReturn(1);
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("error");
        brasAcServiceImpl.checkMacExist("mac");
    }

    /**
     * 测试删除设备
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年6月16日 上午9:29:22
     */
    @Test
    public void testRemoveBrasAc() throws Exception {
        CenterPubEntity entity = new CenterPubEntity();
        entity.setIpAddr("ip");
        entity.setEntityName("name");
        entity.setEntityType(11);
        entity.setOutTypeId("AWIFI");
        PowerMockito.when(DeviceQueryClient.queryEntityInfoById(anyObject())).thenReturn(entity);
        String[] ids = { "1", "2" };
        brasAcServiceImpl.removeBrasAc(ids);
    }

    /**
     * 向设备总线发送注册Nas设备请求
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年6月16日 上午10:54:53
     */
    @Test
    public void testCreateNas() throws Exception {
        List<JSONObject> msgList = new ArrayList<>();
        brasAcServiceImpl.createNas("id", null, msgList);
    }

    /**
     * 向设备总线发送注册Nas设备请求
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年6月16日 上午10:54:53
     */
    @Test
    public void testCreateNas1() throws Exception {
        List<JSONObject> msgList = new ArrayList<>();
        CenterPubEntity entity = new CenterPubEntity();
        entity.setId("id");
        brasAcServiceImpl.createNas("id", entity, msgList);
    }

    /**
     * 向设备总线发送注册Nas设备请求
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年6月16日 上午10:54:53
     */
    @Test
    public void testCreateNas2() throws Exception {
        List<JSONObject> msgList = new ArrayList<>();
        CenterPubEntity entity = new CenterPubEntity();
        entity.setId("id");
        entity.setIpAddr("ip");
        brasAcServiceImpl.createNas("id", entity, msgList);
    }

    /**
     * 向设备总线发送注册Nas设备请求
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年6月16日 上午10:54:53
     */
    @Test
    public void testCreateNas3() throws Exception {
        List<JSONObject> msgList = new ArrayList<>();
        CenterPubEntity entity = new CenterPubEntity();
        entity.setId("id");
        entity.setIpAddr("ip");
        entity.setEntityName("name");
        brasAcServiceImpl.createNas("id", entity, msgList);
    }
    
    /**
     * 检查设备是否满足操作条件
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年6月16日 上午11:03:01
     */
    @Test
    public void testCheckEntityInfo() throws Exception {
        List<JSONObject> msgList = new ArrayList<>();
        PowerMockito.when(DeviceQueryClient.queryEntityInfoById(anyObject())).thenReturn(null);
        brasAcServiceImpl.checkEntityInfo("id", msgList);
    }
    
    /**
     * 检查设备是否满足操作条件
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年6月16日 上午11:03:01
     */
    @Test
    public void testCheckEntityInfo1() throws Exception {
        List<JSONObject> msgList = new ArrayList<>();
        CenterPubEntity entity = new CenterPubEntity();
        entity.setId("id");
        entity.setEntityName("name");
        PowerMockito.when(DeviceQueryClient.queryEntityInfoById(anyObject())).thenReturn(entity);
        brasAcServiceImpl.checkEntityInfo("id", msgList);
    }
    
    /**
     * 检查设备是否满足操作条件
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年6月16日 上午11:03:01
     */
    @Test
    public void testCheckEntityInfo2() throws Exception {
        List<JSONObject> msgList = new ArrayList<>();
        CenterPubEntity entity = new CenterPubEntity();
        entity.setId("id");
        entity.setEntityName("name");
        entity.setEntityType(11);
        entity.setOutTypeId("AWIFI");
        entity.setMerchantId(11L);
        PowerMockito.when(DeviceQueryClient.queryEntityInfoById(anyObject())).thenReturn(entity);
        brasAcServiceImpl.checkEntityInfo("id", msgList);
    }

}

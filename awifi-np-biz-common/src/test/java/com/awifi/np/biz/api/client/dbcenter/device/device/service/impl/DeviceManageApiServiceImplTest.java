/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月4日 下午4:28:17
* 创建作者：王冬冬
* 文件名称：DeviceManageApiServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.device.device.service.impl;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyLong;
import java.net.URLEncoder;
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

import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubEntity;
import com.awifi.np.biz.api.client.dbcenter.fatap.util.DeviceQueryClient;
import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CenterHttpRequest.class,SysConfigUtil.class,URLEncoder.class,RedisUtil.class,DeviceQueryClient.class})
public class DeviceManageApiServiceImplTest {

    /**
     * 被测试类
     */
    @InjectMocks
    private DevcieManageApiServiceImpl deviceManageApiServiceImpl;
    
    /**
     * 初始化
     * @author 王冬冬
     * @date 2017年3月23日 上午10:44:41
     */
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(CenterHttpRequest.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
//        PowerMockito.mockStatic(JsonUtil.class);   
        PowerMockito.mockStatic(URLEncoder.class);   
        PowerMockito.mockStatic(RedisUtil.class);   
        PowerMockito.mockStatic(DeviceQueryClient.class); 
    }
    
    /**
     * 测试修改实体设备的流程状态
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月20日 上午9:52:32
     */
    @Test
    public void testupdateEntityFlowSts() throws Exception{
        List<Object> list = new ArrayList<Object>();
        Map<String, Object> map = new HashMap<String,Object>();
        list.add(map);
        Map<String, Object> returnMap = new HashMap<String,Object>();
        returnMap.put("rs", list);
        PowerMockito.when(CenterHttpRequest.sendPutRequest(anyObject(), anyObject())).thenReturn(null);
        PowerMockito.when(SysConfigUtil.getParamValue("dbc_DeviceManageUpdateEntityFlowSts_url")).thenReturn("xxx");
        deviceManageApiServiceImpl.updateEntityFlowSts(map);
    }
    
    /**
     * 测试逻辑删除一台或者多台设备
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月20日 上午9:52:32
     */
    @Test
    public void testdeleteEntityByIds() throws Exception{
        String[] ids={"1","3"};
        
        List<Object> list = new ArrayList<Object>();
        Map<String, Object> map = new HashMap<String,Object>();
        list.add(map);
        Map<String, Object> returnMap = new HashMap<String,Object>();
        returnMap.put("rs", list);
        PowerMockito.when(CenterHttpRequest.sendDeleteRequest(anyObject(),anyObject())).thenReturn(null);
        PowerMockito.when(SysConfigUtil.getParamValue("dbc_DeviceManageDeleteEntityByIds_url")).thenReturn("xxx");
        deviceManageApiServiceImpl.deleteEntityByIds(ids);
    }
    
    /**
     * 测试插入一批设备
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月20日 上午9:52:32
     */
    @Test
    public void testaddEntityBatch() throws Exception{
        
        List<Object> list = new ArrayList<Object>();
        Map<String, Object> map = new HashMap<String,Object>();
        list.add(map);
        Map<String, Object> returnMap = new HashMap<String,Object>();
        returnMap.put("rs", list);
        PowerMockito.when(CenterHttpRequest.sendPostRequest(anyObject(),anyObject())).thenReturn(null);
        PowerMockito.when(SysConfigUtil.getParamValue("dbc_DeviceManageAddEntityBatch_url")).thenReturn("xxx");
        List<CenterPubEntity> sublist=new ArrayList<CenterPubEntity>();
        deviceManageApiServiceImpl.addEntityBatch(sublist);
    }
    
    /**
     * 测试插入一批设备
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月20日 上午9:52:32
     */
    @Test
    public void testaddPFitAPBatch() throws Exception{
        
        List<Object> list = new ArrayList<Object>();
        Map<String, Object> map = new HashMap<String,Object>();
        list.add(map);
        Map<String, Object> returnMap = new HashMap<String,Object>();
        returnMap.put("rs", list);
        PowerMockito.when(CenterHttpRequest.sendPostRequest(anyObject(),anyObject())).thenReturn(null);
        PowerMockito.when(SysConfigUtil.getParamValue("dbc_DeviceManageAddPFitAPBatch_url")).thenReturn("xxx");
        List<CenterPubEntity> sublist=new ArrayList<CenterPubEntity>();
        deviceManageApiServiceImpl.addEntityBatch(sublist);
    }
    
    
    /**
     * 测试插入一批设备
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月20日 上午9:52:32
     */
    @Test
    public void testupdateEntity() throws Exception{
        List<Object> list = new ArrayList<Object>();
        Map<String, Object> map = new HashMap<String,Object>();
        list.add(map);
        Map<String, Object> returnMap = new HashMap<String,Object>();
        returnMap.put("rs", list);
        PowerMockito.when(CenterHttpRequest.sendPutRequest(anyObject(),anyObject())).thenReturn(null);
        PowerMockito.when(SysConfigUtil.getParamValue("dbc_DeviceManageUpdateEntity_url")).thenReturn("xxx");
        List<CenterPubEntity> sublist=new ArrayList<CenterPubEntity>();
        CenterPubEntity entity=new CenterPubEntity();
        deviceManageApiServiceImpl.updateEntity(entity);
    }
    
    
    /**
     * 测试逻辑删除一台或者多台设备
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月20日 上午9:52:32
     */
    @Test
    public void testdeleteDeviceByDeviceIds() throws Exception{
        String[] ids={"1","3"};
        
        List<Object> list = new ArrayList<Object>();
        Map<String, Object> map = new HashMap<String,Object>();
        list.add(map);
        Map<String, Object> returnMap = new HashMap<String,Object>();
        returnMap.put("rs", list);
        PowerMockito.when(CenterHttpRequest.sendDeleteRequest(anyObject(),anyObject())).thenReturn(null);
        PowerMockito.when(SysConfigUtil.getParamValue("dbc_DeviceManageDeleteDeviceByDeviceIds_url")).thenReturn("xxx");
        deviceManageApiServiceImpl.deleteEntityByIds(ids);
    }
    
    
    /**
     * 测试更新一批设备
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月20日 上午9:52:32
     */
    @Test
    public void testUpdateEntity() throws Exception{
        List<Object> list = new ArrayList<Object>();
        Map<String, Object> map = new HashMap<String,Object>();
        list.add(map);
        Map<String, Object> returnMap = new HashMap<String,Object>();
        returnMap.put("rs", list);
        PowerMockito.when(CenterHttpRequest.sendPutRequest(anyObject(),anyObject())).thenReturn(null);
        PowerMockito.when(SysConfigUtil.getParamValue("dbc_DeviceManageUpdateEntity_url")).thenReturn("xxx");
        List<CenterPubEntity> sublist=new ArrayList<CenterPubEntity>();
        CenterPubEntity entity=new CenterPubEntity();
        deviceManageApiServiceImpl.updateEntity(map);
    }
    
    /**
     * 测试逻辑删除一台或者多台设备
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月20日 上午9:52:32
     */
    @Test
    public void testdeleteHotareaByIds() throws Exception{
        String[] ids={"1","3"};
        List<Object> list = new ArrayList<Object>();
        Map<String, Object> map = new HashMap<String,Object>();
        list.add(map);
        Map<String, Object> returnMap = new HashMap<String,Object>();
        returnMap.put("rs", list);
        Map<String,Object> result=new HashMap<String,Object>();
        result.put("merchantId", 0);
        PowerMockito.when(DeviceQueryClient.queryHotareaInfoById(anyLong())).thenReturn(result);
        PowerMockito.when(CenterHttpRequest.sendDeleteRequest(anyObject(),anyObject())).thenReturn(null);
        PowerMockito.when(SysConfigUtil.getParamValue("dbc_DeviceManageDeleteHotareaByIds_url")).thenReturn("xxx");
        deviceManageApiServiceImpl.deleteHotareaByIds(ids);
    }
    
    
    /**
     * 测试更新一批设备
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月20日 上午9:52:32
     */
    @Test
    public void testUpdateHotarea() throws Exception{
        List<Object> list = new ArrayList<Object>();
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("id", "1");
        map.put("province", "1");
        map.put("city", "1");
        map.put("county", "1");
        map.put("hotareaName", "dd");
        map.put("acName", "dd");
        map.put("brasName", "dd");
        map.put("pvlan", "dd");
        map.put("cvlan", "dd");
        
        Map<String, Object> returnMap = new HashMap<String,Object>();
        returnMap.put("rs", list);
        PowerMockito.when(DeviceQueryClient.queryHotareaInfoById(anyLong())).thenReturn(returnMap);
        PowerMockito.when(CenterHttpRequest.sendPutRequest(anyObject(),anyObject())).thenReturn(null);
        PowerMockito.when(SysConfigUtil.getParamValue("dbc_DeviceManageUpdateEntity_url")).thenReturn("xxx");
        List<CenterPubEntity> sublist=new ArrayList<CenterPubEntity>();
        CenterPubEntity entity=new CenterPubEntity();
        deviceManageApiServiceImpl.updateHotarea(map);
    }
    
    /**
     * 测试插入一批设备
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月20日 上午9:52:32
     */
    @Test
    public void testaddHotareaBatch() throws Exception{
        List<Object> list = new ArrayList<Object>();
        Map<String, Object> map = new HashMap<String,Object>();
        list.add(map);
        Map<String, Object> returnMap = new HashMap<String,Object>();
        returnMap.put("rs", list);
        PowerMockito.when(CenterHttpRequest.sendPostRequest(anyObject(),anyObject())).thenReturn(null);
        PowerMockito.when(SysConfigUtil.getParamValue("dbc_DeviceManageAddHotareaBatch_url")).thenReturn("xxx");
        List<CenterPubEntity> sublist=new ArrayList<CenterPubEntity>();
        deviceManageApiServiceImpl.addHotareaBatch(sublist);
    }
}

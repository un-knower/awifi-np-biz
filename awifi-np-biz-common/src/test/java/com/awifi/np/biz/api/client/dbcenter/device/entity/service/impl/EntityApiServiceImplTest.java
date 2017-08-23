package com.awifi.np.biz.api.client.dbcenter.device.entity.service.impl;

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

import com.awifi.np.biz.api.client.dbcenter.device.entity.model.EntityInfo;
import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月20日 上午10:18:10
 * 创建作者：亢燕翔
 * 文件名称：EntityApiServiceImplTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({CenterHttpRequest.class,SysConfigUtil.class,JsonUtil.class})
public class EntityApiServiceImplTest {

    /**被测试类*/
    @InjectMocks
    private EntityApiServiceImpl entityApiServiceImpl;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(CenterHttpRequest.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(JsonUtil.class);   
    }
    
    /**
     * 测试设备实体
     * @author 亢燕翔  
     * @date 2017年3月22日 下午4:36:27
     */
    @Test
    public void testModel(){
        EntityInfo entityInfo = new EntityInfo();
        entityInfo.getStatusDsp();
        entityInfo.getDevMac();
        entityInfo.getSsid();
        entityInfo.getAcName();
        entityInfo.getEntityType();
        entityInfo.getEntityTypeDsp();
        entityInfo.getOnlineNum();
        entityInfo.getStatus();
        entityInfo.toString();
    }
    
    /**
     * 测试设备监控查询总数
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月20日 上午10:33:32
     */
    @Test
    public void testGetEntityInfoCountByMerId() throws Exception{
        Map<String, Object> returnMap = new HashMap<String,Object>();
        returnMap.put("rs", 10);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(anyString(),anyString())).thenReturn(returnMap);
        entityApiServiceImpl.getEntityInfoCountByMerId("xxx");
    }
    
    /**
     * 测试设备监控列表
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月20日 上午10:33:49
     */
    /*@Test
    public void testGetEntityInfoListByMerId() throws Exception{
        List<Object> list = new ArrayList<Object>();
        Map<String, Object> map = new HashMap<String,Object>();
        list.add(map);
        Map<String, Object> returnMap = new HashMap<String,Object>();
        returnMap.put("rs", list);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(anyString(),anyString())).thenReturn(returnMap);
        entityApiServiceImpl.getEntityInfoListByMerId("xxx");
    }*/
    
    /**
     * 测试编辑设备
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年3月20日 上午10:34:48
     */
    @Test
    public void testUpdate() throws Exception{
        entityApiServiceImpl.update("xxx");
    }
    
}

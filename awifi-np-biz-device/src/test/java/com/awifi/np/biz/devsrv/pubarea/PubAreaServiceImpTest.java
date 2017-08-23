/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月7日 下午4:21:15
* 创建作者：王冬冬
* 文件名称：PubAreaServiceImpTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.pubarea;

import static org.mockito.Matchers.anyObject;

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
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.location.util.LocationClient;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.devsrv.hotarea.controller.HotareaController;
import com.awifi.np.biz.devsrv.hotarea.service.HotareaService;
import com.awifi.np.biz.devsrv.pubarea.servcie.impl.PubAreaServiceImpl;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LocationClient.class})
@PowerMockIgnore({"javax.management.*"})
public class PubAreaServiceImpTest {
    
    /**被测试类*/
    @InjectMocks
    private PubAreaServiceImpl pubAreaService;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(LocationClient.class);
    }
 
    /**
     * 测试查询所有区域
     * @throws Exception 异常 
     * @author 亢燕翔  
     * @date 2017年2月14日 下午2:53:13
     */
    @Test
    public void testqueryAllArea() throws Exception{
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("type","PROVINCE");
        map.put("name","zhejiang");
        map.put("fullName","zhejiang");
        map.put("parentId",1L);

        Map<Long, Map<String, Object>> value=new HashMap<Long, Map<String, Object>>();
        value.put(1L, map);
        PowerMockito.when(LocationClient.getAllLocation()).thenReturn(value);
        
        pubAreaService.queryAllArea();
//        String accessToken = "XXX";
//        Map<String, Object> resultMap = hotareaController.deleteByDevMacs(accessToken, anyObject());
//        Assert.assertNotNull(resultMap);
    }

    @Test
    public void testqueryByParam() throws Exception{
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("type","PROVINCE");
        map.put("name","zhejiang");
        map.put("fullName","zhejiang");
        map.put("parentId",1L);

        Map<String,Object> map1=new HashMap<String,Object>();
        map1.put("type","CITY");
        map1.put("name","zhejiang");
        map1.put("fullName","zhejiang");
        map1.put("parentId",31L);
        
        Map<String,Object> map2=new HashMap<String,Object>();
        map2.put("type","COUNTY");
        map2.put("name","zhejiang");
        map2.put("fullName","zhejiang");
        map2.put("parentId",3660L);
        
        Map<Long, Map<String, Object>> value=new HashMap<Long, Map<String, Object>>();
        value.put(1L, map);
        value.put(31L, map1);
        value.put(3660L, map2);
        PowerMockito.when(LocationClient.getAllLocation()).thenReturn(value);
        
        pubAreaService.queryByParam("zhejiang", "PROVINCE", 1L);
        pubAreaService.queryByParam("zhejiang", "COUNTY", 3660L);
        pubAreaService.queryByParam("zhejiang", "CITY", 31L);
//        String accessToken = "XXX";
//        Map<String, Object> resultMap = hotareaController.deleteByDevMacs(accessToken, anyObject());
//        Assert.assertNotNull(resultMap);
    }

}

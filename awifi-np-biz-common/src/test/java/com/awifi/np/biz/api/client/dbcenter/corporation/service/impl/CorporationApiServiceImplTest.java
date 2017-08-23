/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月17日 上午11:37:26
* 创建作者：范涌涛
* 文件名称：corporationApiServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.corporation.service.impl;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyObject;
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

import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
//import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CenterHttpRequest.class,SysConfigUtil.class,CastUtil.class,JsonUtil.class})
public class CorporationApiServiceImplTest {
	
    /**被测试类**/
    @InjectMocks
    private CorporationApiServiceImpl  corporationApiServiceImpl;
    
    /**
     * 初始化
     * @author 范涌涛  
     * @date 2017年4月17日 下午8:47:24
     */
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(CenterHttpRequest.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(CastUtil.class);
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(URLEncoder.class);
        
        //        PowerMockito.when();
    }
    
    /**
     * 分页查询厂商测试方法
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月17日 下午8:47:55
     */
    @Test
    public void testQueryListByParam() throws Exception{
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("url_test");
        PowerMockito.when(JsonUtil.toJson(anyString())).thenReturn("json_test"); 
        PowerMockito.when(URLEncoder.encode(anyObject(),anyString())).thenReturn("param_test");
        Map<String, Object> returnMap = new HashMap<String,Object>();
        returnMap.put("id", 1);
        returnMap.put("corpCode","11");
        returnMap.put("corpName","H3C");
        returnMap.put("email", "test@qq.com");
        returnMap.put("phone", "testNO");
        returnMap.put("status", 1);
        Map<String, Object> resMap = new HashMap<String,Object>();
        List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
        returnList.add(returnMap);
        resMap.put("rs", returnList);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(anyString(),anyString())).thenReturn(resMap);
    	corporationApiServiceImpl.queryCorpListByParam(null);
    }
    
    /**
     * 查询符合条件的记录记录数
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月17日 下午8:48:38
     */
    @Test 
    public void testQueryCountByParam() throws Exception{
        PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("url_test");
        PowerMockito.when(JsonUtil.toJson(anyString())).thenReturn("json_test"); 
        PowerMockito.when(URLEncoder.encode(anyObject(),anyString())).thenReturn("param_test");
        
        Map<String, Object> returnMap = new HashMap<String,Object>();
        Map<String, Object> reqMap = new HashMap<String,Object>();
        returnMap.put("rs", 1);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(anyString(),anyString())).thenReturn(returnMap);
        corporationApiServiceImpl.queryCorpCountByParam(reqMap);
    }
}

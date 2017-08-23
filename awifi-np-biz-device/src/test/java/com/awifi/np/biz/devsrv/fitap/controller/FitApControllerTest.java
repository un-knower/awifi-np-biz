/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月20日 下午1:50:42
* 创建作者：伍恰
* 文件名称：FitApControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.fitap.controller;


import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.awifi.np.biz.api.client.dbcenter.fatap.util.DeviceQueryClient;
import com.awifi.np.biz.api.client.dbcenter.fitap.util.FitApClient;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.devsrv.common.MockBase;
@PrepareForTest({JsonUtil.class,FitApClient.class,DeviceQueryClient.class})
public class FitApControllerTest extends MockBase{
    /**
     * 被测试类
     */
    @InjectMocks
    private FitApController fitApController;
    /**
     * 
     * 初始化
     * @author 伍恰  
     * @date 2017年6月20日 下午2:03:28
     */
    @Before
    public void befor(){
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(FitApClient.class);
        PowerMockito.mockStatic(DeviceQueryClient.class);
    }
    /**
     * 分页查询瘦AP
     * @throws Exception 异常
     * @author 伍恰  
     * @date 2017年6月20日 下午1:54:55
     */
    @Test
    public void testQueryFitApList() throws Exception {
        String params = "{\"outTypeId\":\"CHINANET\"}";
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("outTypeId", "CHINANET");
        PowerMockito.when(JsonUtil.fromJson(params,Map.class)).thenReturn(resultMap);
        fitApController.queryFitApList(access_token,params);
        resultMap.put("outTypeId", "AWIFI");
        PowerMockito.when(JsonUtil.fromJson(params,Map.class)).thenReturn(resultMap);
        fitApController.queryFitApList(access_token,params);
    }

}

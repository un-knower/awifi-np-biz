/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月13日 下午2:39:32
* 创建作者：伍恰
* 文件名称：FitApClientTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.fitap.util;


import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.fitap.service.FitApApiService;
import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;

@SuppressWarnings("static-access")
@RunWith(PowerMockRunner.class)
@PrepareForTest({CenterHttpRequest.class,SysConfigUtil.class, MessageUtil.class, BeanUtil.class,JsonUtil.class})
public class FitApClientTest {
    /**
     * 被测试类
     */
    @InjectMocks
    private  FitApClient fitApClient;
    /**
     * 分页查询chinanet瘦AP服务类
     */
    @Mock(name = "fitApApiService")
    private FitApApiService fitApApiService;
    /**
     * 初始化方法
     * @author 伍恰  
     * @date 2017年6月13日 下午3:13:09
     */
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(CenterHttpRequest.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.when(JsonUtil.toJson(anyObject())).thenReturn("result");
    }
    /**
     * 分页查询chinanet瘦AP
     * @throws Exception 异常
     * @author 伍恰  
     * @date 2017年6月13日 下午3:12:46
     */
    @Test
    public void testQueryChinanetDevInfoList() throws Exception {
        Map<String, Object> reqParam = new HashMap<>();
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("suc", true);
        returnMap.put("rs", 10);
        PowerMockito.when(BeanUtil.getBean(anyObject())).thenReturn(fitApApiService);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(anyString(), anyString()))
                .thenReturn(returnMap);
        fitApClient.queryChinanetDevInfoListByParam(reqParam );
    }

}


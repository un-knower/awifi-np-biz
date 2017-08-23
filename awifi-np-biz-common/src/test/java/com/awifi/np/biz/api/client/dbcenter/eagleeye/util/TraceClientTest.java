/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年8月22日 下午3:31:16
* 创建作者：尤小平
* 文件名称：TraceClientTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.eagleeye.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;

import com.awifi.np.biz.api.client.dbcenter.eagleeye.model.Span;
import com.awifi.np.biz.api.client.dbcenter.eagleeye.service.TraceApiService;
import com.awifi.np.biz.api.client.dbcenter.eagleeye.service.impl.TraceApiServiceImpl;
import com.awifi.np.biz.common.util.BeanUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("static-access")
@RunWith(PowerMockRunner.class)
@PrepareForTest({ BeanUtil.class })
public class TraceClientTest {
    /**
     * 被测试类
     */
    @InjectMocks
    private TraceClient client;

    /**
     * TraceApiService
     */
    private static TraceApiService traceApiService;

    /**
     * init.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年8月22日 下午4:01:49
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        traceApiService = Mockito.mock(TraceApiServiceImpl.class);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.when(BeanUtil.getBean(any(String.class))).thenReturn(traceApiService);
    }

    /**
     * destroy.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年8月22日 下午4:02:03
     */
    @After
    public void tearDown() throws Exception {
        client = null;
        traceApiService = null;
    }

    /**
     * 测试getTraceApiService.
     * 
     * @author 尤小平
     * @date 2017年8月22日 下午4:02:24
     */
    @Test
    public void testGetTraceApiService() {
        TraceApiService actual = client.getTraceApiService();

        assertNotNull(actual);
    }

    /**
     * 测试查询所有services.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年8月22日 下午4:02:38
     */
    @Test
    public void testGetServiceNames() throws Exception {
        List<String> serviceNames = new ArrayList<>();
        PowerMockito.when(traceApiService.getServiceNames()).thenReturn(serviceNames);

        List<String> actual = client.getServiceNames();

        assertEquals(actual, serviceNames);
        PowerMockito.verifyStatic();
        traceApiService.getServiceNames();
    }

    /**
     * 测试根据serviceName查询所有span.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年8月22日 下午4:03:18
     */
    @Test
    public void testGetSpanNames() throws Exception {
        List<String> spanNames = new ArrayList<>();
        PowerMockito.when(traceApiService.getSpanNames(any(String.class))).thenReturn(spanNames);

        List<String> actual = client.getSpanNames("serviceName");

        assertEquals(actual, spanNames);
        PowerMockito.verifyStatic();
        traceApiService.getSpanNames(any(String.class));
    }

    /**
     * 测试根据关键字查询trace.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年8月22日 下午4:03:45
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetTraces() throws Exception {
        List<List<Span>> result = new ArrayList<List<Span>>();
        PowerMockito.when(traceApiService.getTraces(any(Map.class))).thenReturn(result);

        Map<String, Object> params = new HashMap<String, Object>();
        List<List<Span>> actual = client.getTraces(params);

        assertEquals(actual, result);
        PowerMockito.verifyStatic();
        traceApiService.getTraces(any(Map.class));
    }

    /**
     * 测试根据traceId查询链路.
     * 
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年8月22日 下午4:03:57
     */
    @Test
    public void testGetTrace() throws Exception {
        List<Span> trace = new ArrayList<>();
        PowerMockito.when(traceApiService.getTrace(any(String.class))).thenReturn(trace);

        List<Span> actual = client.getTrace("1");

        assertEquals(actual, trace);
        PowerMockito.verifyStatic();
        traceApiService.getTrace(any(String.class));
    }

}

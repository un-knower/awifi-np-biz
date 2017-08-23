/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年8月9日 下午4:36:23
* 创建作者：尤小平
* 文件名称：TraceClient.java
* 版本：  v1.0
* 功能：鹰眼服务端接口
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.eagleeye.util;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.awifi.np.biz.api.client.dbcenter.eagleeye.service.TraceApiService;
import org.apache.log4j.Logger;

import com.awifi.np.biz.api.client.dbcenter.eagleeye.model.Span;
import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.common.util.JsonUtil;

public class TraceClient {
    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger(TraceClient.class);

    /**
     * TraceApiService
     */
    @Resource
    private static TraceApiService traceApiService;

    /**
     * @return the traceApiService
     */
    public static TraceApiService getTraceApiService() {
        if (traceApiService == null) {
            traceApiService = (TraceApiService) BeanUtil.getBean("traceApiService");
        }
        return traceApiService;
    }

    /**
     * 查询所有services.
     * 
     * @return 所有services列表
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年8月14日 上午9:48:59
     */
    public static List<String> getServiceNames() throws Exception {
        return getTraceApiService().getServiceNames();
    }

    /**
     * 根据serviceName查询所有span.
     * 
     * @param serviceName serviceName
     * @return 指定service下所有span名称
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年8月14日 上午9:49:52
     */
    public static List<String> getSpanNames(String serviceName) throws Exception {
        logger.debug("serviceName=" + serviceName);
        return getTraceApiService().getSpanNames(serviceName);
    }

    /**
     * 根据关键字查询trace.
     * 
     * @param params 输入参数
     * @return traces列表
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年8月14日 上午9:50:54
     */
    public static List<List<Span>> getTraces(Map<String, Object> params) throws Exception {
        logger.debug("params=" + JsonUtil.toJson(params));
        return getTraceApiService().getTraces(params);
    }

    /**
     * 根据traceId查询链路.
     * 
     * @param traceIdHex traceId
     * @return Span列表
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年8月14日 上午9:52:01
     */
    public static List<Span> getTrace(String traceIdHex) throws Exception {
        return getTraceApiService().getTrace(traceIdHex);
    }
}

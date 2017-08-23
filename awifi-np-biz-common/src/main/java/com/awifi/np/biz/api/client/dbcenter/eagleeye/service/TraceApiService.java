/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年8月9日 下午4:34:37
* 创建作者：尤小平
* 文件名称：TraceApiService.java
* 版本：  v1.0
* 功能：鹰眼服务端接口
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.eagleeye.service;

import java.util.List;
import java.util.Map;

import com.awifi.np.biz.api.client.dbcenter.eagleeye.model.Span;

public interface TraceApiService {
    /**
     * 查询所有services.
     * 
     * @return 所有services列表
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年8月14日 上午9:48:59
     */
    List<String> getServiceNames() throws Exception;

    /**
     * 根据serviceName查询所有span.
     * 
     * @param serviceName serviceName
     * @return 指定service下所有span名称
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年8月14日 上午9:49:52
     */
    List<String> getSpanNames(String serviceName) throws Exception;

    /**
     * 根据关键字查询trace.
     * 
     * @param params 输入参数
     * @return traces列表
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年8月14日 上午9:50:54
     */
    List<List<Span>> getTraces(Map<String, Object> params) throws Exception;

    /**
     * 根据traceId查询链路.
     * 
     * @param traceIdHex traceId
     * @return Span列表
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年8月14日 上午9:52:01
     */
    List<Span> getTrace(String traceIdHex) throws Exception;
}

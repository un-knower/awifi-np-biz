/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年8月9日 下午4:31:44
* 创建作者：尤小平
* 文件名称：TraceService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.eagleeyesrv.trace.service;

import java.util.Map;

import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.eagleeyesrv.trace.bean.SpanNode;
import com.awifi.np.biz.eagleeyesrv.trace.bean.Trace;

public interface TraceService {
    /**
     * 根据关键字查询trace.
     *
     * @param params 输入参数
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年8月14日 上午9:50:54
     */
    void getTraceListByParam(Map<String, Object> params, Page<SpanNode> page) throws Exception;

    /**
     * 根据traceId查询链路.
     *
     * @param traceId traceId
     * @return Trace
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年8月14日 上午9:52:01
     */
    Trace getTrace(String traceId) throws Exception;
}

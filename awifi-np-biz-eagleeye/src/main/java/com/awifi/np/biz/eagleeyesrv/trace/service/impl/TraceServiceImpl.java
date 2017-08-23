/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年8月9日 下午4:32:22
* 创建作者：尤小平
* 文件名称：TraceServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.eagleeyesrv.trace.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.awifi.np.biz.api.client.dbcenter.eagleeye.model.BinaryAnnotation;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.util.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.eagleeye.model.Span;
import com.awifi.np.biz.api.client.dbcenter.eagleeye.util.TraceClient;
import com.awifi.np.biz.eagleeyesrv.trace.bean.Trace;
import com.awifi.np.biz.eagleeyesrv.trace.bean.Param;
import com.awifi.np.biz.eagleeyesrv.trace.bean.SpanNode;
import com.awifi.np.biz.eagleeyesrv.trace.service.TraceService;

@Service(value = "traceService")
public class TraceServiceImpl implements TraceService {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * 根据关键字查询trace.
     *
     * @param params 输入参数
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年8月14日 上午9:50:54
     */
    @Override
    public void getTraceListByParam(Map<String, Object> params, Page<SpanNode> page) throws Exception {
        logger.debug("params=" + JsonUtil.toJson(params));
        List<SpanNode> spanList = new ArrayList<SpanNode>();

        List<List<Span>> traceList = TraceClient.getTraces(params);

        for (List<Span> spans : traceList) {
            for (Span span : spans) {
                SpanNode spanNode = new SpanNode();
                spanNode.setTraceId(span.getTraceId());
                spanNode.setServiceName(span.getAnnotations().get(0).getEndpoint().getServiceName());
                spanNode.setTime(span.getTimestamp() + "");

                List<Param> paramList = new ArrayList<Param>();
                for (BinaryAnnotation binaryAnnotation : span.getBinaryAnnotations()) {
                    Param param = new Param();
                    param.setKey(binaryAnnotation.getKey());
                    param.setValue(binaryAnnotation.getValue());
                    paramList.add(param);
                }
                spanNode.setParams(paramList);
                spanList.add(spanNode);
            }
        }

        page.setRecords(spanList);
        page.setTotalRecord(spanList.size());
        page.setTotalPage(1);

        logger.debug("spanNodeList.size =" + spanList.size());
    }

    /**
     * 根据traceId查询链路.
     *
     * @param traceId traceId
     * @return Trace
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年8月14日 上午9:52:01
     */
    @Override
    public Trace getTrace(String traceId) throws Exception {
        logger.debug("traceId=" + traceId);
        Trace trace = new Trace();

        List<Span> spanList = TraceClient.getTrace(traceId);

        trace.setTraceId(spanList.get(0).getTraceId());
        trace.setCreateDate(spanList.get(0).getTimestamp() + "");
        trace.setTotalTime(spanList.get(0).getDuration() + "");
        trace.setServiceNum(spanList.size() + "");
        trace.setNodeNum(spanList.size() + "");

        List<SpanNode> spanNodeList = new ArrayList<SpanNode>();
        for (Span span : spanList) {
            SpanNode spanNode = new SpanNode();
            spanNode.setTraceId(span.getTraceId());
            spanNode.setName(span.getName());
            spanNode.setType("http");
            spanNode.setStatus("OK");
            spanNode.setServiceName(span.getAnnotations().get(0).getEndpoint().getServiceName());
            spanNode.setIp(span.getAnnotations().get(0).getEndpoint().getIpv4());
            spanNode.setStartDate(span.getTimestamp() + "");
            spanNode.setRunTime(span.getDuration() + "");

            List<Param> paramList = new ArrayList<Param>();
            for (BinaryAnnotation binaryAnnotation : span.getBinaryAnnotations()) {
                Param param = new Param();
                param.setKey(binaryAnnotation.getKey());
                param.setValue(binaryAnnotation.getValue());
                paramList.add(param);
            }
            spanNode.setParams(paramList);
            spanNodeList.add(spanNode);
        }
        trace.setSpanList(spanNodeList);

        logger.debug("spanNodeList.size =" + spanNodeList.size());
        return trace;
    }
}

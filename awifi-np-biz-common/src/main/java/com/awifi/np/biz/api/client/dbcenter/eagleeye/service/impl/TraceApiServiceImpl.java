/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年8月9日 下午4:35:12
* 创建作者：尤小平
* 文件名称：TraceApiServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.eagleeye.service.impl;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.awifi.np.biz.api.client.dbcenter.eagleeye.model.Annotation;
import com.awifi.np.biz.api.client.dbcenter.eagleeye.model.BinaryAnnotation;
import com.awifi.np.biz.api.client.dbcenter.eagleeye.model.Endpoint;
import com.awifi.np.biz.common.exception.InterfaceException;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.eagleeye.model.Span;
import com.awifi.np.biz.api.client.dbcenter.eagleeye.service.TraceApiService;
import com.awifi.np.biz.common.util.JsonUtil;
import zipkin.Codec;

@SuppressWarnings("unchecked")
@Service(value = "traceApiService")
public class TraceApiServiceImpl implements TraceApiService {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * 鹰眼服务端url前缀,eg: http://127.0.0.1:9411/api
     */
    private String eagleeyeAddress = "eagleeyeAddress";

    /**
     * 查询所有services URL
     */
    private String getServiceNamesUrl = "/services";
    
    /**
     * 根据serviceName查询所有span URL
     */
    private String getSpanNamesUrl = "/spans?serviceName=";
    
    /**
     * 根据关键字查询trace URL
     */
    private String getTracesUrl = "/traces";
    
    /**
     * 根据traceId查询链路 URL
     */
    private String getTraceUrl = "/trace/";

    /**
     * 查询所有services.
     * 
     * @return 所有services列表
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年8月14日 上午9:48:59
     */
    @Override
    public List<String> getServiceNames() throws Exception {
        logger.debug("查询所有services列表");
        List<String> serviceNames = new ArrayList<String>();

        String url = SysConfigUtil.getParamValue(eagleeyeAddress);
        ByteBuffer byteBuffer = HttpRequest.sendGetRequest(url + getServiceNamesUrl, null);// 发送get请求
        if (byteBuffer == null) {
            throw new InterfaceException(MessageUtil.getMessage("E2000009"), url + getServiceNamesUrl, "");// 接口无返回值!
        }

        serviceNames = Codec.JSON.readStrings(byteBuffer.array());
        logger.debug("dbcenter return:" + serviceNames.toString());

        return serviceNames;
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
    @Override
    public List<String> getSpanNames(String serviceName) throws Exception {
        logger.debug("根据serviceName查询所有span, serviceName=" + serviceName);
        List<String> spanNames = new ArrayList<String>();

        String url = SysConfigUtil.getParamValue(eagleeyeAddress);
        ByteBuffer byteBuffer = HttpRequest.sendGetRequest(url + getSpanNamesUrl + serviceName, null);// 发送get请求
        if (byteBuffer == null) {
            throw new InterfaceException(MessageUtil.getMessage("E2000009"),
                    url + getSpanNamesUrl + serviceName, "");// 接口无返回值!
        }

        spanNames = Codec.JSON.readStrings(byteBuffer.array());
        logger.debug("dbcenter return:" + spanNames.toString());

        return spanNames;
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
    @Override
    public List<List<Span>> getTraces(Map<String, Object> params) throws Exception {
        logger.debug("根据关键字查询trace, params=" + JsonUtil.toJson(params));
        List<List<Span>> traceList = new ArrayList<List<Span>>();

        String paramStr = getParams(params);// 接口参数, eg: limit=2&pageSize=2&serviceName=timebuysrv&pageNum=0

        String url = SysConfigUtil.getParamValue(eagleeyeAddress);
        ByteBuffer byteBuffer = HttpRequest.sendGetRequest(url + getTracesUrl, paramStr);// 发送get请求
        if (byteBuffer == null) {
            throw new InterfaceException(MessageUtil.getMessage("E2000009"), url + getTracesUrl, paramStr);// 接口无返回值!
        }

        List<List<zipkin.Span>> traces = Codec.JSON.readTraces(byteBuffer.array());
        logger.debug("dbcenter return : " + traces.toString());
        logger.debug("traces size:" + traces.size());

        if (traces == null || traces.size() == 0) {
            return null;
        }

        for (List<zipkin.Span> zipSpanList : traces) {
            List<Span> spanList = new ArrayList<Span>();
            logger.debug("spanList.size=" + zipSpanList.size());
            for (zipkin.Span zipkinSpan : zipSpanList) {
                Span span = getSpan(zipkinSpan.toString());
                List<zipkin.Annotation> zipAnnotations = zipkinSpan.annotations;
                List<zipkin.BinaryAnnotation> zipBAnnotations = zipkinSpan.binaryAnnotations;

                if (zipAnnotations != null && zipAnnotations.size() > 0) {
                    List<Annotation> annotations = new ArrayList<Annotation>();
                    logger.debug("annotations.size=" + zipAnnotations.size());
                    for (zipkin.Annotation zipkinAnnotation : zipAnnotations) {
                        Annotation annotation = this.getAnnotation(zipkinAnnotation);
                        annotations.add(annotation);
                    }
                    span.setAnnotations(annotations);
                }

                if (zipBAnnotations != null && zipBAnnotations.size() > 0) {
                    List<BinaryAnnotation> binaryAnnotations = new ArrayList<BinaryAnnotation>();
                    logger.debug("binaryAnnotations.size=" + zipBAnnotations.size());
                    for (zipkin.BinaryAnnotation zipBAnnotation : zipBAnnotations) {
                        BinaryAnnotation binaryAnnotation = this.getBinaryAnnotation(zipBAnnotation);
                        binaryAnnotations.add(binaryAnnotation);
                    }
                    span.setBinaryAnnotations(binaryAnnotations);
                }
                spanList.add(span);
            }
            traceList.add(spanList);
        }

        logger.debug("traceList size:" + traceList.size() + ", " + JsonUtil.toJson(traceList));
        return traceList;
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
    @Override
    public List<Span> getTrace(String traceIdHex) throws Exception {
        logger.debug("根据traceId查询链路, traceIdHex=" + traceIdHex);
        List<Span> spanList = new ArrayList<Span>();

        String url = SysConfigUtil.getParamValue(eagleeyeAddress);
        ByteBuffer byteBuffer = HttpRequest.sendGetRequest(url + getTraceUrl + traceIdHex, null);// 发送get请求
        if (byteBuffer == null) {
            throw new InterfaceException(MessageUtil.getMessage("E2000009"), url + getTraceUrl + traceIdHex,
                    "");// 接口无返回值!
        }

        List<zipkin.Span> zipSpanList = Codec.JSON.readSpans(byteBuffer.array());
        logger.debug("dbcenter return : " + zipSpanList.toString());
        logger.debug("spanList size:" + zipSpanList.size());

        if (zipSpanList == null || zipSpanList.size() == 0) {
            return null;
        }

        for (zipkin.Span zipkinSpan : zipSpanList) {
            Span span = getSpan(zipkinSpan.toString());
            List<zipkin.Annotation> zipAnnotations = zipkinSpan.annotations;
            List<zipkin.BinaryAnnotation> zipBAnnotations = zipkinSpan.binaryAnnotations;

            if (zipAnnotations != null && zipAnnotations.size() > 0) {
                List<Annotation> annotations = new ArrayList<Annotation>();
                logger.debug("annotations.size=" + zipAnnotations.size());
                for (zipkin.Annotation zipkinAnnotation : zipAnnotations) {
                    Annotation annotation = this.getAnnotation(zipkinAnnotation);
                    annotations.add(annotation);
                }
                span.setAnnotations(annotations);
            }

            if (zipBAnnotations != null && zipBAnnotations.size() > 0) {
                List<BinaryAnnotation> binaryAnnotations = new ArrayList<BinaryAnnotation>();
                logger.debug("binaryAnnotations.size=" + zipBAnnotations.size());
                for (zipkin.BinaryAnnotation zipBAnnotation : zipBAnnotations) {
                    BinaryAnnotation binaryAnnotation = this.getBinaryAnnotation(zipBAnnotation);
                    binaryAnnotations.add(binaryAnnotation);
                }
                span.setBinaryAnnotations(binaryAnnotations);
            }

            spanList.add(span);
        }

        logger.debug("return spanList size:" + spanList.size() + ", " + JsonUtil.toJson(spanList));
        return spanList;
    }

    /**
     * 获取参数.
     * 
     * @param paramMap Map
     * @return 参数String
     * @author 尤小平  
     * @date 2017年8月14日 上午9:58:47
     */
    private String getParams(Map<String, Object> paramMap) {
        if (paramMap == null || paramMap.size() <= 0) {
            return StringUtils.EMPTY;
        }
        StringBuilder params = new StringBuilder();
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            Object value = entry.getValue();
            String key = entry.getKey();
            params.append(key).append('=').append(value).append('&');
        }
        params.deleteCharAt(params.length() - 1);
        return params.toString();
    }

    /**
     * 获取Span.
     * 
     * @param spanStr zipkin.Span String
     * @return Span
     * @author 尤小平  
     * @date 2017年8月15日 下午4:19:00
     */
    private Span getSpan(String spanStr){
        if (StringUtils.isBlank(spanStr)) {
            return null;
        }

        Map<String, Object> spanMap = JsonUtil.fromJson(spanStr, Map.class);
        if (spanMap == null || spanMap.size() == 0) {
            return null;
        }

        Span span = new Span();
        if (spanMap.get("id") != null && StringUtils.isNotBlank(spanMap.get("id").toString())) {
            span.setId(spanMap.get("id").toString());
        }
        if (spanMap.get("traceId") != null && StringUtils.isNotBlank(spanMap.get("traceId").toString())) {
            span.setTraceId(spanMap.get("traceId").toString());
        }
        if (spanMap.get("name") != null && StringUtils.isNotBlank(spanMap.get("name").toString())) {
            span.setName(spanMap.get("name").toString());
        }
        if (spanMap.get("parentId") != null && StringUtils.isNotBlank(spanMap.get("parentId").toString())) {
            span.setParentId(spanMap.get("parentId").toString());
        }
        if (spanMap.get("timestamp") != null && StringUtils.isNotBlank(spanMap.get("timestamp").toString())) {
            span.setTimestamp(Long.valueOf(spanMap.get("timestamp").toString()));
        }
        if (spanMap.get("duration") != null && StringUtils.isNotBlank(spanMap.get("duration").toString())) {
            span.setDuration(Long.valueOf(spanMap.get("duration").toString()));
        }

        return span;
    }

    /**
     * 获取Endpoint.
     * 
     * @param endpointStr zipkin.Endpoint String
     * @return Endpoint
     * @author 尤小平  
     * @date 2017年8月15日 下午4:19:15
     */
    private Endpoint getEndpoint(String endpointStr){
        if (StringUtils.isBlank(endpointStr)) {
            return null;
        }

        Map<String, Object> endpointMap = JsonUtil.fromJson(endpointStr, Map.class);
        if (endpointMap == null || endpointMap.size() == 0) {
            return null;
        }

        Endpoint endpoint = new Endpoint();
        if (endpointMap.get("serviceName") != null
                && StringUtils.isNotBlank(endpointMap.get("serviceName").toString())) {
            endpoint.setServiceName(endpointMap.get("serviceName").toString());
        }
        if (endpointMap.get("ipv4") != null && StringUtils.isNotBlank(endpointMap.get("ipv4").toString())) {
            endpoint.setIpv4(endpointMap.get("ipv4").toString());
        }
        if (endpointMap.get("ipv6") != null && StringUtils.isNotBlank(endpointMap.get("ipv6").toString())) {
            endpoint.setIpv6(endpointMap.get("ipv6").toString());
        }
        if (endpointMap.get("port") != null && StringUtils.isNotBlank(endpointMap.get("port").toString())) {
            endpoint.setPort(endpointMap.get("port").toString());
        }

        return endpoint;
    }

    /**
     * 获取Annotation.
     * 
     * @param zipkinAnnotation zipkin.Annotation
     * @return Annotation
     * @author 尤小平  
     * @date 2017年8月15日 下午4:20:10
     */
    private Annotation getAnnotation(zipkin.Annotation zipkinAnnotation) {
        if (zipkinAnnotation == null) {
            return null;
        }

        Annotation annotation = new Annotation();
        annotation.setEndpoint(getEndpoint(zipkinAnnotation.endpoint.toString()));
        if (zipkinAnnotation.timestamp > 0) {
            annotation.setTimestamp(zipkinAnnotation.timestamp);
        }
        if (StringUtils.isNotBlank(zipkinAnnotation.value)) {
            annotation.setValue(zipkinAnnotation.value);
        }

        return annotation;
    }

    /**
     * 获取BinaryAnnotation.
     * 
     * @param zipBAnnotation zipkin.BinaryAnnotation
     * @return BinaryAnnotation
     * @author 尤小平  
     * @date 2017年8月15日 下午4:20:30
     */
    private BinaryAnnotation getBinaryAnnotation(zipkin.BinaryAnnotation zipBAnnotation) {
        if (zipBAnnotation == null) {
            return null;
        }

        BinaryAnnotation binaryAnnotation = new BinaryAnnotation();
        binaryAnnotation.setEndpoint(getEndpoint(zipBAnnotation.endpoint.toString()));
        if (StringUtils.isNotBlank(zipBAnnotation.key)) {
            binaryAnnotation.setKey(zipBAnnotation.key);
        }
        if (zipBAnnotation.value != null) {
            if(zipBAnnotation.type.toString().equals("STRING")){
                binaryAnnotation.setValue(new String(zipBAnnotation.value));
            } else {
                binaryAnnotation.setValue(zipBAnnotation.value[0] + "");
            }
        }

        return binaryAnnotation;
    }
}

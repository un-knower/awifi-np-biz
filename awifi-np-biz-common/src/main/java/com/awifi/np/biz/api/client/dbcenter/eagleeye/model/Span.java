/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年8月9日 上午10:43:19
* 创建作者：尤小平
* 文件名称：Span.java
* 版本：  v1.0
* 功能：服务点（跨度）
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.eagleeye.model;

import java.io.Serializable;
import java.util.List;

public class Span implements Serializable {

    private static final long serialVersionUID = 1L;
    
//    private long traceIdHigh;//目前不用
    
    /**
     * 链路ID
     */
    private String traceId;
    
    /**
     * span 名称
     */
    private String name;
    
    /**
     * 本span id
     */
    private String id;
    
    /**
     * 父span Id
     */
    private String parentId;
    
    /**
     * 时间戳
     */
    private Long timestamp;
    
    /**
     * 总历时
     */
    private Long duration;
    
    /**
     * 注释列表
     */
    private List<Annotation> annotations;
    
    /**
     * 二元注释列表
     */
    private List<BinaryAnnotation> binaryAnnotations;

    public Span() {
        
    }

//    /**
//     * @return the traceIdHigh
//     */
//    public long getTraceIdHigh() {
//        return traceIdHigh;
//    }
//
//    /**
//     * @param traceIdHigh the traceIdHigh to set
//     */
//    public void setTraceIdHigh(long traceIdHigh) {
//        this.traceIdHigh = traceIdHigh;
//    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the timestamp
     */
    public Long getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the duration
     */
    public Long getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(Long duration) {
        this.duration = duration;
    }

    /**
     * @return the annotations
     */
    public List<Annotation> getAnnotations() {
        return annotations;
    }

    /**
     * @param annotations the annotations to set
     */
    public void setAnnotations(List<Annotation> annotations) {
        this.annotations = annotations;
    }

    /**
     * @return the binaryAnnotations
     */
    public List<BinaryAnnotation> getBinaryAnnotations() {
        return binaryAnnotations;
    }

    /**
     * @param binaryAnnotations the binaryAnnotations to set
     */
    public void setBinaryAnnotations(List<BinaryAnnotation> binaryAnnotations) {
        this.binaryAnnotations = binaryAnnotations;
    }

    /**
     * @return the traceId
     */
    public String getTraceId() {
        return traceId;
    }

    /**
     * @param traceId the traceId to set
     */
    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the parentId
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * @param parentId the parentId to set
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}

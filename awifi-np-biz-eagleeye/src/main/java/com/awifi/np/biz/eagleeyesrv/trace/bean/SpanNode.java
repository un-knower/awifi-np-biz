/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年8月16日 上午9:34:09
* 创建作者：尤小平
* 文件名称：SpanNode.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.eagleeyesrv.trace.bean;

import java.util.List;

public class SpanNode {
    private String traceId; // 全局ID,traceId
    private String spanId;// id
    private String parentId;// parent spanId
    private String name;// 应用名,工程名
    private String serviceName; // 服务名称,方法名,Annotation.endpoint.serviceName
    private String ip;// 服务器ip,Annotation.endpoint.ipv4
    private String port;// 服务器端口
    private String time; // 时间,Annotation.timestamp
    private String type;// 类型
    private String status;// 状态
    private String startDate;// 开始时间
    private String runTime;// 执行时长
    private List<Param> params;// 详细参数:参数名和参数值
    
    public SpanNode() {
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
     * @return the spanId
     */
    public String getSpanId() {
        return spanId;
    }

    /**
     * @param spanId the spanId to set
     */
    public void setSpanId(String spanId) {
        this.spanId = spanId;
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
     * @return the serviceName
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @param serviceName the serviceName to set
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the port
     */
    public String getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the runTime
     */
    public String getRunTime() {
        return runTime;
    }

    /**
     * @param runTime the runTime to set
     */
    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    /**
     * @return the params
     */
    public List<Param> getParams() {
        return params;
    }

    /**
     * @param params the params to set
     */
    public void setParams(List<Param> params) {
        this.params = params;
    }
}

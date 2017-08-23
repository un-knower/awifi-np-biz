/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年8月16日 上午9:33:50
* 创建作者：尤小平
* 文件名称：Trace.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.eagleeyesrv.trace.bean;

import java.util.List;

public class Trace {
    /**
     * 全局ID
     */
    private String traceId;

    /**
     * 起始时间
     */
    private String createDate;

    /**
     * 总耗时
     */
    private String totalTime;

    /**
     * 服务总数
     */
    private String serviceNum;

    /**
     * 节点总数
     */
    private String nodeNum;

    /**
     * 节点
     */
    private List<SpanNode> spanList;

    
    public Trace() {
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
     * @return the createDate
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the totalTime
     */
    public String getTotalTime() {
        return totalTime;
    }

    /**
     * @param totalTime the totalTime to set
     */
    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    /**
     * @return the serviceNum
     */
    public String getServiceNum() {
        return serviceNum;
    }

    /**
     * @param serviceNum the serviceNum to set
     */
    public void setServiceNum(String serviceNum) {
        this.serviceNum = serviceNum;
    }

    /**
     * @return the nodeNum
     */
    public String getNodeNum() {
        return nodeNum;
    }

    /**
     * @param nodeNum the nodeNum to set
     */
    public void setNodeNum(String nodeNum) {
        this.nodeNum = nodeNum;
    }

    /**
     * @return the spanList
     */
    public List<SpanNode> getSpanList() {
        return spanList;
    }

    /**
     * @param spanList the spanList to set
     */
    public void setSpanList(List<SpanNode> spanList) {
        this.spanList = spanList;
    }
}

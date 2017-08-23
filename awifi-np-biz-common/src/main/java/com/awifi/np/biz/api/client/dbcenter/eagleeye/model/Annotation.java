/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年8月9日 上午10:38:21
* 创建作者：尤小平
* 文件名称：Annotation.java
* 版本：  v1.0
* 功能：注释
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.eagleeye.model;

import java.io.Serializable;

public class Annotation implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * 时间戳
     */
    private long timestamp;
    
    /**
     * 值
     */
    private String value;
    
    /**
     * 端点
     */
    private Endpoint endpoint;

    public Annotation() {
        
    }

    /**
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the endpoint
     */
    public Endpoint getEndpoint() {
        return endpoint;
    }

    /**
     * @param endpoint the endpoint to set
     */
    public void setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }
}

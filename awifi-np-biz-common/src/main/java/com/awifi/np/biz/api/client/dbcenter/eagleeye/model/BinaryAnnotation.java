/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年8月9日 上午10:41:58
* 创建作者：尤小平
* 文件名称：BinaryAnnotation.java
* 版本：  v1.0
* 功能：二元注释
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.eagleeye.model;

import java.io.Serializable;

public class BinaryAnnotation implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * 时间戳
     */
//    private long timestamp;
    
    /**
     * key
     */
    private String key;
    
    /**
     * 值
     */
    private String value;
    
    /**
     * 端点
     */
    private Endpoint endpoint;

    public BinaryAnnotation() {
        
    }

//    /**
//     * @return the timestamp
//     */
//    public long getTimestamp() {
//        return timestamp;
//    }
//
//    /**
//     * @param timestamp the timestamp to set
//     */
//    public void setTimestamp(long timestamp) {
//        this.timestamp = timestamp;
//    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
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

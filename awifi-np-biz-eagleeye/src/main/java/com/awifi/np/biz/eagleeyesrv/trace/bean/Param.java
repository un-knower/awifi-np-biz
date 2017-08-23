/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年8月16日 上午9:37:27
* 创建作者：尤小平
* 文件名称：Param.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.eagleeyesrv.trace.bean;

public class Param {
    private String key;// 参数名
    private String value;// 参数值
    
    public Param() {
    }
    
    public Param(String key, String value) {
        super();
        this.key = key;
        this.value = value;
    }
    
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
    
}

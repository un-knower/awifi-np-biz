/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年8月9日 上午10:34:39
* 创建作者：尤小平
* 文件名称：Endpoint.java
* 版本：  v1.0
* 功能：服务端点
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.eagleeye.model;

import java.io.Serializable;

public class Endpoint implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * 服务名称
     */
    private String serviceName;
    
    /**
     * ip4地址
     */
    private String ipv4;
    
    /**
     * ip6地址
     */
    private String ipv6;
    
    /**
     * 端口
     */
    private String port;

    public Endpoint() {
        
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
     * @return the ipv4
     */
    public String getIpv4() {
        return ipv4;
    }

    /**
     * @param ipv4 the ipv4 to set
     */
    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }

    /**
     * @return the ipv6
     */
    public String getIpv6() {
        return ipv6;
    }

    /**
     * @param ipv6 the ipv6 to set
     */
    public void setIpv6(String ipv6) {
        this.ipv6 = ipv6;
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
}

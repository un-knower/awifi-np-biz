package com.awifi.np.biz.toe.admin.device.model;

import java.io.Serializable;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月6日 下午4:34:41
 * 创建作者：亢燕翔
 * 文件名称：SfTerminalConfig.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class SfTerminalConfig implements Serializable{

    /**序列化*/
    private static final long serialVersionUID = 2134420803339467656L;

    /** 认证地址  */
    private String authHostName;
    /** 平台地址  */
    private String platformHostName;
    /** portal地址  */
    private String portalHostName;
    /**   */
    private String hostname;
    /**   */
    private String http_port;
    /**  */
    private String path;
    /**   */
    private String ssl_available;
    /**  端口 */
    private String ssl_port;
    
    public String getAuthHostName() {
        return authHostName;
    }
    public void setAuthHostName(String authHostName) {
        this.authHostName = authHostName;
    }
    public String getPlatformHostName() {
        return platformHostName;
    }
    public void setPlatformHostName(String platformHostName) {
        this.platformHostName = platformHostName;
    }
    public String getPortalHostName() {
        return portalHostName;
    }
    public void setPortalHostName(String portalHostName) {
        this.portalHostName = portalHostName;
    }
    public String getHostname() {
        return hostname;
    }
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
    public String getHttp_port() {
        return http_port;
    }
    public void setHttp_port(String http_port) {
        this.http_port = http_port;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getSsl_available() {
        return ssl_available;
    }
    public void setSsl_available(String ssl_available) {
        this.ssl_available = ssl_available;
    }
    public String getSsl_port() {
        return ssl_port;
    }
    public void setSsl_port(String ssl_port) {
        this.ssl_port = ssl_port;
    }
    
    @Override
    public String toString() {
        return "SfTerminalConfig [authHostName=" + authHostName
                + ", platformHostName=" + platformHostName
                + ", portalHostName=" + portalHostName + ", hostname="
                + hostname + ", http_port=" + http_port + ", path=" + path
                + ", ssl_available=" + ssl_available + ", ssl_port=" + ssl_port
                + "]";
    }
    
}

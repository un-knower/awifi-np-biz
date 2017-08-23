package com.awifi.np.biz.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.awifi.np.biz.common.base.constants.RegexConstants;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月13日 下午3:49:03
 * 创建作者：周颖
 * 文件名称：IPUtil.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class IPUtil {

    /** 日志  */
    private static final Log logger = LogFactory.getLog(IPUtil.class);
    
    /**
     * 获取客户端真实ip地址
     * @param request 请求
     * @return ip地址
     * @author 许小满  
     * @date 2013-11-5 上午11:32:55
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress  = request.getHeader("X-Forwarded-For");
        logger.debug("提示：ip --> X-Forwarded-For ： " + ipAddress);
        if (StringUtils.isBlank(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("x-forwarded-for");
            logger.debug("提示：ip --> x-forwarded-for ： " + ipAddress);
        }
        if (StringUtils.isBlank(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
            logger.debug("提示：ip --> Proxy-Client-IP ： " + ipAddress);
        }
        if (StringUtils.isBlank(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
            logger.debug("提示：ip --> WL-Proxy-Client-IP ： " + ipAddress);
        }
        if (StringUtils.isBlank(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                // 根据网卡取本机配置的IP
                try {
                    InetAddress inet = InetAddress.getLocalHost();
                    ipAddress = inet.getHostAddress();
                } catch (UnknownHostException e) {
                    // 日志
                }
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { // IP最大长度为15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }

        if (StringUtils.isBlank(ipAddress) || !RegexConstants.IP_PATTERN_COMPILE.matcher(ipAddress).matches()) {
            ipAddress = StringUtils.EMPTY;
        }
        return ipAddress;
    }
    
    /***
     * 获取客户端源端口
     * @param request HttpServletRequest
     * @return 端口,未获取返回0
     * @author wangx
     * @date 2015年10月29日 上午11:19:12
     */
    public static String getRemotePort(HttpServletRequest request){
        String port = request.getHeader("X-Forwarded-Port");
        logger.debug("提示：port --> X-Forwarded-Port： " + port);
        if(StringUtils.isBlank(port)){
            port = request.getRemotePort() + StringUtils.EMPTY;
            logger.debug("提示：port --> request.getRemotePort()： " + port);
        }
        return StringUtils.defaultIfBlank(port, "0");

    }
}
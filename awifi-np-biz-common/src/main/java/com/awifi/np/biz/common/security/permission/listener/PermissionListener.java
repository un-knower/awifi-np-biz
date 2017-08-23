package com.awifi.np.biz.common.security.permission.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.awifi.np.biz.common.security.permission.thread.PermissionThread;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月14日 上午10:29:19
 * 创建作者：亢燕翔
 * 文件名称：PermissionListener.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class PermissionListener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();//获取应用上下文
        String serviceCode = servletContext.getInitParameter("servicecode");//从web.xml中获取服务代码
        String serviceKey = servletContext.getInitParameter("servicekey");//从web.xml中获取服务密钥
        new PermissionThread(serviceCode, serviceKey).start();//启动线程
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub
    }
    
}

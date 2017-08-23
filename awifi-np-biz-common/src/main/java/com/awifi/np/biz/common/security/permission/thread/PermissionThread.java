package com.awifi.np.biz.common.security.permission.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.awifi.np.biz.common.security.permission.service.PermissionService;
import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.common.util.ErrorUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月14日 上午9:02:12
 * 创建作者：亢燕翔
 * 文件名称：PermissionThread.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class PermissionThread extends Thread{

    /**服务代码*/
    private String serviceCode;
    
    /**服务密钥*/
    private String serviceKey;
    
    /**权限--业务层*/
    private static PermissionService permissionService;
    
    /**log*/
    private Log logger = LogFactory.getLog(PermissionThread.class);
    
    /**默认构造函数*/
    public PermissionThread() {}
    
    /**
     * 构造函数
     * @param serviceCode 服务代码
     * @param serviceKey 服务密钥
     * @author 亢燕翔  
     * @date 2017年3月14日 上午9:09:31
     */
    public PermissionThread(String serviceCode, String serviceKey) {
        this.serviceCode = serviceCode;
        this.serviceKey = serviceKey;
    }
    
    /**
     * 获取PermissionService实例
     * @return PermissionService
     * @author 亢燕翔  
     * @date 2017年3月14日 上午9:44:47
     */
    private static PermissionService getPermissionService(){
        if(permissionService == null){
            permissionService = (PermissionService) BeanUtil.getBean("permissionService");
        }
        return permissionService;
    }
    
    /**
     * run方法
     * @author 亢燕翔  
     * @date 2017年3月14日 上午9:10:49
     */
    @Override
    public void run(){
        try {
            logger.debug("提示：系统启动后执行接口注册方法，服务代码 = （"+serviceCode+"） 服务密钥 = （"+serviceKey+")");
            getPermissionService().pushInterfaces(serviceCode, serviceKey);//执行接口推送
        } catch (Exception e) {
            ErrorUtil.printException(e, logger);//控制台输出错误日志
        }
    }
    
}

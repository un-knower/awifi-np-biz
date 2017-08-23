package com.awifi.np.biz.common.util;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月11日 下午8:26:19
 * 创建作者：亢燕翔
 * 文件名称：BeanUtil.java
 * 版本：  v1.0
 * 功能： Bean 工具类，作用：从 spring上下文 获取bean
 * 修改记录：
 */
public class BeanUtil {

    /** 应用上下文 */
    private static WebApplicationContext webApplicationContext;

    /**
     * 获取bean对象
     * @param name bean名称
     * @return bean
     * @author 亢燕翔  
     * @date Jan 9, 2017 5:29:45 PM
     */
    public static Object getBean(String name) {
        return getContext().getBean(name);
    }
    
    /**
     * 获取 应用上下文
     * @return 应用上下文
     * @author 亢燕翔  
     * @date Jan 9, 2017 5:32:27 PM
     */
    private static WebApplicationContext getContext() {
        if (webApplicationContext == null) {
            webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        }
        return webApplicationContext;
    }

}
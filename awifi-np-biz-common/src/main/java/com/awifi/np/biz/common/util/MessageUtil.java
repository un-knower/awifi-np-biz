package com.awifi.np.biz.common.util;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:Jan 10, 2017 8:41:23 AM
 * 创建作者：亢燕翔
 * 文件名称：MessageUtil.java
 * 版本：  v1.0
 * 功能：  消息工具类（国际化）
 * 修改记录：
 */
public class MessageUtil {

    /** 消息源 */
    private static ReloadableResourceBundleMessageSource messageSource;

    /**
     * 获取消息
     * @param key 消息key值
     * @return 消息值
     * @author 许小满
     * @date 2015年6月29日 上午10:49:35
     */
    public static String getMessage(String key) {
        return getMessage(key, null);
    }

    /**
     * 获取消息
     * 
     * @param key 消息key
     * @param arg 参数
     * @return 消息值
     * @author 许小满
     * @date 2015年6月29日 上午10:49:46
     */
    public static String getMessage(String key, Object arg) {
        Object[] args = new Object[] { arg };
        return getMessageSource().getMessage(key, args, "[" + key + "]未匹配，需在messages.properties中进行添加!", null);
    }

    /**
     * 获取消息
     * @param key 消息key
     * @param args 参数
     * @return 消息值
     * @author 许小满
     * @date 2015年6月29日 上午10:49:46
     */
    public static String getMessage(String key, Object[] args) {
        return getMessageSource().getMessage(key, args, "[" + key + "]未匹配，需在messages.properties中进行添加!", null);
    }

    /**
     * 获取消息源
     * @return 消息源
     * @author 许小满  
     * @date 2015年12月7日 下午2:30:07
     */
    private static ReloadableResourceBundleMessageSource getMessageSource() {
        if (messageSource == null) {
            messageSource = (ReloadableResourceBundleMessageSource) BeanUtil.getBean("messageSource");
        }
        return messageSource;
    }

}

package com.awifi.np.biz.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;

import com.awifi.np.biz.common.exception.ValidException;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:Jan 10, 2017 4:59:11 PM
 * 创建作者：亢燕翔
 * 文件名称：ErrorUtil.java
 * 版本：  v1.0
 * 功能：  错误消息处理工具类
 * 修改记录：
 */
public class ErrorUtil {

	/**
	 * 打印异常日志
	 * @param e 异常
	 * @param logger 日志
	 * @author 亢燕翔  
	 * @date Jan 10, 2017 4:59:49 PM
	 */
    public static void printException(Exception e, Log logger) {
        if(e instanceof ValidException){//如果是校验异常，不需要输出复杂的异常堆栈信息
            ((ValidException)e).log();//输出日志
            return;
        }
        logger.error(getExceptionStackTrace(e));
    }
    
    /**
     * 打印异常日志
     * @param e 异常
     * @param logger 日志
     * @author 许小满  
     * @date 2017年7月20日 上午8:33:46
     */
    public static void printException(Exception e, org.apache.log4j.Logger logger) {
        if(e instanceof ValidException){//如果是校验异常，不需要输出复杂的异常堆栈信息
            ((ValidException)e).log();//输出日志
            return;
        }
        logger.error(getExceptionStackTrace(e));
    }
	
	/**
	 * 打印异常日志
	 * @param e 异常
	 * @return 异常信息
	 * @author 亢燕翔  
	 * @date Jan 10, 2017 5:01:09 PM
	 */
    public static String getExceptionStackTrace(Exception e) {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer, true));
        return writer.toString();
    }
	
}

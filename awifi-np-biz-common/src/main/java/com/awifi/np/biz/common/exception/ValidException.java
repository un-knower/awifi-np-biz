package com.awifi.np.biz.common.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月17日 下午2:08:42
 * 创建作者：许小满
 * 文件名称：ValidException.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class ValidException extends RuntimeException{

    /** 序列号 */
    private static final long serialVersionUID = 3393238725135126965L;
    
    /** 日志 */
    private static final Log logger = LogFactory.getLog(ValidException.class);
    
    /** 错误编码 */
    private String code;
    
    public ValidException(String code,String message){
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    /**
     * 输出日志格式化
     * @author 许小满  
     * @date 2017年7月14日 下午6:53:12
     */
    public void log(){
        logger.error(this.getMessage());
    }
}

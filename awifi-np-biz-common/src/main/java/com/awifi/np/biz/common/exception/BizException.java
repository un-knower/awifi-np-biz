package com.awifi.np.biz.common.exception;
/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月6日 下午5:37:11
 * 创建作者：周颖
 * 文件名称：BizException.java
 * 版本：  v1.0
 * 功能：业务异常实体类
 * 修改记录：
 */
public class BizException extends RuntimeException {

    
    private static final long serialVersionUID = -3284888190778085777L;
    
    /**错误编码*/
    private String code;
    
    public BizException(String code,String message){
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
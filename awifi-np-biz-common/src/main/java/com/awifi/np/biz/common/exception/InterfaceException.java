package com.awifi.np.biz.common.exception;


/**
 * 版权所有： 爱WiFi无线运营中心 
 * 创建日期:2017年1月6日 下午3:58:39 
 * 创建作者：周颖
 * 文件名称：InterfaceException.java 
 * 版本： v1.0 
 * 功能： 接口异常实体类
 * 修改记录：
 */
public class InterfaceException extends RuntimeException {

    
	/** 序列号 */
    private static final long serialVersionUID = 8662044174443879224L;

    /** 错误编号 */
    private String code;
    
	/** 接口地址 */
    private String interfaceUrl;

	/** 接口参数 */
    private String interfaceParam;

	/** 接口返回值 */
    private String interfaceReturnValue;
    
    public InterfaceException(String message, String interfaceUrl) {
        super(message);
        this.code = "E2000018";
        this.interfaceUrl = interfaceUrl;
    }
    
    public InterfaceException(String message, String interfaceUrl, Throwable e) {
        super(message, e);
        this.code = "E2000018";
        this.interfaceUrl = interfaceUrl;
    }

    public InterfaceException(String message, String interfaceUrl, String interfaceParam) {
        super(message);
        this.code = "E2000018";
        this.interfaceUrl = interfaceUrl;
        this.interfaceParam = interfaceParam;
    }
    
    public InterfaceException(String message, String interfaceUrl, String interfaceParam, Throwable e) {
        super(message, e);
        this.code = "E2000018";
        this.interfaceUrl = interfaceUrl;
        this.interfaceParam = interfaceParam;
    }

    public InterfaceException(String message, String interfaceUrl, String interfaceParam, String interfaceReturnValue) {
        super(message);
        this.code = "E2000018";
        this.interfaceUrl = interfaceUrl;
        this.interfaceParam = interfaceParam;
        this.interfaceReturnValue = interfaceReturnValue;
    }
    
    public InterfaceException(String message, String interfaceUrl, String interfaceParam, String interfaceReturnValue, Throwable e) {
        super(message, e);
        this.code = "E2000018";
        this.interfaceUrl = interfaceUrl;
        this.interfaceParam = interfaceParam;
        this.interfaceReturnValue = interfaceReturnValue;
    }
    
    public InterfaceException(String code, String message, String interfaceUrl, String interfaceParam, String interfaceReturnValue) {
        super(message);
        this.code = code;
        this.interfaceUrl = interfaceUrl;
        this.interfaceParam = interfaceParam;
        this.interfaceReturnValue = interfaceReturnValue;
    }
    
    public InterfaceException(String code, String message, String interfaceUrl, String interfaceParam, String interfaceReturnValue, Throwable e) {
        super(message, e);
        this.code = code;
        this.interfaceUrl = interfaceUrl;
        this.interfaceParam = interfaceParam;
        this.interfaceReturnValue = interfaceReturnValue;
    }
    
    public String getCode() {
        return code;
    }

    public String getInterfaceUrl() {
        return interfaceUrl;
    }

    public String getInterfaceParam() {
        return interfaceParam;
    }

    public String getInterfaceReturnValue() {
        return interfaceReturnValue;
    }

}
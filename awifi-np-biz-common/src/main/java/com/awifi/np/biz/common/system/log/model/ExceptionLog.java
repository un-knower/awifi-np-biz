package com.awifi.np.biz.common.system.log.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月6日 下午3:43:40
 * 创建作者：周颖
 * 文件名称：ExceptionLog.java
 * 版本：  v1.0
 * 功能：异常实体类
 * 修改记录：
 */
public class ExceptionLog implements Serializable{
    
	/**
     * 序列化id
     */
    private static final long serialVersionUID = -7317416139265361073L;

    /** 主键id */
    private Long id;

    /** 错误代码 */
    private String errorCode;

    /** 模块名称 */
    private String moduleName;

    /** 参数 */
    private String parameter;

    /** 错误消息 */
    private String errorMessage;
    
    /** 接口地址 */
    private String interfaceUrl;
    
    /** 接口参数 */
    private String interfaceParam;
    
    /** 接口返回值 */
    private String interfaceReturnValue;

    /** 创建时间 */
    private Date createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getInterfaceUrl() {
        return interfaceUrl;
    }

    public void setInterfaceUrl(String interfaceUrl) {
        this.interfaceUrl = interfaceUrl;
    }

    public String getInterfaceParam() {
        return interfaceParam;
    }

    public void setInterfaceParam(String interfaceParam) {
        this.interfaceParam = interfaceParam;
    }

    public String getInterfaceReturnValue() {
        return interfaceReturnValue;
    }

    public void setInterfaceReturnValue(String interfaceReturnValue) {
        this.interfaceReturnValue = interfaceReturnValue;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }    
}
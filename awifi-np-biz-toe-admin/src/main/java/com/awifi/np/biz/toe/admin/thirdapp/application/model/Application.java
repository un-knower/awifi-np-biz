package com.awifi.np.biz.toe.admin.thirdapp.application.model;

import java.io.Serializable;

/**   
 * @Description:  
 * @Title: Application.java 
 * @Package com.awifi.toe.admin.microshop.model 
 * @author kangyanxiang 
 * @date Nov 17, 2016 9:31:34 AM
 * @version V1.0   
 */
public class Application implements Serializable{

    /** 序列化 */
    private static final long serialVersionUID = 2819797501196593953L;

    /** 主键ID  */
    private Long id;
    
    /** 应用ID  */
    private String appid;
    
    /** 应用KEY  */
    private String appKey;
    
    /** 应用名称  */
    private String appName;
    
    /** 登陆地址  */
    private String loginUrl;
    
    /** 参数  */
    private String params;
    
    /** 认证地址  */
    private String wechatAuthUrl;
    
    /** 解绑地址  */
    private String wechatUnbindUrl;
    
    /** 备注 */
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getWechatAuthUrl() {
        return wechatAuthUrl;
    }

    public void setWechatAuthUrl(String wechatAuthUrl) {
        this.wechatAuthUrl = wechatAuthUrl;
    }

    public String getWechatUnbindUrl() {
        return wechatUnbindUrl;
    }

    public void setWechatUnbindUrl(String wechatUnbindUrl) {
        this.wechatUnbindUrl = wechatUnbindUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

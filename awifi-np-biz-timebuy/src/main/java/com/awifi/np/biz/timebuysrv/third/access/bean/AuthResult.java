package com.awifi.np.biz.timebuysrv.third.access.bean;

import java.io.Serializable;

/**
 * 网络认证接口申请token 返回结果
 * 
 * @author 张智威 2017年4月10日 上午10:00:15
 */
public class AuthResult implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -8482052526892450452L;
    /**
     * 返回code
     */
    private String resultCode;
    /**
     * 返回数据
     */
    private String data;
    /**
     * 消息
     */
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

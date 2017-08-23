/**   
 * @Description:  短信配置实体类
 * @Title: AuthCodeConfig.java 
 * @Package com.awifi.toe.system.authcodeconfi.model 
 * @author ZhouYing 
 * @date 2016年12月2日 上午11:07:15
 * @version V1.0   
 */
package com.awifi.np.biz.common.system.smsconfig.model;

import java.io.Serializable;

public class SmsConfig implements Serializable{

    /**
     * 对象序列化id
     */
    private static final long serialVersionUID = -2643925676928590275L;

    /**主键*/
    private Long id;
    
    /**短信内容*/
    private String smsContent;
    
    /**验证码长度*/
    private Integer codeLength;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public Integer getCodeLength() {
        return codeLength;
    }

    public void setCodeLength(Integer codeLength) {
        this.codeLength = codeLength;
    }

}

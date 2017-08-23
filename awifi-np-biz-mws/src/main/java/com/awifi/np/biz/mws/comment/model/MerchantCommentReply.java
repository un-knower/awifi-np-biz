/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月16日 上午9:13:12
* 创建作者：季振宇
* 文件名称：MerchantCommentReply.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mws.comment.model;

import java.io.Serializable;

public class MerchantCommentReply implements Serializable{
	
    /**
     * 
     */
    private static final long serialVersionUID = -5884690288857188147L;

    /**评论回复表主键id*/
    private Long commentReplyId;
	
	/**回复人用户id*/
    private Integer replyUserId;
	
	/**回复人用户名*/
    private String replyUserName;
	
	/**回复给的人用户id*/
    private Integer replyToUserId;
	
	/**回复给的人用户名*/
    private String replyToUserName;
	
	/**评论内容*/
    private String content;
	
	/**创建日期*/
    private String createDate;

    public Long getCommentReplyId() {
        return commentReplyId;
    }

    public void setCommentReplyId(Long commentReplyId) {
        this.commentReplyId = commentReplyId;
    }

    public Integer getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(Integer replyUserId) {
        this.replyUserId = replyUserId;
    }

    public String getReplyUserName() {
        return replyUserName;
    }

    public void setReplyUserName(String replyUserName) {
        this.replyUserName = replyUserName;
    }

    public Integer getReplyToUserId() {
        return replyToUserId;
    }

    public void setReplyToUserId(Integer replyToUserId) {
        this.replyToUserId = replyToUserId;
    }

    public String getReplyToUserName() {
        return replyToUserName;
    }

    public void setReplyToUserName(String replyToUserName) {
        this.replyToUserName = replyToUserName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}

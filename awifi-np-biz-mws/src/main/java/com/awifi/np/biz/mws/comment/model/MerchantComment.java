/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月16日 下午2:50:50
* 创建作者：方志伟
* 文件名称：Comment.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mws.comment.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MerchantComment implements Serializable{
    
    /**
     * 序列化id
     */
    private static final long serialVersionUID = -5291084972690354955L;

    /**评论表主键id*/
    private Long commentId;
    
    /**商户编号*/
    private Long merchantId;
    
    /**评论用户编号*/
    private Long commentUserId;

    /**评论人名*/
    private String commentUserName;
    
    /**评论等级*/
    private String commentGrade;
    
    /**评论内容*/
    private String content;
    
    /**
     * 是否置顶（1、置顶；0、不置顶）
     */
    private Integer isTop;
    
    /**
     * 创建时间
     */
    private String createDate;
    
    /**
     * 状态 0：待审；1：已审；2：被举报；9：回收站
     */
    private Integer status;
    
    /**
     * 状态时间
     */
    private Date statusDate;
    
    /**评论图片url*/
    private List<String> commentPicUrls;
    
    /**评论回复数组*/
    private List<MerchantCommentReply> commentReplys;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Long getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(Long commentUserId) {
        this.commentUserId = commentUserId;
    }

    public String getCommentUserName() {
        return commentUserName;
    }

    public void setCommentUserName(String commentUserName) {
        this.commentUserName = commentUserName;
    }

    public String getCommentGrade() {
        return commentGrade;
    }

    public void setCommentGrade(String commentGrade) {
        this.commentGrade = commentGrade;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public List<String> getCommentPicUrls() {
        return commentPicUrls;
    }

    public void setCommentPicUrls(List<String> commentPicUrls) {
        this.commentPicUrls = commentPicUrls;
    }

    public List<MerchantCommentReply> getCommentReplys() {
        return commentReplys;
    }

    public void setCommentReplys(List<MerchantCommentReply> commentReplys) {
        this.commentReplys = commentReplys;
    }
}

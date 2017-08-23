/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月8日 上午9:41:38
* 创建作者：余红伟
* 文件名称：MerchantPic.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mws.merchant.pic.model;

import java.io.Serializable;
import java.util.Date;

import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;

public class MerchantPic implements Serializable{
    public MerchantPic(){
        
    }
    public MerchantPic(Long id,String path,String url){
        this.id=id;
        this.picUrl=url;
        this.picPath=path;
    }
    /**
     * 调用微站接口组成接口返回对象 
     * @param id
     * @param orderNum
     * @param picTitle
     * @param picType
     * @param picPath
     * @param picUrl
     * @param picLink
     * @author 余红伟 
     * @date 2017年6月19日 下午3:41:39
     */
   public MerchantPic(Long id,Integer orderNum,String picTitle,Integer picType,String picPath,String picUrl,String picLink){
       this.id = id;
       this.orderNum = orderNum;
       this.picTitle = picTitle;
       this.picType = picType;
       this.picPath = picPath;
       this.picUrl = picUrl;
       this.picLink = picLink;
   }
    /**
     * 
     */
    private static final long serialVersionUID = -2332319155742052384L;

    /**
     * center_pub_merchant_picture.id: 
     * <p>
     * <code>
     * 主键编号<br>
     * </code>
     */
    private Long id;

    /**
     * center_pub_merchant_picture.merchant_id: 
     * <p>
     * <code>
     * 商户编号<br>
     * </code>
     */
    private Long merchantId;

    /**
     * center_pub_merchant_picture.pic_title: 
     * <p>
     * <code>
     * 图片标题<br>
     * </code>
     */
    private String picTitle;

    /**
     * center_pub_merchant_picture.pic_type: 
     * <p>
     * <code>
     * 图片类型 1：海报；2：照片墙<br>
     * </code>
     */
    private Integer picType;

    /**
     * center_pub_merchant_picture.pic_sub_type: 
     * <p>
     * <code>
     * 图片子类型(预留)<br>
     * </code>
     */
    private Integer picSubType;

    /**
     * center_pub_merchant_picture.pic_path: 
     * <p>
     * <code>
     * 图片路径<br>
     * </code>
     */
    private String picPath;

    /**
     * center_pub_merchant_picture.pic_url: 
     * <p>
     * <code>
     * 超链接<br>
     * </code>
     */
    private String picUrl;


    private String picLink;

    /**
     * center_pub_merchant_picture.order_num: 
     * <p>
     * <code>
     * 显示顺序<br>
     * </code>
     */
    private Integer orderNum;

    /**
     * center_pub_merchant_picture.status: 
     * <p>
     * <code>
     * 状态 1：正常；9：注销<br>
     * </code>
     */
    private Integer status;

    /**
     * center_pub_merchant_picture.status_date: 
     * <p>
     * <code>
     * 状态时间<br>
     * </code>
     */
    private Date statusDate;

    /**
     * center_pub_merchant_picture.remarks: 
     * <p>
     * <code>
     * 描述<br>
     * </code>
     */
    private String remarks;

    /**
     * center_pub_merchant_picture.create_date: 
     * <p>
     * <code>
     * 创建时间<br>
     * </code>
     */
    private Date createDate;

    /**
     * center_pub_merchant_picture.modify_date: 
     * <p>
     * <code>
     * 修改时间<br>
     * </code>
     */
    private Date modifyDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getPicTitle() {
        return picTitle;
    }

    public void setPicTitle(String picTitle) {
        this.picTitle = picTitle;
    }

    public Integer getPicType() {
        return picType;
    }

    public void setPicType(Integer picType) {
        this.picType = picType;
    }

    public Integer getPicSubType() {
        return picSubType;
    }

    public void setPicSubType(Integer picSubType) {
        this.picSubType = picSubType;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPicLink() {
        return picLink;
    }

    public void setPicLink(String picLink) {
        this.picLink = picLink;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}

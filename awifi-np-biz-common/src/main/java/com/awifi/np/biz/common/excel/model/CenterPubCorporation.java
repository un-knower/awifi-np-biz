package com.awifi.np.biz.common.excel.model;

import java.io.Serializable;
import java.util.Date;

/**
 *@ClassName:CenterPubCorporation
 *@author 李程程
 *
 */
public class CenterPubCorporation implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 687036197158058L;

    /**
     * center_pub_corporation.ID:
     * 内部关联使用，AUTO_INCREMENT<br>
     */
    private Long id;

    /**
     * center_pub_corporation.CORP_CODE:
     * 厂家编号
     */
    private String corpCode;

    /**
     * center_pub_corporation.CORP_NAME:
     * 厂家名称
     */
    private String corpName;

    /**
     * center_pub_corporation.MANAGER:
     * 
     */
    private String manager;

    /**
     * center_pub_corporation.PHONE:
     * 联系方式
     */
    private String phone;

    /**
     * center_pub_corporation.EMAIL:
     * email
     */
    private String email;

    /**
     * center_pub_corporation.STATUS:
     * 状态
     */
    private Integer status;

    /**
     * center_pub_corporation.CREATE_DATE:
     * 创建日期
     */
    private Date createDate;

    /**
     * center_pub_corporation.MODIFY_DATE:
     * 修改日期
     */
    private Date modifyDate;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getCorpCode(){
        return corpCode;
    }

    public void setCorpCode(String corpCode){
        this.corpCode = corpCode == null ? null : corpCode.trim();
    }

    public String getCorpName(){
        return corpName;
    }

    public void setCorpName(String corpName){
        this.corpName = corpName == null ? null : corpName.trim();
    }

    public String getManager(){
        return manager;
    }

    public void setManager(String manager){
        this.manager = manager == null ? null : manager.trim();
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email == null ? null : email.trim();
    }

    public Integer getStatus(){
        return status;
    }

    public void setStatus(Integer status){
        this.status = status;
    }

    public Date getCreateDate(){
        return createDate;
    }

    public void setCreateDate(Date createDate){
        this.createDate = createDate;
    }

    public Date getModifyDate(){
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate){
        this.modifyDate = modifyDate;
    }
}
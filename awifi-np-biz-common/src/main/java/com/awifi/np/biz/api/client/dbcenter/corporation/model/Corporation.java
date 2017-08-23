/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月12日 下午7:21:19
* 创建作者：范涌涛
* 文件名称：Corporation.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.corporation.model;

import java.util.Date;

public class Corporation {

    /** 主键 */
    private Long id;

    /** 主键 */
    private String corpCode;

    /** 名称 */
    private String corpName;

    /** 联系人 */
    private String manager;

    /** 联系电话 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 状态 */
    private Integer status;

    /** 新建时间 */
    private Date createDate;

    /** 更新时间 */
    private Date modifyDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorpCode() {
        return corpCode;
    }

    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

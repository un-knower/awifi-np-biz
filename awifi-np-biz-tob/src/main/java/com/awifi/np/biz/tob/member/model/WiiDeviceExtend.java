package com.awifi.np.biz.tob.member.model;

import java.util.Date;

public class WiiDeviceExtend {
    private Integer id;

    private Long device_id;

    private String devId;

    private String code;

    private Byte status;

    private Long merid;

    private String remark;

    private Date modify_date;

    private String modify_operator;

    private Byte modify_type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getDevice_id() {
        return device_id;
    }

    public void setDevice_id(Long device_id) {
        this.device_id = device_id;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId == null ? null : devId.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Long getMerid() {
        return merid;
    }

    public void setMerid(Long merid) {
        this.merid = merid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getModify_date() {
        return modify_date;
    }

    public void setModify_date(Date modify_date) {
        this.modify_date = modify_date;
    }

    public String getModify_operator() {
        return modify_operator;
    }

    public void setModify_operator(String modify_operator) {
        this.modify_operator = modify_operator == null ? null : modify_operator.trim();
    }

    public Byte getModify_type() {
        return modify_type;
    }

    public void setModify_type(Byte modify_type) {
        this.modify_type = modify_type;
    }
}
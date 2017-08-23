/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年3月24日 下午7:19:14
* 创建作者：许小满
* 文件名称：SysConfig.java
* 版本：  v1.0
* 功能：系统参数配置--实体类
* 修改记录：
*/
package com.awifi.np.biz.common.system.sysconfig.model;

import java.io.Serializable;

public class SysConfig implements Serializable {

    /** 对象序列号  */
    private static final long serialVersionUID = 8158443729331458761L;

    /** 主键id */
    private Long id;
    
    /** 名称 */
    private String aliasName;//名称
    
    /** 参数键 */
    private String paramKey;//参数键
    
    /** 参数值 */
    private String paramValue;//参数值
    
    /** 备注 */
    private String remark;//备注
    
    /** 排序号 */
    private Integer orderNo;//排序号
    
    /** 创建时间 */
    private String createDate;//创建时间
    
    /** 修改时间 */
    private String updateDate;//修改时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
    
}

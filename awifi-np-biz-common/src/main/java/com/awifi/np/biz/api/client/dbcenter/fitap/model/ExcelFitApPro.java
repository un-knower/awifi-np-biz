package com.awifi.np.biz.api.client.dbcenter.fitap.model;

import java.math.BigDecimal;
import java.util.Date;

import com.awifi.np.biz.common.excel.model.ExcelColumn;
import com.awifi.np.biz.common.excel.model.Validation;
import com.awifi.np.biz.common.excel.model.Type;
/**
 * 项目型瘦AP的导入属性
 */
public class ExcelFitApPro implements Validation {
    /**
     * id
     */
    private String id;
    /**
     * 省份
     */
    @ExcelColumn(columnNum = 0, columnName = "省", none = false)
    private String province;
    /**
     * 市
     */
    @ExcelColumn(columnNum = 1, columnName = "市", none = false)
    private String city;
    /**
     * 区县
     */
    @ExcelColumn(columnNum = 2, columnName = "区/县", none = false)
    private String county;
    /**
     * mac地址
     */
    @ExcelColumn(columnNum = 3, columnName = "APMAC", columnType = Type.Mac, none = false)
    private String macAddr;
    /**
     * ssid
     */
    @ExcelColumn(columnNum = 4, columnName = "SSID", none = true)
    private String ssid;
    /**
     * 所属ac
     */
    @ExcelColumn(columnNum = 5, columnName = "ACNAME", none = false)
    private String acName;
    /**
     * 
     */
    private String parId;
    /**
     * 
     */
    private String batchNum;
    /**
     * 
     */
    private Integer entityType;
    /**
     * 
     */
    private String entityName;
    /**
     * 
     */
    private String outTypeId;
    /**
     * 
     */
    private String entityOutId;
    /**
     * 
     */
    private Long hotareaId;
    /**
     * 
     */
    private Long acpoolId;
    /**
     * 
     */
    private String corporation;
    /**
     * 
     */
    private String model;
    /**
     * 
     */
    private String fwVersion;
    /**
     * 
     */
    private String cpVersion;
    /**
     * 
     */
    private String pinCode;
    /**
     * 
     */
    private String ipAddr;
    /**
     * 
     */
    private Integer port;
    /**
     * 
     */
    private Long maxBw;
    /**
     * 
     */
    private Long maxCapc;
    /**
     * 
     */
    private Long maxDevconn;
    /**
     * 
     */
    private Long maxStaconn;
    /**
     * 
     */
    private String district;
    /**
     * 
     */
    private BigDecimal xpos;
    /**
     * 
     */
    private BigDecimal ypos;
    /**
     * 
     */
    private String fixAddr;
    /**
     * 
     */
    private String entityAddrType;
    /**
     * 
     */
    private String coverage;
    /**
     * 
     */
    private Integer isCollected;
    /**
     * 
     */
    private Integer snmpVersion;
    /**
     * 
     */
    private String readCom;
    /**
     * 
     */
    private String writeCom;
    /**
     * 
     */
    private String importer;
    /**
     * 
     */
    private Integer salerflag;
    /**
     * 
     */
    private String repairComp;
    /**
     * 
     */
    private Integer flowSts;
    /**
     * 
     */
    private String flowStsBy;
    /**
     * 
     */
    private Date flowStsDate;
    /**
     * 
     */
    private Integer status;
    /**
     * 
     */
    private String statusBy;
    /**
     * 
     */
    private Date statusDate;
    /**
     * 
     */
    private String createBy;
    /**
     * 
     */
    private Date createDate;
    /**
     * 
     */
    private String modifyBy;
    /**
     * 
     */
    private Date modifyDate;
    /**
     * 
     */
    private String remarks;
    /**
     * 
     */
    private String batchCount;

    @Override
    public String getProvince() {
        return province;
    }

    @Override
    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String getCounty() {
        return county;
    }

    @Override
    public void setCounty(String county) {
        this.county = county;
    }

    public String getMacAddr() {
        return macAddr;
    }

    public void setMacAddr(String macAddr) {
        this.macAddr = macAddr;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParId() {
        return parId;
    }

    public void setParId(String parId) {
        this.parId = parId;
    }

    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }

    public Integer getEntityType() {
        return entityType;
    }

    public void setEntityType(Integer entityType) {
        this.entityType = entityType;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getOutTypeId() {
        return outTypeId;
    }

    public void setOutTypeId(String outTypeId) {
        this.outTypeId = outTypeId;
    }

    public String getEntityOutId() {
        return entityOutId;
    }

    public void setEntityOutId(String entityOutId) {
        this.entityOutId = entityOutId;
    }

    public Long getHotareaId() {
        return hotareaId;
    }

    public void setHotareaId(Long hotareaId) {
        this.hotareaId = hotareaId;
    }

    public Long getAcpoolId() {
        return acpoolId;
    }

    public void setAcpoolId(Long acpoolId) {
        this.acpoolId = acpoolId;
    }

    @Override
    public String getCorporation() {
        return corporation;
    }

    @Override
    public void setCorporation(String corporation) {
        this.corporation = corporation;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public void setModel(String model) {
        this.model = model;
    }

    public String getFwVersion() {
        return fwVersion;
    }

    public void setFwVersion(String fwVersion) {
        this.fwVersion = fwVersion;
    }

    public String getCpVersion() {
        return cpVersion;
    }

    public void setCpVersion(String cpVersion) {
        this.cpVersion = cpVersion;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Long getMaxBw() {
        return maxBw;
    }

    public void setMaxBw(Long maxBw) {
        this.maxBw = maxBw;
    }

    public Long getMaxCapc() {
        return maxCapc;
    }

    public void setMaxCapc(Long maxCapc) {
        this.maxCapc = maxCapc;
    }

    public Long getMaxDevconn() {
        return maxDevconn;
    }

    public void setMaxDevconn(Long maxDevconn) {
        this.maxDevconn = maxDevconn;
    }

    public Long getMaxStaconn() {
        return maxStaconn;
    }

    public void setMaxStaconn(Long maxStaconn) {
        this.maxStaconn = maxStaconn;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public BigDecimal getXpos() {
        return xpos;
    }

    public void setXpos(BigDecimal xpos) {
        this.xpos = xpos;
    }

    public BigDecimal getYpos() {
        return ypos;
    }

    public void setYpos(BigDecimal ypos) {
        this.ypos = ypos;
    }

    public String getFixAddr() {
        return fixAddr;
    }

    public void setFixAddr(String fixAddr) {
        this.fixAddr = fixAddr;
    }

    public String getEntityAddrType() {
        return entityAddrType;
    }

    public void setEntityAddrType(String entityAddrType) {
        this.entityAddrType = entityAddrType;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public Integer getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(Integer isCollected) {
        this.isCollected = isCollected;
    }

    public Integer getSnmpVersion() {
        return snmpVersion;
    }

    public void setSnmpVersion(Integer snmpVersion) {
        this.snmpVersion = snmpVersion;
    }

    public String getReadCom() {
        return readCom;
    }

    public void setReadCom(String readCom) {
        this.readCom = readCom;
    }

    public String getWriteCom() {
        return writeCom;
    }

    public void setWriteCom(String writeCom) {
        this.writeCom = writeCom;
    }

    public String getImporter() {
        return importer;
    }

    public void setImporter(String importer) {
        this.importer = importer;
    }

    public Integer getSalerflag() {
        return salerflag;
    }

    public void setSalerflag(Integer salerflag) {
        this.salerflag = salerflag;
    }

    public String getRepairComp() {
        return repairComp;
    }

    public void setRepairComp(String repairComp) {
        this.repairComp = repairComp;
    }

    public Integer getFlowSts() {
        return flowSts;
    }

    public void setFlowSts(Integer flowSts) {
        this.flowSts = flowSts;
    }

    public String getFlowStsBy() {
        return flowStsBy;
    }

    public void setFlowStsBy(String flowStsBy) {
        this.flowStsBy = flowStsBy;
    }

    public Date getFlowStsDate() {
        return flowStsDate;
    }

    public void setFlowStsDate(Date flowStsDate) {
        this.flowStsDate = flowStsDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusBy() {
        return statusBy;
    }

    public void setStatusBy(String statusBy) {
        this.statusBy = statusBy;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getBatchCount() {
        return batchCount;
    }

    public void setBatchCount(String batchCount) {
        this.batchCount = batchCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ExcelFitApPro) {
            ExcelFitApPro excel = (ExcelFitApPro) obj;
            if (excel.getMacAddr() == null) {
                return false;
            }
            if (excel.getMacAddr().equals(this.getMacAddr())) {
                return true;
            }
        }
        return false;
    }
    /**
     * <p>
     * Title:hashCode
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @return
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return 0;

    }
}

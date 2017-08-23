package com.awifi.np.biz.api.client.dbcenter.fatap.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CenterPubEntity implements Serializable {
    /**
    * 
    */
    /** 序列号 */
    private static final long serialVersionUID = 9147684142943390843L;

    /**
     * center_view_entity_info.ID: 主键
     */
    private String id;

    /**
     * center_view_entity_info.PAR_ID: 父设备编号
     */
    private String parId;

    /**
     * center_view_entity_info.BATCH_NUM: 批次号
     */
    private String batchNum;

    /**
     * center_view_entity_info.ENTITY_TYPE: 设备类型
     */
    private Integer entityType;

    /**
     * center_view_entity_info.ENTITY_NAME: 实体设备名称
     */
    private String entityName;

    /**
     * center_view_entity_info.OUT_TYPE_ID: 外部平台编号
     */
    private String outTypeId;

    /**
     * center_view_entity_info.ENTITY_OUT_ID: 设备外部编号
     */
    private String entityOutId;

    /**
     * center_view_entity_info.HOTAREA_ID: 热点编号
     */
    private String hotareaId;

    /**
     * center_view_entity_info.ACPOOL_ID: AC池编号
     */
    private Long acpoolId;

    /**
     * center_view_entity_info.CORPORATION: 厂家
     */
    private String corporation;

    /**
     * 翻译
     */
    private String corporationText;

    /**
     * center_view_entity_info.MODEL: 型号
     */
    private String model;

    /**
     * 翻译
     */
    private String modelText;

    /**
     * center_view_entity_info.FW_VERSION: 固件版本号
     */
    private String fwVersion;

    /**
     * center_view_entity_info.CP_VERSION: 组件版本号
     */
    private String cpVersion;

    /**
     * center_view_entity_info.PIN_CODE: PING码
     */
    private String pinCode;

    /**
     * center_view_entity_info.IP_ADDR: 设备地址
     */
    private String ipAddr;

    /**
     * center_view_entity_info.PORT: 端口
     */
    private Integer port;

    /**
     * center_view_entity_info.MAX_BW: 最大带宽
     */
    private Long maxBw;

    /**
     * center_view_entity_info.MAX_CAPC: 交换容量
     */
    private Long maxCapc;

    /**
     * center_view_entity_info.MAX_DEVCONN: 最大连接数量
     */
    private Long maxDevconn;

    /**
     * center_view_entity_info.MAX_STACONN: 最大STA终端连接数量
     */
    private Long maxStaconn;

    /**
     * center_view_entity_info.MAC_ADDR: 设备物理地址
     */
    private String macAddr;

    /**
     * center_view_entity_info.PROVINCE: 省
     */
    private Integer province;

    /**
     * center_view_entity_info.CITY: 市
     */
    private Integer city;

    /**
     * center_view_entity_info.COUNTY: 县
     */
    private Integer county;

    /**
     * center_view_entity_info.DISTRICT: 街道
     */
    private String district;

    /**
     * center_view_entity_info.XPOS: 经度
     */
    private BigDecimal xpos;

    /**
     * center_view_entity_info.YPOS: 纬度
     */
    private BigDecimal ypos;

    /**
     * center_view_entity_info.FIX_ADDR: 安装详细地址
     */
    private String fixAddr;

    /**
     * center_view_entity_info.ENTITY_ADDR_TYPE: 设备属性
     */
    private String entityAddrType;

    /**
     * center_view_entity_info.COVERAGE:
     */
    private String coverage;

    /**
     * center_view_entity_info.IS_COLLECTED: 是否需要采集
     */
    private Integer isCollected;

    /**
     * center_view_entity_info.SNMP_PORT: 采集SNMP端口
     */
    private Integer snmpPort;

    /**
     * center_view_entity_info.SNMP_VERSION: 采集SNMP协议版本
     */
    private Integer snmpVersion;

    /**
     * center_view_entity_info.READ_COM: 读团体名
     */
    private String readCom;

    /**
     * center_view_entity_info.WRITE_COM: 写团体名
     */
    private String writeCom;

    /**
     * center_view_entity_info.IMPORTER: 设备录入/导入者
     */
    private String importer;

    /**
     * center_view_entity_info.SALERFLAG: 设备销售者
     */
    private Integer salerflag;

    /**
     * center_view_entity_info.REPAIR_COMP: 代维公司
     */
    private String repairComp;

    /**
     * center_view_entity_info.FLOW_STS: 流程状态
     */
    private Integer flowSts;

    /**
     * center_view_entity_info.FLOW_STS_BY: 流程状态修改人
     */
    private String flowStsBy;

    /**
     * center_view_entity_info.FLOW_STS_DATE: 流程状态时间
     */
    private Date flowStsDate;

    /**
     * center_view_entity_info.STATUS: 设备状态
     */
    private Integer status;

    /**
     * center_view_entity_info.STATUS_BY: 设备状态修改人
     */
    private String statusBy;

    /**
     * center_view_entity_info.STATUS_DATE: 设备状态修改时间
     */
    private Date statusDate;

    /**
     * center_view_entity_info.CREATE_BY: 创建人
     */
    private String createBy;

    /**
     * center_view_entity_info.CREATE_DATE: 创建时间
     */
    private Date createDate;

    /**
     * center_view_entity_info.MODIFY_BY: 修改人
     */
    private String modifyBy;

    /**
     * center_view_entity_info.MODIFY_DATE: 修改时间
     */
    private Date modifyDate;

    /**
     * center_view_entity_info.REMARKS: 备注
     */
    private String remarks;

    /**
     * 虚拟设备表主键id
     */
    private Long dId;

    /**
     * center_view_entity_info.DEVICE_ID: 虚拟设备编号
     */
    private String deviceId;

    /**
     * center_view_entity_info.DEVICE_NAME:
     */
    private String deviceName;

    /**
     * center_view_entity_info.SSID: SSID
     */
    private String ssid;

    /**
     * center_view_entity_info.DEV_PROVINCE: 省
     */
    private Integer devProvince;

    /**
     * center_view_entity_info.DEV_CITY: 市
     */
    private Integer devCity;

    /**
     * center_view_entity_info.DEV_COUNTY:
     */
    private Integer devCounty;

    /**
     * center_view_entity_info.ACCOUNT_ID: 设备所属商户编号
     */
    private Long merchantId;

    /**
     * center_view_entity_info.PROJECT_ID: 工程编号
     */
    private Long projectId;

    /**
     * center_view_entity_info.MINS_LIMIT: 设备时长上限
     */
    private Long minsLimit;

    /**
     * center_view_entity_info.TRAFFIC_LIMIT: 设备流程上限
     */
    private Long trafficLimit;

    /**
     * center_view_entity_info.WAN_PROTOCOL: 联网模式
     */
    private String wanProtocol;

    /**
     * 父设备名称 parEntityName
     */
    private String parEntityName;

    /**
     * 热点名称 hotareaName
     */
    private String hotareaName;

    /**
     * 用于翻译 province
     */
    private String provinceText;

    /**
     * 用于翻译 city
     */
    private String cityText;

    /**
     * 用于翻译 county
     */
    private String countyText;

    /**
     * 激活状态
     */
    private Integer activateStatus;

    /**
     * 属于
     */
    private String belongto;

    // baseEntity的属性

    /** 流程编码key */
    private String globalKey;

    /** 流程编码值 */
    private String globalValue;

    /** 流程编码备用字段 */
    private String globalStandby;

    /**
     * @Fields token : 记号
     */
    private String token;

    /**
     * @Fields seed : 来源
     */
    private String seed;
    
    /**
     * nas过滤主键id
     */
    private Long nasId;
    
    /**
     * 有效ip前段
     */
    private String ipSectionBegin;
    
    /**
     * 有效ip后段
     */
    private String ipSectionEnd;

    /**
     * @Fields pageNum : 分页每页个数
     */
    private Integer pageNum;

    /**
     * @Fields pageSize : 分页页码
     */
    private Integer pageSize;

    
    /**
     * center_view_hotarea_info.HOTAREA_OUT_ID:
     * <p>
     * <code>
     * 外部编号<br>
     * </code>
     */
    private String hotareaOutId;
    
    /**
     * 热点类型
     */
    private String hotareaType;
    /**
     * 热点等级
     */
    private String hotareaDegree;
    /**
     * pvlan
     */
    private String pvlan;
    /**
     * cvlan
     */
    private String cvlan;
    /**
     * vlan
     */
    private String vlan;
    /**
     * bras名称
     */
    private String brasName;
    /**
     * brasIP
     */
    private String brasIp;
    /**
     * bas机架号
     */
    private String brasShel;
    /**
     * BRAS槽位号
     */
    private String brasSlot;
    /**
     * bras端口
     */
    private String brasPort;
    /**
     * 帐号
     */
    private String acount;
    /**
     * ac名称
     */
    private String acName;
    /**
     * 归属nas
     */
    private String nas;
    /**
     * 网段
     */
    private String segment;
    /**
     * 下挂AP数
     */
    private Long apNum;
    /**
     * bras端口类型
     */
    private String brasPortType;
    /**
     * wlan干放数
     */
    private Long wlanGanfangNum;
    /**
     * 业主
     */
    private String owner;
    /**
     * 业主联系方式
     */
    private String ownerPhone;
    /**
     * 是否有室分 0:否 1:是
     */
    private Integer ishasshifen;
    /**
     * 是否与室分合路 0:否 1:是
     */
    private Integer islineshifen;
    /**
     * 接入电路编号
     */
    private String accessNo;
    /**
     * 热点地址 
     */
    private String hotarea;
    /**
     * 地标
     */
    private String landmark;
    /**
     * 集成商
     */
    private String intergrator;
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 批次数量
     */
    private Long batchCount;
    
    public Long getBatchCount() {
        return batchCount;
    }

    public void setBatchCount(Long batchCount) {
        this.batchCount = batchCount;
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
        this.batchNum = batchNum == null ? null : batchNum.trim();
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
        this.entityName = entityName == null ? null : entityName.trim();
    }

    public String getOutTypeId() {
        return outTypeId;
    }

    public void setOutTypeId(String outTypeId) {
        this.outTypeId = outTypeId == null ? null : outTypeId.trim();
    }

    public String getEntityOutId() {
        return entityOutId;
    }

    public void setEntityOutId(String entityOutId) {
        this.entityOutId = entityOutId == null ? null : entityOutId.trim();
    }

    public String getHotareaId() {
        return hotareaId;
    }

    public void setHotareaId(String hotareaId) {
        this.hotareaId = hotareaId;
    }

    public Long getAcpoolId() {
        return acpoolId;
    }

    public void setAcpoolId(Long acpoolId) {
        this.acpoolId = acpoolId;
    }

    public String getCorporation() {
        return corporation;
    }

    public void setCorporation(String corporation) {
        this.corporation = corporation == null ? null : corporation.trim();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getFwVersion() {
        return fwVersion;
    }

    public void setFwVersion(String fwVersion) {
        this.fwVersion = fwVersion == null ? null : fwVersion.trim();
    }

    public String getCpVersion() {
        return cpVersion;
    }

    public void setCpVersion(String cpVersion) {
        this.cpVersion = cpVersion == null ? null : cpVersion.trim();
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode == null ? null : pinCode.trim();
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr == null ? null : ipAddr.trim();
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

    public String getMacAddr() {
        return macAddr;
    }

    public void setMacAddr(String macAddr) {
        this.macAddr = macAddr == null ? null : macAddr.trim();
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getCounty() {
        return county;
    }

    public void setCounty(Integer county) {
        this.county = county;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
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
        this.fixAddr = fixAddr == null ? null : fixAddr.trim();
    }

    public String getEntityAddrType() {
        return entityAddrType;
    }

    public void setEntityAddrType(String entityAddrType) {
        this.entityAddrType = entityAddrType == null
                ? null
                : entityAddrType.trim();
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage == null ? null : coverage.trim();
    }

    public Integer getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(Integer isCollected) {
        this.isCollected = isCollected;
    }

    public Integer getSnmpPort() {
        return snmpPort;
    }

    public void setSnmpPort(Integer snmpPort) {
        this.snmpPort = snmpPort;
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
        this.readCom = readCom == null ? null : readCom.trim();
    }

    public String getWriteCom() {
        return writeCom;
    }

    public void setWriteCom(String writeCom) {
        this.writeCom = writeCom == null ? null : writeCom.trim();
    }

    public String getImporter() {
        return importer;
    }

    public void setImporter(String importer) {
        this.importer = importer == null ? null : importer.trim();
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
        this.repairComp = repairComp == null ? null : repairComp.trim();
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
        this.flowStsBy = flowStsBy == null ? null : flowStsBy.trim();
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
        this.statusBy = statusBy == null ? null : statusBy.trim();
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
        this.createBy = createBy == null ? null : createBy.trim();
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
        this.modifyBy = modifyBy == null ? null : modifyBy.trim();
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
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid == null ? null : ssid.trim();
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getMinsLimit() {
        return minsLimit;
    }

    public void setMinsLimit(Long minsLimit) {
        this.minsLimit = minsLimit;
    }

    public Long getTrafficLimit() {
        return trafficLimit;
    }

    public void setTrafficLimit(Long trafficLimit) {
        this.trafficLimit = trafficLimit;
    }

    public String getWanProtocol() {
        return wanProtocol;
    }

    public void setWanProtocol(String wanProtocol) {
        this.wanProtocol = wanProtocol == null ? null : wanProtocol.trim();
    }

    public String getParEntityName() {
        return parEntityName;
    }

    public void setParEntityName(String parEntityName) {
        this.parEntityName = parEntityName;
    }

    public String getHotareaName() {
        return hotareaName;
    }

    public void setHotareaName(String hotareaName) {
        this.hotareaName = hotareaName;
    }

    public String getProvinceText() {
        return provinceText;
    }

    public void setProvinceText(String provinceText) {
        this.provinceText = provinceText;
    }

    public String getCityText() {
        return cityText;
    }

    public void setCityText(String cityText) {
        this.cityText = cityText;
    }

    public String getCountyText() {
        return countyText;
    }

    public void setCountyText(String countyText) {
        this.countyText = countyText;
    }

    public String getCorporationText() {
        return corporationText;
    }

    public void setCorporationText(String corporationText) {
        this.corporationText = corporationText;
    }

    public String getModelText() {
        return modelText;
    }

    public void setModelText(String modelText) {
        this.modelText = modelText;
    }

    public Integer getDevProvince() {
        return devProvince;
    }

    public void setDevProvince(Integer devProvince) {
        this.devProvince = devProvince;
    }

    public Integer getDevCity() {
        return devCity;
    }

    public void setDevCity(Integer devCity) {
        this.devCity = devCity;
    }

    public Integer getDevCounty() {
        return devCounty;
    }

    public void setDevCounty(Integer devCounty) {
        this.devCounty = devCounty;
    }

    public Long getdId() {
        return dId;
    }

    public void setdId(Long dId) {
        this.dId = dId;
    }

    public Integer getActivateStatus() {
        return activateStatus;
    }

    public void setActivateStatus(Integer activateStatus) {
        this.activateStatus = activateStatus;
    }

    public String getBelongto() {
        return belongto;
    }

    public void setBelongto(String belongto) {
        this.belongto = belongto;
    }

    public String getGlobalKey() {
        return globalKey;
    }

    public void setGlobalKey(String globalKey) {
        this.globalKey = globalKey;
    }

    public String getGlobalValue() {
        return globalValue;
    }

    public void setGlobalValue(String globalValue) {
        this.globalValue = globalValue;
    }

    public String getGlobalStandby() {
        return globalStandby;
    }

    public void setGlobalStandby(String globalStandby) {
        this.globalStandby = globalStandby;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public String getIpSectionBegin() {
        return ipSectionBegin;
    }

    public void setIpSectionBegin(String ipSectionBegin) {
        this.ipSectionBegin = ipSectionBegin;
    }

    public String getIpSectionEnd() {
        return ipSectionEnd;
    }

    public void setIpSectionEnd(String ipSectionEnd) {
        this.ipSectionEnd = ipSectionEnd;
    }

    public Long getNasId() {
        return nasId;
    }

    public void setNasId(Long nasId) {
        this.nasId = nasId;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getHotareaOutId() {
        return hotareaOutId;
    }

    public void setHotareaOutId(String hotareaOutId) {
        this.hotareaOutId = hotareaOutId;
    }

    public String getHotareaType() {
        return hotareaType;
    }

    public void setHotareaType(String hotareaType) {
        this.hotareaType = hotareaType;
    }

    public String getHotareaDegree() {
        return hotareaDegree;
    }

    public void setHotareaDegree(String hotareaDegree) {
        this.hotareaDegree = hotareaDegree;
    }

    public String getPvlan() {
        return pvlan;
    }

    public void setPvlan(String pvlan) {
        this.pvlan = pvlan;
    }

    public String getCvlan() {
        return cvlan;
    }

    public void setCvlan(String cvlan) {
        this.cvlan = cvlan;
    }

    public String getVlan() {
        return vlan;
    }

    public void setVlan(String vlan) {
        this.vlan = vlan;
    }

    public String getBrasName() {
        return brasName;
    }

    public void setBrasName(String brasName) {
        this.brasName = brasName;
    }

    public String getBrasIp() {
        return brasIp;
    }

    public void setBrasIp(String brasIp) {
        this.brasIp = brasIp;
    }

    public String getBrasShel() {
        return brasShel;
    }

    public void setBrasShel(String brasShel) {
        this.brasShel = brasShel;
    }

    public String getBrasSlot() {
        return brasSlot;
    }

    public void setBrasSlot(String brasSlot) {
        this.brasSlot = brasSlot;
    }

    public String getBrasPort() {
        return brasPort;
    }

    public void setBrasPort(String brasPort) {
        this.brasPort = brasPort;
    }

    public String getAcount() {
        return acount;
    }

    public void setAcount(String acount) {
        this.acount = acount;
    }

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName;
    }

    public String getNas() {
        return nas;
    }

    public void setNas(String nas) {
        this.nas = nas;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public Long getApNum() {
        return apNum;
    }

    public void setApNum(Long apNum) {
        this.apNum = apNum;
    }

    public String getBrasPortType() {
        return brasPortType;
    }

    public void setBrasPortType(String brasPortType) {
        this.brasPortType = brasPortType;
    }

    public Long getWlanGanfangNum() {
        return wlanGanfangNum;
    }

    public void setWlanGanfangNum(Long wlanGanfangNum) {
        this.wlanGanfangNum = wlanGanfangNum;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public Integer getIshasshifen() {
        return ishasshifen;
    }

    public void setIshasshifen(Integer ishasshifen) {
        this.ishasshifen = ishasshifen;
    }

    public Integer getIslineshifen() {
        return islineshifen;
    }

    public void setIslineshifen(Integer islineshifen) {
        this.islineshifen = islineshifen;
    }

    public String getAccessNo() {
        return accessNo;
    }

    public void setAccessNo(String accessNo) {
        this.accessNo = accessNo;
    }

    public String getHotarea() {
        return hotarea;
    }

    public void setHotarea(String hotarea) {
        this.hotarea = hotarea;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getIntergrator() {
        return intergrator;
    }

    public void setIntergrator(String intergrator) {
        this.intergrator = intergrator;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}

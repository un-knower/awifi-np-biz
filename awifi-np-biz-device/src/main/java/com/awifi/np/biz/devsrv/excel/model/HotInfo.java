/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月17日 下午3:53:19
* 创建作者：范涌涛
* 文件名称：HotInfo.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.excel.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class HotInfo implements Serializable {
    /** 序列号 */
    private static final long serialVersionUID = 114860683805802L;

    /**
     * center_view_hotarea_info.ID:
     * <p>
     * <code>
     * 主键<br>
     * </code>
     */
    private String id;

    /**
     * center_pub_hotarea.importer
     * <p>
     * <code>
     * 设备录入/导入者<br>
     * </code>
     */
    private String importer;

    /**
     * center_view_hotarea_info.HOTAREA_NAME:
     * <p>
     * <code>
     * 热点名称<br>
     * </code>
     */
    private String hotareaName;

    /**
     * center_view_hotarea_info.HOTAREA_OUT_ID:
     * <p>
     * <code>
     * 外部编号<br>
     * </code>
     */
    private String hotareaOutId;

    /**
     * center_view_hotarea_info.OUT_TYPE_ID:
     * <p>
     * <code>
     * 外部平台编号<br>
     * </code>
     */
    private String outTypeId;

    /**
     * center_view_hotarea_info.PROVINCE:
     * <p>
     * <code>
     * 省份<br>
     * </code>
     */
    private Integer province;

    /**
     * center_view_hotarea_info.CITY:
     * <p>
     * <code>
     * 地市<br>
     * </code>
     */
    private Integer city;

    /**
     * center_view_hotarea_info.COUNTY:
     * <p>
     * <code>
     * 县<br>
     * </code>
     */
    private Integer county;

    /**
     * center_view_hotarea_info.XPOS:
     * <p>
     * <code>
     * 经度<br>
     * </code>
     */
    private BigDecimal xpos;

    /**
     * center_view_hotarea_info.YPOS:
     * <p>
     * <code>
     * 纬度<br>
     * </code>
     */
    private BigDecimal ypos;

    /**
     * center_view_hotarea_info.HOTAREA:
     * <p>
     * <code>
     * 热点地址<br>
     * </code>
     */
    private String hotarea;

    /**
     * center_view_hotarea_info.LANDMARK:
     * <p>
     * <code>
     * 地标<br>
     * </code>
     */
    private String landmark;

    /**
     * center_view_hotarea_info.HOTAREA_TYPE:
     * <p>
     * <code>
     * 热点类型<br>
     * </code>
     */
    private String hotareaType;

    /**
     * center_view_hotarea_info.HOTAREA_DEGREE:
     * <p>
     * <code>
     * 热点等级<br>
     * </code>
     */
    private String hotareaDegree;

    /**
     * center_view_hotarea_info.BRAS_NAME:
     * <p>
     * <code>
     * 对应的bras名称<br>
     * </code>
     */
    private String brasName;

    /**
     * center_view_hotarea_info.BRAS_IP:
     * <p>
     * <code>
     * BRAS-IP地址<br>
     * </code>
     */
    private String brasIp;

    /**
     * center_view_hotarea_info.BRAS_PORT_TYPE:
     * <p>
     * <code>
     * bras端口类型<br>
     * </code>
     */
    private String brasPortType;

    /**
     * center_view_hotarea_info.BRAS_PORT:
     * <p>
     * <code>
     * BRAS端口<br>
     * </code>
     */
    private String brasPort;

    /**
     * center_view_hotarea_info.BRAS_SHEL:
     * <p>
     * <code>
     * bas机架号<br>
     * </code>
     */
    private String brasShel;

    /**
     * center_view_hotarea_info.BRAS_SLOT:
     * <p>
     * <code>
     * BRAS槽位号<br>
     * </code>
     */
    private String brasSlot;

    /**
     * center_view_hotarea_info.AC_NAME:
     * <p>
     * <code>
     * 对应的ac名称<br>
     * </code>
     */
    private String acName;

    /**
     * center_view_hotarea_info.AP_NUM:
     * <p>
     * <code>
     * 下挂AP数<br>
     * </code>
     */
    private Long apNum;

    /**
     * center_view_hotarea_info.PVLAN:
     * <p>
     * <code>
     * PVLAN<br>
     * </code>
     */
    private String pvlan;

    /**
     * center_view_hotarea_info.CVLAN:
     * <p>
     * <code>
     * CVLAN<br>
     * </code>
     */
    private String cvlan;

    /**
     * center_view_hotarea_info.VLAN:
     * <p>
     * <code>
     * VLAN<br>
     * </code>
     */
    private String vlan;

    /**
     * center_view_hotarea_info.NAS:
     * <p>
     * <code>
     * 接入NAS归属域<br>
     * </code>
     */
    private String nas;

    /**
     * center_view_hotarea_info.SEGMENT:
     * <p>
     * <code>
     * 网段<br>
     * </code>
     */
    private String segment;

    /**
     * center_view_hotarea_info.ACOUNT:
     * <p>
     * <code>
     * 账号<br>
     * </code>
     */
    private String acount;

    /**
     * center_view_hotarea_info.WLAN_GANFANG_NUM:
     * <p>
     * <code>
     * wlan干放数<br>
     * </code>
     */
    private Long wlanGanfangNum;

    /**
     * center_view_hotarea_info.OWNER:
     * <p>
     * <code>
     * 业主<br>
     * </code>
     */
    private String owner;

    /**
     * center_view_hotarea_info.OWNER_PHONE:
     * <p>
     * <code>
     * 业主联系电话<br>
     * </code>
     */
    private String ownerPhone;

    /**
     * center_view_hotarea_info.ISHASSHIFEN:
     * <p>
     * <code>
     * 是否有室分<br>
     * </code>
     */
    private Integer ishasshifen;

    /**
     * center_view_hotarea_info.ISLINESHIFEN:
     * <p>
     * <code>
     * 是否与室分合路<br>
     * </code>
     */
    private Integer islineshifen;

    /**
     * center_view_hotarea_info.ACCESS_NO:
     * <p>
     * <code>
     * 接入电路编号<br>
     * </code>
     */
    private String accessNo;

    /**
     * center_view_hotarea_info.INTERGRATOR:
     * <p>
     * <code>
     * 集成商<br>
     * </code>
     */
    private String intergrator;

    /**
     * center_view_hotarea_info.REPAIR_COMP:
     * <p>
     * <code>
     * 代维公司<br>
     * </code>
     */
    private String repairComp;
    /**
     * center_view_hotarea_info.FLOW_STS:
     * <p>
     * <code>
     * 流程状态<br>
     * </code>
     */
    private Integer flowSts;

    /**
     * center_view_hotarea_info.FLOW_STS_BY:
     * <p>
     * <code>
     * 流程状态修改人<br>
     * </code>
     */
    private String flowStsBy;

    /**
     * center_view_hotarea_info.FLOW_STS_DATE:
     * <p>
     * <code>
     * 流程状态时间<br>
     * </code>
     */
    private Date flowStsDate;
    /**
     * center_view_hotarea_info.STATUS:
     * <p>
     * <code>
     * 状态<br>
     * </code>
     */
    private Integer status;

    /**
     * center_view_hotarea_info.CREATE_BY:
     * <p>
     * <code>
     * 创建人<br>
     * </code>
     */
    private String createBy;

    /**
     * center_view_hotarea_info.CREATE_DATE:
     * <p>
     * <code>
     * 创建时间<br>
     * </code>
     */
    private Date createDate;

    /**
     * center_view_hotarea_info.MODIFY_BY:
     * <p>
     * <code>
     * 修改人<br>
     * </code>
     */
    private String modifyBy;

    /**
     * center_view_hotarea_info.MODIFY_DATE:
     * <p>
     * <code>
     * 修改时间<br>
     * </code>
     */
    private Date modifyDate;

    /**
     * center_view_hotarea_info.REMARK:
     * <p>
     * <code>
     * 备注<br>
     * </code>
     */
    private String remark;

    /**
     * center_view_hotarea_info.DEVICE_ID:
     * <p>
     * <code>
     * 虚拟设备编号<br>
     * </code>
     */
    private String deviceId;

    /**
     * center_view_hotarea_info.DEVICE_NAME:
     * <p>
     */
    private String deviceName;

    /**
     * center_view_hotarea_info.SSID:
     * <p>
     * <code>
     * SSID<br>
     * </code>
     */
    private String ssid;
    /**
     * center_view_entity_info.DEV_PROVINCE:
     * <p>
     * <code>
     * 省<br>
     * </code>
     */
    private Integer devProvince;

    /**
     * center_view_entity_info.DEV_CITY:
     * <p>
     * <code>
     * 市<br>
     * </code>
     */
    private Integer devCity;

    /**
     * center_view_entity_info.DEV_COUNTY:
     * <p>
     */
    private Integer devCounty;
    /**
     * center_view_hotarea_info.ACCOUNT_ID:
     * <p>
     * <code>
     * 设备所属商户编号<br>
     * </code>
     */
    private Long merchantId;

    /**
     * center_view_hotarea_info.PROJECT_ID:
     * <p>
     * <code>
     * 工程编号<br>
     * </code>
     */
    private Long projectId;

    /**
     * center_view_hotarea_info.MINS_LIMIT:
     * <p>
     * <code>
     * 设备时长上限<br>
     * </code>
     */
    private Long minsLimit;

    /**
     * center_view_hotarea_info.TRAFFIC_LIMIT:
     * <p>
     * <code>
     * 设备流程上限<br>
     * </code>
     */
    private Long trafficLimit;
    
    /**
     * marketingStaffNumber
     */
    private String marketingStaffNumber;
    
    /**
     * isVpn
     */
    private Integer isVpn;
    
    /**
     * broadbandAccount
     */
    private String broadbandAccount;

    /**
     * center_view_hotarea_info.WAN_PROTOCOL:
     * <p>
     * <code>
     * 联网模式<br>
     * </code>
     */
    private String wanProtocol;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHotareaName() {
        return hotareaName;
    }

    public void setHotareaName(String hotareaName) {
        this.hotareaName = hotareaName == null ? null : hotareaName.trim();
    }

    public String getHotareaOutId() {
        return hotareaOutId;
    }

    public void setHotareaOutId(String hotareaOutId) {
        this.hotareaOutId = hotareaOutId == null ? null : hotareaOutId.trim();
    }

    public String getOutTypeId() {
        return outTypeId;
    }

    public void setOutTypeId(String outTypeId) {
        this.outTypeId = outTypeId == null ? null : outTypeId.trim();
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

    public String getHotarea() {
        return hotarea;
    }

    public void setHotarea(String hotarea) {
        this.hotarea = hotarea == null ? null : hotarea.trim();
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark == null ? null : landmark.trim();
    }

    public String getHotareaType() {
        return hotareaType;
    }

    public void setHotareaType(String hotareaType) {
        this.hotareaType = hotareaType == null ? null : hotareaType.trim();
    }

    public String getHotareaDegree() {
        return hotareaDegree;
    }

    public void setHotareaDegree(String hotareaDegree) {
        this.hotareaDegree = hotareaDegree == null ? null : hotareaDegree.trim();
    }

    public String getBrasName() {
        return brasName;
    }

    public void setBrasName(String brasName) {
        this.brasName = brasName == null ? null : brasName.trim();
    }

    public String getBrasIp() {
        return brasIp;
    }

    public void setBrasIp(String brasIp) {
        this.brasIp = brasIp == null ? null : brasIp.trim();
    }

    public String getBrasPortType() {
        return brasPortType;
    }

    public void setBrasPortType(String brasPortType) {
        this.brasPortType = brasPortType == null ? null : brasPortType.trim();
    }

    public String getBrasPort() {
        return brasPort;
    }

    public void setBrasPort(String brasPort) {
        this.brasPort = brasPort == null ? null : brasPort.trim();
    }

    public String getBrasShel() {
        return brasShel;
    }

    public void setBrasShel(String brasShel) {
        this.brasShel = brasShel == null ? null : brasShel.trim();
    }

    public String getBrasSlot() {
        return brasSlot;
    }

    public void setBrasSlot(String brasSlot) {
        this.brasSlot = brasSlot == null ? null : brasSlot.trim();
    }

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName == null ? null : acName.trim();
    }

    public Long getApNum() {
        return apNum;
    }

    public void setApNum(Long apNum) {
        this.apNum = apNum;
    }

    public String getPvlan() {
        return pvlan;
    }

    public void setPvlan(String pvlan) {
        this.pvlan = pvlan == null ? null : pvlan.trim();
    }

    public String getCvlan() {
        return cvlan;
    }

    public void setCvlan(String cvlan) {
        this.cvlan = cvlan == null ? null : cvlan.trim();
    }

    public String getVlan() {
        return vlan;
    }

    public void setVlan(String vlan) {
        this.vlan = vlan == null ? null : vlan.trim();
    }

    public String getNas() {
        return nas;
    }

    public void setNas(String nas) {
        this.nas = nas == null ? null : nas.trim();
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment == null ? null : segment.trim();
    }

    public String getAcount() {
        return acount;
    }

    public void setAcount(String acount) {
        this.acount = acount == null ? null : acount.trim();
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
        this.owner = owner == null ? null : owner.trim();
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone == null ? null : ownerPhone.trim();
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
        this.accessNo = accessNo == null ? null : accessNo.trim();
    }

    public String getIntergrator() {
        return intergrator;
    }

    public void setIntergrator(String intergrator) {
        this.intergrator = intergrator == null ? null : intergrator.trim();
    }

    public String getRepairComp() {
        return repairComp;
    }

    public void setRepairComp(String repairComp) {
        this.repairComp = repairComp == null ? null : repairComp.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public String getImporter() {
        return importer;
    }

    public void setImporter(String importer) {
        this.importer = importer;
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

    public String getMarketingStaffNumber() {
        return marketingStaffNumber;
    }

    public void setMarketingStaffNumber(String marketingStaffNumber) {
        this.marketingStaffNumber = marketingStaffNumber;
    }

    public Integer getIsVpn() {
        return isVpn;
    }

    public void setIsVpn(Integer isVpn) {
        this.isVpn = isVpn;
    }

    public String getBroadbandAccount() {
        return broadbandAccount;
    }

    public void setBroadbandAccount(String broadbandAccount) {
        this.broadbandAccount = broadbandAccount;
    }

}
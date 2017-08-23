/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月16日 下午4:26:08
* 创建作者：范涌涛
* 文件名称：ExcelHotPoint.java
* 版本：  v1.0
* 功能：热点导入 excel对应实体类
* 修改记录：
*/
package com.awifi.np.biz.devsrv.excel.model;

import java.math.BigDecimal;
import java.util.Date;

import com.awifi.np.biz.common.excel.model.ExcelColumn;
import com.awifi.np.biz.common.excel.model.Validation;
import com.awifi.np.biz.common.excel.model.Type;

public class ExcelHot implements Validation {
    /** 热点编号 */
    private Long hotareaid;

    /** 场点编号 */
    private Long spotid;

    /** 热点编码 */
    private String hotareacode;

    /** 创建日期 */
    private Date createdate;

    /** 创建人 */
    private Long createby;

    /** 修改时间 */
    private Date modifydate;

    /** 修改人 */
    private Long modifyby;

    /** 场点状态1:正常9:作废 */
    private String status;

    /** 外部平台编号00：本系统01：CHINANET-ZJ31：CHINANET-TW */
    private String outtypeid;

    /** 热点名称 */
    @ExcelColumn(columnNum = 0, columnName = "热点名称", length = 70, none = false)
    private String hotareaname;

    /** 热点外部编号,excel中为集团编号 */
    @ExcelColumn(columnNum = 1, columnName = "热点外部编号", length = 30)
    private String hotareaoutid;

    /** 国籍 */
    @ExcelColumn(columnNum = 2, columnName = "国籍", length = 70, none = false)
    private String countryid;

    /** 省份 */
    @ExcelColumn(columnNum = 3, columnName = "省份", length = 70, none = false)
    private String provinceid;

    /** 地市 */
    @ExcelColumn(columnNum = 4, columnName = "地市", length = 70, none = false)
    private String cityid;

    /** 县 */
    @ExcelColumn(columnNum = 5, columnName = "县", length = 70, none = false)
    private String county;

    /** 经度 */
    @ExcelColumn(columnNum = 6, columnName = "经度", length = 70, columnType = Type.LONGI)
    private BigDecimal xpos;

    /** 纬度 */
    @ExcelColumn(columnNum = 7, columnName = "纬度", length = 70, columnType = Type.LATI)
    private BigDecimal ypos;

    /** 热点类型 */
    @ExcelColumn(columnNum = 8, columnName = "热点类型", length = 70, none = false)
    private String hotareatype;

    /** 热点等级 */
    @ExcelColumn(columnNum = 9, columnName = "热点等级", length = 70, none = false)
    private String hotareadegree;

    /** pvlan */
    @ExcelColumn(columnNum = 10, columnName = "PVLAN", columnType = Type.Num, length = 10, none = false)
    private String pvlan;

    /** cvlan */
    @ExcelColumn(columnNum = 11, columnName = "CVLAN", columnType = Type.Num, length = 10, none = false)
    private String cvlan;

    /** vlan */
    @ExcelColumn(columnNum = 12, columnName = "VLAN", length = 10, none = false)
    private String vlan;

    /** brasID,excel中为BRASNAME */
    @ExcelColumn(columnNum = 13, columnName = "brasID", length = 50, none = false)
    private String brasId;

    /** bas的IP */
    @ExcelColumn(columnNum = 14, columnName = "BRAS-IP", length = 70, columnType = Type.Ip)
    private String basip;

    /** bas机架号 */
    @ExcelColumn(columnNum = 15, columnName = "BRAS机架号", length = 50)
    private String shel;

    /** BRAS槽位号 */
    @ExcelColumn(columnNum = 16, columnName = "BRAS槽位号", length = 50)
    private String slot;

    /** BRAS端口 */
    @ExcelColumn(columnNum = 17, columnName = "BRAS端口", length = 70, none = false)
    private String hotport;

    /** SSID */
    @ExcelColumn(columnNum = 18, columnName = "SSID", length = 30, none = false)
    private String ssid;
    /** 账号 */
    @ExcelColumn(columnNum = 19, columnName = "账号", length = 30, none = false)
    private String acount;

    /** 所属AC */
    @ExcelColumn(columnNum = 20, columnName = "所属AC", length = 30, none = false)
    private String belongac;

    /** 接入NAS归属域 */
    @ExcelColumn(columnNum = 21, columnName = "接入NAS归属域", length = 30)
    private String nas;

    /** 网段 */
    @ExcelColumn(columnNum = 22, columnName = "网段", length = 30)
    private String segment;

    /** 下挂AP数 */
    @ExcelColumn(columnNum = 23, columnName = "下挂AP数", length = 10, columnType = Type.Num)
    private Integer apnum;

    /** bras端口类型 */
    @ExcelColumn(columnNum = 24, columnName = "bras端口类型", length = 30, columnType = Type.NumString)
    private String brasporttype;

    /** 代维公司 */
    @ExcelColumn(columnNum = 25, columnName = "代维公司", length = 30)
    private String repaircomp;

    /** wlan干放数 */
    @ExcelColumn(columnNum = 26, columnName = "wlan干放数", length = 10, columnType = Type.Num)
    private Integer wlanganfangnum;

    /** 业主 */
    @ExcelColumn(columnNum = 27, columnName = "业主", length = 30)
    private String owner;

    /** 业主联系电话 */
    @ExcelColumn(columnNum = 28, columnName = "业主联系电话", length = 70, columnType = Type.PHONE)
    private String ownerphone;

    /** 是否有室分 */
    @ExcelColumn(columnNum = 29, columnName = "是否有室分", length = 70, columnType = Type.ZOROne)
    private Integer ishasshifen;

    /** 是否与室分合路 */
    @ExcelColumn(columnNum = 30, columnName = "是否与室分合路", length = 70, columnType = Type.ZOROne)
    private Integer islineshifen;

    /** 接入电路编号 */
    @ExcelColumn(columnNum = 31, columnName = "接入电路编号", length = 30)
    private String accessno;

    /** 热点地点 */
    @ExcelColumn(columnNum = 32, columnName = "热点地点", length = 100)
    private String hotarea;

    /** 描述 */
    @ExcelColumn(columnNum = 33, columnName = "描述", length = 160)
    private String desc;

    /** 地标 */
    @ExcelColumn(columnNum = 34, columnName = "地标", length = 30)
    private String landmark;

    /** 集成商 */
    @ExcelColumn(columnNum = 35, columnName = "集成商", length = 30)
    private String intergrator;

    /** 备注 */
    @ExcelColumn(columnNum = 36, columnName = "备注", length = 160)
    private String remark;

    public Long getHotareaid() {
        return hotareaid;
    }

    public void setHotareaid(Long hotareaid) {
        this.hotareaid = hotareaid;
    }

    public Long getSpotid() {
        return spotid;
    }

    public void setSpotid(Long spotid) {
        this.spotid = spotid;
    }

    public String getHotareacode() {
        return hotareacode;
    }

    public void setHotareacode(String hotareacode) {
        this.hotareacode = hotareacode;
    }

    public String getHotareaname() {
        return hotareaname;
    }

    public void setHotareaname(String hotareaname) {
        this.hotareaname = hotareaname;
    }

    public String getHotareaoutid() {
        return hotareaoutid;
    }

    public void setHotareaoutid(String hotareaoutid) {
        this.hotareaoutid = hotareaoutid;
    }

    public String getOuttypeid() {
        return outtypeid;
    }

    public void setOuttypeid(String outtypeid) {
        this.outtypeid = outtypeid;
    }

    public String getCountryid() {
        return countryid;
    }

    public void setCountryid(String countryid) {
        this.countryid = countryid;
    }

    public String getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(String provinceid) {
        this.provinceid = provinceid;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    @Override
    public String getCounty() {
        return county;
    }

    @Override
    public void setCounty(String county) {
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

    public String getHotareatype() {
        return hotareatype;
    }

    public void setHotareatype(String hotareatype) {
        this.hotareatype = hotareatype;
    }

    public String getHotareadegree() {
        return hotareadegree;
    }

    public void setHotareadegree(String hotareadegree) {
        this.hotareadegree = hotareadegree;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getBrasId() {
        return brasId;
    }

    public void setBrasId(String brasId) {
        this.brasId = brasId;
    }

    public Long getCreateby() {
        return createby;
    }

    public void setCreateby(Long createby) {
        this.createby = createby;
    }

    public Date getModifydate() {
        return modifydate;
    }

    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
    }

    public Long getModifyby() {
        return modifyby;
    }

    public void setModifyby(Long modifyby) {
        this.modifyby = modifyby;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getHotport() {
        return hotport;
    }

    public void setHotport(String hotport) {
        this.hotport = hotport;
    }

    public String getAcount() {
        return acount;
    }

    public void setAcount(String acount) {
        this.acount = acount;
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

    public Integer getApnum() {
        return apnum;
    }

    public void setApnum(Integer apnum) {
        this.apnum = apnum;
    }

    public String getBrasporttype() {
        return brasporttype;
    }

    public void setBrasporttype(String brasporttype) {
        this.brasporttype = brasporttype;
    }

    public String getRepaircomp() {
        return repaircomp;
    }

    public void setRepaircomp(String repaircomp) {
        this.repaircomp = repaircomp;
    }

    public Integer getWlanganfangnum() {
        return wlanganfangnum;
    }

    public void setWlanganfangnum(Integer wlanganfangnum) {
        this.wlanganfangnum = wlanganfangnum;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnerphone() {
        return ownerphone;
    }

    public void setOwnerphone(String ownerphone) {
        this.ownerphone = ownerphone;
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

    public String getAccessno() {
        return accessno;
    }

    public void setAccessno(String accessno) {
        this.accessno = accessno;
    }

    public String getHotarea() {
        return hotarea;
    }

    public void setHotarea(String hotarea) {
        this.hotarea = hotarea;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public String getBelongac() {
        return belongac;
    }

    public void setBelongac(String belongac) {
        this.belongac = belongac;
    }

    public String getShel() {
        return shel;
    }

    public void setShel(String shel) {
        this.shel = shel;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getBasip() {
        return basip;
    }

    public void setBasip(String basip) {
        this.basip = basip;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ExcelHot) {
            ExcelHot excel = (ExcelHot) obj;
            if (excel.getHotareaname() == null) {
                return false;
            }
            if (excel.getHotareaname().equals(this.getHotareaname())) {
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

    @Override
    public String getCorporation() {
        return null;
    }

    @Override
    public void setCorporation(String corporation) {
        // TODO Auto-generated method stub
    }

    @Override
    public String getModel() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setModel(String model) {
        // TODO Auto-generated method stub
    }

    @Override
    public String getProvince() {
        return provinceid;
    }

    @Override
    public void setProvince(String province) {
        this.provinceid = province;
    }

    @Override
    public String getCity() {
        return cityid;
    }

    @Override
    public void setCity(String city) {
        this.cityid = city;
    }

}

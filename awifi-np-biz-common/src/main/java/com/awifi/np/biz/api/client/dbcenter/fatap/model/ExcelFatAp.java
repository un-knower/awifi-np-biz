package com.awifi.np.biz.api.client.dbcenter.fatap.model;

import com.awifi.np.biz.common.excel.model.ExcelColumn;
import com.awifi.np.biz.common.excel.model.Type;
import com.awifi.np.biz.common.excel.model.Validation;

public class ExcelFatAp implements Validation {
    /**
     * CORPORATION: 厂家
     */
    @ExcelColumn(columnNum = 0, columnName = "厂家", none = false)
    private String corporation;

    /**
     * MODEL: 型号
     */
    @ExcelColumn(columnNum = 1, columnName = "型号", none = false)
    private String model;
    /**
     * FWVERSION: 固件版本号
     */
    @ExcelColumn(columnNum = 2, columnName = "固件版本", columnType = Type.Version, none = false)
    private String fwversion;

    /**
     * CPVERSION: 组件版本号
     */
    @ExcelColumn(columnNum = 3, columnName = "组件版本", columnType = Type.Version, none = false)
    private String cpversion;

    /**
     * MACADDR: 设备物理地址
     */
    @ExcelColumn(columnNum = 4, columnName = "MAC", columnType = Type.Mac, none = false)
    private String macaddr;

    /**
     * PINCODE: PING码
     */
    @ExcelColumn(columnNum = 5, columnName = "PING码", none = false)
    private String pincode;

    /**
     * PROVINCE: 省份
     */
    @ExcelColumn(columnNum = 6, columnName = "省", none = false)
    private String province;

    /**
     * CITYID: 地市
     */
    @ExcelColumn(columnNum = 7, columnName = "市", none = false)
    private String city;

    /**
     * COUNTY: 县
     */
    @ExcelColumn(columnNum = 8, columnName = "区/县", none = false)
    private String county;
    /**
     * 上门服务(0/1)
     */
    @ExcelColumn(columnNum = 9, columnName = "是否包含上门服务费", columnType = Type.ZOROne, none = false)
    private Integer onsiteservice;

    /**
     * OS: 操作系统
     */
    private String os;

    /**
     * CPU: CPU属性
     */
    private String cpu;

    /**
     * MEM: 内存<
     */
    private String mem;

    /**
     * STORAGE: 存储属性
     */
    private String storage;

    /**
     * BAND: 频段2.4G;5G
     */
    private String band;

    /**
     * ANTENNUM: 天线数量
     */
    private Long antennum;

    /**
     * HASUSB: 是否有USB接口
     */
    private Integer hasusb;

    /**
     * RJ45NUM: RJ45个数
     */
    private Long rj45num;
    /**
     * 所属acname
     */
    private String acname;
    /**
     * ssid
     */
    private String ssid;
    /**
     * parentid
     */
    private Long parentid;

    /**
     * OS: 厂家中文名
     */
    private String corporationText;

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

    public String getFwversion() {
        return fwversion;
    }

    public void setFwversion(String fwversion) {
        this.fwversion = fwversion;
    }

    public String getCpversion() {
        return cpversion;
    }

    public void setCpversion(String cpversion) {
        this.cpversion = cpversion;
    }

    public String getMacaddr() {
        return macaddr;
    }

    public void setMacaddr(String macaddr) {
        this.macaddr = macaddr;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

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

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getMem() {
        return mem;
    }

    public void setMem(String mem) {
        this.mem = mem;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public Long getAntennum() {
        return antennum;
    }

    public void setAntennum(Long antennum) {
        this.antennum = antennum;
    }

    public Integer getHasusb() {
        return hasusb;
    }

    public void setHasusb(Integer hasusb) {
        this.hasusb = hasusb;
    }

    public Long getRj45num() {
        return rj45num;
    }

    public void setRj45num(Long rj45num) {
        this.rj45num = rj45num;
    }

    public String getAcname() {
        return acname;
    }

    public void setAcname(String acname) {
        this.acname = acname;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public Integer getOnsiteservice() {
        return onsiteservice;
    }

    public void setOnsiteservice(Integer onsiteservice) {
        this.onsiteservice = onsiteservice;
    }

    public String getCorporationText() {
        return corporationText;
    }

    public void setCorporationText(String corporationText) {
        this.corporationText = corporationText;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ExcelFatAp) {
            ExcelFatAp excel = (ExcelFatAp) obj;
            if (excel.getMacaddr() == null) {
                return false;
            }
            if (excel.getMacaddr().equals(this.getMacaddr())) {
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

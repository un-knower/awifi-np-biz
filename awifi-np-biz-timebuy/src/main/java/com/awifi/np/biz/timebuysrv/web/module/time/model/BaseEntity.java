package com.awifi.np.biz.timebuysrv.web.module.time.model;



public class BaseEntity {
    /** 流程编码key */
    private String globalKey;

    /** 流程编码值 */
    private String globalValue;

    /** 流程编码备用字段 */
    private String globalStandby;
    
    /**
     * 省份
     */
    private int province;
    /**
     * 城市
     */
    private int city;
    /**
     * 乡镇
     */
    private int county;

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getCounty() {
        return county;
    }

    public void setCounty(int county) {
        this.county = county;
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
}

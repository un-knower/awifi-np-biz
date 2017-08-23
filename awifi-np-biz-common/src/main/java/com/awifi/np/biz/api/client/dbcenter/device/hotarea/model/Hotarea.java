package com.awifi.np.biz.api.client.dbcenter.device.hotarea.model;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.awifi.np.biz.common.util.FormatUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月10日 上午10:05:07
 * 创建作者：亢燕翔
 * 文件名称：Hotarea.java
 * 版本：  v1.0
 * 功能：  热点实体类
 * 修改记录：
 */
public class Hotarea implements Serializable{

    /**序列化*/
    private static final long serialVersionUID = -3514904786662356495L;
    
    /**热点id*/
    private Long id;
    
    /**商户名称*/
    private String merchantName;
    
    /**热点名称*/
    private String hotareaName;
    
    /**设备MAC*/
    private String devMac;
    
    /**状态，0代表离线、1代表在线*/    
    private Integer status;

    /**
     * 将设备状态转化成中文
     * @return 设备类型
     */
    public String getStatusDsp(){
        if(status == null){
            return StringUtils.EMPTY;
        }
        return FormatUtil.formatDeviceStatus(status);
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getHotareaName() {
        return hotareaName;
    }

    public void setHotareaName(String hotareaName) {
        this.hotareaName = hotareaName;
    }

    public String getDevMac() {
        return devMac;
    }

    public void setDevMac(String devMac) {
        this.devMac = devMac;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Hotarea [id=" + id + ", merchantName=" + merchantName
                + ", hotareaName=" + hotareaName + ", devMac=" + devMac
                + ", status=" + status + "]";
    }

}

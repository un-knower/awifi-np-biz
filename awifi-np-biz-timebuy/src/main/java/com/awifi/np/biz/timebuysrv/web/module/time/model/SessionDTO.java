package com.awifi.np.biz.timebuysrv.web.module.time.model;

import java.io.Serializable;
import java.util.List;

import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;
import com.awifi.np.biz.timebuysrv.third.access.bean.AuthResult;

/**
 * Created by dozen.zhang on 2017/2/24. 用于缓存商户用户信息防止每次都去查
 * 在MerchantController的index 首页信息中会去查
 */
public class SessionDTO implements  Serializable {
    private static final long serialVersionUID = 350504050668708L;
    /**
     * portal参数
     */
    private PortalParam portalParam;

    /**
     * 上网时长信息
     */
    private UserTimeInfo timeInfo;
    /**
     * 用户请求ip
     */
    private String requestIp;
    /**
     * 用户请求端口号
     */
    private String requestIpPort;
    /**
     * 是否买断商户
     */
    private boolean buyout;
    /**
     * 滚动图片信息
     */
   // List<String> slidelist;
    /**
     * 用户信息
     */
    private SessionUser sessionUser;
    /**
     * 商户信息
     */
    private Device merchant;
    /**
     * 接入认证放通网络结果
     */
    private AuthResult authResult;

    public AuthResult getAuthResult() {
        return authResult;
    }

    public void setAuthResult(AuthResult authResult) {
        this.authResult = authResult;
    }

    /*
     * public void copy(SessionDTO dto){
     * 
     * ParamDTO paramDTO if(dto==null){ return ; } if(dto.getLogId()!=null){
     * this.setLogId(dto.getLogId()); }
     * if(StringUtil.isNotBlank(dto.getDeviceId()) &&
     * StringUtil.isNotBlank(deviceId) && !deviceId.equals(dto.getDeviceId())){
     * this.setMerchant(null); } if(StringUtil.isNotBlank(dto.getDeviceId())){
     * this.setDeviceId(dto.getDeviceId()); }
     * if(StringUtil.isNotBlank(dto.getMobilePhone())){
     * this.setMobilePhone(dto.getMobilePhone()); }
     * if(StringUtil.isNotBlank(dto.getUserMac())){
     * this.setUserMac(dto.getUserMac()); }
     * if(StringUtil.isNotBlank(dto.getUserIP())){
     * this.setUserIP(dto.getUserIP()); }
     * if(StringUtil.isNotBlank(dto.getGwAddress())){
     * this.setGwAddress(dto.getGwAddress()); }
     * if(StringUtil.isNotBlank(dto.getGwPort())){
     * this.setGwPort(dto.getGwPort()); }
     * if(StringUtil.isNotBlank(dto.getNasName())){
     * this.setNasName(dto.getNasName()); } }
     */
    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getRequestIpPort() {
        return requestIpPort;
    }

    public void setRequestIpPort(String requestIpPort) {
        this.requestIpPort = requestIpPort;
    }

    public SessionUser getSessionUser() {
        return sessionUser;
    }

    public void setSessionUser(SessionUser sessionUser) {
        this.sessionUser = sessionUser;
    }

   /* public List<String> getSlidelist() {
        return slidelist;
    }

    public void setSlidelist(List<String> slidelist) {
        this.slidelist = slidelist;
    }*/

    public Device getMerchant() {
        return merchant;
    }

    public void setMerchant(Device merchant) {
        this.merchant = merchant;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public UserTimeInfo getTimeInfo() {
        return timeInfo;
    }

    public void setTimeInfo(UserTimeInfo timeInfo) {
        this.timeInfo = timeInfo;
    }

    public PortalParam getPortalParam() {
        return portalParam;
    }

    public void setPortalParam(PortalParam portalParam) {
        this.portalParam = portalParam;
    }

    public boolean isBuyout() {
        return buyout;
    }

    public void setBuyout(boolean buyout) {
        this.buyout = buyout;
    }

}

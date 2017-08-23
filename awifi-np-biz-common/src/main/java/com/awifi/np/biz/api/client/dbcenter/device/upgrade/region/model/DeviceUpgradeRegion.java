/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月10日 下午4:39:04
* 创建作者：尤小平
* 文件名称：DeviceUpgradeRegion.java
* 版本：  v1.0
* 功能： 地区升级包
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.device.upgrade.region.model;

import java.io.Serializable;
import java.util.Date;

public class DeviceUpgradeRegion implements Serializable {
    private static final long serialVersionUID = 8672094859168844676L;
    private Long id; // 主键
    private Long corporationId; // 厂家id
    private String corporationName; // 厂家名称
    private String modelName; // 型号名称
    private Long modelId; // 型号id
    private String versions; // 版本
    private String hdVersions; // hd版本
    private String type; // 升级类型,固件:firmware;组件:module
    private String path; // 路径
    private Long province; // 省编号
    private Long city; // 市编号
    private Long county; // 区编号
    private Long issueNum; // 任务下发数量
    private Long successNum; // 升级成功数量
    private Date startTime; // 启用时间
    private Long state; // 状态,1:启用;0:关闭
    private Long userId; // 操作用户id
    private String userName; // 操作用户名
    private Date createTime; // 新增时间
    private Date modifyTime; // 修改时间
    private Long status; // 逻辑删除状态,1:未删除; 0:已删除
    
    private String corModelName; // 厂家型号名称
    private String areaName; //升级区域
    private String successRate;//升级成功率
    private String startTimeStr; // 启用时间
    private String stateName; // 状态描述
    private String typeName; // 升级类型描述

    /**
     * 启用
     */
    public static final Long START_STATUS = 1L;
    /**
     * 关闭
     */
    public static final Long CLOSE_STATUS = 0L;
    /**
     * 升级类型-固件版本
     */
    public static final String TYPE_FWVERSION = "firmware";

    /**
     * 升级类型-组件版本
     */
    public static final String TYPE_CPVERSION = "module";

    /**
     * 组件版本号分隔符
     */
    public static final String SPLIT = "\\.";
    
    /**
     * 关闭升级包.
     * 
     * @return DeviceUpgradeRegion
     * @author 尤小平  
     * @date 2017年7月13日 下午5:57:26
     */
    public DeviceUpgradeRegion close(){
        this.setState(CLOSE_STATUS);
        this.setModifyTime(new Date());
        return this;
    }

//    /**
//     * 启用升级包.
//     * 
//     * @return DeviceUpgradeRegion
//     * @author 尤小平  
//     * @date 2017年7月13日 下午5:57:42
//     */
//    public DeviceUpgradeRegion start(){
//        this.setState(START_STATUS);
//        this.setStartTime(new Date());
//        return this;
//    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the corporationId
     */
    public Long getCorporationId() {
        return corporationId;
    }

    /**
     * @param corporationId
     *            the corporationId to set
     */
    public void setCorporationId(Long corporationId) {
        this.corporationId = corporationId;
    }

    /**
     * @return the modelId
     */
    public Long getModelId() {
        return modelId;
    }

    /**
     * @param modelId
     *            the modelId to set
     */
    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    /**
     * @return the versions
     */
    public String getVersions() {
        return versions;
    }

    /**
     * @param versions
     *            the versions to set
     */
    public void setVersions(String versions) {
        this.versions = versions;
    }

    /**
     * @return the hdVersions
     */
    public String getHdVersions() {
        return hdVersions;
    }

    /**
     * @param hdVersions
     *            the hdVersions to set
     */
    public void setHdVersions(String hdVersions) {
        this.hdVersions = hdVersions;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path
     *            the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the province
     */
    public Long getProvince() {
        return province;
    }

    /**
     * @param province
     *            the province to set
     */
    public void setProvince(Long province) {
        this.province = province;
    }

    /**
     * @return the city
     */
    public Long getCity() {
        return city;
    }

    /**
     * @param city
     *            the city to set
     */
    public void setCity(Long city) {
        this.city = city;
    }

    /**
     * @return the county
     */
    public Long getCounty() {
        return county;
    }

    /**
     * @param county
     *            the county to set
     */
    public void setCounty(Long county) {
        this.county = county;
    }

    /**
     * @return the issueNum
     */
    public Long getIssueNum() {
        return issueNum;
    }

    /**
     * @param issueNum
     *            the issueNum to set
     */
    public void setIssueNum(Long issueNum) {
        this.issueNum = issueNum;
    }

    /**
     * @return the successNum
     */
    public Long getSuccessNum() {
        return successNum;
    }

    /**
     * @param successNum
     *            the successNum to set
     */
    public void setSuccessNum(Long successNum) {
        this.successNum = successNum;
    }

    /**
     * @return the startTime
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime
     *            the startTime to set
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the status
     */
    public Long getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(Long status) {
        this.status = status;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     *            the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the modifyTime
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * @param modifyTime
     *            the modifyTime to set
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * @return the state
     */
    public Long getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(Long state) {
        this.state = state;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the corModelName
     */
    public String getCorModelName() {
        return corModelName;
    }

    /**
     * @param corModelName the corModelName to set
     */
    public void setCorModelName(String corModelName) {
        this.corModelName = corModelName;
    }

    /**
     * @return the areaName
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * @param areaName the areaName to set
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    /**
     * @return the successRate
     */
    public String getSuccessRate() {
        return successRate;
    }

    /**
     * @param successRate the successRate to set
     */
    public void setSuccessRate(String successRate) {
        this.successRate = successRate;
    }

    /**
     * @return the startTimeStr
     */
    public String getStartTimeStr() {
        return startTimeStr;
    }

    /**
     * @param startTimeStr the startTimeStr to set
     */
    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    /**
     * @return the stateName
     */
    public String getStateName() {
        return stateName;
    }

    /**
     * @param stateName the stateName to set
     */
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    /**
     * @return the typeName
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * @param typeName the typeName to set
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * @return the corporationName
     */
    public String getCorporationName() {
        return corporationName;
    }

    /**
     * @param corporationName the corporationName to set
     */
    public void setCorporationName(String corporationName) {
        this.corporationName = corporationName;
    }

    /**
     * @return the modelName
     */
    public String getModelName() {
        return modelName;
    }

    /**
     * @param modelName the modelName to set
     */
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
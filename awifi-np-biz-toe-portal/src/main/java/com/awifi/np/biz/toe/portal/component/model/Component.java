/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月11日 下午3:29:22
* 创建作者：周颖
* 文件名称：Component.java
* 版本：  v1.0
* 功能：组件实体
* 修改记录：
*/
package com.awifi.np.biz.toe.portal.component.model;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.awifi.np.biz.common.util.FormatUtil;

public class Component implements Serializable {
    
    /**
     * 序列化id
     */
    private static final long serialVersionUID = -6981292254513112035L;
    
    /**主键*/
    private Long id;
    
    /** 组件唯一码 */
    private String code;
    
    /** 组件设置唯一码 */
    private String setCode;
    
    /** 组件名称 */
    private String name;
    
    /** 组件类型: 1 引导页、2 认证页、3 过渡页、4 导航页 */
    private String type;
    
    /** 是否唯一 */
    private Integer canUnique;
    
    /** 组件分类 1 通用、 2 认证组件、3 过渡跳转组件 */
    private Integer classify;
    
    /** 组件版本 */
    private String version;
    
    /** 缩略图  路径*/
    private String thumb;
    
    /** 组件图标  路径*/
    private String iconPath;
    
    /**平台编号：ALL 所有平台、ToE ToE平台*/
    private String platformCode;
    
    /** 组件包路径: /media/component/201509/14/71fbd9f1a8f64cd88671c0f6ddbba9a9/ */
    private String componentPath;
    
    /** 项目ids */
    private String projectIds;
    
    /**项目名称*/
    private String projectNames;
    
    /** 项目id(过滤) */
    private String filterProjectIds;
    
    /**项目名称(过滤)*/
    private String filterProjectNames;
    
    /** 备注 */
    private String remark;
    
    /** 创建时间 */
    private String createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSetCode() {
        return setCode;
    }

    public void setSetCode(String setCode) {
        this.setCode = setCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeDsp(){
        return FormatUtil.componentTypeDsp(type);
    }
    
    public Integer getCanUnique() {
        return canUnique;
    }

    public void setCanUnique(Integer canUnique) {
        this.canUnique = canUnique;
    }
    
    public Integer getClassify() {
        return classify;
    }

    public void setClassify(Integer classify) {
        this.classify = classify;
    }

    public String getClassifyDsp(){
        return FormatUtil.componentClassifyDsp(classify);
    }
    
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }
    
    public String getComponentPath() {
        return componentPath;
    }

    public void setComponentPath(String componentPath) {
        this.componentPath = componentPath;
    }

    public String getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(String projectIds) {
        this.projectIds = projectIds;
    }
    
    /**
     * 项目ids转化 {0}{1}转为1,2
     * @return 项目ids
     * @author
     * @date 2017年4月17日 上午10:20:45
     */
    public String getProjectIdsDsp() {
        if(StringUtils.isBlank(projectIds)){
            return StringUtils.EMPTY;
        }
        String newProjectIds = projectIds.replaceAll("\\{", StringUtils.EMPTY);
        newProjectIds = newProjectIds.replaceAll("\\}", ",");
        return newProjectIds.substring(0, newProjectIds.length()-1);
    }
    
    public String getProjectNames() {
        return projectNames;
    }

    public void setProjectNames(String projectNames) {
        this.projectNames = projectNames;
    }

    public String getFilterProjectIds() {
        return filterProjectIds;
    }
    
    /**
     * 过滤项目ids转化 {0}{1}转为1,2
     * @return 项目ids
     * @author 周颖
     * @date 2017年4月17日 上午10:20:45
     */
    public String getFilterProjectIdsDsp() {
        if(StringUtils.isBlank(filterProjectIds) || filterProjectIds.equals("{-1}")){
            return StringUtils.EMPTY;
        }
        String newProjectIds = filterProjectIds.replaceAll("\\{", StringUtils.EMPTY);
        newProjectIds = newProjectIds.replaceAll("\\}", ",");
        return newProjectIds.substring(0, newProjectIds.length()-1);
    }

    public void setFilterProjectIds(String filterProjectIds) {
        this.filterProjectIds = filterProjectIds;
    }
    
    public String getFilterProjectNames() {
        return filterProjectNames;
    }

    public void setFilterProjectNames(String filterProjectNames) {
        this.filterProjectNames = filterProjectNames;
    }
   
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}

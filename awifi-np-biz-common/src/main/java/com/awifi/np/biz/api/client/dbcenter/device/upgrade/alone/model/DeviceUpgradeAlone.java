/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月11日 下午3:37:58
* 创建作者：余红伟
* 文件名称：DeviceUpgradeAlone.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.device.upgrade.alone.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 个性化升级包
 * @author 余红伟
 * 2017年7月11日 下午3:38:30
 */
public class DeviceUpgradeAlone implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -8229542622798573130L;
    
    private Long id;
    private String name;
    private String type;
    private Long corporation_id;
    private Long model_id;
    private String versions;
    private String hd_versions;
    private String path;
    private Long status;
    private Long user_id;
    private Date create_time;
    private Date modify_time;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public Long getCorporation_id() {
        return corporation_id;
    }
    public void setCorporation_id(Long corporation_id) {
        this.corporation_id = corporation_id;
    }
    public Long getModel_id() {
        return model_id;
    }
    public void setModel_id(Long model_id) {
        this.model_id = model_id;
    }
    public String getVersions() {
        return versions;
    }
    public void setVersions(String versions) {
        this.versions = versions;
    }
    public String getHd_versions() {
        return hd_versions;
    }
    public void setHd_versions(String hd_versions) {
        this.hd_versions = hd_versions;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public Long getStatus() {
        return status;
    }
    public void setStatus(Long status) {
        this.status = status;
    }
    public Long getUser_id() {
        return user_id;
    }
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
    public Date getCreate_time() {
        return create_time;
    }
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
    public Date getModify_time() {
        return modify_time;
    }
    public void setModify_time(Date modify_time) {
        this.modify_time = modify_time;
    }
    
    
}

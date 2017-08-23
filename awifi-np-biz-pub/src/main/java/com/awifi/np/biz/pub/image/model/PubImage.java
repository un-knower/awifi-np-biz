/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月27日 下午4:24:07
* 创建作者：余红伟
* 文件名称：PubImage.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.pub.image.model;

import java.io.Serializable;
import java.util.Date;

public class PubImage implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 2789910888407917813L;
    
    private Long id;
    /**
     * 本图片路径
     */
    private String url;
    /**
     * 被替换图片url
     */
    private String oldUrl;
    /**
     * 被替换图片id
     */
    private Long oldId;
    /**
     * 系统配置参数upload_dir
     */
    private String uploadDir;
    /**
     * 系统配置参数upload_url
     */
    private String uploadUrl;
    /**
     * 图片磁盘绝对路径
     */
    private String path;
    /**
     * 状态，初始为临时状态0；成功替换是1；被替换了是9(删除)
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createDate;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getOldUrl() {
        return oldUrl;
    }
    public void setOldUrl(String oldUrl) {
        this.oldUrl = oldUrl;
    }
    public Long getOldId() {
        return oldId;
    }
    public void setOldId(Long oldId) {
        this.oldId = oldId;
    }
    public String getUploadDir() {
        return uploadDir;
    }
    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
    public String getUploadUrl() {
        return uploadUrl;
    }
    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
   
   
   
    
}

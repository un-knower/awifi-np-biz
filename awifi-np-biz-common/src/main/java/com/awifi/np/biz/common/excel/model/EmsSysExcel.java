package com.awifi.np.biz.common.excel.model;

import java.io.Serializable;
import java.util.Date;


public class EmsSysExcel implements Serializable{
    private static final long serialVersionUID = 379058591497416L;

    /**
     * ems_sys_excel.ID:
     * <p>
     */
    private Long id;

    /**
     * ems_sys_excel.FILENAME:
     * <p>
     * <code>
     * 文件名<br>
     * </code>
     */
    private String filename;

    /**
     * ems_sys_excel.FILEPATH:
     * <p>
     * <code>
     * 文件路径<br>
     * </code>
     */
    private String filepath;

    /**
     * ems_sys_excel.TYPE:
     * 文件类型：代码建枚举，胖AP，项目型瘦AP，热点，热点型瘦AP
     */
    private String type;

    /**
     * ems_sys_excel.RECORDNUM:
     * 实际入库数，解析完成后更新
     */
    private Integer recordnum;

    /**
     * ems_sys_excel.TOTALNUM:
     * 文件总条数
     */
    private Integer totalnum;

    /**
     * ems_sys_excel.UPLOADER:
     * 上传人
     */
    private Long uploader;

    /**
     * ems_sys_excel.UPLOADNAME:
     * 
     */
    private String uploadname;

    /**
     * ems_sys_excel.UPLOADTIME:
     * 上传时间
     */
    private Date uploadtime;

    /**
     * ems_sys_excel.FILESTATUS:
     * 文件状态，0：已上传，1：正在解析，2：解析完成，3：解析异常
     */
    private Integer filestatus;

    /**
     * ems_sys_excel.COMPLETECOSTTIME:
     * 解析花费时间
     */
    private Long completecosttime;

    /**
     * ems_sys_excel.COMPLETETIME:
     * 解析完成时间
     */
    private Date completetime;

    /**
     * ems_sys_excel.ERRORFILE:
     * 错误文件
     */
    private String errorfile;

    private String corporation;
    
    private String batch;
    /**
     * ems_sys_excel.REMARK:
     * 主要存储异常信息
     */
    private String remark;

    /**
     * ems_sys_excel.UUID:
     * UUID,随机值用来确定执行任务
     */
    private String uuid;

    /**
     * ems_sys_excel.EXT:
     */
    private String ext;

	public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getFilename(){
        return filename;
    }

    public void setFilename(String filename){
        this.filename = filename == null ? null : filename.trim();
    }

    public String getFilepath(){
        return filepath;
    }

    public void setFilepath(String filepath){
        this.filepath = filepath == null ? null : filepath.trim();
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type == null ? null : type.trim();
    }

    public Integer getRecordnum(){
        return recordnum;
    }

    public void setRecordnum(Integer recordnum){
        this.recordnum = recordnum;
    }

    public Integer getTotalnum(){
        return totalnum;
    }

    public void setTotalnum(Integer totalnum){
        this.totalnum = totalnum;
    }

    public Long getUploader(){
        return uploader;
    }

    public void setUploader(Long uploader){
        this.uploader = uploader;
    }

    public String getUploadname(){
        return uploadname;
    }

    public void setUploadname(String uploadname){
        this.uploadname = uploadname == null ? null : uploadname.trim();
    }

    public Date getUploadtime(){
        return uploadtime;
    }

    public void setUploadtime(Date uploadtime){
        this.uploadtime = uploadtime;
    }

    public Integer getFilestatus(){
        return filestatus;
    }

    public void setFilestatus(Integer filestatus){
        this.filestatus = filestatus;
    }

    public Long getCompletecosttime(){
        return completecosttime;
    }

    public void setCompletecosttime(Long completecosttime){
        this.completecosttime = completecosttime;
    }

    public Date getCompletetime(){
        return completetime;
    }

    public void setCompletetime(Date completetime){
        this.completetime = completetime;
    }

    public String getErrorfile(){
        return errorfile;
    }

    public void setErrorfile(String errorfile){
        this.errorfile = errorfile == null ? null : errorfile.trim();
    }

    public String getCorporation(){
        return corporation;
    }

    public void setCorporation(String corporation){
        this.corporation = corporation == null ? null : corporation.trim();
    }
    
    public String getBatch(){
        return batch;
    }

    public void setBatch(String batch){
        this.batch = batch == null ? null : batch.trim();
    }
    public String getRemark(){
        return remark;
    }

    public void setRemark(String remark){
        this.remark = remark == null ? null : remark.trim();
    }

    public String getUuid(){
        return uuid;
    }

    public void setUuid(String uuid){
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getExt(){
        return ext;
    }

    public void setExt(String ext){
        this.ext = ext == null ? null : ext.trim();
    }
}
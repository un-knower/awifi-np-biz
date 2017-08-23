package com.awifi.np.biz.devsrv.fatap.model;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 实体类
 * </p>
 * <p>
 * Table: np_biz_device_upgrade_default -
 * </p>
 * 
 * @since 2017-06-27 03:26:27
 */
public class FatApUpgradePatch implements Serializable {

    private static final long serialVersionUID = 1;
    /** id - 主键 */
    private Integer id;

    /** corporation_text - 厂商 */
    private String corporation_text;
    /** model_text - 型号 */
    private String model_text;
    /** version - 版本 */
    private String version;
    /** corporation_id - 厂商id */
    private Integer corporation_id;
    /** model_id - 型号id */
    private Integer model_id;
    /** version_hd - hd版本号 */
    private String version_hd;
    /** type_upgrade - 升级类型(0 组件版本,1 固件版本) */
    private Integer type_upgrade;
    /** province_text - 省 */
    private String province_text;
    /** city_text - 市 */
    private String city_text;
    /** district_text - 区 */
    private String district_text;
    /** province_id - 省id */
    private Integer province_id;
    /** city_id - 市id */
    private Integer city_id;
    /** district_id - 区id */
    private Integer district_id;
    /** status - 1：启用，9：关闭 */
    private Integer status;
    /** start_time - 启用时间 */
    private Date start_time;
    /** user_name - 上传用户 */
    private String user_name;
    /** user_id - 上传用户id */
    private Integer user_id;
    /** file_path - 文件路径 */
    private String file_path;
    /** create_time - 上传时间 */
    private Date create_time;
    /** file_name - 文件名称 */
    private String file_name;
    /** upgrade_num - 升级设备数量 */
    private Integer upgrade_num;

    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getCorporation_text() {
        return this.corporation_text;
    }
    public void setCorporation_text(String corporation_text) {
        this.corporation_text = corporation_text;
    }

    public String getModel_text() {
        return this.model_text;
    }
    public void setModel_text(String model_text) {
        this.model_text = model_text;
    }

    public String getVersion() {
        return this.version;
    }
    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getCorporation_id() {
        return this.corporation_id;
    }
    public void setCorporation_id(Integer corporation_id) {
        this.corporation_id = corporation_id;
    }

    public Integer getModel_id() {
        return this.model_id;
    }
    public void setModel_id(Integer model_id) {
        this.model_id = model_id;
    }

    public String getVersion_hd() {
        return this.version_hd;
    }
    public void setVersion_hd(String version_hd) {
        this.version_hd = version_hd;
    }

    public Integer getType_upgrade() {
        return this.type_upgrade;
    }
    public void setType_upgrade(Integer type_upgrade) {
        this.type_upgrade = type_upgrade;
    }

    public String getProvince_text() {
        return this.province_text;
    }
    public void setProvince_text(String province_text) {
        this.province_text = province_text;
    }

    public String getCity_text() {
        return this.city_text;
    }
    public void setCity_text(String city_text) {
        this.city_text = city_text;
    }

    public String getDistrict_text() {
        return this.district_text;
    }
    public void setDistrict_text(String district_text) {
        this.district_text = district_text;
    }

    public Integer getProvince_id() {
        return this.province_id;
    }
    public void setProvince_id(Integer province_id) {
        this.province_id = province_id;
    }

    public Integer getCity_id() {
        return this.city_id;
    }
    public void setCity_id(Integer city_id) {
        this.city_id = city_id;
    }

    public Integer getDistrict_id() {
        return this.district_id;
    }
    public void setDistrict_id(Integer district_id) {
        this.district_id = district_id;
    }

    public Integer getStatus() {
        return this.status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStart_time() {
        return this.start_time;
    }
    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public String getUser_name() {
        return this.user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Integer getUser_id() {
        return this.user_id;
    }
    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getFile_path() {
        return this.file_path;
    }
    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public Date getCreate_time() {
        return this.create_time;
    }
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getFile_name() {
        return this.file_name;
    }
    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public Integer getUpgrade_num() {
        return this.upgrade_num;
    }
    public void setUpgrade_num(Integer upgrade_num) {
        this.upgrade_num = upgrade_num;
    }
}
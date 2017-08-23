/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年4月13日 下午5:04:05
 * 创建作者：尤小平
 * 文件名称：PubUser.java
 * 版本：  v1.0
 * 功能：用户实体类
 * 修改记录：
 */
package com.awifi.np.biz.api.client.dbcenter.user.model;

import java.io.Serializable;
import java.util.Date;

public class PubUser implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -6812382480031361620L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 认证用户名
     */
    private String authId;
    /**
     * 认证手机号
     */
    private String telphone;
    /**
     * 微信
     */
    private String wechat;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 身份证号码
     */
    private String userCard;
    /**
     * 用户昵称
     */
    private String userNick;
    /**
     * 用户姓名
     */
    private String userRealname;
    /**
     * 性别
     */
    private String sex;
    /**
     * 政治面貌
     */
    private String political;
    /**
     * 籍贯
     */
    private String nativeStr;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 生日
     */
    private String birthdayStr;

    /**
     * 可用积分
     */
    private Long integralUsable;
    /**
     * 已消费积分
     */
    private Long integralUsed;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 联系地址
     */
    private String address;
    /**
     * 头像
     */
    private String faceInfo;
    /**
     * 毕业院校
     */
    private String college;
    /**
     * 专业
     */
    private String major;
    /**
     * 当前就业单位
     */
    private String company;
    /**
     * 行业/职业
     */
    private String industry;
    /**
     * 岗位
     */
    private String post;
    /**
     * 爱好
     */
    private String hobby;
    /**
     * 厌恶
     */
    private String abhor;
    /**
     * 休闲场所
     */
    private String leisurePlace;
    /**
     * 等级
     */
    private Long level;
    /**
     * 总经验
     */
    private Long totalExp;
    /**
     * 徽章
     */
    private String badges;
    /**
     * 备注说明
     */
    private String remarks;
    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the authId
     */
    public String getAuthId() {
        return authId;
    }

    /**
     * @param authId the authId to set
     */
    public void setAuthId(String authId) {
        this.authId = authId;
    }

    /**
     * @return the telphone
     */
    public String getTelphone() {
        return telphone;
    }

    /**
     * @param telphone the telphone to set
     */
    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    /**
     * @return the wechat
     */
    public String getWechat() {
        return wechat;
    }

    /**
     * @param wechat the wechat to set
     */
    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the userCard
     */
    public String getUserCard() {
        return userCard;
    }

    /**
     * @param userCard the userCard to set
     */
    public void setUserCard(String userCard) {
        this.userCard = userCard;
    }

    /**
     * @return the userNick
     */
    public String getUserNick() {
        return userNick;
    }

    /**
     * @param userNick the userNick to set
     */
    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    /**
     * @return the userRealname
     */
    public String getUserRealname() {
        return userRealname;
    }

    /**
     * @param userRealname the userRealname to set
     */
    public void setUserRealname(String userRealname) {
        this.userRealname = userRealname;
    }

    /**
     * @return the sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return the political
     */
    public String getPolitical() {
        return political;
    }

    /**
     * @param political the political to set
     */
    public void setPolitical(String political) {
        this.political = political;
    }

    /**
     * @return the nativeStr
     */
    public String getNativeStr() {
        return nativeStr;
    }

    /**
     * @param nativeStr the nativeStr to set
     */
    public void setNativeStr(String nativeStr) {
        this.nativeStr = nativeStr;
    }

    /**
     * @return the birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * @return the integralUsable
     */
    public Long getIntegralUsable() {
        return integralUsable;
    }

    /**
     * @param integralUsable the integralUsable to set
     */
    public void setIntegralUsable(Long integralUsable) {
        this.integralUsable = integralUsable;
    }

    /**
     * @return the integralUsed
     */
    public Long getIntegralUsed() {
        return integralUsed;
    }

    /**
     * @param integralUsed the integralUsed to set
     */
    public void setIntegralUsed(Long integralUsed) {
        this.integralUsed = integralUsed;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the faceInfo
     */
    public String getFaceInfo() {
        return faceInfo;
    }

    /**
     * @param faceInfo the faceInfo to set
     */
    public void setFaceInfo(String faceInfo) {
        this.faceInfo = faceInfo;
    }

    /**
     * @return the college
     */
    public String getCollege() {
        return college;
    }

    /**
     * @param college the college to set
     */
    public void setCollege(String college) {
        this.college = college;
    }

    /**
     * @return the major
     */
    public String getMajor() {
        return major;
    }

    /**
     * @param major the major to set
     */
    public void setMajor(String major) {
        this.major = major;
    }

    /**
     * @return the company
     */
    public String getCompany() {
        return company;
    }

    /**
     * @param company the company to set
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * @return the industry
     */
    public String getIndustry() {
        return industry;
    }

    /**
     * @param industry the industry to set
     */
    public void setIndustry(String industry) {
        this.industry = industry;
    }

    /**
     * @return the post
     */
    public String getPost() {
        return post;
    }

    /**
     * @param post the post to set
     */
    public void setPost(String post) {
        this.post = post;
    }

    /**
     * @return the hobby
     */
    public String getHobby() {
        return hobby;
    }

    /**
     * @param hobby the hobby to set
     */
    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    /**
     * @return the abhor
     */
    public String getAbhor() {
        return abhor;
    }

    /**
     * @param abhor the abhor to set
     */
    public void setAbhor(String abhor) {
        this.abhor = abhor;
    }

    /**
     * @return the leisurePlace
     */
    public String getLeisurePlace() {
        return leisurePlace;
    }

    /**
     * @param leisurePlace the leisurePlace to set
     */
    public void setLeisurePlace(String leisurePlace) {
        this.leisurePlace = leisurePlace;
    }

    /**
     * @return the level
     */
    public Long getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(Long level) {
        this.level = level;
    }

    /**
     * @return the totalExp
     */
    public Long getTotalExp() {
        return totalExp;
    }

    /**
     * @param totalExp the totalExp to set
     */
    public void setTotalExp(Long totalExp) {
        this.totalExp = totalExp;
    }

    /**
     * @return the badges
     */
    public String getBadges() {
        return badges;
    }

    /**
     * @param badges the badges to set
     */
    public void setBadges(String badges) {
        this.badges = badges;
    }

    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the birthdayStr
     */
    public String getBirthdayStr() {
        return birthdayStr;
    }

    /**
     * @param birthdayStr the birthdayStr to set
     */
    public void setBirthdayStr(String birthdayStr) {
        this.birthdayStr = birthdayStr;
    }
}

package com.awifi.np.biz.timebuysrv.web.module.time.model;



import java.io.Serializable;
import java.util.Date;

/**
 * 电渠传入数据
 * @author 张智威
 * 2017年4月10日 下午3:24:12
 */
public class CenterOnlineDataObject extends BaseEntity implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = 8886980360575664023L;
    /**
     * 序号
     */
    private long id;
    /**
     * 宽带账号
     */
    private String broadbandAccount;
    /**
     * 园区id
     */
    private String merid;
    /**
     * 请求流水
     */
    private String objectId;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 商品编号
     */
    private String goodsCode;
    /**
     * 套餐价值
     */
    private String pkgPrice;
    /**
     * 金额, 元为单位
     */
    private String amount;
    /**
     * 天数
     */
    private String adddays;
    /**
     * 套餐,值为天/月/年
     */
    private String pkgDetail;
    /**
     * 请求时间
     */
    private String inputTime;
    /**
     * 备注1
     */
    private String remark1;
    /**
     * 备注2
     */
    private String remark2;
    /**
     * 备注3
     */
    private String remark3;
    /**
     * 加密码
     */
    private String sign;
    /**
     * 加密验证码
     */
    private String identityCode;
    /**
     * 当前时间
     */
    private String nowTime;
    /**
     * 标志位
     */
    private String processFlg;
    /**
     * 手机号
     */
    private String tel;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 套餐数量
     */
    private String pkgNum;

    public String getPkgNum() {
        return pkgNum;
    }

    public void setPkgNum(String pkgNum) {
        this.pkgNum = pkgNum;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBroadbandAccount() {
        return broadbandAccount;
    }

    public void setBroadbandAccount(String broadbandAccount) {
        this.broadbandAccount = broadbandAccount;
    }

    public String getMerid() {
        return merid;
    }

    public void setMerid(String merid) {
        this.merid = merid;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getPkgPrice() {
        return pkgPrice;
    }

    public void setPkgPrice(String pkgPrice) {
        this.pkgPrice = pkgPrice;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAdddays() {
        return adddays;
    }

    public void setAdddays(String adddays) {
        this.adddays = adddays;
    }

    public String getPkgDetail() {
        return pkgDetail;
    }

    public void setPkgDetail(String pkgDetail) {
        this.pkgDetail = pkgDetail;
    }

    public String getInputTime() {
        return inputTime;
    }

    public void setInputTime(String inputTime) {
        this.inputTime = inputTime;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = remark3;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getIdentityCode() {
        return identityCode;
    }

    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode;
    }

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public String getProcessFlg() {
        return processFlg;
    }

    public void setProcessFlg(String processFlg) {
        this.processFlg = processFlg;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}

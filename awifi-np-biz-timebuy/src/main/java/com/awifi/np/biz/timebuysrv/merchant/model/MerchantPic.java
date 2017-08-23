/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年4月10日 下午3:04:51
 * 创建作者：尤小平
 * 文件名称：MerchantPic.java
 * 版本：  v1.0
 * 功能：商户图片类
 * 修改记录：
 */
package com.awifi.np.biz.timebuysrv.merchant.model;

public class MerchantPic {
    /**
     * 编号
     */
    private Integer id;
    /**
     * 槽位
     */
    private Integer slot;
    /**
     * 路径
     */
    private String path;
    /**
     * 商户id
     */
    private Long merid;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the slot
     */
    public Integer getSlot() {
        return slot;
    }

    /**
     * @param slot
     *            the slot to set
     */
    public void setSlot(Integer slot) {
        this.slot = slot;
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
     * @return the merid
     */
    public Long getMerid() {
        return merid;
    }

    /**
     * @param merid
     *            the merid to set
     */
    public void setMerid(Long merid) {
        this.merid = merid;
    }
}

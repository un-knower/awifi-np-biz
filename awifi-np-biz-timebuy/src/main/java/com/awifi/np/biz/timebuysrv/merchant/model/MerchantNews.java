/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年4月1日 上午11:09:02
 * 创建作者：尤小平
 * 文件名称：MerchantNews.java
 * 版本：  v1.0
 * 功能：商户介绍类
 *
 * 修改记录：
 */
package com.awifi.np.biz.timebuysrv.merchant.model;

public class MerchantNews {

    /**
     * 编号
     */
    private Integer id;

    /**
     * 消息
     */
    private String content;

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
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the merid
     */
    public Long getMerid() {
        return merid;
    }

    /**
     * @param merid the merid to set
     */
    public void setMerid(Long merid) {
        this.merid = merid;
    }
}

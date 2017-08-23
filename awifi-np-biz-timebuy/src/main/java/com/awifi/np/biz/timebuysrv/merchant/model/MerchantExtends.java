/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月25日 上午11:16:42
* 创建作者：尤小平
* 文件名称：MerchantManager.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.merchant.model;

public class MerchantExtends {
    /**
     * sys_mng_merchart.id: 
     * <p>
     * <code>
     * 主键自增<br>
     * </code>
     */
    private Long id;
    
    /**
     * 是否买断 0未买断 1买断
     */
    private int buyout;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getBuyout() {
        return buyout;
    }

    public void setBuyout(int buyout) {
        this.buyout = buyout;
    }
}

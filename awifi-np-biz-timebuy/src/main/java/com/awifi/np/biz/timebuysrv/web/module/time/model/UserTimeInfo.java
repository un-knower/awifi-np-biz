package com.awifi.np.biz.timebuysrv.web.module.time.model;

import java.io.Serializable;

/**
 *  用户时间信息封装类
 * @author 张智威
 * 2017年4月10日 下午4:46:43
 */
public class UserTimeInfo implements  Serializable{
    private static final long serialVersionUID =2017831538233l;
	/**
	 * 是否vip
	 */
	boolean vip;
	/**
	 * 是否能领取免费礼包
	 */
	boolean canGetFreePkg;// free_pkg_get
	/**
	 * 结束时间 如果有时长 记录用户截止时间
	 */
	
	
	Long endTime;
	/**
	 * 是否是买断企业
	 */
	boolean buyout;
	
	public boolean isBuyout() {
        return buyout;
    }

    public void setBuyout(boolean buyout) {
        this.buyout = buyout;
    }

    public boolean isCanGetFreePkg() {
		return canGetFreePkg;
	}

	public void setCanGetFreePkg(boolean canGetFreePkg) {
		this.canGetFreePkg = canGetFreePkg;
	}

	public boolean isVip() {
		return vip;
	}

	public void setVip(boolean vip) {
		this.vip = vip;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

}

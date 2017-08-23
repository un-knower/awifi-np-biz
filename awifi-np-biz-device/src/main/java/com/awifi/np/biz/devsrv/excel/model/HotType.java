/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月17日 下午3:17:58
* 创建作者：范涌涛
* 文件名称：HotType.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.excel.model;

import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.util.MessageUtil;

public enum HotType {
    /**
     * 机场
     */
    airport(1, "机场"),
    /**
     * 火车站
     */
    trainStation(2, "火车站"),
    /**
     * 长途汽车站
     */
    busStation(3, "长途汽车站"),
    /**
     * 码头
     */
    wharf(4, "码头"),
    /**
     * 学校
     */
    school(5, "学校"),
    /**
     * 宾馆酒店
     */
    Hotel(6, "宾馆酒店"),
    /**
     * 休闲度假区
     */
    holiday(7, "休闲度假区"),
    /**
     * 休闲娱乐吧
     */
    recreationBar(8, "休闲娱乐吧"),
    /**
     * 会展中心
     */
    exhibitionCenter(9, "会展中心"),
    /**
     * 体育场馆
     */
    stadiums(10, "体育场馆"),
    /**
     * 商务楼
     */
    businessBuilding(11, "商务楼"),
    /**
     * 政府机关等事业单位
     */
    government(12, "政府机关等事业单位"),
    /**
     * 医院
     */
    hospital(13, "医院"),
    /**
     * 聚类市场
     */
    clusterMarket(14, "聚类市场"),
    /**
     * 大型购物广场
     */
    shoppingPlaza(15, "大型购物广场"),
    /**
     * 电信自有
     */
    telecommunications(16, "电信自有"),
    /**
     * 其他
     */
    others(99, "其他");
    /**
     * 值
     */
    private int value;
    /**
     * 名称
     */
    private String name;

    HotType(int value, String name) {
        this.setValue(value);
        this.setName(name);
    }
    /**
     * 根据类型 获取 hot
     * @param typeName 
     * @return HotType 
     * @author 伍恰  
     * @date 2017年6月14日 下午1:56:41
     */
    public static HotType getHotareaType(String typeName) {
        for (HotType hot : values()) {
            if ((hot.getName()).equals(typeName)) {
                return hot;
            }
        }
        throw new ValidException("E2301106",
                MessageUtil.getMessage("E2301106", typeName));
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

package com.awifi.np.biz.common.enums;

import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 设备类型
 * 
 */
public enum DevType {

    /**
     * 以下设备类型是数据中心已经定义好了的，如需新增，请自行添加，注意别重复
     * 
     * 1:开头：bras; 2:开头 ac; 3:开头fatap; 4:开头fitap;
     */

    bras(11, 1, "BAS","Bas"),
    /**
     * 
     */
    ac(21, 2, "AC","Ac"),
    /**
     * 
     */
    fatap(31, 3, "FAT_AP","FatAP"),
    /**
     * 
     */
    gpon(32, 3, "GPON","Gpon"),
    /**
     * 
     */
    gponw(33, 3, "GPON_W","Gponw"),
    //gponw(372, 3, "GPON_W","Gponw"),
    /**
     * 
     */
    epon(34, 3, "EPON","Epon"),
    /**
     * 
     */
    eponw(35, 3, "EPON_W","Eponw"),
    //eponw(373, 3, "EPON_W","Eponw"),
    /**
     * 
     */
    twoForOne(36, 3, "twoForOne","TwoForOne"),
    /**
     * 
     */
    threeForOne(37, 3, "threeForOne","ThreeForOne"),
    /**
     * 
     */
    lanFuse(371,3,"LAN融合","LanFuse"),
    /**
     * 
     */
    gponFuse(372,3,"GPON融合","GponFuse"),
    /**
     * 
     */
    eponFuse(373,3,"EPON融合","EponFuse"),
    /**
     * 
     */
    fitap(41, 4, "FIT_AP","FitAp"),
    /**
     * 
     */
    pFitap(42, 4, "FIT_AP","FitAPPro"),
    /**
     * 
     */
    hFitap(43, 4, "HOT_FIT_AP","HFitAp");

    /**
     * 设备类型编码
     */
    private int value;
    /**
     * 设备类型
     */
    private int type;
    /**
     * 设备类型名称
     */
    private String displayName;
    
    /**
     * 设备类型名称，适用于导入数据时，对该型号的辨识
     */
    private String entityType;

    DevType(int value, int type, String displayName,String entityType) {
        this.value = value;
        this.type = type;
        this.displayName = displayName;
        this.entityType=entityType;
    }

    /**
     * @Title:displayName @Description: @return @return DevType @throws
     */
    public String displayName() {
        return displayName;
    }

    public int getValue() {
        return value;
    }

    public int getType() {
        return type;
    }
    
    public String entityType(){
        return entityType;
    }

    /**
     * @Title:getDevType @Description: @param value value @return @return DevType @throws
     */
    public static DevType getDevType(int value) {
        for (DevType type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new ValidException("E2300003", MessageUtil.getMessage("E2300003"));
    }

    /**
     * 返回所有的胖AP设备类型【 用于胖AP】
     * 
     * @author cjl
     * @date 2015年12月4日上午10:02:29
     * @return StringBuilder
     */
    public static StringBuilder getFatAp() {
        StringBuilder sb = new StringBuilder();
        for (DevType type : values()) {
            if (type.type == 3) {
                sb.append(type.getValue()).append(",");
            }
        }
        // 去掉最后一个逗号
        sb.replace(sb.length() - 1, sb.length(), "");
        return sb;
    }

}

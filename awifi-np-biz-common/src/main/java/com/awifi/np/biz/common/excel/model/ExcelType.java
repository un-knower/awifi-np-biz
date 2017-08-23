/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.awifi.np.biz.common.excel.model;

import com.awifi.np.biz.common.exception.ApplicationException;

public enum ExcelType{
	/**
	 * 项目型瘦AP
	 */
    FitAPPro("项目型瘦AP"),

    /**
     * 热点型瘦AP
     */
    FitAPHot("热点型瘦AP"),

    /**
     * 胖AP
     */
    FatAP("胖AP"),

    /**
     * 热点
     */
    Hot("热点"),

    /**
     * VPN
     */
    VPN("VPN"),

    /**
     * ChinaNetAC
     */
    ChinaNetAc("ChinaNetAC"),

    /**
     * ChinaNetAP
     */
    ChinaNetAp("ChinaNetAP"),

    /**
     * ChinaNet热点
     */
    ChinaNetHot("ChinaNet热点"),

    /**
     * AwifiAc
     */
    AwifiAc("AwifiAc"),

    /**
     * AwifiBras
     */
    AwifiBras("AwifiBras");

	/**
	 * value
	 */
    private String value;

    /**
     * 获取值
     * @param value
     */
    ExcelType(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public void setValue(String value){
        this.value = value;
    }

    /**
     * 获取type
     * @param value value
     * @return type
     */
    public static String getExcelType(String value){
//    	System.out.println(values());
        for (ExcelType type : values()){
//        	System.out.println(type.value);
//    	System.out.println(type.name());
//    	
            if (type.value.equals(value)){
            	return type.name();
            }
        }
        throw new ApplicationException("类型不存在！");
    }

}

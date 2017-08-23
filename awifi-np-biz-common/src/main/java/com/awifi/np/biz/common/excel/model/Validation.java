package com.awifi.np.biz.common.excel.model;

/**
 *@ClassName:Validation
 *@author 李程程
 *
 */
public interface Validation{

    /**
     * 厂家信息的get方法
     * @return str
     */
    String getCorporation();

    /**
     * 厂家信息的set方法
     * @param corporation 厂家
     */
    void setCorporation(String corporation);


    /**
     * 设备型号的get方法
     * @return str
     */
    String getModel();


    /**
     * 设备型号的set方法
     * @param model 设备型号
     */
    void setModel(String model);


    /**
     * 地区省份的get方法
     * @return str
     */
    String getProvince();


    /**
     * 省份的set方法
     * @param province 省份
     */
    void setProvince(String province);


    /**
     * 市的get方法
     * @return str
     */
    String getCity();


    /**
     * 市的set方法
     * @param city 市
     */
    void setCity(String city);


    /**
     *区县的get方法
     * @return str
     */
    String getCounty();


    /**
     * 区县的set方法
     * @param county 区县
     */
    void setCounty(String county);

}

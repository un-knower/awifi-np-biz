/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月18日 上午10:35:42
* 创建作者：范立松
* 文件名称：ComboApiService.java
* 版本：  v1.0
* 功能：套餐配置管理数据中心接口
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.combo.service;

import java.util.List;
import java.util.Map;

public interface ComboApiService {

    /**
     * 查询所有套餐
     * @return 套餐信息列表
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 上午11:15:09
     */
    List<Map<String, Object>> getComboList() throws Exception;

    /**添加套餐信息
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午2:50:15
     */
    void addCombo(Map<String, Object> paramsMap) throws Exception;

    /**
     * 查询套餐数量
     * @param paramsMap 请求参数
     * @return 套餐数量
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午3:01:20
     */
    int countComboByParam(Map<String, Object> paramsMap) throws Exception;

    /**删除套餐信息
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午2:50:15
     */
    void removeCombo(Map<String, Object> paramsMap) throws Exception;

    /**
     * 查询套餐配置数量
     * @param paramsMap 请求参数
     * @return 套餐数量
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午3:01:20
     */
    int countComboManageByParam(Map<String, Object> paramsMap) throws Exception;

    /**
     * 分页查询套餐配置信息
     * @return 套餐信息列表
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 上午11:15:09
     */
    List<Map<String, Object>> getComboManageList(Map<String, Object> paramsMap) throws Exception;

    /**
     * 添加套餐配置信息
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午2:50:15
     */
    void addComboManage(Map<String, Object> paramsMap) throws Exception;

    /**
     * 删除套餐配置信息
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午2:50:15
     */
    void removeComboManage(Map<String, Object> paramsMap) throws Exception;
    
    /**
     * 套餐配置续时
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年5月5日 下午3:22:00
     */
    void continueComboManage(Map<String, Object> paramsMap) throws Exception;

}

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月17日 下午5:03:21
* 创建作者：范立松
* 文件名称：ComboService.java
* 版本：  v1.0
* 功能：套餐配置接口
* 修改记录：
*/
package com.awifi.np.biz.devsrv.combo.service;

import java.util.List;
import java.util.Map;

import com.awifi.np.biz.common.base.model.Page;

public interface ComboService {

    /**
     * 查询所有套餐
     * @param page 套餐信息
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午4:27:45
     */
    void getComboList(Page<Map<String, Object>> page) throws Exception;

    /**
     * 添加套餐信息
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午2:50:15
     */
    void addCombo(Map<String, Object> paramsMap) throws Exception;

    /**
     * 删除套餐信息
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午2:50:15
     */
    void removeCombo(Map<String, Object> paramsMap) throws Exception;

    /**
     * 分页查询套餐与商户关系列表
     * @param page 套餐与商户关系列表
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午5:17:38
     */
    void getComboManageList(Page<Map<String, Object>> page, Map<String, Object> paramsMap) throws Exception;

    /**
     * 添加套餐配置信息
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午2:50:15
     */
    void addComboManage(Map<String, Object> paramsMap) throws Exception;
    
    /**
     * 删除套餐信息
     * @param ids 商户id列表
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午2:50:15
     */
    void removeComboManage(String[] ids) throws Exception;
    
    /**
     * 套餐配置续时
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年5月5日 下午3:22:00
     */
    void continueComboManage(Map<String, Object> paramsMap) throws Exception;

}

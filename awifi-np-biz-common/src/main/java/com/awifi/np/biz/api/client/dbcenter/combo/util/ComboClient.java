/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月18日 上午11:28:00
* 创建作者：范立松
* 文件名称：ComboClient.java
* 版本：  v1.0
* 功能：套餐配置管理数据中心接口
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.combo.util;

import java.util.List;
import java.util.Map;

import com.awifi.np.biz.api.client.dbcenter.combo.service.ComboApiService;
import com.awifi.np.biz.common.util.BeanUtil;

public class ComboClient {

    /**
     * 业务对象
     */
    private static ComboApiService comboApiService;

    /**
     * 查询所有套餐
     * @return 套餐信息列表
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午1:45:34
     */
    public static List<Map<String, Object>> getComboList() throws Exception {
        return getComboApiService().getComboList();
    }

    /**
     * 添加套餐信息
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午3:09:47
     */
    public static void addCombo(Map<String, Object> paramsMap) throws Exception {
        getComboApiService().addCombo(paramsMap);
    }

    /**
     * 查询套餐数量
     * @param paramsMap 请求参数
     * @return 套餐数量
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午3:01:20
     */
    public static int countComboByParam(Map<String, Object> paramsMap) throws Exception {
        return getComboApiService().countComboByParam(paramsMap);
    }

    /**删除套餐信息
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午2:50:15
     */
    public static void removeCombo(Map<String, Object> paramsMap) throws Exception {
        getComboApiService().removeCombo(paramsMap);
    }

    /**
     * 查询套餐配置数量
     * @param paramsMap 请求参数
     * @return 套餐配置数量
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午3:01:20
     */
    public static int countComboManageByParam(Map<String, Object> paramsMap) throws Exception {
        return getComboApiService().countComboManageByParam(paramsMap);
    }

    /**
     * 分页查询套餐配置信息
     * @param paramsMap 请求参数
     * @return 套餐信息列表
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 上午11:15:09
     */
    public static List<Map<String, Object>> getComboManageList(Map<String, Object> paramsMap) throws Exception {
        return getComboApiService().getComboManageList(paramsMap);
    }

    /**
     * 添加套餐配置信息
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午2:50:15
     */
    public static void addComboManage(Map<String, Object> paramsMap) throws Exception {
        getComboApiService().addComboManage(paramsMap);
    }

    /**
     * 删除套餐配置信息
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午2:50:15
     */
    public static void removeComboManage(Map<String, Object> paramsMap) throws Exception {
        getComboApiService().removeComboManage(paramsMap);
    }
    
    /**
     * 套餐配置续时
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年5月5日 下午3:27:20
     */
    public static void continueComboManage(Map<String, Object> paramsMap) throws Exception {
        getComboApiService().continueComboManage(paramsMap);
    }

    /**
     * 获取bean对象
     * @return bean对象
     * @author 范立松  
     * @date 2017年4月18日 下午1:42:32
     */
    public static ComboApiService getComboApiService() {
        if (comboApiService == null) {
            comboApiService = (ComboApiService) BeanUtil.getBean("comboApiService");
        }
        return comboApiService;
    }

}

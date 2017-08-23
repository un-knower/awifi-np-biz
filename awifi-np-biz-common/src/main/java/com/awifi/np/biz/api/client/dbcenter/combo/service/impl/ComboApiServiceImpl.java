/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月18日 上午10:35:53
* 创建作者：范立松
* 文件名称：ComboApiServiceImpl.java
* 版本：  v1.0
* 功能：套餐配置管理数据中心接口
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.combo.service.impl;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.combo.service.ComboApiService;
import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;

@SuppressWarnings("unchecked")
@Service("comboApiService")
public class ComboApiServiceImpl implements ComboApiService {

    /**
     * 查询所有套餐
     * @author 范立松  
     * @date 2017年4月18日 上午11:18:54
     */
    @Override
    public List<Map<String, Object>> getComboList() throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_getallpackage_url");// 获取请求地址
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, null);
        return (List<Map<String, Object>>) returnMap.get("rs");// 套餐信息列表
    }

    /**
     * 添加套餐信息
     * @author 范立松  
     * @date 2017年4月18日 下午7:30:24
     */
    @Override
    public void addCombo(Map<String, Object> paramsMap) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_addpackage_url");// 获取请求地址
        CenterHttpRequest.sendPostRequest(url, JsonUtil.toJson(paramsMap));
    }

    /**
     * 查询套餐数量
     * @author 范立松  
     * @date 2017年4月18日 下午3:01:16
     */
    @Override
    public int countComboByParam(Map<String, Object> paramsMap) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_countpackage_url");// 获取请求地址
        String paramString = "params=" + URLEncoder.encode(JsonUtil.toJson(paramsMap), "UTF-8");
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);
        return (Integer) returnMap.get("rs");// 套餐数量
    }

    /**
     * 删除套餐信息
     * @author 范立松  
     * @date 2017年4月18日 下午3:31:52
     */
    @Override
    public void removeCombo(Map<String, Object> paramsMap) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_deletepackage_url");// 获取请求地址
        String paramString = "params=" + URLEncoder.encode(JsonUtil.toJson(paramsMap), "UTF-8");
        CenterHttpRequest.sendDeleteRequest(url, paramString);
    }

    /**
     * 查询套餐配置数量
     * @author 范立松  
     * @date 2017年4月18日 下午6:34:33
     */
    @Override
    public int countComboManageByParam(Map<String, Object> paramsMap) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_countaccountpackage_url");// 获取请求地址
        String paramString = "params=" + URLEncoder.encode(JsonUtil.toJson(paramsMap), "UTF-8");
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);
        return (Integer) returnMap.get("rs");// 套餐配置数量
    }

    /**
     * 分页查询套餐配置信息
     * @author 范立松  
     * @date 2017年4月18日 下午7:31:08
     */
    @Override
    public List<Map<String, Object>> getComboManageList(Map<String, Object> paramsMap) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_getaccountpackage_url");// 获取请求地址
        String paramString = "params=" + URLEncoder.encode(JsonUtil.toJson(paramsMap), "UTF-8");
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);
        return (List<Map<String, Object>>) returnMap.get("rs");// 套餐配置信息列表
    }

    /**
     * 删除套餐配置信息
     * @author 范立松  
     * @date 2017年4月18日 下午7:34:40
     */
    @Override
    public void removeComboManage(Map<String, Object> paramsMap) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_deleteaccountpackage_url");// 获取请求地址
        String paramString = "params=" + URLEncoder.encode(JsonUtil.toJson(paramsMap), "UTF-8");
        CenterHttpRequest.sendDeleteRequest(url, paramString);
    }

    /**
     * 添加套餐配置信息
     * @author 范立松  
     * @date 2017年4月18日 下午8:26:54
     */
    @Override
    public void addComboManage(Map<String, Object> paramsMap) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_addaccountpackage_url");// 获取请求地址
        CenterHttpRequest.sendPostRequest(url, JsonUtil.toJson(paramsMap));
    }

    /**
     * 套餐配置续时
     * @author 范立松  
     * @date 2017年5月5日 下午3:23:51
     */
    @Override
    public void continueComboManage(Map<String, Object> paramsMap) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_continueaccountpackage_url");// 获取请求地址
        CenterHttpRequest.sendPutRequest(url, JsonUtil.toJson(paramsMap));
    }

}

package com.awifi.np.biz.api.client.dbcenter.device.hotarea.service.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.device.hotarea.model.Hotarea;
import com.awifi.np.biz.api.client.dbcenter.device.hotarea.service.HotareaApiService;
import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月10日 上午10:09:47
 * 创建作者：亢燕翔
 * 文件名称：HotareaApiServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("hotareaApiService")
public class HotareaApiServiceImpl implements HotareaApiService {

    /**
     * 热点管理获取总数
     * @param params 参数
     * @return count
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年2月10日 上午11:06:26
     */
    public int getCountByParam(String params) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_hotareacount_url");//获取数据中心热点管理总记录数接口地址
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url+"?params="+URLEncoder.encode(params,"utf-8"), null);
        return (int) returnMap.get("rs");//总条数
    }

    /**
     * 热点管理列表
     * @param params 参数
     * @return list
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年2月10日 下午2:49:42
     */
    public List<Hotarea> getListByParam(String params) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_hotarealist_url");//获取数据中心热点管理列表接口地址
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url+"?params="+URLEncoder.encode(params,"utf-8"), null);
        List recordsList = (List) returnMap.get("rs");//获取数据集
        int maxSize = recordsList.size();//计算数据集合最大长度
        Hotarea hotarea = null;
        Map<String, Object> recordsMap = null;
        List<Hotarea> hotareaList = new ArrayList<Hotarea>();
        for(int i=0; i<maxSize; i++){
            hotarea = new Hotarea();
            recordsMap = (Map<String, Object>) recordsList.get(i);
            hotarea.setId(CastUtil.toLong(recordsMap.get("hotareaId")));
            hotarea.setHotareaName((String) recordsMap.get("hotareaName"));
            hotarea.setDevMac((String) recordsMap.get("mac"));
            hotarea.setMerchantName((String) recordsMap.get("merchantName"));
            hotarea.setStatus(CastUtil.toInteger(recordsMap.get("isOnline")));
            hotareaList.add(hotarea);
        }
        return hotareaList;
    }

    /**
     * 批量导入热点
     * @param params 参数
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年2月13日 上午10:02:39
     */
    public void batchAddRelation(String params) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_batchaddrelation_url");//获取数据中心批量导入热点接口
        CenterHttpRequest.sendPostRequest(url, params);
    }

    /**
     * 删除热点
     * @param params 参数
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年2月13日 下午2:17:27
     */
    public void deleteByDevMacs(String params) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_deletebydevmacs_url");//获取数据中心删除热点接口
        CenterHttpRequest.sendPutRequest(url, params);
    }

}

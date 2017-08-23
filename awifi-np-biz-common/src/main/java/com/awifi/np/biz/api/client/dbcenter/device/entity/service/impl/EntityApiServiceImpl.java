package com.awifi.np.biz.api.client.dbcenter.device.entity.service.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.device.entity.model.EntityInfo;
import com.awifi.np.biz.api.client.dbcenter.device.entity.service.EntityApiService;
import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.DateUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月8日 上午9:09:47
 * 创建作者：亢燕翔
 * 文件名称：EntityApiServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service(value = "entityApiService")
public class EntityApiServiceImpl implements EntityApiService{

    /**
     * 设备监控查询总数
     * @param params 参数
     * @return count
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年2月8日 上午9:55:13
     */
    public int getEntityInfoCountByMerId(String params) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_entitycount_url");//获取数据中心设备监控总记录数接口地址
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url+"?params="+URLEncoder.encode(params,"utf-8"), null);
        return (int) returnMap.get("rs");//总条数
    }

    /**
     * 设备监控列表
     * @param params 参数
     * @return list
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年2月8日 上午10:03:36
     */
    public List<EntityInfo> getEntityInfoListByMerId(String params) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_entitylist_url");//获取数据中心设备监控列表接口地址
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url+"?params="+URLEncoder.encode(params,"utf-8"), null);
        List recordsList = (List) returnMap.get("rs");//获取数据集
        int maxSize = recordsList.size();//计算数据集合最大长度
        EntityInfo entityInfo = null;
        Map<String, Object> recordsMap = null;
        List<EntityInfo> deviceList = new ArrayList<EntityInfo>();
        Long onlineLastTime = null;//最近在线时间
        for(int i=0; i<maxSize; i++){
            entityInfo = new EntityInfo();
            recordsMap = (Map<String, Object>) recordsList.get(i);
            entityInfo.setMerchantId(CastUtil.toLong(recordsMap.get("merchantId")));//商户id
            entityInfo.setAcName((String) recordsMap.get("acName"));//设备名称
            entityInfo.setEntityType(CastUtil.toInteger(recordsMap.get("entityType")));//设备类型
            entityInfo.setDevMac((String) recordsMap.get("macAddr"));//设备MAC
            entityInfo.setSsid((String) recordsMap.get("ssid"));//SSID
            
            onlineLastTime = CastUtil.toLong(recordsMap.get("onlineLastTime"));//最近在线时间
            entityInfo.setOnlineLastTime(onlineLastTime != null ?  DateUtil.formatTimestamp(onlineLastTime) : null);//最近在线时间
            entityInfo.setOnlineNum((CastUtil.toInteger(recordsMap.get("onlineNum"))));//在线人数
            entityInfo.setStatus(CastUtil.toInteger(recordsMap.get("status")));//状态
            deviceList.add(entityInfo);
        }
        return deviceList;
    }

    /**
     * 编辑设备
     * @param params 参数
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年2月10日 下午3:41:03
     */
    public void update(String params) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_updatedevice_url");//获取数据中心编辑设备接口地址
        CenterHttpRequest.sendPutRequest(url, params);
    }
}

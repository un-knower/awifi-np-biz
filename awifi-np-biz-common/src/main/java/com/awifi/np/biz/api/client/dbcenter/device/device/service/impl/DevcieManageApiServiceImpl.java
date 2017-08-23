package com.awifi.np.biz.api.client.dbcenter.device.device.service.impl;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.device.device.service.DeviceManageApiService;
import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubEntity;
import com.awifi.np.biz.api.client.dbcenter.fatap.util.DeviceQueryClient;
import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;

/**
 * 对应开放平台的DeviceManageService
 *
 */
@SuppressWarnings("unchecked")
@Service("deviceManageApiService")
public class DevcieManageApiServiceImpl implements DeviceManageApiService {

    /**
     * 修改实体设备的流程状态
     * @param params 修改参数
     * @throws Exception 异常
     * @author 李程程
     */
    @Override
    public void updateEntityFlowSts(Map<String, Object> params) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_DeviceManageUpdateEntityFlowSts_url");
        String interfaceParams = JsonUtil.toJson(params);
        CenterHttpRequest.sendPutRequest(url, interfaceParams);
    }

    /**
     * 逻辑删除一台或者多台设备
     * @param ids id集合
     * @throws Exception 异常
     * @author 李程程
     */
    @Override
    public void deleteEntityByIds(String[] ids) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_DeviceManageDeleteEntityByIds_url");
        List<String> strArray = Arrays.asList(ids);
        ValidUtil.valid("id集合[ids]", strArray, "arrayNotBlank");//数组内部是否存在null
        Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        String params = "params=" + URLEncoder.encode(JsonUtil.toJson(map), "UTF-8");
        CenterHttpRequest.sendDeleteRequest(url, params);
    }

    /**
     * 插入一批设备
     * @param sublist 待插入设备集合
     * @throws Exception 异常
     * @author 李程程
     */
    @Override
    public void addEntityBatch(List<CenterPubEntity> sublist) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_DeviceManageAddEntityBatch_url");
        String params = JsonUtil.toJson(sublist);
        CenterHttpRequest.sendPostRequest(url, params);
    }

    /**
     * 批量新增项目型瘦ap
     * @param subList 待插入项目型瘦ap集合
     * @throws Exception 异常
     */
    @Override
    public void addPFitAPBatch(List<CenterPubEntity> subList) throws Exception {
        // TODO Auto-generated method stub
        //String url=SysConfigUtil.getParamValue("");
        //        String url="http://192.168.41.48:28830/device-manage/device/pfitap-batch";
        String url = SysConfigUtil.getParamValue("dbc_DeviceManageAddPFitAPBatch_url");
        String params = JsonUtil.toJson(subList);
        CenterHttpRequest.sendPostRequest(url, params);
    }

    /**
     * 更新实体设备
     * @param entity 设备参数
     * @throws Exception 异常
     */
    @Override
    public void updateEntity(CenterPubEntity entity) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_DeviceManageUpdateEntity_url");
        String interfaceparams = JsonUtil.toJson(entity);
        CenterHttpRequest.sendPutRequest(url, interfaceparams);
    }

    /**
     * 逻辑删除一台或者多台虚拟设备
     * @param ids id的集合
     * @throws Exception 异常
     */

    @Override
    public void deleteDeviceByDeviceIds(String[] ids) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_DeviceManageDeleteDeviceByDeviceIds_url");
        List<String> params = Arrays.asList(ids);
        ValidUtil.valid("id集合[ids]", params, "arrayNotBlank");//数组内部是否存在null
        Map<String, Object> map = new HashMap<>();
        map.put("deviceIds", ids);
        String interfaceParams = "params=" + URLEncoder.encode(JsonUtil.toJson(map), "UTF-8");
        CenterHttpRequest.sendDeleteRequest(url, interfaceParams);
    }

    /**
     * 更新设备信息
     * @author 范立松  
     * @date 2017年5月9日 下午7:34:56
     */
    @Override
    public void updateEntity(Map<String, Object> params) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_DeviceManageUpdateEntity_url");
        String interfaceParams = JsonUtil.toJson(params);
        CenterHttpRequest.sendPutRequest(url, interfaceParams);
    }

    /**
     * 批量删除awifi热点
     * @param ids
     * @throws Exception
     * @author 范涌涛  
     * @date 2017年5月18日 下午9:32:52
     */
    public void deleteHotareaByIds(String[] ids) throws Exception {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("status", 1);// 正常
        queryMap.put("outTypeId", "AWIFI");
        queryMap.put("importer", "EMS");
        StringBuffer alarm = new StringBuffer();
        boolean existError = false;
        for (String id : ids) {
            queryMap.put("hotareaId", id);
            Map<String, Object> hotareaInfo = DeviceQueryClient.queryHotareaInfoById(CastUtil.toLong(id));
            Long merchantId = CastUtil.toLong(hotareaInfo.get("merchantId"));
            if (merchantId != null && merchantId != 0L) {
                alarm.append(hotareaInfo.get("hotareaName") + " ");
                existError = true;
            }

        }
        if (existError) {
            throw new BizException("E2301107", MessageUtil.getMessage("E2301107", alarm));
        }

        String url = SysConfigUtil.getParamValue("dbc_DeviceManageDeleteHotareaByIds_url");
        List<String> strArray = Arrays.asList(ids);
        ValidUtil.valid("热点的id集合[ids]", strArray, "arrayNotBlank");//数组内部是否存在null
        Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        String params = "params=" + URLEncoder.encode(JsonUtil.toJson(map), "UTF-8");
        CenterHttpRequest.sendDeleteRequest(url, params);
    }

    /**
     * 更新awifi热点信息
     * @param reqMap 更新请求参数
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年5月18日 下午10:38:59
     */
    public void updateHotarea(Map<String, Object> reqMap) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_DeviceManageUpdateHotarea_url");
        //        String interfaceParams="params="+URLEncoder.encode(JsonUtil.toJson(paramMap),"UTF-8");
        CenterHttpRequest.sendPutRequest(url, JsonUtil.toJson(reqMap));

    }

    /**
     * 批量新增awifi热点信息
     * @param subList 热点信息
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年5月18日 下午10:39:09
     */
    public void addHotareaBatch(List<CenterPubEntity> subList) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_DeviceManageAddHotareaBatch_url");//获取请求地址
        String params = JsonUtil.toJson(subList);
        CenterHttpRequest.sendPostRequest(url, params);
    }

    /**
     * 查询nasip过滤列表
     * @author 范立松  
     * @date 2017年6月22日 下午2:45:41
     */
    @Override
    public List<Map<String, Object>> getNasFilterList(Map<String, Object> paramsMap) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_DeviceManageQueryFilterList_url");// 获取请求地址
        String paramString = "params=" + URLEncoder.encode(JsonUtil.toJson(paramsMap), "UTF-8");
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);
        return (List<Map<String, Object>>) returnMap.get("rs");// nasip过滤列表
    }

    /**
     * 查询nasip过滤总数
     * @author 范立松  
     * @date 2017年6月22日 下午2:46:49
     */
    @Override
    public int countNasFilterByParam(Map<String, Object> paramsMap) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_DeviceManageQueryFilterCount_url");// 获取请求地址
        String paramString = "params=" + URLEncoder.encode(JsonUtil.toJson(paramsMap), "UTF-8");
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);
        return (Integer) returnMap.get("rs");// nasip过滤总数
    }

    /**
     * 添加nasip过滤
     * @author 范立松  
     * @date 2017年6月22日 下午2:47:40
     */
    @Override
    public void addNasFilter(Map<String, Object> paramsMap) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_DeviceManageAddNasFilter_url");// 获取请求地址
        CenterHttpRequest.sendPostRequest(url, JsonUtil.toJson(paramsMap));
    }

    /**
     * 更新nasip过滤
     * @author 范立松  
     * @date 2017年6月22日 下午2:49:41
     */
    @Override
    public void updateNasFilter(Map<String, Object> paramsMap) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_DeviceManageUpdateNasFilter_url");// 获取请求地址
        CenterHttpRequest.sendPutRequest(url, JsonUtil.toJson(paramsMap));
    }

    /**
     * 删除nasip过滤
     * @author 范立松  
     * @date 2017年6月22日 下午2:49:56
     */
    @Override
    public void removeNasFilter(List<String> nasIpList) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_DeviceManageBatchDeleteNasFilter_url");// 获取请求地址
        String paramString = "params=" + URLEncoder.encode(JsonUtil.toJson(nasIpList), "UTF-8");
        CenterHttpRequest.sendDeleteRequest(url, paramString);
    }

    /**
     * 根据批次号修改实体设备的流程状态
     * @param params 条件
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年7月19日 下午8:08:00
     */
    @Override
    public void updateByBatch(Map<String, Object> params) throws Exception {
        String batchNum = CastUtil.toString(params.get("batchNum"));
        String flowSts = CastUtil.toString(params.get("flowSts"));
        ValidUtil.valid("批次号【batchNum】", batchNum, "{'required':true}");
        ValidUtil.valid("流程状态【flowSts】", flowSts, "{'required':true}");
        String url = SysConfigUtil.getParamValue("dbc_DeviceManageUpdateByBatch_url");
        String interfaceParams = JsonUtil.toJson(params);
        CenterHttpRequest.sendPutRequest(url, interfaceParams);
    }

    /**
     * 根据批次号，逻辑删除一台或多台设备
     * @param params 参数
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年7月19日 下午8:06:56
     */
    @Override
    public void deleteEntityByBatch(Map<String, Object> params) throws Exception {
        String batchNum = CastUtil.toString(params.get("batchNum"));
        ValidUtil.valid("批次号batchNum", batchNum, "{'required':true}");
        String url = SysConfigUtil.getParamValue("dbc_DeviceManageDeleteEntityByBatch_url");// 获取请求地址
        String paramString = "params=" + URLEncoder.encode(JsonUtil.toJson(params), "UTF-8");
        CenterHttpRequest.sendDeleteRequest(url, paramString);
    }
    
    
}

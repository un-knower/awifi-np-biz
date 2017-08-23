package com.awifi.np.biz.api.client.dbcenter.fatap.servcie.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubEntity;
import com.awifi.np.biz.api.client.dbcenter.fatap.servcie.DeviceQueryApiService;
import com.awifi.np.biz.api.client.dbcenter.fatap.util.DeviceQueryClient;
import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.enums.Status;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;

/**
 * 对应开放平台的deviceQueryService接口,
 * @author 李程程
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
@Service(value="deviceQueryApiService")
public class DeviceQueryApiServcieImpl implements DeviceQueryApiService{

    /**
     * 通过条件查询设备以及虚拟信息记录数
     * @param params 条件
     * @return int
     * @throws Exception 异常
     */
    @Override
    public Integer queryEntityCountByParam(Map<String, Object> params) throws Exception {
        String url=SysConfigUtil.getParamValue("dbc_DeviceQueryQueryEntityInfoCountByParam_url");
        String interfaceParams="params="+URLEncoder.encode(JsonUtil.toJson(params), "UTF-8");
        Map<String,Object> returnCount=CenterHttpRequest.sendGetRequest(url, interfaceParams);
        Integer count=(Integer) returnCount.get("rs");
        return count;
    }

    /**
     * 通过条件查询设备和虚拟信息
     * @param params 查询条件
     * @return list
     * @throws Exception 异常
     */
    @Override
    public List<CenterPubEntity> queryEntityInfoListByParam(Map<String, Object> params) throws Exception {
        String url=SysConfigUtil.getParamValue("dbc_DeviceQueryQueryEntityInfoListByParam_url");
        String interfaceParams="params="+URLEncoder.encode(JsonUtil.toJson(params),"UTF-8");
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, interfaceParams);
        //得到具体数据
        List<Map<String, Object>> returnList = (List<Map<String, Object>>) returnMap.get("rs");
        int maxSize = 0;
        if(returnList!=null){
            maxSize = returnList.size();
        }
        List<CenterPubEntity> records = new ArrayList<CenterPubEntity>();
        for (int i = 0; i < maxSize; i++) {
            CenterPubEntity pubEntity = JSONObject.parseObject(JsonUtil.toJson(returnList.get(i)), CenterPubEntity.class);
            records.add(pubEntity);
        }
        return records;
    }
    
    @Override
    public Page queryHotFitapInfoListByParam(Map<String, Object> paramMap) throws Exception {
        Integer pageSize = (Integer)paramMap.get("pageSize");
        Integer pageNo = (Integer)paramMap.get("pageNo");
        ValidUtil.valid("pageSize",pageSize,"{'required':true,'numeric':true}");
        ValidUtil.valid("pageNo",pageNo,"{'required':true,'numeric':true}");
        paramMap.remove("pageNo");
        paramMap.put("pageNum",pageNo);
        paramMap.put("entityType",43); //HOT_FIT_AP 热点型瘦AP
        String url=SysConfigUtil.getParamValue("dbc_DeviceQueryQueryEntityInfoListByParam_url");
        String interfaceParams="params="+URLEncoder.encode(JsonUtil.toJson(paramMap),"UTF-8");
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, interfaceParams);
        List<Map<String, Object>> hotFitapList = (List<Map<String, Object>>) returnMap.get("rs");
        Integer totalRecord = DeviceQueryClient.queryEntityCountByParam(paramMap);
        Page page= new Page<Map<String,Object>>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setRecords(hotFitapList);
        page.setTotalRecord(totalRecord);
        return page;
    }
    /**
     * 根据主键查询设备和虚拟信息
     * @param id 主键
     * @return entity
     * @throws Exception 异常
     */
    @Override
    public CenterPubEntity queryEntityInfoById(String id) throws Exception {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("status", Status.normal.getValue());
        String url=SysConfigUtil.getParamValue("dbc_DeviceQueryQueryEntityInfoById_url");
        String interfaceParams="params="+URLEncoder.encode(JsonUtil.toJson(paramMap),"UTF-8");
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, interfaceParams);
        //得到具体数据
        Map<String, Object> recordMap = (Map<String, Object>) returnMap.get("rs");
        return JsonUtil.fromJson(JsonUtil.toJson(recordMap), CenterPubEntity.class);
    }
    /**
     * 按照条件查不同型号设备数量
     * @param params 查询条件
     * @return long
     * @throws Exception 异常
     * @author 伍恰  
     * @date 2017年6月14日 上午11:13:09
     */
    @Override
    public Long queryEntityCountByParamGroupByModel(Map<String,Object> params)throws Exception{
//        String url="http://192.168.41.48:28840/query-device/query/entityinfo-model-count";
        String url=SysConfigUtil.getParamValue("dbc_DeviceQueryQueryEntityCountByParamGroupByModel_url");
        String interfaceParams="params="+URLEncoder.encode(JsonUtil.toJson(params),"UTF-8");
        Map<String,Object> returnValue=CenterHttpRequest.sendGetRequest(url, interfaceParams);
        JSONArray array=(JSONArray) returnValue.get("rs");
        Long count=(long)array.size();
        return count;
    }
    
    /**
     * 
     * @param id 
     * @return Map 
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年5月18日 下午10:05:18
     */
    public Map<String,Object> queryHotareaInfoById(Long id) throws Exception {
        
        ValidUtil.valid("热点ID", id,"{'required':true,'numeric':true}");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        String url=SysConfigUtil.getParamValue("dbc_DeviceQueryQueryHotareaInfoById_url");
        String interfaceParams="params="+URLEncoder.encode(JsonUtil.toJson(paramMap),"UTF-8");
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, interfaceParams);
        return (Map<String,Object>)returnMap.get("rs");
    }
    
    /**
     * 
     * @param reqParam 参数
     * @return Integer 合计
     * @throws Exception 异常 
     * @author 范涌涛  
     * @date 2017年5月18日 下午10:06:11
     */
    private Integer queryHotareaInfoCountByParam(Map<String,Object> reqParam) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_DeviceQueryQueryHotareaInfoCountByParam_url");
        String interfaceParams="params="+URLEncoder.encode(JsonUtil.toJson(reqParam), "UTF-8");
        Map<String,Object> res = CenterHttpRequest.sendGetRequest(url, interfaceParams);
        return (Integer)res.get("rs");
    }
    /**
     * 
     * @param reqParam 查询参数
     * @return Page 
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年5月18日 下午10:05:28
     */
    public Page queryHotareaInfoListByParam(Map<String,Object> reqParam) throws Exception {
        Integer pageNo = (Integer)reqParam.get("pageNo");
        Integer pageSize = (Integer)reqParam.get("pageSize");
        ValidUtil.valid("pageSize",pageSize,"{'required':true,'numeric':true}");
        ValidUtil.valid("pageNo",pageNo,"{'required':true,'numeric':true}");
        reqParam.remove("pageNo");
        reqParam.put("pageNum", pageNo);//前端使用pageNo,数据中心使用pageNum
        Page page= new Page<Map<String,Object>>();
        String url = SysConfigUtil.getParamValue("dbc_DeviceQueryQueryHotareaInfoListByParam_url");
        reqParam.put("status", 1);
        reqParam.put("isVpn", 0); //是否是vpn 0：hotarea  1：vpn
        Integer totalRecord = queryHotareaInfoCountByParam(reqParam);
        if(totalRecord==null){
            totalRecord=0;
        }
        String interfaceParams="params="+URLEncoder.encode(JsonUtil.toJson(reqParam), "UTF-8");
        Map<String,Object> res = CenterHttpRequest.sendGetRequest(url, interfaceParams);
        List<Map<String,Object>> records = (List<Map<String,Object>>)res.get("rs");
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotalRecord(totalRecord);
        page.setRecords(records);
        return page;
    }
    /**
     * 根据条件查询Chianet热点数量
     * @param reqParam 查询条件
     * @return 条数
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年7月12日 下午2:56:37
     */
    private Integer queryChinaNetHotareaInfoCountByParam(Map<String,Object> reqParam) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_queryChinaNetHotareaInfoCountByParam_url");
        String interfaceParams="params="+URLEncoder.encode(JsonUtil.toJson(reqParam), "UTF-8");
        Map<String,Object> res = CenterHttpRequest.sendGetRequest(url, interfaceParams);
        return (Integer)res.get("rs");
    }
    
    /**
     * 根据条件查询Chianet热点列表
     * @param reqParam 查询条件
     * @return 分页信息
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年7月12日 下午2:56:57
     */
    public Page queryChinaNetHotareaInfoListByParam(Map<String,Object> reqParam) throws Exception {
        Integer pageNo = (Integer)reqParam.get("pageNo");
        Integer pageSize = (Integer)reqParam.get("pageSize");
        ValidUtil.valid("pageSize",pageSize,"{'required':true,'numeric':true}");
        ValidUtil.valid("pageNo",pageNo,"{'required':true,'numeric':true}");
        reqParam.remove("pageNo");
        reqParam.put("pageNum", pageNo);//前端使用pageNo,数据中心使用pageNum
        Page page= new Page<Map<String,Object>>();
        String url = SysConfigUtil.getParamValue("dbc_queryChinaNetHotareaInfoListByParam_url");
        reqParam.put("status", 1);
        Integer totalRecord = queryChinaNetHotareaInfoCountByParam(reqParam);
        if(totalRecord==null){
            totalRecord=0;
        }
        String interfaceParams="params="+URLEncoder.encode(JsonUtil.toJson(reqParam), "UTF-8");
        Map<String,Object> res = CenterHttpRequest.sendGetRequest(url, interfaceParams);
        List<Map<String,Object>> records = (List<Map<String,Object>>)res.get("rs");
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotalRecord(totalRecord);
        page.setRecords(records);
        return page;
    }
    /**
     * 根据ID查询Chianet热点信息
     * @param id 热点ID
     * @return 热点信息
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年7月12日 下午2:56:57
     */
    public Map queryChinaHotareaInfoById(Long id) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_queryChinaHotareaInfoById_url");
        Map<String,Object> reqParam = new HashMap<String,Object>();
        reqParam.put("id", id);
        String interfaceParams="params="+URLEncoder.encode(JsonUtil.toJson(reqParam), "UTF-8");
        Map<String,Object> res = CenterHttpRequest.sendGetRequest(url, interfaceParams);
        return (Map<String,Object>)res.get("rs");
    }
    
    /**
     * 条件查询设备按批次分组 分页总条数
     * @param params 条件
     * @return 总数
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年7月19日 下午7:59:53
     */
    @Override
    public Integer queryEntityCountByParamGroup(Map<String, Object> params) throws Exception {
        String url=SysConfigUtil.getParamValue("dbc_DeviceQueryEntityCountByParamGroup_url");
        String interfaceParams="params="+URLEncoder.encode(JsonUtil.toJson(params), "UTF-8");
        Map<String,Object> returnCount=CenterHttpRequest.sendGetRequest(url, interfaceParams);
        Integer count=(Integer) returnCount.get("rs");
        return count;
    }

    /**
     * 条件查询设备按批次分组 信息--分页
     * @param params 条件
     * @return 设备信息
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年7月19日 下午7:55:31
     */
    @Override
    public List<CenterPubEntity> queryEntityListByParamGroup(Map<String, Object> params) throws Exception {
        String url=SysConfigUtil.getParamValue("dbc_DeviceQueryEntityListByParamGroup_url");
        String interfaceParams="params="+URLEncoder.encode(JsonUtil.toJson(params),"UTF-8");
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, interfaceParams);
        //得到具体数据
        List<Map<String, Object>> returnList = (List<Map<String, Object>>) returnMap.get("rs");
        int maxSize = 0;
        if(returnList!=null){
            maxSize = returnList.size();
        }
        List<CenterPubEntity> records = new ArrayList<CenterPubEntity>();
        for (int i = 0; i < maxSize; i++) {
            CenterPubEntity pubEntity = JSONObject.parseObject(JsonUtil.toJson(returnList.get(i)), CenterPubEntity.class);
            records.add(pubEntity);
        }
        return records;
    }
}

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年4月12日 下午7:03:55
 * 创建作者：范涌涛
 * 文件名称：CorporationApiService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.api.client.dbcenter.corporation.service.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.awifi.np.biz.api.client.dbcenter.corporation.model.Corporation;
import com.awifi.np.biz.api.client.dbcenter.corporation.service.CorporationApiService;
import com.awifi.np.biz.api.client.dbcenter.fatap.util.DeviceQueryClient;
import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.enums.DevType;
import com.awifi.np.biz.common.enums.Status;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;

@SuppressWarnings({"rawtypes","unchecked"})
@Service("corporationApiService")
public class CorporationApiServiceImpl implements CorporationApiService {
    
    /**
     * 根据条件分页查询厂商
     * @param reqParam 查询条件
     * @return 厂商列表
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月17日 下午8:01:01
     */
    public List<Corporation> queryCorpListByParam(Map<String, Object> reqParam) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_CorporationQueryListByParam_url");//获取请求地址
        String paramString = "params="+URLEncoder.encode(JsonUtil.toJson(reqParam),"UTF-8");//请求参数格式化
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);//返回成功的数据
        List<Map<String,Object>> returnList = (List<Map<String, Object>>)returnMap.get("rs");//厂商列表
        int size = returnList.size();
        List<Corporation> corporationList = new ArrayList<Corporation>(size);// 返回的list
        for (int i = 0; i < size; i++) {
            Map<String, Object> corpMap = returnList.get(i);
            Corporation corporation = new Corporation();
            corporation.setId(CastUtil.toLong(corpMap.get("id")));
            corporation.setCorpCode(CastUtil.toString(corpMap.get("corpCode")));
            corporation.setCorpName(CastUtil.toString(corpMap.get("corpName")));
            corporation.setEmail(CastUtil.toString(corpMap.get("email")));
            corporation.setManager(CastUtil.toString(corpMap.get("manager")));
            corporation.setPhone(CastUtil.toString(corpMap.get("phone")));
            corporation.setStatus(CastUtil.toInteger(corpMap.get("status")));
            // corporation.setModifyDate(modifyDate);
            // corporation.setCreateDate(createDate);
            corporationList.add(corporation);
        }
        return corporationList;
    }
    
    
    

    /**
     * 查询符合条件的厂商总数
     * @param reqParam 查询条件
     * @return 条数
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月17日 下午8:02:04
     */
    public int queryCorpCountByParam(Map<String, Object> reqParam) throws Exception {
        reqParam.put("status",1);//1:正常,9:作废
        String url = SysConfigUtil.getParamValue("dbc_CorporationQueryCountByParam_url");//获取请求地址
        String paramString ="params="+URLEncoder.encode(JsonUtil.toJson(reqParam),"UTF-8");//请求参数格式化
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url,paramString);//返回成功的数据
        return CastUtil.toInteger(returnMap.get("rs"));
    }
    /**
     * 根据厂商ID查询厂商信息
     * @param corpId 厂商ID
     * @return 厂商信息
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月17日 下午8:02:14
     */
    public Map queryCorpById(Long corpId) throws Exception {
        Map reqParam = new HashMap<String,Object>();
        reqParam.put("id",corpId);
        String url = SysConfigUtil.getParamValue("dbc_CorporationQueryCorpById_url");//获取请求地址
        String paramString ="params="+URLEncoder.encode(JsonUtil.toJson(reqParam),"UTF-8");//请求参数格式化
        Map<String, Object> res = CenterHttpRequest.sendGetRequest(url,paramString);//返回成功的数据
        return (Map<String, Object>)res.get("rs");
    }
    /**
     * 添加厂商
     * @param paramsMap 厂商信息
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月17日 下午9:23:42
     */
    public void addCorperation(Map<String, Object> paramsMap) throws Exception {
        String corpName = CastUtil.toString(paramsMap.get("corpName"));
        ValidUtil.valid("厂商名称[corpName]",corpName,"required");//必备字段且值不为空
        paramsMap.put("corpCode", corpName);//老功能中corpCode为新增厂家时用户输入,但该字段除数据中心会用来做联表查询外并没有实际意义
        Map checkCorpName = new HashMap<String,Object>();
        checkCorpName.put("corpName", corpName);
        if(queryCorpCountByParam(checkCorpName)>0) { //厂商已存在抛异常
            throw  new ValidException("E2001101", MessageUtil.getMessage("E2001101",corpName));
        }
        String url = SysConfigUtil.getParamValue("dbc_CorperationAdd_url");
        CenterHttpRequest.sendPostRequest(url, JsonUtil.toJson(paramsMap));
    }
    
    /**
     * 修改厂商
     * @param reqParam 厂商参数
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月21日 下午5:45:48
     */
    public void updateCorporation(Map<String,Object> reqParam) throws Exception {
        Long corpId = CastUtil.toLong(reqParam.get("id"));
        String corpName = CastUtil.toString(reqParam.get("corpName")) ;
        ValidUtil.valid("厂商ID", corpId,"{'required':true,'numeric':true}");
        ValidUtil.valid("厂商名称[corpName]",corpName,"required");//必备字段且值不为空
        
        Map checkCorpName = new HashMap<String,Object>();
        checkCorpName.put("corpName", corpName);
        List<Corporation> corpList=queryCorpListByParam(checkCorpName);
        if(corpList.size()>1) {
            throw new BizException("E2001106", MessageUtil.getMessage("E2001106",corpName));//厂家名称已存在多个
        }
        else if(corpList.size()==1) {
            Long existCorpId = corpList.get(0).getId();
            if(existCorpId!=null && !corpId.equals(existCorpId)) { //要修改的厂商名称与其他厂商冲突
                throw  new ValidException("E2001101", MessageUtil.getMessage("E2001101",corpName));
            }
        }
        String url = SysConfigUtil.getParamValue("dbc_CorperationUpdate_url");
        reqParam.put("corpCode",corpName);//老功能中corpCode为新增厂家时用户输入,但该字段除数据中心会用来做联表查询外并没有实际意义
        CenterHttpRequest.sendPutRequest(url, JsonUtil.toJson(reqParam));
    }
    
    /**
     * 删除厂商
     * @param reqParam 厂商参数
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月21日 下午5:46:43
     */
    public void deleteCorporation(Map<String,Object> reqParam) throws Exception {
        Long corpId = CastUtil.toLong(reqParam.get("id"));
        ValidUtil.valid("厂商ID", corpId,"{'required':true,'numeric':true}");
        //厂商下有型号则不允许删除
        Page page = queryModelList(null, corpId);
        if( page.getTotalRecord()>0) {
            throw new BizException("E2001104",MessageUtil.getMessage("E2001104"));
        }
        String url = SysConfigUtil.getParamValue("dbc_CorperationDelete_url");
//        CenterHttpRequest.sendPutRequest(url, JsonUtil.toJson(reqParam));//逻辑删除 导致删除过的厂家无法再新增,改用物理删除
        String interfaceParams="params="+URLEncoder.encode(JsonUtil.toJson(reqParam), "UTF-8");
        CenterHttpRequest.sendDeleteRequest(url, interfaceParams);
    }
    
    /**
     * 查询特定厂商下的型号总数
     * @param corpId 型号ID
     * @param modelName 型号名称
     * @return 条数
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月21日 下午5:55:03
     */
    private Integer queryModelCount(Long corpId, String modelName,Long entityType, boolean fuzzy) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_ModelQueryCount_url");
        Map<String,Object> map= new HashMap<String,Object>();
        map.put("corpId", corpId);
        map.put("status",Status.normal.getValue());
        if (modelName != null && !modelName.equals("") && fuzzy) {
            map.put("modelNameLike", modelName);//全模糊查询
        }
        else {
            map.put("modelName",modelName);
        }
        if(entityType!=null) {
            map.put("entityType", entityType);
        }
        String interfaceParams="params="+URLEncoder.encode(JsonUtil.toJson(map), "UTF-8");
        Map<String,Object> res = CenterHttpRequest.sendGetRequest(url, interfaceParams);
        return CastUtil.toInteger(res.get("rs"));
    }
    
    /**
     * 查询特定厂商下的型号
     * @param reqParam 查询型号参数
     * @param corpId 厂商ID
     * @return 型号列表
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月21日 下午5:47:21
     */
    public Page queryModelList(String reqParam,Long corpId) throws Exception {
        Integer pageSize; 
        Integer pageNo;
        Integer totalRecord;
        Page page= new Page<Map<String,Object>>();
        Map<String,Object> res;
        List<Map<String,Object>> modelList;
        String url = SysConfigUtil.getParamValue("dbc_ModelQueryList_url");
        //查询该厂商下所有型号
        if(reqParam==null) {
            Map paramMap = new HashMap<String,Object>();
            paramMap.put("corpId",corpId);
            paramMap.put("status",1);   //1:有效; 2:作废
            String interfaceParams="params="+URLEncoder.encode(JsonUtil.toJson(paramMap), "UTF-8");
            res = CenterHttpRequest.sendGetRequest(url, interfaceParams);
            modelList = (List<Map<String,Object>>)res.get("rs");
            totalRecord = modelList.size();
            page.setPageNo(1);
            page.setPageSize(totalRecord==0?20:totalRecord);//当查询结果为0条数据totalRecord=0,如果将0赋值给pageSize,setToalRecord函数出错
            page.setTotalPage(1);
            page.setTotalRecord(totalRecord);
        } else {
        //pageSize pageNo有效性检测
            Map paramMap=JsonUtil.fromJson(reqParam, Map.class);
            pageSize = CastUtil.toInteger(paramMap.get("pageSize"));
            pageNo = CastUtil.toInteger(paramMap.get("pageNo"));
            String modelName = CastUtil.toString(paramMap.get("modelName"));
            Long entityType = CastUtil.toLong(paramMap.get("entityType"));
            ValidUtil.valid("pageSize",pageSize,"{'required':true,'numeric':true}");
            ValidUtil.valid("pageNo",pageNo,"{'required':true,'numeric':true}");
            paramMap.remove("pageNo");
            if (modelName != null && !modelName.equals("")) {
                paramMap.remove("modelName");
                paramMap.put("modelNameLike", modelName);//全模糊查询
            }
            paramMap.put("pageNum", pageNo);//前端使用pageNo,数据中心使用pageNum
            paramMap.put("corpId", corpId);
            paramMap.put("status",1);   //1:有效; 2:作废
            totalRecord = queryModelCount(corpId, modelName,entityType,true);
            String interfaceParams="params="+URLEncoder.encode(JsonUtil.toJson(paramMap), "UTF-8");
            res = CenterHttpRequest.sendGetRequest(url, interfaceParams);
            modelList = (List<Map<String,Object>>)res.get("rs");
            page.setPageNo(pageNo);
            page.setPageSize(pageSize);
            page.setTotalRecord(totalRecord);
        }
        
        //根据型号ID和厂商ID查询入库数量
        Map<String, Object> queryCount = new HashMap<String, Object>();
        queryCount.put("importer", "EMS");
        queryCount.put("status", 1);
        
        for(Map<String,Object> modelInfo : modelList) {
            queryCount.put("model", modelInfo.get("id"));
            queryCount.put("corporation", modelInfo.get("corpId"));
            int count = DeviceQueryClient.queryEntityCountByParam(queryCount);
            modelInfo.put("importNum",count);
        }
        page.setRecords(modelList);
        return page;
    }
    
    
    /**
     * 根据型号查询类型
     * @param map 参数
     * @param corpText 厂商名称
     * @return 型号
     * @throws Exception 异常
     */
    public String getEntityType(Map<String,Object> map,String corpText)throws Exception{
        String url=SysConfigUtil.getParamValue("dbc_ModelQueryList_url");
        String interfaceParams="params="+URLEncoder.encode(JsonUtil.toJson(map), "UTF-8");
        Map<String,Object> res=CenterHttpRequest.sendGetRequest(url, interfaceParams);
        List<Map<String,Object>> modelList=(List<Map<String, Object>>) res.get("rs");
        String entityType=null;
        Integer type=0;
        if(modelList.size()==0){
            throw new ValidException("E2301307",MessageUtil.getMessage("E2301307","型号"));
        }
        //查找出一个的情况下，不再比较厂家
        if(modelList.size()==1){
            type=CastUtil.toInteger(modelList.get(0).get("entityType"));
        }
        //大于一个，通过查找厂家的id，来确定唯一的model
        if(modelList.size()>1){
            Map<String,Object> cMap=new HashMap<>();
            cMap.put("corpName",corpText);
            List<Corporation> corpList=queryCorpListByParam(cMap);
            if(corpList.size()==0){
                throw new ValidException("E2301307",MessageUtil.getMessage("E2301307", "厂家名称"));
            }
            Long corpId=corpList.get(0).getId();
            for(Map<String,Object> gMap:modelList){
                int id=(int) gMap.get("corpId");
                if((long)id==corpId){
                    type=CastUtil.toInteger(gMap.get("entityType")) ;
                    break;
                }
            }
        }
        if(type==null||type==0){
//          "型号或者厂商不正确,无法查找相应的型号类型"
            throw new ValidException("E2301306",MessageUtil.getMessage("E2301306"));
        }
        if((type>=31&&type<=37)||type==371||type==372||type==373){
            entityType="FatAP";
        }else{
            entityType=DevType.getDevType(type).entityType();//TODO:这边后续会根据导入的几种类型做专门验证
        }
        return entityType;
    }
    
    
    
    
    /**
     * 根据型号ID查询型号
     * @param modelId 型号ID
     * @return 查询结果
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月21日 下午5:48:22
     */
    public Map<String,Object> queryModelById(Long modelId) throws Exception {
        ValidUtil.valid("modelId",modelId,"{'required':true,'numeric':true}");
        String url = SysConfigUtil.getParamValue("dbc_ModelQueryById_url");
        Map<String,Long> map= new HashMap<String,Long>();
        map.put("id", modelId);
        String interfaceParams="params="+URLEncoder.encode(JsonUtil.toJson(map), "UTF-8");
        Map<String,Object> res = CenterHttpRequest.sendGetRequest(url, interfaceParams);
        return (Map<String, Object>)res.get("rs");
    }
    
    /**
     * 添加型号
     * @param reqParam 型号信息
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月21日 下午5:49:14
     */
    public void addModel(Map<String,Object> reqParam) throws Exception {
        Long  corpId = CastUtil.toLong(reqParam.get("corpId"));
        String modelName =CastUtil.toString(reqParam.get("modelName"));
        ValidUtil.valid("厂商ID[corpId]", corpId, "{'required':true,'numeric':true}");
        ValidUtil.valid("设备类型[entityType]", reqParam.get("entityType"), "{'required':true,'numeric':true}");
        ValidUtil.valid("型号名称[modelName]",modelName,"required");
        reqParam.put("modelCode",modelName);//modelCode数据中心为必填,无实际意义,使用ModelName代替
        //查询该设备型号是否已经存在
        if(queryModelCount(corpId,modelName,null,false) != 0) {
            throw  new ValidException("E2001102", MessageUtil.getMessage("E2001102",modelName));
        }
        String url = SysConfigUtil.getParamValue("dbc_ModelAdd_url");
//        String interfaceParams="params="+URLEncoder.encode(JsonUtil.toJson(reqParam), "UTF-8");
        CenterHttpRequest.sendPostRequest(url, JsonUtil.toJson(reqParam));
    }
    
    /**
     * 更新型号信息
     * @param reqParam 型号信息
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月21日 下午5:50:24
     */
    public void updateModel(Map<String,Object> reqParam) throws Exception {
        ValidUtil.valid("设备类型[entityType]", reqParam.get("entityType"), "{'required':true,'numeric':true}");
        ValidUtil.valid("型号名称[modelName]",reqParam.get("modelName"),"required");
      //查询该设备型号是否已经存在
        Long  corpId = CastUtil.toLong(reqParam.get("corpId"));
        String modelName =CastUtil.toString(reqParam.get("modelName"));
        Long modelId = CastUtil.toLong(reqParam.get("id"));
        modelNameExist( corpId, modelId, modelName);
        String url = SysConfigUtil.getParamValue("dbc_ModelUpdate_url");
        CenterHttpRequest.sendPutRequest(url, JsonUtil.toJson(reqParam));
    }
    
    /**
     * 判断某厂家下是否存在同名的其他型号
     * @param corpId 厂家ID
     * @param modelId 型号ID
     * @param modelName 型号名称
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年6月14日 下午2:55:29
     */
    public void modelNameExist(Long corpId,Long modelId,String modelName) throws Exception {
        Map<String,Object> checkModelParam = new HashMap<String,Object>();
        checkModelParam.put("corpId", corpId);
        checkModelParam.put("status", 1);
        checkModelParam.put("modelName", modelName);
        String urlCheck = SysConfigUtil.getParamValue("dbc_ModelQueryList_url");
        String interfaceParams="params="+URLEncoder.encode(JsonUtil.toJson(checkModelParam), "UTF-8");
        Map<String,Object> res = CenterHttpRequest.sendGetRequest(urlCheck, interfaceParams);
        List<Map<String,Object>> modelList = (List<Map<String,Object>>)res.get("rs");
        if(modelList.size()>1 ) {
            Object []args={modelName,corpId};
            throw new ValidException("E2001105",MessageUtil.getMessage("E2001105",args));
        }
        else if(modelList.size()==1) {
            Long existModelId = CastUtil.toLong(modelList.get(0).get("id"));
            if(!existModelId.equals(modelId)){//根据厂家id，型号名称可查到，且型号ID不相同
                throw  new ValidException("E2001102", MessageUtil.getMessage("E2001102",modelName));
            }
        }
    }
    /**
     * 删除型号
     * @param modelId 型号ID
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月21日 下午5:51:56
     */
    public void deleteModel(Long modelId) throws Exception {
        //根据型号ID和厂商ID查询入库数量(是否在使用)
        Map<String, Object> queryCount = new HashMap<String, Object>();
        queryCount.put("importer", "EMS");
        queryCount.put("status", 1);
        queryCount.put("model", modelId);
        int count = DeviceQueryClient.queryEntityCountByParam(queryCount);
        if(count == 0){
            String url = SysConfigUtil.getParamValue("dbc_ModelDelete_url");
            Map<String,Long> reqParam = new HashMap<String,Long>();
            reqParam.put("id", modelId);
//            String interfaceParams="params="+URLEncoder.encode(JsonUtil.toJson(reqParam), "UTF-8");
            CenterHttpRequest.sendPutRequest(url, JsonUtil.toJson(reqParam));
        }
        else {
            throw  new ValidException("E2001103", MessageUtil.getMessage("E2001103",modelId));//型号正在使用无法删除
        }
    }
    
    @Override
    public String queryListByParam2(Map<String, Object> params) throws Exception {
        // TODO Auto-generated method stub
        String url =SysConfigUtil.getParamValue("dbc_CorporationQueryListByParam_url");//获取请求地址
        String paramString = "params="+URLEncoder.encode(JsonUtil.toJson(params),"UTF-8");//请求参数格式化
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);//返回成功的数据
        JSONArray array = (JSONArray)returnMap.get("rs");//厂商列表
        return array.toJSONString();
    }
    /**
     * 根据型号ID和厂商ID查设备类型
     * @param modelId 型号ID
     * @param corpId 厂商ID
     * @return 设备类型entityType
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年7月12日 下午3:08:55
     */
    @Override
    public String getModelType(Long modelId,Long corpId) throws Exception {
        String url=SysConfigUtil.getParamValue("dbc_ModelQueryList_url");
        Map<String,Object> map=queryModelById(modelId);
        if(map==null||map.get("modelName")==null){
            throw new ValidException("E2301307",MessageUtil.getMessage("E2301307","型号"));
        }
        String modelName=(String) map.get("modelName");
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("modelName", modelName);
        params.put("corpId", corpId);
        String interfaceParams="params="+URLEncoder.encode(JsonUtil.toJson(params), "UTF-8");
        Map<String,Object> res=CenterHttpRequest.sendGetRequest(url, interfaceParams);
        List<Map<String,Object>> modelList=(List<Map<String, Object>>) res.get("rs");
        if(modelList.size()==0){
            throw new ValidException("E2301307",MessageUtil.getMessage("E2301307","型号"));
        }
        Integer type=CastUtil.toInteger(modelList.get(0).get("entityType"));
        String entityType=null;
        if(type==null||type==0){
            throw new ValidException("E2301306",MessageUtil.getMessage("E2301306"));
        }
        if((type>=31&&type<=37)||type==371||type==372||type==373){
            entityType="FatAP";
        }else{
            entityType=DevType.getDevType(type).entityType();
        }
        return entityType;
    }



}

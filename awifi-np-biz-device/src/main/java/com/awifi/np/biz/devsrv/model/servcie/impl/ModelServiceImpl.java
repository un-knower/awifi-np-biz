package com.awifi.np.biz.devsrv.model.servcie.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.awifi.np.biz.api.client.dbcenter.fatap.util.DeviceQueryClient;
import com.awifi.np.biz.api.client.dbcenter.device.model.util.ModelClient;
import com.awifi.np.biz.common.excel.model.CenterPubModel;
import com.awifi.np.biz.common.excel.model.CenterPubModelContract;
import com.awifi.np.biz.common.exception.ApplicationException;
import com.awifi.np.biz.devsrv.model.servcie.ModelService;


@Service(value="modelService")
public class ModelServiceImpl implements ModelService{

    /**
     * 查询所有型号
     * @return map
     * @throws Exception excpeiton
     */
    @Override
    public Map<String, CenterPubModel> getAllModelMap(String model) throws Exception {
        // TODO Auto-generated method stub
        Map<String,CenterPubModel> modelMap=new HashMap<String,CenterPubModel>();
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("status", 1);
        params.put("modelName", model);
        //查询数量
        int total=ModelClient.queryCountByParam(params);
        params.put("pageNum", 1);
        params.put("pageSize",total);
        //查询集合
        String list=ModelClient.queryListByParam(params);
        if(StringUtils.isBlank(list)){
            throw new ApplicationException("不存在该型号:"+model);
        }
        JSONArray array=JSONObject.parseArray(list);
        List<CenterPubModel> modelList = new ArrayList<CenterPubModel>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject tempObj = array.getJSONObject(i);
            CenterPubModel tempModel = new CenterPubModel();
            //现在不需要统计入库数量
//            Map<String, Object> queryCount = new HashMap<String, Object>();
//            queryCount.put("importer", "EMS");
//            queryCount.put("status", 1);
//            queryCount.put("model", tempObj.getLong("id"));
//            queryCount.put("corporation", array.getJSONObject(i).getLong("corpId"));
            // 统计入库的数量
//            int count=DeviceQueryClient.queryEntityCountByParam(params);
//            tempModel.setImportNum(count);
            tempModel.setId(tempObj.getLong("id"));
            tempModel.setCollectionNum(tempObj.getLong("collectionNum"));
            tempModel.setCorpId(tempObj.getLong("corpId"));
            tempModel.setCreateDate(tempObj.getDate("createDate"));
            tempModel.setModelCode(tempObj.getString("modelCode"));
            tempModel.setModelName(tempObj.getString("modelName"));
            tempModel.setModifyDate(tempObj.getDate("modifyDate"));
            tempModel.setStatus(tempObj.getInteger("status"));
            tempModel.setEntityType(tempObj.getInteger("entityType"));
            tempModel.setOrigins(JSONArray.parseArray(tempObj.getString("info"), CenterPubModelContract.class));
            tempModel.setCorpIdText(tempObj.getString("corpIdText"));
            modelList.add(tempModel);
            modelMap.put(tempModel.getId().toString(), tempModel);
        }
        return modelMap;
    }

}

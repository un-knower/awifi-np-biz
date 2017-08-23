/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月10日 下午8:16:27
* 创建作者：范涌涛
* 文件名称：CorpServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.pub.system.corperation.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.corporation.model.Corporation;
import com.awifi.np.biz.api.client.dbcenter.corporation.util.CorporationClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.pub.system.corperation.service.CorpService;

@SuppressWarnings({"rawtypes","unchecked"})
@Service("corpService")
public class CorpServiceImpl implements CorpService {

    /**
     * 根据条件查询厂商
     * @param param 查询条件 page 查询结果
     * @param page 查询结果
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月17日 下午8:01:01
     */
    public void queryListByParam(String param,Page page) throws Exception{
        List<Corporation> corpList=null;
        Integer totalRecord=0;
        if(null==param) { //查询所有厂商
            Map reqParam = new HashMap<String,Object>();
            reqParam.put("status",1);
            totalRecord = CorporationClient.queryCountByParam(reqParam);
            corpList=CorporationClient.queryListByParam(reqParam);
            page.setRecords(corpList);
            page.setPageSize(totalRecord==0?20:totalRecord);//必须先setPageSize再setTotalRecord 否则出错
            page.setTotalRecord(totalRecord);
            page.setPageNo(1);
        }
        else { //分页查询
            Map reqParam=JsonUtil.fromJson(param, Map.class);
            Integer pageSize = CastUtil.toInteger(reqParam.get("pageSize"));
            Integer pageNo = CastUtil.toInteger(reqParam.get("pageNo"));
            String corpName = CastUtil.toString(reqParam.get("corpName"));
            ValidUtil.valid("pageSize",pageSize,"{'required':true,'numeric':true}");
            ValidUtil.valid("pageNo",pageNo,"{'required':true,'numeric':true}");
            reqParam.remove("pageNo");
            reqParam.put("pageNum",pageNo);
            reqParam.put("status",1);
            if (corpName != null && !corpName.equals("")) {
                reqParam.remove("corpName");
                reqParam.put("corpNameRLike", corpName);//右模糊查询
            }
            corpList=CorporationClient.queryListByParam(reqParam);
            totalRecord = CorporationClient.queryCountByParam(reqParam);
            page.setPageNo(pageNo);
            page.setPageSize(pageSize);
            page.setRecords(corpList);
            page.setTotalRecord(totalRecord);
        }
    }
    
}

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月24日 下午8:18:54
* 创建作者：范涌涛
* 文件名称：FitApApiServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.fitap.service.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.fitap.service.FitApApiService;
import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;

@SuppressWarnings({"rawtypes","unchecked"})
@Service("fitApApiService")
public class FitApApiServiceImpl implements FitApApiService{

    /**
     * 条件查询chinanet设备记录数
     * @param reqParam 查询参数
     * @return 记录数
     * @throws Exception 异常
     * @author 范涌涛
     * @date 2017年6月13日 上午10:57:17
     */
    public Integer queryChinaNetDevInfoCount(Map<String, Object> reqParam) throws Exception {
        String url = SysConfigUtil.getParamValue("dbc_QueryChinaNetDevInfoCountByParam_url");//dbc_ChinaNetDeviceQueryServiceQueryChinaNetDevInfoCountByParam_url
        String paramString ="params="+URLEncoder.encode(JsonUtil.toJson(reqParam),"UTF-8");//请求参数格式化
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url,paramString);//返回成功的数据
        return (Integer)returnMap.get("rs");
    }
    
    /**
     * 条件查询chinanet设备列表
     * @param paramMap 查询参数
     * @return page
     * @throws Exception 异常
     * @author 范涌涛
     * @date 2017年6月13日 上午10:57:24
     */
    public Page queryChinanetDevInfoList(Map<String, Object>  paramMap) throws Exception {
        Integer pageSize = (Integer)paramMap.get("pageSize");
        Integer pageNo = (Integer)paramMap.get("pageNo");
        String outTypeId = (String)paramMap.get("outTypeId");
        ValidUtil.valid("pageSize",pageSize,"{'required':true,'numeric':true}");
        ValidUtil.valid("pageNo",pageNo,"{'required':true,'numeric':true}");
        ValidUtil.valid("outTypeId", outTypeId, "{'required':true}");
        paramMap.remove("pageNo");
        paramMap.put("pageNum", pageNo);
        paramMap.put("entityType", 41);     //fitap
        paramMap.put("status",1);           //l(1, "正常"), (2, "锁定/冻结"), (9, "作废/删除");
        String url = SysConfigUtil.getParamValue("dbc_QueryChinaNetDevInfoListByParam_url");//获取请求地址
        String paramString ="params="+URLEncoder.encode(JsonUtil.toJson(paramMap),"UTF-8");//请求参数格式化
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url,paramString);//返回成功的数据
        List<Map<String,Object>> chinanetFitApList = new ArrayList<Map<String, Object>>();
        chinanetFitApList = (List<Map<String,Object>>)returnMap.get("rs");
        Integer totalRecord= queryChinaNetDevInfoCount(paramMap);
        Page page= new Page<Map<String,Object>>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setRecords(chinanetFitApList);
        page.setTotalRecord(totalRecord);
        return page;
    }
    
    
}

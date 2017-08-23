package com.awifi.np.biz.api.client.dbcenter.industry.service.impl;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.api.client.dbcenter.industry.service.IndustryApiService;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月19日 下午2:50:04
 * 创建作者：周颖
 * 文件名称：IndustryApiServiceImpl.java
 * 版本：  v1.0
 * 功能：行业实现类
 * 修改记录：
 */
@Service("industryApiService")
public class IndustryApiServiceImpl implements IndustryApiService {

    /**
     * 获取全部行业数据
     * @return 行业list
     * @author 周颖  
     * @throws Exception 
     * @date 2017年1月20日 上午9:11:09
     */
    @SuppressWarnings({ "unchecked" })
    public List<Map<String, Object>> getAllIndustry() throws Exception{
        String url = SysConfigUtil.getParamValue("dbc_getindustrylist_url");//获取数据中心行业信息接口地址
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("status", 1);
        String paramString = "&params="+URLEncoder.encode(JsonUtil.toJson(param), "UTF-8");
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);
        return (List<Map<String, Object>>) returnMap.get("rs");
    }
}
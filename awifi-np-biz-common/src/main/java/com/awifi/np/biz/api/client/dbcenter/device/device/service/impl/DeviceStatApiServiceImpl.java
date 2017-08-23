/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月5日 下午4:51:31
* 创建作者：王冬冬
* 文件名称：DeviceStatApiServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.device.device.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.device.device.service.DeviceStatApiService;
import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;

@Service("deviceStatApiService")
public class DeviceStatApiServiceImpl implements DeviceStatApiService {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<Map<String, Object>> queryEntityAndMerchantCountByProjectIds(String params) throws Exception {
        Map<String, String> parameterMap = new HashMap<String, String>();//参数map
        parameterMap.put("params", params);
        String interfaceParam = HttpRequest.getParams(parameterMap);//接口参数
        String url = SysConfigUtil.getParamValue("dbc_entityandmerchantcount_url");//获取数据中心虚拟设备总记录数接口地址
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, interfaceParam);
        List list=(List)returnMap.get("rs");//总条数
        
        List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
        if(list==null||list.isEmpty()){
            return null;
        }
        for(int i=0;i<list.size();i++){
            Map<String,Object> map=(Map<String, Object>) list.get(i);
            Map<String,Object> newMap=new HashMap<String,Object>();
            newMap.put("merNum",map.get("merNum"));//商户数
            newMap.put("deviceNum",map.get("deviceNum"));//设备数
            result.add(newMap);
        }
        return result;
    }

}

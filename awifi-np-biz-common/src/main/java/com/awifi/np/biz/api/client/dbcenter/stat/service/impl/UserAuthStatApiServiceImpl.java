/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年7月31日 上午9:00:50
 * 创建作者：梁聪
 * 文件名称：UserAuthStatApiServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.api.client.dbcenter.stat.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.api.client.dbcenter.stat.service.UserAuthStatApiService;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;

@Service("userAuthStatApiService")
public class UserAuthStatApiServiceImpl implements UserAuthStatApiService {

    /**
     * 用户认证-地区维度-折线趋势图接口
     * @param params 参数
     * @return 结果
     * @throws Exception 异常
     * @author 梁聪
     * @date 2017年7月31日 上午9:20:58
     */
    @SuppressWarnings("unchecked")
    public Map<String,Object> getTrendByArea(String params) throws Exception{
        Map<String,Object> returnMap = request(params, "dbc_usergettrendbyarea_url");

        List list=(List)returnMap.get("rs");//总数据

        Map<String,Object> result=new HashMap<String,Object>();
        List<Object> dayFlags=new ArrayList<Object>();
        List<Object> userNums=new ArrayList<Object>();
        List<Object> authNums=new ArrayList<Object>();
        if(list==null||list.isEmpty()){
            return null;
        }

        for(int i=0;i<list.size();i++){
            Map<String,Object> map=(Map<String, Object>) list.get(i);

            dayFlags.add(map.get("dayFlag"));//时间
            userNums.add(map.get("userNum"));//商户数
            authNums.add(map.get("authNum"));//设备数

        }

        result.put("hourOrDay",dayFlags);
        result.put("userNum",userNums);
        result.put("authNum",authNums);
        return result;
    }

    /**
     * 用户认证-地区维度-统计接口
     * @param params 参数
     * @param hasTotal 是否需要返回总计
     * @param areaId 为省市区的拼接组合
     * @return 结果
     * @author 梁聪
     * @throws Exception 异常
     * @date 2017年7月31日 上午9:20:58
     */
    @SuppressWarnings("unchecked")
    public List<Map<String,Object>> getByArea(String params,boolean hasTotal,String areaId) throws Exception{
        Map<String,Object> returnMap = request(params, "dbc_usergetbyarea_url");

        List list=(List)returnMap.get("rs");//总数据
        List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();

        int newUserNum = 0;
        int userNum = 0;
        int authNum = 0;
        //总计
        if(hasTotal){
            Map<String,Object> totalMap=new HashMap<String,Object>();//总计
            totalMap.put("areaName","总计");//总计
            totalMap.put("hasChild", false);//是否有下级
            for(int i=0;i<list.size();i++){
                Map<String,Object> map=(Map<String, Object>) list.get(i);
                newUserNum += (int)map.get("newUserNum");
                userNum += (int)map.get("userNum");
                authNum += (int)map.get("authNum");
            }
            totalMap.put("newUserNum",newUserNum);//新用户数--总计
            totalMap.put("userNum",userNum);//用户数--总计
            totalMap.put("authNum",authNum);//认证数--总计
            result.add(totalMap);
        }

        String statType = (String)((JSONObject) JSON.parse(params)).get("statType");//获取跨度类型
        boolean flag = false;
        if("P".equals(statType)){//省
            flag = true;
        }
        if("T".equals(statType)){//市
            flag = true;
        }
        if("C".equals(statType)){//区
            flag = false;
        }
        for(int i=0;i<list.size();i++){
            Map<String,Object> map=(Map<String, Object>) list.get(i);
            Map<String,Object> newMap=new HashMap<String,Object>();

            Integer areaIdcenter = CastUtil.toInteger(map.get("areaId"));//获取返回的地区id
            if(StringUtils.isBlank(areaId)){//如果上级地区id为空 查省级
                newMap.put("areaId",areaIdcenter.toString());
            }else{
                newMap.put("areaId",areaId + "-" + areaIdcenter);//拼上自身地区id
            }
            newMap.put("areaName",map.get("areaName"));//区域名称
            newMap.put("hasChild",flag);//默认有下级
            newMap.put("newUserNum",map.get("newUserNum"));//新用户数
            newMap.put("userNum",map.get("userNum"));//用户数
            newMap.put("authNum",map.get("authNum"));//认证数
            result.add(newMap);
        }

        return result;
    }

    /**
     * 用户认证-商户维度-折线趋势图接口
     * @param params 参数
     * @return 结果
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jul 31, 2017 4:47:24 PM
     */
    @Override
    public Map<String, Object> getTrendByMerchant(String params) throws Exception {
        return request(params, "dbc_QueryUserAuthStatByMerchant_url");
    }

    /**
     * 用户认证-商户维度-统计接口总计
     * @author 季振宇  
     * @date Aug 1, 2017 9:05:23 AM
     */
    @Override
    public Map<String, Object> getTotalCountByMerchant(String params) throws Exception {
        return request(params, "dbc_QueryUserAuthCountByMerchant_url");
    }

    /**
     * 用户认证-商户维度-统计接口分页总数
     * @author 季振宇  
     * @date Aug 1, 2017 9:05:09 AM
     */
    @Override
    public Map<String, Object> getCountByMerchant(String params) throws Exception {
        return request(params, "dbc_QueryAuthStatListCountByMerchant_url");
    }

    /**
     * 用户认证-商户维度-统计接口分页列表
     * @author 季振宇  
     * @date Aug 1, 2017 9:04:16 AM
     */
    @Override
    public Map<String, Object> getListByMerchant(String params) throws Exception {
        return request(params, "dbc_QueryAuthStatListByMerchant_url");
    }

    /**
     * 从数据中心请求数据
     * @param params 参数
     * @param urlKey 数据库中对应的请求的URL key值
     * @return 结果
     * @throws Exception 异常
     * @author 季振宇  
     * @date Aug 1, 2017 9:03:11 AM
     */
    private Map<String, Object> request(String params,String urlKey) throws Exception {
        String url = SysConfigUtil.getParamValue(urlKey);//获取请求地址
        Map<String, String> paramsMap = new HashMap<String, String>();//参数map
        paramsMap.put("params", params);//添加params参数
        String requestParam = HttpRequest.getParams(paramsMap);//接口参数
        Map<String, Object> resultMap = CenterHttpRequest.sendGetRequest(url, requestParam);//向数据中心发起请求
        return resultMap;//返回结果
    }
}

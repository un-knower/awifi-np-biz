package com.awifi.np.biz.api.client.dbcenter.location.service.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.api.client.dbcenter.location.service.LocationApiService;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月22日 上午8:51:04
 * 创建作者：周颖
 * 文件名称：LocationApiServiceImpl.java
 * 版本：  v1.0
 * 功能：地区实现类
 * 修改记录：
 */
@Service("locationApiService")
@SuppressWarnings("unchecked")
public class LocationApiServiceImpl implements LocationApiService {

    /**日志*/
    private static final Log logger = LogFactory.getLog(LocationApiServiceImpl.class);
    
    /**
     * 获取全部地区信息
     * @author 周颖 
     * @return map 
     * @throws Exception 
     * @date 2017年1月22日 上午10:05:34
     */
    public Map<Long,Map<String,Object>> getAllLocation() throws Exception{
        String url = SysConfigUtil.getParamValue("dbc_getlocationlist_url");//获取数据中心地区信息接口地址
        Map<String,Object> param = new HashMap<String,Object>();//请求参数map
        param.put("status", 1);//状态关系 1 正常 9 作废
        String paramString = "&params="+URLEncoder.encode(JsonUtil.toJson(param), "UTF-8");
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);//请求数据中心接口
        List<Map<String,Object>> locationList = (List<Map<String, Object>>) returnMap.get("rs");//获取数据
        Map<Long,Map<String,Object>> allLocationMap = new LinkedHashMap<Long,Map<String,Object>>();
        Map<String, Object> locationMap;
        for(Map<String,Object> map : locationList){
            Long id = CastUtil.toLong(map.get("id"));//主键id
            if(id == null){//如果为空 继续
                continue;
            }
            Long parentId = CastUtil.toLong(map.get("parentId"));//父id为空 继续
            if(parentId == null){
                logger.debug("错误：地区id[" + id + "]对应的parentId为空！");
                continue;
            }
            locationMap = new LinkedHashMap<String,Object>(6);
            locationMap.put("id", id);
            locationMap.put("name", (String) map.get("areaName"));//地区名称
            locationMap.put("code", (String) map.get("areaCnCode"));//地区编号，用于排序
            locationMap.put("type", (String) map.get("areaType"));//地区级别：PROVINCE 省、CITY 市、 COUNTY 区县
            locationMap.put("parentId", parentId);//父id
            allLocationMap.put(id, locationMap);
        }
        this.configFullName(allLocationMap);//配置地区全路径
        return allLocationMap;
    }
    
    /**
     * 地区 key为地区名称，value为该地区属性
     * @return map
     * @author 周颖  
     * @throws Exception 
     * @date 2017年4月10日 上午9:56:08
     */
    public Map<String, List<Map<String, Object>>> getLocationMap() throws Exception{
        String url = SysConfigUtil.getParamValue("dbc_getlocationlist_url");//获取数据中心地区信息接口地址
        Map<String,Object> param = new HashMap<String,Object>();//请求参数map
        param.put("status", 1);//状态关系 1 正常 9 作废
        String paramString = "&params="+URLEncoder.encode(JsonUtil.toJson(param), "UTF-8");
        Map<String, Object> returnMap = CenterHttpRequest.sendGetRequest(url, paramString);//请求数据中心接口
        List<Map<String,Object>> returnList = (List<Map<String, Object>>) returnMap.get("rs");//获取数据
        Map<String, List<Map<String, Object>>> allLocationMap = new HashMap<String,List<Map<String, Object>>>();
        List<Map<String, Object>> locationList = null;
        Map<String, Object> locationMap = null;
        for(Map<String,Object> location :returnList){
            locationMap = new HashMap<String,Object>();
            Long id = CastUtil.toLong(location.get("id"));//主键id
            if(id == null){//如果为空 继续
                continue;
            }
            Long parentId = CastUtil.toLong(location.get("parentId"));//父id为空 继续
            if(parentId == null){
                logger.debug("错误：地区id[" + id + "]对应的parentId为空！");
                continue;
            }
            locationMap.put("id", id);
            locationMap.put("parentId", parentId);//父id
            String areaName = (String) location.get("areaName");//地区名称
            locationMap.put("name", areaName);
            locationList = allLocationMap.get(areaName);
            if(locationList == null){
                locationList = new ArrayList<Map<String, Object>>();
                allLocationMap.put(areaName, locationList);
            }
            locationList.add(locationMap);
        }
        return allLocationMap;
    }
    
    /**
     * 补全地区全称
     * @param allLocationMap 地区
     * @author 周颖  
     * @date 2017年2月8日 上午10:03:26
     */
    private void configFullName(Map<Long,Map<String,Object>> allLocationMap){
        for(Map.Entry<Long, Map<String,Object>> entry : allLocationMap.entrySet()){
            Map<String,Object> locationMap = entry.getValue();//地区map
            String type = (String)locationMap.get("type");//类别： PROVINCE 省、CITY 市、 COUNTY 区县
            if(StringUtils.isBlank(type)){
                logger.debug("错误：type 为空！");
                continue;
            }
            if(type.equals("PROVINCE")){//省
                String provinceName = (String)locationMap.get("name");//省名称
                locationMap.put("fullName", StringUtils.defaultString(provinceName));//设置地区全路径
            }else if(type.equals("CITY")){//市
                String cityName = (String)locationMap.get("name");//市名称
                
                Long provinceId = (Long)locationMap.get("parentId");//省id
                if(provinceId == null){
                    logger.debug("错误：市[" + cityName + "]对应的父id为空！");
                    continue;
                }
                Map<String,Object> provinceMap = allLocationMap.get(provinceId);//省map
                
                String provinceName = (String)provinceMap.get("name");//省名称
                
                locationMap.put("fullName", StringUtils.defaultString(provinceName) + StringUtils.defaultString(cityName));//设置地区全路径
            }else if(type.equals("COUNTY")){//区县
                String countyName = (String)locationMap.get("name");//区县名称
                
                Long cityId = (Long)locationMap.get("parentId");//市id
                if(cityId == null){
                    logger.debug("错误：区县[" + countyName + "]对应的父id为空！");
                    continue;
                }
                Map<String,Object> cityMap = allLocationMap.get(cityId);//市map
                if(cityMap == null){
                    logger.debug("错误：区县["+ countyName +"]--- 市[" + cityId + "]对应的cityMap为空！");
                    continue;
                }
                String cityName = (String)cityMap.get("name");//市名称
                
                Long provinceId = (Long)cityMap.get("parentId");//省id
                if(provinceId == null){
                    logger.debug("错误：市[" + cityName + "]对应的父id为空！");
                    continue;
                }
                Map<String,Object> provinceMap = allLocationMap.get(provinceId);//省map
                String provinceName = (String)provinceMap.get("name");//省名称
                
                locationMap.put("fullName", StringUtils.defaultString(provinceName) + StringUtils.defaultString(cityName) + StringUtils.defaultString(countyName));//设置地区全路径
            }else{
                logger.debug("错误：type[" + type + "]超出了范围[PROVINCE|CITY|COUNTY].");
            }
        }
    }
}
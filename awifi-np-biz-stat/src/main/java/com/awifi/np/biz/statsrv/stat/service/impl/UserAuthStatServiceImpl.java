/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:Jul 27, 2017 3:53:26 PM
* 创建作者：季振宇
* 文件名称：UserAuthStatServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.statsrv.stat.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.stat.util.UserAuthStatClient;
import com.awifi.np.biz.common.base.constants.Constants;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.DateUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.statsrv.stat.service.UserAuthStatService;
import com.awifi.np.biz.toe.admin.project.service.ProjectService;

@SuppressWarnings("unchecked")
@Service("userAuthStatService")
public class UserAuthStatServiceImpl implements UserAuthStatService {
    
    /**项目服务*/
    @Resource(name = "projectService")
    private ProjectService projectService;
    
    /**
     * 用户认证-商户维度-折线趋势图接口
     * @param params 入参
     * @return 结果
     * @throws Exception 异常
     * @author 季振宇  
     * @date Aug 1, 2017 1:30:13 PM
     */
    public Map<String, Object> getTrendByMerchant(String params) throws Exception {
        Map<String, Object> returnMap = UserAuthStatClient.getTrendByMerchant(params);//请求数据中心接口并返回趋势图数据
        Map<String, Object> resultMap = getHoursOrDayData(returnMap);//处理数据中心返回的数据
        return resultMap;//返回正确格式的数据
    }
    
    /**
     * 处理返回参数
     * @param paramsMap 入参
     * @return 结果
     * @throws Exception 异常
     * @author 季振宇  
     * @date Aug 1, 2017 1:30:13 PM
     */
    private Map<String, Object> getHoursOrDayData(Map<String, Object> paramsMap) {
        
        List<Map<String, Object>> dataMap = (List<Map<String, Object>>)paramsMap.get("rs");//获取数据中心返回的数据数组
        if(dataMap == null){
            return null;
        }
        
        List<String> timeList = new ArrayList<>();//创建时间数组
        List<Integer> userNumList = new ArrayList<>();//创建用户数数组
        List<Integer> authNumList = new ArrayList<>();//创建认证数组
        
        String dateFormat = DateUtil.YYYYMMDD;//定义时间格式
        for (Map<String, Object> map : dataMap) {//循环处理
            String time = (String)map.get("dayFlag");//获取时间
            if (time.length() > 8) {//判断时间是否到小时
                int begin = time.indexOf("[");//获取'['坐标
                int end = time.indexOf("]");//获取']'坐标
                time = time.substring(begin+1, end);//截取小时
            }else {
                Date date = DateUtil.parseToDate(time, dateFormat);//转换为Date类型
                time = DateUtil.formatToString(date, dateFormat);//获取时间string
            }
            timeList.add(time);//添加时间到时间数组
            userNumList.add(CastUtil.toInteger(map.get("userNum")));//添加用户数到用户数数组
            authNumList.add(CastUtil.toInteger(map.get("authNum")));//添加认证数到认证数组
        }
        
        Map<String, Object> resultMap = new HashMap<>();//创建返回map
        resultMap.put("hourOrDay", timeList);//添加时间项
        resultMap.put("userNum", userNumList);//添加用户数
        resultMap.put("authNum", authNumList);//添加认证书
        
        return resultMap;//返回结果
    }

    /**
     * 用户认证-商户维度-接口
     * @param page 页面
     * @param params 入参
     * @throws Exception 异常
     * @author 季振宇  
     * @date Aug 1, 2017 1:30:13 PM
     */
    public void getByMerchant(Page<Map<String,Object>> page,Map<String, Object> params) throws Exception {
        Map<String, Object> totalMap = UserAuthStatClient.getTotalCountByMerchant(JsonUtil.toJson(params));//请求总计数据
        Map<String, Object> total = (Map<String, Object>)totalMap.get("rs");//获取总计
        if(total == null){
            return;
        }
        total.put("merchantName", "总计");
        
        Map<String, Object> countMap = UserAuthStatClient.getCountByMerchant(JsonUtil.toJson(params));//请求总条数
        Integer count = CastUtil.toInteger(countMap.get("rs"));//获取总条数
        page.setTotalRecord(count);//page设置总条数
        
        List<Map<String, Object>> records = new ArrayList<>();//创建返回记录
        records.add(total);//添加总计
        if (count > 0) {//当总条数不为0时
            params.put("pageNum", page.getPageNo());//添加页码
            params.put("pageSize", page.getPageSize());//添加页条数
            Map<String, Object> listMap = UserAuthStatClient.getListByMerchant(JsonUtil.toJson(params));//请求分页数据
            List<Map<String, Object>> record = (List<Map<String, Object>>)listMap.get("rs"); //获取分页数据
            String projectName = projectService.getNameById((Long)params.get("projectId"));//通过项目id获取项目名称
            for(Map<String, Object> data : record){
                data.put("projectName", projectName);
            }
            records.addAll(record);//添加数据
        }
        page.setRecords(records);//返回分页数据
    }

    /**
     * 用户认证-地区维度-折线趋势图接口
     * @param dateScope 统计日期范围，不允许为空，day代表按日统计、month代表按月统计
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @param yearMonth yearMonth
     * @param provinceId 省Id
     * @param cityId 市id
     * @param areaId 区id
     * @param bizType 业务类型
     * @return list
     * @throws Exception 异常
     * @author 梁聪
     * @date 2017年7月31日 下午2:30:25
     */
    public Map<String,Object> getTrendByArea(String dateScope, String beginDate, String endDate, String yearMonth,
                                             Integer provinceId, Integer cityId, Integer areaId, String bizType) throws Exception{

        Map<String,Object> dbParams=getDbParams(dateScope,beginDate,endDate,yearMonth,provinceId,cityId,areaId,bizType);
        return UserAuthStatClient.getTrendByArea(JsonUtil.toJson(dbParams));
    }

    /**
     * 用户认证-地区维度-统计接口
     * @param dateScope 统计日期范围，不允许为空，day代表按日统计、month代表按月统计
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @param yearMonth yearMonth
     * @param provinceId 省Id
     * @param cityId 市id
     * @param country 区id
     * @param bizType 业务类型
     * @param hasTotal 是否需要返回总计
     * @param areaId 省市区id
     * @return list
     * @throws Exception 异常
     * @author 梁聪
     * @date 2017年7月31日 下午2:30:25
     */
    public List<Map<String,Object>> getByArea(String dateScope, String beginDate, String endDate,String yearMonth, Integer provinceId,Integer cityId,
                                        Integer country, String bizType, boolean hasTotal,String areaId)throws Exception{

        Map<String,Object> dbParams=getDbParams(dateScope,beginDate,endDate,yearMonth,provinceId,cityId,country,bizType);
        return UserAuthStatClient.getByArea(JsonUtil.toJson(dbParams),hasTotal,areaId);
    }

    /**
     * 获取数据中心 折线趋势图接口 的入参
     * @param dateScope 统计日期范围，不允许为空，day代表按日统计、month代表按月统计
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @param yearMonth yearMonth
     * @param provinceId 省Id
     * @param cityId 市id
     * @param country 区id
     * @param bizType 业务类型
     * @return 参数map
     * @author 梁聪
     * @date 2017年7月31日 下午2:17:39
     */
    private Map<String, Object> getDbParams(String dateScope, String beginDate, String endDate, String yearMonth,
                                           Integer provinceId, Integer cityId, Integer country, String bizType) {
        Map<String, Object> params=new HashMap<>();

        if(DateUtil.getTodayDate().equals(beginDate)&&DateUtil.getTodayDate().equals(endDate)){
            params.put("timeUnit", "H");
        }
        params.put("timeUnit", "D");

        params.put("bizType", bizType);
        if("month".equals(dateScope)){
            if("month".equals(dateScope)){
                beginDate = DateUtil.getMonthLastDay(yearMonth);//获取一个月最后一天
                endDate = yearMonth + "-01";
            }
        }
        //根据地区id 获取地区纬度
        params.put("statType", getStatType(provinceId,cityId,country));//跨度为区
        params.put("province", provinceId);
        params.put("city", cityId);
        params.put("county", country);

        Date begindate=DateUtil.parseToDate(beginDate, DateUtil.YYYY_MM_DD);
        Date enddate=DateUtil.parseToDate(endDate, DateUtil.YYYY_MM_DD);
        params.put("dayFlagB", DateUtil.formatToString(begindate, DateUtil.YYYYMMDD));
        params.put("dayFlagE", DateUtil.formatToString(enddate, DateUtil.YYYYMMDD));
        return params;
    }
    /**
     * 根据省市区获取statType地区纬度
     * @param provinceId 省Id
     * @param cityId 市id
     * @param country 区id
     * @return statType
     * @author 梁聪
     * @date 2017年8月2日 下午3:21:19
     */
    private String getStatType(Integer provinceId, Integer cityId, Integer country) {
        String statType=null;
        if(provinceId==null&&cityId==null&&country==null){
            statType=Constants.STATTYPEP;//跨度为省
        }
        if(provinceId!=null&&cityId==null&&country==null){
            statType=Constants.STATTYPET;//跨度为市
        }
        if(provinceId!=null&&cityId!=null){
            statType=Constants.STATTYPEC;//跨度为区
        }
        return statType;
    }
}

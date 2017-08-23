package com.awifi.np.biz.statsrv.stat.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.stat.util.PortalPVStatClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.DateUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.statsrv.stat.service.PortalPVStatService;
import com.awifi.np.biz.toe.admin.project.service.ProjectService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017年7月27日 上午11:20:39
 * 创建作者：许尚敏
 * 文件名称：PortalPVStatServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Service("portalPVStatService")
@SuppressWarnings("unchecked")
public class PortalPVStatServiceImpl implements PortalPVStatService{
    
    /**
     * 项目服务
     */
    @Resource(name="projectService")
    private ProjectService projectService;
    
    /**
     * Portal页面-商户维度-统计接口
     * @param page page
     * @param paramsMap 参数
     * @author 许尚敏
     * @throws Exception 
     * @date 2017年7月27日 下午2:12:25
     */
    @SuppressWarnings("rawtypes")
    public void getByMerchant(Page page, Map<String,Object> paramsMap) throws Exception{
        Map<String,Object> requestParamMap=getParams(paramsMap);
        Map<String, Object> returnMap = PortalPVStatClient.getTotalCountByMerchant(requestParamMap);
        //符合条件的总数
        int count = PortalPVStatClient.getCountByMerchant(requestParamMap);
        page.setTotalRecord(count);//page置总条数
        if(count <= 0){//如果小于0 直接返回
            return;
        }
        requestParamMap.put("pageNum", page.getPageNo());
        requestParamMap.put("pageSize", page.getPageSize());

        List<Map<String, Object>> mapPvList = PortalPVStatClient.getListByMerchant(requestParamMap);
        for(Map<String,Object> data:mapPvList){
            String projectName=projectService.getNameById(CastUtil.toLong(data.get("merchantProject")));
            data.put("projectName", projectName);
            Integer pv1=CastUtil.toInteger(data.get("pv1"));
            Integer pv2=CastUtil.toInteger(data.get("pv2"));
            Integer pv3=CastUtil.toInteger(data.get("pv3"));
            Integer pv4=CastUtil.toInteger(data.get("pv4"));
            data.put("totalNum", pv1+pv2+pv3+pv4);
        }
        totalSum(returnMap);
        mapPvList.add(0, returnMap);
        page.setRecords(mapPvList);
    }

    /**
     * 计算合计
     * @param returnMap 总计接口返回的数据map
     * @author 王冬冬  
     * @date 2017年8月21日 下午6:44:12
     */
    private void totalSum(Map<String, Object> returnMap) {
        returnMap.put("merchantName", "总计");
        Integer pv1=CastUtil.toInteger(returnMap.get("pv1"));
        Integer pv2=CastUtil.toInteger(returnMap.get("pv2"));
        Integer pv3=CastUtil.toInteger(returnMap.get("pv3"));
        Integer pv4=CastUtil.toInteger(returnMap.get("pv4"));
        returnMap.put("totalNum", pv1+pv2+pv3+pv4);
    }

    /**
     * 获取调用数据中心的参数
     * @param paramsMap 参数
     * @return map
     * @author 王冬冬  
     * @date 2017年8月21日 下午2:59:22
     */
    private Map<String, Object> getParams(Map<String, Object> paramsMap) {
        Map<String, Object> params=new HashMap<>();
        
        String beginDate=(String) paramsMap.get("beginDate");
        String endDate=(String)paramsMap.get("endDate");
        Date begindate=DateUtil.parseToDate(beginDate, DateUtil.YYYY_MM_DD);
        Date enddate=DateUtil.parseToDate(endDate, DateUtil.YYYY_MM_DD);
      
        params.put("merchantId", paramsMap.get("merchantId"));
        params.put("projectId", paramsMap.get("projectId"));
        params.put("dayFlagB", DateUtil.formatToString(begindate, DateUtil.YYYYMMDD));
        params.put("dayFlagE", DateUtil.formatToString(enddate, DateUtil.YYYYMMDD));
        String timeUnit=(String) paramsMap.get("timeUnit");
        if(StringUtils.isNoneBlank(timeUnit)){
            params.put("timeUnit", timeUnit);
        }
        return params;
    }

    /**
     * Portal页面-商户维度-折线趋势图接口
     * @param paramsMap 参数
     * @return 符合条件的记录
     * @author 许尚敏
     * @throws Exception 
     * @date 2017年7月27日 下午3:07:25
     */
    public Map<String, List<String>> getTrendByMerchant(Map<String,Object> paramsMap) throws Exception{
        Map<String,Object> requestParamMap=getParams(paramsMap);
        List<Map<String, Object>> returnMap = PortalPVStatClient.getTrendByMerchant(requestParamMap);
        Map<String, List<String>> mapList = new HashMap<String, List<String>>();
        List<String> listHourOrDay = new ArrayList<String>();//时间日期列表
        List<String> listPV1 = new ArrayList<String>();//引导页浏览量列表
        List<String> listPV2 = new ArrayList<String>();//认证页浏览量列表
        List<String> listPV3 = new ArrayList<String>();//过渡页浏览量列表
        List<String> listPV4 = new ArrayList<String>();//导航页浏览量列表
        String timeUnit=(String) paramsMap.get("timeUnit");
        for(int i = 0; i< returnMap.size(); i++){
            String dayOrhour=(String) returnMap.get(i).get("visitDate");//获取时间
            if("D".equals(timeUnit)){
                Date date=DateUtil.parseToDate(dayOrhour, DateUtil.YYYYMMDD);
                dayOrhour=DateUtil.formatToString(date, DateUtil.YYYY_MM_DD);
            }else{
                Date date=DateUtil.parseToDate(dayOrhour, DateUtil.YYYYMMDDHH); 
                dayOrhour=Integer.valueOf(date.getHours()).toString();
            }
            Integer p1=CastUtil.toInteger(returnMap.get(i).get("pv1"));
            Integer p2=CastUtil.toInteger(returnMap.get(i).get("pv2"));
            Integer p3=CastUtil.toInteger(returnMap.get(i).get("pv3"));
            Integer p4=CastUtil.toInteger(returnMap.get(i).get("pv4"));
            listHourOrDay.add(dayOrhour);//添加时间日期到列表
            listPV1.add(p1.toString());//添加引导页浏览量到列表
            listPV2.add(p2.toString());//添加认证页浏览量到列表
            listPV3.add(p3.toString());//添加过渡页浏览量到列表
            listPV4.add(p4.toString());//添加导航页浏览量到列表
        }
        mapList.put("hourOrDay", listHourOrDay);
        mapList.put("pv1", listPV1);
        mapList.put("pv2", listPV2);
        mapList.put("pv3", listPV3);
        mapList.put("pv4", listPV4);
        return mapList;
    }
    
    /**
     * Portal页面-地区维度-趋势图
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @param provinceId 省Id
     * @param cityId 市id
     * @param areaId 区id
     * @param entityType 设备类型
     * @param timeUnit 时间单位
     * @return list
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年7月27日 下午2:30:25
     */
    public Map<String, Object> getTrendByArea(String beginDate, String endDate, Integer provinceId, Integer cityId,
            Integer areaId, String entityType, char timeUnit) throws Exception {
        
        Map<String,Object> dbParams=getDbParams(beginDate,endDate,provinceId, cityId,areaId, entityType,timeUnit);
        List<Map<String, Object>> pvList= PortalPVStatClient.getTrendByArea(JsonUtil.toJson(dbParams));
        List<String> dayOrhourList=new ArrayList<>();
        List<Integer> p1List=new ArrayList<>();
        List<Integer> p2List=new ArrayList<>();
        List<Integer> p3List=new ArrayList<>();
        List<Integer> p4List=new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
        if(pvList!=null&&pvList.size()>0){
            for(Map<String,Object> data:pvList){
                String dayOrhour=(String) data.get("dayFlag");
                if(timeUnit=='D'){
                    Date date=DateUtil.parseToDate(dayOrhour, DateUtil.YYYYMMDD);
                    dayOrhour=DateUtil.formatToString(date, DateUtil.YYYY_MM_DD);
                }else{
                    Date date=DateUtil.parseToDate(dayOrhour, DateUtil.YYYYMMDDHH); 
                    dayOrhour=Integer.valueOf(date.getHours()).toString();
                }
                
                Integer p1=CastUtil.toInteger(data.get("pv1"));
                Integer p2=CastUtil.toInteger(data.get("pv2"));
                Integer p3=CastUtil.toInteger(data.get("pv3"));
                Integer p4=CastUtil.toInteger(data.get("pv4"));
                
                dayOrhourList.add(dayOrhour);
                p1List.add(p1);
                p2List.add(p2);
                p3List.add(p3);
                p4List.add(p4);
            }
        }
        map.put("hourOrDay", dayOrhourList);
        map.put("pv1", p1List);
        map.put("pv2", p2List);
        map.put("pv3", p3List);
        map.put("pv4", p4List);
        return map;
    }

    /**
     * 获取数据中心接口的入参
     * @param beginDate 开始时间
     * @param endDate 结束时间
     * @param provinceId 省Id
     * @param cityId 市id
     * @param areaId 区id
     * @param entityType 设备类型
     * @param timeUnit 时间单位
     * @return 参数map
     * @author 王冬冬  
     * @date 2017年7月27日 下午3:17:39
     */
    public Map<String, Object> getDbParams(String beginDate, String endDate, Integer provinceId, Integer cityId,
            Integer areaId, String entityType, char timeUnit) {
        Map<String, Object> params=new HashMap<>();
//        if(provinceId!=null&&cityId==null&&areaId==null){
//            params.put("statType", "P");
//        }
//        if(cityId!=null&&provinceId==null&&areaId==null){
//            params.put("statType", "T");
//        }
//        if(areaId!=null&&provinceId==null&&cityId==null){
//            params.put("statType", "C");
//        }
        params.put("bizType",entityType);
        params.put("province", provinceId);
        params.put("city", cityId);
        params.put("country", areaId);
        params.put("timeUnit", timeUnit);
        Date begindate=DateUtil.parseToDate(beginDate, DateUtil.YYYY_MM_DD);
        Date enddate=DateUtil.parseToDate(endDate, DateUtil.YYYY_MM_DD);
        
        params.put("dayFlagB", DateUtil.formatToString(begindate, DateUtil.YYYYMMDD));
        params.put("dayFlagE", DateUtil.formatToString(enddate, DateUtil.YYYYMMDD));
        return params;
    }

    /**
     * Portal页面-地区维度-列表
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @param provinceId 省id
     * @param cityId 市id
     * @param areaId 区id
     * @param bizType 业务类型类型
     * @param hasTotal 是否需要返回总计
     * @return list
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年7月27日 下午2:30:25
     */
    public List<Map<String, Object>> getByArea(String beginDate, String endDate, Long provinceId, Long cityId,Long areaId, String bizType,Boolean hasTotal)
            throws Exception {
        Map<String,Object> dbParams=getDbParams(beginDate,endDate,provinceId,cityId,areaId, bizType);//组装参数
        List<Map<String, Object>> resultPvList=initList(hasTotal);
        List<Map<String, Object>> pvList= PortalPVStatClient.getByArea(JsonUtil.toJson(dbParams));//数据中心接口查询趋势图数据
        Map<String,Object> map=null;
        Integer pv1Sum=0;
        Integer pv2Sum=0;
        Integer pv3Sum=0;
        Integer pv4Sum=0;
        String statType=(String) dbParams.get("statType");//获取跨度类型
        if(pvList!=null&&pvList.size()>0){
            for(Map<String,Object> data:pvList){
                map=new HashMap<>();
                Integer areaid=CastUtil.toInteger(data.get("areaId"));
                String areaName=(String) data.get("areaName");
                Integer p1=CastUtil.toInteger(data.get("pv1"));//获取引导页浏览量
                Integer p2=CastUtil.toInteger(data.get("pv2"));//获取认证页浏览量
                Integer p3=CastUtil.toInteger(data.get("pv3"));//获取过渡页浏览量
                Integer p4=CastUtil.toInteger(data.get("pv4"));//获取导航页浏览量
                pv1Sum+=p1;
                pv2Sum+=p2;
                pv3Sum+=p3;
                pv4Sum+=p4;
                if("P".equals(statType)){//省
                    map.put("hasChild",true);
                    map.put("areaId",areaid);
                }
                if("T".equals(statType)){//市
                    map.put("hasChild",true);
                    map.put("areaId",provinceId+"-"+areaid);
                }
                if("C".equals(statType)){//区
                    map.put("hasChild",false);
                    map.put("areaId",provinceId+"-"+cityId+"-"+areaid);
                }
                
                map.put("areaName",areaName);
                map.put("pv1", p1);//引导页浏览量
                map.put("pv2", p2);//认证页浏览量
                map.put("pv3", p3);//过渡页浏览量
                map.put("pv4", p4);//导航页浏览量
                map.put("totalNum",p1+p2+p3+p4);//导航页浏览量
                resultPvList.add(map);
            }
            if(hasTotal){//需要总计
                sumPV(resultPvList,pv1Sum,pv2Sum,pv3Sum,pv4Sum);//计算总计
            }
        }
        return resultPvList;
    }

    /**
     * @param beginDate 开始时间
     * @param endDate 结束时间
     * @param provinceId 省id
     * @param cityId 市id
     * @param areaId 区id
     * @param bizType 业务类型
     * @return map
     * @author 王冬冬  
     * @date 2017年7月31日 下午4:04:17
     */
    private Map<String, Object> getDbParams(String beginDate, String endDate, Long provinceId, Long cityId, Long areaId,
            String bizType) {
        Map<String, Object> params=new HashMap<>();
        if(provinceId==null&&cityId==null&&areaId==null){
            params.put("statType", "P");//跨度为省
        }
        if(provinceId!=null&&cityId==null&&areaId==null){
            params.put("statType", "T");//跨度为市
        }
        if(provinceId!=null&&cityId!=null){
            params.put("statType", "C");//跨度为区
        }
        params.put("province", provinceId);
        params.put("city", cityId);
        params.put("country", areaId);
        params.put("bizType", bizType);//业务类型
        Date begindate=DateUtil.parseToDate(beginDate, DateUtil.YYYY_MM_DD);
        Date enddate=DateUtil.parseToDate(endDate, DateUtil.YYYY_MM_DD);
        
        params.put("dayFlagB", DateUtil.formatToString(begindate, DateUtil.YYYYMMDD));//开始日期
        params.put("dayFlagE", DateUtil.formatToString(enddate, DateUtil.YYYYMMDD));//结束日期
        return params;
    }

    /**总计所有引导页，认证页，过渡页，导航页浏览量
     * @param resultPvList 返回数据集合
     * @param pv1Sum 引导页浏览量
     * @param pv2Sum 认证页浏览量
     * @param pv3Sum 过渡页浏览量
     * @param pv4Sum 导航页浏览量
     * @author 王冬冬  
     * @date 2017年7月31日 上午9:16:46
     */
    private void sumPV(List<Map<String, Object>> resultPvList, Integer pv1Sum, Integer pv2Sum, Integer pv3Sum,
            Integer pv4Sum) {
        Map<String, Object> first=resultPvList.get(0);
        if(first==null||first.isEmpty()){
            return;
        }
        first.put("pv1", pv1Sum);//引导页浏览量
        first.put("pv2",pv2Sum);//认证页浏览量
        first.put("pv3", pv3Sum);//过渡页浏览量
        first.put("pv4", pv4Sum);//导航页浏览量
        first.put("totalNum", pv1Sum+pv2Sum+pv3Sum+pv4Sum);//浏览量总计
        return;
    }

    /**
     * 初始化返回集合
     * @return list集合
     * @author 王冬冬  
     * @param hasTotal 是否返回总计
     * @date 2017年7月31日 上午9:18:57
     */
    private List<Map<String, Object>> initList(Boolean hasTotal) {
        List<Map<String, Object>> resultPvList=new ArrayList<Map<String, Object>>();
        if(hasTotal){
            Map<String, Object> map=new HashMap<>();
            map.put("areaName","总计");
            map.put("pv1", 0);//引导页浏览量
            map.put("pv2", 0);//认证页浏览量
            map.put("pv3", 0);//过渡页浏览量
            map.put("pv4", 0);//导航页浏览量
            map.put("totalNum", 0);//浏览量总计
            resultPvList.add(map);
        }
        return resultPvList;
    }

    /**
     * portal地区维度列表导出
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @param provinceId 省id
     * @param cityId 市id
     * @param areaId 区id
     * @param bizType 业务类型类型
     * @return list
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年7月27日 下午2:30:25
     */
    public List<Map<String, Object>> exportByArea(String beginDate, String endDate, Long provinceId, Long cityId,
            Long areaId, String bizType) throws Exception {
        Map<String,Object> dbParams=getDbParams(beginDate,endDate,provinceId,cityId,areaId, bizType);//组装参数
        List<Map<String, Object>> resultPvList=initList(null);
        List<Map<String, Object>> pvList= PortalPVStatClient.getByArea(JsonUtil.toJson(dbParams));//数据中心接口查询趋势图数据
        Map<String,Object> map=null;
        Integer pv1Sum=0;
        Integer pv2Sum=0;
        Integer pv3Sum=0;
        Integer pv4Sum=0;
        String statType=(String) dbParams.get("statType");//获取跨度类型
        if(pvList!=null&&pvList.size()>0){
            for(Map<String,Object> data:pvList){
                map=new HashMap<>();
                Integer areaid=CastUtil.toInteger(data.get("areaId"));
                String areaName=(String) data.get("areaName");
                Integer p1=CastUtil.toInteger(data.get("pv1"));//获取引导页浏览量
                Integer p2=CastUtil.toInteger(data.get("pv2"));//获取认证页浏览量
                Integer p3=CastUtil.toInteger(data.get("pv3"));//获取过渡页浏览量
                Integer p4=CastUtil.toInteger(data.get("pv4"));//获取导航页浏览量
                pv1Sum+=p1;
                pv2Sum+=p2;
                pv3Sum+=p3;
                pv4Sum+=p4;
                if("P".equals(statType)){//省
                    map.put("hasChild",true);
                    map.put("areaId",areaid);
                }
                if("T".equals(statType)){//市
                    map.put("hasChild",true);
                    map.put("areaId",provinceId+"-"+areaid);
                }
                if("C".equals(statType)){//区
                    map.put("hasChild",false);
                    map.put("areaId",provinceId+"-"+cityId+"-"+areaid);
                }
                
                map.put("areaName",areaName);
                map.put("pv1", p1);//引导页浏览量
                map.put("pv2", p2);//认证页浏览量
                map.put("pv3", p3);//过渡页浏览量
                map.put("pv4", p4);//导航页浏览量
                map.put("totalNum",p1+p2+p3+p4);//导航页浏览量
                resultPvList.add(map);
            }
            sumPV(resultPvList,pv1Sum,pv2Sum,pv3Sum,pv4Sum);
        }
        return resultPvList;
    }
}

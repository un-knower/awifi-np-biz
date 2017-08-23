/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月26日 下午1:54:25
* 创建作者：王冬冬
* 文件名称：PortalController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mocksrv.dbcenter.stat.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.util.JsonUtil;

@Controller
@RequestMapping("/mocksrv/dbc")
public class PortalController extends BaseController{
    /**
     * @param params 入参
     * 按照地区统计portal浏览量-趋势图
     * @return map
     * @author 王冬冬
     * @date 2017年1月13日 上午9:41:43
     */
    @ResponseBody
    @RequestMapping(value="/queryPortalPvStatByArea",method=RequestMethod.GET)
    public Map queryAuthCountByParam(@RequestParam(name="params",required=true) String params){
        Map<String,Object> paramMap=JsonUtil.fromJson(params, Map.class);
        paramMap.get("dayFlagB");
        paramMap.get("dayFlagE");
        paramMap.get("province");
        paramMap.get("city");
        paramMap.get("county");
        
        paramMap.get("statType");
        paramMap.get("entityType");
        String timeUnit=(String) paramMap.get("timeUnit");
        List<Map> list=new ArrayList<Map>();
        Random d=new Random();
        if("H".equals(timeUnit)){
            Map<String, Object> secResultMap = new LinkedHashMap<String, Object>();
            secResultMap.put("dayFlag","8");
            secResultMap.put("pv1", d.nextInt(100));
            secResultMap.put("pv2", d.nextInt(100));
            secResultMap.put("pv3", d.nextInt(100));
            secResultMap.put("pv4", d.nextInt(100));
            list.add(secResultMap);
            
            Map<String, Object> secResultMap1 = new LinkedHashMap<String, Object>();
            secResultMap1.put("dayFlag","9");
            secResultMap1.put("pv1", d.nextInt(100));
            secResultMap1.put("pv2", d.nextInt(100));
            secResultMap1.put("pv3", d.nextInt(100));
            secResultMap1.put("pv4", d.nextInt(100));
            list.add(secResultMap1);
            
            Map<String, Object> secResultMap2 = new LinkedHashMap<String, Object>();
            secResultMap2.put("dayFlag","10");
            secResultMap2.put("pv1", d.nextInt(100));
            secResultMap2.put("pv2", d.nextInt(100));
            secResultMap2.put("pv3", d.nextInt(100));
            secResultMap2.put("pv4", d.nextInt(100));
            list.add(secResultMap2);
            
            Map<String, Object> secResultMap3 = new LinkedHashMap<String, Object>();
            secResultMap3.put("dayFlag","11");
            secResultMap3.put("pv1", d.nextInt(100));
            secResultMap3.put("pv2", d.nextInt(100));
            secResultMap3.put("pv3", d.nextInt(100));
            secResultMap3.put("pv4", d.nextInt(100));
            list.add(secResultMap3);
            
            Map<String, Object> secResultMap4 = new LinkedHashMap<String, Object>();
            secResultMap4.put("dayFlag","12");
            secResultMap4.put("pv1", d.nextInt(100));
            secResultMap4.put("pv2", d.nextInt(100));
            secResultMap4.put("pv3", d.nextInt(100));
            secResultMap4.put("pv4", d.nextInt(100));
            list.add(secResultMap4);
        }
        if("D".equals(timeUnit)){
            Map<String, Object> secResultMap = new LinkedHashMap<String, Object>();
            secResultMap.put("dayFlag","2017-07-31");
            secResultMap.put("pv1", d.nextInt(100));
            secResultMap.put("pv2", d.nextInt(100));
            secResultMap.put("pv3", d.nextInt(100));
            secResultMap.put("pv4", d.nextInt(100));
            list.add(secResultMap);
            
            Map<String, Object> secResultMap1 = new LinkedHashMap<String, Object>();
            secResultMap1.put("dayFlag","2017-08-01");
            secResultMap1.put("pv1", d.nextInt(100));
            secResultMap1.put("pv2", d.nextInt(100));
            secResultMap1.put("pv3", d.nextInt(100));
            secResultMap1.put("pv4", d.nextInt(100));
            list.add(secResultMap1);
            
            Map<String, Object> secResultMap2 = new LinkedHashMap<String, Object>();
            secResultMap2.put("dayFlag","2017-08-02");
            secResultMap2.put("pv1", d.nextInt(100));
            secResultMap2.put("pv2", d.nextInt(100));
            secResultMap2.put("pv3", d.nextInt(100));
            secResultMap2.put("pv4", d.nextInt(100));
            list.add(secResultMap2);
            
            Map<String, Object> secResultMap3 = new LinkedHashMap<String, Object>();
            secResultMap3.put("dayFlag","2017-08-03");
            secResultMap3.put("pv1", d.nextInt(100));
            secResultMap3.put("pv2", d.nextInt(100));
            secResultMap3.put("pv3", d.nextInt(100));
            secResultMap3.put("pv4", d.nextInt(100));
            list.add(secResultMap3);
            
            Map<String, Object> secResultMap4 = new LinkedHashMap<String, Object>();
            secResultMap4.put("dayFlag","2017-08-04");
            secResultMap4.put("pv1", d.nextInt(100));
            secResultMap4.put("pv2", d.nextInt(100));
            secResultMap4.put("pv3", d.nextInt(100));
            secResultMap4.put("pv4", d.nextInt(100));
            list.add(secResultMap4);
        }
        Map<String,Object> result=new LinkedHashMap<String,Object>();
        result.put("suc", true);
        result.put("msg", "成功");
        result.put("rs", list);
        return result;
    }
    public static void main(String[] args) {
        Random d=new Random();
        int s=d.nextInt(100);
        System.out.println(s);
    }
    /**
     * @param params 入参
     * 按照地区统计portal浏览量
     * @return map
     * @author 王冬冬
     * @date 2017年1月13日 上午9:41:43
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value="/queryPortalPvByArea",method=RequestMethod.GET)
    public Map queryAuthListByParamGroupByMerchantId(@RequestParam(name="params",required=true) String params){
        Map<String,Object> paramMap=JsonUtil.fromJson(params, Map.class);
        paramMap.get("dayFlagB");
        paramMap.get("dayFlagE");
        paramMap.get("province");
        paramMap.get("city");
        paramMap.get("county");
        
        String statType=(String) paramMap.get("statType");
        paramMap.get("entityType");
        List<Map> list=new ArrayList<Map>();
        Random d=new Random();
        if("P".equals(statType)){
            Map<String, Object> secResultMap = new LinkedHashMap<String, Object>();
            secResultMap.put("areaId","31");
            secResultMap.put("areaName","浙江");
            secResultMap.put("pv1", d.nextInt(100));
            secResultMap.put("pv2", d.nextInt(100));
            secResultMap.put("pv3", d.nextInt(100));
            secResultMap.put("pv4", d.nextInt(100));
            list.add(secResultMap);
            
            Map<String, Object> secResultMap1 = new LinkedHashMap<String, Object>();
            secResultMap1.put("areaId","32");
            secResultMap1.put("areaName","江苏");
            secResultMap1.put("pv1", d.nextInt(100));
            secResultMap1.put("pv2", d.nextInt(100));
            secResultMap1.put("pv3", d.nextInt(100));
            secResultMap1.put("pv4", d.nextInt(100));
            list.add(secResultMap1);
            
            Map<String, Object> secResultMap2 = new LinkedHashMap<String, Object>();
            secResultMap2.put("areaId","33");
            secResultMap2.put("areaName","福建");
            secResultMap2.put("pv1", d.nextInt(100));
            secResultMap2.put("pv2", d.nextInt(100));
            secResultMap2.put("pv3", d.nextInt(100));
            secResultMap2.put("pv4", d.nextInt(100));
            list.add(secResultMap2);
            
            Map<String, Object> secResultMap3 = new LinkedHashMap<String, Object>();
            secResultMap3.put("areaId","34");
            secResultMap3.put("areaName","安徽");
            secResultMap3.put("pv1", d.nextInt(100));
            secResultMap3.put("pv2", d.nextInt(100));
            secResultMap3.put("pv3", d.nextInt(100));
            secResultMap3.put("pv4", d.nextInt(100));
            list.add(secResultMap3);
            
            Map<String, Object> secResultMap4 = new LinkedHashMap<String, Object>();
            secResultMap4.put("areaId","35");
            secResultMap4.put("areaName","江西");
            secResultMap4.put("pv1", d.nextInt(100));
            secResultMap4.put("pv2", d.nextInt(100));
            secResultMap4.put("pv3", d.nextInt(100));
            secResultMap4.put("pv4", d.nextInt(100));
            list.add(secResultMap4);
        }
       
        if("T".equals(statType)){
            Map<String, Object> secResultMap = new LinkedHashMap<String, Object>();
            secResultMap.put("areaId","383");
            secResultMap.put("areaName","杭州");
            secResultMap.put("pv1", d.nextInt(100));
            secResultMap.put("pv2", d.nextInt(100));
            secResultMap.put("pv3", d.nextInt(100));
            secResultMap.put("pv4", d.nextInt(100));
            list.add(secResultMap);
            
            Map<String, Object> secResultMap1 = new LinkedHashMap<String, Object>();
            secResultMap1.put("areaId","384");
            secResultMap1.put("areaName","宁波");
            secResultMap1.put("pv1", d.nextInt(100));
            secResultMap1.put("pv2", d.nextInt(100));
            secResultMap1.put("pv3", d.nextInt(100));
            secResultMap1.put("pv4", d.nextInt(100));
            list.add(secResultMap1);
            
            Map<String, Object> secResultMap2 = new LinkedHashMap<String, Object>();
            secResultMap2.put("areaId","385");
            secResultMap2.put("areaName","绍兴");
            secResultMap2.put("pv1", d.nextInt(100));
            secResultMap2.put("pv2", d.nextInt(100));
            secResultMap2.put("pv3", d.nextInt(100));
            secResultMap2.put("pv4", d.nextInt(100));
            list.add(secResultMap2);
            
            Map<String, Object> secResultMap3 = new LinkedHashMap<String, Object>();
            secResultMap3.put("areaId","386");
            secResultMap3.put("areaName","温州");
            secResultMap3.put("pv1", d.nextInt(100));
            secResultMap3.put("pv2", d.nextInt(100));
            secResultMap3.put("pv3", d.nextInt(100));
            secResultMap3.put("pv4", d.nextInt(100));
            list.add(secResultMap3);
            
            Map<String, Object> secResultMap4 = new LinkedHashMap<String, Object>();
            secResultMap4.put("areaId","387");
            secResultMap4.put("areaName","台州");
            secResultMap4.put("pv1", d.nextInt(100));
            secResultMap4.put("pv2", d.nextInt(100));
            secResultMap4.put("pv3", d.nextInt(100));
            secResultMap4.put("pv4", d.nextInt(100));
            list.add(secResultMap4);
        }
        
        if("C".equals(statType)){
            Map<String, Object> secResultMap = new LinkedHashMap<String, Object>();
            secResultMap.put("areaId","3229");
            secResultMap.put("areaName","拱墅区");
            secResultMap.put("pv1", d.nextInt(100));
            secResultMap.put("pv2", d.nextInt(100));
            secResultMap.put("pv3", d.nextInt(100));
            secResultMap.put("pv4", d.nextInt(100));
            list.add(secResultMap);
            
            Map<String, Object> secResultMap1 = new LinkedHashMap<String, Object>();
            secResultMap1.put("areaId","3230");
            secResultMap1.put("areaName","西湖区");
            secResultMap1.put("pv1", d.nextInt(100));
            secResultMap1.put("pv2", d.nextInt(100));
            secResultMap1.put("pv3", d.nextInt(100));
            secResultMap1.put("pv4", d.nextInt(100));
            list.add(secResultMap1);
            
            Map<String, Object> secResultMap2 = new LinkedHashMap<String, Object>();
            secResultMap2.put("areaId","3231");
            secResultMap2.put("areaName","江干区");
            secResultMap2.put("pv1", d.nextInt(100));
            secResultMap2.put("pv2", d.nextInt(100));
            secResultMap2.put("pv3", d.nextInt(100));
            secResultMap2.put("pv4", d.nextInt(100));
            list.add(secResultMap2);
            
            Map<String, Object> secResultMap3 = new LinkedHashMap<String, Object>();
            secResultMap3.put("areaId","3232");
            secResultMap3.put("areaName","上城区");
            secResultMap3.put("pv1", d.nextInt(100));
            secResultMap3.put("pv2", d.nextInt(100));
            secResultMap3.put("pv3", d.nextInt(100));
            secResultMap3.put("pv4", d.nextInt(100));
            list.add(secResultMap3);
            
            Map<String, Object> secResultMap4 = new LinkedHashMap<String, Object>();
            secResultMap4.put("areaId","3233");
            secResultMap4.put("areaName","下城区");
            secResultMap4.put("pv1", d.nextInt(100));
            secResultMap4.put("pv2", d.nextInt(100));
            secResultMap4.put("pv3", d.nextInt(100));
            secResultMap4.put("pv4", d.nextInt(100));
            list.add(secResultMap4);
        }
        Map<String,Object> result=new LinkedHashMap<String,Object>();
        result.put("suc", true);
        result.put("msg", "成功");
        result.put("rs", list);
        return result;
    }
    
    /**
     * @param params 入参
     * 按照商户统计portal浏览量-趋势图
     * @return map
     * @author 王冬冬
     * @date 2017年1月13日 上午9:41:43
     */
    @ResponseBody
    @RequestMapping(value="/queryPortalPvStatByMerchant",method=RequestMethod.GET)
    public Map queryAuthCountByParamGroupByMerchantId(@RequestParam(name="params",required=true) String params){
        Map<String,Object> paramMap=JsonUtil.fromJson(params, Map.class);
        paramMap.get("dayFlagB");
        paramMap.get("dayFlagE");
        paramMap.get("merchantId");
        paramMap.get("projectId");
        
        List<Map> list=new ArrayList<Map>();
        String timeUnit=(String) paramMap.get("timeUnit");
        Random d=new Random();
        if("H".equals(timeUnit)){
            Map<String, Object> secResultMap = new LinkedHashMap<String, Object>();
            secResultMap.put("dayFlag","8");
            secResultMap.put("pv1", d.nextInt(100));
            secResultMap.put("pv2", d.nextInt(100));
            secResultMap.put("pv3", d.nextInt(100));
            secResultMap.put("pv4", d.nextInt(100));
            list.add(secResultMap);
            
            Map<String, Object> secResultMap1 = new LinkedHashMap<String, Object>();
            secResultMap1.put("dayFlag","9");
            secResultMap1.put("pv1", d.nextInt(100));
            secResultMap1.put("pv2", d.nextInt(100));
            secResultMap1.put("pv3", d.nextInt(100));
            secResultMap1.put("pv4", d.nextInt(100));
            list.add(secResultMap1);
            
            Map<String, Object> secResultMap2 = new LinkedHashMap<String, Object>();
            secResultMap2.put("dayFlag","10");
            secResultMap2.put("pv1", d.nextInt(100));
            secResultMap2.put("pv2", d.nextInt(100));
            secResultMap2.put("pv3", d.nextInt(100));
            secResultMap2.put("pv4", d.nextInt(100));
            list.add(secResultMap2);
            
            Map<String, Object> secResultMap3 = new LinkedHashMap<String, Object>();
            secResultMap3.put("dayFlag","11");
            secResultMap3.put("pv1", d.nextInt(100));
            secResultMap3.put("pv2", d.nextInt(100));
            secResultMap3.put("pv3", d.nextInt(100));
            secResultMap3.put("pv4", d.nextInt(100));
            list.add(secResultMap3);
            
            Map<String, Object> secResultMap4 = new LinkedHashMap<String, Object>();
            secResultMap4.put("dayFlag","12");
            secResultMap4.put("pv1", d.nextInt(100));
            secResultMap4.put("pv2", d.nextInt(100));
            secResultMap4.put("pv3", d.nextInt(100));
            secResultMap4.put("pv4", d.nextInt(100));
            list.add(secResultMap4);
        }
        if("D".equals(timeUnit)){
            Map<String, Object> secResultMap = new LinkedHashMap<String, Object>();
            secResultMap.put("dayFlag","2017-07-31");
            secResultMap.put("pv1", d.nextInt(100));
            secResultMap.put("pv2", d.nextInt(100));
            secResultMap.put("pv3", d.nextInt(100));
            secResultMap.put("pv4", d.nextInt(100));
            list.add(secResultMap);
            
            Map<String, Object> secResultMap1 = new LinkedHashMap<String, Object>();
            secResultMap1.put("dayFlag","2017-08-01");
            secResultMap1.put("pv1", d.nextInt(100));
            secResultMap1.put("pv2", d.nextInt(100));
            secResultMap1.put("pv3", d.nextInt(100));
            secResultMap1.put("pv4", d.nextInt(100));
            list.add(secResultMap1);
            
            Map<String, Object> secResultMap2 = new LinkedHashMap<String, Object>();
            secResultMap2.put("dayFlag","2017-08-02");
            secResultMap2.put("pv1", d.nextInt(100));
            secResultMap2.put("pv2", d.nextInt(100));
            secResultMap2.put("pv3", d.nextInt(100));
            secResultMap2.put("pv4", d.nextInt(100));
            list.add(secResultMap2);
            
            Map<String, Object> secResultMap3 = new LinkedHashMap<String, Object>();
            secResultMap3.put("dayFlag","2017-08-03");
            secResultMap3.put("pv1", d.nextInt(100));
            secResultMap3.put("pv2", d.nextInt(100));
            secResultMap3.put("pv3", d.nextInt(100));
            secResultMap3.put("pv4", d.nextInt(100));
            list.add(secResultMap3);
            
            Map<String, Object> secResultMap4 = new LinkedHashMap<String, Object>();
            secResultMap4.put("dayFlag","2017-08-04");
            secResultMap4.put("pv1", d.nextInt(100));
            secResultMap4.put("pv2", d.nextInt(100));
            secResultMap4.put("pv3", d.nextInt(100));
            secResultMap4.put("pv4", d.nextInt(100));
            list.add(secResultMap4);
        }
        Map<String,Object> result=new LinkedHashMap<String,Object>();
        result.put("suc", true);
        result.put("msg", "成功");
        result.put("rs", list);
        return result;
    }
    
    
    /**
     * @param params 入参
     * 按照商户统计portal浏览量-总计
     * @return map
     * @author 王冬冬
     * @date 2017年1月13日 上午9:41:43
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value="/queryPortalPvCountByMerchant",method=RequestMethod.GET)
    public Map queryAuthListByParamGroupByTime(@RequestParam(name="params",required=true) String params){
        Map<String,Object> paramMap=JsonUtil.fromJson(params, Map.class);
        paramMap.get("dayFlagB");
        paramMap.get("dayFlagE");
        paramMap.get("merchantId");
        paramMap.get("projectId");
       
        Map<String, Object> secResultMap = new LinkedHashMap<String, Object>();
        secResultMap.put("merchantName", "总计");
        secResultMap.put("pv1", 3);
        secResultMap.put("pv2", 22);
        secResultMap.put("pv3", 33);
        secResultMap.put("pv4", 444);
        secResultMap.put("totalNum", 123);
        Map<String,Object> result=new LinkedHashMap<String,Object>();
        result.put("suc", true);
        result.put("msg", "成功");
        result.put("rs", secResultMap);
        return result;
    }
    
    
    /**
     * 按照商户分页统计portal浏览量列表
     * @param params
     * @return
     * @author 王冬冬  
     * @date 2017年7月26日 下午2:52:05
     */
    @ResponseBody
    @RequestMapping(value="/queryPortalPvListByMerchant",method=RequestMethod.GET)
    public Map queryPortalPvListByMerchant(@RequestParam(name="params",required=true) String params){
        Map<String,Object> paramMap=JsonUtil.fromJson(params, Map.class);
        paramMap.get("dayFlagB");
        paramMap.get("dayFlagE");
        paramMap.get("merchantId");
        paramMap.get("projectId");
        paramMap.get("pageNum");
        paramMap.get("pageSize");
        
        Random d=new Random();
        List<Map> list=new ArrayList<Map>();
        Map<String, Object> secResultMap = new LinkedHashMap<String, Object>();
        secResultMap.put("merchantId",12);
        secResultMap.put("merchantName","测试商户");
        secResultMap.put("projectId",4);
        secResultMap.put("pv1", 3);
        secResultMap.put("pv2", 22);
        secResultMap.put("pv3", 33);
        secResultMap.put("pv4", 444);
        list.add(secResultMap);
        
        Map<String, Object> secResultMap1 = new LinkedHashMap<String, Object>();
        secResultMap1.put("merchantId",13);
        secResultMap1.put("merchantName","宁波商户");
        secResultMap1.put("projectId",4);
        secResultMap1.put("pv1", d.nextInt(100));
        secResultMap1.put("pv2", d.nextInt(100));
        secResultMap1.put("pv3", d.nextInt(100));
        secResultMap1.put("pv4", d.nextInt(100));
        list.add(secResultMap1);
        
        Map<String, Object> secResultMap2 = new LinkedHashMap<String, Object>();
        secResultMap2.put("merchantId",14);
        secResultMap2.put("merchantName","宁波商户1");
        secResultMap2.put("projectId",4);
        secResultMap2.put("pv1", d.nextInt(100));
        secResultMap2.put("pv2", d.nextInt(100));
        secResultMap2.put("pv3", d.nextInt(100));
        secResultMap2.put("pv4", d.nextInt(100));
        list.add(secResultMap2);
        
        Map<String, Object> secResultMap3 = new LinkedHashMap<String, Object>();
        secResultMap3.put("merchantId",15);
        secResultMap3.put("merchantName","宁波商户2");
        secResultMap3.put("projectId",4);
        secResultMap3.put("pv1", d.nextInt(100));
        secResultMap3.put("pv2", d.nextInt(100));
        secResultMap3.put("pv3", d.nextInt(100));
        secResultMap3.put("pv4", d.nextInt(100));
        list.add(secResultMap3);
        
        Map<String, Object> secResultMap4 = new LinkedHashMap<String, Object>();
        secResultMap4.put("merchantId",16);
        secResultMap4.put("merchantName","宁波商户3");
        secResultMap4.put("projectId",4);
        secResultMap4.put("pv1", d.nextInt(100));
        secResultMap4.put("pv2", d.nextInt(100));
        secResultMap4.put("pv3", d.nextInt(100));
        secResultMap4.put("pv4", d.nextInt(100));
        list.add(secResultMap4);
        
        Map<String, Object> secResultMap5 = new LinkedHashMap<String, Object>();
        secResultMap5.put("merchantId",17);
        secResultMap5.put("merchantName","测试商户11");
        secResultMap5.put("projectId",4);
        secResultMap5.put("pv1", 3);
        secResultMap5.put("pv2", 22);
        secResultMap5.put("pv3", 33);
        secResultMap5.put("pv4", 444);
        list.add(secResultMap5);
        
        Map<String, Object> secResultMap6 = new LinkedHashMap<String, Object>();
        secResultMap6.put("merchantId",18);
        secResultMap6.put("merchantName","宁波商户12");
        secResultMap6.put("projectId",4);
        
        secResultMap6.put("pv1", d.nextInt(100));
        secResultMap6.put("pv2", d.nextInt(100));
        secResultMap6.put("pv3", d.nextInt(100));
        secResultMap6.put("pv4", d.nextInt(100));
        list.add(secResultMap6);
        
        Map<String, Object> secResultMap7 = new LinkedHashMap<String, Object>();
        secResultMap7.put("merchantId",19);
        secResultMap7.put("merchantName","宁波商户13");
        secResultMap7.put("projectId",4);
        secResultMap7.put("pv1", d.nextInt(100));
        secResultMap7.put("pv2", d.nextInt(100));
        secResultMap7.put("pv3", d.nextInt(100));
        secResultMap7.put("pv4", d.nextInt(100));
        list.add(secResultMap7);
        
        Map<String, Object> secResultMap8 = new LinkedHashMap<String, Object>();
        secResultMap8.put("merchantId",20);
        secResultMap8.put("merchantName","宁波商户14");
        secResultMap8.put("projectId",4);
        secResultMap8.put("pv1", d.nextInt(100));
        secResultMap8.put("pv2", d.nextInt(100));
        secResultMap8.put("pv3", d.nextInt(100));
        secResultMap8.put("pv4", d.nextInt(100));
        list.add(secResultMap8);
        
        Map<String, Object> secResultMap9 = new LinkedHashMap<String, Object>();
        secResultMap9.put("merchantId",21);
        secResultMap9.put("merchantName","宁波商户15");
        secResultMap9.put("projectId",4);
        secResultMap9.put("pv1", d.nextInt(100));
        secResultMap9.put("pv2", d.nextInt(100));
        secResultMap9.put("pv3", d.nextInt(100));
        secResultMap9.put("pv4", d.nextInt(100));
        list.add(secResultMap9);
        
        Map<String, Object> secResultMap10 = new LinkedHashMap<String, Object>();
        secResultMap10.put("merchantId",22);
        secResultMap10.put("merchantName","宁波商户16");
        secResultMap10.put("projectId",4);
        secResultMap10.put("pv1", d.nextInt(100));
        secResultMap10.put("pv2", d.nextInt(100));
        secResultMap10.put("pv3", d.nextInt(100));
        secResultMap10.put("pv4", d.nextInt(100));
        list.add(secResultMap10);
        Map<String,Object> result=new LinkedHashMap<String,Object>();
        result.put("suc", true);
        result.put("msg", "成功");
        result.put("rs", list);
        return result;
    }
    
    
    /**
     * @param params 入参
     * 按照商户统计portal浏览量列表分页条数
     * @return map
     * @author 王冬冬
     * @date 2017年1月13日 上午9:41:43
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value="/queryPortalPvListCountByMerchant",method=RequestMethod.GET)
    public Map queryPortalPvListCountByMerchant(@RequestParam(name="params",required=true) String params){
        Map<String,Object> paramMap=JsonUtil.fromJson(params, Map.class);
        paramMap.get("dayFlagB");
        paramMap.get("dayFlagE");
        paramMap.get("merchantId");
        paramMap.get("projectId");
        
        Map<String,Object> result=new LinkedHashMap<String,Object>();
        result.put("suc", true);
        result.put("msg", "成功");
        result.put("rs", 11);
        return result;
    }
}


package com.awifi.np.biz.mocksrv.dbcenter.stat.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.util.JsonUtil;


/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017年7月27日 上午9:48:44
 * 创建作者：许尚敏
 * 文件名称：PortalPVStatController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Controller
@RequestMapping("/mocksrv/dbc")
public class PortalPVStatController extends BaseController{
    /**
     * Portal页面-商户维度-折线趋势图接口 测试
     * @param params 参数
     * @return json 
     * @author 许尚敏   
     * @date 2017年7月27日 上午9:20:44
     */
    @RequestMapping(method=RequestMethod.GET,value="/queryPortalPvStatByMerchant2")
    @ResponseBody
    public Map<String,Object> queryPortalPvStatByMerchant2(@RequestParam(name="params",required=true) String params){
        logger.debug("开始调用 PortalPVStatController.queryPortalPvStatByMerchant()...........");
        Map<String,Object> paramMap = JsonUtil.fromJson(params, Map.class);
        logger.debug("beginDate=" + (String)paramMap.get("beginDate"));//开始日期，不允许为空
        logger.debug("endDate=" + (String)paramMap.get("endDate"));//截至日期，不允许为空
        logger.debug("projectId=" + (String)paramMap.get("projectId"));//项目id，数字，不允许为空
        logger.debug("merchantId=" + (String)paramMap.get("merchantId"));//商户id，数字，允许为空
        Map<String, String> dataList = new HashMap<String, String>();
        dataList.put("hourOrDay", "8");//时间：当天返回小时（示例：3），其它情况返回天（示例：2017-07-26）
        dataList.put("pv1", "123");//引导页浏览量
        dataList.put("pv2", "123");//认证页浏览量
        dataList.put("pv3", "123");//过渡页浏览量
        dataList.put("pv4", "123");//导航页浏览量
        List<Map<String, String>> dataLists = new ArrayList<Map<String, String>>();
        dataLists.add(dataList);
        dataLists.add(dataList);
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("suc", "true");
        resultMap.put("code", "0");
        resultMap.put("msg", "success.");
        resultMap.put("data", dataLists);
        return resultMap;
    }
    
    /**
     * Portal页面-商户维度-统计接口-获取跨页总计 测试
     * @param params 参数
     * @return json 
     * @author 许尚敏   
     * @date 2017年7月27日 上午9:20:44
     */
    @RequestMapping(method=RequestMethod.GET,value="/queryPortalPvCountByMerchant2")
    @ResponseBody
    public Map<String,Object> queryPortalPvCountByMerchant2(@RequestParam(name="params",required=true) String params){
        logger.debug("开始调用 MerchantController.queryPortalPvCountByMerchant()...........");
        Map<String,Object> paramMap = JsonUtil.fromJson(params, Map.class);
        logger.debug("beginDate=" + (String)paramMap.get("beginDate"));//开始日期，不允许为空
        logger.debug("endDate=" + (String)paramMap.get("endDate"));//截至日期，不允许为空
        logger.debug("projectId=" + (String)paramMap.get("projectId"));//项目id，数字，不允许为空
        logger.debug("merchantId=" + (String)paramMap.get("merchantId"));//商户id，数字，允许为空
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("suc", "true");
        resultMap.put("code", "0");
        resultMap.put("msg", "success.");
        resultMap.put("rs", 6);
        return resultMap;
    }
    
    /**
     * Portal页面-商户维度-统计接口-获取分页总记录数 测试
     * @param params 参数
     * @return json 
     * @author 许尚敏   
     * @date 2017年7月27日 上午9:20:44
     */
    @RequestMapping(method=RequestMethod.GET,value="/queryPortalPvListCountByMerchant2")
    @ResponseBody
    public Map<String,Object> queryPortalPvListCountByMerchant2(@RequestParam(name="params",required=true) String params){
        logger.debug("开始调用 PortalPVStatController.queryPortalPvListCountByMerchant()...........");
        Map<String,Object> paramMap = JsonUtil.fromJson(params, Map.class);
        logger.debug("beginDate=" + (String)paramMap.get("beginDate"));//开始日期，不允许为空
        logger.debug("endDate=" + (String)paramMap.get("endDate"));//截至日期，不允许为空
        logger.debug("projectId=" + (String)paramMap.get("projectId"));//项目id，数字，不允许为空
        logger.debug("merchantId=" + (String)paramMap.get("merchantId"));//商户id，数字，允许为空
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("code", "0");
        resultMap.put("msg", "success.");
        resultMap.put("rs", 8);
        return resultMap;
    }
    
    /**
     * Portal页面-商户维度-统计接口-获取分页数据集合 测试
     * @param params 参数
     * @return json 
     * @author 许尚敏   
     * @date 2017年7月27日 上午9:20:44
     */
    @RequestMapping(method=RequestMethod.GET,value="/queryPortalPvListByMerchant2")
    @ResponseBody
    public Map<String,Object> queryPortalPvListByMerchant2(@RequestParam(name="params",required=true) String params){
        logger.debug("开始调用 PortalPVStatController.queryPortalPvListByMerchant()...........");
        Map<String,Object> paramMap = JsonUtil.fromJson(params, Map.class);
        logger.debug("beginDate=" + (String)paramMap.get("beginDate"));//开始日期，不允许为空
        logger.debug("endDate=" + (String)paramMap.get("endDate"));//截至日期，不允许为空
        logger.debug("projectId=" + (String)paramMap.get("projectId"));//项目id，数字，不允许为空
        logger.debug("merchantId=" + (String)paramMap.get("merchantId"));//商户id，数字，允许为空
        Map<String, String> dataList = new HashMap<String, String>();
        dataList.put("merchantName", "总计");//商户名称
        dataList.put("pv1", "123");//引导页浏览量--总计
        dataList.put("pv2", "123");//认证页浏览量--总计
        dataList.put("pv3", "123");//过渡页浏览量--总计
        dataList.put("pv4", "123");//导航页浏览量--总计
        dataList.put("totalNum", "123");//浏览量总计
        List<Map<String, String>> dataLists = new ArrayList<Map<String, String>>();
        dataLists.add(dataList);
        dataList = new HashMap<String, String>();
        dataList.put("merchantId", "31");//商户id
        dataList.put("merchantName", "xxxx");//商户名称
        dataList.put("projectId", "123");//项目id
        dataList.put("projectName", "xxxx");//项目名称
        dataList.put("pv1", "123");//引导页浏览量
        dataList.put("pv2", "123");//认证页浏览量
        dataList.put("pv3", "123");//过渡页浏览量
        dataList.put("pv4", "123");//导航页浏览量
        dataList.put("totalNum", "123");//浏览量总计
        dataLists.add(dataList);
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("code", "0");
        resultMap.put("msg", "success.");
        resultMap.put("data", dataLists);
        return resultMap;
    }
}

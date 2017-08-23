/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:Jul 27, 2017 9:13:23 AM
* 创建作者：季振宇
* 文件名称：UserAuthStatController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mocksrv.dbcenter.stat.controller;

import java.util.*;

import com.awifi.np.biz.common.base.constants.Constants;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.util.JsonUtil;

@SuppressWarnings("unchecked")
@Controller
@RequestMapping("/mocksrv/dbc")
public class UserAuthStatController extends BaseController {
    
    /**
     * 模拟用户认证-商户维度-折线趋势图接口
     * @param params 参数
     * @return 折线趋势图数据
     * @author 季振宇  
     * @date Jul 27, 2017 9:41:31 AM
     */
    @RequestMapping(method=RequestMethod.GET,value="/queryUserAuthStatByMerchant")
    @ResponseBody
    public Map<String,Object> queryUserAuthStatByMerchant(@RequestParam(name="params",required=true) String params){
        logger.debug("开始调用 UserAuthStatController.queryUserAuthStatByMerchant()...........");
        Map<String,Object> paramMap=JsonUtil.fromJson(params, Map.class);
        String timeUnit = (String)paramMap.get("timeUnit");
        logger.debug("merchantId=" + CastUtil.toInteger(paramMap.get("merchantId")));//商户ID
        logger.debug("projectId=" + CastUtil.toInteger(paramMap.get("projectId")));//项目ID
        logger.debug("timeUnit=" + timeUnit);//查询时间类型：天/月
        logger.debug("dayFlagB=" + (String)paramMap.get("dayFlagB"));//开始日期（必传）
        logger.debug("dayFlagE=" + (String)paramMap.get("dayFlagE"));//结束日期（必传）
        
        List<Map<String, Object>> list = new ArrayList<>();//返回的数据数组
        if (timeUnit.equals("H")) {
            for (int i = 0; i < 24; i++) {
                Map<String, Object> map1 = new HashMap<>(); //返回数据项1
                map1.put("dayFlag", "20170727["+ i +"]");//时间
                map1.put("newUserNum", getRandom());//新用户数
                map1.put("userNum", getRandom());//用户数
                map1.put("authNum", getRandom());//认证数
                list.add(map1);//添加到数组
            }
        }else {
            for (int i = 1; i <= 20; i++) {
                Map<String, Object> map1 = new HashMap<>(); //返回数据项1
                map1.put("dayFlag", "201707"+(i<10?("0"+i):i));//时间
                map1.put("newUserNum", getRandom());//新用户数
                map1.put("userNum", getRandom());//用户数
                map1.put("authNum", getRandom());//认证数
                list.add(map1);//添加到数组
            }
        }
        
        return packageResult(list); //组装返回参数
    }
    
    /**
     * 封装结果
     * @param dataLists 参数
     * @return map
     * @author 季振宇  
     * @date 2017年7月25日 下午2:51:44
     */
    private Map<String,Object> packageResult(Object dataLists){
        Map<String,Object> resultMap = new LinkedHashMap<String,Object>();
        resultMap.put("suc", true); //返回成功标识
        resultMap.put("code", "0");//返回成功码
        resultMap.put("msg", "success.");//返回成功信息
        resultMap.put("rs", dataLists);//返回成功数据
        return resultMap;
    }
    
    /**
     * 模拟获取跨页总计
     * @param params 参数
     * @return 跨页总计
     * @author 季振宇  
     * @date Jul 27, 2017 9:57:05 AM
     */
    @RequestMapping(method=RequestMethod.GET,value="/queryUserAuthCountByMerchant")
    @ResponseBody
    public Map<String,Object> queryUserAuthCountByMerchant(@RequestParam(value="params", required=true) String params){
        logger.debug("开始调用 MethodTestController.post()...........");
        Map<String,Object> paramMap=JsonUtil.fromJson(params, Map.class);
        logger.debug("merchantId=" + CastUtil.toInteger(paramMap.get("merchantId")));//商户ID
        logger.debug("projectId=" + CastUtil.toInteger(paramMap.get("projectId")));//项目ID
        logger.debug("dayFlagB=" + (String)paramMap.get("dayFlagB"));//开始日期（必传）
        logger.debug("dayFlagE=" + (String)paramMap.get("dayFlagE"));//结束日期（必传）
        
        Map<String, Object> map1 = new HashMap<>(); //返回数据项1
        map1.put("newUserNum", getRandom());//新用户数
        map1.put("userNum", getRandom());//用户数
        map1.put("authNum", getRandom());//认证数
        
        return packageResult(map1);
    }
    
    /**
     * 模拟获取分页总记录数
     * @param params 参数
     * @return 总条数
     * @author 季振宇  
     * @date Jul 27, 2017 9:55:29 AM
     */
    @RequestMapping(method=RequestMethod.GET,value="/queryAuthStatListCountByMerchant")
    @ResponseBody
    public Map<String,Object> queryAuthStatListCountByMerchant(@RequestParam(name="params",required=true) String params){
        logger.debug("开始调用 MethodTestController.post()...........");
        Map<String,Object> paramMap=JsonUtil.fromJson(params, Map.class);
        logger.debug("merchantId=" + CastUtil.toInteger(paramMap.get("merchantId")));//商户ID
        logger.debug("projectId=" + CastUtil.toInteger(paramMap.get("projectId")));//项目ID
        logger.debug("dayFlagB=" + (String)paramMap.get("dayFlagB"));//开始日期（必传）
        logger.debug("dayFlagE=" + (String)paramMap.get("dayFlagE"));//结束日期（必传）
        return packageResult(25); 
    }
    
    /**
     * 模拟获取分页数据集合
     * @param params 参数
     * @return 分页列表
     * @author 季振宇  
     * @date Jul 27, 2017 9:56:39 AM
     */
    @RequestMapping(method=RequestMethod.GET,value="/queryAuthStatListByMerchant")
    @ResponseBody
    public Map<String,Object> queryAuthStatListByMerchant(@RequestParam(value="params", required=true) String params){
        logger.debug("开始调用 UserAuthStatController.queryUserAuthStatByMerchant()...........");
        Map<String,Object> paramMap=JsonUtil.fromJson(params, Map.class);
        logger.debug("merchantId=" + CastUtil.toInteger(paramMap.get("merchantId")));//商户ID
        logger.debug("projectId=" + CastUtil.toInteger(paramMap.get("projectId")));//项目ID
        logger.debug("dayFlagB=" + (String)paramMap.get("dayFlagB"));//开始日期（必传）
        logger.debug("dayFlagE=" + (String)paramMap.get("dayFlagE"));//结束日期（必传）
        Integer pageNo = CastUtil.toInteger(paramMap.get("pageNo"));
        Integer pageSize = CastUtil.toInteger(paramMap.get("pageSize"));
        
        if (pageNo == null) {
            pageNo = 1;
        }
        
        if (pageSize == null) {
            pageSize = 20;
        }
        
        List<Map<String, Object>> list = new ArrayList<>();//返回的数据数组
        int size = (pageNo == 1 ? pageSize:5);
        for (int i = 0; i < size; i++) {
            Map<String, Object> map1 = new HashMap<>();//返回数据项1
            map1.put("merchantId", i+1);//商户id
            map1.put("merchantName", "apple"+(i+1));//商户名称
            map1.put("projectId", ""+getRandom());//项目id
            map1.put("projectName", "apple"+getRandom());//项目名称
            map1.put("newUserNum", getRandom());//新用户数
            map1.put("userNum", getRandom());//用户数
            map1.put("authNum", getRandom());//认证数
            list.add(map1);//添加到数组
        }

        return packageResult(list);
    }

    /**
     * 按照地区统计用户认证量-趋势图
     * @param params 入参
     * @return map
     * @author 梁聪
     * @date 2017年7月27日 上午9:41:43
     */
    @ResponseBody
    @RequestMapping(value="/queryUserAuthStatByArea",method= RequestMethod.GET)
    public Map queryUserAuthStatByArea(@RequestParam(name="params",required=true) String params){
        Map<String,Object> paramMap=JsonUtil.fromJson(params, Map.class);
        logger.debug("statType=" + (String)paramMap.get("statType"));//统计跨度，P/T/C，P表示按省份，T表示按城市，必传
        logger.debug("province=" + paramMap.get("province"));
        logger.debug("city=" + paramMap.get("city"));
        logger.debug("county=" + paramMap.get("county"));
        logger.debug("bizType=" + (String)paramMap.get("bizType"));
        logger.debug("timeUnit=" + (String)paramMap.get("timeUnit"));
        logger.debug("dayFlagB=" + (String)paramMap.get("dayFlagB"));//开始日期（必传）
        logger.debug("dayFlagE=" + (String)paramMap.get("dayFlagE"));//结束日期（必传）

        String begindate= (String)paramMap.get("dayFlagB");
        String enddate=(String)paramMap.get("dayFlagE");


        List<Map> list=new ArrayList<Map>();


        int[] dayFlagHs = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};
        String[] dayFlagDs = {"20170601","20170602","20170603","20170604","20170605","20170606","20170607","20170608",
                             "20170609","20170610","20170611","20170612","20170613","20170614","20170615","20170616",
                             "20170617","20170618","20170619","20170620","20170621","20170622","20170623","20170624"};
        int[] newUserNums = {120,132,101,134,90,230,210,369,466,546,820,932,901,934,1290,1330,1320,1450,1632,1701,1854,1990,2030,2110};
        int[] userNums =    {220,182,191,234,290,330,310,320,332,301,334,390,380,420,450,432,401,464,490,530,560,589,623,659};
        int[] authNums =    {150,232,201,154,190,330,410,450,432,461,454,490,530,540,550,562,571,584,590,630,660,740,839,932};

        String today = DateUtil.formatToString(new Date(),DateUtil.YYYYMMDD);
        for(int i=0;i<dayFlagHs.length;i++){
            Map<String, Object> secResultMap = new LinkedHashMap<String, Object>();
            if(today.equals(begindate)&&today.equals(enddate)){
                secResultMap.put("hourOrDay", dayFlagHs[i]);
            }else{
                secResultMap.put("hourOrDay",dayFlagDs[i]);
            }
            secResultMap.put("newUserNum", newUserNums[i]);
            secResultMap.put("userNum", userNums[i]);
            secResultMap.put("authNum", authNums[i]);
            list.add(secResultMap);
        }

        Map<String,Object> result=new LinkedHashMap<String,Object>();
        result.put("suc", true);
        result.put("msg", "成功");
        result.put("rs", list);
        return result;
    }

    /**
     * 按照地区统计用户认证情况
     * @param params 入参
     * @return map
     * @author 梁聪
     * @date 2017年7月27日 上午9:41:43
     */
    @ResponseBody
    @RequestMapping(value="/queryUserAuthByArea",method= RequestMethod.GET)
    public Map queryUserAuthByArea(@RequestParam(name="params",required=true) String params){
        Map<String,Object> paramMap=JsonUtil.fromJson(params, Map.class);
        logger.debug("statType=" + (String)paramMap.get("statType"));//统计跨度，P/T/C，P表示按省份，T表示按城市，必传
        logger.debug("province=" + paramMap.get("province"));
        logger.debug("city=" + paramMap.get("city"));
        logger.debug("county=" + paramMap.get("county"));
        logger.debug("dayFlagB=" + (String)paramMap.get("dayFlagB"));//开始日期（必传）
        logger.debug("dayFlagE=" + (String)paramMap.get("dayFlagE"));//结束日期（必传）
        String statType = (String)paramMap.get("statType");

        List<Map> list=new ArrayList<Map>();

        String[] areaIdP = {"31","2","25","13","16"};
        String[] areaNameP = {"浙江","北京","上海","湖北","江苏"};

        String[] areaIdT = {"31-383","31-384","31-385","31-386","31-387"};
        String[] areaNameT = {"杭州","湖州","嘉兴","金华","丽水"};

        String[] areaIdC = {"31-383-3230","31-383-3231","31-383-3232","31-383-3233","31-383-3234"};
        String[] areaNameC = {"上城区","下城区","拱墅区","滨江区","江干区"};

        int[] newUserNums = {120, 132, 101, 134, 90};
        int[] userNums =  {220, 182, 191, 234, 290};
        int[] authNums =  {150, 232, 201, 154, 190};

        for(int i=0;i<newUserNums.length;i++){
            Map<String, Object> secResultMap = new LinkedHashMap<String, Object>();
            if(Constants.STATTYPEP.equals(statType)) {//省
                secResultMap.put("areaId", areaIdP[i]);
                secResultMap.put("areaName", areaNameP[i]);
            }
            if(Constants.STATTYPET.equals(statType)) {//区
                secResultMap.put("areaId", areaIdT[i]);
                secResultMap.put("areaName", areaNameT[i]);
            }
            if(Constants.STATTYPEC.equals(statType)) {//市
                secResultMap.put("areaId", areaIdC[i]);
                secResultMap.put("areaName", areaNameC[i]);
            }

            secResultMap.put("newUserNum", newUserNums[i]);
            secResultMap.put("userNum", userNums[i]);
            secResultMap.put("authNum", authNums[i]);
            list.add(secResultMap);
        }

        Map<String,Object> result=new LinkedHashMap<String,Object>();
        result.put("suc", true);
        result.put("msg", "成功");
        result.put("rs", list);
        return result;
    }

    private int getRandom() {
        return (int)(Math.random()*100);
    }
}

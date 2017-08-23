/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月26日 下午4:41:09
* 创建作者：周颖
* 文件名称：DeviceTrendStatController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mocksrv.dbcenter.stat.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.constants.Constants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;

@Controller
@RequestMapping("/mocksrv/dbc")
public class DeviceTrendStatController extends BaseController {

    /**
     * 设备发展按周统计
     * @param params 参数
     * @return 结果
     * @author 周颖  
     * @date 2017年7月26日 下午5:13:25
     */
    @ResponseBody
    @RequestMapping(value="/querydevicedevelopbyweek",method=RequestMethod.GET)
    public Map<String,Object> queryDeviceDevelopByWeek(@RequestParam(name="params",required=true) String params){
        logger.debug("入参："+params);
        Map<String,Object> paramMap=JsonUtil.fromJson(params, Map.class);
        Long provinceId = CastUtil.toLong(paramMap.get("province"));//省
        Long cityId = CastUtil.toLong(paramMap.get("city"));//市
        Long countyId = CastUtil.toLong(paramMap.get("county"));//区县
        String entityType = (String) paramMap.get("entityType");//设备类型
        String statType = (String) paramMap.get("statType");//(统计跨度) P表示按省份，T表示按城市，C表示城镇，必传
        String dayFlagB = (String) paramMap.get("dayFlagB");//开始日期（必传）
        String dayFlagE = (String) paramMap.get("dayFlagE");//结束日期（必传）
        
        ValidUtil.valid("statType", statType, "required");
        ValidUtil.valid("dayFlagB", dayFlagB, "required");
        ValidUtil.valid("dayFlagE", dayFlagE, "required");
        
        List<Map<String,Object>> deviceDevelopList = new ArrayList<Map<String,Object>>();
        Map<String,Object> deviceDevelopMap = new HashMap<String, Object>();
        if(StringUtils.equals(statType, Constants.STATTYPEP)){
            deviceDevelopMap.put("areaId", 31);
            deviceDevelopMap.put("areaName", "浙江");
            deviceDevelopMap.put("acceptArriveNum", 5);
            deviceDevelopMap.put("acceptNewNum", 10);
            deviceDevelopMap.put("acceptUnbindeNum", 15);
            deviceDevelopMap.put("apArriveNum", 20);
            deviceDevelopMap.put("apNewNum", 25);
            deviceDevelopMap.put("apUnbindNum", 30);
            deviceDevelopMap.put("apActiveNum", 35);
            deviceDevelopMap.put("arriveMerchantNum", 40);
            deviceDevelopMap.put("activeMerchantNum", 45);
            deviceDevelopList.add(deviceDevelopMap);
            
            deviceDevelopMap = new HashMap<String, Object>();
            deviceDevelopMap.put("areaId", 2);
            deviceDevelopMap.put("areaName", "北京");
            deviceDevelopMap.put("acceptArriveNum", 11);
            deviceDevelopMap.put("acceptNewNum", 12);
            deviceDevelopMap.put("acceptUnbindeNum", 13);
            deviceDevelopMap.put("apArriveNum", 14);
            deviceDevelopMap.put("apNewNum", 15);
            deviceDevelopMap.put("apUnbindNum", 16);
            deviceDevelopMap.put("apActiveNum", 17);
            deviceDevelopMap.put("arriveMerchantNum", 18);
            deviceDevelopMap.put("activeMerchantNum", 19);
            deviceDevelopList.add(deviceDevelopMap);
            
            deviceDevelopMap = new HashMap<String, Object>();
            deviceDevelopMap.put("areaId", 25);
            deviceDevelopMap.put("areaName", "上海");
            deviceDevelopMap.put("acceptArriveNum", 21);
            deviceDevelopMap.put("acceptNewNum", 22);
            deviceDevelopMap.put("acceptUnbindeNum", 23);
            deviceDevelopMap.put("apArriveNum", 24);
            deviceDevelopMap.put("apNewNum", 25);
            deviceDevelopMap.put("apUnbindNum", 26);
            deviceDevelopMap.put("apActiveNum", 27);
            deviceDevelopMap.put("arriveMerchantNum", 0);
            deviceDevelopMap.put("activeMerchantNum", 0);
            deviceDevelopList.add(deviceDevelopMap);
            
            deviceDevelopMap = new HashMap<String, Object>();
            deviceDevelopMap.put("areaId", 13);
            deviceDevelopMap.put("areaName", "湖北");
            deviceDevelopMap.put("acceptArriveNum", 21);
            deviceDevelopMap.put("acceptNewNum", 22);
            deviceDevelopMap.put("acceptUnbindeNum", 23);
            deviceDevelopMap.put("apArriveNum", 24);
            deviceDevelopMap.put("apNewNum", 25);
            deviceDevelopMap.put("apUnbindNum", 26);
            deviceDevelopMap.put("apActiveNum", 27);
            deviceDevelopMap.put("arriveMerchantNum", 0);
            deviceDevelopMap.put("activeMerchantNum", 0);
            deviceDevelopList.add(deviceDevelopMap);
            
            deviceDevelopMap = new HashMap<String, Object>();
            deviceDevelopMap.put("areaId", 16);
            deviceDevelopMap.put("areaName", "江苏");
            deviceDevelopMap.put("acceptArriveNum", 21);
            deviceDevelopMap.put("acceptNewNum", 22);
            deviceDevelopMap.put("acceptUnbindeNum", 23);
            deviceDevelopMap.put("apArriveNum", 24);
            deviceDevelopMap.put("apNewNum", 25);
            deviceDevelopMap.put("apUnbindNum", 26);
            deviceDevelopMap.put("apActiveNum", 27);
            deviceDevelopMap.put("arriveMerchantNum", 0);
            deviceDevelopMap.put("activeMerchantNum", 0);
            deviceDevelopList.add(deviceDevelopMap);
            
        }else if(StringUtils.equals(statType, Constants.STATTYPET)){
            deviceDevelopMap.put("areaId", 383);
            deviceDevelopMap.put("areaName", "杭州");
            deviceDevelopMap.put("acceptArriveNum", 1);
            deviceDevelopMap.put("acceptNewNum", 2);
            deviceDevelopMap.put("acceptUnbindeNum", 3);
            deviceDevelopMap.put("apArriveNum", 4);
            deviceDevelopMap.put("apNewNum", 5);
            deviceDevelopMap.put("apUnbindNum", 6);
            deviceDevelopMap.put("apActiveNum", 7);
            deviceDevelopMap.put("arriveMerchantNum", 8);
            deviceDevelopMap.put("activeMerchantNum", 9);
            deviceDevelopList.add(deviceDevelopMap);
            
            deviceDevelopMap = new HashMap<String, Object>();
            deviceDevelopMap.put("areaId", 393);
            deviceDevelopMap.put("areaName", "衢州");
            deviceDevelopMap.put("acceptArriveNum", 1);
            deviceDevelopMap.put("acceptNewNum", 2);
            deviceDevelopMap.put("acceptUnbindeNum", 3);
            deviceDevelopMap.put("apArriveNum", 4);
            deviceDevelopMap.put("apNewNum",5);
            deviceDevelopMap.put("apUnbindNum", 6);
            deviceDevelopMap.put("apActiveNum", 7);
            deviceDevelopMap.put("arriveMerchantNum", 8);
            deviceDevelopMap.put("activeMerchantNum", 9);
            deviceDevelopList.add(deviceDevelopMap);
            
            deviceDevelopMap = new HashMap<String, Object>();
            deviceDevelopMap.put("areaId", 386);
            deviceDevelopMap.put("areaName", "金华");
            deviceDevelopMap.put("acceptArriveNum", 1);
            deviceDevelopMap.put("acceptNewNum", 2);
            deviceDevelopMap.put("acceptUnbindeNum", 3);
            deviceDevelopMap.put("apArriveNum", 4);
            deviceDevelopMap.put("apNewNum",5);
            deviceDevelopMap.put("apUnbindNum", 6);
            deviceDevelopMap.put("apActiveNum", 7);
            deviceDevelopMap.put("arriveMerchantNum", 8);
            deviceDevelopMap.put("activeMerchantNum", 9);
            deviceDevelopList.add(deviceDevelopMap);
            
            deviceDevelopMap = new HashMap<String, Object>();
            deviceDevelopMap.put("areaId", 387);
            deviceDevelopMap.put("areaName", "丽水");
            deviceDevelopMap.put("acceptArriveNum", 1);
            deviceDevelopMap.put("acceptNewNum", 2);
            deviceDevelopMap.put("acceptUnbindeNum", 3);
            deviceDevelopMap.put("apArriveNum", 4);
            deviceDevelopMap.put("apNewNum",5);
            deviceDevelopMap.put("apUnbindNum", 6);
            deviceDevelopMap.put("apActiveNum", 7);
            deviceDevelopMap.put("arriveMerchantNum", 8);
            deviceDevelopMap.put("activeMerchantNum", 9);
            deviceDevelopList.add(deviceDevelopMap);
            
            deviceDevelopMap = new HashMap<String, Object>();
            deviceDevelopMap.put("areaId", 388);
            deviceDevelopMap.put("areaName", "宁波");
            deviceDevelopMap.put("acceptArriveNum", 1);
            deviceDevelopMap.put("acceptNewNum", 2);
            deviceDevelopMap.put("acceptUnbindeNum", 3);
            deviceDevelopMap.put("apArriveNum", 4);
            deviceDevelopMap.put("apNewNum",5);
            deviceDevelopMap.put("apUnbindNum", 6);
            deviceDevelopMap.put("apActiveNum", 7);
            deviceDevelopMap.put("arriveMerchantNum", 8);
            deviceDevelopMap.put("activeMerchantNum", 9);
            deviceDevelopList.add(deviceDevelopMap);
        }else{
            deviceDevelopMap = new HashMap<String, Object>();
            deviceDevelopMap.put("areaId", 3230);
            deviceDevelopMap.put("areaName", "上城区");
            deviceDevelopMap.put("acceptArriveNum", 1);
            deviceDevelopMap.put("acceptNewNum", 0);
            deviceDevelopMap.put("acceptUnbindeNum", 0);
            deviceDevelopMap.put("apArriveNum", 0);
            deviceDevelopMap.put("apNewNum", 0);
            deviceDevelopMap.put("apUnbindNum", 0);
            deviceDevelopMap.put("apActiveNum", 0);
            deviceDevelopMap.put("arriveMerchantNum", 0);
            deviceDevelopMap.put("activeMerchantNum", 0);
            deviceDevelopList.add(deviceDevelopMap);
            
            deviceDevelopMap = new HashMap<String, Object>();
            deviceDevelopMap.put("areaId", 3231);
            deviceDevelopMap.put("areaName", "下城区");
            deviceDevelopMap.put("acceptArriveNum", 0);
            deviceDevelopMap.put("acceptNewNum", 2);
            deviceDevelopMap.put("acceptUnbindeNum", 0);
            deviceDevelopMap.put("apArriveNum", 0);
            deviceDevelopMap.put("apNewNum", 0);
            deviceDevelopMap.put("apUnbindNum", 0);
            deviceDevelopMap.put("apActiveNum", 0);
            deviceDevelopMap.put("arriveMerchantNum", 0);
            deviceDevelopMap.put("activeMerchantNum", 0);
            deviceDevelopList.add(deviceDevelopMap);
            
            deviceDevelopMap = new HashMap<String, Object>();
            deviceDevelopMap.put("areaId", 3232);
            deviceDevelopMap.put("areaName", "拱墅区");
            deviceDevelopMap.put("acceptArriveNum", 0);
            deviceDevelopMap.put("acceptNewNum", 0);
            deviceDevelopMap.put("acceptUnbindeNum", 3);
            deviceDevelopMap.put("apArriveNum", 0);
            deviceDevelopMap.put("apNewNum", 0);
            deviceDevelopMap.put("apUnbindNum", 0);
            deviceDevelopMap.put("apActiveNum", 0);
            deviceDevelopMap.put("arriveMerchantNum", 0);
            deviceDevelopMap.put("activeMerchantNum", 0);
            deviceDevelopList.add(deviceDevelopMap);
            
            deviceDevelopMap = new HashMap<String, Object>();
            deviceDevelopMap.put("areaId", 3233);
            deviceDevelopMap.put("areaName", "滨江区");
            deviceDevelopMap.put("acceptArriveNum", 0);
            deviceDevelopMap.put("acceptNewNum", 0);
            deviceDevelopMap.put("acceptUnbindeNum", 0);
            deviceDevelopMap.put("apArriveNum", 4);
            deviceDevelopMap.put("apNewNum", 0);
            deviceDevelopMap.put("apUnbindNum", 0);
            deviceDevelopMap.put("apActiveNum", 0);
            deviceDevelopMap.put("arriveMerchantNum", 0);
            deviceDevelopMap.put("activeMerchantNum", 0);
            deviceDevelopList.add(deviceDevelopMap);
            
            deviceDevelopMap = new HashMap<String, Object>();
            deviceDevelopMap.put("areaId", 3234);
            deviceDevelopMap.put("areaName", "江干区");
            deviceDevelopMap.put("acceptArriveNum", 0);
            deviceDevelopMap.put("acceptNewNum", 0);
            deviceDevelopMap.put("acceptUnbindeNum", 0);
            deviceDevelopMap.put("apArriveNum", 0);
            deviceDevelopMap.put("apNewNum", 5);
            deviceDevelopMap.put("apUnbindNum", 6);
            deviceDevelopMap.put("apActiveNum", 7);
            deviceDevelopMap.put("arriveMerchantNum", 8);
            deviceDevelopMap.put("activeMerchantNum", 9);
            deviceDevelopList.add(deviceDevelopMap);
        }
        
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("suc", true);
        result.put("msg", "成功");
        result.put("rs", deviceDevelopList);
        return result;
    }

}

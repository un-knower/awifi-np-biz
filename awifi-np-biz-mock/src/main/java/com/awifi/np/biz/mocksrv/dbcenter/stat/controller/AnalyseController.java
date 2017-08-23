/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月25日 上午9:28:11
* 创建作者：王冬冬
* 文件名称：AnalyseController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mocksrv.dbcenter.stat.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
 * 模拟数据中心接口
 * @author 王冬冬
 * 2017年7月25日 下午4:22:16
 */
@Controller
@RequestMapping("/mocksrv/dbc")
public class AnalyseController extends BaseController{
    /**
     * @param params 入参
     * 统计总的认证数据
     * @return map
     * @author 王冬冬
     * @date 2017年1月13日 上午9:41:43
     */
    @ResponseBody
    @RequestMapping(value="/queryAuthCountByParam",method=RequestMethod.GET)
    public Map queryAuthCountByParam(@RequestParam(name="params",required=true) String params){
        Map<String,Object> paramMap=JsonUtil.fromJson(params, Map.class);
        paramMap.get("dayFlagB");
        paramMap.get("dayFlagE");
        paramMap.get("merchantId");
        paramMap.get("merchantQueryType");
        paramMap.get("merchantNameRLike");
        
        paramMap.get("merchantName");
        paramMap.get("province");
        paramMap.get("city");
        paramMap.get("county");
        paramMap.get("industry");
        paramMap.get("industryRLike");
        paramMap.get("merchantProject");
        paramMap.get("globalKey");
        paramMap.get("globalValue");
        paramMap.get("globalStandBy");
        
        Map<String, Object> secResultMap = new LinkedHashMap<String, Object>();
        secResultMap.put("totalVisits", 12);
        secResultMap.put("totalUsers", 3);
        secResultMap.put("totalNewUsers", 22);
        secResultMap.put("totalSuccessVisits", 33);
        secResultMap.put("cumulativeUser", 444);
        secResultMap.put("cumulativeVisits", 777);
        //secResultMap.put("merchantId", 1110);
        secResultMap.put("cumulativeSuccessVisits",777);
        
        Map<String,Object> result=new LinkedHashMap<String,Object>();
        result.put("suc", true);
        result.put("msg", "成功");
        result.put("rs", secResultMap);
        return result;
    }
    
    /**
     * @param params 入参
     * 根据条件查询并商户为维度统计认证数据列表
     * @return map
     * @author 王冬冬
     * @date 2017年1月13日 上午9:41:43
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value="/queryAuthListByParamGroupByMerchantId",method=RequestMethod.GET)
    public Map queryAuthListByParamGroupByMerchantId(@RequestParam(name="params",required=true) String params){
        Map<String,Object> paramMap=JsonUtil.fromJson(params, Map.class);
        paramMap.get("dayFlagB");
        paramMap.get("dayFlagE");
        paramMap.get("merchantId");
        paramMap.get("merchantQueryType");
        paramMap.get("merchantNameRLike");
        paramMap.get("merchantName");
        paramMap.get("province");
        paramMap.get("city");
        paramMap.get("county");
        
        paramMap.get("merchantType");
        paramMap.get("industry");
        paramMap.get("industryRLike");
        paramMap.get("merchantProject");
        paramMap.get("pageNum");
        paramMap.get("pageSize");
        paramMap.get("globalKey");
        paramMap.get("globalValue");
        paramMap.get("globalStandBy");
        
        Map<String, Object> secResultMap = new LinkedHashMap<String, Object>();
        secResultMap.put("merchantId", 127);
        secResultMap.put("merchantName", "");
        secResultMap.put("totalVisits", 12);
        secResultMap.put("totalUsers", 3);
        
        secResultMap.put("totalSuccessUsers", 3);
        secResultMap.put("totalNewUsers", 22);
        secResultMap.put("totalNewSuccessUsers", 3);

        secResultMap.put("totalSuccessVisits", 33);
        secResultMap.put("cumulativeUser", 444);
        secResultMap.put("cumulativeSuccessUser", 777);
        secResultMap.put("cumulativeVisits", 777);
        //secResultMap.put("merchantId", 1110);
        secResultMap.put("cumulativeSuccessVisits",777);
        
        List<Map> arr=new ArrayList<Map>();
        arr.add(secResultMap);
        Map<String,Object> result=new LinkedHashMap<String,Object>();
        result.put("suc", true);
        result.put("msg", "成功");
        result.put("rs", arr);
        return result;
    }
    
    /**
     * @param params 入参
     * 根据条件查询并商户为维度统计认证数据列表记录数
     * @return map
     * @author 王冬冬
     * @date 2017年1月13日 上午9:41:43
     */
    @ResponseBody
    @RequestMapping(value="/queryAuthCountByParamGroupByMerchantId",method=RequestMethod.GET)
    public Map queryAuthCountByParamGroupByMerchantId(@RequestParam(name="params",required=true) String params){
        Map<String,Object> paramMap=JsonUtil.fromJson(params, Map.class);
        paramMap.get("dayFlagB");
        paramMap.get("dayFlagE");
        paramMap.get("merchantId");
        paramMap.get("merchantQueryType");
        paramMap.get("merchantNameRLike");
        
        paramMap.get("merchantName");
        paramMap.get("province");
        paramMap.get("city");
        paramMap.get("county");
        paramMap.get("merchantType");
        paramMap.get("industry");
        paramMap.get("industryRLike");
        paramMap.get("merchantProject");
        paramMap.get("globalKey");
        paramMap.get("globalValue");
        paramMap.get("globalStandBy");
        
        Map<String,Object> result=new LinkedHashMap<String,Object>();
        result.put("suc", true);
        result.put("msg", "成功");
        result.put("rs", 1);
        return result;
    }
    
    
    /**
     * @param params 入参
     * 根据条件查询并商户为维度统计认证数据列表
     * @return map
     * @author 王冬冬
     * @date 2017年1月13日 上午9:41:43
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value="/queryAuthListByParamGroupByTime",method=RequestMethod.GET)
    public Map queryAuthListByParamGroupByTime(@RequestParam(name="params",required=true) String params){
        Map<String,Object> paramMap=JsonUtil.fromJson(params, Map.class);
        paramMap.get("statType");
        paramMap.get("dayFlagB");
        paramMap.get("dayFlagE");
        paramMap.get("merchantId");
        paramMap.get("merchantQueryType");
        paramMap.get("merchantNameRLike");
        paramMap.get("merchantName");
        paramMap.get("province");
        paramMap.get("city");
        paramMap.get("county");
        
        paramMap.get("merchantType");
        paramMap.get("industry");
        paramMap.get("industryRLike");
        paramMap.get("merchantProject");
        paramMap.get("pageNum");
        paramMap.get("pageSize");
        paramMap.get("globalKey");
        paramMap.get("globalValue");
        paramMap.get("globalStandBy");
        
        Map<String, Object> secResultMap = new LinkedHashMap<String, Object>();
        secResultMap.put("dayFlag", "201701");
        secResultMap.put("totalVisits", 12);
        secResultMap.put("totalUsers", 3);
        
        secResultMap.put("totalSuccessUsers", 3);
        secResultMap.put("totalNewUsers", 22);
        secResultMap.put("totalNewSuccessUsers", 3);

        secResultMap.put("totalSuccessVisits", 33);
        secResultMap.put("cumulativeUser", 444);
        secResultMap.put("cumulativeSuccessUser", 777);
        secResultMap.put("cumulativeVisits", 777);
        //secResultMap.put("merchantId", 1110);
        secResultMap.put("cumulativeSuccessVisits",777);
        
        List<Map> arr=new ArrayList<Map>();
        arr.add(secResultMap);
        Map<String,Object> result=new LinkedHashMap<String,Object>();
        result.put("suc", true);
        result.put("msg", "成功");
        result.put("rs", arr);
        return result;
    }
}

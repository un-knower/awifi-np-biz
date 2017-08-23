/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月2日 上午8:52:38
* 创建作者：周颖
* 文件名称：PortalPolicyController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mersrv.portalpolicy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.api.client.dbcenter.portalpolicy.util.PortalPolicyClient;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;

@Controller
public class PortalPolicyController extends BaseController {

    /**
     * 刷新商户无感知配置
     * @param access_token access_token
     * @param params 商户id
     * @return 结果
     * @author 周颖  
     * @throws Exception 
     * @date 2017年5月17日 上午9:44:16
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(method=RequestMethod.GET,value="/mersrv/portalpolicy/switch/status")
    public Map<String, Object> refresh(
            @RequestParam(value="access_token",required=true) String access_token,
            @RequestParam(value="params",required=true)String params) throws Exception{
        Map<String,Object> paramsMap = JsonUtil.fromJson(params, Map.class);//请求参数 json格式
        Long merchantId = CastUtil.toLong(paramsMap.get("merchantId"));//要刷新的商户id
        Map<String, Object> portalPolicy = PortalPolicyClient.getByMerchantId(merchantId);//请求数据中心接口 
        Map<String, Object> result = new HashMap<String,Object>(2);
        if(portalPolicy == null){//没有配无感知 返回没有
            result.put("policySwitch", "OFF");//无感知配置关闭
            return this.successMsg(result);
        }
        Integer unit = CastUtil.toInteger(portalPolicy.get("unit"));//无感知时间单位 0 非无感知 2 天 3 小时
        Integer times = CastUtil.toInteger(portalPolicy.get("times"));//无感知 时间
        if(unit.equals(0) || times == null){//没有配无感知
            result.put("policySwitch", "OFF");
            return this.successMsg(result);
        }else if(unit.equals(3)){//小时
            result.put("policySwitch", "ON");
            result.put("time", times/24);//小时转天
            return this.successMsg(result);
        }else if(unit.equals(2)){//配置的天
            result.put("policySwitch", "ON");//无感知配置开启
            result.put("time", times);
            return this.successMsg(result);
        }
        return this.successMsg(result);
    }
    
    /**
     * 无感知配置
     * @param access_token access_token
     * @param bodyParam 参数
     * @return 结果
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年5月17日 上午10:17:01
     */
    @ResponseBody
    @RequestMapping(method=RequestMethod.PUT,value="/mersrv/portalpolicy/switch",produces="application/json")
    public Map<String, Object> batchSwitch(
            @RequestParam(value="access_token",required=true) String access_token,
            @RequestBody(required=true) List<Map<String,Object>> bodyParam) throws Exception{
        Long merchantId = null;//商户id，数字，不允许为空
        String policySwitch= null;//开关，不允许为空，ON代表开启、OFF代表关闭
        Integer time = null;//当policySwitch==ON时，不允许为空，大于零的整数
        Long id = null;//无感知主键
        Map<String, Object> portalPolicy = null;
        Map<String,Object> merPortalPolicy = new HashMap<String,Object>();
        for(Map<String,Object> map:bodyParam){//循环
            merchantId = CastUtil.toLong(map.get("merchantId"));
            policySwitch = (String) map.get("policySwitch");
            time = CastUtil.toInteger(map.get("time"));
            ValidUtil.valid("商户id[merchantId]", merchantId, "{'required':true,'numeric':true}");
            ValidUtil.valid("开关[policySwitch]", policySwitch, "required");
            if(StringUtils.equals(policySwitch, "ON")){
                ValidUtil.valid("小时[time]", time, "{'required':true,'numeric':true}");
            }
            portalPolicy = PortalPolicyClient.getByMerchantId(merchantId);//请求数据中心接口
            if(portalPolicy == null ){//商户没有配置无感知 
                if(StringUtils.equals(policySwitch, "ON")){//页面请求开启 商户新增无感知
                    merPortalPolicy.put("merchantId", merchantId);
                    merPortalPolicy.put("unit", 2);//单位 天
                    merPortalPolicy.put("times", time);
                    PortalPolicyClient.add(merPortalPolicy);
                }else{//其余 跳到下一条记录
                    continue;
                }
            }else{//商户已经配置了无感知
                id = CastUtil.toLong(portalPolicy.get("id"));
                if(StringUtils.equals(policySwitch, "OFF")){//把原先无感知关闭
                    PortalPolicyClient.delete(id);
                }else{//编辑原无感知
                    merPortalPolicy.put("id", id);//无感知主键
                    merPortalPolicy.put("times", time);//
                    merPortalPolicy.put("merchantId", merchantId);//商户id
                    merPortalPolicy.put("unit", 2);//单位 天
                    PortalPolicyClient.update(merPortalPolicy);
                }
            }
        }
        return this.successMsg();
    }  
}

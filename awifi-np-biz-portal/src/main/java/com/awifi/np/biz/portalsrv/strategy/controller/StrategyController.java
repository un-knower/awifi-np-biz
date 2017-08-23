/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月27日 上午10:57:33
* 创建作者：周颖
* 文件名称：StrategyController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.portalsrv.strategy.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.DateUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.toe.admin.strategy.model.Strategy;
import com.awifi.np.biz.toe.admin.strategy.service.StrategyService;
import com.awifi.np.biz.toe.portal.site.service.SiteService;

@Controller
@RequestMapping("/portalsrv")
public class StrategyController extends BaseController {

    /**策略服务*/
    @Resource(name = "strategyService")
    private StrategyService strategyService;
    
    /**站点服务*/
    @Resource(name = "siteService")
    private SiteService siteService;
    
    /**
     * 策略列表
     * @param accessToken access_token
     * @param params 参数
     * @param request 请求
     * @param siteId 站点id
     * @return 策略
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年4月27日 上午10:59:25
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.GET,value = "/site/{siteid}/strategys")
    @ResponseBody
    public Map<String,Object> getListByParam(
            @RequestParam(value="access_token",required=true)String accessToken,
            @RequestParam(value="params",required=true)String params,
            HttpServletRequest request,
            @PathVariable(name = "siteid",required=true) Long siteId) throws Exception{
        Map<String,Object> paramsMap = JsonUtil.fromJson(params, Map.class);//请求参数 json格式
        Integer pageSize = CastUtil.toInteger(paramsMap.get("pageSize"));//每页记录数
        Integer maxPageSize = Integer.parseInt(SysConfigUtil.getParamValue("page_maxsize"));//每页记录最大数
        ValidUtil.valid("每页数量[pageSize]", pageSize, "{'required':true,'numeric':{'max':"+maxPageSize+"}}");
        String strategyName = (String) paramsMap.get("strategyName");
        Integer pageNo = CastUtil.toInteger(paramsMap.get("pageNo"));//页码
        if(pageNo == null){//如果为空，默认第一页
            pageNo = 1;
        }
        Page<Strategy> page = new Page<Strategy>(pageNo,pageSize);
        strategyService.getListByParam(page,siteId,strategyName);
        return this.successMsg(page);
    }
    
    /**
     * 添加策略
     * @param accessToken access_token
     * @param bodyParam 参数
     * @param siteId 站点id
     * @return 结果
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年4月28日 下午2:03:06
     */
    @RequestMapping(method = RequestMethod.POST,value = "/site/{siteid}/strategy",produces="application/json")
    @ResponseBody
    public Map<String,Object> add(
            @RequestParam(value="access_token",required=true)String accessToken,
            @RequestBody(required=true) Map<String,Object> bodyParam,
            @PathVariable(name="siteid",required=true) Long siteId) throws Exception{
        String strategyName = (String) bodyParam.get("strategyName");//站点名称，不允许为空，1-50位汉字，字母，下划线、数字，不含特殊字符！
        String startDate = (String) bodyParam.get("startDate");//策略开始日期，不允许为空，格式为：yyyy-MM-dd
        String endDate = (String) bodyParam.get("endDate");//策略截止日期，不允许为空，格式为：yyyy-MM-dd
        Integer strategyType = CastUtil.toInteger(bodyParam.get("strategyType"));//策略类型，数字，不允许为空，其中：1代表全部 、2代表SSID、3代表设备id
        String content = (String) bodyParam.get("content");//策略内容 strategyType=1允许为空 其余必填
        //参数校验
        ValidUtil.valid("策略名称[strategyName]", strategyName, "{'required':true,'regex':'"+RegexConstants.STRATEGY_NAME_PATTERN+"'}");
        ValidUtil.valid("开始日期[startDate]", startDate, "required");
        ValidUtil.valid("截止日期[endDate]", endDate, "required");
        ValidUtil.valid("策略类型[strategyType]", strategyType, "{'required':true,'numeric':{'min':1,'max':3}}");
        if(!strategyType.equals(1)){
            ValidUtil.valid("策略内容[content]", content, "required");
        }
        if(strategyService.isStrategyNameExist(siteId,strategyName)){//策略名称重复
            throw new BizException("E2600007", MessageUtil.getMessage("E2600007",strategyName));//策略名称（strategyName[{0}]）已存在!
        }
        Strategy strategy = new Strategy();
        strategy.setStrategyName(strategyName);
        strategy.setStrategyType(strategyType);
        strategy.setSiteId(siteId);
        Long startTimestamp = DateUtil.getTimestampMills(startDate + " 00:00:00");
        strategy.setStartDate(startTimestamp.toString());
        Long endTimestamp = DateUtil.getTimestampMills(endDate + " 23:59:59");
        strategy.setEndDate(endTimestamp.toString());
        Map<String,Object> merchantMap = siteService.getMerchantById(siteId);
        strategy.setMerchantId((Long)merchantMap.get("merchantId"));
        strategy.setCascadeLabel((String)merchantMap.get("cascadeLabel"));
        Integer maxOrderNo = strategyService.getMaxNo(siteId);
        strategy.setOrderNo(maxOrderNo + 1);
        strategyService.add(strategy,content);
        return this.successMsg();
    }
    
    /**
     * 编辑策略
     * @param accessToken access_token
     * @param bodyParam 参数
     * @param siteId 站点id
     * @param strategyId 策略id
     * @return 结果
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年4月28日 下午3:14:14
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/site/{siteid}/strategy/{strategyid}",produces="application/json")
    @ResponseBody
    public Map<String,Object> update(
            @RequestParam(value="access_token",required=true)String accessToken,
            @RequestBody(required=true) Map<String,Object> bodyParam,
            @PathVariable(name="siteid",required=true) Long siteId,
            @PathVariable(name="strategyid",required=true)Long strategyId) throws Exception{
        String strategyName = (String) bodyParam.get("strategyName");//站点名称，不允许为空，1-50位汉字，字母，下划线、数字，不含特殊字符！
        String startDate = (String) bodyParam.get("startDate");//策略开始日期，不允许为空，格式为：yyyy-MM-dd，不能大于截止日期
        String endDate = (String) bodyParam.get("endDate");//策略截止日期，不允许为空，格式为：yyyy-MM-dd，不能小于开始日期
        Integer strategyType = CastUtil.toInteger(bodyParam.get("strategyType"));//策略类型，数字，不允许为空，其中：1代表全部 、2代表SSID、3代表设备id
        String content = (String) bodyParam.get("content");//策略内容 strategyType=1允许为空 其余必填
        //参数校验
        ValidUtil.valid("策略名称[strategyName]", strategyName, "{'required':true,'regex':'"+RegexConstants.STRATEGY_NAME_PATTERN+"'}");
        ValidUtil.valid("开始日期[startDate]", startDate, "required");
        ValidUtil.valid("截止日期[endDate]", endDate, "required");
        ValidUtil.valid("策略类型[strategyType]", strategyType, "{'required':true,'numeric':{'min':1,'max':3}}");
        if(!strategyType.equals(1)){
            ValidUtil.valid("策略内容[content]", content, "required");
        }
        if(strategyService.isStrategyNameExist(siteId,strategyId,strategyName)){//策略名称重复
            throw new BizException("E2600007", MessageUtil.getMessage("E2600007",strategyName));//策略名称（strategyName[{0}]）已存在!
        }
        Strategy strategy = new Strategy();
        strategy.setId(strategyId);
        strategy.setStrategyName(strategyName);
        strategy.setStrategyType(strategyType);
        Long startTimestamp = DateUtil.getTimestampMills(startDate + " 00:00:00");
        strategy.setStartDate(startTimestamp.toString());
        Long endTimestamp = DateUtil.getTimestampMills(endDate + " 23:59:59");
        strategy.setEndDate(endTimestamp.toString());
        strategyService.update(strategy,content);
        return this.successMsg();
    }
    
    /**
     * 站点下最新策略
     * @param accessToken access_token
     * @param siteId 站点id
     * @return 最新策略
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年5月2日 上午10:05:11
     */
    @RequestMapping(method = RequestMethod.GET,value = "/site/{siteid}/strategy")
    @ResponseBody
    public Map<String,Object> getBySiteId(
            @RequestParam(value="access_token",required=true)String accessToken,
            @PathVariable(name="siteid",required=true) Long siteId) throws Exception{
        Strategy strategy = strategyService.getBySiteId(siteId);
        return this.successMsg(strategy);
    }
    
    /**
     * 策略详情
     * @param accessToken access_token
     * @param id 策略id
     * @return 详情
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年5月2日 上午10:33:14
     */
    @RequestMapping(method = RequestMethod.GET,value = "/strategy/{id}")
    @ResponseBody
    public Map<String,Object> getById(
            @RequestParam(value="access_token",required=true)String accessToken,
            @PathVariable(name="id",required=true) Long id) throws Exception{
        Strategy strategy = strategyService.getById(id);
        return this.successMsg(strategy);
    }
    
    /**
     * 策略删除
     * @param accessToken access_token
     * @param id 策略id
     * @return 结果
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年5月2日 上午10:38:06
     */
    @RequestMapping(method = RequestMethod.DELETE,value = "/strategy/{id}")
    @ResponseBody
    public Map<String,Object> delete(
            @RequestParam(value="access_token",required=true)String accessToken,
            @PathVariable(name="id",required=true) Long id) throws Exception{
        strategyService.delete(id);
        return this.successMsg();
    }
    
    /**
     * 策略优先级调整
     * @param accessToken access_token
     * @param bodyParam 参数
     * @param id 主键id
     * @return 结果
     * @author 周颖  
     * @date 2017年5月2日 上午11:24:16
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/strategy/{id}/priority",produces="application/json")
    @ResponseBody
    public Map<String,Object> priority(
            @RequestParam(value="access_token",required=true)String accessToken,
            @RequestBody(required=true) Map<String,Object> bodyParam,
            @PathVariable(name="id",required=true) Long id){
        strategyService.priority(id,bodyParam);
        return this.successMsg();
    }
}

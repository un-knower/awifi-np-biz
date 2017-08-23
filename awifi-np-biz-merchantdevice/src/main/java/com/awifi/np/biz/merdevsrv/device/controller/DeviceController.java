package com.awifi.np.biz.merdevsrv.device.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.template.service.TemplateService;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.ExcelUtil;
import com.awifi.np.biz.common.util.FormatUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.merdevsrv.device.service.DeviceService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月3日 上午11:24:29
 * 创建作者：亢燕翔
 * 文件名称：DeviceController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings({"rawtypes","unchecked"})
@Controller
@RequestMapping("/merdevsrv")
public class DeviceController extends BaseController{

    /**设备业务层*/
    @Resource(name = "deviceService")
    private DeviceService deviceService;
    
    /**模板服务*/
    @Resource(name = "templateService")
    private TemplateService templateService;
    
    /**
     * 商户设备显示接口
     * @param request 请求
     * @param templatecode page(pan)编号
     * @param access_token 安全令牌
     * @return map
     * @author 亢燕翔  
     * @date 2017年2月3日 上午11:07:47
     */
    @ResponseBody
    @RequestMapping(value="/view/{templatecode}", method=RequestMethod.GET)
    public Map view(HttpServletRequest request, @PathVariable String templatecode, @RequestParam(value="access_token",required=true) String access_token){
        String serviceCode = SysConfigUtil.getParamValue("servicecode_merchantdevice");//从配置表读取服务代码
        String suitCode = SessionUtil.getCurSessionUser(request).getSuitCode();//从session中获取套码
        if(StringUtils.isBlank(suitCode)){//套码未找到抛出异常
            throw new ValidException("E2000005", MessageUtil.getMessage("E2000005"));//套码不允许为空!
        }
        String template = templateService.getByCode(suitCode,serviceCode,templatecode);//获取模板页面
        return this.successMsg(template);
    }
    
    /**
     * 设备分页列表
     * @param request 请求
     * @param access_token 安全令牌 
     * @param params 请求参数
     * @return map 
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月3日 上午11:31:02
     */
    @ResponseBody
    @RequestMapping(value="/devices", method=RequestMethod.GET)
    public Map getListByParam(HttpServletRequest request,@RequestParam(value="access_token",required=true) String access_token,
            @RequestParam(name="params",required=true) String params) throws Exception{
        Page<Device> page = new Page<Device>();
        //Long orgId = OrgUtil.getCurOrgId(request);
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        deviceService.getListByParam(sessionUser,params,page);
        return this.successMsg(page);
    }
    
    /**
     * 设备绑定（归属）
     * @param request 请求
     * @param response 响应
     * @param access_token 安全令牌
     * @param params 请求参数
     * @return map
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月6日 上午8:50:37
     */
    @ResponseBody
    @RequestMapping(value="/owner", method=RequestMethod.POST)
    public Map setOwner(HttpServletRequest request, HttpServletResponse response,@RequestParam(value="access_token",required=true) String access_token,
            @RequestParam(name="params",required=true) String params) throws Exception{
        /*接收参数*/
        Map<String, Object> paramsMap = JsonUtil.fromJson(params, Map.class);
        Long projectId = CastUtil.toLong(paramsMap.get("projectId"));//项目id
        Long merchantId = CastUtil.toLong(paramsMap.get("merchantId"));//商户id
        String entityType = (String) paramsMap.get("entityType");//设备类型
        
        Integer isSFInteger = CastUtil.toInteger(paramsMap.get("isSF"));
        Boolean isSF = isSFInteger != null ? isSFInteger.equals(1) : false;//0：非省分 1：省分
        Long provinceId = CastUtil.toLong(paramsMap.get("provinceId"));//省id
        
        /*数据校验*/
        ValidUtil.valid("项目id[projectId]", projectId, "{'required':true,'numeric':true}");
        ValidUtil.valid("商户id[merchantId]", merchantId, "{'required':true,'numeric':true}");
        ValidUtil.valid("设备类型[entityType]", entityType, "required");
        if(isSF){//当isSF为true时，provinceId不允许为空，其它情况，允许为空，用于省分平台设备绑定
            ValidUtil.valid("省id[provinceId]", provinceId, "{'required':true,'numeric':true}");
        }
        /*获取sheet*/
        Sheet sheet = ExcelUtil.fileResolver(request);
        int lastRowNum = sheet.getLastRowNum();//最后一行
        String belongTo = FormatUtil.formatBelongToByProjectId(projectId.intValue());//通过项目id获取对应的belongTo
        /*依据设备类型分别进行数据录入*/
        if(entityType.equals("fat")){//代表胖AP
            deviceService.importFat(sheet,belongTo,entityType.toUpperCase(),merchantId,projectId,isSF,provinceId,lastRowNum);
        } else if (entityType.equals("fit")){
            deviceService.importFit(sheet,belongTo,entityType.toUpperCase(),merchantId,projectId,lastRowNum);
        } else if (entityType.equals("nas")){
            deviceService.importNas(sheet,belongTo,entityType.toUpperCase(),merchantId,projectId,lastRowNum);
        } else if (entityType.equals("hotarea")){
            deviceService.importHotarea(sheet,belongTo,entityType.toUpperCase(),merchantId,projectId,lastRowNum);
        } else {
            throw new BizException("E2400001", MessageUtil.getMessage("E2400001",entityType));//entityType[{0}]超出了范围!
        }
        return this.successMsg();
    }
    
    /**
     * 设备过户
     * @param request 请求
     * @param access_token 安全令牌
     * @param bodyParam 请求体参数
     * @return map
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月7日 下午2:16:54
     */
    @ResponseBody
    @RequestMapping(value="/owner", method=RequestMethod.PUT)
    public Map transfer(HttpServletRequest request,@RequestParam(value="access_token",required=true) String access_token,
            @RequestBody(required=true) Map<String,Object> bodyParam) throws Exception{
        deviceService.transfer(bodyParam);
        return this.successMsg();
    }
    
    /**
     * 设备放通
     * @param request 请求
     * @param access_token 安全令牌
     * @param bodyParam 请求体数据
     * @return map
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年4月17日 上午11:14:17
     */
    @ResponseBody
    @RequestMapping(value="/merchants/switch/escape", method=RequestMethod.PUT, produces="application/json")
    public Map batchEscape(HttpServletRequest request,@RequestParam(value="access_token",required=true) String access_token,@RequestBody(required=true) List<Map<String,Object>> bodyParam) throws Exception{
    	for(Map<String,Object> map:bodyParam){
            ValidUtil.valid("商户id[merchantId]", map.get("merchantId"), "{'required':true,'numeric':true}");
            ValidUtil.valid("设备开关[onekeySwitch]", map.get("devSwitch"), "{'required':true}");
        }
    	
    	deviceService.batchEscape(bodyParam);//将胖ap设备放通
        return this.successMsg();
    }
    
    
    /**
     * 开关刷新
     * @param request 请求
     * @param access_token 令牌
     * @param id 商户id 
     * @param params 请求参数
     * @return json
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年5月4日 上午10:50:41
     */
    @ResponseBody
    @RequestMapping(value="/merchant/{id}/switch/status", method=RequestMethod.GET, produces="application/json")
    public Map getSwitchStatus(HttpServletRequest request,@RequestParam(value="access_token",required=true) String access_token,@PathVariable String id,@RequestParam(name="params",required=true) String params) throws Exception{
        Map<String, Object> paramsMap = JsonUtil.fromJson(params, Map.class);
        String switchType = (String)paramsMap.get("switchType");//开关类型
        ValidUtil.valid("商户id[merchantId]", id, "{'required':true,'numeric':true}"); 
        ValidUtil.valid("开关类型[switchType]",switchType, "{'required':true}"); 
        Map<String,Object> result= deviceService.getSwitchStatus(CastUtil.toLong(id),switchType);
        return this.successMsg(result);
    }
    /**
     * chinanet开关
     * @param request 请求
     * @param access_token 安全令牌
     * @param bodyParam 请求体数据
     * @return map
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年4月17日 上午11:14:17
     */
    @ResponseBody
    @RequestMapping(value="/merchants/switch/chinanet", method=RequestMethod.PUT, produces="application/json")
    public Map batchChinanet(HttpServletRequest request,@RequestParam(value="access_token",required=true) String access_token,@RequestBody(required=true) List<Map<String,Object>> bodyParam) throws Exception{
    	for(Map<String,Object> map:bodyParam){
            ValidUtil.valid("商户id[merchantId]", map.get("merchantId"), "{'required':true,'numeric':true}");
            ValidUtil.valid("chinanet开关[chinaNetSwitch]", map.get("devSwitch"), "{'required':true}");
        }
    	
    	deviceService.batchChinanetSsidSwitch(bodyParam);//将chinanet放通
        return this.successMsg();
    }
    
    
    /**
     * awifi开关
     * @param request 请求
     * @param access_token 安全令牌
     * @param bodyParam 请求体数据
     * @return map
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年4月17日 上午11:14:17
     */
    @ResponseBody
    @RequestMapping(value="/merchants/switch/awifi", method=RequestMethod.PUT, produces="application/json")
    public Map batchAwifi(HttpServletRequest request,@RequestParam(value="access_token",required=true) String access_token,@RequestBody(required=true) List<Map<String,Object>> bodyParam) throws Exception{
    	for(Map<String,Object> map:bodyParam){
            ValidUtil.valid("商户id[merchantId]", map.get("merchantId"), "{'required':true,'numeric':true}");
            ValidUtil.valid("设备开关[awifiSwitch]", map.get("devSwitch"), "{'required':true}");
        }
    	
    	deviceService.batchAwifiSsidSwitch(bodyParam);//将胖ap设备放通
        return this.successMsg();
    }
    
    
    /**
     * LAN口
     * @param request 请求
     * @param access_token 安全令牌
     * @param bodyParam 请求体数据
     * @return map
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年4月17日 上午11:14:17
     */
    @ResponseBody
    @RequestMapping(value="/merchants/switch/lan", method=RequestMethod.PUT, produces="application/json")
    public Map batchLan(HttpServletRequest request,@RequestParam(value="access_token",required=true) String access_token,@RequestBody(required=true) List<Map<String,Object>> bodyParam) throws Exception{
    	for(Map<String,Object> map:bodyParam){
            ValidUtil.valid("商户id[merchantId]", map.get("merchantId"), "{'required':true,'numeric':true}");
            ValidUtil.valid("lan开关[lanSwitch]", map.get("devSwitch"), "{'required':true}");
        }
    	
    	deviceService.batchLanSwitch(bodyParam);//将胖ap设备放通
        return this.successMsg();
    }
    
    /**
     * 闲时下线
     * @param request 请求
     * @param access_token 安全令牌
     * @param bodyParam 请求体数据
     * @return map
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年4月17日 上午11:14:17
     */
    @ResponseBody
    @RequestMapping(value="/merchants/switch/timeout", method=RequestMethod.PUT, produces="application/json")
    public Map batchTimeout(HttpServletRequest request,@RequestParam(value="access_token",required=true) String access_token,@RequestBody(required=true) List<Map<String,Object>> bodyParam) throws Exception{
    	for(Map<String,Object> map:bodyParam){
            ValidUtil.valid("商户id[merchantId]", map.get("merchantId"), "{'required':true,'numeric':true}");
            //参数校验
            ValidUtil.valid("闲时下线时间[offlineTime]", map.get("offlineTime"), "required");
        }
    	
    	deviceService.batchClientTimeout(bodyParam);//将胖ap设备放通
        return this.successMsg();
    }
    
    
    
    /**
     * 商户设备批量绑定
     * @param request 请求
     * @param response 响应
     * @param access_token 安全令牌
     * @param params 请求参数
     * @return map
     * @author 王冬冬
     * @throws Exception 
     * @date 2017年2月6日 上午8:50:37
     */
    @ResponseBody
    @RequestMapping(value="/owners", method=RequestMethod.POST)
    public Map batchBind(HttpServletRequest request, HttpServletResponse response,@RequestParam(value="access_token",required=true) String access_token,
            @RequestParam(name="params",required=true) String params) throws Exception{
        //todo
        /*接收参数*/
        Map<String, Object> paramsMap = JsonUtil.fromJson(params, Map.class);
        Object projectIdObj = paramsMap.get("projectId");//项目id
        String entityType = (String) paramsMap.get("entityType");//设备类型
        Object roleIdObj = paramsMap.get("roleId");//角色id
        
        /*数据校验*/
        ValidUtil.valid("项目id[projectId]", projectIdObj, "{'required':true,'numeric':true}");
        ValidUtil.valid("角色id[roleId]", roleIdObj, "{'required':true,'numeric':true}");
        ValidUtil.valid("设备类型[entityType]", entityType, "required");
        
        Long projectId = CastUtil.toLong(projectIdObj);//项目id
        Long roleId=CastUtil.toLong(paramsMap.get("roleId"));//角色id
        
        /*获取sheet*/
        Sheet sheet = ExcelUtil.fileResolver(request);
        int lastRowNum = sheet.getLastRowNum();//最后一行
        String belongTo = FormatUtil.formatBelongToByProjectId(projectId.intValue());//通过项目id获取对应的belongTo
        SessionUser curUser = SessionUtil.getCurSessionUser(request);
        /*依据设备类型分别进行数据录入*/
        if(entityType.equals("fat")){//代表胖AP
            ExcelUtil.cmpTemplateExcel(sheet, ExcelUtil.FATAP_EXCELCOLUMNS);
            deviceService.importFat(sheet,belongTo,entityType.toUpperCase(),projectId,roleId,lastRowNum,curUser);
        } else if (entityType.equals("fit")){
            ExcelUtil.cmpTemplateExcel(sheet, ExcelUtil.FITAP_EXCELCOLUMNS);
            deviceService.importFitDev(sheet,belongTo,entityType.toUpperCase(),projectId,roleId,lastRowNum,curUser);
        } else if (entityType.equals("nas")){
            ExcelUtil.cmpTemplateExcel(sheet, ExcelUtil.NAS_EXCELCOLUMNS);
            deviceService.importACBasDev(sheet,belongTo,entityType.toUpperCase(),projectId,roleId,lastRowNum,curUser);
        } else if (entityType.equals("hotarea")){
            ExcelUtil.cmpTemplateExcel(sheet, ExcelUtil.HOTAREA_EXCELCOLUMNS);
            deviceService.importHotareaDev(sheet,belongTo,entityType.toUpperCase(),projectId,roleId,lastRowNum,curUser);
        } else {
            throw new BizException("E2400001", MessageUtil.getMessage("E2400001",entityType));//entityType[{0}]超出了范围!
        }
        return this.successMsg();
    }
    
    
    /**
     * 防蹭网
     * @param request 请求
     * @param access_token 安全令牌
     * @param bodyParam 请求体数据
     * @return map
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年4月17日 上午11:14:17
     */
//    @ResponseBody
//    @RequestMapping(value="/merchants/switch/antirobber", method=RequestMethod.PUT, produces="application/json")
//    public Map batchantiRobber(HttpServletRequest request,@RequestParam(value="access_token",required=true) String access_token,@RequestBody(required=true) List<Map<String,Object>> bodyParam) throws Exception{
//        for(Map<String,Object> map:bodyParam){
//            ValidUtil.valid("商户id[merchantId]", map.get("merchantId"), "{'required':true,'numeric':true}");
//            //参数校验
//            ValidUtil.valid("防蹭网开关[status]", map.get("status"), "required");
//        }
//        
//        deviceService.batchAntiRobber(bodyParam);//
//        return this.successMsg();
//    }
    /**
     * 通过商户id获取设备名称、ssid集合（添加策略下拉）
     * @param access_token access_token
     * @param merchantId 商户id
     * @return 结果
     * @author 周颖  
     * @throws Exception 
     * @date 2017年5月3日 下午2:01:41
     */
    @RequestMapping(method=RequestMethod.GET,value="/merchant/{merchantid}/devinfo")
    @ResponseBody
    public Map<String,Object> getDevInfoByMerchantId(
            @RequestParam(value="access_token",required=true) String access_token,
            @PathVariable(value="merchantid",required=true)Long merchantId) throws Exception{
        Map<String,Object> devInfoMap = deviceService.getDevInfoByMerchantId(merchantId);
        return this.successMsg(devInfoMap);
    }
}

package com.awifi.np.biz.appsrv.app.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.appsrv.app.model.App;
import com.awifi.np.biz.appsrv.app.service.AppService;
import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.template.service.TemplateService;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.KeyUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.RequestParamUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.toe.admin.thirdapp.microshop.service.MicroshopService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017年7月10日 下午2:28:40
 * 创建作者：许尚敏
 * 文件名称：AppController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Controller
@RequestMapping("/appsrv")
@SuppressWarnings({"rawtypes","unchecked"})
public class AppController extends BaseController {
    
    /** 应用管理—应用添加服务 */
    @Resource(name = "appService")
    private AppService appService;
    
    /**模板服务*/
    @Resource(name = "templateService")
    private TemplateService templateService;
    
    /** 微旺铺业务层 */
    @Resource(name = "microshopService")
    private MicroshopService microShopService;
    
    /**
     * app应用显示接口
     * @param request 请求
     * @param templatecode page(pan)编号
     * @param access_token 安全令牌
     * @return map
     * @author 王冬冬
     * @date 2017年2月3日 上午11:07:47
     */
    @ResponseBody
    @RequestMapping(value="/view/{templatecode}", method=RequestMethod.GET)
    public Map view(HttpServletRequest request, @PathVariable String templatecode, @RequestParam(value="access_token",required=true) String access_token){
        String serviceCode = SysConfigUtil.getParamValue("servicecode_app");//从配置表读取服务代码
        String suitCode = SessionUtil.getCurSessionUser(request).getSuitCode();//从session中获取套码
        if(StringUtils.isBlank(suitCode)){//套码未找到抛出异常
            throw new ValidException("E2000005", MessageUtil.getMessage("E2000005"));//套码不允许为空!
        }
        String template = templateService.getByCode(suitCode,serviceCode,templatecode);//获取模板页面
        return this.successMsg(template);
    }
    
    /**
     * 获取第三方单点登录url
     * @param request 请求
     * @param response 响应
     * @param access_token 令牌
     * @param params 请求参数
     * @return json
     * @author 王冬冬  
     * @throws Exception 异常
     * @date 2017年7月11日 上午10:19:33
     */
    @RequestMapping(value = "/app/sso/url",method=RequestMethod.GET)
    @ResponseBody
    public Map getSsoUrl(HttpServletRequest request, HttpServletResponse response,@RequestParam(value="access_token",required=true) String access_token,@RequestParam(name="params",required=true) String params) throws Exception {
        Map<String,Object> paramsMap = JsonUtil.fromJson(params, Map.class);//请求参数 json格式
        SessionUser user = SessionUtil.getCurSessionUser(request);//获得user
        Long merchantId = user.getMerchantId();
        if (merchantId == null) {
            throw new BizException("E2900001", MessageUtil.getMessage("E2900001"));// 当前账号缺少商户信息，无法登录至第三方应用！
        }
        String appId = (String) paramsMap.get("appId");//获取appId
        ValidUtil.valid("appId", appId, "required");//验证appid
        Map<String,String> result = appService.getSsoUrl(merchantId, appId);//获取第三方单点登录url
        String url = result.get("url");//第三方单点登录url
        logger.debug("提示：单点登录地址= " + url);
        if(StringUtils.isNotBlank(url)){
            response.sendRedirect(url);//302 重定向
        }
        return this.successMsg(result);
    }
    
    /** 应用管理—应用添加接口.
     * @param bodyParam  参数
     * @param access_token  访问令牌
     * @return 结果
     * @throws Exception 异常
     * @author 许尚敏 
     * @date 2017年7月10日 下午3:00:31
     */
    @RequestMapping(method=RequestMethod.POST,value="/app", produces="application/json")
    @ResponseBody
    public Map add(@RequestParam(value="access_token",required=true) String access_token, 
            @RequestBody(required=true) Map<String, Object> bodyParam) throws Exception{
        String appName = (String) bodyParam.get("appName");//应用名称，不允许为空
        String appParam = (String) bodyParam.get("appParam");//应用参数，允许为空
        String company = (String) bodyParam.get("company");//公司名称，不允许为空
        String businessLicense = (String) bodyParam.get("businessLicense");//营业执照，不允许为空
        String contactPerson = (String) bodyParam.get("contactPerson");//联系人，不允许为空
        String contactWay = (String) bodyParam.get("contactWay");//联系方式，不允许为空
        Integer status = CastUtil.toInteger(bodyParam.get("status"));//状态，不允许为空
        String remark = (String) bodyParam.get("remark");//备注（应用描述），不允许为空
        logger.debug("appName=" + appName + "---appParam=" + appParam);
        ValidUtil.valid("应用名称[appName]", appName, "{'regex':'" + RegexConstants.getStringPattern(1, 30) + "'}");//校验应用名称，不允许为空，30位字符
        if(appParam!=null && appParam.length()>2000){//字符串长度超过2000
            throw new ValidException("E2000016", MessageUtil.getMessage("E2000016", "应用参数[appParam]"));
        }
        ValidUtil.valid("公司名称[company]", company, "{'regex':'" + RegexConstants.getStringPattern(1, 50) + "'}");//校验公司名称，不允许为空，50位字符
        ValidUtil.valid("营业执照[businessLicense]", businessLicense, "{'regex':'" + RegexConstants.getStringPattern(1, 50) + "'}");//校验营业执照，不允许为空，50位字符
        ValidUtil.valid("联系人[contactPerson]", contactPerson, "{'regex':'" + RegexConstants.getStringPattern(1, 20) + "'}");//校验联系人，不允许为空，20位字符
        ValidUtil.valid("联系方式[contactWay]", contactWay, "{'regex':'" + RegexConstants.getStringPattern(1, 30) + "'}");//校验联系方式 ，不允许为空，30位字符
        ValidUtil.valid("状态[status]", status, "{'numeric':{'min':1,'max':2}}");//校验状态，其中：1代表启用、2代表禁用
        ValidUtil.valid("备注[remark]", remark, "required");//校验备注，不允许为空，100位字符
        if(remark!=null && remark.length()>100){//字符串长度超过100
            throw new ValidException("E2000016", MessageUtil.getMessage("E2000016", "备注[remark]"));
        }
        App app = new App();//创建应用实体
        app.setAppId(KeyUtil.generateAppId());//设置appId
        app.setAppKey(KeyUtil.generateAppKey());//设置appKey
        app.setAppName(appName);//设置应用名称
        app.setAppParam(appParam);//设置应用参数
        app.setCompany(company);//设置公司名称
        app.setBusinessLicense(businessLicense);//设置营业执照
        app.setContactPerson(contactPerson);//设置联系人
        app.setContactWay(contactWay);//设置联系方式
        app.setStatus(status);//设置状态
        app.setRemark(remark);//设置备注
        appService.add(app);//添加应用
        return this.successMsg();
    }
    
    /** 应用管理—应用编辑接口.
     * @param id  编号
     * @param access_token  访问令牌
     * @param bodyParam  参数
     * @return 结果
     * @throws Exception 异常
     * @author 许尚敏 
     * @date 2017年7月11日 上午11:12:11
     */
    @RequestMapping(method=RequestMethod.PUT,value="/app/{id}", produces="application/json")
    @ResponseBody
    public Map update(@RequestParam(value="access_token",required=true) String access_token, 
            @PathVariable(value="id",required=true) Long id,
            @RequestBody(required=true) Map<String, Object> bodyParam) throws Exception{
        String appName = (String) bodyParam.get("appName");//应用名称，不允许为空
        String appParam = (String) bodyParam.get("appParam");//应用参数，允许为空
        String company = (String) bodyParam.get("company");//公司名称，不允许为空
        String businessLicense = (String) bodyParam.get("businessLicense");//营业执照，不允许为空
        String contactPerson = (String) bodyParam.get("contactPerson");//联系人，不允许为空
        String contactWay = (String) bodyParam.get("contactWay");//联系方式，不允许为空
        Integer status = CastUtil.toInteger(bodyParam.get("status"));//状态，不允许为空
        String remark = (String) bodyParam.get("remark");//备注（应用描述），不允许为空
        ValidUtil.valid("应用名称[appName]", appName, "{'regex':'" + RegexConstants.getStringPattern(1, 30) + "'}");//校验应用名称，不允许为空，30位字符
        if(appParam!=null && appParam.length()>2000){//字符串长度超过2000
            throw new ValidException("E2000016", MessageUtil.getMessage("E2000016", "应用参数[appParam]"));
        }
        ValidUtil.valid("公司名称[company]", company, "{'regex':'" + RegexConstants.getStringPattern(1, 50) + "'}");//校验公司名称，不允许为空，50位字符
        ValidUtil.valid("营业执照[businessLicense]", businessLicense, "{'regex':'" + RegexConstants.getStringPattern(1, 50) + "'}");//校验营业执照，不允许为空，50位字符
        ValidUtil.valid("联系人[contactPerson]", contactPerson, "{'regex':'" + RegexConstants.getStringPattern(1, 20) + "'}");//校验联系人，不允许为空，20位字符
        ValidUtil.valid("联系方式[contactWay]", contactWay, "{'regex':'" + RegexConstants.getStringPattern(1, 30) + "'}");//校验联系方式 ，不允许为空，30位字符
        ValidUtil.valid("状态[status]", status, "{'numeric':{'min':1,'max':2}}");//校验状态，其中：1代表启用、2代表禁用
        ValidUtil.valid("备注[remark]", remark, "required");//校验备注，不允许为空，100位字符
        if(remark!=null && remark.length()>100){//字符串长度超过100
            throw new ValidException("E2000016", MessageUtil.getMessage("E2000016", "备注[remark]"));
        }
        App app = new App();//创建应用实体
        app.setId(id);//设置Id
        app.setAppId(KeyUtil.generateAppId());//设置appId
        app.setAppKey(KeyUtil.generateAppKey());//设置appKey
        app.setAppName(appName);//设置应用名称
        app.setAppParam(appParam);//设置应用参数
        app.setCompany(company);//设置公司名称
        app.setBusinessLicense(businessLicense);//设置营业执照
        app.setContactPerson(contactPerson);//设置联系人
        app.setContactWay(contactWay);//设置联系方式
        app.setStatus(status);//设置状态
        app.setRemark(remark);//设置备注
        appService.update(app);//编辑应用
        return this.successMsg();//返回结果
    }
    
    /**
     * 应用管理--分页查询接口
     * @param request 请求    
     * @param access_token access_token
     * @param params 参数
     * @return 
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jul 10, 2017 3:08:22 PM
     * @return 结果
     */
    @ResponseBody
    @RequestMapping(value="/apps", method=RequestMethod.GET)
    public Map<String,Object> getListByParam(HttpServletRequest request,@RequestParam(value="access_token",required=true) String access_token,
            @RequestParam(name="params",required=true) String params) throws Exception{
        Map<String,Object> paramsMap = JsonUtil.fromJson(params, Map.class);//请求参数 json格式
        String appName = (String)paramsMap.get("appName");//获取应用名称，允许为空
        Integer status = CastUtil.toInteger(paramsMap.get("status"));//获取状态，允许为空 
        Integer pageNo = RequestParamUtil.getPageNo(paramsMap);//页码
        Integer pageSize = RequestParamUtil.getPageSize(paramsMap);//每页记录数
        Integer maxPageSize = Integer.parseInt(SysConfigUtil.getParamValue("page_maxsize"));//每页记录最大数
        ValidUtil.valid("每页数量[pageSize]", pageSize, "{'required':true,'numeric':{'max':"+maxPageSize+"}}");//校验每页记录最大数
        
        if(pageNo == null){//如果为空，默认第一页
            pageNo = 1;
        }
        
        Page<App> page = new Page<App>(pageNo,pageSize);  //新建page
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request); //获取请求权限
        appService.getListByParam(sessionUser, page, appName, status);//查询应用信息
        return this.successMsg(page);//返回查询结果
    }
    
    /**
     * 应用管理--应用列表-详情接口
     * @param access_token access_token
     * @param id 应用id
     * @return 应用详情
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jul 12, 2017 10:03:51 AM
     */
    @RequestMapping(value="/app/{id}",method=RequestMethod.GET, produces="application/json")
    @ResponseBody
    public Map<String,Object> getById(
            @RequestParam(value="access_token",required=true) String access_token,
            @PathVariable Long id) throws Exception {
        App app = appService.getById(id);//查询应用详情
        return this.successMsg(app);//返回查询结果
    }
    
    /**
     * 应用管理--应用列表-删除接口
     * @param access_token access_token
     * @param id 应用id
     * @return 结果
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jul 13, 2017 5:57:35 PM
     */
    @RequestMapping(value="/app/{id}",method=RequestMethod.DELETE, produces="application/json")
    @ResponseBody
    public Map<String,Object> delete(
            @RequestParam(value="access_token",required=true) String access_token,
            @PathVariable Long id) throws Exception {
        appService.delete(id);//查询应用详情
        return this.successMsg();//返回查询结果
    }
    
    /**
     * 根据当前登录的客户merchantId,appid查询微托、聚来宝详情
     * @param request 请求
     * @param response 响应
     * @param access_token 令牌
     * @param appid appid
     * @return json
     * @author 王冬冬  
     * @throws Exception 异常
     * @date 2017年7月11日 上午10:18:09
     */
    @RequestMapping(value = "/microshop/{appid}",method=RequestMethod.GET)
    @ResponseBody
    public Map getByParams(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="access_token",required=true) String access_token,@PathVariable(value="appid",required=true) String appid) throws Exception{
        SessionUser user = SessionUtil.getCurSessionUser(request);
        Long merchantId = user.getMerchantId();
//        Long merchantId = 56064L;
        if (merchantId == null) {
            return this.successMsg();//商户id为空，直接返回
        }
//        String appId = request.getParameter("appId");
//        ValidUtil.valid("appId", appId, "required");
        Map<String, Object> resultMap = appService.getByParams(merchantId,appid);//根据merchantId,appid查询app详情
        return this.successMsg(resultMap);
    }
    
    /**
     * 获得关联配置的商户列表
     * @param request 请求
     * @param response 响应
     * @param access_token 令牌
     * @param params 参数
     * @return json
     * @author 王冬冬  
     * @throws Exception 异常
     * @date 2017年7月13日 上午10:27:19
     */
    @RequestMapping(value = "/microshop/merchants",method=RequestMethod.GET)
    @ResponseBody
    public Map getMerchantListByParam(HttpServletRequest request, HttpServletResponse response,@RequestParam(value="access_token",required=true) String access_token, @RequestParam(name="params",required=true) String params) throws Exception {
        Map paramMap=JsonUtil.fromJson(params, Map.class);
        Integer pageNo = RequestParamUtil.getPageNo(paramMap);//页码
        Integer pageSize = RequestParamUtil.getPageSize(paramMap);//每页记录数
        Page<Map> page = new Page<Map>(pageNo,pageSize);  //新建page
        SessionUser user = SessionUtil.getCurSessionUser(request);//获得user
        Long merchantId = user.getMerchantId();
//        Long merchantId = 1111L;
        if (merchantId == null) {
            throw new BizException("E2900001", MessageUtil.getMessage("E2900001"));// 当前账号缺少商户信息，无法登录至第三方应用！
        }
        String appId = (String) paramMap.get("appId");
        ValidUtil.valid("appId", appId, "required");//验证appId
        Long queryMerchantId=CastUtil.toLong(paramMap.get("merchantId"));
        appService.getMerchantListByParam(merchantId,queryMerchantId,appId,page);//获得关联配置的商户列表
        return this.successMsg(page);
    }
    
    /**
     * 获取access_token
     * @param appId appId
     * @param timestamp 时间戳
     * @param token 令牌
     * @return 结果
     * @author 周颖  
     * @date 2017年7月14日 下午3:31:02
     */
    @RequestMapping(method=RequestMethod.GET,value="/access_token")
    @ResponseBody
    public Map<String,Object> getAccessToken(
            @RequestParam(value="appid",required=true)String appId,
            @RequestParam(value="timestamp",required=true)String timestamp,
            @RequestParam(value="token",required=true)String token){
        
        ValidUtil.valid("应用id[appid]", appId, "required");//应用id，不允许为空
        ValidUtil.valid("服务器时间戳[timestamp]", timestamp, "required");//服务器时间戳（秒），不允许为空
        ValidUtil.valid("令牌[token]", token, "required");//令牌，不允许为空
        
        Map<String,Object> data = appService.getAccessToken(appId,timestamp,token);
        return this.successMsg(data);
    }
}

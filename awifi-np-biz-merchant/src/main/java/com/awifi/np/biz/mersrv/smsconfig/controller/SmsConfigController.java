/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月3日 下午4:01:07
* 创建作者：周颖
* 文件名称：SmsConfigController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mersrv.smsconfig.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.smsconfig.service.SmsConfigService;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;

@Controller
@RequestMapping("/mersrv")
public class SmsConfigController extends BaseController{

    /**短信配置*/
    @Resource(name="smsConfigService")
    private SmsConfigService smsConfigService;
    
    /**
     * 短信配置列表
     * @param accessToken access_token
     * @param params 参数
     * @param request 请求
     * @return 结果
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年5月4日 上午9:39:57
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.GET,value = "/smsconfigs")
    @ResponseBody
    public Map<String, Object> getListByParam(
            @RequestParam(value = "access_token", required = true) String accessToken,
            @RequestParam(value = "params", required = true) String params,
            HttpServletRequest request) throws Exception {
        Map<String, Object> paramsMap = JsonUtil.fromJson(params, Map.class);// 请求参数 json格式
        Integer pageSize = CastUtil.toInteger(paramsMap.get("pageSize"));// 每页记录数
        Integer maxPageSize = Integer.parseInt(SysConfigUtil.getParamValue("page_maxsize"));// 每页记录最大数
        ValidUtil.valid("每页数量[pageSize]", pageSize,"{'required':true,'numeric':{'max':" + maxPageSize + "}}");
        Long merchantId = CastUtil.toLong(paramsMap.get("merchantId"));// 商户id 可为空
        Integer pageNo = CastUtil.toInteger(paramsMap.get("pageNo"));// 页码
        if (pageNo == null) {// 如果为空，默认第一页
            pageNo = 1;
        }
        SessionUser user = SessionUtil.getCurSessionUser(request);
        Page<Map<String,Object>> page = new Page<Map<String,Object>>(pageNo, pageSize);
        smsConfigService.getListByParam(user,page,merchantId);
        return this.successMsg(page);
    }
    
    /**
     * 添加商户短信配置
     * @param access_token access_token
     * @param bodyParam 参数
     * @return 结果
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年5月3日 下午4:29:22
     */
    @RequestMapping(method=RequestMethod.POST, value="/smsconfig", produces="application/json")
    @ResponseBody
    public Map<String,Object> add(
            @RequestParam(value="access_token",required=true) String access_token,
            @RequestBody(required=true) Map<String,Object> bodyParam) throws Exception{
        Long merchantId = CastUtil.toLong(bodyParam.get("merchantId"));
        String smsContent = (String) bodyParam.get("smsContent");//短信内容
        Integer codeLength = CastUtil.toInteger(bodyParam.get("codeLength"));//验证码长度
        ValidUtil.valid("商户id[merchantId]", merchantId, "required");
        ValidUtil.valid("短信内容[smsContent]", smsContent, "required");
        ValidUtil.valid("验证码长度[codeLength]", codeLength, "required");
        if(StringUtils.indexOf(smsContent, "${code}") == -1){
            Object[] error = {smsContent,"${code}"};
            throw new BizException("E2200006", MessageUtil.getMessage("E2200006",error));//短信内容（smsContent[{0}]）必须包含${code}!
        }
        if(codeLength != 4 && codeLength != 6){
            throw new BizException("E2200007", MessageUtil.getMessage("E2200007",codeLength));//验证码长度（codeLength[{0}]）超出了范围[4|6]!
        }
        if(smsConfigService.isExist(merchantId)){
            throw new BizException("E2200008", MessageUtil.getMessage("E2200008"));//商户已存在短信配置
        }
        smsConfigService.add(merchantId,smsContent,codeLength);//添加配置
        return this.successMsg();
    }
    
    /**
     * 编辑商户短信配置
     * @param access_token access_token
     * @param bodyParam 参数
     * @param id 配置id
     * @return 结果
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年5月3日 下午4:29:22
     */
    @RequestMapping(method=RequestMethod.PUT, value="/smsconfig/{id}", produces="application/json")
    @ResponseBody
    public Map<String,Object> update(
            @RequestParam(value="access_token",required=true) String access_token,
            @RequestBody(required=true) Map<String,Object> bodyParam,
            @PathVariable(value="id",required=true)Long id) throws Exception{
        String smsContent = (String) bodyParam.get("smsContent");//短信内容
        Integer codeLength = CastUtil.toInteger(bodyParam.get("codeLength"));//验证码长度
        ValidUtil.valid("短信内容[smsContent]", smsContent, "required");
        ValidUtil.valid("验证码长度[codeLength]", codeLength, "required");
        if(StringUtils.indexOf(smsContent, "${code}") == -1){
            Object[] error = {smsContent,"${code}"};
            throw new BizException("E2200006", MessageUtil.getMessage("E2200006",error));//短信内容（smsContent[{0}]）必须包含${code}!
        }
        if(codeLength != 4 && codeLength != 6){
            throw new BizException("E2200007", MessageUtil.getMessage("E2200007",codeLength));//验证码长度（codeLength[{0}]）超出了范围[4|6]!
        }
        smsConfigService.update(id,smsContent,codeLength);//添加配置
        return this.successMsg();
    }
    
    /**
     * 短信配置详情
     * @param access_token access_token
     * @param id 主键id
     * @return 详情
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年5月3日 下午4:56:14
     */
    @RequestMapping(method=RequestMethod.GET, value="/smsconfig/{id}")
    @ResponseBody
    public Map<String,Object> getById(
            @RequestParam(value="access_token",required=true) String access_token,
            @PathVariable(value="id",required=true)Long id) throws Exception{
        Map<String,Object> smsConfigMap = smsConfigService.getById(id);//添加配置
        return this.successMsg(smsConfigMap);
    }
    
    /**
     * 短信配置删除
     * @param access_token access_token
     * @param id 主键id
     * @return 结果
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年5月3日 下午4:56:14
     */
    @RequestMapping(method=RequestMethod.DELETE, value="/smsconfig/{id}")
    @ResponseBody
    public Map<String,Object> delete(
            @RequestParam(value="access_token",required=true) String access_token,
            @PathVariable(value="id",required=true)Long id) throws Exception{
        smsConfigService.delete(id);//添加配置
        return this.successMsg();
    } 
}

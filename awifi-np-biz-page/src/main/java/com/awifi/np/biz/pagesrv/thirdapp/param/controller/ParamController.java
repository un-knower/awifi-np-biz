/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月12日 下午7:03:16
* 创建作者：许小满
* 文件名称：ParamController.java
* 版本：  v1.0
* 功能：参数加密
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.thirdapp.param.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.ms.util.StringUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.pagesrv.base.util.ExceptionUtil;
import com.awifi.np.biz.pagesrv.thirdapp.param.service.ParamService;

@Controller
public class ParamController extends BaseController {
    
    /** 微站des密钥 */
    private static String MWS_SECURITY_KEY = "xfchgfhf$sdEasdtfsd";

    /** 参数 业务层 */
    @Resource(name = "paramService")
    private ParamService paramService;
    
    /**
     * 参数加密
     * @param request 请求
     * @return json
     * @author 许小满  
     * @date 2017年5月12日 下午7:05:32
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/app/encryption")
    @ResponseBody
    public Map encryption(HttpServletRequest request){
        long beginTime = System.currentTimeMillis();
        Map<String, Object>resultMap = new HashMap<String, Object>();//结果map
        try{
            // 接收参数
            String customerId = StringUtils.defaultString(request.getParameter("customerId"));//客户ID
            String deviceId = StringUtils.defaultString(request.getParameter("deviceId"));//设备ID
            String devMac = StringUtils.defaultString(request.getParameter("devMac"));//设备MAC
            String userIp = StringUtils.defaultString(request.getParameter("userIp"));//用户IP
            String userMac = StringUtils.defaultString(request.getParameter("userMac"));//用户MAC
            String userPhone = StringUtils.defaultString(request.getParameter("userPhone"));//用户手机
            String terminalType = StringUtils.defaultString(request.getParameter("terminalType"));//用户终端类型
            
            String redisKey = paramService.putParamsToCache(customerId, deviceId, devMac, userIp, userMac, userPhone, terminalType);
            
            resultMap.put("result", "OK");
            resultMap.put("message", StringUtils.EMPTY);
            resultMap.put("params", redisKey);
        }catch (Exception e) {
            ExceptionUtil.formatMsg(request, resultMap, e, logger, "参数加密失败!");
        } finally {
            logger.debug("提示：参数加密 共花费了 " + (System.currentTimeMillis() - beginTime) + " ms.");
        }
        return resultMap;
    }
    
    /**
     * 参数加密（微站）
     * @param params 参数
     * @return json
     * @author 许小满  
     * @date 2017年6月20日 上午12:51:25
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(method=RequestMethod.GET, value="/pagesrv/mws/param/encry")
    @ResponseBody
    public Map<String,Object> mwsParamEncry(
            @RequestParam(value="params",required=true)String params//json格式参数
    ){
        Map<String,Object> paramsMap = JsonUtil.fromJson(params, Map.class);//请求参数 json格式
        Object merchantIdObj = paramsMap.get("merchantId");//商户id
        Object userIdObj = paramsMap.get("userId");//用户id
        //参数校验
        ValidUtil.valid("商户id[merchantId]", merchantIdObj, "{'required':true, 'numeric':true}");//商户id
        
        Long merchantId = CastUtil.toLong(merchantIdObj);//商户id
        Long userId = CastUtil.toLong(userIdObj);//用户id
        
        String merchantOpenId = StringUtil.encryptByDes(merchantId.toString(), MWS_SECURITY_KEY);//商户id(加密后)
        String userOpenId = userId != null ? StringUtil.encryptByDes(userId.toString(), MWS_SECURITY_KEY) : null;//用户id(加密后)
        
        Map<String,Object> resultMap = new HashMap<String,Object>(2);
        resultMap.put("merchantOpenId", merchantOpenId);//商户id(加密后)
        if(userOpenId != null){
            resultMap.put("userOpenId", userOpenId);//用户id(加密后)
        }
        return this.successMsg(resultMap);
    }
}

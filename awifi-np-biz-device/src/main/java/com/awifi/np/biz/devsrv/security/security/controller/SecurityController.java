package com.awifi.np.biz.devsrv.security.security.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月9日 下午7:35:20
 * 创建作者：许小满
 * 文件名称：SecurityController.java
 * 版本：  v1.0
 * 功能：安全--控制层
 * 修改记录：
 */
@Controller
@RequestMapping("/devsrv")
public class SecurityController extends BaseController {

    /**
     * 安全校验
     * @param access_token 安全令牌
     * @param params 请求参数
     * @return json
     * @author 许小满  
     * @date 2017年2月9日 下午7:35:58
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value="/security/check", method=RequestMethod.GET)
    public Map<String,Object> check(
            @RequestParam(value="access_token",required=true)String access_token,//安全令牌，不允许为空
            @RequestParam(value="params",required=true)String params//请求参数
    ){
        logger.debug("----------提示：开始执行安全接口  SecurityController.check()----------");
        long beginTime = System.currentTimeMillis();//开始时间，用于计算方法执行花费时间
        Map<String,Object> paramMap = JsonUtil.fromJson(params, Map.class);//参数从字符串转json
        /* 参数校验 */
        String code = (String)paramMap.get("interfaceCode");//接口编号
        ValidUtil.valid("权限（接口）编号[interfaceCode]", code, "required");//接口编号必填校验
        
        Map<String,Object> paramsMap = (Map<String,Object>)paramMap.get("params");//参数
        ValidUtil.valid("参数[params]", paramsMap, "required");//用户信息必填校验
        logger.debug("----------提示：安全接口执行成 功  SecurityController.check(),共花费了 " + (System.currentTimeMillis()-beginTime) + "ms.----------");
        return this.successMsg();//返回成功信息
    }
    
}

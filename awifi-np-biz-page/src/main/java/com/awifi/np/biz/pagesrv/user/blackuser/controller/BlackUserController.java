/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月7日 下午3:53:11
* 创建作者：许小满
* 文件名称：BlackUserController.java
* 版本：  v1.0
* 功能：黑名单--控制层
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.user.blackuser.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.pagesrv.base.util.ExceptionUtil;
import com.awifi.np.biz.toe.admin.usrmgr.blackuser.service.BlackUserService;

@Controller
@RequestMapping("/blackuser")
public class BlackUserController extends BaseController {

    /**黑名单*/
    @Resource(name="blackUserService")
    private BlackUserService blackUserService;
    
    /**
     * 校验手机号是否在黑名单里
     * @param request 请求
     * @return resultMap
     * @author ZhouYing 
     * @date 2015年12月9日 下午2:45:03
     */
    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value = "/checkbyphone")
    public Map checkByPhone(HttpServletRequest request) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        try {
            if (request.getMethod().equals(HttpRequest.METHOD_GET)) {
                resultMap.put("result", "FAIL");
                resultMap.put("message", "非法请求");
                return resultMap;
            }
            String cellphone = request.getParameter("cellphone");//手机号
            String customerId = request.getParameter("customerId");//客户id
            // 请求参数校验
            ValidUtil.valid("手机号[cellphone]", cellphone, "{'required':true, 'regex':'"+RegexConstants.CELLPHONE+"'}");//手机号
            ValidUtil.valid("商户id[customerId]", customerId, "required");//商户id
            
            Long customerIdLong = Long.parseLong(customerId);//客户id
            String isBlacklist = null;//是否为黑名单： yes 是黑名单 、no 不是黑名单
            
            //1. 获取该客户下面所有的匹配规则
            List<Integer> matchRuleList = blackUserService.getMatchRulesByMerchantId(customerIdLong);
            int maxSize = matchRuleList != null ? matchRuleList.size() : 0;
            if(maxSize <= 0){
                resultMap.put("result", "OK");
                resultMap.put("message", "");
                resultMap.put("isBlacklist", "no");
                return resultMap;
            }
            Integer matchRule = null;
            for(int i=0; i<maxSize; i++){
                matchRule = matchRuleList.get(i);
                if(matchRule == null){
                    logger.debug("错误：matchRule为空!");
                    continue;
                }
                //1.1 精确型 校验
                if(matchRule == 1){
                    isBlacklist = blackUserService.isBlackForRule1(cellphone, customerIdLong);
                    if(StringUtils.isNotBlank(isBlacklist) && isBlacklist.equals("yes")){
                        break;
                    }
                }
                //1.2 模糊型校验
                else if(matchRule == 2){
                    isBlacklist = blackUserService.isBlackForRule2(cellphone, customerIdLong);
                    if(StringUtils.isNotBlank(isBlacklist) && isBlacklist.equals("yes")){
                        break;
                    }
                }else{
                    logger.debug("错误:matchRule["+matchRule+"]超出了范围[1|2].");;
                }
            }
            resultMap.put("result", "OK");
            resultMap.put("message", "");
            resultMap.put("isBlacklist", StringUtils.defaultIfBlank(isBlacklist, "no"));
        } catch (Exception e) {
            ExceptionUtil.formatMsg(request, resultMap, e, logger, "系统异常：黑名单校验失败！");
        }
        return resultMap;
    }
    
}

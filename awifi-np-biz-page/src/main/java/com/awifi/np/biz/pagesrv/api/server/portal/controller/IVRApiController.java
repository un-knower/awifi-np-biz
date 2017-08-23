/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月16日 上午11:09:00
* 创建作者：许小满
* 文件名称：IVRApiController.java
* 版本：  v1.0
* 功能：IVR 语音认证
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.api.server.portal.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.util.IPUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.pagesrv.base.util.ExceptionUtil;
import com.awifi.np.biz.toe.admin.auth.ivr.service.IVRService;

@Controller
public class IVRApiController extends BaseController {

    /**IVR业务层*/
    @Resource(name="ivrService")
    private IVRService ivrService;
    
    /**
     * IVR语音认证-手机号接收接口
     * @param request 请求
     * @return json
     * @author 许小满  
     * @date 2017年5月16日 上午11:14:57
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/ivr/receive")
    @ResponseBody
    public Map ivr(HttpServletRequest request) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        try {
            // 请求方法类型 校验 用get请求
            if (request.getMethod().equals(HttpRequest.METHOD_POST)) {
                resultMap.put("result", "FAIL");
                resultMap.put("message", MessageUtil.getMessage("request_illegal"));// 非法请求！
                return resultMap;
            }
            String phoneNumber = request.getParameter("phoneNumber");// 手机号
            logger.debug("提示：IVR语音认证-手机号接收接口-手机号="+phoneNumber);
            //String timestamp = request.getParameter("timestamp");// 时间戳
            ValidUtil.valid("手机号[phoneNumber]", phoneNumber, "{'required':true, 'regex':'"+RegexConstants.CELLPHONE+"'}");//手机号
            
            String publicUserIp = IPUtil.getIpAddr(request);//用户公网IP,type=2时必填
            String publicUserPort = IPUtil.getRemotePort(request);//用户公网端口,type=2时且version>=v1.0以上必填
            String userAgent = request.getHeader("User-Agent");//请求头里面的userAgent
            ivrService.ivr(phoneNumber.trim(), publicUserIp, publicUserPort, userAgent);
            resultMap.put("result", "OK");
            resultMap.put("message", "执行成功！");
        }catch (Exception e) {
            ExceptionUtil.formatMsg(request, resultMap, e, logger, "Sorry, ivr receive failed.");
        }
        return resultMap;
    }
    
}

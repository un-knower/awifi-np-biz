/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月14日 下午10:37:26
* 创建作者：许小满
* 文件名称：PVController.java
* 版本：  v1.0
* 功能：PV
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.statis.pv.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantVisitClient;
import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.pagesrv.base.util.ExceptionUtil;

@Controller
@RequestMapping(value = "/pv")
public class PVController extends BaseController {

    /** 日志 */
    private static final Log logger = LogFactory.getLog(PVController.class);
    
    /**
     * pv 数据推送
     * @param request 请求
     * @return json
     * @author 许小满  
     * @date 2016年2月22日 下午6:06:25
     */
    @RequestMapping(value = "/push")
    @ResponseBody
    public Map<String, Object> push(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            // 请求方法类型 校验
            if (request.getMethod().equals(HttpRequest.METHOD_GET)) {
                resultMap.put("result", "FAIL");
                resultMap.put("message", "非法请求！");// 非法请求！
                return resultMap;
            }
            // 请求参数 校验
            String customerId = request.getParameter("customerId");//商户id
            String devId = request.getParameter("devId");//设备id
            String pageType = request.getParameter("pageType");//站点页面类型:1 引导页、2 认证页、3 过渡页、4 导航页
            String num = request.getParameter("num");//站点页面序号
            String userMac = request.getParameter("userMac");//用户终端MAC
            String visitDate = request.getParameter("visitDate");//访问日期：格式 yyyyMMdd[HH24]
            
            ValidUtil.valid("商户id[customerId]", customerId, "required");//商户id
            ValidUtil.valid("设备id[devId]", devId, "required");//设备id
            ValidUtil.valid("站点页面类型[pageType]", pageType, "{'required':true,'numeric':true}");//站点页面类型:1 引导页、2 认证页、3 过渡页、4 导航页
            ValidUtil.valid("站点页面序号[num]", num, "{'required':true,'numeric':true}");//站点页面序号
            ValidUtil.valid("用户MAC[userMac]", userMac, "{'required':true, 'regex':'"+RegexConstants.MAC_PATTERN+"'}");//用户mac地址
            ValidUtil.valid("访问日期[visitDate]", visitDate, "required");//访问日期：格式 yyyyMMdd[HH24]
            
            logger.debug("pv推送参数：customerId="+customerId+"&devId="+devId+"&pageType="+pageType+"&num="+num+"&userMac="+userMac+"&visitDate="+visitDate);
            MerchantVisitClient.pvPush(customerId, devId, pageType, num, userMac, visitDate);//推送pv数据
            resultMap.put("result", "OK");
            resultMap.put("message", StringUtils.EMPTY);
        } catch (Exception e) {
            ExceptionUtil.formatMsg(request, resultMap, e, logger, "Sorry, pv push failed.");
        }
        return resultMap;
    }
    
}

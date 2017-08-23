/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月13日 上午11:48:56
* 创建作者：许小满
* 文件名称：CssController.java
* 版本：  v1.0
* 功能：css接口
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.thirdapp.css.controller;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.util.ResponseUtil;
import com.awifi.np.biz.pagesrv.api.client.thirdapp.css.util.CssApiClient;
import com.awifi.np.biz.pagesrv.base.util.ExceptionUtil;
import com.awifi.np.biz.pagesrv.thirdapp.param.service.ParamService;

public class CssController extends BaseController {

    /** 参数 业务层 */
    @Resource(name = "paramService")
    private ParamService paramService;
    
    /**
     * 获取css接口
     * @param request 请求
     * @param response 响应
     * @author 许小满  
     * @date 2017年5月13日 上午11:54:44
     */
    @RequestMapping(value = "/app/getCssSource")
    @ResponseBody
    public void getCss(HttpServletRequest request, HttpServletResponse response){
        long beginTime = System.currentTimeMillis();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            //接收参数
            String customerId = StringUtils.defaultString(request.getParameter("customerId"));//客户ID
            String deviceId = StringUtils.defaultString(request.getParameter("deviceId"));//设备ID
            String devMac = StringUtils.defaultString(request.getParameter("devMac"));//设备MAC
            String userIp = StringUtils.defaultString(request.getParameter("userIp"));//用户IP
            String userMac = StringUtils.defaultString(request.getParameter("userMac"));//用户MAC
            String userPhone = StringUtils.defaultString(request.getParameter("userPhone"));//用户手机
            String terminalType = StringUtils.defaultString(request.getParameter("terminalType"));//用户终端类型
            String url = URLDecoder.decode(StringUtils.defaultString(request.getParameter("url")),"UTF-8");//URL
            
            String redisKey = paramService.putParamsToCache(customerId, deviceId, devMac, userIp, userMac, userPhone, terminalType);//将页面参数存放至redis中
            String data = CssApiClient.getCss(redisKey, url);
            ResponseUtil.responseText(response, data);
        } catch (Exception e) {
            ExceptionUtil.formatMsg(request, resultMap, e, logger, "css获取失败！");
        } finally {
            logger.debug("提示：独立服务接口（获取CSS源代码） 共花费了 " + (System.currentTimeMillis() - beginTime) + " ms.");
        }
        
    }
    
}

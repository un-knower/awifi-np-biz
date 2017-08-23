package com.awifi.np.biz.api.client.npadmin.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.awifi.np.biz.api.client.npadmin.util.NPAdminClient;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月17日 上午9:38:13
 * 创建作者：周颖
 * 文件名称：NPAdminViewInterceptor.java
 * 版本：  v1.0
 * 功能：显示接口拦截器
 * 修改记录：
 */
public class NPAdminViewInterceptor extends HandlerInterceptorAdapter {

    /** 日志 */
    private static final Log logger = LogFactory.getLog(NPAdminViewInterceptor.class);
    
    /** 服务编号key */
    @Value("serviceCodeKey")
    private String serviceCodeKey;
    
    @SuppressWarnings("rawtypes")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("----------提示：开始执行拦截器  NPAdminViewInterceptor.preHandle()----------");
        long beginTime = System.currentTimeMillis();//开始时间，用于计算方法执行花费时间
        if(StringUtils.isBlank(serviceCodeKey)){//服务编号key
            throw new BizException("E0000002", MessageUtil.getMessage("E0000002", "serviceCodeKey"));//{0}不允许为空!
        }
        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);//获取pathVariable
        String templateCode = StringUtils.EMPTY;
        if(pathVariables != null){
            if(pathVariables.containsKey("templatecode")){
                templateCode = (String)pathVariables.get("templatecode");
            }
        }
        String suitCode = request.getParameter("suitcode");
        String accessToken = request.getParameter("access_token");
        String serviceCode = SysConfigUtil.getParamValue(serviceCodeKey);//服务编号
        Map<String, Object> resultMap = NPAdminClient.viewInterface(serviceCode, suitCode, templateCode,accessToken);//调管理平台显示接口
        request.setAttribute("userInfo", resultMap);//把Map信息放入request中
        logger.debug("----------提示：拦截器执行成功  NPAdminViewInterceptor.preHandle(),共花费了 " + (System.currentTimeMillis()-beginTime) + "ms.----------");
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e)throws Exception {
    }

    public void setServiceCodeKey(String serviceCodeKey) {
        this.serviceCodeKey = serviceCodeKey;
    }
}
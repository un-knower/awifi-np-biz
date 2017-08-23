package com.awifi.np.biz.api.client.npadmin.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.awifi.np.biz.api.client.npadmin.util.NPAdminClient;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月12日 下午7:10:59
 * 创建作者：亢燕翔
 * 文件名称：NPAdminDataInterceptor.java
 * 版本：  v1.0
 * 功能：  管理系统数据接口拦截器
 * 修改记录：
 */
public class NPAdminDataInterceptor extends HandlerInterceptorAdapter {
    
    /** 日志 */
    private static Log logger = LogFactory.getLog(NPAdminDataInterceptor.class);
    
    /** 服务编号key */
    @Value("serviceCodeKey")
    private String serviceCodeKey;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("----------提示：开始执行拦截器  NPAdminDataInterceptor.preHandle()----------");
        long beginTime = System.currentTimeMillis();//开始时间，用于计算方法执行花费时间
        logger.debug("request.requestURI= " + request.getRequestURI());
        if(StringUtils.isBlank(serviceCodeKey)){//服务编号key
            throw new BizException("E0000002", MessageUtil.getMessage("E0000002", "serviceCodeKey"));//{0}不允许为空!
        }
        String accessToken = request.getParameter("access_token");//从request中获取access_token
        String serviceCode = SysConfigUtil.getParamValue(serviceCodeKey);//服务编号
        String interfaceCode = getInterfaceCode(handler);//接口编号
        logger.debug("提示：interfaceCode=" + interfaceCode);
        String params = "{}";//为安全接口，预留参数
        Map<String, Object> resultMap = NPAdminClient.dataInterface(accessToken, serviceCode, interfaceCode, params);//调用管理平台获取用户信息
        request.setAttribute("userInfo", resultMap);//把Map信息放入request中
        logger.debug("----------提示：拦截器执行成功 NPAdminDataInterceptor.preHandle(),共花费了 " + (System.currentTimeMillis()-beginTime) + "ms.----------");
        return true;
    }

    /**
     * 获取接口编号
     * @param handler 方法
     * @return 接口编号
     * @author 许小满  
     * @date 2017年2月17日 下午4:17:54
     */
    private String getInterfaceCode(Object handler){
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        StringBuffer interfaceCode = new StringBuffer();
        //1.获取类中@RequestMapping配置的路径
        RequestMapping classRequestMapping = handlerMethod.getBeanType().getAnnotation(RequestMapping.class);
        String classMappingValue = this.getRequestMappingValue(classRequestMapping);
        if(classMappingValue != null){
            interfaceCode.append(classMappingValue);
        }
        //2.获取方法中@RequestMapping配置的路径
        RequestMapping methodRequestMapping = handlerMethod.getMethodAnnotation(RequestMapping.class);
        String methodMappingValue = this.getRequestMappingValue(methodRequestMapping);
        if(methodMappingValue != null){
            interfaceCode.append(methodMappingValue);
        }
        String method = getRequestMappingMethod(methodRequestMapping);//http请求方式
        if(StringUtils.isNoneBlank(method)){
            interfaceCode.append(":").append(method);
        }
        return interfaceCode.toString();
    }
    
    /**
     * 获取 @RequestMapping中的value
     * @param requestMapping requestMapping
     * @return value
     * @author 许小满  
     * @date 2017年2月20日 上午9:26:27
     */
    private String getRequestMappingValue(RequestMapping requestMapping){
        if(requestMapping == null){
            return null;
        }
        String[] values = requestMapping.value();
        if(values == null || values.length <= 0){
            return null;
        }
        return values[0];
    }
    
    /**
     * 获取 @RequestMapping中的method
     * @param requestMapping requestMapping
     * @return method
     * @author 许小满  
     * @date 2017年2月27日 下午3:26:53
     */
    private String getRequestMappingMethod(RequestMapping requestMapping){
        if(requestMapping == null){
            return null;
        }
        RequestMethod[] requestMethod = requestMapping.method();
        if(requestMethod == null || requestMethod.length <= 0){
            return null;
        }
        return requestMethod[0].name();
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

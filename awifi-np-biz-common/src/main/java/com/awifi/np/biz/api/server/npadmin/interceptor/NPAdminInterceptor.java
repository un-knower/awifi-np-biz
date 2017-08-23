package com.awifi.np.biz.api.server.npadmin.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.EncryUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月15日 上午9:58:39
 * 创建作者：许小满
 * 文件名称：NPAmInterceptor.java
 * 版本：  v1.0
 * 功能：管理系统调用业务系统接口统一拦截器，用于校验token的有效性
 * 修改记录：
 */
public class NPAdminInterceptor extends HandlerInterceptorAdapter{

    /** 日志 */
    private static Log logger = LogFactory.getLog(NPAdminInterceptor.class);
    
    /** 5*60*1000 timestamp有效时间 五分钟 */
    private static long TOKEN_TIMEOUT = 300000;
    
    /** 服务编号key */
    @Value("serviceCodeKey")
    private String serviceCodeKey;
    
    /** 服务密钥key */
    @Value("serviceKeyKey")
    private String serviceKeyKey;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("----------提示：开始执行拦截器 NPAdminInterceptor.preHandle()----------");
        long beginTime = System.currentTimeMillis();//开始时间，用于计算方法执行花费时间
        if(StringUtils.isBlank(serviceCodeKey)){//服务编号key
            throw new BizException("E0000002", MessageUtil.getMessage("E0000002", "serviceCodeKey"));//{0}不允许为空!
        }
        if(StringUtils.isBlank(serviceKeyKey)){//服务密钥key
            throw new BizException("E0000002", MessageUtil.getMessage("E0000002", "serviceKeyKey"));//{0}不允许为空!
        }
        String timestamp = request.getParameter("timestamp");//服务器当前时间戳，数字，不允许为空，示例：1419399913
        ValidUtil.valid("时间戳[timestamp]", timestamp, "{'required':true,'numeric':true}");//服务器当前时间戳  校验
        String token = request.getParameter("token");//令牌，不允许为空，生成规则：md5(serviceCode + serviceKey + timestamp)
        ValidUtil.valid("令牌[token]", token, "{'required':true}");//令牌 校验
        /* timestamp 超时校验 */
        if(EncryUtil.isTimeout(timestamp, TOKEN_TIMEOUT)){
            throw new ValidException("E2000043", MessageUtil.getMessage("E2000043", new Object[]{"timestamp",timestamp}));//{0}[{1}]超时!
        }
        String serviceCode = SysConfigUtil.getParamValue(serviceCodeKey);//服务编号
        String serviceKey = SysConfigUtil.getParamValue(serviceKeyKey);//服务密钥
        String tokenNew = EncryUtil.generateToken(serviceCode, serviceKey, timestamp);//根据传参重新生成token
        if(!tokenNew.equals(token)){//如果两个token不同
            throw new ValidException("E2000004", MessageUtil.getMessage("E2000004", token));//token[{0}]无效!
        }
        logger.debug("----------提示：拦截器执行成功  NPAdminInterceptor.preHandle(),共花费了 " + (System.currentTimeMillis()-beginTime) + "ms.----------");
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

    public void setServiceKeyKey(String serviceKeyKey) {
        this.serviceKeyKey = serviceKeyKey;
    }
}

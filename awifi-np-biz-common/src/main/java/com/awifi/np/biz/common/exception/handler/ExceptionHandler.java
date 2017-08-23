package com.awifi.np.biz.common.exception.handler;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.InterfaceException;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.system.log.model.ExceptionLog;
import com.awifi.np.biz.common.system.log.util.ExceptionLogUtil;
import com.awifi.np.biz.common.util.ErrorUtil;
import com.awifi.np.biz.common.util.FormatUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月6日 下午5:08:25
 * 创建作者：周颖
 * 文件名称：ExceptionHandler.java
 * 版本：  v1.0
 * 功能：异常统一处理
 * 修改记录：
 */
public class ExceptionHandler implements HandlerExceptionResolver {
    
    /** 日志  */
    private static Log logger = LogFactory.getLog(ExceptionHandler.class);
    
    /**异常日志*/
    //@Resource(name = "exceptionLogService")
    //private ExceptionLogService exceptionLogService;
    
    /**
     * 异常统一处理
     * @author 周颖  
     * @date 2017年1月9日 下午2:23:27
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ErrorUtil.printException(ex, logger);//打印异常
        ExceptionLog log = new ExceptionLog();//new异常日志对象
        String requestURI = request.getRequestURI();//获取请求相对路径
        log.setModuleName(requestURI);//setModuleName
        log.setParameter(FormatUtil.formatRequestParam(request));//set请求参数
        log.setErrorMessage(ErrorUtil.getExceptionStackTrace(ex));//set错误信息
        ModelAndView mv = new ModelAndView();//new ModelAndView
        /*使用FastJson提供的FastJsonJsonView视图返回，不需要捕获异常 */ 
        FastJsonJsonView view = new FastJsonJsonView();//new FastJsonJsonView 返回json
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>(); 
        boolean needSave = true;//用于控制异常是否需要保存到数据库中
        /* 参数校验异常 */
        if(ex instanceof ValidException){//参数校验异常
            needSave = false;
            ValidException validex = (ValidException)ex;//转成业务异常
            resultMap.put("code", validex.getCode());//返回的json 增加code参数
            resultMap.put("msg", validex.getMessage());//返回json增加msg
        }
        /* json转换异常 */
        else if(ex instanceof JSONException){//json转换异常
            needSave = false;
            resultMap.put("code", "E2000036");//返回的json 增加code参数
            resultMap.put("msg", MessageUtil.getMessage("E2000036"));//返回的json 增加msg，json解析出错
        }
        /* 业务异常 */
        else if(ex instanceof BizException){//业务异常
            BizException bizex = (BizException)ex;//转成业务异常
            log.setErrorCode(bizex.getCode());
            resultMap.put("code", bizex.getCode());//返回的json 增加code参数
            resultMap.put("msg", bizex.getMessage());//返回的json 增加msg
        }
        /* 接口异常 */
        else if(ex instanceof InterfaceException){//接口异常 额外增加异常参数
            InterfaceException iex = (InterfaceException)ex;//转成接口异常
            log.setErrorCode(iex.getCode());
            log.setInterfaceUrl(iex.getInterfaceUrl());//set接口url
            log.setInterfaceParam(iex.getInterfaceParam());//set接口参数
            log.setInterfaceReturnValue(iex.getInterfaceReturnValue());//set接口返回值
            resultMap.put("code", iex.getCode());//接口异常增加统一异常编号
            resultMap.put("msg", iex.getMessage());//返回的json 增加msg
        }
        /* 其它异常 */
        else{
            log.setErrorCode("E2000019");
            resultMap.put("code", "E2000019");//未知异常编号
            resultMap.put("msg", MessageUtil.getMessage("E2000019"));//未知异常msg
        }   
        if(needSave){
            ExceptionLogUtil.add(log);//保存异常日志 
        }
        view.setAttributesMap(resultMap);  
        mv.setView(view); 
        return mv;
    }
}
/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月22日 下午7:52:51
* 创建作者：许小满
* 文件名称：ExceptionLogUtil.java
* 版本：  v1.0
* 功能：异常日志工具类
* 修改记录：
*/
package com.awifi.np.biz.common.system.log.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.awifi.np.biz.common.exception.InterfaceException;
import com.awifi.np.biz.common.system.log.model.ExceptionLog;
import com.awifi.np.biz.common.system.log.service.ExceptionLogService;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.common.util.ErrorUtil;
import com.awifi.np.biz.common.util.FormatUtil;

public class ExceptionLogUtil {
    
    /** 日志 */
    private static Log logger = LogFactory.getLog(ExceptionLogUtil.class);

    /** 异常日志service */
    private static ExceptionLogService exceptionLogService;
    
    /**
     * 保存异常日志
     * @param log 异常日志
     * @author 许小满
     * @date 2017年6月22日 下午8:02:04
     */
    public static void add(ExceptionLog log){
        String logSwitch = SysConfigUtil.getParamValue("error_log_switch");//异常日志开关：ON代表开、OFF 代表关
        if(StringUtils.isBlank(logSwitch)){
            return;
        }
        if(logSwitch.equals("OFF")){
            return;
        }
        if(!logSwitch.equals("ON")){
            logger.error("错误：logSwitch["+ logSwitch +"]超出了范围[ON|OFF]！");
            return; 
        }
        getExceptionLogService().saveExceptionLog(log);
    }
    
    /**
     * 保存异常日志
     * @param request 请求
     * @param e 异常
     * @author 许小满
     * @date 2017年6月22日 下午8:02:04
     */
    public static void add(HttpServletRequest request, Exception e){
        String logSwitch = SysConfigUtil.getParamValue("error_log_switch");//异常日志开关：ON代表开、OFF 代表关
        if(StringUtils.isBlank(logSwitch)){
            return;
        }
        if(logSwitch.equals("OFF")){
            return;
        }
        if(!logSwitch.equals("ON")){
            logger.error("错误：logSwitch["+ logSwitch +"]超出了范围[ON|OFF]！");
            return; 
        }
        String requestURI = request.getRequestURI();//获取请求相对路径
        
        ExceptionLog log = new ExceptionLog();//new异常日志对象
        log.setModuleName(requestURI);//setModuleName
        log.setParameter(FormatUtil.formatRequestParam(request));//set请求参数
        log.setErrorMessage(ErrorUtil.getExceptionStackTrace(e));//set错误信息
        
        if(e instanceof InterfaceException){//接口异常 额外增加异常参数
            InterfaceException iex = (InterfaceException)e;//转成接口异常
            log.setErrorCode(iex.getCode());
            log.setInterfaceUrl(iex.getInterfaceUrl());//set接口url
            log.setInterfaceParam(iex.getInterfaceParam());//set接口参数
            log.setInterfaceReturnValue(iex.getInterfaceReturnValue());//set接口返回值
        }
        getExceptionLogService().saveExceptionLog(log);
    }
    
    /**
     * 获取异常日志服务
     * @return exceptionLogService
     * @author 许小满  
     * @date 2017年6月22日 下午8:02:04
     */
    private static ExceptionLogService getExceptionLogService(){
        if(exceptionLogService == null){
            exceptionLogService = (ExceptionLogService)BeanUtil.getBean("exceptionLogService");
        }
        return exceptionLogService;
    }
    
}

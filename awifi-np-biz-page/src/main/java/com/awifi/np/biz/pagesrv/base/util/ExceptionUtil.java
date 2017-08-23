/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月13日 下午6:38:39
* 创建作者：许小满
* 文件名称：ExceptionUtil.java
* 版本：  v1.0
* 功能：异常处理工具类
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.base.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;

import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.InterfaceException;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.system.log.util.ExceptionLogUtil;
import com.awifi.np.biz.common.util.ErrorUtil;

public class ExceptionUtil {

    /**
     * 格式化异常信息
     * @param request 请求
     * @param resultMap 结果map
     * @param e 异常
     * @param logger 日志
     * @param defaultMsg 默认消息
     * @author 许小满  
     * @date 2017年5月13日 下午6:39:50
     */
    public static void formatMsg(HttpServletRequest request, Map<String,Object> resultMap, Exception e, Log logger, String defaultMsg){
        ErrorUtil.printException(e, logger);//打印日志
        ExceptionLogUtil.add(request, e);//保存异常日志
        /* 校验异常 */
        if(e instanceof ValidException){
            resultMap.put("result", "FAIL");
            resultMap.put("message", e.getMessage());
        }
        /* 业务异常 */
        else if(e instanceof BizException){
            resultMap.put("result", "FAIL");
            resultMap.put("message", e.getMessage());
        }
        /* 接口异常 */
        else if(e instanceof InterfaceException){
            resultMap.put("result", "FAIL");
            resultMap.put("message", e.getMessage());
        }
        /* 未知异常 */
        else {
            resultMap.put("result", "FAIL");
            if(StringUtils.isNotBlank(defaultMsg)){
                resultMap.put("message", defaultMsg);
                return ;
            }
            String message = e.getMessage();
            int msgLength = message.length();
            if(msgLength <= 30){
                resultMap.put("message", message);
                return ;
            }
            resultMap.put("message", message.substring(0, 30) + "...");
        }
    }
    
}

package com.awifi.np.biz.common.base.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.ValidUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:Jan 10, 2017 11:30:58 AM
 * 创建作者：亢燕翔
 * 文件名称：BaseController.java
 * 版本：  v1.0
 * 功能：  提供controller日志功能
 * 修改记录：
 */
@SuppressWarnings("rawtypes")
@ControllerAdvice
public class BaseController {
    
	/** 日志  */
    protected Log logger = LogFactory.getLog(this.getClass());
	
    /**
     * 设置分页信息
     * @param page 分页实体
     * @author 亢燕翔  
     * @date Jan 11, 2017 10:31:54 AM
     */
    protected void setPageInfo(Page page){
    	Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
    	resultMap.put("pageNo", page.getPageNo());
        resultMap.put("pageSize", page.getPageSize());
        resultMap.put("totalRecord", page.getTotalRecord());
        resultMap.put("totalPage", page.getTotalPage());
        resultMap.put("begin", page.getBegin());
        if (page.getRecords() != null) {
            resultMap.put("records", page.getRecords());
        }
    }
    
    /**
     * 设置分页信息 根据前台 参数
     * @param params 前台参数
     * @param page 分页实体
     * @author 伍恰  
     * @date 2017年6月27日 下午4:37:14
     */
    protected void setPageInfo(Map<String,Object> params, Page page){
        Integer pageNo=null;
        Integer pageSize=null;
        if(null == params){
            page.setPageNo(1);
            page.setPageSize(10);
        }else{
            pageNo = CastUtil.toInteger(params.get("pageNo"));
            pageSize = CastUtil.toInteger(params.get("pageSize"));
            if(null == pageNo){
                pageNo = 1;
            }
            page.setPageNo(pageNo);
            if(null == pageSize){
                pageSize = 10;
            }
            page.setPageSize(pageSize);
            Integer maxPageSize = Integer.parseInt(SysConfigUtil.getParamValue("page_maxsize"));// 每页记录最大数
            ValidUtil.valid("每页数量[pageSize]", pageSize,"{'required':true,'numeric':{'max':" + maxPageSize + "}}");
        }
    }
    
    /**
     * 返回客户端成功信息
     * @return map
     * @author 亢燕翔  
     * @date Jan 11, 2017 10:45:10 AM
     */
    protected Map<String, Object> successMsg(){
    	Map<String, Object> resultMap = new LinkedHashMap<String, Object>(1);
        resultMap.put("code", "0");
        return resultMap;
    }
    
    /**
     * 返回客户端data数据
     * @param data 数据
     * @return map
     * @author 亢燕翔  
     * @date Jan 11, 2017 11:17:18 AM
     */
    protected Map<String, Object> successMsg(Object data){
    	Map<String, Object> resultMap = new LinkedHashMap<String, Object>(2);
    	resultMap.put("code", "0");
        resultMap.put("data", data);
        return resultMap;
    }
    
    /**
     * 返回客户端data数据
     * @param data 数据
     * @param msg 消息
     * @return map
     * @author 许小满  
     * @date 2017年8月11日 下午12:58:03
     */
    protected Map<String, Object> successMsg(Object data, String msg){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>(2);
        resultMap.put("code", "0");
        resultMap.put("data", data);
        if(StringUtils.isNotBlank(msg)){
            resultMap.put("msg", msg);
        }
        return resultMap;
    }
    
    /**
     * 返回客户端模板数据
     * @param template 模板
     * @return map
     * @author 亢燕翔  
     * @date Jan 11, 2017 11:18:03 AM
     */
    protected Map<String, Object> successMsg(String template){
    	Map<String, Object> resultMap = new LinkedHashMap<String, Object>(2);
    	resultMap.put("code", "0");
        resultMap.put("template", template);
        return resultMap;
    }
    
    /**
     * 返回客户端失败信息
     * @param code 错误编码
     * @param data 错误信息
     * @return map
     * @author 范立松  
     * @date 2017年5月12日 下午5:39:54
     */
    protected Map<String, Object> failMsg(String code, Object data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>(2);
        resultMap.put("code", code);
        resultMap.put("msg", data);
        return resultMap;
    }
    
   /* @ExceptionHandler({ Exception.class })
    @ResponseBody
    public Object exception(Exception e, HttpServletRequest request) {
        logger.debug("=========request test=========");
        if (e instanceof TypeMismatchException) {
            if (((TypeMismatchException) e).getValue().toString().length() == 0) {
                return getWrongResultFromCfg("err.param.notnull");
            }
            return getWrongResultFromCfg("err.param.type");
        } else if (e instanceof MissingServletRequestParameterException) {
            return getWrongResultFromCfg("err.param.notnull");
        } else {
            return getWrongResultFromCfg(e.getMessage());
        }
    }
    
    public Map getWrongResultFromCfg(String message){
        Map<String,Object> error = new HashMap<String,Object>();
        error.put("msg", message);
        return error;
    }*/
    
    
    /**
     * 异常处理
     * @param request 请求对象
     * @param e 异常
     * @return json
     * @author 许小满  
     * @date 2017年2月10日 下午4:08:21
     */
    //@ExceptionHandler({ Exception.class })
    @ExceptionHandler({
            MissingPathVariableException.class,//请求参数丢失异常
            MissingServletRequestParameterException.class,//URI参数丢失异常
            TypeMismatchException.class,
            UnsatisfiedServletRequestParameterException.class,
            HttpRequestMethodNotSupportedException.class
            })
    public ResponseEntity<Map<String, Object>> handle(HttpServletRequest request, Exception e) {
        //logger.debug("e=" + e);
        Map<String, Object> resultMap = new LinkedHashMap<String,Object>();
        resultMap.put("code", "400");
        resultMap.put("msg", e.getMessage());
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
}
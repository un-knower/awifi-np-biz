package com.awifi.np.biz.timebuysrv.web.util;

/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月11日
 * 文件说明: 
 */


/*import cola.machine.util.log.ServiceMsg;*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.timebuysrv.web.log.ResultDTO;



public class ResultUtil {
    public static int succ=0;
    public static int fail=1;
	private static Logger logger =LoggerFactory.getLogger(ResultUtil.class);
	/**
	 * 返回成功，默认代码1
	 * @return
	 * @author 宋展辉
	 */
    public static ResultDTO getResult(){
        return getResult(succ, null, null,null);
    }
    
    /**
     * 返回成功，代码result
     * @param result
     * @return
     * @author 宋展辉
     */
    public static  ResultDTO getResult(int result){
        return getResult(result, null, null,null);
    }
    
	/**
	 * 返回成功，数据data
	 * @return
	 * @author 宋展辉
	 */
	/*public static  ResultDTO getResult(Object data){
		return getResult(succ, data, null,null);
	}*/
	/*public static  ResultDTO getResult(ServiceMsg msg){
		return getResult(fail,null,msg.toString(),null);
	}*/


	public static ResultDTO getSuccResult(){
		return getResult(succ, null, null,null);
	}
	
	
	
	public static ResultDTO getFailResult(String errcode){
		return getResult(fail, null, errcode,null);
	}
	
	/**
	 * 返回成功，数据data,分页page
	 * @param data
	 * @return
	 * @author 宋展辉
	 */
	public static  ResultDTO getResult(Object data,Page page){
		return getResult(succ, data, null,page);
	}
	
	public static  ResultDTO getDataResult(Object data){
        return getResult(succ, data, null,null);
    }
	/**
	 * 返回错误请求，错误代码result，错误说明msg
	 * @param result
	 * @param msg
	 * @return
	 * @author 宋展辉
	 */
	public static  ResultDTO getResult(int result, String msg){
        return getResult(result, null, msg,null);
    }
	public static  ResultDTO getResultDetail(int service ,int type,int result, String msg){
		return getResult(service*100000+type*1000+result, null, msg,null);
	}

	
/*	public static  ResultDTO get(ServiceMsg msg){


		return getResult(msg.ordinal(),  msg.toString());

	}*/
	
	
	/**
	 * 自定义返回
	 * @param result
	 * @param data
	 * @param msg
	 * @return
	 * @author 宋展辉
	 */
	public static  ResultDTO getResult(int result, Object data, String msg){
		return getResult(result, data, msg,null);
	}
	
	/**
	 * 自定义返回
	 * @param result
	 * @param data
	 * @param msg
	 * @param page
	 * @return
	 * @author 宋展辉
	 */
	public static  ResultDTO getResult(int result, Object data, String msg , Page page){
        return new ResultDTO(result+"", data, msg, page);
    }

	
}

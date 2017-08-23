/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月28日 下午7:26:54
* 创建作者：许小满
* 文件名称：RequestParamUtil.java
* 版本：  v1.0
* 功能：常见请求参数获取工具类
* 修改记录：
*/
package com.awifi.np.biz.common.util;

import java.util.Map;

import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;

public class RequestParamUtil {
    
    /**
     * 获取分页的页面
     * 校验规则：页码，数字，允许为空，默认第1页
     * @param paramsMap 参数map
     * @return 页码
     * @author 许小满  
     * @date 2017年7月28日 下午7:30:46
     */
    public static Integer getPageNo(Map<String, Object> paramsMap){
        Object pageNoObj = paramsMap.get("pageNo");//页码
        ValidUtil.valid("页码[pageNo]", pageNoObj, "numeric");//数字校验
        return pageNoObj != null ? CastUtil.toInteger(pageNoObj) : 1;
    }
    
    /**
     * 获取分页的每页记录数
     * 校验规则：每页记录数，数字，不允许为空
     * @param paramsMap 参数map
     * @return 每页记录数
     * @author 许小满  
     * @date 2017年7月28日 下午7:33:57
     */
    public static Integer getPageSize(Map<String, Object> paramsMap){
        int maxSize = Integer.parseInt(SysConfigUtil.getParamValue("page_maxsize"));//获取系统配置最大每页条数限制
        Object pageSizeObj = paramsMap.get("pageSize");//页码
        ValidUtil.valid("每页数量[pageSize]", pageSizeObj, "{'required':true,'numeric':{'max':"+maxSize+"}}");//不能为空且为数字
        return CastUtil.toInteger(pageSizeObj);
    }
    
}

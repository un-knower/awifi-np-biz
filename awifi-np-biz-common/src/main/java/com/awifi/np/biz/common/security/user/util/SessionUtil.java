package com.awifi.np.biz.common.security.user.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月13日 上午10:55:42
 * 创建作者：亢燕翔
 * 文件名称：SessionUtil.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings("unchecked")
public class SessionUtil {
    
    /** 日志  */
    //private static Log logger = LogFactory.getLog(SessionUtil.class);
    
    /**
     * 在请求中获取用户信息
     * @param request 请求
     * @return SessionUser
     * @author 亢燕翔  
     * @date 2017年1月13日 上午11:00:02
     */
    public static SessionUser getCurSessionUser(HttpServletRequest request){
        /*1.先从Attribute获取sessionUser对象存在直接返回*/
        if(request.getAttribute("sessionUser") != null){
            return (SessionUser) request.getAttribute("sessionUser");
        }
        /*2.不存在则从map中封装数据*/
        Map<String, Object> resultMap = (Map<String, Object>) request.getAttribute("userInfo");
        SessionUser sessionUser = resultMapToSessionUser(resultMap);//封装数据
        /*3.sessionUser封装之后再放入Attribute*/
        request.setAttribute("sessionUser", sessionUser);
        return sessionUser;
    }
    
    /**
     * map转换为sessionUser
     * @param resultMap 
     * @return sessionUser
     * @author 亢燕翔  
     * @date 2017年1月13日 上午11:05:58
     */
    private static SessionUser resultMapToSessionUser(Map<String, Object> resultMap) {
        Map<String, Object> secResultMap = (Map<String, Object>) resultMap.get("data");
        Long id = CastUtil.toLong(secResultMap.get("id"));//用户id
        String roleIds = (String) secResultMap.get("roleIds");//角色ids
        if(id == null){//用户ID为空校验
            throw new BizException("E2000012", MessageUtil.getMessage("E2000012"));//用户ID不能为空！
        }
        if(StringUtils.isBlank(roleIds)){//角色ID为空校验
            throw new BizException("E2000011", MessageUtil.getMessage("E2000011"));//用户信息中的角色id[roleIds]不允许为空!
        }
        //懒加载，需要的时候再创建SessionUser
        SessionUser sessionUser = new SessionUser();
        sessionUser.setId(id);
        sessionUser.setUserName((String) secResultMap.get("userName"));//用户名
        sessionUser.setRoleIds(roleIds);//角色ids
        sessionUser.setSuitCode((String) secResultMap.get("suitCode"));//套码
        sessionUser.setMerchantId(CastUtil.toLong(secResultMap.get("merchantId")));//商户id
        sessionUser.setProvinceId(CastUtil.toLong(secResultMap.get("provinceId")));//省id
        sessionUser.setCityId(CastUtil.toLong(secResultMap.get("cityId")));//市id
        sessionUser.setAreaId(CastUtil.toLong(secResultMap.get("areaId")));//区县id
        sessionUser.setProjectIds((String)secResultMap.get("projectIds"));//管理项目
        sessionUser.setFilterProjectIds((String)secResultMap.get("filterProjectIds"));
        sessionUser.setMerchantIds((String)secResultMap.get("merchantIds"));
        
        Map<String, Object> levResultMap = (Map<String, Object>) secResultMap.get("extend");//扩展字段
        //当levResultMap不为空时
        if(levResultMap != null){
            sessionUser.setOrgId(CastUtil.toLong(levResultMap.get("orgId")));
            sessionUser.setCascadeLabel((String) levResultMap.get("cascadeLabel"));//商户层级关系 ，格式为：1-2-3
        }
        return sessionUser;
    }
    
}
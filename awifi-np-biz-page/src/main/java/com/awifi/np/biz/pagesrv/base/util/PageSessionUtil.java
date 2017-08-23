/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月19日 上午11:17:40
* 创建作者：许小满
* 文件名称：PageSessionUtil.java
* 版本：  v1.0
* 功能：页面服务--session相关操作工具类
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.base.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PageSessionUtil {

    /** 日志  */
    private static Log logger = LogFactory.getLog(PageSessionUtil.class);
    
    /**
     * 将用户信息存放入session中
     * @param request 请求
     * @param merchantId 商户id
     * @param deviceId 设备id
     * @param userId 用户id
     * @author 许小满  
     * @date 2017年7月19日 下午12:18:58
     */
    public static void putUserToSession(HttpServletRequest request, Long merchantId, String deviceId, Long userId){
        if(userId == null){
            return;
        }
        HttpSession session = request.getSession();//会话
        session.setAttribute("userId", userId);//将用户id放入session中，防止页面刷新丢失
        logger.debug("提示：将userId["+ userId +"]存入session中！");
        if(merchantId != null){//商户id非空判断
            session.setAttribute("merchantId", merchantId);//将商户id存入session中
        }
        if(StringUtils.isNotBlank(deviceId)){//设备id非空判断
            session.setAttribute("deviceId", deviceId);//将设备id存入session中
        }
    }
    
    /**
     * 从session中获取用户id（用户中心），防止页面刷新导致用户id丢失问题（页面服务专用）
     * @param request 请求
     * @return 用户id
     * @author 许小满
     * @date 2017年6月22日 下午7:37:07
     */
    public static Long getUserId(HttpServletRequest request){
        HttpSession session = request.getSession();//获取session
        String merchantId = StringUtils.defaultString(request.getParameter("customer_id"));//商户id
        //判断商户是否改变，如果改变，返回空值
        Long merchantIdLong = StringUtils.isNotBlank(merchantId) ? Long.parseLong(merchantId) : null;
        Long merchantIdInSession = (Long)session.getAttribute("merchantId");//商户id
        if(merchantIdLong != null && merchantIdInSession != null && !(merchantIdLong.equals(merchantIdInSession))){
            session.invalidate();//session销毁
            logger.debug("提示：sesion中保存的商户id发生变更，系统将重置userId！");
            return null;
        }
        String deviceId = StringUtils.defaultString(request.getParameter("dev_id"));//设备id
        //判断设备是否改变，如果改变，返回空值
        String deviceIdInSession = (String)session.getAttribute("deviceId");//设备id
        if(StringUtils.isNotBlank(deviceId) && StringUtils.isNotBlank(deviceIdInSession) && !(deviceId.equals(deviceIdInSession))){
            logger.debug("提示：sesion中保存的设备id发生变更，系统将重置userId！");
            session.invalidate();//session销毁
            return null;
        }
        //1.从请求中获取用户id
        String userIdRequest = request.getParameter("user_id");//请求中的用户id
        if(StringUtils.isNotBlank(userIdRequest)){
            logger.debug("提示：userId in request=" + userIdRequest);
            return Long.parseLong(userIdRequest);
        }
        //2.从session中获取用户id 
        Long userIdSession = (Long)session.getAttribute("userId");//session中的用户id
        logger.debug("提示：userId in session=" + userIdSession);
        return userIdSession;
    }
    
}

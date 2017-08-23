/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月4日 下午1:29:46
* 创建作者：许小满 
* 文件名称：UserController.java
* 版本：  v1.0 
* 功能：用户相关操作--控制层
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.user.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;

@Controller
public class UserController extends BaseController {

    /**
     * 从session中获取用户id
     * @param request 请求
     * @param params json格式参数
     * @return json
     * @author 许小满  
     * @date 2017年7月4日 下午6:11:40
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(method=RequestMethod.GET, value="/pagesrv/user/session/id")
    @ResponseBody
    public Map<String,Object> getSessionUserId(
            HttpServletRequest request,//请求对象
            @RequestParam(value="params",required=false)String params//json格式参数
    ){
        HttpSession session = request.getSession(false);
        if(session == null){
            return this.failMsg("E0000003", MessageUtil.getMessage("E0000003"));//session失效!
        }
        
        if(StringUtils.isNotBlank(params)){
            Map<String,Object> paramsMap = JsonUtil.fromJson(params, Map.class);//请求参数 json格式
            Long merchantId = CastUtil.toLong(paramsMap.get("merchantId"));//商户id
            //判断商户是否改变，如果改变，返回空值
            Long merchantIdInSession = (Long)session.getAttribute("merchantId");//商户id
            if(merchantId != null && merchantIdInSession != null && !(merchantId.equals(merchantIdInSession))){
                logger.debug("提示：sesion中保存的商户id发生变更，系统将重置userId！");
                session.invalidate();//session销毁
                return this.successMsg();
            }
            String deviceId = (String)paramsMap.get("deviceId");//设备id
            //判断设备是否改变，如果改变，返回空值
            String deviceIdInSession = (String)session.getAttribute("deviceId");//设备id
            if(StringUtils.isNotBlank(deviceId) && StringUtils.isNotBlank(deviceIdInSession) && !(deviceId.equals(deviceIdInSession))){
                logger.debug("提示：sesion中保存的设备id发生变更，系统将重置userId！");
                session.invalidate();//session销毁
                return this.successMsg();
            }
        }
        
        Long userId = (Long)session.getAttribute("userId");//用户id
        logger.debug("userId=" + userId);
        Map<String,Object> resultMap = new HashMap<String,Object>();
        if(userId != null){
            resultMap.put("userId", userId);
        }
        return this.successMsg(resultMap);
    }
    
}

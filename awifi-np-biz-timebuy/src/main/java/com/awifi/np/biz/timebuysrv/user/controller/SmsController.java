/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月24日 下午1:41:48
* 创建作者：尤小平
* 文件名称：SmsController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.user.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.awifi.np.biz.api.client.sms.util.SmsClient;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.timebuysrv.user.bean.Captcha;
import com.awifi.np.biz.timebuysrv.util.CaptchaUtil;
import com.awifi.np.biz.timebuysrv.util.Tools;
import com.awifi.np.biz.timebuysrv.util.check.CommonCheckUtil;
import com.awifi.np.biz.timebuysrv.util.define.MsCommonDefine;
import com.awifi.np.biz.timebuysrv.web.core.Constants;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionDTO;
//import com.awifi.np.biz.timebuysrv.web.util.SmsUtil;
import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.timebuysrv.user.service.UserAuthService;
import com.cpj.swagger.annotation.APIs;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@APIs(description = "发送验证码")
@Controller
@RequestMapping(value = "/timebuysrv/sms")
public class SmsController extends BaseController {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * UserAuthService
     */
    @Resource
    private UserAuthService userAuthService;

    /**
     * 发送验证码.
     * 
     * @param request HttpServletRequest
     * @return Map
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年4月24日 下午5:30:13
     */
    @SuppressWarnings("rawtypes")
    @API(summary = "发送验证码", parameters = {
            @Param(name = "mobile", description = "手机号码", dataType = DataType.STRING, required = true),
            @Param(name = "type", description = "验证码类型", dataType = DataType.STRING, required = true) })
    @RequestMapping(value = "/send", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map view(HttpServletRequest request)
            throws Exception {
        logger.debug("发送验证码, 请求参数：" + JsonUtil.toJson(request.getParameterMap()));

        String mobile = request.getParameter("mobile");
        String type = request.getParameter("type");

        ValidUtil.valid("mobile", mobile, "required");
        ValidUtil.valid("type", type, "required");

        // 检查session中是否有dto
        SessionDTO sessionDTO = (SessionDTO) request.getSession().getAttribute(Constants.SESSION_DTO);
        if (sessionDTO == null || sessionDTO.getMerchant() == null || sessionDTO.getMerchant().getDeviceId() == null) {
            throw new ValidException("E5071003", MessageUtil.getMessage("E5071003"));// 登录信息已失效，请重新刷新页面
        }
        if (!CommonCheckUtil.checkMobile(mobile)) {
            throw new ValidException("E5071002", MessageUtil.getMessage("E5071002"));// 手机号码格式错误
        }

//        SmsUtil smsUtil =new SmsUtil();
        StringBuilder content = new StringBuilder();
        String code = Tools.rand(4);
        if (type.equals(MsCommonDefine.SMS_TYPE_LOGIN)) {
            if (userAuthService.getUserByLogName(mobile) == null) {
                // 新增用户
                if (userAuthService.saveReg(mobile, SysConfigUtil.getParamValue("default_password")) > 0) {
                    CaptchaUtil.sendCaptcha( mobile, MsCommonDefine.SMS_TYPE_LOGIN, request, sessionDTO);
                } else {
                    throw new ValidException("E5071001", MessageUtil.getMessage("E5071001"));// 发送验证码失败
                }
            } else {
                CaptchaUtil.sendCaptcha(mobile, MsCommonDefine.SMS_TYPE_LOGIN, request, sessionDTO);

            }
            return this.successMsg();
        } else if (type.equals(MsCommonDefine.SMS_TYPE_CHANGEPWD)) {
            if (userAuthService.getUserByLogName(mobile) == null) {
                throw new ValidException("E2000056", MessageUtil.getMessage("E2000056", "用户"));// 用户不存在
            }
            content.append("您的验证码是:").append(code);
        } else if (type.equals(MsCommonDefine.SMS_TYPE_WARN)) {
            Long last = (Long) request.getSession().getAttribute("warn_sms_time");
            if (last != null) {
                if ((last + 60 * 1000 * 60L) > new Date().getTime()) {
                    throw new Exception("警告短信过于频繁");
                }
            }
            request.getSession().setAttribute("warn_sms_time", new Date().getTime());
            SimpleDateFormat sdf = new SimpleDateFormat("HH");
            int time = Integer.parseInt(sdf.format(new Date()));
            if (time >= 20 && time < 8) {
                throw new Exception("警告短信时间段过滤");
            }
            content.append("亲爱的园区用户，您的上网时间已不足一小时，请尽快购买上网时长。");
        }

        Map<String, Object> reusltMap = SmsClient.sendMsg(mobile,content.toString());
        // 未加安全措施
        if (reusltMap != null && reusltMap.get("resultCode").equals("0")) {
//        if (smsUtil.sendSms(content.toString(), mobile, (short)2).equalsIgnoreCase("succ")) {
            Captcha captchaOb = new Captcha(mobile, code, new Date());
            if (type.equals(MsCommonDefine.SMS_TYPE_WARN)) {
                request.getSession().setAttribute(MsCommonDefine.SESSION_SMS_CODE + type, captchaOb); // session中保存短信验证码
            } else if (type.equals(MsCommonDefine.SMS_TYPE_CHANGEPWD)) {
                request.getSession().setAttribute(MsCommonDefine.SESSION_SMS_CODE + type, captchaOb); // session中保存短信验证码
            }
        } else {
            throw new ValidException("E5071001", MessageUtil.getMessage("E5071001"));// 发送验证码失败
        }

        return this.successMsg();
    }

}

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月21日 下午5:06:42
* 创建作者：尤小平
* 文件名称：LoginController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.user.controller;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.timebuysrv.user.service.UserAuthService;
import com.awifi.np.biz.timebuysrv.util.StringUtil;
import com.awifi.np.biz.timebuysrv.web.core.Constants;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionDTO;
import com.awifi.np.biz.timebuysrv.web.module.time.model.UserTimeInfo;
import com.awifi.np.biz.timebuysrv.web.module.time.service.UserConsumeService;
import com.awifi.np.biz.timebuysrv.web.module.time.service.UserCutoffService;
import com.awifi.np.biz.timebuysrv.web.module.time.service.WifiService;
import com.awifi.np.biz.timebuysrv.web.util.PortalUtil;
import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIResponseBody;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;

@APIs(description = "用户登录")
@Controller
@RequestMapping(value = "/timebuysrv/login")
public class LoginController extends BaseController {

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
     * UserCutoffLogicService
     */
    @Resource
    private UserCutoffService userCutoffLogicService;

    /**
     * wifi放通服务
     */
    @Resource
    WifiService wifiService;
    /**
     * UserConsumeLogicService 消费
     */
    @Resource
    private UserConsumeService userConsumeService;

    @API(summary = "用户登录接口", description = " 此方法的主要功能是 用户在访问首页后 登录用 此处需要注意的是如果session中没有值 建议用户重传portal参数", parameters = {
            @Param(name = "username", description = "用户名(手机号码)", dataType = DataType.PHONE, required = true),
            @Param(name = "captcha", description = "验证码(如果是一键登录就不用)", dataType = DataType.CAPTCHA, required = false), })
    @APIResponseBody(value = "{ \"r\": 0, \"data\": { \"now_date\": 1489975765652, \"merchant\": {\"merchantId\": 5597, \"merchantName\": \"凌云公寓\", 'broadbandAccount': '宽带账号电话号码' }, user:{'phone':13958173965,name:'用户名'},timeInfo:{'vip':'是否vip',canGetFreePgk:'1:是能领取免费礼包 0:不能领取',endTime:'1489975765652时长时间到期时间''},\"topPicList\": [\"123.jpg\"] }, \"msg\": \"错误原因\" }\n}")
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String, Object> login(@RequestParam(value = "username", required = true) String username, // 手机号码
            @RequestParam(value = "captcha", required = false) String captcha, // 验证码
            HttpServletRequest request) throws Exception {
        //取session
        SessionDTO sessionDTO = (SessionDTO) request.getSession().getAttribute(Constants.SESSION_DTO);
        String param = "username:" + username + ",captcha:" + captcha;
        
        // 如果没有上报过portal 参数不允许登录
        if (sessionDTO == null || sessionDTO.getMerchant() == null
                || StringUtil.isBlank(sessionDTO.getMerchant().getDevMac())
                || sessionDTO.getMerchant().getMerchantId() == null) {
            logger.error("用户登录时遇到sessionDto参数错误");
            throw new ValidException("E5071005", MessageUtil.getMessage("E5071005"));
        }

        // 如果获取不到设备的参数，则先从session中获取

        if (StringUtil.isBlank(username)) {
            logger.error("手机号码不能为空");
            throw new ValidException("E0000002", MessageUtil.getMessage("E0000002", "手机号码"));
        }

        if (!StringUtil.isPhone(username)) {
            logger.error("手机号码格式错误");
            throw new ValidException("E5071002", MessageUtil.getMessage("E5071002"));
        }
        if (StringUtil.isBlank(sessionDTO.getPortalParam().getUserMac())) {
            logger.error("手机mac不能为空");
            throw new ValidException("E0000002", MessageUtil.getMessage("E0000002", "手机mac"));
        }

        if (sessionDTO.getMerchant() == null || sessionDTO.getMerchant().getMerchantId() == null) {
            logger.error("商户id不能为空");
            throw new ValidException("E0000002", MessageUtil.getMessage("E0000002", "商户id"));
        }
        // 3.对用户信息做相关处理
        userAuthService.loginByPhoneAndSms(sessionDTO, username, captcha, request);
       
        UserTimeInfo timeInfo = sessionDTO.getTimeInfo();
        // 如果当前有时长 或者是vip 那么就放行
        if (timeInfo != null && ((timeInfo.getEndTime() != null && timeInfo.getEndTime() > new Date().getTime())
                || timeInfo.isVip())) {

            logger.debug(" before permitWLAN" + username);
            wifiService.permitWLAN(sessionDTO);
        }
        return this.successMsg(PortalUtil.getDetailInfo(sessionDTO));
    }

}

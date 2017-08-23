/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月5日 下午4:24:15
* 创建作者：尤小平
* 文件名称：KickAuthController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.web.core.auth.controller;

import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.timebuysrv.third.access.bean.DevParms;
import com.awifi.np.biz.timebuysrv.util.StringUtil;
import com.awifi.np.biz.timebuysrv.web.core.Constants;
import com.awifi.np.biz.timebuysrv.web.module.time.model.PortalParam;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionDTO;
import com.awifi.np.biz.timebuysrv.web.module.time.service.WifiService;
import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@APIs(description = "踢用户下线")
@Controller
@RequestMapping(value = "/timebuysrv/kickaccess")
public class KickAuthController extends BaseController {
    /**
     * Logger
     */
    Logger logger = Logger.getLogger(this.getClass());

    /**
     * AccessAuthService
     */
    @Resource
    private WifiService wifiService;

    /**
     * 踢用户下线接口.
     * 
     * @param request request
     * @param deviceId 设备id
     * @param deviceMac 设备mac
     * @param userMac 终端用户mac
     * @param userName 手机号码
     * @param kickLevel 下线等级
     * @return Map
     * @author 尤小平
     * @date 2017年6月5日 下午9:04:43
     */
    @API(summary = "踢用户下线接口", description = "{ \"code\": \"0\", \"msg\":\"\", \"data\": \"\" }", parameters = {
            @Param(name = "userMac", description = "终端用户mac", dataType = DataType.STRING, required = true),
            @Param(name = "userName", description = "手机号码", dataType = DataType.STRING, required = false),
            @Param(name = "deviceId", description = "设备id", dataType = DataType.STRING, required = false),
            @Param(name = "deviceMac", description = "设备mac", dataType = DataType.STRING, required = false),
            @Param(name = "kickLevel", description = "下线等级, 0:只踢下线；1：踢下线并将用户设置为免认证用户;2：踢下线并将用户设置为新用户;默认等级：0", dataType = DataType.INTEGER, required = false) })
    @RequestMapping(value = "/kickauth", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String, Object> kickUserOffLine(HttpServletRequest request,
            @RequestParam(value = "deviceId", required = false) String deviceId,
            @RequestParam(value = "deviceMac", required = false) String deviceMac,
            @RequestParam(value = "userMac", required = true) String userMac,
            @RequestParam(value = "userName", required = false) String userName,
            @RequestParam(value = "kickLevel", required = false) String kickLevel) throws Exception {

        logger.debug("踢用户下线, 请求参数：" + JsonUtil.toJson(request.getParameterMap()));

        Device device = null;
        PortalParam portalParam = null;
        SessionDTO sessionDTO = (SessionDTO) request.getSession().getAttribute(Constants.SESSION_DTO);
        if (null != sessionDTO) {
            device = sessionDTO.getMerchant();
            portalParam = sessionDTO.getPortalParam();
        }

        if (StringUtil.isBlank(deviceId) && null != portalParam && StringUtil.isNotBlank(portalParam.getDeviceId())) {
            deviceId = portalParam.getDeviceId();
        }
        if (StringUtil.isBlank(userMac) && null != portalParam && StringUtil.isNotBlank(portalParam.getUserMac())) {
            userMac = portalParam.getUserMac();
        }
        if (StringUtil.isBlank(userName) && null != portalParam
                && StringUtil.isNotBlank(portalParam.getMobilePhone())) {
            userName = portalParam.getMobilePhone();
        }
        if (StringUtil.isBlank(deviceMac) && null != device && StringUtil.isNotBlank(device.getDevMac())) {
            deviceMac = device.getDevMac();
        }
        if (StringUtil.isBlank(kickLevel) || (!kickLevel.equals("1") && !kickLevel.equals("2"))) {
            kickLevel = "0";
        }

        DevParms devParams = new DevParms();
        devParams.setDeviceId(deviceId);
        devParams.setApMac(deviceMac);
        devParams.setUsername(userName);
        devParams.setTerMac(userMac);
        int result = wifiService.kickuseroffline(devParams, kickLevel);

        if (result == 1) {// 踢下线成功
            return this.successMsg();
        } else {// 踢下线失败
            return this.failMsg("E5066004", MessageUtil.getMessage("E5066004"));
        }
    }
}

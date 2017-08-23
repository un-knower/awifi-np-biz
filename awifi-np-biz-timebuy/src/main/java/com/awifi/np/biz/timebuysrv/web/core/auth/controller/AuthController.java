/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月18日 上午9:13:05
* 创建作者：尤小平
* 文件名称：AuthController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.web.core.auth.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;
import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.api.client.dbcenter.user.model.PubUserAuth;
import com.awifi.np.biz.api.client.dbcenter.user.util.UserAuthClient;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.timebuysrv.web.core.Constants;
import com.awifi.np.biz.timebuysrv.web.core.annotation.RequiresUser;
import com.awifi.np.biz.timebuysrv.web.module.time.model.PortalParam;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionDTO;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionUser;
import com.awifi.np.biz.timebuysrv.web.module.time.service.TimeBuyService;
import com.awifi.np.biz.timebuysrv.web.module.time.service.WifiService;
import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;

@APIs(description = "网络放通")
@Controller
@RequestMapping(value = "/timebuysrv/access")
public class AuthController extends BaseController {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * WifiService 设备总线服务 
     */
    @Resource
    private WifiService wifiService;

    /**
     * TimeBuyService 时长服务
     */
    @Resource
    private TimeBuyService timeBuyService;

    /**
     * 接入全局认证, 网络放通.
     * 
     * @param request
     *            request
     * @return Map
     * @throws Exception
     *             异常
     * @author 尤小平
     * @date 2017年4月18日 下午3:02:59
     */
    @SuppressWarnings("rawtypes")
    @API(summary = "正式放通/临时放通", description = "{ \"code\": \"0\", \"data\": {\"token\":\"H1asuiasdjfklasjdf@awifi.com\",\"poratlParam\":\"Object\",\"buyurl\":\"url\",\"paytype\":1},\"msg\":\"失败原因\" }", parameters = {
            @Param(name = "paytype", description = "用于前端展示, 1:临时放通", dataType = DataType.INTEGER, required = false) })
    @RequestMapping(value = "/auth", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map auth(HttpServletRequest request) throws Exception {
        logger.debug("接入全局认证, params=" + JsonUtil.toJson(request.getParameterMap()));
        // 1.参数获取
        SessionDTO sessionDTO = (SessionDTO) request.getSession().getAttribute(Constants.SESSION_DTO);
        String username = request.getParameter("username");
        Integer paytype = CastUtil.toInteger(request.getParameter("paytype"));

        Map<String, Object> result = new HashMap<String, Object>();

       
         logger.info("开始认证");
        // 用于前端展示
        result.put("paytype", paytype);
        // 前一种情况是为了预防开着浏览器从一台路由器转移到另外一台路由器
        // 说明是临时放通
        if (paytype != null && paytype == 1) {
            // 当日认证次数判断
            String times = getTimesFromRedis(Constants.REDIS_TEMP_PASS_TIMES + username);
            if (!(times != null && Integer.parseInt(times) >= 3)) {
                // 临时放通
                wifiService.accessAccountAuth4MD5(sessionDTO, true);
                result.put("token", sessionDTO.getAuthResult().getData());
            } else {
                result.put("msg", "已超过当日认证次数");
            }
        } else {
            // 2.通过redis缓存判断设备是否匹配
            // 3.获取用户信息
            // 4.认证并放行网络
            wifiService.accessAccountAuth4MD5(sessionDTO, false);
            result.put("token", sessionDTO.getAuthResult().getData());

        }
        result.put("poratlParam", sessionDTO.getPortalParam());
        result.put("buyurl", timeBuyService.getBuyUrl(sessionDTO));
        return this.successMsg(result);
    }

    /**
     * 园区app调用 接入全局认证, 网络放通.
     * 
     * @param request
     *            request
     * @return Map
     * @throws Exception
     *             异常
     * @author 张智威
     * @date 2017年4月18日 下午3:02:59
     */
    @SuppressWarnings("rawtypes")
    @API(summary = "正式放通对外接口(园区app调用)", description = "返回参数{ \"code\": \"0\", \"data\": \"H1asuiasdjfklasjdf@awifi.com\" ,\"msg\":\"失败原因\"}", parameters = {
            @Param(name = "deviceId", description = "设备id", dataType = DataType.STRING, required = true),
            @Param(name = "terMac", description = "终端mac", dataType = DataType.MAC_SHORT, required = true),
            @Param(name = "apMac", description = "设备Mac", dataType = DataType.MAC_SHORT, required = true),
            @Param(name = "ip", description = "用户ip", dataType = DataType.IP, required = true),
            @Param(name = "port", description = "用户端口", dataType = DataType.PORT, required = true),
            @Param(name = "telephone", description = "手机号码", dataType = DataType.PHONE, required = true) ,
            
    
            @Param(name = "logId", description = "日志id", dataType = DataType.STRING, required = false),
            @Param(name = "gwAddress", description = "设备内网地址", dataType = DataType.IP, required = false),
            @Param(name = "gwPort", description = "设备端口", dataType = DataType.PORT, required = false),
            @Param(name = "nasName", description = "设备名称", dataType = DataType.STRING, type = "string",required = false),
            @Param(name = "userType", description = "userType(NEW_USER,EXEMPT_AUTH_USER)", dataType = DataType.ARRAY, items = "NEW_USER,EXEMPT_AUTH_USER", required = false),
            @Param(name = "token", description = "token", dataType = DataType.STRING ,required = false),
            @Param(name = "url", description = "url", dataType = DataType.URL,required = false)
    })
    @RequestMapping(value = "/auth/api", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @RequiresUser
    public Map authApi(HttpServletRequest request) throws Exception {
        logger.debug("接入全局认证, params=" + JsonUtil.toJson(request.getParameterMap()));
        SessionDTO sessionDTO = new SessionDTO();

        String deviceId = request.getParameter("deviceId");
        // String apMac = request.getParameter("apMac");
        String telephone = request.getParameter("telephone");
        String terMac = request.getParameter("terMac");
        String ip = request.getParameter("ip");
        String port = request.getParameter("port");
        sessionDTO.setRequestIp(ip);
        sessionDTO.setRequestIpPort(port);
        //反查设备信息 的apMac 判断踢下线需要匹配用户是否在第二台设备登录
        Device device = DeviceClient.getByDevId(deviceId);
        sessionDTO.setMerchant(device);
        PortalParam portalParam = new PortalParam();
        portalParam.setUserMac(terMac);
        portalParam.setDeviceId(deviceId);
        sessionDTO.setPortalParam(portalParam);
        //查用户的信息 需要用userid 去匹配用户的时长信息 判断用户是否有时长
        PubUserAuth pubUser = UserAuthClient.getUserByLogName(telephone);
        SessionUser sessionUser = new SessionUser();
        sessionUser.setId(pubUser.getUserId());
        //用户的密码 放通是需要
        sessionUser.setAuthPswd(pubUser.getAuthPswd());
        sessionUser.setPhone(telephone);
        sessionDTO.setSessionUser(sessionUser);
        sessionDTO.setTimeInfo(timeBuyService.getUserTimeInfo(sessionUser.getId(), telephone, device.getMerchantId()));
        //Map<String, Object> result = new HashMap<String, Object>();
        // 4.认证并放行网络
        try {
            wifiService.accessAccountAuth4MD5(sessionDTO, false);
        } catch (Exception ex) {
            logger.info("认证异常");
            if(sessionDTO.getTimeInfo().isBuyout()){
                logger.info("是买断商户,不临时放通");
                throw ex;
            }
            logger.info("时长不够开始临时放通");
            if(ex instanceof BizException && ((BizException) ex).getCode().equals("E0464102")){
                // 临时放通
                wifiService.accessAccountAuth4MD5(sessionDTO, true);
                Map<String, Object> resultMap = this.successMsg((Object)sessionDTO.getAuthResult().getData());
                resultMap.put("code", "E5066005");// 园区app临时放通返回码: 上网时长不够,临时放通
                return resultMap;
            }
        }

        //result.put("token", sessionDTO.getAuthResult().getData());
        return this.successMsg((Object)sessionDTO.getAuthResult().getData());
    }
    
    /**
     * 园区app调用, 临时放通.
     * 
     * @param request 请求
     * @return Map<String, Object>
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月31日 下午8:01:21
     */
    @API(summary = "临时放通对外接口(园区app调用)", description = "返回参数{ \"code\": \"0\", \"data\": \"H1asuiasdjfklasjdf@awifi.com\" ,\"msg\":\"失败原因\"}", parameters = {
            @Param(name = "deviceId", description = "设备id", dataType = DataType.STRING, required = true),
            @Param(name = "terMac", description = "终端mac", dataType = DataType.MAC_SHORT, required = true),
            @Param(name = "ip", description = "用户ip", dataType = DataType.IP, required = true),
            @Param(name = "port", description = "用户端口", dataType = DataType.PORT, required = true),
            @Param(name = "telephone", description = "手机号码", dataType = DataType.PHONE, required = true) ,
            
            @Param(name = "apMac", description = "设备Mac", dataType = DataType.MAC_SHORT, required = false),
            @Param(name = "logId", description = "日志id", dataType = DataType.STRING, required = false),
            @Param(name = "gwAddress", description = "设备内网地址", dataType = DataType.IP, required = false),
            @Param(name = "gwPort", description = "设备端口", dataType = DataType.PORT, required = false),
            @Param(name = "nasName", description = "设备名称", dataType = DataType.STRING, type = "string",required = false),
            @Param(name = "userType", description = "userType(NEW_USER,EXEMPT_AUTH_USER)", dataType = DataType.ARRAY, items = "NEW_USER,EXEMPT_AUTH_USER", required = false),
            @Param(name = "token", description = "token", dataType = DataType.STRING ,required = false),
            @Param(name = "url", description = "url", dataType = DataType.URL,required = false)
    })
    @RequestMapping(value = "/temp/auth/api", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @RequiresUser
    public Map<String, Object> tempAuthApi(HttpServletRequest request) throws Exception {
        logger.info("临时放通, params=" + JsonUtil.toJson(request.getParameterMap()));

        // 参数获取
        String deviceId = request.getParameter("deviceId");
        String telephone = request.getParameter("telephone");
        String terMac = request.getParameter("terMac");
        String ip = request.getParameter("ip");
        String port = request.getParameter("port");

        // 参数校验
        ValidUtil.valid("手机号码", telephone, "required");
        ValidUtil.valid("设备id", deviceId, "required");

        String times = RedisUtil.get(Constants.REDIS_TEMP_PASS_TIMES + telephone);
        if (times != null && Integer.parseInt(times) >= 3) {
            // 超过今日临时放通次数
            throw new BizException("E5066003", MessageUtil.getMessage("E5066003"));
        }

        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setRequestIp(ip);
        sessionDTO.setRequestIpPort(port);
        // 反查设备信息 的apMac 判断踢下线需要匹配用户是否在第二台设备登录
        Device device = DeviceClient.getByDevId(deviceId);
        sessionDTO.setMerchant(device);
        PortalParam portalParam = new PortalParam();
        portalParam.setUserMac(terMac);
        portalParam.setDeviceId(deviceId);
        sessionDTO.setPortalParam(portalParam);
        // 查用户的信息 需要用userid 去匹配用户的时长信息 判断用户是否有时长
        PubUserAuth pubUser = UserAuthClient.getUserByLogName(telephone);
        SessionUser sessionUser = new SessionUser();
        sessionUser.setId(pubUser.getUserId());
        // 用户的密码,放通时需要
        sessionUser.setAuthPswd(pubUser.getAuthPswd());
        sessionUser.setPhone(telephone);
        sessionDTO.setSessionUser(sessionUser);
        sessionDTO.setTimeInfo(timeBuyService.getUserTimeInfo(sessionUser.getId(), telephone, device.getMerchantId()));

        // 临时放通
        wifiService.accessAccountAuth4MD5(sessionDTO, true);
        logger.info("临时放通认证结束, token=" + sessionDTO.getAuthResult().getData());

        return this.successMsg((Object) sessionDTO.getAuthResult().getData());
    }
    
    /**
     * for testing only.
     * 
     * @param key
     *            key
     * @return String
     * @author 尤小平
     * @date 2017年4月21日 下午4:38:42
     */
    protected String getTimesFromRedis(String key) {
        return RedisUtil.get(key);
    }

    /**
     * for testing only.
     * 
     * @param wifiService
     *            用于设备总线服务
     * @author 尤小平
     * @date 2017年4月18日 下午5:10:42
     */
    public void setWifiService(WifiService wifiService) {
        this.wifiService = wifiService;
    }

    /**
     * for testing only.
     * 
     * @param timeBuyService
     *            TimeBuyService 时长购买service
     * @author 尤小平
     * @date 2017年4月18日 下午5:11:01
     */
    public void setUserWifiTime(TimeBuyService timeBuyService) {
        this.timeBuyService = timeBuyService;
    }
}

package com.awifi.np.biz.timebuysrv.web.module.time.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.awifi.np.biz.common.redis.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.timebuysrv.util.StringUtil;
import com.awifi.np.biz.timebuysrv.web.core.Constants;
import com.awifi.np.biz.timebuysrv.web.module.time.model.PortalParam;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionDTO;
import com.awifi.np.biz.timebuysrv.web.module.time.model.UserCutoff;
import com.awifi.np.biz.timebuysrv.web.module.time.model.UserTimeInfo;
import com.awifi.np.biz.timebuysrv.web.module.time.service.TimeBuyService;
import com.awifi.np.biz.timebuysrv.web.module.time.service.UserCutoffService;
import com.awifi.np.biz.timebuysrv.web.util.ConfigUtil;
import com.awifi.np.biz.timebuysrv.web.util.PortalUtil;
import com.awifi.np.biz.timebuysrv.web.util.RequestUtil;
import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIResponse;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;

@APIs(description = "时间组件全信息接口")
@Controller
@RequestMapping("/timebuysrv/time")
public class TimeController extends BaseController {
    /** * Logger 引入 . */
    private static Logger logger = LoggerFactory.getLogger(TimeController.class);
    /** * 引入 packageLogicService . */

    /** * 引入 timeBuyService . 这里主要提供一个购买链接 */
    @Resource
    private TimeBuyService timeBuyService;
    
    
    /**  用户剩余时长服务 **/
    @Resource
    private UserCutoffService userCutoffService;

    /** * 引入AccessAuth . */

    /**
     * 登录页接口 此方法的主要功能是 step 1接受页面portal参数,加入到sessionDTO中
     * ,并初始化商户信息(存入到session中,如果有就不用),并把商户 用户信息返回给页面 测试urll:
     * http://127.0.0.1:8081/static/html/merchant/index.html?logId=logId&
     * deviceId=FatAP_31_201603181c537d5c-2333-4611-9d0d-3f5db479e4a6&
     * mobilePhone=13958173965&userMac=1C184A15A5D9&userIP=192.168.10.15&
     * gwAddress=192.168.10.1&gwPort=2020&nasName=&userType=NEW_USER&token=
     * token_test&url=www.163.com
     * 
     * @param request
     *            request
     * @return ResultDTO {asdfasdfasdf}
     * @author zzw
     */
    @API(summary = "时间组件全信息接口", description = "MerchantController.java:index 此方法的主要功能是 step 1接受页面portal参数,加入到sessionDTO中 ,并初始化商户信息(存入到session中,如果有就不用),并把商户 用户信息返回给页面", parameters = {

            @Param(name = "logId", description = "日志id", dataType = DataType.STRING, required = true),
            @Param(name = "deviceId", description = "设备id", dataType = DataType.STRING, required = true),
            @Param(name = "mobilePhone", description = "手机号码", dataType = DataType.PHONE, required = false),
            @Param(name = "userMac", description = "设备mac地址", dataType = DataType.MAC_SHORT, required = true),
            @Param(name = "userIP", description = "用户ip", dataType = DataType.IP, required = false),
            @Param(name = "gwAddress", description = "设备内网地址", dataType = DataType.IP, required = false),
            @Param(name = "gwPort", description = "设备端口", dataType = DataType.PORT, required = false),
            @Param(name = "nasName", description = "设备名称", dataType = DataType.STRING, type = "string"),
            @Param(name = "userType", description = "userType(NEW_USER,EXEMPT_AUTH_USER)", dataType = DataType.ARRAY, items = "NEW_USER,EXEMPT_AUTH_USER", required = true),
            @Param(name = "token", description = "token", dataType = DataType.STRING),
            @Param(name = "url", description = "url", dataType = DataType.URL), })
    @APIResponse(value = "{ \"r\": 0, \"data\": { \"now_date\": 1489975765652, \"merchant\": {\"merchantId\": 5597, \"merchantName\": \"凌云公寓\", 'broadbandAccount': '宽带账号电话号码' }, user:{'phone':13958173965,name:'用户名'},timeInfo:{'vip':'是否vip',canGetFreePgk:'1:是能领取免费礼包 0:不能领取',endTime:'1489975765652时长时间到期时间''},\"topPicList\": [\"123.jpg\"] }, \"msg\": \"错误原因\" }\n}")
    @RequestMapping(value = "info", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map index(PortalParam portalParam, HttpServletRequest request) throws Exception {
        logger.info("request params=" + JsonUtil.toJson(portalParam));
        // Map<String, Object> ret = new HashMap<String, Object>();
        // step 1接受页面portal参数,加入到sessionDTO中
        // 检查session中是否有 dto了
        if ("EXEMPT_AUTH_USER".equals(portalParam.getUserType())) {
            if (StringUtil.isBlank(portalParam.getMobilePhone())) {
                throw new BizException("E5071014", MessageUtil.getMessage("E5071014"));
            } 
        }
        // 获取sessionDTO看是不是已经登录过了
        SessionDTO sessionDTO = (SessionDTO) request.getSession().getAttribute(Constants.SESSION_DTO);
        boolean changeMerchantIdFlag = true;
        if (sessionDTO == null) {// 第一次访问
            sessionDTO = new SessionDTO();
            sessionDTO.setPortalParam(portalParam);
            request.getSession().setAttribute(Constants.SESSION_DTO, sessionDTO);
        } else {
            // 非第一次访问首页
            // 如果portal 参数不是空的 就用portal参数替换session中的数据
            if (portalParam != null && portalParam.getDeviceId() != null) {
                if (sessionDTO.getPortalParam() != null
                        && portalParam.getDeviceId().equals(sessionDTO.getPortalParam().getDeviceId())) {
                    // 说明设备没发生了变化 用户只是刷新了页面而已
                    changeMerchantIdFlag = false;
                } else {
                }
                sessionDTO.setPortalParam(portalParam);
            } else {
                logger.error("this param dto should not be null and meantime the sessionDto is also null");
                throw new ValidException("E5071005", MessageUtil.getMessage("E5071005"));
            }
        }
        // localhost 的时候取到的ip 和端口号有问题
        String requestIp = RequestUtil.getIp(request);
        logger.info("request ip=" + requestIp );
        if("127.0.0.1".equals(requestIp)){
            requestIp="192.168.3.15";
        }
        String requestIpPort = request.getHeader("X-Forwarded-Port");
        logger.info("request port=" + requestIpPort);
        sessionDTO.setRequestIp(requestIp);
        sessionDTO.setRequestIpPort(requestIpPort);

        // 根据设备查询商户信息
        // 如果session为空 || session中的account_id为空 || url中的account_id 改变 ||
        // url中的devId改变， 则更新session
        // step 2 并初始化商户信息(存入到session中,如果有就不用)
        // 检查原来的deviceId 和现在的DeviceId 是否一致 如果不一致 还要重新根据deviceId查询商户信息
        if (sessionDTO.getMerchant() == null || changeMerchantIdFlag) {
            Device device = DeviceClient.getByDevId(sessionDTO.getPortalParam().getDeviceId());
            if (device == null) {// 没有查到设备说明参数错误
                logger.error("this deviceId can't find merchantInfo:" + sessionDTO.getPortalParam().getDeviceId());
                throw new ValidException("E5071005", MessageUtil.getMessage("E5071005") );
            }
            sessionDTO.setMerchant(device);
            
            //查找是否是买断商户
            //TODO 优化 现在redis 读取的都是同一台 
        }else{//新加这一段为了防止用户从购买完成后跳回页面发现时间并没有增长
            //刷新用户剩余时间
            UserTimeInfo timeInfo = sessionDTO.getTimeInfo();
            if(timeInfo!=null &&sessionDTO.getMerchant()!=null && sessionDTO.getSessionUser()!=null ){
                UserCutoff userCutoff = userCutoffService.selectByMerIdAndUserId(sessionDTO.getMerchant().getMerchantId(), sessionDTO.getSessionUser().getId());//查找该商户下该用户的剩余时长
                
                if (userCutoff != null) {//如果为空说明没有时长 并且没有领取过免费礼包
                    timeInfo.setEndTime(userCutoff.getCutoffDate().getTime());
                }
            }
        }
        return this.successMsg(PortalUtil.getDetailInfo(sessionDTO));
    }

    /**
     * 时间组件获取全信息接口
     * 
     * @param request
     * @return Map
     * @author zhangzw
     * @throws Excpetion
     *             异常
     */
    @API(summary = "时间组件全信息接口", description = "返回当前人的时间信息"
            + "返回结果说明 { \"code\": \"0/*表示正确结果*/\", \"data\": { \"nowDate\": 1493002975623, "
            + "\"timeInfo\": { \"canGetFreePkg\": true/*是否能获取免费礼包*/, \"endTime\": 1493089375623/*当前用户时长截止时间*/, \"vip\": false/*是否vip*/, \"netStatus\": false/*当前网络放通状态 */}, "
            + "  \"canFastLogin\": false/*是否能直接登录*/,"
            + "  \"user\": { \"role\": \"admin\"/*角色*/, \"phone\": \"13958173965\" /*登录人的手机号码*/}, "
            + "\"merchant\": { \"merchantId\": 10/*当前商户id*/, \"merchantName\": \"陕西味儿面馆\"/*当前商户名称*/ } }}", parameters = {})
    @APIResponse(value = "")
    @RequestMapping(value = "info", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String, Object> info(HttpServletRequest request) throws Exception {
        SessionDTO sessionDTO = (SessionDTO) request.getSession().getAttribute(Constants.SESSION_DTO);
        return this.successMsg(PortalUtil.getDetailInfo(sessionDTO));
    }

    /**
     * 获取是否vip 时长 能否领取免费礼包信息
     * 
     * @param request 请求
     * @return Map
     * @author zhangzw
     * @throws Exception 异常
     */
    @API(summary = "园区app接口根据用户id商户id获取上网时长相关信息", description = "园区app接口根据用户id商户id获取获取是否vip 时长 能否领取免费礼包信息"
            + "返回结果说明 { \"code\": \"0/*表示正确结果*/\", \"data\": { \"nowDate\": 1493002975623, \"buyurl\":\"url\", "
            + "\"timeInfo\": { \"canGetFreePkg\": true/*是否能获取免费礼包*/, \"endTime\": 1493089375623/*当前用户时长截止时间*/, \"vip\": false/*是否vip*/, \"buyout\": false/*是否是买断商户 */}, "
            + "}}", parameters = {
                    @Param(name = "merchantId", description = "商户id", dataType = DataType.LONG, required = true),
                    @Param(name = "telephone", description = "手机号码", dataType = DataType.PHONE, required = true) })
    @APIResponse(value = "")
    @RequestMapping(value = "info/api", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String, Object> getUserTimeInfo(HttpServletRequest request) throws Exception {
        // 获取参数
        logger.info("request params= " + JsonUtil.toJson(request.getParameterMap()));
        Long merchantId = CastUtil.toLong(request.getParameter("merchantId"));
        String telephone = request.getParameter("telephone");

        // 校验参数
        ValidUtil.valid("商户id", merchantId, "required");
        ValidUtil.valid("手机号码", telephone, "required");

        // 获取用户id
        PubUserAuth user = UserAuthClient.getUserByLogName(telephone);
        // 获取上网时长信息
        UserTimeInfo timeInfo = timeBuyService.getUserTimeInfo(user.getUserId(), telephone, merchantId);

        // 获取宽带账号
        String broadAccount = RedisUtil.hget(Constants.REDIS_MSP_BROAD_ACCOUNT, merchantId + "");

        if (StringUtil.isBlank(broadAccount)) {
            // 查询商户底下所有设备 取出相关的一条设备的宽带账号信息
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("merchantId", merchantId);
            param.put("merchantQueryType", "this");
            List<Device> list = DeviceClient.getListByParam(JsonUtil.toJson(param));

            if (list != null && list.size() > 0) {
                broadAccount = list.get(0).getBroadbandAccount();
                logger.info("broadAccount=" + broadAccount);
            }

            if(StringUtils.isNotBlank(broadAccount)) {
                RedisUtil.hset(Constants.REDIS_MSP_BROAD_ACCOUNT, merchantId + "", broadAccount);
            }
        }

        // 返回上网时长信息
        HashMap<String, Object> returnMap = new HashMap<>();
        returnMap.put("timeinfo", timeInfo);
        returnMap.put("nowDate", new Date().getTime());
        returnMap.put("buyurl", getBuyUrl(telephone, merchantId, broadAccount));// 购买url

        logger.info("获取到上网时长相关信息: " + JsonUtil.toJson(returnMap));
        return this.successMsg(returnMap);
    }

    /**
     * 获取购买url.
     * 
     * @param telephone 用户手机号码
     * @param merchantId 商户id
     * @param broadAccount 宽带账号
     * @return 购买url
     * @author 尤小平  
     * @date 2017年8月8日 上午10:12:56
     */
    public static String getBuyUrl(String telephone, Long merchantId, String broadAccount) {
        StringBuffer sb = new StringBuffer("");
        sb.append(ConfigUtil.getConfig("dq.buy.url")).append("&link_phone=").append(telephone).append("&code_number=")
                .append(broadAccount).append("&yxgh=").append(merchantId);
        return sb.toString();
    }
    
    /**
     * 获取配置信息
     * 
     * @param request
     * @return
     * @author 张智威
     * @date 2017年6月10日 上午9:44:48
     */
    @API(summary = "获取配置属性", description = "", parameters = {
            @Param(name = "key", description = "key", dataType = DataType.STRING, required = true) })
    @RequestMapping(value = "config", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String, Object> getConfig(HttpServletRequest request) {
        String key = request.getParameter("key");
        return this.successMsg(ConfigUtil.getConfig(key));
    }

    /**
     * 设置配置信息
     * 
     * @param request
     * @return
     * @author 张智威
     * @date 2017年6月10日 上午9:44:48
     */
    @API(summary = "设置配置属性", description = "", parameters = {
            @Param(name = "key", description = "key", dataType = DataType.STRING, required = true),
            @Param(name = "value", description = "value", dataType = DataType.STRING, required = true) })
    @RequestMapping(value = "config", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    public Map<String, Object> setConfig(HttpServletRequest request) {
        String key = request.getParameter("key");
        String value = request.getParameter("value");
        ConfigUtil.setConfig(key, value);
        return this.successMsg(ConfigUtil.getConfig(key));
    }

    public TimeBuyService getTimeBuyService() {
        return timeBuyService;
    }

    public void setTimeBuyService(TimeBuyService timeBuyService) {
        this.timeBuyService = timeBuyService;
    }
}

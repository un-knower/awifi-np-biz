package com.awifi.np.biz.timebuysrv.web.module.time.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.api.client.dbcenter.user.model.PubUserAuth;
import com.awifi.np.biz.api.client.dbcenter.user.util.UserAuthClient;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.timebuysrv.web.module.time.model.UserCutoff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.RandomUtil;
import com.awifi.np.biz.timebuysrv.web.module.time.service.TimeBuyService;
import com.awifi.np.biz.timebuysrv.web.module.time.service.WifiService;
import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIResponse;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;

@APIs(description = "时长购买服务对外api接口")
@Controller
@RequestMapping("/timebuysrv/time/api")
public class TimeApiController extends BaseController {
    /** * Logger 引入 . */
    private static Logger logger = LoggerFactory.getLogger(TimeApiController.class);
    /** * 引入 packageLogicService . */
   
    /** * 引入 timeBuyService . 这里主要提供一个购买链接 */
    @Resource
    private TimeBuyService timeBuyService;
    /** * 引入AccessAuth . */
   
    /**
     * AccessAuthService
     */
    @Resource
    private WifiService wifiService;
    
    
    /**
     * 园区app接口获取token
     * @param request
     * @return Map 
     * @author zhangzw
     * @throws Exception 异常
     */
    @API(summary = "园区app接口获取token ", description = "token 10分钟有效返回参数{ \"code\": \"0\", \"data\": \"H1asuiasdjfklasjdf@awifi.com\" ,\"msg\":\"失败原因\"}"
         , 
            parameters = { @Param(name = "appkey", description = "appkey应用key", dataType = DataType.LONG, required = true),
                    @Param(name = "token", description = "token", dataType = DataType.PHONE, required = true)}
    )
                  
    @APIResponse(value = "")
    @RequestMapping(value = "token", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String,Object> token( HttpServletRequest request) throws Exception {
        String appkey = request.getParameter("merchantId");
        String token = RandomUtil.getRandomNumber(10);
        RedisUtil.set(token, appkey, 10*60);
        return this.successMsg(token);
    }

    /**
     * 园区app时长信息对外接口.
     * 
     * @param request 请求
     * @return Map<String, Object>
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月31日 下午4:42:01
     */
    @API(summary = "园区app-园区剩余时长信息", description = "返回园区剩余时长信息 {\"code\": \"0\",\"data\": [{\"endTime\": \"1496825302000(可用截至日期时间戳)\",\"merchantName\": \"爱WiFi默认微站(园区名称)\"}]}", parameters = {
                    @Param(name = "telephone", description = "手机号码", dataType = DataType.PHONE, required = true) })
    @APIResponse(value = "{}")
    @RequestMapping(value = "/lefttime", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String, Object> getLeftTimeApi(HttpServletRequest request) throws Exception {
        // 获取参数
        logger.info("request params=" + JsonUtil.toJson(request.getParameterMap()));
        String telephone = request.getParameter("telephone");// 用户手机号码

        // 参数校验
        ValidUtil.valid("用户手机号码", telephone, "required");

        // 获取用户信息, 获取用户id
        PubUserAuth user = UserAuthClient.getUserByLogName(telephone);
        Long userId = user.getUserId();

        // 获取园区时长信息
        List<UserCutoff> list = timeBuyService.getUserCutoffList(userId, null);

        // 返回数据
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        for (UserCutoff userCutoff : list) {
            Map<String, Object> returnMap = new HashMap<>();
            returnMap.put("merchantName", MerchantClient.getNameByIdCache(userCutoff.getMerchantId()));
            returnMap.put("endTime", userCutoff.getCutoffDate().getTime());
            returnList.add(returnMap);
        }

        logger.info("调用了园区app时长信息查到了:" + JsonUtil.toJson(returnList));
        return this.successMsg(returnList);
    }
    
   
    
    public TimeBuyService getTimeBuyService() {
        return timeBuyService;
    }

    public void setTimeBuyService(TimeBuyService timeBuyService) {
        this.timeBuyService = timeBuyService;
    }

 
}

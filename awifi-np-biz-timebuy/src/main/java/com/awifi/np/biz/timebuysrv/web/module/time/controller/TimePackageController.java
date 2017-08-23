package com.awifi.np.biz.timebuysrv.web.module.time.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.api.client.dbcenter.user.model.PubUserAuth;
import com.awifi.np.biz.api.client.dbcenter.user.util.UserAuthClient;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.timebuysrv.web.core.Constants;
import com.awifi.np.biz.timebuysrv.web.core.annotation.RequiresUser;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionDTO;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionUser;
import com.awifi.np.biz.timebuysrv.web.module.time.model.TimePackage;
import com.awifi.np.biz.timebuysrv.web.module.time.service.TimeBuyService;
import com.awifi.np.biz.timebuysrv.web.module.time.service.TimePackageService;
import com.awifi.np.biz.timebuysrv.web.module.time.service.WifiService;
import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIResponse;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;

@APIs(description = "时间套餐接口")
@Controller
@RequestMapping("/timebuysrv/time/pkg")
public class TimePackageController extends BaseController {
    /** * Logger 引入 . */
    private static Logger logger = LoggerFactory.getLogger(TimePackageController.class);
    /** * 引入 packageLogicService . */
    @Resource
    private TimePackageService timePackageService;

    /** * 引入 timeBuyService . */
    @Resource
    private TimeBuyService timeBuyService;

    /** * 引入AccessAuth . */
    @Resource
    private WifiService wifiService;

    /**
     * 商户免费套餐领取接口
     * 
     * @param request
     *            请求
     * @return Map 结果
     * @author zhangzw
     * @throws Exception
     *             异常
     * @date 2017-3-30 14:48:01
     */
    @API(summary = "领取商户免费套餐", description = "根据存入在session中的商户id 用户id去判断是否领取过免费礼包 如果领取过就不再领取,如果没有时长领取成功会返回token 用于认证放行"
            + "返回portalParam:portal参数 ,buyurl:购买链接,token:请求放通token")
    @RequestMapping(value = "get", method = RequestMethod.GET, produces = "application/json")
    @RequiresUser
    @ResponseBody
    public Map<String, Object> getFreePackage(HttpServletRequest request) throws Exception {
        // 从session中获取sessionDTO
        SessionDTO sessionDTO = (SessionDTO) request.getSession().getAttribute(Constants.SESSION_DTO);
        if (sessionDTO == null || sessionDTO.getMerchant() == null || sessionDTO.getSessionUser() == null
                || sessionDTO.getMerchant().getMerchantId() == null || sessionDTO.getSessionUser().getId() == null) {
            logger.error("商户id 和用户id 丢失 用户未登录");
            throw new ValidException("E4005001", MessageUtil.getMessage("E4005001"));// 用户未登录
        }
        // 判断是否是vip 如果是vip 就不用再领取免费礼包了 ,领取了就浪费了
        if (sessionDTO.getTimeInfo() != null && sessionDTO.getTimeInfo().isVip()) {
            logger.error("已经是vip用户了,不用领取");
            throw new BizException("E5054004", MessageUtil.getMessage("E5054004"));// 已经是vip用户
                                                                                   // 不用领取免费礼包
        }

        Long merchantId = sessionDTO.getMerchant().getMerchantId();
        SessionUser sessionUser = sessionDTO.getSessionUser();
        Long userId = sessionUser.getId();// userid
        String phone = sessionUser.getPhone();// 手机号码
        // 验证是否领取成功,更新已经领取后的剩余时长,以及是否是vip
        String canGetFreePkgFlag = RedisUtil.get(Constants.REDIS_PKG_GET + userId + "" + merchantId);
        // 标志位判断是否能领取
        if (!"ok".equals(canGetFreePkgFlag) || !sessionDTO.getTimeInfo().isCanGetFreePkg()) {
            logger.error("用户不能领取免费礼包");// 正常情况下用户是不能访问到这里的 除非前端逻辑错误
            throw new ValidException("E4005002", MessageUtil.getMessage("E4005002"));// 用户不能领取免费礼包
        }

        // 领取免费礼包
        Map<String, Object> ret = timeBuyService.getFreePkgAndUpdateTime(merchantId, userId);
        // 返回portal 参数用于设备放通
        ret.put("poratlParam", sessionDTO.getPortalParam()); 
        // 返回购买url
        ret.put("buyurl", timeBuyService.getBuyUrl(sessionDTO));
        sessionDTO.getTimeInfo().setEndTime((Long) (ret.get("end_time")));
        sessionDTO.getTimeInfo().setCanGetFreePkg(false);

        // 开始领取免费包网络放通
        wifiService.accessAccountAuth4MD5(sessionDTO, false);
        // 设置全局的时长信息
        sessionDTO.setTimeInfo(timeBuyService.getUserTimeInfo(userId, phone, merchantId));
        // 返回token 用于设备放通
        if (sessionDTO.getAuthResult() != null) {
            ret.put("token", sessionDTO.getAuthResult().getData());
        }
        return this.successMsg(ret);
    }

    /**
     * 园区app领取免费礼包对外接口
     * 
     * @param request
     *            请求
     * @return Map
     * @throws Exception
     *             异常
     * @author 张智威
     * @date 2017年6月8日 上午10:33:25
     */
    @API(summary = "领取免费礼包对外接口", description = "领取免费礼包接口返回: {\"code\":0,\"data\":{\"endTime\":\"14388881231时间到期毫秒数\"},\"msg\":\"错误信息\"}",

            parameters = { @Param(name = "merchantId", description = "商户id", dataType = DataType.LONG, required = true),
                    @Param(name = "telephone", description = "手机号码", dataType = DataType.PHONE, required = true) })
    @APIResponse(value = "")
    @RequestMapping(value = "get/api", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String, Object> getFreePackageApi(HttpServletRequest request) throws Exception {

        Long merchantId = Long.valueOf(request.getParameter("merchantId"));
        String telephone = request.getParameter("telephone");

        PubUserAuth user = UserAuthClient.getUserByLogName(telephone);
        // 如果是手机的就跳过redis 校验 实际上还是
        // TODO 存疑 实际上也是查询过是否能领取免费礼包的
        RedisUtil.set(Constants.REDIS_PKG_GET + user.getUserId() + "" + merchantId, "ok", 10 * 60);
        // 套餐包id获取
        Map<String, Object> ret = timeBuyService.getFreePkgAndUpdateTime(merchantId, user.getUserId());

        HashMap<String, Object> result = new HashMap<>();
        result.put("endTime", ret.get("end_time"));

        // 是否领取成功,已经领取后的剩余时长,以及是否是vip
        return this.successMsg(ret);
    }

    /**
     * 商户免费套餐保存接口 后台管理系统调用
     * 
     * @return ResultDTO
     * @author zzw
     * @date 2017-3-30 14:48:01
     */
    @API(summary = "商户免费套餐保存接口 后台管理系统调用",consumes = "application/json", description = "返回{ code: 0 }管理系统设置商户免费套餐 单位小时", parameters = {
            /*@Param(name = "merchantId", description = "商户id", in="body" ,dataType = DataType.LONG, required = true),
            @Param(name = "hours", description = "免费礼包设置时间",in="body", dataType = DataType.INTEGER, required = true)*/ 
    })
    
    @APIResponse("成功情况:{ \"code\": \"0\"}, 错误情况:{ \"msg\": \"未知异常!\", \"code\": \"E2000019\" }")
    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String, Object> saveMerchantFreePackage(@RequestBody(required = true) Map<String, Object> bodyParam,
            HttpServletRequest request) throws Exception {

        // 获取参数
        String merchantIdStr = bodyParam.get("merchantId") + "";
        ValidUtil.valid("商户id", merchantIdStr, "{'required':true}");
        String merchantIdAry[] = merchantIdStr.split(",");
        for (String merchantIdS : merchantIdAry) {
            ValidUtil.valid("商户id", merchantIdS, "{'required':true,'numeric':true}");
            Long merchantId = Long.valueOf(merchantIdS);

            Integer hours = Integer.valueOf(bodyParam.get("hours") + "");
            // 查询是否有设置过免费礼包
            TimePackage pkg = timePackageService.queryFreePkgByMerId(merchantId);
            // 有就更新
            if (pkg != null && pkg.getId() != null && pkg.getId() != 0) {// 更新
                pkg.setPackageValue(hours.floatValue()/24);
                timePackageService.update(pkg);
            } else {// 没有就 新增
                TimePackage merPac = new TimePackage();
                merPac.setMerchantId(merchantId);
                merPac.setCreateDate(new Date());
                // 新增免费礼包设置 type为2
                merPac.setPackageType(2);
                // 201是免费礼包的key
                merPac.setPackageKey(201);
                merPac.setStatus(1);// 可用
                merPac.setStatusDate(new Date());// 设置时间
                merPac.setPackageValue(hours.floatValue()/24);// 免费礼包的时间
                                                           // 存入数据库是以天为单位的
                                                           // 本次调整会影响到领取免费礼包的领取
                timePackageService.add(merPac);
            }

        }

        return this.successMsg();

    }

    /**
     * 查询免费礼包套餐
     * 
     * @Description: queryListByParam
     * @param request
     *            请求踢
     * @param params
     *            {merchantId:123123}
     * @return ResultDTO 返回类型
     * @author zhangzw
     * @throws Exception
     *             异常
     * @date 2016年12月21日 下午3:40:35
     */
    @API(summary = "查询免费礼包套餐", description = "返回免费礼包小时数 单位小时 { \"code\": \"0\", \"data\": {\"hours\":12}} 根据查询显示该商户底下免费礼包套餐 PackageController.java:queryListByParam  ", parameters = {
            @Param(name = "access_token", description = "安全令牌，不允许为空", dataType = DataType.STRING, required = true),
            @Param(name = "params", description = "{merchantId:123123} 请求参数商户id", dataType = DataType.STRING, required = true), })
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    // @RequiresUser
    @ResponseBody
    public Map<String, Object> queryMerchantFreePackage(@RequestParam(value = "params", required = true) String params, // 请求参数
            HttpServletRequest request) throws Exception {
        Map<String, Object> paramMap = JsonUtil.fromJson(params, Map.class);// 参数从字符串转json
        /* 参数校验 */
        Long merchantId = Long.valueOf(paramMap.get("merchantId") + "");// 接口编号
        ValidUtil.valid("商户id", merchantId, "required");// 接口编号必填校验
        List<TimePackage> list = timePackageService.queryListByParam(merchantId, 2);
        HashMap<String, Object> returnMap = new HashMap<>();
        if (list != null && list.size() > 0) {
            TimePackage timePackage = list.get(0);
            float packageValue = 0.0f;
            if (timePackage.getPackageValue() != null) {
                packageValue =   Math.round(timePackage.getPackageValue() * 24);
                
            }
          
            returnMap.put("hours",  packageValue);
        } else { // 未设置免费礼包 返回0
            returnMap.put("hours", 0);
        }
        
        return this.successMsg(returnMap);
    }

    /**
     * 逻辑删除套餐
     * 
     * @param request
     * @return
     * @throws Exception
     * @author zhangzw
     * @date 2017-3-30 14:48:01
     */
    @API(summary = "删除套餐", description = "根据套餐id删除 应用场景 商户不再设置免费礼包", parameters = {
            @Param(name = "packageId", description = "套餐id", type = "long", required = true), })
    // @RequiresRoles("admin")
    @RequestMapping(value = "", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public Map<String, Object> logicDelete(HttpServletRequest request) throws Exception {
        Long packageId = Long.valueOf(request.getParameter("packageId"));
        timePackageService.logicDelete(packageId);
        return this.successMsg();

    }

    public void setTimePackageService(TimePackageService timePackageService) {
        this.timePackageService = timePackageService;
    }

    public TimeBuyService getTimeBuyService() {
        return timeBuyService;
    }

    public void setTimeBuyService(TimeBuyService timeBuyService) {
        this.timeBuyService = timeBuyService;
    }

    public void setWifiService(WifiService wifiService) {
        this.wifiService = wifiService;
    }
    
    public static  void main(String args[]){
        
        System.out.println(Math.round(3.9));
    }
}

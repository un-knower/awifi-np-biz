package com.awifi.np.biz.timebuysrv.web.module.time.service;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;
import com.awifi.np.biz.api.client.sms.util.SmsClient;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.timebuysrv.third.access.bean.AuthLoginParam;
import com.awifi.np.biz.timebuysrv.third.access.bean.AuthResult;
import com.awifi.np.biz.timebuysrv.third.access.bean.DevParms;
import com.awifi.np.biz.timebuysrv.third.access.service.AccessAuthService;
import com.awifi.np.biz.timebuysrv.util.MD5;
import com.awifi.np.biz.timebuysrv.util.StringUtil;
import com.awifi.np.biz.timebuysrv.web.core.Constants;
import com.awifi.np.biz.timebuysrv.web.module.time.model.PortalParam;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionDTO;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionUser;
import com.awifi.np.biz.timebuysrv.web.module.time.model.UserCutoff;
import com.awifi.np.biz.timebuysrv.web.module.time.model.UserTimeInfo;

import redis.clients.jedis.Jedis;

/**
 * Created by dozen.zhang on 2017/2/28.
 */
@Service
public class WifiService {

    /** * 日志打印 . */
    private static Logger logger = LoggerFactory.getLogger(WifiService.class);

    @Resource
    UserCutoffService userCutoffService;
    @Resource
    private AccessAuthService accessAuthService;

    @Resource
    private TimeBuyService timeBusService;
    @Resource
    private TimeBuyService userWifiTime;

    /*
     * 有三种情况会调用此方法 临时放通 登录时候 发现是vip 登录时候发现是有时长 领取免费礼包后 临时放通
     * 
     * LogUtil.track(serviceCode, 321, param.toString(),
     * 
     * } /** . 调用接入的全局认证接口 踢掉通账户用户 更新redis 信息 密码已加密
     * 
     * @param sessionDTO
     * 
     * @Param Boolean tempraryFlag 是否是临时放行
     * 
     * @return String
     * 
     * @author 张智威
     * 
     * @date 2017年4月14日 下午2:27:31
     */
    public Map<String, Object> accessAccountAuth4MD5(SessionDTO sessionDTO, Boolean tempraryFlag) throws Exception {
        // step1 取到所有参数
        // 从session中获取已登录用户信息
        SessionUser seesionUser = sessionDTO.getSessionUser();
        // 获取商户设备对象
        Device merchant = sessionDTO.getMerchant();
        // 获取portal参数
        PortalParam portalParam = sessionDTO.getPortalParam();
        // 获取登录人手机号码
        String username = seesionUser.getPhone();// 手机号
        // String password = seesionUser.getAuthPswd();//接入auth的用户名密码
        // 获取设备id
        String deviceId = portalParam.getDeviceId();

        String terMac = portalParam.getUserMac();// 用户的手机mac地址
        // String callback ="";
        String apMac = merchant.getDevMac();// 设备mac
        String ip = sessionDTO.getRequestIp();// 用户的ip
        String port = sessionDTO.getRequestIpPort();// 用户的端口号
        Long merchantId = merchant.getMerchantId();// 商户的id
        Long userId = seesionUser.getId();// 商户的userId
        // 准备获取redis连接
        // 获取当前人的时长信息 如果此人是临时放通进来的 是可能为空的

        UserTimeInfo timeInfo = sessionDTO.getTimeInfo();

        judgeLogiclegal(tempraryFlag, timeInfo);
        // StringBuffer param = new StringBuffer();
        Map<String, Object> result = new HashMap<String, Object>();
        try {

            if (tempraryFlag) {// 如果是临时放通的话
                String times = RedisUtil.get(Constants.REDIS_TEMP_PASS_TIMES + username);
                if (times != null && Integer.parseInt(times) >= 3) {
                    // 超过今日放通次数
                    throw new BizException("E5066003", MessageUtil.getMessage("E5066003"));
                }
                // long num =
                // jedis.incr(Constants.REDIS_TEMP_PASS_TIMES+username);

            }
            // 组装放通的bean
            AuthLoginParam authLoginParam = new AuthLoginParam();
            authLoginParam.setLogId(portalParam.getLogId());
            authLoginParam.setDeviceId(deviceId);
            authLoginParam.setUserId(userId);// 记录wifirecord用
            authLoginParam.setMerchantId(merchantId);// 记录wifirecord用
            authLoginParam.setUsermac(terMac);
            authLoginParam.setPlatform("msp");
            authLoginParam.setAuthType("account");
            authLoginParam.setPublicuserip(ip);
            authLoginParam.setPublicuserport(port);
            authLoginParam.setUsername(username);
            // 对password 进行密码加密
            authLoginParam.setPassword(seesionUser.getAuthPswd());
            // 把前一次登录的账号踢下线
            logger.info("判断是否需要踢下线");
            judgeAndKickLastLogin(username, terMac, apMac, result);
            // 更新用户的mac信息到redis
            DevParms newDevParms = new DevParms();
            newDevParms.setUsername(username);
            newDevParms.setApMac(apMac);
            newDevParms.setDeviceId(deviceId);
            newDevParms.setTerMac(terMac);
            // String newRedisDevParam = JSON.toJSONString(newDevParms);
            // 这个是用来挤踢下线的
            RedisUtil.set(Constants.REDIS_TEL_TO_APMACTERMACDEVID + username, JSON.toJSONString(newDevParms),
                    Constants.HOUR_SESCONDS * 24);
            // step2 发送放通网络请求
            AuthResult authResult = accessAuthService.authLogin(authLoginParam);

            /*
             * if (ConfigUtil.getConfig("test").equals("true")) {
             * authResult.setResultCode("0");
             * authResult.setData("hlkjkljaksdjf@awifi.com");
             * 
             * }
             */
            sessionDTO.setAuthResult(authResult);

            if (authResult.getResultCode().equals("0")) {

                // jedis.hset(Constants.REDIS_TEL_TO_APMACTERMACDEVID,
                // username, JSON.toJSONString(newDevParms));
                // 过期时间设置
                if (tempraryFlag) {
                    // sessionDTO中的时间临时增加10分钟
                    Long nowTime = System.currentTimeMillis();
                    Long newTime = null;
                    // 如果已经加过时长了 那么就再次加时间
                    // TODO 如果是临时放通 当前肯定没有时长
                    if (sessionDTO.getTimeInfo() != null && sessionDTO.getTimeInfo().getEndTime() != null
                            && sessionDTO.getTimeInfo().getEndTime() > 0) {
                        if (sessionDTO.getTimeInfo().getEndTime() > nowTime) {
                            // 有一种可能是往之前的时间往上加 其他的可能都是往当前时间上加
                            newTime = sessionDTO.getTimeInfo().getEndTime() + 10 * 60 * 1000;
                        }
                    }
                    // 如果没有时间基础 或者时间基础小于当前时间 那么加时长
                    if (newTime == null) {
                        newTime = nowTime + 10 * 60 * 1000;
                    }

                    if (sessionDTO.getTimeInfo() == null) {
                        sessionDTO.setTimeInfo(new UserTimeInfo());
                    }
                    // 往sessionDTO中增加时间
                    sessionDTO.getTimeInfo().setEndTime(newTime);
                    //

                    // newRedisDevParam);//redisParms
                    // long num =
                    // 增加临时放通次数
                    RedisUtil.incr(Constants.REDIS_TEMP_PASS_TIMES + username, userWifiTime.todaySecondsLeft());
                }
                if (sessionDTO.getTimeInfo().isVip()) {
                    // 如果是临时放行的话 就更新redis 中的 截止日期
                    // accessAuthTimeUpdateTemprary(username, jedis,
                    // newRedisDevParam);

                } else {
                    // accessAuthTimeUpdateTemprary(username, jedis,
                    // newRedisDevParam);
                    // 设置踢下线队列时间
                    Long outtime = null;
                    if (sessionDTO.getTimeInfo() != null) {
                        outtime = sessionDTO.getTimeInfo().getEndTime();

                    }
                    // 保证outtime 不为null 取当前事件
                    if (outtime == null) {
                        logger.error("次数endTime不能为空");
                        throw new BizException("E4005003", MessageUtil.getMessage("E4005003"));
                        // outtime =System.currentTimeMillis();
                    }
                    // 放通了之后记得要设置用户的到时
                    RedisUtil.zadd(Constants.REDIS_TIME_TO_DEVPARAM, (outtime), JSON.toJSONString(newDevParms));
                }

            }
        } catch (Exception e) {
            /*
             * LogUtil.track(serviceCode, 321, param.toString(),
             * "access.account.auth4.MD5.catch" + e.getMessage(), "");
             */
            // e.printStackTrace();
            logger.error("access.auth.accountauth error: ", e);
            throw e;
            // TODO delete this cheat

            // result.put("token","hjahsdfhashd@awifi.com");
        }
        logger.info("send request to access.account.auth.url end:");
        /*
         * LogUtil.track(serviceCode, 322, JSON.toJSONString(result),
         * "send request to access.account.auth.url end:", "");
         */
        return result;
    }

    /*
     * 
     * public AuthResult accessAccountAuth4MD5(String apMac,String
     * username,String terMac,String deviceId,String ip ,String port,String
     * authPwd) throws Exception {
     * 
     * 
     * //准备获取redis连接 Jedis jedis = null;
     * 
     * //StringBuffer param = new StringBuffer(); Map<String, Object> result =
     * new HashMap<String, Object>(); try {
     * 
     * jedis = RedisUtil.getResource();
     * 
     * 
     * // 组装放通的bean AuthLoginParam authLoginParam = new AuthLoginParam();
     * authLoginParam.setLogId(""); authLoginParam.setDeviceId(deviceId);
     * authLoginParam.setUserId(userId);//记录wifirecord用
     * authLoginParam.setMerchantId(merchantId);//记录wifirecord用
     * authLoginParam.setUsermac(terMac); authLoginParam.setPlatform("msp");
     * authLoginParam.setAuthType("account");
     * authLoginParam.setPublicuserip(ip);
     * authLoginParam.setPublicuserport(port);
     * authLoginParam.setUsername(username); // 对password 进行密码加密
     * authLoginParam.setPassword(authPwd); //把前一次登录的账号踢下线
     * judgeAndKickLastLogin(username, terMac, apMac, jedis, result); //
     * 跟新用户的mac信息到redis
     * 
     * DevParms newDevParms = new DevParms(); newDevParms.setUsername(username);
     * newDevParms.setApMac(apMac); newDevParms.setDeviceId(deviceId);
     * newDevParms.setTerMac(terMac);
     * 
     * //String newRedisDevParam = JSON.toJSONString(newDevParms); // 这个是用来挤踢下线的
     * jedis.set(Constants.REDIS_TEL_TO_APMACTERMACDEVID + username,
     * JSON.toJSONString(newDevParms));
     * jedis.expire(Constants.REDIS_TEL_TO_APMACTERMACDEVID + username,
     * Constants.HOUR_SESCONDS * 24);
     * 
     * // step2 发送放通网络请求 AuthResult authResult =
     * accessAuthService.authLogin(authLoginParam);
     * 
     * 
     * if (ConfigUtil.getConfig("test").equals("true")) {
     * authResult.setResultCode("0");
     * authResult.setData("hlkjkljaksdjf@awifi.com");
     * 
     * }
     * 
     * 
     * 
     * if (authResult.getResultCode().equals("0")) {
     * 
     * // jedis.hset(Constants.REDIS_TEL_TO_APMACTERMACDEVID, // username,
     * JSON.toJSONString(newDevParms)); // 过期时间设置 if
     * (sessionDTO.getTimeInfo().isVip()) { // 如果是临时放行的话 就跟新redis 中的 截止日期 //
     * accessAuthTimeUpdateTemprary(username, jedis, // newRedisDevParam);
     * 
     * } else { // accessAuthTimeUpdateTemprary(username, jedis, //
     * newRedisDevParam); // 设置踢下线队列时间 Long outtime =null;
     * if(sessionDTO.getTimeInfo() != null){ outtime =
     * sessionDTO.getTimeInfo().getEndTime();
     * 
     * } //保证outtime 不为null 取当前事件 if(outtime == null){
     * logger.error("次数endTime不能为空"); throw new
     * BizException("E4005003",MessageUtil.getMessage("E4005003")); // outtime
     * =System.currentTimeMillis(); } // 放通了之后记得要设置用户的到时
     * jedis.zadd(Constants.REDIS_TIME_TO_DEVPARAM, (outtime),
     * JSON.toJSONString(newDevParms));
     * 
     * }
     * 
     * } } catch (Exception e) {
     * 
     * } finally { if (jedis != null) { try { jedis.close(); } catch
     * (JedisException e) { e.printStackTrace(); } } } logger.info(
     * "send request to access.account.auth.url end:");
     * 
     * return authResult; }
     */
    /**
     * 判断是否要踢掉 并且踢掉上次登录
     * 
     * @param username
     * @param terMac
     * @param apMac
     * @param result
     * @author 张智威
     * @date 2017年6月3日 下午5:28:48
     */
    private void judgeAndKickLastLogin(String username, String terMac, String apMac, Map<String, Object> result) {
        logger.info("新的设备信息: username="+username+", terMac="+terMac+", apMac="+apMac);
        logger.info("新的设备信息: username="+username+", terMac="+terMac+", apMac="+apMac);
        // 踢掉用户其他设备上网认证
        DevParms oldDevParam = new DevParms();
        // 踢人的代码要放到放通之前
        String apmacUserMacDevidJson = RedisUtil.get(Constants.REDIS_TEL_TO_APMACTERMACDEVID + username);
        // 如果用户有在其他设备登录 或者用其他手机登录的情况下

        logger.info("旧的设备信息:"+apmacUserMacDevidJson);
        if (StringUtil.isNotBlank(apmacUserMacDevidJson)) {
            // 上次上网的记录
            oldDevParam = JSON.parseObject(apmacUserMacDevidJson, DevParms.class);
            // 是否需要T用户下线
            // 如果换了一个设备上网 或者换了一台手机上网 或者换了个账号 上网
            logger.info("old apMac:"+oldDevParam.getApMac()+", new apMac:"+apMac);
            logger.info("old terMac:"+oldDevParam.getTerMac()+", new terMac:"+terMac);
            if (((!apMac.equals(oldDevParam.getApMac())) || (!terMac.equals(oldDevParam.getTerMac())))
                    && (username.equals(oldDevParam.getUsername()))) {
                logger.info("判断需要踢下线");
                result.put("kickoff", 1);// 页面端增加提醒
                if (StringUtil.isNotBlank(username) && StringUtil.isPhone(username)) {
                    SmsClient.sendMsg(username, "抱歉，您的园区上网手机账号已在第二台终端登录上网，将终止该手机账号在第一台终端的上网服务，敬请谅解。");
                }
                // logger.info("判断需要踢下线2:" + result.get("kickoff"));
                kickuseroffline(oldDevParam, "");
            }
        }

    }

    /**
     * 如果不是临时放通 那么timeINfo不能为空 并且 该用户需要有时间.
     * 
     * @param tempraryFlag
     * @param timeInfo
     * @author 张智威
     * @date 2017年6月3日 下午5:27:09
     */
    private void judgeLogiclegal(Boolean tempraryFlag, UserTimeInfo timeInfo) {

        if (!tempraryFlag && !timeInfo.isVip()) {
            // 说明不是临时放通
            // 如果是正常房sing的话需要有时长 以下几种都是不行的
            if (timeInfo == null || timeInfo.getEndTime() == null
                    || timeInfo.getEndTime() < System.currentTimeMillis()) {
                throw new BizException("E0464102", MessageUtil.getMessage("E0464102"));
            }
        }
        /*
         * if(!tempraryFlag && ( timeInfo==null ||
         * timeInfo.getEndTime()<System.currentTimeMillis() ) ){
         * 
         * 
         * }
         */
    }

    /**
     * 临时放通次数跟新
     * 
     * @param username
     * @param jedis
     * @param redisParms
     * @throws ParseException
     * @author 张智威
     */
    private void accessAuthTimeUpdateTemprary(String username, Jedis jedis, String redisParms) throws Exception {

        Calendar outtime = Calendar.getInstance();
        outtime.add(Calendar.MINUTE, 10);
        jedis.zadd(Constants.REDIS_TIME_TO_DEVPARAM, outtime.getTimeInMillis(), redisParms);// redisParms
        String times = jedis.get(Constants.REDIS_TEMP_PASS_TIMES + username);
        // 设置当前
        int num = 0;
        if (StringUtil.isBlank(times)) {
            num = 1;
        } else {
            num = Integer.parseInt(times) + 1;
        }
        // 设置当前日期过期次数
        jedis.set(Constants.REDIS_TEMP_PASS_TIMES + username, num + "");
        // 设置当前晚上过期
        jedis.expire(Constants.REDIS_TEMP_PASS_TIMES, userWifiTime.todaySecondsLeft());
    }

    /**
     * 接入认证放行 request
     * 
     * @return Map
     * @throws Exception
     * @author 张智威 2017年6月6日14:01:10
     */
    public Map<String, Object> permitWLAN(SessionDTO sessionDTO) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        Long merchantId = sessionDTO.getMerchant().getMerchantId();
        SessionUser sessionUser = sessionDTO.getSessionUser();
        if (sessionUser == null)
            throw new BizException("E5052001", MessageUtil.getMessage("E5052001"));//用户未登录
        String username = sessionUser.getPhone();
        Long userId = sessionUser.getId();
        // 如果是vip用户
        if (sessionDTO.getTimeInfo() != null && sessionDTO.getTimeInfo().isVip()) {// vip放行
            // if (timeBusService.isFreeUser(username)) {
            result.put("access_auth", this.accessAccountAuth4MD5(sessionDTO, false));
            return result;
        } else {// 非vip 用户 那就要看看有没有剩余时长
            // 找出商户和用户对应的消费时长剩余记录
            // sessionDTO.getTimeInfo();
            UserCutoff merchant_user_cutoff = userCutoffService.selectByMerIdAndUserId(merchantId, userId);

            // 是否放行网络
            if (merchant_user_cutoff != null) {
                if (merchant_user_cutoff.getCutoffDate().getTime() > (new Date()).getTime()) {
                    result.put("access_auth", this.accessAccountAuth4MD5(sessionDTO, false));

                    // logger.info("判断需要踢下线3:"+JSON.toJSONString(result));

                    result.put("user_cutoff", merchant_user_cutoff);// 把用户的上网时长放入到返回结果中
                    sessionDTO.getTimeInfo().setEndTime(merchant_user_cutoff.getCutoffDate().getTime());

                } else {
                    result.put("have_no_time", "1");
                }

            } else {
                result.put("have_no_time", "1");// 从未购买过时间
            }
        }

        return result;
    }

    /**
     * . 登录成功将账号与设备信息存入redis缓存
     *
     * @param username
     *            用户名
     * @param terMac
     *            手机mca
     * @param merchantId
     *            商户id
     * @throws Exception
     * 2017年7月14日11:15:14 废弃不用代码
     */
   /* public void setDeviceCache(String username, String terMac, Long merchantId) throws Exception {
        RedisUtil.set(Constants.REDIS_TEL_TO_TERMAC_MER + username, terMac + "," + merchantId,
                Constants.HALF_HOUR_SESCONDS);
    }*/

    /**
     * . 免认证时检测设备信息是否匹配
     *
     * @param username
     *            账号
     * @param terMac
     *            手机mac
     * @param merchantId
     *            商户id
     * @throws Exception
     * @author 张智威2016年1月27日 上午11:04:51
     * 2017年7月14日11:15:48 废弃不用代码
     */
    /*public void checkService(String username, String terMac, Long merchantId) throws Exception {
        String code = terMac + "," + merchantId;
        String value = RedisUtil.get(Constants.REDIS_TEL_TO_TERMAC_MER + username);
        if (!code.equals(value)) {
            
             * LogUtil.info(serviceCode, 202, "username:" + username +
             * " termac:" + terMac + " merchantid:" + merchantId,
             * "not equal redis user_device " + value, "");
             
            throw new Exception("设备信息有误，请重新登录");
        }
    }*/

    /**
     * 接入踢用户接口
     * 
     * @param devParams
     *            踢下线相关参数
     * @param kickLevel
     *            下线等级
     * @return Map
     * @author 张智威
     */
    public int kickuseroffline(DevParms devParams, String kickLevel) {
        logger.info("接入踢用户接口, param: " + JsonUtil.toJson(devParams) + ", kickLevel=" + kickLevel);
        // StringBuffer param = new StringBuffer();
        // Map<String, Object> result = new HashMap<String, Object>();

        String terMac = devParams.getTerMac();
//        Map<String, Object> parms = new HashMap<String, Object>();
        // String oldmac = devParams.getTerMac();
        try {// 这里加try catch 是为了归还redis
             // jedis = RedisUtil.getResource();

            Map<String, Object> authResult = accessAuthService.kickuser(terMac, kickLevel);
            logger.info("kick user offline result: " + JsonUtil.toJson(authResult));
            // SmsUtil smsUtil = new SmsUtil();

            // smsUtil.sendSms("抱歉，您的园区上网手机账号已在第二台终端登录上网，将终止该手机账号在第一台终端的上网服务，敬请谅解。",
            // username, (short) 3);
            // 删除需要被踢的队列中的值
            if (authResult != null && "0".equals(authResult.get("resultCode"))) {
                if (StringUtil.isNotBlank(JsonUtil.toJson(devParams))) {
                    RedisUtil.zrem(Constants.REDIS_TIME_TO_DEVPARAM, JsonUtil.toJson(devParams));
                    
                    //并且删除用户上次登录记录
                    RedisUtil.del(Constants.REDIS_TIME_TO_DEVPARAM+devParams.getUsername());
                }
                return 1;// 踢下线成功
            } else {
                // 记录踢下线失败的用户
                RedisUtil.hset(Constants.REDIS_KICK_FAIL, terMac, JSON.toJSONString(devParams));
                logger.error("access.kickuseroffline fail, result=  " + JsonUtil.toJson(authResult));
                return 0;
            }
        } catch (Exception e) {
            logger.error("access.kickuseroffline.Exception: " + e.getMessage());

            // 记录踢下线失败的用户
            RedisUtil.hset(Constants.REDIS_KICK_FAIL, terMac, JSON.toJSONString(devParams));
//            logger.error("access.kickuseroffline.catch: " + parms.toString());
            // throw e;
        } // finally {
        /*
         * try{ jedis.close(); }catch(Exception e){
         * 
         * }
         */
        // }
        /*
         * LogUtil.track(serviceCode, 313, JSON.toJSONString(result),
         * "send request to access.kickuseroffline end:", "");
         */
        return 0;// 踢下线失败
    }

    /**
     * 踢下线 踢过期用户下线
     * 
     * @author 张智威
     * @date 2017年6月1日 上午9:41:17
     */
    public void kickTimeOutUser() {
        try {
            // 需要T下线的用户
            // Set<String> allUser = jedis.hkeys("msp_8");
            // 从0到当前时间 取出所有
            Set<String> allUser = RedisUtil.zrangeByScore(Constants.REDIS_TIME_TO_DEVPARAM, 0,
                    System.currentTimeMillis());
            // Set<String> allowUser = jedis.hkeys("9");
            // allUser.removeAll(allowUser);

            // logger.info(allUser.size());
            for (String user : allUser) {
                if (StringUtil.isNotBlank(user)) {
                    try {
                        logger.info("kickuseroffline:" + JsonUtil.toJson(user));
                        DevParms devParms = JsonUtil.fromJson(user, DevParms.class);
                        if (1 != kickuseroffline(devParms, "")) {
                            logger.error(user + "用户踢下线失败");
                        } else {
                            logger.info("用户踢下线成功, user= " + user);
                        }
                    } catch (Exception e) {
                        logger.error(user + "用户踢下线异常: ", e);
                    }
                }
            }
            // 取出所有的设备信息
            Map<String, String> wrongUser = RedisUtil.hgetAll(Constants.REDIS_KICK_FAIL);
            for (String user : wrongUser.keySet()) {
                String userValue = RedisUtil.hget(Constants.REDIS_KICK_FAIL, user);
                try {
                    DevParms devParms = JsonUtil.fromJson(userValue, DevParms.class);
                    if (1 != kickuseroffline(devParms, "")) {
                        logger.error(user + "用户踢下线失败, devParms= " + userValue);
                        Integer num = devParms.getNum();
                        //TODO 这里会存在一个问题 尝试踢失败一次后往往后面都会失败
                        if (num == null) {
                            devParms.setNum(1);
                            RedisUtil.hset(Constants.REDIS_KICK_FAIL, user, JsonUtil.toJson(devParms));
                            logger.warn("尝试次数:1 ");
                        } else if (num < 5) {// 踢5次
                            devParms.setNum(1 + num);
                            RedisUtil.hset(Constants.REDIS_KICK_FAIL, user, JsonUtil.toJson(devParms));
                            logger.warn("尝试次数:" + devParms.getNum());
                        } else {// 如果超过5次 就不踢了
                            RedisUtil.hdel(Constants.REDIS_KICK_FAIL, user);
                            logger.warn("失败已超过5次,不再踢了,尝试次数:" + devParms.getNum());
                        }
                    } else {
                        RedisUtil.hdel(Constants.REDIS_KICK_FAIL, user);
                        logger.info("用户踢下线成功:" + userValue);
                    }
                } catch (Exception e) {
                    logger.error(user + "用户踢下线异常: ", e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        System.out.println(MD5.md5("123456"));
    }
}

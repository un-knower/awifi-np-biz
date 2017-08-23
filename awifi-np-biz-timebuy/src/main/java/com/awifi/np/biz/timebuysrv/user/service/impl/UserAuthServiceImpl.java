/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月24日 下午2:04:30
* 创建作者：尤小平
* 文件名称：UserAuthServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.api.client.dbcenter.user.model.PubUser;
import com.awifi.np.biz.api.client.dbcenter.user.model.PubUserAuth;
import com.awifi.np.biz.api.client.dbcenter.user.util.UserAuthClient;
import com.awifi.np.biz.api.client.dbcenter.user.util.UserBaseClient;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.ms.util.MsCommonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.timebuysrv.third.access.service.AccessAuthService;
import com.awifi.np.biz.timebuysrv.user.model.SysUser;
import com.awifi.np.biz.timebuysrv.user.service.IThirdUserService;
import com.awifi.np.biz.timebuysrv.user.service.UserAuthService;
import com.awifi.np.biz.timebuysrv.user.service.UserBaseService;
import com.awifi.np.biz.timebuysrv.util.CaptchaUtil;
import com.awifi.np.biz.timebuysrv.util.MD5;
import com.awifi.np.biz.timebuysrv.util.StringUtil;
import com.awifi.np.biz.timebuysrv.util.Tools;
import com.awifi.np.biz.timebuysrv.util.define.CommonDefine;
import com.awifi.np.biz.timebuysrv.util.define.MsCommonDefine;
import com.awifi.np.biz.timebuysrv.web.log.ResultDTO;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionDTO;
import com.awifi.np.biz.timebuysrv.web.module.time.service.TimeBuyService;
import com.awifi.np.biz.timebuysrv.web.module.time.service.UserCutoffService;
import com.awifi.np.biz.timebuysrv.web.util.ResultUtil;

@Service(value = "userAuhService")
public class UserAuthServiceImpl implements UserAuthService {

    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * UserBaseService
     */
    @Resource
    private UserBaseService userBaseService;

    /**
     * AccessAuthService
     */
    @Resource
    private AccessAuthService accessAuthService;

    /**
     * UserCutoffLogicService
     */
    @Resource
    private UserCutoffService userCutoffService;

    /**
     * IThirdUserService
     */
    @Resource
    private IThirdUserService thirdUserService;

    /**
     * TimeBuyService
     */
    @Resource
    private TimeBuyService timeBuyService;

    /**
     * 根据手机号码获取用户信息.
     *
     * @param telphone
     *            手机号码
     * @return PubUserAuth
     * @throws Exception
     *             异常
     * @author 尤小平
     * @date 2017年4月24日 上午10:51:02
     */
    @Override
    public PubUserAuth getUserByLogName(String telphone) throws Exception {
        return UserAuthClient.getUserByLogName(telphone);
    }

    /**
     * 保存注册.
     *
     * @param username
     *            用户名
     * @param password
     *            密码
     * @return 认证用户id
     * @throws Exception
     *             异常
     * @author 尤小平
     * @date 2017年4月24日 下午2:19:43
     */
    @Override
    public Long saveReg(String username, String password) throws Exception {
        PubUserAuth auth = new PubUserAuth();
        String randNum = Tools.rand(5);
        auth.setSafetyCode(randNum);
        auth.setAuthPlatform(MsCommonDefine.authPlatform);
        auth.setToken(MsCommonUtil.getCenterToken(randNum));
        auth.setTelphone(username);
//        auth.setLogname(username);
        // auth.setAuthPswd(PwdEncryptUtil.getPwd(password, randNum));
        auth.setAuthPswd(MD5.md5(password));
        auth.setSeed(randNum);

        // 注册 这里会创建center_pub_user_auth 表记录和center_pub_user表记录
        long id = UserAuthClient.addUserAuth(auth);

        // 注册成功
        if (id > 0) {
            PubUserAuth pubUserAuth = new PubUserAuth();
            pubUserAuth.setTelphone(username);
            List<PubUserAuth> list = UserAuthClient.queryUserAuthByParam(pubUserAuth);
            if (list != null && list.size() > 0) {
                pubUserAuth = list.get(0);
            }

            SysUser sysUser = new SysUser();
            sysUser.setUsername(username);
            sysUser.setTelno(username);
            sysUser.setPassword(MD5.md5(password));
            if (pubUserAuth.getUserId() > 0) {
                sysUser.setOutid(pubUserAuth.getUserId());
            }
            userBaseService.addSysUser(sysUser);
        }

        return id;
    }

    /**
     * 登录获取用户信息并做相关保存操作.
     * 
     * @param request
     *            request
     * @param username
     *            账号
     * @param deviceId
     *            设备id
     * @param terMac
     *            用户mac
     * @param apMac
     *            设备mac
     * @param merchantId
     *            商户id
     * @return Map
     * @throws Exception
     *             异常
     * @author 尤小平
     * @date 2017年4月25日 上午10:51:37
     */
    public Map<String, Object> saveLogin(HttpServletRequest request, String username, String deviceId, String terMac,
            String apMac, Long merchantId) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        PubUserAuth userAuth = new PubUserAuth();
        userAuth.setTelphone(username);
        // 在发送短信的时候已经发送过了 这里反查是为了检查是否发送过短信
        // 根据手机号码反查用户信息 userid
        List<PubUserAuth> list = UserAuthClient.queryUserAuthByParam(userAuth);

        if (list != null && list.size() > 0) {
            userAuth = list.get(0);
            result.put(MsCommonDefine.SESSION_USER_AUTH, userAuth);
            request.getSession().setAttribute(MsCommonDefine.SESSION_USER_AUTH, userAuth);
            // 查询用户信息
            PubUser pubUser = UserBaseClient.queryByUserId(userAuth.getUserId());

            if (pubUser != null) {
                // 是否需要补全个人资料
                if (StringUtils.isEmpty(pubUser.getUserNick())) {
                    result.put("write_user_info", "ok");
                }
                result.put("user", pubUser);
                request.getSession().setAttribute(MsCommonDefine.SESSION_USER, pubUser);
            } else {
                throw new BizException("E2000056", MessageUtil.getMessage("E2000056", "该用户")); //该用户不存在
            }
            // 清除商户session
            request.getSession().removeAttribute(CommonDefine.SESSION_MERCHANT);
            // 如果是商家用户，再取一下商家用户信息
            Merchant connectMer = MerchantClient.getByIdCache(merchantId);

            if (connectMer != null && connectMer.getUserId() != null
                    && connectMer.getUserId().longValue() == userAuth.getUserId() && connectMer.getStatus() == 1) {
                logger.debug(
                        "merchantId:" + merchantId + ", userid:" + connectMer.getUserId() + ", username=" + username);
                result.put("type", CommonDefine.SESSION_MERCHANT);
                result.put(CommonDefine.SESSION_MERCHANT, connectMer);
                request.getSession().setAttribute(CommonDefine.SESSION_MERCHANT, connectMer);
            }
            /*
             * // 增加统计 Map<String, Object> visitParm = new HashMap<>();
             * visitParm.put("merchantId", merchantId); visitParm.put("userId",
             * userAuth.getUserId()); // 增加用户商户关系 try { if(
             * merchantVisitServiceApi.queryUserCountByParam(JSON
             * .toJSONString(visitParm))==0)
             * merchantVisitServiceApi.addVisit(JSON .toJSONString(visitParm));
             * } catch (Exception e) { e.printStackTrace(); } // 添加一条用户商户的PV记录
             * try { visitParm .put("visitDate", new
             * SimpleDateFormat("yyyyMMdd") .format(new Date()));
             * merchantVisitServiceApi.statVisitInfo(JSON
             * .toJSONString(visitParm)); } catch (Exception e) {
             * logger.error("增加pv统计错误",e); }
             */
        } else {
            throw new BizException("E2000056", MessageUtil.getMessage("E2000056", "该用户")); //该用户不存在
        }
        
        //setDeviceCache(username, terMac, merchantId);

        /*
         * // 关联商户id try { Map<String, Object> queryVipUserParam = new
         * HashMap<String, Object>(); queryVipUserParam.put("telephone",
         * username); queryVipUserParam.put("merchantId", merchantId);
         * queryVipUserParam.put("globalKey", "queryVipUserCount");
         * queryVipUserParam.put("globalValue", System.currentTimeMillis()); if
         * (onlineService.queryVipUserCount(JSON
         * .toJSONString(queryVipUserParam)) < 1) { // 绑定用户商户关系
         * queryVipUserParam.put("globalKey", "updateVipUser"); Integer ret =
         * onlineService.updateVipUser(JSON .toJSONString(queryVipUserParam));
         * if (ret < 1) { logger.error("updateVipUser fail:" +
         * JSON.toJSONString(queryVipUserParam));
         * LogUtil.track(ServiceCode.USERLOGIC_SAVELOGIN, 107,
         * JSON.toJSONString(queryVipUserParam), "updateVipUser", ret + ""); } }
         * } catch (Exception e) { logger.error("onlineService.catch", e);
         * LogUtil.track(ServiceCode.USERLOGIC_SAVELOGIN, 108, e.getMessage(),
         * "onlineService.catch", ""); }
         */
        return result;
    }

    /**
     * 登录成功将账号与设备信息存入redis缓存.
     * 
     * @param username
     *            用户名
     * @param terMac
     *            手机mac
     * @param merchantId
     *            商户id
     * @throws Exception
     *             异常
     * @author 尤小平
     * @date 2017年4月25日 上午10:55:10
     */
    /*private void setDeviceCache(String username, String terMac, Long merchantId) throws Exception {
        RedisUtil.set(Constants.REDIS_TEL_TO_TERMAC_MER + username, terMac + "," + merchantId, 24 * 60 * 60);
    }*/

    /**
     * 接入认证放行.
     * 
     * @param request
     *            request
     * @return Map
     * @throws Exception
     *             异常
     * @author 尤小平
     * @date 2017年4月25日 下午2:30:45
     */
    /*public Map<String, Object> permitWLAN(HttpServletRequest request) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            SessionDTO sessionDTO = (SessionDTO) request.getSession().getAttribute(Constants.SESSION_DTO);
            Long merchantId = (Long) request.getSession().getAttribute("SessionMerchantId");
            PubUserAuth userAuth = (PubUserAuth) request.getSession().getAttribute(MsCommonDefine.SESSION_USER_AUTH);
            if (userAuth != null) {
                logger.debug("userAuth:" + JsonUtil.toJson(userAuth));
            }
            String username = userAuth.getTelphone();
            Long userId = userAuth.getUserId();
            if (isFreeUser(username)) {
                result.put("access_auth", accessAuthService.accessAccountAuth4MD5(sessionDTO,false));
                return result;
            }
            // 找出商户和用户对应的消费时长剩余记录
            UserCutoff merchantUserCutoff = userCutoffService.selectByMerIdAndUserId(merchantId, userId);
            // 是否放行网络
            if (merchantUserCutoff != null) {
                if (merchantUserCutoff.getCutoffDate().getTime() > (new Date()).getTime()) {
                    result.put("access_auth", accessAuthService.accessAccountAuth4MD5(sessionDTO,false));

                    // logger.info("判断需要踢下线3:"+JSON.toJSONString(result));

                    result.put("user_cutoff", merchantUserCutoff);// 把用户的上网时长放入到返回结果中
                    request.getSession().setAttribute(UserDefine.MERCHANT_USET_CUTOFF, merchantUserCutoff);
                } else {
                    result.put("have_no_time", "1");
                }

            } else {
                result.put("have_no_time", "1");// 从未购买过时间
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return result;
    }*/

    /**
     * 判断是否为vip用户.
     * 
     * @param username
     *            账号
     * @return boolean
     * @throws Exception
     *             异常
     * @author 尤小平
     * @date 2017年4月25日 下午2:32:24
     */
    public boolean isFreeUser(String username) throws Exception {
        return timeBuyService.isVipUser(username);
    }
    /**
     * 根据手机号码和验证码登录
     * @author 张智威  
     * @date 2017年4月28日 下午1:53:35
     */
    @Override
    public ResultDTO loginByPhoneAndSms(SessionDTO sessionDTO, String phone, String captcha, HttpServletRequest request)
            throws Exception {
        //====================这里先做短信验证码校验===================
        if (sessionDTO == null) {// sessionDTO不能为空
            logger.error("sessionDTO can't not be null phone:" + phone);
            throw new BizException("E0000002", MessageUtil.getMessage("E0000002", "sessionDTO")); //sessionDTO can't not be null
        }

        // 2.判断验证是否通过 一键登录 1验证验证码是否通过,
        if (StringUtil.isBlank(captcha)){
            if (!StringUtil.isBlank(sessionDTO.getPortalParam().getUserType())
                    && sessionDTO.getPortalParam().getUserType().equals("EXEMPT_AUTH_USER") && phone.equals(sessionDTO.getPortalParam().getMobilePhone())) {
                //说明是一键登录来的
            }else{
                //说明是非一键登录 普通登录来的 但是没给验证码 pass掉!
                throw new BizException("E5066002", MessageUtil.getMessage("E5066002"));
            }
        }else{//校验验证码
            CaptchaUtil.checkCaptcha(phone, "login", captcha, request);
        }
       

        // 调用认证记录接口查询用户信息
        logger.info("before userAuthServiceApi.queryUserAuthByParam" + phone);
        // 在发送短信的时候已经发送过了 这里反查是为了检查是否发送过短信
        // 根据手机号码反查用户信息 userid
        // 2验证手机号是否在auth中存在 没有的话报错
        
        // 2验证手机号是否在auth中存在 没有的话报错
        // 3验证手机号码在base 中是否存在 没有的话报错

        // 4查询昵称是否存在 5检查用户商户角色放入sessionUser中的role
        // 6如果不是商户管理员检查用户是否是代理管理员 7初始化用户上网时长信息 8 存入 最近的用户 手机+ 用户
        
      //====================这里提取用户信息以及缓存时长相关信息===================
        if (thirdUserService != null) {// 这里有一个同步机制 就是 所有的登录用户信息都保存在本地库
                                       // ,登录的时候直接去本地库获取用户信息,
            thirdUserService.loginByPhoneAndSMS(sessionDTO, phone, captcha);
        }
        
        return ResultUtil.getResult();

    }

    /**
     * for testing only.
     * 
     * @param timeBuyService TimeBuyService
     * @author 尤小平  
     * @date 2017年5月17日 下午3:54:55
     */
    protected void setTimeBuyService(TimeBuyService timeBuyService) {
        this.timeBuyService = timeBuyService;
    }

    /**
     * for testing only.
     * 
     * @param thirdUserService IThirdUserService
     * @author 尤小平  
     * @date 2017年5月17日 下午3:55:03
     */
    protected void setThirdUserService(IThirdUserService thirdUserService) {
        this.thirdUserService = thirdUserService;
    }

    /**
     * for testing only.
     * 
     * @param userBaseService UserBaseService
     * @author 尤小平  
     * @date 2017年5月17日 下午3:55:07
     */
    protected void setUserBaseService(UserBaseService userBaseService) {
        this.userBaseService = userBaseService;
    }
}

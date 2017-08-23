package com.awifi.np.biz.timebuysrv.user.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.user.model.PubUser;
import com.awifi.np.biz.api.client.dbcenter.user.model.PubUserAuth;
import com.awifi.np.biz.api.client.dbcenter.user.util.UserAuthClient;
import com.awifi.np.biz.api.client.dbcenter.user.util.UserBaseClient;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.timebuysrv.user.service.IThirdUserService;
import com.awifi.np.biz.timebuysrv.user.service.UserAuthService;
import com.awifi.np.biz.timebuysrv.user.service.UserBaseService;
import com.awifi.np.biz.timebuysrv.util.StringUtil;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionDTO;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionUser;
import com.awifi.np.biz.timebuysrv.web.module.time.model.UserTimeInfo;
import com.awifi.np.biz.timebuysrv.web.module.time.service.TimeBuyService;
import com.awifi.np.biz.timebuysrv.web.module.time.service.WifiService;

/**
 * Created by dozen.zhang on 2017/3/10. 对 userAuthService 和 userBaseService的业务封装
 * 用于园区wifi用户认证业务
 */
@Service("thirdUserService")
public class ThirdWifiAuthUserService implements IThirdUserService {
    //日志
    private static final Logger logger = LoggerFactory.getLogger(ThirdWifiAuthUserService.class);

    /**
     * WiFi服务
     **/
    @Resource
    WifiService wifiService;
    /**
     * 时长总线接口
     **/
    @Resource
    private TimeBuyService timeBuyService;
    /**
     * 第三方用户认证接口
     **/
    @Resource
    private UserAuthService userAuthService;
    /**
     * 第三方用户基础信息接口
     **/
    @Resource
    private UserBaseService userBaseService;

    /*
     * public void register(SessionUser user){
     * 
     * }
     */
    /**
     * 根据手机号反查用户信息
     * 
     * @author 张智威
     * @date 2017年6月5日 上午10:00:47
     */
    public SessionUser getUserByPhone(String phone) throws Exception {
        if (!StringUtil.isBlank(phone) && !StringUtil.isPhone(phone)) {
            throw new BizException("E0000002", MessageUtil.getMessage("E0000002", "手机号")); //phone is null
        }
        SessionUser sessionUser = new SessionUser();
        PubUserAuth userAuth = userAuthService.getUserByLogName(phone);
        if (userAuth != null && userAuth.getId() != null) {
            sessionUser.setId(userAuth.getUserId());
            sessionUser.setName(userAuth.getLogname());
            sessionUser.setPhone(phone);
            sessionUser.setAuthPswd(userAuth.getAuthPswd());//之后放通网络的时候需要账号密码进行放通
        } else {
            /*
             * LogUtil.error(serviceCode, 102, phone,
             * "can't find auther user by phone :" + phone, null);
             */
            throw new BizException("E5071004", MessageUtil.getMessage("E5071004"));//该用户未注册   说明在发送短息的时候没有创建过用户
        }
        // 查询用户信息
        // Map<String, Object> param = new HashMap<String, Object>();
        // param.put("id", userAuth.getUserId());
        //auth表的userid 是base表的id
        PubUser pubUser = UserBaseClient.queryByUserId(userAuth.getUserId());
        /*
         * LogUtil.track(serviceCode, 103, "params:" + param + "results:" +
         * JsonUtils.toJsonString(pubUser), "after userServiceApi.queryById",
         * phone);
         */
        //查询用户的基础信息的
        if (pubUser != null) {
            sessionUser.setFace(pubUser.getFaceInfo());//头像
            if (pubUser.getBirthday() != null) {
                sessionUser.setBirthday(pubUser.getBirthday().getTime());
            }
            sessionUser.setSex(StringUtil.isBlank(pubUser.getSex()) ? 0 : Integer.valueOf(pubUser.getSex()));//0是未知 1是男的 2是女的
            sessionUser.setAddress(pubUser.getAddress());
            sessionUser.setNick(pubUser.getUserNick());
        } else {
            throw new BizException("E5071004", MessageUtil.getMessage("E5071004"));//该用户未注册   说明在发送短息的时候没有创建过用户
        }
        // after success login init the user info in session
        return sessionUser;
    }

    public SessionUser getUserById(Long id) {
        return null;
    }

    public void updateUser(SessionUser sessionUser) throws Exception {
        PubUser user = new PubUser();
        user.setSex(String.valueOf(sessionUser.getSex()));
        user.setId(sessionUser.getId());
        user.setAddress(sessionUser.getAddress());
        user.setUserNick(sessionUser.getNick());
        if (sessionUser.getBirthday() != null && sessionUser.getBirthday() > 0) {
            user.setBirthday(new Date(sessionUser.getBirthday()));
        }
        user.setFaceInfo(sessionUser.getFace());

        userBaseService.update(user);
        // 更新session中的用户信息

    }

    public void addUser(SessionUser sessionUser) throws Exception {
        PubUserAuth user = UserAuthClient.getUserByLogName(sessionUser.getPhone());
        if (user == null) {
            // 直接注册吗
            user = new PubUserAuth();
            user.setTelphone(sessionUser.getPhone());
            user.setAuthPswd(SysConfigUtil.getParamValue("default_password"));
            UserAuthClient.addUserAuth(user);
        }
    }
    /**
     * 去数据中心校验用户是否存在
     * @author 张智威  
     * @date 2017年6月5日 下午2:57:24
     */
    @Override
    public void loginByPhoneAndSMS(SessionDTO sessionDTO, String phone, String captcha) throws Exception {
        Long merchantId = sessionDTO.getMerchant().getMerchantId();// 获取商户id

        if (merchantId == null)// 如果当前商户id是空
            throw new BizException("E5052006",MessageUtil.getMessage("E5052006"));
        String terMac = sessionDTO.getPortalParam().getUserMac();// 用户手机mac是不能为空的
        if (StringUtil.isBlank(terMac))// 如果当前商户id是空
            throw new BizException("E0000002", MessageUtil.getMessage("E0000002", "terMac")); //terMac can't not be null

        // 第三方的用户接口不应该把代码写在主业务逻辑里 删除
        SessionUser sessionUser = sessionDTO.getSessionUser();

//        if (sessionUser == null) {// sessionUser初始化
//            sessionUser = new SessionUser();
//            sessionDTO.setSessionUser(sessionUser);
//        }
        //去数据中心查用户
        SessionUser thirdUser = getUserByPhone(phone);// 从数据中心查找用户信息
        if (thirdUser != null) {
//          
            sessionDTO.setSessionUser(thirdUser);
        } else {
            logger.error("auth接口没有查到该用户");
            throw new BizException("E4005003", MessageUtil.getMessage("E4005003"));//业务逻辑错误
        }
        //去查询用户的时长消费信息
        UserTimeInfo userTimeInfo = timeBuyService.getUserTimeInfo(sessionDTO.getSessionUser().getId(), phone,
                merchantId);
        sessionDTO.setTimeInfo(userTimeInfo);
     
    }

}

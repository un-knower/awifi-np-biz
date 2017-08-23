/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月13日 下午2:36:51
* 创建作者：许小满
* 文件名称：AuthController.java
* 版本：  v1.0
* 功能：认证--控制层
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.auth.auth.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.api.client.auth.util.AuthClient;
import com.awifi.np.biz.api.client.dbcenter.user.util.UserBaseClient;
import com.awifi.np.biz.api.client.sms.util.SmsClient;
import com.awifi.np.biz.common.base.constants.Constants;
import com.awifi.np.biz.common.base.constants.RedisConstants;
import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.system.smsconfig.service.SmsConfigService;
import com.awifi.np.biz.common.userauth.util.UserAuthUtil;
import com.awifi.np.biz.common.util.EncryUtil;
import com.awifi.np.biz.common.util.IPUtil;
import com.awifi.np.biz.common.util.RandomUtil;
import com.awifi.np.biz.common.util.RegexUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.common.util.xxtea.ByteFormat;
import com.awifi.np.biz.common.util.xxtea.XXTeaUtil;
import com.awifi.np.biz.pagesrv.api.client.thirdauth.util.ThirdAuthClient;
import com.awifi.np.biz.pagesrv.base.util.ExceptionUtil;
import com.awifi.np.biz.pagesrv.base.util.PageSessionUtil;
import com.awifi.np.biz.toe.admin.merchant.customerconfig.service.CustomerConfigService;
import com.awifi.np.biz.toe.admin.merchant.customerconfig.util.CustomerConfigUtil;
import com.awifi.np.biz.toe.admin.usrmgr.staticuser.model.StaticUser;
import com.awifi.np.biz.toe.admin.usrmgr.staticuser.service.StaticUserService;

@SuppressWarnings("rawtypes")
@Controller
public class AuthController extends BaseController {
    
    /** 短信配置 */
    @Resource(name = "smsConfigService")
    private SmsConfigService smsConfigService;
    
    /** 短信配置 */
    @Resource(name = "customerConfigService")
    private CustomerConfigService customerConfigService;
    
    /** 静态用户 */
    @Resource(name = "staticUserService")
    private StaticUserService staticUserService;
    
    /** 短信记录-业务层 */
    //@Resource(name = "smsService")
    //private SmsService smsService;

    /**
     * 获取验证码
     * @param request 请求
     * @return json
     * @author 许小满  
     * @date 2017年5月13日 下午2:47:44
     */
    @RequestMapping(value = "/auth/sendSmsCode")
    @ResponseBody
    public Map sendSmsCode(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            // 请求方法类型 校验
            if (request.getMethod().equals(HttpRequest.METHOD_GET)) {
                resultMap.put("result", "FAIL");
                resultMap.put("message", "非法请求！");
                return resultMap;
            }
            //String globalKey = StringUtils.defaultString(request.getParameter("globalKey"));//全局日志key
            //String globalValue = StringUtils.defaultString(request.getParameter("globalKey"));//全局日志value
            String devId = StringUtils.defaultString(request.getParameter("devId"));//设备ID
            //String version = StringUtils.defaultString(request.getParameter("version"));//版本号
            String phoneNumber = StringUtils.defaultString(request.getParameter("phoneNumber"));//手机号
            String userMac = StringUtils.defaultString(request.getParameter("userMac"));//用户终端MAC
            //String platform = StringUtils.defaultString(request.getParameter("platform"));//省分平台-前缀
            //String callback = StringUtils.defaultString(request.getParameter("callback"));//回调函数名
            String customerId = StringUtils.defaultString(request.getParameter("customerId"));//客户id
            
            // 请求参数 校验
            ValidUtil.valid("设备id[devId]", devId, "required");//设备id
            ValidUtil.valid("手机号[phoneNumber]", phoneNumber, "{'required':true, 'regex':'"+RegexConstants.CELLPHONE+"'}");//手机号
            ValidUtil.valid("用户MAC[userMac]", userMac, "{'required':true, 'regex':'"+RegexConstants.MAC_PATTERN+"'}");//用户mac地址
            
            // 发送短信验证码
            Long merchantId = StringUtils.isNotBlank(customerId) ? Long.parseLong(customerId) : null;//商户id
            SmsClient.sendSmsCode(merchantId, phoneNumber, userMac, null);
            //smsService.add(merchantId, phoneNumber, smsContent);//保存短信内容
            
            resultMap.put("result", "OK");
            resultMap.put("message", StringUtils.EMPTY);
        }catch (Exception e) {
            ExceptionUtil.formatMsg(request, resultMap, e, logger, "Sorry, sendSmsCode failed.");
        }
        return resultMap;
    }
    
    /**
     * 获取验证码，返回加密后的验证码
     * 张武林测试专用
     * @param request 请求
     * @return json
     * @author 许小满  
     * @date 2017年8月7日 上午8:55:35
     */
    @RequestMapping(value = "/auth/test/sendSmsCode")
    @ResponseBody
    public Map sendSmsCodeWithCode(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            // 请求方法类型 校验
            if (request.getMethod().equals(HttpRequest.METHOD_GET)) {
                resultMap.put("result", "FAIL");
                resultMap.put("message", "非法请求！");
                return resultMap;
            }
            //String globalKey = StringUtils.defaultString(request.getParameter("globalKey"));//全局日志key
            //String globalValue = StringUtils.defaultString(request.getParameter("globalKey"));//全局日志value
            String devId = StringUtils.defaultString(request.getParameter("devId"));//设备ID
            //String version = StringUtils.defaultString(request.getParameter("version"));//版本号
            String phoneNumber = StringUtils.defaultString(request.getParameter("phoneNumber"));//手机号
            String userMac = StringUtils.defaultString(request.getParameter("userMac"));//用户终端MAC
            //String platform = StringUtils.defaultString(request.getParameter("platform"));//省分平台-前缀
            //String callback = StringUtils.defaultString(request.getParameter("callback"));//回调函数名
            String customerId = StringUtils.defaultString(request.getParameter("customerId"));//客户id
            
            // 请求参数 校验
            ValidUtil.valid("设备id[devId]", devId, "required");//设备id
            ValidUtil.valid("手机号[phoneNumber]", phoneNumber, "{'required':true, 'regex':'"+RegexConstants.CELLPHONE+"'}");//手机号
            ValidUtil.valid("用户MAC[userMac]", userMac, "{'required':true, 'regex':'"+RegexConstants.MAC_PATTERN+"'}");//用户mac地址
            
            // 发送短信验证码
            Long merchantId = StringUtils.isNotBlank(customerId) ? Long.parseLong(customerId) : null;//商户id
            String smsCode = RandomUtil.getRandomNumber(6);//生成验证码(固定6位随机验证码)
            SmsClient.sendSmsCode(merchantId, phoneNumber, userMac, smsCode);
            //smsService.add(merchantId, phoneNumber, smsContent);//保存短信内容
            
            resultMap.put("result", "OK");
            resultMap.put("smsCode", XXTeaUtil.encrypt(smsCode, ByteFormat.toHex("aWiFi@123".getBytes())));//返回加密后的字符串
            resultMap.put("message", StringUtils.EMPTY);
        }catch (Exception e) {
            ExceptionUtil.formatMsg(request, resultMap, e, logger, "Sorry, sendSmsCode failed.");
        }
        return resultMap;
    }

    /**
     * 手机号认证
     * @param request 请求
     * @return json
     * @author 许小满  
     * @date 2017年5月14日 下午5:50:36
     */
    @RequestMapping(value = "/auth/phoneAuth")
    @ResponseBody
    public Map phoneAuth(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            // 请求方法类型 校验
            if (request.getMethod().equals(HttpRequest.METHOD_GET)) {
                resultMap.put("result", "FAIL");
                resultMap.put("message", "非法请求！");
                return resultMap;
            }
            String globalKey = StringUtils.defaultString(request.getParameter("globalKey"));//全局日志key
            //String globalValue = StringUtils.defaultString(request.getParameter("globalValue"));//全局日志value
            //String version = StringUtils.defaultString(request.getParameter("version"));//版本号:v1.0 v1.1
            //String plateformName = StringUtils.defaultString(request.getParameter("plateformName"));//平台名称
            String type = StringUtils.defaultString(request.getParameter("type"));//1: 手机号+验证码; 2: 手机号认证; 3: IVR语音认证; 4: 免认证
            String traceType = StringUtils.defaultString(request.getParameter("traceType"));//phone:手机号，username：用户名，passport：护照号，identity：身份证号
            //String traceValue = StringUtils.defaultString(request.getParameter("traceValue"));//当traceType为非phone时，必填，填写对应值
            String phoneNumber = StringUtils.defaultString(request.getParameter("phoneNumber"));//手机号, type=2为非必填
            String userMac = StringUtils.defaultString(request.getParameter("userMac"));//用户终端MAC
            String devId = StringUtils.defaultString(request.getParameter("devId"));//设备ID
            String smsCode = StringUtils.defaultString(request.getParameter("smsCode"));//验证码,type=1时必填
            String userIp = StringUtils.defaultString(request.getParameter("userIp"));//用户IP
            String publicUserIp = IPUtil.getIpAddr(request);//用户公网IP,type=2时必填
            String publicUserPort = IPUtil.getRemotePort(request);//用户公网端口,type=2时且version>=v1.0以上必填
            //String apMac = StringUtils.defaultString(request.getParameter("apMac"));//设备MAC
            String ssid = StringUtils.defaultString(request.getParameter("ssId"));//SSID
            //String callback = StringUtils.defaultString(request.getParameter("callback"));//回调函数名
            String acName = StringUtils.defaultString(request.getParameter("acName"));//nas设备名称，NAS认证必填
            String platform = StringUtils.defaultString(request.getParameter("platform"));//省分平台-前缀
            String customerId = StringUtils.defaultString(request.getParameter("customerId"));//客户id
            
            // 请求参数 校验
            ValidUtil.valid("设备id[devId]", devId, "required");//设备id
            ValidUtil.valid("手机号[phoneNumber]", phoneNumber, "{'required':true, 'regex':'"+RegexConstants.CELLPHONE+"'}");//手机号
            ValidUtil.valid("用户MAC[userMac]", userMac, "{'required':true, 'regex':'"+RegexConstants.MAC_PATTERN+"'}");//用户mac地址
            
            Long customerIdLong = StringUtils.isNotBlank(customerId) ? Long.parseLong(customerId) : null;//商户id-long
            String authType = null;//认证类型,sms:验证码认证、account:账号密码认证、authed:免认证、green:无感知认证、irv:IVR认证、platform:平台认证、temp:临时放行
                    
            //手机号+验证码认证
            if(type.equals("1")){
                String redisKey = RedisConstants.SMS + phoneNumber + "_" + userMac;
                List<String> smsCodeList = RedisUtil.hmget(redisKey, "smsCode");
                if(smsCodeList == null || smsCodeList.size() <= 0){
                    resultMap.put("result", "FAIL");
                    resultMap.put("message", "sms_code_expired");// 验证码过期或未发送
                    return resultMap;
                }
                String smsCodeCache = smsCodeList.get(0);//redis缓存中的验证码
                if(StringUtils.isBlank(smsCodeCache)){
                    resultMap.put("result", "FAIL");
                    resultMap.put("message", "sms_code_expired");// 验证码过期或未发送
                    return resultMap;
                } else if (smsCodeCache.equals(smsCode)){//验证码正确
                    //type = "2";//走平台认证
                }else {
                    resultMap.put("result", "FAIL");
                    resultMap.put("message", "sms_code_error");// 验证码错误
                    return resultMap;
                }
                authType = "platform";
            }
            //免认证，场景：老用户、自动登录
            else if(type.equals("4")){
                authType = "authed";
            }
            //其它情况，暂不支持
            else {
                throw new BizException("E2700001", "type["+type+"]超出了范围[1|4]！");
            }
            
            Map<String, String> paramMap = new HashMap<String, String>();//参数
            
            paramMap.put("logid", globalKey);//日志ID
            paramMap.put("deviceid", devId);//设备ID
            paramMap.put("usermac", userMac);//用户MAC
            paramMap.put("platform", Constants.NP);//平台名称,toe:商户平台、mws:微站平台、msp:园区平台、portal:页面服务
            paramMap.put("authtype", authType);//认证类型,sms:验证码认证、account:账号密码认证、authed:免认证、green:无感知认证、irv:IVR认证、platform:平台认证、temp:临时放行
            paramMap.put("publicuserip", publicUserIp);//用户真实IP,当平台请求时必填（ToE/微站等）
            paramMap.put("publicuserport", publicUserPort);//用户真实端口,当平台请求时必填（ToE/微站等）
            paramMap.put("mobilephone", phoneNumber);//手机号：sms、authed、green、ivr认证时必填
            //paramMap.put("smscode", smsCode);//验证码：sms认证时必填
            //paramMap.put("username", userName);//用户名：account认证时必填
            //paramMap.put("password", password);//密码：account认证时必填
            paramMap.put("tracetype", traceType);//溯源类型：platform认证必填，[phone|username|passport|identity]
            paramMap.put("tracevalue", phoneNumber);//溯源值：platform认证必填
            paramMap.put("userip", userIp);//用户IP：NAS类设备认证必填
            paramMap.put("nasname", acName);//NAS设备名称：NAS类设备认证必填
            paramMap.put("ssid", ssid);//无线热点名称（需中文转义）
            //paramMap.put("callback", callback);//回调函数名称：JSONP格式返回需要带上该参数。如果不带则不支持JSONP
            //paramMap.put("gwaddress", gwaddress);//设备放行IP：无感知时必填
            //paramMap.put("gwport", gwport);//设备放行端口：无感知时必填
            //paramMap.put("url", url);//重定向URL：无感知时必填

            String userAgent = request.getHeader("User-Agent");//请求头里面的userAgent
            Map<String, Object> authResultMap = AuthClient.auth(paramMap, platform, userAgent);//调用接入接口
            
            putToCache(devId,phoneNumber,userMac,type,userAgent);//缓存设备认证记录
            
            resultMap.put("result", "OK");
            resultMap.put("message", StringUtils.EMPTY);
            String token = (String)authResultMap.get("data");//胖AP放行token
            if(StringUtils.isNotBlank(token)){
                resultMap.put("token", token);//胖AP放行token号
            }
            //获取数据中心--用户id
            Long userId = UserBaseClient.getUserIdByPhone(phoneNumber);
            if(userId != null){
                resultMap.put("userId", userId);//用户id
                PageSessionUtil.putUserToSession(request, customerIdLong, devId, userId);//将用户信息存入session中
            }
        }catch (Exception e) {
            ExceptionUtil.formatMsg(request, resultMap, e, logger, "Sorry, authentication failed.");
        }
        return resultMap;
    }
    
    /**
     * 缓存设备认证记录
     * @param devId 设备id
     * @param phoneNumber 手机号
     * @param userMac 终端mac
     * @param type 类型 1手机号+验证码认证 4老用户、自动登录
     * @param userAgent 终端类型
     * @author 周颖  
     * @date 2017年7月12日 上午9:23:00
     */
    private void putToCache(String devId, String phoneNumber, String userMac, String type, String userAgent){
        String authType = null;
        //手机号+验证码认证
        if(type.equals("1")){
            authType = Constants.SMS;
        }
        //免认证，场景：老用户、自动登录
        else if(type.equals("4")){
            authType = Constants.AUTHED;
        }
        //其它情况，暂不支持
        else {
            logger.debug("type["+type+"]超出了范围[1|4]！");;
        }
        UserAuthUtil.putToCache(devId,phoneNumber,userMac,authType,userAgent);//缓存设备认证记录
    }
    
    /**
     * 手机号认证（静态用户名认证）
     * @param request 请求
     * @return json
     * @author 许小满  
     * @date 2016年1月8日 上午1:06:47
     */
    @RequestMapping(value = "/staticuser/phoneAuth")
    @ResponseBody
    public Map userAuth(HttpServletRequest request) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        try {
            // 请求方法类型 校验
            if (request.getMethod().equals(HttpRequest.METHOD_GET)) {
                resultMap.put("result", "FAIL");
                resultMap.put("message", "非法请求！");// 非法请求！
                return resultMap;
            }
            String globalKey = StringUtils.defaultString(request.getParameter("globalKey"));//全局日志key
            //String globalValue = StringUtils.defaultString(request.getParameter("globalValue"));//全局日志value
            //String version = StringUtils.defaultString(request.getParameter("version"));//版本号:v1.0 v1.1
            //String plateformName = StringUtils.defaultString(request.getParameter("plateformName"));//平台名称
            String traceType = StringUtils.defaultString(request.getParameter("traceType"));//phone:手机号，username：用户名，passport：护照号，identity：身份证号
            String traceValue = StringUtils.defaultString(request.getParameter("traceValue"));//当traceType为非phone时，必填，填写对应值
            String userMac = StringUtils.defaultString(request.getParameter("userMac"));//用户终端MAC
            String devId = StringUtils.defaultString(request.getParameter("devId"));//设备ID
            //String smsCode = StringUtils.defaultString(request.getParameter("smsCode"));//验证码,type=1时必填
            String userIp = StringUtils.defaultString(request.getParameter("userIp"));//用户IP
            String publicUserIp = IPUtil.getIpAddr(request);//用户公网IP,type=2时必填
            String publicUserPort = IPUtil.getRemotePort(request);//用户公网端口,type=2时且version>=v1.0以上必填
            //String apMac = StringUtils.defaultString(request.getParameter("apMac"));//设备MAC
            String ssid = StringUtils.defaultString(request.getParameter("ssId"));//SSID
            String acName = StringUtils.defaultString(request.getParameter("acName"));//nas设备名称，NAS认证必填
            String platform = StringUtils.defaultString(request.getParameter("platform"));//省分平台-前缀
            
            String customerId = StringUtils.defaultString(request.getParameter("customerId"));//客户id
            String userName = StringUtils.defaultString(request.getParameter("userName"));//用户名
            String password = StringUtils.defaultString(request.getParameter("password"));//密码
            
            // 请求参数 校验
            ValidUtil.valid("商户id[customerId]", customerId, "required");//商户id
            ValidUtil.valid("设备ID[devId]", devId, "required");//设备ID
            ValidUtil.valid("用户名", userName, "required");//用户名
            ValidUtil.valid("密码", password, "required");//密码
            
            
            String cellphone = null;//手机号
            Long customerIdLong = Long.parseLong(customerId);//客户id
            boolean isSC = RegexUtil.match(userName, RegexConstants.SC_PATTERN, Pattern.CASE_INSENSITIVE);//判断是否是特通账号
            Map<String, String> paramMap = new HashMap<String, String>();//参数
            String staticUserRemoteAuthUrl = !isSC ? CustomerConfigUtil.getStaticUserAuthUrlCache(customerIdLong) : null;//第三方静态用户名认证地址
            
            //如果是特通账号时，进行本地特通认证
            if(isSC){
                this.localSCPhoneAuth(customerIdLong, userName, password, traceType, traceValue, paramMap, resultMap);
            }
            //如果第三方认证地址不为空时，进行第三方认证
            else if(StringUtils.isNotBlank(staticUserRemoteAuthUrl)){
                this.remotePhoneAuth(staticUserRemoteAuthUrl, userName, password, cellphone, traceType, traceValue, paramMap, resultMap);
                cellphone = paramMap.get("mobilephone");//手机号
            }
            //其它情况，默认进行本地认证
            else{
                this.localPhoneAuth(customerIdLong, userName, password, cellphone, traceType, traceValue, paramMap, resultMap);
                cellphone = paramMap.get("mobilephone");//手机号
            }
            
            String result = (String)resultMap.get("result");//执行结果
            if(StringUtils.isNotBlank(result) && result.equals("FAIL")){//如果执行失败，认证结束
                return resultMap;
            }
            
            logger.debug("traceValue=" + traceValue);
            
            paramMap.put("logid", globalKey);//日志ID
            paramMap.put("deviceid", devId);//设备ID
            paramMap.put("usermac", userMac);//用户MAC
            paramMap.put("platform", Constants.NP);//平台名称,toe:商户平台、mws:微站平台、msp:园区平台、portal:页面服务
            paramMap.put("authtype", "platform");//认证类型,sms:验证码认证、account:账号密码认证、authed:免认证、green:无感知认证、irv:IVR认证、platform:平台认证、temp:临时放行
            paramMap.put("publicuserip", publicUserIp);//用户真实IP,当平台请求时必填（ToE/微站等）
            paramMap.put("publicuserport", publicUserPort);//用户真实端口,当平台请求时必填（ToE/微站等）
            //paramMap.put("mobilephone", phoneNumber);//手机号：sms、authed、green、ivr认证时必填
            //paramMap.put("smscode", smsCode);//验证码：sms认证时必填
            //paramMap.put("username", userName);//用户名：account认证时必填
            //paramMap.put("password", password);//密码：account认证时必填
            //paramMap.put("tracetype", traceType);//溯源类型：platform认证必填，[phone|username|passport|identity]
            //paramMap.put("tracevalue", traceValue);//溯源值：platform认证必填
            paramMap.put("userip", userIp);//用户IP：NAS类设备认证必填
            paramMap.put("nasname", acName);//NAS设备名称：NAS类设备认证必填
            paramMap.put("ssid", ssid);//无线热点名称（需中文转义）
            //paramMap.put("callback", callback);//回调函数名称：JSONP格式返回需要带上该参数。如果不带则不支持JSONP
            //paramMap.put("gwaddress", gwaddress);//设备放行IP：无感知时必填
            //paramMap.put("gwport", gwport);//设备放行端口：无感知时必填
            //paramMap.put("url", url);//重定向URL：无感知时必填
            
            String userAgent = request.getHeader("User-Agent");//请求头里面的userAgent
            
            Map<String, Object> authResultMap = AuthClient.auth(paramMap, platform, userAgent);//调用接入接口
            
            UserAuthUtil.putToCache(devId, cellphone, userMac, Constants.ACCOUNT, userAgent);
            
            resultMap.put("result", "OK");
            resultMap.put("message", StringUtils.EMPTY);
            resultMap.put("phoneNumber", cellphone);//手机号
            String token = (String)authResultMap.get("data");//胖AP放行token
            if(StringUtils.isNotBlank(token)){
                resultMap.put("token", token);//胖AP放行token号
            }
            //获取数据中心--用户id
            Long userId = UserBaseClient.getUserIdByPhone(cellphone);
            if(userId != null){
                resultMap.put("userId", userId);//用户id
                PageSessionUtil.putUserToSession(request, customerIdLong, devId, userId);//将用户信息存入session中
            }
        }catch (Exception e) {
            ExceptionUtil.formatMsg(request, resultMap, e, logger, "Sorry, authentication failed.");
        }
        return resultMap;
    }
    
    /**
     * 本地特通-静态用户名认证
     * @param customerId 客户id
     * @param userName 用户名
     * @param password 密码
     * @param traceType phone:手机号，username：用户名，passport：护照号，identity：身份证号
     * @param traceValue 当traceType为非phone时，必填，填写对应值
     * @param paramMap 认证放行参数map
     * @param resultMap 结果map
     * @author 许小满  
     * @date 2016年10月28日 下午5:37:50
     */
    private void localSCPhoneAuth(Long customerId, String userName, String password, String traceType, String traceValue,
            Map<String, String> paramMap, Map<String, Object> resultMap){
        StaticUser staticUser = staticUserService.getStaticUser(customerId, userName, EncryUtil.getMd5Str(password));
        if(staticUser == null){
            resultMap.put("result", "FAIL");
            resultMap.put("message", "用户名与密码不匹配，请重新输入!");
            return;
        }
        logger.debug("提示：当前账号为特通账号["+ userName +"]");
        paramMap.put("tracetype", "username");//phone:手机号，username：用户名，passport：护照号，identity：身份证号
        paramMap.put("tracevalue", userName.toUpperCase());//当traceType为非phone时，必填，填写对应值
        paramMap.put("mobilephone", StringUtils.EMPTY);//手机号
    }
    
    /**
     * 本地静态用户名认证
     * @param customerId 客户id
     * @param userName 用户名
     * @param password 密码
     * @param cellphone 手机号
     * @param traceType phone:手机号，username：用户名，passport：护照号，identity：身份证号
     * @param traceValue 当traceType为非phone时，必填，填写对应值
     * @param paramMap 认证放行参数map
     * @param resultMap 结果map
     * @author 许小满  
     * @date 2016年10月28日 下午5:37:50
     */
    private void localPhoneAuth(Long customerId, String userName, String password, String cellphone, String traceType, String traceValue,
            Map<String, String> paramMap, Map<String, Object> resultMap){
        StaticUser staticUser = staticUserService.getStaticUser(customerId, userName, EncryUtil.getMd5Str(password));
        if(staticUser == null){
            resultMap.put("result", "FAIL");
            resultMap.put("message", "用户名与密码不匹配，请重新输入!");
            return;
        }
        cellphone = StringUtils.defaultString(staticUser.getCellphone());//手机号
        
        /* 静态用户认证优化： 支持 手机号、护照、身份证 3种方式认证 */
        Integer userInfoType = staticUser.getUserInfoType(); //用户信息类别:1 手机号、2 护照号 、3 身份证号 
        if(userInfoType == null || userInfoType == 1){//手机号
            //默认为手机号，不做处理
            traceValue = cellphone;
        }else if(userInfoType == 2){//护照号
            traceType = "passport";
            traceValue = StringUtils.defaultString(staticUser.getPassport());
            cellphone = StringUtils.EMPTY;//手机号设置为空
        }else if(userInfoType == 3){//身份证号
            traceType = "identity";
            traceValue = StringUtils.defaultString(staticUser.getIdentityCard());
            cellphone = StringUtils.EMPTY;//手机号设置为空
        }else{
          //其它情况默认为手机号，不做处理
            logger.error("错误：用户信息类别["+userInfoType+"]超出了范围.");
        }
        paramMap.put("tracetype", traceType);//phone:手机号，username：用户名，passport：护照号，identity：身份证号
        paramMap.put("tracevalue", traceValue);//当traceType为非phone时，必填，填写对应值
        paramMap.put("mobilephone", cellphone);//手机号
    }
    
    /**
     * 远程静态用户名认证
     * @param staticUserRemoteAuthUrl 第三方静态用户名认证地址
     * @param userName 用户名
     * @param password 密码
     * @param cellphone 手机号
     * @param traceType phone:手机号，username：用户名，passport：护照号，identity：身份证号
     * @param traceValue 当traceType为非phone时，必填，填写对应值
     * @param paramMap 认证放行参数map
     * @param resultMap 结果map
     * @throws Exception 异常
     * @author 许小满  
     * @date 2016年10月28日 下午5:37:50
     */
    private void remotePhoneAuth(String staticUserRemoteAuthUrl, String userName, String password, String cellphone, String traceType, String traceValue,
            Map<String, String> paramMap, Map<String, Object> resultMap) throws Exception{
        Map<String, Object> interfaceReturnValueMap = ThirdAuthClient.staticUserAuth(staticUserRemoteAuthUrl, userName, password);//第三方-静态用户名认证
        if(!"OK".equals((String) interfaceReturnValueMap.get("result"))){
            String message = (String)interfaceReturnValueMap.get("message");
            resultMap.put("result", "FAIL");
            resultMap.put("message", message);
            return;
        }
        //接口返回值 校验
        traceType = (String)interfaceReturnValueMap.get("traceType");//phone:手机号，passport：护照号，identity：身份证号
        traceValue = (String)interfaceReturnValueMap.get("traceValue");//填写对应值
        if(StringUtils.isBlank(traceType)){
            resultMap.put("result", "FAIL");
            resultMap.put("message", "抱歉，认证失败（错误信息：参数traceType不能为空！）");
            return;
        }
        if(StringUtils.isBlank(traceValue)){
            resultMap.put("result", "FAIL");
            resultMap.put("message", "抱歉，认证失败（错误信息：参数traceValue不能为空！）");
            return;
        }
        boolean isTraceTypePhone = traceType.equals("phone");//判断是否为手机号
        boolean isTraceTypeRight = isTraceTypePhone || traceType.equals("passport") || traceType.equals("identity");//判断traceType是否符合规范
        if(!isTraceTypeRight){//判断traceType是否符合规范
            resultMap.put("result", "FAIL");
            resultMap.put("message", "抱歉，认证失败（错误信息：参数traceType["+ traceType +"]超出了范围，必须在[phone|passport|identity]之内！）");
            return;
        }
        
        if(isTraceTypePhone){//手机号
            cellphone = traceValue;
        }
        //passport：护照号，identity：身份证号
        else{
            cellphone = StringUtils.EMPTY;//手机号设置为空
        }
        paramMap.put("tracetype", traceType);//phone:手机号，username：用户名，passport：护照号，identity：身份证号
        paramMap.put("tracevalue", traceValue);//当traceType为非phone时，必填，填写对应值
        paramMap.put("mobilephone", cellphone);//手机号
    }
}

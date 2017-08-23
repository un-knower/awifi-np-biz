/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月16日 下午12:07:26
* 创建作者：许小满
* 文件名称：WechatController.java
* 版本：  v1.0
* 功能：微信相关接口
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.thirdapp.wechat.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.api.client.auth.util.AuthClient;
import com.awifi.np.biz.common.base.constants.Constants;
import com.awifi.np.biz.common.base.constants.RedisConstants;
import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.IPUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.KeyUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.mws.merchant.app.service.AppMerchantRelationService;
import com.awifi.np.biz.pagesrv.api.client.thirdapp.wechat.util.WeChatClient;
import com.awifi.np.biz.pagesrv.base.util.ExceptionUtil;
import com.awifi.np.biz.toe.admin.thirdapp.application.model.Application;
import com.awifi.np.biz.toe.admin.thirdapp.application.service.ApplicationService;
import com.awifi.np.biz.toe.admin.thirdapp.microshop.model.Microshop;
import com.awifi.np.biz.toe.admin.thirdapp.microshop.service.MicroshopService;
import com.awifi.np.biz.toe.admin.thirdapp.microshop.service.WechatAttentionService;

@Controller
public class WechatController extends BaseController {

    /** 微旺铺service */
    @Resource(name="microshopService")
    private MicroshopService microshopService;
    
    /** 微旺铺日志service */
    //@Resource(name="microshopLogService")
    //private MicroshopLogService microshopLogService;
    
    /** 微信关注service */
    @Resource(name="wechatAttentionService")
    private WechatAttentionService wechatAttentionService;
    
    /** 应用service */
    @Resource(name="applicationService")
    private ApplicationService applicationService;
    
    /** 商户-应用关系表 service */
    @Resource(name="appMerchantRelationService")
    private AppMerchantRelationService appMerchantRelationService;
    
    
    
    
    /**
     * 微信认证(认证页/认证后页)
     * authType:
     *      UNAUTH   未认证
     *      AUTHED   已认证
     * @param request 请求
     * @return resultMap
     * @author 亢燕翔 
     * @date 2016年3月21日 下午4:36:32
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/wechat/wechatCertification")
    @ResponseBody
    public Map wechatCertification(HttpServletRequest request){
        //定义变量
        Map<String,Object> resultMap = new HashMap<String, Object>();
        try{
            // 请求方法类型 校验
            if(request.getMethod().equals(HttpRequest.METHOD_POST)) {
                resultMap.put("result", "FAIL");
                resultMap.put("message", MessageUtil.getMessage("request_illegal"));
                return resultMap;
            }
            //*********************************前端传入参数***************************************************
            String globalKey = StringUtils.defaultString(request.getParameter("global_key"));//全局日志key
            String globalValue = StringUtils.defaultString(request.getParameter("global_value"));//全局日志key
            
            String customerId = StringUtils.defaultString(request.getParameter("customer_id"));//客户ID
            
            String devId = StringUtils.defaultString(request.getParameter("dev_id"));//设备ID
            String acName = StringUtils.defaultString(request.getParameter("ac_name"));//AC_NAME设备名称
            String devMac = StringUtils.defaultString(request.getParameter("dev_mac"));//设备MAC
            String ssid = StringUtils.defaultString(request.getParameter("ssid"));//热点名称
            
            String userPhone = StringUtils.defaultString(request.getParameter("user_phone"));//用户手机号
            String userMac = StringUtils.defaultString(request.getParameter("user_mac"));//用户MAC
            String userIp = StringUtils.defaultString(request.getParameter("user_ip"));//用户IP
            
            String authType = StringUtils.defaultString(request.getParameter("auth_type"));//查询状态
            String platform = StringUtils.defaultString(request.getParameter("platform"));//省份平台前缀
            String userAgent = request.getHeader("User-Agent");//请求头里面的userAgent
            
            //--1--参数校验
            ValidUtil.valid("设备id[devId]", devId, "required");//设备id
            ValidUtil.valid("商户id[customerId]", devId, "required");//商户id
            
            //--2--获取shopId
            logger.debug("提示：请求微旺铺获取SHOPID(参数:customerId="+customerId+")");
            Microshop microshop = microshopService.getByMerchantId(Long.parseLong(customerId));
            if(microshop == null){
                throw new ValidException("E2700003", MessageUtil.getMessage("E2700003"));//该商户尚未关联公众号，请先联系商户关联公众号！
            }
            String shopId = StringUtils.defaultString(microshop.getShopId());//微旺铺shopid
            if(StringUtils.isBlank(shopId)){
                throw new ValidException("E2700004", MessageUtil.getMessage("E2700004"));//通过商户id查询不到shopId！
            }
            
            //--3--请求接入放行(认证页执行此处方法)
            if("unauth".equals(authType)){//认证页
                String token = provisionalRelease(request, globalKey, globalValue, userMac, devId, userIp, devMac, ssid, acName, platform, userAgent);
                if(StringUtils.isNotBlank(token)){
                    resultMap.put("token", token);//胖AP放行token号
                }
            }
            if("authed".equals(authType) && StringUtils.isBlank(userPhone)){
                logger.debug("提示：认证后页loginType="+authType+"&手机号＝"+userPhone);
                throw new BizException("E2700011", "认证后页，手机号不允许为空!");
            }
            
            //--4--前端传入数据保存在redis中(有效时间10分钟)
            String redisKey = RedisConstants.PORTAL_PARAM + KeyUtil.generateKey();
            Integer forceAttention = microshop.getForceAttention();//是否强制关注:-1 非强制关注、1 强制关注
            String forceAttentionStr = forceAttention != null ? forceAttention.toString() : StringUtils.EMPTY;//是否强制关注:-1 非强制关注、1 强制关注
            saveToRedis(globalKey, globalValue, userMac, devId, userIp, devMac, ssid, acName, userPhone, authType, customerId, forceAttentionStr, redisKey, userAgent, platform, shopId);
            //--5--唤起微信
            String wwpAppId = microshop.getWwpAppid();//微旺铺应用id
            String wifiKey = microshop.getWifiKey();//微旺铺应用秘钥
            Application application = applicationService.getByAppid(microshop.getAppid());//通过appid获取对应的应用信息
            logger.debug("提示：请求微旺铺唤起微信(wwpAppId="+wwpAppId+",userMac="+userMac+",ssid="+ssid+",wifiKey="+wifiKey+",devId="+devId+",acName="+acName+",customerId="+customerId+",shopId="+shopId+")***********");
            String value = WeChatClient.callWechat(wwpAppId,redisKey,"",shopId,userMac,devMac,devId,"",wifiKey,userPhone,authType,forceAttentionStr,application.getWechatAuthUrl());
            logger.debug("提示：唤起微信的返回值[value]="+value);
            resultMap.put("result", "OK");
            resultMap.put("message", StringUtils.EMPTY);
            resultMap.put("data", value);
            //--6--判断微信是否唤起成功
            if(value.contains("true")){
                logger.debug("5.唤起微信成功");
            }else{
                logger.debug("4.唤起微信失败");
            }
            //--7--保存微信关注记录
            wechatAttentionService.addWithCheck(shopId, userPhone, userMac);
        } catch(Exception e) {
            ExceptionUtil.formatMsg(request, resultMap, e, logger, "微信呼起失败!");
        }
        return resultMap;
    }
    
    /**
     * 请求接入临时放行
     * @param request 请求
     * @param globalKey 日志key
     * @param globalValue 日志value
     * @param userMac 用户mac
     * @param devId 设备ID
     * @param userIp 用户IP
     * @param devMac 用户mac
     * @param ssid 热点
     * @param acName 设备名称
     * @param platform 平台
     * @param userAgent userAgent
     * @return token
     * @throws Exception 
     * @author kangyanxiang 
     * @date Nov 17, 2016 10:24:00 AM
     */
    private String provisionalRelease(HttpServletRequest request, String globalKey, String globalValue, String userMac,
            String devId, String userIp, String devMac, String ssid, String acName, String platform,String userAgent) throws Exception {
        logger.debug("提示：***********请求接入放行***********"+"userMac="+userMac+"&devId="+devId+"&userIp="+userIp+"&devMac="+devMac+"&ssid="+ssid+"&acName="+acName);
        String publicUserIp = IPUtil.getIpAddr(request);//用户公网ip
        String publicUserPort = IPUtil.getRemotePort(request);//用户公网端口,type=2时且version>=v1.0以上必填
        
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("logid", globalKey);//日志ID
        paramMap.put("deviceid", devId);//设备ID
        paramMap.put("usermac", userMac);//用户MAC
        paramMap.put("platform", Constants.NP);//平台名称,toe:商户平台、mws:微站平台、msp:园区平台、portal:页面服务
        paramMap.put("authtype", "temp");//认证类型,sms:验证码认证、account:账号密码认证、authed:免认证、green:无感知认证、irv:IVR认证、platform:平台认证、temp:临时放行
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
        
        Map<String, Object> authResultMap = AuthClient.auth(paramMap, platform, userAgent);
        return (String)authResultMap.get("data");
    }
    
    /**
     * 将共享数据保存至redis
     * @param globalKey 日志key
     * @param globalValue 日志value
     * @param userMac 用户mac
     * @param devId 设备ID
     * @param userIp 用户IP
     * @param devMac 设备MAC
     * @param ssid 热点
     * @param acName 设备名称
     * @param userPhone 用户手机
     * @param authType 认证类型
     * @param customerId 客户ID
     * @param forceAttention 是否强制关注
     * @param redisKey redisKey
     * @param platform 省份平台前缀
     * @param userAgent 请求头里面的userAgent
     * @param shopId 微旺铺shopid
     * @return 封装成json的参数
     * @author kangyanxiang 
     * @date Nov 17, 2016 10:09:52 AM
     */
    private String saveToRedis(String globalKey, String globalValue, String userMac, String devId, String userIp,
            String devMac, String ssid, String acName, String userPhone, String authType, String customerId,
            String forceAttention, String redisKey, String userAgent, String platform, String shopId) {
        Map<String, String> redisMap = new HashMap<String, String>();
        redisMap.put("globalKey", globalKey);// 全局日志key
        redisMap.put("globalValue", globalValue);// 全局日志value
        redisMap.put("userMac", userMac);// 用户终端MAC
        redisMap.put("devId", devId);// 设备ID
        redisMap.put("userIp", userIp);// 用户IP
        redisMap.put("apMac", devMac);// 设备MAC
        redisMap.put("ssid", ssid);// SSID
        redisMap.put("acName", acName);// nas设备名称，NAS认证必填
        redisMap.put("userPhone", userPhone);//手机号
        redisMap.put("loginType", authType);//登录类型: authed 代表已认证过，控制页面显示“免费上网”；unauth 代表未认证过，页面显示 “手机号认证”或“用户名认证”;welcome 代表认证已放行（如果是胖AP需要用token调用设备总线接口），可以上外网
        redisMap.put("customerId", customerId);//商户id
        redisMap.put("forceAttention", forceAttention);//是否强制关注
        redisMap.put("userAgent", userAgent);// 请求头里面的userAgent
        redisMap.put("platform", platform);// 平台名称
        redisMap.put("shopId", shopId);// 微旺铺shopid
        redisMap.put("portal", "5.0");//页面引擎版本：用于区分是4.x还是5.x来呼起的页面
        String redisParams = JsonUtil.toJson(redisMap);
        RedisUtil.set(redisKey, redisParams, RedisConstants.PORTAL_PARAM_TIME);
        return redisParams;
    }
    
    /**
     * 判断用户是否已关注公众号
     * @param params 参数
     * @return json
     * @author 许小满  
     * @date 2017年6月18日 下午6:52:58
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(method=RequestMethod.GET, value="/pagesrv/wechat/attention")
    @ResponseBody
    public Map<String,Object> checkAttention(
            @RequestParam(value="params",required=true)String params//json格式参数
    ){
        Map<String,Object> paramsMap = JsonUtil.fromJson(params, Map.class);//请求参数 json格式
        Object merchantIdObj = paramsMap.get("merchantId");//商户id
        String userMac = (String)paramsMap.get("userMac");//用户终端MAC
        //参数校验
        ValidUtil.valid("商户id[merchantId]", merchantIdObj, "{'required':true, 'numeric':true}");//商户id
        ValidUtil.valid("用户终端MAC[userMac]", userMac, "{'required':true, 'regex':'" + RegexConstants.MAC_PATTERN + "'}");//用户终端MAC
        
        Long merchantId = CastUtil.toLong(merchantIdObj);//商户id
        
        //1.判断商户是否已经开通“微信”应用
        boolean isOpen = appMerchantRelationService.isOpen(merchantId, 1000030L);//其中  1000030代表微信关注应用(微站)
        if(!isOpen){//如果未开通，不显示“进入公众号”按钮
            throw new ValidException("E2700006", MessageUtil.getMessage("E2700006"));//该商户未开通微信应用！
        }
        //获取shopId
        Microshop microshop = microshopService.getByMerchantId(merchantId);
        if(microshop == null){
            throw new ValidException("E2700003", MessageUtil.getMessage("E2700003"));//该商户尚未关联公众号，请先联系商户关联公众号！
        }
        String shopId = StringUtils.defaultString(microshop.getShopId());//微旺铺shopid
        if(StringUtils.isBlank(shopId)){
            throw new ValidException("E2700004", MessageUtil.getMessage("E2700004"));//通过商户id查询不到shopId！
        }
        Integer attentionFlag = wechatAttentionService.getAttentionFlag(shopId, userMac);
        if(attentionFlag == null){
            //throw new ValidException("E2700005", MessageUtil.getMessage("E2700005", new Object[]{shopId, userMac}));//通过shopId[{0}]、用户MAC[{1}]未查询不到微信关注信息！
            attentionFlag = -1;//将关注状态设置为未关注
            wechatAttentionService.add(shopId, null, userMac);//新增微信关注记录
        }
        Map<String,Object> resultMap = new HashMap<String,Object>(1);
        resultMap.put("attentionFlag", attentionFlag);//关注标识：-1 未关注、1 已关注
        return this.successMsg(resultMap);
    }
}

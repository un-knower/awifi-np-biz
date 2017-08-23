/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月11日 下午6:39:34
* 创建作者：许小满
* 文件名称：PortalController.java
* 版本：  v1.0
* 功能：Portal -- 控制层相关代码
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.api.server.portal.controller;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;
import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.common.base.constants.Constants;
import com.awifi.np.biz.common.base.constants.RedisConstants;
import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.pagesrv.api.server.portal.model.PortalParams;
import com.awifi.np.biz.pagesrv.base.util.ExceptionUtil;
import com.awifi.np.biz.toe.admin.strategy.service.StrategyService;
import com.awifi.np.biz.toe.portal.site.service.SiteService;

@Controller
@SuppressWarnings({"rawtypes", "unchecked"})
public class PortalApiController extends BaseController{

    /** 策略Service */
    @Resource(name = "strategyService")
    private StrategyService strategyService;
    
    /** 站点Service */
    @Resource(name = "siteService")
    private SiteService siteService;
    
    /**
     * 页面独立服务 与 接入 对接接口
     * @param request 请求
     * @param response response
     * @return json
     * @author 许小满  
     * @date 2015年12月25日 上午12:20:15
     */
    @RequestMapping(value = "/portal")
    @ResponseBody
    public Map portal(HttpServletRequest request, HttpServletResponse response){
        long beginTime = System.currentTimeMillis();
        Map<String,Object> resultMap = new HashMap<String, Object>();
        try{ 
            //1.判断浏览器版本是否低于ie8
            //1.1 如果是
            if(this.isLowBrowser(request)){
                this.forwardLowBrowser(request, response, resultMap);//跳转至低浏览器页面
                return resultMap;
            }
            //1.2 如果不是
            //1.2.1 获取请求中的参数
            PortalParams portalParams = packagePortalParams(request);//封装portal页面参数
            logger.debug(portalParams);//打印参数
            // 请求参数 校验
            ValidUtil.valid("日志ID[logid]", portalParams.getGlobalKey(), "required");//全局日志key
            ValidUtil.valid("设备id[deviceid]", portalParams.getDevId(), "required");//设备id
            ValidUtil.valid("用户MAC[usermac]", portalParams.getUserMac(), "{'required':true, 'regex':'"+RegexConstants.MAC_PATTERN+"'}");//用户mac地址
            ValidUtil.valid("用户类型[usertype]", portalParams.getUserType(), "required");//用户类型： NEW_USER 新用户、EXEMPT_AUTH_USER 免认证用户
            
            // 解析设备信息
            Device device = this.getDevice(request);//通过devId获取设备信息
            
            // 获取商户id
            Long customerId = getCustomerIdWithDefault(device);//获取商户id，如果为空，使用默认商户id
            // 解析商户信息
            Merchant merchant = MerchantClient.getByIdCache(customerId);//通过商户id获取商户信息
            if(merchant == null){
                resultMap.put("result", "FAIL");
                resultMap.put("message", "通过商户id[" + customerId + "]未找到商户信息！");
                return resultMap;
            }
            //1.2.3 获取站点id
            Long siteId = getSiteId(merchant, device, portalParams.getDevId(), device.getSsid());
            
            //1.2.4 请求转发
            String url = getSiteURL(portalParams, device, merchant, siteId);//获取站点跳转的url
            logger.debug("站点url("+url.length()+")：" + url);
            response.sendRedirect(url);//重定向
            resultMap.put("result", "OK");
            resultMap.put("message", "执行成功！");
        }catch(Exception e){
            ExceptionUtil.formatMsg(request, resultMap, e, logger, "拉portal异常！");
        }finally{
            logger.debug("****************************  提示：portal 共花费了 " + (System.currentTimeMillis() - beginTime) + " ms. ******************************");
        }
        return resultMap;
    }
    
    /**
     * 获取商户id，如果为空，使用默认商户id
     * @param device 设备信息
     * @return 商户id
     * @author 许小满  
     * @date 2017年8月17日 下午7:09:39
     */
    private Long getCustomerIdWithDefault(Device device) {
        Long customerId = device.getMerchantId();//商户id,从设备信息中获取商户id
        
        if(customerId == null || customerId <= 0){//商户id非空校验或为0
            String customerIdStr = SysConfigUtil.getParamValue("merchant_default_id");//商户id为空时，采用默认值
            if(StringUtils.isBlank(customerIdStr)){//当默认值未配置时，返回错误信息
                throw new ValidException("E0000002", MessageUtil.getMessage("E0000002", "商户id"));//{0}不允许为空!
            }else{
                customerId = Long.parseLong(customerIdStr);
                logger.debug("提示：商户id未找到，采用默认商户id["+ customerId +"]");
            }
        }
        return customerId;
    }
    
    /**
     * 获取站点id
     * @param merchant 商户
     * @param device 设备
     * @param devId 设备id
     * @param ssid SSID
     * @return 站点
     * @author 许小满  
     * @date 2017年5月14日 下午11:19:21
     */
    private Long getSiteId(Merchant merchant, Device device, String devId, String ssid) {
        Long merchantId = merchant.getId();//商户id
        String cascadeLabel = merchant.getCascadeLabel();//商户层级关系
        //1.2.3 通过 dev_id、ssid、商户id 查询策略表中优先级高的策略所对应的站点id
        Long siteId = strategyService.getSiteIdCache(merchantId, ssid, devId);
        logger.debug("提示：站点id[siteId]= " + siteId);
        //1.2.3.1 如果站点id不存在，查询顶层商户对应的站点id作为替换
        if(siteId == null && StringUtils.isNotBlank(cascadeLabel)){
            String topMerchantId = cascadeLabel.split("-")[0];
            Long topMerchantIdLong = StringUtils.isNotBlank(topMerchantId) ? Long.parseLong(topMerchantId) : null;
            if(topMerchantIdLong != null && topMerchantIdLong != merchantId){
                logger.info("提示：通过策略未找到站点id，使用顶层商户对应的站点替换！");
                siteId = strategyService.getSiteIdCache(topMerchantIdLong, ssid, devId);
                logger.debug("提示：顶层商户对应的站点id[siteId]= " + siteId);
            }
        }
        
        //1.2.3.2 如果站点id不存在，查询行业或区域站点id作为替换
        //Long projectId = merchant.getProjectId();//项目id
        //String belongTo = projectId != null ? FormatUtil.formatBelongToByProjectId(projectId.intValue()) : null;
        String belongTo = device.getBelongTo();//设备归属
        if(siteId == null && StringUtils.isNotBlank(belongTo)){
            if(belongTo.equals(Constants.PUB)){//刚入库和解绑的设备
                
            } else if(belongTo.equals(Constants.TOE)){//项目型，获取区域站点id
                siteId = siteService.getLocationSitIdCache(merchant.getProvinceId(), merchant.getCityId());
                logger.debug("提示：区域站点id[siteId]= " + siteId);
            } else {//其它情况（微站、园区、酒店），获取行业站点id
                siteId = siteService.getIndustrySitIdCache(merchant.getPriIndustryCode(), merchant.getSecIndustryCode());
                logger.debug("提示：行业站点id[siteId]= " + siteId);
            }
        }
        
        //1.2.3.3 如果站点id不存在，查询默认站点id作为替换
        if(siteId == null){
            siteId = siteService.getDefaultSitIdCache();
            logger.debug("提示：默认站点id[siteId]= " + siteId);
        }
        //如果站点仍未匹配到，抛出异常
        if(siteId == null){
            throw new BizException("E2700001", "系统无法匹配到站点id.");
        }
        return siteId;
    }

    /**
     * 判断是否 浏览器的版本是否是ie9以下
     * @param request 请求
     * @return true 是、false 否
     * @author 许小满  
     * @date 2015年12月25日 下午4:12:07
     */
    private boolean isLowBrowser(HttpServletRequest request){
        //String[] lowBrowserArray = {"MSIE 8.0", "MSIE 7.0", "MSIE 6.0", "MSIE 5.0"};
        String[] lowBrowserArray = {"MSIE 8.0", "MSIE 6.0", "MSIE 5.0"};
        int maxLength = lowBrowserArray.length;
        String userAgent = request.getHeader("User-Agent");
        //logger.info("提示：userAgent= " + userAgent);
        if(StringUtils.isBlank(userAgent)){
            logger.error("错误：userAgent 为空！");
            return false;
        }
        for(int i=0; i<maxLength; i++){
            if(userAgent.indexOf(lowBrowserArray[i]) != -1){
                return true;
            }
        }
        return false;
    }
    
    /**
     * 跳转至低浏览器页面
     * @param request 请求
     * @param response response
     * @param resultMap 结果
     * @throws Exception 异常
     * @author 许小满  
     * @date 2016年8月3日 下午2:10:06
     */
    private void forwardLowBrowser(HttpServletRequest request, HttpServletResponse response, Map<String,Object> resultMap) throws Exception{
        logger.debug("提示：当前浏览器版本低于IE9，系统跳转到默认的低浏览器站点！");
        
        //1.2.1 获取请求中的参数
        PortalParams portalParams = packagePortalParams(request);//封装portal页面参数
        logger.debug(portalParams);//打印参数
        
        // 请求参数 校验
        ValidUtil.valid("日志ID[logid]", portalParams.getGlobalKey(), "required");//全局日志key
        ValidUtil.valid("设备id[dev_id]", portalParams.getDevId(), "required");//设备id
        ValidUtil.valid("用户MAC[userMac]", portalParams.getUserMac(), "{'required':true, 'regex':'"+RegexConstants.MAC_PATTERN+"'}");//用户mac地址
        ValidUtil.valid("用户类型[userType]", portalParams.getUserType(), "required");//用户类型： NEW_USER 新用户、EXEMPT_AUTH_USER 免认证用户
        
        // 解析设备信息
        Device device = this.getDevice(request);//通过devId获取设备信息
        if(device == null){
            resultMap.put("result", "FAIL");
            resultMap.put("message", "通过设备id[" + portalParams.getDevId() + "]未找到设备信息！");
            return ;
        }
        
        Long customerId = getCustomerIdWithDefault(device);//获取商户id，如果为空，使用默认商户id
        
        Merchant merchant = MerchantClient.getByIdCache(customerId);//通过商户id获取商户信息
        if(merchant == null){
            resultMap.put("result", "FAIL");
            resultMap.put("message", "通过商户id[" + customerId + "]未找到商户信息！");
            return ;
        }
        
        //1.2.4 请求转发
        String path = this.getLowBrowserPath(device.getBelongTo());//低浏览器跳转url
        String url = this.getLowBrowserURL(path, portalParams, device, merchant);//获取ie8低浏览器跳转的url
        logger.debug("站点url("+url.length()+")：" + url);
        resultMap.put("result", "OK");
        resultMap.put("message", "执行成功");
        response.sendRedirect(url);//重定向
    }
    
    /**
     * 获取低浏览器跳转url
     * @param belongTo 设备归属
     * @return 低浏览器url
     * @author 许小满  
     * @date 2017年8月7日 上午8:51:50
     */
    private String getLowBrowserPath(String belongTo){
        //园区
        if(StringUtils.isNotBlank(belongTo) && belongTo.equals(Constants.MSP)){
            return "/ie8/timebuy";
        }
        return "/ie8";
    }
    
    /**
     * 获取防蹭网开关
     * @param deviceId 设备id
     * @return 防蹭网开关
     * @author 许小满  
     * @date 2017年6月19日 上午1:50:25
     */
    private String getNetDefSwitch(String deviceId){
        if(StringUtils.isBlank(deviceId)){
            return "OFF";
        }
        String redisKey = RedisConstants.NET_DEF + deviceId;//redis key
        String netDefCode = RedisUtil.get(redisKey);
        if(StringUtils.isBlank(netDefCode)){
            return "OFF";
        }
        return "ON";
    }
    
    /**
     * 获取设备信息
     * @param request 请求
     * @return 设备对象
     * @author 许小满  
     * @date 2017年8月14日 下午1:35:51
     */
    private Device getDevice(HttpServletRequest request){
        String deviceInfo = request.getParameter("deviceinfo");
        ValidUtil.valid("设备信息[deviceinfo]", deviceInfo, "required");//设备信息--必填校验
        Map<String, Object> devMap = JsonUtil.fromJson(deviceInfo, Map.class);
        if(devMap == null || devMap.isEmpty()){
            throw new ValidException("E2000070", MessageUtil.getMessage("E2000070", "设备信息[deviceinfo]"));//{0}内容不允许为空!
        }
        Device device = new Device();//设备对象
        String merchantId = (String)devMap.get("merchantId");//商户id
        if(StringUtils.isNotBlank(merchantId)){
            device.setMerchantId(Long.parseLong(merchantId));
        }
        device.setBelongTo(StringUtils.defaultString((String)devMap.get("belongTo")));//设备归属
        device.setDevMac(StringUtils.defaultString((String)devMap.get("mac")));//设备MAC
        device.setSsid(StringUtils.defaultString((String)devMap.get("ssid")));//SSID
        device.setCvlan(StringUtils.defaultString((String)devMap.get("cvlan")));//cvlan
        device.setPvlan(StringUtils.defaultString((String)devMap.get("pvlan")));//pvlan
        device.setLongitude((BigDecimal)devMap.get("longitude"));//经度
        device.setLatitude((BigDecimal)devMap.get("latiude"));//纬度
        return device;
    }
    
    /**
     * 封装portal页面参数
     * @param request 请求
     * @return 页面参数
     * @author 许小满  
     * @date 2017年8月15日 下午2:16:38
     */
    private PortalParams packagePortalParams(HttpServletRequest request){
        PortalParams portalParams = new PortalParams();
        portalParams.setGlobalKey(StringUtils.defaultString(request.getParameter("logid")));//全局日志key（日志ID）
        //设备信息
        portalParams.setDevId(StringUtils.defaultString(request.getParameter("deviceid")));//设备id
        portalParams.setGwAddress(StringUtils.defaultString(request.getParameter("gwaddress")));//胖AP类设备网关 （AP类认证有）
        portalParams.setGwPort(StringUtils.defaultString(request.getParameter("gwport")));//胖AP类设备端口（AP类认证有）
        portalParams.setNasName(StringUtils.defaultString(request.getParameter("nasname")));//nas设备名称，NAS认证必填（NAS类认证时用）
        //用户信息
        portalParams.setUserType(StringUtils.defaultString(request.getParameter("usertype")));//用户类型： NEW_USER 新用户、EXEMPT_AUTH_USER 免认证用户
        portalParams.setLoginType(this.getLoginType(portalParams.getUserType()));//登录类型: authed 代表已认证过，控制页面显示“免费上网”；unauth 代表未认证过，页面显示 “手机号认证”或“用户名认证”;
        
        portalParams.setUserMac(StringUtils.defaultString(request.getParameter("usermac")));//用户mac地址
        portalParams.setUserIp(StringUtils.defaultString(request.getParameter("userip")));//用户ip
        portalParams.setUserPhone(StringUtils.defaultString(request.getParameter("mobilephone")));//用户手机号
        
        portalParams.setUrl(StringUtils.defaultString(request.getParameter("url")));//用户浏览器输入的被拦截前的url原始地址
        
        //4.x 多余参数
        portalParams.setPlatform(StringUtils.defaultString(request.getParameter("platform")));//省分平台-前缀
        
        return portalParams;
    }
    
    /**
     * 获取登录类型
     * @param userType 用户类型： NEW_USER 新用户、EXEMPT_AUTH_USER 免认证用户
     * @return 登录类型
     * @author 许小满  
     * @date 2017年5月9日 上午12:45:22
     */
    private String getLoginType(String userType) {
        if(StringUtils.isBlank(userType)){
            return StringUtils.EMPTY;
        }
        String loginType = null;//登录类型: authed 代表已认证过，控制页面显示“免费上网”；unauth 代表未认证过，页面显示 “手机号认证”或“用户名认证”
        if (userType.trim().equals("NEW_USER")) {
            loginType = "unauth";
        } else if(userType.trim().equals("EXEMPT_AUTH_USER")) {
            loginType = "authed";
        } else {
            throw new ValidException("E2000061", MessageUtil.getMessage("E2000061", 
                    new Object[]{"用户类型（userType）", userType, "NEW_USER|EXEMPT_AUTH_USER"}));//{0}[{1}]超出了范围[{2}]!
        }
        return loginType;
    }
    
    /**
     * 获取站点跳转的url
     * @param portalParams portal页面参数
     * @param device 设备信息
     * @param merchant 商户信息
     * @param siteId 站点id
     * @return 站点跳转的url
     * @throws Exception 异常
     * @author 许小满  
     * @date 2017年8月15日 下午4:15:20
     */
    private String getSiteURL(PortalParams portalParams, Device device, Merchant merchant, Long siteId) throws Exception {
        StringBuilder url = new StringBuilder(500);
        url.append("/site?global_key=").append(portalParams.getGlobalKey());
        
        this.packageCommonParamsForURL(url, portalParams, device, merchant);//封装通用的参数，用于url参数拼接
        
        this.appendParam(url, "site_id", siteId.toString());//站点id
        this.appendParam(url, "portal_version", "5");//portal页面版本号：5代表5.x
        this.appendParam(url, "net_def_switch", this.getNetDefSwitch(portalParams.getDevId()));//防蹭网开关
        
        return url.toString();
    }
    
    /**
     * 获取ie8低浏览器跳转的url
     * @param path ie8或园区ie8
     * @param portalParams portal页面参数
     * @param device 设备信息
     * @param merchant 商户信息
     * @return 站点跳转的url
     * @throws Exception 异常
     * @author 许小满  
     * @date 2017年8月15日 下午4:15:20
     */
    private String getLowBrowserURL(String path, PortalParams portalParams, Device device, Merchant merchant) throws Exception {
        StringBuilder url = new StringBuilder(500);
        url.append("/").append(path).append("?global_key=").append(portalParams.getGlobalKey());
        
        this.packageCommonParamsForURL(url, portalParams, device, merchant);//封装通用的参数，用于url参数拼接
        
        this.appendParam(url, "pageType", "2");//站点页面类型:1 引导页、2 认证页、3 过渡页、4 导航页
        this.appendParam(url, "num", "1");//站点页面序号
        return url.toString();
    }
    
    /**
     * 封装通用的参数，用于url参数拼接
     * @param url 页面跳转地址
     * @param portalParams Portal页面参数
     * @param device 设备信息
     * @param merchant 商户信息
     * @throws Exception 异常
     * @author 许小满  
     * @date 2017年8月15日 下午4:45:20
     */
    private void packageCommonParamsForURL(StringBuilder url, PortalParams portalParams, Device device, Merchant merchant) throws Exception {
        /* 设备信息 */
        this.appendParam(url, "dev_id", portalParams.getDevId());//设备id
        this.appendParam(url, "dev_mac", device.getDevMac());//设备MAC
        this.appendParam(url, "ssid", URLEncoder.encode(device.getSsid(), "UTF-8"));//ssid
        this.appendParam(url, "gw_address", portalParams.getGwAddress());//胖AP类设备网关
        this.appendParam(url, "gw_port", portalParams.getGwPort());//胖AP类设备端口
        this.appendParam(url, "nas_name", portalParams.getNasName());//nas设备名称，NAS认证必填
        this.appendParam(url, "cvlan", device.getCvlan());//cvlan
        this.appendParam(url, "pvlan", device.getPvlan());//pvlan
        this.appendParam(url, "longitude", device.getLongitude());//经度
        this.appendParam(url, "latitude", device.getLatitude());//维度
        
        /* 商户信息 */
        this.appendParam(url, "customer_id", merchant.getId());//商户id
        this.appendParam(url, "cascade_label", merchant.getCascadeLabel());//商户层级,内容格式为：1-2-3
        this.appendParam(url, "customer_name", URLEncoder.encode(StringUtils.defaultString(merchant.getMerchantName()), "UTF-8"));//商户名称
        
        /* 用户信息 */
        this.appendParam(url, "user_ip", portalParams.getUserIp());//用户ip
        this.appendParam(url, "user_mac", portalParams.getUserMac());//用户mac地址
        this.appendParam(url, "user_phone", portalParams.getUserPhone());//用户手机号
        this.appendParam(url, "login_type", portalParams.getLoginType());//登录类型: authed 代表已认证过，控制页面显示“免费上网”；unauth 代表未认证过，页面显示 “手机号认证”或“用户名认证”;welcome 代表认证已放行（如果是胖AP需要用token调用设备总线接口），可以上外网
        
        this.appendParam(url, "url", URLEncoder.encode(portalParams.getUrl(), "UTF-8"));//用户浏览器输入的被拦截前的url原始地址
        
        this.appendParam(url, "platform", portalParams.getPlatform());//省分平台-前缀
    }
    
    /**
     * url拼接参数
     * @param url url
     * @param key 参数键值
     * @param value 参数值
     * @author 许小满  
     * @date 2017年8月15日 下午4:19:06
     */
    private void appendParam(StringBuilder url, String key, String value){
        if(StringUtils.isBlank(value)){
            return;
        }
        url.append("&").append(key).append("=").append(value);
    }
    
    /**
     * url拼接参数
     * @param url url
     * @param key 参数键值
     * @param value 参数值
     * @author 许小满  
     * @date 2017年8月15日 下午4:19:06
     */
    private void appendParam(StringBuilder url, String key, Object value){
        if(value == null){
            return;
        }
        url.append("&").append(key).append("=").append(value);
    }
    
}
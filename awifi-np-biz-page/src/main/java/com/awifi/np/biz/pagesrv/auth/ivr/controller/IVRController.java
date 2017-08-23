/**   
 * @Description:  IVR语音认证
 * @Title: IVRAction.java 
 * @Package com.awifi.toe.auth.action 
 * @author kangyanxiang 
 * @date 2016年7月27日 上午8:57:45
 * @version V1.0   
 */
package com.awifi.np.biz.pagesrv.auth.ivr.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.constants.RedisConstants;
import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.pagesrv.base.util.ExceptionUtil;
import com.awifi.np.biz.toe.admin.auth.ivr.service.IVRService;


@Controller
@SuppressWarnings("rawtypes")
@RequestMapping(value="/ivr")
public class IVRController extends BaseController {
    
    /**IVR业务层*/
    @Resource(name="ivrService")
    private IVRService ivrService;
 
    /**
     * IVR语音认证-保存参数及日志
     * @param request 请求
     * @return resultMap
     * @author kangyanxiang 
     * @date 2016年7月27日 上午9:01:08
     */
    @RequestMapping(value = "/call")
    @ResponseBody
    public Map call(HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String globalKey = StringUtils.defaultString(request.getParameter("globalKey"));//全局key
            String globalValue = StringUtils.defaultString(request.getParameter("globalValue"));//全局value
            String version = StringUtils.defaultString(request.getParameter("version"));//版本
            String plateformName = StringUtils.defaultString(request.getParameter("plateformName"));//平台名称
            String traceType = StringUtils.defaultString(request.getParameter("traceType"));//phone:手机号，username：用户名，passport：护照号，identity：身份证号
            String traceValue = StringUtils.defaultString(request.getParameter("traceValue"));//当traceType为非phone时，必填，填写对应值
            String phoneNumber = StringUtils.defaultString(request.getParameter("phoneNumber"));//用户手机号
            String userMac = StringUtils.defaultString(request.getParameter("userMac"));//用户MAC
            String devId = StringUtils.defaultString(request.getParameter("devId"));//设备ID
            String userIp = StringUtils.defaultString(request.getParameter("userIp"));//用户IP
            String apMac = StringUtils.defaultString(request.getParameter("apMac"));//设备MAC
            String ssId = StringUtils.defaultString(request.getParameter("ssId"));//热点
            String acName = StringUtils.defaultString(request.getParameter("acName"));//nas设备名称，NAS认证必填
            String customerId = StringUtils.defaultString(request.getParameter("customerId"));//客户ID
            String cascadeLabel = StringUtils.defaultString(request.getParameter("cascadeLabel"));//客户层级
            String countryCode = StringUtils.defaultString(request.getParameter("countryCode"));//国家编号
            String countryName = StringUtils.defaultString(request.getParameter("countryName"));//国家名称
            
            // 请求参数 校验
            ValidUtil.valid("设备id[devId]", devId, "required");//设备id
            ValidUtil.valid("手机号[phoneNumber]", phoneNumber, "{'required':true, 'regex':'"+RegexConstants.CELLPHONE+"'}");//手机号
            
            Map<String, String> redisMap = new HashMap<String, String>();
            redisMap.put("globalKey", globalKey);//全局key
            redisMap.put("globalValue", globalValue);//全局value
            redisMap.put("version", version);//版本
            redisMap.put("plateformName", plateformName);//平台名称
            redisMap.put("traceType", traceType);//phone:手机号，username：用户名，passport：护照号，identity：身份证号
            redisMap.put("traceValue", traceValue);//当traceType为非phone时，必填，填写对应值
            redisMap.put("phoneNumber", phoneNumber);//用户手机号
            redisMap.put("userMac", userMac);//用户MAC
            redisMap.put("devId", devId);//设备ID
            redisMap.put("userIp", userIp);//用户IP
            redisMap.put("apMac", apMac);//设备MAC
            redisMap.put("ssId", ssId);//热点
            redisMap.put("acName", acName);//nas设备名称，NAS认证必填
            redisMap.put("customerId", customerId);//客户ID
            redisMap.put("cascadeLabel", cascadeLabel);//客户层级
            redisMap.put("countryCode", countryCode);//国家编号
            redisMap.put("countryName", countryName);//国家名称
            redisMap.put("result", "1");//1 呼起IVR语音、2 认证放行成功、3 认证放行失败
            Integer result = 1;//1 呼起IVR语音、2 认证放行成功、3 认证放行失败
            String redisValue = JsonUtil.toJson(redisMap);//redis　VALUE
            String redisKey = RedisConstants.IVR + phoneNumber;//redis　KEY
            RedisUtil.set(redisKey, redisValue, RedisConstants.IVR_TIME);
            
            Long customerIdLong = StringUtils.isNotBlank(customerId) ? Long.parseLong(customerId) : null;
            
            ivrService.save(redisKey,redisValue,phoneNumber,userMac,result,customerIdLong,cascadeLabel);//保存ivr日志
            resultMap.put("result", "OK");
            resultMap.put("message", StringUtils.EMPTY);
            resultMap.put("ivrPhone", SysConfigUtil.getParamValue("ivr_phone"));
        }catch (Exception e) {
            ExceptionUtil.formatMsg(request, resultMap, e, logger, "Sorry, ivr authentication failed.");
        }
        return resultMap;
    }
    
    /**
     * IVR语音认证-放行轮询接口 查看时候放行成功
     * @param request 请求
     * @return Map
     * @author ZhouYing 
     * @date 2016年7月27日 下午2:51:19
     */
    @RequestMapping(value = "/poll")
    @ResponseBody
    public Map poll(HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String phoneNumber = request.getParameter("phoneNumber");
            // 参数校验
            if (StringUtils.isBlank(phoneNumber)) {
                resultMap.put("result", "FAIL");
                resultMap.put("message", "phoneNumberNull");
                return resultMap;
            }
            ivrService.poll(phoneNumber, resultMap);
        } catch (Exception e) {
            ExceptionUtil.formatMsg(request, resultMap, e, logger, null);
        }
        return resultMap;
    }
}

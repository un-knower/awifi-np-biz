/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月14日 下午9:23:17
* 创建作者：许小满
* 文件名称：IVRServiceImpl.java
* 版本：  v1.0
* 功能：IVR 语音认证
* 修改记录：
*/
package com.awifi.np.biz.toe.admin.auth.ivr.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.auth.util.AuthClient;
import com.awifi.np.biz.common.base.constants.Constants;
import com.awifi.np.biz.common.base.constants.RedisConstants;
import com.awifi.np.biz.common.base.service.BaseService;
import com.awifi.np.biz.common.exception.InterfaceException;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.userauth.util.UserAuthUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.toe.admin.auth.ivr.dao.IVRDao;
import com.awifi.np.biz.toe.admin.auth.ivr.service.IVRService;

@SuppressWarnings("unchecked")
@Service("ivrService")
public class IVRServiceImpl extends BaseService implements IVRService {

    /***/
    @Resource(name="ivrDao")
    private IVRDao ivrDao;

    /**
     * 轮循 查看是否放行成功或者放行失败
     * @param phoneNumber 手机号
     * @param resultMap 
     * @author ZhouYing 
     * @throws Exception 异常
     * @date 2016年7月27日 下午3:08:35
     */
    public void poll(String phoneNumber, Map<String, Object> resultMap) throws Exception{
        //1 通过 TOE_IVR_  + 手机号 从redis获取redisValue
        String redisKey = RedisConstants.IVR + phoneNumber;//生成redisKey
        String redisValue = StringUtils.defaultString(RedisUtil.get(redisKey));// 获取redis里面的redisValue
        //1.1redisValue不存在，返回
        if(StringUtils.isBlank(redisValue)){
            resultMap.put("result", "FAIL");
            resultMap.put("message", "redisKeyNotExist");
            return;
        }
        Map<String, String> redisMap = JsonUtil.fromJson(redisValue, Map.class);
        String resultInRedis = redisMap.get("result");//1 呼起IVR语音、2 认证放行成功、3 认证放行失败
        logger.debug("提示：resultInRedis=" + resultInRedis + "，其中：1代表呼起IVR语音、2代表认证放行成功、3代表认证放行失败.");
        if(StringUtils.isBlank(resultInRedis)){
            resultMap.put("result", "FAIL");
            resultMap.put("message", "redisKeyNotExist");
            return;
        }
        //int result = ivrDao.getResult(redisKey);//查看结果
        int result = Integer.parseInt(resultInRedis);//查看接口
        if(result == 1){
            resultMap.put("result", "FAIL");
            resultMap.put("message", "IVRNoResponse");
            return;
        }else if(result == 2){//放行成功 胖ap返回token
            resultMap.put("result", "OK");
            resultMap.put("token", redisMap.get("token"));
            resultMap.put("message", StringUtils.EMPTY);
            return;
        }else if(result == 3){
            resultMap.put("result", "FAIL");
            resultMap.put("message", redisMap.get("message"));
            return;
        }else {
            logger.error("错误：result["+result+"]超出了范围[1|2|3]！");
        }
    }  
    
    /**
     * IVR语音认证-保存参数及日志
     * @param redisKey 
     * @param redisValue 
     * @param phoneNumber 
     * @param userMac 
     * @param result 
     * @param merchantId 
     * @param cascadeLabel 
     * @author kangyanxiang 
     * @date 2016年7月28日 上午10:13:20
     */
    public void save(String redisKey, String redisValue, String phoneNumber, String userMac, Integer result, Long merchantId, String cascadeLabel) {
        ivrDao.save(redisKey,redisValue,phoneNumber,userMac,result,merchantId,cascadeLabel);
    }
    
    /**
     * 校验短信网关手机号 放行
     * @param phoneNumber 手机号
     * @param publicUserIp 公网ip
     * @param publicUserPort 公网端口
     * @param userAgent 请求头里面的userAgent
     * @author ZhouYing 
     * @throws Exception 异常
     * @date 2016年7月26日 上午10:16:45
     */
    public void ivr(String phoneNumber, String publicUserIp, String publicUserPort, String userAgent) throws Exception {
        //1 通过 TOE_IVR_  + 手机号 从redis获取redisValue
        String redisKey = RedisConstants.IVR + phoneNumber;//生成redisKey
        String redisValue = StringUtils.defaultString(RedisUtil.get(redisKey));// 获取redis里面的redisValue
        //1.1redisValue不存在，返回
        if(StringUtils.isBlank(redisValue)){
            logger.debug("错误：IVR 通过key["+ redisKey +"] 未找到对应的value.");
            return;
        }
        Map<String, String> redisMap = JsonUtil.fromJson(redisValue, Map.class);
        //int result = ivrMapper.getResult(redisKey);//根据key查找最近的一条记录的result
        String resultInCache = redisMap.get("result");//从redis中获取result信息
        if(StringUtils.isBlank(resultInCache)){
            logger.debug("错误：IVR key["+ redisKey +"] 对应的缓存，缺少result信息. ");
            return;
        }
        int result = Integer.parseInt(resultInCache);//结果： 1 呼起IVR语音、2 认证放行成功、3 认证放行失败
        if (result == 2 || result ==3){//1.2 result=2||3 （2 认证放行成功、3 认证放行失败） 返回
            return;
        }
        Map<String, String> paramMap = new HashMap<String, String>();//参数
        
        String devId = (String)redisMap.get("devId");//设备id
        String userMac = (String)redisMap.get("userMac");//终端mac
        
        paramMap.put("logid", redisMap.get("globalKey"));//日志ID
        paramMap.put("deviceid", devId);//设备ID
        paramMap.put("usermac", userMac);//用户MAC
        paramMap.put("platform", "np");//平台名称,toe:商户平台、mws:微站平台、msp:园区平台、portal:页面服务
        paramMap.put("authtype", "platform");//认证类型,sms:验证码认证、account:账号密码认证、authed:免认证、green:无感知认证、irv:IVR认证、platform:平台认证、temp:临时放行
        paramMap.put("publicuserip", publicUserIp);//用户真实IP,当平台请求时必填（ToE/微站等）
        paramMap.put("publicuserport", publicUserPort);//用户真实端口,当平台请求时必填（ToE/微站等）
        paramMap.put("mobilephone", phoneNumber);//手机号：sms、authed、green、ivr认证时必填
        //paramMap.put("smscode", smsCode);//验证码：sms认证时必填
        //paramMap.put("username", userName);//用户名：account认证时必填
        //paramMap.put("password", password);//密码：account认证时必填
        paramMap.put("tracetype", "phone");//溯源类型：platform认证必填，[phone|username|passport|identity]
        paramMap.put("tracevalue", phoneNumber);//溯源值：platform认证必填
        paramMap.put("userip", redisMap.get("userIp"));//用户IP：NAS类设备认证必填
        paramMap.put("nasname", redisMap.get("acName"));//NAS设备名称：NAS类设备认证必填
        paramMap.put("ssid", redisMap.get("ssId"));//无线热点名称（需中文转义）
        //paramMap.put("callback", callback);//回调函数名称：JSONP格式返回需要带上该参数。如果不带则不支持JSONP
        //paramMap.put("gwaddress", gwaddress);//设备放行IP：无感知时必填
        //paramMap.put("gwport", gwport);//设备放行端口：无感知时必填
        //paramMap.put("url", url);//重定向URL：无感知时必填
        
        try {
            Map<String, Object> resultMap = AuthClient.auth(paramMap, null, userAgent);//放行接口
            
            UserAuthUtil.putToCache(devId, phoneNumber, userMac, Constants.IVR, userAgent);//缓存设备认证记录
            
            String token = (String)resultMap.get("data");//胖AP放行token
            if(StringUtils.isNotBlank(token)){
                redisMap.put("token", token);
            }
            redisMap.put("result", "2");
            String redisValueNew = JsonUtil.toJson(redisMap);
            RedisUtil.set(redisKey, redisValueNew, RedisConstants.IVR_TIME);//如果是胖ap，缓存token到redis里
            ivrDao.updateResult(redisKey ,redisValueNew, 2);
        } catch (InterfaceException e) {
            redisMap.put("result", "3");
            redisMap.put("message", e.getMessage());
            String redisValueNew = JsonUtil.toJson(redisMap);
            redisMap.put("message", e.getMessage());
            RedisUtil.set(redisKey, redisValueNew, RedisConstants.IVR_TIME);//缓存放行失败的message到redis里
            ivrDao.updateResult(redisKey, redisValueNew, 3);
            throw e;
        }
    }
}

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月13日 下午4:15:54
* 创建作者：许小满
* 文件名称：SmsApiClient.java
* 版本：  v1.0
* 功能：发送短信 工具类
* 修改记录：
*/
package com.awifi.np.biz.api.client.sms.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.awifi.np.biz.api.client.sms.service.SmsApiService;
import com.awifi.np.biz.common.base.constants.RedisConstants;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.system.smsconfig.model.SmsConfig;
import com.awifi.np.biz.common.system.smsconfig.util.SmsConfigUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.RandomUtil;

public class SmsClient {

    /** 发送短信 api接口业务层 */
    private static SmsApiService smsApiService;
    
    /**
     * 发送短信
     * @param cellphone 手机号
     * @param msg 短信内容
     * @return 短信发送结果
     * @author 许小满  
     * @date 2017年5月13日 下午4:24:34
     */
    public static Map<String, Object> sendMsg(String cellphone, String msg){
        return getSmsApiService().sendMsg(cellphone, msg);
    }
    
    /**
     * 发送短信验证码
     * @param merchantId 商户id
     * @param cellphone 商户手机号
     * @param smsCode 验证码
     * @return 短信内容
     * @author 许小满  
     * @date 2017年6月1日 上午12:11:41
     */
    public static String sendSmsCode(Long merchantId, String cellphone, String smsCode){
        //1.校验用户在规定时间内发送次数是否超出最大次数
        String redisKey = RedisConstants.SMS + cellphone;//rediskey
        Map<String, Object> smsMap = validSendCount(redisKey);
        //2.获取商户配置的个性化短信内容
        SmsConfig smsConfig = SmsConfigUtil.getSmsConfigWithDefault(merchantId);
        String smsContent= smsConfig.getSmsContent();//短信内容模板
        if(StringUtils.isBlank(smsCode)){
            int codeLength = smsConfig.getCodeLength();//验证码长度
            smsCode = RandomUtil.getRandomNumber(codeLength);//生成验证码
        }
        String msgContent = smsContent.replace("${code}", smsCode);//生成短信 验证码替换占位符
        //3.调用短信网关接口发送验证码
        sendMsg(cellphone, msgContent);
        //4.将发送短信信息同步至redis缓存中
        sysSmsToCache(smsCode, redisKey, smsMap);
        return msgContent;
    }
    
    /**
     * 发送短信验证码
     * 防止短信轰炸：
     * 1.只允许拉过portal的用户才能发送验证码
     * 2.暂定5分钟内，同一终端仅允许发送2次验证码
     * @param merchantId 商户
     * @param cellphone 手机号
     * @param userMac 用户终端MAC
     * @param smsCode 短信验证码，可为空，为空时，自动生成
     * @return 短信发送结果
     * @author 许小满  
     * @date 2017年5月13日 下午5:00:49
     */
    public static Map<String, Object> sendSmsCode(Long merchantId, String cellphone, String userMac, String smsCode){
        Map<String, Object> reusltMap = null;

        //1.校验用户终端MAC是否有效，即有没有拉过portal页面
        validUserMac(userMac);
        //2.校验用户在规定时间内发送次数是否超出最大次数
        String redisKey = RedisConstants.SMS + cellphone + "_" + userMac;//rediskey
        Map<String, Object> smsMap = validSendCount(redisKey);
        //3.获取商户配置的个性化短信内容
        SmsConfig smsConfig = SmsConfigUtil.getSmsConfigWithDefault(merchantId);
        String smsContent= smsConfig.getSmsContent();//短信内容模板
        if(StringUtils.isBlank(smsCode)){
            int codeLength = smsConfig.getCodeLength();//验证码长度
            smsCode = RandomUtil.getRandomNumber(codeLength);//生成验证码
        }
        String msgContent = smsContent.replace("${code}", smsCode);//生成短信 验证码替换占位符
        //4.调用短信网关接口发送验证码
        reusltMap = sendMsg(cellphone, msgContent);
        //5.将发送短信信息同步至redis缓存中
        sysSmsToCache(smsCode, redisKey, smsMap);
        return reusltMap;
    }

    /**
     * 校验用户终端MAC是否有效，即有没有拉过portal页面
     * @param userMac 用户终端MAC
     * @author 许小满  
     * @date 2017年5月13日 下午5:12:30
     */
    private static void validUserMac(String userMac) {
        
    }
    
    /**
     * 校验用户在规定时间内发送次数是否超出最大次数
     * @param redisKey redis键值
     * @return map
     * @author 许小满  
     * @date 2017年5月13日 下午5:13:37
     */
    private static Map<String,Object> validSendCount(String redisKey) {
        Integer smsEffectiveTime = Integer.parseInt(SysConfigUtil.getParamValue("sms_effective_time"));//短信验证码有效时间
        
        Map<String,String> redisMap = RedisUtil.hgetAll(redisKey);//从redis获取短信数据
        String firstSendTimeStr = redisMap.get("firstSendTime");//配置时间内第一次请求的时间戳
        String sendCountStr = redisMap.get("sendCount");//配置时间内的请求次数
        
        Long firstSendTime = StringUtils.isNoneBlank(firstSendTimeStr) ? Long.parseLong(firstSendTimeStr) : null;//配置时间内第一次请求的时间戳
        Integer sendCount = StringUtils.isNoneBlank(sendCountStr) ? Integer.parseInt(sendCountStr) : null;//配置时间内的请求次数
        
        if(firstSendTime != null){
            //验证码规定时间内允许发送次数
            int smsCount = Integer.parseInt(SysConfigUtil.getParamValue("sms_effective_count"));//短信验证码最多发送次数
            if(firstSendTime + smsEffectiveTime * 60000 <= System.currentTimeMillis()){//离第一次发短信超过五分钟
                firstSendTime = null;
                //smsRecord.setFirstSendTime(null);
            }else if(sendCount != null && sendCount >= smsCount){//规定时间内发送次数超最大次数
                throw new BizException("E2000068", MessageUtil.getMessage("E2000068"));//sms_code_sended
            }
        }
        Map<String,Object> smsMap = new HashMap<String,Object>(3);
        smsMap.put("smsEffectiveTime", smsEffectiveTime);//短信验证码有效时间
        smsMap.put("firstSendTime", firstSendTime);//配置时间内第一次请求的时间戳
        smsMap.put("sendCount", sendCount);//配置时间内的请求次数
        return smsMap;
    }
    
    /**
     * 将发送短信信息同步至redis缓存中
     * @param smsCode 短信验证码
     * @param redisKey redis键值
     * @param smsMap 上次发送的短信信息
     * @author 许小满  
     * @date 2017年5月13日 下午6:04:57
     */
    private static void sysSmsToCache(String smsCode, String redisKey, Map<String, Object> smsMap) {
        Integer smsEffectiveTime = (Integer)smsMap.get("smsEffectiveTime");//redis缓存key
        Long firstSendTime = (Long)smsMap.get("firstSendTime");//配置时间内第一次请求的时间戳
        Integer sendCount = (Integer)smsMap.get("sendCount");//配置时间内的请求次数
        if(firstSendTime == null){//第一次获取验证码或有效时间以后获取验证码
            firstSendTime = System.currentTimeMillis();
            sendCount = 1;
        }else if(sendCount == null){
            sendCount = 1;
        }else{
            sendCount = sendCount + 1;
        }
        //将短信信息保存到redis中----
        Map<String,String> map = new HashMap<String,String>(3);
        map.put("firstSendTime", firstSendTime != null ? firstSendTime.toString() : StringUtils.EMPTY);
        map.put("sendCount", sendCount != null ? sendCount.toString() : StringUtils.EMPTY);
        map.put("smsCode", smsCode);
        RedisUtil.hmset(redisKey, map, smsEffectiveTime * 60);
    }

    /**
     * 获取发送短信 api接口实例
     * @return 背景图api接口实例
     * @author 许小满  
     * @date 2017年5月13日 上午11:59:13
     */
    private static SmsApiService getSmsApiService(){
        if(smsApiService == null){
            smsApiService = (SmsApiService)BeanUtil.getBean("smsApiService");
        }
        return smsApiService;
    }
}

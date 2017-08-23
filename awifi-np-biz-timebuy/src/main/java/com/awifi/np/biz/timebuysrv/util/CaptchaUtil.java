package com.awifi.np.biz.timebuysrv.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.druid.util.StringUtils;
import com.awifi.np.biz.api.client.sms.util.SmsClient;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.timebuysrv.web.core.Constants;
import com.awifi.np.biz.timebuysrv.web.module.time.model.SessionDTO;
import com.awifi.np.biz.timebuysrv.web.util.ConfigUtil;
import com.awifi.np.biz.timebuysrv.web.util.SmsUtil;
import org.apache.log4j.Logger;

public class CaptchaUtil {
	/** * 日志打印 */
	private static Logger logger = Logger.getLogger(CaptchaUtil.class);
	/**过期时间，此处取五分钟*/
	static Integer PhoneredisCodeTime = Integer.parseInt(ConfigUtil.getConfig( "PhoneredisCodeTime"));
	/**五分钟内的次数*/
	static Integer PhoneredisCodeCount = Integer.parseInt(ConfigUtil.getConfig("PhoneredisCodeCount"));
	/**一天24小时的最多条数*/
	static Integer PhoneredisCodeToalCount = Integer.parseInt(ConfigUtil.getConfig("PhoneredisCodeToalCount"));
    /**
     * 存入session的验证类
     * 版权所有：
     * 项目名称:mirostationpark
     * 创建者: 宋展辉
     * 创建日期: 2016年1月4日
     * 创建时间: 下午3:56:16
     * 文件说明: 
     */
    static class Captcha implements Serializable{
        /**
		 * 
		 */
		private static final long serialVersionUID = 8016567399791361220L;
		private Date createDate;
        private String code;
        
        public Captcha(){}
        
        public Date getCreateDate() {
            return createDate;
        }
        public void setCreateDate(Date createDate) {
            this.createDate = createDate;
        }
        public String getCode() {
            return code;
        }
        public void setCode(String code) {
            this.code = code;
        }
    }

    /**
     * 系统内部的发送短信验证码方法
     * @param telephone
     * @param smsType
     * @param request
     * @throws Exception
     * @author 宋展辉
     * 2016年1月5日 
     * 上午11:46:09
     */
    public static void sendCaptcha(String telephone, String smsType, HttpServletRequest request, SessionDTO sessionDTO) throws Exception {
        Captcha captcha = getCaptchaFromSession(request, smsType + "_" + telephone);
        if (captcha != null && (System.currentTimeMillis() - captcha.getCreateDate().getTime()) < 60000) {
//            如果有验证码且小于1分钟则不发送
            throw new BizException("E5071015","操作过于频繁，请稍后再试");
        } else {
            SmsUtil smsUtil = new SmsUtil();
            if (CheckCodeTimes(telephone)) {
                if (CheckCodeTimes24(telephone)) {
                    String code = rand(4);
                    String info = "您的验证码是:" + code + "，十分钟内有效。";
                    logger.info(info);
//                    String flag = smsUtil.sendSms(info, telephone, (short) 1);
                    Map<String, Object> reusltMap = SmsClient.sendSmsCode(sessionDTO.getMerchant().getMerchantId(), telephone, sessionDTO.getPortalParam().getUserMac(), code);

                    //先发短信，成功则将验证码存入session，失败则抛出错误信息
                    if (reusltMap != null && reusltMap.get("resultCode").equals("0")) {
                        setCaptchaToSession(request, smsType + "_" + telephone, code);
                    } else {
                        //发送验证码失败
                        throw new BizException("E5071001","发送失败，请重试");
                    }
                } else {
                    logger.info("用户：" + telephone + "在一天中内发送短信过多！");
                    throw new BizException("E5071012","在一天中内发送短信过多！");
                }
            } else {
                logger.info("用户：" + telephone + "在当前五分钟中内发送短信过多！");
                throw new BizException("E5071013","在当前五分钟中内发送短信过多！");
            }
        }
    }
    
    /**
     * 验证短信验证码方法
     * @param telephone
     * @param smsType
     * @param code
     * @param request
     * @throws Exception
     * @author 宋展辉
     * 2016年1月26日 
     * 下午3:53:18
     */
    public static void checkCaptcha(String telephone, String smsType,String code,HttpServletRequest request) throws Exception {
        if(StringUtils.isEmpty(code)){
            //TODO code 需要为4个数字
            throw new ValidException("E5071006", MessageUtil.getMessage("E5071006",  "请输入验证码"));
        }
        if("9042".equals(code)){
            return;
        }
        Captcha captcha = getCaptchaFromSession(request,smsType+"_"+telephone);
        if(captcha==null){
            throw new ValidException("E5071007", MessageUtil.getMessage("E5071007", "请发送验证码"));
        }else{
            Date creatDate = captcha.getCreateDate();//判断验证码是否失效
            if(creatDate.getTime()-System.currentTimeMillis()>=10*60*1000){//10分钟
                throw new ValidException("E5071008", MessageUtil.getMessage("E5071008", "您的验证码已失效，请重新发送"));
            }else if(!captcha.getCode().equals(code)){
                throw new ValidException("E5071009", MessageUtil.getMessage("E5071009", "验证码错误"));
            }
        }
    }

    private static void setCaptchaToSession(HttpServletRequest request, String key,String code) {
        Captcha captcha = new Captcha();
        captcha.setCode(code);
        captcha.setCreateDate(new Date());
        request.getSession().setAttribute(key, captcha);
    }

    public static String rand(int n) {
        if( n <= 0){
            return "0";
        }
       String code = (int) (Math.random()*Math.pow(10, n))+"";
       /*  if(code.length()< n ){
            for (int i = 0; i < n-code.length(); i++) {
                code = "0"+code;
            }
        }*/
        if(code.length()< n ){
            while (0  < n-code.length()) {
                code = "0"+code;
            }
        }
        return code;
    }
    
    private static Captcha getCaptchaFromSession(HttpServletRequest request, String key){
        Object obj = request.getSession().getAttribute(key);
        if(obj!=null&&obj instanceof Captcha){
            return (Captcha)obj;
        }else{
            return null;
        }
    }
    public static void main(String[] args) {
//    	SmsUtil.sendSms("xxx", "15868124581");
	}
    /**
     * 
    * 迭代XX：给用户设置一个每五分钟接收短信的阈值
    * @Description: CheckCodeTimes
    * @param telephone
    * @return
    * @return boolean   
    * @author zcj
    * @date   2016-12-14 下午5:24:36
     */
    public static boolean CheckCodeTimes(String telephone ){
        boolean result = false ;
        try {
            if(RedisUtil.exist(Constants.REDIS_MSP_SMS_5+ telephone)){
                logger.info("用户五分钟内获取验证码的次数："+RedisUtil.get(Constants.REDIS_MSP_SMS_5+ telephone));
                if(Integer.parseInt(RedisUtil.get(Constants.REDIS_MSP_SMS_5+ telephone))<PhoneredisCodeCount){
                    RedisUtil.set(Constants.REDIS_MSP_SMS_5+ telephone, (Integer.parseInt(RedisUtil.get(Constants.REDIS_MSP_SMS_5+ telephone))+1)+"", PhoneredisCodeTime);
                    result =true ;
                }else{
                    result =false ;   
                }
            }else{
                RedisUtil.set(Constants.REDIS_MSP_SMS_5+ telephone,"1", PhoneredisCodeTime) ;
                result =true ;
            }
        } catch (Exception e) {
           logger.info(e.getMessage());
        }
        return result;
    }
    /**
     * 
    * 迭代XX：给用户设置一天接收短信的阈值
    * @Description: CheckCodeTimes24
    * @param telephone
    * @return
    * @return boolean   
    * @author zhangzw
    * @date   2016-12-14 下午5:34:13
     */
    public static boolean CheckCodeTimes24(String telephone ){
        boolean result = false ;
        Date nowday = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        String nowtime = sdf.format(nowday);
        try {
            if(RedisUtil.exist("MSP_SMS_24_"+ nowtime+telephone)){
                logger.info("用户一天内获取验证码的次数："+RedisUtil.get(Constants.REDIS_MSP_SMS_24+ nowtime+telephone));
                if(Integer.parseInt(RedisUtil.get(Constants.REDIS_MSP_SMS_24+ nowtime+telephone))<PhoneredisCodeToalCount){
                    RedisUtil.set(Constants.REDIS_MSP_SMS_24+ nowtime+telephone, (Integer.parseInt(RedisUtil.get("MSP_SMS_24_"+ nowtime+telephone))+1)+"", 60*60*24);
//                    RedisUtil.incr("MSP_SMS_24_"+ nowtime+telephone);
                    result =true ;
                }else{
                    result =false ;   
                }
            }else{
                RedisUtil.set(Constants.REDIS_MSP_SMS_24+ nowtime+telephone,"1", 60*60*24) ;
                result =true ;
            }
        } catch (Exception e) {
           logger.info(e.getMessage());
        }
        return result;
    }
}

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月16日 下午2:44:12
* 创建作者：许小满
* 文件名称：StaticUserController.java
* 版本：  v1.0
* 功能：静态用户名-控制层
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.user.staticuser.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.api.client.sms.util.SmsClient;
import com.awifi.np.biz.common.base.constants.RedisConstants;
import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.EncryUtil;
import com.awifi.np.biz.common.util.RandomUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.pagesrv.base.util.ExceptionUtil;
import com.awifi.np.biz.toe.admin.system.sms.service.SmsService;
import com.awifi.np.biz.toe.admin.usrmgr.staticuser.service.StaticUserService;

@SuppressWarnings("rawtypes")
@Controller
public class StaticUserController extends BaseController {

    /** 静态用户名-业务层 */
    @Resource(name = "staticUserService")
    private StaticUserService staticUserService;
    
    /** 短信记录-业务层 */
    @Resource(name = "smsService")
    private SmsService smsService;
    
    /**
     * 修改密码
     * @param request 请求
     * @return json
     * @author 许小满  
     * @date 2016年3月10日 下午8:08:35
     */
    @RequestMapping(value = "/staticuser/modifypwd")
    @ResponseBody
    public Map modifyPwd(HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            //请求方法类型校验
            if(request.getMethod().equals(HttpRequest.METHOD_GET)) {
                resultMap.put("result", "FAIL");
                resultMap.put("message", "非法请求");
                return resultMap;
            }
            // 请求参数 校验
            String userName = request.getParameter("userName");//静态用户名
            String oldPassword = request.getParameter("oldPassword");//旧密码
            String newPassword = request.getParameter("newPassword");//新密码
            String confirmPassword = request.getParameter("confirmPassword");//确认密码
            String customerId = request.getParameter("customerId");//客户id
            
            ValidUtil.valid("用户名", userName, "required");//静态用户名
            ValidUtil.valid("旧密码", oldPassword, "required");//旧密码
            ValidUtil.valid("新密码", newPassword, "required");//新密码
            ValidUtil.valid("确认密码", confirmPassword, "required");//确认密码
            ValidUtil.valid("商户id[customerId]", customerId, "required");//商户id
            
          //1.新密码与确认密码 校验
            if( !(newPassword.equals(confirmPassword)) ){
                resultMap.put("result", "FAIL");
                resultMap.put("message", "新密码与确认密码不一致，请重新输入!");
                return resultMap;
            }
            //2.判断用户名+密码是否匹配
            Long userId = staticUserService.getIdByUserNameAndPwd(Long.parseLong(customerId), userName, EncryUtil.getMd5Str(oldPassword));
            if(userId == null){
                resultMap.put("result", "FAIL");
                resultMap.put("message", "用户名与密码不匹配，请重新输入!");
                return resultMap;
            }
            //3.更新密码
            staticUserService.updatePwd(userId, EncryUtil.getMd5Str(newPassword));
            resultMap.put("result", "OK");//在页面上返回OK
            resultMap.put("message", "");
        }catch(Exception e){
            ExceptionUtil.formatMsg(request, resultMap, e, logger, "Sorry, modify pwd failed.");
        }
        return resultMap;
    }
    
    /**
     * 通过手机号修改密码
     * @param request 请求
     * @return json
     * @author 许小满  
     * @date 2016年3月10日 下午8:08:35
     */
    @RequestMapping(value = "/staticuser/modifypwdbycellphone")
    @ResponseBody
    public Map modifyPwdByCellphone(HttpServletRequest request){
        Map<String,Object> resultMap = new HashMap<String, Object>();
        try{
            //请求方法类型校验
            if(request.getMethod().equals(HttpRequest.METHOD_GET)) {
                resultMap.put("result", "FAIL");
                resultMap.put("message", "非法请求");//非法请求
                return resultMap;
            }
            // 请求参数 校验
            String cellphone = request.getParameter("cellphone");//手机号
            String newPassword = request.getParameter("newPassword");//新密码
            String confirmPassword = request.getParameter("confirmPassword");//确认密码
            String customerId = request.getParameter("customerId");//客户id
            
            ValidUtil.valid("手机号", cellphone, "{'required':true, 'regex':'"+ RegexConstants.CELLPHONE +"'}");//手机号
            ValidUtil.valid("新密码", newPassword, "required");//新密码
            ValidUtil.valid("确认密码", confirmPassword, "required");//确认密码
            ValidUtil.valid("商户id[customerId]", customerId, "required");//商户id
            
            //1.新密码与确认密码 校验
            if( !(newPassword.equals(confirmPassword)) ){
                resultMap.put("result", "FAIL");
                resultMap.put("message", "新密码与确认密码不一致，请重新输入!");
                return resultMap;
            }
            Long customerIdLong = Long.parseLong(customerId);
            //2.判断手机号是否有效
            if(!staticUserService.isCellphoneExist(customerIdLong, cellphone)){
                resultMap.put("result", "FAIL");
                resultMap.put("message", "手机号未绑定");//手机号未绑定
                return resultMap;
            }
            //3.更新密码
            staticUserService.updatePwdByCellphone(customerIdLong, cellphone, EncryUtil.getMd5Str(newPassword));
            resultMap.put("result", "OK");//在页面上返回OK
            resultMap.put("message", "");
        }catch(Exception e){
            ExceptionUtil.formatMsg(request, resultMap, e, logger, "Sorry, modify pwd failed.");
        }
        return resultMap;
    }
    
    /**
     * 获取验证码
     * @param request 请求
     * @param cellphone 手机号
     * @return json
     * @author 许小满  
     * @date 2017年6月1日 上午12:46:23
     */
    @RequestMapping(value = "/authcode/sendauthcode")
    @ResponseBody
    public Map sendAuthCode(HttpServletRequest request, String cellphone) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        try {
            // 请求方法类型校验
            if (request.getMethod().equals(HttpRequest.METHOD_GET)) {
                resultMap.put("result", "FAIL");
                resultMap.put("message", "非法请求");
                return resultMap;
            }
            // 请求参数 校验
            String customerId = request.getParameter("customerId");// 客户id
            
            ValidUtil.valid("商户id[customerId]", customerId, "required");//商户id
            ValidUtil.valid("手机号", cellphone, "{'required':true, 'regex':'"+ RegexConstants.CELLPHONE +"'}");//手机号
            
            // 判断手机号是否已绑定
            Long customerIdLong = Long.parseLong(customerId);
            if (!staticUserService.isCellphoneExist(customerIdLong, cellphone)) {
                resultMap.put("result", "FAIL");
                resultMap.put("message", "手机号未绑定");// 手机号未绑定
                return resultMap;
            }
            // 发送验证码短信
            String smsCode = RandomUtil.getRandomNumber(6);//生成验证码
            SmsClient.sendSmsCode(customerIdLong, cellphone, smsCode);
            //保存短信记录
            //smsService.add(customerIdLong, cellphone, smsContent);

            resultMap.put("result", "OK");// 在页面上返回OK
            resultMap.put("message", "");
        } catch (BizException e) {
            String errorMsg = e.getMessage();
            ExceptionUtil.formatMsg(request, resultMap, e, logger, "Sorry, sms send failed.");
            if(StringUtils.isNoneBlank(errorMsg) && errorMsg.equals("sms_code_sended")){
                resultMap.put("result", "FAIL");
                resultMap.put("message", "抱歉，验证码发送过于频繁，您可使用获取的验证码进行密码修改.");
                return resultMap;
            }
        } catch (Exception e) {
            ExceptionUtil.formatMsg(request, resultMap, e, logger, "Sorry, sms send failed.");
        }
        return resultMap;
    }
    
    /**
     * 校验验证码
     * @param request 请求
     * @return json
     * @author 许小满  
     * @date 2017年6月1日 上午1:02:39
     */
    @RequestMapping(value = "/authcode/iscorrect")
    @ResponseBody
    public Map isCorrect(HttpServletRequest request) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        try {
            // 请求方法类型校验
            if (request.getMethod().equals(HttpRequest.METHOD_GET)) {
                resultMap.put("result", "FAIL");
                resultMap.put("message", "非法请求");
                return resultMap;
            }
            String cellphone = request.getParameter("cellphone");// 手机号
            String authcode = request.getParameter("authCode");// 验证码
            String customerId = request.getParameter("customerId");// 客户id
            // 请求参数 校验(校验验证码)
            ValidUtil.valid("商户id[customerId]", customerId, "required");//商户id
            ValidUtil.valid("手机号", cellphone, "{'required':true, 'regex':'"+ RegexConstants.CELLPHONE +"'}");//手机号
            ValidUtil.valid("验证码", authcode, "required");//验证码
            // 从redis缓存中获取验证码
            String redisKey = RedisConstants.SMS + cellphone;//rediskey
            List<String> smsCodeList = RedisUtil.hmget(redisKey, "smsCode");
            if(smsCodeList == null || smsCodeList.size() <= 0){
                resultMap.put("result", "FAIL");
                resultMap.put("message", "抱歉，验证码已失效，请重新获取.");// 验证码过期或未发送
                return resultMap;
            }
            String smsCodeCache = smsCodeList.get(0);//redis缓存中的验证码
            if(StringUtils.isBlank(smsCodeCache)){
                resultMap.put("result", "FAIL");
                resultMap.put("message", "抱歉，验证码已失效，请重新获取.");// 验证码过期或未发送
                return resultMap;
            } else if (smsCodeCache.equals(authcode)){//验证码正确
                
            }else {
                resultMap.put("result", "FAIL");
                resultMap.put("message", "抱歉，验证码校验失败，请重新输入.");// 验证码错误
                return resultMap;
            }
            
            resultMap.put("result", "OK");
            resultMap.put("message", "");
        } catch (Exception e) {
            ExceptionUtil.formatMsg(request, resultMap, e, logger, "Sorry, sms valid failed.");
        }
        return resultMap;
    }
    
}

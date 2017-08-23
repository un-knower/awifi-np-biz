/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月10日 上午9:47:00
* 创建作者：周颖
* 文件名称：UserAuthController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.usrsrv.userauth.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.constants.Constants;
import com.awifi.np.biz.common.base.constants.RedisConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.DateUtil;
import com.awifi.np.biz.common.util.FormatUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;

@Controller
@RequestMapping("/usrsrv")
public class UserAuthController extends BaseController {
    
    /**
     * 设备认证上网记录
     * @param access_token access_token
     * @param params 设备id
     * @return 记录
     * @author 周颖  
     * @throws Exception 异常
     * @date 2017年7月10日 下午5:03:22
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(method=RequestMethod.GET,value="/userauth/device")
    public Map<String,Object> getListByDeviceId(
            @RequestParam(value="access_token",required=true)String access_token,
            @RequestParam(value="params",required=true)String params) throws Exception{
        Map<String,Object> paramsMap = JsonUtil.fromJson(params, Map.class);//请求参数 json格式
        String deviceId = (String) paramsMap.get("deviceId");//设备id
        ValidUtil.valid("设备id[deviceId]", deviceId, "required");//不允许为空
        
        String redisKey = RedisConstants.USER_AUTH + deviceId;//生成rediskey
        List<String> redisList = RedisUtil.lrange(redisKey, 0, Constants.AUTHTOTAL-1);//获取列表
        int maxSize = redisList.size();//列表大小
        logger.debug("redisKey:"+ redisKey + ",size:"+maxSize);
        Map<String,Object> redisMap = null;
        Map<String,Object> userAuthMap = null;
        String nowDate = DateUtil.getNow();
        List<Map<String,Object>> userAuthList = new ArrayList<Map<String,Object>>(maxSize);
        for(int i=0;i<maxSize;i++){//循环列表
            String userAuth = redisList.get(i);
            if(StringUtils.isBlank(userAuth)){//如果为空，下一条
                logger.debug("认证记录为空！");
                continue;
            }
            redisMap = JsonUtil.fromJson(userAuth, Map.class);
            if(redisMap == null || redisMap.size() == 0){//如果为空，下一条
                logger.debug("认证记录为空！");
                continue;
            }
            String authDate = (String) redisMap.get("authDate");//认证时间
            Long second = DateUtil.minusDate(authDate, nowDate);//与现在时间的时间差 s
            if(second > RedisConstants.USER_AUTH_TIME){//如果大于设置的时间，后面的数据直接跳过
                break;
            }
            
            userAuthMap = new HashMap<String, Object>();
            String phone = (String) redisMap.get("phone");//手机号
            if(StringUtils.isNotBlank(phone)){//如果不为空  中间四位隐藏
                phone = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
            }
            String authType = (String) redisMap.get("authType");//认证类型 sms、account、authed、wechat、ivr
            String authTypeDsp = FormatUtil.formatAuthType(authType);
            
            userAuthMap.put("phone", phone);
            userAuthMap.put("userMac", redisMap.get("userMac"));//终端mac
            userAuthMap.put("authType", authType);//认证类型 sms、account、authed、wechat、ivr
            userAuthMap.put("authTypeDsp", authTypeDsp);//认证类型显示值
            userAuthMap.put("terminalType", redisMap.get("terminalType"));//终端类型
            userAuthMap.put("terminalBrand", redisMap.get("terminalBrand"));//终端品牌
            userAuthMap.put("terminalVersion", redisMap.get("terminalVersion"));//终端版本
            userAuthMap.put("authDate", authDate);//认证时间
            userAuthList.add(userAuthMap);
        }
        return this.successMsg(userAuthList);
    }
}

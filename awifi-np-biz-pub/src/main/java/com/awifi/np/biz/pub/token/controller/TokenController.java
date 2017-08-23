package com.awifi.np.biz.pub.token.controller;

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

import com.awifi.np.admin.platform.service.PlatformService;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.redis.util.RedisAdminUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.EncryUtil;
import com.awifi.np.biz.common.util.IPUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.KeyUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月12日 下午2:38:31
 * 创建作者：周颖
 * 文件名称：TokenController.java
 * 版本：  v1.0
 * 功能：access_token控制层
 * 修改记录：
 */
@Controller
public class TokenController extends BaseController {
    
    /**timestamp有效时间*/
    public static final long TOKEN_TIMEOUT = 300000;//5*60*1000 timestamp有效时间 五分钟
    
    /**平台服务层*/
    @Resource(name = "platformService")
    private PlatformService platformService;

    /**
     * 生成access_token
     * @param appId 平台id
     * @param timestamp 时间戳
     * @param token token
     * @param userInfo 用户信息
     * @param request 请求
     * @return access_token
     * @author 周颖  
     * @date 2017年1月12日 下午3:27:09
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(method=RequestMethod.GET,value="/pubsrv/access_token")
    @ResponseBody
    public Map getAccessToken(@RequestParam(value="appid",required=true)String appId,@RequestParam(value="timestamp",required=true)String timestamp,
                              @RequestParam(value="token",required=true)String token,@RequestParam(value="userinfo",required=true)String userInfo,HttpServletRequest request){
        String appKey = platformService.getKeyByAppId(appId);//根据平台id获取平台key
        if(StringUtils.isBlank(appKey)){//如果平台key为空
            throw new BizException("E2000002", MessageUtil.getMessage("E2000002", appId));//抛业务异常 平台id无效
        }
        if(EncryUtil.isTimeout(timestamp, TOKEN_TIMEOUT)){//如果时间戳超时
            throw new BizException("E2000003", MessageUtil.getMessage("E2000003", token));//抛业务异常
        }
        String tokenNew = EncryUtil.getMd5Str(appId + appKey + timestamp);//根据传参重新生成token
        if(!tokenNew.equals(token)){//如果两个token不同
            throw new BizException("E2000004", MessageUtil.getMessage("E2000004", token));//抛异常
        }
        String accessToken = KeyUtil.generateAccessToken(appId);//生成access_token;
        String userIp = IPUtil.getIpAddr(request);//用户ip
        Map<String,Object> value = new HashMap<String,Object>();
        value.put("appId", appId);//redis 保存平台id
        value.put("userIp", userIp);//redis 保存用户ip
        value.put("userInfo", userInfo);//redis 保存用户信息
        int accessTokenTime = Integer.parseInt(SysConfigUtil.getParamValue("access_token_time"));
        RedisAdminUtil.set(accessToken, JsonUtil.toJson(value), accessTokenTime);//access_token保存到redis
        Map<String,Object> data =new HashMap<String,Object>();
        data.put("access_token", accessToken);//返回access_token
        return this.successMsg(data);//返回结果
    }
}   
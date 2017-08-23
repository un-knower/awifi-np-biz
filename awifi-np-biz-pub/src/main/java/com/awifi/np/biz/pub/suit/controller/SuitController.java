/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月9日 下午2:03:36
* 创建作者：周颖
* 文件名称：SuitController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.pub.suit.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.admin.security.user.service.UserService;
import com.awifi.np.admin.suit.service.SuitService;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.redis.util.RedisAdminUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;

@Controller
@RequestMapping("/pubsrv")
@SuppressWarnings("unchecked")
public class SuitController extends BaseController {

    /**套码*/
    @Resource(name="suitService")
    private SuitService suitService;
    
    /**用户*/
    @Resource(name="userService")
    private UserService userService;
    
    /**
     * 通过access_token获取当前登录用户所有套码接口
     * @param accessToken access_token
     * @return 结果
     * @author 周颖  
     * @date 2017年5月9日 下午2:17:41
     */
    @ResponseBody
    @RequestMapping(method=RequestMethod.GET,value="/suits")
    public Map<String,Object> getSuitByAccessToken(@RequestParam(value="access_token",required=true)String accessToken){
        String valueString = RedisAdminUtil.get(accessToken);//从redis获取access_token的值
        if(StringUtils.isBlank(valueString)){
            throw new BizException("E0000001", MessageUtil.getMessage("E0000001"));//access_token失效
        }
        Map<String,Object> value = (Map<String, Object>) JsonUtil.fromJson(valueString, HashMap.class);//转map
        Map<String,Object> userInfo = (Map<String, Object>) value.get("userInfo");//获取redis的用户信息
        String suitCode = (String) userInfo.get("suitCode");
        Long userId = CastUtil.toLong(userInfo.get("id"));
        List<Map<String,Object>> suitList = suitService.getSuitCodesByUserId(userId);
        Map<String,Object> suitMap = new HashMap<String,Object>();
        suitMap.put("suitCode", suitCode);
        suitMap.put("suits", suitList);
        return this.successMsg(suitMap);
    }
    
    /**
     * 通过access_token更新当前登录用户默认套码接口
     * @param accessToken access_token
     * @param suitCode 套码
     * @return 结果
     * @author 周颖  
     * @date 2017年5月9日 下午3:07:07
     */
    @ResponseBody
    @RequestMapping(method=RequestMethod.PUT,value="/suit/{suitcode}")
    public Map<String,Object> updateSuitCodeByAccessToken(
            @RequestParam(value="access_token",required=true)String accessToken,
            @PathVariable(value="suitcode",required=true)String suitCode){
        String valueString = RedisAdminUtil.get(accessToken);//从redis获取access_token的值
        if(StringUtils.isBlank(valueString)){
            throw new BizException("E0000001", MessageUtil.getMessage("E0000001"));//access_token失效
        }
        Map<String,Object> value = (Map<String, Object>) JsonUtil.fromJson(valueString, HashMap.class);//转map
        Map<String,Object> userInfo = (Map<String, Object>) value.get("userInfo");//获取redis的用户信息
        Long userId = CastUtil.toLong(userInfo.get("id"));
        userService.updateSuitById(userId,suitCode);
        return this.successMsg();
    }
}

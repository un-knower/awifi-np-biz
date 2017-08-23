package com.awifi.np.biz.pub.security.logout.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.redis.util.RedisAdminUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月23日 下午1:54:11
 * 创建作者：周颖
 * 文件名称：LogoutController.java
 * 版本：  v1.0
 * 功能：注销控制层
 * 修改记录：
 */
@SuppressWarnings("rawtypes")
@Controller
public class LogoutController extends BaseController {
    
    /**
     * 注销
     * @param accessToken access_token
     * @return 结果
     * @author 周颖  
     * @date 2017年2月23日 下午2:11:44
     */
    @RequestMapping(method=RequestMethod.DELETE,value="/pubsrv/logout")
    @ResponseBody
    public Map logout(@RequestParam(value="access_token",required=true)String accessToken){
        RedisAdminUtil.delete(accessToken);
        return this.successMsg();
    }
}
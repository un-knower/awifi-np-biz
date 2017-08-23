package com.awifi.np.biz.pub.view.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.redis.util.RedisAdminUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.template.service.TemplateService;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年3月2日 下午4:25:09
 * 创建作者：周颖
 * 文件名称：ViewController.java
 * 版本：  v1.0
 * 功能：显示接口
 * 修改记录：
 */
@Controller
public class ViewController extends BaseController {

    /**模板业务层*/
    @Resource(name = "templateService")
    private TemplateService templateService;
    
    /**
     * 获取首页页面
     * @param templateCode 模板编号
     * @param accessToken access_token
     * @return 模板页面
     * @author 周颖  
     * @date 2017年1月12日 上午10:52:19
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(method=RequestMethod.GET, value="/pubsrv/view/{templatecode}")
    @ResponseBody
    public Map indexView(@PathVariable(value="templatecode") String templateCode,@RequestParam(value="access_token",required=true)String accessToken){
        String serviceCode = SysConfigUtil.getParamValue("servicecode_pub");//从配置表读取服务代码
        String valueString = RedisAdminUtil.get(accessToken);//从redis获取access_token的值
        if(StringUtils.isBlank(valueString)){//如果为空
            throw new BizException("E0000001", MessageUtil.getMessage("E0000001"));//access_token失效
        }
        Map<String,Object> value = JsonUtil.fromJson(valueString, Map.class);//转map
        Map<String,Object> userInfo = (Map<String, Object>) value.get("userInfo");//获取redis的用户信息
        String suitCode = (String) userInfo.get("suitCode");//获取用户套码信息
        String template = templateService.getByCode(suitCode,serviceCode,templateCode);//获取模板页面
        return this.successMsg(template);//返回
    }
}
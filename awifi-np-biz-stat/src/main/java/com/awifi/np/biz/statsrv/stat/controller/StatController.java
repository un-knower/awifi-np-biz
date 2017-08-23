/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月26日 下午2:16:33
* 创建作者：许小满
* 文件名称：StatController.java
* 版本：  v1.0
* 功能：统计--控制层相关代码
* 修改记录：
*/
package com.awifi.np.biz.statsrv.stat.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.template.service.TemplateService;
import com.awifi.np.biz.common.util.MessageUtil;

@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/statsrv")
public class StatController extends BaseController {

    /**模板服务*/
    @Resource(name = "templateService")
    private TemplateService templateService;
    
    /**
     * 显示接口
     * @param request 请求
     * @param templatecode page(pan)编号
     * @param access_token 安全令牌
     * @return map
     * @author 许小满  
     * @date 2017年7月26日 下午2:18:16
     */
    @ResponseBody
    @RequestMapping(value="/view/{templatecode}", method=RequestMethod.GET)
    public Map view(HttpServletRequest request, @PathVariable String templatecode, @RequestParam(value="access_token",required=true) String access_token){
        String serviceCode = SysConfigUtil.getParamValue("servicecode_stat");//从配置表读取服务代码
        String suitCode = SessionUtil.getCurSessionUser(request).getSuitCode();//从session中获取套码
        if(StringUtils.isBlank(suitCode)){//套码未找到抛出异常
            throw new ValidException("E2000005", MessageUtil.getMessage("E2000005"));//套码不允许为空!
        }
        String template = templateService.getByCode(suitCode,serviceCode,templatecode);//获取模板页面
        return this.successMsg(template);
    }
    
}

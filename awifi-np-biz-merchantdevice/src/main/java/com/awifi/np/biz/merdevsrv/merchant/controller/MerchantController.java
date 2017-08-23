/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月9日 下午1:58:34
* 创建作者：王冬冬
* 文件名称：MerchantController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.merdevsrv.merchant.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.template.service.TemplateService;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.merdevsrv.merchant.service.MerchantService;

@Controller
@RequestMapping("/merdevsrv")
public class MerchantController extends BaseController{
    /**设备业务层*/
    @Resource(name = "merchantService")
    private MerchantService merchantService;
    
    /**模板服务*/
    @Resource(name = "templateService")
    private TemplateService templateService;
    
    /**
     * 防蹭网
     * @param request 请求
     * @param access_token 安全令牌
     * @param bodyParam 请求体数据
     * @return map
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年4月17日 上午11:14:17
     */
    @ResponseBody
    @RequestMapping(value="/merchants/switch/antirobber", method=RequestMethod.PUT, produces="application/json")
    public Map batchantiRobber(HttpServletRequest request,@RequestParam(value="access_token",required=true) String access_token,@RequestBody(required=true) List<Map<String,Object>> bodyParam) throws Exception{
        for(Map<String,Object> map:bodyParam){
            ValidUtil.valid("商户id[merchantId]", map.get("merchantId"), "{'required':true,'numeric':true}");
            //参数校验
            ValidUtil.valid("防蹭网开关[status]", map.get("status"), "required");
        }
        
        merchantService.batchAntiRobber(bodyParam);//
        return this.successMsg();
    }
}

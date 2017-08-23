/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月22日 上午12:19:29
* 创建作者：许小满
* 文件名称：IndustryController.java
* 版本：  v1.0
* 功能：行业控制层
* 修改记录：
*/
package com.awifi.np.biz.devicebindsrv.system.industry.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.devicebindsrv.system.industry.service.IndustryService;

@Controller
@SuppressWarnings("rawtypes")
@RequestMapping(value = "/devbindsrv")
public class IndustryController extends BaseController {

    /**行业服务层*/
    @Resource(name = "industryService")
    private IndustryService industryService;
    
    /**
     * 获取行业列表
     * @param parentCode 父行业编号
     * @return 行业列表
     * @author 周颖  
     * @throws Exception 
     * @date 2017年1月20日 上午11:05:01
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,value = "/industrys")
    public Map getListByParam(@RequestParam(value="parentcode",required=false)String parentCode) throws Exception{
        List<Map<String,String>> industryList = industryService.getListByParam(parentCode);
        return this.successMsg(industryList);
    }
}

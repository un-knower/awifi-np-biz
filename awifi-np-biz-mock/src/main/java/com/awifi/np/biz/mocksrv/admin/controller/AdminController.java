package com.awifi.np.biz.mocksrv.admin.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.controller.BaseController;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月13日 上午9:41:22
 * 创建作者：亢燕翔
 * 文件名称：AdminController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/mocksrv/admin")
public class AdminController extends BaseController{

    /**
     * 通过access_token获取用户信息
     * @return 用户信息
     * @author 亢燕翔  
     * @date 2017年1月13日 上午9:41:43
     */
    @ResponseBody
    @RequestMapping(value="/call",method=RequestMethod.GET)
    public Map call(){
        //2级map
        Map<String, Object> secResultMap = new LinkedHashMap<String, Object>();
        secResultMap.put("id", 1L);
        secResultMap.put("userName", "superadmin");
        secResultMap.put("roleIds", "1");
        secResultMap.put("provinceId", 31L);
        secResultMap.put("cityId", 383L);
        secResultMap.put("areaId", 3232L);
        //secResultMap.put("merchantId", 1110);
        secResultMap.put("suitCode", "superadmin_v1");
        
        //3级map
        Map<String, Object> levResultMap = new LinkedHashMap<String, Object>();
        levResultMap.put("orgId", 1L);
        secResultMap.put("extend", levResultMap);
        
        return successMsg(secResultMap);
    }
    
    
}

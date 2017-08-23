package com.awifi.np.biz.devsrv.entity.controller;

import java.util.Map;

import javax.annotation.Resource;

import com.awifi.np.biz.common.util.ValidUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.devsrv.entity.service.EntityService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月10日 下午3:18:03
 * 创建作者：亢燕翔
 * 文件名称：EntityController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/devsrv")
public class EntityController extends BaseController{
    
    /**实体业务层*/
    @Resource(name = "entityService")
    private EntityService entityService;
    
    /**
     * 设备编辑
     * @param access_token 安全令牌
     * @param devid 设备id
     * @param bodyParam 请求参数
     * @return map
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月10日 下午3:22:19
     */
    @ResponseBody
    @RequestMapping(value="/entity/{devid}", method=RequestMethod.PUT, produces="application/json")
    public Map update(@RequestParam(value="access_token",required=true)String access_token, @PathVariable String devid,
            @RequestBody(required=true) Map<String, Object> bodyParam) throws Exception{
        ValidUtil.valid("deviceId",devid, "required");//设备id 必填
        entityService.update(devid,bodyParam);
        return this.successMsg();
    }
    

}

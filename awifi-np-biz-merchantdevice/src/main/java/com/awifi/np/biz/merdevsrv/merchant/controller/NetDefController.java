/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月19日 下午1:47:28
* 创建作者：王冬冬
* 文件名称：NetDefController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.merdevsrv.merchant.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.merdevsrv.merchant.service.NetDefService;

/**
 * 防蹭网缓存
 * @author 王冬冬
 * 2017年6月19日 下午3:28:58
 */
@Controller
@RequestMapping("/merdevsrv")
public class NetDefController extends BaseController{
    
    /**
     * 防蹭网缓存service
     */
    @Resource(name="netDefService")
    private NetDefService netDefService;
    
    /**
     * @param request 请求
     * @param access_token 令牌
     * @return map
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年6月19日 下午3:29:28
     */
    @RequestMapping(value="/netdef/cache", method=RequestMethod.GET, produces="application/json")
    @ResponseBody
    public Map addToCache(HttpServletRequest request,@RequestParam(value="access_token",required=true) String access_token) throws Exception{
        netDefService.addToCache();
        return this.successMsg();
    }
    

}

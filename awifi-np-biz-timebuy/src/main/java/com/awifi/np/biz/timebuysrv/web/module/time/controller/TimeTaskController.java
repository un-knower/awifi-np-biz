package com.awifi.np.biz.timebuysrv.web.module.time.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.timebuysrv.web.module.time.service.WifiService;
import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIs;

@APIs(description = "时间定时接口")
@Controller
@RequestMapping("/timebuysrv/time/kick")
public class TimeTaskController extends BaseController {
    /** * Logger 引入 . */
    private static Logger logger = LoggerFactory.getLogger(TimeTaskController.class);

    /** * 引入AccessAuth . */
    @Resource
    private WifiService wifiService;

    /**
     * 踢下线接口
     * @param request
     * @return Map
     * @author zhangzw
     * @throws Exception 异常
     * @date 2017年5月26日17:41:16
     */
    @API(summary = "遍历踢下线 ", description = "由定时任务每隔10分钟执行一次 将时间到期的用户遍历踢下线 以及以往踢下线失败的用户")
    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    // @RequiresUser
    
    @ResponseBody
    public Map<String, Object> kickUser(HttpServletRequest request) throws Exception {
        wifiService.kickTimeOutUser();
        return this.successMsg();
    }


    public void setWifiService(WifiService wifiService) {
        this.wifiService = wifiService;
    }
}

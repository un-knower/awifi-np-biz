package com.awifi.np.biz.portalsrv.tool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.awifi.np.biz.common.base.controller.BaseController;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年4月6日 上午9:26:18
 * 创建作者：周颖
 * 文件名称：ToolController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Controller
@RequestMapping("/portalsrv")
public class ToolController extends BaseController {

    @RequestMapping(value = "/tool")
    public String getToolPage() {
        return "tool/tool";
    }
}

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月12日 下午7:27:48
* 创建作者：许小满
* 文件名称：BgPicController.java
* 版本：  v1.0
* 功能：背景图
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.thirdapp.bgpic.controller;

import java.net.URLDecoder;
import java.nio.ByteBuffer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.ResponseUtil;
import com.awifi.np.biz.pagesrv.api.client.thirdapp.bgpic.util.BgPicApiClient;
import com.awifi.np.biz.pagesrv.thirdapp.param.service.ParamService;

@Controller
public class BgPicController extends BaseController {

    /** 参数 业务层 */
    @Resource(name = "paramService")
    private ParamService paramService;
    
    /**
     * 获取背景图接口
     * @param request 请求
     * @param response 响应
     * @author 许小满  
     * @date 2017年5月12日 下午7:30:59
     */
    @RequestMapping(value = "/app/getBgSource")
    @ResponseBody
    public void getBgPic(HttpServletRequest request, HttpServletResponse response){
        long beginTime = System.currentTimeMillis();
        //InputStream inputStream = null;
        try{
            // 接收参数
            String customerId = StringUtils.defaultString(request.getParameter("customerId"));//客户ID
            String deviceId = StringUtils.defaultString(request.getParameter("deviceId"));//设备ID
            String devMac = StringUtils.defaultString(request.getParameter("devMac"));//设备MAC
            String userIp = StringUtils.defaultString(request.getParameter("userIp"));//用户IP
            String userMac = StringUtils.defaultString(request.getParameter("userMac"));//用户MAC
            String userPhone = StringUtils.defaultString(request.getParameter("userPhone"));//用户手机
            String terminalType = StringUtils.defaultString(request.getParameter("terminalType"));//用户终端类型
            String url = URLDecoder.decode(StringUtils.defaultString(request.getParameter("url")),"UTF-8");//URL
            
            String redisKey = paramService.putParamsToCache(customerId, deviceId, devMac, userIp, userMac, userPhone, terminalType);//将页面参数存放至redis中
            ByteBuffer byteBuffer = BgPicApiClient.getBgPic(redisKey, url);
            
            ResponseUtil.responseImage(response, byteBuffer);
        } catch (Exception e) {
            String filePath = SysConfigUtil.getParamValue("resources_folder_path") + "/media/picture/yyt/bg.jpg";
            logger.debug("提示：filePath= " + filePath);
            ResponseUtil.responseImageByPath(response, filePath);
            //saveExceptionLog("", "app", appService.getClass().toString(), e);
        } finally {
            logger.debug("提示：获取背景图接口 共花费了 " + (System.currentTimeMillis() - beginTime) + " ms.");
        }
        
    }
    
}

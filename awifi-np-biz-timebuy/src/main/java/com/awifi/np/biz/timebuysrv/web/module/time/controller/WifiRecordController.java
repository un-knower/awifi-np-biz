/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月11日 下午5:00:51
* 创建作者：尤小平
* 文件名称：WifiRecordController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.web.module.time.controller;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.timebuysrv.util.StringUtil;
import com.awifi.np.biz.timebuysrv.web.module.time.model.WifiRecord;
import com.awifi.np.biz.timebuysrv.web.module.time.service.WifiRecordService;
import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@APIs(description = "网络放通日志信息")
@Controller
@RequestMapping(value = "/timebuysrv/wifi/record")
public class WifiRecordController extends BaseController {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * WifiRecordService
     */
    @Resource
    private WifiRecordService wifiRecordService;

    /**
     * 根据id获取网络放通日志信息.
     * 
     * @param id id
     * @return Map
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月11日 下午8:55:14
     */
    @API(summary = "根据id获取网络放通日志信息", parameters = {
            @Param(name = "id", description = "id", in = "path", dataType = DataType.LONG, required = true) })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String, Object> getById(@PathVariable(value = "id") String id) throws Exception {
        logger.debug("根据id获取网络放通日志信息, request id= " + id);
        ValidUtil.valid("id", id, "required");
        ValidUtil.valid("id", id, "numeric");

        WifiRecord record = wifiRecordService.selectById(Long.valueOf(id));

        return this.successMsg(record);
    }

    /**
     * 根据deviceId和token获取网络放通日志信息.
     * 
     * @param request request
     * @return Map
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月12日 下午4:14:21
     */
    @API(summary = "根据deviceId和token获取网络放通日志信息", parameters = {
            @Param(name = "deviceId", description = "deviceId", dataType = DataType.STRING, required = true),
            @Param(name = "token", description = "token", dataType = DataType.STRING, required = true) })
    @RequestMapping(value = "/getByDevIdAndToken", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String, Object> getByDevIdAndToken(HttpServletRequest request) throws Exception {
        logger.debug("根据deviceId和token获取网络放通日志信息, request params= " + JsonUtil.toJson(request.getParameterMap()));
        String deviceId = request.getParameter("deviceId");
        String token = request.getParameter("token");

        ValidUtil.valid("deviceId", deviceId, "required");
        ValidUtil.valid("token", token, "required");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("deviceId", deviceId);
        params.put("token", token);

        WifiRecord record = wifiRecordService.selectByDevIdAndToken(params);

        return this.successMsg(record);
    }

    /**
     * 添加网络放通日志信息.
     * 
     * @param request request
     * @return Map
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月11日 下午8:55:28
     */
    @API(summary = "添加网络放通日志信息", parameters = {
            @Param(name = "userId", description = "userId", dataType = DataType.LONG, required = false),
            @Param(name = "merchantId", description = "merchantId", dataType = DataType.LONG, required = false),
            @Param(name = "portalUrl", description = "portalUrl", dataType = DataType.STRING, required = false),
            @Param(name = "telphone", description = "telphone", dataType = DataType.STRING, required = false),
            @Param(name = "deviceId", description = "deviceId", dataType = DataType.STRING, required = false),
            @Param(name = "devType", description = "devType", dataType = DataType.STRING, required = false),
            @Param(name = "token", description = "token", dataType = DataType.STRING, required = false) })
    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String, Object> add(HttpServletRequest request) throws Exception {
        logger.debug("添加网络放通日志信息, request params= " + JsonUtil.toJson(request.getParameterMap()));
        Long userId = CastUtil.toLong(request.getParameter("userId"));
        Long merchantId = CastUtil.toLong(request.getParameter("merchantId"));
        String portalUrl = request.getParameter("portalUrl");
        String telphone = request.getParameter("telphone");
        String deviceId = request.getParameter("deviceId");
        String devType = request.getParameter("devType");
        String token = request.getParameter("token");

        WifiRecord record = new WifiRecord();
        record.setUserId(userId);
        record.setMerchantId(merchantId);
        record.setPortalUrl(portalUrl);
        record.setTelphone(telphone);
        record.setDeviceId(deviceId);
        record.setDevType(devType);
        record.setToken(token);

        Long id = wifiRecordService.save(record);

        return this.successMsg(id);
    }

    /**
     * 更新网络放通日志信息.
     * 
     * @param request request
     * @return Map
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月11日 下午8:55:41
     */
    @API(summary = "更新网络放通日志信息", parameters = {
            @Param(name = "id", description = "id", dataType = DataType.LONG, required = true),
            @Param(name = "wifiUrl", description = "wifiUrl", dataType = DataType.STRING, required = false),
            @Param(name = "wifiResult", description = "wifiResult", dataType = DataType.STRING, required = false) })
    @RequestMapping(value = "", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    public Map<String, Object> edit(HttpServletRequest request) throws Exception {
        logger.debug("更新网络放通日志信息, request params= " + JsonUtil.toJson(request.getParameterMap()));

        Long id = CastUtil.toLong(request.getParameter("id"));
        String wifiUrl = request.getParameter("wifiUrl");
        String wifiResult = request.getParameter("wifiResult");

        ValidUtil.valid("id", id, "required");
        ValidUtil.valid("id", id, "numeric");

        WifiRecord record = new WifiRecord();
        record.setId(id);
        if (StringUtil.isNotBlank(wifiUrl)) {
            record.setWifiUrl(wifiUrl);
        }
        if (StringUtil.isNotBlank(wifiResult)) {
            record.setWifiResult(wifiResult);
        }

        boolean result = wifiRecordService.update(record);

        return this.successMsg(result);
    }

    /**
     * for testing only.
     * 
     * @param wifiRecordService WifiRecordService
     * @author 尤小平  
     * @date 2017年5月15日 上午10:40:26
     */
    public void setWifiRecordService(WifiRecordService wifiRecordService) {
        this.wifiRecordService = wifiRecordService;
    }
}

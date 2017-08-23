/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月12日 下午3:28:32
* 创建作者：尤小平
* 文件名称：MerchantAppController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mersrv.ms.merchantapp.controller;

import com.alibaba.fastjson.JSONObject;
//import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
//import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.ms.util.MsCommonUtil;
import com.awifi.np.biz.common.ms.util.StringUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.mws.merchant.app.model.StationApp;
import com.awifi.np.biz.mws.merchant.app.service.MerchantAppService;
import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@APIs(description = "商户应用模块")
@Controller
@RequestMapping(value = "/mersrv/ms/merchant/app")
public class MerchantAppController extends BaseController {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * MerchantAppService
     */
    @Resource
    private MerchantAppService merchantAppService;

    /**
     * 获取商户下的已发布和已配置应用列表.
     *
     * @param request request
     * @return Map
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年6月12日 下午4:08:56
     */
    @API(summary = "根据商户id查询商户下的已发布和已配置应用列表", parameters = {
            @Param(name = "merchantId", description = "商户id", dataType = DataType.LONG, required = true),
            @Param(name = "userId", description = "用户id", dataType = DataType.LONG, required = false) })
    @RequestMapping(method = RequestMethod.GET, value = "/list", produces = "application/json")
    @ResponseBody
    public Map<String, Object> getAppList(HttpServletRequest request) throws Exception {
        List<StationApp> appList = null;
        String userOpenId = null;

        // ==========获取参数======================
        logger.debug("request param=" + JsonUtil.toJson(request.getParameterMap()));
        Long merchantId = CastUtil.toLong(request.getParameter("merchantId"));// 商户id
        Long userId = CastUtil.toLong(request.getParameter("userId"));// 用户id

        // ==========参数校验======================
        ValidUtil.valid("merchantId", merchantId, "{'required':true,'numeric':true}");

        if (userId != null && userId > 0) {
            userOpenId = MsCommonUtil.getOpenId(userId);
        }

        // ==========获取应用列表，并设置token和openid======================
        if (merchantId != null) {
            appList = merchantAppService.getAppListByMerchantId(merchantId);
            if (appList != null && appList.size() > 0) {
                logger.debug("app list size=" + appList.size());
                for (StationApp app : appList) {
                    Long timestamp = System.currentTimeMillis();
                    app.setTimestamp(timestamp);
                    app.setToken(MsCommonUtil.createToken(timestamp, MsCommonUtil.getOpenId(merchantId),
                            app.getAppSecret()));
                    app.setMerchantOpenId(MsCommonUtil.getOpenId(merchantId));
                    app.setUserOpenId(userOpenId);
                    app.setAppIcon(SysConfigUtil.getParamValue("mws_domain") + "/msv2/" + app.getAppIcon());
                }
            }
        }

        JSONObject json = new JSONObject();
        json.put("appList", appList);

        return this.successMsg(json);
    }

    /**
     * 应用查询.
     * 
     * @param id 应用id
     * @param request request
     * @return Map<String, Object>
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年6月13日 下午9:02:20
     */
    @API(summary = "查询应用", parameters = {
            @Param(name = "id", description = "id", in = "path", dataType = DataType.INTEGER, required = true) })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String, Object> getAppById(@PathVariable(value = "id", required = true) Integer id,
                                          HttpServletRequest request) throws Exception {
        HashMap<String, Object> result = new HashMap<>();
//        Merchant merchant = null;
        String merchantOpenId = "";
        Long timestamp = System.currentTimeMillis();

        // ==========获取参数======================
        logger.debug("id = " + id);
        logger.debug("request params = " + JsonUtil.toJson(request.getParameterMap()));
        Long merchantId = CastUtil.toLong(request.getParameter("merchantId"));
        Long userId = CastUtil.toLong(request.getParameter("userId"));

        // ==========参数校验======================
        ValidUtil.valid("id", id, "{'required':true,'numeric':true}");

        if (userId != null && userId > 0) {
            result.put("userOpenId", MsCommonUtil.getOpenId(userId));
        }
//        if(merchantId != null && merchantId>0){
//            merchant = MerchantClient.getByIdCache(merchantId);
//        }
//        if (merchant != null) {
//            result.put("merchant", merchant);
//            merchantOpenId = MsCommonUtil.getOpenId(merchant.getId());
        merchantOpenId = MsCommonUtil.getOpenId(merchantId);
        result.put("merchantOpenId", merchantOpenId);
//        }

        // ==========查询应用======================
        StationApp app = this.merchantAppService.getAppById(id);
        logger.debug("app = " + JsonUtil.toJson(app));

        if (app != null && !StringUtil.isBlank(app.getAppIcon())) {
            app.setAppIcon(SysConfigUtil.getParamValue("mws_domain") + "/msv2/" + app.getAppIcon());
        }

        result.put("timestamp", timestamp);
        result.put("token", MsCommonUtil.createToken(timestamp, merchantOpenId, app.getAppSecret()));
        result.put("app", app);

        return this.successMsg(result);
    }
}
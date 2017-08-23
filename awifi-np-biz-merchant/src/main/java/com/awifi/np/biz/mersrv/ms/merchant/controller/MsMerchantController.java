/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月8日 上午9:44:24
* 创建作者：尤小平
* 文件名称：MsMerchantController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mersrv.ms.merchant.controller;

import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.ms.util.TimeTag;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.mersrv.ms.merchant.service.MsMerchantService;
import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@APIs(description = "商户信息模块")
@Controller
@RequestMapping(value = "/mersrv/ms/merchant")
public class MsMerchantController extends BaseController {
    /**
     * logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * MsMerchantService
     */
    @Resource
    private MsMerchantService msMerchantService;

    /**
     * 微站商户信息展示.
     * 
     * @param merchantId 商户id
     * @param request request
     * @return Map
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年6月12日 上午11:23:57
     */
    @API(summary = "根据商户id查询微站商户信息", parameters = {
            @Param(name = "merchantId", description = "商户id", dataType = DataType.LONG, required = false) })
    @RequestMapping(method = RequestMethod.GET, value = "/{merchantId}", produces = "application/json")
    @ResponseBody
    public Map<String, Object> getMerchant(@PathVariable(value = "merchantId", required = true) Long merchantId,
            HttpServletRequest request) throws Exception {
        // ==========打印参数======================
        logger.debug("request param = " + JsonUtil.toJson(request.getParameterMap()));
        logger.debug("merchantId = " + merchantId);

        // ==========参数校验======================
        ValidUtil.valid("merchantId", merchantId, "{'required':true,'numeric':true}");

        // ==========获取商户======================
        Merchant merchant = msMerchantService.findMerchantInfoExclude9(merchantId);
        logger.info("merchant = "+JsonUtil.toJson(merchant));
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("merchant", getSimpleMerchantInfo(merchant));
        result.put("imgdomain", SysConfigUtil.getParamValue("media_image_domain"));//图片资源服务器前缀

        return this.successMsg(result);
    }

    /**
     * 编辑微站商户信息.
     * 
     * @param merchantId 商户id
     * @param bodyParam 参数
     * @return Map<String, Object>
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年6月14日 下午5:03:14
     */
    @API(summary = "编辑微站商户信息", parameters = {
            @Param(name = "merchantId", description = "商户id", dataType = DataType.LONG, required = false) })
    @RequestMapping(method = RequestMethod.PUT, value = "/{merchantId}", produces = "application/json")
    @ResponseBody
    public Map<String, Object> editMerchant(@PathVariable(value = "merchantId", required = true) Long merchantId,
            @RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
        // ==========获取参数======================
        logger.debug("bodyParam param = " + JsonUtil.toJson(bodyParam));
        logger.debug("merchantId = " + merchantId);
        String merchantName = (String) bodyParam.get("merchantName");// 店铺名称
        String remark = (String) bodyParam.get("remark");// 店铺简介
        String address = (String) bodyParam.get("address");// 店铺地址
        String telephone = (String) bodyParam.get("telephone");// 店铺电话
        String openTime = (String) bodyParam.get("openTime");// 营业开门时间
        String closeTime = (String) bodyParam.get("closeTime");// 营业关门时间

        // ==========参数校验======================
        ValidUtil.valid("merchantId", merchantId, "{'required':true,'numeric':true}");

        // ==========更新商户信息======================
        Merchant merchant = MerchantClient.getById(merchantId);
        if (merchant != null) {
            String industryCode = "";
            if (merchantName != null) {
                merchant.setMerchantName(merchantName);
            }
            if (telephone != null) {
                merchant.setContactWay(telephone);
            }
            if (remark != null) {
                merchant.setRemark(remark);
            }
            if (address != null) {
                merchant.setAddress(address);
            }
            if (openTime != null) {
                merchant.setOpenTime(TimeTag.getTimeFromStr(openTime));
            }
            if (closeTime != null) {
                merchant.setCloseTime(TimeTag.getTimeFromStr(closeTime));
            }
            if (merchant.getSecIndustryCode() != null) {
                industryCode = merchant.getSecIndustryCode();
            } else if (merchant.getPriIndustryCode() != null) {
                industryCode = merchant.getPriIndustryCode();
            }

            MerchantClient.update(merchant, industryCode);
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("merchant", merchant);
        result.put("imgdomain", SysConfigUtil.getParamValue("media_image_domain"));//图片资源服务器前缀

        return this.successMsg(result);
    }
    
    /**
     * 店铺展示信息.
     * 
     * @param merchant 店铺全信息
     * @return 店铺展示的信息.
     * @author 尤小平  
     * @date 2017年6月15日 下午8:14:44
     */
    private Map<String, Object> getSimpleMerchantInfo(Merchant merchant) {
        if (merchant != null) {
            Map<String, Object> simpleMerchant = new HashMap<String, Object>();
            simpleMerchant.put("id", merchant.getId());
            simpleMerchant.put("merchantName", merchant.getMerchantName());
            simpleMerchant.put("remark", merchant.getRemark());
            simpleMerchant.put("address", merchant.getAddress());
            simpleMerchant.put("telephone", merchant.getContactWay());
            simpleMerchant.put("openTime", merchant.getOpenTime());
            simpleMerchant.put("closeTime", merchant.getCloseTime());

            return simpleMerchant;
        }
        return null;
    }
}

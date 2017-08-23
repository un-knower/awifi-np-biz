/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月8日 上午10:20:44
* 创建作者：尤小平
* 文件名称：MerchantController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.merchant.controller;

import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;
import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.timebuysrv.merchant.model.PubMerchant;
import com.awifi.np.biz.timebuysrv.merchant.service.PubMerchantService;
import com.awifi.np.biz.timebuysrv.util.StringUtil;
import com.awifi.np.biz.timebuysrv.web.core.Constants;
import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;

import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@APIs(description = "商户服务")
@Controller
@RequestMapping(value = "/timebuysrv/merchant")
public class MerchantController extends BaseController {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * PubMerchantService
     */
    @Resource
    private PubMerchantService pubMerchantService;

    /**
     * 根据id获取商户信息.
     * 
     * @param id
     *            id
     * @return Map
     * @throws Exception
     *             异常
     * @author 尤小平
     * @date 2017年5月9日 下午4:49:43
     */
    @SuppressWarnings("rawtypes")
    @API(summary = "根据id获取商户信息", parameters = {
            @Param(name = "id", description = "id", in = "path", dataType = DataType.LONG, required = false) })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map getMerchantById(@PathVariable(value = "id") String id) throws Exception {

        logger.debug("根据id获取商户信息, request id= " + id);
        ValidUtil.valid("id", id, "{'required':true,'numeric':true}");
        
        Merchant merchant = MerchantClient.getByIdCache(Long.valueOf(id));
        logger.debug("get merchant by id return: " + JsonUtil.toJson(merchant));

        PubMerchant pubMerchant = JsonUtil.fromJson(JsonUtil.toJson(merchant), PubMerchant.class);
        pubMerchantService.saveMerchant(pubMerchant);

        return this.successMsg(merchant);
    }

    /**
     * 保存宽带账号
     * @param merchantId 商户id
     * @param account 宽带账号
     * @param bodyParam 请求体
     *            merchantId 商户id(为long型id) 宽带账号 格式一般为手机号码13912345678
     *            或者0571-87654321 或者057187654321",
     * @param request 请求
     * @return
     * @author 张智威
     * @date 2017年6月7日 下午8:36:11
     */
    @SuppressWarnings("rawtypes")
    @API(summary = "保存宽带账号", consumes = "application/json", description = "正常返回{code:0} 异常返回{code:非0值,msg:'错误消息'}", parameters = {
    })
    @RequestMapping(value = "/broadaccount", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map broadbandAccountSave(@RequestBody(required = true) Map<String, Object> bodyParam,
            HttpServletRequest request) {
        // 查询宽带账号

        Long merchantId = Long.valueOf(bodyParam.get("merchantId") + "");

        String account = String.valueOf(bodyParam.get("account"));

        // 存入宽带账号到redis
        RedisUtil.hset(Constants.REDIS_MSP_BROAD_ACCOUNT, merchantId + "", account);
        return this.successMsg();

    }

    /**
     * 获取宽带账号
     * @param access_token 权限口令
     * @param params 请求参数{merchantId:xxxxx Long}
     * @return Map account:宽带账号
     * @throws Exception 异常
     * @author 张智威
     * @date 2017年6月7日 下午8:36:34
     */
    @SuppressWarnings("rawtypes")
    @API(summary = "获取商户宽带账号", description = "正常返回{code:0,data:{account:'13912345678'}} 异常返回{code:非0值,msg:'错误消息'}", parameters = {
            @Param(name = "access_token", description = "安全令牌，不允许为空", dataType = DataType.STRING, required = true),
            @Param(name = "params", description = "请求参数{merchantId:123}", dataType = DataType.STRING, required = true), })
    @RequestMapping(value = "/broadaccount", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map broadbandAccountGet(@RequestParam(value = "access_token", required = true) String access_token, // 安全令牌，不允许为空
            @RequestParam(value = "params", required = true) String params// 请求参数
    ) throws Exception {
        // 查询宽带账号
        Map<String, Object> paramMap = JsonUtil.fromJson(params, Map.class);// 参数从字符串转json
        /* 参数校验 */
        Long merchantId = Long.valueOf(paramMap.get("merchantId") + "");// 商户id
        ValidUtil.valid("商户id", merchantId, "required");// 商户id必填校验

        // 构造查询参数
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("merchantId", merchantId);
        param.put("merchantQueryType", "this");
        String broadAccount = null;

        broadAccount = RedisUtil.hget(Constants.REDIS_MSP_BROAD_ACCOUNT, merchantId + "");
        if (StringUtil.isBlank(broadAccount)) {
            // 查询商户底下所有设备 取出相关的一条设备的宽带账号信息

            List<Device> list = DeviceClient.getListByParam(JsonUtil.toJson(param));

            if (list != null && list.size() > 0) {
                broadAccount = list.get(0).getBroadbandAccount();
            }

            if(StringUtils.isNotBlank(broadAccount)) {
                RedisUtil.hset(Constants.REDIS_MSP_BROAD_ACCOUNT, merchantId + "", broadAccount);
            }
        }
        // 构造返回参数
        Map<String, Object> map = new HashMap<>();
        map.put("account", broadAccount==null?"":broadAccount);
        return this.successMsg(map);
    }
}

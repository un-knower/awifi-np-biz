/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月3日 下午2:31:39
* 创建作者：尤小平
* 文件名称：TestController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.eagleeyesrv.test.controller;

import com.awifi.core.pre.eagleeye.client.http.HttpClientUtil;
import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(value = "/eagleeyesrv")
public class TestController extends BaseController {

    @Autowired
    private HttpClientUtil httpClient;

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    @ResponseBody
    String hello() throws Exception {
        System.out.println("httpClient:" + httpClient);
        String response = null;

        response = httpClient.get("http://localhost:8080/eagleeyesrv/merchant/204");
        // response =
        // httpClient.get("http://192.168.212.90:8089/timebuysrv/merchant/1");
        // //MerchantController
        // response =
        // httpClient.get("http://192.168.212.90:8089/timebuysrv/time/consume/synMerchant");//TimeConsumeController
        System.out.println("response:" + response);

        // response =
        // httpClient.get("http://i.51awifi.com/timebuysrv/user/base/204034");
        // System.out.println("response:" + response);
        // response =
        // httpClient.get("http://192.168.41.78:8083/devsrv/device/upgrade/region/78");
        // System.out.println("response:" + response);

        return response;
    }

    @RequestMapping(value = "/merchant/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String, Object> getMerchantById(@PathVariable(value = "id") String id) throws Exception {

        System.out.println("id:" + id);
        ValidUtil.valid("id", id, "{'required':true,'numeric':true}");

        Merchant merchant = MerchantClient.getByIdCache(Long.valueOf(id));
        System.out.println("merchant: " + JsonUtil.toJson(merchant));

        return this.successMsg(merchant);
    }
}

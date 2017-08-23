/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月24日 下午7:13:53
* 创建作者：范涌涛
* 文件名称：FitApController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.fitap.controller;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.awifi.np.biz.api.client.dbcenter.fatap.util.DeviceQueryClient;
import com.awifi.np.biz.api.client.dbcenter.fitap.util.FitApClient;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;


@Controller
@SuppressWarnings({"rawtypes","unchecked"})
public class FitApController extends BaseController{


    /**
     * 分页查询瘦AP
     * @param accessToken 安全令牌
     * @param params 请求参数
     * @return Map
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年6月4日 下午2:38:12
     */
    @RequestMapping(method = RequestMethod.GET, value = "/devsrv/fitaps")
    @ResponseBody
    public Map queryFitApList(@RequestParam(value = "access_token", required = true) String accessToken,
            @RequestParam(name = "params", required = true) String params) throws Exception {
        Page<Map<String,Object>> page = new Page<Map<String,Object>>();
        Map paramMap=JsonUtil.fromJson(params, Map.class);
        String outTypeId = (String)paramMap.get("outTypeId");
        ValidUtil.valid("outTypeId", outTypeId, "{'required':true}");
        if(outTypeId.equals("CHINANET")) {
            page = FitApClient.queryChinanetDevInfoListByParam(paramMap);
        }
        else if(outTypeId.equals("AWIFI")) {
            page = DeviceQueryClient.queryHotFitapInfoListByParam(paramMap);
        }
        else {
            throw new ValidException("E2301105", MessageUtil.getMessage("E2301105",outTypeId));
        }
        return this.successMsg(page);
    }
}

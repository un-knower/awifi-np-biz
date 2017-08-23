/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月22日 上午12:52:06
* 创建作者：许小满
* 文件名称：MerchantController.java
* 版本：  v1.0
* 功能：商户相关操作业务层
* 修改记录：
*/
package com.awifi.np.biz.devicebindsrv.merchant.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.devicebindsrv.merchant.service.MerchantService;
import com.awifi.np.biz.toe.admin.security.user.service.ToeUserService;

@Controller
@RequestMapping(value = "/devbindsrv")
public class MerchantController extends BaseController {

    /**商户服务*/
    @Resource(name = "merchantService")
    private MerchantService merchantService;
    
    /**toe用户服务*/
    @Resource(name = "toeUserService")
    private ToeUserService toeUserService;
    
    /**
     * 通过账号获取商户详情接口
     * @param userName 商户账号（商户手机号），不允许为空，1开头的11位符合手机号码规则的数字，  正则：^(1[0-9]{10})?$
     * @return json
     * @throws Exception 异常
     * @author 许小满  
     * @date 2017年5月22日 上午12:58:58
     */
    @RequestMapping(method = RequestMethod.GET,value = "/merchant/{account}")
    @ResponseBody
    public Map<String,Object> getByUserName(
            @PathVariable(value="account",required=true) String userName//商户账号（商户手机号），不允许为空，1开头的11位符合手机号码规则的数字，  正则：^(1[0-9]{10})?$
    ) throws Exception{
        //参数校验
        ValidUtil.valid("商户手机号[account]", userName, "{'required':true, 'regex':'"+RegexConstants.CELLPHONE+"'}");//商户账号（商户手机号），不允许为空，1开头的11位符合手机号码规则的数字，  正则：^(1[0-9]{10})?$
        //通过账号获取商户id
        Long merchantId = toeUserService.getMerIdByUserName(userName);
        if(merchantId == null){
            return this.successMsg();
        }
        //调用数据中心接口,获取商户信息
        Merchant merchant = merchantService.getById(merchantId);
        merchant.setAccount(userName);//商户账号
        return this.successMsg(merchant);
    }
    
}

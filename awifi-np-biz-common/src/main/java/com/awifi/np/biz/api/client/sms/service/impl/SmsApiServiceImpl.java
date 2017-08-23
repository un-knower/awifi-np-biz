/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月13日 下午4:17:24
* 创建作者：许小满
* 文件名称：SmsApiServiceImpl.java
* 版本：  v1.0
* 功能：发送短信 api接口业务层接口实现类
* 修改记录：
*/
package com.awifi.np.biz.api.client.sms.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.auth.http.util.AuthHttpRequest;
import com.awifi.np.biz.api.client.sms.service.SmsApiService;
import com.awifi.np.biz.common.base.service.BaseService;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;

@Service("smsApiService")
public class SmsApiServiceImpl extends BaseService implements SmsApiService {

    /**
     * 发送短信
     * @param cellphone 手机号
     * @param msg 短信内容
     * @return resultMap
     * @author 许小满  
     * @date 2017年5月13日 下午4:24:34
     */
    public Map<String, Object> sendMsg(String cellphone, String msg){
        String interfaceUrl = SysConfigUtil.getParamValue("sms_gateway_sendmsg_url");//接口url
        Map<String, String> paramMap = new HashMap<String,String>(2);//参数
        paramMap.put("mobile", cellphone);//手机号
        paramMap.put("msg", msg);//短信内容
        String interfaceParam = HttpRequest.getParams(paramMap);//接口参数
        return AuthHttpRequest.sendGetRequest(interfaceUrl, interfaceParam);
    }
}

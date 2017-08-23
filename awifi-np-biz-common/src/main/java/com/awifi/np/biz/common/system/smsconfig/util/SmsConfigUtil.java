/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月13日 下午5:37:16
* 创建作者：许小满
* 文件名称：SmsConfigUtil.java
* 版本：  v1.0
* 功能：短信配置工具类
* 修改记录：
*/
package com.awifi.np.biz.common.system.smsconfig.util;

import com.awifi.np.biz.common.system.smsconfig.model.SmsConfig;
import com.awifi.np.biz.common.system.smsconfig.service.SmsConfigService;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.BeanUtil;

public class SmsConfigUtil {

    /** 短信配置 业务层 */
    private static SmsConfigService smsConfigService;
    
    /**
     * 获取商户的短信配置信息
     * @param merchantId 商户id
     * @return 短信配置
     * @author 许小满  
     * @date 2017年5月13日 下午4:07:18
     */
    public static SmsConfig getSmsConfigWithDefault(Long merchantId) {
        SmsConfig smsConfig = getSmsConfigService().getByCustomerId(merchantId);
        if(smsConfig == null){//已经配置
            smsConfig = new SmsConfig();
            smsConfig.setCodeLength(Integer.parseInt(SysConfigUtil.getParamValue("sms_authcode_default_length")));//短信验证码默认长度
            smsConfig.setSmsContent(SysConfigUtil.getParamValue("sms_authcode_default_content"));//短信验证码默认内容
        }
        return smsConfig;
    }
    
    /**
     * 获取发送短信 api接口实例
     * @return 背景图api接口实例
     * @author 许小满  
     * @date 2017年5月13日 上午11:59:13
     */
    private static SmsConfigService getSmsConfigService(){
        if(smsConfigService == null){
            smsConfigService = (SmsConfigService)BeanUtil.getBean("smsConfigService");
        }
        return smsConfigService;
    }
    
}

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月13日 下午4:16:30
* 创建作者：许小满
* 文件名称：SmsApiService.java
* 版本：  v1.0
* 功能：发送短信 api接口业务层接口
* 修改记录：
*/
package com.awifi.np.biz.api.client.sms.service;

import java.util.Map;

public interface SmsApiService {

    /**
     * 发送短信
     * @param cellphone 手机号
     * @param msg 短信内容
     * @return resultMap
     * @author 许小满  
     * @date 2017年5月13日 下午4:24:34
     */
    Map<String, Object> sendMsg(String cellphone, String msg);
    
}

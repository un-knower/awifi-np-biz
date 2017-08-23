/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月1日 上午12:36:18
* 创建作者：许小满
* 文件名称：SmsService.java
* 版本：  v1.0
* 功能：短信记录-业务层接口
* 修改记录：
*/
package com.awifi.np.biz.toe.admin.system.sms.service;

public interface SmsService {

    /**
     * 保存短信记录
     * @param merchantId 商户id
     * @param cellphone 手机号
     * @param content 短信内容
     * @author 许小满  
     * @date 2017年6月1日 上午12:42:03
     */
    void add(Long merchantId, String cellphone, String content);
    
}

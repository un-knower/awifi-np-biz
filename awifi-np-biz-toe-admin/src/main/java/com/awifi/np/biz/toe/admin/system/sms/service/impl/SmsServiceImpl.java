/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月1日 上午12:36:40
* 创建作者：许小满
* 文件名称：SmsServiceImpl.java
* 版本：  v1.0
* 功能：短信记录-业务层接口实现类
* 修改记录：
*/
package com.awifi.np.biz.toe.admin.system.sms.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.common.base.service.BaseService;
import com.awifi.np.biz.toe.admin.system.sms.dao.SmsDao;
import com.awifi.np.biz.toe.admin.system.sms.service.SmsService;

@Service("smsService")
public class SmsServiceImpl extends BaseService implements SmsService {

    /** 短信记录-模型层 */
    @Resource( name="smsDao" )
    private SmsDao smsDao;
    
    /**
     * 保存短信记录
     * @param merchantId 商户id
     * @param cellphone 手机号
     * @param content 短信内容
     * @author 许小满  
     * @date 2017年6月1日 上午12:42:03
     */
    public void add(Long merchantId, String cellphone, String content){
        smsDao.add(merchantId, cellphone, content);
    }
    
}

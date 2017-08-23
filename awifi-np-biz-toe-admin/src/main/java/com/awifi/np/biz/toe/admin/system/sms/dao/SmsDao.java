/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月1日 上午12:37:41
* 创建作者：许小满
* 文件名称：SmsDao.java
* 版本：  v1.0
* 功能：短信记录-模型层
* 修改记录：
*/
package com.awifi.np.biz.toe.admin.system.sms.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

@Service("smsDao")
public interface SmsDao {

    /**
     * 保存短信记录
     * @param merchantId 商户id
     * @param cellphone 手机号
     * @param content 短信内容
     * @author 许小满  
     * @date 2017年6月1日 上午12:42:03
     */
    @Insert("insert into toe_sms(cellphone,content,fk_customer_id,create_date) values(#{cellphone},#{content},#{merchantId},unix_timestamp(now()))")
    void add(@Param("merchantId")Long merchantId, @Param("cellphone")String cellphone, @Param("content")String content);
    
}

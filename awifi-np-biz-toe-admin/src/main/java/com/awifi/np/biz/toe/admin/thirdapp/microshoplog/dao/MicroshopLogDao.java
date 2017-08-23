/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月16日 下午2:31:04
* 创建作者：许小满
* 文件名称：MicroshopLogDao.java
* 版本：  v1.0
* 功能：微旺铺认证日志--模型层
* 修改记录：
*/
package com.awifi.np.biz.toe.admin.thirdapp.microshoplog.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

@Service("microshopLogDao")
public interface MicroshopLogDao {

    /**
     * 保存微旺铺日志
     * @param redisKey redisKey
     * @param redisParams redis参数
     * @param shopId shopId
     * @param shopName shopName
     * @param forceAttention 是否强制关注
     * @param authType 认证类型
     * @param userPhone 手机号
     * @param userMac 用户mac
     * @param callWechatParameter 唤起微信参数
     * @param value 唤起微信返回数据
     * @param result 流程编码
     * @param customerId 客户id
     * @param cascadeLabel 客户层级
     * @author kangyanxiang 
     * @date 2016年7月12日 下午2:17:25
     */
    @Insert("insert into toe_app_micro_shop_log(redis_key,redis_value,shop_id,shop_name,force_attention,component_type,user_phone,user_mac,interface_param,interface_return_value,result,fk_customer_id,cascade_label,create_date) "
            + "value(#{redisKey},#{redisParams},#{shopId},#{shopName},#{forceAttention},#{authType},#{userPhone},#{userMac},#{callWechatParameter},#{value},#{result},#{customerId},#{cascadeLabel},unix_timestamp(now()))")
    void saveMicroshopLog(@Param("redisKey")String redisKey,@Param("redisParams")String redisParams,@Param("shopId")String shopId,@Param("shopName")String shopName,@Param("forceAttention")Integer forceAttention,
            @Param("authType")String authType,@Param("userPhone")String userPhone,@Param("userMac")String userMac,@Param("callWechatParameter")String callWechatParameter,@Param("value")String value,
            @Param("result")String result,@Param("customerId")String customerId,@Param("cascadeLabel")String cascadeLabel);
    
}

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月14日 下午9:24:01
* 创建作者：许小满
* 文件名称：IVRDao.java
* 版本：  v1.0
* 功能：IVR 语音认证
* 修改记录：
*/
package com.awifi.np.biz.toe.admin.auth.ivr.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

@Service("ivrDao")
public interface IVRDao {

    /**
     * 根据rediskey查找结果
     * @param redisKey 缓存key
     * @return result
     * @author ZhouYing 
     * @date 2016年7月26日 上午10:50:50
     */
    @Select("select result from toe_ivr_log where redis_key=#{redisKey} order by pk_id desc limit 1")
    int getResult(String redisKey);

    /**
     * IVR语音认证-保存参数及日志
     * @param redisKey 缓存key
     * @param redisValue 缓存value
     * @param phoneNumber 手机号
     * @param userMac 用户MAC
     * @param result 执行结果
     * @param merchantId 商户id
     * @param cascadeLabel  商户层级
     * @author kangyanxiang 
     * @date 2016年7月28日 上午10:13:20
     */
    @Insert("insert into toe_ivr_log (redis_key,redis_value,user_phone,user_mac,result,fk_customer_id,cascade_label,create_date) "
            + "value(#{redisKey},#{redisValue},#{phoneNumber},#{userMac},#{result},#{merchantId},#{cascadeLabel},unix_timestamp(now()))")
    void save(@Param("redisKey")String redisKey,@Param("redisValue")String redisValue,@Param("phoneNumber")String phoneNumber,@Param("userMac")String userMac,
            @Param("result")Integer result,@Param("merchantId")Long merchantId,@Param("cascadeLabel")String cascadeLabel);
    
    /**
     * 更新result
     * @param redisKey redisKey
     * @param redisValue redisValue
     * @param result 结果
     * @author ZhouYing 
     * @date 2016年7月26日 下午2:22:26
     */
    @Update("update toe_ivr_log set result=#{result},redis_value=#{redisValue} where redis_key=#{redisKey} order by pk_id desc limit 1")
    void updateResult(@Param("redisKey") String redisKey, @Param("redisValue") String redisValue,@Param("result") int result);
}

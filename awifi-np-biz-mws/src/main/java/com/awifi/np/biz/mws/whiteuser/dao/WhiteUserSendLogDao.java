/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月11日 下午5:03:06
* 创建作者：王冬冬
* 文件名称：WhiteUserDao.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mws.whiteuser.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import com.awifi.np.biz.mws.whiteuser.model.StationMerchantNamelistSendlog;

@Repository
public interface WhiteUserSendLogDao {

    /**
     * 白名单下发列表
     * @param deviceId 设备id
     * @return List<WhiteUser>
     * @author 王冬冬  
     * @date 2017年4月11日 下午5:16:04
     */
    @Results(value = {
            @Result(property = "id", column = "Id", javaType =Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "accountId", column = "AccountId", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "merchantId", column = "MerchantId", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "deviceId", column = "DeviceId", javaType =String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "userMac", column = "UserMac", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "status", column = "Status", javaType = Byte.class, jdbcType = JdbcType.TINYINT),
            @Result(property = "failReason", column = "FailReason", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "flg", column = "Flg", javaType =Byte.class, jdbcType = JdbcType.TINYINT),
            @Result(property = "createTime", column = "CreateTime", javaType = Integer.class, jdbcType = JdbcType.INTEGER)
            })
    @Select("select Id, AccountId, MerchantId, DeviceId, UserMac, Status, FailReason, Flg, CreateTime from station_merchant_namelist_sendlog where DeviceId = #{deviceId}")
    List<StationMerchantNamelistSendlog> findByDevId(String deviceId);

    /**
     * 保存白名单下发
     * @param record 白名单下发日志
     * @author 王冬冬  
     * @date 2017年4月26日 下午1:20:24
     */
    @Insert("insert into station_merchant_namelist_sendlog (Id, AccountId, MerchantId, DeviceId, UserMac, Status, FailReason, Flg, CreateTime, TaskId) values (#{id,jdbcType=INTEGER}, #{accountId,jdbcType=INTEGER}, #{merchantId,jdbcType=BIGINT}, "
            + "#{deviceId,jdbcType=INTEGER}, #{userMac,jdbcType=VARCHAR}, #{status,jdbcType=TINYINT}, #{failReason,jdbcType=VARCHAR}, #{flg,jdbcType=TINYINT}, #{createTime,jdbcType=INTEGER}, #{taskId,jdbcType=VARCHAR})")
    void insert(StationMerchantNamelistSendlog record);

    /**
     * @param record 白名单下发日志
     * @author 王冬冬  
     * @date 2017年4月26日 下午1:20:54
     */
    @Update("update station_merchant_namelist_sendlog set AccountId = #{accountId,jdbcType=INTEGER}, MerchantId = #{merchantId,jdbcType=BIGINT},DeviceId = #{deviceId,jdbcType=INTEGER},UserMac = #{userMac,jdbcType=VARCHAR},Status = #{status,jdbcType=TINYINT},"
            + "FailReason = #{failReason,jdbcType=VARCHAR},Flg = #{flg,jdbcType=TINYINT},CreateTime = #{createTime,jdbcType=INTEGER} where Id = #{id,jdbcType=INTEGER}")
    void updateByPrimaryKey(StationMerchantNamelistSendlog record);
}

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年5月11日 上午11:27:21
 * 创建作者：尤小平
 * 文件名称：WifiRecordDao.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
/**
 *
 */
package com.awifi.np.biz.timebuysrv.web.module.time.dao;

import com.awifi.np.biz.timebuysrv.web.module.time.dao.sql.WifiRecordSql;
import com.awifi.np.biz.timebuysrv.web.module.time.model.WifiRecord;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface WifiRecordDao {
    /**
     * 插入.
     * 
     * @param record WifiRecord
     * @return int
     * @author 尤小平
     * @date 2017年5月11日 下午9:06:22
     */
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @InsertProvider(type = WifiRecordSql.class, method = "insert")
    int insert(WifiRecord record);

    /**
     * 根据id查询.
     * 
     * @param id id
     * @return WifiRecord
     * @author 尤小平  
     * @date 2017年5月11日 下午9:06:39
     */
    @Results(value = { @Result(property = "id", column = "id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "userId", column = "user_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "merchantId", column = "merchant_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "portalUrl", column = "portal_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "telphone", column = "telphone", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "deviceId", column = "device_id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "devType", column = "dev_type", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "token", column = "token", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "tokenDate", column = "token_date", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "wifiUrl", column = "wifi_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "wifiResult", column = "wifi_result", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "wifiDate", column = "wifi_date", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "errorInfo", column = "error_info", javaType = String.class, jdbcType = JdbcType.VARCHAR) })
    @Select("select id,user_id,merchant_id,portal_url,telphone,device_id,dev_type,token,token_date,wifi_url,wifi_result,wifi_date,error_info from wifi_record where id=#{id}")
    WifiRecord selectByPrimaryKey(@Param(value = "id") Long id);

    /**
     * 根据条件查询.
     * 
     * @param params params
     * @return WifiRecord
     * @author 尤小平  
     * @date 2017年5月12日 下午4:18:15
     */
    @Results(value = { @Result(property = "id", column = "id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "userId", column = "user_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "merchantId", column = "merchant_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "portalUrl", column = "portal_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "telphone", column = "telphone", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "deviceId", column = "device_id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "devType", column = "dev_type", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "token", column = "token", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "tokenDate", column = "token_date", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "wifiUrl", column = "wifi_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "wifiResult", column = "wifi_result", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "wifiDate", column = "wifi_date", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "errorInfo", column = "error_info", javaType = String.class, jdbcType = JdbcType.VARCHAR) })
    @Select("select id,user_id,merchant_id,portal_url,telphone,device_id,dev_type,token,token_date,wifi_url,wifi_result,wifi_date,error_info from wifi_record where device_id=#{deviceId} and token=#{token} order by token_date desc limit 1 ")
    WifiRecord selectByDevIdAndToken(Map<String, Object> params);

    /**
     * 更新.
     * 
     * @param record WifiRecord
     * @return int
     * @author 尤小平  
     * @date 2017年5月11日 下午9:06:43
     */
    @UpdateProvider(type = WifiRecordSql.class, method = "update")
    int updateByPrimaryKey(WifiRecord record);
}

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月8日 下午3:13:26
* 创建作者：王冬冬
* 文件名称：MsMerchantDeviceDao.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.tob.member.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.tob.member.dao.sql.MsMerchantDeviceSql;
import com.awifi.np.biz.tob.member.model.WiiDeviceExtend;


@Service("msMerchantDeviceDao")
public interface MsMerchantDeviceDao {

    /**
     * 
     * @param map 参数
     * @return int
     * @author 王冬冬  
     * @date 2017年5月8日 下午3:18:08
     */
    @SelectProvider(type=MsMerchantDeviceSql.class, method="updateSwitchStatusAll")
    void updateSwitchStatusAll(Map<String, Object> map);
    
//    @Results(value = {
//            @Result(property = "id", column = "id", javaType =Integer.class, jdbcType = JdbcType.INTEGER),
//            @Result(property = "device_id", column = "device_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
//            @Result(property = "devId", column = "devId", javaType = String.class, jdbcType = JdbcType.VARCHAR),
//            @Result(property = "code", column = "code", javaType =String.class, jdbcType = JdbcType.VARCHAR),
//            @Result(property = "status", column = "status", javaType = Byte.class, jdbcType = JdbcType.TINYINT),
//            @Result(property = "merid", column = "merid", javaType =Long.class, jdbcType = JdbcType.BIGINT),
//            @Result(property = "remark", column = "remark", javaType = String.class, jdbcType = JdbcType.VARCHAR),
//            @Result(property = "modify_date", column = "modify_date", javaType = Date.class, jdbcType = JdbcType.TIMESTAMP),
//            @Result(property = "modify_operator", column = "modify_operator", javaType =String.class, jdbcType = JdbcType.VARCHAR),
//            @Result(property = "modify_type", column = "modify_type", javaType = Byte.class, jdbcType = JdbcType.TINYINT)
//            })
//    @Select("select count(id) from station_merchant_namelist where Mobile=#{cellphone} and merchantId=#{merchantId}")
//    List<WiiDeviceExtend> getAllAntiRobberCodes();

    /**
     * 查询防蹭网码
     * @param maxSize
     * @return
     * @author 王冬冬  
     * @date 2017年6月19日 下午3:31:01
     */
    @Select("select devid,code from wii_device_extend where status=1 limit #{maxSize}")
    List<Map> addToCache(@Param("maxSize") Integer maxSize);
}

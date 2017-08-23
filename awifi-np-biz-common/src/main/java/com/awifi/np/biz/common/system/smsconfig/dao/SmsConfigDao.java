/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月3日 下午4:13:20
* 创建作者：周颖
* 文件名称：SmsConfigDao.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.common.system.smsconfig.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.common.system.smsconfig.dao.sql.SmsConfigSql;
import com.awifi.np.biz.common.system.smsconfig.model.SmsConfig;

@Service("smsConfigDao")
public interface SmsConfigDao {
    
    /**
     * 短信配置总数
     * @param merchantId 商户id
     * @param merchantIds 商户ids
     * @return 总数
     * @author 周颖  
     * @date 2017年5月4日 上午8:59:32
     */
    @SelectProvider(type=SmsConfigSql.class,method="getCountByParam")
    int getCountByParam(@Param("merchantId")Long merchantId,@Param("merchantIds") Long[] merchantIds);

    /**
     * 短信配置列表
     * @param merchantId 商户id
     * @param merchantIds 商户ids
     * @param begin 开始行数
     * @param pageSize 页面大小
     * @return 列表
     * @author 周颖  
     * @date 2017年5月4日 上午9:32:20
     */
    @Results(value = {
            @Result(property = "id", column = "id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "smsContent", column = "sms_content", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "codeLength", column = "code_length", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "merchantId", column = "merchant_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "createDate", column = "create_date", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @SelectProvider(type=SmsConfigSql.class,method="getListByParam")
    List<Map<String, Object>> getListByParam(@Param("merchantId")Long merchantId,@Param("merchantIds") Long[] merchantIds,
            @Param("begin")Integer begin,@Param("pageSize") Integer pageSize);

    /**
     * 商户配置短信数量
     * @param merchantId 商户id
     * @return 总数
     * @author 周颖  
     * @date 2017年5月3日 下午4:28:55
     */
    @Select("select count(id) from np_biz_sms_code_config where merchant_id=#{merchantId}")
    int getNumByMerchantId(@Param("merchantId")Long merchantId);

    /**
     * 新增短信配置
     * @param merchantId 商户id
     * @param smsContent 短信内容
     * @param codeLength 验证码长度
     * @author 周颖  
     * @date 2017年5月3日 下午4:32:29
     */
    @Insert("insert into np_biz_sms_code_config(sms_content,code_length,merchant_id) values(#{smsContent},#{codeLength},#{merchantId})")
    void add(@Param("merchantId")Long merchantId,@Param("smsContent") String smsContent,@Param("codeLength") Integer codeLength);

    /**
     * 编辑短信配置
     * @param id 配置id
     * @param smsContent 短信内容
     * @param codeLength 验证码长度
     * @author 周颖  
     * @date 2017年5月3日 下午4:39:29
     */
    @Update("update np_biz_sms_code_config set sms_content=#{smsContent},code_length=#{codeLength} where id=#{id}")
    void update(@Param("id")Long id,@Param("smsContent") String smsContent,@Param("codeLength") Integer codeLength);

    /**
     * 短信配置详情
     * @param id 主键id
     * @return 详情
     * @author 周颖  
     * @date 2017年5月3日 下午4:48:53
     */
    @Results(value = {
            @Result(property = "smsContent", column = "sms_content", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "codeLength", column = "code_length", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "merchantId", column = "merchant_id", javaType = Long.class, jdbcType = JdbcType.BIGINT)})
    @Select("select id,sms_content,code_length,merchant_id from np_biz_sms_code_config where id=#{id}")
    Map<String, Object> getById(@Param("id")Long id);
    
    /**
     * 获取客户的短信配置
     * @param merchantId 商户id
     * @return 短信配置信息
     * @author ZhouYing 
     * @date 2016年12月6日 上午11:07:33
     */
    @Select("select sms_content,code_length from np_biz_sms_code_config where merchant_id=#{merchantId} limit 1")
    @Results(value = {
            @Result(property = "smsContent", column = "sms_content", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "codeLength", column = "code_length", javaType = Integer.class, jdbcType = JdbcType.INTEGER)})
    SmsConfig getByCustomerId(Long merchantId);

    /**
     * 逻辑删除短信配置
     * @param id 主键id
     * @author 周颖  
     * @date 2017年5月4日 上午10:59:22
     */
    @Update("delete from np_biz_sms_code_config where id=#{id}")
    void delete(@Param("id")Long id);
}

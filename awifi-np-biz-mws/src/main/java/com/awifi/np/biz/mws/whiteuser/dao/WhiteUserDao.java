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
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import com.awifi.np.biz.mws.whiteuser.dao.sql.WhiteUserSql;
import com.awifi.np.biz.mws.whiteuser.model.WhiteUser;

@Repository
public interface WhiteUserDao {

    /**
     * 白名单列表
     * @param merchantId 商户id
     * @param keywords 关键词
     * @param begin 起始行
     * @param pageSize 页面大小
     * @return List<WhiteUser>
     * @author 王冬冬  
     * @date 2017年4月11日 下午5:16:04
     */
    @Results(value = {
            @Result(property = "id", column = "id", javaType =Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "cellPhone", column = "Mobile", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "mac", column = "mac", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "merchantId", column = "merchantId", javaType =Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "createDate", column = "CreateTime", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @SelectProvider(type=WhiteUserSql.class,method="getListByParam")
    List<WhiteUser> getListByParam(@Param("merchantId") Long merchantId,@Param("keywords") String keywords,@Param("begin")Integer begin,@Param("pageSize") Integer pageSize);
    /**
     * 白名单总条数
     * @param merchantId 商户id
     * @param keywords 关键词
     * @return int
     * @author 王冬冬  
     * @date 2017年4月11日 下午5:18:48
     */
    @SelectProvider(type=WhiteUserSql.class,method="getCountByParam")
    int getCountByParam(@Param("merchantId") Long merchantId,@Param("keywords") String keywords);
    /**
     * @param merchantId 商户id
     * @param cellphone 手机号
     * @return int
     * @author 王冬冬  
     * @date 2017年4月21日 上午10:21:47
     */
    @Select("select count(id) from station_merchant_namelist where status=0 and Type=1 and Mobile=#{cellphone} and merchantId=#{merchantId}")
    int isMobileExist(@Param("merchantId") Long merchantId,@Param("cellphone") String cellphone);

    /**
     * @param whiteUser 白名单
     * @author 王冬冬  
     * @date 2017年4月21日 下午4:47:01
     */
    @Insert("insert into station_merchant_namelist(AccountId,MerchantId,Userid,mobile,mac,addmac,status,isissued,createtime,type) values(#{accountId},#{merchantId},#{userId},#{cellPhone},#{mac},null,0,0,unix_timestamp(now()),1)")
    void add(WhiteUser whiteUser);

    /**
     * @param id 主键id
     * @author 王冬冬  
     * @date 2017年4月21日 下午4:47:04
     */
    @Update("update station_merchant_namelist set status=9 where id=#{id}")
    void delete(@Param("id") Integer id);

    /**
     * @param idArr 多个主键id
     * @author 王冬冬  
     * @date 2017年4月21日 下午4:47:10
     */
    @SelectProvider(type=WhiteUserSql.class,method="batchDelete")
    void batchDelete(@Param("idArr") Long[] idArr);
	/**
	 * @param merchantId 商户id
	 * @param mac mac地址
	 * @return int
	 * @author 王冬冬  
	 * @date 2017年4月25日 上午9:55:45
	 */
    @Select("select count(id) from station_merchant_namelist where status=0 and Type=1 and merchantId=#{merchantId} and mac=#{mac} ")
    int isMacExist(@Param("merchantId") Long merchantId,@Param("mac") String mac);
    
    
    /**
     * 根据ids查询白名单
     * @param idArr 多个id
     * @return List<WhiteUser>
     * @author 王冬冬  
     * @date 2017年4月11日 下午5:16:04
     */
    @Results(value = {
            @Result(property = "id", column = "id", javaType =Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "cellPhone", column = "Mobile", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "mac", column = "mac", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "merchantId", column = "merchantId", javaType =Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "createDate", column = "CreateTime", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @SelectProvider(type=WhiteUserSql.class,method="getListByIds")
    List<WhiteUser> getListByIds(@Param("idArr") Long[] idArr);
    
    /**
     * 白名单总条数
     * @param merchantIds 商户id
     * @param keywords 关键词
     * @return int
     * @author 王冬冬  
     * @date 2017年4月11日 下午5:18:48
     */
    @SelectProvider(type=WhiteUserSql.class,method="getCountByParams")
    int getCountByParams(@Param("merchantIds") Long[] merchantIds, @Param("keywords") String keywords);
    
    /**
     * 白名单列表(多个商户)
     * @param merchantIds 商户id
     * @param keywords 关键词
     * @param begin 起始行
     * @param pageSize 页面大小
     * @return List<WhiteUser>
     * @author 王冬冬  
     * @date 2017年4月11日 下午5:16:04
     */
    @Results(value = {
            @Result(property = "id", column = "id", javaType =Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "cellPhone", column = "Mobile", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "mac", column = "mac", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "merchantId", column = "merchantId", javaType =Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "createDate", column = "CreateTime", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @SelectProvider(type=WhiteUserSql.class,method="getListByParams")
    List<WhiteUser> getListByParams(@Param("merchantIds") Long[] merchantIds, @Param("keywords") String keywords,@Param("begin")Integer begin,@Param("pageSize") Integer pageSize);
}

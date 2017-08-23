/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月12日 下午2:29:55
* 创建作者：尤小平
* 文件名称：StationAppDao.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mws.merchant.app.dao;

import com.awifi.np.biz.mws.merchant.app.dao.sql.StationAppSql;
import com.awifi.np.biz.mws.merchant.app.model.StationApp;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "stationAppDao")
public interface StationAppDao {
    /**
     * 查询商户已发布和已配置的应用.
     * 
     * @param merchantId 商户id
     * @return List<StationApp>
     * @author 尤小平
     * @date 2017年6月12日 下午4:15:21
     */
    @Results(value = { @Result(property = "id", column = "Id", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "appId", column = "AppId", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "appSecret", column = "AppSecret", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "appName", column = "AppName", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "grantType", column = "GrantType", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "developCom", column = "DevelopCom", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "IndustryCode", column = "IndustryCode", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "description", column = "Description", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "price", column = "Price", javaType = Double.class, jdbcType = JdbcType.DOUBLE),
            @Result(property = "appCode", column = "AppCode", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "appIcon", column = "AppIcon", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "redirectUrl", column = "RedirectUrl", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "linkUrl", column = "LinkUrl", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "ownerUserId", column = "OwnerUserId", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "ownerEmail", column = "OwnerEmail", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "ownerName", column = "OwnerName", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "createTime", column = "CreateTime", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "status", column = "Status", javaType = Short.class, jdbcType = JdbcType.TINYINT),
            @Result(property = "needConfiguration", column = "NeedConfiguration", javaType = Short.class, jdbcType = JdbcType.TINYINT),
            @Result(property = "configUrl", column = "ConfigUrl", javaType = String.class, jdbcType = JdbcType.VARCHAR) })
    @Select("select b.* from station_app_merchant_relation a " +
            "left join station_app b on a.AppId=b.AppId " +
            "where a.Status=1 and b.Status=1 " +
            "and a.MerchantId=#{merchantId}")
    List<StationApp> getAppListByMerchantId(@Param(value = "merchantId") Long merchantId);

    /**
     * 根据主键查询应用.
     * 
     * @param id 主键
     * @return StationApp
     * @author 尤小平  
     * @date 2017年6月13日 下午4:00:55
     */
    @Results(value = { @Result(property = "id", column = "Id", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "appId", column = "AppId", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "appSecret", column = "AppSecret", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "appName", column = "AppName", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "grantType", column = "GrantType", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "developCom", column = "DevelopCom", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "IndustryCode", column = "IndustryCode", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "description", column = "Description", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "price", column = "Price", javaType = Double.class, jdbcType = JdbcType.DOUBLE),
            @Result(property = "appCode", column = "AppCode", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "appIcon", column = "AppIcon", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "redirectUrl", column = "RedirectUrl", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "linkUrl", column = "LinkUrl", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "ownerUserId", column = "OwnerUserId", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "ownerEmail", column = "OwnerEmail", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "ownerName", column = "OwnerName", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "createTime", column = "CreateTime", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "status", column = "Status", javaType = Short.class, jdbcType = JdbcType.TINYINT),
            @Result(property = "needConfiguration", column = "NeedConfiguration", javaType = Short.class, jdbcType = JdbcType.TINYINT),
            @Result(property = "configUrl", column = "ConfigUrl", javaType = String.class, jdbcType = JdbcType.VARCHAR) })
    @Select("select * from station_app where Id = #{id}")
    StationApp selectByPrimaryKey(Integer id);

    /**
     * 删除商户下某类型的所有应用.
     * 
     * @param record StationApp
     * @return 删除条数
     * @author 尤小平  
     * @date 2017年6月13日 下午4:01:39
     */
    @Delete("delete from station_app_merchant_relation " +
            "where AppId in (select distinct Id from station_app where GrantType=#{grantType}) " +
            "and MerchantId=#{merchantId}")
    int deleteRelationsByParam(StationApp record);

    /**
     * 根据授权类型查询商户的应用.
     * 
     * @param record StationApp
     * @return List<StationApp>
     * @author 尤小平  
     * @date 2017年6月13日 下午4:02:24
     */
    @Results(value = { @Result(property = "id", column = "Id", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "appId", column = "AppId", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "appSecret", column = "AppSecret", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "appName", column = "AppName", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "grantType", column = "GrantType", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "IndustryCode", column = "IndustryCode", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "linkUrl", column = "LinkUrl", javaType = String.class, jdbcType = JdbcType.VARCHAR) })
    @SelectProvider(type = StationAppSql.class, method = "selectRelationsByParam")
    List<StationApp> selectRelationsByParam(StationApp record);

    /**
     * 查询应用商城应用列表（商户未获取的应用列表）.
     * 
     * @param merchantId 商户id
     * @return List<StationApp>
     * @author 尤小平  
     * @date 2017年6月13日 下午4:03:16
     */
    @Results(value = { @Result(property = "id", column = "Id", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "appId", column = "AppId", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "appSecret", column = "AppSecret", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "appName", column = "AppName", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "grantType", column = "GrantType", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "developCom", column = "DevelopCom", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "IndustryCode", column = "IndustryCode", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "description", column = "Description", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "price", column = "Price", javaType = Double.class, jdbcType = JdbcType.DOUBLE),
            @Result(property = "appCode", column = "AppCode", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "appIcon", column = "AppIcon", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "redirectUrl", column = "RedirectUrl", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "linkUrl", column = "LinkUrl", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "ownerUserId", column = "OwnerUserId", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "ownerEmail", column = "OwnerEmail", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "ownerName", column = "OwnerName", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "createTime", column = "CreateTime", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "status", column = "Status", javaType = Short.class, jdbcType = JdbcType.TINYINT),
            @Result(property = "needConfiguration", column = "NeedConfiguration", javaType = Short.class, jdbcType = JdbcType.TINYINT),
            @Result(property = "configUrl", column = "ConfigUrl", javaType = String.class, jdbcType = JdbcType.VARCHAR) })
    @Select(value = "select * from station_app " +
            "where Status=1 " +
            "and Id not in (select AppId from station_app_merchant_relation where MerchantId=#{merchantId})")
    List<StationApp> getMerchantAppStoreList(@Param(value = "merchantId") Long merchantId);

    /**
     * 查询应用商城应用列表（商户已购应用列表）.
     * 
     * @param merchantId 商户id
     * @return List<StationApp>
     * @author 尤小平  
     * @date 2017年6月13日 下午4:03:31
     */
    @Results(value = { @Result(property = "id", column = "Id", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "appId", column = "AppId", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "appSecret", column = "AppSecret", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "appName", column = "AppName", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "grantType", column = "GrantType", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "developCom", column = "DevelopCom", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "IndustryCode", column = "IndustryCode", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "description", column = "Description", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "price", column = "Price", javaType = Double.class, jdbcType = JdbcType.DOUBLE),
            @Result(property = "appCode", column = "AppCode", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "appIcon", column = "AppIcon", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "redirectUrl", column = "RedirectUrl", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "linkUrl", column = "LinkUrl", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "ownerUserId", column = "OwnerUserId", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "ownerEmail", column = "OwnerEmail", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "ownerName", column = "OwnerName", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "createTime", column = "CreateTime", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "status", column = "Status", javaType = Short.class, jdbcType = JdbcType.TINYINT),
            @Result(property = "needConfiguration", column = "NeedConfiguration", javaType = Short.class, jdbcType = JdbcType.TINYINT),
            @Result(property = "configUrl", column = "ConfigUrl", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "relationId", column = "relationId", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "buyStatus", column = "buyStatus", javaType = Byte.class, jdbcType = JdbcType.TINYINT)})
    @Select(value = "select b.*,a.Id as relationId,a.Status as buyStatus " +
            "from station_app_merchant_relation a " +
            "left join station_app b on a.AppId=b.Id " +
            "where b.Status=1 " +
            "and a.MerchantId=#{merchantId}")
    List<StationApp> getMerchantAppBuyList(@Param(value = "merchantId") Long merchantId);
}

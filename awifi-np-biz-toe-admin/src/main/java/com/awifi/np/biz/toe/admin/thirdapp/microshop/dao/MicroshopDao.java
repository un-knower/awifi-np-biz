/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月16日 下午1:33:46
* 创建作者：许小满
* 文件名称：MicroshopDao.java
* 版本：  v1.0
* 功能：微旺铺相关操作--模型层
* 修改记录：
*/
package com.awifi.np.biz.toe.admin.thirdapp.microshop.dao;

import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.toe.admin.thirdapp.microshop.dao.sql.MicroShopSql;
import com.awifi.np.biz.toe.admin.thirdapp.microshop.model.Microshop;

@Service("microshopDao")
public interface MicroshopDao {
    /**
     * 通过客户ID获取微旺铺信息(模式1)
     * @param merchantId 商户id
     * @return 微旺铺信息
     * @author 亢燕翔 
     * @date 2016年3月25日 下午3:07:51
     */
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "appid", column = "app_id", javaType = String.class, jdbcType = JdbcType.CHAR),
            @Result(property = "wwpAppid", column = "wwp_appid", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "wifiKey", column = "wifi_key", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "shopId", column = "shop_id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "shopName", column = "shop_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "spreadModel", column = "spread_model", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "forceAttention", column = "force_attention", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "cascadeLabel", column = "cascade_label", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "relateCustomerId", column = "fk_relate_customer_id", javaType = Long.class, jdbcType = JdbcType.BIGINT)
            })
    @Select("select pk_id,app_id,wwp_appid,wifi_key,shop_id,shop_name,spread_model,force_attention,cascade_label,fk_relate_customer_id from toe_app_micro_shop where fk_customer_id=#{merchantId} and delete_flag=1")
    Microshop getByMerchantId(Long merchantId);
    
    /**
     * 根据商户id获取关联商户id
     * @param merchantId 商户id
     * @param appId appId
     * @return microshop
     * @author 王冬冬  
     * @date 2017年7月12日 下午3:37:45
     */
    @SelectProvider(type=MicroShopSql.class,method="getRelateMerIdByMerId")
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "appid", column = "app_id", javaType = String.class, jdbcType = JdbcType.CHAR),
            @Result(property = "wwpAppid", column = "wwp_appid", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "wifiKey", column = "wifi_key", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "shopId", column = "shop_id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "shopName", column = "shop_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "spreadModel", column = "spread_model", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "forceAttention", column = "force_attention", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "cascadeLabel", column = "cascade_label", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "relateCustomerId", column = "fk_relate_customer_id", javaType = Long.class, jdbcType = JdbcType.BIGINT)
            })
    Microshop getRelateMerIdByMerId(@Param("customerId") Long merchantId, @Param("appId") String appId);


    /**
     * 获取关联配置商户列表数量
     * @param projectId 项目id
     * @param merchantId 商户Id
     * @param relateCustomerId 关联商户Id
     * @param queryMerchantId 查询的商户id
     * @param appId 应用id
     * @return 数量
     * @author 王冬冬  
     * @date 2017年7月12日 下午4:15:36
     */
    @SelectProvider(type=MicroShopSql.class,method="getMerchantCountByParam")
    int getMerchantCountByParam(@Param("projectId") Long projectId,@Param("merchantId") Long merchantId,@Param("relateCustomerId") Long relateCustomerId,@Param("queryMerchantId") Long queryMerchantId, @Param("appId") String appId);

    /**
     * 获取关联配置商户列表
     * @param merchantId 商户id
     * @param appId 应用Id
     * @param relateCustomerId 关联商户id
     * @param projectId 项目id
     * @param pageNo 页数
     * @param pageSize 分页大小
     * @return 列表
     * @author 王冬冬  
     * @param queryMerchantId 查询商户id
     * @date 2017年7月12日 下午4:07:37
     */
    @SelectProvider(type=MicroShopSql.class,method="getMerchantByParam")
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "appid", column = "app_id", javaType = String.class, jdbcType = JdbcType.CHAR),
            @Result(property = "wwpAppid", column = "wwp_appid", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "wifiKey", column = "wifi_key", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "shopId", column = "shop_id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "shopName", column = "shop_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "spreadModel", column = "spread_model", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "forceAttention", column = "force_attention", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "cascadeLabel", column = "cascade_label", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "customerId", column = "fk_customer_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "relateCustomerId", column = "fk_relate_customer_id", javaType = Long.class, jdbcType = JdbcType.BIGINT)
            })
    List<Microshop> getMerchantByParam(@Param("projectId") Long projectId,@Param("merchantId") Long merchantId,@Param("relateCustomerId")  Long relateCustomerId, @Param("queryMerchantId") Long queryMerchantId,@Param("appId") String appId, @Param("pageNo") int pageNo,@Param("pageSize") int pageSize);

    /**
     * 通过客户id查找公众号id
     * @param customerId 当前登录客户id
     * @param appId 第三方id
     * @return  项目id
     * @author 梁聪
     * @date 2016年7月13日 下午3:38:07
     */
    @Select("select pk_id from toe_app_micro_shop where fk_customer_id =#{customerId} and app_id=#{appId} and delete_flag=1")
    Long getIdByParams(@Param("customerId") Long customerId,@Param("appId") String appId);

    /**
     * （公众号表主键id为空时）新增公众号记录
     * @param customerId 当前登录客户id
     * @param cascadelabel 客户层级
     * @param projectId 项目id
     * @param appId 第三方id
     * @author 梁聪
     * @date 2016年7月13日 下午3:38:07
     */
    @Insert("insert into toe_app_micro_shop(app_id,fk_customer_id,cascade_label,fk_project_id,create_date,update_date) values (#{appId},#{customerId},#{cascadelabel},#{projectId},unix_timestamp(now()),unix_timestamp(now()))")
    void addForRelate(@Param("customerId")Long customerId,@Param("cascadelabel") String cascadelabel, @Param("projectId")Long projectId,@Param("appId")String appId);

    /**
     * 应用管理--微托、聚来宝--更新公众号信息
     * @param customerId 当前登录客户id
     * @param relateCustomerId 关联客户id
     * @param appId 第三方id
     * @author 梁聪
     * @date 2016年7月13日 下午3:38:07
     */
    @Update("update toe_app_micro_shop set fk_relate_customer_id=#{relateCustomerId} where fk_customer_id=#{customerId} and app_id=#{appId}")
    void updateForRelate(@Param("customerId") Long customerId,@Param("relateCustomerId") Long relateCustomerId,@Param("appId") String appId);

    /**
     * 应用管理--微托、聚来宝--模式生效接口
     * @param spreadmodel 1 代表 模式一、2 代表 模式二
     * @param id id
     * @author 梁聪
     * @date 2016年7月14日 下午3:38:07
     */
    @Update("update toe_app_micro_shop set spread_model=#{spreadmodel} where pk_id=#{id}")
    void updateSpreadModel(@Param("spreadmodel") int spreadmodel,@Param("id") Long id);

    /**
     * 应用管理--微托、聚来宝—强制关注生效接口
     * @param forceattention 强制关注：-1代表非强制关注、1代表强制关注
     * @param id id
     * @author 梁聪
     * @date 2016年7月17日 下午2:12:11
     */
    @Update("update toe_app_micro_shop set force_attention=#{forceattention} where pk_id=#{id}")
    void updateForceAttention (@Param("forceattention") Integer forceattention,@Param("id") Long id);

    /**
     * 通过商户id[merchantId]、应用id[appId]查询公众号信息
     * @param merchantId 商户id
     * @param appId 应用Id
     * @return map
     * @author 王冬冬  
     * @date 2017年7月13日 下午3:44:45
     */
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "appid", column = "app_id", javaType = String.class, jdbcType = JdbcType.CHAR),
            @Result(property = "wwpAppid", column = "wwp_appid", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "wifiKey", column = "wifi_key", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "shopId", column = "shop_id", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "shopName", column = "shop_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "spreadModel", column = "spread_model", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "forceAttention", column = "force_attention", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "cascadeLabel", column = "cascade_label", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "relateCustomerId", column = "fk_relate_customer_id", javaType = Long.class, jdbcType = JdbcType.BIGINT)
            })
    @Select("select pk_id,shop_name,spread_model,force_attention,fk_relate_customer_id from toe_app_micro_shop where fk_customer_id=#{merchantId} and app_id= #{appId} and delete_flag=1")
    Microshop getByParams(@Param("merchantId") Long merchantId,@Param("appId") String appId);
    
}

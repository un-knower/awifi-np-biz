package com.awifi.np.biz.timebuysrv.web.module.time.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.timebuysrv.merchant.dao.sql.MerchantNewsSql;
import com.awifi.np.biz.timebuysrv.web.module.time.dao.sql.TimePackageSql;
import com.awifi.np.biz.timebuysrv.web.module.time.model.TimePackage;

/**
 * 商品套餐dao
 * 
 * @author 张智威 2017年4月7日 下午4:12:37
 */
@Service(value = "goodsPackageDao")
public interface TimePackageDao {
    /**
     * 根据参数查询商品套餐
     * 
     * @param map
     * @return
     * @author 张智威
     * @date 2017年4月7日 下午4:10:45
     */
    @Results(value = { @Result(property = "id", column = "id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "merchantId", column = "merchant_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "packageType", column = "package_type", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "packageKey", column = "package_key", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "packageValue", column = "package_value", javaType = Float.class, jdbcType = JdbcType.FLOAT),
            @Result(property = "effectDatetime", column = "effect_datetime", javaType = Date.class, jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "expiredDatetime", column = "expired_datetime", javaType = Date.class, jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "remarks", column = "remarks", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "status", column = "status", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "statusDate", column = "status_date", javaType = Date.class, jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "createDate", column = "create_date", javaType = Date.class, jdbcType = JdbcType.TIMESTAMP) })
    @SelectProvider(type = TimePackageSql.class, method = "getListByParam")
    List<TimePackage> queryListByParam(Map<String, Object> map);

    /**
     * 根据id查询套餐信息
     * 
     * @param id
     * @return
     * @author 张智威
     * @date 2017年4月7日 下午4:11:20
     */
    @Results(value = { @Result(property = "id", column = "id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "content", column = "content", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "merid", column = "merid", javaType = Long.class, jdbcType = JdbcType.BIGINT) })
    @Select("select *  from center_pub_merchant_package where id=#{id} and status=1 limit 1")
    TimePackage queryById(Long id);

    /**
     * 添加套餐
     *
     * @param merchantPackage
     * @return
     * @author 张智威
     * @date 2017年4月7日 下午4:11:34
     */
    @Insert(" insert into center_pub_merchant_package   (merchant_id,package_type,package_key,package_value,effect_datetime,expired_datetime,remarks,status,status_date,create_date)"
            + " values (#{merchantId} ,#{packageType} ,#{packageKey},#{packageValue}"
            + ",#{effectDatetime},#{expiredDatetime},#{remarks} ,#{status},#{statusDate}"
            + ",#{createDate})")
    int add(TimePackage timePackage);

    /**
     * 更新套餐
     *
     * @param merchantPackage
     * @return
     * @author 张智威
     * @date 2017年4月7日 下午4:11:46
     */

    @Update(" update center_pub_merchant_package set package_type=#{packageType},"
            + " package_key=#{packageKey},  package_value=#{packageValue},  "
            + " effect_datetime=#{effectDatetime},    expired_datetime=#{expiredDatetime}, "
            + " status=#{status,jdbcType=INTEGER},  status_date=#{statusDate} where id = #{id}")
    int update(TimePackage timePackage);

    /**
     * 删除套餐
     *
     * @param merchantPackage
     * @return
     * @author 张智威
     * @date 2017年4月7日 下午4:11:53
     */
    @Update(" update center_pub_merchant_package set status=9 where id = #{id}")
    int logicDelete(Long id);
    
     /**
     * 根据参数查询个数
     *
     * @param map
     * @return
     * @author 张智威
     * @date 2017年4月7日 下午4:12:11
     */
    @SelectProvider(type = TimePackageSql.class, method = "queryCountByParam") 
     int queryCountByParam(Map<String, Object> map);

}

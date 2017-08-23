/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年4月1日 下午2:12:51
 * 创建作者：尤小平
 * 文件名称：MerchantNewsDao.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.timebuysrv.merchant.dao;

import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.timebuysrv.merchant.dao.sql.MerchantNewsSql;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantNews;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.annotations.Delete;

import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "merchantNewsDao")
public interface MerchantNewsDao {

    /**
     * 插入MerchantNews.
     *
     * @param merchantNews MerchantNews
     * @return int
     * @author 尤小平
     * @date 2017年4月6日 上午9:23:36
     */
    @InsertProvider(type = MerchantNewsSql.class, method = "insert")
    int insert(MerchantNews merchantNews);

    /**
     * 获取MerchantNews列表.
     *
     * @param merchantNews MerchantNews
     * @param page page
     * @return List<MerchantNews>
     * @author 尤小平
     * @date 2017年4月6日 上午9:23:40
     */
    @Results(value = { @Result(property = "id", column = "id", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "content", column = "content", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "merid", column = "merid", javaType = Long.class, jdbcType = JdbcType.BIGINT) })
    @SelectProvider(type = MerchantNewsSql.class, method = "getListByParam")
    List<MerchantNews> getListByParam(@Param("merchantNews") MerchantNews merchantNews,
            @Param("page") Page<MerchantNews> page);

    /**
     * 按主键获取MerchantNews.
     *
     * @param id id
     * @return MerchantNews
     * @author 尤小平
     * @date 2017年4月6日 上午9:23:45
     */
    @Results(value = { @Result(property = "id", column = "id", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "content", column = "content", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "merid", column = "merid", javaType = Long.class, jdbcType = JdbcType.BIGINT) })
    @Select("select id, content, merid from merchant_news where id=#{id} limit 1")
    MerchantNews selectByPrimaryKey(@Param(value = "id") Integer id);

    /**
     * 按主键更新MerchantNews.
     *
     * @param merchantNews MerchantNews
     * @return int
     * @author 尤小平
     * @date 2017年4月6日 上午9:23:50
     */
    @UpdateProvider(type = MerchantNewsSql.class, method = "updateByPrimaryKey")
    int updateByPrimaryKey(MerchantNews merchantNews);

    /**
     * 按主键删除MerchantNews.
     *
     * @param id id
     * @return int
     * @author 尤小平
     * @date 2017年4月6日 上午9:23:55
     */
    @Delete("delete from merchant_news where id = #{id}")
    int deleteByPrimaryKey(Integer id);

    /**
     * 按商户id获取MerchantNews列表.
     *
     * @param merid merid
     * @return List<MerchantNews>
     * @author 尤小平
     * @date 2017年4月6日 上午9:23:45
     */
    @Results(value = { @Result(property = "id", column = "id", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "content", column = "content", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "merid", column = "merid", javaType = Long.class, jdbcType = JdbcType.BIGINT) })
    @Select("select id, content, merid from merchant_news where merid=#{merid}")
    List<MerchantNews> selectListByMerid(@Param(value = "merid") Long merid);
}

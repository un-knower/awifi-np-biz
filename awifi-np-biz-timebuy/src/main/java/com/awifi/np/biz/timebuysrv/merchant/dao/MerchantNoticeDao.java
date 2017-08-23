/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年4月7日 上午11:22:58
 * 创建作者：尤小平
 * 文件名称：MerchantNoticeDao.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.timebuysrv.merchant.dao;

import com.awifi.np.biz.timebuysrv.merchant.dao.sql.MerchantNoticeSql;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantNotice;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.annotations.Delete;

import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "merchantNoticeDao")
public interface MerchantNoticeDao {
    /**
     * 插入商户滚动消息.
     *
     * @param merchantNotice MerchantNotice
     * @return int
     * @author 尤小平
     * @date 2017年4月7日 下午3:51:41
     */
    @InsertProvider(type = MerchantNoticeSql.class, method = "insert")
    int insert(MerchantNotice merchantNotice);

    /**
     * 根据id查询商户滚动消息.
     *
     * @param id id
     * @return MerchantNotice
     * @author 尤小平
     * @date 2017年4月7日 下午3:52:26
     */
    @Results(value = { @Result(property = "id", column = "id", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "slot", column = "slot", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "content", column = "content", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "merid", column = "merid", javaType = Long.class, jdbcType = JdbcType.BIGINT) })
    @Select("select id, slot, content, merid from merchant_notice where id=#{id} limit 1")
    MerchantNotice selectByPrimaryKey(@Param(value = "id") Integer id);

    /**
     * 根据商户id查询商户滚动消息列表.
     *
     * @param merid merid
     * @return List<MerchantNotice>
     * @author 尤小平
     * @date 2017年4月7日 下午3:53:28
     */
    @Results(value = { @Result(property = "id", column = "id", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "slot", column = "slot", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "content", column = "content", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "merid", column = "merid", javaType = Long.class, jdbcType = JdbcType.BIGINT) })
    @Select("select id, slot, content, merid from merchant_notice where merid=#{merid}")
    List<MerchantNotice> selectListByMerid(@Param(value = "merid") Long merid);

    /**
     * 根据id更新商户滚动消息.
     *
     * @param merchantNotice MerchantNotice
     * @return int
     * @author 尤小平
     * @date 2017年4月7日 下午3:53:51
     */
    @UpdateProvider(type = MerchantNoticeSql.class, method = "update")
    int updateByPrimaryKey(MerchantNotice merchantNotice);

    /**
     * 根据id删除商户滚动消息.
     *
     * @param id id
     * @return int
     * @author 尤小平
     * @date 2017年4月7日 下午3:54:11
     */
    @Delete("delete from merchant_notice where id = #{id}")
    int delete(Integer id);

}

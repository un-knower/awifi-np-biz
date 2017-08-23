/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月10日 下午3:04:16
* 创建作者：尤小平
* 文件名称：MerchantPicDao.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.merchant.dao;

import com.awifi.np.biz.timebuysrv.merchant.dao.sql.MerchantPicSql;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantPic;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

@Service(value = "merchantPicDao")
public interface MerchantPicDao {
    /**
     * 插入商户滚动图片.
     * 
     * @param merchantPic MerchantPic
     * @return int
     * @author 尤小平
     * @date 2017年4月10日 下午4:39:59
     */
    @InsertProvider(type = MerchantPicSql.class, method = "insert")
    int insert(MerchantPic merchantPic);

    /**
     * 根据id查询商户滚动图片.
     * 
     * @param id id
     * @return MerchantPic
     * @author 尤小平
     * @date 2017年4月10日 下午4:40:29
     */
    @Results(value = { @Result(property = "id", column = "id", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "slot", column = "slot", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "path", column = "path", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "merid", column = "merid", javaType = Long.class, jdbcType = JdbcType.BIGINT) })
    @Select("select id, slot, path, merid from merchant_pic where id=#{id} limit 1")
    MerchantPic selectByPrimaryKey(@Param(value = "id") Integer id);

    /**
     * 根据商户id查询商户滚动图片列表.
     * 
     * @param merid merid
     * @return List<MerchantPic>
     * @author 尤小平
     * @date 2017年4月10日 下午4:40:58
     */
    @Results(value = { @Result(property = "id", column = "id", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "slot", column = "slot", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "path", column = "path", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "merid", column = "merid", javaType = Long.class, jdbcType = JdbcType.BIGINT) })
    @Select("select id, slot, path, merid from merchant_pic where merid=#{merid}")
    List<MerchantPic> selectListByMerid(@Param(value = "merid") Long merid);

    /**
     * 根据id更新商户滚动图片.
     * 
     * @param merchantPic MerchantPic
     * @return int
     * @author 尤小平
     * @date 2017年4月10日 下午4:41:17
     */
    @UpdateProvider(type = MerchantPicSql.class, method = "update")
    int updateByPrimaryKey(MerchantPic merchantPic);

    /**
     * 根据id删除商户滚动图片.
     * 
     * @param id id
     * @return int
     * @author 尤小平
     * @date 2017年4月10日 下午4:41:35
     */
    @Delete("delete from merchant_pic where id = #{id}")
    int delete(Integer id);
}

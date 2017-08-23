/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月25日 上午11:18:04
* 创建作者：尤小平
* 文件名称：MerchantManagerDao.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.merchant.dao;

import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.timebuysrv.merchant.dao.sql.MerchantManagerSql;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantManager;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "merchantManagerDao")
public interface MerchantManagerDao {

    /**
     * 获取MerchantManager列表.
     * 
     * @param merchantManager MerchantManager
     * @return List<MerchantManager>
     * @author 尤小平
     * @date 2017年4月25日 下午2:46:03
     */
    @Results(value = { @Result(property = "id", column = "id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "uid", column = "uid", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "mid", column = "mid", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "uname", column = "uname", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "type", column = "type", javaType = String.class, jdbcType = JdbcType.VARCHAR) })
    @SelectProvider(type = MerchantManagerSql.class, method = "getListByMerchantManager")
    List<MerchantManager> getListByMerchantManager(MerchantManager merchantManager);

    /**
     * 获取MerchantManager列表，分页
     * 
     * @param merchantManager MerchantManager
     * @param page Page
     * @return List<MerchantManager>
     * @author 尤小平
     * @date 2017年4月27日 下午8:14:41
     */
    @Results(value = { @Result(property = "id", column = "id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "uid", column = "uid", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "mid", column = "mid", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "uname", column = "uname", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "type", column = "type", javaType = String.class, jdbcType = JdbcType.VARCHAR) })
    @SelectProvider(type = MerchantManagerSql.class, method = "getListByParam")
    List<MerchantManager> getListByParams(@Param(value = "merchantManager") MerchantManager merchantManager,
            @Param(value = "page") Page<MerchantManager> page);

    /**
     * 获取MerchantManager列表总条数.
     * 
     * @param merchantManager MerchantManager
     * @return 总条数
     * @author 尤小平
     * @date 2017年4月27日 下午8:15:21
     */
    @SelectProvider(type = MerchantManagerSql.class, method = "getCountByParams")
    int getCountByParams(MerchantManager merchantManager);

    /**
     * 新增管理员.
     * 
     * @param merchantManager MerchantManager
     * @return 成功条数
     * @author 尤小平
     * @date 2017年4月27日 下午8:13:33
     */
    @InsertProvider(type = MerchantManagerSql.class, method = "insert")
    int insertSelective(MerchantManager merchantManager);

    /**
     * 根据主键修改MerchantManager.
     * 
     * @param merchantManager MerchantManager
     * @return 成功条数
     * @author 尤小平
     * @date 2017年4月27日 下午8:13:59
     */
    @UpdateProvider(type = MerchantManagerSql.class, method = "update")
    int updateByPrimaryKey(MerchantManager merchantManager);

    /**
     * 根据主键删除MerchantManager.
     * 
     * @param id 主键
     * @return 成功条数
     * @author 尤小平
     * @date 2017年4月27日 下午8:14:16
     */
    @Delete("delete from merchant_manager where id=#{id}")
    int deleteByPrimaryKey(Long id);

    /**
     * 根据主键获取MerchantManager.
     * 
     * @param id 主键
     * @return MerchantManager
     * @author 尤小平
     * @date 2017年4月27日 下午8:12:29
     */
    @Results(value = { @Result(property = "id", column = "id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "uid", column = "uid", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "mid", column = "mid", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "uname", column = "uname", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "type", column = "type", javaType = String.class, jdbcType = JdbcType.VARCHAR) })
    @Select("select id,uid,mid,uname,type from merchant_manager where id=#{id}")
    MerchantManager selectByPrimaryKey(Long id);
}

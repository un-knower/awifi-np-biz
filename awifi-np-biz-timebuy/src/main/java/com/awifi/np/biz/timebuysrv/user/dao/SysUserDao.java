/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年4月14日 下午2:09:21
 * 创建作者：尤小平
 * 文件名称：SysUserDao.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.timebuysrv.user.dao;

import com.awifi.np.biz.timebuysrv.user.dao.sql.SysUserSql;
import com.awifi.np.biz.timebuysrv.user.model.SysUser;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service(value = "sysUserDao")
public interface SysUserDao {
    /**
     * 新增用户.
     *
     * @param sysUser
     * @return
     * @author 尤小平
     * @date 2017年4月10日 下午4:39:59
     */
    @InsertProvider(type = SysUserSql.class, method = "insert")
    int insert(final SysUser sysUser);

    /**
     * 根据userId查询用户.
     *
     * @param outid
     * @return
     * @author 尤小平
     * @date 2017年4月10日 下午4:40:29
     */
    @Results(value = {@Result(property = "id", column = "id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "face", column = "face", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "nkname", column = "nkname", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "sex", column = "sex", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "birth", column = "birth", javaType = Date.class, jdbcType = JdbcType.DATE),
            @Result(property = "address", column = "address", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "outid", column = "outId", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "telno", column = "telno", javaType = String.class, jdbcType = JdbcType.VARCHAR)})
    @Select("select id,face,nkname,sex,birth,address,outId,telno from sys_user where outId=#{outid} limit 1")
    SysUser selectByUserId(@Param(value = "outid") Long outid);


    /**
     * 根据userId更新用户.
     *
     * @param sysUser
     * @return
     * @author 尤小平
     * @date 2017年4月10日 下午4:41:17
     */
    @UpdateProvider(type = SysUserSql.class, method = "update")
    int updateByUserId(SysUser sysUser);
}

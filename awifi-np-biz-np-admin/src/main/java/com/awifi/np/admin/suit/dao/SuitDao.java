package com.awifi.np.admin.suit.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

import com.awifi.np.admin.suit.dao.sql.SuitSql;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月11日 下午4:56:58
 * 创建作者：周颖
 * 文件名称：SuitDao.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Service("suitDao")
public interface SuitDao {

    /**
     * 根据角色查找套码
     * @param roleIds 角色
     * @return 套码
     * @author 周颖  
     * @date 2017年1月11日 下午8:01:00
     */
    @SelectProvider(type=SuitSql.class,method="getCodeById")
    String getCodeById(@Param("roleIds")Long[] roleIds);

    /**
     * 获取用户全部套码
     * @param userId 用户id
     * @return 套码
     * @author 周颖  
     * @date 2017年5月9日 下午2:36:48
     */
    @Results(value = {
            @Result(property = "id", column = "id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "suitCode", column = "suit_code", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "suitName", column = "suit_name", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @Select("select distinct s.id,s.suit_code,s.suit_name from np_biz_user_role ur inner join np_biz_role r on ur.role_id=r.id "
            + "inner join np_biz_suit_role sr on r.id=sr.role_id inner join np_suit s on sr.suit_code=s.suit_code where ur.user_id=#{userId} order by show_level")
    List<Map<String, Object>> getSuitCodesByUserId(Long userId);
}
package com.awifi.np.biz.common.system.sysconfig.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.common.system.sysconfig.dao.sql.SysConfigSql;
import com.awifi.np.biz.common.system.sysconfig.model.SysConfig;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:Jan 10, 2017 8:59:06 AM
 * 创建作者：亢燕翔
 * 文件名称：SysConfigDao.java
 * 版本：  v1.0
 * 功能：  系统参数持久层
 * 修改记录：
 */
@Service("sysConfigDao")
public interface SysConfigDao {
	
	/**
	 * 通过参数key获取参数value
	 * @param paramKey 参数key
	 * @return value
	 * @author 亢燕翔  
	 * @date Jan 10, 2017 9:04:46 AM
	 */
    @Select("select param_value from np_biz_system_config where param_key=#{paramKey}")
	String getParamValue(String paramKey);
	
    /**
     * 系统参数配置--记录总数
     * @param keywords 关键字，允许为空， 支持[名称|key|value|备注]模糊查询
     * @return 记录总数
     * @author 许小满  
     * @date 2017年3月24日 下午7:33:37
     */
    @SelectProvider(type=SysConfigSql.class,method="getCountByParam")
    int getCountByParam(@Param("keywords")String keywords);
    
    /**
     * 系统参数配置--分页查询接口
     * @param keywords 关键字配置
     * @param begin 开始记录数
     * @param pageSize 每页记录数
     * @return 系统参数配置集合
     * @author 许小满  
     * @date 2017年3月24日 下午7:31:48
     */
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "aliasName", column = "alias_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "paramKey", column = "param_key", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "paramValue", column = "param_value", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "remark", column = "remark", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "createDate", column = "create_date", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "updateDate", column = "update_date", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @SelectProvider(type=SysConfigSql.class,method="getListByParam")
    List<SysConfig> getListByParam(@Param("keywords")String keywords, @Param("begin")Integer begin, @Param("pageSize")Integer pageSize);
    
    /**
     * 通过key查询记录数量
     * @param paramKey 参数键
     * @param id 需屏蔽的记录id
     * @return 数量
     * @author 许小满  
     * @date 2017年5月18日 上午12:05:50
     */
    @SelectProvider(type=SysConfigSql.class,method="getNumByParamKey")
    Long getNumByParamKey(@Param("paramKey")String paramKey, @Param("id")Long id);
    
    /**
     * 添加
     * @param aliasName 别名
     * @param paramKey 参数键
     * @param paramValue 参数值
     * @param remark 备注
     * @author 许小满  
     * @date 2017年5月18日 上午12:14:06
     */
    @Insert("insert into np_biz_system_config(alias_name,param_key,param_value,remark) values(#{aliasName},#{paramKey},#{paramValue},#{remark})")
    void add(@Param("aliasName")String aliasName, @Param("paramKey")String paramKey, @Param("paramValue")String paramValue, @Param("remark")String remark);
    
    /**
     * 更新
     * @param id 主键id
     * @param aliasName 别名
     * @param paramKey 参数键
     * @param paramValue 参数值
     * @param remark 备注
     * @author 许小满  
     * @date 2017年5月18日 上午12:14:06
     */
    @Insert("update np_biz_system_config set alias_name=#{aliasName},param_key=#{paramKey},param_value=#{paramValue},remark=#{remark} where id=#{id}")
    void update(@Param("id")Long id, @Param("aliasName")String aliasName, @Param("paramKey")String paramKey, @Param("paramValue")String paramValue, @Param("remark")String remark);
    
    /**
     * 删除
     * @param id 主键id
     * @author 许小满  
     * @date 2017年5月18日 上午12:14:06
     */
    @Insert("delete from np_biz_system_config where id=#{id}")
    void delete(@Param("id")Long id);
    
    /**
     * 通过id查询记录
     * @param id 主键id
     * @return 系统参数配置表记录
     * @author 许小满  
     * @date 2017年5月18日 上午12:14:06
     */
    @Results(value = {
            @Result(property = "aliasName", column = "alias_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "paramKey", column = "param_key", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "paramValue", column = "param_value", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "remark", column = "remark", javaType = String.class, jdbcType = JdbcType.VARCHAR),
    })
    @Select("select alias_name,param_key,param_value,remark from np_biz_system_config where id=#{id}")
    SysConfig getById(@Param("id")Long id);
}

package com.awifi.np.biz.toe.admin.usrmgr.staticuser.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.toe.admin.usrmgr.staticuser.dao.sql.StaticUserSql;
import com.awifi.np.biz.toe.admin.usrmgr.staticuser.model.StaticUser;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月9日 上午10:00:40
 * 创建作者：周颖
 * 文件名称：StaticUserDao.java
 * 版本：  v1.0
 * 功能：静态用户
 * 修改记录：
 */
@Service("staticUserDao")
public interface StaticUserDao {

    /**
     * 静态用户列表总数
     * @param keywords 关键字  [用户名|手机号|护照|身份证]模糊查询
     * @param merchantId 商户id
     * @param userType 用户类型
     * @param cascadeLabel 商户层级
     * @param merchantIds 管理的商户ids
     * @return 总数
     * @author 周颖  
     * @date 2017年2月9日 上午10:17:20
     */
    @SelectProvider(type=StaticUserSql.class,method="getCountByParam")
    int getCountByParam(@Param("keywords")String keywords,@Param("merchantId") Long merchantId,@Param("userType") Integer userType,
            @Param("cascadeLabel") String cascadeLabel,@Param("merchantIds")Long[] merchantIds);

    /**
     * 静态用户列表
     * @param keywords 关键字  [用户名|手机号|护照|身份证]模糊查询
     * @param merchantId 商户id
     * @param userType 用户类型
     * @param cascadeLabel 商户层级
     * @param merchantIds 管理的商户ids
     * @param begin 开始条数
     * @param pageSize 页面大小
     * @return 列表
     * @author 周颖  
     * @date 2017年2月9日 上午11:10:16
     */
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "userName", column = "user_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "userType", column = "user_type", javaType = Integer.class, jdbcType = JdbcType.TINYINT),
            @Result(property = "realName", column = "real_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "cellphone", column = "cellphone", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "passport", column = "passport", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "identityCard", column = "identity_card", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "deptName", column = "dept_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "remark", column = "remark", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "merchantId", column = "fk_customer_id", javaType = Long.class, jdbcType = JdbcType.BIGINT)
            })
    @SelectProvider(type=StaticUserSql.class,method="getListByParam")
    List<StaticUser> getListByParam(@Param("keywords")String keywords,@Param("merchantId") Long merchantId,@Param("userType") Integer userType,
            @Param("cascadeLabel") String cascadeLabel,@Param("merchantIds")Long[] merchantIds,@Param("begin") Integer begin,@Param("pageSize") Integer pageSize);

    /**
     * 商户下用户名数量
     * @param merchantId 商户名
     * @param userName 用户名
     * @return 总数
     * @author 周颖  
     * @date 2017年2月9日 下午3:58:56
     */
    @Select("select count(pk_id) from toe_static_user where delete_flag=1 and user_name=#{userName} and fk_customer_id=#{merchantId}")
    int getNumByUserName(@Param("merchantId")Long merchantId,@Param("userName") String userName);

    /**
     * 商户下手机号数量
     * @param merchantId 商户id
     * @param cellphone 手机号
     * @return 总数
     * @author 周颖  
     * @date 2017年2月9日 下午4:07:32
     */
    @Select("select count(pk_id) from toe_static_user where delete_flag=1 and cellphone=#{cellphone} and fk_customer_id=#{merchantId}")
    int getNumByCellphone(@Param("merchantId")Long merchantId,@Param("cellphone") String cellphone);

    /**
     * 商户下手机号数量
     * @param id 用户id
     * @param merchantId 商户id
     * @param cellphone 手机号
     * @return 总数
     * @author 周颖  
     * @date 2017年2月9日 下午7:20:58
     */
    @Select("select count(pk_id) from toe_static_user where delete_flag=1 and cellphone=#{cellphone} and fk_customer_id=#{merchantId} and pk_id!=#{id}")
    int getNumByParam(@Param("id")Long id,@Param("merchantId") Long merchantId,@Param("cellphone") String cellphone);    
    
    /**
     * 新增用户
     * @param staticUser 用户
     * @author 周颖  
     * @date 2017年2月9日 下午4:28:44
     */
    @Insert("insert into toe_static_user(user_name,password,user_type,user_info_type,cellphone,passport,identity_card,real_name,dept_name,remark,fk_customer_id,cascade_label,create_date,update_date) "
            + "values(#{userName},#{password},#{userType},#{userInfoType},#{cellphone},#{passport},#{identityCard},#{realName},#{deptName},#{remark},#{merchantId},#{cascadeLabel},unix_timestamp(now()),unix_timestamp(now()))")
    void add(StaticUser staticUser);

    /**
     * 更新用户
     * @param staticUser 用户
     * @author 周颖  
     * @date 2017年2月9日 下午7:41:01
     */
    @Update("update toe_static_user set user_info_type=#{userInfoType},cellphone=#{cellphone},passport=#{passport},identity_card=#{identityCard},real_name=#{realName},dept_name=#{deptName},remark=#{remark},update_date=unix_timestamp(now()) where pk_id=#{id}")
    void update(StaticUser staticUser);

    /**
     * 用户详情
     * @param id 主键id
     * @return 用户详情
     * @author 周颖  
     * @date 2017年2月10日 上午8:42:48
     */
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "userName", column = "user_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "userType", column = "user_type", javaType = Integer.class, jdbcType = JdbcType.TINYINT),
            @Result(property = "realName", column = "real_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "userInfoType", column = "user_info_type", javaType = Integer.class, jdbcType = JdbcType.TINYINT),
            @Result(property = "cellphone", column = "cellphone", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "passport", column = "passport", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "identityCard", column = "identity_card", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "deptName", column = "dept_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "remark", column = "remark", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "merchantId", column = "fk_customer_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "cascadeLabel", column = "cascade_label", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @Select(" select pk_id,user_name,user_type,user_info_type,cellphone,passport,identity_card,real_name,dept_name,remark,fk_customer_id,cascade_label from toe_static_user where pk_id=#{id}")
    StaticUser getById(@Param("id")Long id);

    /**
     * 删除单条用户 逻辑删除
     * @param id 用户主键id
     * @author 周颖  
     * @date 2017年2月10日 上午9:21:06
     */
    @Update("update toe_static_user set delete_flag=-1,update_date=unix_timestamp(now()) where pk_id=#{id}")
    void delete(@Param("id")Long id);

    /**
     * 批量删除用户 逻辑删除
     * @param ids 用户ids
     * @author 周颖  
     * @date 2017年2月10日 上午9:38:11
     */
    @UpdateProvider(type=StaticUserSql.class,method="batchDelete")
    void batchDelete(@Param("ids")Long[] ids);

    /**
     * 删除商户下的所有用户
     * @param merchantId 商户id
     * @author 周颖  
     * @date 2017年2月10日 上午9:59:59
     */
    @Update("update toe_static_user set delete_flag=-1,update_date=unix_timestamp(now()) where fk_customer_id=#{merchantId}")
    void deleteByMerchantId(@Param("merchantId")Long merchantId);

    /**
     * 静态用户列表
     * @param cellphone 手机号
     * @param merchantId 商户id
     * @return 列表
     * @author 王冬冬  
     * @date 2017年2月9日 上午11:10:16
     */
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "userName", column = "user_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "userType", column = "user_type", javaType = Integer.class, jdbcType = JdbcType.TINYINT),
            @Result(property = "realName", column = "real_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "cellphone", column = "cellphone", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "passport", column = "passport", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "identityCard", column = "identity_card", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "deptName", column = "dept_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "remark", column = "remark", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "merchantId", column = "fk_customer_id", javaType = Long.class, jdbcType = JdbcType.BIGINT)
            })
    @SelectProvider(type=StaticUserSql.class,method="getListByMerchantIdAndCellPhone")
    List<StaticUser> getListByMerchantIdAndCellPhone(@Param("merchantId") Long merchantId,@Param("cellphone") String cellphone);
    
    /**
     * 通过 用户名、密码 获取用户id
     * @param merchantId 商户id
     * @param userName 用户名
     * @param password 密码
     * @return 用户表主键id
     * @author 许小满  
     * @date 2016年3月10日 下午8:19:59
     */
    @Select("select pk_id from toe_static_user where user_name=#{userName} and password=#{password} and fk_customer_id=#{merchantId} and delete_flag=1")
    Long getIdByUserNameAndPwd(@Param("merchantId")Long merchantId, @Param("userName")String userName, @Param("password")String password);
    
    /**
     * 更新密码
     * @param id 用户表主键id
     * @param password 密码
     * @author 许小满  
     * @date 2016年3月10日 下午10:32:31
     */
    @Update("update toe_static_user set password=#{password} where pk_id=#{id}")
    void updatePwd(@Param("id")Long id, @Param("password")String password);
    
    /**
     * 通过手机号更新密码
     * @param merchantId 商户id
     * @param cellphone 手机号
     * @param password 密码
     * @author 许小满  
     * @date 2016年3月11日 上午9:17:56
     */
    @Update("update toe_static_user set password=#{password} where cellphone = #{cellphone} and fk_customer_id=#{merchantId} and delete_flag=1")
    void updatePwdByCellphone(@Param("merchantId")Long merchantId, @Param("cellphone")String cellphone, @Param("password")String password);
    
    /**
     * 获取静态用户对象
     * @param customerId 客户id
     * @param userName 用户名
     * @param password 密码
     * @return 静态用户对象
     * @author 许小满  
     * @date 2016年7月25日 下午12:37:17
     */
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "userInfoType", column = "user_info_type", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "cellphone", column = "cellphone", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "passport", column = "passport", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "identityCard", column = "identity_card", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @Select("select pk_id,user_info_type,cellphone,passport,identity_card from toe_static_user where user_name=#{userName} and password=#{password} and fk_customer_id=#{customerId} and delete_flag=1")
    StaticUser getStaticUser(@Param("customerId")Long customerId, @Param("userName")String userName, @Param("password")String password);
}
package com.awifi.np.biz.toe.admin.usrmgr.blackuser.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

import com.awifi.np.biz.toe.admin.usrmgr.blackuser.dao.sql.BlackUserSql;
import com.awifi.np.biz.toe.admin.usrmgr.blackuser.model.BlackUser;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月13日 上午8:52:49
 * 创建作者：周颖
 * 文件名称：BlackUserDao.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface BlackUserDao {

    /**
     * 黑名单总数
     * @param type black/white
     * @param merchantId 商户id
     * @param matchRule 匹配规则
     * @param keywords 关键字
     * @param cascadeLabel 层级
     * @param merchantIds 管理的商户ids
     * @return 总数
     * @author 周颖  
     * @date 2017年2月13日 上午9:52:21
     */
    @SelectProvider(type=BlackUserSql.class,method="getCountByParam")
    int getCountByParam(@Param("type")String type,@Param("merchantId") Long merchantId,@Param("matchRule") Integer matchRule,
            @Param("keywords") String keywords,@Param("cascadeLabel") String cascadeLabel,@Param("merchantIds")Long[] merchantIds);

    /**
     * 黑名单列表
     * @param type black/white
     * @param merchantId 商户id
     * @param matchRule 匹配规则
     * @param keywords 关键字
     * @param cascadeLabel 层级关系
     * @param merchantIds 管理的商户ids
     * @param begin 开始行
     * @param pageSize 页面大小
     * @return 列表
     * @author 周颖  
     * @date 2017年2月13日 上午9:59:18
     */
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "userName", column = "user_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "matchRule", column = "match_rule", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "cellphone", column = "cellphone", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "realName", column = "real_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "merchantId", column = "fk_customer_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "userType", column = "user_type", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "createDate", column = "create_date", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @SelectProvider(type=BlackUserSql.class,method="getListByParam")
    List<BlackUser> getListByParam(@Param("type")String type,@Param("merchantId") Long merchantId,@Param("matchRule") Integer matchRule,@Param("keywords") String keywords,
            @Param("cascadeLabel") String cascadeLabel,@Param("merchantIds")Long[] merchantIds, @Param("begin")Integer begin,@Param("pageSize") Integer pageSize);

    /**
     * 判断商户下手机号是否加黑名称
     * @param merchantId 商户id
     * @param cellphone 手机号
     * @param type 类型  black/white
     * @return 总数
     * @author 周颖  
     * @date 2017年2月13日 上午11:25:24
     */
    @Select("select count(pk_id) from toe_blackwhiteuser where cellphone=#{cellphone} and fk_customer_id=#{merchantId} and type=#{type} and delete_flag=1")
    int getNumByCellphone(@Param("merchantId") Long merchantId,@Param("cellphone") String cellphone,@Param("type") String type);

    /**
     * 新建黑名单
     * @param blackUser 黑名单
     * @author 周颖  
     * @date 2017年2月13日 下午1:52:29
     */
    @Insert(" insert into toe_blackwhiteuser(cellphone,match_rule,type,remark,fk_customer_id,cascade_label,create_date,update_date) "
            + "values(#{cellphone},#{matchRule},'black',#{remark},#{merchantId},#{cascadeLabel},unix_timestamp(now()),unix_timestamp(now()))")
    void add(BlackUser blackUser);

    /**
     * 删除黑名单 逻辑删除
     * @param id 黑名单主键
     * @author 周颖  
     * @date 2017年2月13日 下午2:01:07
     */
    @Update("update toe_blackwhiteuser set delete_flag=-1,update_date=unix_timestamp(now()) where pk_id=#{id}")
    void delete(@Param("id")Long id);
    
    /**
     * 获取指定客户下所有的匹配规则
     * @param merchantId 商户id
     * @return 匹配规则
     * @author 许小满  
     * @date 2016年10月31日 下午12:25:34
     */
    @Select("select distinct match_rule from toe_blackwhiteuser where delete_flag=1 and type='black' and fk_customer_id=#{merchantId}")
    List<Integer> getMatchRulesByMerchantId(@Param("merchantId") Long merchantId);
    
    /**
     * 判断手机号是否加黑名单
     * @param cellphone 手机号
     * @param merchantId 商户id
     * @param matchRule 匹配规则：1 精确、2 模糊
     * @return 总数
     * @author ZhouYing 
     * @date 2016年6月14日 上午9:10:03
     */
    @Select("select count(pk_id) from toe_blackwhiteuser where cellphone=#{cellphone} and fk_customer_id=#{merchantId} and type='black' and match_rule=#{matchRule} and delete_flag=1")
    int isBlack(@Param("cellphone") String cellphone, @Param("merchantId") Long merchantId, @Param("matchRule") Integer matchRule);
    
    /**
     * 获取指定客户下的所有手机号
     * @param merchantId 商户id
     * @param matchRule 匹配规则：1 精确、2 模糊
     * @return 手机号
     * @author 许小满  
     * @date 2016年10月31日 下午12:36:23
     */
    @Select("select cellphone from toe_blackwhiteuser where fk_customer_id=#{merchantId} and match_rule=#{matchRule} and type='black' and delete_flag=1 limit 100")
    List<String> getCellphonesByMerchantId(@Param("merchantId") Long merchantId, @Param("matchRule") Integer matchRule);
}
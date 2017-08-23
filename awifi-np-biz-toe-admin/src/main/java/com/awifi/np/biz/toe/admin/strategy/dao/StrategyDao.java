/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月19日 上午11:18:22
* 创建作者：周颖
* 文件名称：StrategyDao.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.toe.admin.strategy.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.toe.admin.strategy.dao.sql.StrategySql;
import com.awifi.np.biz.toe.admin.strategy.model.Strategy;

@Service("strategyDao")
public interface StrategyDao {

    /**
     * 批量获取站点下的策略数量
     * @param siteIds 站点ids
     * @return 策略数
     * @author 周颖  
     * @date 2017年4月19日 下午1:41:17
     */
    @Results(value = {
            @Result(property = "siteId", column = "fk_site_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "num", column = "num", javaType = Integer.class, jdbcType = JdbcType.INTEGER)
            })
    @SelectProvider(type=StrategySql.class,method="getTotalBySiteIds")
    List<Map<String, Object>> getTotalBySiteIds(@Param("siteIds")Long[] siteIds);

    /**
     * 查看站点关联的策略
     * @param siteId 站点id
     * @return 总数
     * @author 周颖  
     * @date 2017年4月25日 下午7:47:32
     */
    @Select("select count(pk_id) from toe_strategy where delete_flag=1 and fk_site_id=#{siteId}")
    int getNumBySiteId(@Param("siteId")Long siteId);

    /**
     * 策略列表总数
     * @param siteId 站点名称
     * @param strategyName 策略名称关键字
     * @return 总数
     * @author 周颖  
     * @date 2017年4月27日 下午1:54:54
     */
    @SelectProvider(type=StrategySql.class,method="getCountByParam")
    int getCountByParam(@Param("siteId")Long siteId,@Param("strategyName") String strategyName);

    /**
     * 策略列表
     * @param siteId 站点id
     * @param strategyName 策略名称关键字
     * @param begin 开始条数
     * @param pageSize 一页大小
     * @return 列表
     * @author 周颖  
     * @date 2017年4月27日 下午4:16:24
     */
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "strategyName", column = "strategyName", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "strategyType", column = "strategy_type", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "orderNo", column = "order_no", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "siteId", column = "fk_site_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "startDate", column = "start_date", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "endDate", column = "end_date", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @SelectProvider(type=StrategySql.class,method="getListByParam")
    List<Strategy> getListByParam(@Param("siteId")Long siteId,@Param("strategyName") String strategyName,
            @Param("begin")Integer begin,@Param("pageSize") Integer pageSize);

    /**
     * 获取策略内容 类型为2
     * @param strategyId 策略主键id
     * @return 内容
     * @author 周颖  
     * @date 2017年4月27日 下午4:44:08
     */
    @Select("select content from toe_strategy_item where fk_strategy_id=#{strategyId} limit 1")
    String getContentById(@Param("strategyId")Long strategyId);

    /**
     * 策略内容 类型为3
     * @param strategyId 策略主键id
     * @return 内容列表
     * @author 周颖  
     * @date 2017年4月27日 下午4:53:05
     */
    @Select("select content from toe_strategy_item where fk_strategy_id=#{strategyId}")
    List<String> getContentsById(@Param("strategyId")Long strategyId);

    /**
     * 一个站点下策略同名的条数
     * @param siteId 站点id
     * @param strategyName 策略名称
     * @return 总数
     * @author 周颖  
     * @date 2017年4月28日 下午1:55:26
     */
    @Select("select count(pk_id) from toe_strategy where strategyName=#{strategyName} and fk_site_id=#{siteId} and delete_flag=1")
    int getNumByStrategyName(@Param("siteId")Long siteId,@Param("strategyName") String strategyName);

    /**
     * 站点下策略最大的排序号
     * @param siteId 站点id
     * @return 排序号
     * @author 周颖  
     * @date 2017年4月28日 下午2:27:11
     */
    @Select("select max(order_no) from toe_strategy where fk_site_id=#{siteId} and delete_flag=1")
    Integer getMaxNo(@Param("siteId")Long siteId);

    /**
     * 新建策略
     * @param strategy 策略
     * @author 周颖  
     * @date 2017年4月28日 下午2:32:12
     */
    @SelectKey(before=false,keyProperty="id",resultType=Long.class,statementType=StatementType.STATEMENT,statement="SELECT LAST_INSERT_ID() AS id")
    @Insert("insert into toe_strategy(strategyName,strategy_type,start_date,end_date,order_no,fk_site_id,fk_customer_id,cascade_label,create_date,update_date) "
            + "values(#{strategyName},#{strategyType},#{startDate},#{endDate},#{orderNo},#{siteId},#{merchantId},#{cascadeLabel},unix_timestamp(now()),unix_timestamp(now()))")
    void add(Strategy strategy);

    /**
     * 保存策略子表
     * @param strategyId 策略id
     * @param content 内容
     * @author 周颖  
     * @date 2017年4月28日 下午2:43:31
     */
    @Insert("insert into toe_strategy_item(content,fk_strategy_id,create_date,update_date) values(#{content},#{strategyId},unix_timestamp(now()),unix_timestamp(now()))")
    void addItem(@Param("strategyId")Long strategyId,@Param("content") String content);
    
    /**
     * 一个站点下策略同名的条数
     * @param siteId 站点id
     * @param strategyId 策略id
     * @param strategyName 策略名称
     * @return 总数
     * @author 周颖  
     * @date 2017年4月28日 下午3:00:29
     */
    @Select("select count(pk_id) from toe_strategy where strategyName=#{strategyName} and fk_site_id=#{siteId} and delete_flag=1 and pk_id!=#{strategyId}")
    int getNumByIdAndName(@Param("siteId")Long siteId,@Param("strategyId") Long strategyId,@Param("strategyName") String strategyName);

    /**
     * 删除子表
     * @param strategyId 策略id
     * @author 周颖  
     * @date 2017年4月28日 下午3:10:08
     */
    @Delete("delete from toe_strategy_item where fk_strategy_id=#{strategyId}")
    void deleteItem(@Param("strategyId")Long strategyId);

    /**
     * 编辑策略
     * @param strategy 编辑策略
     * @author 周颖  
     * @date 2017年4月28日 下午3:13:05
     */
    @Update("update toe_strategy set strategyName=#{strategyName},strategy_type=#{strategyType},start_date=#{startDate},end_date=#{endDate},update_date=unix_timestamp(now()) where pk_id=#{id}")
    void update(Strategy strategy);

    /**
     * 通过站点id获取最新的策略
     * @param siteId 站点id
     * @return 策略
     * @author 周颖  
     * @date 2017年5月2日 上午9:10:32
     */
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "strategyName", column = "strategyName", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "strategyType", column = "strategy_type", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "orderNo", column = "order_no", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "merchantId", column = "fk_customer_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "startDate", column = "start_date", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "endDate", column = "end_date", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @Select("select pk_id,strategyName,strategy_type,order_no,fk_customer_id,from_unixtime(start_date,'%Y-%m-%d') as start_date,from_unixtime(end_date,'%Y-%m-%d') as end_date "
            + "from toe_strategy where fk_site_id=#{siteId} and delete_flag=1 order by pk_id desc limit 1")
    Strategy getBySiteId(@Param("siteId")Long siteId);

    /**
     * 策略详情
     * @param id 主键id
     * @return 策略详情
     * @author 周颖  
     * @date 2017年5月2日 上午10:32:12
     */
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "strategyName", column = "strategyName", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "strategyType", column = "strategy_type", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "orderNo", column = "order_no", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "merchantId", column = "fk_customer_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "startDate", column = "start_date", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "endDate", column = "end_date", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @Select("select pk_id,strategyName,strategy_type,order_no,fk_customer_id,from_unixtime(start_date,'%Y-%m-%d') as start_date,from_unixtime(end_date,'%Y-%m-%d') as end_date "
            + "from toe_strategy where pk_id=#{id}")
    Strategy getById(@Param("id")Long id);
    
    /**
     * 获取站点id
     * @param merchantId 客户id
     * @param ssid ssid
     * @param devId 设备id
     * @return 站点id
     * @author 许小满  
     * @date 2017年5月11日 下午6:22:05
     */
    @SelectProvider(type=StrategySql.class, method="getSiteId")
    Long getSiteId(@Param("merchantId")Long merchantId, @Param("ssid")String ssid, @Param("devId")String devId);

    /**
     * 删除策略
     * @param id 策略id
     * @author 周颖  
     * @date 2017年5月2日 上午10:39:53
     */
    @Update("update toe_strategy set delete_flag=-1,update_date=unix_timestamp(now()) where pk_id=#{id}")
    void delete(@Param("id")Long id);

    /**
     * 站点策略最大排序号
     * @param siteId 站点id
     * @return 最大排序号
     * @author 周颖  
     * @date 2017年5月2日 上午11:03:52
     */
    @Select("select max(order_no) from toe_strategy where fk_site_id=#{siteId} and delete_flag=1")
    Integer getMaxOrderNo(@Param("siteId")Long siteId);

    /**
     * 获取紧邻的上面策略
     * @param siteId 站点id
     * @param orderNo 当前排序号
     * @return 策略
     * @author 周颖  
     * @date 2017年5月2日 上午11:05:10
     */
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "orderNo", column = "order_no", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            })
    @Select("select pk_id,order_no from toe_strategy where fk_site_id=#{siteId} and order_no>#{orderNo} and delete_flag=1 order by order_no limit 1")
    Map<String, Object> getUpStrategy(@Param("siteId")Long siteId,@Param("orderNo") Integer orderNo);

    /**
     * 更新排序号
     * @param id 策略id
     * @param orderNo 排序号
     * @author 周颖  
     * @date 2017年5月2日 上午11:08:45
     */
    @Update("update toe_strategy set order_no=#{orderNo},update_date=unix_timestamp(now()) where pk_id=#{id}")
    void updateOrderNo(@Param("id")Long id,@Param("orderNo") Integer orderNo);

    /**
     * 站点策略最小排序号
     * @param siteId 站点id
     * @return 最小排序号
     * @author 周颖  
     * @date 2017年5月2日 上午11:11:36
     */
    @Select("select min(order_no) from toe_strategy where fk_site_id=#{siteId} and delete_flag=1")
    Integer getMinOrderNo(@Param("siteId")Long siteId);

    /**
     * 获取紧邻的下面策略
     * @param siteId 站点id
     * @param orderNo 排序号
     * @return 策略
     * @author 周颖  
     * @date 2017年5月2日 上午11:14:51
     */
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "orderNo", column = "order_no", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            })
    @Select("select pk_id,order_no from toe_strategy where fk_site_id=#{siteId} and order_no<#{orderNo} and delete_flag=1 order by order_no desc limit 1")
    Map<String, Object> getDownStrategy(@Param("siteId")Long siteId,@Param("orderNo") Integer orderNo);
}

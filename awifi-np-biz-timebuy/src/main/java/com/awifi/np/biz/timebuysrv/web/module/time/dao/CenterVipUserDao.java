package com.awifi.np.biz.timebuysrv.web.module.time.dao;




import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.timebuysrv.web.module.time.dao.sql.CenterVipUserSql;
import com.awifi.np.biz.timebuysrv.web.module.time.model.VipUserObject;




/**
 * 推送用户数据保存
 * @author zhouwx
 * @since 2016-03-24
 */
@Service(value = "centerOnlineUserDao")
public interface CenterVipUserDao {
    /**
     * 新增推送用户
     * @param onlineUser
     * 推送用户对象
     * @return 成功数
     */
    @InsertProvider(type = CenterVipUserSql.class, method = "add")
    int add(VipUserObject onlineUser);
    /**
     * 更新推送用户数据
     * @param onlineUser
     * 推送数据对象
     */
    @UpdateProvider(type = CenterVipUserSql.class, method = "update")
    int  update(VipUserObject onlineUser);
    /**
     * 根据手机号、时间查询有效记录数
     * @param map
     * telephone:手机号码
     * nowTime:当前时间
     * @return
     * 记录数
     */
    @SelectProvider(type = CenterVipUserSql.class, method = "queryOnlineUserCount")
    int queryOnlineUserCount(Map<String, Object> map);
    /**
     * 根据手机号查询最新记录
     * @param map
     * telephone:手机号码 
     * @return
     * OnlineUserObject 推送用户数据对象
     */
    @Results(value = { @Result(property ="id", column ="id", javaType = Long.class, jdbcType = JdbcType.BIGINT ),
            @Result(property = "telephone", column = "telephone", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "processFlg", column = "process_flg", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "startTime", column = "start_time", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "endTime", column = "end_time", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "createDate", column = "create_date", javaType = Date.class, jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "modifyDate", column = "modify_date", javaType = Date.class, jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "merchantId", column = "merchant_id", javaType = Long.class, jdbcType = JdbcType.TIMESTAMP)
    })
    @SelectProvider(type = CenterVipUserSql.class, method = "queryLastOnlineUser")
    VipUserObject queryLastOnlineUser(Map<String, Object> map);
    /**
     * 根据条件查询电渠用户数据列表
     * @param map 
     * 条件参数
     * @return
     * List<OnlineUserObject>
     */
    @Results(value = { @Result(property ="id", column ="id", javaType = Long.class, jdbcType = JdbcType.BIGINT ),
            @Result(property = "telephone", column = "telephone", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "processFlg", column = "process_flg", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "startTime", column = "start_time", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "endTime", column = "end_time", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "createDate", column = "create_date", javaType = Date.class, jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "modifyDate", column = "modify_date", javaType = Date.class, jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "merchantId", column = "merchant_id", javaType = Long.class, jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "merchantName", column = "merchant_name", javaType =String.class,jdbcType = JdbcType.VARCHAR),
            @Result(property = "provinceName", column = "province_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "cityName", column = "city_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "countyName", column = "county_name", javaType = String.class, jdbcType = JdbcType.VARCHAR)
    })
    @SelectProvider(type = CenterVipUserSql.class, method = "queryListByMerArea")
    List<VipUserObject> queryListByMerArea(Map<String, Object> map);
    /**
     * 根据条件查询电渠用户记录数
     * @param map
     * 条件参数
     * @return
     * int 记录数
     */
    @SelectProvider(type = CenterVipUserSql.class, method = "queryCountByMerArea")
    int queryCountByMerArea(Map<String, Object> map);
    /**
     * 更新商户id
     * @param onlineUser
     * 用户信息
     * @return
     * int 更新记录数
     */
    @UpdateProvider(type = CenterVipUserSql.class, method = "updateVipUser")
    int updateVipUser(VipUserObject onlineUser);
    
    /**
     * 查询VIP用户列表
     * 
     * @param map
     * @return
     * @author 余红伟 
     * @date 2017年4月24日 上午11:19:16
     */
    @Results(value = { @Result(property ="id", column ="id", javaType = Long.class, jdbcType = JdbcType.BIGINT ),
            @Result(property = "telephone", column = "telephone", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "processFlg", column = "process_flg", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "startTime", column = "start_time", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "endTime", column = "end_time", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "startTimeStr", column = "start_time", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "endTimeStr", column = "end_time", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "createDateStr", column = "create_date", javaType = Date.class, jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "modifyDateStr", column = "modify_date", javaType = Date.class, jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "merchantId", column = "merchant_id", javaType = Long.class, jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "merchantName", column = "merchantName", javaType =String.class,jdbcType = JdbcType.VARCHAR),
            @Result(property = "provinceName", column = "province", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "cityName", column = "city", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "countyName", column = "area", javaType = String.class, jdbcType = JdbcType.VARCHAR)
    })
    @SelectProvider(type = CenterVipUserSql.class, method = "queryListByParam")
    List<VipUserObject> queryListByParam(Map<String, Object> map);
    /**
     * 查询vip用户条数
     * @param map
     * @return
     * @author 余红伟 
     * @date 2017年8月11日 上午10:59:12
     */
    @SelectProvider(type = CenterVipUserSql.class, method = "queryVipUserCount")
    int queryVipUserCount(Map<String, Object> map);
    /**
     *是否VIP用户
     * 
     * @param telephone
     * @return
     * @author 余红伟 
     * @date 2017年4月26日 下午5:07:33
     */
    @SelectProvider(type = CenterVipUserSql.class, method = "isVipUser")
    int isVipUser(Long telephone);
}

package com.awifi.np.biz.timebuysrv.web.module.time.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;
import com.awifi.np.biz.timebuysrv.web.module.time.model.UserCutoff;

/**
 * 用户时长dao
 * @author 张智威
 * 2017年4月10日 下午5:20:14
 */
@Service
public interface MerchantUserCutoffDateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserCutoff record);

    int insertSelective(UserCutoff record);

    UserCutoff selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserCutoff record);
    /**
     * 根据id更新时长 用户领取免费礼包用到
     * @param id
     * @param endTime
     * @return
     * @author 张智威  
     * @date 2017年4月10日 下午5:27:28
     */
    @Update("update  center_pub_merchant_user_cutoff_date set cutoff_date=#{cutoffDate} where id = #{id}")
    int updateEndDate(@Param("id")Long id, @Param("cutoffDate")Date cutoffDate);

    int updateByPrimaryKey(UserCutoff record);
    
    List<UserCutoff> selectByMap(Map<String, Object> map);
    /**
     * 更新之前要反查数据
     * @param userId
     * @param merId
     * @return
     * @author 张智威  
     * @date 2017年4月10日 下午6:30:00
     */
    @Results(value = {
            @Result(property = "id", column = "id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "merchantId", column = "merchant_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "cutoffDate", column = "cutoff_date", javaType = Date.class, jdbcType = JdbcType.BIGINT)}
    		)
    @Select("select * from center_pub_merchant_user_cutoff_date where user_id=#{userId} and merchant_id = #{merId}  limit 1" )
    UserCutoff selectByUserIdAndMerId( @Param("userId")Long userId, @Param("merId")Long merId);
    
    int queryCountByParam(Map<String, Object> map);
}
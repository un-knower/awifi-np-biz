/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月8日 上午9:24:16
* 创建作者：余红伟
* 文件名称：MerchantStatisticsDao.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.merchant.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import com.awifi.np.biz.timebuysrv.merchant.dao.sql.MerchantStatisticsSql;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantStatistics;
import org.springframework.stereotype.Service;

@Service
public interface MerchantStatisticsDao {

    /**
     * 插入数据
     * 
     * @author 余红伟 
     * @date 2017年5月12日 上午9:04:58
     */
    @InsertProvider(type = MerchantStatisticsSql.class, method = "insertStatistics")
    public void insertStatistics();
    /**
     * 更新统计数据
     * 
     * @author 余红伟 
     * @date 2017年5月12日 上午9:08:04
     */
    @UpdateProvider(type = MerchantStatisticsSql.class, method = "updateStatistics")
    public void updateStatistics();
    
    @Results(value = { @Result(property = "id", column = "id", javaType = Long.class,jdbcType = JdbcType.BIGINT),
            @Result(property = "merchantId", column = "merchant_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
//            @Result(property = "merchantNum", column = "merchant_num", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
//            @Result(property = "deviceNum", column = "device_num", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "totalPaid", column = "total_paid", javaType = Float.class, jdbcType = JdbcType.DECIMAL),
            @Result(property = "totalUsers", column = "total_users", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "vipUsers", column = "vip_users", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "payUsers", column = "pay_users", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "pkgDays", column = "pkg_days", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "pkgMonths", column = "pkg_months", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "pkgYears", column = "pkg_years", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "createDate", column = "create_date", javaType = Date.class, jdbcType = JdbcType.DATE),
            @Result(property = "area", column = "area", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            
    })
    /**
     * 根据省市区查询园区统计数据
     * @param map
     * @return
     * @author 余红伟 
     * @date 2017年5月12日 上午9:08:20
     */
    @SelectProvider(type = MerchantStatisticsSql.class, method = "queryStatistics")
    public List<MerchantStatistics> queryStatistics(Map map);
    /**
     * 统计条数
     * @param map
     * @return
     * @author 余红伟 
     * @date 2017年5月23日 上午9:38:49
     */
    @SelectProvider(type = MerchantStatisticsSql.class, method = "countByParams")
    public Integer countByParams(Map map);
    
    /**
     * 获取查询
     * @param map
     * @return
     * @author 余红伟 
     * @date 2017年5月23日 上午9:41:35
     */
    @Results(value = { @Result(property = "id", column = "id", javaType = Long.class,jdbcType = JdbcType.BIGINT),
            @Result(property = "merchantId", column = "merchant_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
//            @Result(property = "merchantNum", column = "merchant_num", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
//            @Result(property = "deviceNum", column = "device_num", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "totalPaid", column = "total_paid", javaType = Float.class, jdbcType = JdbcType.DECIMAL),
            @Result(property = "totalUsers", column = "total_users", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "vipUsers", column = "vip_users", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "payUsers", column = "pay_users", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "pkgDays", column = "pkg_days", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "pkgMonths", column = "pkg_months", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "pkgYears", column = "pkg_years", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "createDate", column = "create_date", javaType = Date.class, jdbcType = JdbcType.DATE),
            @Result(property = "area", column = "area", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            
    })
    @SelectProvider(type= MerchantStatisticsSql.class, method = "getListByToday")
    public List<MerchantStatistics> getListByToday();
    
    /**
     * 删除
     * @param id
     * @return
     * @author 余红伟 
     * @date 2017年5月23日 上午9:52:39
     */
//    @DeleteProvider(type = MerchantStatisticsSql.class, method = "deleteById")
    @Delete("delete from center_pub_merchant_statistics where id = #{id}")
    public Integer deleteById(Long id);
}

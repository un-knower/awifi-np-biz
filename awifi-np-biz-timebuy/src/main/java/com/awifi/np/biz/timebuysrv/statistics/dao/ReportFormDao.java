/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月23日 上午10:41:45
* 创建作者：余红伟
* 文件名称：ReportFormDao.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.statistics.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.timebuysrv.statistics.dao.sql.ReportFormSql;
@Service
public interface ReportFormDao {
    /**
     * 统计表插入数据
     * @param sql
     * @return
     * @author 余红伟 
     * @date 2017年7月28日 下午6:31:51
     */
    @InsertProvider(type = ReportFormSql.class, method = "insertStatistics")
    public Integer insertStatistics(String sql);
    /**
     * 统计表更新数据
     * @param sql
     * @return
     * @author 余红伟 
     * @date 2017年7月28日 下午6:32:10
     */
    @UpdateProvider(type = ReportFormSql.class, method = "updateStatistics")
    public Integer updateStatistics(String sql);
    /**
     * 统计表删除今天的数据
     * @param statisticsTableName
     * @param statisticsCondition
     * @return
     * @author 余红伟 
     * @date 2017年7月28日 下午6:32:21
     */
    @DeleteProvider(type = ReportFormSql.class, method = "deleteToday")
    public Integer deleteToday(String statisticsTableName, String statisticsCondition);
    /**
     * 查询今天统计表数据条数
     * @param statisticsTableName
     * @param statisticsCondition
     * @return
     * @author 余红伟 
     * @date 2017年7月28日 下午6:32:36
     */
    @SelectProvider(type = ReportFormSql.class, method = "getCountByToday")
    public Integer getCountByToday(String statisticsTableName, String statisticsCondition);
    /**
     * 查询统计结果
     * @param sql
     * @return
     * @author 余红伟 
     * @date 2017年7月28日 下午6:34:33
     */
    @SelectProvider(type = ReportFormSql.class, method = "queryListByParam")
    public List<HashMap<String, Object>> queryListByParam(String sql);
    
    /**
     * 查询出所有的groupby字段值 供javaMethod 调用
     * @param statisticsTableName 表明
     * @param statisticsGroupBy group by 字段
     * @return List<Long>
     * @author 张智威  
     * @date 2017年8月14日 上午10:33:09
     */
    @SelectProvider(type = ReportFormSql.class, method = "getGroupByValueList")
    List<Long> getGroupByValueList(String statisticsTableName, String statisticsGroupBy);

}

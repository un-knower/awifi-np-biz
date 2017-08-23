/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月23日 上午10:02:19
* 创建作者：余红伟
* 文件名称：ReportFormSql.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.statistics.dao.sql;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportFormSql {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 统计表插入数据
     * @param sql
     * @return
     * @author 余红伟 
     * @date 2017年7月28日 下午6:35:15
     */
    public String insertStatistics(String sql){
        logger.debug("sql: " + sql);
        if(StringUtils.isNotBlank(sql)){
            return sql;
        }
        return null;
    }
    /**
     * 统计表更新数据
     * @param sql
     * @return
     * @author 余红伟 
     * @date 2017年7月28日 下午6:35:19
     */
    public String updateStatistics(String sql){
        logger.debug("sql: " + sql);
        if(StringUtils.isNotBlank(sql)){
            return sql;
        }
        return null;
    }
    /**
     * 查询今天统计表数据条
     * @param statisticsTableName
     * @param statisticsCondition
     * @return
     * @author 余红伟 
     * @date 2017年7月28日 下午6:35:24
     */
    public String getCountByToday(String statisticsTableName,String statisticsCondition){
        StringBuffer sql = new StringBuffer();
        sql.append("select count(*) from ").append(statisticsTableName).append(" where ").append(statisticsCondition);
        logger.debug("sql :" + sql.toString());
        return sql.toString();
    }
    /**
     * 统计表删除今天的数据
     * @param statisticsTableName
     * @param statisticsCondition
     * @return
     * @author 余红伟 
     * @date 2017年7月28日 下午6:35:27
     */
    public String deleteToday(String statisticsTableName,String statisticsCondition){
        StringBuffer sql = new StringBuffer();
        sql.append("delete from ").append(statisticsTableName).append(" where ").append(statisticsCondition);
        logger.debug("sql :" + sql.toString());
        return sql.toString();
    }
    /**
     * 查询统计结果
     * @param sql
     * @return
     * @author 余红伟 
     * @date 2017年7月28日 下午6:35:32
     */
    public String queryListByParam(String sql){
        logger.debug("查询sql: " + sql);
        if(StringUtils.isNotBlank(sql)){
            return sql;
        }
        return null;
    }
    
    
    public String getGroupByValueList(String statisticsTableName,String statisticsGroupBy){
        StringBuffer sql = new StringBuffer();
        sql.append("select ").append(statisticsGroupBy).append( " from ").append(statisticsTableName).append(" where create_date > curdate() ");
        logger.debug("sql :" + sql.toString());
        return sql.toString();
    }
}

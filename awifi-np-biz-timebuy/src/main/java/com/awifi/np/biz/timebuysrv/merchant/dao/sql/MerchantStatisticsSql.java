/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月4日 下午7:29:07
* 创建作者：余红伟
* 文件名称：MerchantStatisticsSql.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.merchant.dao.sql;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MerchantStatisticsSql {
    /**
     * logger
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 插入统计数据(昨天的)
     * @return
     * @author 余红伟 
     * @date 2017年5月11日 下午3:10:53
     */
    public String insertStatistics(){
        //插入部分字段数据, 先插入商户merchant_id, 然后再更新时插入其他字段数据
        StringBuffer sql = new StringBuffer("insert into center_pub_merchant_statistics(merchant_id,create_date) ");
        sql.append("select c.merchant_id,now() from center_pub_merchant_user_consume c ");
        //凌晨统计，昨天0点开始至今天凌晨0点的消费记录
        sql.append(" where c.create_date between curdate() - INTERVAL 1 day and curdate()");
        sql.append(" group by c.merchant_id");
        
        logger.debug("sql.toString(): "+ sql.toString());
        return sql.toString();
    }
    /**
     * 更新插入数据,插入最大值
     * @return
     * @author 余红伟 
     * @date 2017年5月11日 下午3:21:21
     */
    public String updateStatistics(){
        StringBuffer sql = new StringBuffer("update center_pub_merchant_statistics s set s.total_users=");
        //用户总人数
        sql.append("(select count(distinct user_id) from center_pub_merchant_user_consume c where c.merchant_id = s.merchant_id ),");
        
        //付费总人数
        sql.append("s.pay_users=");
        sql.append("(select count(distinct user_id) from center_pub_merchant_user_consume c where c.merchant_id = s.merchant_id and c.consume_type=3 ), ");
       
        //包天数量
        sql.append("s.pkg_days=");
        sql.append("(select count(distinct user_id) from center_pub_merchant_user_consume c where c.merchant_id = s.merchant_id and c.pkg_detail='days'  ),");
        //包月数量
        sql.append("s.pkg_months=");
        sql.append("(select count(distinct user_id) from center_pub_merchant_user_consume c where c.merchant_id = s.merchant_id and c.pkg_detail='months'  ),");
        //包年数量
        sql.append("s.pkg_years=");
        sql.append("(select count(distinct user_id) from center_pub_merchant_user_consume c where c.merchant_id = s.merchant_id and c.pkg_detail='years' ),");
        //付费金额
        sql.append("s.total_paid = ");
        sql.append("(select sum(c.pay_num) from center_pub_merchant_user_consume c where c.merchant_id = s.merchant_id ),");
        //VIP用户数
        sql.append("s.vip_users = ");
        sql.append("(select count(distinct u.id) from center_pub_merchant_user_consume c left join center_merchant_vip_user u on c.merchant_id = u.merchant_id where c.merchant_id = s.merchant_id )");
        //更新刚刚插入的记录
        sql.append(" where s.create_date > curdate()");//2017-09-10 11:21:21 > 2017-09-10 00:00:00
        logger.debug("sql.toString(): "+sql.toString());
        return sql.toString();
    }
    /**
     * 根据省市区查询商户园区统计表
     * @param map
     * @return
     * @author 余红伟 
     * @date 2017年5月11日 下午3:49:38
     */
    public String queryStatistics(Map map){
        Object province = map.get("province");
        Object city = map.get("city");
        Object county = map.get("county");
        String startTime = (String) map.get("startTime");
        String endTime = (String) map.get("endTime");
        Object start = map.get("start");
        Object pageSize = map.get("pageSize");
        //凭借sql语句，省市区全无，按provinceId分组，含有省：按cityId分组；含市：countyId分组；含区：按merchant_id分组
        StringBuffer sql = new StringBuffer("select s.id,");
        if(province != null){
            sql.append("m.cityId as city,m.city as area,");
        }
        if(city !=null){
            sql.append("m.countyId as county,m.area as area,");
        }
        if(county !=null){
            sql.append("m.merchantName as area,");
        }
        if(province == null && city == null && county == null){
            sql.append("m.provinceId as province,m.province as area,");
        }
        //查询 商户Id,总付费金额，总使用人数，总vip数，总付费人数，总包日包月包年人数
        sql.append("s.merchant_id as merchant_id,sum(total_paid) as total_paid,max(total_users) as total_users,max(vip_users) as vip_users,")
                .append("max(pay_users) as pay_users,max(pkg_days) as pkg_days,max(pkg_months) as pkg_months,max(pkg_years) as pkg_years,s.create_date as create_date ");
        sql.append("from center_pub_merchant_statistics s left join merchant m  on s.merchant_id = m.id ");

        //merchant表含有省市区
        if(province !=null){
            sql.append(" and m.provinceId = #{province} ");
        }
        if(city != null){
            sql.append(" and m.cityId = #{city} ");
        }
        if(county !=null){
            sql.append(" and m.areaId = #{county} ");
        }
        //where拼接
        sql.append(" where 1=1 ");
        //查询时间参数变得不是那么重要，会影响的大概有总付费金额
        if(!StringUtils.isBlank(startTime)){
            sql.append(" and s.create_date > #{startTime}");
        }
        if(!StringUtils.isBlank(endTime)){
            sql.append(" and s.create_date < #{endTime}");
        }
        if(province !=null){
            sql.append(" group by m.cityId");
        }
        if(city != null){
            sql.append(" group by m.countyId");
        }
        if(county !=null){
            sql.append(" group by s.merchant_id");
        }
        //首次进入按省分组，显示数据
        if(province == null && city == null && county == null){
            sql.append(" group by m.provinceId ");
        }
        if(start!=null && pageSize!=null){
            sql.append(" limit #{start},#{pageSize}");
        }
        logger.debug("sql.toString(): " + sql.toString()); 
        return sql.toString();
    }
    /**
     * 根据条件统计总条数
     * @param map
     * @return
     * @author 余红伟 
     * @date 2017年5月22日 上午9:58:46
     */
    public String countByParams(Map map){
        Object province = map.get("province");
        Object city = map.get("city");
        Object county = map.get("county");
        String startTime = (String) map.get("startTime");
        String endTime = (String) map.get("endTime");
        Object start = map.get("start");
        Object pageSize = map.get("pageSize");
        //凭借sql语句，省市区全无，按provinceId分组，含有省：按cityId分组；含市：countyId分组；含区：按merchant_id分组
        StringBuffer sql = new StringBuffer("select count(*) ");
        
       
        sql.append("from center_pub_merchant_statistics s left join merchant m  on s.merchant_id = m.id ");

        //merchant表含有省市区
        if(province !=null){
            sql.append(" and m.provinceId = #{province} ");
        }
        if(city != null){
            sql.append(" and m.cityId = #{city} ");
        }
        if(county !=null){
            sql.append(" and m.areaId = #{county} ");
        }
        //where拼接
        sql.append(" where 1=1 ");
        //查询时间参数变得不是那么重要，会影响的大概有总付费金额
        if(!StringUtils.isBlank(startTime)){
            sql.append(" and s.create_date > #{startTime}");
        }
        if(!StringUtils.isBlank(endTime)){
            sql.append(" and s.create_date < #{endTime}");
        }
        if(province !=null){
            sql.append(" group by m.cityId");
        }
        if(city != null){
            sql.append(" group by m.countyId");
        }
        if(county !=null){
            sql.append(" group by s.merchant_id");
        }
        //首次进入按省分组，显示数据
        if(province == null && city == null && county == null){
            sql.append(" group by m.provinceId ");
        }
      
        logger.debug("sql.toString(): " + sql.toString()); 
        return sql.toString();
    }
    /**
     * 获取今天的统计数据
     * @return
     * @author 余红伟 
     * @date 2017年5月23日 上午9:49:09
     */
    public String getListByToday(){
        StringBuffer sql = new StringBuffer("select id,merchant_id,total_paid,total_users,vip_users,pay_users,pkg_days,pkg_months,"
                + "pkg_years,create_date from center_pub_merchant_statistics ");
        sql.append(" where create_date > curdate()");
        logger.debug("sql.toString(): " + sql.toString()); 
        return sql.toString();
    }
}

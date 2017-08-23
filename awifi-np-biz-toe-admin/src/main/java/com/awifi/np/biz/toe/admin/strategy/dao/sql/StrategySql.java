/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月19日 下午1:47:51
* 创建作者：周颖
* 文件名称：StrategySql.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.toe.admin.strategy.dao.sql;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.awifi.np.biz.common.util.SqlUtil;

public class StrategySql {

    /**
     * 批量获取站点下的策略数量
     * @param params 参数
     * @return 策略数
     * @author 周颖  
     * @date 2017年4月19日 下午1:41:17
     */
    public String getTotalBySiteIds(Map<String, Object> params){
        StringBuffer sql = new StringBuffer();
        sql.append("select fk_site_id,count(pk_id) as num from toe_strategy where delete_flag=1 ");
        Long[] siteIds = (Long[]) params.get("siteIds");
        SqlUtil.in("fk_site_id", "siteIds", siteIds, sql);
        sql.append("group by fk_site_id");
        return sql.toString();
    }
    
    /**
     * 站点策略列表
     * @param params 参数
     * @return 总数sql
     * @author 周颖  
     * @date 2017年4月27日 下午4:11:35
     */
    public String getCountByParam(Map<String, Object> params){
        StringBuffer sql = new StringBuffer();
        sql.append("select count(pk_id) from toe_strategy where fk_site_id=#{siteId} and delete_flag=1 ");
        String strategyName = (String) params.get("strategyName");
        if(StringUtils.isNotBlank(strategyName)){
            sql.append("and strategyName like concat('%',#{strategyName},'%')");
        }
        return sql.toString();
    }
   
    /**
     * 策略列表
     * @param params 参数
     * @return 列表sql
     * @author 周颖  
     * @date 2017年4月27日 下午4:22:01
     */
    public String getListByParam(Map<String, Object> params){
        StringBuffer sql = new StringBuffer();
        sql.append("select pk_id,strategyName,strategy_type,order_no,from_unixtime(start_date,'%Y-%m-%d') as start_date,from_unixtime(end_date,'%Y-%m-%d') as end_date "
                + "from toe_strategy where fk_site_id=#{siteId} and delete_flag=1 ");
        String strategyName = (String) params.get("strategyName");
        if(StringUtils.isNotBlank(strategyName)){
            sql.append("and strategyName like concat('%',#{strategyName},'%') ");
        }
        sql.append("order by order_no desc limit #{begin},#{pageSize}");
        return sql.toString();
    }
    
    /**
     * 获取站点id -- sql
     * @param params 参数
     * @return sql
     * @author 许小满  
     * @date 2017年5月11日 下午6:22:52
     */
    public String getSiteId(Map<String, Object> params) {
        StringBuffer sql = new StringBuffer(415);
        sql.append("select s.fk_site_id ")
           .append("from toe_strategy s ")
           .append("where s.delete_flag=1 ")
              .append("and s.fk_customer_id=#{merchantId} ")
              .append("and s.start_date <= unix_timestamp(now()) ")
              .append("and s.end_date >= unix_timestamp(now()) ")
              .append("and (")
                  .append("s.strategy_type=1 ")
                  .append("or exists (select si.pk_id from toe_strategy_item si where s.pk_id=si.fk_strategy_id ");
        String ssid = (String)params.get("ssid");//ssid
        String devId = (String)params.get("devId");//设备id
        boolean ssidNotNull = StringUtils.isNotBlank(ssid);//ssid 非空
        boolean devIdNotNull = StringUtils.isNotBlank(devId);//设备id 非空
        //1. ssid 不为空、设备id 为空
        if(ssidNotNull && !devIdNotNull){
            sql.append("and (s.strategy_type=2 and si.content=#{ssid})");
        }
        //2. ssid 为空、设备id 不为空
        else if(!ssidNotNull && devIdNotNull){
            sql.append("and (s.strategy_type=3 and si.content=#{devId})");
        }
        //3. ssid、设备id 都不为空
        else if(ssidNotNull && devIdNotNull){
            sql.append("and ((s.strategy_type=2 and si.content=#{ssid}) or (s.strategy_type=3 and si.content=#{devId}))");
        }
        //4. ssid、设备id 都为空
        else if(!ssidNotNull && !devIdNotNull){
        }
        sql.append(")")
            .append(") order by s.order_no desc limit 1");
        //System.out.println("sql.length = " + sql.length());
        return sql.toString();
    }
}

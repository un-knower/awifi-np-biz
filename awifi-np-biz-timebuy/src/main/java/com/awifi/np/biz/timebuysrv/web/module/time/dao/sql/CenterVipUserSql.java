/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月18日 下午6:39:01
* 创建作者：余红伟
* 文件名称：CenterOnlineUserSql.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.web.module.time.dao.sql;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.timebuysrv.web.module.time.model.VipUserObject;

public class CenterVipUserSql {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());
    
    /**
     * 新增推送用户
     * 
     * @param onlineUserObject OnlineUserObject
     * @return String
     * @author 余红伟 
     * @date 2017年4月18日 下午7:35:02
     */
    public String add(VipUserObject onlineUserObject){
        logger.debug("params:" + JSON.toJSONString(onlineUserObject));

        StringBuffer sql = new StringBuffer("insert into center_merchant_vip_user(");
        Object telephone = onlineUserObject.getTelephone();
        Object processFlg = onlineUserObject.getProcessFlg();
        Object startTime = onlineUserObject.getStartTime();
        Object endTime = onlineUserObject.getEndTime();
        Object createDate = onlineUserObject.getCreateDate();
        Object modifyDate = onlineUserObject.getModifyDate();
        Object merchantId = onlineUserObject.getMerchantId();
        
        if(telephone !=null){
            sql.append("telephone, ");
        }
        if(processFlg !=null){
            sql.append("process_flg, ");
        }
        if(startTime !=null){
            sql.append("start_time, ");
        }
        if(endTime !=null){
            sql.append("end_time, ");
        }
        if(createDate !=null){
            sql.append("create_date, ");
        }
        if(modifyDate !=null){
            sql.append("modify_date, ");
        }
        if(merchantId !=null){
            sql.append("merchant_id ");
        }
        
        sql.append(") values (");
        
        if(telephone !=null){
            sql.append("#{telephone}, ");
        }
        if(processFlg !=null){
            sql.append("#{processFlg}, ");
        }
        if(startTime !=null){
            sql.append("#{startTime}, ");
        }
        if(endTime !=null){
            sql.append("#{endTime}, ");
        }
        if(createDate !=null){
            sql.append("#{createDate}, ");
        }
        if(modifyDate !=null){
            sql.append("#{modifyDate}, ");
        }
        if(merchantId !=null){
            sql.append("#{merchantId} ");
        }
        sql.append(")");
        
        logger.debug("sql.toString()= "+ sql.toString());
        return sql.toString();
    }
    
    /**
     * 更新推送用户数据
     * 
     * @param onlineUserObject VipUserObject
     * @return String
     * @author 余红伟 
     * @date 2017年4月18日 下午8:26:58
     */
    public String update(VipUserObject onlineUserObject){
        logger.debug("params:" + JSON.toJSONString(onlineUserObject));
        
        StringBuffer sql = new StringBuffer("update center_merchant_vip_user set");
        StringBuffer condition = new StringBuffer();
        
        Object id = onlineUserObject.getId();
        Object telephone = onlineUserObject.getTelephone();
        Object processFlg = onlineUserObject.getProcessFlg();
        Object startTime = onlineUserObject.getStartTime();
        Object endTime = onlineUserObject.getEndTime();
        Object createDate = onlineUserObject.getCreateDate();
        Object modifyDate = onlineUserObject.getModifyDate();
        Object merchantId = onlineUserObject.getMerchantId();
        
        if(telephone !=null){
            condition.append(" telephone=#{telephone},");
        }
        if(processFlg !=null){
            condition.append(" process_flg=#{processFlg},");
        }
        if(startTime !=null){
            condition.append(" start_time=#{startTime},");
        }
        if(endTime !=null){
            condition.append(" end_time=#{endTime},");
        }
        if(createDate !=null){
            condition.append(" create_date=#{createDate},");
        }
        if(modifyDate !=null){
            condition.append(" modify_date=#{modifyDate},");
        }
        if(merchantId !=null){
            condition.append(" merchant_id=#{merchantId},");
        }
        
        if(id !=null && condition.toString().length() >0){
            sql.append(condition.deleteCharAt(condition.length() - 1));
            sql.append(" where id=#{id}");
        }
        
        logger.debug("sql.toString()= " + sql.toString());
        return sql.toString();
    }
    
    /**
     * 根据手机号、时间查询有效记录数
     * 
     * @param params Map
     * @return String
     * @author 余红伟 
     * @date 2017年4月19日 上午11:08:46
     */
    public String queryOnlineUserCount(Map<String, Object> params){
        logger.debug("params:" + JSON.toJSONString(params));
        
        StringBuffer sql = new StringBuffer("select count(1) from center_merchant_vip_user ");
        //Object telephone = params.get("telephone");
        Object nowTime = params.get("nowTime");
        sql.append("where telephone=#{telephone} ");
        
        if(nowTime !=null && (Long)nowTime > 0){
            sql.append("and start_time <= #{nowTime} ")
                .append("and (end_time is null or end_time >= #{nowTime})");
        }
        
        logger.debug("sql.toString()=" + sql.toString());
        return sql.toString();
    }
    
    /**
     * 根据手机号查询最新记录
     * 
     * @param params Map
     * @return String
     * @author 余红伟 
     * @date 2017年4月19日 下午2:03:14
     */
    public String queryLastOnlineUser(Map<String, Object> params){
        logger.debug("params:" + JSON.toJSONString(params));
        
        StringBuffer sql = new StringBuffer("select id,telephone,start_time,end_time,create_date from center_merchant_vip_user ");
       // Object telephone = params.get("telephone");
        sql.append("where telephone=#{telephone} ");
        sql.append("order by create_date desc limit 1");
        
        logger.debug("sql.toString()=" + sql.toString());
        return sql.toString();
        
    }
    /**
     * 根据条件查询电渠用户数据列表
     * 
     * @param params Map
     * @return String
     * @author 余红伟 
     * @date 2017年4月19日 下午2:45:01
     */
    public String queryListByMerArea(Map<String, Object> params){
        logger.debug("params:" + JSON.toJSONString(params));
        
        StringBuffer sql = new StringBuffer("select v.id,v.telephone,v.start_time,v.end_time,v.create_date,v.merchant_id,v.modify_date,")
                .append("m.provinceId,m.cityId,m.areaId,m.merchantName merchant_name,")
                .append("p.AREA_NAME province_name,")
                .append("c.AREA_NAME city_name,")
                .append("x.AREA_NAME county_name ")
                .append("from center_merchant_vip_user v left join merchant m ")
                .append("on v.merchant_id = m.id ")
                .append("left join center_pub_area p on m.provinceId = p.id ")
                .append("left join center_pub_area c on m.cityId = c.id ")
                .append("left join center_pub_area x on m.areaId = x.id ")
                .append("where 1=1 ");
        Object telephone = params.get("telephone");
        Object startTime = params.get("startTime");
        Object endTime = params.get("endTime");
        Object province = params.get("province");
        Object city = params.get("city");
        Object county = params.get("county");
        Object merchantId = params.get("merchantId");
        Object merchantName = params.get("merchantName");
        Object start = params.get("start");
        Object pageSize = params.get("pageSize");
        
        if(telephone != null){
            sql.append("and v.telephone = #{telephone} ");
        }
        if(startTime !=null){
            sql.append("and UNIX_TIMESTAMP(v.create_date) >= #{startTime} ");
        }
        if(endTime !=null){
            sql.append("and UNIX_TIMESTAMP(v.create_date) < #{endTime} ");
        }
        if(province !=null){
            sql.append("and m.provinceId = #{province} ");
        }
        if(city !=null){
            sql.append("and m.cityId = #{city} ");
        }
        if(county !=null){
            sql.append("and m.areaId = #{county} ");
        }
        if(merchantId !=null){
            sql.append("and v.merchant_id = #{merchantId} ");
        }
        if(merchantName !=null){
            sql.append("and m.merchantName like CONCAT(#{merchantName},'%') ");
        }
        sql.append("order by v.id desc ");
        if(start !=null && pageSize !=null){
            sql.append("limit ${start}, ${pageSize}");
        }
        logger.debug("sql.toString()=" + sql.toString());
        return sql.toString();
    }
    /**
     * 根据条件查询电渠用户数据列表
     * 
     * @param params Map
     * @return String
     * @author 余红伟 
     * @date 2017年4月19日 下午2:59:01
     */
    public String queryCountByMerArea(Map<String, Object> params){
        logger.debug("params:" + JSON.toJSONString(params));
        
        StringBuffer sql = new StringBuffer("select count(1) from center_merchant_vip_user v left join merchant m ")
                .append("on v.merchant_id = m.id ")
                .append("left join center_pub_area p on m.provinceId = p.id ")
                .append("left join center_pub_area c on m.cityId = c.id ")
                .append("left join center_pub_area x on m.areaId = x.id ")
                .append("where 1=1 ");
        Object telephone = params.get("telephone");
        Object startTime = params.get("startTime");
        Object endTime = params.get("endTime");
        Object province = params.get("province");
        Object city = params.get("city");
        Object county = params.get("county");
        Object merchantId = params.get("merchantId");
        Object merchantName = params.get("merchantName");

        if(telephone != null){
            sql.append("and v.telephone = #{telephone} ");
        }
        if(startTime !=null){
            sql.append("and UNIX_TIMESTAMP(v.create_date) >= #{startTime} ");
        }
        if(endTime !=null){
            sql.append("and UNIX_TIMESTAMP(v.create_date) < #{endTime} ");
        }
        if(province !=null){
            sql.append("and m.provinceId = #{province} ");
        }
        if(city !=null){
            sql.append("and m.cityId = #{city} ");
        }
        if(county !=null){
            sql.append("and m.areaId = #{county} ");
        }
        if(merchantId !=null){
            sql.append("and v.merchant_id = #{merchantId} ");
        }
        if(merchantName !=null){
            sql.append("and m.merchantName like CONCAT(#{merchantName},'%') ");
        }
        
        logger.debug("sql.toString()= "+ sql.toString());
        return sql.toString();
    }
    /**
     * 更新商户id
     * @param onlineUserObject VipUserObject
     * @return String
     * @author 余红伟 
     * @date 2017年4月19日 上午9:38:50
     */
    public String updateVipUser(VipUserObject onlineUserObject){
        logger.debug("params:" + JSON.toJSONString(onlineUserObject));
        
        StringBuffer sql = new StringBuffer("update center_merchant_vip_user set");
        StringBuffer condition = new StringBuffer();
        
        Object merchantId = onlineUserObject.getMerchantId();
        Object telephone = onlineUserObject.getTelephone();
        
        if(merchantId !=null){
            condition.append(" merchant_id=#{merchantId},");
        }
        if(telephone !=null && condition.toString().length() >0){
            sql.append(condition.deleteCharAt(condition.length() - 1));
            sql.append(" where telephone=#{telephone} and merchant_id is null");
            
        }
        
        logger.debug("sql.toString()=" + sql.toString());
        return sql.toString();
    }
    /**
     * 查询VIP用户列表，分页
     * 
     * @param params Map
     * @return String
     * @author 余红伟 
     * @date 2017年4月24日 下午4:13:27
     */
    public String queryListByParam(Map<String, Object> params){
        logger.debug("params: " + JSON.toJSONString(params));
        StringBuffer sql = new StringBuffer("select * from center_merchant_vip_user u left join merchant m on u.merchant_id = m.id where 1=1 ");
        Long telephone = CastUtil.toLong(MapUtils.getString(params, "telephone"));
        Long merchantId = CastUtil.toLong(MapUtils.getString(params, "merchantId"));
        Integer provinceId = CastUtil.toInteger(MapUtils.getString(params, "provinceId"));
        Integer cityId = CastUtil.toInteger(MapUtils.getString(params, "cityId"));
        Integer areaId = CastUtil.toInteger(MapUtils.getString(params, "areaId"));
        Long startTime =  CastUtil.toLong(MapUtils.getString(params, "startTime"));
        Long endTime = CastUtil.toLong(MapUtils.getString(params, "endTime"));
        Integer pageNum = CastUtil.toInteger(MapUtils.getString(params, "pageNum"));
        Integer pageSize = CastUtil.toInteger(MapUtils.getString(params, "pageSize"));
        
        if(telephone!=null){
            sql.append("and u.telephone=#{telephone} ");
        }
        if(merchantId !=null){
            sql.append("and u.merchant_id=#{merchantId} ");
        }
        if(provinceId != null){
            sql.append(" and m.provinceId=#{provinceId}");
        }
        if(cityId !=null){
            sql.append(" and m.cityId=#{cityId}");
        }
        if(areaId !=null){
            sql.append(" and areaId=#{areaId}");
        }
        if(startTime != null){
            sql.append("and u.start_time>#{startTime} ");
        }
        if(endTime != null){
            sql.append("and end_time<#{endTime} ");
        }
        if(pageNum != null && pageSize != null){
            sql.append("limit #{start},")
                .append("#{pageSize}");
        }
        logger.debug("sql.toString()=" + sql.toString());
        return sql.toString();
    }
    /**
     * 查询vip条数
     * @param params
     * @return
     * @author 余红伟 
     * @date 2017年8月11日 上午10:57:35
     */
    public String queryVipUserCount(Map<String, Object> params){
        logger.debug("params: " + JSON.toJSONString(params));
        StringBuffer sql = new StringBuffer("select count(1) from center_merchant_vip_user u left join merchant m on u.merchant_id = m.id where 1=1 ");
        Long telephone = CastUtil.toLong(MapUtils.getString(params, "telephone"));
        Long merchantId = CastUtil.toLong(MapUtils.getString(params, "merchantId"));
        Integer provinceId = CastUtil.toInteger(MapUtils.getString(params, "provinceId"));
        Integer cityId = CastUtil.toInteger(MapUtils.getString(params, "cityId"));
        Integer areaId = CastUtil.toInteger(MapUtils.getString(params, "areaId"));
        Long startTime =  CastUtil.toLong(MapUtils.getString(params, "startTime"));
        Long endTime = CastUtil.toLong(MapUtils.getString(params, "endTime"));
        Integer pageNum = CastUtil.toInteger(MapUtils.getString(params, "pageNum"));
        Integer pageSize = CastUtil.toInteger(MapUtils.getString(params, "pageSize"));
        
        if(telephone!=null){
            sql.append("and u.telephone=#{telephone} ");
        }
        if(merchantId !=null){
            sql.append("and u.merchant_id=#{merchantId} ");
        }
        if(provinceId != null){
            sql.append(" and m.provinceId=#{provinceId}");
        }
        if(cityId !=null){
            sql.append(" and m.cityId=#{cityId}");
        }
        if(areaId !=null){
            sql.append(" and areaId=#{areaId}");
        }
        if(startTime != null){
            sql.append("and u.start_time>#{startTime} ");
        }
        if(endTime != null){
            sql.append("and end_time<#{endTime} ");
        }
      
        logger.debug("sql.toString()=" + sql.toString());
        return sql.toString();
    }
    /**
     * 根据手机号查询此时此刻(start_tiem/end_time)它是否是VIP用户
     * @param telephone Long
     * @return String
     * @author 余红伟 
     * @date 2017年4月26日 下午5:29:57
     */
    public String isVipUser(Long telephone){
        logger.debug("params:" + telephone);
        StringBuffer sql = new StringBuffer("select count(1) from center_merchant_vip_user ")
                .append("where telephone=#{telephone} ")    //数据库的时间戳是秒数  而且通常endTime都是空的 只有逻辑删除了才会设定结束时间
                .append("and unix_timestamp(now())  between start_time and IFNULL(end_time,2000000000)"); //此时此刻处于开始时间和结束时间之间,暂定13位时间戳，数据库写入时最好也是13位  
        
        logger.debug("sql.toString()=" + sql.toString());
        return sql.toString();
    }
}

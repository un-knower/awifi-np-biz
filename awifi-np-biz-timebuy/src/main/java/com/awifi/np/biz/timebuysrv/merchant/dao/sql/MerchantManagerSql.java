/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月25日 上午11:27:12
* 创建作者：尤小平
* 文件名称：MerchantManagerSql.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.merchant.dao.sql;

import com.alibaba.fastjson.JSON;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantManager;
import org.apache.log4j.Logger;

import java.util.Map;

public class MerchantManagerSql {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * 查询MerchantManager列表.
     * 
     * @param merchantManager MerchantManager
     * @return String
     * @author 尤小平
     * @date 2017年4月25日 上午11:35:25
     */
    public String getListByMerchantManager(MerchantManager merchantManager) {
        logger.debug("params:" + JSON.toJSONString(merchantManager));

        StringBuffer sql = new StringBuffer("select id, uid, mid, uname, type from merchant_manager where 1=1 ");

        Object id = merchantManager.getId();
        Object uid = merchantManager.getUid();
        Object mid = merchantManager.getMid();
        Object uname = merchantManager.getUname();
        Object type = merchantManager.getType();

        if (id != null) {
            sql.append("and id = #{id} ");
        }
        if (uid != null) {
            sql.append("and uid = #{uid} ");
        }
        if (mid != null) {
            sql.append("and mid = #{mid} ");
        }
        if (uname != null) {
            sql.append("and uname like concat('%',#{uname},'%') ");
        }
        if (type != null) {
            sql.append("and type = #{type} ");
        }

        logger.debug("sql.toString()= " + sql.toString());
        return sql.toString();
    }

    /**
     * 查询MerchantManager列表，分页.
     * 
     * @param params Map<String, Object>
     * @return String
     * @author 尤小平
     * @date 2017年4月27日 下午8:23:00
     */
    public String getListByParam(Map<String, Object> params) {
        logger.debug("getListByParam params:" + JSON.toJSONString(params));

        StringBuffer sql = new StringBuffer("select id, uid, mid, uname, type from merchant_manager where 1=1 ");

        MerchantManager merchantManager = (MerchantManager) params.get("merchantManager");
        Object id = merchantManager.getId();
        Object type = merchantManager.getType();
        Object uid = merchantManager.getUid();
        Object uname = merchantManager.getUname();
        Object mid = merchantManager.getMid();

        if (id != null) {
            sql.append("and id = #{merchantManager.id} ");
        }
        if (type != null) {
            sql.append("and type = #{merchantManager.type} ");
        }
        if (uid != null) {
            sql.append("and uid = #{merchantManager.uid} ");
        }
        if (uname != null) {
            sql.append("and uname like concat('%',#{merchantManager.uname},'%') ");
        }
        if (mid != null) {
            sql.append("and mid = #{merchantManager.mid} ");
        }
        sql.append("order by id desc limit #{page.begin}, #{page.pageSize}");

        logger.debug("sql.toString()= " + sql.toString());
        return sql.toString();
    }

    /**
     * 查询MerchantManager列表总条数.
     * 
     * @param merchantManager MerchantManager
     * @return String
     * @author 尤小平
     * @date 2017年4月27日 下午8:23:25
     */
    public String getCountByParams(MerchantManager merchantManager) {
        logger.debug("params:" + JSON.toJSONString(merchantManager));

        StringBuffer sql = new StringBuffer("select count(*) from merchant_manager where 1=1 ");
        StringBuffer condition = new StringBuffer("");

        Object id = merchantManager.getId();
        Object uname = merchantManager.getUname();
        Object uid = merchantManager.getUid();
        Object mid = merchantManager.getMid();
        Object type = merchantManager.getType();

        if (id != null) {
            condition.append("and id = #{id} ");
        }
        if (uid != null) {
            condition.append("and uid = #{uid} ");
        }
        if (mid != null) {
            condition.append("and mid = #{mid} ");
        }
        if (uname != null) {
            condition.append("and uname like concat('%',#{uname},'%') ");
        }
        if (type != null) {
            condition.append("and type = #{type} ");
        }
        sql.append(condition.toString());

        logger.debug("sql.toString()= " + sql.toString());
        return sql.toString();
    }

    /**
     * 插入.
     * 
     * @param merchantManager MerchantManager
     * @return String
     * @author 尤小平
     * @date 2017年4月27日 下午8:23:45
     */
    public String insert(MerchantManager merchantManager) {
        logger.debug("insert params:" + JsonUtil.toJson(merchantManager));

        StringBuffer sql = new StringBuffer("insert into merchant_manager(");

        if (merchantManager.getUname() != null) {
            sql.append("uname,");
        }
        if (merchantManager.getMid() != null) {
            sql.append("mid,");
        }
        if (merchantManager.getUid() != null) {
            sql.append("uid,");
        }
        if (merchantManager.getType() != null) {
            sql.append("type,");
        }
        sql.deleteCharAt(sql.toString().length() - 1);
        sql.append(") values (");

        if (merchantManager.getUname() != null) {
            sql.append("#{uname},");
        }
        if (merchantManager.getMid() != null) {
            sql.append("#{mid},");
        }
        if (merchantManager.getUid() != null) {
            sql.append("#{uid},");
        }
        if (merchantManager.getType() != null) {
            sql.append("#{type},");
        }
        sql.deleteCharAt(sql.toString().length() - 1);
        sql.append(")");

        logger.debug("sql.toString()= " + sql.toString());
        return sql.toString();
    }

    /**
     * 修改.
     * 
     * @param merchantManager
     *            MerchantManager
     * @return String
     * @author 尤小平
     * @date 2017年4月27日 下午8:24:01
     */
    public String update(MerchantManager merchantManager) {
        logger.debug("update params:" + JsonUtil.toJson(merchantManager));

        StringBuffer sql = new StringBuffer("update merchant_manager set ");

        if (merchantManager.getUname() != null) {
            sql.append("uname=#{uname},");
        }
        if (merchantManager.getMid() != null) {
            sql.append("mid=#{mid},");
        }
        if (merchantManager.getUid() != null) {
            sql.append("uid=#{uid},");
        }
        if (merchantManager.getType() != null) {
            sql.append("type=#{type},");
        }
        sql.deleteCharAt(sql.toString().length() - 1);

        if (merchantManager.getId() != null) {
            sql.append(" where id=#{id}");
        }

        logger.debug("sql.toString()= " + sql.toString());
        return sql.toString();
    }
}

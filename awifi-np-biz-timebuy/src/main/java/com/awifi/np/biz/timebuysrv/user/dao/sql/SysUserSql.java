/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年4月14日 下午2:15:25
 * 创建作者：尤小平
 * 文件名称：SysUserSql.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.timebuysrv.user.dao.sql;

import com.alibaba.fastjson.JSON;
import com.awifi.np.biz.timebuysrv.user.model.SysUser;
import org.apache.log4j.Logger;

public class SysUserSql {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * 插入sql.
     *
     * @param sysUser
     * @return
     * @author 尤小平
     * @date 2017年4月17日 下午3:39:09
     */
    public String insert(SysUser sysUser) {
        logger.debug("params:" + JSON.toJSONString(sysUser));

        StringBuffer sql = new StringBuffer("insert into sys_user(");
        Object face = sysUser.getFace();
        Object nkname = sysUser.getNkname();
        Object sex = sysUser.getSex();
        Object birth = sysUser.getBirth();
        Object address = sysUser.getAddress();
        Object outId = sysUser.getOutid();
        Object telno = sysUser.getTelno();
        Object userName =sysUser.getUsername();
        if (face != null) {
            sql.append("face,");
        }
        if (userName != null) {
            sql.append("username,");
        }
        if (nkname != null) {
            sql.append("nkname,");
        }
        if (sex != null) {
            sql.append("sex,");
        }
        if (birth != null) {
            sql.append("birth,");
        }
        if (address != null) {
            sql.append("address,");
        }
        if (outId != null) {
            sql.append("outid,");
        }
        if (telno != null) {
            sql.append("telno,");
        }
        sql.deleteCharAt(sql.length()-1);
        sql.append(") values (");

        if (face != null) {
            sql.append("#{face},");
        }
        if (userName != null) {
            sql.append("#{username},");
        }
        if (nkname != null) {
            sql.append("#{nkname},");
        }
        if (sex != null) {
            sql.append("#{sex},");
        }
        if (birth != null) {
            sql.append("#{birth},");
        }
        if (address != null) {
            sql.append("#{address},");
        }
        if (outId != null) {
            sql.append("#{outid},");
        }
        if (telno != null) {
            sql.append("#{telno},");
        }
        sql.deleteCharAt(sql.length()-1);
        sql.append(")");

        logger.debug("sql.toString()= " + sql.toString());
        return sql.toString();
    }

    /**
     * 修改sql.
     *
     * @param sysUser
     * @return
     * @author 尤小平
     * @date 2017年4月17日 下午3:39:25
     */
    public String update(SysUser sysUser) {
        logger.debug("params:" + JSON.toJSONString(sysUser));

        StringBuffer sql = new StringBuffer("update sys_user set");
        StringBuffer condition = new StringBuffer("");

        Object face = sysUser.getFace();
        Object nkname = sysUser.getNkname();
        Object sex = sysUser.getSex();
        Object birth = sysUser.getBirth();
        Object address = sysUser.getAddress();
        Object telno = sysUser.getTelno();

        if (face != null) {
            condition.append(" face=#{face},");
        }
        if (nkname != null) {
            condition.append(" nkname=#{nkname},");
        }
        if (sex != null) {
            condition.append(" sex=#{sex},");
        }
        if (birth != null) {
            condition.append(" birth=#{birth},");
        }
        if (address != null) {
            condition.append(" address=#{address},");
        }
        if (telno != null) {
            condition.append(" telno=#{telno},");
        }

        if (condition.toString().length() == 0) {
            return null;
        }

        sql.append(condition.deleteCharAt(condition.length() - 1));
        sql.append(" where outid=#{outid}");

        logger.debug("sql.toString()= " + sql.toString());
        return sql.toString();
    }
}

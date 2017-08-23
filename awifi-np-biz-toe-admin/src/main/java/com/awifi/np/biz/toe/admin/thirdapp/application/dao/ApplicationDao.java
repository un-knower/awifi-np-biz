/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月16日 下午2:05:51
* 创建作者：许小满
* 文件名称：ApplicationDao.java
* 版本：  v1.0
* 功能：第三方应用--模型层
* 修改记录：
*/
package com.awifi.np.biz.toe.admin.thirdapp.application.dao;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.toe.admin.thirdapp.application.model.Application;

@Service("applicationDao")
public interface ApplicationDao {

    
    /**
     * 通过appid获取应用表信息
     * @param appid 应用ID
     * @return Application
     * @author kangyanxiang 
     * @date Nov 17, 2016 9:42:47 AM
     */
    @Results(value = {
            @Result(property = "id", column = "pk_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "appid", column = "app_id", javaType = String.class, jdbcType = JdbcType.CHAR),
            @Result(property = "appKey", column = "app_key", javaType = String.class, jdbcType = JdbcType.CHAR),
            @Result(property = "appName", column = "app_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "loginUrl", column = "login_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "params", column = "params", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "wechatAuthUrl", column = "wechat_auth_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "wechatUnbindUrl", column = "wechat_unbind_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "remark", column = "remark", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @Select("select pk_id,app_id,app_key,app_name,login_url,params,wechat_auth_url,wechat_unbind_url,remark from toe_application where app_id=#{appid} and delete_flag=1")
    Application getByAppid(String appid);
    
}

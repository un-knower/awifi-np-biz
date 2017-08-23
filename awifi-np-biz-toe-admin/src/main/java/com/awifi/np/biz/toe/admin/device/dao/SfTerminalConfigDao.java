package com.awifi.np.biz.toe.admin.device.dao;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.toe.admin.device.model.SfTerminalConfig;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月6日 下午4:21:01
 * 创建作者：亢燕翔
 * 文件名称：SfTerminalConfigDao.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Service("sfTerminalConfigDao")
public interface SfTerminalConfigDao {
    
    /**
     * 通过省ID获取省分平台定制终端配置信息
     * @param provinceId 省id
     * @return SfTerminalConfig
     * @author 亢燕翔  
     * @date 2017年2月6日 下午4:56:29
     */
    @Results(value = {
            @Result(property = "authHostName", column = "auth_hostname", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "platformHostName", column = "platform_hostname", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "portalHostName", column = "portal_hostname", javaType = String.class, jdbcType = JdbcType.VARCHAR)
            })
    @Select("select auth_hostname,platform_hostname,portal_hostname from toe_sf_terminal_config where province_id=#{provinceId} and delete_flag=1") 
    SfTerminalConfig getByProvinceId(Long provinceId);

}

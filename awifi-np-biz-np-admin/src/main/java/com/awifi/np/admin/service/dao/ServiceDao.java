package com.awifi.np.admin.service.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Service;

import com.awifi.np.admin.service.dao.sql.ServiceSql;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月12日 下午3:58:11
 * 创建作者：周颖
 * 文件名称：ServiceDao.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Service("serviceDao")
public interface ServiceDao {

    /**
     * 获取一级菜单
     * @param appId 平台id
     * @param roleIds 角色id
     * @return 一级菜单
     * @author 周颖  
     * @date 2017年1月13日 上午8:38:28
     */
    @SelectProvider(type=ServiceSql.class,method="getTopMenus")
    List<Map<String, Object>> getTopMenus(@Param("appId")String appId,@Param("roleIds")Long[] roleIds);
}
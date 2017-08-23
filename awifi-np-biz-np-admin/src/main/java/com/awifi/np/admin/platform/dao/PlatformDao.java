package com.awifi.np.admin.platform.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月12日 下午2:48:09
 * 创建作者：周颖
 * 文件名称：PlatformDao.java
 * 版本：  v1.0
 * 功能：平台dao
 * 修改记录：
 */
@Service("platformDao")
public interface PlatformDao {

    /**
     * 根据平台id获取平台key
     * @param appId 平台id
     * @return 平台key
     * @author 周颖  
     * @date 2017年1月12日 下午2:59:11
     */
    @Select("select app_key from np_platform where app_id=#{appId} limit 1")
    String getKeyByAppId(@Param("appId")String appId);
}

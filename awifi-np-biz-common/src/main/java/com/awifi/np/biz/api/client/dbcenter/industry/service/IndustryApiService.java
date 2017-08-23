package com.awifi.np.biz.api.client.dbcenter.industry.service;

import java.util.List;
import java.util.Map;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月19日 下午2:49:16
 * 创建作者：周颖
 * 文件名称：IndustryApiService.java
 * 版本：  v1.0
 * 功能：行业服务层
 * 修改记录：
 */
public interface IndustryApiService {

    /**
     * 获取全部行业数据
     * @return 行业list
     * @throws Exception 
     * @author 周颖  
     * @date 2017年1月20日 上午9:11:09
     */
    List<Map<String, Object>> getAllIndustry() throws Exception;
}

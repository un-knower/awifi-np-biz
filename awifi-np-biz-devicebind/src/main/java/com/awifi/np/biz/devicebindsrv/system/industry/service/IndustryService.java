package com.awifi.np.biz.devicebindsrv.system.industry.service;

import java.util.List;
import java.util.Map;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月20日 上午9:54:16
 * 创建作者：周颖
 * 文件名称：IndustryService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface IndustryService {

    /**
     * 获取行业信息
     * @param parentCode 父行业编号
     * @return 结果
     * @author 周颖  
     * @throws Exception 
     * @date 2017年1月20日 上午9:59:50
     */
    List<Map<String, String>> getListByParam(String parentCode) throws Exception;

}

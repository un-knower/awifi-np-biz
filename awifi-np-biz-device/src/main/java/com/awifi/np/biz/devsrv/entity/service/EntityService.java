package com.awifi.np.biz.devsrv.entity.service;

import java.util.Map;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月8日 上午9:08:01
 * 创建作者：亢燕翔
 * 文件名称：EntityService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface EntityService {

    /**
     * 编辑设备
     * @param devid 设备id
     * @param bodyParam 请求参数
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月10日 下午3:24:39
     */
    void update(String devid, Map<String, Object> bodyParam) throws Exception;

}

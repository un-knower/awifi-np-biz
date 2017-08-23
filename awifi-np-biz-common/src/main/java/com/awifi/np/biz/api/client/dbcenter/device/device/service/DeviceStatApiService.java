/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月5日 下午4:50:48
* 创建作者：王冬冬
* 文件名称：DeviceStatApiService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.device.device.service;

import java.util.List;
import java.util.Map;

public interface DeviceStatApiService {

    /**
     * 根据
     * @param params
     * @return
     * @throws Exception
     * @author 王冬冬  
     * @date 2017年5月5日 下午4:57:02
     */
    List<Map<String, Object>> queryEntityAndMerchantCountByProjectIds(String params) throws Exception;

}

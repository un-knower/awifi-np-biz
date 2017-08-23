/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年8月14日 上午10:46:59
* 创建作者：张智威
* 文件名称：DeviceStatService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.statistics.service;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.common.util.JsonUtil;
@Service("deviceStatService")
public class DeviceStatService {
    /**
     * 根据商户id查询设备数量
     * 
     * @param merId 商户id
     * @return 设备数量
     * @throws Exception 异常
     * @author 张智威
     * @date 2017年8月14日 上午10:50:03
     */
    public Long getDeviceCountByMerid(Long merId) throws Exception {
        HashMap<String, Object> params = new HashMap<>();
        params.put("merchantId", merId);
        params.put("merchantQueryType", "this");
        return DeviceClient.getCountByParam(JsonUtil.toJson(params)) * 1L;
    }
}

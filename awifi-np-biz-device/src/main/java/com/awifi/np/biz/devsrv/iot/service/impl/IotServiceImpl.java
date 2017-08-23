/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月26日 下午2:15:50
* 创建作者：范立松
* 文件名称：IotServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.iot.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.iot.util.IotClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.devsrv.iot.service.IotService;

@Service("iotService")
public class IotServiceImpl implements IotService {

    /**
     * 分页查询物联网设备列表
     * @author 范立松  
     * @date 2017年4月26日 下午2:28:56
     */
    @Override
    public void getIotList(Page<Map<String, Object>> page, Map<String, Object> paramsMap) throws Exception {
        // 查询总记录条数
        int total = IotClient.countIotByParam(paramsMap);
        page.setTotalRecord(total);
        if (total > 0) {
            List<Map<String, Object>> dataList = IotClient.getIotList(paramsMap);
            page.setRecords(dataList);
        }
    }

    /**
     * 删除物联网设备信息
     * @author 范立松  
     * @date 2017年4月26日 下午2:29:16
     */
    @Override
    public void removeIotByIds(List<String> idList) throws Exception {
        for (String deviceId : idList) {
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("id", deviceId);
            IotClient.removeIotByIds(paramsMap);
        }
    }

}

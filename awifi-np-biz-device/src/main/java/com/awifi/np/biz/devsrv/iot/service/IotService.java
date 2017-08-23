/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月26日 下午2:15:00
* 创建作者：范立松
* 文件名称：IotService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.iot.service;

import java.util.List;
import java.util.Map;

import com.awifi.np.biz.common.base.model.Page;

public interface IotService {

    /**
     * 分页查询物联网设备列表
     * @param page 物联网设备列表
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午5:17:38
     */
    void getIotList(Page<Map<String, Object>> page, Map<String, Object> paramsMap) throws Exception;

    /**
     * 删除物联网设备信息
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午2:50:15
     */
    void removeIotByIds(List<String> idList) throws Exception;

}

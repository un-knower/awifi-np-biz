/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月26日 下午2:36:06
* 创建作者：范立松
* 文件名称：IotApiService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.iot.service;

import java.util.List;
import java.util.Map;

public interface IotApiService {
    
    /**
     * 分页查询物联网设备列表
     * @return 物联网设备列表
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午5:17:38
     */
    List<Map<String, Object>> getIotList(Map<String, Object> paramsMap) throws Exception;

    /**
     * 删除物联网设备信息
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午2:50:15
     */
    void removeIotByIds(Map<String, Object> paramsMap) throws Exception;
    
    /**
     * 查询物联网设备数量
     * @param paramsMap 请求参数
     * @return 物联网设备数量
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月26日 下午2:52:50
     */
    int countIotByParam(Map<String, Object> paramsMap) throws Exception;

}

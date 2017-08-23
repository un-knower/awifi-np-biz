/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月26日 下午2:39:37
* 创建作者：范立松
* 文件名称：IotClient.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.iot.util;

import java.util.List;
import java.util.Map;

import com.awifi.np.biz.api.client.dbcenter.iot.service.IotApiService;
import com.awifi.np.biz.common.util.BeanUtil;

public class IotClient {

    /**
     * 业务对象
     */
    private static IotApiService iotApiService;

    /**
     * 分页查询物联网设备列表
     * @return 物联网设备列表
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午5:17:38
     */
    public static List<Map<String, Object>> getIotList(Map<String, Object> paramsMap) throws Exception {
        return getIotApiService().getIotList(paramsMap);
    }

    /**
     * 删除物联网设备信息
     * @param paramsMap 请求参数
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月18日 下午2:50:15
     */
    public static void removeIotByIds(Map<String, Object> paramsMap) throws Exception {
        getIotApiService().removeIotByIds(paramsMap);
    }

    /**
     * 查询物联网设备数量
     * @param paramsMap 请求参数
     * @return 物联网设备数量
     * @throws Exception 异常
     * @author 范立松  
     * @date 2017年4月26日 下午2:52:50
     */
    public static int countIotByParam(Map<String, Object> paramsMap) throws Exception {
        return getIotApiService().countIotByParam(paramsMap);
    }

    /**
     * 获取bean对象
     * @return bean对象
     * @author 范立松  
     * @date 2017年4月18日 下午1:42:32
     */
    public static IotApiService getIotApiService() {
        if (iotApiService == null) {
            iotApiService = (IotApiService) BeanUtil.getBean("iotApiService");
        }
        return iotApiService;
    }

}

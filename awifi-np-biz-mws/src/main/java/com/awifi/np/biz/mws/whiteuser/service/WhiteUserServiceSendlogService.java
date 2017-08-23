/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月26日 上午11:07:46
* 创建作者：王冬冬
* 文件名称：WhiteUserServiceSendlogService.java
* 版本：  v1.0
* 功能：白名单下发日志
* 修改记录：
*/
package com.awifi.np.biz.mws.whiteuser.service;

import java.util.List;

import com.awifi.np.biz.mws.whiteuser.model.StationMerchantNamelistSendlog;


public interface WhiteUserServiceSendlogService {
    /**
     * @param deviceId 设备id
     * @return list
     * @author 王冬冬  
     * @date 2017年4月26日 上午11:09:24
     */
    List<StationMerchantNamelistSendlog> findByDevId(String deviceId);

    /**
     * 保存白名单下发
     * @param accountId 用户id
     * @param merchantId 商户id
     * @param mobile 手机号
     * @param devId 设备id
     * @param params 请求参数
     * @param rs 返回结果
     * @param userId 用户id
     * @param taskId taskId
     * @author 王冬冬  
     * @date 2017年4月26日 下午1:04:17
     */
    void saveNameListSendLog(Long accountId, Long merchantId, String mobile, String devId, String params, String rs,Long userId, String taskId);
}

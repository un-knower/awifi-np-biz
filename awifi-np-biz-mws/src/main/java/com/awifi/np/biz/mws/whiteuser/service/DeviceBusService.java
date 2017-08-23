/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月26日 上午9:31:11
* 创建作者：王冬冬
* 文件名称：DeviceBusService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mws.whiteuser.service;

import java.util.List;

import com.awifi.np.biz.mws.whiteuser.model.WhiteUser;

public interface DeviceBusService {

    /**
     * 发送白名单到设备总线
     * @param devId 设备id
     * @param devMac 设备id
     * @param macList mac列表
     * @param taskId 任务id
     * @param accountId 账号id
     * @param merchantId 商户id
     * @param mobile 手机号
     * @param userId 用户id
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年6月14日 下午2:16:25
     */
    void sendWhiteToDevicebus(String devId, String devMac, List<String> macList, String taskId, Long accountId, Long merchantId, String mobile,
            Long userId) throws Exception;

    /**
     * 批量发送白名单到设备总线
     * @param deviceId 设备id
     * @param devMac 设备id
     * @param macList mac列表
     * @param taskId 任务id
     * @param accountId 账号id
     * @param whiteUserList 白名单列表
     * @param userId 用户id
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年6月14日 下午2:16:25
     */
    void sendWhiteToDevicebus(String deviceId, String devMac, List<String> macList, String taskId, Long accountId,List<WhiteUser> whiteUserList, Long userId) throws Exception;

}

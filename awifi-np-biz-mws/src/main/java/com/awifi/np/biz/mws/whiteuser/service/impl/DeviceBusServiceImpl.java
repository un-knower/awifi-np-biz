/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月26日 上午9:32:03
* 创建作者：王冬冬
* 文件名称：DeviceBusServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mws.whiteuser.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.awifi.np.biz.api.client.devicebus.util.DeviceBusClient;
import com.awifi.np.biz.mws.whiteuser.model.WhiteUser;
import com.awifi.np.biz.mws.whiteuser.service.DeviceBusService;
import com.awifi.np.biz.mws.whiteuser.service.WhiteUserServiceSendlogService;


@Service("deviceBusClientService")
public class DeviceBusServiceImpl implements DeviceBusService{

    /**白名单下发服务*/
    @Resource(name = "whiteUserServiceSendlogService")
    private WhiteUserServiceSendlogService whiteUserServiceSendlogService;
    
    
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
    public void sendWhiteToDevicebus(String devId, String devMac, List<String> macList, String taskId, Long accountId,
            Long merchantId, String mobile, Long userId) throws Exception {
        String rs = sendToDevicebus(devMac, macList);
        whiteUserServiceSendlogService.saveNameListSendLog(accountId, merchantId, mobile, devId, JSON.toJSONString(macList), rs, userId,taskId);
        return;
    }

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
    public void sendWhiteToDevicebus(String deviceId, String devMac, List<String> macList, String taskId,
            Long accountId, List<WhiteUser> whiteUserList, Long userId) throws Exception {
        String rs = sendToDevicebus(devMac, macList);
        for(WhiteUser whiteUser:whiteUserList){
            whiteUserServiceSendlogService.saveNameListSendLog(accountId, whiteUser.getMerchantId(), whiteUser.getCellPhone(), deviceId, JSON.toJSONString(macList), rs, userId,taskId);
        }
    }
    
    /**
     * 下发设备总线
     * @param devMac 设备mac
     * @param macList mac列表
     * @return rs调设备总线返回结果
     * @author 王冬冬  
     * @throws Exception 异常
     * @date 2017年6月14日 下午2:23:39
     */
    private String sendToDevicebus(String devMac, List<String> macList) throws Exception {
        if(macList!=null && macList.size()>0){
            for(int i=0;i<macList.size();i++){
                if(StringUtils.isBlank(macList.get(i)) || "undefined".equalsIgnoreCase(macList.get(i))){
                    macList.remove(macList.get(i));
                    i--;
                }
            }
        }
        String rs=DeviceBusClient.setWhiteUser(devMac,macList);
        return rs;
    }
}

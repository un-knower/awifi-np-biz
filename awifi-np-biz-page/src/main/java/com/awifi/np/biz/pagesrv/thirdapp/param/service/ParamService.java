/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月13日 上午10:28:36
* 创建作者：许小满
* 文件名称：ParamService.java
* 版本：  v1.0
* 功能：参数处理业务层
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.thirdapp.param.service;

public interface ParamService {

    /**
     * 将参数存入到redis缓存中
     * @param merchantId 商户id
     * @param deviceId 设备id
     * @param devMac 设备MAC
     * @param userIp 用户IP
     * @param userMac 用户MAC
     * @param userPhone 用户手机号
     * @param terminalType 终端类型
     * @return redis key
     * @author 许小满  
     * @date 2017年5月13日 上午10:30:17
     */
    String putParamsToCache(String merchantId, String deviceId, String devMac, 
            String userIp, String userMac, String userPhone, String terminalType);
    
}

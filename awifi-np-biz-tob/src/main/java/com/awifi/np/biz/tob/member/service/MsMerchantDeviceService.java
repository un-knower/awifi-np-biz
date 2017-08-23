/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月8日 下午2:22:38
* 创建作者：王冬冬
* 文件名称：MsMerchantDeviceService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.tob.member.service;

public interface MsMerchantDeviceService {

    /**
     * 防蹭网开关
     * @param merchantId 商户id
     * @param status 开关状态
     * @author 王冬冬  
     * @throws Exception 
     * @date 2017年5月8日 下午2:49:55
     */
    void updateAntiRobberSwitch(Long merchantId, Byte status) throws Exception;

    void putAntiRobberCodesToRedis() throws Exception;
}

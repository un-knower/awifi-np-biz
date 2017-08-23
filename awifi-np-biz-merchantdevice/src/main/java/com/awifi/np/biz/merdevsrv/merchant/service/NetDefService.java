/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月19日 下午1:47:53
* 创建作者：王冬冬
* 文件名称：NetDefService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.merdevsrv.merchant.service;

public interface NetDefService {
    /**
     * 将设备防蹭网码放入redis
     * @throws Exception 异常
     * @author 王冬冬   
     * @date 2017年6月19日 下午1:50:05
     */
    void addToCache() throws Exception;
}

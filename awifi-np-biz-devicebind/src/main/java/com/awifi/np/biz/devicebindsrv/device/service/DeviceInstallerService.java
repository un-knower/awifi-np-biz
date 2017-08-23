/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月22日 下午8:02:04
* 创建作者：许小满
* 文件名称：DeviceInstallerService.java
* 版本：  v1.0
* 功能：设备-装维人员 业务层接口
* 修改记录：
*/
package com.awifi.np.biz.devicebindsrv.device.service;

public interface DeviceInstallerService {

    /**
     * 新增 设备-装维人员关系表记录
     * @param deviceId 设备id
     * @param jobNumber 服务人员工号
     * @author 许小满  
     * @date 2017年5月22日 下午8:10:55
     */
    void add(String deviceId, String jobNumber);
    
}

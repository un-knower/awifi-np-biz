/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月22日 下午8:02:46
* 创建作者：许小满
* 文件名称：DeviceInstallerServiceImpl.java
* 版本：  v1.0
* 功能：设备-装维人员 业务层接口实现类
* 修改记录：
*/
package com.awifi.np.biz.devicebindsrv.device.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.common.base.service.BaseService;
import com.awifi.np.biz.devicebindsrv.device.dao.DeviceInstallerServiceDao;
import com.awifi.np.biz.devicebindsrv.device.service.DeviceInstallerService;

@Service("deviceInstallerService")
public class DeviceInstallerServiceImpl extends BaseService implements DeviceInstallerService {

    /** 设备-装维人员 dao */
    @Resource(name = "deviceInstallerServiceDao")
    private DeviceInstallerServiceDao deviceInstallerServiceDao;
 
    /**
     * 新增 设备-装维人员关系表记录
     * @param deviceId 设备id
     * @param jobNumber 服务人员工号
     * @author 许小满  
     * @date 2017年5月22日 下午8:10:55
     */
    public void add(String deviceId, String jobNumber){
        Long count = deviceInstallerServiceDao.count(deviceId, jobNumber);
        if(count > 0){//如果已存在记录，流程结束
            return;
        }
        deviceInstallerServiceDao.add(deviceId, jobNumber);
    }
    
}

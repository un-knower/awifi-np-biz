/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月22日 下午8:04:33
* 创建作者：许小满
* 文件名称：DeviceInstallerServiceDao.java
* 版本：  v1.0
* 功能：设备-装维人员 模型层接口
* 修改记录：
*/
package com.awifi.np.biz.devicebindsrv.device.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

@Service("deviceInstallerServiceDao")
public interface DeviceInstallerServiceDao {

    /**
     * 新增 设备-装维人员关系表记录
     * @param deviceId 设备id
     * @param jobNumber 服务人员工号
     * @author 许小满  
     * @date 2017年5月22日 下午8:07:30
     */
    @Insert("insert into np_biz_device_installer(device_id,job_number) value(#{deviceId},#{jobNumber})")
    void add(@Param("deviceId")String deviceId, @Param("jobNumber")String jobNumber);
    
    /**
     * 查询数量
     * @param deviceId 设备id
     * @param jobNumber 服务人员工号
     * @return 数量
     * @author 许小满  
     * @date 2017年6月15日 下午6:51:34
     */
    @Select("select count(*) from np_biz_device_installer where device_id=#{deviceId} and job_number=#{jobNumber}")
    Long count(@Param("deviceId")String deviceId, @Param("jobNumber")String jobNumber);
    
}

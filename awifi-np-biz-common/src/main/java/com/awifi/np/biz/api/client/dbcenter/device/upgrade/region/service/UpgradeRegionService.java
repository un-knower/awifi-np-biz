/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月10日 下午5:30:03
* 创建作者：尤小平
* 文件名称：UpgradeRegionService.java
* 版本：  v1.0
* 功能：区域默认升级service
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.device.upgrade.region.service;

import com.awifi.np.biz.api.client.dbcenter.device.upgrade.region.model.DeviceUpgradeRegion;

import java.util.List;

public interface UpgradeRegionService {
    /**
     * 根据条件查询列表.
     * 
     * @param region DeviceUpgradeRegion
     * @param begin 起始页
     * @param pageSize 每页条数
     * @return List<DeviceUpgradeRegion>
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年7月11日 上午11:14:25
     */
    List<DeviceUpgradeRegion> queryListByParam(DeviceUpgradeRegion region, int begin, int pageSize) throws Exception;

    /**
     * 根据条件统计条数.
     * 
     * @param region DeviceUpgradeRegion
     * @return int
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年7月11日 上午11:14:32
     */
    int queryCountByParam(DeviceUpgradeRegion region) throws Exception;

    /**
     * 根据id查询.
     * 
     * @param id 主键
     * @return DeviceUpgradeRegion
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年7月11日 上午11:14:38
     */
    DeviceUpgradeRegion queryById(Long id) throws Exception;

    /**
     * 新增.
     * 
     * @param region DeviceUpgradeRegion
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年7月11日 上午11:14:43
     */
    void add(DeviceUpgradeRegion region) throws Exception;

    /**
     * 修改.
     * 
     * @param region DeviceUpgradeRegion
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年7月11日 上午11:14:50
     */
    void update(DeviceUpgradeRegion region) throws Exception;

    /**
     * 删除.
     * 
     * @param id 主键
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年7月11日 上午11:14:54
     */
    void delete(Long id) throws Exception;

    /**
     * 启用.
     * 
     * @param id 主键
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年7月11日 上午11:14:58
     */
    void start(Long id) throws Exception;
}

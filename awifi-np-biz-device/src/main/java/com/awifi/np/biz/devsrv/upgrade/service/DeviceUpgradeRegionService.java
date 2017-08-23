/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月10日 下午8:04:13
* 创建作者：尤小平
* 文件名称：DeviceUpgradeRegionService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.upgrade.service;

import com.awifi.np.biz.api.client.dbcenter.device.upgrade.region.model.DeviceUpgradeRegion;
import com.awifi.np.biz.common.base.model.Page;

public interface DeviceUpgradeRegionService {
    /**
     * 定制终端-获取区域默认升级包列表.
     * 
     * @param region DeviceUpgradeRegion
     * @param page Page
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月11日 下午3:57:45
     */
    void getListByParam(DeviceUpgradeRegion region, Page<DeviceUpgradeRegion> page) throws Exception;

    /**
     * 新增终端地区升级.
     * 
     * @param region DeviceUpgradeRegion
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月12日 下午5:24:30
     */
    void add(DeviceUpgradeRegion region) throws Exception;

    /**
     * 查看升级情况.
     * 
     * @param id id
     * @return 升级情况
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月13日 下午3:03:03
     */
    DeviceUpgradeRegion getById(Long id) throws Exception;
    
    /**
     * 删除终端地区升级.
     * 
     * @param id id
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月13日 下午3:02:52
     */
    void delete(Long id) throws Exception;
    
    /**
     * 判断是否已经有启用的升级包.
     * 
     * @param id id
     * @return 是否存在升级包
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月13日 下午3:01:41
     */
    boolean existStartUpgrade(Long id) throws Exception;

    /**
     * 启用升级包.
     * 
     * @param id id
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月13日 下午3:00:05
     */
    void start(Long id) throws Exception;
    
    /**
     * 更新终端地区升级.
     * 
     * @param region region
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月13日 下午3:17:58
     */
    /*void update(DeviceUpgradeRegion region) throws Exception;*/
}

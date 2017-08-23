/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年7月10日 下午5:30:03
 * 创建作者：尤小平
 * 文件名称：UpgradeRegionServiceImpl.java
 * 版本：  v1.0
 * 功能：区域默认升级
 * 修改记录：
 */
package com.awifi.np.biz.api.client.dbcenter.device.upgrade.region.util;

import com.awifi.np.biz.api.client.dbcenter.device.upgrade.region.model.DeviceUpgradeRegion;
import com.awifi.np.biz.api.client.dbcenter.device.upgrade.region.service.UpgradeRegionService;
import com.awifi.np.biz.common.util.BeanUtil;

import java.util.List;

public class UpgradeRegionClient {
    /**
     * 终端地区升级
     */
    private static UpgradeRegionService upgradeRegionService;

    /**
     * 获取UpgradeRegionService
     * 
     * @return UpgradeRegionService
     * @author 尤小平
     * @date 2017年7月11日 上午11:35:11
     */
    private static UpgradeRegionService getUpgradeRegionService() {
        if (upgradeRegionService == null) {
            upgradeRegionService = (UpgradeRegionService) BeanUtil.getBean("upgradeRegionService");
        }
        return upgradeRegionService;
    }

    /**
     * 新增终端地区升级.
     * 
     * @param region 终端地区升级
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年7月11日 下午1:43:06
     */
    public static void add(DeviceUpgradeRegion region) throws Exception {
        getUpgradeRegionService().add(region);
    }

    /**
     * 根据主键查询终端地区升级包.
     * 
     * @param id 主键
     * @return 终端地区升级
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月11日 下午1:44:03
     */
    public static DeviceUpgradeRegion queryById(Long id) throws Exception {
        return getUpgradeRegionService().queryById(id);
    }

    /**
     * 查询终端地区升级列表.
     * 
     * @param region 终端地区升级
     * @param begin 起始页
     * @param pageSize 每页条数
     * @return 终端地区升级列表
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月11日 下午1:44:08
     */
    public static List<DeviceUpgradeRegion> queryListByParam(DeviceUpgradeRegion region, int begin, int pageSize)
            throws Exception {
        return getUpgradeRegionService().queryListByParam(region, begin, pageSize);
    }

    /**
     * 查询终端地区升级总数.
     * 
     * @param region 终端地区升级
     * @return 总条数
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月11日 下午1:44:13
     */
    public static int queryCountByParam(DeviceUpgradeRegion region) throws Exception {
        return getUpgradeRegionService().queryCountByParam(region);
    }

    /**
     * 更新终端地区升级.
     * 
     * @param region 终端地区升级
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月11日 下午1:44:17
     */
    public static void update(DeviceUpgradeRegion region) throws Exception {
        getUpgradeRegionService().update(region);
    }

    /**
     * 删除终端地区升级.
     * 
     * @param id 主键
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月11日 下午1:44:21
     */
    public static void delete(Long id) throws Exception {
        getUpgradeRegionService().delete(id);
    }

    /**
     * 启用.
     * 
     * @param id 主键
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月11日 下午1:44:25
     */
    public static void start(Long id) throws Exception {
        getUpgradeRegionService().start(id);
    }
}

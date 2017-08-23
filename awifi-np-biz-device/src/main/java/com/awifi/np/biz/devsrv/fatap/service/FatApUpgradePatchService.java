/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月27日 下午3:34:41
* 创建作者：伍恰
* 文件名称：FatApUpgradePatchController.java
* 版本：  v1.0
* 功能： 定制终端升级包 相关功能 包括 个性化升级包和默认升级包
* 修改记录：
*/
package com.awifi.np.biz.devsrv.fatap.service;

import java.util.Map;

import com.awifi.np.biz.common.base.model.Page;

public interface FatApUpgradePatchService {
    /**
     * 定制终端升级--默认升级包分页查询
     * @param params 查询参数
     * @param page 分页实体
     * @author 伍恰  
     * @date 2017年6月27日 下午4:25:16
     */
    void getDefaulUpgradetPatchList(Map<String, Object> params,Page<Map<String, Object>> page);
    
    /**
     * 定制终端升级--默认升级包根据id查询
     * @param id 升级包id
     * @return 异常/参数
     * @author 伍恰  
     * @date 2017年6月27日 下午4:53:58
     */
    Map<String, Object> getDefaulUpgradetPatchById(Long id);
    
    /**
     * 定制终端升级--默认升级启用
     * @param ids id 数组
     * @author 伍恰  
     * @date 2017年6月27日 下午5:05:25
     */
    void updateUpgradetPatchStatus(Long [] ids);
    
    /**
     * 定制终端升级--升级包信息新增
     * @param params 参数
     * @author 伍恰  
     * @date 2017年6月27日 下午5:22:49
     */
    void addDefaulUpgradetPatch(Map<String, Object> params);
    
    /**
     * 定制终端升级--默认升级包删除
     * @param ids id 数组
     * @author 伍恰  
     * @date 2017年6月27日 下午8:00:25
     */
    void deleteDefaulUpgradetPatch(Long [] ids);
    
    /**
     * 定制终端升级--个性化升级设备查询
     * @param params 参数
     * @param page 分页实体
     * @author 伍恰  
     * @date 2017年6月27日 下午8:26:36
     */
    void getUpgradeDeviceList(Map<String, Object> params,Page<Map<String, Object>> page);
    
    /**
     * 定制终端升级--个性化升级包查询
     * @param params 查询参数
     * @param page 分页实体
     * @author 伍恰  
     * @date 2017年6月27日 下午8:30:43
     */
    void getPersonalizUpgradePatchList(Map<String, Object> params,Page<Map<String, Object>> page);
    
    /**
     * 定制终端升级--个性化升级包根据id查询
     * @param id id
     * @return 异常/参数
     * @author 伍恰  
     * @date 2017年6月27日 下午8:33:04
     */
    Map<String, Object> getPersonalizUpgradePatchById(Long id);
    
    /**
     * 定制终端升级--个性化升级包上传
     * @param params 参数
     * @author 伍恰  
     * @date 2017年6月27日 下午8:35:55
     */
    void addPersonalizedUpgradePatch(Map<String, Object> params);
    
    /**
     * 定制终端升级--个性化升级包删除
     * @param ids id 数组
     * @author 伍恰  
     * @date 2017年6月27日 下午8:37:02
     */
    void deletePersonalizedUpgradePatch(Long [] ids);
}

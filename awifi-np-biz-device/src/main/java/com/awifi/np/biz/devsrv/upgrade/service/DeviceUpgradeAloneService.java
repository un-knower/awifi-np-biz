/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月10日 下午8:06:44
* 创建作者：尤小平
* 文件名称：DeviceUpgradeAloneService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.upgrade.service;

import java.util.Map;

import com.awifi.np.biz.common.base.model.Page;
//@SuppressWarnings({"rawtypes"})
public interface DeviceUpgradeAloneService {
    /**
     * 定制终端升级--个性化升级任务查询列表
     * @param params 参数 
     * @param page 分页实体
     * @author 伍恰  
     * @date 2017年6月27日 下午8:23:59
     */
    void getPersonalizedUpgradeTaskList(Map<String, Object> params,Page<Map<String, Object>> page)throws Exception;
    
    /**
     * 定制终端升级--个性化任务根据id查询
     * @param id id 
     * @return 异常/参数
     * @author 伍恰  
     * @date 2017年6月27日 下午8:25:39
     */
    Map<String, Object> getPersonalizedUpgradeTaskById(Long id)throws Exception;
    
    /**
     * 定制终端升级--个性化升级任务添加
     * @param params 异常/参数
     * @author 伍恰  
     * @date 2017年6月27日 下午8:28:45
     */
    void addPersonalizedUpgradeTask(Map<String, Object> params)throws Exception;
    
    /**
     * 定制终端升级--个性化升级任务删除
     * @param ids id数组
     * @author 伍恰  
     * @date 2017年6月27日 下午8:29:34
     */
    void deletePersonalizedUpgradeTask(Long [] ids)throws Exception;
    
    /**
     * 定制终端升级--个性化升级任务统计
     * @param params
     * @return
     * @author 余红伟 
     * @date 2017年7月11日 上午9:34:23
     */
    Integer getPersonalizedUpgradeTaskCount(Map<String, Object> params)throws Exception;
    /**
     * 定制终端升级--个性化升级包查询 根据 id
     * @param id
     * @return
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月11日 上午10:22:24
     */
    Map<String, Object> getPersonalizUpgradePatchById(Long id)throws Exception;
    /**
     * 根据mac地址查询设备
     * @param mac
     * @return
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月11日 下午2:14:53
     */
    Map<String, Object> getDeviceByMac(String mac)throws Exception;
    /**
     * 个性化升级包 列表查询
     * @param params
     * @param page
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月11日 下午3:01:38
     */
    void getPersonalizUpgradePatchList(Map<String, Object> params,Page<Map<String, Object>> page)throws Exception;
    /**
     * 个性化升级包 统计
     * @param params
     * @return
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月11日 下午3:02:07
     */
    Integer getPersonalizUpgradePatchCount(Map<String, Object> params)throws Exception;
    /**
     * 个性化升级包 删除
     * @param id
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月11日 下午3:03:16
     */
    void deletePersonalizedUpgradePatch(Long id)throws Exception;
    /**
     * 个人性化升级包 添加
     * @param params
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月11日 下午3:03:46
     */
    void addPersonalizedUpgradePatch(Map<String, Object> params)throws Exception;
    /**
     * 定制终端升级--个性化升级设备查询
     * @param params 参数
     * @param page 分页实体
     * @author 伍恰  
     * @date 2017年6月27日 下午8:26:36
     */
    void getUpgradeDeviceList(Map<String, Object> params,Page<Map<String, Object>> page)throws Exception;
    /**
     * 查询设备数 -- 添加任务界面  
     * @param params
     * @return
     * @author 余红伟 
     * @date 2017年7月12日 上午9:21:40
     */
    Integer queryDeviceCountByParam(Map<String, Object> params)throws Exception;
}

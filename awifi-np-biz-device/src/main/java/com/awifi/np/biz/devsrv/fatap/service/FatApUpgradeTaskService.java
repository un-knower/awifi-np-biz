/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月27日 下午8:08:15
* 创建作者：伍恰
* 文件名称：FatApUpgradeTaskService.java
* 版本：  v1.0
* 功能：定制终端升级任务 相关
* 修改记录：
*/
package com.awifi.np.biz.devsrv.fatap.service;

import java.util.Map;

import com.awifi.np.biz.common.base.model.Page;

public interface FatApUpgradeTaskService {
    /**
     * 定制终端升级--个性化升级任务查询
     * @param params 参数 
     * @param page 分页实体
     * @author 伍恰  
     * @date 2017年6月27日 下午8:23:59
     */
    void getPersonalizedUpgradeTaskList(Map<String, Object> params,Page<Map<String, Object>> page);
    
    /**
     * 定制终端升级--个性化任务根据id查询
     * @param id id 
     * @return 异常/参数
     * @author 伍恰  
     * @date 2017年6月27日 下午8:25:39
     */
    Map<String, Object> getPersonalizedUpgradeTaskById(Long id);
    
    /**
     * 定制终端升级--个性化升级任务添加
     * @param params 异常/参数
     * @author 伍恰  
     * @date 2017年6月27日 下午8:28:45
     */
    void addPersonalizedUpgradeTask(Map<String, Object> params);
    
    /**
     * 定制终端升级--个性化升级任务删除
     * @param ids id数组
     * @author 伍恰  
     * @date 2017年6月27日 下午8:29:34
     */
    void deletePersonalizedUpgradeTask(Long [] ids);
    
    
}

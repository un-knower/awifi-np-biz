/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月27日 下午3:34:41
* 创建作者：伍恰
* 文件名称：FatApUpgradePatchController.java
* 版本：  v1.0
* 功能： 定制终端升级包 相关功能 包括 个性化升级包和默认升级包
* 修改记录：
*/
package com.awifi.np.biz.devsrv.fatap.service.impl;

import java.util.Map;
import org.springframework.stereotype.Service;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.devsrv.fatap.service.FatApUpgradePatchService;

@Service("fatApUpgradePatchService")
public class FatApUpgradePatchServiceImpl implements FatApUpgradePatchService{
    
    /**
     * 定制终端升级--默认升级包分页查询
     * @param params 查询参数
     * @param page 分页实体
     * @author 伍恰  
     * @date 2017年6月27日 下午4:25:16
     */
    @Override
    public void getDefaulUpgradetPatchList(Map<String, Object> params, Page<Map<String,Object>> page) {
        
    }
    
    /**
     * 定制终端升级--默认升级包根据id查询
     * @param id 升级包id
     * @return 异常/参数
     * @author 伍恰  
     * @date 2017年6月27日 下午4:53:58
     */
    @Override
    public Map<String, Object> getDefaulUpgradetPatchById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * 定制终端升级--默认升级启用
     * @param ids id 数组
     * @author 伍恰  
     * @date 2017年6月27日 下午5:05:25
     */
    @Override
    public void updateUpgradetPatchStatus(Long[] ids) {
        // 点击后启用选中的升级包，启用时关闭相同地区和厂商型号的其他升级包
        
    }
    
    /**
     * 定制终端升级--升级包信息新增
     * @param params 参数
     * @author 伍恰  
     * @date 2017年6月27日 下午5:22:49
     */
    @Override
    public void addDefaulUpgradetPatch(Map<String, Object> params) {
        
        
    }
    
    /**
     * 定制终端升级--默认升级包删除
     * @param ids id 数组
     * @author 伍恰  
     * @date 2017年6月27日 下午8:00:25
     */
    @Override
    public void deleteDefaulUpgradetPatch(Long[] ids) {
        // TODO Auto-generated method stub
        
    }

    /**
     * 定制终端升级--个性化升级任务添加
     * @param params 异常/参数
     * @author 伍恰  
     * @date 2017年6月27日 下午8:28:45
     */
    @Override
    public void getUpgradeDeviceList(Map<String, Object> params,
            Page<Map<String, Object>> page) {
        // TODO Auto-generated method stub
        
    }

    /**
     * 定制终端升级--个性化升级包查询
     * @param params 查询参数
     * @param page 分页实体
     * @author 伍恰  
     * @date 2017年6月27日 下午8:30:43
     */
    @Override
    public void getPersonalizUpgradePatchList(Map<String, Object> params,
            Page<Map<String, Object>> page) {
        // TODO Auto-generated method stub
        
    }

    /**
     * 定制终端升级--个性化升级包根据id查询
     * @param id id
     * @return 异常/参数
     * @author 伍恰  
     * @date 2017年6月27日 下午8:33:04
     */
    @Override
    public Map<String, Object> getPersonalizUpgradePatchById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 定制终端升级--个性化升级包上传
     * @param params 参数
     * @author 伍恰  
     * @date 2017年6月27日 下午8:35:55
     */
    @Override
    public void addPersonalizedUpgradePatch(Map<String, Object> params) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * 定制终端升级--个性化升级任务删除
     * @param ids id数组
     * @author 伍恰  
     * @date 2017年6月27日 下午8:29:34
     */
    @Override
    public void deletePersonalizedUpgradePatch(Long[] ids) {
        // TODO Auto-generated method stub
        
    }
    
}

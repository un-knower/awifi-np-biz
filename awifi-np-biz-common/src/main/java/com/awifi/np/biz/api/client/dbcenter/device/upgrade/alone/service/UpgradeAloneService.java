package com.awifi.np.biz.api.client.dbcenter.device.upgrade.alone.service;

import java.util.List;
import java.util.Map;




/**
 * Created by youxp on 2017/7/10.
 */
public interface UpgradeAloneService {
    /**
     * 定制终端升级--个性化升级任务查询  --主界面 主查询 -  参数：MAC地址，商户名称(id)，升级包名称(id)
     * @param params
     * @return
     * @author 余红伟 
     * @date 2017年7月10日 下午4:47:42
     */
    public List<Map<String, Object>> getPersonalizedUpgradeTaskList(Map<String, Object> params)throws Exception;
    /**
     * 定制终端升级--个性化升级任务 统计
     * @param params
     * @return
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月11日 上午10:23:03
     */
    public Integer getPersonalizedUpgradeTaskCount(Map<String, Object> params) throws Exception;
    /**
     * 定制终端升级-- 个性化任务添加
     * @param params
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月11日 上午8:51:27
     */
    public void addPersonalizedUpgradeTask(Map<String, Object> params)throws Exception;
    /**
     * 定制终端升级--个性化升级任务删除 --主界面 删除任务
     * @param ids
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月11日 上午9:48:10
     */
    public void deletePersonalizedUpgradeTask(Long id)throws Exception;
    /**
     * 定制终端升级--个性化升级包查询 根据 id
     * @param id
     * @return
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月11日 上午10:22:24
     */
    public Map<String, Object> getPersonalizUpgradePatchById(Long id)throws Exception;
    /**
     * 根据mac地址查询设备
     * @param mac
     * @return
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月11日 下午2:16:36
     */
    public Map<String, Object> getDeviceByMac(String mac)throws Exception;
    /**
     * 个性化升级包 列表查询
     * @param params
     * @param page
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月11日 下午2:28:30
     */
    public List<Map<String,Object>> getPersonalizUpgradePatchList(Map<String, Object> params)throws Exception;
    /**
     * 个性化升级包 统计
     * @param params
     * @return
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月11日 下午2:44:19
     */
    public Integer getPersonalizUpgradePatchCount(Map<String, Object> params)throws Exception;
    /**
     * 个性化升级包 删除
     * @param id
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月11日 下午2:42:18
     */
    public void deletePersonalizedUpgradePatch(Long id)throws Exception;
    /**
     * 个性化任务包 根据id查询
     * @param id
     * @return
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月13日 下午1:53:26
     */
    public Map<String, Object> getPersonalizedUpgradeTaskById(Long id)throws Exception;
    /**
     * 个人性化升级包 添加
     * @param params
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月11日 下午2:46:03
     */
    public void addPersonalizedUpgradePatch(Map<String, Object> params)throws Exception;
    /**
     * 定制终端升级--个性化升级设备查询列表
     * @param params 参数
     * @param page 分页实体
     * @author 伍恰  
     * @date 2017年6月27日 下午8:26:36
     */
    public List<Map<String, Object>> getUpgradeDeviceList(Map<String, Object> params)throws Exception;
    
    /**
     * 查询设备数 -- 添加任务界面  
     * @param params
     * @return
     * @author 余红伟 
     * @date 2017年7月12日 上午9:21:40
     */
    public Integer queryDeviceCountByParam(Map<String, Object> params)throws Exception;
}

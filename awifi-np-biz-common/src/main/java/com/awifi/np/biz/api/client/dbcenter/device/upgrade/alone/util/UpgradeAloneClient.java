package com.awifi.np.biz.api.client.dbcenter.device.upgrade.alone.util;

import java.util.List;
import java.util.Map;
import com.awifi.np.biz.api.client.dbcenter.device.upgrade.alone.service.UpgradeAloneService;
import com.awifi.np.biz.common.util.BeanUtil;

/**
 * Created by youxp on 2017/7/10.
 */
public class UpgradeAloneClient {
//    private static Logger logger  = Logger.getLogger(UpgradeAloneClient.class);
    
    private static UpgradeAloneService upgradeAloneService;
    
    public static UpgradeAloneService getUpgradeAloneService(){
        if(upgradeAloneService == null){
            upgradeAloneService = (UpgradeAloneService) BeanUtil.getBean("upgradeAloneService");
        }
        return upgradeAloneService;
    }
    /**
     * 定制终端升级-- 个性化任务列表查询
     * @param params
     * @param page
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月11日 下午2:20:06
     */
    public static List<Map<String, Object>> getPersonalizedUpgradeTaskList(Map<String, Object> params)throws Exception{
        return getUpgradeAloneService().getPersonalizedUpgradeTaskList(params);
    }
    /**
     * 定制终端升级-- 个性化任务统计
     * @param params
     * @return
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月11日 下午2:19:29
     */
    public static Integer getPersonalizedUpgradeTaskCount(Map<String, Object> params)throws Exception{
        return getUpgradeAloneService().getPersonalizedUpgradeTaskCount(params);
    }
    /**
     * 定制终端升级-- 个人性化任务添加
     * @param params
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月11日 下午2:19:07
     */
    public static void addPersonalizedUpgradeTask(Map<String, Object> params)throws Exception{
        getUpgradeAloneService().addPersonalizedUpgradeTask(params);
    }
    /**
     * 定制终端升级--个性化升级任务删除 --主界面 删除任务
     * @param ids
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月11日 上午10:05:27
     */
    public static void deletePersonalizedUpgradeTask(Long id)throws Exception{
        getUpgradeAloneService().deletePersonalizedUpgradeTask(id);
    }

    /**
     * 定制终端升级--个性化升级包查询 根据 id
     * @param id
     * @return
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月11日 下午2:18:31
     */
    public static Map<String, Object> getPersonalizUpgradePatchById(Long id) throws Exception{
        return getUpgradeAloneService().getPersonalizUpgradePatchById(id);
    }
    /**
     * 根据mac地址查询设备
     * @param mac
     * @return
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月11日 下午2:14:53
     */
    public static Map<String, Object> getDeviceByMac(String mac)throws Exception{
        return getUpgradeAloneService().getDeviceByMac(mac);
    }
    /**
     * 个性化升级包 列表查询
     * @param params
     * @param page
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月11日 下午3:18:27
     */
   public static List<Map<String,Object>> getPersonalizUpgradePatchList(Map<String, Object> params)throws Exception{
       return getUpgradeAloneService().getPersonalizUpgradePatchList(params);
    }
   /**
    * 个性化升级包 统计
    * @param params
    * @return
    * @throws Exception
    * @author 余红伟 
    * @date 2017年7月11日 下午3:02:07
    */
   public static Integer getPersonalizUpgradePatchCount(Map<String, Object> params)throws Exception{
       return getUpgradeAloneService().getPersonalizUpgradePatchCount(params);
   }
   /**
    * 个性化升级包 删除
    * @param id
    * @throws Exception
    * @author 余红伟 
    * @date 2017年7月11日 下午3:03:16
    */
   public static void deletePersonalizedUpgradePatch(Long id)throws Exception{
       getUpgradeAloneService().deletePersonalizedUpgradePatch(id);
   }
   public static Map<String, Object> getPersonalizedUpgradeTaskById(Long id)throws Exception{
       return getUpgradeAloneService().getPersonalizedUpgradeTaskById(id);
   }
   /**
    * 个人性化升级包 添加
    * @param params
    * @throws Exception
    * @author 余红伟 
    * @date 2017年7月11日 下午3:03:46
    */
   public static void addPersonalizedUpgradePatch(Map<String, Object> params)throws Exception{
       getUpgradeAloneService().addPersonalizedUpgradePatch(params);
   }
   /**
    * 查询设备数 -- 添加任务界面  
    * @param params
    * @return
    * @author 余红伟 
    * @date 2017年7月12日 上午9:21:40
    */
   public static List<Map<String, Object>> getUpgradeDeviceList(Map<String, Object> params)throws Exception{
       return getUpgradeAloneService().getUpgradeDeviceList(params);
   }
   /**
    * 查询设备数 -- 添加任务界面  
    * @param params
    * @return
    * @author 余红伟 
    * @date 2017年7月12日 上午9:21:40
    */
   public static Integer queryDeviceCountByParam(Map<String, Object> params)throws Exception {
       return getUpgradeAloneService().queryDeviceCountByParam(params);
   }
}

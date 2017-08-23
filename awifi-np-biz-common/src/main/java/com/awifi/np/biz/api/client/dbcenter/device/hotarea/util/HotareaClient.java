package com.awifi.np.biz.api.client.dbcenter.device.hotarea.util;

import java.util.List;

import com.awifi.np.biz.api.client.dbcenter.device.hotarea.model.Hotarea;
import com.awifi.np.biz.api.client.dbcenter.device.hotarea.service.HotareaApiService;
import com.awifi.np.biz.common.util.BeanUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月10日 上午10:09:05
 * 创建作者：亢燕翔
 * 文件名称：HotareaClient.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class HotareaClient {

    /**热点管理*/
    private static HotareaApiService hotareaApiService;
    
    /**
     * 热点管理获取总数
     * @param params 请求参数
     * @return count
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月10日 上午11:02:42
     */
    public static int getCountByParam(String params) throws Exception {
        return getHotareaApiService().getCountByParam(params);
    }

    /**
     * 热点管理列表
     * @param params 请求参数
     * @return list
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月10日 上午11:09:37
     */
    public static List<Hotarea> getListByParam(String params) throws Exception {
        return getHotareaApiService().getListByParam(params);
    }
    
    /**
     * 批量导入热点
     * @param params 请求参数
     * @author 亢燕翔 
     * @throws Exception  
     * @date 2017年2月13日 上午10:01:12
     */
    public static void batchAddRelation(String params) throws Exception {
        getHotareaApiService().batchAddRelation(params);
    }

    /**
     * 删除热点
     * @param params 请求参数
     * @author 亢燕翔  
     * @throws Exception  
     * @date 2017年2月13日 下午2:16:12
     */
    public static void deleteByDevMacs(String params) throws Exception {
        getHotareaApiService().deleteByDevMacs(params);
    }
    
    /**
     * 获取HotareaApiService
     * @return HotareaApiService
     * @author 亢燕翔  
     * @date 2017年2月10日 上午11:05:35
     */
    private static HotareaApiService getHotareaApiService(){
        if(hotareaApiService == null){
            hotareaApiService = (HotareaApiService) BeanUtil.getBean("hotareaApiService");
        }
        return hotareaApiService;
    }
}

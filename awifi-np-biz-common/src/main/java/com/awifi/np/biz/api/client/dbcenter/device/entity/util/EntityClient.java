package com.awifi.np.biz.api.client.dbcenter.device.entity.util;

import java.util.List;

import com.awifi.np.biz.api.client.dbcenter.device.entity.model.EntityInfo;
import com.awifi.np.biz.api.client.dbcenter.device.entity.service.EntityApiService;
import com.awifi.np.biz.common.util.BeanUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月8日 上午9:08:53
 * 创建作者：亢燕翔
 * 文件名称：EntityClient.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class EntityClient {

    /**实体设备*/
    private static EntityApiService entityApiService;
    
    /**
     * 设备监控查询总数
     * @param params 参数 
     * @return count
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月8日 上午9:53:42
     */
    public static int getEntityInfoCountByMerId(String params) throws Exception {
        return getEntityApiService().getEntityInfoCountByMerId(params);
    }
    
    /**
     * 设备监控列表
     * @param params 参数
     * @return list
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月8日 上午10:02:27
     */
    public static List<EntityInfo> getEntityInfoListByMerId(String params) throws Exception {
        return getEntityApiService().getEntityInfoListByMerId(params);
    }

    /**
     * 编辑设备
     * @param params 请求参数
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月10日 下午3:31:06
     */
    public static void update(String params) throws Exception {
        getEntityApiService().update(params);
    }
    
    /**
     * 获取deviceApiService
     * @return deviceApiService
     * @author 亢燕翔  
     * @date 2017年2月3日 下午4:57:13
     */
    private static EntityApiService getEntityApiService(){
        if(entityApiService == null){
            entityApiService = (EntityApiService) BeanUtil.getBean("entityApiService");
        }
        return entityApiService;
    }
}

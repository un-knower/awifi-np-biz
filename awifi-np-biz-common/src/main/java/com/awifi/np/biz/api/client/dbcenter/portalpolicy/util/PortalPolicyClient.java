/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月2日 上午9:25:56
* 创建作者：周颖
* 文件名称：PortalPolicyClient.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.portalpolicy.util;

import java.util.Map;

import com.awifi.np.biz.api.client.dbcenter.portalpolicy.service.PortalPolicyService;
import com.awifi.np.biz.common.util.BeanUtil;

public class PortalPolicyClient {
    
    /***/
    private static PortalPolicyService portalPolicyService;
   
    /**
     * 获取实例
     * @return portalPolicyService
     * @author 周颖  
     * @date 2017年6月2日 下午2:05:01
     */
    public static PortalPolicyService getPortalPolicyService(){
        if(portalPolicyService == null){
            portalPolicyService = (PortalPolicyService)BeanUtil.getBean("portalPolicyService");
        }
        return portalPolicyService;
    }
    
    /**
     * 查看商户无感知配置
     * @param merchantId 商户id
     * @return 商户无感知
     * @author 周颖  
     * @throws Exception 
     * @date 2017年5月16日 下午1:44:28
     */
    public static Map<String,Object> getByMerchantId(Long merchantId) throws Exception{
        return getPortalPolicyService().getByMerchantId(merchantId);
    }
    
    /**
     * 添加无感知配置
     * @param params 参数
     * @author 周颖  
     * @throws Exception 
     * @date 2017年5月17日 上午9:29:34
     */
    public static void add(Map<String,Object> params) throws Exception{
        getPortalPolicyService().add(params);
    }
    
    /**
     * 更新无感知配置
     * @param params 参数
     * @author 周颖  
     * @throws Exception 
     * @date 2017年5月17日 上午9:30:53
     */
    public static void update(Map<String,Object> params) throws Exception{
        getPortalPolicyService().update(params);
    }
    
    /**
     * 删除无感知
     * @param id 无感知主键id
     * @author 周颖  
     * @throws Exception 
     * @date 2017年6月2日 下午1:51:08
     */
    public static void delete(Long id) throws Exception{
        getPortalPolicyService().delete(id);
    }
}

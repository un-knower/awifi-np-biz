/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月2日 上午9:26:27
* 创建作者：周颖
* 文件名称：PortalPolicyService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.portalpolicy.service;

import java.util.Map;

public interface PortalPolicyService {
    
    /**
     * 查看商户无感知配置
     * @param merchantId 商户无感知
     * @return 无感知
     * @author 周颖  
     * @throws Exception 
     * @date 2017年5月16日 下午1:45:08
     */
    Map<String, Object> getByMerchantId(Long merchantId) throws Exception;

    /**
     * 添加无感知配置
     * @param params 参数
     * @author 周颖  
     * @throws Exception 
     * @date 2017年5月17日 上午9:29:53
     */
    void add(Map<String, Object> params) throws Exception;

    /**
     * 修改无感知配置
     * @param params 参数
     * @author 周颖  
     * @throws Exception 
     * @date 2017年5月17日 上午9:31:15
     */
    void update(Map<String, Object> params) throws Exception;

    /**
     * 删除无感知
     * @param id 主键id
     * @author 周颖  
     * @throws Exception 
     * @date 2017年6月2日 下午1:51:29
     */
    void delete(Long id) throws Exception;
}

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月22日 上午1:06:04
* 创建作者：许小满
* 文件名称：MerchantService.java
* 版本：  v1.0
* 功能：商户 业务层接口
* 修改记录：
*/
package com.awifi.np.biz.devicebindsrv.merchant.service;

import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;

public interface MerchantService {

    
    /**
     * 获取商户详情
     * @param id 商户id
     * @return 商户详情
     * @author 周颖  
     * @throws Exception 
     * @date 2017年2月6日 上午8:50:18
     */
    Merchant getById(Long id) throws Exception;
    
    /**
     * 判断商户名称是否存在
     * @param merchantName 商户名称
     * @return true 存在 false 不存在
     * @author 周颖  
     * @throws Exception 
     * @date 2017年2月16日 上午11:11:22
     */
    boolean isMerchantNameExist(String merchantName) throws Exception;
    
}

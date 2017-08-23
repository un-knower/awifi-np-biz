/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月8日 上午9:45:22
* 创建作者：尤小平
* 文件名称：MsMerchantService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mersrv.ms.merchant.service;

import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;

public interface MsMerchantService {
    /**
     * 根据merchantId查找商户，排除已撤销的商户.
     * 
     * @param merchantId 商户id
     * @return 商户
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年6月12日 上午11:26:46
     */
    Merchant findMerchantInfoExclude9(long merchantId) throws Exception;
}

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月8日 上午10:33:09
* 创建作者：尤小平
* 文件名称：PubMerchantService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.merchant.service;

import com.awifi.np.biz.timebuysrv.merchant.model.PubMerchant;

public interface PubMerchantService {
    /**
     * 保存商户数据到本地库.
     * 
     * @param merchant PubMerchant
     * @return 是否成功
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月9日 下午4:51:05
     */
    boolean saveMerchant(PubMerchant merchant) throws Exception;

    /**
     * 根据id获取本地库商户数据.
     * 
     * @param id id
     * @return PubMerchant
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月9日 下午4:51:10
     */
    PubMerchant getMerchantById(Long id) throws Exception;
}

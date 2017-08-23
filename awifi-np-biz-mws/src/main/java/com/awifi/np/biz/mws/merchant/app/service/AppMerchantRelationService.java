/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月15日 上午10:32:26
* 创建作者：尤小平
* 文件名称：AppMerchantRelationService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mws.merchant.app.service;

import com.awifi.np.biz.mws.merchant.app.model.StationAppMerchantRelation;

public interface AppMerchantRelationService {
    /**
     * 根据应用id和商户id 删除商户应用.
     * 
     * @param appId 应用id
     * @param merchantId 商户id
     * @return 成功条数
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年6月15日 下午4:43:04
     */
    int deleteByAppIdAndMerchantId(Integer appId, Long merchantId) throws Exception;

    /**
     * 根据商户id 删除商户应用.
     * 
     * @param merchantId 商户id
     * @return 成功条数
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年6月15日 下午4:44:23
     */
    int deleteByMerchantId(Long merchantId) throws Exception;

    /**
     * 根据商户id和应用id查询商户应用信息.
     * 
     * @param appId 应用id
     * @param merchantId 商户id
     * @return 商户应用
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年6月15日 下午4:45:31
     */
    StationAppMerchantRelation seleteByAppIdAndMerchantId(Integer appId, Long merchantId) throws Exception;

    /**
     * 增加商户应用.
     * 
     * @param relation 商户应用
     * @return 成功条数
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年6月15日 下午4:51:27
     */
    int save(StationAppMerchantRelation relation) throws Exception;

    /**
     * 更新商户应用.
     * 
     * @param relation 商户应用
     * @return 成功条数
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年6月15日 下午4:51:39
     */
    int update(StationAppMerchantRelation relation) throws Exception;
    
    /**
     * 判断商户是否开通应用
     * @param merchantId 商户id
     * @param appId 应用id
     * @return true 开通、false 未开通
     * @author 许小满  
     * @date 2017年6月19日 下午7:51:49
     */
    boolean isOpen(Long merchantId, Long appId);
}

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月12日 下午3:21:19
* 创建作者：尤小平
* 文件名称：MerchantAppService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mws.merchant.app.service;

import com.awifi.np.biz.mws.merchant.app.model.StationApp;

import java.util.List;

public interface MerchantAppService {
    /**
     * 根据Id获取应用.
     * 
     * @param id id
     * @return StationApp
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年6月13日 下午3:47:40
     */
    StationApp getAppById(Integer id) throws Exception;

    /**
     * 删除商户下某类型的所有应用.
     * 
     * @param merchantId 商户id
     * @param grantType 类型
     * @return 删除是否成功
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年6月13日 下午3:48:28
     */
    Boolean deleteMerchantAppByType(long merchantId, String grantType) throws Exception;

    /**
     * 根据授权类型查询商户的应用.
     * 
     * @param merchantId 商户id
     * @param grantType 类型
     * @param limitNum 条数
     * @return List<StationApp>
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年6月13日 下午3:49:02
     */
    List<StationApp> selectMerchantAppByType(long merchantId, String grantType, Integer limitNum) throws Exception;

    /**
     * 查询商户已发布和已配置的应用.
     * 
     * @param merchantId 商户id
     * @return List<StationApp>
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年6月12日 下午4:11:17
     */
    List<StationApp> getAppListByMerchantId(Long merchantId) throws Exception;
    
    /**
     * 获取应用商城应用列表（商户未获取的应用列表）.
     * 
     * @param merchantId 商户id
     * @return List<StationApp>
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年6月13日 下午3:51:48
     */
    List<StationApp> getMerchantAppStoreList(Long merchantId) throws Exception;

    /**
     * 获取应用商城应用列表（商户已购应用列表）.
     * 
     * @param merchantId 商户id
     * @return List<StationApp>
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年6月13日 下午3:52:20
     */
    List<StationApp> getMerchantAppBuyList(Long merchantId) throws Exception;
}

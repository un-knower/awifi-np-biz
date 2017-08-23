/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月12日 下午3:22:33
* 创建作者：尤小平
* 文件名称：MerchantAppServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mws.merchant.app.service.impl;

//import com.awifi.np.biz.mersrv.ms.merchantapp.dao.StationAppDao;
//import com.awifi.np.biz.mersrv.ms.merchantapp.model.StationApp;
//import com.awifi.np.biz.mersrv.ms.merchantapp.service.MerchantAppService;
import com.awifi.np.biz.mws.merchant.app.dao.StationAppDao;
import com.awifi.np.biz.mws.merchant.app.model.StationApp;
import com.awifi.np.biz.mws.merchant.app.service.MerchantAppService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "merchantAppService")
public class MerchantAppServiceImpl implements MerchantAppService {
    /**
     * Logger
     */
    
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * StationAppDao
     */
    @Resource
    private StationAppDao stationAppDao;

    /**
     * 根据Id获取应用.
     *
     * @param id id
     * @return StationApp
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年6月13日 下午3:47:40
     */
    @Override
    public StationApp getAppById(Integer id) throws Exception {
        logger.debug("id = " + id);
        return stationAppDao.selectByPrimaryKey(id);
    }

    /**
     * 删除商户下某类型的所有应用.
     *
     * @param merchantId 商户id
     * @param grantType  类型
     * @return 删除是否成功
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年6月13日 下午3:48:28
     */
    @Override
    public Boolean deleteMerchantAppByType(long merchantId, String grantType) throws Exception {
        logger.debug("merchantId = " + merchantId + ", grantType = " + grantType);
        StationApp record = new StationApp();
        record.setMerchantId(merchantId);
        record.setGrantType(grantType);
        return stationAppDao.deleteRelationsByParam(record) > 0;
    }

    /**
     * 根据授权类型查询商户的应用.
     *
     * @param merchantId 商户id
     * @param grantType  类型
     * @param limitNum   条数
     * @return List<StationApp>
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年6月13日 下午3:49:02
     */
    @Override
    public List<StationApp> selectMerchantAppByType(long merchantId, String grantType, Integer limitNum)
            throws Exception {
        logger.debug("merchantId = " + merchantId + ", grantType = " + grantType + ", limitNum = " + limitNum);
        StationApp record = new StationApp();
        record.setMerchantId(merchantId);
        record.setGrantType(grantType);
        record.setLimitNum(limitNum);
        return stationAppDao.selectRelationsByParam(record);
    }

    /**
     * 查询商户已发布和已配置的应用.
     * 
     * @param merchantId 商户id
     * @return List<StationApp>
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年6月12日 下午4:11:17
     */
    public List<StationApp> getAppListByMerchantId(Long merchantId) throws Exception {
        logger.debug("merchantId = " + merchantId);
        return stationAppDao.getAppListByMerchantId(merchantId);
    }

    /**
     * 获取应用商城应用列表（商户未获取的应用列表）.
     *
     * @param merchantId 商户id
     * @return List<StationApp>
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年6月13日 下午3:51:48
     */
    @Override
    public List<StationApp> getMerchantAppStoreList(Long merchantId) throws Exception {
        logger.debug("merchantId = " + merchantId);
        return stationAppDao.getMerchantAppStoreList(merchantId);
    }

    /**
     * 获取应用商城应用列表（商户已购应用列表）.
     *
     * @param merchantId 商户id
     * @return List<StationApp>
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年6月13日 下午3:52:20
     */
    @Override
    public List<StationApp> getMerchantAppBuyList(Long merchantId) throws Exception {
        logger.debug("merchantId = " + merchantId);
        return stationAppDao.getMerchantAppBuyList(merchantId);
    }
}

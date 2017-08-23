/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年6月15日 上午10:32:42
 * 创建作者：尤小平
 * 文件名称：AppMerchantRelationServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.mws.merchant.app.service.impl;

import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.mws.merchant.app.dao.StationAppMerchantRelationDao;
import com.awifi.np.biz.mws.merchant.app.model.StationAppMerchantRelation;
import com.awifi.np.biz.mws.merchant.app.service.AppMerchantRelationService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

@Service(value = "appMerchantRelationService")
public class AppMerchantRelationServiceImpl implements AppMerchantRelationService {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * StationAppMerchantRelationDao
     */
    @Resource
    private StationAppMerchantRelationDao stationAppMerchantRelationDao;

    /**
     * 根据应用id和商户id 删除商户应用.
     *
     * @param appId      应用id
     * @param merchantId 商户id
     * @return 成功条数
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年6月15日 下午4:43:04
     */
    @Override
    public int deleteByAppIdAndMerchantId(Integer appId, Long merchantId) throws Exception {
        logger.debug("appId = " + appId + ", merchantId = " + merchantId);
        StationAppMerchantRelation relation = new StationAppMerchantRelation();
        relation.setAppId(appId);
        relation.setMerchantId(merchantId);
        return stationAppMerchantRelationDao.delete(relation);
    }

    /**
     * 根据商户id 删除商户应用.
     *
     * @param merchantId 商户id
     * @return 成功条数
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年6月15日 下午4:44:23
     */
    @Override
    public int deleteByMerchantId(Long merchantId) throws Exception {
        logger.debug("merchantId = " + merchantId);
        StationAppMerchantRelation relation = new StationAppMerchantRelation();
        relation.setMerchantId(merchantId);
        return stationAppMerchantRelationDao.delete(relation);
    }

    /**
     * 根据商户id和应用id查询商户应用信息.
     *
     * @param appId      应用id
     * @param merchantId 商户id
     * @return 商户应用
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年6月15日 下午4:45:31
     */
    @Override
    public StationAppMerchantRelation seleteByAppIdAndMerchantId(Integer appId, Long merchantId) throws Exception {
        logger.debug("appId = " + appId + ", merchantId = " + merchantId);
        StationAppMerchantRelation relation = new StationAppMerchantRelation();
        relation.setAppId(appId);
        relation.setMerchantId(merchantId);
        List<StationAppMerchantRelation> list = stationAppMerchantRelationDao.select(relation);
        logger.debug("relation list = " + JsonUtil.toJson(list));
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 增加商户应用.
     *
     * @param relation 商户应用
     * @return 成功条数
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年6月15日 下午4:51:27
     */
    @Override
    public int save(StationAppMerchantRelation relation) throws Exception {
        logger.debug("relation = " + JsonUtil.toJson(relation));
        return stationAppMerchantRelationDao.insertSelective(relation);
    }

    /**
     * 更新商户应用.
     *
     * @param relation 商户应用
     * @return 成功条数
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年6月15日 下午4:51:39
     */
    @Override
    public int update(StationAppMerchantRelation relation) throws Exception {
        logger.debug("relation = " + JsonUtil.toJson(relation));
        return stationAppMerchantRelationDao.updateByPrimaryKeySelective(relation);
    }
    
    /**
     * 判断商户是否开通应用
     * @param merchantId 商户id
     * @param appId 应用id
     * @return true 开通、false 未开通
     * @author 许小满  
     * @date 2017年6月19日 下午7:51:49
     */
    public boolean isOpen(Long merchantId, Long appId){
        int num = stationAppMerchantRelationDao.getNumByMerIdAndAppId(merchantId, appId, 1);
        if(num > 0){
            return true;
        }
        return false;
    }
}

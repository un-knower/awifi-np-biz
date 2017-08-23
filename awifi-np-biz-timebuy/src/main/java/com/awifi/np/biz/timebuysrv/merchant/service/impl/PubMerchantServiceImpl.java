/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月8日 上午10:32:55
* 创建作者：尤小平
* 文件名称：PubMerchantServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.merchant.service.impl;

import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.timebuysrv.merchant.dao.PubMerchantDao;
import com.awifi.np.biz.timebuysrv.merchant.model.PubMerchant;
import com.awifi.np.biz.timebuysrv.merchant.service.PubMerchantService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PubMerchantServiceImpl implements PubMerchantService {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * PubMerchantDao
     */
    @Resource
    private PubMerchantDao pubMerchantDao;

    /**
     * 保存商户数据到本地库.
     * 
     * @param merchant PubMerchant
     * @return 是否成功
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月9日 下午4:51:05
     */
    public boolean saveMerchant(PubMerchant merchant) throws Exception {

        boolean result = false;
        logger.debug("merchant: " + JsonUtil.toJson(merchant));

        if (merchant == null || merchant.getId() == null) {
            return result;
        }

        PubMerchant merchantTemp = getMerchantById(merchant.getId());

        if (merchantTemp == null) {
            logger.debug("insert merchant: " + JsonUtil.toJson(merchant));
            int num = addMerchant(merchant);
            if(num > 0){
                result = true;
            }
        } else {
            logger.warn("merchant已经存在, id= " + merchantTemp.getId() + ", merchant= " + JsonUtil.toJson(merchantTemp));
        }
        
        return result;
    }

    /**
     * 根据id获取本地库商户数据.
     * 
     * @param id id
     * @return PubMerchant
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月9日 下午4:51:10
     */
    public PubMerchant getMerchantById(Long id) throws Exception {

        logger.debug("id = " + id);

        PubMerchant merchant = pubMerchantDao.selectByPrimaryKey(id);

        logger.debug("merchant = " + JsonUtil.toJson(merchant));

        return merchant;
    }

    /**
     * 新增商户数据到本地库.
     * 
     * @param merchant PubMerchant
     * @return int
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月9日 下午4:52:39
     */
    private int addMerchant(PubMerchant merchant) throws Exception {

        logger.debug("merchant = " + JsonUtil.toJson(merchant));

        int result = pubMerchantDao.insertSelective(merchant);

        logger.debug("save num = " + result);

        return result;
    }

    /**
     * for testing only.
     * 
     * @param pubMerchantDao PubMerchantDao
     * @author 尤小平  
     * @date 2017年5月9日 下午5:33:55
     */
    protected void setPubMerchantDao(PubMerchantDao pubMerchantDao) {
        this.pubMerchantDao = pubMerchantDao;
    }
}

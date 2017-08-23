/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月25日 下午1:32:30
* 创建作者：尤小平
* 文件名称：MerchantManagerServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.merchant.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.timebuysrv.merchant.dao.MerchantExtendsDao;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantExtends;
import com.awifi.np.biz.timebuysrv.merchant.service.MerchantExtendsService;

@Service
public class MerchantExtendsServiceImpl implements MerchantExtendsService {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * merchantExtendsDao
     */
    @Resource
    private MerchantExtendsDao merchantExtendsDao;

    /**
     * 根据主键获取MerchantManager.
     *
     * @param id 主键
     * @return MerchantExtends
     * @throws Exception 异常
     * @author 张智威
     * @date 2017年4月27日 下午8:12:29
     */
    @Override
    public MerchantExtends selectByPrimaryKey(Long id) throws Exception {
        logger.debug("id:" + id);
        return merchantExtendsDao.selectByPrimaryKey(id);
    }

    
}

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年4月7日 上午11:22:17
 * 创建作者：尤小平
 * 文件名称：MerchantNoticeServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.timebuysrv.merchant.service.impl;

import com.alibaba.fastjson.JSON;
import com.awifi.np.biz.timebuysrv.merchant.dao.MerchantNoticeDao;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantNotice;
import com.awifi.np.biz.timebuysrv.merchant.service.MerchantNoticeService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MerchantNoticeServiceImpl implements MerchantNoticeService {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * MerchantNoticeDao
     */
    @Resource
    private MerchantNoticeDao merchantNoticeDao;

    /**
     * 根据id获取单个商户滚动消息信息.
     *
     * @param id id
     * @return MerchantNotice
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月7日 下午3:16:21
     */
    @Override
    public MerchantNotice getMerchantNoticeById(int id) throws Exception {
        logger.debug("id:" + id);
        return merchantNoticeDao.selectByPrimaryKey(id);
    }

    /**
     * 根据商户id获取商户滚动消息信息列表.
     *
     * @param merid merid
     * @return List<MerchantNotice>
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月7日 下午3:17:03
     */
    @Override
    public List<MerchantNotice> getListByMerid(Long merid) throws Exception {
        logger.debug("merid=" + merid);
        return merchantNoticeDao.selectListByMerid(merid);
    }

    /**
     * 添加单个商户滚动消息信息.
     *
     * @param merchantNotice MerchantNotice
     * @return int
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月7日 下午3:17:29
     */
    @Override
    public int addMerchantNotice(MerchantNotice merchantNotice) throws Exception {
        logger.debug("merchantNotice:" + JSON.toJSONString(merchantNotice));
        return merchantNoticeDao.insert(merchantNotice);
    }

    /**
     * 根据id更新单个商户滚动消息信息.
     *
     * @param merchantNotice MerchantNotice
     * @return int
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月7日 下午3:17:55
     */
    @Override
    public int updateMerchantNoticeById(MerchantNotice merchantNotice) throws Exception {
        logger.debug("merchantNotice:" + JSON.toJSONString(merchantNotice));
        return merchantNoticeDao.updateByPrimaryKey(merchantNotice);
    }

    /**
     * 根据id删除单个商户滚动消息信息.
     *
     * @param id id
     * @return int
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月7日 下午3:18:15
     */
    @Override
    public int deleteMerchantNoticeById(int id) throws Exception {
        return merchantNoticeDao.delete(id);
    }

    /**
     * for testing only.
     * 
     * @param merchantNoticeDao MerchantNoticeDao
     * @author 尤小平
     * @date 2017年4月10日 下午2:04:10
     */
    public void setMerchantNoticeDao(MerchantNoticeDao merchantNoticeDao) {
        this.merchantNoticeDao = merchantNoticeDao;
    }
}

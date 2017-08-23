/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月10日 下午3:05:39
* 创建作者：尤小平
* 文件名称：MerchantPicServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.merchant.service.impl;

import com.alibaba.fastjson.JSON;
import com.awifi.np.biz.timebuysrv.merchant.dao.MerchantPicDao;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantPic;
import com.awifi.np.biz.timebuysrv.merchant.service.MerchantPicService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MerchantPicServiceImpl implements MerchantPicService {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * MerchantPicDao
     */
    @Resource
    private MerchantPicDao merchantPicDao;

    /**
     * 根据id获取单个商户滚动图片信息.
     *
     * @param id id
     * @return MerchantPic
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月10日 下午4:27:58
     */
    @Override
    public MerchantPic getMerchantPicById(int id) throws Exception {
        logger.debug("id:" + id);
        return merchantPicDao.selectByPrimaryKey(id);
    }

    /**
     * 根据商户id获取商户滚动图片列表.
     *
     * @param merid merid
     * @return List<MerchantPic>
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月10日 下午4:26:59
     */
    @Override
    public List<MerchantPic> getListByMerid(Long merid) throws Exception {
        logger.debug("merid=" + merid);
        return merchantPicDao.selectListByMerid(merid);
    }

    /**
     * 添加单个商户滚动图片信息.
     *
     * @param merchantPic MerchantPic
     * @return int
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月10日 下午4:27:24
     */
    @Override
    public int addMerchantPic(MerchantPic merchantPic) throws Exception {
        logger.debug("merchantPic:" + JSON.toJSONString(merchantPic));
        return merchantPicDao.insert(merchantPic);
    }

    /**
     * 根据id更新单个商户滚动图片信息.
     *
     * @param merchantPic MerchantPic
     * @return int
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月10日 下午4:27:44
     */
    @Override
    public int updateMerchantPicById(MerchantPic merchantPic) throws Exception {
        logger.debug("merchantPic:" + JSON.toJSONString(merchantPic));
        return merchantPicDao.updateByPrimaryKey(merchantPic);
    }

    /**
     * 根据id删除单个商户滚动图片信息.
     *
     * @param id id
     * @return int
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月10日 下午4:28:09
     */
    @Override
    public int deleteMerchantPicById(int id) throws Exception {
        logger.debug("id:" + id);
        return merchantPicDao.delete(id);
    }

    /**
     * for testing only.
     * 
     * @param merchantPicDao MerchantPicDao
     * @author 尤小平
     * @date 2017年4月10日 下午8:08:42
     */
    public void setMerchantPicDao(MerchantPicDao merchantPicDao) {
        this.merchantPicDao = merchantPicDao;
    }
}

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年4月1日 下午4:33:10
 * 创建作者：尤小平
 * 文件名称：MerchantNewsServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.timebuysrv.merchant.service.impl;

import com.alibaba.fastjson.JSON;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.timebuysrv.merchant.dao.MerchantNewsDao;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantNews;
import com.awifi.np.biz.timebuysrv.merchant.service.MerchantNewsService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MerchantNewsServiceImpl implements MerchantNewsService {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * MerchantNewsDao
     */
    @Resource
    private MerchantNewsDao merchantNewsDao;

    /**
     * 根据Id查询单个MerchantNews信息.
     *
     * @param id
     * @return MerchantNews
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月5日 下午3:30:25
     */
    
    @Override
    public MerchantNews getMerchantNewsById(int id) throws Exception {
        logger.debug("id:" + id);
        return merchantNewsDao.selectByPrimaryKey(id);
    }

    /**
     * 查询MerchantNews列表.
     *
     * @param merchantNews MerchantNews
     * @param page
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月5日 下午3:27:54
     */
    @Override
    public void getListByParam(MerchantNews merchantNews, Page<MerchantNews> page) throws Exception {
        logger.debug("merchantNews:" + JSON.toJSONString(merchantNews) + ", page:" + JSON.toJSONString(page));

        List<MerchantNews> list = merchantNewsDao.getListByParam(merchantNews, page);

        logger.debug("list size:" + list != null ? list.size() : 0);

        page.setRecords(list);
    }

    /**
     * 新增单个MerchantNews信息.
     *
     * @param merchantNews MerchantNews
     * @return int
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月5日 下午3:26:53
     */
    @Override
    public int addMerchantNews(MerchantNews merchantNews) throws Exception {
        logger.debug("merchantNews:" + JSON.toJSONString(merchantNews));
        return merchantNewsDao.insert(merchantNews);
    }

    /**
     * 更新单个MerchantNews信息.
     *
     * @param merchantNews MerchantNews
     * @return int
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月5日 下午3:29:11
     */
    @Override
    public int updateMerchantNewsById(MerchantNews merchantNews) throws Exception {
        logger.debug("merchantNews:" + JSON.toJSONString(merchantNews));
        return merchantNewsDao.updateByPrimaryKey(merchantNews);
    }

    /**
     * 根据Id删除单个MerchantNews信息.
     *
     * @param id
     * @return int
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月5日 下午3:31:19
     */
    @Override
    public int deleteMerchantNewsById(int id) throws Exception {
        logger.debug("id:" + id);
        return merchantNewsDao.deleteByPrimaryKey(id);
    }

    /**
     * 根据商户Id获取商户介绍信息列表.
     *
     * @param merid 商户Id
     * @return List<MerchantNews>
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月7日 上午10:30:23
     */
    @Override
    public List<MerchantNews> getListByMerid(Long merid) throws Exception {
        return merchantNewsDao.selectListByMerid(merid);
    }

    /**
     * for test only.
     *
     * @param merchantNewsDao MerchantNewsDao
     * @author 尤小平
     * @date 2017年4月6日 下午4:39:03
     */
    public void setMerchantNewsDao(MerchantNewsDao merchantNewsDao) {
        this.merchantNewsDao = merchantNewsDao;
    }
}

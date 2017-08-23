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

import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.timebuysrv.merchant.dao.MerchantManagerDao;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantManager;
import com.awifi.np.biz.timebuysrv.merchant.service.MerchantManagerService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MerchantManagerServiceImpl implements MerchantManagerService {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * MerchantManagerDao
     */
    @Resource
    private MerchantManagerDao merchantManagerDao;

    /**
     * 根据主键获取MerchantManager.
     *
     * @param id 主键
     * @return MerchantManager
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月27日 下午8:12:29
     */
    @Override
    public MerchantManager queryById(Long id) throws Exception {
        logger.debug("id:" + id);
        if (id > 0) {
            return merchantManagerDao.selectByPrimaryKey(id);
        } else {
            logger.error("id 不能为空");
            return null;
        }
    }

    /**
     * 新增管理员.
     *
     * @param merchantManager MerchantManager
     * @return 是否成功
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月27日 下午8:13:33
     */
    @Override
    public boolean insert(MerchantManager merchantManager) throws Exception {
        logger.debug("merchantManager:" + JsonUtil.toJson(merchantManager));
        boolean result = false;

        if (merchantManager == null) {
            logger.error("merchantManager 不能为空");
            return result;
        }

        if (merchantManagerDao.insertSelective(merchantManager) > 0) {
            result = true;
        }

        return result;
    }

    /**
     * 根据主键修改管理员信息.
     *
     * @param merchantManager MerchantManager
     * @return 是否成功
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月27日 下午8:13:59
     */
    @Override
    public boolean update(MerchantManager merchantManager) throws Exception {
        logger.debug("merchantManager:" + JsonUtil.toJson(merchantManager));

        if (merchantManager == null || merchantManager.getId() < 1) {
            logger.error("merchantManager 不能为空, 且id不能为空");
            return false;
        }

        int num = merchantManagerDao.updateByPrimaryKey(merchantManager);
        if (num > 0) {
            return true;
        }
        return false;
    }

    /**
     * 根据主键删除管理员信息.
     *
     * @param id 主键
     * @return 是否成功
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月27日 下午8:14:16
     */
    @Override
    public boolean deleteById(Long id) throws Exception {
        logger.debug("id:" + id);

        if (merchantManagerDao.deleteByPrimaryKey(id) > 0) {
            return true;
        }

        return false;
    }

    /**
     * 获取管理员信息列表.
     *
     * @param merchantManager MerchantManager
     * @return List<MerchantManager>
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月5日 下午3:27:54
     */
    @Override
    public List<MerchantManager> getListByMerchantManager(MerchantManager merchantManager) throws Exception {
        logger.debug("merchantManager:" + JsonUtil.toJson(merchantManager));

        List<MerchantManager> list = merchantManagerDao.getListByMerchantManager(merchantManager);
        logger.debug("list.size()=" + list != null ? list.size() : 0);

        return list;
    }

    /**
     * 获取管理员信息列表，分页
     *
     * @param merchantManager MerchantManager
     * @param page Page
     * @return Page<MerchantManager>
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月27日 下午8:14:41
     */
    @Override
    public Page<MerchantManager> getListByParams(MerchantManager merchantManager, Page<MerchantManager> page)
            throws Exception {
        logger.debug("merchantManager:" + JsonUtil.toJson(merchantManager) + ", page:" + JsonUtil.toJson(page));

        List<MerchantManager> list = merchantManagerDao.getListByParams(merchantManager, page);
        logger.debug("list=" + JsonUtil.toJson(list));

        int totalRecord = getCountByParams(merchantManager);
        logger.debug("totalRecord=" + totalRecord);

        int totalPage = totalRecord / page.getPageSize();
        if (totalRecord % page.getPageSize() > 0) {
            totalPage = totalPage + 1;
        }
        page.setTotalRecord(totalRecord);
        page.setTotalPage(totalPage);
        page.setRecords(list);

        return page;
    }

    /**
     * 获取管理员信息列表总条数.
     *
     * @param merchantManager MerchantManager
     * @return 总条数
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月27日 下午8:15:21
     */
    @Override
    public int getCountByParams(MerchantManager merchantManager) throws Exception {
        logger.debug("merchantManager:" + JsonUtil.toJson(merchantManager));

        int count = merchantManagerDao.getCountByParams(merchantManager);
        logger.debug("count=" + count);

        return count;
    }

    /**
     * for testing only.
     * 
     * @param merchantManagerDao MerchantManagerDao
     * @author 尤小平  
     * @date 2017年5月2日 下午2:51:56
     */
    public void setMerchantManagerDao(MerchantManagerDao merchantManagerDao) {
        this.merchantManagerDao = merchantManagerDao;
    }
}

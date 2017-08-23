/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年4月1日 下午4:32:49
 * 创建作者：尤小平
 * 文件名称：MerchantNewsService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.timebuysrv.merchant.service;

import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantNews;

import java.util.List;

public interface MerchantNewsService {
    /**
     * 根据Id查询单个MerchantNews信息.
     *
     * @param id id
     * @return MerchantNews
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月5日 下午3:30:25
     */
    MerchantNews getMerchantNewsById(int id) throws Exception;

    /**
     * 查询MerchantNews列表.
     *
     * @param merchantNews MerchantNews
     * @param page Page
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月5日 下午3:27:54
     */
    void getListByParam(MerchantNews merchantNews, Page<MerchantNews> page) throws Exception;

    /**
     * 新增单个MerchantNews信息.
     *
     * @param merchantNews MerchantNews
     * @return int
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月5日 下午3:26:53
     */
    int addMerchantNews(MerchantNews merchantNews) throws Exception;

    /**
     * 更新单个MerchantNews信息.
     *
     * @param merchantNews MerchantNews
     * @return int
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月5日 下午3:29:11
     */
    int updateMerchantNewsById(MerchantNews merchantNews) throws Exception;

    /**
     * 根据Id删除单个MerchantNews信息.
     *
     * @param id id
     * @return int
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月5日 下午3:31:19
     */
    int deleteMerchantNewsById(int id) throws Exception;

    /**
     * 根据商户Id获取商户滚动消息信息列表.
     *
     * @param merid merid
     * @return List<MerchantNews>
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月7日 上午10:30:23
     */
    List<MerchantNews> getListByMerid(Long merid) throws Exception;
}

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年4月7日 上午11:21:53
 * 创建作者：尤小平
 * 文件名称：MerchantNoticeService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.timebuysrv.merchant.service;

import com.awifi.np.biz.timebuysrv.merchant.model.MerchantNotice;

import java.util.List;

public interface MerchantNoticeService {
    /**
     * 根据id获取单个商户滚动消息信息.
     *
     * @param id id
     * @return MerchantNotice
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月7日 下午3:16:21
     */
    MerchantNotice getMerchantNoticeById(int id) throws Exception;

    /**
     * 根据商户id获取商户滚动消息信息列表.
     *
     * @param merid merid
     * @return List<MerchantNotice>
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月7日 下午3:17:03
     */
    List<MerchantNotice> getListByMerid(Long merid) throws Exception;

    /**
     * 添加单个商户滚动消息信息.
     *
     * @param merchantNotice MerchantNotice
     * @return int
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月7日 下午3:17:29
     */
    int addMerchantNotice(MerchantNotice merchantNotice) throws Exception;

    /**
     * 根据id更新单个商户滚动消息信息.
     *
     * @param merchantNotice MerchantNotice
     * @return int
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月7日 下午3:17:55
     */
    int updateMerchantNoticeById(MerchantNotice merchantNotice) throws Exception;

    /**
     * 根据id删除单个商户滚动消息信息.
     *
     * @param id id
     * @return int
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月7日 下午3:18:15
     */
    int deleteMerchantNoticeById(int id) throws Exception;
}

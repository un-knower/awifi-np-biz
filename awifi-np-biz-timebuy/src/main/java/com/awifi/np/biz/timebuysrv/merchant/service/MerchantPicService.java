/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月10日 下午3:05:06
* 创建作者：尤小平
* 文件名称：MerchantPicService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.merchant.service;

import com.awifi.np.biz.timebuysrv.merchant.model.MerchantPic;

import java.util.List;

public interface MerchantPicService {
    /**
     * 根据id获取单个商户滚动图片信息.
     * 
     * @param id id
     * @return MerchantPic
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月10日 下午4:27:58
     */
    MerchantPic getMerchantPicById(int id) throws Exception;

    /**
     * 根据商户id获取商户滚动图片列表.
     * 
     * @param merid merid
     * @return List<MerchantPic>
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月10日 下午4:26:59
     */
    List<MerchantPic> getListByMerid(Long merid) throws Exception;

    /**
     * 添加单个商户滚动图片信息.
     * 
     * @param merchantPic MerchantPic
     * @return int
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月10日 下午4:27:24
     */
    int addMerchantPic(MerchantPic merchantPic) throws Exception;

    /**
     * 根据id更新单个商户滚动图片信息.
     * 
     * @param merchantPic MerchantPic
     * @return int
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月10日 下午4:27:44
     */
    int updateMerchantPicById(MerchantPic merchantPic) throws Exception;

    /**
     * 根据id删除单个商户滚动图片信息.
     * 
     * @param id id
     * @return int
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月10日 下午4:28:09
     */
    int deleteMerchantPicById(int id) throws Exception;
}

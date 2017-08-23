/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月8日 上午9:35:57
* 创建作者：余红伟
* 文件名称：MerchantPicService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mws.merchant.pic.service;

import java.util.List;

import com.awifi.np.biz.mws.merchant.pic.model.MerchantPic;



public interface MerchantPicService {
    /**
     * 获取商户滚动图片接口
     * 
     * @param merchantId
     * @return
     * @author 余红伟 
     * @date 2017年6月8日 上午10:36:09
     */
    public List<MerchantPic> getMerCarouselByMerId(Long merchantId);
    
    /**
     * 获取商户照片墙图片接口
     * 
     * @param merchantId
     * @return
     * @author 余红伟 
     * @date 2017年6月8日 上午10:36:14
     */
    public List<MerchantPic> getMerWallAlbumByMerId(Long merchantId);
    
    /**
     *  查询结果为空，或者查询错误时，返回默认的滚动图片列表
     * @return
     * @author 余红伟 
     * @date 2017年6月19日 下午4:18:48
     */
    public List<MerchantPic> getMerCarouselByDefault();
    
    
    /**
     * 更新图片
     * @param merchantPic
     * @return
     * @author 余红伟 
     * @date 2017年6月9日 下午3:05:27
     */
    public int updateMerchantPic(MerchantPic merchantPic);
    
    /**
     * 添加图片
     * @param merchantPic
     * @return
     * @author 余红伟 
     * @date 2017年6月14日 下午3:32:47
     */
    public int addMerchantPic(MerchantPic merchantPic);
    
    /**
     * 添加滚动图片
     * @param merchantPic
     * @return
     * @author 余红伟 
     * @date 2017年6月15日 下午5:08:21
     */
    public int addMercarousel(MerchantPic merchantPic);
    
    /**
     * 添加照片墙图片
     * @param merchantPic
     * @return
     * @author 余红伟 
     * @date 2017年6月15日 下午5:07:13
     */
    public int addMerWallalbum(MerchantPic merchantPic);
    
    /**
     * 根据商户Id和图片类型批量删除图片
     * @param merid
     * @param picType
     * @return
     * @author 余红伟 
     * @date 2017年6月14日 下午3:54:36
     */
    public int deleteByMeridAndPicType(Long merid,Integer picType);
    
    /**
     * 逻辑删除数据库图片，根据图片id
     * @param id
     * @return
     * @author 余红伟 
     * @date 2017年6月15日 下午4:08:11
     */
    public int deleteMerPic(Long id);
    /**
     * 调用微站接口获取滚动图片
     * @param merchantId
     * @return
     * @throws Exception
     * @author 余红伟 
     * @date 2017年6月19日 下午5:14:38
     */
    List<MerchantPic> getMerCarouselByMerIdThroughMws(Long merchantId) throws Exception ;
    /**
     * 调用微站接口获取图片墙
     * @param merchantId
     * @return
     * @author 余红伟 
     * @date 2017年6月19日 下午5:16:48
     */
    List<MerchantPic> getMerWallAlbumByMerIdThroughMws(Long merchantId) throws Exception;
}

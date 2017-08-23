/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月15日 下午7:21:44
* 创建作者：方志伟
* 文件名称：MerchantCommentService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mws.comment.service;

import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.mws.comment.model.MerchantComment;

public interface MerchantCommentService {
	/**
	 * 查询商户评论
	 * @param page 
	 * @param merchantId 搜索参数
	 * @throws Exception 异常
	 * @author 季振宇  
	 * @date Jun 16, 2017 4:38:54 PM
	 */
    void getListByParam(Page<MerchantComment> page,Long merchantId) throws Exception;
    /**
     * 商户评论内容接口
     * @param merchantId 商户id
     * @param userPhone  手机号
     * @param content 评论内容
     * @param ip ip地址
     * @throws Exception   
     * @return 结果
     * @author 方志伟  
     * @throws Exception 
     * @date 2017年6月15日 下午8:14:44
     */
    Long add(Long merchantId, String userPhone, String content, String ip) throws Exception;
}

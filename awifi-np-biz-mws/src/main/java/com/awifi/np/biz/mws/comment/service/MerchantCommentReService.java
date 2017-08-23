package com.awifi.np.biz.mws.comment.service;

import java.util.List;
import java.util.Map;

import com.awifi.np.biz.mws.comment.model.MerchantCommentReply;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017年6月15日 下午8:05:44
 * 创建作者：许尚敏
 * 文件名称：MerchantCommentReplyService.java
 * 版本：  v1.0
 * 功能：添加商户评论-回复
 * 修改记录：
 */
public interface MerchantCommentReService {
	
    /**
     * 添加商户评论-回复
     * @param replyParam 参数
     * @throws Exception 异常o
     * @author 许尚敏  
     * @date 2017年6月15日 下午20:10:21
     */
    void addReply(Map<String, Object> replyParam) throws Exception;
	
	/**
	 * 查询商户评论回复列表
	 * @param commentId 评论id
	 * @return 商户评论回复列表
	 * @throws Exception 异常
	 * @author 季振宇  
	 * @date Jun 16, 2017 4:38:02 PM
	 */
    List<MerchantCommentReply> getReplyListByCommentId(Long commentId) throws Exception;

}

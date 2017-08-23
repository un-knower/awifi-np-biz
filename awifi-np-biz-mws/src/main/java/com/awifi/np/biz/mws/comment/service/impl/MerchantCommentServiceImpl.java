/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月15日 下午7:21:56
* 创建作者：方志伟
* 文件名称：MerchantCommentServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mws.comment.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.user.model.PubUserAuth;
import com.awifi.np.biz.api.client.dbcenter.user.util.UserAuthClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.mws.comment.dao.MerchantCommentDao;
import com.awifi.np.biz.mws.comment.model.MerchantComment;
import com.awifi.np.biz.mws.comment.model.MerchantCommentReply;
import com.awifi.np.biz.mws.comment.service.MerchantCommentPicService;
import com.awifi.np.biz.mws.comment.service.MerchantCommentReService;
import com.awifi.np.biz.mws.comment.service.MerchantCommentService;
	
@Service("merchantCommentService")
public class MerchantCommentServiceImpl implements MerchantCommentService{
    
    /**
     * 注解
     */
    @Resource(name="merchantCommentDao")
    private MerchantCommentDao merchantCommentDao;
    
	/**
	 * 商户评论图片dao
	 */
    @Resource
	private MerchantCommentPicService merchantCommentPicService;
    
	/**
	 * 商户评论回复dao
	 */
    @Resource
    private MerchantCommentReService merchantCommentReplyService;
    
    /**
     * 商户评论——内容实现
     * @param merchantId 商户id
     * @param userPhone 手机号
     * @param content 评论内容
     * @param ip ip地址
     * @return 结果
     * @throws Exception 
     * @author 方志伟  
     * @date 2017年6月16日 上午11:10:32
     */
    public Long add(Long merchantId, String userPhone, String content, String ip) throws Exception {
        content = content.replace("[^\\u0000-\\uFFFF]", "");//将不符合正则的评论字符替换为空
        String userName = "游客";
        MerchantComment comment = new MerchantComment();
        comment.setCommentUserId(0L);
        comment.setIsTop(0);//设置内容是否置顶
        comment.setStatus(1);//状态
        //comment.setCreateDate(new Date());//创建时间
        //comment.setStatusDate(new Date());//状态时间
        //comment.setTopDate(new Date());
        comment.setContent(content);//评论内容
        comment.setMerchantId(merchantId);//商户编号
        if(ip != null){
            ip = ip.substring(ip.indexOf(".") + 1, ip.length());//截取ip地址
            ip = ip.substring(ip.indexOf(".") + 1, ip.length());//截取ip地址
            userName = userName + " *.*." + ip;
        }
        if(!StringUtils.isBlank(userPhone)){
            PubUserAuth pubUserAuth = UserAuthClient.getUserByLogName(userPhone);//调数据中心接口，校验是否是用户
            if(pubUserAuth != null){
                userName = userPhone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");//对手机号码中间四位作隐藏显示
                comment.setCommentUserId(pubUserAuth.getUserId());//设置评论用户编号
            }
        } 
        comment.setCommentUserName(userName);//设置游客评论名
        merchantCommentDao.add(comment);//将评论信息插入数据库
        Long commentId = comment.getCommentId();//获取评论表主键id
        return commentId;
    }
    
    /**
     * 商户评论列表
     * @param page page
     * @param merchantId 商户id
     * @author 季振宇 
     * @throws Exception 
     * @date 2017年2月3日 上午9:15:22
     */
    public void getListByParam(Page<MerchantComment> page,Long merchantId) throws Exception{
        int count = merchantCommentDao.getCountByParam(merchantId);//符合条件的总数
        page.setTotalRecord(count);//page设置总条数
        if(count <= 0){//如果小于0 直接返回
            return;
        }
        //符合条件的记录
        List<MerchantComment> commentList = merchantCommentDao.getListByParam(merchantId,page.getBegin(),page.getPageSize());
        for (MerchantComment merchantComment : commentList) {//依据commentid循环设置商户评论的图片和回复
            //查询评论中的图片url
            List<String> commentPicUrls = merchantCommentPicService.getPicListByCommentId(merchantComment.getCommentId());//
            merchantComment.setCommentPicUrls(commentPicUrls);//设置商户评论的图片url
            //查询商户评论回复列表
            List<MerchantCommentReply> commentReplyList = merchantCommentReplyService.getReplyListByCommentId(merchantComment.getCommentId());
            merchantComment.setCommentReplys(commentReplyList);//设置商户评论对应的回复列表
        }
        page.setRecords(commentList);//返回查询的商户评论
    }
}

package com.awifi.np.biz.mws.comment.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.user.model.PubUserAuth;
import com.awifi.np.biz.api.client.dbcenter.user.util.UserAuthClient;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.mws.comment.dao.MerchantCommentReplyDao;
import com.awifi.np.biz.mws.comment.model.MerchantCommentReply;
import com.awifi.np.biz.mws.comment.service.MerchantCommentReService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017年6月15日 下午8:06:44
 * 创建作者：许尚敏
 * 文件名称：MerchantCommentReplyServiceImpl.java
 * 版本：  v1.0
 * 功能：添加商户评论-回复
 * 修改记录：
 */
@Service("merchantCommentReService")
public class MerchantCommentReServiceImpl implements MerchantCommentReService{
	/**
     * 商户评论-回复接口dao
     */
    @Resource(name="merchantCommentReplyDao")
    private MerchantCommentReplyDao merchantCommentReplyDao;
    
    /**
     * 添加商户评论-回复
     * @param replyParam 参数
     * @author 许尚敏  
     * @throws Exception 
     * @date 2017年6月16日 上午9:10:21
     */
    public void addReply(Map<String, Object> replyParam) throws Exception{
    	Long merchantId = CastUtil.toLong(replyParam.get("merchantId"));//商户id
    	Long commentId = CastUtil.toLong(replyParam.get("commentId"));//评论表主键id评论表主键id
    	Long commentUserId = CastUtil.toLong(replyParam.get("commentUserId"));//评论用户id
    	String commentUserName = CastUtil.toString(replyParam.get("commentUserName"));//评论用户名
    	Long replyUserId = CastUtil.toLong(replyParam.get("replyUserId"));//回复人用户id
    	String ipAddress = CastUtil.toString(replyParam.get("ipAddress"));//IP地址
    	String userPhone = CastUtil.toString(replyParam.get("userPhone"));//手机号码
    	String content = CastUtil.toString(replyParam.get("content")).replace("[^\\u0000-\\uFFFF]", "");//评论内容
    	String createDate = CastUtil.toString(replyParam.get("createDate"));//回复日期
    	Long replyToUserId = CastUtil.toLong(replyParam.get("replyToUserId"));//回复给的人用户id
    	String replyToUserName = CastUtil.toString(replyParam.get("replyToUserName"));//回复给的人用户名
    	String replyUserName = "";//回复人用户名
    	if(ipAddress != null){
    	    ipAddress = ipAddress.substring(ipAddress.indexOf(".") + 1, ipAddress.length());//截取ip地址
    	    ipAddress = ipAddress.substring(ipAddress.indexOf(".") + 1, ipAddress.length());//截取ip地址
            replyUserName = "游客 *.*." + ipAddress;//设置游客评论名
        }
    	if(StringUtils.isNotBlank(userPhone)){//如果手机号不为空
            PubUserAuth pubUserAuth = UserAuthClient.getUserByLogName(userPhone);//调数据中心接口 返回用户信息
            if(pubUserAuth!=null){//用户信息不为空
                replyUserId = pubUserAuth.getUserId();//赋值数据中心用户ID
                replyUserName = userPhone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");//赋值数据中心用户名
            }
        }
        merchantCommentReplyDao.addReply(merchantId, commentId, commentUserId, commentUserName, replyUserId, replyUserName, content, createDate, replyToUserId, replyToUserName);
    }
    
    /**
     * 获取商户评论的回复列表
     * @param commentId 评论主键
     * @return 结果
     * @author 季振宇  
     * @date Jun 16, 2017 4:50:18 PM
     */
    public List<MerchantCommentReply> getReplyListByCommentId(Long commentId){
        return merchantCommentReplyDao.getReplyListByCommentId(commentId);//查询商户评论的回复列表
    }
}

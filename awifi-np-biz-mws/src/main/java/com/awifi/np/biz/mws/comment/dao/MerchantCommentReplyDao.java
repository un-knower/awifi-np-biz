
/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月15日 下午6:49:36
* 创建作者：季振宇
* 文件名称：MerchantCommentReplyDao.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mws.comment.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import com.awifi.np.biz.mws.comment.model.MerchantCommentReply;


/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017年6月15日 下午8:07:41
 * 创建作者：许尚敏
 * 文件名称：MerchantCommentReplyDao.java
 * 版本：  v1.0
 * 功能：添加商户评论-回复
 * 修改记录：
 */
public interface MerchantCommentReplyDao {
	
	/**
	 * 查询补充评论回复数据
	 * @param commentId 评论id
	 * @return 补充评论回复数据
	 * @author 季振宇  
	 * @date Jun 16, 2017 4:36:25 PM
	 */
    @Results(value = {
	        @Result(property = "commentReplyId", column = "id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "replyUserId", column = "reply_user_id", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "replyUserName", column = "reply_user_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "replyToUserId", column = "reply_to_user_id", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "replyToUserName", column = "reply_to_user_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "content", column = "content", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "createDate", column = "create_date", javaType = String.class, jdbcType = JdbcType.VARCHAR)})
	@Select("select id,reply_user_id,reply_user_name,reply_to_user_id,reply_to_user_name,content,date_format(create_date,'%Y-%m-%d %H:%i:%S') as create_date from station_merchant_comment_reply where comment_id=#{commentId} ")
    List<MerchantCommentReply> getReplyListByCommentId(@Param("commentId")Long commentId);
	
    
    /**
     * 添加商户评论-回复
     * @param merchantId 商户id
     * @param commentId 评论表主键id
     * @param commentUserId 评论用户id
     * @param commentUserName 评论用户名
     * @param replyUserId 回复人用户id
     * @param replyUserName 回复人用户名
     * @param content 评论内容
     * @param createDate 回复日期
     * @param replyToUserId 回复给的人用户id
     * @param replyToUserName 回复给的人用户名
     * @author 许尚敏  
     * @date 2017年6月15日 下午20:10:21
     */
    @Insert("insert into station_merchant_comment_reply(merchant_id,comment_id,comment_user_id,comment_user_name,reply_user_id,reply_user_name,content,create_date,reply_to_user_id,reply_to_user_name) "
            + "values(#{merchantId},#{commentId},#{commentUserId},#{commentUserName},#{replyUserId},#{replyUserName},#{content},#{createDate},#{replyToUserId},#{replyToUserName})")
    void addReply(@Param("merchantId")Long merchantId,@Param("commentId")Long commentId,@Param("commentUserId") Long commentUserId,
            @Param("commentUserName") String commentUserName,@Param("replyUserId") Long replyUserId,@Param("replyUserName") String replyUserName,
            @Param("content") String content,@Param("createDate") String createDate,@Param("replyToUserId") Long replyToUserId,@Param("replyToUserName") String replyToUserName);
}


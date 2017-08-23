/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月15日 下午6:48:24
* 创建作者：季振宇
* 文件名称：MerchantCommentDao.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mws.comment.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.mws.comment.model.MerchantComment;

@Service(value = "merchantCommentDao")
public interface MerchantCommentDao {
	/**
	 * 分页查询获取记录总数
	 * @param merchantId 商户id
	 * @return 分页查询获取记录总数
	 * @author 季振宇  
	 * @date Jun 16, 2017 4:31:18 PM
	 */
    @Select("select count(1) from station_merchant_comment where merchant_id=#{merchantId} and status=1")
    Integer getCountByParam(@Param("merchantId")Long merchantId);
	
	/**
	 * 分页查询获取数据
	 * @param merchantId 商户id
	 * @param begin 查询开始位置
	 * @param pageSize 一页大小
	 * @return 分页数据
	 * @author 季振宇  
	 * @date Jun 16, 2017 4:33:44 PM
	 */
    @Results(value = {
	        @Result(property = "commentId", column = "id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "commentUserId", column = "comment_user_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "commentUserName", column = "comment_user_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "content", column = "content", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "createDate", column = "create_date", javaType = String.class, jdbcType = JdbcType.VARCHAR)})
	@Select("select id,comment_user_id,comment_user_name,content,date_format(create_date,'%Y-%m-%d %H:%i:%S') as create_date from station_merchant_comment where merchant_id=#{merchantId} and status=1 order by is_top desc,id desc limit #{begin},#{pageSize} ")
    List<MerchantComment> getListByParam(@Param("merchantId")Long merchantId, @Param("begin")Integer begin, @Param("pageSize")Integer pageSize);

    /**
     * 商户评论——内容插入sql语句
     * @param comment 
     * @return 结果
     * @author 方志伟  
     * @date 2017年6月16日 下午3:16:16
     */
    @Options(useGeneratedKeys = true, keyProperty = "commentId", keyColumn = "id")
    @Insert("insert into station_merchant_comment(merchant_id,comment_user_id,comment_user_name,comment_grade,"
            + "content,status,is_top,create_date,status_date)values (#{merchantId},#{commentUserId},#{commentUserName},#{commentGrade},"
            + "#{content},#{status},#{isTop},now(),now())")
    int add(MerchantComment comment);
}

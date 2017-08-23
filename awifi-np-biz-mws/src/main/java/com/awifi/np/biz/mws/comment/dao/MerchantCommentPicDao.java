/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月16日 下午12:03:01
* 创建作者：方志伟
* 文件名称：MerchantCommentPicDao.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mws.comment.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

@Service("merchantCommentPicDao")
public interface MerchantCommentPicDao {
    
    /**
     * 商户评论——图片插入sql语句
     * @param picUrl 图片地址
     * @param commentId 评论表中主键id
     * @param status 状态
     * @author 方志伟 
     * @date 2017年6月16日 下午12:16:12
     */
    @Insert("insert into station_merchant_comment_picture (comment_id,pic_url,status,create_date) "
            + "values (#{commentId},#{picUrl},#{status},now())")
    void add(@Param("picUrl")String picUrl,@Param("commentId")Long commentId,@Param("status")Integer status);

    /**
	 * 查询补充评论图片url
	 * @param commentId 评论id
	 * @return 补充评论图片url
	 * @author 季振宇  
	 * @date Jun 16, 2017 4:35:26 PM
	 */
    @Select("select pic_url from station_merchant_comment_picture where comment_id=#{commentId}")
    List<String> getPicListByCommentId(@Param("commentId")Long commentId);
}

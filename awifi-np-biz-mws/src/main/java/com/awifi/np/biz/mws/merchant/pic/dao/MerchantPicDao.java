/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月8日 上午9:37:55
* 创建作者：余红伟
* 文件名称：MerchantPicDao.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mws.merchant.pic.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import com.awifi.np.biz.mws.merchant.pic.dao.sql.MerchantPicSql;
import com.awifi.np.biz.mws.merchant.pic.model.MerchantPic;



public interface MerchantPicDao {
    /**
     * 获取商户滚动图片接口
     * @param merchantId
     * @return
     * @author 余红伟 
     * @date 2017年6月8日 上午9:46:31
     */
    @Results(value = { @Result(property = "id", column = "id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "merchantId", column = "merchant_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "picTitle", column = "pic_title", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "picType", column = "pic_type", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "picSubType", column = "pic_sub_type", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "picPath", column = "pic_path", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "picUrl", column = "pic_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "picLink", column = "pic_link", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "orderNum", column = "order_num", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "status", column = "status", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "statusDate", column = "status_date", javaType = Date.class, jdbcType = JdbcType.DATE),
            @Result(property = "remarks", column = "remarks", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "createDate", column = "create_date", javaType = Date.class, jdbcType = JdbcType.DATE),
            @Result(property = "modifyDate", column = "modify_date", javaType = Date.class, jdbcType = JdbcType.DATE),
    })
    @SelectProvider(type = MerchantPicSql.class, method = "getMerCarouselByMerId")
    public List<MerchantPic> getMerCarouselByMerId(Long merchantId);
    
    /**
     * 获取商户照片墙图片接口
     * @param merchantId
     * @return
     * @author 余红伟 
     * @date 2017年6月8日 上午9:47:43
     */
    @Results(value = { @Result(property = "id", column = "id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "merchantId", column = "merchant_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "picTitle", column = "pic_title", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "picType", column = "pic_type", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "picSubType", column = "pic_sub_type", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "picPath", column = "pic_path", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "picUrl", column = "pic_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "picLink", column = "pic_link", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "orderNum", column = "order_num", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "status", column = "status", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "statusDate", column = "status_date", javaType = Date.class, jdbcType = JdbcType.DATE),
            @Result(property = "remarks", column = "remarks", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "createDate", column = "create_date", javaType = Date.class, jdbcType = JdbcType.DATE),
            @Result(property = "modifyDate", column = "modify_date", javaType = Date.class, jdbcType = JdbcType.DATE),
    })
    @SelectProvider(type = MerchantPicSql.class, method = "getMerWallAlbumByMerId")
    public List<MerchantPic> getMerWallAlbumByMerId(Long merchantId);
    

    
    /**
     * 更新图片
     * @param merchantPic
     * @return
     * @author 余红伟 
     * @date 2017年6月9日 下午3:12:44
     */
    @UpdateProvider(type = MerchantPicSql.class, method = "updateMerchantPic")
    public int updateMerchantPic(MerchantPic merchantPic);
    
    /**
     * 添加图片
     * @param merchantPic
     * @return
     * @author 余红伟 
     * @date 2017年6月14日 下午3:38:53
     */
    @InsertProvider(type = MerchantPicSql.class, method = "addMerchantPic")
    public int addMerchantPic(MerchantPic merchantPic);
    
    /**
     * 根据商户Id和图片类型批量删除图片
     * @param merid
     * @param picType
     * @return
     * @author 余红伟 
     * @date 2017年6月14日 下午3:51:22
     */
    @UpdateProvider(type = MerchantPicSql.class, method = "deleteByMeridAndPicType")
    public int deleteByMeridAndPicType(Long merid,Integer picType);
    
    /**
     * 逻辑删除数据库图片，根据图片id
     * @param id
     * @return
     * @author 余红伟 
     * @date 2017年6月15日 下午4:06:01
     */
    @UpdateProvider(type = MerchantPicSql.class, method = "logicDeleteMerPicById")
    public int logicDeleteMerPicById(Long id);
}

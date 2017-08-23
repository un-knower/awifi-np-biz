/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月8日 上午9:44:06
* 创建作者：余红伟
* 文件名称：MerchantPicSql.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mws.merchant.pic.dao.sql;

import java.util.Date;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.awifi.np.biz.mws.merchant.pic.model.MerchantPic;

public class MerchantPicSql {
    private Logger logger = Logger.getLogger(this.getClass());
    /**
     * 获取商户滚动图片接口
     * @param merchantId
     * @return
     * @author 余红伟 
     * @date 2017年6月8日 上午9:53:13
     */
    public String getMerCarouselByMerId(Long merchantId){
        //status 1:正常；2:已删除 。 picType 1:图片轮播； 2：图片墙
        StringBuffer sql = new StringBuffer("select id,order_num,pic_title,pic_type,pic_path,pic_url,pic_link from station_merchant_picture where status = 1 and pic_type = 1 ");
        if(merchantId != null){
            //商户最多可以配置6张图片
            sql.append("and merchant_id = #{merchantId} limit 6");
        }
        logger.debug(sql.toString());
        return sql.toString();
    }
    /**
     * 获取商户照片墙图片
     * 
     * @param merchantId
     * @return
     * @author 余红伟 
     * @date 2017年6月8日 上午11:09:15
     */
    public String getMerWallAlbumByMerId(Long merchantId){
      //status 1:正常；2:已删除 。 picType 1:图片轮播； 2：图片墙
        StringBuffer sql = new StringBuffer("select id,order_num,pic_title,pic_type,pic_path,pic_url,pic_link from station_merchant_picture where status = 1 and pic_type = 2 ");
        if(merchantId != null){
          //商户最多可以配置图片墙18张图片
            sql.append(" and merchant_id = #{merchantId} limit 18");
        }
        logger.debug(sql.toString());
        return sql.toString();
    }

    /**
     * 逻辑删除数据库图片，根据图片id
     * @param id
     * @return
     * @author 余红伟 
     * @date 2017年6月15日 下午4:01:55
     */
    public String logicDeleteMerPicById(Long id){
        StringBuffer sql = new StringBuffer("update station_merchant_picture set ");
        if(id != null){
            //status 1:正常 ; 2:删除
            sql.append(" status = 9 and modify_date = now() where id = #{id}");
        }
        logger.debug(sql.toString());
        return sql.toString();
    }
    /**
     * 更新图片
     * @param merchantPic
     * @return
     * @author 余红伟 
     * @date 2017年6月9日 下午2:58:15
     */
    public String updateMerchantPic(MerchantPic merchantPic){
        logger.debug("params: " + JSON.toJSONString(merchantPic));
        StringBuffer sql = new StringBuffer("update station_merchant_picture set");
        Long id = merchantPic.getId();
        Long merchantId = merchantPic.getMerchantId();
        String picTitle = merchantPic.getPicTitle();
        Integer picType = merchantPic.getPicType();
        Integer picSubType = merchantPic.getPicSubType();
        String picPath = merchantPic.getPicPath();
        String picUrl = merchantPic.getPicUrl();
        String picLink = merchantPic.getPicLink();
        Integer orderNum = merchantPic.getOrderNum();
        Integer status = merchantPic.getStatus();
        Date statusDate = merchantPic.getStatusDate();
        String remarks = merchantPic.getRemarks();
        Date createDate = merchantPic.getCreateDate();
        Date modifyDate = merchantPic.getModifyDate();
        
        if(merchantId != null){
            sql.append(" merchant_id = #{merchantId},");
        }
        if(picTitle != null){
            sql.append("pic_title = #{picTitle},");
        }
        if(picType != null){
            sql.append("pic_type = #{picType},");
        }
        if(picSubType != null){
            sql.append("pic_sub_type = #{picSubType},");
        }
        if(picPath != null){
            sql.append("pic_path = #{picPath},");
        }
        if(picLink != null){
            sql.append("pic_link = #{picLink},");
        }
        if(orderNum != null){
            sql.append("order_num = #{orderNum},");
        }
        if(status != null){
            sql.append("status = #{status},");
        }
        if(statusDate != null){
            sql.append("status_date = #{statusDate},");
        }
        if(remarks != null){
            sql.append("remarks = #{remarks},");
        }
        if(createDate != null){
            sql.append("create_date = #{createDate},");
        }
        
        sql.append("modify_date = now() ");
        if(id != null){
            sql.append(" where id = #{id}");
        }
        logger.debug(sql.toString());
        return sql.toString();
    }
    
    /**
     * 添加图片
     * @param merchantPic
     * @return
     * @author 余红伟 
     * @date 2017年6月14日 下午3:28:52
     */
    public String addMerchantPic(MerchantPic merchantPic){
        StringBuffer sql = new StringBuffer("insert into station_merchant_picture(");
        if(merchantPic.getMerchantId() !=null){
            sql.append("merchant_id,");
        }
        if(merchantPic.getPicTitle() !=null){
            sql.append("pic_title,");
        }
        if(merchantPic.getPicType()!=null){
            sql.append("pic_type,");
        }
        if(merchantPic.getPicSubType() != null){
            sql.append("pic_sub_type,");
        }
        if(merchantPic.getPicPath() !=null){
            sql.append("pic_path,");
        }
        if(merchantPic.getPicLink() != null){
            sql.append("pick_link,");
        }
        if(merchantPic.getPicUrl() !=null){
            sql.append("pic_url,");
        }
        if(merchantPic.getOrderNum() != null){
           sql.append("order_num"); 
        }
        if(merchantPic.getStatus() != null){
            sql.append("status,");
        }
        if(merchantPic.getStatusDate() != null){
            sql.append("status_date");
        }
        if(merchantPic.getRemarks() != null){
            sql.append("remarks,");
        }
//        if(merchantPic.getCreateDate() != null){
//            sql.append("create_date,");
//        }
//        if(merchantPic.getModifyDate() != null){
//            sql.append("modify_date,");
//        }
//        sql.deleteCharAt(sql.toString().length() - 1);
        sql.append("create_date,modify_date");
        sql.append(") values(");
        if(merchantPic.getMerchantId() !=null){
            sql.append("#{merchantId},");
        }
        if(merchantPic.getPicTitle() !=null){
            sql.append("#{picTitle},");
        }
        if(merchantPic.getPicType()!=null){
            sql.append("#{picType},");
        }
        if(merchantPic.getPicSubType() != null){
            sql.append("#{picSubType},");
        }
        if(merchantPic.getPicPath() !=null){
            sql.append("#{picPath},");
        }
        if(merchantPic.getPicLink() != null){
            sql.append("#{picLink},");
        }
        if(merchantPic.getPicUrl() !=null){
            sql.append("#{picUrl},");
        }
        if(merchantPic.getOrderNum() != null){
           sql.append("#{orderNum}"); 
        }
        if(merchantPic.getStatus() != null){
            sql.append("#{status},");
        }
        if(merchantPic.getStatusDate() != null){
            sql.append("#{statusDate}");
        }
        if(merchantPic.getRemarks() != null){
            sql.append("#{remarks},");
        }
//        if(merchantPic.getCreateDate() != null){
//            sql.append("createDate,");
//        }
//        if(merchantPic.getModifyDate() != null){
//            sql.append("modifyDate,");
//        }
//        sql.deleteCharAt(sql.toString().length() - 1);
        sql.append("now(),now()");
        sql.append(")");
        
        logger.debug(sql.toString());
        return sql.toString();
    }
    /**
     * 根据商户Id和图片类型批量删除图片
     * @param merid
     * @param picType
     * @return
     * @author 余红伟 
     * @date 2017年6月14日 下午3:44:21
     */
    public String deleteByMeridAndPicType(Long merid,Integer picType){
        StringBuffer sql = new StringBuffer("update station_merchant_picture set ");
        if(merid != null && picType != null){
            //status 1:正常 ; 2:删除
            sql.append(" status = 9 and modify_date = now() where merchant_id = #{merid} and pic_type = #{pic_type}");
        }
        logger.debug(sql.toString());
        return sql.toString();
    }
}

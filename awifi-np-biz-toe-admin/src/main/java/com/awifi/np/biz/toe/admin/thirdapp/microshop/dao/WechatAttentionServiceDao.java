/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月17日 下午7:34:33
* 创建作者：许小满
* 文件名称：WechatAttentionServiceMapper.java
* 版本：  v1.0
* 功能：微信关注--模型层接口
* 修改记录：
*/
package com.awifi.np.biz.toe.admin.thirdapp.microshop.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

@Service("wechatAttentionServiceDao")
public interface WechatAttentionServiceDao {
    
    /**
     * 通过shopId和UserMac获取记录总数
     * @param shopId 微旺铺shopid
     * @param userMac 用户mac
     * @return 记录总数
     * @author 许小满  
     * @date 2017年6月18日 下午1:49:08
     */
    @Select("select count(pk_id) from toe_app_wechat_attention where shop_id=#{shopId} and user_mac=#{userMac}")
    Long count(@Param("shopId")String shopId, @Param("userMac")String userMac);
    
    /**
     * 新增微信关注记录
     * @param shopId 微旺铺shopid
     * @param userPhone 用户手机号
     * @param userMac 用户mac
     * @author 许小满  
     * @date 2017年6月18日 下午1:55:13
     */
    @Insert("insert into toe_app_wechat_attention(shop_id,user_phone,user_mac,create_date) values(#{shopId},#{userPhone},#{userMac},unix_timestamp(now()))")
    void add(@Param("shopId")String shopId, @Param("userPhone")String userPhone, @Param("userMac")String userMac);

    /**
     * 通过shopId和UserMac获取关注信息
     * @param shopId 微旺铺shopid
     * @param userMac 用户mac
     * @return 关注标识：-1 未关注、1 已关注
     * @author 许小满  
     * @date 2017年6月18日 下午7:14:41
     */
    @Select("select attention_flag from toe_app_wechat_attention where shop_id=#{shopId} and user_mac=#{userMac}")
    Integer getAttentionFlag(@Param("shopId")String shopId, @Param("userMac")String userMac);
    
}

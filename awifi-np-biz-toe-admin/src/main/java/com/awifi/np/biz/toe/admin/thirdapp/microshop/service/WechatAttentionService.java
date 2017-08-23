/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月17日 下午7:32:21
* 创建作者：许小满
* 文件名称：WechatAttentionService.java
* 版本：  v1.0
* 功能：微信关注--业务层接口
* 修改记录：
*/
package com.awifi.np.biz.toe.admin.thirdapp.microshop.service;

public interface WechatAttentionService {
    
    /**
     * 新增微信关注记录
     * 如果已存在，自动跳过
     * @param shopId 微旺铺shopid
     * @param userPhone 用户手机号
     * @param userMac 用户mac
     * @author 许小满  
     * @date 2017年6月18日 下午1:45:11
     */
    void add(String shopId, String userPhone, String userMac);
    
    /**
     * 新增微信关注记录
     * 如果已存在，自动跳过
     * @param shopId 微旺铺shopid
     * @param userPhone 用户手机号
     * @param userMac 用户mac
     * @author 许小满  
     * @date 2017年6月18日 下午1:45:11
     */
    void addWithCheck(String shopId, String userPhone, String userMac);
    
    /**
     * 通过shopId和UserMac获取关注信息
     * @param shopId 微旺铺shopid
     * @param userMac 用户mac
     * @return 关注标识：-1 未关注、1 已关注
     * @author 许小满  
     * @date 2017年6月18日 下午7:14:41
     */
    Integer getAttentionFlag(String shopId, String userMac);
    
}

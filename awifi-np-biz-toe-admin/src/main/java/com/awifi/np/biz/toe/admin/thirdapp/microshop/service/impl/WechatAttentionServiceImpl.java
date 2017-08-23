/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月17日 下午7:33:05
* 创建作者：许小满
* 文件名称：WechatAttentionServiceImpl.java
* 版本：  v1.0
* 功能：微信关注--业务层接口实现类
* 修改记录：
*/
package com.awifi.np.biz.toe.admin.thirdapp.microshop.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.common.base.service.BaseService;
import com.awifi.np.biz.toe.admin.thirdapp.microshop.dao.WechatAttentionServiceDao;
import com.awifi.np.biz.toe.admin.thirdapp.microshop.service.WechatAttentionService;

@Service("wechatAttentionService")
public class WechatAttentionServiceImpl extends BaseService implements WechatAttentionService {

    /**  */
    @Resource(name="wechatAttentionServiceDao")
    private WechatAttentionServiceDao wechatAttentionServiceDao;
    
    /**
     * 新增微信关注记录
     * @param shopId 微旺铺shopid
     * @param userPhone 用户手机号
     * @param userMac 用户mac
     * @author 许小满  
     * @date 2017年6月18日 下午1:45:11
     */
    public void add(String shopId, String userPhone, String userMac){
        //如果不存在，新增记录
        wechatAttentionServiceDao.add(shopId, userPhone, userMac);
    }
    
    /**
     * 新增微信关注记录
     * 如果已存在，自动跳过
     * @param shopId 微旺铺shopid
     * @param userPhone 用户手机号
     * @param userMac 用户mac
     * @author 许小满  
     * @date 2017年6月18日 下午1:45:11
     */
    public void addWithCheck(String shopId, String userPhone, String userMac){
      //判断是否已存在（shopId + userMac）
        Long count = wechatAttentionServiceDao.count(shopId, userMac);
        if(count > 0){
            return;
        }
        //如果不存在，新增记录
        wechatAttentionServiceDao.add(shopId, userPhone, userMac);
    }
    
    /**
     * 通过shopId和UserMac获取关注信息
     * @param shopId 微旺铺shopid
     * @param userMac 用户mac
     * @return 关注标识：-1 未关注、1 已关注
     * @author 许小满  
     * @date 2017年6月18日 下午7:14:41
     */
    public Integer getAttentionFlag(String shopId, String userMac){
        return wechatAttentionServiceDao.getAttentionFlag(shopId, userMac);
    }
}

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月16日 下午2:30:44
* 创建作者：许小满
* 文件名称：MicroshopLogServiceImpl.java
* 版本：  v1.0
* 功能：微旺铺认证日志--业务层接口实现类
* 修改记录：
*/
package com.awifi.np.biz.toe.admin.thirdapp.microshoplog.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.common.base.service.BaseService;
import com.awifi.np.biz.toe.admin.thirdapp.microshoplog.dao.MicroshopLogDao;

@Service("microshopLogService")
public class MicroshopLogServiceImpl extends BaseService implements MicroshopLogService {

    /** 微信日志dao */
    @Resource(name="microshopLogDao")
    private MicroshopLogDao microshopLogDao;
    
    /**
     * 保存微旺铺日志
     * @param redisKey redisKey
     * @param redisParams redis参数
     * @param shopId shopId
     * @param shopName shopName
     * @param forceAttention 是否强制关注
     * @param authType 认证类型
     * @param userPhone 手机号
     * @param userMac 用户mac
     * @param callWechatParameter 唤起微信参数
     * @param value 唤起微信返回数据
     * @param result 流程编码
     * @param customerId 客户id
     * @param cascadeLabel 客户层级
     * @author kangyanxiang 
     * @date 2016年7月12日 下午2:17:25
     */
    public void saveMicroshopLog(String redisKey, String redisParams, String shopId, String shopName,
            Integer forceAttention, String authType, String userPhone, String userMac,
            String callWechatParameter, String value, String result, String customerId, String cascadeLabel) {
        microshopLogDao.saveMicroshopLog(redisKey,redisParams,shopId,shopName,forceAttention,authType,userPhone,userMac,
                callWechatParameter,value,result,customerId,cascadeLabel);
    }
    
}

package com.awifi.np.biz.toe.admin.merchant.customerconfig.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.common.base.constants.RedisConstants;
import com.awifi.np.biz.common.base.service.BaseService;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.toe.admin.merchant.customerconfig.dao.CustomerConfigMapper;
import com.awifi.np.biz.toe.admin.merchant.customerconfig.service.CustomerConfigService;

/**   
 * @Description:  客户配置 -业务层
 * @Title: CustomerConfigService.java 
 * @Package com.awifi.toe.admin.customer.service 
 * @author 许小满 
 * @date 2016年10月28日 下午2:50:07
 * @version V1.0   
 */
@Service("customerConfigService")
public class CustomerConfigServiceImpl extends BaseService implements CustomerConfigService{
    
    /** 客户配置-模型层 */
    @Resource(name = "customerConfigDao")
    private CustomerConfigMapper customerConfigMapper;
    
    /**
     * 通过客户id获取第三方静态用户名认证地址
     * 优先从redis缓存中读取
     * @param customerId 客户id
     * @return 第三方静态用户名认证地址
     * @author 许小满  
     * @date 2016年10月28日 下午2:53:09
     */
    public String getStaticUserAuthUrlCache(Long customerId){
        String staticUserAuthUrl = null;//第三方静态用户名认证地址
        //1.从redis获取地址
        String redisKey = RedisConstants.CUSTOMER_CONFIG + customerId;
        staticUserAuthUrl = RedisUtil.get(redisKey);
        //2.如果未找到，再从数据库中读取，然后再存入redis缓存中
        if(StringUtils.isBlank(staticUserAuthUrl)){
            staticUserAuthUrl = getStaticUserAuthUrl(customerId);
            if(StringUtils.isNotBlank(staticUserAuthUrl)){//存入redis缓存中
                RedisUtil.set(redisKey, staticUserAuthUrl, RedisConstants.CUSTOMER_CONFIG_TIME);
            }
        }
        return staticUserAuthUrl;
    }
    
    /**
     * 通过客户id获取第三方静态用户名认证地址
     * @param customerId 客户id
     * @return 第三方静态用户名认证地址
     * @author 许小满  
     * @date 2016年10月28日 下午2:53:09
     */
    private String getStaticUserAuthUrl(Long customerId){
        return customerConfigMapper.getStaticUserAuthUrl(customerId);
    }
    
}
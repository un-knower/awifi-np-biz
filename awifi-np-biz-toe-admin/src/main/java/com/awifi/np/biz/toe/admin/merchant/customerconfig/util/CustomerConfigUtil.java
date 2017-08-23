package com.awifi.np.biz.toe.admin.merchant.customerconfig.util;

import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.toe.admin.merchant.customerconfig.service.CustomerConfigService;

/**   
 * @Description:  客户配置 -工具类
 * @Title: CustomerConfigUtil.java 
 * @Package com.awifi.toe.admin.customer.util 
 * @author 许小满 
 * @date 2016年10月28日 下午5:45:22
 * @version V1.0   
 */
public class CustomerConfigUtil {
    
    /** 系统参数业务层 */
    private static CustomerConfigService customerConfigService;

    /**
     * 通过客户id获取第三方静态用户名认证地址
     * 优先从redis缓存中读取
     * @param customerId 客户id
     * @return 第三方静态用户名认证地址
     * @author 许小满  
     * @date 2016年10月28日 下午2:53:09
     */
    public static String getStaticUserAuthUrlCache(Long customerId){
        return getCustomerConfigService().getStaticUserAuthUrlCache(customerId);
    }
    
    
    
    /**
     * 获取 CustomerConfigService bean
     * @return sysConfigService bean
     * @author 许小满  
     * @date 2016年10月28日 下午5:47:07
     */
    public static CustomerConfigService getCustomerConfigService() {
        if (customerConfigService == null) {
            customerConfigService = (CustomerConfigService) BeanUtil.getBean("customerConfigService");
        }
        return customerConfigService;
    }
    
}

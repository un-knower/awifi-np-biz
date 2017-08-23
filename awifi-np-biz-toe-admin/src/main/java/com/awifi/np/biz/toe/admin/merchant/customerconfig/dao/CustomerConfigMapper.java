package com.awifi.np.biz.toe.admin.merchant.customerconfig.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**   
 * @Description:  客户配置 -模型层
 * @Title: CustomerConfigMapper.java 
 * @Package com.awifi.toe.admin.customer.mapper 
 * @author 许小满 
 * @date 2016年10月28日 下午2:50:45
 * @version V1.0   
 */
@Service("customerConfigDao")
public interface CustomerConfigMapper {

    /**
     * 通过客户id获取第三方静态用户名认证地址
     * @param customerId 客户id
     * @return 第三方静态用户名认证地址
     * @author 许小满  
     * @date 2016年10月28日 下午2:53:09
     */
    @Select("select static_user_auth_url from toe_customer_config where fk_customer_id=#{customerId} and delete_flag=1 limit 1")
    String getStaticUserAuthUrl(@Param("customerId")Long customerId);
    
}

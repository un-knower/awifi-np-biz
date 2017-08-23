/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月16日 下午3:32:24
* 创建作者：许小满
* 文件名称：CustomerConfigService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.toe.admin.merchant.customerconfig.service;

public interface CustomerConfigService {

    /**
     * 通过客户id获取第三方静态用户名认证地址
     * 优先从redis缓存中读取
     * @param customerId 客户id
     * @return 第三方静态用户名认证地址
     * @author 许小满  
     * @date 2016年10月28日 下午2:53:09
     */
    String getStaticUserAuthUrlCache(Long customerId);
    
}

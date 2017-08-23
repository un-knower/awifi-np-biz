/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月25日 下午1:31:49
* 创建作者：尤小平
* 文件名称：MerchantManagerService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.merchant.service;

import com.awifi.np.biz.timebuysrv.merchant.model.MerchantExtends;
/**
 * 商户扩展字段表 以商户id为主键 且唯一
 * @author 张智威
 * 2017年7月18日 上午9:47:27
 */
public interface MerchantExtendsService {

    /**
     * 根据主键判断是否为买断商户
     * 
     * @param id 主键
     * @return MerchantManager
     * @throws Exception 异常
     * @author 张智威
     * @date 2017年4月27日 下午8:12:29
     */
    MerchantExtends selectByPrimaryKey(Long id) throws Exception;

  
}

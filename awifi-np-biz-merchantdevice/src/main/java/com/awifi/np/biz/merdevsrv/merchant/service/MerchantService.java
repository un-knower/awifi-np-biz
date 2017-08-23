/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月9日 下午2:02:52
* 创建作者：王冬冬
* 文件名称：MerchantService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.merdevsrv.merchant.service;

import java.util.List;
import java.util.Map;

public interface MerchantService {
    /**
     * 防蹭网开关
     * @param bodyParam 传入参数
     * @author 王冬冬  
     * @throws Exception 
     * @date 2017年5月8日 下午2:17:59
     */
    void batchAntiRobber(List<Map<String, Object>> bodyParam) throws Exception;

}

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月9日 下午2:03:33
* 创建作者：王冬冬
* 文件名称：MerchantServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.merdevsrv.merchant.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.merdevsrv.merchant.service.MerchantService;
import com.awifi.np.biz.tob.member.service.MsMerchantDeviceService;
@Service("merchantService")
public class MerchantServiceImpl implements MerchantService {

    /**微站商户设备*/
    @Resource(name = "msMerchantDeviceService")
    private MsMerchantDeviceService msMerchantDeviceService;
    /**
     * 防蹭网开关
     * @param bodyParam 传入参数
     * @author 王冬冬  
     * @throws Exception 
     * @date 2017年5月8日 下午2:17:59
     */
    public void batchAntiRobber(List<Map<String, Object>> bodyParam) throws Exception {
        for(Map<String,Object> map:bodyParam){
            Long merchantId=CastUtil.toLong(map.get("merchantId")); 
            Byte status=CastUtil.toByte(map.get("status"));
            msMerchantDeviceService.updateAntiRobberSwitch(merchantId,status);
        }
    }

}

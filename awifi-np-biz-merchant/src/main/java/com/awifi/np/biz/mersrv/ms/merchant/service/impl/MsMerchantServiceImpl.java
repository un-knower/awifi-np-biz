/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月8日 上午9:46:12
* 创建作者：尤小平
* 文件名称：MsMerchantServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mersrv.ms.merchant.service.impl;

import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.common.ms.util.StringUtil;
import com.awifi.np.biz.common.ms.util.TimeTag;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.mersrv.ms.merchant.service.MsMerchantService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service(value = "msMerchantService")
public class MsMerchantServiceImpl implements MsMerchantService {
    /**
     * logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * 根据merchantId查找商户，排除已撤销的商户.
     * 
     * @param merchantId 商户id
     * @return 商户
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年6月12日 上午11:26:46
     */
    public Merchant findMerchantInfoExclude9(long merchantId) throws Exception {
        try {
            logger.debug("merchantId = " + merchantId);
            Merchant merchant = MerchantClient.getById(merchantId);
            logger.debug("MerchantClient.getById() return :" + JsonUtil.toJson(merchant));

            if(StringUtil.isBlank(merchant.getAddress())){
                if(!StringUtil.isBlank(merchant.getProvince())){
                    merchant.setAddress(merchant.getProvince());
                }
                if(!StringUtil.isBlank(merchant.getCity())){
                    merchant.setAddress(merchant.getAddress()+merchant.getCity());
                }
                if(!StringUtil.isBlank(merchant.getArea())){
                    merchant.setAddress(merchant.getAddress()+merchant.getArea());
                }
            }

            if (merchant != null && merchant.getStatus() != null && merchant.getStatus().intValue() == 9) {
                return null;
            } else {
                if(StringUtil.isBlank(merchant.getOpenTime())){
                    merchant.setOpenTime("9:00");
                }else {
                    merchant.setOpenTime(TimeTag.getTimeFromInt(Integer.parseInt(merchant.getOpenTime())));
                }

                if(StringUtil.isBlank(merchant.getCloseTime())){
                    merchant.setCloseTime("18:00");
                }else {
                    merchant.setCloseTime(TimeTag.getTimeFromInt(Integer.parseInt(merchant.getCloseTime())));
                }
            }
            
            if(StringUtil.isBlank(merchant.getContactWay())){
                merchant.setContactWay("暂无手机号码");
            }
            return merchant ;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

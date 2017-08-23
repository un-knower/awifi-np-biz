/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月22日 上午1:06:35
* 创建作者：许小满
* 文件名称：MerchantServiceImpl.java
* 版本：  v1.0
* 功能：商户 业务层接口实现类
* 修改记录：
*/
package com.awifi.np.biz.devicebindsrv.merchant.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.industry.util.IndustryClient;
import com.awifi.np.biz.api.client.dbcenter.location.util.LocationClient;
import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.common.base.service.BaseService;
import com.awifi.np.biz.common.util.FormatUtil;
import com.awifi.np.biz.devicebindsrv.merchant.service.MerchantService;

@Service("merchantService")
public class MerchantServiceImpl extends BaseService implements MerchantService {

    /**
     * 获取商户详情
     * @param id 商户id
     * @return 商户详情
     * @author 周颖  
     * @throws Exception 
     * @date 2017年2月6日 上午8:50:18
     */
    public Merchant getById(Long id) throws Exception{
        Merchant merchant = MerchantClient.getById(id);//调数据中心商户详情接口
        //补全行业信息
        String industryName = StringUtils.EMPTY;
        String industryId = merchant.getPriIndustryCode();//一级行业标签
        if (StringUtils.isNotBlank(industryId)) {
            industryName = IndustryClient.getNameByCode(industryId);// 从缓存中拿
            merchant.setPriIndustry(industryName);
        }
        industryId = merchant.getSecIndustryCode();// 二级行业标签
        if (StringUtils.isNotBlank(industryId)) {
            industryName = IndustryClient.getNameByCode(industryId);// 从缓存中拿
            merchant.setSecIndustry(industryName);
        }
        //补全地区
        String nameParam = "name";//地区名称
        Long provinceId = merchant.getProvinceId();//省id
        String province = provinceId != null ? LocationClient.getByIdAndParam(provinceId, nameParam) : null;//省
        Long cityId = merchant.getCityId();//市id
        String city = cityId != null ? LocationClient.getByIdAndParam(cityId, nameParam) : null;//市
        Long areaId = merchant.getAreaId();//区id
        String area = areaId != null ? LocationClient.getByIdAndParam(areaId, nameParam) : null;//区
        merchant.setProvince(province);//省
        merchant.setCity(city);//市
        merchant.setArea(area);//区
        merchant.setLocationFullName(FormatUtil.locationFullName(province, city, area));//地区全路径
        return merchant;
    }
    
    /**
     * 判断商户名称是否存在
     * @param merchantName 商户名称
     * @throws Exception 异常
     * @return true/false
     * @author 周颖  
     * @date 2017年2月4日 上午11:13:35
     */
    public boolean isMerchantNameExist(String merchantName) throws Exception{
        Map<String,Object> paramsMap = new HashMap<String,Object>();
        paramsMap.put("merchantName", merchantName);
        //paramsMap.put("merchantType", 2);
        int count = MerchantClient.getCountByParam(paramsMap);//查看商户名称是否存在
        return count > 0 ? true : false;
    }
    
}

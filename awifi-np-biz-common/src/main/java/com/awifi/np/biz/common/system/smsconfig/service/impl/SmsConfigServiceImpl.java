/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月3日 下午4:12:58
* 创建作者：周颖
* 文件名称：SmsConfigServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.common.system.smsconfig.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.security.permission.util.PermissionUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.system.smsconfig.dao.SmsConfigDao;
import com.awifi.np.biz.common.system.smsconfig.model.SmsConfig;
import com.awifi.np.biz.common.system.smsconfig.service.SmsConfigService;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.MessageUtil;

@Service("smsConfigService")
public class SmsConfigServiceImpl implements SmsConfigService {

    
    /**短信配置*/
    @Resource(name="smsConfigDao")
    private SmsConfigDao smsConfigDao;
    
    /**
     * 商户短信配置列表
     * @param user 登陆用户
     * @param page 页面信息
     * @param merchantId 商户id
     * @author 周颖  
     * @throws Exception 
     * @date 2017年5月4日 上午8:48:37
     */
    public void getListByParam(SessionUser user, Page<Map<String, Object>> page, Long merchantId) throws Exception{
        Long[] merchantIds = null;
        if(PermissionUtil.isSuperAdmin(user)){//超管 可以看全部
        }else if(PermissionUtil.isMerchant(user)){//商户管理员
            if(merchantId == null){//如果没选择商户，看自己的短信配置
                merchantId = user.getMerchantId();
            }
        }else if(PermissionUtil.isMerchants(user)){//多个商户管理员
            String[] merchantIdsArray = user.getMerchantIds().split(",");
            merchantIds = CastUtil.toLongArray(merchantIdsArray);
        }else if(PermissionUtil.isProject(user) || PermissionUtil.isLocation(user)){//地区或项目管理员 强制选择一个商户
            if(merchantId == null){
                throw new BizException("E2000059", MessageUtil.getMessage("E2000059"));//必须选择一个商户
            }
        }else{//没有维护属性 可视为超管
            
        }
        int count = smsConfigDao.getCountByParam(merchantId,merchantIds);
        page.setTotalRecord(count);
        if(count <= 0){
            return;
        }
        List<Map<String,Object>> smsConfigList = smsConfigDao.getListByParam(merchantId, merchantIds, page.getBegin(), page.getPageSize());
        Long merchantIdLong = null;
        String merchantName = null;
        for(Map<String,Object> smsConfig : smsConfigList){
            merchantIdLong = (Long)smsConfig.get("merchantId");
            merchantName = MerchantClient.getNameByIdCache(merchantIdLong);
            smsConfig.put("merchantName", merchantName);
        }
        page.setRecords(smsConfigList);
    }
    
    /**
     * 判断商户是否已经配置
     * @param merchantId 商户id
     * @return true 已配置
     * @author 周颖  
     * @date 2017年5月3日 下午4:25:26
     */
    public boolean isExist(Long merchantId){
        int count = smsConfigDao.getNumByMerchantId(merchantId);
        return count >0 ;
    }
    
    /**
     * 添加短信配置
     * @param merchantId 商户id
     * @param smsContent 短信内容
     * @param codeLength 验证码长度
     * @author 周颖  
     * @date 2017年5月3日 下午4:30:27
     */
    public void add(Long merchantId, String smsContent, Integer codeLength){
        smsConfigDao.add(merchantId,smsContent,codeLength);
    }

    /**
     * 编辑短信配置
     * @param id 配置id
     * @param smsContent 短信内容
     * @param codeLength 验证码长度
     * @author 周颖  
     * @date 2017年5月3日 下午4:38:21
     */
    public void update(Long id, String smsContent, Integer codeLength) {
        smsConfigDao.update(id,smsContent,codeLength);
    }

    /**
     * 短信配置详情
     * @param id 主键id
     * @return 详情
     * @author 周颖  
     * @throws Exception 
     * @date 2017年5月3日 下午4:44:53
     */
    public Map<String,Object> getById(Long id) throws Exception {
        Map<String,Object> smsConfigMap = smsConfigDao.getById(id);
        smsConfigMap.put("id", id);
        Long merchantId = (Long)smsConfigMap.get("merchantId");
        String merchantName = MerchantClient.getNameByIdCache(merchantId);
        smsConfigMap.put("merchantName", merchantName);
        return smsConfigMap;
    }
    
    /**
     * 获取该客户的短信配置信息
     * @param merchantId 商户id
     * @return 客户短信配置信息
     * @author ZhouYing 
     * @date 2016年12月6日 上午11:08:22
     */
    public SmsConfig getByCustomerId(Long merchantId) {
        if(merchantId == null){
            return null;
        }
        return smsConfigDao.getByCustomerId(merchantId);
    }
    
    /**
     * 短信配置删除
     * @param id 主键id
     * @author 周颖  
     * @date 2017年5月4日 上午10:56:42
     */
    public void delete(Long id){
        smsConfigDao.delete(id);
    }
}

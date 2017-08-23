/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月16日 下午1:31:20
* 创建作者：许小满
* 文件名称：MicroshopServiceImpl.java
* 版本：  v1.0
* 功能：微旺铺相关操作--业务层接口实现类
* 修改记录：
*/
package com.awifi.np.biz.toe.admin.thirdapp.microshop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.common.base.service.BaseService;
import com.awifi.np.biz.toe.admin.thirdapp.microshop.dao.MicroshopDao;
import com.awifi.np.biz.toe.admin.thirdapp.microshop.model.Microshop;
import com.awifi.np.biz.toe.admin.thirdapp.microshop.service.MicroshopService;

@Service("microshopService")
public class MicroshopServiceImpl extends BaseService implements MicroshopService{

    /** 微旺铺dao */
    @Resource(name = "microshopDao")
    private MicroshopDao microshopDao;
    
    /**
     * 通过商户id获取微旺铺表信息
     * @param merchantId 商户id
     * @return 微旺铺表记录
     * @author 许小满  
     * @date 2017年5月16日 下午1:35:08
     */
    public Microshop getByMerchantId(Long merchantId){
        Microshop microshop = microshopDao.getByMerchantId(merchantId);
        if(microshop == null){
            return null;
        }
        Integer spreadModel = microshop.getSpreadModel();
        if(spreadModel != null && spreadModel == 2){
            microshop = microshopDao.getByMerchantId(microshop.getRelateCustomerId());
        }
        return microshop;
    }
    /**
     * 根据当前登录的客户，查询微旺铺信息
     * @param merchantId 商户id
     * @param appId 第三方id 用于区别微旺铺和聚来宝
     * @return 微旺铺信息
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2016年3月21日 下午4:00:59
     */
    public Microshop getRelateMerIdByMerId(Long merchantId, String appId) {
        return microshopDao.getRelateMerIdByMerId(merchantId,appId);
    }

    /**
     * 获取关联配置商户列表数量
     * @param merchantId 商户id
     * @param appId 应用Id
     * @param relateCustomerId 关联商户id
     * @param projectId 项目id
     * @return 数量
     * @author 王冬冬  
     * @param queryMerchantId 查询商户id
     * @date 2017年7月12日 下午4:07:37
     */
    public int getMerchantCountByParam(Long merchantId, Long queryMerchantId,String appId, Long relateCustomerId, Long projectId) {
        return microshopDao.getMerchantCountByParam(projectId,merchantId,relateCustomerId,queryMerchantId,appId);
    }

    /**
     * 获取关联配置商户列表
     * @param merchantId 商户id
     * @param appId 应用Id
     * @param relateCustomerId 关联商户id
     * @param projectId 项目id
     * @param pageNo 页数
     * @param pageSize 分页大小
     * @return 列表
     * @author 王冬冬  
     * @param queryMerchantId 查询商户id
     * @date 2017年7月12日 下午4:07:37
     */
    public List<Microshop> getMerchantByParam(Long merchantId, Long queryMerchantId, String appId,
            Long relateCustomerId, Long projectId, int pageNo, int pageSize) {
        return microshopDao.getMerchantByParam(projectId,merchantId,relateCustomerId,queryMerchantId,appId,pageNo,pageSize);
    }

    /**
     * 根据merchantId和appid获取微旺铺信息
     * @param merchantId 当前登录客户id
     * @param appId 第三方id
     * @param relateMerchantId 关联商户id
     * @author 梁聪
     * @throws Exception
     * @date 2017年7月13日 下午3:32:05
     */
    public void relate(Long merchantId, String appId, Long relateMerchantId) throws Exception{
        Long id = microshopDao.getIdByParams(merchantId,appId);
        if (id == null) {
            Merchant merchant= MerchantClient.getById(merchantId);//获取merchant信息
            if(merchant == null){
                return;
            }
            microshopDao.addForRelate(merchant.getId(), merchant.getCascadeLabel(), merchant.getProjectId(), appId);
        }
        microshopDao.updateForRelate(merchantId,relateMerchantId,appId);
    }

    /**
     * 应用管理--微托、聚来宝--模式生效接口
     * @param spreadmodel 1 代表 模式一、2 代表 模式二
     * @param id id
     * @author 梁聪
     * @date 2016年7月14日 下午3:38:07
     */
    public void updateSpreadModel(int spreadmodel,Long id){
        microshopDao.updateSpreadModel(spreadmodel,id);
    }

    /**
     * 应用管理--微托、聚来宝—强制关注生效接口
     * @param forceattention 参数
     * @param id id
     * @author 梁聪
     * @date 2016年7月17日 下午2:12:11
     */
    public void updateForceAttention(Integer forceattention,Long id){
        microshopDao.updateForceAttention( forceattention, id);
    }

    /**
     * 通过商户id[merchantId]、应用id[appId]查询公众号信息
     * @param merchantId 商户id
     * @param appId 应用Id
     * @return map
     * @author 王冬冬  
     * @date 2017年7月13日 下午3:44:45
     */
    public Microshop getByParams(Long merchantId, String appId) {
        return microshopDao.getByParams(merchantId,appId);
    }
}

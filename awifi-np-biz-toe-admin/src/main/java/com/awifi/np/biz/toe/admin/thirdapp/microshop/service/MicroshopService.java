/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月16日 下午1:30:52
* 创建作者：许小满
* 文件名称：MicroshopService.java
* 版本：  v1.0
* 功能：微旺铺相关操作--业务层接口
* 修改记录：
*/
package com.awifi.np.biz.toe.admin.thirdapp.microshop.service;

import java.util.List;

import com.awifi.np.biz.toe.admin.thirdapp.microshop.model.Microshop;

public interface MicroshopService {
    /**
     * 通过商户id获取微旺铺表信息
     * @param merchantId 商户id
     * @return 微旺铺表记录
     * @author 许小满  
     * @date 2017年5月16日 下午1:35:08
     */
    Microshop getByMerchantId(Long merchantId);
    
    /**
     * 根据merchantId和appid获取微旺铺信息
     * @param merchantId 商户Id
     * @param appId appid
     * @return microshop
     * @author 王冬冬  
     * @date 2017年7月12日 下午3:32:05
     */
    Microshop getRelateMerIdByMerId(Long merchantId, String appId);

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
    int getMerchantCountByParam(Long merchantId,Long queryMerchantId, String appId,  Long relateCustomerId, Long projectId);

    /**
     * 获取关联配置商户列表数量
     * @param merchantId 商户id
     * @param appId 应用Id
     * @param relateCustomerId 关联商户id
     * @param projectId 项目id
     * @param pageNo 页数
     * @param pageSize 分页大小
     * @return 数量
     * @author 王冬冬  
     * @param queryMerchantId 查询商户id
     * @date 2017年7月12日 下午4:07:37
     */
    List<Microshop> getMerchantByParam(Long merchantId, Long queryMerchantId, String appId, Long relateCustomerId, Long projectId,int pageNo, int pageSize);

    /**
     * 根据merchantId和appid获取微旺铺信息
     * @param merchantId 当前登录客户id
     * @param appId 第三方id
     * @param relateMerchantId 关联商户id
     * @author 梁聪
     * @throws Exception
     * @date 2017年7月13日 下午3:32:05
     */
    void relate(Long merchantId, String appId, Long relateMerchantId) throws Exception;

    /**
     * 应用管理--微托、聚来宝--模式生效接口
     * @param spreadmodel 1 代表 模式一、2 代表 模式二
     * @param id  id
     * @author 梁聪
     * @date 2016年7月14日 下午3:38:07
     */
    void updateSpreadModel(int spreadmodel,Long id);

    /**
     * 应用管理--微托、聚来宝—强制关注生效接口
     * @param forceattention 参数
     * @param id id
     * @author 梁聪
     * @date 2016年7月17日 下午2:12:11
     */
    void updateForceAttention(Integer forceattention,Long id);

    /**
     * 通过merchantId、appId查询公众号信息
     * @param merchantId 商户id
     * @param appId 应用id
     * @return 公众号信息
     * @author 王冬冬  
     * @date 2017年7月13日 下午3:38:11
     */
    Microshop getByParams(Long merchantId, String appId);
    
}

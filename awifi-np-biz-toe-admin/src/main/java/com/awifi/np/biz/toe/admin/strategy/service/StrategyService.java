/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月19日 上午11:17:15
* 创建作者：周颖
* 文件名称：StrategyService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.toe.admin.strategy.service;

import java.util.List;
import java.util.Map;

import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.toe.admin.strategy.model.Strategy;

public interface StrategyService {

    /**
     * 批量获取站点下的策略数量
     * @param siteIdList 站点ids
     * @return 策略数
     * @author 周颖  
     * @date 2017年4月19日 下午1:41:17
     */
    Map<Long, Integer> getTotalBySiteIds(List<Long> siteIdList);

    /**
     * 判断站点是否关联策略
     * @param siteId 站点id
     * @return true 关联
     * @author 周颖  
     * @date 2017年4月25日 下午7:42:27
     */
    boolean isExist(Long siteId);

    /**
     * 策略列表
     * @param page 页面
     * @param siteId 站点id
     * @param strategyName 策略名称
     * @author 周颖  
     * @throws Exception 
     * @date 2017年4月27日 下午1:28:34
     */
    void getListByParam(Page<Strategy> page, Long siteId, String strategyName) throws Exception;

    /**
     * 判断一个站点下策略是否重名
     * @param siteId 站点id
     * @param strategyName 策略名称
     * @return true 已存在
     * @author 周颖  
     * @date 2017年4月28日 下午1:56:02
     */
    boolean isStrategyNameExist(Long siteId,String strategyName);
    
    /**
     * 新增策略
     * @param strategy 策略
     * @param content 内容
     * @author 周颖  
     * @date 2017年4月28日 下午1:29:33
     */
    void add(Strategy strategy, String content);

    /**
     * 获取站点下策略最大的排序号
     * @param siteId 站点id
     * @return 排序号
     * @author 周颖  
     * @date 2017年4月28日 下午2:24:54
     */
    Integer getMaxNo(Long siteId);

    /**
     * 判断一个站点下策略是否重名
     * @param siteId 站点id
     * @param strategyId 策略id
     * @param strategyName 策略名称
     * @return true 已存在
     * @author 周颖   
     * @date 2017年4月28日 下午2:57:44
     */
    boolean isStrategyNameExist(Long siteId, Long strategyId, String strategyName);

    /**
     * 编辑策略
     * @param strategy 策略
     * @param content 内容
     * @author 周颖  
     * @date 2017年4月28日 下午3:06:50
     */
    void update(Strategy strategy, String content);

    /**
     * 通过站点id获取最新的策略
     * @param siteId 站点id
     * @return 策略
     * @author 周颖  
     * @throws Exception 
     * @date 2017年5月2日 上午9:04:43
     */
    Strategy getBySiteId(Long siteId) throws Exception;

    /**
     * 策略详情
     * @param id 策略id
     * @return 详情
     * @author 周颖  
     * @throws Exception 
     * @date 2017年5月2日 上午10:28:23
     */
    Strategy getById(Long id) throws Exception;
    
    /**
     * 通过策略获取站点id
     * @param merchantId 商户id
     * @param ssid ssid
     * @param devId 设备id
     * @return 站点id
     * @author 许小满  
     * @date 2017年5月11日 下午6:14:16
     */
    Long getSiteId(Long merchantId, String ssid, String devId);
    
    /**
     * 通过策略获取站点id（缓存）
     * @param merchantId 商户id
     * @param ssid ssid
     * @param devId 设备id
     * @return 站点id
     * @author 许小满  
     * @date 2017年5月11日 下午6:14:16
     */
    Long getSiteIdCache(Long merchantId, String ssid, String devId);
    
    /**
     * 删除策略
     * @param id 策略id
     * @author 周颖  
     * @date 2017年5月2日 上午10:38:39
     */
    void delete(Long id);

    /**
     * 调整策略优先级
     * @param id 策略id
     * @param bodyParam 参数
     * @author 周颖  
     * @date 2017年5月2日 上午10:44:25
     */
    void priority(Long id, Map<String, Object> bodyParam);
}

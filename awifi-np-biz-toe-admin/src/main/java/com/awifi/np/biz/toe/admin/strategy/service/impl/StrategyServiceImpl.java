/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月19日 上午11:17:49
* 创建作者：周颖
* 文件名称：StrategyServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.toe.admin.strategy.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.common.base.constants.RedisConstants;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.toe.admin.strategy.dao.StrategyDao;
import com.awifi.np.biz.toe.admin.strategy.model.Strategy;
import com.awifi.np.biz.toe.admin.strategy.service.StrategyService;

@Service("strategyService")
public class StrategyServiceImpl implements StrategyService {

    /**策略*/
    @Resource(name = "strategyDao")
    private StrategyDao strategyDao;
    
    /**
     * 批量获取站点下的策略数量
     * @param siteIdList 站点ids
     * @return 策略数
     * @author 周颖  
     * @date 2017年4月19日 下午1:41:17
     */
    public Map<Long, Integer> getTotalBySiteIds(List<Long> siteIdList){
        int maxSize = siteIdList.size();
        Long[] siteIds = siteIdList.toArray(new Long[maxSize]);
        List<Map<String, Object>> totalList = strategyDao.getTotalBySiteIds(siteIds);
        Map<Long, Integer> totalMap = new HashMap<Long, Integer>();
        Long siteId = null;
        Integer total = null;
        for(Map<String, Object> obj : totalList){
            siteId = (Long) obj.get("siteId");
            total = (Integer) obj.get("num");
            totalMap.put(siteId, total);
        }
        return totalMap;
    }
    
    /**
     * 判断站点是否关联策略
     * @param siteId 站点id
     * @return true 关联
     * @author 周颖  
     * @date 2017年4月25日 下午7:42:27
     */
    public boolean isExist(Long siteId){
        int count = strategyDao.getNumBySiteId(siteId);
        return count >0;
    }
    
    /**
     * 策略列表
     * @param page 页面
     * @param siteId 站点id
     * @param strategyName 策略名称关键字
     * @author 周颖  
     * @throws Exception 
     * @date 2017年4月27日 下午1:28:34
     */
    public void getListByParam(Page<Strategy> page, Long siteId, String strategyName) throws Exception{
        int count = strategyDao.getCountByParam(siteId,strategyName);
        page.setTotalRecord(count);
        if(count <= 0){
            return;
        }
        List<Strategy> strategyList = strategyDao.getListByParam(siteId,strategyName,page.getBegin(),page.getPageSize());
        Long strategyId = null;
        Integer strategyType = null;
        String ssid = null;
        String deviceName = null;
        for(Strategy strategy : strategyList){
            strategyId = strategy.getId();
            strategyType = strategy.getStrategyType();
            if(strategyType.equals(1)){//全部
                strategy.setSsid("全部");
                strategy.setDeviceName("全部");
            }else if(strategyType.equals(2)){//ssid
                ssid = strategyDao.getContentById(strategyId);
                strategy.setSsid(ssid);
            }else if(strategyType.equals(3)){
                deviceName = this.getDeviceName(strategyId);
                strategy.setDeviceName(deviceName);      
            }
        }
        page.setRecords(strategyList);
    }
    
    /**
     * 获取设备名称
     * @param strategyId 策略id
     * @return 设备名称
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年4月27日 下午5:02:19
     */
    private String getDeviceName(Long strategyId) throws Exception{
        List<String> contentList = strategyDao.getContentsById(strategyId);//获取设备id集合
        int maxSize = contentList.size();
        StringBuffer str = new StringBuffer();
        String deviceId = null;
        String deviceName = null;
        for (int i = 0; i < maxSize; i++) {
            deviceId = contentList.get(i);//设备id
            if(StringUtils.isBlank(deviceId)){//为空 下个设备id
                continue;
            }
            deviceName = DeviceClient.getByDevId(deviceId).getDeviceName();//获取设备名称
            if (StringUtils.isNotBlank(deviceName)) {//不为空 拼接
                str.append(deviceName);
                if (i < maxSize - 1) {
                    str.append(",");
                }
            }
        }
        return str.toString();
    }
    
    /**
     * 新增策略
     * @param strategy 策略
     * @param content 内容
     * @author 周颖  
     * @date 2017年4月28日 下午1:29:33
     */
    public void add(Strategy strategy, String content){
        strategyDao.add(strategy);
        Long strategyId = strategy.getId();
        Integer strategyType = strategy.getStrategyType();
        this.addItem(strategyId, strategyType, content);
    }
    
    /**
     * 添加策略子表
     * @param strategyId 策略id
     * @param strategyType 策略类型
     * @param content 策略内容
     * @author 周颖  
     * @date 2017年5月2日 上午9:13:10
     */
    private void addItem(Long strategyId,Integer strategyType,String content){
        if (strategyType == 2) {//ssid
            strategyDao.addItem(strategyId, content);
        } else if (strategyType == 3) {//指定设备
            String[] contentStrArray = content.split(",");
            int maxLength = contentStrArray.length;
            for (int i = 0; i < maxLength; i++) {
                strategyDao.addItem(strategyId, contentStrArray[i]);
            }
        }
    }
    
    /**
     * 判断一个站点下策略是否重名（新建）
     * @param siteId 站点id
     * @param strategyName 策略名称
     * @return true 已存在
     * @author 周颖  
     * @date 2017年4月28日 下午1:56:02
     */
    public boolean isStrategyNameExist(Long siteId,String strategyName){
        int count = strategyDao.getNumByStrategyName(siteId,strategyName);
        return count > 0 ? true : false;
    }
    
    /**
     * 获取站点下策略最大的排序号
     * @param siteId 站点id
     * @return 排序号
     * @author 周颖  
     * @date 2017年4月28日 下午2:24:54
     */
    public Integer getMaxNo(Long siteId){
        Integer maxNo =  strategyDao.getMaxNo(siteId);
        if(maxNo == null){
            return 0;
        }
        return maxNo;
    }
    
    /**
     * 判断一个站点下策略是否重名（编辑）
     * @param siteId 站点id
     * @param strategyId 策略id
     * @param strategyName 策略名称
     * @return true 已存在
     * @author 周颖   
     * @date 2017年4月28日 下午2:57:44
     */
    public boolean isStrategyNameExist(Long siteId, Long strategyId, String strategyName){
        int count = strategyDao.getNumByIdAndName(siteId,strategyId,strategyName);
        return count > 0 ? true : false; 
    }
    
    /**
     * 编辑策略
     * @param strategy 策略
     * @param content 内容
     * @author 周颖  
     * @date 2017年4月28日 下午3:06:50
     */
    public void update(Strategy strategy, String content){
        Long strategyId = strategy.getId();
        strategyDao.deleteItem(strategyId);//删除子表
        strategyDao.update(strategy);
        Integer strategyType = strategy.getStrategyType();
        addItem(strategyId,strategyType,content);
    }
    
    /**
     * 通过站点id获取最新的策略
     * @param siteId 站点id
     * @return 策略
     * @author 周颖  
     * @throws Exception 
     * @date 2017年5月2日 上午9:04:43
     */
    public Strategy getBySiteId(Long siteId) throws Exception{
        Strategy strategy = strategyDao.getBySiteId(siteId);
        if(strategy == null){
            return null;
        }
        this.formatItem(strategy);
        return strategy;
    }
    
    /**
     * 补充策略子项
     * @param strategy 策略
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年5月2日 上午10:00:19
     */
    public void formatItem(Strategy strategy) throws Exception{
        Long strategyId = strategy.getId();
        Integer strategyType = strategy.getStrategyType();
        if(strategyType.equals(1)){
            strategy.setSsid("全部");
            strategy.setDeviceName("全部");
        }else if(strategyType.equals(2)){//ssid
            String ssid = strategyDao.getContentById(strategyId);
            strategy.setSsid(ssid);
        }else if(strategyType.equals(3)){
            List<String> contentList = strategyDao.getContentsById(strategyId);//获取设备id集合
            List<Map<String,Object>> deviceList = new ArrayList<Map<String,Object>>();
            int maxSize = contentList.size();
            String deviceId = null;
            String deviceName = null;
            Map<String,Object> deviceMap = null;
            for (int i = 0; i < maxSize; i++) {
                deviceId = contentList.get(i);//设备id
                if(StringUtils.isBlank(deviceId)){//为空 下个设备id
                    continue;
                }
                deviceMap = new HashMap<String,Object>();
                deviceName = DeviceClient.getByDevId(deviceId).getDeviceName();//获取设备名称
                deviceMap.put("deviceId", deviceId);
                deviceMap.put("deviceName", deviceName);
                deviceList.add(deviceMap);
            }
            strategy.setDeviceList(deviceList);
        }
    }
    
    /**
     * 策略详情
     * @param id 策略id
     * @return 详情
     * @author 周颖  
     * @throws Exception 
     * @date 2017年5月2日 上午10:28:23
     */
    public Strategy getById(Long id) throws Exception{
        Strategy strategy = strategyDao.getById(id);
        this.formatItem(strategy);
        return strategy;
    }
    
    /**
     * 通过策略获取站点id
     * @param merchantId 商户id
     * @param ssid ssid
     * @param devId 设备id
     * @return 站点id
     * @author 许小满  
     * @date 2017年5月11日 下午6:14:16
     */
    public Long getSiteId(Long merchantId, String ssid, String devId){
        return strategyDao.getSiteId(merchantId, ssid, devId);
    }
    
    /**
     * 通过策略获取站点id
     * @param merchantId 商户id
     * @param ssid ssid
     * @param devId 设备id
     * @return 站点id
     * @author 许小满  
     * @date 2017年5月11日 下午6:14:16
     */
    public Long getSiteIdCache(Long merchantId, String ssid, String devId){
        //1.从redis缓存中读取信息
        String redisKey = RedisConstants.SITE_STRATEGY_ID + merchantId + "_" + StringUtils.defaultString(ssid) + "_" + StringUtils.defaultString(devId);
        String siteIdStr = RedisUtil.get(redisKey);
        boolean siteIdNotNull = StringUtils.isNotBlank(siteIdStr);
        if(siteIdNotNull && siteIdStr.equals("-1")){//表示上次已经查询过而且策略未配置
            return null;
        }
        Long siteId = siteIdNotNull ? Long.parseLong(siteIdStr) : null;
        //2.如果找不到，则从数据库中获取，然后再放入redis缓存中
        if(siteId == null){
            siteId = this.getSiteId(merchantId, ssid, devId);
            RedisUtil.set(redisKey, (siteId != null ? siteId.toString() : "-1"), RedisConstants.SITE_STRATEGY_ID_TIME);
        }
        return siteId;
    }
    
    /**
     * 删除策略
     * @param id 策略id
     * @author 周颖  
     * @date 2017年5月2日 上午10:38:39
     */
    public void delete(Long id){
        strategyDao.delete(id);
    }
    
    /**
     * 调整策略优先级
     * @param id 策略id
     * @param bodyParam 参数
     * @author 周颖  
     * @date 2017年5月2日 上午10:44:25
     */
    public void priority(Long id, Map<String, Object> bodyParam){
        Long siteId = CastUtil.toLong(bodyParam.get("siteId"));
        String sign = (String) bodyParam.get("sign");
        Integer orderNo = CastUtil.toInteger(bodyParam.get("orderNo"));
        ValidUtil.valid("站点id[siteId]", siteId, "{'required':true,'numeric':true}");
        ValidUtil.valid("上下移标识[sign]", sign, "required");
        ValidUtil.valid("当前排序号[orderNo]", orderNo, "{'required':true,'numeric':true}");
        if(StringUtils.equals(sign, "up")){//上移
            Integer maxOrderNo = strategyDao.getMaxOrderNo(siteId);//获取站点下策略最大排序号
            if(orderNo >= maxOrderNo){//如果当前排序号大于等于站点下策略最大排序号，抛异常
                throw new BizException("E2600009", MessageUtil.getMessage("E2600009"));//当前策略已位于顶部！
            }
            Map<String,Object> orderNoUpMap = strategyDao.getUpStrategy(siteId,orderNo);
            strategyDao.updateOrderNo(id,(Integer)orderNoUpMap.get("orderNo"));
            strategyDao.updateOrderNo((Long)orderNoUpMap.get("id"),orderNo);
        }else if(StringUtils.equals(sign, "down")){
            Integer minOrderNo = strategyDao.getMinOrderNo(siteId);
            if(orderNo <= minOrderNo){
                throw new BizException("E2600010", MessageUtil.getMessage("E2600010"));//当前策略已位于底部！
            }
            Map<String,Object> orderNoDownMap = strategyDao.getDownStrategy(siteId,orderNo);
            strategyDao.updateOrderNo(id,(Integer)orderNoDownMap.get("orderNo"));
            strategyDao.updateOrderNo((Long)orderNoDownMap.get("id"),orderNo);
        }else{
            throw new BizException("E2600008", MessageUtil.getMessage("E2600008", sign));//上下移标识（sign[{0}]）超出了范围，不在[up|down]内！
        }
    }
}

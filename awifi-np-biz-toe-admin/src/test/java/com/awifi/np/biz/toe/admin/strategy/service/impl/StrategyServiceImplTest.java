/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月20日 上午10:31:23
* 创建作者：方志伟
* 文件名称：StrategyServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.toe.admin.strategy.service.impl;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.toe.admin.strategy.dao.StrategyDao;
import com.awifi.np.biz.toe.admin.strategy.model.Strategy;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RedisUtil.class,JsonUtil.class,ValidUtil.class,BeanUtil.class,MessageUtil.class})
public class StrategyServiceImplTest {
    /**被测试类*/
    @InjectMocks
    private StrategyServiceImpl strategyServiceImpl;
    /**持久层*/
    @Mock(name = "strategyDao")
    private StrategyDao strategyDao;
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(RedisUtil.class);
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(ValidUtil.class);
        PowerMockito.mockStatic(BeanUtil.class);
        PowerMockito.mockStatic(MessageUtil.class);
    }
    
    /**
     * 测试批量获取站点下的策略数量
     * @author 方志伟  
     * @date 2017年6月20日 上午11:09:03
     */
    @Test
    public void testGetTotalBySiteIds(){
        List<Long> list = new ArrayList<Long>();
        list.add(1L);
        list.add(2L);
        strategyServiceImpl.getTotalBySiteIds(list);
    }
    
    /**
     * 测试判断站点是否关联策略
     * @author 方志伟  
     * @date 2017年6月20日 下午1:51:58
     */
    @Test
    public void testIsExist(){
        strategyServiceImpl.isExist(1L);
    }
    
    /**
     * 策略列表
     * @throws Exception 异常
     * @author 方志伟  
     * @date 2017年6月21日 下午4:21:39
     */
    @Test
    public void testGetListByParamNull() throws Exception {
//        List<String> list = new ArrayList<String>();
//        when(strategyDao.getContentsById(anyObject())).thenReturn(list);
        when(strategyDao.getCountByParam(anyObject(), anyObject())).thenReturn(0);
        Page<Strategy> page = new Page<Strategy>();
        page.setPageSize(15);
        strategyServiceImpl.getListByParam(page, 1L, "strategyName");
        PowerMockito.verifyStatic();
    }
    
    /**
     * 策略列表
     * StrategyType = 1
     * @throws Exception 异常
     * @author 方志伟  
     * @date 2017年6月21日 下午4:51:21
     */
    @Test
    public void testGetListByParam01() throws Exception {
        when(strategyDao.getCountByParam(anyObject(), anyObject())).thenReturn(1);
        List<Strategy> strategyList = new ArrayList<Strategy>();
        Strategy strategy = new Strategy();
        strategy.setMerchantId(1L);
        strategy.setStrategyType(1);
        strategyList.add(strategy);
        when(strategyDao.getListByParam(anyObject(),anyObject(),anyObject(),anyObject())).thenReturn(strategyList);
        Page<Strategy> page = new Page<Strategy>();
        page.setPageSize(15);
        strategyServiceImpl.getListByParam(page, 180L, "测试策略001勿删");
        PowerMockito.verifyStatic();
    }
    
    /**
     * 策略列表
     * StrategyType = 2
     * @throws Exception 异常
     * @author 方志伟  
     * @date 2017年6月21日 下午4:51:29
     */
    @Test
    public void testGetListByParam02() throws Exception {
        when(strategyDao.getCountByParam(anyObject(), anyObject())).thenReturn(1);
        List<Strategy> strategyList = new ArrayList<Strategy>();
        Strategy strategy = new Strategy();
        strategy.setMerchantId(1L);
        strategy.setStrategyType(2);
        strategyList.add(strategy);
        when(strategyDao.getListByParam(anyObject(),anyObject(),anyObject(),anyObject())).thenReturn(strategyList);
        Page<Strategy> page = new Page<Strategy>();
        page.setPageSize(15);
        strategyServiceImpl.getListByParam(page, 180L, "测试策略001勿删");
        PowerMockito.verifyStatic();
    }
    
    /**
     * 策略列表
     * StrategyType = 3
     * @throws Exception 异常
     * @author 方志伟  
     * @date 2017年6月21日 下午4:51:36
     */
    @Test
    public void testGetListByParam03() throws Exception {
        when(strategyDao.getCountByParam(anyObject(), anyObject())).thenReturn(1);
        List<Strategy> strategyList = new ArrayList<Strategy>();
        Strategy strategy = new Strategy();
        strategy.setMerchantId(1L);
        strategy.setStrategyType(3);
        strategyList.add(strategy);
        when(strategyDao.getListByParam(anyObject(),anyObject(),anyObject(),anyObject())).thenReturn(strategyList);
        Page<Strategy> page = new Page<Strategy>();
        page.setPageSize(15);
        strategyServiceImpl.getListByParam(page, 180L, "测试策略001勿删");
        PowerMockito.verifyStatic();
    }
    
    /**
     * 测试新增策略
     * strategyType == 2
     * @author 方志伟  
     * @date 2017年6月20日 下午2:31:53
     */
    @Test
    public void testAdd01(){
        Strategy strategy = new Strategy();
        strategy.setCascadeLabel("xxxx");
        strategy.setDeviceName("deviceName");
        strategy.setStrategyType(2);
        strategyServiceImpl.add(strategy, "123123");
    }
    
    /**
     * 测试新增策略
     * strategyType == 3
     * @author 方志伟  
     * @date 2017年6月20日 下午2:32:30
     */
    @Test
    public void testAdd02(){
        Strategy strategy = new Strategy();
        strategy.setCascadeLabel("xxxx");
        strategy.setDeviceName("deviceName");
        strategy.setStrategyType(3);
        strategyServiceImpl.add(strategy, "123123");
    }
    
    /**
     * 判断一个站点下策略是否重名（新建）接口测试
     * @author 方志伟  
     * @date 2017年6月20日 下午2:35:17
     */
    @Test
    public void testIsStrategyNameExist(){
        strategyServiceImpl.isStrategyNameExist(1L, "strategyName");
    }
    
    /**
     * 测试获取站点下策略最大的排序号
     * @author 方志伟  
     * @date 2017年6月20日 下午2:37:21
     */
    @Test
    public void testGetMaxNo(){
        strategyServiceImpl.getMaxNo(1L);
    }
    
    /**
     * 判断一个站点下策略是否重名（编辑）接口测试
     * @author 方志伟  
     * @date 2017年6月20日 下午2:43:47
     */
    @Test
    public void testIsStrategyNameExistEdit(){
        strategyServiceImpl.isStrategyNameExist(1L, 2L, "strategyName");
    }
    
    /**
     * 测试编辑策略
     * @author 方志伟  
     * @date 2017年6月20日 下午2:45:15
     */
    @Test
    public void testUpdate(){
        Strategy strategy = new Strategy();
        strategy.setCascadeLabel("xxxx");
        strategy.setDeviceName("deviceName");
        strategy.setStrategyType(3);
        strategyServiceImpl.update(strategy, "content");
    }
    
    /**
     * 测试通过站点id获取最新的策略
     * @throws Exception 异常
     * @author 方志伟  
     * @date 2017年6月20日 下午2:51:47
     */
    @Test
    public void testGetBySiteId() throws Exception{
        strategyServiceImpl.getBySiteId(1L);
    }
    
    /**
     * 测试补充策略子项
     * @throws Exception 异常
     * @author 方志伟  
     * @date 2017年6月20日 下午3:09:18
     */
    @Test
    public void testFormatItem01() throws Exception{
        Strategy strategy = new Strategy();
        strategy.setId(1L);
        strategy.setCascadeLabel("xxxx");
        strategy.setDeviceName("deviceName");
        strategy.setStrategyType(1);
        strategyServiceImpl.formatItem(strategy);
    }
    
    /**
     * 测试补充策略子项
     * @throws Exception 异常
     * @author 方志伟  
     * @date 2017年6月20日 下午3:09:15
     */
    @Test
    public void testFormatItem02() throws Exception{
        Strategy strategy = new Strategy();
        strategy.setId(1L);
        strategy.setCascadeLabel("xxxx");
        strategy.setDeviceName("deviceName");
        strategy.setStrategyType(2);
        strategyServiceImpl.formatItem(strategy);
    }
    
    /**
     * 测试补充策略子项
     * @throws Exception 异常
     * @author 方志伟  
     * @date 2017年6月20日 下午3:09:12
     */
    @Test
    public void testFormatItem03() throws Exception{
        Strategy strategy = new Strategy();
        strategy.setId(3L);
        strategy.setCascadeLabel("xxxx");
        strategy.setDeviceName("deviceName");
        strategy.setStrategyType(3);
        strategyServiceImpl.formatItem(strategy);
    }
    
    /**
     * 测试通过策略获取站点id
     * @throws Exception 异常
     * @author 方志伟  
     * @date 2017年6月20日 下午3:13:31
     */
    @Test
    public void testGetSiteId() throws Exception{
        strategyServiceImpl.getSiteId(1L, "ssid", "devId");
    }
    
    /**
     * 测试通过策略获取站点id
     * @author 方志伟  
     * @date 2017年6月20日 下午3:30:01
     */
    @Test
    public void testGetSiteIdCache(){
        Map<String, Object> redisMap = new HashMap<String, Object>();
        redisMap.put("np_biz_site_strategy_id_EMPTY_EMPTY", "redisValue");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", "1");
        PowerMockito.when(JsonUtil.fromJson(anyString(),anyObject())).thenReturn(resultMap);
        strategyServiceImpl.getSiteIdCache(1L, "ssid", "devId");
    }
    
    /**
     * 测试删除策略
     * @author 方志伟  
     * @date 2017年6月20日 下午3:30:23
     */
    @Test
    public void testDelete(){
        strategyServiceImpl.delete(1L);
    }
    
    /**
     * 测试调整策略优先级
     * @throws Exception 异常
     * @author 方志伟  
     * @date 2017年6月20日 下午3:40:00
     */
    @Test(expected=Exception.class)
    public void testPriorityUp() throws Exception{
        Map<String, Object> bodyParam = new HashMap<String, Object>();
        bodyParam.put("result", "1");
        bodyParam.put("sign", "up");
        strategyServiceImpl.priority(1L, bodyParam);
    }
    
    /**
     * 测试调整策略优先级
     * @throws Exception 异常
     * @author 方志伟  
     * @date 2017年6月22日 上午9:31:32
     */
    @Test(expected=Exception.class)
    public void testPriorityDown() throws Exception{
        Map<String, Object> bodyParam = new HashMap<String, Object>();
        bodyParam.put("result", "1");
        bodyParam.put("sign", "down");
        strategyServiceImpl.priority(1L, bodyParam);
    }
    
    /**
     * 测试调整策略优先级
     * @throws Exception 异常
     * @author 方志伟  
     * @date 2017年6月22日 上午9:31:32
     */
    @Test(expected=Exception.class)
    public void testPriorityNull() throws Exception{
        Map<String, Object> bodyParam = new HashMap<String, Object>();
        bodyParam.put("result", "1");
        strategyServiceImpl.priority(1L, bodyParam);
    }
}

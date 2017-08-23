package com.awifi.np.biz.timebuysrv.web.module.time.dao;

import com.alibaba.fastjson.JSON;
import com.awifi.np.biz.timebuysrv.merchant.dao.sql.MerchantNewsSql;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantNews;
import com.awifi.np.biz.timebuysrv.web.module.time.dao.TimePackageDao;
import com.awifi.np.biz.timebuysrv.web.module.time.dao.UserConsumeDao;
import com.awifi.np.biz.timebuysrv.web.module.time.dao.sql.TimePackageSql;
import com.awifi.np.biz.timebuysrv.web.module.time.model.TimePackage;
import com.awifi.np.biz.timebuysrv.web.module.time.model.UserConsume;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by dozen.zhang on 2017/4/18.
 */

/** 
 * @author arganzheng
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({
//    "classpath:spring/spring-context.xml"
//  
//})
public class UserConsumerDaoTest {/*
   private Logger logger =LoggerFactory.getLogger(UserConsumerDaoTest.class);
    *//**
     * 被测试类
     *//*
    @Autowired
    private UserConsumeDao userConsumeMapper;

    *//**
     * 初始化
     *//*
    @Before
    public void before() {
       
    }

    *//**
     * 测试查询时间套餐列表.
     *
     * @throws Exception
     * @author 张智威
     * @date 2017年4月6日 上午10:02:42
     *//*
    @Test
    public void testSelectByPrimaryKey() throws Exception {
        userConsumeMapper.selectByPrimaryKey(1l);
    }
    *//**
     * 测试添加套餐
     * 
     * @author 张智威  
     * @date 2017年4月19日 下午12:05:14
     *//*
    @Test
    public void testInsertSelective(){
        UserConsume consume =new UserConsume();
        consume.setBeginDate(new Date());
        consume.setAddDay(1);
        consume.setBroadbandAccount("13958173965");
        consume.setConsumeType(2);
        consume.setCreateDate(new Date());
        consume.setEndDate(new Date());
        consume.setMerchantId(123l);
        consume.setOrderId("123123");
        consume.setPackageId(123l);
        consume.setPackageNum(2);
        consume.setPayNum(123f);
        consume.setPkgDetail("months");
        consume.setPkgPrice("123");
        consume.setRemarks("123123");
        consume.setTotalNum(1f);
        consume.setUserId(720l);
        userConsumeMapper.insertSelective(consume);
    }
    
    @Test
    public void testUpdateByPrimaryKeySelective(){
        UserConsume consume =new UserConsume();
        consume.setBeginDate(new Date());
        consume.setAddDay(1);
        consume.setBroadbandAccount("13958173965");
        consume.setConsumeType(2);
        consume.setCreateDate(new Date());
        consume.setEndDate(new Date());
        consume.setMerchantId(123l);
        consume.setOrderId("123123");
        consume.setPackageId(123l);
        consume.setPackageNum(2);
        consume.setPayNum(123f);
        consume.setPkgDetail("months");
        consume.setPkgPrice("123");
        consume.setRemarks("123123");
        consume.setTotalNum(1f);
        consume.setUserId(720l);
        consume.setId(1l);
        userConsumeMapper.updateByPrimaryKeySelective(consume);
    }
    @Test
    public void testListByParams(){
        HashMap map =new HashMap();
        userConsumeMapper.listByParams(map);
    }
    
    @Test
    public void testlistByParams4Page(){
        HashMap map =new HashMap();
        map.put("start", 1);
        map.put("pageSize", 10);
        userConsumeMapper.listByParams4Page(map);
    }
    
    @Test
    public void testcountByParams(){
        HashMap map =new HashMap();
       
        userConsumeMapper.countByParams(map);
    }
    
    @Test
    public void testunionListByParams4Page(){
        HashMap map =new HashMap();
        map.put("start", 1);
        map.put("pageSize", 10);
        userConsumeMapper.unionListByParams4Page(map);
    }
    
    @Test
    public void testunionCountByParams(){
        HashMap map =new HashMap();
       
        userConsumeMapper.unionCountByParams(map);
    }
    
    @Test
    public void testgetUserTotalPayment(){
        HashMap map =new HashMap();
       
        userConsumeMapper.getUserTotalPayment(map);
    }
   
*/}

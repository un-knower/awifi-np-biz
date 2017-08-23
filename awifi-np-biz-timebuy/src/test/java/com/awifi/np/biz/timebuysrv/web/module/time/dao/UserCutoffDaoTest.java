package com.awifi.np.biz.timebuysrv.web.module.time.dao;

import com.alibaba.fastjson.JSON;
import com.awifi.np.biz.timebuysrv.merchant.dao.sql.MerchantNewsSql;
import com.awifi.np.biz.timebuysrv.merchant.model.MerchantNews;
import com.awifi.np.biz.timebuysrv.web.module.time.dao.UserCutoffDateDao;
import com.awifi.np.biz.timebuysrv.web.module.time.dao.TimePackageDao;
import com.awifi.np.biz.timebuysrv.web.module.time.dao.UserConsumeDao;
import com.awifi.np.biz.timebuysrv.web.module.time.dao.sql.TimePackageSql;
import com.awifi.np.biz.timebuysrv.web.module.time.model.TimePackage;
import com.awifi.np.biz.timebuysrv.web.module.time.model.UserConsume;
import com.awifi.np.biz.timebuysrv.web.module.time.model.UserCutoff;

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
public class UserCutoffDaoTest {
//   private Logger logger =LoggerFactory.getLogger(UserCutoffDaoTest.class);
//    /**
//     * 被测试类
//     */
//    @Autowired
//    private UserCutoffDateDao userCutoffDao;
//
//    /**
//     * 初始化
//     */
//    @Before
//    public void before() {
//       
//    }
//
//    
//    @Test
//    public void testdeleteByPrimaryKey() throws Exception {
//        userCutoffDao.deleteByPrimaryKey(1l);
//    }
//   
//    @Test
//    public void testinsert(){
//        UserCutoff consume =new UserCutoff();
//        consume.setCutoffDate(new Date());
//        consume.setMerchantId(10l);
//        consume.setUserId(720l);
//        consume.setRemarks("123123");
//        userCutoffDao.insert(consume);
//    }
//    
//    @Test
//    public void testInsertSelective(){
//        UserCutoff consume =new UserCutoff();
//        consume.setCutoffDate(new Date());
//        consume.setMerchantId(10l);
//        consume.setUserId(720l);
//        consume.setRemarks("123123");
//        userCutoffDao.insertSelective(consume);
//    }
//    @Test
//    public void testselectByPrimaryKey(){
//        userCutoffDao.selectByPrimaryKey(1l);
//    }
//    
//    @Test
//    public void updateByPrimaryKeySelective(){
//        UserCutoff consume =new UserCutoff();
//        consume.setCutoffDate(new Date());
//        consume.setMerchantId(10l);
//        consume.setUserId(720l);
//        consume.setRemarks("123123");
//        consume.setId(1l);
//        userCutoffDao.updateByPrimaryKeySelective(consume);
//    }
//    
//    @Test
//    public void updateEndDate(){
//        userCutoffDao.updateEndDate(1l,new Date());
//    }
//    
//    @Test
//    public void testupdateByPrimaryKey(){
//        UserCutoff consume =new UserCutoff();
//        consume.setCutoffDate(new Date());
//        consume.setMerchantId(10l);
//        consume.setUserId(720l);
//        consume.setRemarks("123123");
//        consume.setId(1l);
//        userCutoffDao.updateByPrimaryKey(consume);
//    }
//    
//    @Test
//    public void testselectByMap(){
//        HashMap map =new HashMap();
//        map.put("merchantId", 1);
//        userCutoffDao.selectByMap(map);
//    }
//    
//    @Test
//    public void testgetUserTotalPayment(){
//        userCutoffDao.selectByUserIdAndMerId(720l,1l);
//        
//    }
//    @Test
//    public void testqueryCountByParam(){
//        HashMap map =new HashMap();
//        map.put("id", 1);
//        userCutoffDao.queryCountByParam(map);
//        
//    }
//   
}

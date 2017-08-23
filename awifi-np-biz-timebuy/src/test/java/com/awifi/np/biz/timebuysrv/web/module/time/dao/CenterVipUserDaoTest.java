/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月20日 下午4:12:20
* 创建作者：余红伟
* 文件名称：CenterOnlineUserDaoTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.web.module.time.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.awifi.np.biz.timebuysrv.web.module.time.model.VipUserObject;
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({
//    "classpath:spring/spring-context.xml"
//})
public class CenterVipUserDaoTest {
    private Logger logger = LoggerFactory.getLogger(CenterVipUserDaoTest.class);
    /**
     * 被测试类
     */
    @Autowired
    private CenterVipUserDao centerOnlineUserDao;
    
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
    /**
     * 新增推送用户
     * @throws Exception
     * @author 余红伟 
     * @date 2017年4月20日 下午6:58:26
     */
//    @Test
    public void testAdd() throws Exception{
        VipUserObject object = new VipUserObject();
        object.setTelephone("183172787555512");
        object.setProcessFlg("d656565sd");
        object.setStartTime(System.currentTimeMillis());
        object.setEndTime(System.currentTimeMillis());
        object.setCreateDate(new Date());
        object.setModifyDate(new Date());
        object.setMerchantId(90L);
        centerOnlineUserDao.add(object);
        
    }
    
//    @Test
    public void testUpdate(){
        VipUserObject object =new VipUserObject();
        object.setId(1);
        object.setTelephone("18317278712");
        object.setProcessFlg("星之守千秋");
        centerOnlineUserDao.update(object);
    }
    /**
     * 根据手机号、时间查询有效记录数
     * 
     * @author 余红伟 
     * @date 2017年4月20日 下午6:58:04
     */
//    @Test
    public void testQueryOnlineUserCount(){
        Map<String,Object> params = new HashMap<>();
        params.put("telephone", "18317278712");
        centerOnlineUserDao.queryOnlineUserCount(params);
    }
    /**
     * 根据手机号查询最新记录
     * 
     * @author 余红伟 
     * @date 2017年4月20日 下午7:46:00
     */
//    @Test
    public void testQueryLastOnlineUser(){
        Map<String, Object> params = new HashMap<>();
        params.put("telephone", "18317278712");
        centerOnlineUserDao.queryLastOnlineUser(params);
    }
    /**
     * 根据条件查询电渠用户数据列表
     * 
     * @author 余红伟 
     * @date 2017年4月20日 下午7:45:56
     */
//    @Test
    public void testQueryListByMerArea(){
        Map<String, Object> params = new HashMap<>();
        params.put("telephone", "18317278712");
        List<VipUserObject> list = centerOnlineUserDao.queryListByMerArea(params);
        logger.debug(JSON.toJSONString(list));
    }
    
//    @Test
    public void testQueryCountByMerArea(){
        Map<String, Object> params = new HashMap<>();
        params.put("telephone", "18317278712");
        centerOnlineUserDao.queryCountByMerArea(params);
    }
    /**
     * 更新商户id
     * 
     * @author 余红伟 
     * @date 2017年4月21日 上午8:56:49
     */
//    @Test
    public void testUpdateVipUser(){
        VipUserObject object =new VipUserObject();
        object.setTelephone("18317278712");
        object.setMerchantId(10);
        centerOnlineUserDao.updateVipUser(object);
    }
//    @Test
    public void testQueryListByParams(){
        Map<String, Object> map = new HashMap<>();
        map.put("telephone", 123456789176L);
        map.put("pageNum", 1);
        map.put("pageSize", 10);
        //start参数在controller内计算
        Integer start = 0;
        map.put("start", 0);
        List<VipUserObject> list = centerOnlineUserDao.queryListByParam(map);
        logger.debug(JSON.toJSONString(list));
    }
    
//    @Test
    public void tesIsVipUser(){
        Long telephone = 18317278712L;
        int count = centerOnlineUserDao.isVipUser(telephone);
        logger.debug("结果条数：" + JSON.toJSONString(count));
    }
}

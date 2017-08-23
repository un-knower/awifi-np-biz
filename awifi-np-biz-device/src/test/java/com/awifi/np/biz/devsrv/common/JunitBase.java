//package com.awifi.np.biz.devsrv.common;
//
//
//import org.apache.log4j.Logger;
//import org.junit.Before;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.web.context.WebApplicationContext;
//
//import com.awifi.np.biz.common.util.BeanUtil;
//import com.awifi.np.biz.devsrv.fatap.service.PlatFormBaseService;
///**
// * 
//* @ClassName: JunitBase
//* @Description: 单元测试抽象类
//* @author wuqia
//* @date 2017年5月11日 上午9:53:46
//*
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath*:spring/spring-mvc.xml","classpath*:spring/spring-context.xml"})
//@WebAppConfiguration()
//public abstract class JunitBase {
//	protected Logger logger=Logger.getLogger(JunitBase.class);
//	@Autowired
//	protected WebApplicationContext wac;
//	protected MockHttpServletRequest request;
//	protected MockHttpServletResponse response;
//	@Before
//	public void before(){
//	   BeanUtil.setWebApplicationContext(wac);
//    }
//}

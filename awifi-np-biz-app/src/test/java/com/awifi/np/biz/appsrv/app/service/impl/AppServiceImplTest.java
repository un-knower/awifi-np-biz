package com.awifi.np.biz.appsrv.app.service.impl;

import static org.mockito.Matchers.anyString;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import com.awifi.np.biz.appsrv.app.dao.AppDao;
import com.awifi.np.biz.appsrv.app.model.App;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.util.MessageUtil;


/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017年7月12日 上午10:53:19
 * 创建作者：许尚敏
 * 文件名称：AppServiceImplTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ AppDao.class,MessageUtil.class,SessionUser.class,Page.class,App.class})
public class AppServiceImplTest {
    /**
     * 被测试类
     */
    @InjectMocks
    private AppServiceImpl appServiceImpl;
    
    /**
     * appDao
     */
    @Mock
    private AppDao appDao;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(AppDao.class);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(SessionUser.class);
        PowerMockito.mockStatic(Page.class);
        PowerMockito.mockStatic(App.class);
    }
    
    /**
     * 应用管理—应用添加接口
     * @author 许尚敏
     * @date 2017年7月12日 上午10:23:22
     */
    @Test
    public void testAdd() {
        App app = new App();
        app.setAppId("asasdada");//设置appId
        app.setAppKey("dssdaa");//设置appKey
        app.setAppName("11");//设置应用名称
        app.setAppParam("dssdaa");//设置应用参数
        app.setCompany("dssdaa");//设置公司名称
        app.setBusinessLicense("dssdaa");//设置营业执照
        app.setContactPerson("dssdaa");//设置联系人
        app.setContactWay("dssdaa");//设置联系方式
        app.setStatus(1);//设置状态
        app.setRemark("dssdaa");//设置备注
        appServiceImpl.add(app);
    }

    /**
     * 应用管理—应用编辑接口
     * @author 许尚敏
     * @date 2017年7月12日 上午9:53:22
     */
    @Test
    public void testUpdate() {
        App app = new App();
        app.setId(1L);
        app.setAppId("asasdada");//设置appId
        app.setAppKey("dssdaa");//设置appKey
        app.setAppName("11");//设置应用名称
        app.setAppParam("dssdaa");//设置应用参数
        app.setCompany("dssdaa");//设置公司名称
        app.setBusinessLicense("dssdaa");//设置营业执照
        app.setContactPerson("dssdaa");//设置联系人
        app.setContactWay("dssdaa");//设置联系方式
        app.setStatus(1);//设置状态
        app.setRemark("dssdaa");//设置备注
        appServiceImpl.update(app);
    }

    /**
     * 测试应用管理-分页查询接口
     * 
     * @author 季振宇  
     * @date Jul 14, 2017 9:46:10 AM
     */
    @Test
    public void testGetListByParam() {
        PowerMockito.when(appDao.getCountByParam(Mockito.anyString(),Mockito.anyInt())).thenReturn(2);
        List<App> list = new ArrayList<>();
        
        for (int i = 0; i < 9; i++) {
            App app = new App();
            app.setStatus(1);
            list.add(app);
        }
        App app1 = new App();
        app1.setStatus(2);
        list.add(app1);
        PowerMockito.when(appDao.getListByParam(Mockito.anyString(), Mockito.anyInt(), Mockito.anyObject(), Mockito.anyObject())).thenReturn(list);
        
        SessionUser sessionUser = new SessionUser();
        Page<App> page = new Page<>();
        page.setPageSize(2);
        appServiceImpl.getListByParam(sessionUser, page, "12", 1);
    }
    
    /**
     * 测试应用管理-分页查询接口
     * 
     * @author 季振宇  
     * @date Jul 14, 2017 9:46:10 AM
     */
    @Test
    public void testGetListByParam1() {
        PowerMockito.when(appDao.getCountByParam(Mockito.anyString(),Mockito.anyInt())).thenReturn(0);
        App app = new App();
        app.setStatus(1);
        List<App> list = new ArrayList<>();
        list.add(app);
        
        PowerMockito.when(appDao.getListByParam(Mockito.anyString(), Mockito.anyInt(), Mockito.anyObject(), Mockito.anyObject())).thenReturn(list);

        SessionUser sessionUser = new SessionUser();
        Page<App> page = new Page<>();
        page.setPageSize(1);
        appServiceImpl.getListByParam(sessionUser, page, "12", 1);
    }
    
    /**
     * 测试应用管理-应用列表-详情接口
     * 
     * @author 季振宇  
     * @date Jul 14, 2017 9:46:33 AM
     */
    @Test
    public void testGetById() {
        App app = new App();
        app.setStatus(1);
        PowerMockito.when(appDao.getById(Mockito.anyLong())).thenReturn(app);
        App result = appServiceImpl.getById(2L);
        
        Assert.assertNotNull(result);
    }
    
    /**
     * 测试应用管理-应用列表-详情接口
     * 
     * @author 季振宇  
     * @date Jul 14, 2017 9:46:33 AM
     */
    @Test
    public void testGetById1() {
        App app = new App();
        app.setStatus(2);
        PowerMockito.when(appDao.getById(Mockito.anyLong())).thenReturn(app);
        App result = appServiceImpl.getById(2L);
        
        Assert.assertNotNull(result);
    }
    
    /**
     * 测试应用管理-应用删除接口
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jul 14, 2017 9:46:58 AM
     */
    @Test(expected = BizException.class)
    public void testDelete() throws Exception {
        PowerMockito.when(appDao.getStatusById(Mockito.anyLong())).thenReturn(1);
        PowerMockito.when(MessageUtil.getMessage(anyString())).thenReturn("error");
        
        PowerMockito.doNothing().when(appDao).delete(Mockito.anyLong());
        
        appServiceImpl.delete(2L);
    }
    
    /**
     * 测试应用管理-应用删除接口
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jul 14, 2017 9:46:58 AM
     */
    @Test
    public void testDelete1() throws Exception {
        PowerMockito.when(appDao.getStatusById(Mockito.anyLong())).thenReturn(null);
        PowerMockito.doNothing().when(appDao).delete(Mockito.anyLong());
        appServiceImpl.delete(2L);
    }
    
    /**
     * 测试应用管理-应用删除接口
     * @throws Exception 异常
     * @author 季振宇  
     * @date Jul 14, 2017 9:46:58 AM
     */
    @Test
    public void testDelete2() throws Exception {
        PowerMockito.when(appDao.getStatusById(Mockito.anyLong())).thenReturn(9);
        PowerMockito.doNothing().when(appDao).delete(Mockito.anyLong());
        appServiceImpl.delete(2L);
    }
}

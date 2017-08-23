package com.awifi.np.biz.appsrv.app.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import com.awifi.np.biz.appsrv.app.dao.AppDao;
import com.awifi.np.biz.appsrv.app.model.App;
import com.awifi.np.biz.appsrv.app.service.impl.AppServiceImpl;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017年7月12日 上午10:58:16
 * 创建作者：许尚敏
 * 文件名称：AppServiceTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ AppDao.class })
public class AppServiceTest {
    /**
     * appService服务层
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
    }
    
    /**
     * 应用管理—应用添加接口
     * @author 许尚敏
     * @date 2017年7月12日 上午11:10:22
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
     * @date 2017年7月12日 上午11:15:22
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

}

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月13日 下午4:22:38
* 创建作者：伍恰
* 文件名称：FitapproServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.fitappro.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubEntity;
import com.awifi.np.biz.api.client.dbcenter.fatap.util.DeviceQueryClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.devsrv.common.MockBase;
@PrepareForTest({DeviceClient.class,DeviceQueryClient.class})
public class FitapproServiceImplTest extends MockBase{
    /**
     * 被测试类
     */
    @InjectMocks
    private FitapproServiceImpl fitapproServiceImpl;
    /**
     * 初始化
     * @author 伍恰  
     * @date 2017年6月13日 下午4:28:03
     */
    @Before
    public void before() {
        PowerMockito.mockStatic(DeviceQueryClient.class);
        PowerMockito.mockStatic(DeviceClient.class);
    }
    /**
     * 依据条件查询项目型瘦ap类型设备
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年6月13日 下午4:30:17
     */
    @Test
    public void testGetFitapproList() throws Exception {
        Page<CenterPubEntity> page = new Page<>();
        page.setPageNo(1);
        page.setPageSize(10);
        CenterPubEntity entity = new CenterPubEntity();
        entity.setMacAddr("A23123B24149");
        SessionUser sessionUser = new SessionUser();
        entity.setProvince(1);
        entity.setCity(1);
        entity.setCounty(1);
        entity.setParEntityName("ad");
        fitapproServiceImpl.getFitapproList(page , entity , sessionUser);
        entity.setProvince(null);
        entity.setCity(null);
        entity.setCounty(null);
        sessionUser.setProvinceId(1L);
        sessionUser.setCityId(1L);
        sessionUser.setAreaId(1L);
        fitapproServiceImpl.getFitapproList(page , entity , sessionUser);
    }
    /**
     * 依据id集合删除项目型瘦ap设备
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年6月13日 下午4:33:29
     */
    @Test
    public void testDeleteFitapproList() throws Exception {
        String[] ids = {"1","2"};
        fitapproServiceImpl.deleteFitapproList(ids);
    }
    /**
     * 依据id获取项目型瘦ap设备
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年6月13日 下午4:33:39
     */
    @Test
    public void testGetFitapproById() throws Exception {
        fitapproServiceImpl.getFitapproById("1");
    }

}

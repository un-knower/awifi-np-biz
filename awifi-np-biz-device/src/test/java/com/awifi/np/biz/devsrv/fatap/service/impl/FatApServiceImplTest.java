package com.awifi.np.biz.devsrv.fatap.service.impl;

import java.util.Map;
import static org.mockito.Matchers.anyObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubEntity;
import com.awifi.np.biz.api.client.dbcenter.fatap.util.DeviceQueryClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.enums.DevType;
import com.awifi.np.biz.common.enums.FlowSts;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.devsrv.common.MockBase;
/**
 * 
 * @ClassName: FatApServiceImplTest
 * @Description: 定制终端 服务类 单元测试
 * @author wuqia
 * @date 2017年6月12日 下午3:49:28
 */
@SuppressWarnings("rawtypes")
@PrepareForTest({DeviceQueryClient.class, DeviceClient.class})
public class FatApServiceImplTest extends MockBase {
    /**
     * 被测试类
     */
    @InjectMocks
    private FatApServiceImpl fatApServiceImpl;

    /** 初始化 */
    @Before
    public void before() {
        PowerMockito.mockStatic(DeviceQueryClient.class);
        PowerMockito.mockStatic(DeviceClient.class);
    }
    /**
     * @Title: testGetEmsDevBasePassedFatShowList
     * @Description: 定制终端信息查询
     * @throws 
     * @data  2017年6月12日 下午3:44:39
     * @author wuqia
     * @throws Exception 异常
     */
    @Test
    public void testGetEmsDevBasePassedFatShow() throws Exception {
        CenterPubEntity centerPubEntity = new CenterPubEntity();
        String createDateB = "2017-03-28";
        String createDateE = "2017-03-28";
        Page page = new Page<>();
        page.setPageNo(1);
        page.setPageSize(10);
        SessionUser user = new SessionUser();
        PowerMockito
                .when(DeviceQueryClient.queryEntityCountByParam(anyObject()))
                .thenReturn(10);
        page = fatApServiceImpl.getEmsDevBasePassedFatShowList(centerPubEntity,
                createDateB, createDateE, page, user);
        Assert.assertNotNull(page);
    }
    /**
     * 
     * @Title: testGetFatapMap
     * @Description: 将查询条件转化为map
     * @throws 
     * @data  2017年6月12日 下午4:05:03
     * @author wuqia
     */
    @Test
    public void testGetFatapMap() {
        CenterPubEntity centerPubEntity = new CenterPubEntity();
        String createDateB = "2017-03-28";
        String createDateE = "2017-03-28";
        centerPubEntity.setProvince(1);
        centerPubEntity.setCounty(1);
        centerPubEntity.setCity(1);
        centerPubEntity.setBatchNum("123");
        centerPubEntity.setMacAddr("A23123F23137");
        centerPubEntity.setCorporation("123");
        centerPubEntity.setModel("123");
        centerPubEntity.setFlowSts(FlowSts.waitReview.getValue());
        Page page = new Page<>();
        page.setPageNo(1);
        page.setPageSize(10);
        SessionUser user = new SessionUser();
        Map<String, Object> map = fatApServiceImpl.getFatapMap(centerPubEntity,
                createDateB, createDateE, page, user);
        Assert.assertNotNull(map);
        centerPubEntity.setProvince(null);
        centerPubEntity.setCounty(null);
        centerPubEntity.setCity(null);
        user.setProvinceId(1L);
        user.setCityId(1L);
        user.setAreaId(1L);
        centerPubEntity.setEntityType(DevType.fatap.getValue());
        Map<String, Object> map1 = fatApServiceImpl.getFatapMap(centerPubEntity,
                createDateB, createDateE, page, user);
        Assert.assertNotNull(map1);
    }
    /**
     * 
     * @Title: testGetBaseMap
     * @Description: 得到查询的最基本的条件
     * @throws 
     * @data  2017年6月12日 下午4:05:10
     * @author wuqia
     */
    @Test
    public void testGetBaseMap() {
        Map<String, Object> map = fatApServiceImpl.getBaseMap();
        Assert.assertNotNull(map);
    }
    /**
     * 
     * @Title: testUpdateFlowStsByIds
     * @Description: 根据ids更新定制终端审核情况
     * @throws 
     * @data  2017年6月12日 下午4:05:39
     * @author wuqia
     * @throws Exception 异常
     */
    @Test
    public void testUpdateFlowStsByIds() throws Exception {
        String[] ids = {"1"};
        Integer status = 1;
        String userName = "admin";
        String remark = "test";
        // PowerMockito.when(DeviceClient.updateFlowStsByIds(anyObject()));
        fatApServiceImpl.updateFlowStsByIds(ids, status, userName, remark);;
    }
    /**
     * 
     * @Title: testDeleteAwifiFatAPByIds
     * @Description: 根据ids删除定制终端信息
     * @throws Exception
     *             参数描述
     * @throws 
     * @data  2017年6月12日 下午4:07:43
     * @author wuqia
     */
    @Test
    public void testDeleteAwifiFatAPByIds() throws Exception {
        String[] ids = {"1"};
        fatApServiceImpl.deleteAwifiFatAPByIds(ids);;
    }
    /**
     * 
     * @Title: testUpdateFatApById
     * @Description: 根据ids更新定制终端信息(除了审核状态)
     * @throws Exception
     *             参数描述
     * @throws 
     * @data  2017年6月12日 下午4:08:52
     * @author wuqia
     */
    @Test
    public void testUpdateFatApById() throws Exception {
        CenterPubEntity entity = new CenterPubEntity();
        entity.setIpAddr("ip");
        entity.setEntityName("name");
        entity.setEntityType(11);
        fatApServiceImpl.updateFatApById(entity);
        Assert.assertNotNull(entity);
    }
    /**
     * 
     * @Title: testQueryEntityInfoById
     * @Description: 根据id查找设备信息
     * @throws Exception
     *             参数描述
     * @throws 
     * @data  2017年6月12日 下午4:09:59
     * @author wuqia
     */
    @Test
    public void testQueryEntityInfoById() throws Exception {
        String id = "1";
        CenterPubEntity entity = new CenterPubEntity();
        entity.setIpAddr("ip");
        entity.setEntityName("name");
        entity.setEntityType(11);
        PowerMockito.when(DeviceQueryClient.queryEntityInfoById(anyObject()))
                .thenReturn(entity);
        CenterPubEntity resultentity = fatApServiceImpl.queryEntityInfoById(id);
        Assert.assertNotNull(resultentity);
    }

}

package com.awifi.np.biz.devsrv.fatap.service.impl;

import static org.mockito.Matchers.anyObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.alibaba.fastjson.JSONArray;
import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubPlatform;
import com.awifi.np.biz.api.client.dbcenter.location.util.LocationClient;
import com.awifi.np.biz.api.client.dbcenter.platform.client.PlatFormClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.RegexUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.devsrv.common.MockBase;
import com.awifi.np.biz.devsrv.fatap.util.FatapUtil;
@PrepareForTest({JSONArray.class, FatapUtil.class,PlatFormClient.class,MessageUtil.class,RegexUtil.class,ValidUtil.class,LocationClient.class})
public class PlatFormServiceImplTest extends MockBase {
    /**
     * 被测试类
     */
    @InjectMocks
    private PlatFormServiceImpl platFormServiceImpl;
    /**
     * 初始化
     */
    @Before
    public void before() {
        PowerMockito.mockStatic(FatapUtil.class);
        PowerMockito.mockStatic(PlatFormClient.class);
        PowerMockito.mockStatic(MessageUtil.class);
        PowerMockito.mockStatic(RegexUtil.class);
        PowerMockito.mockStatic(ValidUtil.class);
        PowerMockito.mockStatic(LocationClient.class);
        PowerMockito.mockStatic(JSONArray.class);
    }
    
    /**
     * @Title: testListPlatForm
     * @Description: 根据条件查询平台数量
     * @throws Exception
     *             参数描述
     * @throws 
     * @data  2017年6月12日 下午1:55:48
     * @author wuqia
     */
    @Test
    public void testListPlatForm() throws Exception {
        Integer pageNo = 1;
        Integer pageSize = 10;
        String platformName = "中文";
        SessionUser sessionUser = new SessionUser();
        sessionUser.setProvinceId(1L);
        sessionUser.setCityId(1L);
        sessionUser.setAreaId(1L);
        PowerMockito.when(PlatFormClient.queryPlatformCountByParam(anyObject()))
                .thenReturn(10);
        List<CenterPubPlatform> list = new ArrayList<>();
        CenterPubPlatform centerPubPlatform = new CenterPubPlatform();
        centerPubPlatform.setProvince(16);
        centerPubPlatform.setCounty(1813);
        centerPubPlatform.setCity(220);
        list.add(centerPubPlatform);
        Map<String, Object> result = new HashMap<>();
        result.put("rs", list);
        PowerMockito.when(PlatFormClient.queryPlatformListByParam(anyObject())).thenReturn(result);
        
        Page<CenterPubPlatform> pageList = platFormServiceImpl
                .listPlatForm(pageNo, pageSize, platformName, sessionUser);
        Assert.assertNotNull(pageList);
    }
    
    /**
     * 
     * @Title: testQueryPlatformById
     * @Description: 根据id查找省分平台详细信息
     * @throws Exception
     *             参数描述
     * @throws 
     * @data  2017年6月12日 下午2:00:28
     * @author wuqia
     */
    @Test
    public void testQueryPlatformById() throws Exception {
        String id = "1";
        Map<String, Object> result = new HashMap<String, Object>();
        CenterPubPlatform centerPubPlatform = new CenterPubPlatform();
        centerPubPlatform.setProvince(16);
        centerPubPlatform.setCounty(1813);
        centerPubPlatform.setCity(220);
        result.put("rs", JsonUtil.toJson(centerPubPlatform));
        PowerMockito.when(PlatFormClient.queryPlatformById(anyObject())).thenReturn(result);
        Map<String, Object> resultMap = platFormServiceImpl
                .queryPlatformById(id);
        Assert.assertNotNull(resultMap);
    }
    
    /**
     * @Title: testEditPlatForm
     * @Description: 省分平台编辑
     * @throws Exception
     *             参数描述
     * @throws 
     * @data  2017年6月12日 下午2:01:14
     * @author wuqia
     */
    @Test
    public void testEditPlatForm() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("platformType", "0");
        params.put("portalDomain", "www.51awifi.com");
        params.put("portalPort", 8080);
        params.put("authDomain", "www.51awifi.com");
        params.put("authPort", 8080);
        params.put("platformDomain", "www.51awifi.com");
        params.put("platformPort", 8080);
        params.put("devBusIp", "www.51awifi.com");
        params.put("administrantPhone","17788583288");
        params.put("id", "0");
        CenterPubPlatform centerPubPlatform = new CenterPubPlatform();
        centerPubPlatform.setProvince(16);
        centerPubPlatform.setCounty(1813);
        centerPubPlatform.setCity(220);
        centerPubPlatform.setId(0);
        centerPubPlatform.setPlatformType("0");
        List<CenterPubPlatform> list = new ArrayList<>();
        list.add(centerPubPlatform);
        Map<String, Object> result = new HashMap<>();
        result.put("rs", list);
        PowerMockito.when(PlatFormClient.queryPlatformListByParam(anyObject())).thenReturn(result);
        PowerMockito.when(FatapUtil.isUrl(anyObject())).thenReturn(true);
        platFormServiceImpl.editPlatForm(params);
    }
    
    /**
     * @Title: testAddPlatform
     * @Description: 省分平台新增
     * @throws 
     * @data  2017年6月12日 下午2:10:11
     * @author wuqia
     * @throws Exception 
     */
    @Test
    public void testAddPlatform() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("id", 1);
        params.put("platformType", "0");
        params.put("portalDomain", "www.51awifi.com");
        params.put("portalPort", 8080);
        params.put("authDomain", "www.51awifi.com");
        params.put("authPort", 8080);
        params.put("platformDomain", "www.51awifi.com");
        params.put("platformPort", 8080);
        params.put("administrantPhone","17788583288");
        PowerMockito.when(FatapUtil.isUrl(anyObject())).thenReturn(true);
        platFormServiceImpl.addPlatform(params);
    }
    
    /**
     * 根据id删除省分平台信息
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年6月13日 下午4:14:12
     */
    @Test
    public void testDeletePlatform() throws Exception {
        Long id = 1L;
        platFormServiceImpl.deletePlatform(id);
    }
    
    /**
     * 
     * @author 伍恰  
     * @date 2017年6月13日 下午4:14:27
     */
    @Test
    public void testSuccessMsg() {
        Map<String, Object> map = platFormServiceImpl.successMsg(new Object());
        Assert.assertNotNull(map);
    }

}

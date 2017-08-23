package com.awifi.np.biz.toe.portal.site.service.impl;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.mock.web.MockHttpServletRequest;
import com.awifi.np.biz.api.client.dbcenter.location.util.LocationClient;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.security.permission.util.PermissionUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.IOUtil;
import com.awifi.np.biz.toe.admin.strategy.service.StrategyService;
import com.awifi.np.biz.toe.portal.component.model.Component;
import com.awifi.np.biz.toe.portal.component.service.ComponentService;
import com.awifi.np.biz.toe.portal.site.dao.SiteDao;
import com.awifi.np.biz.toe.portal.site.model.Site;
import com.awifi.np.biz.toe.portal.site.model.SitePage;
import com.awifi.np.biz.toe.portal.site.model.SitePageComponent;


/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017年6月22日 下午7:06:20
 * 创建作者：许尚敏
 * 文件名称：SiteServiceImplTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({SysConfigUtil.class, StringUtils.class, LocationClient.class, PermissionUtil.class,CastUtil.class,MerchantClient.class,IOUtil.class,RedisUtil.class})
public class SiteServiceImplTest {
    /**被测试类*/
    @InjectMocks
    private SiteServiceImpl siteServiceImpl;
    
    /**短信配置服务*/
    @Mock(name = "siteDao")
    private SiteDao siteDao;
    
    /**策略*/
    @Mock(name = "strategyService")
    private StrategyService strategyService;
    
    /**组件*/
    @Mock(name = "componentService")
    private ComponentService componentService;

    /**请求*/
    private MockHttpServletRequest httpRequest;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        httpRequest = new MockHttpServletRequest();
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(StringUtils.class);
        PowerMockito.mockStatic(LocationClient.class);
        PowerMockito.mockStatic(PermissionUtil.class);
        PowerMockito.mockStatic(CastUtil.class);
        PowerMockito.mockStatic(MerchantClient.class);
        PowerMockito.mockStatic(IOUtil.class);
        PowerMockito.mockStatic(RedisUtil.class);
    }
    
    /***
     * 站点列表测试
     * @author 许尚敏  
     * @date 2017年6月23日 下午3:33:29
     */
    @Test
    public void testGetDefaultListByParam() {
        Page<Site> page = new Page<Site>();
        page.setBegin(1);
        page.setPageSize(10);
        List<Site> siteList = new ArrayList<Site>();
        Site site = new Site();
        site.setThumb("aa");
        siteList.add(site);
        PowerMockito.when(siteDao.getDefaultCountByParam(anyObject())).thenReturn(1);
        PowerMockito.when(siteDao.getDefaultListByParam(anyObject(),anyObject(),anyObject())).thenReturn(siteList);
        PowerMockito.when(SysConfigUtil.getParamValue(anyObject())).thenReturn("bbb");
        siteServiceImpl.getDefaultListByParam(page, "a");
    }
    
    /***
     * 站点列表测试
     * @author 许尚敏  
     * @date 2017年6月23日 下午3:33:29
     */
    @Test
    public void testGetDefaultListByParam2() {
        Page<Site> page = new Page<Site>();
        page.setBegin(1);
        page.setPageSize(10);
        List<Site> siteList = new ArrayList<Site>();
        Site site = new Site();
        site.setThumb("aa");
        siteList.add(site);
        PowerMockito.when(siteDao.getDefaultCountByParam(anyObject())).thenReturn(0);
        PowerMockito.when(siteDao.getDefaultListByParam(anyObject(),anyObject(),anyObject())).thenReturn(siteList);
        PowerMockito.when(SysConfigUtil.getParamValue(anyObject())).thenReturn("bbb");
        siteServiceImpl.getDefaultListByParam(page, "a");
    }

    /***
     * 地区站点列表测试
     * @author 许尚敏  
     * @throws Exception 
     * @date 2017年6月23日 下午3:33:29
     */
    @Test
    public void testGetLocationListByParam() throws Exception{
        Page<Site> page = new Page<Site>();
        page.setBegin(1);
        page.setPageSize(10);
        List<Site> siteList = new ArrayList<Site>();
        Site site = new Site();
        site.setThumb("aa");
        site.setProvinceId(1L);
        site.setCityId(1L);
        siteList.add(site);
        PowerMockito.when(StringUtils.isBlank(anyObject())).thenReturn(true);
        PowerMockito.when(LocationClient.getByIdAndParam(anyObject(), anyObject())).thenReturn("aaaa");
        PowerMockito.when(siteDao.getLocationCountByParam(anyObject(),anyObject(),anyObject())).thenReturn(1);
        PowerMockito.when(siteDao.getLocationListByParam(anyObject(),anyObject(),anyObject(),anyObject(),anyObject())).thenReturn(siteList);
        PowerMockito.when(SysConfigUtil.getParamValue(anyObject())).thenReturn("bbb");
        siteServiceImpl.getLocationListByParam(page, "a", 1L, 1L);
    }

    /***
     * 行业站点列表测试
     * @author 许尚敏  
     * @throws Exception 
     * @date 2017年6月23日 下午3:33:29
     */
    @Test
    public void testGetIndustryListByParam() throws Exception {
        Page<Site> page = new Page<Site>();
        page.setBegin(1);
        page.setPageSize(10);
        List<Site> siteList = new ArrayList<Site>();
        Site site = new Site();
        site.setThumb("aa");
        site.setPriIndustryCode("ee");
        site.setSecIndustryCode("aa");
        site.setCityId(1L);
        siteList.add(site);
        PowerMockito.when(siteDao.getIndustryCountByParam(anyObject(),anyObject(),anyObject())).thenReturn(1);
        PowerMockito.when(StringUtils.isBlank(anyString())).thenReturn(true);
        PowerMockito.when(StringUtils.isNotBlank(anyString())).thenReturn(true);
        PowerMockito.when(siteDao.getIndustryListByParam(anyObject(),anyObject(),anyObject(),anyObject(),anyObject())).thenReturn(siteList);
        PowerMockito.when(SysConfigUtil.getParamValue(anyObject())).thenReturn("bbb");
        siteServiceImpl.getIndustryListByParam(page, "a", "bb", "bb");
    }

    /***
     * 站点策略列表测试
     * @author 许尚敏  
     * @throws Exception 
     * @date 2017年6月26日 上午9:46:29
     */
    @Test
    public void testGetListByParam() throws Exception{
        SessionUser user = new SessionUser();
        user.setMerchantIds("aaa");
        PowerMockito.when(PermissionUtil.isMerchants(anyObject())).thenReturn(true);
        String[] vv = new String[]{};
        PowerMockito.when(CastUtil.toLongArray(vv)).thenReturn(new Long[]{1L});
        Page<Site> page = new Page<Site>();
        page.setBegin(1);
        page.setPageSize(10);
        PowerMockito.when(siteDao.getCountByParam(anyObject(),anyObject(),anyObject(),anyObject(),anyObject(),anyObject())).thenReturn(1);
        List<Site> siteList = new ArrayList<Site>();
        Site site = new Site();
        site.setThumb("aa");
        site.setPriIndustryCode("ee");
        site.setSecIndustryCode("aa");
        site.setCityId(1L);
        siteList.add(site);
        PowerMockito.when(siteDao.getListByParam(anyObject(),anyObject(),anyObject(),anyObject(),anyObject(),anyObject(),anyObject(),anyObject())).thenReturn(siteList);
        PowerMockito.when(StringUtils.isBlank(anyString())).thenReturn(true);
        PowerMockito.when(MerchantClient.getNameByIdCache(anyObject())).thenReturn("aaa");
        Map<Long,Integer> totalMap = new HashMap<Long,Integer>();
        PowerMockito.when(strategyService.getTotalBySiteIds(anyObject())).thenReturn(totalMap);
        siteServiceImpl.getListByParam(user, page, "aa", 1L, 1L, 1);
    }

    /***
     * 判断默认站点是否存在测试
     * @author 许尚敏  
     * @date 2017年6月26日 上午10:44:29
     */
    @Test
    public void testIsDefaultSiteExist() {
        PowerMockito.when(siteDao.getDefaultSiteNum()).thenReturn(1);
        siteServiceImpl.isDefaultSiteExist();
    }

    /***
     * 判断一级行业是否存在测试
     * @author 许尚敏  
     * @date 2017年6月26日 上午10:44:29
     */
    @Test
    public void testIsPriIndustrySiteExistByS() {
        PowerMockito.when(siteDao.getPriIndustrySiteNum(anyObject())).thenReturn(1);
        siteServiceImpl.isPriIndustrySiteExist("aa");
    }

    /***
     * 判断一级行业是否存在 排除自己测试
     * @author 许尚敏  
     * @date 2017年6月26日 上午10:44:29
     */
    @Test
    public void testIsPriIndustrySiteExistByLS() {
        PowerMockito.when(siteDao.getPriIndustrySiteNumById(anyObject(), anyObject())).thenReturn(1);
        siteServiceImpl.isPriIndustrySiteExist(1L, "aa");
    }

    /***
     * 判断一级行业是否存在测试
     * @author 许尚敏  
     * @date 2017年6月26日 上午10:44:29
     */
    @Test
    public void testIsSecIndustrySiteExistByS() {
        PowerMockito.when(siteDao.getSecIndustrySiteNum(anyObject())).thenReturn(1);
        siteServiceImpl.isSecIndustrySiteExist("aa");
    }

    /***
     * 判断一级行业是否存在测试
     * @author 许尚敏  
     * @date 2017年6月26日 上午10:44:29
     */
    @Test
    public void testIsSecIndustrySiteExistByLS() {
        PowerMockito.when(siteDao.getSecIndustrySiteNumById(anyObject(), anyObject())).thenReturn(1);
        siteServiceImpl.isSecIndustrySiteExist(1L, "aa");
    }

    /***
     * 判断二级行业站点是否存在 排除自己测试
     * @author 许尚敏  
     * @date 2017年6月26日 上午10:44:29
     */
    @Test
    public void testIsProvinceSiteExistByL() {
        PowerMockito.when(siteDao.getProvinceSiteNum(anyObject())).thenReturn(1);
        siteServiceImpl.isProvinceSiteExist(1L);
    }

    /***
     * 判断省站点是否存在测试
     * @author 许尚敏  
     * @date 2017年6月26日 上午11:02:29
     */
    @Test
    public void testIsProvinceSiteExistbyLL() {
        PowerMockito.when(siteDao.getProvinceSiteNumById(anyObject(), anyObject())).thenReturn(1);
        siteServiceImpl.isProvinceSiteExist(1L, 1L);
    }

    /***
     * 判断市站点是否存在测试
     * @author 许尚敏  
     * @date 2017年6月26日 上午11:02:29
     */
    @Test
    public void testIsCitySiteExistLong() {
        PowerMockito.when(siteDao.getCitySiteNum(anyObject())).thenReturn(1);
        siteServiceImpl.isCitySiteExist(1L);
    }

    /***
     * 判断市站点是否存在测试
     * @author 许尚敏  
     * @date 2017年6月26日 上午11:02:29
     */
    @Test
    public void testIsCitySiteExistByLL() {
        PowerMockito.when(siteDao.getCitySiteNumById(anyObject(), anyObject())).thenReturn(1);
        siteServiceImpl.isCitySiteExist(1L, 1L);
    }

    /***
     * 判断商户下站点名称是否存在测试
     * @author 许尚敏  
     * @date 2017年6月26日 上午11:02:29
     */
    @Test
    public void testIsSiteNameExist() {
        PowerMockito.when(siteDao.getSiteNameNum(anyObject(), anyObject())).thenReturn(1);
        siteServiceImpl.isSiteNameExist("aa", 1L);
    }

    /***
     * 添加默认站点测试
     * @author 许尚敏  
     * @throws Exception 
     * @date 2017年6月26日 上午11:17:29
     */
    @Test(expected=NullPointerException.class)
    public void testAddDefault() throws Exception{
        Map<String, Object> bodyParam = new HashMap<String, Object>();
        List<Map<String,Object>> sitePageList = new ArrayList<Map<String,Object>>();
        Map<String,Object> sitePageMap = new HashMap<String,Object>();
        sitePageMap.put("pageOperation", "add");
        sitePageMap.put("pageId", 1L);
        sitePageMap.put("pageType", "1");
        sitePageMap.put("num", 1L);
        List<Map<String, Object>> componentList = new ArrayList<Map<String,Object>>();
        Map<String,Object> componentMap = new HashMap<String,Object>();
        componentMap.put("componentOperation", "add");
        componentMap.put("componentId", 1L);
        componentMap.put("sitePageComponentId", 1L);
        componentList.add(componentMap);
        sitePageMap.put("components", componentList);
        sitePageList.add(sitePageMap);
        bodyParam.put("siteName", "siteName");
        bodyParam.put("pages", sitePageList);
        siteServiceImpl.addDefault(bodyParam, httpRequest);
    }
    
    /***
     * 添加默认站点测试
     * @author 许尚敏  
     * @throws Exception 
     * @date 2017年6月26日 上午11:17:29
     */
    @Test(expected=NullPointerException.class)
    public void testAddDefaultByEdit() throws Exception{
        Map<String, Object> bodyParam = new HashMap<String, Object>();
        List<Map<String,Object>> sitePageList = new ArrayList<Map<String,Object>>();
        Map<String,Object> sitePageMap = new HashMap<String,Object>();
        sitePageMap.put("pageOperation", "edit");
        sitePageMap.put("pageId", 1L);
        sitePageMap.put("pageType", "1");
        sitePageMap.put("num", 1L);
        List<Map<String, Object>> componentList = new ArrayList<Map<String,Object>>();
        Map<String,Object> componentMap = new HashMap<String,Object>();
        componentMap.put("componentOperation", "edit");
        componentMap.put("componentId", 1L);
        componentMap.put("sitePageComponentId", 1L);
        componentList.add(componentMap);
        sitePageMap.put("components", componentList);
        sitePageList.add(sitePageMap);
        bodyParam.put("siteName", "siteName");
        bodyParam.put("pages", sitePageList);
        siteServiceImpl.addDefault(bodyParam, httpRequest);
    }
    
    /***
     * 添加默认站点测试
     * @author 许尚敏  
     * @throws Exception 
     * @date 2017年6月26日 上午11:17:29
     */
    @Test
    public void testAddDefaultByRemove() throws Exception{
        Map<String, Object> bodyParam = new HashMap<String, Object>();
        List<Map<String,Object>> sitePageList = new ArrayList<Map<String,Object>>();
        Map<String,Object> sitePageMap = new HashMap<String,Object>();
        sitePageMap.put("pageOperation", "remove");
        sitePageMap.put("pageId", 1L);
        sitePageMap.put("pageType", "1");
        sitePageMap.put("num", 1L);
        List<Map<String, Object>> componentList = new ArrayList<Map<String,Object>>();
        Map<String,Object> componentMap = new HashMap<String,Object>();
        componentMap.put("componentOperation", "remove");
        componentMap.put("componentId", 1L);
        componentMap.put("sitePageComponentId", 1L);
        componentList.add(componentMap);
        sitePageMap.put("components", componentList);
        sitePageList.add(sitePageMap);
        bodyParam.put("siteName", "siteName");
        bodyParam.put("pages", sitePageList);
        siteServiceImpl.addDefault(bodyParam, httpRequest);
    }

    /***
     * 添加行业站点测试
     * @author 许尚敏  
     * @throws Exception 
     * @date 2017年6月26日 下午2:36:29
     */
    @Test(expected=NullPointerException.class)
    public void testAddIndustry() throws Exception{
        Map<String, Object> bodyParam = new HashMap<String, Object>();
        bodyParam.put("priIndustryCode", "priIndustryCode");
        bodyParam.put("secIndustryCode", "secIndustryCode");
        bodyParam.put("siteName", "siteName");
        List<Map<String,Object>> sitePageList = new ArrayList<Map<String,Object>>();
        Map<String,Object> sitePageMap = new HashMap<String,Object>();
        sitePageMap.put("pageOperation", "add");
        sitePageMap.put("pageId", 1L);
        sitePageMap.put("pageType", "1");
        sitePageMap.put("num", 1L);
        List<Map<String, Object>> componentList = new ArrayList<Map<String,Object>>();
        Map<String,Object> componentMap = new HashMap<String,Object>();
        componentMap.put("componentOperation", "add");
        componentMap.put("componentId", 1L);
        componentMap.put("sitePageComponentId", 1L);
        componentMap.put("json", "");
        componentMap.put("orderNo", 1);
        componentList.add(componentMap);
        sitePageMap.put("components", componentList);
        sitePageList.add(sitePageMap);
        bodyParam.put("pages", sitePageList);
        Component component = new Component();
        component.setCode("code");
        PowerMockito.when(componentService.getById(anyObject())).thenReturn(component);
        Mockito.doNothing().when(siteDao).updateCascadeLabel(anyObject(), anyObject());
        siteServiceImpl.addIndustry(bodyParam, httpRequest);
    }

    /***
     * 添加地区站点测试
     * @author 许尚敏  
     * @throws Exception 
     * @date 2017年6月26日 下午3:36:29
     */
    @Test(expected=NullPointerException.class)
    public void testAddLocation() throws Exception{
        Map<String, Object> bodyParam = new HashMap<String, Object>();
        bodyParam.put("provinceId", 1L);
        bodyParam.put("cityId", 1L);
        bodyParam.put("siteName", "siteName");
        List<Map<String,Object>> sitePageList = new ArrayList<Map<String,Object>>();
        Map<String,Object> sitePageMap = new HashMap<String,Object>();
        sitePageMap.put("pageOperation", "add");
        sitePageMap.put("pageId", 1L);
        sitePageMap.put("pageType", "1");
        sitePageMap.put("num", 1L);
        List<Map<String, Object>> componentList = new ArrayList<Map<String,Object>>();
        Map<String,Object> componentMap = new HashMap<String,Object>();
        componentMap.put("componentOperation", "add");
        componentMap.put("componentId", 1L);
        componentMap.put("sitePageComponentId", 1L);
        componentMap.put("json", "");
        componentMap.put("orderNo", 1);
        componentList.add(componentMap);
        sitePageMap.put("components", componentList);
        sitePageList.add(sitePageMap);
        bodyParam.put("pages", sitePageList);
        Component component = new Component();
        component.setCode("code");
        PowerMockito.when(componentService.getById(anyObject())).thenReturn(component);
        Mockito.doNothing().when(siteDao).updateCascadeLabel(anyObject(), anyObject());
        siteServiceImpl.addLocation(bodyParam, httpRequest);
    }

    /***
     * 添加站点测试
     * @author 许尚敏  
     * @throws Exception 
     * @date 2017年6月26日 下午3:36:29
     */
    @Test(expected=NullPointerException.class)
    public void testAdd() throws Exception{
        Map<String, Object> bodyParam = new HashMap<String, Object>();
        bodyParam.put("cascadeLabel", "cascadeLabel");
        bodyParam.put("merchantId", 1L);
        bodyParam.put("siteName", "siteName");
        List<Map<String,Object>> sitePageList = new ArrayList<Map<String,Object>>();
        Map<String,Object> sitePageMap = new HashMap<String,Object>();
        sitePageMap.put("pageOperation", "add");
        sitePageMap.put("pageId", 1L);
        sitePageMap.put("pageType", "1");
        sitePageMap.put("num", 1L);
        List<Map<String, Object>> componentList = new ArrayList<Map<String,Object>>();
        Map<String,Object> componentMap = new HashMap<String,Object>();
        componentMap.put("componentOperation", "add");
        componentMap.put("componentId", 1L);
        componentMap.put("sitePageComponentId", 1L);
        componentMap.put("json", "");
        componentMap.put("orderNo", 1);
        componentList.add(componentMap);
        sitePageMap.put("components", componentList);
        sitePageList.add(sitePageMap);
        bodyParam.put("pages", sitePageList);
        Component component = new Component();
        component.setCode("code");
        PowerMockito.when(componentService.getById(anyObject())).thenReturn(component);
        Mockito.doNothing().when(siteDao).updateCascadeLabel(anyObject(), anyObject());
        siteServiceImpl.add(bodyParam, httpRequest);
    }

    /***
     * 站点页面内容保存、更新、删除测试
     * @author 许尚敏  
     * @throws Exception 
     * @date 2017年6月26日 下午3:36:29
     */
    @Test(expected=NullPointerException.class)
    public void testDealPage() throws Exception {
        List<Map<String,Object>> sitePageList = new ArrayList<Map<String,Object>>();
        Map<String,Object> sitePageMap = new HashMap<String,Object>();
        sitePageMap.put("pageOperation", "add");
        sitePageMap.put("pageId", 1L);
        sitePageMap.put("pageType", "1");
        sitePageMap.put("num", 1L);
        List<Map<String, Object>> componentList = new ArrayList<Map<String,Object>>();
        Map<String,Object> componentMap = new HashMap<String,Object>();
        componentMap.put("componentOperation", "add");
        componentMap.put("componentId", 1L);
        componentMap.put("sitePageComponentId", 1L);
        componentMap.put("json", "");
        componentMap.put("orderNo", 1);
        componentList.add(componentMap);
        sitePageMap.put("components", componentList);
        sitePageList.add(sitePageMap);
        siteServiceImpl.dealPage(1L, sitePageList, httpRequest);
    }

    /***
     * 更新站点状态测试
     * @author 许尚敏  
     * @date 2017年6月26日 下午3:36:29
     */
    @Test
    public void testUpdateStatusById() {
        Mockito.doNothing().when(siteDao).updateStatusById(anyObject(),anyObject());
        siteServiceImpl.updateStatusById(1L, 1);
    }

    /***
     * 编辑默认站点测试
     * @author 许尚敏  
     * @throws Exception 
     * @date 2017年6月26日 下午3:36:29
     */
    @Test(expected=NullPointerException.class)
    public void testUpdateDefault() throws Exception {
        Map<String, Object> bodyParam = new HashMap<String, Object>();
        bodyParam.put("siteName", "siteName");
        List<Map<String,Object>> sitePageList = new ArrayList<Map<String,Object>>();
        Map<String,Object> sitePageMap = new HashMap<String,Object>();
        sitePageMap.put("pageOperation", "add");
        sitePageMap.put("pageId", 1L);
        sitePageMap.put("pageType", "1");
        sitePageMap.put("num", 1L);
        List<Map<String, Object>> componentList = new ArrayList<Map<String,Object>>();
        Map<String,Object> componentMap = new HashMap<String,Object>();
        componentMap.put("componentOperation", "add");
        componentMap.put("componentId", 1L);
        componentMap.put("sitePageComponentId", 1L);
        componentMap.put("json", "");
        componentMap.put("orderNo", 1);
        componentList.add(componentMap);
        sitePageMap.put("components", componentList);
        sitePageList.add(sitePageMap);
        bodyParam.put("pages", sitePageList);
        Component component = new Component();
        component.setCode("code");
        PowerMockito.when(componentService.getById(anyObject())).thenReturn(component);
        Mockito.doNothing().when(siteDao).updateCascadeLabel(anyObject(), anyObject());
        siteServiceImpl.updateDefault(1L, bodyParam, httpRequest);
    }

    /***
     * 编辑地区站点测试
     * @author 许尚敏  
     * @throws Exception 
     * @date 2017年6月26日 下午4:02:29
     */
    @Test(expected=NullPointerException.class)
    public void testUpdateLocation() throws Exception{
        Map<String, Object> bodyParam = new HashMap<String, Object>();
        bodyParam.put("provinceId", 1L);
        bodyParam.put("cityId", 1L);
        bodyParam.put("siteName", "siteName");
        List<Map<String,Object>> sitePageList = new ArrayList<Map<String,Object>>();
        Map<String,Object> sitePageMap = new HashMap<String,Object>();
        sitePageMap.put("pageOperation", "add");
        sitePageMap.put("pageId", 1L);
        sitePageMap.put("pageType", "1");
        sitePageMap.put("num", 1L);
        List<Map<String, Object>> componentList = new ArrayList<Map<String,Object>>();
        Map<String,Object> componentMap = new HashMap<String,Object>();
        componentMap.put("componentOperation", "add");
        componentMap.put("componentId", 1L);
        componentMap.put("sitePageComponentId", 1L);
        componentMap.put("json", "");
        componentMap.put("orderNo", 1);
        componentList.add(componentMap);
        sitePageMap.put("components", componentList);
        sitePageList.add(sitePageMap);
        bodyParam.put("pages", sitePageList);
        Component component = new Component();
        component.setCode("code");
        PowerMockito.when(componentService.getById(anyObject())).thenReturn(component);
        Mockito.doNothing().when(siteDao).updateCascadeLabel(anyObject(), anyObject());
        siteServiceImpl.updateLocation(1L, bodyParam, httpRequest);
    }

    /***
     * 编辑行业站点测试
     * @author 许尚敏  
     * @throws Exception 
     * @date 2017年6月26日 下午4:02:29
     */
    @Test(expected=NullPointerException.class)
    public void testUpdateIndustry() throws Exception{
        Map<String, Object> bodyParam = new HashMap<String, Object>();
        bodyParam.put("priIndustryCode", "priIndustryCode");
        bodyParam.put("secIndustryCode", "secIndustryCode");
        bodyParam.put("siteName", "siteName");
        List<Map<String,Object>> sitePageList = new ArrayList<Map<String,Object>>();
        Map<String,Object> sitePageMap = new HashMap<String,Object>();
        sitePageMap.put("pageOperation", "add");
        sitePageMap.put("pageId", 1L);
        sitePageMap.put("pageType", "1");
        sitePageMap.put("num", 1L);
        List<Map<String, Object>> componentList = new ArrayList<Map<String,Object>>();
        Map<String,Object> componentMap = new HashMap<String,Object>();
        componentMap.put("componentOperation", "add");
        componentMap.put("componentId", 1L);
        componentMap.put("sitePageComponentId", 1L);
        componentMap.put("json", "");
        componentMap.put("orderNo", 1);
        componentList.add(componentMap);
        sitePageMap.put("components", componentList);
        sitePageList.add(sitePageMap);
        bodyParam.put("pages", sitePageList);
        Component component = new Component();
        component.setCode("code");
        PowerMockito.when(componentService.getById(anyObject())).thenReturn(component);
        Mockito.doNothing().when(siteDao).updateCascadeLabel(anyObject(), anyObject());
        siteServiceImpl.updateIndustry(1L, bodyParam, httpRequest);
    }

    /***
     * 编辑站点测试
     * @author 许尚敏  
     * @throws Exception 
     * @date 2017年6月26日 下午4:02:29
     */
    @Test(expected=NullPointerException.class)
    public void testUpdate() throws Exception{
        Map<String, Object> bodyParam = new HashMap<String, Object>();
        bodyParam.put("priIndustryCode", "priIndustryCode");
        bodyParam.put("secIndustryCode", "secIndustryCode");
        bodyParam.put("siteName", "siteName");
        List<Map<String,Object>> sitePageList = new ArrayList<Map<String,Object>>();
        Map<String,Object> sitePageMap = new HashMap<String,Object>();
        sitePageMap.put("pageOperation", "add");
        sitePageMap.put("pageId", 1L);
        sitePageMap.put("pageType", "1");
        sitePageMap.put("num", 1L);
        List<Map<String, Object>> componentList = new ArrayList<Map<String,Object>>();
        Map<String,Object> componentMap = new HashMap<String,Object>();
        componentMap.put("componentOperation", "add");
        componentMap.put("componentId", 1L);
        componentMap.put("sitePageComponentId", 1L);
        componentMap.put("json", "");
        componentMap.put("orderNo", 1);
        componentList.add(componentMap);
        sitePageMap.put("components", componentList);
        sitePageList.add(sitePageMap);
        bodyParam.put("pages", sitePageList);
        Component component = new Component();
        component.setCode("code");
        PowerMockito.when(componentService.getById(anyObject())).thenReturn(component);
        Mockito.doNothing().when(siteDao).updateCascadeLabel(anyObject(), anyObject());
        siteServiceImpl.update(1L, bodyParam, httpRequest);
    }

    /***
     * 删除站点测试
     * @author 许尚敏  
     * @date 2017年6月26日 下午4:17:29
     */
    @Test
    public void testDelete() {
        Mockito.doNothing().when(siteDao).delete(anyObject());
        siteServiceImpl.delete(1L);
    }

    /***
     * 站点详情测试
     * @author 许尚敏  
     * @throws Exception 
     * @date 2017年6月26日 下午4:17:29
     */
    @Test
    public void testGetById() throws Exception{
        Site site = new Site();
        site.setMerchantId(1L);
        PowerMockito.when(siteDao.getById(anyObject())).thenReturn(site);
        PowerMockito.when(MerchantClient.getNameByIdCache(anyObject())).thenReturn("aaa");
        List<SitePage> sitePageList = new ArrayList<SitePage>();
        SitePage sitePage = new SitePage();
        sitePage.setPageId(1L);
        sitePageList.add(sitePage);
        List<SitePageComponent> spcList = new ArrayList<SitePageComponent>();
        PowerMockito.when(siteDao.getSitePageComponentList(anyObject())).thenReturn(spcList);
        PowerMockito.when(siteDao.getSitePageList(anyObject())).thenReturn(sitePageList);
        siteServiceImpl.getById(1L);
    }

    /***
     * 通过站点id获取商户信息测试
     * @author 许尚敏  
     * @date 2017年6月26日 下午4:51:29
     */
    @Test
    public void testGetMerchantById() {
        //Mockito.doNothing().when(siteDao).getMerchantById(anyObject());
        siteServiceImpl.getMerchantById(1L);
    }

    /***
     * 获取区域站点id测试
     * @author 许尚敏  
     * @date 2017年6月26日 下午5:18:29
     */
    @Test
    public void testGetLocationSitIdCache() {
        PowerMockito.when(RedisUtil.get(anyObject())).thenReturn("1");
        PowerMockito.when(StringUtils.isNotBlank(anyObject())).thenReturn(true);
        siteServiceImpl.getLocationSitIdCache(1L, 1L);
    }

    /***
     * 获取行业站点id测试
     * @author 许尚敏  
     * @date 2017年6月26日 下午7:18:29
     */
    @Test
    public void testGetIndustrySitIdCache() {
        PowerMockito.when(StringUtils.isNotBlank(anyObject())).thenReturn(true);
        PowerMockito.when(RedisUtil.get(anyObject())).thenReturn("1");
        siteServiceImpl.getIndustrySitIdCache("aaa", "bbb");
    }

    /***
     * 获取获取默认站点id测试
     * @author 许尚敏  
     * @date 2017年6月27日 下午3:18:29
     */
    @Test
    public void testGetDefaultSitId() {
        siteServiceImpl.getDefaultSitId();
    }

    /***
     * 取默认站点id(缓存)测试
     * @author 许尚敏  
     * @date 2017年6月27日 下午3:25:29
     */
    @Test
    public void testGetDefaultSitIdCache() {
        siteServiceImpl.getDefaultSitIdCache();
    }

    /***
     * 获取站点名称测试
     * @author 许尚敏  
     * @date 2017年6月27日 下午3:30:29
     */
    @Test
    public void testGetSiteName() {
        siteServiceImpl.getSiteName(1L);
    }

    /***
     * 获取站点名称(缓存)测试
     * @author 许尚敏  
     * @date 2017年6月27日 下午3:30:29
     */
    @Test
    public void testGetSiteNameCache() {
        PowerMockito.when(RedisUtil.get(anyObject())).thenReturn("1");
        PowerMockito.when(StringUtils.isBlank(anyObject())).thenReturn(true);
        siteServiceImpl.getSiteNameCache(1L);
    }

    /***
     * 查询 站点首页面测试
     * @author 许尚敏  
     * @date 2017年6月27日 下午3:52:29
     */
    @Test
    public void testGetFirstSitePage() {
        SitePage sitePage = new SitePage();
        PowerMockito.when(siteDao.getFirstPage(anyObject())).thenReturn(sitePage);
        siteServiceImpl.getFirstSitePage(1L);
    }

    /***
     * 查询 站点首页面(缓存)测试
     * @author 许尚敏  
     * @date 2017年6月27日 下午3:52:29
     */
    @Test
    public void testGetFirstSitePageCache() {
        List<String> columnList = new ArrayList<String>();
        columnList.add("11");
        columnList.add("22");
        columnList.add("33");
        columnList.add("44");
        columnList.add("55");
        PowerMockito.when(RedisUtil.hmget(anyObject(), anyObject(),anyObject(),anyObject(),anyObject(),anyObject())).thenReturn(columnList);
        siteServiceImpl.getFirstSitePageCache(1L);
    }

    /***
     * 获取站点下一页测试
     * @author 许尚敏  
     * @date 2017年6月27日 下午4:30:29
     */
    @Test
    public void testGetNextPage() {
        SitePage sitePage = new SitePage();
        PowerMockito.when(siteDao.getNextPage(anyObject(),anyObject(),anyObject())).thenReturn(sitePage);
        siteServiceImpl.getNextPage(1L, 1, 1);
    }

    /***
     * 获取站点下一页(缓存)测试
     * @author 许尚敏  
     * @date 2017年6月27日 下午4:40:29
     */
    @Test
    public void testGetNextPageCache() {
        List<String> columnList = new ArrayList<String>();
        columnList.add("11");
        columnList.add("22");
        columnList.add("33");
        columnList.add("44");
        columnList.add("55");
        PowerMockito.when(RedisUtil.hmget(anyObject(), anyObject(),anyObject(),anyObject(),anyObject(),anyObject())).thenReturn(columnList);
        PowerMockito.when(StringUtils.equals(anyObject(),anyObject())).thenReturn(true);
        siteServiceImpl.getNextPageCache(1L, 1, 1);
    }

    /***
     * 获取行业站点id测试
     * @author 许尚敏  
     * @date 2017年6月27日 下午4:50:29
     */
    @Test
    public void testGetSitePageForPreview() {
        List<String> columnList = new ArrayList<String>();
        columnList.add("11");
        columnList.add("22");
        columnList.add("33");
        columnList.add("44");
        columnList.add("55");
        PowerMockito.when(RedisUtil.hmget(anyObject(), anyObject(),anyObject(),anyObject(),anyObject(),anyObject())).thenReturn(columnList);
        PowerMockito.when(StringUtils.equals(anyObject(),anyObject())).thenReturn(true);
        SitePage sitePage = new SitePage();
        sitePage.setPageId(1L);
        PowerMockito.when(siteDao.getPrevSitePage(anyObject(),anyObject(),anyObject())).thenReturn(sitePage);
        siteServiceImpl.getSitePageForPreview(1L, "{\"pageType\": 1,\"num\": 1,\"option\": \"prevpage\"}");
    }

    /***
     * 获取站点页路径测试
     * @author 许尚敏  
     * @date 2017年6月27日 下午4:50:29
     */
    @Test
    public void testGetPagePath() {
        siteServiceImpl.getPagePath(1L);
    }

    /***
     * 获取站点缩略图路径测试
     * @author 许尚敏  
     * @date 2017年6月27日 下午4:50:29
     */
    @Test
    public void testGetThumbPath() {
        siteServiceImpl.getThumbPath(1l);
    }

    /***
     * 保存站点缩略图路径测试
     * @author 许尚敏  
     * @date 2017年6月27日 下午4:50:29
     */
    @Test
    public void testSaveThumbPath() {
        siteServiceImpl.saveThumbPath(1L, "aaa");
    }

    /***
     * 更新或删除站点前，清空行业站点对应的redis缓存测试
     * @author 许尚敏  
     * @date 2017年6月27日 下午5:10:29
     */
    @Test
    public void testClearIndustrySiteCache() {
        Site site = new Site();
        site.setPriIndustryCode("aaa");
        site.setSecIndustryCode("aaa");
        PowerMockito.when(siteDao.getById(anyObject())).thenReturn(site);
        PowerMockito.when(RedisUtil.del(anyObject())).thenReturn(1L);
        siteServiceImpl.clearIndustrySiteCache(1l);
    }

    /***
     * 更新或删除站点前，清空区域站点对应的redis缓存测试
     * @author 许尚敏  
     * @date 2017年6月27日 下午5:10:29
     */
    @Test
    public void testClearLocationSiteCache() {
        Site site = new Site();
        site.setPriIndustryCode("aaa");
        site.setSecIndustryCode("aaa");
        PowerMockito.when(siteDao.getById(anyObject())).thenReturn(site);
        PowerMockito.when(RedisUtil.del(anyObject())).thenReturn(1L);
        siteServiceImpl.clearLocationSiteCache(1L);
    }

}

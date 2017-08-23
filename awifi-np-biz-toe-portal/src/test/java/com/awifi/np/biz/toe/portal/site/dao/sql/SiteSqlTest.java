package com.awifi.np.biz.toe.portal.site.dao.sql;


import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;


/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017年6月22日 下午7:06:12
 * 创建作者：许尚敏
 * 文件名称：SiteSqlTest.java
 * 版本：  v1.0
 * 功能：站点测试
 * 修改记录：
 */
@RunWith(PowerMockRunner.class)
public class SiteSqlTest {
    /**被测试类*/
    @InjectMocks
    private SiteSql siteSql;
    
    /**初始化*/
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }
    
    /**
     * 默认站点总数测试
     * @author 许尚敏
     * @date 2017年6月23日 下午14:21:37
     */
    @Test
    public void testGetDefaultCountByParam() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keywords", "aaa");
        siteSql.getDefaultCountByParam(params);
    }

    /**
     * 默认站点列表测试
     * @author 许尚敏
     * @date 2017年6月23日 下午14:21:37
     */
    @Test
    public void testGetDefaultListByParam() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keywords", "aaa");
        siteSql.getDefaultListByParam(params);
    }

    /**
     * 地区站点总数测试
     * @author 许尚敏
     * @date 2017年6月23日 下午14:21:37
     */
    @Test
    public void testGetLocationCountByParam() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keywords", "aaa");
        params.put("provinceId", 1L);
        params.put("cityId", 1L);
        siteSql.getLocationCountByParam(params);
    }

    /**
     * 地区站点总数测试
     * @author 许尚敏
     * @date 2017年6月23日 下午14:21:37
     */
    @Test
    public void testGetLocationListByParam() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keywords", "aaa");
        params.put("provinceId", 1L);
        params.put("cityId", 1L);
        siteSql.getLocationListByParam(params);
    }

    /**
     * 行业站点总数测试
     * @author 许尚敏
     * @date 2017年6月23日 下午14:21:37
     */
    @Test
    public void testGetIndustryCountByParam() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keywords", "aaa");
        params.put("priIndustryCode", "aaa");
        params.put("secIndustryCode", "aaa");
        siteSql.getIndustryCountByParam(params);
    }

    /**
     * 行业站点列表测试
     * @author 许尚敏
     * @date 2017年6月23日 下午14:21:37
     */
    @Test
    public void testGetIndustryListByParam() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keywords", "aaa");
        params.put("priIndustryCode", "aaa");
        params.put("secIndustryCode", "aaa");
        siteSql.getIndustryListByParam(params);
    }

    /**
     * 商户站点总数测试
     * @author 许尚敏
     * @date 2017年6月23日 下午14:21:37
     */
    @Test
    public void testGetCountByParam() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keywords", "aaa");
        params.put("siteId", 1L);
        params.put("cascadeLabel", "aaa");
        params.put("merchantIds", new Long[]{1L,2L});
        params.put("status", 1);
        siteSql.getCountByParam(params);
    }
    
    /**
     * 商户站点总数测试
     * @author 许尚敏
     * @date 2017年6月23日 下午14:21:37
     */
    @Test
    public void testGetCountByParamByMerchantId() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keywords", "aaa");
        params.put("merchantId", 1L);
        params.put("siteId", 1L);
        params.put("cascadeLabel", "aaa");
        params.put("merchantIds", new Long[]{1L,2L});
        params.put("status", 1);
        siteSql.getCountByParam(params);
    }

    /**
     * 商户站点列表测试
     * @author 许尚敏
     * @date 2017年6月23日 下午14:21:37
     */
    @Test
    public void testGetListByParam() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keywords", "aaa");
        params.put("siteId", 1L);
        params.put("cascadeLabel", "aaa");
        params.put("merchantIds", new Long[]{1L,2L});
        params.put("status", 1);
        siteSql.getListByParam(params);
    }
    
    /**
     * 商户站点列表测试
     * @author 许尚敏
     * @date 2017年6月23日 下午14:21:37
     */
    @Test
    public void testGetListByParamByMerchantId() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keywords", "aaa");
        params.put("merchantId", 1L);
        params.put("siteId", 1L);
        params.put("cascadeLabel", "aaa");
        params.put("merchantIds", new Long[]{1L,2L});
        params.put("status", 1);
        siteSql.getListByParam(params);
    }

}

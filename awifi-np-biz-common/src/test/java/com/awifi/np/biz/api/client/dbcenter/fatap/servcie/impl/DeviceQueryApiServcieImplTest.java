package com.awifi.np.biz.api.client.dbcenter.fatap.servcie.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import static org.mockito.Matchers.anyString;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.anyObject;

import com.alibaba.fastjson.JSONArray;
import com.awifi.np.biz.api.client.dbcenter.fatap.util.DeviceQueryClient;
import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.common.system.sysconfig.service.SysConfigService;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.test.MockBase;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;
/**
 * 
 * @ClassName: DeviceQueryApiServcieImplTest
 * @Description: 对应开放平台的deviceQueryService接口 单元测试
 * @author wuqia
 * @date 2017年6月12日 下午7:56:13
 *
 */
@PrepareForTest({DeviceQueryClient.class, SysConfigUtil.class})
public class DeviceQueryApiServcieImplTest extends MockBase {
    /**
     * 被测试类
     */
    @InjectMocks
    private DeviceQueryApiServcieImpl deviceQueryApiServcieImpl;
    /** 系统参数业务层 */
    @Mock(name = "sysConfigService")
    private SysConfigService sysConfigService;
    /** 初始化 */
    @Before
    public void before() {
        PowerMockito.mockStatic(CenterHttpRequest.class);
        PowerMockito.mockStatic(JsonUtil.class);
        PowerMockito.mockStatic(DeviceQueryClient.class);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(ValidUtil.class);
        PowerMockito.when(JsonUtil.toJson(anyObject())).thenReturn("result");
        // PowerMockito.when(SysConfigUtil.getParamValue(anyString())).thenReturn("url");
        // PowerMockito.when(BeanUtil.getBean("sysConfigService")).thenReturn(sysConfigService);
    }
    /**
     * @Title: testQueryEntityCountByParam
     * @Description: 通过条件查询设备以及虚拟信息记录数
     * @throws Exception
     *             参数描述
     * @data 2017年6月13日 上午9:16:21
     * @author wuqia
     */
    @Test
    public void testQueryEntityCountByParam() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("a", "a");
        Map<String, Object> returnMap = new HashMap<>();
        PowerMockito.when(
                CenterHttpRequest.sendGetRequest(anyString(), anyString()))
                .thenReturn(returnMap);
        deviceQueryApiServcieImpl.queryEntityCountByParam(params);
    }
    /**
     * @Title: testQueryEntityInfoListByParam
     * @Description: 通过条件查询设备和虚拟信息
     * @throws Exception
     *             参数描述
     * @data 2017年6月13日 上午9:16:40
     * @author wuqia
     */
    @Test
    public void testQueryEntityInfoListByParam() throws Exception {
        Map<String, Object> params = new HashMap<>();
        deviceQueryApiServcieImpl.queryEntityInfoListByParam(params);
    }
    /**
     * @Title: testQueryHotFitapInfoListByParam
     * @Description: 根据主键查询设备和虚拟信息
     * @throws Exception
     *             参数描述
     * @data 2017年6月13日 上午9:20:53
     * @author wuqia
     */
    @Test
    public void testQueryHotFitapInfoByParam() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("pageNo", 1);
        params.put("pageSize", 10);
        deviceQueryApiServcieImpl.queryHotFitapInfoListByParam(params);
    }
    /**
     * 
     * @Title: testQueryEntityInfoById
     * @Description: 根据主键查询设备和虚拟信息
     * @throws Exception
     *             参数描述
     * @data 2017年6月13日 上午9:21:15
     * @author wuqia
     */
    @Test
    public void testQueryEntityInfoById() throws Exception {
        deviceQueryApiServcieImpl.queryEntityInfoById("1");
    }
    /**
     * 
     * @Title: testQueryEntityCountByParamGroupByModel
     * @Description: 条件查不同型号设备数量
     * @throws Exception
     *             参数描述
     * @throws 
     * @data 2017年6月13日 上午9:21:31
     * @author wuqia
     */
    @Test
    public void testQueryEntity() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("pageNo", 1);
        params.put("pageSize", 10);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("rs", new JSONArray());
        PowerMockito.when(
                CenterHttpRequest.sendGetRequest(anyString(), anyString()))
                .thenReturn(returnMap);
        deviceQueryApiServcieImpl.queryEntityCountByParamGroupByModel(params);
    }
    /**
     * 
     * @Title: testQueryHotareaInfoById
     * @Description: 根据ID查awifi热点信息
     * @throws Exception
     *             参数描述
     * 返回类型描述
     * @data 2017年6月13日 上午9:21:51
     * @author wuqia
     */
    @Test
    public void testQueryHotareaInfoById() throws Exception {
        deviceQueryApiServcieImpl.queryHotareaInfoById(1L);
    }
    /**
     * 
     * @Title: testQueryHotareaInfoListByParam
     * @Description: 查询chinanet 热点信息
     * @throws Exception
     *             参数描述
     * @data 2017年6月13日 上午9:22:04
     * @author wuqia
     */
    @Test
    public void testQueryHotareaInfoByParam() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("pageNo", 1);
        params.put("pageSize", 10);
        deviceQueryApiServcieImpl.queryHotareaInfoListByParam(params);
    }
    /**
     * @Title: testQueryChinaNetHotareaInfoListByParam
     * @Description: 查询热点
     * @throws Exception
     *             参数描述
     * @data 2017年6月13日 上午9:26:44
     * @author wuqia
     */
    @Test
    public void testQueryChinaNetHotareaInfo() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("pageNo", 1);
        params.put("pageSize", 10);
        deviceQueryApiServcieImpl.queryChinaNetHotareaInfoListByParam(params);
    }
    /**
     * @Title: testQueryChinaHotareaInfoById
     * @Description: 根据id查询
     * @throws Exception
     *             参数描述
     * @data 2017年6月13日 上午9:29:35
     * @author wuqia
     */
    @Test
    public void testQueryChinaHotareaInfoById() throws Exception {
        Map<String, Object> returnMap = new HashMap<>();
        PowerMockito.when(
                CenterHttpRequest.sendGetRequest(anyString(), anyString()))
                .thenReturn(returnMap);
        PowerMockito.when(JsonUtil.toJson(anyObject())).thenReturn("result");
        deviceQueryApiServcieImpl.queryChinaHotareaInfoById(1L);
    }

}

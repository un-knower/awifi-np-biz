package com.awifi.np.biz.api.client.dbcenter.platform.servcie.impl;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;

import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.common.test.MockBase;
import com.awifi.np.biz.common.util.JsonUtil;

/**
 * 
 * @ClassName: PlatFormApiServiceImplTest
 * @Description: 省分平台 服务类 单元测试
 * @author wuqia
 * @date 2017年6月12日 下午7:03:12
 *
 */
public class PlatFormApiServiceImplTest extends MockBase {
    /**
     * 被测试类
     */
    @InjectMocks
    private PlatFormApiServiceImpl platFormApiServiceImpl;
    /**
     * @Title: befor
     * @Description: 初始化
     * 2017年6月12日 下午7:26:59
     * @author wuqia
     */
    @Before
    public void befor() {
        PowerMockito.when(JsonUtil.toJson(anyObject())).thenReturn("result");
    }
    /**
     * 
     * @Title: testQueryPlatformCountByParam
     * @Description: 根据条件查询平台数量
     * @throws Exception
     *             参数描述
     *             2017年6月12日 下午7:17:49
     * @author wuqia
     */
    @Test
    public void testQueryPlatformCountByParam() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("pageNo", 1);
        params.put("pageSize", 10);
        Map<String, Object> returnMap = new HashMap<>();
        PowerMockito.when(
                CenterHttpRequest.sendGetRequest(anyString(), anyString()))
                .thenReturn(returnMap);
        platFormApiServiceImpl.queryPlatformCountByParam(params);
    }
    /**
     * 
     * @Title: testQueryPlatformListByParam
     * @Description: 根据条件查询平台信息
     * @throws Exception
     *             参数描述
     *             2017年6月12日 下午7:19:38
     * @author wuqia
     */
    @Test
    public void testQueryPlatformListByParam() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("pageNo", 1);
        params.put("pageSize", 10);
        Map<String, Object> returnMap = new HashMap<>();
        PowerMockito.when(
                CenterHttpRequest.sendGetRequest(anyString(), anyString()))
                .thenReturn(returnMap);
        platFormApiServiceImpl.queryPlatformListByParam(params);
    }

    @Test
    public void testQueryPlatformById() throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("id", "1");
        Map<String, Object> returnMap = new HashMap<>();
        PowerMockito.when(
                CenterHttpRequest.sendGetRequest(anyString(), anyString()))
                .thenReturn(returnMap);
        platFormApiServiceImpl.queryPlatformById(param);
    }
    
    @Test
    public void testEditPlatForm() throws Exception {
        Map<String, Object> params = new HashMap<>();
        platFormApiServiceImpl.editPlatForm(params);
    }

    @Test
    public void testAddPlatForm() throws Exception {
        Map<String, Object> params = new HashMap<>();
        platFormApiServiceImpl.addPlatForm(params);
    }

    @Test
    public void testDeletePaltform() throws Exception {
        Long id = 1L;
        Map<String, Object> returnMap = new HashMap<>();
        PowerMockito.when(
                CenterHttpRequest.sendDeleteRequest(anyString(), anyString()))
                .thenReturn(returnMap);
        platFormApiServiceImpl.deletePaltform(id);
    }

}

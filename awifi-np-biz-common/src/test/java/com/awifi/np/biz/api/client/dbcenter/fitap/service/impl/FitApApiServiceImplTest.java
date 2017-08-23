package com.awifi.np.biz.api.client.dbcenter.fitap.service.impl;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;

import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.test.MockBase;
import com.awifi.np.biz.common.util.JsonUtil;

/**
 * 
 * @ClassName: FitApApiServiceImplTest
 * @Description: chinanet瘦AP 测试类
 * @author wuqia
 * @date 2017年6月13日 上午10:24:24
 *
 */
public class FitApApiServiceImplTest extends MockBase {
    /**
     * 被测试类
     */
    @InjectMocks
    private FitApApiServiceImpl fitApApiServiceImpl;
    /**
     * @Title: befor
     * @Description: 初始化方法 参数描述
     * @data  2017年6月13日 下午1:39:58
     * @author wuqia
     */
    @Before
    public void befor() {
        PowerMockito.when(JsonUtil.toJson(anyObject())).thenReturn("result");
    }
    /**
     * 
    * @Title: testQueryChinaNetDevInfoCountByParam
    * @Description: 查询chinanet 设备 数量
    * @throws Exception  参数描述
    * @throws
    * @data 2017年6月13日 下午1:41:21
    * @author wuqia
     */
    @Test
    public void testQueryChinaNetDevInfoCount() throws Exception {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("importer", "EMS");
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("suc", true);
        returnMap.put("rs", 1);
        PowerMockito.when(
                CenterHttpRequest.sendGetRequest(anyString(), anyString()))
                .thenReturn(returnMap);
        fitApApiServiceImpl.queryChinaNetDevInfoCount(paramMap);
    }
    /**
    * @Title: testQueryChinanetDevInfoListByParam
    * @Description: 查询chinanet 设备  信息
    * @throws Exception  参数描述
    * @throws
    * @data 2017年6月13日 下午1:42:01
    * @author wuqia
     */
    @Test
    public void testQueryChinanetDevInfoList() throws Exception {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("pageNo", 1);
        paramMap.put("pageSize", 10);
        paramMap.put("importer", "EMS");
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, Object> returnMap1 = new HashMap<>();
        returnMap.put("suc", true);
        returnMap1.put("suc", true);
        PowerMockito.when(SysConfigUtil.getParamValue("dbc_QueryChinaNetDevInfoListByParam_url"))
                .thenReturn("QueryChinanetDevInfoListByParam");
        returnMap1.put("rs", 1);
        PowerMockito.when(CenterHttpRequest.sendGetRequest(null, "params=result"))
                .thenReturn(returnMap1);
        returnMap.put("rs", new ArrayList<>());
        PowerMockito
                .when(CenterHttpRequest.sendGetRequest(
                        "QueryChinanetDevInfoListByParam", "params=result"))
                .thenReturn(returnMap);
        fitApApiServiceImpl.queryChinanetDevInfoList(paramMap);
    }

}

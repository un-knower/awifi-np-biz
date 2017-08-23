package com.awifi.np.biz.api.client.dbcenter.fatap.util;

import java.util.HashMap;
import java.util.Map;
import static org.mockito.Matchers.anyString;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import com.awifi.np.biz.api.client.dbcenter.fatap.servcie.DeviceQueryApiService;
import com.awifi.np.biz.api.client.dbcenter.http.util.CenterHttpRequest;
import com.awifi.np.biz.common.test.MockBase;
import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.common.util.JsonUtil;
@SuppressWarnings("static-access")
public class DeviceQueryClientTest extends MockBase {
    /**
     * 被测试类 
     */
    @InjectMocks
    private DeviceQueryClient deviceQueryClient;
    /**
     * 设备查询服务service
     */
    @Mock(name = "deviceQueryApiService")
    private DeviceQueryApiService deviceQueryApiService;
    /**
     * 
     * @Title: befor
     * @Description: 初始化
     * @data 2017年6月13日 上午9:15:42
     * @author wuqia
     */
    @Before
    public void befor() {
        PowerMockito.when(BeanUtil.getBean("deviceQueryApiService"))
                .thenReturn(deviceQueryApiService);
    }
    /**
     * 
     * @Title: testQueryEntityCountByParam
     * @Description: 得到数据库中符合条件的条数
     * @throws Exception
     *             参数描述
     * @throws 
     * @data  2017年6月13日 上午9:51:24
     * @author wuqia
     */
    @Test
    public void testQueryEntityCountByParam() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("deviceId", "1");
        params.put("importer", "2E");
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("suc", true);
        returnMap.put("rs", 10);
        PowerMockito.when(
                CenterHttpRequest.sendGetRequest(anyString(), anyString()))
                .thenReturn(returnMap);
        deviceQueryClient.queryEntityCountByParam(params);
    }
    /**
     * 
     * @Title: testQueryEntityInfoListByParam
     * @Description: 获取符合条件的记录
     * @throws Exception
     *             参数描述
     * @throws 
     * @data  2017年6月13日 上午9:51:38
     * @author wuqia
     */
    @Test
    public void testQueryEntityInfoListByParam() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("deviceId", "1");
        params.put("importer", "2E");
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("suc", true);
        returnMap.put("msg", "成功");
        returnMap.put("rs", "[]");
        PowerMockito.when(
                CenterHttpRequest.sendGetRequest(anyString(), anyString()))
                .thenReturn(returnMap);
        deviceQueryClient.queryEntityInfoListByParam(params);
    }
    /**
     * 
     * @Title: testQueryEntityCountByParamGroupByModel
     * @Description: 条件查不同型号设备数量
     * @throws Exception
     *             参数描述
     * @throws 
     * @data  2017年6月13日 上午9:51:55
     * @author wuqia
     */
    @Test
    public void testQueryEntityCountByGroup() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("corporation", "cc");
        map.put("importer", "2E");
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("suc", true);
        returnMap.put("msg", "成功");
        returnMap.put("rs", "[]");
        PowerMockito.when(
                CenterHttpRequest.sendGetRequest(anyString(), anyString()))
                .thenReturn(returnMap);
        deviceQueryClient.queryEntityCountByParamGroupByModel(map);
    }
    /**
     * @Title: testQueryEntityInfoById
     * @Description: 根据主键查询设备和虚拟信息
     * @throws Exception
     *             参数描述
     * @throws 
     * @data 2017年6月13日 上午9:52:10
     * @author wuqia
     */
    @Test
    public void testQueryEntityInfoById() throws Exception {
        String id = "1";
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("suc", true);
        returnMap.put("msg", "成功");
        returnMap.put("rs", "[]");
        PowerMockito.when(
                CenterHttpRequest.sendGetRequest(anyString(), anyString()))
                .thenReturn(returnMap);
        deviceQueryClient.queryEntityInfoById(id);
    }
    /**
     * @Title: testQueryHotareaInfoById
     * @Description: 根据id 获取热点信息
     * @throws Exception
     *             参数描述
     * @throws 
     * @data 2017年6月13日 上午9:52:54
     * @author wuqia
     */
    @Test
    public void testQueryHotareaInfoById() throws Exception {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("suc", true);
        returnMap.put("msg", "成功");
        returnMap.put("rs", "[]");
        PowerMockito.when(
                CenterHttpRequest.sendGetRequest(anyString(), anyString()))
                .thenReturn(returnMap);
        deviceQueryClient.queryHotareaInfoById(1L);
    }
    /**
     * 
     * @Title: testQueryHotareaInfoListByParam
     * @Description: 根据条件查询热点信息
     * @throws Exception
     *             参数描述
     * @throws 
     * @data   2017年6月13日 上午9:53:20
     * @author wuqia
     */
    @Test
    public void testQueryHotareaInfoByParam() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("owner", "2E");
        params.put("hotareaName", "");
        params.put("pageNo", 1);
        params.put("pageSize", 10);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("suc", true);
        returnMap.put("msg", "成功");
        returnMap.put("rs", "[]");
        PowerMockito.when(
                CenterHttpRequest.sendGetRequest(anyString(), anyString()))
                .thenReturn(returnMap);
        deviceQueryClient.queryHotareaInfoListByParam(params);
    }
    /**
     * 
     * @Title: testQueryChinaNetHotareaInfoListByParam
     * @Description: 查询 chinanet 信息
     * @throws Exception
     *       参数描述
     * @data  2017年6月13日 上午9:53:37
     * @author wuqia
     * 
     */
    @Test
    public void testQueryChinaNetHotareaInfoBy() throws Exception {
        Map<String, Object> reqparam = new HashMap<>();
        reqparam.put("importer", "EMS");
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("suc", true);
        returnMap.put("msg", "成功");
        returnMap.put("rs", "[]");
        PowerMockito.when(
                CenterHttpRequest.sendGetRequest(anyString(), anyString()))
                .thenReturn(returnMap);
        deviceQueryClient.queryChinaNetHotareaInfoListByParam(reqparam);
    }
    /**
     * 
     * @Title: testQueryChinaHotareaInfoById
     * @Description: 根据id查询热点信息
     * @throws Exception
     *             参数描述
     * @data 2017年6月13日 上午10:02:06
     * @author wuqia
     */
    @Test
    public void testQueryChinaHotareaInfoById() throws Exception {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("suc", true);
        returnMap.put("msg", "成功");
        returnMap.put("rs", JsonUtil.toJson(new HashMap<>()));
        PowerMockito.when(
                CenterHttpRequest.sendGetRequest(anyString(), anyString()))
                .thenReturn(returnMap);
        deviceQueryClient.queryChinaHotareaInfoById(1L);
    }
    /**
     * 
     * @Title: testQueryHotFitapInfoListByParam
     * @Description: 设备信息查询
     * @throws Exception
     *             参数描述
     * @data 2017年6月13日 上午10:06:02
     * @author wuqia
     */
    @Test
    public void testQueryHotFitapInfoByParam() throws Exception {
        Map<String, Object> reqparam = new HashMap<String, Object>();
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("suc", true);
        returnMap.put("msg", "成功");
        returnMap.put("rs", JsonUtil.toJson(new HashMap<>()));
        PowerMockito.when(
                CenterHttpRequest.sendGetRequest(anyString(), anyString()))
                .thenReturn(returnMap);
        deviceQueryClient.queryHotFitapInfoListByParam(reqparam);
    }

}

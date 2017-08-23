package com.awifi.np.biz.devsrv.fatap.controller;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.awifi.np.biz.api.client.dbcenter.corporation.util.CorporationClient;
import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubEntity;
import com.awifi.np.biz.api.client.dbcenter.fatap.util.DeviceQueryClient;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.devsrv.common.MockBase;
import com.awifi.np.biz.devsrv.fatap.service.FatApService;
/**
 * 
 * @ClassName: FatApQueryControllerTest
 * @Description: 定制终端单元测试
 * @author wuqia
 * @date 2017年6月12日 下午2:14:31
 */
@SuppressWarnings("unchecked")
@PrepareForTest({CorporationClient.class,DeviceQueryClient.class})
public class FatApQueryControllerTest extends MockBase {
    /**
     * 被测试类
     */
    @InjectMocks
    private FatApQueryController fatApQueryController;
    /**
     * 定制终端服务类
     */
    @Mock(name = "fatApService")
    private FatApService fatApService;
    /**
     * 初始化
     * @author 伍恰  
     * @date 2017年6月13日 下午4:05:16
     */
    @Before
    public void befor() {
        PowerMockito.mockStatic(DeviceQueryClient.class);
        PowerMockito.mockStatic(CorporationClient.class);
        PowerMockito.when(CastUtil.toInteger(null)).thenReturn(null);
    }
    /**
     * @Title: testGetEmsDevBaseImpList
     * @Description: 定制终端导入页面查询功能
     * @data   2017年6月12日 下午2:16:44
     * @author wuqia
     * @throws Exception 异常
     */
    @Test
    public void testGetEmsDevBaseImpList() throws Exception {
        String params = "{}";
        Map<String, Object> paramsMap = new HashMap<>();
        PowerMockito.when(JsonUtil.fromJson(anyString(), anyObject()))
                .thenReturn(paramsMap);
        PowerMockito.when( CastUtil.toInteger(null)).thenReturn(null);
        Map<String, Object> map = fatApQueryController
                .getEmsDevBaseImpList(access_token, params, request);
        Assert.assertNotNull(map);
    }
    /**
     * 
     * @Title: testGetDevBaseById
     * @Description: 根据id获得具体设备信息
     * @throws Exception
     *             参数描述
     * @throws 
     * @data  2017年6月12日 下午2:19:47
     * @author wuqia
     */
    @Test
    public void testGetDevBaseById() throws Exception {
        String accesstoken = "1";
        String id = "1";
        Map<String, Object> map = fatApQueryController.getDevBaseById(accesstoken, id);
        Assert.assertNotNull(map);
    }
    /**
     * 
     * @Title: testGetFatapList
     * @Description:定制终端查询界面
     * @throws Exception
     *             参数描述
     * @throws 
     * @data  2017年6月12日 下午2:23:49
     * @author wuqia
     */
    @Test
    public void testGetFatapList() throws Exception {
        String params = "{entityType:\"31\"}";
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("entityType", "31");
        paramsMap.put("batchNums", "batchNums");
        paramsMap.put("pass", true);
        //paramsMap.put("pageNo", 1);
        //paramsMap.put("pageSize", 10);
        PowerMockito.when(JsonUtil.fromJson(anyString(), anyObject()))
                .thenReturn(paramsMap);
        Map<String, Object> result = fatApQueryController.getFatapList(access_token, params, request, response);
        paramsMap.put("pass", false);
        PowerMockito.when(JsonUtil.fromJson(anyString(), anyObject())).thenReturn(paramsMap);
        fatApQueryController.getFatapList(access_token, params, request, response);
        Assert.assertNotNull(result);
    }
    /**
     * @Title: testReviewGoFatAP
     * @Description: 定制终端查询页面的审核通过功能
     * @throws Exception
     *             参数描述
     * @throws 
     * @data  2017年6月12日 下午2:24:02
     * @author wuqia
     */
    @Test
    public void testReviewGoFatAP() throws Exception {
        Map<String, Object> bodyParam = new HashMap<>();
        bodyParam.put("ids", "1");
        bodyParam.put("remark", "test");
        bodyParam.put("batchNums", "batchNums");
        Map<String, Object> map = fatApQueryController
                .reviewGoFatAP(access_token, bodyParam, request);
        Assert.assertNotNull(map);
    }
    /**
     ** @Title: testReviewBackFatap
     * 
     * @Description: 定制终端查询页面的审核驳回功能
     * @throws 
     * @data  2017年6月12日 下午2:37:09
     * @author wuqia
     * @throws Exception 异常
     */
    @Test
    public void testReviewBackFatap() throws Exception {
        Map<String, Object> bodyParam = new HashMap<>();
        bodyParam.put("ids", "1");
        bodyParam.put("remark", "test");
        bodyParam.put("batchNums", "batchNums");
        Map<String, Object> map = fatApQueryController.reviewBackFatap(access_token, bodyParam, request);
        Assert.assertNotNull(map);
    }
    /**
     * @Title: testDeleteAwifiFatAPByIds
     * @Description: 定制终端删除功能
     * @throws 
     * @data  2017年6月12日 下午2:38:10
     * @author wuqia
     * @throws Exception 异常
     */
    @Test
    public void testDeleteAwifiFatAPByIds() throws Exception {
        String params = "{pass:true,idArr:\"123\"}";
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("pass", true);
        paramsMap.put("idArr", "123");
        paramsMap.put("batchNums", "batchNums");
        CenterPubEntity cpe = new CenterPubEntity();
        cpe.setMerchantId(0L);
        PowerMockito.when(fatApService.queryEntityInfoById(anyObject())).thenReturn(cpe);
        PowerMockito.when(JsonUtil.fromJson(anyString(), anyObject())).thenReturn(paramsMap);
        Map<String, Object> map = fatApQueryController.deleteAwifiFatAPByIds(access_token, params);
        Assert.assertNotNull(map);
        PowerMockito.when(fatApService.queryEntityInfoById(anyObject())).thenReturn(null);
        fatApQueryController.deleteAwifiFatAPByIds(access_token, params);
        paramsMap.put("pass", false);
        fatApQueryController.deleteAwifiFatAPByIds(access_token, params);
    }
    /**
     * @Title: testUpdateFatapById
     * @Description: 定制终端update功能
     * @throws Exception
     *             参数描述
     * @throws 
     * @data  2017年6月12日 下午2:43:22
     * @author wuqia
     */
    @Test
    public void testUpdateFatapById() throws Exception {
        Map<String, Object> bodyParams = new HashMap<>();
        bodyParams.put("id", "1");
        bodyParams.put("corporation", "1123");
        bodyParams.put("model", "1");
        bodyParams.put("fwVersion", "aa");
        bodyParams.put("cpVersion", "sa");
        bodyParams.put("provinceId", "1");
        bodyParams.put("cityId", "1");
        bodyParams.put("areaId", "1");
        /*
         * bodyParams.put("cpVersion", 1123); bodyParams.put("id", 1);
         * bodyParams.put("corporation", 1123);
         */
        PowerMockito.when(CorporationClient.getModelType(1L, 1123L))
                .thenReturn("FatAP");
        Map<String, Object> map = fatApQueryController
                .updateFatapById(access_token, bodyParams, request);
        Assert.assertNotNull(map);
    }
    /**
     * @Title: testGetEntity
     * @Description: 校验前端查询参数
     * @throws 
     * @data  2017年6月12日 下午2:43:38
     * @author wuqia
     */
    @Test
    public void testGetEntity() {
        CenterPubEntity entity = new CenterPubEntity();
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        // map.put("corporation", "1123");
        map.put("model", "1");
        map.put("fwVersion", "aa");
        map.put("cpVersion", "sa");
        map.put("provinceId", "1");
        map.put("cityId", "1");
        map.put("areaId", "sa");
        map.put("macAddr", "A23123F23137");
        map.put("batchNum", "A23123F23137");
        map.put("flowSts", "1");
        fatApQueryController.getEntity(entity, map);
    }

}

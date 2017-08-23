package com.awifi.np.biz.devsrv.fatap.controller;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;

import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.devsrv.common.MockBase;
import com.awifi.np.biz.devsrv.fatap.service.PlatFormBaseService;
/**
 * 
 * @ClassName: PlatFormControllerTest
 * @Description: 省分平台单元测试
 * @author wuqia
 * @date 2017年6月12日 上午9:50:23
 */
@SuppressWarnings("unchecked")
public class PlatFormControllerTest extends MockBase {
    /**
     * 被测试类
     **/
    @InjectMocks
    private PlatFormController platFormController;
    /**
     * 服务类
     */
    @Mock(name = "platFormBaseService")
    private PlatFormBaseService platFormBaseService;

    /**
     * @Title: testShowPlatForm
     * @Description: 省分平台查询
     * @throws 
     * @data  2017年6月12日 上午10:05:13
     * @author wuqia
     * @throws Exception 异常
     */
    @Test
    public void testShowPlatForm() throws Exception {
        String params = "{platformName:31}";
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("platformName", "31");
        // paramsMap.put("pageNo", 1);
        // paramsMap.put("pageSize", 10);
        PowerMockito.when(JsonUtil.fromJson(anyString(), anyObject()))
                .thenReturn(paramsMap);
        Map<String, Object> map = platFormController.showPlatForm(access_token,
                params, request);
        Assert.assertNotNull(map);
    }
    /**
     * 
     * @Title: testFindByPlatformId
     * @Description: 省分平台根据id查询
     * @throws 
     * @data  2017年6月12日 上午10:26:02
     * @author wuqia
     * @throws Exception 异常
     */
    @Test
    public void testFindByPlatformId() throws Exception {
        String id = "10";
        Map<String, Object> map = platFormController
                .findByPlatformId(access_token, id);
        Assert.assertNotNull(map);
    }
    /**
     * 
     * @Title: testEditPlatForm
     * @Description: 省分平台编辑
     * @throws Exception
     *             参数描述
     * @throws 
     * @data  2017年6月12日 上午11:26:32
     * @author wuqia
     */
    @Test
    public void testEditPlatForm() throws Exception {
        String id = "1";
        Map<String, Object> bodyParam = new HashMap<>();
        bodyParam.put("platformName", "中文");
        bodyParam.put("platformType", "0");
        Map<String, Object> map = platFormController.editPlatForm(access_token,
                id, bodyParam);
        Assert.assertNotNull(map);
    }
    /**
     * 
     * @Title: testAddPlatForm
     * @Description: 省分平台新增
     * @throws Exception
     *             参数描述
     * @throws 
     * @data  2017年6月12日 上午11:29:26
     * @author wuqia
     */
    @Test
    public void testAddPlatForm() throws Exception {
        Map<String, Object> bodyParam = new HashMap<>();
        bodyParam.put("platformName", "中文");
        bodyParam.put("platformType", "0");
        Map<String, Object> map = platFormController.addPlatForm(access_token,
                bodyParam, request);
        Assert.assertNotNull(map);
    }
    /**
     * @Title: testDelePlatform
     * @Description: 省分删除
     * @throws Exception
     *             参数描述
     * @throws 
     * @data  2017年6月12日 上午11:29:59
     * @author wuqia
     */
    @Test
    public void testDelePlatform() throws Exception {
        Long[] idArr = {1L};
        Map<String, Object> map = platFormController.delePlatform(access_token,
                idArr);
        Assert.assertNotNull(map);
    }
}

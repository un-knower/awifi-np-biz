/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月18日 下午2:15:14
* 创建作者：伍恰
* 文件名称：ExcelValidationTest.java
* 版本：  v1.0
* 功能：ExcelValidation 验证测试类
* 修改记录：
*/
package com.awifi.np.biz.devsrv.fatap.service;


import static org.mockito.Matchers.anyObject;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.awifi.np.biz.api.client.dbcenter.fatap.model.ExcelFatAp;
import com.awifi.np.biz.api.client.dbcenter.location.model.CenterPubArea;
import com.awifi.np.biz.api.client.dbcenter.location.util.LocationClient;
import com.awifi.np.biz.common.excel.model.CenterPubCorporation;
import com.awifi.np.biz.common.excel.model.CenterPubModel;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.devsrv.common.MockBase;
import com.awifi.np.biz.devsrv.pubarea.servcie.PubAreaService;

@PrepareForTest({LocationClient.class})
public class ExcelValidationTest extends MockBase{
    
    /**
     * 被测试类
     */
    @InjectMocks
    private ExcelValidation excelValidation;
    
    /**
     * 厂家集合
     */
    private Map<String, CenterPubCorporation> corpMap;
    
    /**
     * 厂家-型号集合
     */
    private Map<String, Map<String, CenterPubModel>> corporationAndmodelMap;
    /**
     * 设备型号集合
     */
    private Map<String, CenterPubModel> modelMap;
    /**
     * 用户信息
     */
    private SessionUser userSession;
    
    /**
     * 厂商和型号是否已验证标志
     */
    private boolean flag=false;
    /**
     * 本次导入的厂商编号
     */
    private String corporation=null;
    
    /**
     * 地区操作service层
     */
    @Mock
    private PubAreaService pubAreaService;
    
    /**初始化*/
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(LocationClient.class);
        corpMap = new HashMap<>();
    }
    
    /**
     * 获取厂商和型号是否已验证标志
     * @author 伍恰  
     * @date 2017年7月18日 下午2:17:59
     */
    @Test
    public void testIsFlag() {
        excelValidation.isFlag();
    }

    /**
     * 设置厂商和型号是否已验证标志
     * @author 伍恰  
     * @date 2017年7月18日 下午2:18:03
     */
    @Test
    public void testSetFlag() {
        excelValidation.setFlag(flag);
    }

    /**
     * 获取本次导入的厂商编号
     * @author 伍恰  
     * @date 2017年7月18日 下午2:19:16
     */
    @Test
    public void testGetCorporation() {
        excelValidation.getCorporation();
    }

    /**
     * 设置本次导入的厂商编号
     * @author 伍恰  
     * @date 2017年7月18日 下午2:19:48
     */
    @Test
    public void testSetCorporation() {
        excelValidation.setCorporation(corporation);
    }

    /**
     * 构造方法
     * @author 伍恰  
     * @date 2017年7月18日 下午2:21:29
     */
    @Test
    public void testExcelValidation() {
        excelValidation = new ExcelValidation(userSession);
    }

    /**
     * 设置地区操作类
     * @author 伍恰  
     * @date 2017年7月18日 下午2:22:08
     */
    @Test
    public void testSetPubAreaService() {
        excelValidation.setPubAreaService(pubAreaService);
    }

    /**
     * 设置厂家集合
     * @author 伍恰  
     * @date 2017年7月18日 下午2:38:58
     */
    @Test
    public void testSetCorpMap() {
        excelValidation.setCorpMap(corpMap);
    }

    /**
     * 设置厂家-型号集合
     * @author 伍恰  
     * @date 2017年7月18日 下午2:39:17
     */
    @Test
    public void testSetCorporationAndmodelMap() {
        excelValidation.setCorporationAndmodelMap(corporationAndmodelMap);
    }
    
    /**
     * 设置设备型号集合
     * @author 伍恰  
     * @date 2017年7月18日 下午2:39:40
     */
    @Test
    public void testSetModelById() {
        excelValidation.setModelById(modelMap);
    }

    /**
     * 对信息验证的一个过程
     * @author 伍恰  
     * @date 2017年7月18日 下午2:28:53
     */
    @Test
    public void testValidate() {
        userSession = new SessionUser();
        userSession.setOrgId(1L);
        excelValidation = new ExcelValidation(userSession);
        //省份
        excelValidation.setPubAreaService(pubAreaService);
        ExcelFatAp excelFatAp = new ExcelFatAp();
        String province = "浙江";
        excelFatAp.setProvince(province);
        String county = "杭州";
        excelFatAp.setCounty(county);
        String city = "拱墅区";
        excelFatAp.setCity(city);
        CenterPubArea centerPubArea = new CenterPubArea();
        centerPubArea.setId(12L);
        PowerMockito.when(pubAreaService.queryByParam(anyObject(),anyObject(),anyObject())).thenReturn(centerPubArea);
        //厂商
        CenterPubCorporation centerPubCorporation = new CenterPubCorporation();
        excelFatAp.setCorporation("H3C");
        centerPubCorporation.setId(1L);
        corpMap.put("H3C", centerPubCorporation);
        excelValidation.setCorpMap(corpMap);
        //型号
        excelFatAp.setModel("m5500");
        corporationAndmodelMap = new HashMap<>();
        CenterPubModel centerPubModel = new CenterPubModel();
        centerPubModel.setId(1L);
        Map<String, CenterPubModel> centerPubModelMap = new HashMap<>();
        centerPubModelMap.put("m5500", centerPubModel);
        corporationAndmodelMap.put("1m5500", centerPubModelMap);
        excelValidation.setCorporationAndmodelMap(corporationAndmodelMap);
        excelValidation.setFlag(false);
        excelValidation.validate(excelFatAp);
    }

    /**
     * 获取型号集合
     * @author 伍恰  
     * @date 2017年7月18日 下午2:29:21
     */
    @Test
    public void testGetModelMap() {
        excelValidation.getModelMap();
    }

    /**
     * 设置型号集合
     * @author 伍恰  
     * @date 2017年7月18日 下午2:29:47
     */
    @Test
    public void testSetModelMap() {
        excelValidation.setCorpMap(corpMap);
    }

}

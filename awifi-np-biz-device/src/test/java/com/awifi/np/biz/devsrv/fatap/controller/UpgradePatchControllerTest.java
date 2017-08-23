/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月29日 上午10:12:35
* 创建作者：伍恰
* 文件名称：FatApUpgradePatchControllerTest.java
* 版本：  v1.0
* 功能：定制终端升级包 相关功能 测试
* 修改记录：
*/
package com.awifi.np.biz.devsrv.fatap.controller;

import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.IOUtil;
import com.awifi.np.biz.devsrv.common.MockBase;
import com.awifi.np.biz.devsrv.fatap.service.FatApUpgradePatchService;

@PrepareForTest({SysConfigUtil.class,IOUtil.class})
public class UpgradePatchControllerTest extends MockBase{

    /**
     * 被测试类
     */
    @InjectMocks
    private FatApUpgradePatchController fatApUpgradePatchController;
    
    /**
     * 定制终端升级包服务类
     */
    @Mock(name = "fatApUpgradePatchService")
    private FatApUpgradePatchService fatApUpgradePatchService;
    
    /**
     * 初始化
     * @author 伍恰  
     * @date 2017年6月29日 上午11:09:04
     */
    @Before
    public void befor() {
        //PowerMockito.when(JsonUtil.fromJson(anyString(), anyObject())).thenReturn(paramsMap);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.mockStatic(IOUtil.class);
        PowerMockito.when(SysConfigUtil.getParamValue("page_maxsize")).thenReturn("100");
    }
    
    /**
     * 定制终端升级--默认升级包查询
     * @throws Exception 异常
     * @author 伍恰  
     * @date 2017年6月29日 上午10:16:37
     */
    @Test
    public void testGetDefaulUpgradetPatchList() throws Exception {
        Map<String, Object> params = new HashMap<>();
        fatApUpgradePatchController.getDefaulUpgradetPatchList(access_token, params);
    }
    /**
     * 定制终端升级--默认升级包根据id查询
     * @throws Exception 异常
     * @author 伍恰  
     * @date 2017年6月29日 上午10:26:35
     */
    @Test
    public void testGetDefaulUpgradetPatchById() throws Exception {
        String id = "1";
        fatApUpgradePatchController.getDefaulUpgradetPatchById(access_token, id);
    }

    /**
     * 定制终端升级--默认升级启用
     *  参数
     * @author 伍恰  
     * @date 2017年6月29日 上午10:42:27
     */
    @Test
    public void testUpdateUpgradetPatchStatus() {
        Map<String, Object> bodyParam = new HashMap<>();
        bodyParam.put("ids","1,2,3");
        fatApUpgradePatchController.updateUpgradetPatchStatus(access_token, bodyParam );
    }

    /**
     * 定制终端升级--默认升级包上传
     * @throws Exception 异常
     * @author 伍恰  
     * @date 2017年6月29日 上午10:45:59
     */
    /*@Test
    public void testAddDefaulUpgradetPatch() throws Exception {
        Map<String, Object> bodyParam = new HashMap<String, Object>();
        PowerMockito.when(IOUtil.getFileFromRequest(anyObject())).thenReturn(new CommonsMultipartFile((FileItem) new Object()));
        bodyParam.put("", "");
        fatApUpgradePatchController.addDefaulUpgradetPatch(request, response, access_token, bodyParam );
    }*/

    /**
     * 定制终端升级--默认升级包删除
     * @author 伍恰  
     * @date 2017年6月29日 上午10:46:13
     */
    @Test
    public void testDeleteDefaulUpgradetPatch() {
        Long[] idArr = {1L,2L};
        fatApUpgradePatchController.deleteDefaulUpgradetPatch(access_token, idArr );
    }

    /**
     * 定制终端升级--个性化升级设备查询
     * @throws Exception 异常
     * @author 伍恰  
     * @date 2017年6月29日 上午11:03:32
     */
    @Test
    public void testGetUpgradeDeviceList() throws Exception {
        Map<String, Object> params = new HashMap<>();
        fatApUpgradePatchController.getUpgradeDeviceList(access_token, params);
    }

    /**
     * 定制终端升级--个性化升级包查询
     * @author 伍恰  
     * @throws Exception 
     * @date 2017年6月29日 上午11:03:50
     */
    @Test
    public void testGetPersonaUpgradePatchList() throws Exception {
        Map<String, Object> params = new HashMap<>();
        fatApUpgradePatchController.getPersonalizUpgradePatchList(access_token, params );
    }

    /**
     * 定制终端升级--个性化升级包根据id查询
     * @author 伍恰  
     * @throws Exception 
     * @date 2017年6月29日 上午11:05:12
     */
    @Test
    public void testGetPersUpgradePatchById() throws Exception {
        String id = "1";
        fatApUpgradePatchController.getPersonalizUpgradePatchById(access_token, id );
    }

    /**
     * 定制终端升级--个性化升级包上传
     * @throws Exception 异常
     * @author 伍恰  
     * @date 2017年6月29日 上午11:06:20
     */
    /*@Test
    public void testAddPersonalUpgradePatch() throws Exception {
        Map<String, Object> bodyParam = new HashMap<String, Object>();
        fatApUpgradePatchController.addPersonalizedUpgradePatch(request, response, access_token, bodyParam );
    }*/

    /**
     * 定制终端升级--个性化升级包删除
     *  异常/参数
     * @author 伍恰  
     * @date 2017年6月29日 上午11:06:59
     */
    @Test
    public void testDeletePersonaUpgradePatch() {
        Long[] idArr = {1L,2L};
        fatApUpgradePatchController.deletePersonalizedUpgradePatch(access_token, idArr );
    }

}

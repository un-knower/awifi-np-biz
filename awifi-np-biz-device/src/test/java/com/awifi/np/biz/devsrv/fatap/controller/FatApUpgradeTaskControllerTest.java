/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月29日 下午1:50:14
* 创建作者：伍恰
* 文件名称：FatApUpgradeTaskControllerTest.java
* 版本：  v1.0
* 功能：
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
import com.awifi.np.biz.devsrv.fatap.service.FatApUpgradeTaskService;

@PrepareForTest({SysConfigUtil.class,IOUtil.class})
public class FatApUpgradeTaskControllerTest extends MockBase {

    /**
     * 待测试类
     */
    @InjectMocks
    private FatApUpgradeTaskController fatApUpgradeTaskController;
    
    /**
     * 定制终端升级包服务类
     */
    @Mock(name = "fatApUpgradeTaskService")
    private FatApUpgradeTaskService fatApUpgradeTaskService;
    
    /**
     * 初始化
     * @author 伍恰  
     * @date 2017年6月29日 上午11:09:04
     */
    @Before
    public void befor() {
        //PowerMockito.when(JsonUtil.fromJson(anyString(), anyObject())).thenReturn(paramsMap);
        PowerMockito.mockStatic(SysConfigUtil.class);
        PowerMockito.when(SysConfigUtil.getParamValue("page_maxsize")).thenReturn("100");
    }
    
    /**
     * 定制终端升级--个性化升级任务查询
     * @throws Exception 异常
     * @author 伍恰  
     * @date 2017年6月29日 下午1:58:52
     */
    @Test
    public void testPersonaUpgradeTaskList() throws Exception {
        Map<String,Object> params = new HashMap<String, Object>();
        fatApUpgradeTaskController.getPersonalizedUpgradeTaskList(access_token, params);
    }

    /**
     * 定制终端升级--个性化任务根据id查询
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年6月29日 下午2:00:25
     */
    @Test
    public void testGetPersonaUpgradeTaskById() throws Exception {
        String id = "1";
        fatApUpgradeTaskController.getPersonalizedUpgradeTaskById(access_token, id );
    }

    /**
     * 定制终端升级--个性化升级任务添加
     * @author 伍恰  
     * @date 2017年6月29日 下午2:00:45
     */
    @Test
    public void testAddPersonalizedUpgradeTask() {
        Map<String, Object> bodyParam = new HashMap<>();
        fatApUpgradeTaskController.addPersonalizedUpgradeTask(access_token, bodyParam );
    }

    /**
     * 定制终端升级--个性化升级任务删除
     *  异常/参数
     * @author 伍恰  
     * @date 2017年6月29日 下午2:01:29
     */
    @Test
    public void testDeletePersoUpgradeTask() {
        Long[] idArr = {1L};
        fatApUpgradeTaskController.deletePersonalizedUpgradeTask(access_token, idArr );
    }

}

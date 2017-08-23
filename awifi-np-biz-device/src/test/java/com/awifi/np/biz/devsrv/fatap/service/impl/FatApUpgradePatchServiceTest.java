/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月29日 下午2:05:30
* 创建作者：伍恰
* 文件名称：FatApUpgradePatchServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.fatap.service.impl;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.mockito.InjectMocks;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.devsrv.common.MockBase;

public class FatApUpgradePatchServiceTest extends MockBase {

    /**
     * 被测试类
     */
    @InjectMocks
    private FatApUpgradePatchServiceImpl fatApUpgradePatchService;
    
    /**
     * 定制终端升级--默认升级包分页查询
     *  异常/参数
     * @author 伍恰  
     * @date 2017年6月29日 下午2:09:05
     */
    @Test
    public void testGetDefaulUpgradetPatchList() {
        Map<String, Object> params = new HashMap<>();
        Page<Map<String, Object>> page = new Page<>();
        fatApUpgradePatchService.getDefaulUpgradetPatchList(params, page);;
    }

    /**
     * 定制终端升级--默认升级包根据id查询
     * @author 伍恰  
     * @date 2017年6月29日 下午2:18:03
     */
    @Test
    public void testGetDefaulUpgradetPatchById() {
        Long id = 1L;
        fatApUpgradePatchService.getDefaulUpgradetPatchById(id );
    }

    /**
     * 定制终端升级--默认升级启用
     * @author 伍恰  
     * @date 2017年6月29日 下午2:18:33
     */
    @Test
    public void testUpdateUpgradetPatchStatus() {
        Long[] ids = {1L};
        fatApUpgradePatchService.updateUpgradetPatchStatus(ids );;
    }

    /**
     * 定制终端升级--升级包信息新增
     * @author 伍恰  
     * @date 2017年6月29日 下午2:19:52
     */
    @Test
    public void testAddDefaulUpgradetPatch() {
        Map<String, Object> params = new HashMap<>();
        fatApUpgradePatchService.addDefaulUpgradetPatch(params);;
    }

    /**
     * 定制终端升级--默认升级包删除
     * @author 伍恰  
     * @date 2017年6月29日 下午2:20:55
     */
    @Test
    public void testDeleteDefaulUpgradetPatch() {
        Long[] ids = {1L};
        fatApUpgradePatchService.deleteDefaulUpgradetPatch(ids );;
    }

    /**
     * 定制终端升级--个性化升级设备查询
     * @author 伍恰  
     * @date 2017年6月29日 下午2:21:07
     */
    @Test
    public void testGetUpgradeDeviceList() {
        Map<String, Object> params = new HashMap<>();
        Page<Map<String, Object>> page = new Page<>();
        fatApUpgradePatchService.getUpgradeDeviceList(params , page);;
    }

    /**
     * 定制终端升级--个性化升级包根据id查询
     * @author 伍恰  
     * @date 2017年6月29日 下午2:24:41
     */
    @Test
    public void testGetPersoUpgradePatchList() {
        Map<String, Object> params = new HashMap<>();
        Page<Map<String, Object>> page = new Page<>();
        fatApUpgradePatchService.getPersonalizUpgradePatchList(params, page);
    }

    /**
     * 定制终端升级--默认升级包根据id查询
     * @author 伍恰  
     * @date 2017年6月29日 下午2:27:34
     */
    @Test
    public void testGetPersoUpgradePatchById() {
        Long id = 1L;
        fatApUpgradePatchService.getDefaulUpgradetPatchById(id);
    }

    /**
     * 定制终端升级--个性化升级包上传
     * @author 伍恰  
     * @date 2017年6月29日 下午2:28:14
     */
    @Test
    public void testAddPersonUpgradePatch() {
        Map<String, Object> params = new HashMap<>();
        fatApUpgradePatchService.addPersonalizedUpgradePatch(params );;
    }

    /**
     * 定制终端升级--个性化升级包删除
     * @author 伍恰  
     * @date 2017年6月29日 下午2:29:27
     */
    @Test
    public void testDeletePersoUpgradePatch() {
        Long[] ids = {1L};
        fatApUpgradePatchService.deletePersonalizedUpgradePatch(ids );;
    }

}

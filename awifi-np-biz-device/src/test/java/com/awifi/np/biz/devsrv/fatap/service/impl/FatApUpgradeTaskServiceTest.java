/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月29日 下午2:36:27
* 创建作者：伍恰
* 文件名称：FatApUpgradeTaskServiceImplTest.java
* 版本：  v1.0
* 功能：定制终端升级任务 相关 测试
* 修改记录：
*/
package com.awifi.np.biz.devsrv.fatap.service.impl;


import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.mockito.InjectMocks;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.devsrv.common.MockBase;

public class FatApUpgradeTaskServiceTest extends MockBase {

    /**
     * 被测试类
     */
    @InjectMocks
    private FatApUpgradeTaskServiceImpl fatApUpgradeTaskServiceImpl;
    
    /**
     * 定制终端升级--个性化升级任务查询
     * @author 伍恰  
     * @date 2017年6月29日 下午2:38:50
     */
    @Test
    public void testGetPersonaUpgradeTaskList() {
        Map<String, Object> params = new HashMap<>();
        Page<Map<String, Object>> page = new Page<>();
        fatApUpgradeTaskServiceImpl.getPersonalizedUpgradeTaskList(params, page);;
    }

    /**
     * 定制终端升级--个性化任务根据id查询
     * @author 伍恰  
     * @date 2017年6月29日 下午2:39:26
     */
    @Test
    public void testGetPersonaUpgradeTaskById() {
        Long id = 1L;
        fatApUpgradeTaskServiceImpl.getPersonalizedUpgradeTaskById(id );
    }

    /**
     * 定制终端升级--个性化升级任务添加
     * @author 伍恰  
     * @date 2017年6月29日 下午2:40:13
     */
    @Test
    public void testAddPersonalizedUpgradeTask() {
        Map<String, Object> params = new HashMap<>();
        params.put("data", "[{}]");
        fatApUpgradeTaskServiceImpl.addPersonalizedUpgradeTask(params );;
    }

    /**
     * 定制终端升级--个性化升级任务删除
     * @author 伍恰  
     * @date 2017年6月29日 下午2:40:43
     */
    @Test
    public void testDeletePersonaUpgradeTask() {
        Long[] ids = {1L};
        fatApUpgradeTaskServiceImpl.deletePersonalizedUpgradeTask(ids );;
    }

}

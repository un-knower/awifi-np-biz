/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月27日 下午8:08:47
* 创建作者：伍恰
* 文件名称：FatApUpgradeTaskServiceImpl.java
* 版本：  v1.0
* 功能：定制终端升级任务 相关
* 修改记录：
*/
package com.awifi.np.biz.devsrv.fatap.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.devsrv.fatap.service.FatApUpgradeTaskService;

@Service("fatApUpgradeTaskService")
public class FatApUpgradeTaskServiceImpl implements FatApUpgradeTaskService{
    
    /**
     * 定制终端升级--个性化升级任务查询
     * @param params 参数 
     * @param page 分页实体
     * @author 伍恰  
     * @date 2017年6月27日 下午8:23:59
     */
    @Override
    public void getPersonalizedUpgradeTaskList(Map<String, Object> params,
            Page<Map<String, Object>> page) {
        // TODO Auto-generated method stub
        
    }

    /**
     * 定制终端升级--个性化任务根据id查询
     * @param id id 
     * @return 异常/参数
     * @author 伍恰  
     * @date 2017年6月27日 下午8:25:39
     */
    @Override
    public Map<String, Object> getPersonalizedUpgradeTaskById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 定制终端升级--个性化升级任务添加
     * @param params 异常/参数
     * @author 伍恰  
     * @date 2017年6月27日 下午8:28:45
     */
    @Override
    public void addPersonalizedUpgradeTask(Map<String, Object> params) {
        String data = (String)params.get("data");
        ValidUtil.valid("任务数组[data]", data, "{'required':true}");
        List<JSONObject> taskJsonArr = JSON.parseArray(data,JSONObject.class);
        for(JSONObject task : taskJsonArr){
            String id = (String)task.get("id");
            ValidUtil.valid("升级包id[id]", id, "{'required':true,'numeric':true}");
            
            String macAddr = (String)task.get("macAddr");
            ValidUtil.valid("mac地址[version]", macAddr, "{'required':true , 'regex':'"+RegexConstants.MAC_PATTERN+"'}");
            
            String merchantName = (String)task.get("merchantName");
            ValidUtil.valid("商户名称[merchantName]", merchantName, "{'required':true}");
            
            String upgradePatchName = (String)task.get("upgradePatchName");
            ValidUtil.valid("升级包名称[upgradePatchName]", upgradePatchName, "{'required':true}");
           
            String corporationId = (String)task.get("corporationId");
            ValidUtil.valid("厂家id[corporationId]", corporationId, "{'required':true,'numeric':true}");
            
            String corporationText = (String)task.get("corporationText");
            ValidUtil.valid("厂家名称[corporationText]", corporationText, "{'required':true}");
            
            String modelId = (String)task.get("modelId");
            ValidUtil.valid("型号id[modelId]", modelId, "{'required':true,'numeric':true}");
            
            String modelText = (String)task.get("modelText");
            ValidUtil.valid("型号名称[modelText]", modelText, "{'required':true}");
            
            String version = (String)task.get("version");
            ValidUtil.valid("版本号[version]", version, "{'required':true , 'regex':'"+RegexConstants.UPGRADETPATCH_VERSION+"'}");
            
            String versionHd = (String)task.get("versionHd");
            ValidUtil.valid("hd版本号[versionHd]", versionHd, "{'required':true, 'regex':'^[a-zA-Z0-9]{1,20}$'}}");
            
        }
    }

    /**
     * 定制终端升级--个性化升级任务删除
     * @param ids id数组
     * @author 伍恰  
     * @date 2017年6月27日 下午8:29:34
     */
    @Override
    public void deletePersonalizedUpgradeTask(Long[] ids) {
        // TODO Auto-generated method stub
        
    }

}

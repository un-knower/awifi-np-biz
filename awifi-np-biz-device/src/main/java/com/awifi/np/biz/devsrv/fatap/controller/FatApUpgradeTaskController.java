/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月27日 下午8:06:59
* 创建作者：伍恰
* 文件名称：FatApUpgradeTaskController.java
* 版本：  v1.0
* 功能： 定制终端升级任务 相关
* 修改记录：
*/
package com.awifi.np.biz.devsrv.fatap.controller;

import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.devsrv.fatap.service.FatApUpgradeTaskService;

@RequestMapping("/devsrv/devices/fatap")
@Controller
public class FatApUpgradeTaskController extends BaseController{
    
    /**
     * 定制终端升级包服务类
     */
    @Resource(name = "fatApUpgradeTaskService")
    private FatApUpgradeTaskService fatApUpgradeTaskService;
    
    /**
     * 定制终端升级--个性化升级任务查询
     * @param access_token 令牌
     * @param params 查询参数
     * @return 分页信息 
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年6月27日 下午8:44:18
     */
    @RequestMapping(value = "/personalizedupgradetask", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getPersonalizedUpgradeTaskList(@RequestParam(value="access_token",required=true)String access_token,@RequestParam(value="params", required = true)Map<String,Object> params)throws Exception{
        Page<Map<String,Object>> page = new Page<Map<String,Object>>();
        this.setPageInfo(params, page);
        fatApUpgradeTaskService.getPersonalizedUpgradeTaskList(params, page);
        return this.successMsg(page);
    }
    
    /**
     * 定制终端升级--个性化任务根据id查询
     * @param access_token 令牌
     * @param id id
     * @return Map<String, Object> 任务信息
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年6月27日 下午8:47:07
     */
    @RequestMapping(value = "/personalizedupgradetask/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getPersonalizedUpgradeTaskById(@RequestParam(value="access_token",required=true)String access_token,@PathVariable(value = "id") String id)throws Exception{
        ValidUtil.valid("主键id", id, "{'required':true,'numeric':'true'}");
        Long taskId = CastUtil.toLong(id);
        return this.successMsg(fatApUpgradeTaskService.getPersonalizedUpgradeTaskById(taskId));
    }
    
    /**
     * 定制终端升级--个性化升级任务添加
     * @param access_token 令牌
     * @param bodyParam 参数
     * @return  异常/参数
     * @author 伍恰  
     * @date 2017年6月27日 下午8:52:20
     */
    @RequestMapping(value="/personalizedupgradetask",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addPersonalizedUpgradeTask(@RequestParam(value="access_token",required=true) String access_token,
            @RequestBody(required=true) Map<String,Object> bodyParam){
        fatApUpgradeTaskService.addPersonalizedUpgradeTask(bodyParam);
        return this.successMsg();
    }
    
    /**
     * 定制终端升级--个性化升级任务删除
     * @param access_token 令牌
     * @param idArr id 数组
     * @return 异常/参数
     * @author 伍恰  
     * @date 2017年6月27日 下午8:54:26
     */
    @RequestMapping(value="/personalizedupgradetask",method=RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> deletePersonalizedUpgradeTask(@RequestParam(value="access_token",required=true)String access_token,@RequestParam(value="idArr",required=true) Long[] idArr){
        fatApUpgradeTaskService.deletePersonalizedUpgradeTask(idArr);
        return this.successMsg();
    }
}

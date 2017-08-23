package com.awifi.np.biz.pub.system.corperation.controller;

import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.awifi.np.biz.api.client.dbcenter.corporation.model.Corporation;
import com.awifi.np.biz.api.client.dbcenter.corporation.util.CorporationClient;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.pub.system.corperation.service.CorpService;

/**
 * 版权所有： 爱WiFi无线运营中心 
 * 创建日期:2017年4月10日 下午3:39:45 
 * 创建作者：范涌涛 
 * 文件名称：CorpController.java
 * 版本： v1.0 功能：厂商型号管理控制层 修改记录：
 */
@Controller
@SuppressWarnings({"rawtypes","unchecked"})
public class CorpController extends BaseController {

    /**厂商服务**/
    @Resource(name = "corpService")
    private CorpService corpService;

    /**
     * 查询 厂商
     * @param accessToken 必填
     * @param params 查询参数 具备此参数时分页查询，不具备具备此参数时返回所有厂商
     * @return 厂商列表
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月17日 下午7:07:06
     */
    @RequestMapping(method = RequestMethod.GET, value = "/pubsrv/corporations")
    @ResponseBody
    public Map queryCorpList(@RequestParam(value = "access_token", required = true) String accessToken,
            @RequestParam(name = "params", required = false) String params) throws Exception {
        Page<Corporation> page = new Page<Corporation>();
        corpService.queryListByParam(params, page);
        return this.successMsg(page);
    }
    
    /**
     * 按ID查厂商
     * @param accessToken 必填
     * @param id 必填 厂商ID
     * @return 厂商信息
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年6月3日 上午10:27:51
     */
    @RequestMapping(method = RequestMethod.GET, value = "/pubsrv/corporations/{id}")
    @ResponseBody
    public Map queryCorpById(@RequestParam(value = "access_token", required = true) String accessToken,
            @PathVariable(value="id",required=true)Long id) throws Exception {
        Map<String,Object> res = CorporationClient.queryCorpById(id);
        return this.successMsg(res);
    }
    

    /**
     * 添加厂商
     * @param accessToken 必填
     * @param params 厂商信息
     * @return 添加结果
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月21日 下午2:37:11
     */
    @RequestMapping(method = RequestMethod.POST, value = "/pubsrv/corporations", produces = "application/json")
    @ResponseBody
    public Map addCorperation(@RequestParam(value = "access_token", required = true) String accessToken,
            @RequestBody(required=true) Map<String,Object> params) throws Exception {
        CorporationClient.addCorperation(params);
        return this.successMsg();
    }
    
    /**
     * 修改厂商信息
     * @param accessToken token必填
     * @param bodyParam 厂商信息
     * @return Map 修改结果
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月21日 下午2:31:42
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/pubsrv/corporations", produces = "application/json")
    @ResponseBody
    public Map updateCorporation(@RequestParam(value = "access_token", required = true) String accessToken,
            @RequestBody(required=true) Map<String,Object> bodyParam) throws Exception {
        
        CorporationClient.updateCorporation(bodyParam);
        return this.successMsg();
    }

    /**
     * 删除厂商
     * @param accessToken 必填
     * @param bodyParam 删除的厂商信息(id)
     * @return 删除结果
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月21日 下午2:32:08
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/pubsrv/corporations", produces = "application/json")
    @ResponseBody
    public Map deleteCorporation(@RequestParam(value = "access_token", required = true) String accessToken,
            @RequestParam(name = "params", required = false) String params) throws Exception {
        Map reqParam=JsonUtil.fromJson(params, Map.class);
        CorporationClient.deleteCorporation(reqParam);
        return this.successMsg();
    }
    
    /**
     * 查询一个厂商下的型号信息
     * @param accessToken 必填
     * @param corpId 厂商ID
     * @param params 非必填;有params参数时为分页查询,没有params时为查询该厂商下所有型号
     * @return 查询结果
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月21日 下午2:35:17
     */
    @RequestMapping(method = RequestMethod.GET, value = "/pubsrv/corporations/{corpId}/models") 
    @ResponseBody
    public Map queryModelList(@RequestParam(value = "access_token", required = true) String accessToken,
            @PathVariable(value="corpId",required=true)Long corpId,
            @RequestParam(name = "params", required = false) String params) throws Exception {
        Page<Map<String,Object>> page = CorporationClient.queryModelList(params,corpId);
        return this.successMsg(page);
    }
    
    /**
     * 根据型号ID查询型号信息
     * @param accessToken 必填
     * @param modelId 型号ID
     * @return 查询结果
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月21日 下午2:49:57
     */
    @RequestMapping(method = RequestMethod.GET, value = "/pubsrv/models/{modelId}") 
    @ResponseBody
    public Map queryModeById(@RequestParam(value = "access_token", required = true) String accessToken,
            @PathVariable(value="modelId",required=true)Long modelId) throws Exception {
        Map<String,Object> res = CorporationClient.queryModelById(modelId);
        return this.successMsg(res);
    }
    
    /**
     * 特定厂商下新增型号
     * @param accessToken 必填
     * @param corpId 厂商ID
     * @param params 型号参数
     * @return 新增结果
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月21日 下午4:52:52
     */
    @RequestMapping(method = RequestMethod.POST, value = "/pubsrv/corporations/{corpId}/models", produces = "application/json")
    @ResponseBody
    public Map addModel(@RequestParam(value = "access_token", required = true) String accessToken,
            @PathVariable(value="corpId",required=true)Long corpId,
            @RequestBody(required=true) Map<String,Object> params) throws Exception {
        params.put("corpId", corpId);
        CorporationClient.addModel(params);
        return this.successMsg();
    }
    /**
     * 修改型号
     * @param accessToken 必填
     * @param corpId 厂商ID
     * @param modelId 型号ID
     * @param bodyParam 型号参数
     * @return 修改结果
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月24日 上午10:50:49
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/pubsrv/corporations/{corpId}/models/{modelId}", produces = "application/json")
    @ResponseBody
    public Map updateModel(@RequestParam(value = "access_token", required = true) String accessToken,
            @PathVariable(value="corpId",required=true)Long corpId,
            @PathVariable(value="modelId",required=true)Long modelId,
            @RequestBody(required=true) Map<String,Object> bodyParam) throws Exception {
        
        bodyParam.put("corpId", corpId);
        bodyParam.put("id", modelId);//型号id
        CorporationClient.updateModel(bodyParam);
        return this.successMsg();
    }
    /**
     * 删除型号
     * @param accessToken 必填
     * @param modelId 型号ID
     * @return 删除结果
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年4月24日 上午10:49:42
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/pubsrv/models/{modelId}", produces = "application/json")
    @ResponseBody
    public Map deleteModel(@RequestParam(value = "access_token", required = true) String accessToken,
            @PathVariable(value="modelId",required=true)Long modelId) throws Exception {
        CorporationClient.deleteModel(modelId);
        return this.successMsg();
    }
}

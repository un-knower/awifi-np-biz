package com.awifi.np.biz.devsrv.fatap.controller;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubPlatform;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.devsrv.fatap.service.PlatFormBaseService;

@SuppressWarnings({"rawtypes","unchecked"})
@RequestMapping("/devsrv")
@Controller
public class PlatFormController extends BaseController{
    /**
     * 省分平台服务类
     */
    @Resource(name="platFormBaseService")
    private PlatFormBaseService platFormBaseService;
    /**
    * @Title: showPlatForm
    * @Description: 省分平台设备查询 
    * @param access_token 
    * @param params {
       "pageNo": 1//页码，默认为1，可以为空
       "pageSize": 10//每页记录数，默认为10，可以为空
       "platformName":"xxx" //平台名称，可以为空
      }
    * @param request   HttpServletRequest
    * @return  
    * @throws Exception  参数描述
    * @return Map 返回类型描述
    * @throws
    * @data 2017年5月31日 下午4:02:31
    * @author wuqia
     */
    @RequestMapping(value="/devices/platform",method=RequestMethod.GET)
    @ResponseBody
    public Map showPlatForm(@RequestParam(value="access_token",required=true)String access_token,@RequestParam(value="params")String params,HttpServletRequest request)throws Exception{
        Integer pageNo=null;
        Integer pageSize=null;
        String platformName=null;
        if(!StringUtils.isBlank(params)){
            Map<String,Object> paramsMap=JsonUtil.fromJson(params, Map.class);
            pageNo=(Integer) paramsMap.get("pageNo");
            pageSize=(Integer)paramsMap.get("pageSize");
            platformName=(String) paramsMap.get("platformName");
        }
        if(pageNo==null){
            pageNo=1;//默认为第一页
        }
        if(pageSize==null){
            pageSize=10;//默认每页记录数为10
        }
        SessionUser sessionUser=SessionUtil.getCurSessionUser(request);
        //SessionUser sessionUser=new SessionUser();
        Page<CenterPubPlatform> page=platFormBaseService.listPlatForm(pageNo, pageSize, platformName, sessionUser);
        return this.successMsg(page);
    }
    
    /**
     * 
    * @Title: findByPlatformId
    * @Description: 省分平台根据id查询接口
    * @param access_token 令牌
    * @param id  
    * @return
    * @throws Exception  参数描述
    * @return Map 返回类型描述
    * @throws
    * @data 2017年5月31日 下午4:04:47
    * @author wuqia
     */
    @RequestMapping(value="/devices/platform/{id}",method=RequestMethod.GET)
    @ResponseBody
    public Map findByPlatformId(@RequestParam(value="access_token",required=true)String access_token,@PathVariable(value="id",required=true)String id)throws Exception{
    	Map platForm=platFormBaseService.queryPlatformById(id);
        return platForm;
    }
    
    /**
     * 
    * @Title: editPlatForm
    * @Description: 省分平台编辑接口
    * @param access_token 令牌
    * @param id id号
    * @param bodyParam 其他属性
    * @return
    * @throws Exception  参数描述
    * @return Map 返回类型描述
    * @throws
    * @data 2017年5月31日 下午4:05:16
    * @author wuqia
     */
    @RequestMapping(value="/devices/platform/{id}",method=RequestMethod.PUT,produces="application/json")
    @ResponseBody
    public Map editPlatForm(@RequestParam(value="access_token",required=true)String access_token,@PathVariable(value="id",required=true)String id,@RequestBody(required=true) Map<String,Object>bodyParam)throws Exception{
        Map<String,Object> params=new HashMap<String,Object>();
        Long province=CastUtil.toLong(bodyParam.get("province"));
        if(province==null){
            throw new ValidException("E2301314", MessageUtil.getMessage("E2301314", "provicne"));//省分不能为空
        }
        String platformName=(String) bodyParam.get("platformName");
        if(StringUtils.isBlank(platformName)){
            throw new ValidException("E2301314", MessageUtil.getMessage("E2301314", "platformName"));//平台名称不能为空
        }
        String platformType=(String)bodyParam.get("platformType");
        if(StringUtils.isBlank(platformType)){
            throw new ValidException("E2301314", MessageUtil.getMessage("E2301314", "platformType"));//平台类型不能为空
        }
        params.put("id", id);
        params.put("platformName", platformName);
        params.put("platformType", platformType);
        params.put("province", province);
        params.put("city", bodyParam.get("city"));
        params.put("county", bodyParam.get("county"));
        params.put("portalIp", bodyParam.get("portalIp"));
        params.put("authIp", bodyParam.get("authIp"));
        params.put("platformIp", bodyParam.get("platformIp"));
        params.put("devBusIp", bodyParam.get("devBusIp"));
        params.put("devBusPort", bodyParam.get("devBusPort"));
        params.put("administrant", bodyParam.get("administrant"));
        params.put("administrantPhone", bodyParam.get("administrantPhone"));
        params.put("portalDomain", bodyParam.get("portalDomain"));
        params.put("authDomain", bodyParam.get("authDomain"));
        params.put("platformDomain", bodyParam.get("platformDomain"));
        params.put("portalPort", bodyParam.get("portalPort"));
        params.put("authPort", bodyParam.get("authPort"));
        params.put("platformPort", bodyParam.get("platformPort"));
        params.put("url", bodyParam.get("url"));
        params.put("remark", bodyParam.get("remark"));
        platFormBaseService.editPlatForm(params);
        return this.successMsg();
    }
    
    /**
     * 
    * @Title: addPlatForm
    * @Description: 省分平台新增接口
    * @param access_token 令牌
    * @param bodyParam 相关属性
    * @param request 请求
    * @return
    * @throws Exception  参数描述
    * @return Map 返回类型描述
    * @throws
    * @data 2017年5月31日 下午4:05:42
    * @author wuqia
     */
    @RequestMapping(value="/devices/platform",method=RequestMethod.POST,produces="application/json")
    @ResponseBody
    public Map addPlatForm(@RequestParam(value="access_token",required=true)String access_token,@RequestBody(required=true) Map<String,Object> bodyParam,HttpServletRequest request)throws Exception{
        Map<String,Object> params=new HashMap<>();
        Long province=CastUtil.toLong(bodyParam.get("province"));
        if(province==null){
            throw new ValidException("E2301314", MessageUtil.getMessage("E2301314", "provicne"));//省分不能为空
        }
        String platformName=(String) bodyParam.get("platformName");
        if(StringUtils.isBlank(platformName)){
            throw new ValidException("E2301314", MessageUtil.getMessage("E2301314", "platformName"));//平台名称不能为空
        }
        String platformType=(String)bodyParam.get("platformType");
        if(StringUtils.isBlank(platformType)){
            throw new ValidException("E2301314", MessageUtil.getMessage("E2301314", "platformType"));//平台类型不能为空
        }
        params.put("platformName", platformName);
        params.put("platformType", platformType);
        params.put("province", bodyParam.get("province"));
        params.put("city", bodyParam.get("city"));
        params.put("county", bodyParam.get("county"));
        params.put("portalIp", bodyParam.get("portalIp"));
        params.put("authIp", bodyParam.get("authIp"));
        params.put("platformIp", bodyParam.get("platformIp"));
        params.put("devBusIp", bodyParam.get("devBusIp"));
        params.put("devBusPort", bodyParam.get("devBusPort"));
        params.put("administrant", bodyParam.get("administrant"));
        params.put("administrantPhone", bodyParam.get("administrantPhone"));
        params.put("url", bodyParam.get("url"));
        params.put("remark", bodyParam.get("remark"));
        params.put("portalDomain", bodyParam.get("portalDomain"));
        params.put("authDomain", bodyParam.get("authDomain"));
        params.put("platformDomain", bodyParam.get("platformDomain"));
        params.put("portalPort", bodyParam.get("portalPort"));
        params.put("authPort", bodyParam.get("authPort"));
        params.put("platformPort", bodyParam.get("platformPort"));
        platFormBaseService.addPlatform(params);
        return this.successMsg();
    }
    
    /**
     * 
    * @Title: delePlatform
    * @Description: 省分平台删除接口
    * @param access_token 令牌
    * @param idArr id数组  Long[]
    * @return
    * @throws Exception  参数描述
    * @return Map 返回类型描述
    * @throws
    * @data 2017年5月31日 下午4:06:08
    * @author wuqia
     */
    @RequestMapping(value="/devices/platform",method=RequestMethod.DELETE)
    @ResponseBody
    public Map delePlatform(@RequestParam(value="access_token",required=true)String access_token,@RequestParam(value="idArr",required=true) Long[] idArr)throws Exception{
        ValidUtil.valid("省分平台的id数组", idArr, "arrayNotBlank");// 检验数组内部不能有空值
        StringBuffer erro = new StringBuffer();
        for(Long id : idArr){
            try {
                platFormBaseService.deletePlatform(id);
            } catch (Exception e) {
                String msg = "【"+id+"】删除失败："+e.getMessage()+" ";
                erro.append(msg);
                logger.error(msg);
            }
        }
        if(erro.length()>0){
            return this.failMsg("E2301313", erro);
        }
        return this.successMsg();
    }
}

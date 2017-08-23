/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年3月24日 下午6:50:19
* 创建作者：许小满
* 文件名称：SysConfigController.java
* 版本：  v1.0
* 功能：系统参数配置--控制层相关代码
* 修改记录：
*/
package com.awifi.np.biz.pub.system.sysconfig.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.constants.RedisConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.system.sysconfig.model.SysConfig;
import com.awifi.np.biz.common.system.sysconfig.service.SysConfigService;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;

@Controller
@SuppressWarnings("unchecked")
@RequestMapping(value="/pubsrv")
public class SysConfigController extends BaseController {

    /** 系统参数配置业务层 */
    @Resource(name = "sysConfigService")
    private SysConfigService sysConfigService;
    
    /**
     * 系统参数配置--分页查询接口
     * @param accessToken 安全令牌
     * @param params json格式参数
     * @return json
     * @author 许小满  
     * @date 2017年3月24日 下午7:12:25
     */
    @RequestMapping(method=RequestMethod.GET, value="/sysconfigs")
    @ResponseBody
    public Map<String,Object> getListByParam(
            @RequestParam(value="access_token",required=true)String accessToken,//安全令牌，不允许为空
            @RequestParam(value="params",required=true)String params//json格式参数
    ){
        Map<String,Object> paramsMap = JsonUtil.fromJson(params, Map.class);//请求参数 json格式
        Integer pageSize = CastUtil.toInteger(paramsMap.get("pageSize"));//每页记录数
        Integer maxPageSize = Integer.parseInt(SysConfigUtil.getParamValue("page_maxsize"));//每页记录最大数
        ValidUtil.valid("每页数量[pageSize]", pageSize, "{'required':true,'numeric':{'max':"+maxPageSize+"}}");//校验每页记录数 不能大于最大数
        String keywords = (String) paramsMap.get("keywords");//关键字，允许为空， 支持[名称|key|value|备注]模糊查询
        Integer pageNo = ObjectUtils.defaultIfNull(CastUtil.toInteger(paramsMap.get("pageNo")), 1);//页码，数字，允许为空，默认第1页
        Page<SysConfig> page = new Page<SysConfig>(pageNo,pageSize);
        sysConfigService.getListByParam(page, keywords);
        return this.successMsg(page);
    }
    
    /**
     * 系统参数配置添加接口
     * @param accessToken 安全令牌，不允许为空
     * @param bodyParams 请求体参数
     * @return json
     * @author 许小满  
     * @date 2017年5月17日 下午11:57:39
     */
    @RequestMapping(method=RequestMethod.POST, value="/sysconfig")
    @ResponseBody
    public Map<String,Object> add(
            @RequestParam(value="access_token",required=true)String accessToken,//安全令牌，不允许为空
            @RequestBody(required=true) Map<String,Object> bodyParams//请求体参数
    ){
        String aliasName = (String)bodyParams.get("aliasName");//别名
        String paramKey = (String)bodyParams.get("paramKey");//参数键
        String paramValue = (String)bodyParams.get("paramValue");//参数值
        String remark = (String)bodyParams.get("remark");//备注
        
        //请求参数校验
        ValidUtil.valid("名称[aliasName]", aliasName, "required");//别名
        ValidUtil.valid("参数键[paramKey]", paramKey, "required");//参数键
        ValidUtil.valid("参数值[paramValue]", paramValue, "required");//参数值
        
        sysConfigService.add(aliasName, paramKey, paramValue, remark);
        return this.successMsg();
    }
    
    /**
     * 系统参数配置添加接口
     * @param accessToken 安全令牌，不允许为空
     * @param id 系统参数配置表主键id
     * @param bodyParams 请求体参数
     * @return json
     * @author 许小满  
     * @date 2017年5月17日 下午11:57:39
     */
    @RequestMapping(method=RequestMethod.PUT, value="/sysconfig/{id}")
    @ResponseBody
    public Map<String,Object> update(
            @RequestParam(value="access_token",required=true)String accessToken,//安全令牌，不允许为空
            @PathVariable(name="id") Long id,//系统参数配置表主键id
            @RequestBody(required=true) Map<String,Object> bodyParams//请求体参数
    ){
        String aliasName = (String)bodyParams.get("aliasName");//别名
        String paramKey = (String)bodyParams.get("paramKey");//参数键
        String paramValue = (String)bodyParams.get("paramValue");//参数值
        String remark = (String)bodyParams.get("remark");//备注
        
        //请求参数校验
        ValidUtil.valid("主键id[id]", id, "{'required':true,'numeric':true}");//主键id
        ValidUtil.valid("名称[aliasName]", aliasName, "required");//别名
        ValidUtil.valid("参数键[paramKey]", paramKey, "required");//参数键
        ValidUtil.valid("参数值[paramValue]", paramValue, "required");//参数值
        
        sysConfigService.update(id, aliasName, paramKey, paramValue, remark);
        RedisUtil.del(RedisConstants.SYSTEM_CONFIG + paramKey);
        return this.successMsg();
    }
    
    /**
     * 删除
     * @param accessToken 安全令牌，不允许为空
     * @param id 系统参数配置表主键id
     * @return json
     * @author 许小满  
     * @date 2017年5月18日 上午12:31:26
     */
    @RequestMapping(method=RequestMethod.DELETE, value="/sysconfig/{id}")
    @ResponseBody
    public Map<String,Object> delete(
            @RequestParam(value="access_token",required=true)String accessToken,//安全令牌，不允许为空
            @PathVariable(name="id") Long id//系统参数配置表主键id
    ){
        //请求参数校验
        ValidUtil.valid("主键id[id]", id, "{'required':true,'numeric':true}");//主键id
        
        sysConfigService.delete(id);
        return this.successMsg();
    }
    
    /**
     * 通过id查询配置信息
     * @param accessToken 安全令牌，不允许为空
     * @param id 系统参数配置表主键id
     * @return json
     * @author 许小满  
     * @date 2017年5月18日 上午12:31:26
     */
    @RequestMapping(method=RequestMethod.GET, value="/sysconfig/{id}")
    @ResponseBody
    public Map<String,Object> getById(
            @RequestParam(value="access_token",required=true)String accessToken,//安全令牌，不允许为空
            @PathVariable(name="id") Long id//系统参数配置表主键id
    ){
        //请求参数校验
        ValidUtil.valid("主键id[id]", id, "{'required':true,'numeric':true}");//主键id
        
        SysConfig sysConfig = sysConfigService.getById(id);
        return this.successMsg(sysConfig);
    }
    
}

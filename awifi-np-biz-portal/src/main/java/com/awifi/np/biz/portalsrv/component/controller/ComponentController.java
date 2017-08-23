/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月11日 下午3:10:56
* 创建作者：周颖
* 文件名称：ComponentController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.portalsrv.component.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.toe.portal.component.model.Component;
import com.awifi.np.biz.toe.portal.component.service.ComponentService;

@Controller
@RequestMapping("/portalsrv")
@SuppressWarnings("unchecked")
public class ComponentController extends BaseController {
    
    /**组件服务*/
    @Resource(name = "componentService")
    private ComponentService componentService;
    
    /**
     * 组件列表
     * @param accessToken access_token
     * @param params 参数
     * @return 结果
     * @author 周颖  
     * @date 2017年4月11日 下午3:54:49
     */
    @RequestMapping(method = RequestMethod.GET,value = "/components")
    @ResponseBody
    public Map<String,Object> getListByParam(@RequestParam(value="access_token",required=true)String accessToken,
                                             @RequestParam(value="params",required=true)String params){
        Map<String,Object> paramsMap = JsonUtil.fromJson(params, Map.class);//请求参数 json格式
        Integer pageSize = CastUtil.toInteger(paramsMap.get("pageSize"));//每页记录数
        Integer maxPageSize = Integer.parseInt(SysConfigUtil.getParamValue("page_maxsize"));//每页记录最大数
        ValidUtil.valid("每页数量[pageSize]", pageSize, "{'required':true,'numeric':{'max':"+maxPageSize+"}}");
        String keywords = (String) paramsMap.get("keywords");//获取组件名称关键字 可为空
        Integer pageNo = CastUtil.toInteger(paramsMap.get("pageNo"));//页码
        if(pageNo == null){//如果为空，默认第一页
            pageNo = 1;
        }
        Page<Component> page = new Page<Component>(pageNo,pageSize);
        componentService.getListByParam(page,keywords);
        return this.successMsg(page);
    } 
    
    /**
     * 组件添加
     * @param accessToken access_token
     * @param request 请求
     * @return 结果
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年4月14日 上午10:01:58
     */
    @RequestMapping(method=RequestMethod.POST, value="/component")
    @ResponseBody
    public Map<String,Object> add(@RequestParam(value="access_token",required=true)String accessToken,HttpServletRequest request) throws Exception{
        componentService.add(request);
        return this.successMsg();
    }
    
    /**
     * 组件编辑
     * @param accessToken access_token
     * @param request 请求
     * @param id 组件id
     * @return 结果
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年4月14日 下午1:13:03
     */
    @RequestMapping(method=RequestMethod.PUT, value="/component/{id}")
    @ResponseBody
    public Map<String,Object> edit(@RequestParam(value="access_token",required=true)String accessToken,HttpServletRequest request,@PathVariable Long id) throws Exception{
        componentService.edit(request,id);
        return this.successMsg();
    }
    
    /**
     * 组件详情
     * @param accessToken access_token
     * @param id 组件id
     * @return 详情
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年4月14日 下午5:01:16
     */
    @RequestMapping(method=RequestMethod.GET, value="/component/{id}")
    @ResponseBody
    public Map<String,Object> getById(@RequestParam(value="access_token",required=true)String accessToken,@PathVariable Long id) throws Exception{
        Component component = componentService.getById(id);
        return this.successMsg(component);
    }
    
    /**
     * 组件列表 按类型 （站点制作左侧组件列表）
     * @param accessToken access_token
     * @param params 参数
     * @param type 站点类型
     * @return 结果
     * @author 周颖  
     * @throws Exception 
     * @date 2017年5月3日 上午9:33:28
     */
    @RequestMapping(method=RequestMethod.GET, value="/components/type/{type}")
    @ResponseBody
    public Map<String,Object> getListByType(
            @RequestParam(value="access_token",required=true)String accessToken,
            @RequestParam(value="params",required=true)String params,
            @PathVariable Integer type) throws Exception{
        Map<String,Object> paramsMap = JsonUtil.fromJson(params, Map.class);//请求参数 json格式
        Long merchantId = CastUtil.toLong(paramsMap.get("merchantId"));//商户id
        List<Component> componentList = componentService.getListByType(merchantId,type);
        return this.successMsg(componentList);
    }
    
    /**
     * 图片组件保存图片接口
     * @param request 请求
     * @return 结果
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年6月13日 下午7:32:45
     */
    @RequestMapping(method=RequestMethod.POST, value = "/component/picupload")
    @ResponseBody
    public Map<String,Object> picUpload(HttpServletRequest request) throws Exception{
        String imgSrc = componentService.picUpload(request);
        Map<String,Object> data = new HashMap<String,Object>();
        data.put("path", StringUtils.isNotBlank(imgSrc) ? imgSrc : StringUtils.EMPTY);//返回
        return this.successMsg(data);
    }
}

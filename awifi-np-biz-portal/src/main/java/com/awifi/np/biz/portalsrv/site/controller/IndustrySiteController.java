/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月18日 下午3:58:39
* 创建作者：周颖
* 文件名称：IndustrySiteController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.portalsrv.site.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.awifi.np.biz.toe.portal.site.model.Site;
import com.awifi.np.biz.toe.portal.site.service.SiteService;

@Controller
@RequestMapping("/portalsrv")
@SuppressWarnings("unchecked")
public class IndustrySiteController extends BaseController {

    /**站点服务*/
    @Resource(name = "siteService")
    private SiteService siteService;
    
    /**
     * 行业站点列表
     * @param accessToken access_token
     * @param params 参数
     * @return 列表
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年4月18日 上午10:20:56
     */
    @RequestMapping(method = RequestMethod.GET,value = "/sites/industry")
    @ResponseBody
    public Map<String,Object> getIndustryListByParam(@RequestParam(value="access_token",required=true)String accessToken,
                                             @RequestParam(value="params",required=true)String params) throws Exception{
        Map<String,Object> paramsMap = JsonUtil.fromJson(params, Map.class);//请求参数 json格式
        Integer pageSize = CastUtil.toInteger(paramsMap.get("pageSize"));//每页记录数
        Integer maxPageSize = Integer.parseInt(SysConfigUtil.getParamValue("page_maxsize"));//每页记录最大数
        ValidUtil.valid("每页数量[pageSize]", pageSize, "{'required':true,'numeric':{'max':"+maxPageSize+"}}");
        String keywords = (String) paramsMap.get("siteName");//站点名称关键字 可为空
        String priIndustryCode = (String)paramsMap.get("priIndustryCode");//一级行业编号 可为空
        String secIndustryCode = (String)paramsMap.get("secIndustryCode");//二级行业编号 可为空
        Integer pageNo = CastUtil.toInteger(paramsMap.get("pageNo"));//页码
        if(pageNo == null){//如果为空，默认第一页
            pageNo = 1;
        }
        Page<Site> page = new Page<Site>(pageNo,pageSize);
        siteService.getIndustryListByParam(page,keywords,priIndustryCode,secIndustryCode);
        return this.successMsg(page);
    }
    
    /**
     * 添加行业站点
     * @param accessToken access_token
     * @param bodyParam 参数
     * @param request 请求
     * @return 结果
     * @author 周颖  
     * @throws Exception 
     * @date 2017年4月21日 下午2:11:48
     */
    @RequestMapping(method = RequestMethod.POST,value = "/site/industry",produces="application/json")
    @ResponseBody
    public Map<String,Object> add(@RequestParam(value="access_token",required=true)String accessToken,
            @RequestBody(required=true) Map<String,Object> bodyParam,
            HttpServletRequest request) throws Exception{
        Long siteId = siteService.addIndustry(bodyParam,request);
        Map<String,Long> siteIdMap = new HashMap<String,Long>();
        siteIdMap.put("id", siteId);
        return this.successMsg(siteIdMap);
    }
    
    /**
     * 编辑行业站点
     * @param accessToken access_token
     * @param bodyParam 参数
     * @param request 请求
     * @param id 站点id
     * @return 结果
     * @author 周颖  
     * @throws Exception 
     * @date 2017年4月21日 下午2:11:48
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/site/{id}/industry",produces="application/json")
    @ResponseBody
    public Map<String,Object> update(@RequestParam(value="access_token",required=true)String accessToken,
            @RequestBody(required=true) Map<String,Object> bodyParam,
            HttpServletRequest request,@PathVariable Long id) throws Exception{
        siteService.updateIndustry(id,bodyParam,request);
        return this.successMsg();
    }

    /**
     * 删除行业站点
     * @param accessToken access_token
     * @param id 站点id
     * @return 结果
     * @author 周颖  
     * @date 2017年4月25日 下午7:33:58
     */
    @RequestMapping(method = RequestMethod.DELETE,value = "/site/{id}/industry")
    @ResponseBody
    public Map<String,Object> delete(@RequestParam(value="access_token",required=true)String accessToken,@PathVariable Long id){
        siteService.clearIndustrySiteCache(id);//清空行业站点对应的redis缓存
        siteService.delete(id);
        return this.successMsg();
    }
}

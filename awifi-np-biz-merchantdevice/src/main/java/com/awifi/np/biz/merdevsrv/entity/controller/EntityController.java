package com.awifi.np.biz.merdevsrv.entity.controller;

import java.io.File;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.api.client.dbcenter.device.entity.model.EntityInfo;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.merdevsrv.entity.service.EntityService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月8日 上午8:49:10
 * 创建作者：亢燕翔
 * 文件名称：EntityController.java
 * 版本：  v1.0
 * 功能：  实体设备控制层
 * 修改记录：
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/merdevsrv")
public class EntityController extends BaseController{

    /**实体业务层*/
    @Resource(name = "entityService")
    private EntityService entityService;
    
    /**
     * 设备监控列表
     * @param request 请求
     * @param access_token 安全令牌
     * @param params 请求参数
     * @return map
     * @author 亢燕翔
     * @throws Exception 
     * @date 2017年2月8日 上午8:53:19
     */
    @ResponseBody
    @RequestMapping(value="/entityinfos", method=RequestMethod.GET)
    public Map getEntityInfoListByMerId(HttpServletRequest request,@RequestParam(value="access_token",required=true) String access_token,
            @RequestParam(name="params",required=true) String params) throws Exception{
        Page<EntityInfo> page = new Page<EntityInfo>();
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        entityService.getEntityInfoListByMerId(sessionUser,params,page);
        return this.successMsg(page);
    }

    /**
     * 设备监控导出
     * @param request 请求
     * @param response 响应
     * @param access_token 安全令牌
     * @param params 参数
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年5月3日 上午9:49:02
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/entityinfos/xls", method=RequestMethod.GET)
    public void export(HttpServletRequest request,HttpServletResponse response, @RequestParam(value="access_token",required=true) String access_token,
            @RequestParam(name="params",required=true) String params) throws Exception{
        Map<String, Object> paramsMap = JsonUtil.fromJson(params, Map.class);
        Long merchantId = CastUtil.toLong(paramsMap.get("merchantId"));
        String ssid = (String) paramsMap.get("ssid");
        String mac = (String) paramsMap.get("devMac");
        Long provinceId = CastUtil.toLong(paramsMap.get("provinceId"));
        Long cityId = CastUtil.toLong(paramsMap.get("cityId"));
        Long areaId = CastUtil.toLong(paramsMap.get("areaId"));
//        Long orgId = OrgUtil.getCurOrgId(request);
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        String path = request.getServletContext().getRealPath(File.separator + "file" + File.separator + "temp");
        entityService.export(provinceId,cityId,areaId,sessionUser,response,merchantId,ssid,mac,path);
    }
    
    
    /**
     * 设备状态刷新
     * @param request 请求
     * @param response 响应
     * @param access_token 令牌
     * @param params 入参
     * @throws Exception 异常
     * @return map
     * @author 王冬冬  
     * @date 2017年5月31日 下午2:01:44
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/entity/status", method=RequestMethod.GET)
    public Map deviceStatusRefresh(HttpServletRequest request,HttpServletResponse response, @RequestParam(value="access_token",required=true) String access_token,
            @RequestParam(name="params",required=true) String params) throws Exception{
        Map<String, Object> paramsMap = JsonUtil.fromJson(params, Map.class);
        String devMac = (String) paramsMap.get("devMac");
        /*数据校验*/
        ValidUtil.valid("设备mac[devMac]", devMac, "{'required':true}");
        EntityInfo info=new EntityInfo();
        //info.setDevMac(devMac);
        entityService.deviceStatusRefresh(info,devMac);
        return this.successMsg(info);
    }
}

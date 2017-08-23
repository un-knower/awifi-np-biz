package com.awifi.np.biz.pub.system.location.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.api.client.dbcenter.location.util.LocationClient;
import com.awifi.np.biz.common.base.constants.RedisConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.pub.system.location.service.LocationService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月22日 上午8:46:38
 * 创建作者：周颖
 * 文件名称：LocationController.java
 * 版本：  v1.0
 * 功能：地区控制层
 * 修改记录：
 */
@Controller
@SuppressWarnings("rawtypes")
public class LocationController extends BaseController {

    /**地区服务层*/
    @Resource(name = "locationService")
    private LocationService locationService;
    
    /**
     * 地区缓存
     * @param accessToken access_token
     * @return 结果
     * @author 周颖  
     * @throws Exception 
     * @date 2017年1月22日 上午9:26:15
     */
    @RequestMapping(method = RequestMethod.GET,value = "/pubsrv/location/cache")
    @ResponseBody
    public Map cache(@RequestParam(value="access_token",required=true)String accessToken) throws Exception{
        LocationClient.cache();//缓存地区数据
        return this.successMsg();//返回
    }
    
    /**
     * 清除地区缓存
     * @param accessToken access_token
     * @return 结果
     * @author 周颖  
     * @date 2017年1月22日 上午11:09:01
     */
    @RequestMapping(method = RequestMethod.GET,value = "/pubsrv/location/cache/clear")
    @ResponseBody
    public Map cacheClear(@RequestParam(value="access_token",required=true)String accessToken){
        String key = RedisConstants.LOCATION + "*";//生成key
        Set<String> keySet = RedisUtil.keys(key);//批量获取key
        Long count = RedisUtil.delBatch(keySet);//批量删除
        logger.debug("提示：redis共删除 "+ count +" 条数据");
        return this.successMsg();//返回
    }
    
    /**
     * 获取所有省
     * @param accessToken access_token
     * @param request 请求
     * @return 省
     * @author 周颖  
     * @throws Exception 
     * @date 2017年1月22日 下午1:48:35
     */
    @RequestMapping(method = RequestMethod.GET,value = "/pubsrv/provinces")
    @ResponseBody
    public Map getProvinces(@RequestParam(value="access_token",required=true)String accessToken, HttpServletRequest request) throws Exception{
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        List<Map<String,Object>> provinceMap = locationService.getProvinces(sessionUser);//获取所有省
        return this.successMsg(provinceMap);
    }
    
    /**
     * 获取所有市
     * @param accessToken access_token
     * @param request 请求
     * @param parentId 省id
     * @return 市
     * @throws Exception 
     * @author 周颖  
     * @date 2017年1月22日 下午3:03:30
     */
    @RequestMapping(method = RequestMethod.GET,value = "/pubsrv/cities")
    @ResponseBody
    public Map getCities(@RequestParam(value="access_token",required=true)String accessToken, HttpServletRequest request,
                         @RequestParam(value="parentid",required=true) String parentId) throws Exception{
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        List<Map<String,Object>> cityMap = locationService.getCities(sessionUser,parentId);//获取所有市
        return this.successMsg(cityMap);
    }
    
    /**
     * 获取所有区县
     * @param accessToken access_token
     * @param request 请求
     * @param parentId 市id
     * @return 区县
     * @throws Exception 
     * @author 周颖  
     * @date 2017年1月23日 上午9:07:37
     */
    @RequestMapping(method = RequestMethod.GET,value = "/pubsrv/areas")
    @ResponseBody
    public Map getAreas(@RequestParam(value="access_token",required=true)String accessToken, HttpServletRequest request,
                         @RequestParam(value="parentid",required=true) String parentId) throws Exception{
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        List<Map<String,Object>> areaMap = locationService.getAreas(sessionUser,parentId);//获取所有区县
        return this.successMsg(areaMap);
    }
}
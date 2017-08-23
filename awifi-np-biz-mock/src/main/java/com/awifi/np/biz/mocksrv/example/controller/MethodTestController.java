package com.awifi.np.biz.mocksrv.example.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.controller.BaseController;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月9日 上午9:24:32
 * 创建作者：许小满
 * 文件名称：MethodTestController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Controller
@RequestMapping("/mocksrv/method")
public class MethodTestController extends BaseController{

    /**
     * get 测试
     * @param access_token token
     * @return json
     * @author 许小满  
     * @date 2017年1月9日 上午9:33:35
     */
    @RequestMapping(method=RequestMethod.GET,value="/get")
    @ResponseBody
    public Map<String,Object> get(@RequestParam(value="access_token", required=true) String access_token){
        logger.debug("开始调用 MethodTestController.get()...........");
        logger.debug("access_token=" + access_token);
        return packageResult();
    }
    
    /**
     * post 测试
     * @param access_token token
     * @return json
     * @author 许小满  
     * @date 2017年1月9日 上午9:33:35
     */
    @RequestMapping(method=RequestMethod.POST,value="/post")
    @ResponseBody
    public Map<String,Object> post(@RequestParam(value="access_token", required=true) String access_token){
        logger.debug("开始调用 MethodTestController.post()...........");
        logger.debug("access_token=" + access_token);
        return packageResult();
    }
    
    /**
     * put 测试
     * @param access_token token
     * @param id id
     * @return json
     * @author 许小满  
     * @date 2017年1月9日 上午9:33:35
     */
    @RequestMapping(method=RequestMethod.PUT,value="/put/{id}")
    @ResponseBody
    public Map<String,Object> put(
            @RequestParam(value="access_token", required=true) String access_token,
            @PathVariable(required=true) String id){
        logger.debug("开始调用 MethodTestController.put()...........");
        logger.debug("access_token=" + access_token);
        logger.debug("id=" + id);
        return packageResult();
    }
    
    /**
     * delete 测试
     * @param access_token token
     * @param id id
     * @return json
     * @author 许小满  
     * @date 2017年1月9日 上午9:33:35
     */
    @RequestMapping(method=RequestMethod.DELETE,value="/delete/{id}")
    @ResponseBody
    public Map<String,Object> delete(
            @RequestParam(value="access_token", required=true) String access_token,
            @PathVariable(required=true) String id){
        logger.debug("开始调用 MethodTestController.delete()...........");
        logger.debug("access_token=" + access_token);
        logger.debug("id=" + id);
        return packageResult();
    }
    
    /**
     * body 测试
     * @param access_token token
     * @param params params
     * @param request request
     * @return json
     * @author 许小满  
     * @date 2017年1月9日 上午9:33:35
     */
    @RequestMapping(method=RequestMethod.POST,value="/body",consumes="application/json")
    @ResponseBody
    public Map<String,Object> body(
            @RequestParam(value="access_token", required=true) String access_token,
            @RequestBody(required=true) Map<String,Object> params,
            HttpServletRequest request){
        logger.debug("开始调用 MethodTestController.post.body()...........");
        logger.debug("access_token=" + access_token);
        logger.debug("params=" + params);
        String accept = request.getHeader("Accept");
        String contenType = request.getHeader("Content-type");
        logger.debug("accept=" + accept);
        logger.debug("contenType=" + contenType);
        return packageResult();
    }
    
 
    /**
     * 封装结果
     * @return map
     * @author 许小满  
     * @date 2017年1月9日 上午9:34:49
     */
    private Map<String,Object> packageResult(){
        List<String> dataList = new ArrayList<String>();
        dataList.add("aaaaaaaa");
        dataList.add("bbbbbbbb");
        Map<String,Object> resultMap = new LinkedHashMap<String,Object>();
        resultMap.put("code", "0");
        resultMap.put("msg", "success.");
        resultMap.put("data", dataList);
        return resultMap;
    }
}

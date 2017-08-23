package com.awifi.np.biz.pub.system.industry.controller;

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

import com.awifi.np.biz.api.client.dbcenter.industry.util.IndustryClient;
import com.awifi.np.biz.common.base.constants.RedisConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.pub.system.industry.service.IndustryService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月19日 下午3:39:45
 * 创建作者：周颖
 * 文件名称：IndustryController.java
 * 版本：  v1.0
 * 功能：行业控制层
 * 修改记录：
 */
@Controller
@SuppressWarnings("rawtypes")
public class IndustryController extends BaseController {

    /**行业服务层*/
    @Resource(name = "industryService")
    private IndustryService industryService;
    
    /**
     * 缓存所有行业信息
     * @param accessToken access_token
     * @return 结果
     * @author 周颖  
     * @throws Exception 
     * @date 2017年1月20日 上午9:32:34
     */
    @RequestMapping(method = RequestMethod.GET,value = "/pubsrv/industry/cache")
    @ResponseBody
    public Map cache(@RequestParam(value="access_token",required=true)String accessToken) throws Exception{
        IndustryClient.cache();
        return this.successMsg();
    }
    
    /**
     * 行业缓存清空
     * @param accessToken access_token
     * @return 结果
     * @author 周颖  
     * @date 2017年1月19日 下午4:15:31
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,value = "/pubsrv/industry/cache/clear")
    public Map cacheClear(@RequestParam(value="access_token",required=true)String accessToken){
        String redisKey = RedisConstants.INDUSTRY + "*";//要删除的行业rediskey前缀
        Set<String> keySet = RedisUtil.keys(redisKey);//获取完整key
        Long count = RedisUtil.delBatch(keySet);//删除总条数
        logger.debug("提示：redis共删除 "+ count +" 条数据");
        return this.successMsg();
    }
    
    /**
     * 获取行业列表
     * @param accessToken access_token
     * @param parentCode 父行业编号
     * @param request 请求
     * @return 行业列表
     * @author 周颖  
     * @throws Exception 
     * @date 2017年1月20日 上午11:05:01
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,value = "/pubsrv/industrys")
    public Map getListByParam(@RequestParam(value="access_token",required=true)String accessToken,@RequestParam(value="parentcode",required=false)String parentCode,HttpServletRequest request) throws Exception{
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        List<Map<String,String>> industryList = industryService.getListByParam(sessionUser,parentCode);
        return this.successMsg(industryList);
    }
}
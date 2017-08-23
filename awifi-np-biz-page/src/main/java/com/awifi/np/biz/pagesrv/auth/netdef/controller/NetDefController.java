/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月17日 上午9:45:52
* 创建作者：许小满
* 文件名称：NetDefController.java
* 版本：  v1.0
* 功能：防蹭网码-控制层
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.auth.netdef.controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.constants.RedisConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;

@Controller
@RequestMapping(value="/pagesrv/netdef")
@SuppressWarnings("unchecked")
public class NetDefController extends BaseController {

    /**
     * 防蹭网码校验
     * @param params json格式参数
     * @return json
     * @author 许小满  
     * @date 2017年6月17日 下午4:56:45
     */
    @RequestMapping(method=RequestMethod.GET, value="/check")
    @ResponseBody
    public Map<String,Object> check(
            @RequestParam(value="params",required=true)String params //json格式参数
    ){
        Map<String,Object> paramsMap = JsonUtil.fromJson(params, Map.class);//请求参数 json格式
        String deviceId = (String)paramsMap.get("deviceId");//设备id
        String netDefCode = (String)paramsMap.get("netDefCode");//防蹭网码
        
        logger.debug("提示：netDefCode= " + netDefCode);
        
        //参数校验
        ValidUtil.valid("设备id[deviceId]", deviceId, "required");//设备id
        ValidUtil.valid("防蹭网码[netDefCode]", netDefCode, "required");//防蹭网码
        
        String redisKey = RedisConstants.NET_DEF + deviceId;//防蹭网redis key
        String netDefCodeCache = RedisUtil.get(redisKey);//防蹭网码
        if(StringUtils.isBlank(netDefCodeCache)){
            throw new BizException("E2700001", MessageUtil.getMessage("E2700001"));//防蹭网码已失效！
        }
        if(!netDefCode.trim().equalsIgnoreCase(netDefCodeCache)){
            throw new ValidException("E2700002", MessageUtil.getMessage("E2700002"));//防蹭网码错误！
        }
        return this.successMsg();
    }
    
}

package com.awifi.np.biz.pagesrv.device.geolocation.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.pagesrv.base.util.ExceptionUtil;

/**   
 * @Description: 经纬度组件action层
 * @Title: GeolocationAction.java 
 * @Package com.awifi.toe.admin.device.geolocation.action 
 * @author 牛华凤
 * @date 2016年3月22日 下午5:48:04
 * @version V1.0   
 */
@Controller
@SuppressWarnings("rawtypes")
public class GeolocationAction extends BaseController{

    /**
     * 获取到设备的信息储存在数据中心
     * @param request 请求
     * @return resultMap 
     * @author 牛华凤  
     * @date 2016年3月22日 下午5:57:51
     */
    //http://localhost:84/geolocation/save?devId=yuio&longitude=1452.01&latitude=1.201
    @RequestMapping(value = "/geolocation/save")
    @ResponseBody
    public Map save(HttpServletRequest request){
        Map<String,Object> resultMap = new HashMap<String, Object>();
        try{
            // 请求方法类型 校验
            if (request.getMethod().equals(HttpRequest.METHOD_GET)) {
                resultMap.put("result", "FAIL");
                resultMap.put("message", "非法请求！");
                return resultMap;
            }
            // 获取参数
            String devId = request.getParameter("devId");// 设备id
            String longitude = request.getParameter("longitude");// 设备经度
            String latitude = request.getParameter("latitude");// 设备纬度
            
            // 请求参数 校验
            ValidUtil.valid("设备id[devId]", devId, "required");//设备id
            ValidUtil.valid("设备经度[longitude]", longitude, "required");//设备经度
            ValidUtil.valid("设备纬度[latitude]", latitude, "required");//设备纬度
            
            logger.debug("经纬度推送参数：devId="+devId+"&longitude="+longitude+"&latitude="+latitude);
            
            //LatitudeLongitudeClient.pushLatiudeLongitude(devId, longitude, latitude);
            resultMap.put("result", "OK");
            resultMap.put("message", StringUtils.EMPTY);
        }catch (Exception e) {
            ExceptionUtil.formatMsg(request, resultMap, e, logger, "Sorry, geolocation push failed.");
        }
        return resultMap;
    }
    
    /**
     * 设备经纬度推送接口
     * @param bodyParam 参数
     * @return 结果
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年8月15日 下午2:40:09
     */
    @RequestMapping(method=RequestMethod.PUT, value="/pagesrv/device/geolocation", produces="application/json")
    @ResponseBody
    public Map<String,Object> push(@RequestBody(required=true) Map<String,Object> bodyParam) throws Exception{
        String deviceId = (String) bodyParam.get("deviceId");//设备id，不允许为空
        Double longitude = CastUtil.toDouble(bodyParam.get("longitude"));//经度，不允许为空
        Double latitude = CastUtil.toDouble(bodyParam.get("latitude"));//纬度，不允许为空
        // 请求参数 校验
        ValidUtil.valid("设备id[deviceId]", deviceId, "required");//设备id
        ValidUtil.valid("设备经度[longitude]", longitude, "required");//设备经度
        ValidUtil.valid("设备纬度[latitude]", latitude, "required");//设备纬度
        
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("deviceId", deviceId);
        params.put("longitude", longitude);
        params.put("latiude", latitude);
        
        DeviceClient.pushLatitudeLongitude(JsonUtil.toJson(params));
        return this.successMsg();
    }

}

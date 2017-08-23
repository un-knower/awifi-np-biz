/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年8月15日 上午10:38:22
* 创建作者：王冬冬
* 文件名称：GeolocationAction.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devicebindsrv.device.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;

/**
 * 经纬度controller
 * @author 王冬冬
 * 2017年8月15日 上午10:59:47
 */
@Controller
@RequestMapping(value = "/devbindsrv")
public class GeolocationAction extends BaseController{
    
    /**
     * 推送经纬度
     * @param request 请求
     * @param response 响应
     * @param paramMap 参数
     * @throws Exception 异常
     * @return map
     * @author 王冬冬  
     * @date 2017年8月15日 上午10:47:15
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value="/device/geolocation",method=RequestMethod.PUT)
    @ResponseBody
    public Map push(HttpServletRequest request,HttpServletResponse response,@RequestBody(required=true) Map paramMap) throws Exception{
//        Map paramMap=JsonUtil.fromJson(param, Map.class);
        String deviceId=CastUtil.toString(paramMap.get("deviceId"));//设备id
        Double longitude=CastUtil.toDouble(paramMap.get("longitude"));//经度
        Double lantitude=CastUtil.toDouble(paramMap.get("latitude"));//纬度
        ValidUtil.valid("deviceId", deviceId, "required");//设备id不为空
        ValidUtil.valid("longitude", longitude, "required");//经度不为空
        ValidUtil.valid("lantitude", lantitude, "required");//纬度不为空

        Map<String,Object> params = new HashMap<String, Object>();
        params.put("deviceId", deviceId);//设备id
        params.put("longitude", longitude);//经度
        params.put("latiude", lantitude);//纬度

        DeviceClient.pushLatitudeLongitude(JsonUtil.toJson(params));//推送到数据中心
        return this.successMsg();//返回成功
    }
}

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月21日 下午11:52:37
* 创建作者：许小满
* 文件名称：LocationController.java
* 版本：  v1.0
* 功能：地区控制层
* 修改记录：
*/
package com.awifi.np.biz.devicebindsrv.system.location.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.devicebindsrv.system.location.service.LocationService;

@Controller
@SuppressWarnings("rawtypes")
@RequestMapping(value = "/devbindsrv")
public class LocationController extends BaseController {

    /**地区服务层*/
    @Resource(name = "locationService")
    private LocationService locationService;
    
    /**
     * 获取所有省
     * @return 省
     * @author 周颖  
     * @throws Exception 
     * @date 2017年1月22日 下午1:48:35
     */
    @RequestMapping(method = RequestMethod.GET,value = "/provinces")
    @ResponseBody
    public Map getProvinces() throws Exception{
        List<Map<String,Object>> provinceMap = locationService.getProvinces();//获取所有省
        return this.successMsg(provinceMap);
    }
    
    /**
     * 获取所有市
     * @param parentId 省id
     * @return 市
     * @throws Exception 
     * @author 周颖  
     * @date 2017年1月22日 下午3:03:30
     */
    @RequestMapping(method = RequestMethod.GET,value = "/cities")
    @ResponseBody
    public Map getCities(@RequestParam(value="parentid",required=true) String parentId) throws Exception{
        List<Map<String,Object>> cityMap = locationService.getCities(parentId);//获取所有市
        return this.successMsg(cityMap);
    }
    
    /**
     * 获取所有区县
     * @param parentId 市id
     * @return 区县
     * @throws Exception 
     * @author 周颖  
     * @date 2017年1月23日 上午9:07:37
     */
    @RequestMapping(method = RequestMethod.GET,value = "/areas")
    @ResponseBody
    public Map getAreas(@RequestParam(value="parentid",required=true) String parentId) throws Exception{
        List<Map<String,Object>> areaMap = locationService.getAreas(parentId);//获取所有区县
        return this.successMsg(areaMap);
    }
    
}

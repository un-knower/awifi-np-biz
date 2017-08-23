package com.awifi.np.biz.devsrv.device.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.template.service.TemplateService;
import com.awifi.np.biz.common.util.ExcelUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.devsrv.device.service.DeviceService;

import jxl.Sheet;
import jxl.Workbook;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月8日 上午10:53:14
 * 创建作者：亢燕翔
 * 文件名称：DeviceController.java
 * 版本：  v1.0
 * 功能：  
 * 修改记录：
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/devsrv")
public class DeviceController extends BaseController{

    /**设备业务层*/
    @Resource(name = "deviceService")
    private DeviceService deviceService;
    
    /**模板服务*/
    @Resource(name = "templateService")
    private TemplateService templateService;
    
    /**
     * 设备显示接口
     * @param request 请求
     * @param templatecode page(pan)编号
     * @param access_token 安全令牌
     * @return map
     * @author 亢燕翔  
     * @date 2017年2月14日 上午8:55:49
     */
    @ResponseBody
    @RequestMapping(value="/view/{templatecode}", method=RequestMethod.GET)
    public Map view(HttpServletRequest request, @PathVariable String templatecode, @RequestParam(value="access_token",required=true) String access_token){
        String serviceCode = SysConfigUtil.getParamValue("servicecode_device");//从配置表读取服务代码
        String suitCode = SessionUtil.getCurSessionUser(request).getSuitCode();//从session中获取套码
        if(StringUtils.isBlank(suitCode)){//套码未找到抛出异常
            throw new ValidException("E2000005", MessageUtil.getMessage("E2000005"));//套码不允许为空!
        }
        String template = templateService.getByCode(suitCode,serviceCode,templatecode);//获取模板页面
        return this.successMsg(template);
    }
    
    /**
     * 设备详情
     * @param access_token 安全令牌
     * @param deviceid 设备id
     * @return map
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月10日 下午3:50:30
     */
    @ResponseBody
    @RequestMapping(value="/device/{deviceid}", method=RequestMethod.GET)
    public Map getByDevId(@RequestParam(value="access_token",required=true)String access_token,@PathVariable String deviceid) throws Exception{
        return this.successMsg(deviceService.getByDevId(deviceid));
    }
    
    /**
     * 批量修改设备ssid
     * @param ssid 热点名称
     * @param access_token 安全令牌
     * @param bodyParam 请求体数据
     * @return map
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月8日 上午11:04:28
     */
    @ResponseBody
    @RequestMapping(value="/devices/ssid/{ssid}", method=RequestMethod.PUT, produces="application/json")
    public Map batchUpdateSSID(@PathVariable String ssid, @RequestParam(value="access_token",required=true) String access_token,
            @RequestBody(required=true) List<Map<String,Object>> bodyParam) throws Exception{
        deviceService.batchUpdateSSID(ssid,bodyParam);
        return this.successMsg();
    }
    
    /**
     * 设备放通
     * @param access_token 安全令牌
     * @param bodyParam 请求体数据
     * @return map
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年2月15日 下午5:05:13
     */
    @ResponseBody
    @RequestMapping(value="/devices/switch/escape", method=RequestMethod.PUT, produces="application/json")
    public Map batchEscape(@RequestParam(value="access_token",required=true) String access_token,@RequestBody(required=true) List<Map<String, Object>> bodyParam) throws Exception{
        deviceService.batchEscape(bodyParam);
        return this.successMsg();
    }
    
    /**
     * Chinanet开关接口
     * @param access_token 安全令牌
     * @param bodyParam 请求体数据
     * @return map
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年2月15日 下午6:17:07
     */
    @ResponseBody
    @RequestMapping(value="/devices/switch/ssid/chinanet", method=RequestMethod.PUT, produces="application/json")
    public Map batchChinanetSsidSwitch(@RequestParam(value="access_token",required=true) String access_token,@RequestBody(required=true) List<Map<String, Object>> bodyParam) throws Exception{
        deviceService.batchChinanetSsidSwitch(bodyParam);
        return this.successMsg();
    }
    
    /**
     * aWiFi开关接口
     * @param access_token 安全令牌
     * @param bodyParam 请求体数据
     * @return map
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年2月16日 上午9:07:17
     */
    @ResponseBody
    @RequestMapping(value="/devices/switch/ssid/awifi", method=RequestMethod.PUT, produces="application/json")
    public Map batchAwifiSsidSwitch(@RequestParam(value="access_token",required=true) String access_token,@RequestBody(required=true) List<Map<String, Object>> bodyParam) throws Exception{
        deviceService.batchAwifiSsidSwitch(bodyParam);
        return this.successMsg();
    }
    
    /**
     * LAN口认证开关接口
     * @param access_token 安全令牌
     * @param bodyParam 请求体参数
     * @return map
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年2月16日 上午9:16:43
     */
    @ResponseBody
    @RequestMapping(value="/devices/switch/lan", method=RequestMethod.PUT, produces="application/json")
    public Map batchLanSwitch(@RequestParam(value="access_token",required=true) String access_token,@RequestBody(required=true) List<Map<String, Object>> bodyParam) throws Exception{
        deviceService.batchLanSwitch(bodyParam);
        return this.successMsg();
    }
    
    /**
     * 闲时下线接口
     * @param access_token 安全令牌
     * @param bodyParam 请求体参数
     * @return map
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年2月16日 上午9:22:08
     */
    @ResponseBody
    @RequestMapping(value="/devices/timeout", method=RequestMethod.PUT, produces="application/json")
    public Map batchClientTimeout(@RequestParam(value="access_token",required=true) String access_token,@RequestBody(required=true) List<Map<String, Object>> bodyParam) throws Exception{
        deviceService.batchClientTimeout(bodyParam);
        return this.successMsg();
    }
    
    /**
     * 设备解绑
     * @param access_token 安全令牌
     * @param bodyParam 
     * @return map
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月21日 下午7:32:01
     */
    @ResponseBody
    @RequestMapping(value="/unbind", method=RequestMethod.PUT, produces="application/json")
    public Map unbind(@RequestParam(value="access_token",required=true) String access_token,@RequestBody(required=true) Map<String,Object> bodyParam) throws Exception{
        deviceService.unbind(bodyParam);
        return this.successMsg();
    }
    /**
     * 设备解绑
     * @param access_token 安全令牌
     * @param bodyParam 
     * @return map
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月21日 下午7:32:01
     */
    @ResponseBody
    @RequestMapping(value="/merchants/unbind", method=RequestMethod.PUT, produces="application/json")
    public Map unbindMerchant(@RequestParam(value="access_token",required=true) String access_token,@RequestBody(required=true) Map<String,Object> bodyParam) throws Exception{
        deviceService.unbind(bodyParam);
        return this.successMsg();
    }
    
    
    /**
     * 批量修改设备ssid
     * @param request 请求
     * @param access_token 安全令牌
     * @return map
     * @author 王冬冬 
     * @throws Exception 
     * @date 2017年2月8日 上午11:04:28
     */
    @ResponseBody
    @RequestMapping(value="/devices/ssid/batch", method=RequestMethod.POST, produces="application/json")
    public Map batchImportSSID(HttpServletRequest request,@RequestParam(value="access_token",required=true) String access_token) throws Exception{
        
        Integer importMaxNum = Integer.parseInt(SysConfigUtil.getParamValue("xls_import_max_size"));
        CommonsMultipartResolver multipartResolver  = new CommonsMultipartResolver(request.getSession().getServletContext());//创建一个通用的多部分解析器.
        if(!multipartResolver.isMultipart(request)){//判断 request 是否有文件上传,即多部分请求
            throw new BizException("E2000030", MessageUtil.getMessage("E2000030"));//文件内容不允许为空!
        }
        MultipartHttpServletRequest  multiRequest = (MultipartHttpServletRequest)request;//转换成多部分request
        Iterator<String>  iter = multiRequest.getFileNames();
        Workbook book = null;  
        
        while(iter.hasNext()){
            MultipartFile file = multiRequest.getFile(iter.next());//取得上传文件 
            if(file == null){
                continue;
            }
            book = Workbook.getWorkbook(file.getInputStream());
            Sheet sheet = book.getSheet(0);
            ExcelUtil.cmpTemplateExcel(sheet, ExcelUtil.SSID_EXCELCOLUMNS);
            int row = sheet.getRows();//行数
            logger.debug("row:"+row);
            if(row-1 <= 0){//小于等于0 抛异常
                throw new ValidException("E2000030", MessageUtil.getMessage("E2000030"));//文件内容不允许为空!
            }else if(row-1 > importMaxNum){//大于数据导入最大数量
                Object[] message = {row-1,importMaxNum};
                throw new ValidException("E2000031", MessageUtil.getMessage("E2000031", message));//导入的记录总数[{0}]不允许大于[{1}]!
            }
            String devMac = null; //设备mac
            String ssid = null;//ssid
            
            List<Map<String,Object>> successList = new ArrayList<Map<String,Object>>(row-1);
            HashSet<String> macSet = new HashSet<String>();//临时存储mac集合
            HashSet<String> ssidSet = new HashSet<String>();//临时存储ssid集合
            Map<Integer, String> macMap = new TreeMap<Integer, String>();
            Map<Integer, String> ssidMap = new TreeMap<Integer, String>();
            for(int i=1 ; i< row ; i++){
                devMac = sheet.getCell(0,i).getContents().trim();//设备mac
                ValidUtil.valid("第"+ (i+1) + "行设备mac",devMac,"{'required':true,'regex':'"+RegexConstants.MAC_PATTERN+"'}");
                ssid = sheet.getCell(1,i).getContents().trim();//ssid
                ValidUtil.valid("第"+ (i+1) + "行ssid", ssid, "{'required':true,'regex':'"+RegexConstants.SSID_NAME_PATTERN+"'}");
                
                if(StringUtils.isBlank(devMac)){
                    macSet.add(i+"");
                }else{
                    macSet.add(devMac);
                }
                macMap.put(i, devMac);
                if(macSet.size() != i){//mac地址重复校验
                    for(Entry<Integer, String> maps : macMap.entrySet()){
                        if(maps.getValue().equals(devMac)){
                            Object[] message = {(i+1),"mac地址",(maps.getKey()+1)};
                            throw new ValidException("E2000034", MessageUtil.getMessage("E2000034", message));//第（{0}）行的{1}与第（{2}）行重复!
                        }
                    }
                }
                
                if(StringUtils.isBlank(ssid)){
                    ssidSet.add(i+"");
                }else{
                    ssidSet.add(ssid);
                }
                ssidMap.put(i, ssid);
                if(ssidSet.size() != i){//ssid重复校验
                    for(Entry<Integer, String> maps : ssidMap.entrySet()){
                        if(maps.getValue().equals(ssid)){
                            Object[] message = {(i+1),"ssid",(maps.getKey()+1)};
                            throw new ValidException("E2000034", MessageUtil.getMessage("E2000034", message));//第（{0}）行的{1}与第（{2}）行重复!
                        }
                    }
                }
                
                Map<String,Object> resultMap=new HashMap<String,Object>(); 
                resultMap.put("devMac", devMac);
                resultMap.put("ssid", ssid);
                successList.add(resultMap);
            }
            deviceService.batchUpdateSSID(successList);
        }
        return this.successMsg();
    }
}

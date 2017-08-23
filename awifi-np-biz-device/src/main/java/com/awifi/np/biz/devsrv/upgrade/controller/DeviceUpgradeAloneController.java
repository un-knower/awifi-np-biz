/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月10日 下午8:06:21
* 创建作者：尤小平
* 文件名称：DeviceUpgradeAloneController.java
* 版本：  v1.0
* 功能：设备个性化升级
* 修改记录：
*/
package com.awifi.np.biz.devsrv.upgrade.controller;

import com.alibaba.fastjson.JSON;
import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.DateUtil;
import com.awifi.np.biz.common.util.IOUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.devsrv.upgrade.service.DeviceUpgradeAloneService;
import com.awifi.np.biz.devsrv.upgrade.service.impl.DeviceUpgradeAloneServiceImpl;

import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIResponse;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
@APIs(description = "定制终端升级 -- 个性化升级")
@Controller
@RequestMapping(value = "/devsrv/device/upgrade/alone")
public class DeviceUpgradeAloneController extends BaseController {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * DeviceUpgradeAloneService
     */
    @Resource
    private DeviceUpgradeAloneService deviceUpgradeAloneService;
    
    /**
     * 定制终端升级--个性化升级任务查询
     * @param params 查询参数
     * @return 分页信息 
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年6月27日 下午8:44:18
     */
    @SuppressWarnings("unchecked")
    @API(summary = "定制终端升级--个性化升级任务查询", parameters = {
            @Param(name = "mac", description = "设备mac地址", dataType = DataType.STRING, required = false),
            @Param(name = "merchantId", description = "商户id", dataType = DataType.LONG, required = false),
            @Param(name = "packageName", description = "升级包名称", dataType = DataType.STRING, required = false),
            @Param(name = "pageNo", description = "页码", dataType = DataType.INTEGER, required = true),
            @Param(name = "pageSize", description = "每页条数", dataType = DataType.INTEGER, required = true),
    })
    @APIResponse(value = "{ \"code\": \"0\", \"data\": { \"records\": [ { \"corporationId\": 21, \"modelId\": 124, \"corporationName\": \"H3C\", \"userName\": \"用户2\", \"type\": \"firmware\", \"hdVersions\": \"大海之蓝\", \"mac\": \"1C184A0E9610\", \"merchantName\": \"台州溯源测试商户\", \"modelName\": \"WCA504\", \"path\": \"upload2017/07/17/15002863492104810.jpg\", \"merchantId\": 56064, \"versions\": \"大海无量\", \"name\": \"大海\", \"state\": 0 } ], \"pageNo\": 1, \"pageSize\": 1, \"totalRecord\": 1, \"totalPage\": 1 } }")
    @RequestMapping(value = "/tasks", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String, Object> getPersonalizedUpgradeTaskList(@RequestParam(value="params", required = true)String params)throws Exception{
        logger.debug("任务查询params: " + JSON.toJSONString(params));
        
        Map<String, Object> paramsMap = JsonUtil.fromJson(params, Map.class);
        String mac = MapUtils.getString(paramsMap, "mac");
        String merchantId = MapUtils.getString(paramsMap, "merchantId");
        String packageName = MapUtils.getString(paramsMap, "packageName");
        if(StringUtils.isNotBlank(mac)){
            ValidUtil.valid("mac地址 ", mac, "{'required':true , 'regex':'"+RegexConstants.MAC_PATTERN+"'}");
            
        }
        if(StringUtils.isNotBlank(merchantId)){
            ValidUtil.valid("商户id ", merchantId, "{'required':true,'numeric':true}");
        }
        if(StringUtils.isBlank(packageName)){
            paramsMap.remove("packageName");
        }
        Page<Map<String,Object>> page = new Page<Map<String,Object>>();
        this.setPageInfo(paramsMap, page);//设置页码,每页条数
        deviceUpgradeAloneService.getPersonalizedUpgradeTaskList(paramsMap, page);
        return this.successMsg(page);
    }
    
    /**
     * 定制终端升级--个性化升级任务添加   传递 多个设备mac和一个升级包id
     * @param bodyParam 参数
     * @param request 参数
     * @return  异常/参数
     * @author 伍恰  
     * @throws Exception 
     * @date 2017年6月27日 下午8:52:20
     */
    @SuppressWarnings("unchecked")
    @API(summary = "定制终端升级--个性化升级任务添加   传递 多个设备mac和一个升级包id",consumes="application/json", parameters = {
            @Param(name = "upgradeId", description = "升级包id", dataType = DataType.LONG, required = true),
            @Param(name = "macArr", description = "设备mac数组", dataType = DataType.ARRAY, required = true)
    })
    @APIResponse(value = "{ \"code\": \"0\" }")
    @RequestMapping(value="/addTask",method=RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String, Object> addPersonalizedUpgradeTask(
            @RequestBody(required=true) Map<String,Object> bodyParam,HttpServletRequest request) throws Exception{
        //添加条件 1：设备厂商型号 和 所选择 升级包的厂商型号 得相同, 2：且该设备不存在升级任务 
        logger.debug("参数bodyParam: " + JSON.toJSONString(bodyParam));
        Long upgrade_id = CastUtil.toLong(bodyParam.get("upgradeId"));//升级包id
        List<String> macArrList = (ArrayList<String>)bodyParam.get("macArr");//设备mac地址
        String[] macArr = new String[macArrList.size()];
        macArr = macArrList.toArray(macArr);
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);//获取当前操作用户
        //TODO 注释需恢复
        Long userId = CastUtil.toLong(sessionUser.getId());
        String userName = sessionUser.getUserName(); 
//        Long userId = 99l;
//        String userName = "管理员A"; 
        ValidUtil.valid("mac数组", macArr,"arrayNotBlank");
        ValidUtil.valid("升级包id ", upgrade_id, "{'required':true,'numeric':true}");
        ValidUtil.valid("操作用户id ", userId, "{'required':true,'numeric':true}");
        ValidUtil.valid("操作用户名称 ", userName, "{'required':true}");
        
        for(String mac : macArr){
            ValidUtil.valid("mac地址 ", mac, "{'required':true , 'regex':'"+RegexConstants.MAC_PATTERN+"'}");
//            // ================ 根据升级包id查询 所选升级包 (厂商型号验证数据中心会做的，这里就去掉了)
//            Map<String, Object> reuturnPatch = deviceUpgradeAloneService.getPersonalizUpgradePatchById(upgrade_id);
//            //升级包厂商型号
//            Long pkg_corporation_id = CastUtil.toLong(reuturnPatch.get("corporation_id"));
//            Long pkg_model_id = CastUtil.toLong(reuturnPatch.get("model_id"));
//            // =============== 根绝mac地址查询 设备 厂商型号
//            Map<String, Object> returnDevice = deviceUpgradeAloneService.getDeviceByMac(mac);
//            Long dev_corporation_id = CastUtil.toLong(returnDevice.get("corporation_id"));
//            Long dev_model_id = CastUtil.toLong(returnDevice.get("model_id"));
//            
//            //设备和升级包的厂商型号必须相等。不相等,则报错
//            if(!(pkg_corporation_id == dev_corporation_id && pkg_model_id.equals(dev_model_id))){
//                throw new BizException("E2401001", MessageUtil.getMessage("E2401001","设备厂商型号和升级包的厂商型号 不匹配 !"));
//            }
            Map<String,Object> params = new HashMap<String, Object>();
            params.put("mac", mac);
            //================ 根据mac查询 该设备是否已存在升级任务
            Integer count = deviceUpgradeAloneService.getPersonalizedUpgradeTaskCount(params);
            if(count != null && count > 0){
                throw new  BizException("E2401002",MessageUtil.getMessage("E2401002", "该设备已经存在升级任务!"));
            }
            //添加任务 所需参数
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("upgradeId", upgrade_id);
            paramsMap.put("mac", mac);
            paramsMap.put("userId", userId);
            paramsMap.put("userName", userName);
            
            deviceUpgradeAloneService.addPersonalizedUpgradeTask(paramsMap);
        }
        return this.successMsg();
    }
    
    /**
     * 定制终端升级--个性化升级任务删除
     * @param ids 删除id,用"-"连接
     * @return
     * @throws Exception
     * @author 余红伟 
     * @date 2017年6月27日 下午8:54:26
     */
    @API(summary = "定制终端升级--个性化升级任务删除", parameters = {
            @Param(name = "ids", description = "任务id(多个，逗号拼接)", dataType = DataType.STRING, required = true),
    })
    @APIResponse(value = "{ \"code\": \"0\" }")
    @RequestMapping(value="/deleteTask",method=RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public Map<String, Object> deletePersonalizedUpgradeTask(@RequestParam(value = "ids", required = true) String ids ) throws Exception{
        logger.debug("任务id字符串[ids]: " + JSON.toJSONString(ids));
        ValidUtil.valid("任务ids", ids, "{'required':true}");
        //======从bodyParams中获取到id列表,转换成数组
        String[] idsStr = ids.split(",");
        ValidUtil.valid("任务id数组[idsStr]", idsStr, "arrayNotBlank");
        Long[] idArr = new Long[idsStr.length];
        for(int i=0;i<idsStr.length;i++){
            idArr[i] = CastUtil.toLong(idsStr[i]);
        }
//        List<Long> idList = (ArrayList<Long>) bodyParams.get("idArr");
//        Long[] idArr = new Long[idList.size()];
//        for(int i=0 ;i<idList.size();i++){
//            idArr[i] = CastUtil.toLong(idList.get(i));
//        }
        
        ValidUtil.valid("任务id数组[idArr]", idArr, "arrayNotBlank");
        for(int i=0;i<idArr.length;i++){
            ValidUtil.valid("任务id", idArr[i], "{'required':true,'numeric':true}");
        }
        deviceUpgradeAloneService.deletePersonalizedUpgradeTask(idArr);
        return this.successMsg();
    }
    //还没有接口，预留
    @RequestMapping(value = "/task/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String, Object> getPersonalizUpgradeTaskById(@RequestParam(value="access_token",required=true)String access_token,
            @PathVariable(value = "id")String id)throws Exception{
        logger.debug("任务id: " + id);
        ValidUtil.valid("任务id", id, "{'required':true,'numeric':true}");
        Long taskId = CastUtil.toLong(id);
        Map<String, Object> returnTask = deviceUpgradeAloneService.getPersonalizedUpgradeTaskById(taskId);
        return this.successMsg(returnTask);
    }
    /**
     * 定制终端升级--个性化升级设备查询  -->"添加任务界面"
     * @param params 参数
     * @return 分页信息
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年6月27日 下午8:49:57
     */
    @SuppressWarnings("unchecked")
    @API(summary = "定制终端升级--个性化升级设备查询  -->添加任务界面", parameters = {
            @Param(name = "mac", description = "设备mac地址", dataType = DataType.STRING, required = false),
            @Param(name = "merchantId", description = "商户id", dataType = DataType.LONG, required = false),
            @Param(name = "corporationId", description = "厂商id", dataType = DataType.LONG, required = false),
            @Param(name = "modelId", description = "型号id", dataType = DataType.LONG, required = false),
            @Param(name = "pageNo", description = "页码", dataType = DataType.INTEGER, required = true),
            @Param(name = "pageSize", description = "每页条数", dataType = DataType.INTEGER, required = true)
    })
    @APIResponse(value = "{\"code\":\"0\",\"data\":{\"records\":[{\"modelName\":\"WCA504\",\"ACCOUNT_ID\":56064,\"corporationName\":\"H3C\",\"corporation\":\"21\",\"model\":\"124\",\"MAC_ADDR\":\"1C184A0E9610\",\"taskNum\":1,\"merchantName\":\"台州溯源测试商户\"}],\"pageNo\":1,\"pageSize\":2,\"totalRecord\":1,\"totalPage\":1}}")
    @RequestMapping(value = "/devices", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String, Object> getUpgradeDeviceList (@RequestParam(value="params", required = true)String params)throws Exception{
        logger.debug("设备查询参数Param: " + JSON.toJSONString(params));
        Map<String, Object> paramsMap = JsonUtil.fromJson(params, Map.class);
        //=====校验规则：1,厂商型号只能关联商户名称查询，不能单独查询;2,MAC和商户名称必须有一个不为空
        String corporationId = MapUtils.getString(paramsMap, "corporationId");
        String modelId = MapUtils.getString(paramsMap, "modelId");
        String mac =  MapUtils.getString(paramsMap, "mac");
        String merchantId = MapUtils.getString(paramsMap, "merchantId");
        if(StringUtils.isNotBlank(corporationId) && StringUtils.isNotBlank(modelId)){
            ValidUtil.valid("使用厂商和型号时商户名称必填[mechantId]", merchantId, "{'required':true,'numeric':true}");
        }
        if(StringUtils.isBlank(mac)){
            ValidUtil.valid("mac为空时,商户id", merchantId, "{'required':true,'numeric':true}");
        }
        if(StringUtils.isBlank(merchantId)){
            ValidUtil.valid("商户id为空时,mac地址 ", mac, "{'required':true , 'regex':'"+RegexConstants.MAC_PATTERN+"'}");
        }
        //mac和商户id都传递的时候，两者都校验
        if(StringUtils.isNotBlank(mac)&&StringUtils.isNotBlank(merchantId)){
            ValidUtil.valid("mac地址 ", mac, "{'required':true , 'regex':'"+RegexConstants.MAC_PATTERN+"'}");
            ValidUtil.valid("商户id", merchantId, "{'required':true,'numeric':true}");
        }

        Page<Map<String,Object>> page = new Page<Map<String,Object>>();
        this.setPageInfo(paramsMap, page);
        deviceUpgradeAloneService.getUpgradeDeviceList(paramsMap, page);
        return this.successMsg(page);
    }
    /**
     * 个性化升级包  添加
     * @param request 请求
     * @param bodyParam
     * @return 返回一个map
     * @throws Exception 异常
     * @author 余红伟 
     * @date 2017年7月12日 上午10:52:31
     */
    @API(summary = "定制终端升级--个性化升级包  添加", parameters = {
            @Param(name = "patch_name", description = "升级包名称", dataType = DataType.STRING, required = true),
            @Param(name = "patch_type", description = "升级类型", dataType = DataType.STRING, required = true),
            @Param(name = "corporationId", description = "厂商", dataType = DataType.LONG, required = true),
            @Param(name = "modelId", description = "型号", dataType = DataType.LONG, required = true),
            @Param(name = "versions", description = "版本", dataType = DataType.STRING, required = true),
            @Param(name = "hdVersions", description = "hd版本", dataType = DataType.STRING, required = true),
            @Param(name = "aloneFile", description = "升级包文件", dataType = DataType.FILE, required = true)
    })
    @APIResponse(value = "{ \"code\": \"0\" }")
    @RequestMapping(value="/addPatch",method=RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String, Object> addPersonalizedUpgradepatch(HttpServletRequest request) throws Exception{
        logger.debug("升级包添加参数: " + JSON.toJSONString(request.getParameterMap())); 

      //========测试时,参数全部表单提交,然后从request里获取。
        String name =  request.getParameter("patch_name");
        String type = request.getParameter("patch_type");
        Long corporationId = CastUtil.toLong(request.getParameter("corporationId"));
        Long modelId = CastUtil.toLong(request.getParameter("modelId"));
        String versions = request.getParameter("versions");
        String hdVersions = request.getParameter("hdVersions");
      //升级包文件
//      MultipartFile file = IOUtil.getFileFromRequest(request);
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multiRequest.getFile("aloneFile");
        //升级包添加   -- 操作用户
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);//获取当前操作用户
        Long userId = CastUtil.toLong(sessionUser.getId());
        String userName = sessionUser.getUserName(); 
    //  Long userId = 10L;
    //  String userName = "用户50" ;
        ValidUtil.valid("包名称: ", name, "{'required':true}");
        if(name.length() > 30){
            throw new ValidException("E2401006", MessageUtil.getMessage("E2401006", "升级包名称长度最大为30个字符!"));
        }
        ValidUtil.valid("升级类型: ", type, "{'required':true}");
        if(!"firmware".equals(type) && !"module".equals(type)){
            throw new ValidException("E2401005",MessageUtil.getMessage("E2401005","升级类型参数错误!"));
        }
        ValidUtil.valid("厂商: ", corporationId, "{'required':true,'numeric':true}");
        ValidUtil.valid("型号: ", modelId, "{'required':true,'numeric':true}");
    //      ValidUtil.valid("版本: ", versions, "{'required':true,'regex':'^V[0-9]+\\.[0-9]+\\.[0-9]+$'}");
        ValidUtil.valid("版本: ", versions, "{'required':true,'regex':'^V[0-9]*\\\\.[0-9]+\\\\.[0-9]+$'}");//V1.1.23
        ValidUtil.valid("hd版本: ", hdVersions, "{'required':true,'regex':'^[a-zA-Z0-9]{1,20}$'}"); 
        ValidUtil.valid("操作用户id ", userId, "{'required':true,'numeric':true}");
        ValidUtil.valid("操作用户名称 ", userName, "{'required':true}");
          
          // ===================== 升级包文件上传
        if (null == file || file.isEmpty()){
            throw new ValidException("E2000030",MessageUtil.getMessage("E2000030")); //如果文件为空
        }
      
//      String patch_upload_dir = SysConfigUtil.getParamValue("patch_upload_dir");//上传路径
//      String patch_upload_url = SysConfigUtil.getParamValue("patch_upload_url");//返回路径
////      String patch_upload_dir =  "upload";
////      String patch_upload_url =   "upload";
//      if(StringUtils.isBlank(patch_upload_dir) || StringUtils.isBlank(patch_upload_url)){
//          throw new ValidationException("E2401003",MessageUtil.getMessage("E2401003", "文件上传/返回 路径 未配置!"));
//      }
      //=============文件大小不能大于配置项
//      long fileSize = file.getSize();
//      Integer max_patch_size = CastUtil.toInteger(SysConfigUtil.getParamValue("max_patch_size"));
//      if(max_patch_size == null || max_patch_size == 0){
//          max_patch_size = 2 * 1024;//2M
//      }
//      if(fileSize > max_patch_size * 1024){
//          throw new ValidException("E2401004", MessageUtil.getMessage("E2401004", max_patch_size));
//      }
      /**
      //当前时间 的 年月日路径    2017/06/27
      String folderpath = getFolderPath(new Date());
      // ================= 获取文件后缀名
      String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
      String fileName = System.currentTimeMillis() + rand(4) + fileType;//当前时间戳 + 随机数 + 文件后缀名
//      String url = patch_upload_url + folderpath + "/" +fileName ; //返回路径
      String url =   folderpath + "/" +fileName ; //返回路径
//      String sysPath = SysConfigUtil.getParamValue("region_resources_folder_path");
      String sysPath = "F:/tmp";
      StringBuffer path = new StringBuffer();
      path.append("/").append("device").append("/").append("upgrade").append("/").append("alone").append("/");
      File targetfile = new File(sysPath + path.toString() + folderpath);
      if(!targetfile.exists()){
          targetfile.mkdirs();
      }
      **/
//      IOUtil.mkDirsByFilePath(sysPath + path.toString() + folderpath);
//      file.transferTo(new File(sysPath + path.toString() + url));
//      // 如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\配置的patch_upload_dir\\年\\月\\日\\文件夹中
//      File path = PathManager.getInstance().getWebRootPath().resolve(patch_upload_dir).resolve(folderpath).toFile();
//      File path = com.awifi.np.biz.devsrv.upgrade.service.impl.PathManager.getInstance().getWebRootPath().resolve(patch_upload_dir).resolve(folderpath).toFile();
//      File path = com.awifi.np.biz.devsrv.upgrade.service.impl.PathManager.getInstance().getWebRootPath().resolve(patch_upload_dir).toFile();
      //如果文件夹不存在，创建文件夹
//      if(!path.exists()){
//          path.mkdirs();
//      }
//      //==========保存文件
//      file.transferTo(new File(path.getAbsolutePath(),fileName));
//      return this.successMsg((Object)(patch_upload_url +"/"+ System.currentTimeMillis() + fileType) );
      
      // 处理升级包文件,参照定制默认升级的写法
        String resourcesFolderPath = SysConfigUtil.getParamValue("region_resources_folder_path");// 定制终端升级包-文件夹路径
    //      String resourcesFolderPath = "F:/tmp/";// 定制终端升级包-文件夹路径
        String regionZipPath = getSavePath(file);// 文件保存相对路径
        IOUtil.mkDirsByFilePath(resourcesFolderPath + regionZipPath);
        uploadAlone(file, resourcesFolderPath + regionZipPath);
          
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("name", name);
        paramsMap.put("type", type);
        paramsMap.put("corporationId", corporationId);
        paramsMap.put("modelId", modelId);
        paramsMap.put("versions", versions);
        paramsMap.put("hdVersions", hdVersions);
        paramsMap.put("path", regionZipPath);
        paramsMap.put("userId", userId);
        paramsMap.put("userName", userName);
          
        deviceUpgradeAloneService.addPersonalizedUpgradePatch(paramsMap);
        return this.successMsg();
    }
    /**
     * 管理升级包界面 --  升级包删除
     * @param access_token
     * @param id 升级包id
     * @return 返回map 
     * @throws Exception 异常
     * @author 余红伟 
     * @date 2017年7月12日 下午1:44:16
     */
    @API(summary = "定制终端升级--管理升级包界面 --  升级包删除",consumes = "", parameters = {
            @Param(name = "id", description = "升级包id", dataType = DataType.LONG, required = true),
    })
    @APIResponse(value = "{ \"code\": \"0\" }")
    @RequestMapping(value="/deletePatch/{id}",method=RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public Map<String, Object> deletePersonalizedUpgradePatch(@PathVariable(value="id",required=true) String id) throws Exception{
        logger.debug("升级包删除id： " + id);
        ValidUtil.valid("升级包id: ", id, "{'required':true,'numeric':true}");
        Long patchId = CastUtil.toLong(id);
        deviceUpgradeAloneService.deletePersonalizedUpgradePatch(patchId);
        return this.successMsg();
    }
    /**
     * 管理升级包界面 -- 升级包列表查询
     * @param access_token
     * @param params
     * @return
     * @throws Exception
     * @author 余红伟 
     * @date 2017年7月12日 下午1:56:37
     */
    @SuppressWarnings("unchecked")
    @API(summary = "定制终端升级--管理升级包界面 -- 升级包列表查询", consumes="application/json", parameters = {
            @Param(name = "name", description = "升级包名称", dataType = DataType.STRING, required = false),
            @Param(name = "corporationId", description = "厂商(厂商要么都填，要么都不填)", dataType = DataType.LONG, required = false),
            @Param(name = "modelId", description = "型号(厂商要么都填，要么都不填)", dataType = DataType.LONG, required = false),
            @Param(name = "pageNo", description = "页码", dataType = DataType.INTEGER, required = true),
            @Param(name = "pageSize", description = "每页条数", dataType = DataType.INTEGER, required = true),
    })
    @APIResponse(value = "{ \"code\": \"0\", \"data\": { \"records\": [ { \"corporationId\": 21, \"modelId\": 124, \"corporationName\": \"H3C\", \"pageSize\": 50, \"type\": \"个性化升级\", \"hdVersions\": \"大海之蓝\", \"pageNum\": 1, \"modelName\": \"WCA504\", \"path\": \"upload2017/07/17/15002863492104810.jpg\", \"createTime\": 1500286354000, \"versions\": \"大海无量\", \"name\": \"大海\", \"id\": 3, \"upgradeNum\": 1, \"userId\": 1011, \"userName\": \"新运管管理员\", \"status\": 1 } ], \"pageNo\": 1, \"pageSize\": 10, \"totalRecord\": 1, \"totalPage\": 1 } }")
    @RequestMapping(value = "/patches", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String, Object> getPersonalizedUpgradePatchList(
            @RequestParam(value="params", required = true)String params)throws Exception{
        logger.debug("params: " + JSON.toJSONString(params));
        Map<String, Object> paramsMap = JsonUtil.fromJson(params, Map.class);
        String corporationId = MapUtils.getString(paramsMap, "corporationId");
        String modelId = MapUtils.getString(paramsMap, "modelId");
        
        if(StringUtils.isNotBlank(corporationId)){
            ValidUtil.valid("厂商: ", corporationId, "{'required':true,'numeric':true}");
            ValidUtil.valid("型号: ", modelId, "{'required':true,'numeric':true}");
        }
        if(StringUtils.isNotBlank(modelId)){
            ValidUtil.valid("厂商: ", corporationId, "{'required':true,'numeric':true}");
            ValidUtil.valid("型号: ", modelId, "{'required':true,'numeric':true}");
        }
        Page<Map<String,Object>> page = new Page<Map<String,Object>>();
        this.setPageInfo(paramsMap, page);//设置页码,每页条数
        deviceUpgradeAloneService.getPersonalizUpgradePatchList(paramsMap, page);
        return this.successMsg(page);
    }
    /**
     * 根据id 查询升级包
     * @param access_token
     * @param id 升级包id
     * @return
     * @throws Exception 异常
     * @author 余红伟 
     * @date 2017年7月13日 上午9:37:23
     */
    @API(summary = "定制终端升级--根据id 查询升级包", parameters = {
            @Param(name = "id", description = "升级包id",in = "path", dataType = DataType.LONG, required = true)
    })
    @APIResponse(value = "{\"code\":\"0\",\"data\":{\"path\":\"upload2017/07/17/15002863492104810.jpg\",\"corporationId\":21,\"createTime\":1500286354000,\"modelId\":124,\"versions\":\"大海无量\",\"name\":\"大海\",\"pageSize\":50,\"id\":3,\"type\":\"1\",\"hdVersions\":\"大海之蓝\",\"pageNum\":1,\"status\":1}}")
    @RequestMapping(value = "/patch/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String, Object> getPersonalizUpgradePatchById(
            @PathVariable(value = "id")String id)throws Exception{
        logger.debug("升级包查询id： " + id);
        ValidUtil.valid("升级包id", id, "{'required':true,'numeric':true}");
        Long patchId = CastUtil.toLong(id);
        
        Map<String, Object> patch = deviceUpgradeAloneService.getPersonalizUpgradePatchById(patchId);
        return this.successMsg(patch);
    }

    
    
    /**
     * 获取上传年月日 2017/06/27
     * @param now
     * @return
     * @throws Exception
     * @author 余红伟 
     * @date 2017年6月27日 下午2:34:01
     */
    public static String getFolderPath(Date now) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String date = sdf.format(now);
        return date;
    }
    /**
     * 固定位随机数
     * @param count
     * @return
     * @author 余红伟 
     * @date 2017年6月28日 下午2:48:03
     */
    public static String rand(int count){
        StringBuffer sb = new StringBuffer();
        String str = "0123456789";
        Random r = new Random();
        for(int i=0;i<count;i++){
            int num = r.nextInt(str.length());
            sb.append(str.charAt(num));
            str = str.replace((str.charAt(num)+""), "");
        }
        return sb.toString();
    }
    private String getSavePath(MultipartFile multipartFile) {
        logger.debug("fileName: " + multipartFile.getOriginalFilename());
        // 文件上传目录
        StringBuffer path = new StringBuffer();

        String date = DateUtil.getTodayDate();
        String[] dataArray = date.split("-");
        // 年
        String year = dataArray[0];
        // 月
        String month = dataArray[1];
        // 日
        String day = dataArray[2];

        path.append("/").append("device").append("/").append("upgrade").append("/").append("alone").append("/").append(year).append("/").append(month).append("/").append(day).append("/").append(multipartFile.getOriginalFilename());
        logger.debug("文件上传目录: " + path.toString());

        return path.toString();
    }
    protected void uploadAlone(MultipartFile regionZip, String path) throws IOException {
        regionZip.transferTo(new File(path));
    }
    //测试
    public void setDeviceUpgradeAloneServiceImpl(DeviceUpgradeAloneServiceImpl d){
        deviceUpgradeAloneService = d;
    }
    public static void main(String[] args) {
        String versions = "V1.165656.2656563";
        ValidUtil.valid("版本: ", versions, "{'required':true,'regex':'^V[0-9]?\\\\.[0-9]+\\\\.[0-9]+$'}");//V1.1.23
    }
}

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年7月10日 下午8:02:12
* 创建作者：尤小平
* 文件名称：DeviceUpgradeRegionController.java
* 版本：  v1.0
* 功能：设备区域默认升级
* 修改记录：
*/
package com.awifi.np.biz.devsrv.upgrade.controller;

import com.alibaba.druid.util.StringUtils;
import com.awifi.np.biz.api.client.dbcenter.device.upgrade.region.model.DeviceUpgradeRegion;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.ms.util.StringUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.IOUtil;
import com.awifi.np.biz.common.util.DateUtil;
import com.awifi.np.biz.devsrv.upgrade.service.DeviceUpgradeRegionService;
import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.APIResponse;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@APIs(description = "定制终端地区升级")
@Controller
@RequestMapping(value = "/devsrv/device/upgrade/region")
public class DeviceUpgradeRegionController extends BaseController {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * DeviceUpgradeRegionService
     */
    @Resource
    private DeviceUpgradeRegionService deviceUpgradeRegionService;

    /**
     * 定制终端-获取区域默认升级包列表.
     * 
     * @param access_token token
     * @param params params
     * @param request request
     * @return 升级包列表
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月11日 下午3:54:22
     */
    @SuppressWarnings("unchecked")
    @API(summary = "获取区域默认升级包列表", parameters = {
            @Param(name = "access_token", description = "access_token", dataType = DataType.STRING, required = false),
            @Param(name = "province", description = "省", dataType = DataType.LONG, required = false),
            @Param(name = "city", description = "市", dataType = DataType.LONG, required = false),
            @Param(name = "county", description = "区县", dataType = DataType.LONG, required = false),
            @Param(name = "corporationId", description = "厂商", dataType = DataType.LONG, required = false),
            @Param(name = "modelId", description = "型号", dataType = DataType.LONG, required = false),
            @Param(name = "state", description = "状态", dataType = DataType.LONG, required = false),
            @Param(name = "userId", description = "操作用户id", dataType = DataType.LONG, required = false),
            @Param(name = "pageSize", description = "每页数量", dataType = DataType.INTEGER, required = true),
            @Param(name = "pageNum", description = "页数", dataType = DataType.INTEGER, required = false) })
    @APIResponse("{ { \"code\": \"0\", \"data\": { \"records\": [ { \"id\": 17, \"versions\": \"12.3.4(版本号)\", \"hdVersions\": \"hdVersions3(HD版本号)\", \"userName\": \"userNamea(操作用户)\", \"corModelName\": \"H3CHGYBY-098J(品牌型号)\", \"areaName\": \"湖南吉林江苏(升级区域)\", \"startTimeStr\": \"2017-07-21(启用时间)\", \"stateName\": \"启用(状态)\", \"typeName\": \"固件版本(升级类型)\" } ], \"pageNo\": 1, \"pageSize\": 13, \"totalRecord\": 1, \"totalPage\": 1 } } }")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getListByParam(
            @RequestParam(value = "access_token", required = false) String access_token,
            @RequestParam(value = "params", required = true) String params, HttpServletRequest request)
            throws Exception {
        // 打印参数
        logger.debug("params=" + params + ", access_token=" + access_token);

        // 解析参数
        Map<String, Object> paramsMap = JsonUtil.fromJson(params, Map.class);

        // 校验参数
        Integer pageSize = CastUtil.toInteger(paramsMap.get("pageSize"));
        ValidUtil.valid("每页数量", pageSize, "{'required':true,'numeric':true}");

        Integer pageNum = CastUtil.toInteger(paramsMap.get("pageNum"));
        if (pageNum == null || pageNum < 1) {
            // 默认第一页
            pageNum = 1;
        }

        // 获取查询条件
        DeviceUpgradeRegion region = this.getRegionParam(paramsMap);
//        if (region.getUserId() == null || region.getUserId() < 1) {
//            SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
//            region.setUserId(sessionUser.getId());
//            logger.debug("userId=" + region.getUserId());
//        }

        // 获取列表
        Page<DeviceUpgradeRegion> page = new Page<DeviceUpgradeRegion>(pageNum, pageSize);
        deviceUpgradeRegionService.getListByParam(region, page);

        // 返回分页列表
        return this.successMsg(page);
    }

    /**
     * 定制终端-新增终端地区升级.
     * 
     * @param accessToken token
     * @param request request
     * @return Map
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年7月12日 下午5:22:09
     */
    @API(summary = "新增终端地区升级", parameters = {
            @Param(name = "access_token", description = "access_token", dataType = DataType.STRING, required = false),
            @Param(name = "regionZip", description = "升级包", dataType = DataType.FILE, required = true),
            @Param(name = "province", description = "省", dataType = DataType.LONG, required = true),
            @Param(name = "city", description = "市", dataType = DataType.LONG, required = true),
            @Param(name = "county", description = "区县", dataType = DataType.LONG, required = true),
            @Param(name = "corporationId", description = "厂商", dataType = DataType.LONG, required = true),
            @Param(name = "modelId", description = "型号", dataType = DataType.LONG, required = true),
            @Param(name = "type", description = "升级类型(firmware,module)", dataType = DataType.STRING, required = true),
            @Param(name = "versions", description = "版本号", dataType = DataType.STRING, required = true),
            @Param(name = "hdVersions", description = "hd版本号", dataType = DataType.STRING, required = true) })
    @APIResponse("{ {\"code\":\"0\"} }  ")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> add(@RequestParam(value = "access_token", required = false) String accessToken,
            HttpServletRequest request) throws Exception {
        logger.debug("request params: " + JsonUtil.toJson(request.getParameterMap()));

        // 获取参数校验参数
        Long corporationId = CastUtil.toLong(request.getParameter("corporationId"));// 厂商
        Long modelId = CastUtil.toLong(request.getParameter("modelId"));// 型号
        String type = request.getParameter("type");// 升级类型
        String versions = request.getParameter("versions");// 版本号
        String hdVersions = request.getParameter("hdVersions");// hd版本号
        Long province = CastUtil.toLong(request.getParameter("province"));// 省
        Long city = CastUtil.toLong(request.getParameter("city"));// 市
        Long county = CastUtil.toLong(request.getParameter("county"));// 区

        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        Long userId = sessionUser.getId();
        String userName = sessionUser.getUserName();
        logger.debug("session userId=" + userId + ", userName=" + userName);
        if (userId == null || StringUtil.isBlank(userName)) {
            throw new ValidException("E0000004", MessageUtil.getMessage("E0000004"));// 登录信息已失效，请重新登录
        }

        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        MultipartFile regionZip = multiRequest.getFile("regionZip");
        if (regionZip == null || regionZip.isEmpty()) {
            throw new ValidException("E0000002", MessageUtil.getMessage("E0000002", "终端地区升级包"));// 终端地区升级包不允许为空!
        }

        ValidUtil.valid("厂商", corporationId, "{'required':true,'numeric':true}");
        ValidUtil.valid("型号", modelId, "{'required':true,'numeric':true}");
        ValidUtil.valid("升级类型", type, "{'required':true}");
        ValidUtil.valid("版本号", versions, "{'required':true,'regex':'^.{1,20}$'}");
        ValidUtil.valid("HD版本号", hdVersions, "{'required':true,'regex':'^.{1,20}$'}");
        ValidUtil.valid("升级区域-省", province, "required");
        ValidUtil.valid("升级区域-市", city, "required");
        ValidUtil.valid("升级区域-区", county, "required");

        // 处理升级包文件
        String resourcesFolderPath = SysConfigUtil.getParamValue("region_resources_folder_path");// 定制终端升级包-文件夹路径
        String regionZipPath = getSavePath(regionZip);// 文件保存相对路径
        IOUtil.mkDirsByFilePath(resourcesFolderPath + regionZipPath);
        uploadRegion(regionZip, resourcesFolderPath + regionZipPath);

        // 新增终端地区升级
        DeviceUpgradeRegion region = new DeviceUpgradeRegion();
        /*
         * String resourcesDomain = SysConfigUtil.getParamValue("resources_domain");// 静态资源域名
         */
        String pathUrl = /* resourcesDomain + */regionZipPath;
        logger.debug("pathUrl = " + pathUrl);
        region.setPath(pathUrl);
        region.setCorporationId(corporationId);
        region.setModelId(modelId);
        region.setType(type);
        region.setVersions(versions);
        region.setHdVersions(hdVersions);
        region.setProvince(province);
        region.setCity(city);
        region.setCounty(county);
        if (userId != null) {
            region.setUserId(userId);
        }
        if (!StringUtil.isBlank(userName)) {
            region.setUserName(userName);
        }
        region.setState(DeviceUpgradeRegion.CLOSE_STATUS);

        deviceUpgradeRegionService.add(region);

        return this.successMsg();
    }

    /**
     * 上传升级包.
     * 
     * @param regionZip MultipartFile
     * @param path 文件路径
     * @throws IOException 异常
     * @author 尤小平  
     * @date 2017年7月19日 下午2:07:41
     */
    protected void uploadRegion(MultipartFile regionZip, String path) throws IOException {
        regionZip.transferTo(new File(path));
    }

    /**
     * 解析参数.
     * 
     * @param paramsMap 参数
     * @return DeviceUpgradeRegion
     * @author 尤小平  
     * @date 2017年7月11日 下午3:55:49
     */
    private DeviceUpgradeRegion getRegionParam(Map<String, Object> paramsMap) {
        DeviceUpgradeRegion region = new DeviceUpgradeRegion();
        Long id = CastUtil.toLong(paramsMap.get("id"));
        Long corporationId = CastUtil.toLong(paramsMap.get("corporationId"));
        Long modelId = CastUtil.toLong(paramsMap.get("modelId"));
        String versions = (String) paramsMap.get("versions");
        String hdVersions = (String) paramsMap.get("hdVersions");
        String type = (String) paramsMap.get("type");
        String path = (String) paramsMap.get("path");
        Long province = CastUtil.toLong(paramsMap.get("province"));
        Long city = CastUtil.toLong(paramsMap.get("city"));
        Long county = CastUtil.toLong(paramsMap.get("county"));
        Long userId = CastUtil.toLong(paramsMap.get("userId"));
        Long state = CastUtil.toLong(paramsMap.get("state"));

        if (id != null) {
            region.setId(id);
        }
        if (corporationId != null) {
            region.setCorporationId(corporationId);
        }
        if (modelId != null) {
            region.setModelId(modelId);
        }
        if (!StringUtils.isEmpty(versions)) {
            region.setVersions(versions);
        }
        if (!StringUtils.isEmpty(hdVersions)) {
            region.setHdVersions(hdVersions);
        }
        if (!StringUtils.isEmpty(type)) {
            region.setType(type);
        }
        if (!StringUtils.isEmpty(path)) {
            region.setPath(path);
        }
        if (province != null) {
            region.setProvince(province);
        }
        if (city != null) {
            region.setCity(city);
        }
        if (county != null) {
            region.setCounty(county);
        }
        if (userId != null) {
            region.setUserId(userId);
        }
        if (state != null) {
            region.setState(state);
        }

        return region;
    }

    /**
     * 获取文件上传目录.
     * 
     * @param multipartFile MultipartFile
     * @return path
     * @author 尤小平  
     * @date 2017年7月12日 下午5:23:09
     */
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

        path.append("/").append("device").append("/").append("upgrade").append("/").append("region").append("/").append(year).append("/").append(month).append("/").append(day).append("/").append(multipartFile.getOriginalFilename());
        logger.debug("文件上传目录: " + path.toString());

        return path.toString();
    }

    /**
     * 定制终端-查看升级情况.
     * 
     * @param accessToken accessToken
     * @param id id
     * @return Map<String, Object>
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月13日 下午5:40:52
     */
    @API(summary = "查看升级情况", parameters = {
            @Param(name = "access_token", description = "access_token", dataType = DataType.STRING, required = false),
            @Param(name = "id", description = "id", dataType = DataType.LONG, required = true) })
    @APIResponse("{ { \"code\": \"0\", \"data\": { \"id\": 17, \"issueNum\": 0(任务下发数量), \"successNum\": 0(升级成功数量), \"successRate\": \"0.00%(升级成功率)\" } } }")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getById(@RequestParam(value = "access_token", required = false) String accessToken,
            @PathVariable(value = "id") String id) throws Exception {
        logger.debug("request id: " + id);
        ValidUtil.valid("id", id, "required");
        ValidUtil.valid("id", id, "numeric");

        DeviceUpgradeRegion region = deviceUpgradeRegionService.getById(Long.valueOf(id));

        return this.successMsg(getRegionInfo(region));
    }

    /**
     * 获取页面显示信息.
     * 
     * @param region DeviceUpgradeRegion
     * @return DeviceUpgradeRegion
     * @author 尤小平  
     * @date 2017年7月21日 下午5:47:05
     */
    private DeviceUpgradeRegion getRegionInfo(DeviceUpgradeRegion region) {
        DeviceUpgradeRegion newRegion = new DeviceUpgradeRegion();
        newRegion.setId(region.getId());
        if (region.getIssueNum() != null) {
            newRegion.setIssueNum(region.getIssueNum());
        } else {
            newRegion.setIssueNum(0L);
        }
        if (region.getSuccessNum() != null) {
            newRegion.setSuccessNum(region.getSuccessNum());
        } else {
            newRegion.setSuccessNum(0L);
        }
        if (region.getSuccessRate() != null) {
            newRegion.setSuccessRate(region.getSuccessRate());
        } else {
            newRegion.setSuccessRate("0.00%");
        }

        return newRegion;
    }

    /**
     * 定制终端-删除终端地区升级.
     * 
     * @param accessToken accessToken
     * @param id id
     * @return Map<String, Object>
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月13日 下午5:41:21
     */
    @API(summary = "删除终端地区升级", parameters = {
            @Param(name = "access_token", description = "access_token", dataType = DataType.STRING, required = false),
            @Param(name = "id", description = "id", dataType = DataType.LONG, required = true) })
    @APIResponse("{ {\"code\":\"0\"} }")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public Map<String, Object> delete(@RequestParam(value = "access_token", required = false) String accessToken,
            @PathVariable(value = "id") String id) throws Exception {
        logger.debug("request id: " + id);
        ValidUtil.valid("id", id, "required");
        ValidUtil.valid("id", id, "numeric");

        deviceUpgradeRegionService.delete(Long.valueOf(id));

        return this.successMsg();
    }

    /**
     * 定制终端-判断是否已经有启用的升级包.
     * 
     * @param accessToken accessToken
     * @param id id
     * @return Map<String, Object>
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月13日 下午5:41:30
     */
    @API(summary = "判断是否已经有启用的升级包", parameters = {
            @Param(name = "access_token", description = "access_token", dataType = DataType.STRING, required = false),
            @Param(name = "id", description = "id", dataType = DataType.LONG, required = true) })
    @APIResponse("{ {\"code\":\"0\",\"data\":true} }")
    @RequestMapping(value = "/exist/started/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> existUpgrade(@RequestParam(value = "access_token", required = false) String accessToken,
            @PathVariable(value = "id") String id) throws Exception {
        logger.debug("request id: " + id);
        ValidUtil.valid("id", id, "required");
        ValidUtil.valid("id", id, "numeric");

        boolean result = deviceUpgradeRegionService.existStartUpgrade(Long.valueOf(id));
        logger.debug("resut=" + result);

        return this.successMsg(result);
    }

    /**
     * 定制终端-启用升级包.
     * 
     * @param accessToken accessToken
     * @param id id
     * @return Map<String, Object>
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年7月13日 下午5:41:37
     */
    @API(summary = "启用升级包", parameters = {
            @Param(name = "access_token", description = "access_token", dataType = DataType.STRING, required = false),
            @Param(name = "id", description = "id", dataType = DataType.LONG, required = true) })
    @APIResponse("{ {\"code\":\"0\"} }")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> start(@RequestParam(value = "access_token", required = false) String accessToken,
            @PathVariable(value = "id") String id) throws Exception {
        logger.debug("request id: " + id);
        ValidUtil.valid("id", id, "required");
        ValidUtil.valid("id", id, "numeric");

        deviceUpgradeRegionService.start(Long.valueOf(id));

        return this.successMsg();
    }

    /**
     * for testing only.
     * 
     * @param deviceUpgradeRegionService DeviceUpgradeRegionService
     * @author 尤小平  
     * @date 2017年7月18日 下午3:25:18
     */
    protected void setDeviceUpgradeRegionService(DeviceUpgradeRegionService deviceUpgradeRegionService) {
        this.deviceUpgradeRegionService = deviceUpgradeRegionService;
    }
}

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月17日 下午2:49:08
* 创建作者：周颖
* 文件名称：SiteController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.portalsrv.site.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.template.service.TemplateService;
import com.awifi.np.biz.common.util.Base64Decoder;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.DateUtil;
import com.awifi.np.biz.common.util.ErrorUtil;
import com.awifi.np.biz.common.util.IOUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.KeyUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.toe.admin.strategy.model.Strategy;
import com.awifi.np.biz.toe.admin.strategy.service.StrategyService;
import com.awifi.np.biz.toe.portal.site.model.Site;
import com.awifi.np.biz.toe.portal.site.model.SitePage;
import com.awifi.np.biz.toe.portal.site.service.SiteService;

@Controller
@RequestMapping("/portalsrv")
public class SiteController extends BaseController {
   
    /**站点服务*/
    @Resource(name = "siteService")
    private SiteService siteService;
    
    /**模板服务*/
    @Resource(name = "templateService")
    private TemplateService templateService;
    
    /**策略*/
    @Resource(name = "strategyService")
    private StrategyService strategyService;
    
    /**
     * portal工具服务显示接口
     * @param accessToken access_token
     * @param templateCode 模板编号
     * @param request 请求
     * @return 模板页面
     * @author 周颖  
     * @date 2017年4月18日 上午9:06:22
     */
    @RequestMapping(method=RequestMethod.GET, value="/view/{templatecode}")
    @ResponseBody
    public Map<String,Object> view(@RequestParam(value="access_token",required=true)String accessToken,@PathVariable(value="templatecode") String templateCode,HttpServletRequest request){
        String serviceCode = SysConfigUtil.getParamValue("servicecode_portal");//从配置表读取portal服务代码
        String suitCode = SessionUtil.getCurSessionUser(request).getSuitCode();//从request获取套码
        if(StringUtils.isBlank(suitCode)){//如果套码为空
            throw new BizException("E2000005", MessageUtil.getMessage("E2000005"));//套码不允许为空!
        }
        String template = templateService.getByCode(suitCode,serviceCode,templateCode);//获取模板页面
        return this.successMsg(template);
    }
    
    /**
     * 站点策略列表
     * @param accessToken access_token
     * @param params 参数
     * @param request 请求
     * @return 列表
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年4月19日 下午2:31:49
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.GET,value = "/sites")
    @ResponseBody
    public Map<String,Object> getListByParam(@RequestParam(value="access_token",required=true)String accessToken,
                                             @RequestParam(value="params",required=true)String params, HttpServletRequest request) throws Exception{
        Map<String,Object> paramsMap = JsonUtil.fromJson(params, Map.class);//请求参数 json格式
        Integer pageSize = CastUtil.toInteger(paramsMap.get("pageSize"));//每页记录数
        Integer maxPageSize = Integer.parseInt(SysConfigUtil.getParamValue("page_maxsize"));//每页记录最大数
        ValidUtil.valid("每页数量[pageSize]", pageSize, "{'required':true,'numeric':{'max':"+maxPageSize+"}}");
        String keywords = (String) paramsMap.get("siteName");//站点名称关键字 可为空
        Long siteId = CastUtil.toLong(paramsMap.get("siteId"));//站点id 可为空
        Long merchantId = CastUtil.toLong(paramsMap.get("merchantId"));//商户id 不为空
        Integer status = CastUtil.toInteger(paramsMap.get("status"));//状态
        Integer pageNo = CastUtil.toInteger(paramsMap.get("pageNo"));//页码
        if(pageNo == null){//如果为空，默认第一页
            pageNo = 1;
        }
        SessionUser user = SessionUtil.getCurSessionUser(request);
        Page<Site> page = new Page<Site>(pageNo,pageSize);
        siteService.getListByParam(user,page,keywords,siteId,merchantId,status);
        return this.successMsg(page);
    } 
    
    /**
     * 新增站点
     * @param accessToken access_token
     * @param bodyParam 参数
     * @param request 请求
     * @return 结果
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年4月25日 上午9:03:31
     */
    @RequestMapping(method = RequestMethod.POST,value = "/site",produces="application/json")
    @ResponseBody
    public Map<String,Object> add(@RequestParam(value="access_token",required=true)String accessToken,
            @RequestBody(required=true) Map<String,Object> bodyParam,
            HttpServletRequest request) throws Exception{
        Long siteId = siteService.add(bodyParam,request);
        Map<String,Long> siteIdMap = new HashMap<String,Long>();
        siteIdMap.put("id", siteId);
        return this.successMsg(siteIdMap);
    }
    
    /**
     * 编辑站点
     * @param accessToken access_token
     * @param bodyParam 参数
     * @param request 请求
     * @param id 主键id
     * @return 结果
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年4月25日 上午9:03:31
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/site/{id}",produces="application/json")
    @ResponseBody
    public Map<String,Object> update(@RequestParam(value="access_token",required=true)String accessToken,
            @RequestBody(required=true) Map<String,Object> bodyParam,
            HttpServletRequest request,@PathVariable Long id) throws Exception{
        Long siteId = siteService.update(id,bodyParam,request);
        Map<String,Long> siteIdMap = new HashMap<String,Long>();
        siteIdMap.put("id", siteId);
        return this.successMsg(siteIdMap);
    }
    
    /**
     * 删除站点
     * @param accessToken access_token
     * @param id 站点id
     * @return 结果
     * @author 周颖  
     * @date 2017年4月25日 下午7:33:58
     */
    @RequestMapping(method = RequestMethod.DELETE,value = "/site/{id}")
    @ResponseBody
    public Map<String,Object> delete(@RequestParam(value="access_token",required=true)String accessToken,@PathVariable Long id){
        if(strategyService.isExist(id)){//判断站点是否关联策略
            throw new BizException("E2600006", MessageUtil.getMessage("E2600006"));//站点已经关联策略，若要删除，请先删除相关策略！
        }
        siteService.delete(id);
        return this.successMsg();
    }
    
    /**
     * 站点详情
     * @param accessToken access_token
     * @param id 站点id
     * @return 详情
     * @author 周颖  
     * @throws Exception 
     * @date 2017年4月26日 上午9:54:02
     */
    @RequestMapping(method = RequestMethod.GET,value = "/site/{id}")
    @ResponseBody
    public Map<String,Object> getById(@RequestParam(value="access_token",required=true)String accessToken,@PathVariable Long id) throws Exception{
        Site site = siteService.getById(id);
        return this.successMsg(site);
    }
    
    /**
     * 审核站点
     * @param accessToken access_token
     * @param id 站点id
     * @return 结果
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年4月27日 上午8:56:07
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/site/{id}/verify")
    @ResponseBody
    public Map<String,Object> verify(@RequestParam(value="access_token",required=true)String accessToken,@PathVariable Long id) throws Exception{
        siteService.updateStatusById(id,2);//状态改为已审核
        return this.successMsg();
    }
    
    /**
     * 发布站点
     * @param accessToken access_token
     * @param id 站点id
     * @param bodyParam 参数
     * @return 结果
     * @author 周颖  
     * @throws Exception 
     * @date 2017年4月27日 上午10:18:17
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/site/{id}/publish",produces="application/json")
    @ResponseBody
    public Map<String,Object> publish(
            @RequestParam(value="access_token",required=true)String accessToken,
            @PathVariable Long id,@RequestBody(required=true) Map<String,Object> bodyParam) throws Exception{
        Long strategyId = CastUtil.toLong(bodyParam.get("strategyId"));//策略表主键id，数字，允许为空
        String strategyName = (String) bodyParam.get("strategyName");//站点名称，不允许为空，1-50位汉字，字母，下划线、数字，不含特殊字符！
        String startDate = (String) bodyParam.get("startDate");//策略开始日期，不允许为空，格式为：yyyy-MM-dd
        String endDate = (String) bodyParam.get("endDate");//策略截止日期，不允许为空，格式为：yyyy-MM-dd
        Integer strategyType = CastUtil.toInteger(bodyParam.get("strategyType"));//策略类型，数字，不允许为空，其中：1代表全部 、2代表SSID、3代表设备id
        String content = (String) bodyParam.get("content");//策略内容 strategyType=1允许为空 其余必填
        //参数校验
        ValidUtil.valid("策略名称[strategyName]", strategyName, "{'required':true,'regex':'"+RegexConstants.STRATEGY_NAME_PATTERN+"'}");
        ValidUtil.valid("开始日期[startDate]", startDate, "required");
        ValidUtil.valid("截止日期[endDate]", endDate, "required");
        ValidUtil.valid("策略类型[strategyType]", strategyType, "{'required':true,'numeric':{'min':1,'max':3}}");
        if(!strategyType.equals(1)){
            ValidUtil.valid("策略内容[content]", content, "required");
        }
        Strategy strategy = new Strategy();
        strategy.setStrategyName(strategyName);
        strategy.setStrategyType(strategyType);
        Long startTimestamp = DateUtil.getTimestampMills(startDate + " 00:00:00");//转时间戳
        strategy.setStartDate(startTimestamp.toString());
        Long endTimestamp = DateUtil.getTimestampMills(endDate + " 23:59:59");//转时间戳
        strategy.setEndDate(endTimestamp.toString());
        if(strategyId != null){//编辑站点 会先渲染该站点最新建的策略 发布时更新这条最新的 策略
            if(strategyService.isStrategyNameExist(id,strategyId,strategyName)){//策略名称重复
                throw new BizException("E2600007", MessageUtil.getMessage("E2600007",strategyName));//策略名称（strategyName[{0}]）已存在!
            }
            strategy.setId(strategyId);
            strategyService.update(strategy,content);//更新策略信息
        }else{//新建站点  
            if(strategyService.isStrategyNameExist(id,strategyName)){//策略名称重复
                throw new BizException("E2600007", MessageUtil.getMessage("E2600007",strategyName));//策略名称（strategyName[{0}]）已存在!
            }
            Map<String,Object> merchantMap = siteService.getMerchantById(id);//根据站点id获取商户信息 merchantId cascadeLabel用于
            strategy.setMerchantId((Long)merchantMap.get("merchantId"));
            strategy.setCascadeLabel((String)merchantMap.get("cascadeLabel"));
            Integer maxOrderNo = strategyService.getMaxNo(id);//排序号
            strategy.setOrderNo(maxOrderNo + 1);
            strategy.setSiteId(id);
            strategyService.add(strategy,content);
        }
        siteService.updateStatusById(id,3);//站点状态改为已发布
        return this.successMsg();
    }
    
    /**
     * 审核站点 站点页信息
     * @param accessToken access_token
     * @param params 参数
     * @param id 站点id
     * @return 结果
     * @author 周颖  
     * @date 2017年5月2日 下午3:12:21
     */
    @RequestMapping(method = RequestMethod.GET,value = "/site/{siteid}/preview")
    @ResponseBody
    public Map<String,Object> getSitePageForPreview(
            @RequestParam(value="access_token",required=true)String accessToken,
            @RequestParam(value="params",required=true)String params,
            @PathVariable(value="siteid",required=true) Long id){
        SitePage sitePage = siteService.getSitePageForPreview(id,params);
        return this.successMsg(sitePage);
    }
    
    /**
     * 站点页面显示
     * @param accessToken access_token
     * @param request 请求
     * @param response 响应
     * @param id 站点页id
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年5月2日 下午5:18:14
     */
    @RequestMapping(method = RequestMethod.GET,value = "/site/page/{pageid}")
    @ResponseBody
    public void preview(
            @RequestParam(value="access_token",required=true)String accessToken,
            HttpServletRequest request, HttpServletResponse response,
            @PathVariable(value="pageid",required=true) Long id) throws Exception {
        logger.debug("--------站点预览--------");
        long beginTime = System.currentTimeMillis();
        String pagePath = siteService.getPagePath(id);//绝对路径
        logger.debug("pagePath:"+pagePath );
        this.responseHtml(response, pagePath);
        logger.debug("提示：打开站点页面共花费了 " + (System.currentTimeMillis() - beginTime) + " ms.");
    }
    
    /**
     * 回写站点预览html
     * @param response 响应
     * @param pagePath 路径
     * @throws Exception 异常
     * @author ZhouYing 
     * @date 2016年4月25日 上午10:18:19
     */
    private void responseHtml( HttpServletResponse response, String pagePath) throws Exception {
        response.reset();
        response.setCharacterEncoding("UTF-8");// 设置编码
        PrintWriter pw = null;
        BufferedReader br = null;
        try {
            pw = response.getWriter();
            String resourcesFolderPath = SysConfigUtil.getParamValue("resources_folder_path");//静态资源文件夹
            String resourcesDomain = SysConfigUtil.getParamValue("resources_domain");//静态资源域名
            br = new BufferedReader(new FileReader(resourcesFolderPath + pagePath + File.separator + "site.html"));
            String pageFullPath = resourcesDomain + pagePath;
            String html = null;
            String cssPath = "css/portal.min.css";
            String jsPath = "script/portal.min.js";
            while ((html = br.readLine()) != null) {// 判断最后一行不存在，为空结束循环
                // 替换css
                if (html.indexOf(cssPath) != -1) {
                    html = html.replaceAll(cssPath, pageFullPath + "/" + cssPath);
                }
                // 替换js
                if (html.indexOf(jsPath) != -1) {
                    html = html.replaceAll(jsPath, pageFullPath + "/" + jsPath);
                }
                logger.debug("提示：html= " + html);// 原样输出读到的内容
                pw.write(html);
            }
            pw.flush();
        } catch (Exception e) {
            ErrorUtil.printException(e, logger);
            throw e;
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (pw != null) {
                    pw.close();
                }
            } catch (Exception e1) {
            }
        }
    } 
    
    /**
     * 保存站点缩略图
     * @param accessToken access_token
     * @param request 请求
     * @param id 站点id
     * @param bodyParam 参数
     * @return 结果
     * @author 周颖  
     * @date 2017年6月5日 上午10:37:20
     */
    @RequestMapping(method = RequestMethod.POST,value = "/site/{id}/savethumb",produces="application/json")
    @ResponseBody
    public Map<String,Object> saveThumb(
            @RequestParam(value="access_token",required=true)String accessToken,
            HttpServletRequest request,
            @PathVariable(value="id",required=true) Long id,
            @RequestBody(required=true) Map<String,Object> bodyParam){
        String imagStr = (String) bodyParam.get("imageStr");
        ValidUtil.valid("缩略图信息[imageStr]", imagStr, "required");
        String[] imageData = imagStr.split(",");
        String thumbPath = siteService.getThumbPath(id);
        String resourcesFolderPath = SysConfigUtil.getParamValue("resources_folder_path");//静态资源文件夹
        String thumbFullPath = null;//缩略图保存的绝对路径
        boolean isSave = true;//用于判断是否更新站点表 thumb字段
        if(StringUtils.isNotBlank(thumbPath) && new File(resourcesFolderPath + thumbPath).isFile()){
            isSave = false;
        }else{
            String thumbName = KeyUtil.generateKey();
            String fileSuffix = imageData[0].split("/")[1].split(";")[0];
            thumbPath = getPicPath(thumbName)+ "." + fileSuffix;
        }
        thumbFullPath = resourcesFolderPath + thumbPath;
        boolean result = dealImage(imageData[1],thumbFullPath);
        if (!result) {//图片未保存成功
            throw new BizException("E2600013", MessageUtil.getMessage("E2600013"));//保存缩略图失败
        }
        //判断缩略图路径是否要保存到数据库
        if(isSave){
            siteService.saveThumbPath(id, thumbPath);
        }
        return this.successMsg();
    }
    
    /**
     * 缩略图保存路径
     * @param thumbName 缩略图名称
     * @return 结果
     * @author 周颖  
     * @date 2017年5月24日 下午4:31:31
     */
    private String getPicPath(String thumbName){
        String date = DateUtil.getTodayDate();
        String[] dataArray = date.split("-");
        String year = dataArray[0];// 年
        String month = dataArray[1];// 月
        String day = dataArray[2];// 日
        StringBuffer folderPath = new StringBuffer(70);// 文件夹路径
       
        folderPath.append("/").append("media").append("/").append("picture").append("/").append(year).append("/")
                .append(month).append("/").append(day);
        folderPath.append("/").append(thumbName);
        return folderPath.toString();
    }
    
    /**
     * 保存图片信息
     * @param imgStr base64编码的图片
     * @param imgFilePath 保存路径
     * @return 结果
     * @author 周颖  
     * @date 2017年6月5日 上午10:58:26
     */
    private boolean dealImage(String imgStr, String imgFilePath) {
        // 对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) {
            return false;
        }
        IOUtil.mkDirsByFilePath(imgFilePath);
        try {
            // Base64解码
            byte[] b = Base64Decoder.decodeToBytes(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    // 调整异常数据
                    b[i] += 256;
                }
            }
            // 生成Jpeg图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

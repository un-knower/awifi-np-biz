/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月12日 上午8:51:47
* 创建作者：周颖
* 文件名称：ComponentServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.toe.portal.component.service.impl;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.DateUtil;
import com.awifi.np.biz.common.util.ErrorUtil;
import com.awifi.np.biz.common.util.IOUtil;
import com.awifi.np.biz.common.util.KeyUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.common.util.ZipUtil;
import com.awifi.np.biz.toe.admin.project.service.ProjectService;
import com.awifi.np.biz.toe.portal.component.dao.ComponentDao;
import com.awifi.np.biz.toe.portal.component.model.Component;
import com.awifi.np.biz.toe.portal.component.service.ComponentService;

@Service("componentService")
public class ComponentServiceImpl implements ComponentService {

    /**日志*/
    protected final Log logger = LogFactory.getLog(this.getClass());
    
    /**组件*/
    @Resource(name = "componentDao")
    private ComponentDao componentDao;
    
    /**项目*/
    @Resource(name = "projectService")
    private ProjectService projectService;
    
    /**
     * 组件列表
     * @param page 页面
     * @param keywords 组件名称关键字
     * @author 周颖  
     * @date 2017年4月12日 上午10:22:17
     */
    public void getListByParam(Page<Component> page, String keywords){
        int count = componentDao.getCountByParam(keywords);//组件列表总数
        page.setTotalRecord(count);
        if(count <= 0){//如果为空 直接返回
            return;
        }
        List<Component> componentList = componentDao.getListByParam(keywords,page.getBegin(),page.getPageSize());//列表数据
        String resourcesDomain = SysConfigUtil.getParamValue("resources_domain");//静态资源域名
        String thumb = null;//缩略图相对路径
        for(Component component : componentList){
            thumb = component.getThumb();
            if(StringUtils.isNotBlank(thumb)){
                component.setThumb(resourcesDomain + thumb);
            }
        }
        page.setRecords(componentList);
    }
    
    /**
     * 组件添加
     * @param request 请求
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年4月14日 上午9:59:01
     */
    public void add(HttpServletRequest request) throws Exception{
        String componentName = request.getParameter("componentname");//组件名称，不允许为空，1-20位字母、数字、下划线组成，不含特殊字符
        String componentType = request.getParameter("componenttype");//组件类型，不允许为空，{1}代表引导页、{2}代表认证页、{3}代表过渡页、{4}代表导航页
        String classify = request.getParameter("classify");//组件分类，不允许为空，数字，1代表通用、2代表认证组件、3代表过渡跳转组件
        String projectIds = request.getParameter("projectids");//包含的项目ids，允许为空，多个时用逗号拼接，示例：1,2,3...
        String filterProjectIds = request.getParameter("filterprojectids");//排除的项目ids，允许为空，多个时用逗号拼接，示例：1,2,3...
        String canUnique = request.getParameter("canunique");//是否唯一，不允许为空，数字
        String version = request.getParameter("version");//版本 不允许为空 1-20位字母、数字、下划线组成，不含特殊字符
        String remark= request.getParameter("remark");//备注
        ValidUtil.valid("组件名称[componentname]", componentName, "{'required':true,'regex':'"+RegexConstants.COMPONENT_NAME_PATTERN+"'}");
        ValidUtil.valid("组件类型[componenttype]", componentType, "required");
        ValidUtil.valid("组件分类[classify]", classify, "{'required':true,'numeric':true}");
        ValidUtil.valid("组件是否唯一[canunique]", canUnique, "{'required':true,'numeric':true}");
        ValidUtil.valid("版本[version]", version, "required");
        CommonsMultipartResolver multipartResolver  = new CommonsMultipartResolver(request.getSession().getServletContext());//创建一个通用的多部分解析器.
        if(!multipartResolver.isMultipart(request)){//判断 request 是否有文件上传,即多部分请求
            throw new BizException("E2000030", MessageUtil.getMessage("E2000030"));//文件内容不允许为空!
        }
        Component component = new Component();
        String code = getCode();//获取编号（组件唯一码）
        logger.debug("提示：获取编号（组件唯一码） = " + code);
        component.setCode(code);//编号（组件唯一码）
        String setCode = getSetCode();//获取编号（组件设置唯一码）
        logger.debug("提示：获取编号（组件设置唯一码） = " + setCode);
        component.setSetCode(setCode);//编号（组件唯一码）
        MultipartHttpServletRequest  multiRequest = (MultipartHttpServletRequest)request;
        String resourcesFolderPath = SysConfigUtil.getParamValue("resources_folder_path");//静态资源文件夹路径
        MultipartFile componentZip = multiRequest.getFile("componentzip");
        if(componentZip == null || componentZip.isEmpty()){
            throw new BizException("E2600017", MessageUtil.getMessage("E2600017"));//组件压缩包不能为空！
        }
        MultipartFile iconPic = multiRequest.getFile("iconpic");
        if(iconPic == null || iconPic.isEmpty()){
            throw new BizException("E2600018", MessageUtil.getMessage("E2600018"));//组件图标不能为空！
        }
        MultipartFile thumbPic = multiRequest.getFile("thumbpic");
        if(thumbPic == null || thumbPic.isEmpty()){
            throw new BizException("E2600019", MessageUtil.getMessage("E2600019"));//组件缩略图不能为空！
        }
        //处理组件压缩文件
        String componentPath = this.dealComponentZip(multiRequest, componentZip, component, resourcesFolderPath);//组件压缩包--文件保存相对路径
        //处理组件图标
        String iconPath = getPicPath(iconPic);//生成图片保存相对路径
        IOUtil.savePicture(iconPic, resourcesFolderPath + iconPath);
        //处理组件缩略图
        String thumb = getPicPath(thumbPic);//生成图片保存相对路径
        IOUtil.savePicture(thumbPic, resourcesFolderPath + thumb);
        component.setName(componentName);//组件名称
        component.setType(componentType);//组件类型
        component.setComponentPath(componentPath);//组件压缩包--文件保存相对路径
        component.setIconPath(iconPath);//组件图标--文件保存相对路径
        component.setThumb(thumb);//组件缩略图--文件保存相对路径
        component.setCanUnique(CastUtil.toInteger(canUnique));//是否唯一
        component.setClassify(CastUtil.toInteger(classify));//类型
        component.setProjectIds(this.initProjectIds(projectIds));//项目ids
        component.setFilterProjectIds(this.initFilterProjectIds(filterProjectIds));//排除项目ids
        component.setVersion(version);//版本
        component.setRemark(remark);//备注
        componentDao.add(component);
    }
    
    /**
     * 组件编辑
     * @param request 请求
     * @param id 组件id
     * @author 周颖  
     * @throws Exception 
     * @date 2017年4月14日 下午1:12:15
     */
    public void edit(HttpServletRequest request,Long id) throws Exception{
        CommonsMultipartResolver multipartResolver  = new CommonsMultipartResolver(request.getSession().getServletContext());//创建一个通用的多部分解析器.
        MultipartHttpServletRequest  multiRequest = multipartResolver.resolveMultipart(request);
        String componentName = multiRequest.getParameter("componentname");//组件名称，不允许为空，1-20位字母、数字、下划线组成，不含特殊字符
        String componentType = multiRequest.getParameter("componenttype");//组件类型，不允许为空，{1}代表引导页、{2}代表认证页、{3}代表过渡页、{4}代表导航页
        String classify = multiRequest.getParameter("classify");//组件分类，不允许为空，数字，1代表通用、2代表认证组件、3代表过渡跳转组件
        String projectIds = multiRequest.getParameter("projectids");//包含的项目ids，允许为空，多个时用逗号拼接，示例：1,2,3...
        String filterProjectIds = multiRequest.getParameter("filterprojectids");//排除的项目ids，允许为空，多个时用逗号拼接，示例：1,2,3...
        String canUnique = multiRequest.getParameter("canunique");//是否唯一，不允许为空，数字
        String version = multiRequest.getParameter("version");//版本 不允许为空 1-20位字母、数字、下划线组成，不含特殊字符
        String remark= multiRequest.getParameter("remark");//备注
        ValidUtil.valid("组件名称[componentname]", componentName, "{'required':true,'regex':'"+RegexConstants.COMPONENT_NAME_PATTERN+"'}");
        ValidUtil.valid("组件类型[componenttype]", componentType, "required");
        ValidUtil.valid("组件分类[classify]", classify, "{'required':true,'numeric':true}");
        ValidUtil.valid("组件是否唯一[canUnique]", canUnique, "{'required':true,'numeric':true}");
        ValidUtil.valid("版本[version]", version, "required");
        Iterator<String>  iter = multiRequest.getFileNames();
        MultipartFile multipartFile = null;
        String resourcesFolderPath = SysConfigUtil.getParamValue("resources_folder_path");//静态资源文件夹路径
        Component component = componentDao.getById(id);
        component.setId(id);
        while(iter.hasNext()){
            multipartFile = multiRequest.getFile((String)iter.next());
            if(multipartFile == null){
                continue;
            }
            String fileName = multipartFile.getName();
            logger.debug("提示：fileName= " + fileName);
            if(StringUtils.isBlank(fileName)){
                continue;
            }
            //组件压缩包
            if(fileName.equals("componentzip")){
                this.dealComponentZip(multiRequest, multipartFile, component, resourcesFolderPath);
            }
            //处理组件图标
            if(fileName.equals("iconpic")){
                String iconPath = component.getIconPath();//组件图标--文件保存相对路径
                IOUtil.savePicture(multipartFile, resourcesFolderPath + iconPath);
            }
            //处理组件缩略图
            else if(fileName.equals("thumbpic")){
                String thumb = component.getThumb();//组件缩略图--文件保存相对路径
                IOUtil.savePicture(multipartFile, resourcesFolderPath + thumb);
            }
        }
        component.setName(componentName);//组件名称
        component.setType(componentType);//组件类型
        component.setCanUnique(CastUtil.toInteger(canUnique));//是否唯一
        component.setClassify(CastUtil.toInteger(classify));//类型
        component.setProjectIds(this.initProjectIds(projectIds));//项目ids
        component.setFilterProjectIds(this.initFilterProjectIds(filterProjectIds));//排除项目ids
        component.setVersion(version);//版本
        component.setRemark(remark);//备注
        componentDao.update(component);
    }
    
    /**
     * 组件详情
     * @param id 组件id
     * @return 组件
     * @author 周颖  
     * @date 2017年4月14日 下午3:05:32
     */
    public Component getById(Long id){
        Component component = componentDao.getById(id);
        component.setId(id);
        String resourcesDomain = SysConfigUtil.getParamValue("resources_domain");
        component.setIconPath(resourcesDomain + component.getIconPath());
        component.setThumb(resourcesDomain + component.getThumb());
        //component.setComponentPath(resourcesDomain + component.getComponentPath());
        String projectNames = getProjectNames(component);
        component.setProjectNames(projectNames);
        projectNames = getFilterProjectNames(component);
        component.setFilterProjectNames(projectNames);
        return component;
    }
    
    /**
     * 项目名称
     * @param component 组件
     * @return 项目名称
     * @author 周颖  
     * @date 2017年4月17日 上午11:16:09
     */
    private String getProjectNames(Component component) {
        String newProjectIds = component.getProjectIdsDsp();
        if(StringUtils.isBlank(newProjectIds)){
            return StringUtils.EMPTY;
        }
        String newProjectNames = "默认";;
        //只有默认，直接返回默认，不查数据库
        if(newProjectIds.equals("0")){
            return newProjectNames;
        }
        logger.debug("提示：newProjectIds=" + newProjectIds);
        String projectNames = projectService.getNamesByIds(newProjectIds);
        String projectIds = component.getProjectIds();
        //含默认时，特殊处理
        if(projectIds.indexOf("{0}") != -1){
            if(StringUtils.isNotBlank(projectNames)){
                newProjectNames+= "," + projectNames;
            }
        }else{
            newProjectNames = projectNames;
        }
        return StringUtils.defaultString(newProjectNames);
    }
    
    /**
     * 过滤项目名称
     * @param component 组件
     * @return 过滤项目名称
     * @author 周颖  
     * @date 2017年4月17日 下午1:28:59
     */
    private String getFilterProjectNames(Component component) {
        String newProjectIds = component.getFilterProjectIdsDsp();
        if(StringUtils.isBlank(newProjectIds)){
            return StringUtils.EMPTY;
        }
        String projectNames = projectService.getNamesByIds(newProjectIds);
        return StringUtils.defaultString(projectNames);
    }
    
    /**
     * 处理压缩包
     * @param request 请求
     * @param multipartFile 压缩包
     * @param component 组件
     * @param resourcesFolderPath 资源文件路径
     * @return 文件路径
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年4月13日 下午1:46:16
     */
    private String dealComponentZip(HttpServletRequest request, MultipartFile multipartFile,Component component,String resourcesFolderPath) throws Exception{
        File zipTempFile = null;
        String componentPath = component.getComponentPath();
        String code = component.getCode();
        String setCode = component.getSetCode();
        try{
            ServletContext servletContext = request.getServletContext();
            if(StringUtils.isBlank(componentPath)){
                componentPath = this.getComponentPath(code);//组件保存路径
            }
            logger.debug("提示：componentPath = " + componentPath);
            String componentFullPath = resourcesFolderPath + componentPath;
            String tempPath = this.getTempPath(request);//组件zip临时保存路径
            String tempFullpath = servletContext.getRealPath(tempPath);
            logger.debug("提示：tempFullpath = " + tempFullpath);
            //1.拷贝到临时路径
            IOUtil.copyFile(multipartFile.getInputStream(), tempFullpath);
            //2.解压缩
            //2.1 删除旧文件
            IOUtil.remove(componentFullPath);
            //2.2 解压
            zipTempFile = new File(tempFullpath);
            ZipUtil.unzip(zipTempFile, componentFullPath);
            //3.文件内容替换
            String path = null;
            //3.1 替换 Entity.js 内容
            path = componentFullPath + File.separator + "script" + File.separator + "Entity.js";
            IOUtil.replaceText(new File(path), "_Entity_", code);
            //3.2 替换 component.css 内容
            path = componentFullPath + File.separator + "css" + File.separator + "component.css";
            IOUtil.replaceText(new File(path), "_Entity_", code);
            //3.3 替换 Setting.js 内容
            path = componentFullPath + File.separator + "script" + File.separator + "Setting.js";
            IOUtil.replaceText(new File(path), "_Setting_", setCode);
            //3.4 替换 setting.css 内容
            path = componentFullPath + File.separator + "css" + File.separator + "setting.css";
            IOUtil.replaceText(new File(path), "_Setting_", setCode);
            //3.5 替换Setting_Mobile.js内容
            path = componentFullPath + File.separator + "script" + File.separator + "Setting_Mobile.js";
            IOUtil.replaceText(new File(path), "_Setting_", setCode);
            //3.6 替换setting_mobile.css内容
            path = componentFullPath + File.separator + "css" + File.separator + "setting_mobile.css";
            IOUtil.replaceText(new File(path), "_Setting_", setCode);
        }catch(Exception e){
            ErrorUtil.printException(e, logger);
            throw e;
        }finally{
            try{
                //释放资源
                if(zipTempFile != null){
                    IOUtil.remove(zipTempFile);
                }
            }catch(Exception e1){}
        }
        return componentPath;
    }
    
    /** 
     * 压缩包临时存放的路径
     * @param request 请求
     * @return 压缩包临时存放路径
     * @author 周颖  
     * @date 2017年4月13日 下午2:06:15
     */
    private String getTempPath(HttpServletRequest request){
        StringBuffer path = new StringBuffer();
        path.append(File.separator).append("file").append(File.separator).append("temp");
        //创建temp目录（一般是已存在的）
        IOUtil.mkDirs(request.getServletContext().getRealPath(path.toString()));
        path.append(File.separator).append(KeyUtil.generateKey()).append(".zip");
        return path.toString();
    }
    
    /**
     * 生成组件保存的相对路径
     * @param code 组件code
     * @return 相对路径
     * @author 周颖  
     * @date 2017年4月13日 下午2:04:43
     */
    private String getComponentPath(String code){
        StringBuffer componentPath = new StringBuffer();
        componentPath.append("/media/component/");
        String date = DateUtil.getTodayDate();
        String[] dataArray = date.split("-");
        String year = dataArray[0];//年
        String month = dataArray[1];//月
        String day = dataArray[2];//日
        componentPath.append(year).append("/").append(month).append("/").append(day).append("/").append(code);
        return componentPath.toString();
    }
    
    /**
     * 获取图片保存的相对路径
     * @param multipartFile 文件
     * @return 相对路径
     * @author 周颖  
     * @date 2017年4月13日 下午2:18:35
     */
    private String getPicPath(MultipartFile multipartFile){
        String date = DateUtil.getTodayDate();
        String[] dataArray = date.split("-");
        String year = dataArray[0];// 年
        String month = dataArray[1];// 月
        String day = dataArray[2];// 日
        StringBuffer folderPath = new StringBuffer(25);// 文件夹路径
       
        folderPath.append("/").append("media").append("/").append("picture").append("/").append(year).append("/")
                .append(month).append("/").append(day);
        String folderPathStr = folderPath.toString();
        logger.debug("提示：folderPath= " + folderPathStr);
        String fileName = multipartFile.getOriginalFilename();
        logger.debug("提示：上传的图片名[fileName]= " + fileName);
        String fileSuffix = getFileSuffix(fileName);// 文件后缀
        logger.debug("提示：上传的图片后缀[fileSuffix]= " + fileSuffix);
      
        StringBuffer picPath = new StringBuffer(62);
        picPath.append(folderPathStr).append("/").append(KeyUtil.generateKey()).append(".").append(fileSuffix);
        return picPath.toString();
    }
    
    /**
     * 获取后缀
     * @param fileName 文件名称
     * @return 后缀
     * @author 周颖  
     * @date 2017年4月13日 上午10:29:45
     */
    public static String getFileSuffix(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return StringUtils.EMPTY;
        }
        String fileSuffix = null;
        int lastIndex = fileName.lastIndexOf(".");
        if (lastIndex != -1) {
            fileSuffix = fileName.substring(lastIndex + 1, fileName.length());
        }
        return fileSuffix != null ? fileSuffix : StringUtils.EMPTY;
    }
    
    /**
     * 项目id转化
     * @param projectIds 项目ids 1,2,3
     * @return {1}{2}{3}   {0}为所有项目能用
     * @author 周颖  
     * @date 2017年4月12日 下午4:13:35
     */
    private String initProjectIds(String projectIds){
        if(StringUtils.isBlank(projectIds)){
            return "{0}";
        }
        String[] projectIdArray = projectIds.split(",");//转数组
        int maxLength = projectIdArray != null ? projectIdArray.length : 0;
        String projectId;
        StringBuilder newProjectIds = new StringBuilder();
        for(int i=0; i<maxLength; i++){
            projectId = projectIdArray[i];
            if(StringUtils.isBlank(projectId)){
                continue;
            }
            newProjectIds.append("{").append(projectId).append("}");
        }
        return newProjectIds.toString();
    }
    
    /**
     * 排除项目ids转化
     * @param projectIds 排除项目ids
     * @return {1}{2}{3}  {-1}为所以项目能用
     * @author 周颖  
     * @date 2017年4月12日 下午4:46:31
     */
    private String initFilterProjectIds(String projectIds){
        if(StringUtils.isBlank(projectIds)){
            return "{-1}";
        }
        String[] projectIdArray = projectIds.split(",");
        int maxLength = projectIdArray != null ? projectIdArray.length : 0;
        String projectId;
        StringBuilder newProjectIds = new StringBuilder();
        for(int i=0; i<maxLength; i++){
            projectId = projectIdArray[i];
            if(StringUtils.isBlank(projectId)){
                continue;
            }
            newProjectIds.append("{").append(projectId).append("}");
        }
        return newProjectIds.toString();
    }
    
    /**
     * 获取组件code(唯一)
     * @return 组件code
     * @author 周颖  
     * @date 2017年4月12日 下午4:55:53
     */
    private String getCode(){
        String code = null;
        int count;
        do{
            code = KeyUtil.generateKey();//生成code
            count = componentDao.getCountByCode(code);//校验数据库是否存在，存在重新生成再校验，直到数据库不存在
        }while(count > 0);
        return code;
    }
    
    /**
     * 获取组件setCode(唯一)
     * @return 组件code
     * @author 周颖  
     * @date 2017年4月12日 下午4:55:53
     */
    private String getSetCode(){
        String setCode = null;
        int count;
        do{
            setCode = KeyUtil.generateKey();//生成code
            count = componentDao.getCountBySetCode(setCode);//校验数据库是否存在，存在重新生成再校验，直到数据库不存在
        }while(count > 0);
        return setCode;
    }
    
    /**
     * 获取组件列表
     * @param merchantId 商户id
     * @param type 组件类型
     * @return 组件列表
     * @author 周颖  
     * @throws Exception 异常
     * @date 2017年5月3日 上午10:12:52
     */
    public List<Component> getListByType(Long merchantId, Integer type) throws Exception{
        List<Component> componentList = null;
        String typeString = "{" + type + "}";
        if(merchantId == null){//建行业地区默认站点的情况
            componentList = componentDao.getListByType(typeString);
        }else{//建商户站点 通过项目过滤组件
            Long projectId = MerchantClient.getByIdCache(merchantId).getProjectId();
            componentList = componentDao.getListByTypeAndProjectId(typeString,"{" + projectId + "}");
        }
        String resourcesDomain = SysConfigUtil.getParamValue("resources_domain");//静态资源域名
        for(Component component : componentList){
            component.setIconPath(resourcesDomain + component.getIconPath());
            component.setThumb(resourcesDomain + component.getThumb());
            component.setComponentPath(resourcesDomain + component.getComponentPath());
        }
        return componentList;
    }
    
    /**
     * 图片组件图片上传
     * @param request 请求
     * @return 图片保存的路径
     * @author 周颖  
     * @throws Exception 
     * @date 2017年6月13日 下午7:29:57
     */
    public String picUpload(HttpServletRequest request) throws Exception{
        CommonsMultipartResolver multipartResolver  = new CommonsMultipartResolver(request.getSession().getServletContext());
        if(!multipartResolver.isMultipart(request)){
            throw new BizException("E2000030", MessageUtil.getMessage("E2000030"));//文件内容不允许为空!
        }
        MultipartHttpServletRequest  multiRequest = (MultipartHttpServletRequest)request;
        Iterator<String>  iter = multiRequest.getFileNames();
        MultipartFile multipartFile = null;
        String imgSrc = null;
        while(iter.hasNext()){
            multipartFile = multiRequest.getFile((String)iter.next());
            if(multipartFile == null){
                continue;
            }
            long imageSize = multipartFile.getSize();
            logger.debug("提示：图片大小[imageSize]= " + imageSize);
            if(imageSize > 204800){//200kb = 200 * 1024
                throw new BizException("E2600014", MessageUtil.getMessage("E2600014"));//图片大小不允许超过200KB！
            }
            imgSrc = getPicPath(multipartFile);//生成图片保存相对路径
            String resourcesFolderPath = SysConfigUtil.getParamValue("resources_folder_path");//静态资源文件夹路径
            IOUtil.savePicture(multipartFile, resourcesFolderPath + imgSrc);
        }
        return imgSrc;
    }
}

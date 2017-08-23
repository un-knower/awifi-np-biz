package com.awifi.np.biz.mersrv.merchant.controller;

import java.io.File;
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
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WritableWorkbook;

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

import com.awifi.np.biz.api.client.dbcenter.industry.util.IndustryClient;
import com.awifi.np.biz.api.client.dbcenter.location.util.LocationClient;
import com.awifi.np.biz.api.client.dbcenter.merchant.model.Merchant;
import com.awifi.np.biz.api.client.dbcenter.merchant.util.MerchantClient;
import com.awifi.np.biz.api.client.dbcenter.user.util.UserAuthClient;
import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.security.permission.util.PermissionUtil;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.template.service.TemplateService;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.ExcelUtil;
import com.awifi.np.biz.common.util.FormatUtil;
import com.awifi.np.biz.common.util.IOUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.mersrv.merchant.service.MerchantService;
import com.awifi.np.biz.toe.admin.project.service.ProjectService;
import com.awifi.np.biz.toe.admin.security.role.service.ToeRoleService;
import com.awifi.np.biz.toe.admin.security.user.model.ToeUser;
import com.awifi.np.biz.toe.admin.security.user.service.ToeUserService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月17日 上午9:33:24
 * 创建作者：周颖
 * 文件名称：MerchantController.java
 * 版本：  v1.0
 * 功能：商户控制层
 * 修改记录：
 */
@Controller
@SuppressWarnings({"rawtypes","unchecked"})
public class MerchantController extends BaseController {

    /**模板服务*/
    @Resource(name = "templateService")
    private TemplateService templateService;
    
    /**toe用户服务*/
    @Resource(name = "toeUserService")
    private ToeUserService toeUserService;
    
    /**toe角色服务*/
    @Resource(name = "toeRoleService")
    private ToeRoleService toeRoleService;
    
    /**商户服务*/
    @Resource(name = "merchantService")
    private MerchantService merchantService;
    
    /**项目服务*/
    @Resource(name = "projectService")
    private ProjectService projectService;
    
    /**
     * 商户显示接口
     * @param accessToken access_token
     * @param templateCode 模板编号
     * @param request 请求
     * @return 模板页面
     * @author 周颖  
     * @date 2017年1月17日 上午9:55:53
     */
    @RequestMapping(method=RequestMethod.GET, value="/mersrv/view/{templatecode}")
    @ResponseBody
    public Map view(@RequestParam(value="access_token",required=true)String accessToken,
                    @PathVariable(value="templatecode") String templateCode,
                    HttpServletRequest request){
        String serviceCode = SysConfigUtil.getParamValue("servicecode_merchant");//从配置表读取商户服务代码
        String suitCode = SessionUtil.getCurSessionUser(request).getSuitCode();//从request获取套码
        if(StringUtils.isBlank(suitCode)){//如果套码为空
            throw new BizException("E2000005", MessageUtil.getMessage("E2000005"));//抛异常
        }
        String template = templateService.getByCode(suitCode,serviceCode,templateCode);//获取模板页面
        return this.successMsg(template);
    } 
    
    /**
     * 商户列表 兼容建管理员账号 入参由projectId/notEqualProjectId改为projectIds/filterProjectIds
     * @param accessToken access_token
     * @param params 请求参数
     * @param request 请求
     * @return 商户列表
     * @author 周颖  
     * @throws Exception 
     * @date 2017年2月3日 上午8:44:11
     */
    @RequestMapping(method = RequestMethod.GET,value = "/mersrv/merchants")
    @ResponseBody
    public Map getListByParam(@RequestParam(value="access_token",required=true)String accessToken,
                              @RequestParam(value="params",required=true)String params,
                              HttpServletRequest request) throws Exception{
        Map<String,Object> paramsMap = JsonUtil.fromJson(params, Map.class);//请求参数 json格式
        Integer pageSize = CastUtil.toInteger(paramsMap.get("pageSize"));//每页记录数
        Integer maxPageSize = Integer.parseInt(SysConfigUtil.getParamValue("page_maxsize"));//每页记录最大数
        ValidUtil.valid("每页数量[pageSize]", pageSize, "{'required':true,'numeric':{'max':"+maxPageSize+"}}");
        String account = (String) paramsMap.get("account");//获取账号
        Long merchantId = null;
        if(StringUtils.isNotBlank(account)){//如果不为空
            merchantId = toeUserService.getMerIdByUserName(account);//通过账号查找对应的商户id
            if(merchantId == null){//未找到商户id 抛异常
                throw new BizException("E2200001", MessageUtil.getMessage("E2200001",account));
            }
        }
        Integer pageNo = CastUtil.toInteger(paramsMap.get("pageNo"));//页码
        if(pageNo == null){//如果为空，默认第一页
            pageNo = 1;
        }
        Page<Merchant> page = new Page<Merchant>(pageNo, pageSize);
        Integer merchantType = CastUtil.toInteger(paramsMap.get("merchantType"));//商户类型 1代表商客、2代表行客
        String keywords = (String)paramsMap.get("merchantName");//商户名称关键字
        String industryId = (String)paramsMap.get("industryCode");//行业编号
        Long provinceId = CastUtil.toLong(paramsMap.get("provinceId"));//省id 
        Long cityId = CastUtil.toLong(paramsMap.get("cityId"));//市id 
        Long areaId = CastUtil.toLong(paramsMap.get("areaId"));//区县id
        String projectIds = (String) paramsMap.get("projectIds");//项目ids
        String filterProjectIds = (String) paramsMap.get("filterProjectIds");//过滤项目ids
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);//登陆用户
        Map<String,Object> searchParam = new HashMap<String,Object>();
        searchParam.put("keywords", keywords);//商户名称关键字
        searchParam.put("provinceId", provinceId);//省id 
        searchParam.put("cityId", cityId);//市id 
        searchParam.put("areaId", areaId);//区县id
        searchParam.put("industryId", industryId);//行业编号
        searchParam.put("projectIds", projectIds);//项目id
        searchParam.put("merchantId", merchantId);//商户id
        searchParam.put("merchantType", merchantType);//商户类型 1代表商客、2代表行客
        searchParam.put("filterProjectIds", filterProjectIds);//排除的项目id
        merchantService.getListByParam(sessionUser,page,searchParam);
        return this.successMsg(page);
    }
    
    /**
     * 添加商户
     * @param access_token access_token
     * @param bodyParam 参数
     * @param request 请求
     * @return 结果
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月4日 下午2:29:31
     */
    @RequestMapping(method=RequestMethod.POST, value="/mersrv/merchant", produces="application/json")
    @ResponseBody
    public Map add(@RequestParam(value="access_token",required=true) String access_token,
                   @RequestBody(required=true) Map<String,Object> bodyParam,
                   HttpServletRequest request) throws Exception{
        String merchantName = (String) bodyParam.get("merchantName");//商户名称，不允许为空
        String account = (String) bodyParam.get("account");//账号，不允许为空
        String roleIds = (String) bodyParam.get("roleIds");//角色ids 不允许为空，多个时用逗号拼接
        String contact = (String) bodyParam.get("contact");//联系人，允许为空
        String contactWay = (String) bodyParam.get("contactWay");//联系方式，允许为空
        Long projectId = CastUtil.toLong(bodyParam.get("projectId"));//项目id 数字，不允许为空
        String industryCode = (String) bodyParam.get("industryCode");//行业编号,不允许为空
        Long provinceId = CastUtil.toLong(bodyParam.get("provinceId"));//省id,数字,不允许为空
        Long cityId = CastUtil.toLong(bodyParam.get("cityId"));//市id，数字，不允许为空
        Long areaId = CastUtil.toLong(bodyParam.get("areaId"));//区县id，数字，不允许为空
        String address = (String) bodyParam.get("address");//详细地址，不允许为空
        String remark = (String) bodyParam.get("remark");//备注
        Integer storeType = CastUtil.toInteger(bodyParam.get("storeType"));//销售点二级分类，允许为空
        Integer storeLevel = CastUtil.toInteger( bodyParam.get("storeLevel"));//自有厅级别，允许为空
        Integer storeStar = CastUtil.toInteger(bodyParam.get("storeStar"));//专营店星级，允许为空
        Integer storeScope = CastUtil.toInteger( bodyParam.get("storeScope"));//专营店类别，允许为空
        String connectType = (String) bodyParam.get("connectType");//接入方式 ，允许为空
        ValidUtil.valid("商户名称[merchantName]", merchantName, "{'required':true,'regex':'"+RegexConstants.MERCHANT_NAME_PATTERN+"'}");
        ValidUtil.valid("账号[account]", account, "{'required':true,'regex':'"+RegexConstants.USER_NAME_PATTERN+"'}");
        ValidUtil.valid("角色ids[roleIds]", roleIds, "required");//校验角色ids
        ValidUtil.valid("项目id[projectId]", projectId, "{'required':true,'numeric':true}");//校验项目id
        ValidUtil.valid("行业编号[industryCode]", industryCode, "required");//校验行业编号
        ValidUtil.valid("省ID[provinceId]", provinceId, "{'required':true,'numeric':true}");//校验省id
        ValidUtil.valid("市ID[cityId]", cityId, "{'required':true,'numeric':true}");//校验市id
        ValidUtil.valid("区县ID[areaId]", areaId, "{'required':true,'numeric':true}");//校验区县id
        ValidUtil.valid("地址[address]", address, "required");//校验地址
        if(merchantService.isMerchantNameExist(merchantName)){//商户名称存在
            throw new ValidException("E2200002", MessageUtil.getMessage("E2200002",merchantName));
        }
        if(toeUserService.isUserNameExist(account)){//账号存在
            throw new ValidException("E2000027", MessageUtil.getMessage("E2000027",account));
        }
        SessionUser curUser = SessionUtil.getCurSessionUser(request);
        Long curUserId = curUser.getId();
        Long merchantId = curUser.getMerchantId();
        Merchant merchant = new Merchant();
        merchant.setParentId(0L);
        if(merchantId != null){//如果当前登陆账号是商户，建的商户默认是这个商户的下级商户
            merchant.setParentId(merchantId);
        }
        merchant.setMerchantName(merchantName);
        merchant.setAccount(account);
        merchant.setRoleIds(roleIds);
        merchant.setContact(contact);
        merchant.setContactWay(contactWay);
        merchant.setProjectId(projectId);
        merchant.setProvinceId(provinceId);
        merchant.setCityId(cityId);
        merchant.setAreaId(areaId);
        merchant.setAddress(address);
        merchant.setRemark(remark);
        merchant.setStoreType(storeType);
        merchant.setStoreLevel(storeLevel);
        merchant.setStoreStar(storeStar);
        merchant.setStoreScope(storeScope);
        merchant.setConnectType(connectType);
        merchantService.add(curUserId,merchant,industryCode);//添加商户
        return this.successMsg();
    }
    
    /**
     * 添加下级商户
     * @param access_token access_token
     * @param bodyParam 参数
     * @param request 请求
     * @return 结果
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月4日 下午2:29:31
     */
    @RequestMapping(method=RequestMethod.POST, value="/mersrv/submerchant", produces="application/json")
    @ResponseBody
    public Map addSub(@RequestParam(value="access_token",required=true) String access_token,
                      @RequestBody(required=true) Map<String,Object> bodyParam,
                      HttpServletRequest request) throws Exception{
        Long parentId = CastUtil.toLong(bodyParam.get("parentId"));//父id 不允许为空
        String merchantName = (String) bodyParam.get("merchantName");//商户名称，不允许为空
        String account = (String) bodyParam.get("account");//账号，不允许为空
        String roleIds = (String) bodyParam.get("roleIds");//角色ids 不允许为空，多个时用逗号拼接
        String contact = (String) bodyParam.get("contact");//联系人，允许为空
        String contactWay = (String) bodyParam.get("contactWay");//联系方式，允许为空
        Long projectId = CastUtil.toLong(bodyParam.get("projectId"));//项目id 数字，不允许为空
        String industryCode = (String) bodyParam.get("industryCode");//行业编号,不允许为空
        Long provinceId = CastUtil.toLong(bodyParam.get("provinceId"));//省id,数字,不允许为空
        Long cityId = CastUtil.toLong(bodyParam.get("cityId"));//市id，数字，允许为空
        Long areaId = CastUtil.toLong(bodyParam.get("areaId"));//区县id，数字，允许为空
        String address = (String) bodyParam.get("address");//详细地址，允许为空
        String remark = (String) bodyParam.get("remark");
        Integer storeType = CastUtil.toInteger(bodyParam.get("storeType"));//销售点二级分类，允许为空
        Integer storeLevel = CastUtil.toInteger( bodyParam.get("storeLevel"));//自有厅级别，允许为空
        Integer storeStar = CastUtil.toInteger(bodyParam.get("storeStar"));//专营店星级，允许为空
        Integer storeScope = CastUtil.toInteger( bodyParam.get("storeScope"));//专营店类别，允许为空
        String connectType = (String) bodyParam.get("connectType");//接入方式 ，允许为空
        ValidUtil.valid("商户名称[merchantName]", merchantName, "{'required':true,'regex':'"+RegexConstants.MERCHANT_NAME_PATTERN+"'}");
        ValidUtil.valid("账号[account]", account, "{'required':true,'regex':'"+RegexConstants.USER_NAME_PATTERN+"'}");
        ValidUtil.valid("父商户id[parentId]", parentId, "{'required':true,'numeric':true}");//校验商户父id
        ValidUtil.valid("角色ids[roleIds]", roleIds, "required");//校验角色ids
        ValidUtil.valid("项目id[projectId]", projectId, "{'required':true,'numeric':true}");//校验项目id
        ValidUtil.valid("行业编号[industryCode]", industryCode, "required");//校验行业编号
        ValidUtil.valid("省ID[provinceId]", provinceId, "{'required':true,'numeric':true}");//校验省id
        ValidUtil.valid("市ID[cityId]", cityId, "{'required':true,'numeric':true}");//校验市id
        ValidUtil.valid("区县ID[areaId]", areaId, "{'required':true,'numeric':true}");//校验区县id
        ValidUtil.valid("地址[address]", address, "required");//校验地址
        if(merchantService.isMerchantNameExist(merchantName)){//商户名称存在
            throw new ValidException("E2200002", MessageUtil.getMessage("E2200002",merchantName));
        }
        if(toeUserService.isUserNameExist(account)){//账号存在
            throw new ValidException("E2000027", MessageUtil.getMessage("E2000027",account));
        }
        Long curUserId = SessionUtil.getCurSessionUser(request).getId();//当前登陆账号id
        Merchant merchant = new Merchant();
        merchant.setParentId(parentId);
        merchant.setMerchantName(merchantName);
        merchant.setAccount(account);
        merchant.setRoleIds(roleIds);
        merchant.setContact(contact);
        merchant.setContactWay(contactWay);
        merchant.setProjectId(projectId);
        merchant.setProvinceId(provinceId);
        merchant.setCityId(cityId);
        merchant.setAreaId(areaId);
        merchant.setAddress(address);
        merchant.setRemark(remark);
        merchant.setStoreType(storeType);
        merchant.setStoreLevel(storeLevel);
        merchant.setStoreStar(storeStar);
        merchant.setStoreScope(storeScope);
        merchant.setConnectType(connectType);
        merchantService.add(curUserId,merchant,industryCode);//添加商户
        return this.successMsg();
    }
    
    /**
     * 商户详情
     * @param access_token access_token
     * @param id 商户id
     * @return 商户详情
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月6日 上午8:56:28
     */
    @ResponseBody
    @RequestMapping(method=RequestMethod.GET,value="/mersrv/merchant/{id}")
    public Map getById(@RequestParam(value="access_token",required=true) String access_token,
                       @PathVariable Long id) throws Exception{
        Merchant merchant = merchantService.getById(id);
        return this.successMsg(merchant);
    }
    
    /**
     * 编辑商户
     * @param access_token access_token
     * @param bodyParam 参数
     * @param id 商户主键
     * @return 结果
     * @author 周颖  
     * @throws Exception 
     * @date 2017年2月6日 下午2:44:12
     */
    @ResponseBody
    @RequestMapping(method=RequestMethod.PUT,value="/mersrv/merchant/{id}")
    public Map update(@RequestParam(value="access_token",required=true) String access_token,
                      @RequestBody(required=true) Map<String,Object> bodyParam,
                      @PathVariable Long id) throws Exception{
        String oldMerchantName = (String) bodyParam.get("oldMerchantName");//原商户名称
        String merchantName = (String) bodyParam.get("merchantName");//商户名称，不允许为空
        String roleIds = (String) bodyParam.get("roleIds");//角色ids 不允许为空，多个时用逗号拼接
        String contact = (String) bodyParam.get("contact");//联系人，允许为空
        String contactWay = (String) bodyParam.get("contactWay");//联系方式，允许为空
        Long oldProjectId = CastUtil.toLong(bodyParam.get("oldProjectId"));//旧项目id 数字，不允许为空
        Long projectId = CastUtil.toLong(bodyParam.get("projectId"));//项目id 数字，不允许为空
        String industryCode = (String) bodyParam.get("industryCode");//行业编号,不允许为空
        Long provinceId = CastUtil.toLong(bodyParam.get("provinceId"));//省id,数字,不允许为空
        Long cityId = CastUtil.toLong(bodyParam.get("cityId"));//市id，数字，允许为空
        Long areaId = CastUtil.toLong(bodyParam.get("areaId"));//区县id，数字，允许为空
        String address = (String) bodyParam.get("address");//详细地址，允许为空
        String remark = (String) bodyParam.get("remark");
        Integer storeType = CastUtil.toInteger(bodyParam.get("storeType"));//销售点二级分类，允许为空
        Integer storeLevel = CastUtil.toInteger( bodyParam.get("storeLevel"));//自有厅级别，允许为空
        Integer storeStar = CastUtil.toInteger(bodyParam.get("storeStar"));//专营店星级，允许为空
        Integer storeScope = CastUtil.toInteger( bodyParam.get("storeScope"));//专营店类别，允许为空
        String connectType = (String) bodyParam.get("connectType");//接入方式 ，允许为空
        ValidUtil.valid("旧商户名称[oldMerchantName]", oldMerchantName, "{'required':true,'regex':'"+RegexConstants.MERCHANT_NAME_PATTERN+"'}");
        ValidUtil.valid("商户名称[merchantName]", merchantName, "{'required':true,'regex':'"+RegexConstants.MERCHANT_NAME_PATTERN+"'}");
        ValidUtil.valid("角色ids[roleIds]", roleIds, "required");
        ValidUtil.valid("旧项目id[oldProjectId]", oldProjectId, "{'required':true,'numeric':true}");
        ValidUtil.valid("项目id[projectId]", projectId, "{'required':true,'numeric':true}");
        ValidUtil.valid("行业编号[industryCode]", industryCode, "required");
        ValidUtil.valid("省ID[provinceId]", provinceId, "{'required':true,'numeric':true}");
        ValidUtil.valid("市ID[cityId]", cityId, "{'required':true,'numeric':true}");//校验市id
        ValidUtil.valid("区县ID[areaId]", areaId, "{'required':true,'numeric':true}");//校验区县id
        ValidUtil.valid("地址[address]", address, "required");//校验地址
        if(!oldMerchantName.equals(merchantName)){//商户名称修改过
            if(merchantService.isMerchantNameExist(merchantName)){//存在 抛异常
                throw new ValidException("E2200002", MessageUtil.getMessage("E2200002",merchantName));
            }
        }
        Map<String,Object> paramsMap = new HashMap<String,Object>();
        boolean isProjectEdit = !oldProjectId.equals(projectId);
        if(isProjectEdit){//项目修改过
            paramsMap.put("merchantId", id);//商户id
            paramsMap.put("type", "nextAll");
            int count = MerchantClient.getCountByParam(paramsMap);//该商户下的所有商户总数
            if(count > 10){//如果大于10，抛异常
                throw new BizException("E2200003", MessageUtil.getMessage("E2200003"));//下级商户个数多于10个，不允许修改项目信息！
            }
        }
        Merchant merchant = new Merchant();
        merchant.setId(id);
        merchant.setMerchantName(merchantName);
        merchant.setRoleIds(roleIds);
        merchant.setContact(contact != null ? contact : StringUtils.EMPTY);
        merchant.setContactWay(contactWay != null ? contactWay : StringUtils.EMPTY);
        merchant.setProjectId(projectId);
        merchant.setProvinceId(provinceId);
        merchant.setCityId(cityId);
        merchant.setAreaId(areaId);
        merchant.setAddress(address);
        merchant.setRemark(remark != null ? remark : StringUtils.EMPTY);
        merchant.setStoreType(storeType);
        merchant.setStoreLevel(storeLevel);
        merchant.setStoreStar(storeStar);
        merchant.setStoreScope(storeScope);
        merchant.setConnectType(connectType);
        merchantService.update(merchant,industryCode);//更新商户信息
        if(isProjectEdit){//如果更新过项目，下级商户账号的项目归属也要改
            merchantService.updateSub(paramsMap,projectId);//更新下级商户项目归属
        }
        return this.successMsg();
    }
    
    /**
     * 批量导入一级商户
     * @param access_token access_token
     * @param request 请求
     * @return 结果
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年4月6日 下午4:27:54
     */
    @ResponseBody
    @RequestMapping(method=RequestMethod.POST,value="/mersrv/merchants")
    public Map importMerchant(@RequestParam(value="access_token",required=true) String access_token,
                              HttpServletRequest request) throws Exception{
        Integer importMaxNum = Integer.parseInt(SysConfigUtil.getParamValue("xls_import_max_size"));//导入最大值
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
            int row = sheet.getRows();//行数
            logger.debug("row:"+row);
            if(row-1 <= 0){//小于等于0 抛异常
                throw new ValidException("E2000030", MessageUtil.getMessage("E2000030"));//文件内容不允许为空!
            }else if(row-1 > importMaxNum){//大于数据导入最大数量
                Object[] message = {row-1,importMaxNum};
                throw new ValidException("E2000031", MessageUtil.getMessage("E2000031", message));//导入的记录总数[{0}]不允许大于[{1}]!
            }
            HashSet<String> merchantNameSet = new HashSet<String>();//临时存储客户集合
            HashSet<String> accountSet = new HashSet<String>();//临时存储账号集合
            Map<Integer, String> merchantNameMap = new TreeMap<Integer, String>();
            Map<Integer, String> accountMap = new TreeMap<Integer, String>();
            Map<String, Long> accountProject = new HashMap<String, Long>();//保存账号和项目的对应关系
            Map<String,Long> accountRole = new HashMap<String,Long>();//存储账号和角色的关系
            Map<String, List<Map<String, Object>>> industryMap = IndustryClient.getIndustryMap();//一次性获取全部行业信息
            Map<String, List<Map<String, Object>>> locationMap = LocationClient.getLocationMap();//一次性获取全部地区信息
            ToeUser user = null;
            Map<String,Object> merchant = null;
            List<Map<String,Object>> merchantList = new ArrayList<Map<String,Object>>();
            String merchantName = null;//商户名称
            String account = null;//账号
            String roleName = null;//角色
            String projectName = null;//项目名称
            String contact = null;//联系人
            String contactWay = null;//联系方式
            String priIndustry = null;//一级行业
            String secIndustry = null;//二级行业
            String province = null;//省
            String city = null;//市
            String area = null;//区
            String address = null;//详细地址
            String remark = null;//备注
            for(int i=1 ; i<row ; i++){
                merchantName = sheet.getCell(0,i).getContents().trim();
                account = sheet.getCell(1, i).getContents().trim();
                roleName = sheet.getCell(2, i).getContents().trim();
                projectName = sheet.getCell(3, i).getContents().trim();
                contact = sheet.getCell(4, i).getContents().trim();
                contactWay = sheet.getCell(5, i).getContents().trim();
                priIndustry = sheet.getCell(6, i).getContents().trim();
                secIndustry = sheet.getCell(7, i).getContents().trim();
                province = sheet.getCell(8, i).getContents().trim();
                city = sheet.getCell(9, i).getContents().trim();
                area = sheet.getCell(10, i).getContents().trim();
                address = sheet.getCell(11, i).getContents().trim();
                remark = sheet.getCell(12, i).getContents().trim();
                //参数校验
                ValidUtil.valid("第"+(i+1)+"行商户名称[merchantName]", merchantName, "{'required':true,'regex':'"+RegexConstants.MERCHANT_NAME_PATTERN+"'}");
                ValidUtil.valid("第"+(i+1)+"行账号[account]", account, "{'required':true,'regex':'"+RegexConstants.USER_NAME_PATTERN+"'}");
                ValidUtil.valid("第"+(i+1)+"行角色", roleName, "required");
                ValidUtil.valid("第"+(i+1)+"行项目名称[projectName]", projectName, "{'required':true,'regex':'"+RegexConstants.PROJECT_PATTERN+"'}");
                ValidUtil.valid("第"+(i+1)+"行一级行业", priIndustry, "required");
                ValidUtil.valid("第"+(i+1)+"行二级行业", secIndustry, "required");
                ValidUtil.valid("第"+(i+1)+"行省", province, "required");
                ValidUtil.valid("第"+(i+1)+"行市", city, "required");
                ValidUtil.valid("第"+(i+1)+"行区", area, "required");
                ValidUtil.valid("第"+(i+1)+"行详细地址", address, "required");
                if(merchantService.isMerchantNameExist(merchantName)){//商户名称存在
                    Object[] message={(i+1),"商户名称",merchantName};
                    throw new ValidException("E2000045", MessageUtil.getMessage("E2000045", message));//第（{0}）行的{1}（{2}）已存在!
                }
                merchantNameSet.add(merchantName);
                merchantNameMap.put(i, merchantName);
                if(merchantNameSet.size() != i){//商户名称重复校验
                    for(Entry<Integer, String> maps : merchantNameMap.entrySet()){
                        if(maps.getValue().equals(merchantName)){
                            Object[] message = {(i+1),"商户名称",(maps.getKey()+1)};
                            throw new ValidException("E2000034", MessageUtil.getMessage("E2000034", message));//第（{0}）行的{1}与第（{2}）行重复!
                        }
                    }
                }
                if(toeUserService.isUserNameExist(account)){//账号存在
                    Object[] message={(i+1),"账号",account};
                    throw new ValidException("E2000045", MessageUtil.getMessage("E2000045", message));//第（{0}）行的{1}（{2}）已存在!
                }
                accountSet.add(account);
                accountMap.put(i, account);
                if(accountSet.size() != i){//账号重复校验
                    for(Entry<Integer, String> maps : accountMap.entrySet()){
                        if(maps.getValue().equals(account)){
                            Object[] message = {(i+1),"账号",(maps.getKey()+1)};
                            throw new ValidException("E2000034", MessageUtil.getMessage("E2000034", message));//第（{0}）行的{1}与第（{2}）行重复!
                        }
                    }
                }
                Long roleId = toeRoleService.getIdByName(roleName);//角色转化为id
                if(roleId == null){
                    Object[] message={(i+1),"角色"};
                    throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message));//第（{0}）行的{1}错误!
                }
                accountRole.put(account, roleId);//保存账号和角色的关系
                merchant = new HashMap<String,Object>();
                Map<String,Object> priIndustryMap = this.getIndustry(priIndustry, industryMap, "1");
                if(priIndustryMap == null){
                    Object[] message={(i+1),"一级行业"};
                    throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message));
                }
                String priIndustryId = (String)priIndustryMap.get("industryId");
                Map<String,Object> secIndustryMap = this.getIndustry(secIndustry, industryMap, "2");
                if(secIndustryMap == null){
                    Object[] message={(i+1),"二级行业"};
                    throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message));
                }
                //判断是否是这个一级行业下的二级行业
                String secIndustryId = (String)secIndustryMap.get("industryId");
                boolean isChild = StringUtils.contains(secIndustryId,priIndustryId);
                if(!isChild){
                    Object[] message={(i+1),"二级行业"};
                    throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message));
                }
                merchant.put("industry", secIndustryId);
                Map<String,Object> provinceMap = this.getLocation(province, locationMap, null);
                if(provinceMap == null){
                    Object[] message={(i+1),"省"};
                    throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message)); 
                }
                Long provinceId = (Long)provinceMap.get("id");
                Map<String,Object> cityMap = this.getLocation(city, locationMap, provinceMap);
                if(cityMap == null){
                    Object[] message={(i+1),"市"};
                    throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message)); 
                }
                Long cityId = (Long)cityMap.get("id");
                Map<String,Object> areaMap = this.getLocation(area, locationMap, cityMap);
                if(areaMap == null){
                    Object[] message={(i+1),"区"};
                    throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message)); 
                }
                Long areaId = (Long)areaMap.get("id");
                Long projectId = projectService.addProject(projectName,contact,contactWay,provinceId,cityId,areaId);
                if(FormatUtil.isMerchantType1(projectId)){//园区 酒店 微站 保存用户信息
                    Long userId = UserAuthClient.addUserAuth(account);
                    merchant.put("userId",userId);
                    merchant.put("merchantType", 1);//商户类型
                }else{
                    merchant.put("merchantType", 2);//商户类型
                }
                accountProject.put(account, projectId);
                merchant.put("userName", account);//账号
                merchant.put("merchantName", merchantName);//商户名称
                merchant.put("fullName", contact);//联系人
                merchant.put("telephone", contactWay);//联系方式
                merchant.put("merchantProject", projectId.toString());//项目
                merchant.put("province", provinceId);//省id
                merchant.put("city", cityId);//市id
                merchant.put("county", areaId);//区id
                merchant.put("address", address);//地址
                merchant.put("remarks", remark);//备注
                merchantList.add(merchant);
            }
            //调数据中心地址，批量导入客户,返回客户和账号的对应关系，要保存到本地数据库，统一赋权限
            Map<String, Long> customerMap=MerchantClient.batchAdd(0L,merchantList);
            for(String key:customerMap.keySet()){
                user = new ToeUser();
                user.setUserName(key);
                user.setProjectId(accountProject.get(key));
                Long userId = toeUserService.add(user);//新建账号
                toeRoleService.addUserRole(userId, accountRole.get(key).toString());//维护账号角色关系
                toeUserService.addUserMerchant(userId, customerMap.get(key));//维护账号商户关系
            }
        }
        return this.successMsg();
    }
    
    /**
     * 批量 添加下级商户
     * @param access_token access_token
     * @param request 请求
     * @param params 参数
     * @return 结果
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年4月10日 下午1:29:50
     */
    @ResponseBody
    @RequestMapping(method=RequestMethod.POST,value="/mersrv/submerchants")
    public Map importSubMerchant(
            @RequestParam(value="access_token",required=true) String access_token,
            HttpServletRequest request,
            @RequestParam(value="params",required=true)String params) throws Exception{
        Map<String,Object> paramsMap = JsonUtil.fromJson(params, Map.class);//请求参数 json格式
        Long parentId = CastUtil.toLong(paramsMap.get("parentId"));//父id 不允许为空
        String industryCode = (String) paramsMap.get("industryCode");//商户行业编号，不允许为空
        Long projectId = CastUtil.toLong(paramsMap.get("projectId"));//账号，不允许为空
        ValidUtil.valid("父商户id[parentId]", parentId, "{'required':true,'numeric':true}");//校验商户父id
        ValidUtil.valid("项目id[projectId]", projectId, "{'required':true,'numeric':true}");//校验项目id
        ValidUtil.valid("行业编号[industryCode]", industryCode, "required");//校验行业编号
        Integer importMaxNum = Integer.parseInt(SysConfigUtil.getParamValue("xls_import_max_size"));
        CommonsMultipartResolver multipartResolver  = new CommonsMultipartResolver(request.getSession().getServletContext());//创建一个通用的多部分解析器.
        if(!multipartResolver.isMultipart(request)){//判断 request 是否有文件上传,即多部分请求
            throw new BizException("E2000030", MessageUtil.getMessage("E2000030"));//文件内容不允许为空!
        }
        Boolean isMerchantType1 = false;//是否园区酒店微站 默认toe
        Integer merchantType = 2;
        if(FormatUtil.isMerchantType1(projectId)){//园区 酒店 微站 保存用户信息
            isMerchantType1 = true;
            merchantType = 1;
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
            int row = sheet.getRows();//行数
            logger.debug("row:"+row);
            if(row-1 <= 0){//小于等于0 抛异常
                throw new ValidException("E2000030", MessageUtil.getMessage("E2000030"));//文件内容不允许为空!
            }else if(row-1 > importMaxNum){//大于数据导入最大数量
                Object[] message = {row-1,importMaxNum};
                throw new ValidException("E2000031", MessageUtil.getMessage("E2000031", message));//导入的记录总数[{0}]不允许大于[{1}]!
            }
            HashSet<String> merchantNameSet = new HashSet<String>();//临时存储客户集合
            HashSet<String> accountSet = new HashSet<String>();//临时存储账号集合
            Map<Integer, String> merchantNameMap = new TreeMap<Integer, String>();
            Map<Integer, String> accountMap = new TreeMap<Integer, String>();
            Map<String,Long> accountRole = new HashMap<String,Long>();//存储账号和角色的关系
            Map<String, List<Map<String, Object>>> locationMap = LocationClient.getLocationMap();//一次性获取全部地区信息
            ToeUser user = null;
            Map<String,Object> merchant = null;
            List<Map<String,Object>> merchantList = new ArrayList<Map<String,Object>>();
            String merchantName = null;//商户名称
            String account = null;//账号
            String roleName = null;//角色
            String contact = null;//联系人
            String contactWay = null;//联系方式
            String province = null;//省
            String city = null;//市
            String area = null;//区
            String address = null;//详细地址
            String remark = null;//备注
            for(int i=1 ; i<row ; i++){
                merchantName = sheet.getCell(0,i).getContents().trim();
                account = sheet.getCell(1, i).getContents().trim();
                roleName = sheet.getCell(2, i).getContents().trim();
                contact = sheet.getCell(3, i).getContents().trim();
                contactWay = sheet.getCell(4, i).getContents().trim();
                province = sheet.getCell(5, i).getContents().trim();
                city = sheet.getCell(6, i).getContents().trim();
                area = sheet.getCell(7, i).getContents().trim();
                address = sheet.getCell(8, i).getContents().trim();
                remark = sheet.getCell(9, i).getContents().trim();
                //参数校验
                ValidUtil.valid("第"+(i+1)+"行商户名称[merchantName]", merchantName, "{'required':true,'regex':'"+RegexConstants.MERCHANT_NAME_PATTERN+"'}");
                ValidUtil.valid("第"+(i+1)+"行账号[account]", account, "{'required':true,'regex':'"+RegexConstants.USER_NAME_PATTERN+"'}");
                ValidUtil.valid("第"+(i+1)+"行角色", roleName, "required");
                ValidUtil.valid("第"+(i+1)+"行省", province, "required");
                ValidUtil.valid("第"+(i+1)+"行市", city, "required");
                ValidUtil.valid("第"+(i+1)+"行区", area, "required");
                ValidUtil.valid("第"+(i+1)+"行详细地址", address, "required");
                if(merchantService.isMerchantNameExist(merchantName)){//商户名称存在
                    Object[] message={(i+1),"商户名称",merchantName};
                    throw new ValidException("E2000045", MessageUtil.getMessage("E2000045", message));//第（{0}）行的{1}（{2}）已存在!
                }
                merchantNameSet.add(merchantName);
                merchantNameMap.put(i, merchantName);
                if(merchantNameSet.size() != i){//商户名称重复校验
                    for(Entry<Integer, String> maps : merchantNameMap.entrySet()){
                        if(maps.getValue().equals(merchantName)){
                            Object[] message = {(i+1),"商户名称",(maps.getKey()+1)};
                            throw new ValidException("E2000034", MessageUtil.getMessage("E2000034", message));//第（{0}）行的{1}与第（{2}）行重复!
                        }
                    }
                }
                if(toeUserService.isUserNameExist(account)){//账号存在
                    Object[] message={(i+1),"账号",account};
                    throw new ValidException("E2000045", MessageUtil.getMessage("E2000045", message));//第（{0}）行的{1}（{2}）已存在!
                }
                accountSet.add(account);
                accountMap.put(i, account);
                if(accountSet.size() != i){//账号重复校验
                    for(Entry<Integer, String> maps : accountMap.entrySet()){
                        if(maps.getValue().equals(account)){
                            Object[] message = {(i+1),"账号",(maps.getKey()+1)};
                            throw new ValidException("E2000034", MessageUtil.getMessage("E2000034", message));//第（{0}）行的{1}与第（{2}）行重复!
                        }
                    }
                }
                Long roleId = toeRoleService.getIdByName(parentId,roleName);
                if(roleId == null){//没有找到角色
                    Object[] message={(i+1),"角色"};
                    throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message));//第（{0}）行的{1}错误!
                }
                accountRole.put(account, roleId);//保存账号和角色的关系
                Map<String,Object> provinceMap = this.getLocation(province, locationMap, null);//根据省名称获取省id
                if(provinceMap == null){
                    Object[] message={(i+1),"省"};
                    throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message)); 
                }
                Long provinceId = (Long)provinceMap.get("id");
                Map<String,Object> cityMap = this.getLocation(city, locationMap, provinceMap);
                if(cityMap == null){
                    Object[] message={(i+1),"市"};
                    throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message)); 
                }
                Long cityId = (Long)cityMap.get("id");
                Map<String,Object> areaMap = this.getLocation(area, locationMap, cityMap);
                if(areaMap == null){
                    Object[] message={(i+1),"区"};
                    throw new ValidException("E2000057", MessageUtil.getMessage("E2000057", message)); 
                }
                Long areaId = (Long)areaMap.get("id");
                merchant = new HashMap<String,Object>();
                if(isMerchantType1){//园区 酒店 微站 保存用户信息
                    Long userId = UserAuthClient.addUserAuth(account);
                    merchant.put("userId",userId);
                }
                merchant.put("userName", account);//账号
                merchant.put("industry", industryCode);//行业
                merchant.put("merchantName", merchantName);//商户名称
                merchant.put("fullName", contact);//联系人
                merchant.put("telephone", contactWay);//联系方式
                merchant.put("merchantType", merchantType);//商户类型
                merchant.put("merchantProject", projectId.toString());//项目
                merchant.put("province", provinceId);//省id
                merchant.put("city", cityId);//市id
                merchant.put("county", areaId);//区id
                merchant.put("address", address);//地址
                merchant.put("remarks", remark);//备注
                merchantList.add(merchant);
            }
            //调数据中心地址，批量导入商户,返回账号和商户的对应关系，要保存到toe数据库
            Map<String, Long> customerMap=MerchantClient.batchAdd(parentId,merchantList);
            for(String key:customerMap.keySet()){
                user = new ToeUser();
                user.setUserName(key);
                user.setProjectId(projectId);
                Long userId = toeUserService.add(user);//新建账号
                toeRoleService.addUserRole(userId, accountRole.get(key).toString());//维护账号角色关系
                toeUserService.addUserMerchant(userId, customerMap.get(key));//维护账号商户关系
            }
        }
        return this.successMsg();
    }
    
    /**
     * 商户导出
     * @param request 请求
     * @param response 响应
     * @param access_token access_token
     * @param params 参数
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年4月10日 下午2:21:56
     */
    @RequestMapping(method=RequestMethod.GET, value = "/mersrv/merchants/xls")
    public void export(
            HttpServletRequest request,HttpServletResponse response, 
            @RequestParam(value="access_token",required=true) String access_token,
            @RequestParam(name="params",required=true) String params) throws Exception{
        Map<String,Object> paramsMap = JsonUtil.fromJson(params, Map.class);//请求参数 json格式
        String account = (String) paramsMap.get("account");//获取账号
        Long merchantId = null;
        if(StringUtils.isNotBlank(account)){//如果不为空
            merchantId = toeUserService.getMerIdByUserName(account);//通过账号查找对应的商户id
            if(merchantId == null){//未找到商户id 抛异常
                throw new BizException("E2200001", MessageUtil.getMessage("E2200001",account));
            }
        }
        Integer merchantType = CastUtil.toInteger(paramsMap.get("merchantType"));//商户类型 1代表商客、2代表行客
        String keywords = (String)paramsMap.get("merchantName");//商户名称关键字
        String industryId = (String)paramsMap.get("industryCode");//行业编号
        Long provinceId = CastUtil.toLong(paramsMap.get("provinceId"));//省id 
        Long cityId = CastUtil.toLong(paramsMap.get("cityId"));//市id 
        Long areaId = CastUtil.toLong(paramsMap.get("areaId"));//区县id 
        Long projectId = CastUtil.toLong(paramsMap.get("projectId"));//项目id
        String projectIds = null;
        if(projectId != null){
            projectIds = projectId.toString();
        }
        String filterProjectIds = null;
        Integer notEqualProjectId = CastUtil.toInteger(paramsMap.get("notEqualProjectId"));//排除的项目id
        if(notEqualProjectId != null){
            filterProjectIds = notEqualProjectId.toString();
        }
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        String path = request.getServletContext().getRealPath(File.separator + "file" + File.separator + "temp");//创建路径
        Integer pageSize = Integer.valueOf(SysConfigUtil.getParamValue("xls_export_sheet_max_size")).intValue();//每页数量--工作表每页数量
        Map<String, Object> param = PermissionUtil.dataPermission(sessionUser, merchantId, provinceId, cityId, areaId,projectIds,filterProjectIds);//数据权限
        Map<String,Object> searchParam = new HashMap<String,Object>();
        searchParam.put("keywords", keywords);
        searchParam.put("industryId", industryId);
        searchParam.put("merchantType", merchantType);
        searchParam.put("filterProjectIds", (String)param.get("filterProjectIds"));
        searchParam.put("projectIds", (String)param.get("projectIds"));
        searchParam.put("merchantIds", (String) param.get("merchantIds"));
        searchParam.put("merchantId", (Long) param.get("merchantId"));
        searchParam.put("type", (String) param.get("type"));
        searchParam.put("provinceId", (Long) param.get("provinceId"));
        searchParam.put("cityId", (Long) param.get("cityId"));
        searchParam.put("areaId", (Long) param.get("areaId"));
        searchParam.put("pageSize", pageSize);
        int maxSize = 0;//数据的最大长度
        Integer sheetNum = 1;//sheet编号
        String sheetName = null;//sheet名称
        List<Object[]> listObj = new ArrayList<Object[]>();
        String fileName = "merchantExport.xls";//文件名称
        String[] rowName = {"ID","商户名称 ","账号","项目名称","行业","地区","上级商户"};//设置列表名称
        WritableWorkbook book = Workbook.createWorkbook(new File(path + File.separator + fileName));//创建文件
        do{
            searchParam.put("pageNo", sheetNum);
            listObj = merchantService.getExportList(searchParam,rowName.length);//获取数据
            maxSize = listObj.size();//数据长度
            sheetName = getSheetName(sheetNum,pageSize);
            ExcelUtil.fileWriteData(book,sheetName,sheetNum,rowName,listObj);//写文件内容
            sheetNum ++;
        } while (pageSize == maxSize);
        book.write();
        book.close();
        IOUtil.download(fileName, path, response);
    }
    
    /**
     * 创建sheet名称
     * @param sheetNum sheet
     * @param pageSize 一页sheet条数
     * @return sheet名称
     * @author 周颖  
     * @date 2017年4月10日 下午2:36:18
     */
    private String getSheetName(Integer sheetNum, Integer pageSize) {
        return (((sheetNum - 1) * pageSize)+1)+"--"+(sheetNum * pageSize);
    }
    
    
    /**
     * 商户重置密码
     * @param access_token access_token
     * @param id 商户主键id
     * @return 结果
     * @author 周颖  
     * @date 2017年4月6日 下午4:44:30
     */
    @ResponseBody
    @RequestMapping(method=RequestMethod.PUT,value="/mersrv/merchant/{id}/resetpwd")
    public Map resetPassword(@RequestParam(value="access_token",required=true) String access_token,@PathVariable Long id){
        merchantService.resetPassword(id);
        return this.successMsg();
    }
    
    /**
     * 根据行业名称查找行业id
     * @param industryName 行业名称
     * @param industryMap 行业map
     * @param labelLevel 层级
     * @return 对应的行业
     * @author ZhouYing 
     * @date 2016年3月29日 下午2:01:17
     */
    private Map<String,Object> getIndustry(String industryName, Map<String, List<Map<String,Object>>> industryMap,String labelLevel){
        List<Map<String,Object>> industryList = industryMap.get(industryName);
        if(industryList == null){
            return null;
        }
        int maxSize = industryList.size();
        if(maxSize <= 0){//没有数据返回空值
            return null;
        }
        Map<String,Object> industry = new HashMap<String,Object>();
        for(int i=0;i<maxSize;i++){
            industry = industryList.get(i);
            if(industry.get("industryLevel").equals(labelLevel)){//层级相同
                return industry;
            }
        }
        return null;
    }
    
    /**
     * 根据地区名称获取对应的id
     * @param locationName 地区名称
     * @param locationMap 地区map
     * @param parentLocation 父地区
     * @return 对应的地区
     * @author ZhouYing 
     * @date 2016年3月29日 下午1:58:36
     */
    private Map<String,Object> getLocation(String locationName, Map<String,List<Map<String,Object>>> locationMap, Map<String,Object> parentLocation){
        List<Map<String,Object>> locationList = locationMap.get(locationName);
        if(locationList == null){
            return null;
        }
        int maxSize = locationList.size();
        if(maxSize <= 0){//没有数据返回空值
            return null;
        }
        Map<String,Object> location = null;
        for(int i=0; i<maxSize; i++){
            location = locationList.get(i);
            if(parentLocation == null && location.get("parentId").equals(1L)){
                return location;
            }
            if(location.get("parentId").equals(parentLocation.get("id"))){
                return location;
            }
        }
        return null;
    }   
}

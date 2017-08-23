package com.awifi.np.biz.usrsrv.staticuser.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import jxl.Sheet;
import jxl.Workbook;

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
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.template.service.TemplateService;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.EncryUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.RegexUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.toe.admin.usrmgr.staticuser.model.StaticUser;
import com.awifi.np.biz.toe.admin.usrmgr.staticuser.service.StaticUserService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月8日 下午5:08:46
 * 创建作者：周颖
 * 文件名称：StaticUserController.java
 * 版本：  v1.0
 * 功能：静态用户控制层
 * 修改记录：
 */
@Controller
@SuppressWarnings({"rawtypes","unchecked"})
public class StaticUserController extends BaseController {

    /**模板服务*/
    @Resource(name = "templateService")
    private TemplateService templateService;
    
    /**toe静态用户服务*/
    @Resource(name = "staticUserService")
    private StaticUserService staticUserService;
    
    /**
     * 用户服务显示接口
     * @param accessToken access_token
     * @param templateCode 模板编号
     * @param request 请求
     * @return 模板页面
     * @author 周颖  
     * @date 2017年2月8日 下午6:36:14
     */
    @RequestMapping(method=RequestMethod.GET, value="/usrsrv/view/{templatecode}")
    @ResponseBody
    public Map view(@RequestParam(value="access_token",required=true)String accessToken,@PathVariable(value="templatecode") String templateCode,HttpServletRequest request){
        String serviceCode = SysConfigUtil.getParamValue("servicecode_user");//从配置表读取商户服务代码
        String suitCode = SessionUtil.getCurSessionUser(request).getSuitCode();//从request获取套码
        if(StringUtils.isBlank(suitCode)){//如果套码为空
            throw new BizException("E2000005", MessageUtil.getMessage("E2000005"));//套码不允许为空!
        }
        String template = templateService.getByCode(suitCode,serviceCode,templateCode);//获取模板页面
        return this.successMsg(template);
    } 
    
    /**
     * 静态用户列表
     * @param accessToken access_token
     * @param params 参数
     * @param request 请求
     * @return 列表数据
     * @author 周颖  
     * @throws Exception 
     * @date 2017年2月9日 上午9:18:58
     */
    @RequestMapping(method=RequestMethod.GET, value="/usrsrv/staticusers")
    @ResponseBody
    public Map getListByParam(@RequestParam(value="access_token",required=true)String accessToken,@RequestParam(value="params",required=true)String params,HttpServletRequest request) throws Exception{
        Map<String,Object> paramsMap = JsonUtil.fromJson(params, Map.class);//请求参数 json格式
        Integer pageSize = (Integer) paramsMap.get("pageSize");//每页记录数
        Integer maxPageSize = Integer.parseInt(SysConfigUtil.getParamValue("page_maxsize"));//每页记录最大数
        ValidUtil.valid("每页数量[pageSize]", pageSize, "{'required':true,'numeric':{'max':"+maxPageSize+"}}");//校验每页记录数 不能大于最大数
        String keywords = (String) paramsMap.get("keywords");//关键字，允许为空， 支持[用户名|手机号|护照|身份证]模糊查询
        Long merchantId = CastUtil.toLong(paramsMap.get("merchantId"));//商户id，数字，允许为空
        Integer userType = CastUtil.toInteger(paramsMap.get("userType"));//用户类型，数字，允许为空， 1代表普通用户、2代表VIP客户、3代表终端体验区
        Integer pageNo = CastUtil.toInteger(paramsMap.get("pageNo"));//页码，数字，允许为空，默认第1页
        if(pageNo == null){//如果为空，默认第一页
            pageNo = 1;
        }
        Page<StaticUser> page = new Page<StaticUser>(pageNo,pageSize);
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        staticUserService.getListByParam(page,keywords,merchantId,userType,sessionUser);
        return this.successMsg(page);
    }
    
    /**
     * 添加用户
     * @param accessToken access_token
     * @param bodyParam 请求体
     * @return 结果
     * @author 周颖  
     * @date 2017年2月9日 下午4:10:24
     */
    @RequestMapping(method=RequestMethod.POST, value="/usrsrv/staticuser",produces="application/json")
    @ResponseBody
    public Map add(@RequestParam(value="access_token",required=true)String accessToken, @RequestBody(required=true) Map<String,Object> bodyParam){
        Integer userType = CastUtil.toInteger(bodyParam.get("userType"));//用户类型，数字，不允许为空  取值范围：1代表普通员工、2代表VIP客户、3代表终端体验区
        Long merchantId = CastUtil.toLong(bodyParam.get("merchantId"));//商户id，数字，不允许为空
        String cascadeLabel = (String)bodyParam.get("cascadeLabel");//商户层级关系，不允许为空
        String userName = (String) bodyParam.get("userName");//用户名，不允许为空，正则校验[1-50位字符，包括字母、数字、下划线、连接符]
        String password = (String) bodyParam.get("password");//密码，不允许为空 正则校验[1-50位字符，包含字母、数字、下划线、连接符、@、$]
        Integer userInfoType = CastUtil.toInteger(bodyParam.get("userInfoType"));//用户信息类别，数字，不允许为空 取值范围：1代表手机号、2代表护照号 、3代表身份证号 
        String cellphone = (String) bodyParam.get("cellphone");//手机号，当userInfoType==1且不为特通时，不允许为空，其它情况，允许为空 正则校验[1开头的11位符合手机号码规则的数字]
        String passport = (String) bodyParam.get("passport");//护照，当userInfoType==2且不为特通时，不允许为空，其它情况，允许为空 正则校验[1-20位字母、数字]
        String identityCard = (String) bodyParam.get("identityCard");//身份证号，当userInfoType==3且不为特通时，不允许为空，其它情况，允许为空 正则校验[18位数字或17位数字最后一位字母X]
        String realName = (String) bodyParam.get("realName");//真实姓名，允许为空，正则校验[1-20位字符，包括字母、汉字]
        String deptName = (String) bodyParam.get("deptName");//部门，允许为空，正则校验[1-20位汉字、字母、数字]
        String remark = (String) bodyParam.get("remark");//备注，允许为空
        ValidUtil.valid("用户类型[userType]", userType, "{'required':true,'numeric':true}");
        ValidUtil.valid("商户id[merchantId]", merchantId, "{'required':true,'numeric':true}");
        ValidUtil.valid("商户层级关系[cascadeLabel]", cascadeLabel, "required");
        ValidUtil.valid("用户名[userName]", userName, "{'required':true,'regex':'"+RegexConstants.STATIC_USER_NAME+"'}");
        ValidUtil.valid("密码[password]", password, "{'required':true,'regex':'"+RegexConstants.PASSWORD+"'}");
        ValidUtil.valid("用户信息类别[userInfoType]", userInfoType, "{'required':true,'numeric':true}");
        if(StringUtils.isNotBlank(cellphone)){//不为空正则校验
            ValidUtil.valid("手机号[cellphone]", cellphone, "{'regex':'"+RegexConstants.CELLPHONE+"'}");
        }
        if(StringUtils.isNotBlank(passport)){//不为空正则校验
            ValidUtil.valid("护照[passport]", passport, "{'regex':'"+RegexConstants.PASSPORT+"'}");
        }
        if(StringUtils.isNotBlank(identityCard)){//不为空正则校验
            ValidUtil.valid("身份证号[identityCard]", identityCard, "{'regex':'"+RegexConstants.IDENTITY_CARD+"'}");
        }
        if(StringUtils.isNotBlank(realName)){
            ValidUtil.valid("姓名[realName]", realName, "{'regex':'"+RegexConstants.REAL_NAME+"'}");
        }
        if(StringUtils.isNotBlank(deptName)){
            ValidUtil.valid("部门[deptName]", deptName, "{'regex':'"+RegexConstants.DEPT_NAME+"'}");
        }
        boolean isSC = RegexUtil.match(userName, RegexConstants.SC_PATTERN, Pattern.CASE_INSENSITIVE);//判断是否是特通账号
        if(!isSC){
            if(userInfoType.equals(1)){//手机号不允许为空
                ValidUtil.valid("手机号[cellphone]", cellphone, "required");
            }else if(userInfoType.equals(2)){//护照不允许为空
                ValidUtil.valid("护照[passport]", passport, "required");
            }else if(userInfoType.equals(3)){
                ValidUtil.valid("身份证号[identityCard]", identityCard, "required");
            }
        }
        if(staticUserService.isUserNameExist(merchantId,userName)){//判断该商户下用户名是否已经存在
            throw new ValidException("E2000028", MessageUtil.getMessage("E2000028", userName));
        }
        if(StringUtils.isNotBlank(cellphone)){//手机号不为空 商户下手机号唯一校验
            if(staticUserService.isCellphoneExist(merchantId,cellphone)){
                throw new ValidException("E2000040", MessageUtil.getMessage("E2000040", cellphone));
            }
        }
        StaticUser staticUser = new StaticUser();
        staticUser.setUserType(userType);
        staticUser.setMerchantId(merchantId);
        staticUser.setCascadeLabel(cascadeLabel);
        staticUser.setUserName(userName);
        staticUser.setPassword(EncryUtil.getMd5Str(password));
        staticUser.setUserInfoType(userInfoType);
        staticUser.setCellphone(cellphone);
        staticUser.setPassport(passport);
        staticUser.setIdentityCard(identityCard);
        staticUser.setRealName(realName);
        staticUser.setDeptName(deptName);
        staticUser.setRemark(remark);
        staticUserService.add(staticUser);
        return this.successMsg(); 
    }
    
    /**
     * 编辑用户
     * @param accessToken access_token
     * @param bodyParam 参数
     * @param id 用户id
     * @return 结果
     * @author 周颖  
     * @date 2017年2月9日 下午7:21:38
     */
    @RequestMapping(method=RequestMethod.PUT, value="/usrsrv/staticuser/{id}",produces="application/json")
    @ResponseBody
    public Map update(@RequestParam(value="access_token",required=true)String accessToken, @RequestBody(required=true) Map<String,Object> bodyParam,@PathVariable Long id){
        Long merchantId = CastUtil.toLong(bodyParam.get("merchantId"));//商户id
        String userName = (String) bodyParam.get("userName");//不可改 用户名，不允许为空，正则校验[1-50位字符，包括字母、数字、下划线、连接符]
        Integer userInfoType = CastUtil.toInteger(bodyParam.get("userInfoType"));//用户信息类别，数字，不允许为空 取值范围：1代表手机号、2代表护照号 、3代表身份证号 
        String cellphone = (String) bodyParam.get("cellphone");//手机号，当userInfoType==1且不为特通时，不允许为空，其它情况，允许为空 正则校验[1开头的11位符合手机号码规则的数字]
        String passport = (String) bodyParam.get("passport");//护照，当userInfoType==2且不为特通时，不允许为空，其它情况，允许为空 正则校验[1-20位字母、数字]
        String identityCard = (String) bodyParam.get("identityCard");//身份证号，当userInfoType==3且不为特通时，不允许为空，其它情况，允许为空 正则校验[18位数字或17位数字最后一位字母X]
        String realName = (String) bodyParam.get("realName");//真实姓名，允许为空，正则校验[1-20位字符，包括字母、汉字]
        String deptName = (String) bodyParam.get("deptName");//部门，允许为空，正则校验[1-20位汉字、字母、数字]
        String remark = (String) bodyParam.get("remark");//备注，允许为空
        ValidUtil.valid("商户id[merchantId]", merchantId, "{'required':true,'numeric':true}");
        ValidUtil.valid("用户名[userName]", userName, "{'required':true,'regex':'"+RegexConstants.STATIC_USER_NAME+"'}");
        ValidUtil.valid("用户信息类别[userInfoType]", userInfoType, "{'required':true,'numeric':true}");
        if(StringUtils.isNotBlank(cellphone)){//不为空正则校验
            ValidUtil.valid("手机号[cellphone]", cellphone, "{'regex':'"+RegexConstants.CELLPHONE+"'}");
        }
        if(StringUtils.isNotBlank(passport)){//不为空正则校验
            ValidUtil.valid("护照[passport]", passport, "{'regex':'"+RegexConstants.PASSPORT+"'}");
        }
        if(StringUtils.isNotBlank(identityCard)){//不为空正则校验
            ValidUtil.valid("身份证号[identityCard]", identityCard, "{'regex':'"+RegexConstants.IDENTITY_CARD+"'}");
        }
        if(StringUtils.isNotBlank(realName)){
            ValidUtil.valid("姓名[realName]", realName, "{'regex':'"+RegexConstants.REAL_NAME+"'}");
        }
        if(StringUtils.isNotBlank(deptName)){
            ValidUtil.valid("部门[deptName]", deptName, "{'regex':'"+RegexConstants.DEPT_NAME+"'}");
        }
        boolean isSC = RegexUtil.match(userName, RegexConstants.SC_PATTERN, Pattern.CASE_INSENSITIVE);//判断是否是特通账号
        if(!isSC){
            if(userInfoType.equals(1)){//手机号不允许为空
                ValidUtil.valid("手机号[cellphone]", cellphone, "required");
            }else if(userInfoType.equals(2)){//护照不允许为空
                ValidUtil.valid("护照[passport]", passport, "required");
            }else if(userInfoType.equals(3)){
                ValidUtil.valid("身份证号[identityCard]", identityCard, "required");
            }
        }
        if(StringUtils.isNotBlank(cellphone)){//手机号不为空 商户下手机号唯一校验
            if(staticUserService.isCellphoneExist(id,merchantId,cellphone)){
                throw new ValidException("E2000040", MessageUtil.getMessage("E2000040", cellphone));
            }
        }
        StaticUser staticUser = new StaticUser();
        staticUser.setId(id);
        staticUser.setUserInfoType(userInfoType);
        staticUser.setCellphone(cellphone);
        staticUser.setPassport(passport);
        staticUser.setIdentityCard(identityCard);
        staticUser.setRealName(realName);
        staticUser.setDeptName(deptName);
        staticUser.setRemark(remark);
        staticUserService.update(staticUser);
        return this.successMsg();
    }
    
    /**
     * 静态用户详情
     * @param accessToken access_token
     * @param id 静态用户主键
     * @return 详情
     * @author 周颖  
     * @throws Exception 
     * @date 2017年2月10日 上午8:31:46
     */
    @RequestMapping(method=RequestMethod.GET, value="/usrsrv/staticuser/{id}")
    @ResponseBody
    public Map getById(@RequestParam(value="access_token",required=true)String accessToken,@PathVariable Long id) throws Exception{
        StaticUser staticUser = staticUserService.getById(id);
        return this.successMsg(staticUser);
    }
    
    /**
     * 删除单条用户
     * @param accessToken access_token
     * @param id 主键id
     * @return 结果
     * @author 周颖  
     * @date 2017年2月10日 上午9:21:31
     */
    @RequestMapping(method=RequestMethod.DELETE, value="/usrsrv/staticuser/{id}")
    @ResponseBody
    public Map delete(@RequestParam(value="access_token",required=true)String accessToken,@PathVariable Long id){
        staticUserService.delete(id);
        return this.successMsg();
    }
    
    /**
     * 批量删除用户
     * @param accessToken access_token
     * @param ids 用户主键ids 多个以逗号拼接
     * @return 结果
     * @author 周颖  
     * @date 2017年2月10日 上午9:24:20
     */
    @RequestMapping(method=RequestMethod.DELETE, value="/usrsrv/staticusers")
    @ResponseBody
    public Map batchDelete(@RequestParam(value="access_token",required=true)String accessToken,@RequestParam(value="ids",required=true)String ids){
        ValidUtil.valid("静态用户ids", ids, "required");
        staticUserService.batchDelete(ids);
        return this.successMsg();
    }
    
    /**
     * 一键删除用户 商户下的所有用户
     * @param accessToken access_token
     * @param merchantId 商户id
     * @return 结果
     * @author 周颖  
     * @date 2017年2月10日 上午9:56:07
     */
    @RequestMapping(method=RequestMethod.DELETE, value="/usrsrv/staticusers/merchant/{merchantid}")
    @ResponseBody
    public Map deleteByMerchantId(@RequestParam(value="access_token",required=true)String accessToken,@PathVariable(value="merchantid") Long merchantId){
        staticUserService.deleteByMerchantId(merchantId);
        return this.successMsg();
    }
    
    /**
     * 批量导入静态用户
     * @param accessToken access_token
     * @param request 请求
     * @param params 参数
     * @return 结果
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年2月10日 下午4:56:33
     */
    @RequestMapping(method=RequestMethod.POST, value="/usrsrv/staticusers")
    @ResponseBody
    public Map batchAdd(@RequestParam(value="access_token",required=true)String accessToken,HttpServletRequest request,@RequestParam(value="params",required=true)String params) throws Exception{
        logger.debug("批量导入静态用户入参params:"+params);
        Map<String,Object> paramsMap = JsonUtil.fromJson(params, Map.class);
        Long merchantId = CastUtil.toLong(paramsMap.get("merchantId"));
        String cascadeLabel = (String) paramsMap.get("cascadeLabel");
        ValidUtil.valid("商户id[merchantId]", merchantId, "{'required':true,'numeric':true}");
        ValidUtil.valid("商户层级[cascadeLabel]", cascadeLabel, "required");
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
            int row = sheet.getRows();//行数
            logger.debug("row:"+row);
            if(row-1 <= 0){//小于等于0 抛异常
                throw new ValidException("E2000030", MessageUtil.getMessage("E2000030"));//文件内容不允许为空!
            }else if(row-1 > importMaxNum){//大于数据导入最大数量
                Object[] message = {row-1,importMaxNum};
                throw new ValidException("E2000031", MessageUtil.getMessage("E2000031", message));//导入的记录总数[{0}]不允许大于[{1}]!
            }
            StaticUser staticUser = null;
            String userTypeDsp = null;  //用户类型
            Integer userType = null;
            String userName = null;  //用户名
            String password = null;  //密码
            String cellphone = null; //手机号
            String identityCard = null;//身份证
            String passport = null;  //护照
            String realName = null;  //真实姓名
            String deptName = null;  //部门名称
            String remark = null;    //备注 
            HashSet<String> userNameSet = new HashSet<String>();//临时存储用户名集合
            HashSet<String> cellphoneSet = new HashSet<String>();//临时存储手机号集合
            Map<Integer, String> userNameMap = new TreeMap<Integer, String>();
            Map<Integer, String> cellphoneMap = new TreeMap<Integer, String>();
            List<StaticUser> successList = new ArrayList<StaticUser>(row-1);
            for(int i=1 ; i< row ; i++){
                userTypeDsp = sheet.getCell(0,i).getContents().trim();//用户类型
                if(StringUtils.equals(userTypeDsp, "普通员工")){
                    userType = 1;
                }else if(StringUtils.equals(userTypeDsp, "VIP客户")){
                    userType = 2;
                }else if(StringUtils.equals(userTypeDsp, "终端体验区")){
                    userType = 3;
                }else{
                    Object[] message = {(i+1),"用户类型",userTypeDsp};
                    throw new ValidException("E2000046", MessageUtil.getMessage("E2000046", message));
                }
                userName = sheet.getCell(1,i).getContents().trim();//用户名
                ValidUtil.valid("第"+ (i+1) + "行用户名", userName, "{'required':true,'regex':'"+RegexConstants.STATIC_USER_NAME+"'}"); 
                password = sheet.getCell(2,i).getContents().trim();//密码
                ValidUtil.valid("第"+ (i+1) + "行密码", password, "{'required':true,'regex':'"+RegexConstants.PASSWORD+"'}");
                cellphone = sheet.getCell(3,i).getContents().trim();//手机号
                identityCard = sheet.getCell(4,i).getContents().trim();//身份证号
                passport = sheet.getCell(5,i).getContents().trim();//护照
                if(StringUtils.isNotBlank(cellphone)){//不为空 正则校验
                    ValidUtil.valid("第"+ (i+1) + "行手机号", cellphone, "{'regex':'"+RegexConstants.CELLPHONE+"'}");
                }
                if(StringUtils.isNotBlank(passport)){//不为空 正则校验
                    ValidUtil.valid("第"+ (i+1) + "行护照", passport, "{'regex':'"+RegexConstants.PASSPORT+"'}");
                }
                if(StringUtils.isNotBlank(identityCard)){//不为空 正则校验
                    ValidUtil.valid("第"+ (i+1) + "行身份证号", identityCard, "{'regex':'"+RegexConstants.IDENTITY_CARD+"'}");
                }
                boolean isSC = RegexUtil.match(userName, RegexConstants.SC_PATTERN, Pattern.CASE_INSENSITIVE);//判断是否是特通账号
                if(!isSC){
                    if(StringUtils.isBlank(cellphone) && StringUtils.isBlank(identityCard) && StringUtils.isBlank(passport)){
                        Object[] message = {(i+1),"手机号","身份证","护照"};
                        throw new ValidException("E2500002", MessageUtil.getMessage("E2500002", message));//第（{0}）行的{1}、{2}、{3}不允许同时为空!
                    }
                }
                realName = sheet.getCell(6,i).getContents().trim();//姓名
                if(StringUtils.isNotBlank(realName)){
                    ValidUtil.valid("第"+ (i+1) + "行姓名", realName, "{'regex':'"+RegexConstants.REAL_NAME+"'}");
                }
                deptName = sheet.getCell(7,i).getContents().trim();//部门
                if(StringUtils.isNotBlank(deptName)){
                    ValidUtil.valid("第"+ (i+1) + "行部门", deptName, "{'regex':'"+RegexConstants.DEPT_NAME+"'}");
                }
                userNameSet.add(userName);
                userNameMap.put(i, userName);
                if(userNameSet.size() != i){//用户名重复校验
                    for(Entry<Integer, String> maps : userNameMap.entrySet()){
                        if(maps.getValue().equals(userName)){
                            Object[] message = {(i+1),"用户名",(maps.getKey()+1)};
                            throw new ValidException("E2000034", MessageUtil.getMessage("E2000034", message));//第（{0}）行的{1}与第（{2}）行重复!
                        }
                    }
                }
                if(StringUtils.isBlank(cellphone)){
                    cellphoneSet.add(i+"");
                }else{
                    cellphoneSet.add(cellphone);
                }
                cellphoneMap.put(i, cellphone);
                if(cellphoneSet.size() != i){//手机号重复校验
                    for(Entry<Integer, String> maps : cellphoneMap.entrySet()){
                        if(maps.getValue().equals(cellphone)){
                            Object[] message = {(i+1),"手机号",(maps.getKey()+1)};
                            throw new ValidException("E2000034", MessageUtil.getMessage("E2000034", message));//第（{0}）行的{1}与第（{2}）行重复!
                        }
                    }
                }
                if(staticUserService.isUserNameExist(merchantId,userName)){//判断该商户下用户名是否已经存在
                    Object[] message={(i+1),"用户名",userName};
                    throw new ValidException("E2000045", MessageUtil.getMessage("E2000045", message));//第（{0}）行的{1}（{2}）已存在!
                }
                if(StringUtils.isNotBlank(cellphone)){//手机号不为空 商户下手机号唯一校验
                    if(staticUserService.isCellphoneExist(merchantId,cellphone)){
                        Object[] message={(i+1),"手机号",cellphone};
                        throw new ValidException("E2000045", MessageUtil.getMessage("E2000045", message));//第（{0}）行的{1}（{2}）已存在!
                    }
                }
                remark = sheet.getCell(8,i).getContents().trim();//备注
                staticUser = new StaticUser();
                staticUser.setUserType(userType);
                staticUser.setUserName(userName);
                staticUser.setPassword(EncryUtil.getMd5Str(password));
                staticUser.setCellphone(cellphone);
                staticUser.setIdentityCard(identityCard);
                staticUser.setPassport(passport);
                if(StringUtils.isNotBlank(cellphone)){
                    staticUser.setUserInfoType(1);
                }else if(StringUtils.isNotBlank(passport)){
                    staticUser.setUserInfoType(2);
                }else if(StringUtils.isNotBlank(identityCard)){
                    staticUser.setUserInfoType(3);
                }else{
                    staticUser.setUserInfoType(1);
                }
                staticUser.setRealName(realName);
                staticUser.setDeptName(deptName);
                staticUser.setRemark(remark);
                staticUser.setMerchantId(merchantId);
                staticUser.setCascadeLabel(cascadeLabel);
                successList.add(staticUser);//添加到成功列表
            }
            int maxSize = successList.size();
            for(int j=0 ; j<maxSize ; j++){//数据筛选后 统一保存到数据库
                staticUser = successList.get(j);
                staticUserService.add(staticUser);//添加用户
            }
        }
        return this.successMsg();
    }
}
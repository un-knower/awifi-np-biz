package com.awifi.np.biz.pub.security.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.admin.security.role.service.RoleService;
import com.awifi.np.admin.security.user.model.User;
import com.awifi.np.admin.security.user.service.UserService;
import com.awifi.np.biz.api.client.dbcenter.location.util.LocationClient;
import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.redis.util.RedisAdminUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.EncryUtil;
import com.awifi.np.biz.common.util.FormatUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.RegexUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.toe.admin.security.user.model.ToeUser;
import com.awifi.np.biz.toe.admin.security.user.service.ToeUserService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月23日 下午2:16:25
 * 创建作者：周颖
 * 文件名称：UserController.java
 * 版本：  v1.0
 * 功能：用户控制层
 * 修改记录：
 */
@Controller
@SuppressWarnings("unchecked" )
public class UserController extends BaseController {

    /**角色服务层*/
    @Resource(name= "userService")
    private UserService userService;
    
    /**角色服务层*/
    @Resource(name = "roleService")
    private RoleService roleService;
    
    /**toe用户服务*/
    @Resource(name = "toeUserService")
    private ToeUserService toeUserService;
    
    /**
     * 根据access_token获取用户信息
     * @param accessToken access_token
     * @return userInfo
     * @author 周颖  
     * @throws Exception 
     * @date 2017年1月12日 上午11:09:01
     */
    @RequestMapping(method=RequestMethod.GET, value="/pubsrv/user")
    @ResponseBody
    public Map<String,Object> getByAccessToken(@RequestParam(value="access_token",required=true)String accessToken) throws Exception{
        String valueString = RedisAdminUtil.get(accessToken);//从redis获取access_token的值
        if(StringUtils.isBlank(valueString)){
            throw new BizException("E0000001", MessageUtil.getMessage("E0000001"));//access_token失效
        }
        Map<String,Object> value = (Map<String, Object>) JsonUtil.fromJson(valueString, HashMap.class);//转map
        String belongTo = (String) value.get("belongTo");//用户标识NP/ToE
        Map<String,Object> userInfo = (Map<String, Object>) value.get("userInfo");//获取redis的用户信息
        String roleIds = (String) userInfo.get("roleIds");
        String roleNames = roleService.getNamesByIds(roleIds);//获取角色名称
        userInfo.put("roleNames", roleNames);
        String nameParam = "name";//地区名称
        Long areaId = CastUtil.toLong(userInfo.get("areaId"));
        String area = StringUtils.EMPTY;
        if(areaId != null){
            area =  LocationClient.getByIdAndParam(areaId, nameParam);
            userInfo.put("area", area);
        }
        Long cityId = CastUtil.toLong(userInfo.get("cityId"));
        String city = StringUtils.EMPTY;
        if(cityId != null){
            city = LocationClient.getByIdAndParam(cityId, nameParam);
            userInfo.put("city", city);
        }
        Long provinceId = CastUtil.toLong(userInfo.get("provinceId"));
        String province = StringUtils.EMPTY;
        if(provinceId != null){
            province = LocationClient.getByIdAndParam(provinceId, nameParam);
            userInfo.put("province", province);
        }
        userInfo.put("locationFullName", FormatUtil.locationFullName(province, city, area, "全国"));
        Long id = CastUtil.toLong(userInfo.get("id"));
        if(StringUtils.isBlank(belongTo)){//返回空
            
        }else if(belongTo.equals("NP")){//np用户
            User user = userService.getById(id);//补全用户信息
            if(user != null){
                userInfo.put("realname", user.getRealname());
                userInfo.put("nickname",user.getNickname());
                userInfo.put("email", user.getEmail());
                userInfo.put("contactPerson", user.getContactPerson());
                userInfo.put("contactWay", user.getContactWay());
                userInfo.put("remark", user.getRemark());
            }
        }else if(belongTo.equals("ToE")){//toe用户
            ToeUser toeUser = toeUserService.getById(id);
            if(toeUser != null){
                userInfo.put("realname", toeUser.getRealname());
                userInfo.put("nickname",toeUser.getNickname());
                userInfo.put("email", toeUser.getEmail());
                userInfo.put("contactPerson", toeUser.getContactPerson());
                userInfo.put("contactWay", toeUser.getContactWay());
                userInfo.put("remark", toeUser.getRemark());
            }
        }else{
            throw new BizException("E2010002", MessageUtil.getMessage("E2010002"));//用户归属无效！
        }
        return this.successMsg(userInfo);
    }
    
    /**
     * 通过access_token更新当前登录用户信息
     * @param accessToken access_token
     * @param bodyParam 参数
     * @return 结果
     * @author 周颖  
     * @date 2017年2月23日 下午7:02:28
     */
    @RequestMapping(method=RequestMethod.PUT, value="/pubsrv/user")
    @ResponseBody
    public Map<String,Object> updateByAccessToken(@RequestParam(value="access_token",required=true)String accessToken,@RequestBody(required=true) Map<String,Object> bodyParam){
        String email = (String) bodyParam.get("email");//如果邮箱不为空，校验正则
        if(StringUtils.isNotBlank(email)){
            if(!RegexUtil.match(email,RegexConstants.EMAIL_PATTERN)){
                throw new ValidException("E2000016", MessageUtil.getMessage("E2000016","邮箱[email]"));
            }
        }
        String valueString = RedisAdminUtil.get(accessToken);//从redis获取access_token的值
        if(StringUtils.isBlank(valueString)){
            throw new BizException("E0000001", MessageUtil.getMessage("E0000001"));//access_token失效
        }
        Map<String,Object> value = (Map<String, Object>) JsonUtil.fromJson(valueString, HashMap.class);//转map
        String belongTo = (String) value.get("belongTo");//用户标识NP/ToE
        Map<String,Object> userInfo = (Map<String, Object>) value.get("userInfo");//获取redis的用户信息
        Long userId = CastUtil.toLong(userInfo.get("id"));//用户id
        if(userId == null){
            throw new BizException("E2000012", MessageUtil.getMessage("E2000012"));//用户id不允许为空!
        }
        int count = 0;
        if(StringUtils.isBlank(belongTo)){
            throw new BizException("E2010002", MessageUtil.getMessage("E2010002"));//用户归属无效！
        }else if(belongTo.equals("NP")){//np用户
            User user = new User();
            user.setId(userId);
            user.setEmail(email);
            user.setRealname((String) bodyParam.get("realname"));
            user.setNickname((String) bodyParam.get("nickname"));
            user.setContactPerson((String) bodyParam.get("contactPerson"));
            user.setContactWay((String) bodyParam.get("contactWay"));
            user.setRemark((String) bodyParam.get("remark"));
            count = userService.updateById(user);//保存到np的user表
        }else if(belongTo.equals("ToE")){//toe用户
            ToeUser toeUser = new ToeUser();
            toeUser.setId(userId);
            toeUser.setEmail(email);
            toeUser.setRealname((String) bodyParam.get("realname"));
            toeUser.setNickname((String) bodyParam.get("nickname"));
            toeUser.setContactPerson((String) bodyParam.get("contactPerson"));
            toeUser.setContactWay((String) bodyParam.get("contactWay"));
            toeUser.setRemark((String) bodyParam.get("remark"));
            count = toeUserService.updateById(toeUser);//保存到toe的user表
        }else{
            throw new BizException("E2010002", MessageUtil.getMessage("E2010002"));//用户归属无效！
        }
        if(count <= 0){//如果更新条数小于等于0
            throw new BizException("E2000049", MessageUtil.getMessage("E2000049", userId));//通过用户id[{0}]未找到对应的用户信息!
        }
        return this.successMsg();
    }
    
    /**
     * 通过access_token更新当前登录用户密码接口
     * @param accessToken token
     * @param bodyParams 请求体参数
     * @return json
     * @author 许小满  
     * @date 2017年2月23日 下午3:06:26
     */
    @RequestMapping(method=RequestMethod.PUT, value="/pubsrv/user/password")
    @ResponseBody
    public Map<String,Object> updatePwdByAccessToken(
            @RequestParam(value="access_token", required=true)String accessToken,
            @RequestBody(required=true) Map<String,String> bodyParams
    ){
        String oldPassword = bodyParams.get("oldPassword");//旧密码
        String newPassword = bodyParams.get("newPassword");//新密码
        String confirmPassword = bodyParams.get("confirmPassword");//确认密码
        //参数校验
        ValidUtil.valid("旧密码[oldPassword]", oldPassword, "required");//旧密码，不允许为空
        ValidUtil.valid("新密码[newPassword]", newPassword, "required");//新密码，不允许为空
        ValidUtil.valid("确认密码[confirmPassword]", confirmPassword, "required");//确认密码，不允许为空
        if(oldPassword.equals(newPassword)){
            throw new ValidException("E2000050", MessageUtil.getMessage("E2000050"));//旧密码与新密码一致!
        }
        if(!newPassword.equals(confirmPassword)){
            throw new ValidException("E2000051", MessageUtil.getMessage("E2000051"));//新密码与确认密码不一致!
        }
        String userInfo = RedisAdminUtil.get(accessToken);//从redis获取access_token的值
        if(StringUtils.isBlank(userInfo)){
            throw new BizException("E0000001", MessageUtil.getMessage("E0000001"));//access_token失效
        }
        Map<String,Object> userInfoRedisMap = JsonUtil.fromJson(userInfo, HashMap.class);
        String belongTo = (String) userInfoRedisMap.get("belongTo");//用户标识NP/ToE
        Map<String,Object> userInfoMap = (Map<String,Object>)userInfoRedisMap.get("userInfo");
        Long id = CastUtil.toLong(userInfoMap.get("id"));
        ValidUtil.valid("用户id", id, "required");//用户id 必填校验
        String oldPasswordMd5 = EncryUtil.getMd5Str(oldPassword);//旧密码进行md5加密
        String dbPassword = StringUtils.EMPTY;//db中的原始密码
        if(StringUtils.isBlank(belongTo)){
            throw new BizException("E2010002", MessageUtil.getMessage("E2010002"));//用户归属无效！
        }else if(belongTo.equals("NP")){//np的用户
            User user = userService.getById(id);
            if(user == null){
                throw new BizException("E2000049", MessageUtil.getMessage("E2000049", id));//通过用户id[{0}]未找到对应的用户信息!
            }
            dbPassword = user.getPassword();
            if(!oldPasswordMd5.equals(dbPassword)){
                throw new BizException("E2000052", MessageUtil.getMessage("E2000052"));//原密码与页面输入旧密码不一致!
            }
            userService.updatePwdById(id, EncryUtil.getMd5Str(newPassword));
        }else if(belongTo.equals("ToE")){//toe的用户
            ToeUser toeUser = toeUserService.getById(id);
            if(toeUser == null){
                throw new BizException("E2000049", MessageUtil.getMessage("E2000049", id));//通过用户id[{0}]未找到对应的用户信息!
            }
            dbPassword = toeUser.getPassword();//db中的原始密码
            if(!oldPasswordMd5.equals(dbPassword)){
                throw new BizException("E2000052", MessageUtil.getMessage("E2000052"));//原密码与页面输入旧密码不一致!
            }
            toeUserService.updatePwdById(id, EncryUtil.getMd5Str(newPassword));
        }else{
            throw new BizException("E2010002", MessageUtil.getMessage("E2010002"));//用户归属无效！
        }
        return this.successMsg();
    }   
}
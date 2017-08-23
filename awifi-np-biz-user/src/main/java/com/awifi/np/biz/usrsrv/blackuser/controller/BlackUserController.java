package com.awifi.np.biz.usrsrv.blackuser.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.security.user.util.SessionUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.toe.admin.usrmgr.blackuser.model.BlackUser;
import com.awifi.np.biz.toe.admin.usrmgr.blackuser.service.BlackUserService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月13日 上午8:50:00
 * 创建作者：周颖
 * 文件名称：BlackUserController.java
 * 版本：  v1.0
 * 功能：黑名单控制层
 * 修改记录：
 */
@Controller
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BlackUserController extends BaseController {

    /**黑名单服务*/
    @Resource(name = "blackUserService")
    private BlackUserService blackUserService;
    
    /**
     * 黑名单列表
     * @param accessToken access_token
     * @param params 参数
     * @param request 请求
     * @return 列表
     * @author 周颖  
     * @throws Exception 
     * @date 2017年2月13日 上午9:17:29
     */
    @ResponseBody
    @RequestMapping(method=RequestMethod.GET, value="/usrsrv/blackusers")
    public Map getListByParam(@RequestParam(value="access_token",required=true)String accessToken,@RequestParam(value="params",required=true)String params,HttpServletRequest request) throws Exception{
        Map<String,Object> paramsMap = JsonUtil.fromJson(params, Map.class);
        Integer pageSize = CastUtil.toInteger(paramsMap.get("pageSize"));
        Integer maxPageSize = Integer.parseInt(SysConfigUtil.getParamValue("page_maxsize"));//每页记录最大数
        ValidUtil.valid("每页数量[pageSize]", pageSize, "{'required':true,'numeric':{'max':"+maxPageSize+"}}");//校验每页记录数 不能大于最大数
        Integer pageNo = CastUtil.toInteger(paramsMap.get("pageNo"));
        if(pageNo == null){//如果为空 默认第一页
            pageNo = 1;
        }
        Long merchantId = CastUtil.toLong(paramsMap.get("merchantId"));
        Integer matchRule = CastUtil.toInteger(paramsMap.get("matchRule"));
        String keywords = (String) paramsMap.get("keywords");//支持[用户名|手机号]模糊查询
        Page<BlackUser> page = new Page<BlackUser>(pageNo,pageSize);
        SessionUser sessionUser = SessionUtil.getCurSessionUser(request);
        blackUserService.getListByParam(sessionUser,page,merchantId,matchRule,keywords);
        return this.successMsg(page);
    }
    
    /**
     * 新增黑名单
     * @param accessToken access_token
     * @param bodyParam 参数
     * @return 结果
     * @author 周颖  
     * @date 2017年2月13日 下午1:53:21
     */
    @ResponseBody
    @RequestMapping(method=RequestMethod.POST, value="/usrsrv/blackuser")
    public Map add(@RequestParam(value="access_token",required=true)String accessToken,@RequestBody(required=true) Map<String,Object> bodyParam){
        Long merchantId = CastUtil.toLong(bodyParam.get("merchantId"));//商户id，数字，不允许为空
        String cascadeLabel = (String) bodyParam.get("cascadeLabel");//商户层级关系，不允许为空
        Integer matchRule = CastUtil.toInteger(bodyParam.get("matchRule"));//匹配规则：1代表精确、2代表模糊，数字，不允许为空
        String cellphone = (String) bodyParam.get("cellphone");//手机号或号段，不允许为空 当matchRule==1时，正则校验[1开头的11位符合手机号码规则的数字] 
                                                                                  //当matchRule==2时，正则校验[1-10位数字]
        String remark = (String) bodyParam.get("remark");//备注，允许为空
        ValidUtil.valid("商户id[merchantId]", merchantId, "{'required':true,'numeric':true}");
        ValidUtil.valid("商户层级关系[cascadeLabel]", cascadeLabel, "required");
        ValidUtil.valid("匹配规则[matchRule]", matchRule, "{'required':true,'numeric':true}");
        if(matchRule == 1){
            ValidUtil.valid("手机号[cellphone]",cellphone,"{'regex':'"+RegexConstants.CELLPHONE+"'}");
        }else if(matchRule == 2){
            ValidUtil.valid("手机号[cellphone]", cellphone, "{'regex':'^(1[0-9]{0,9})?$'}");//1-10位符合号段规则的数字
        }else{
            throw new ValidException("E2500003", MessageUtil.getMessage("E2500003", matchRule));
        }
        if(blackUserService.isCellphoneExist(merchantId,cellphone)){
            throw new BizException("E2500004", MessageUtil.getMessage("E2500004", cellphone));
        }
        BlackUser blackUser = new BlackUser();
        blackUser.setMerchantId(merchantId);
        blackUser.setCascadeLabel(cascadeLabel);
        blackUser.setMatchRule(matchRule);
        blackUser.setCellphone(cellphone);
        blackUser.setRemark(remark);
        blackUserService.add(blackUser);
        return this.successMsg();
    }
    
    /**
     * 逻辑删除黑名单
     * @param accessToken access_token
     * @param id 黑名单主键id
     * @return 结果
     * @author 周颖  
     * @date 2017年2月13日 下午2:01:34
     */
    @ResponseBody
    @RequestMapping(method=RequestMethod.DELETE, value="/usrsrv/blackuser/{id}")
    public Map delete(@RequestParam(value="access_token",required=true)String accessToken,@PathVariable Long id){
        blackUserService.delete(id);
        return this.successMsg();
    }
}
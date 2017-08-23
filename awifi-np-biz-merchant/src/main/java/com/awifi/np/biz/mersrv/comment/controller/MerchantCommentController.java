/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月15日 上午10:00:45
* 创建作者：方志伟
* 文件名称：MerchantCommentController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mersrv.comment.controller;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.DateUtil;
import com.awifi.np.biz.common.util.IPUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.mws.comment.model.MerchantComment;
import com.awifi.np.biz.mws.comment.service.MerchantCommentPicService;
import com.awifi.np.biz.mws.comment.service.MerchantCommentReService;
import com.awifi.np.biz.mws.comment.service.MerchantCommentService;


@Controller
@RequestMapping("/mersrv")
@SuppressWarnings({"rawtypes"})
public class MerchantCommentController extends BaseController{
	
    /**
	 * 添加评论服务层 
	 */
    @Resource(name = "merchantCommentService")
	private MerchantCommentService merchantCommentService;
    
    /**商户评论-回复接口服务*/
    @Resource(name = "merchantCommentReService")
    private MerchantCommentReService merchantCommentReService;
	
    /**
     * 添加上传图片服务层
     */
    @Resource(name = "merchantCommentPicService")
    private MerchantCommentPicService merchantCommentPicService;
    
    /**
     * 商户评论接口
     * @param merchantId 商户id
     * @param bodyParam 参数
     * @param request 请求
     * @return 结果
     * @throws Exception 
     * @author 方志伟  
     * @date 2017年6月16日 上午8:38:56
     */
    @RequestMapping(method=RequestMethod.POST,value="/merchant/{merchantid}/comment", produces="application/json")
	@ResponseBody
	public Map add(@PathVariable(value="merchantid",required=true) Long merchantId,
				   @RequestBody(required=true) Map<String, Object> bodyParam,HttpServletRequest request) throws Exception{
        String userPhone = (String) bodyParam.get("userPhone");//手机号，允许为空
        String content = (String) bodyParam.get("content");//评论内容，不允许为空
        ArrayList commentPicUrl = (ArrayList) bodyParam.get("commentPicUrl");//评论图片url，允许为空
        ValidUtil.valid("评论内容[content]", content, "required");//校验评论内容是否为空
        String ip = IPUtil.getIpAddr(request);//获取ip地址
        Long commentId = merchantCommentService.add(merchantId, userPhone, content, ip);
        merchantCommentPicService.add(commentPicUrl,commentId);
        return this.successMsg();
    }
    
    /** 商户评论-回复接口
     * @param merchantId 商户id
     * @param bodyParam  参数
     * @param request  请求
     * @return 结果
     * @throws Exception 异常
     * @author 许尚敏 
     * @date 2017年6月15日 上午10:20:40
     */
    @RequestMapping(method=RequestMethod.POST,value="/merchant/{merchantid}/comment/reply", produces="application/json")
    @ResponseBody
    public Map addReply(@PathVariable(value="merchantid",required=true) Long merchantId,
            @RequestBody(required=true) Map<String, Object> bodyParam, 
            HttpServletRequest request) throws Exception{
        Long commentId = CastUtil.toLong(bodyParam.get("commentId"));//评论表主键id，不允许为空
        Long commentUserId = CastUtil.toLong(bodyParam.get("commentUserId"));//评论用户id，不允许为空
        String commentUserName = (String) bodyParam.get("commentUserName");//评论用户名，不允许为空
        String userPhone = (String) bodyParam.get("userPhone");//手机号，允许为空
        Long replyToUserId = CastUtil.toLong(bodyParam.get("replyToUserId"));//回复给的人用户id，允许为空
        String replyToUserName = (String) bodyParam.get("replyToUserName");//回复给的人用户名，不允许为空
        String content = (String) bodyParam.get("content");//评论内容，不允许为空
        ValidUtil.valid("评论表主键id[commentId]", commentId, "{'required':true,'numeric':true}");//校验评论表主键id
        ValidUtil.valid("评论用户id[commentUserId]", commentUserId, "{'required':true,'numeric':true}");//校验评论用户id
        ValidUtil.valid("评论用户名[commentUserName]", commentUserName, "required");//校验评论用户名
        ValidUtil.valid("回复给的人用户名[replyToUserName]", replyToUserName, "required");//校验回复给的人用户名
        ValidUtil.valid("评论内容[content]", content, "required");//校验评论内容
        Long replyUserId = 0L;//回复人用户id 默认id 0
        String ipAddress = IPUtil.getIpAddr(request);//回复人用户名  默认游客+公网IP
        String createDate = DateUtil.getNow();//回复日期
        Map<String, Object> replyParam = new HashMap<String, Object>();
        replyParam.put("merchantId", merchantId);
        replyParam.put("commentId", commentId);
        replyParam.put("commentUserId", commentUserId);
        replyParam.put("commentUserName", commentUserName);
        replyParam.put("replyUserId", replyUserId);
        replyParam.put("ipAddress", ipAddress);
        replyParam.put("userPhone", userPhone);
        replyParam.put("content", content);
        replyParam.put("createDate", createDate);
        replyParam.put("replyToUserId", replyToUserId);
        replyParam.put("replyToUserName", replyToUserName);
        merchantCommentReService.addReply(replyParam);//添加商户评论回复
        return this.successMsg();
    }
    
    /** 商户评论分页查询接口
	 * @param merchantId 商户id
	 * @param pageSize  页面大小
	 * @param pageNo  页码
	 * @return 结果
	 * @throws Exception 异常
	 * @author 季振宇 
	 * @date 2017年6月15日 上午10:22:15
	 */
    @RequestMapping(method=RequestMethod.GET, value="/merchant/{merchantid}/comments")
	@ResponseBody
    public Map getListByParam(
            @PathVariable(value="merchantid",required=true) Long merchantId,
            @RequestParam(required=true) Integer pageSize,
            @RequestParam Integer pageNo) throws Exception{
        Integer maxPageSize = Integer.parseInt(SysConfigUtil.getParamValue("page_maxsize"));//每页记录最大数
        ValidUtil.valid("每页数量[pageSize]", pageSize, "{'required':true,'numeric':{'max':"+maxPageSize+"}}");//校验每页记录最大数
        if(pageNo == null){//如果为空，默认第一页
            pageNo = 1;
        }
        Page<MerchantComment> page = new Page<MerchantComment>(pageNo, pageSize);
        merchantCommentService.getListByParam(page, merchantId); 
        return this.successMsg(page);//返回查询结果
    } 
}

package com.awifi.np.biz.pagesrv.portal.site.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.util.ErrorUtil;
import com.awifi.np.biz.common.util.ValidUtil;

/**   
 * @Description:  低浏览器跳转页面
 * @Title: LowBrowserAction.java 
 * @Package com.awifi.toe.inerface.server.api.portal.action 
 * @author 许小满 
 * @date 2016年8月3日 下午2:12:02
 * @version V1.0   
 */
@Controller
public class LowBrowserController extends BaseController {

    /**
     * 站点首页
     * @param request 请求
     * @param response response
     * @author 许小满
     * @date 2015年12月26日 下午4:32:44
     */
    @RequestMapping(value = "/ie8")
    @ResponseBody
    public void ie8(HttpServletRequest request, HttpServletResponse response) {
        long beginTime = System.currentTimeMillis();
        try {
            String globalKey = StringUtils.defaultString(request.getParameter("global_key"));//全局日志key
            String devId = StringUtils.defaultString(request.getParameter("dev_id"));//设备id
            String userMac = StringUtils.defaultString(request.getParameter("user_mac"));//用户mac地址
            String loginType = StringUtils.defaultString(request.getParameter("login_type"));//登录类型: authed 代表已认证过，控制页面显示“免费上网”；unauth 代表未认证过，页面显示 “手机号认证”或“用户名认证”;welcome 代表认证已放行（如果是胖AP需要用token调用设备总线接口），可以上外网
            
            String customerId = StringUtils.defaultString(request.getParameter("customer_id"));// 客户id
            
            // 请求参数 校验
            ValidUtil.valid("全局日志key[global_key]", globalKey, "required");//全局日志key
            ValidUtil.valid("设备id[dev_id]", devId, "required");//设备id
            ValidUtil.valid("用户MAC[user_mac]", userMac, "{'required':true, 'regex':'"+RegexConstants.MAC_PATTERN+"'}");//用户mac地址
            ValidUtil.valid("登录类型[login_type]", loginType, "required");//登录类型: authed 代表已认证过，控制页面显示“免费上网”；unauth 代表未认证过，页面显示 “手机号认证”或“用户名认证”;
            ValidUtil.valid("商户id[customer_id]", customerId, "required");//商户id
            
            String pageType = StringUtils.defaultString(request.getParameter("pageType"), "2");// 站点页面类型:1 引导页、2 认证页、3 过渡页、4 导航页
            String num = StringUtils.defaultString(request.getParameter("num"), "1");// 页面序号
            //当num为空时，默认为认证页面
            String htmlPath = null;
            boolean isAuthPage = pageType.equals("2") && num.equals("1");
            if(isAuthPage){
                htmlPath = "/html/ie8/auth.html";
            }
            //当num不为空时，跳转导航页
            else{
                htmlPath = "/html/ie8/navigation.html";
            }
            
            this.responseHtml(request, response, htmlPath);
        }catch (Exception e) {
            ErrorUtil.printException(e, logger);
            //saveExceptionLog(interfaceCode, "strategy.site", SiteAction.class.toString(), e);
        }finally{
            logger.debug("----------------------------  提示：打开站点页面共花费了 " + (System.currentTimeMillis() - beginTime) + " ms. ------------------------------");
        }
    }

    /** 
     * 回写站点html
     * @param request 请求
     * @param response response
     * @param htmlPath html页面路径
     * @throws Exception 异常
     * @author 许小满  
     * @date 2015年12月28日 下午12:43:04
     */
    private void responseHtml(HttpServletRequest request, HttpServletResponse response, String htmlPath) throws Exception{
        response.reset();
        response.setCharacterEncoding("UTF-8");//设置编码
        PrintWriter pw = null;
        BufferedReader br = null;
        try {
            String globalKey = StringUtils.defaultString(request.getParameter("global_key"));//全局日志key
            String globalValue = StringUtils.defaultString(request.getParameter("global_value"));//全局日志value
            //设备信息
            String devId = StringUtils.defaultString(request.getParameter("dev_id"));//设备id
            String devMac = StringUtils.defaultString(request.getParameter("dev_mac"));//设备mac
            String ssid = StringUtils.defaultString(request.getParameter("ssid"));//ssid
            String gwAddress = StringUtils.defaultString(request.getParameter("gw_address"));//胖AP类设备网关
            String gwPort = StringUtils.defaultString(request.getParameter("gw_port"));//胖AP类设备端口
            String nasName = StringUtils.defaultString(request.getParameter("nas_name"));//nas设备名称，NAS认证必填
            String cvlan = StringUtils.defaultString(request.getParameter("cvlan"));//cvlan
            String pvlan = StringUtils.defaultString(request.getParameter("pvlan"));//pvlan
            //商户信息
            String customerId = StringUtils.defaultString(request.getParameter("customer_id"));// 客户id
            String cascadeLabel = StringUtils.defaultString(request.getParameter("cascade_label"));// 客户层级
            //用户信息
            String userIp = StringUtils.defaultString(request.getParameter("user_ip"));//用户ip
            String userMac = StringUtils.defaultString(request.getParameter("user_mac"));//用户mac地址
            String userPhone = StringUtils.defaultString(request.getParameter("user_phone"));//用户手机号
            String loginType = StringUtils.defaultString(request.getParameter("login_type"));//登录类型: authed 代表已认证过，控制页面显示“免费上网”；unauth 代表未认证过，页面显示 “手机号认证”或“用户名认证”;welcome 代表认证已放行（如果是胖AP需要用token调用设备总线接口），可以上外网
            //站点信息
            String pageType = StringUtils.defaultString(request.getParameter("pageType"), "2");// 站点页面类型:1 引导页、2 认证页、3 过渡页、4 导航页
            String num = StringUtils.defaultString(request.getParameter("num"), "1");// 页面序号
            
            String url = StringUtils.defaultString(request.getParameter("url"));//用户浏览器输入的被拦截前的url原始地址
            String platform = StringUtils.defaultString(request.getParameter("platform"));//省分平台-前缀
            String token = StringUtils.defaultString(request.getParameter("token"));//用户token
            
            logger.debug("提示：htmlPath = " + htmlPath);
            pw = response.getWriter();
            br = new BufferedReader(new FileReader(request.getServletContext().getRealPath(htmlPath)));
            String html = null;
            while ((html = br.readLine()) != null) {// 判断最后一行不存在，为空结束循环
                /* 参数替换 */
                html = this.dealParam(html, "{@globalKey@}", globalKey);//全局日志key
                html = this.dealParam(html, "{@globalValue@}", globalValue);//全局日志value
                //设备信息
                html = this.dealParam(html, "{@devId@}", devId);//设备id
                html = this.dealParam(html, "{@devMac@}", devMac);//设备MAC
                html = this.dealParam(html, "{@ssid@}", ssid);//ssid
                html = this.dealParam(html, "{@gwAddress@}", gwAddress);//胖AP类设备网关
                html = this.dealParam(html, "{@gwPort@}", gwPort);//胖AP类设备端口
                html = this.dealParam(html, "{@nasName@}", nasName);//nas设备名称，NAS认证必填
                html = this.dealParam(html, "{@cvlan@}", cvlan);//cvlan
                html = this.dealParam(html, "{@pvlan@}", pvlan);//pvlan
                //商户信息
                html = this.dealParam(html, "{@customerId@}", customerId);//客户id
                html = this.dealParam(html, "{@cascadeLabel@}", cascadeLabel);//客户层级
                //用户信息
                html = this.dealParam(html, "{@userIp@}", userIp);//用户ip
                html = this.dealParam(html, "{@userMac@}", userMac);//用户mac地址
                html = this.dealParam(html, "{@userPhone@}", userPhone);//用户手机号
                html = this.dealParam(html, "{@loginType@}", loginType);//登录类型: authed 代表已认证过，控制页面显示“免费上网”；unauth 代表未认证过，页面显示 “手机号认证”或“用户名认证”
                //站点信息
                html = this.dealParam(html, "{@pageType@}", pageType);//点页面类型:1 引导页、2 认证页、3 过渡页、4 导航页
                html = this.dealParam(html, "{@num@}", num);//页面序号
                
                html = this.dealParam(html, "{@url@}", url);//用户浏览器输入的被拦截前的url原始地址
                html = this.dealParam(html, "{@platform@}", platform);//省分平台-前缀
                
                html = this.dealParam(html, "{@token@}", token);//用户token
                //logger.debug("提示：html= " + html);// 原样输出读到的内容
                pw.write(html);
            }
            pw.flush();
        } catch (Exception e) {
            ErrorUtil.printException(e, logger);
            throw e;
        } finally {
            try{
                if(br != null){
                    br.close();
                }
                if(pw != null){
                    pw.close();
                }
            }catch(Exception e1){}
        }
    }
    
    /**
     * 处理参数
     * @param html 回写html
     * @param key key 
     * @param value value
     * @return html
     * @author 许小满  
     * @date 2016年1月5日 上午11:04:29
     */
    private String dealParam(String html, String key, String value){
        try{
            if(html.indexOf(key) != -1){
                html = html.replaceAll("\\"+key, value);
            }
        }catch(Exception e){
            /* 因url含有复杂参数导致系统异常，进行特殊处理 */
            if("{@url@}".equals(key)){
                logger.debug("错误：参数替换错误前的url= " + value);
                String[] valueArray = value.split("?");
                int maxLenth = valueArray != null ? valueArray.length : 0;
                if(maxLenth > 0){
                    html = html.replaceAll("\\"+key, valueArray[0]);
                }else{
                    html = html.replaceAll("\\"+key, StringUtils.EMPTY);
                }
            }else{
                throw e;
            }
        }
        return html;
    }
    
    /**
     * 站点首页--时长购买
     * @param request 请求
     * @param response response
     * @author 许小满
     * @date 2015年12月26日 下午4:32:44
     */
    @RequestMapping(value = "/ie8/timebuy")
    @ResponseBody
    public void ie8ForTimeBuy(HttpServletRequest request, HttpServletResponse response) {
        long beginTime = System.currentTimeMillis();
        try {
            String globalKey = StringUtils.defaultString(request.getParameter("global_key"));//全局日志key
            String devId = StringUtils.defaultString(request.getParameter("dev_id"));//设备id
            String userMac = StringUtils.defaultString(request.getParameter("user_mac"));//用户mac地址
            String loginType = StringUtils.defaultString(request.getParameter("login_type"));//登录类型: authed 代表已认证过，控制页面显示“免费上网”；unauth 代表未认证过，页面显示 “手机号认证”或“用户名认证”;welcome 代表认证已放行（如果是胖AP需要用token调用设备总线接口），可以上外网
            
            String customerId = StringUtils.defaultString(request.getParameter("customer_id"));// 客户id
            
            // 请求参数 校验
            ValidUtil.valid("全局日志key[global_key]", globalKey, "required");//全局日志key
            ValidUtil.valid("设备id[dev_id]", devId, "required");//设备id
            ValidUtil.valid("用户MAC[user_mac]", userMac, "{'required':true, 'regex':'"+RegexConstants.MAC_PATTERN+"'}");//用户mac地址
            ValidUtil.valid("登录类型[login_type]", loginType, "required");//登录类型: authed 代表已认证过，控制页面显示“免费上网”；unauth 代表未认证过，页面显示 “手机号认证”或“用户名认证”;
            ValidUtil.valid("商户id[customer_id]", customerId, "required");//商户id
            
            String htmlPath = "/html/ie8/timebuy.html";
            this.responseHtml(request, response, htmlPath);
        }catch (Exception e) {
            ErrorUtil.printException(e, logger);
            //saveExceptionLog(interfaceCode, "strategy.site", SiteAction.class.toString(), e);
        }finally{
            logger.debug("----------------------------  提示：打开站点页面共花费了 " + (System.currentTimeMillis() - beginTime) + " ms. ------------------------------");
        }
    }
    
    
}

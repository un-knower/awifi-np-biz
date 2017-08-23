/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月11日 下午6:39:34
* 创建作者：许小满
* 文件名称：SiteController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.portal.site.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.PortalException;
import com.awifi.np.biz.common.system.log.util.ExceptionLogUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.ErrorUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.pagesrv.base.util.PageSessionUtil;
import com.awifi.np.biz.toe.portal.site.model.SitePage;
import com.awifi.np.biz.toe.portal.site.service.SiteService;

@Controller
public class SiteController extends BaseController {

    /** 站点Service */
    @Resource(name = "siteService")
    private SiteService siteService;

    /**
     * 站点首页
     * @param request 请求
     * @param response response
     * @author 许小满
     * @date 2015年12月26日 下午4:32:44
     */
    @RequestMapping(value = "/site")
    @ResponseBody
    public void site(HttpServletRequest request, HttpServletResponse response) {
        long beginTime = System.currentTimeMillis();
        try {
            String globalKey = StringUtils.defaultString(request.getParameter("global_key"));//全局日志key
            String devId = StringUtils.defaultString(request.getParameter("dev_id"));//设备id
            String userMac = StringUtils.defaultString(request.getParameter("user_mac"));//用户mac地址
            String loginType = StringUtils.defaultString(request.getParameter("login_type"));//登录类型: authed 代表已认证过，控制页面显示“免费上网”；unauth 代表未认证过，页面显示 “手机号认证”或“用户名认证”;welcome 代表认证已放行（如果是胖AP需要用token调用设备总线接口），可以上外网
            
            String customerId = StringUtils.defaultString(request.getParameter("customer_id"));// 客户id
            String siteId = StringUtils.defaultString(request.getParameter("site_id"));// 站点id
            // 请求参数 校验
            ValidUtil.valid("全局日志key[global_key]", globalKey, "required");//全局日志key
            ValidUtil.valid("商户id[customer_id]", customerId, "required");//商户id
            ValidUtil.valid("设备id[dev_id]", devId, "required");//设备id
            ValidUtil.valid("用户MAC[user_mac]", userMac, "{'required':true, 'regex':'"+RegexConstants.MAC_PATTERN+"'}");//用户mac地址
            ValidUtil.valid("登录类型[login_type]", loginType, "required");//登录类型: authed 代表已认证过，控制页面显示“免费上网”；unauth 代表未认证过，页面显示 “手机号认证”或“用户名认证”;
            ValidUtil.valid("站点id[site_id]", siteId, "required");//商户id
            
            Long siteIdLong = Long.parseLong(siteId);
            String siteName = request.getParameter("site_name");//站点名称
            if(StringUtils.isBlank(siteName)){
                siteName = siteService.getSiteNameCache(siteIdLong);
            }
            String pageType = request.getParameter("page_type");//站点页面类型:1 引导页、2 认证页、3 过渡页、4 导航页
            String num = request.getParameter("num");//站点页面序号
            logger.debug("提示：站点页面类型:1 引导页、2 认证页、3 过渡页、4 导航页[pageType]= " + pageType);
            logger.debug("提示：站点页面序号[num]= " + num);
            SitePage sitePage = this.getSitePage(request, siteIdLong, pageType, num);
            if (sitePage == null) {
                logger.debug("提示：通过站点id[" + siteId + "] 未找到对应页面信息！");
                throw new PortalException("通过站点id[" + siteId + "] 未找到对应页面信息！"); 
            }
            this.responseHtml(request, response, sitePage, siteName);
        }catch (Exception e) {
            ErrorUtil.printException(e, logger);
            ExceptionLogUtil.add(request, e);//保存异常日志
        }finally{
            logger.debug("----------------------------  提示：打开站点页面共花费了 " + (System.currentTimeMillis() - beginTime) + " ms. ------------------------------");
        }
    }

    /** 
     * 回写站点html
     * @param request 请求
     * @param response response
     * @param sitePage 站点页面
     * @param siteName 站点名称
     * @throws Exception 异常
     * @author 许小满  
     * @date 2015年12月28日 下午12:43:04
     */
    private void responseHtml(HttpServletRequest request, HttpServletResponse response, SitePage sitePage, String siteName) throws Exception{
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
            String longitude = StringUtils.defaultString(request.getParameter("longitude"));//经度
            String latitude = StringUtils.defaultString(request.getParameter("latitude"));//维度
            //商户信息
            String customerId = StringUtils.defaultString(request.getParameter("customer_id"));// 客户id
            String cascadeLabel = StringUtils.defaultString(request.getParameter("cascade_label"));// 客户层级
            String customerName = StringUtils.defaultString(request.getParameter("customer_name"));// 客户名称
            //用户信息
            String userIp = StringUtils.defaultString(request.getParameter("user_ip"));//用户ip
            String userMac = StringUtils.defaultString(request.getParameter("user_mac"));//用户mac地址
            String userPhone = StringUtils.defaultString(request.getParameter("user_phone"));//用户手机号
            String loginType = StringUtils.defaultString(request.getParameter("login_type"));//登录类型: authed 代表已认证过，控制页面显示“免费上网”；unauth 代表未认证过，页面显示 “手机号认证”或“用户名认证”;welcome 代表认证已放行（如果是胖AP需要用token调用设备总线接口），可以上外网
            Long userIdLong = PageSessionUtil.getUserId(request);// 用户id
            String userId = userIdLong != null ? userIdLong.toString() : StringUtils.EMPTY;// 用户id
            logger.debug("提示：userId=" + userId);
            //站点信息
            String siteId = StringUtils.defaultString(request.getParameter("site_id"));// 站点id
            
            String url = StringUtils.defaultString(request.getParameter("url"));//用户浏览器输入的被拦截前的url原始地址
            String platform = StringUtils.defaultString(request.getParameter("platform"));//省分平台-前缀
            String portalVersion = StringUtils.defaultString(request.getParameter("portal_version"));// portal页面版本号：5代表5.x
            String netDefSwitch = StringUtils.defaultString(request.getParameter("net_def_switch"));// 防蹭网开关
            
            String token = StringUtils.defaultString(request.getParameter("token"));//用户token
            
            String pageHtmlPath = sitePage.getPagePath();//站点页面保存路径
            String siteHtmlFullPath = SysConfigUtil.getParamValue("resources_folder_path") + File.separator + pageHtmlPath + File.separator + "site.html";//site.html完整路径
            Long sitePageId = sitePage.getPageId();//站点页面id
            
            String pageType = sitePage.getPageType() != null ? sitePage.getPageType().toString() : StringUtils.EMPTY;//页面类型
            String num = sitePage.getNum() != null ? sitePage.getNum().toString() : StringUtils.EMPTY;//页面序号
            
            pw = response.getWriter();
            br = new BufferedReader(new FileReader(siteHtmlFullPath));
            String html = null;
            String version = StringUtils.defaultString(SysConfigUtil.getParamValue("pagesrv_resources_version"));//静态资源版本，目的屏蔽缓存，示例：v1.0.0
            String libPathPrefix = StringUtils.defaultString(SysConfigUtil.getParamValue("pagesrv_resource_lib_path_prefix"));//静态资源lib路径前缀，示例：/media/site/lib
            String cssLibPath = "/css/portal_lib.min.css";
            String cssLibFullPath = libPathPrefix + cssLibPath + "?v=" + version;//示例：/media/site/lib/css/portal_lib.min.css?_v=v1.0.0
            String cssPath = "css/portal.min.css";
            String jsLibPath  = "/js/portal_lib.min.js";
            String jsLibFullPath  = libPathPrefix + jsLibPath + "?v=" + version;//示例：/media/site/lib/js/portal_lib.min.js?_v=v1.0.0
            String jsPath  = "script/portal.min.js";
            while ((html = br.readLine()) != null) {// 判断最后一行不存在，为空结束循环
                /* 替换 css */
                html = this.replace(html, cssLibPath, cssLibFullPath);//替换 lib css
                html = this.replace(html, cssPath, pageHtmlPath + "/" + cssPath + "?v=" + version);//替换 css
                
                /* 替换 js */
                html = this.replace(html, jsLibPath, jsLibFullPath);//替换 lib js
                html = this.replace(html, jsPath, pageHtmlPath + "/" + jsPath + "?v=" + version);//替换 js
                
                /* 参数替换 */
                html = this.dealParam(html, "{@globalKey@}", globalKey);//全局日志key
                html = this.dealParam(html, "{@globalValue@}", globalValue);//全局日志value
                /* 设备信息 */
                html = this.dealParam(html, "{@devId@}", devId);//设备id
                html = this.dealParam(html, "{@devMac@}", devMac);//设备MAC
                html = this.dealParam(html, "{@ssid@}", ssid);//ssid
                html = this.dealParam(html, "{@gwAddress@}", gwAddress);//胖AP类设备网关
                html = this.dealParam(html, "{@gwPort@}", gwPort);//胖AP类设备端口
                html = this.dealParam(html, "{@nasName@}", nasName);//nas设备名称，NAS认证必填
                html = this.dealParam(html, "{@cvlan@}", cvlan);//cvlan
                html = this.dealParam(html, "{@pvlan@}", pvlan);//pvlan
                html = this.dealParam(html, "{@longitude@}", longitude);//经度
                html = this.dealParam(html, "{@latitude@}", latitude);//维度
                //商户信息
                html = this.dealParam(html, "{@customerId@}", customerId);//客户id
                html = this.dealParam(html, "{@cascadeLabel@}", cascadeLabel);//客户层级
                html = this.dealParam(html, "{@customerName@}", customerName);//客户名称
                //用户信息
                html = this.dealParam(html, "{@userIp@}", userIp);//用户ip
                html = this.dealParam(html, "{@userMac@}", userMac);//用户mac地址
                html = this.dealParam(html, "{@userPhone@}", userPhone);//用户手机号
                html = this.dealParam(html, "{@loginType@}", loginType);//登录类型: authed 代表已认证过，控制页面显示“免费上网”；unauth 代表未认证过，页面显示 “手机号认证”或“用户名认证”
                html = this.dealParam(html, "{@userId@}", userId);//用户id
                //站点信息
                html = this.dealParam(html, "{@siteId@}", siteId);//站点id
                html = this.dealParam(html, "{@sitePageId@}", sitePageId.toString());//站点页面id
                html = this.dealParam(html, "{@siteTitle@}", siteName);//站点名称
                html = this.dealParam(html, "{@siteName@}", siteName);//站点名称
                html = this.dealParam(html, "{@pageType@}", pageType);//页面类型
                html = this.dealParam(html, "{@num@}", num);//页面序号
                
                html = this.dealParam(html, "{@url@}", URLEncoder.encode(url, "UTF-8"));//用户浏览器输入的被拦截前的url原始地址
                html = this.dealParam(html, "{@platform@}", platform);//省分平台-前缀
                html = this.dealParam(html, "{@portalVersion@}", portalVersion);//portal页面版本号：5代表5.x
                html = this.dealParam(html, "{@netDefSwitch@}", netDefSwitch);//防蹭网开关
                
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
     * 替换html内容
     * @param html html
     * @param from 源字符串
     * @param to 替换后的字符串
     * @return html
     * @author 许小满  
     * @date 2017年6月28日 下午7:10:39
     */
    private String replace(String html, String from, String to) {
        if(html.indexOf(from) != -1){
            html = html.replaceAll(from, to);
        }
        return html;
    }
    
    /**
     * 处理参数
     * @param html 回写html
     * @param key key 
     * @param value value
     * @throws Exception 异常
     * @return html
     * @author 许小满  
     * @date 2016年1月5日 上午11:04:29
     */
    private String dealParam(String html, String key, String value) throws Exception{
        try{
            if(html.indexOf(key) != -1){
                html = html.replaceAll("\\"+key, value);
            }
        }catch(Exception e){
            String message = "参数替换错误：key="+ key +"&value=" + value;
            throw new PortalException(message, e);
        }
        return html;
    }
    
    /**
     * 获取站点页面
     * @param request 请求
     * @param siteId 站点id
     * @param pageType 站点页面类型:1 引导页、2 认证页、3 过渡页、4 导航页
     * @param num 站点页面序号
     * @return 站点页面
     * @author 许小满  
     * @date 2016年1月26日 下午6:05:02
     */
    private SitePage getSitePage(HttpServletRequest request, Long siteId, String pageType, String num){
        /* 旧版  */
        //1.页面类型和序号都不为空时，拉取站点下一页
        if(StringUtils.isNotBlank(pageType) && StringUtils.isNotBlank(num)){
            return siteService.getNextPageCache(siteId, Integer.parseInt(pageType), Integer.parseInt(num));
        }
        //2.页面类型或序号为空时，拉取站点首页
        return siteService.getFirstSitePageCache(siteId);
    }
    
}

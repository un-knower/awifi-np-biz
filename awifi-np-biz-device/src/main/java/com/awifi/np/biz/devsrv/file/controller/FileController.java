package com.awifi.np.biz.devsrv.file.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.util.IOUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月13日 上午8:55:09
 * 创建作者：亢燕翔
 * 文件名称：FileController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Controller
@RequestMapping("/devsrv")
public class FileController extends BaseController{

    /**
     * 文件下载
     * @param request 请求
     * @param response 响应
     * @param access_token 安全令牌
     * @param filename 文件名称
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月4日 上午8:48:54
     */
    @RequestMapping(value="/file/{filename:.*}", method=RequestMethod.GET)
    public void template(HttpServletRequest request, HttpServletResponse response, @RequestParam(value="access_token",required=true) String access_token,
            @PathVariable(value="filename",required=true) String filename) throws Exception{
        logger.debug("提示：filename=" + filename);
        String path = request.getServletContext().getRealPath("file/template/");
        File file = new File(path + File.separator + filename);
        if(!file.exists()){
            throw new BizException("E2000029", MessageUtil.getMessage("E2000029",filename));//filename[{0}]不存在!
        }
        IOUtil.download(filename, path, response);
    }
    
}

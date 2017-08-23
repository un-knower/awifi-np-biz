package com.awifi.np.biz.usrsrv.file.controller;

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
 * 创建日期:2017年2月9日 下午5:02:55
 * 创建作者：周颖
 * 文件名称：FileController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Controller
public class FileController extends BaseController {

    /**
     * 模板下载
     * @param accessToken access_token
     * @param fileName 文件名称
     * @param request 请求
     * @param response  响应
     * @author 周颖  
     * @date 2017年2月9日 下午5:07:15
     */
    @RequestMapping(method=RequestMethod.GET, value="/usrsrv/file/{filename:.*}")
    public void template(@RequestParam(value="access_token",required=true)String accessToken,@PathVariable(value="filename")String fileName,
            HttpServletRequest request,HttpServletResponse response){
        String path = request.getServletContext().getRealPath("file/template/");
        File file = new File(path + File.separator + fileName);
        if(!file.exists()){
            throw new BizException("E2000029", MessageUtil.getMessage("E2000029",fileName));//文件名称不存在
        }
        IOUtil.download(fileName, path, response);  
    }
    
    /**
     * 白名单模板下载
     * @param accessToken access_token
     * @param fileName 文件名称
     * @param request 请求
     * @param response  响应
     * @author 王冬冬  
     * @date 2017年2月9日 下午5:07:15
     */
    @RequestMapping(method=RequestMethod.GET, value="/usrsrv/whiteuser/file/{filename:.*}")
    public void whiteUserTemplate(@RequestParam(value="access_token",required=true)String accessToken,@PathVariable(value="filename")String fileName,
            HttpServletRequest request,HttpServletResponse response){
        String path = request.getServletContext().getRealPath("file/template/");
        File file = new File(path + File.separator + fileName);
        if(!file.exists()){
            throw new BizException("E2000029", MessageUtil.getMessage("E2000029",fileName));//文件名称不存在
        }
        IOUtil.download(fileName, path, response);  
    }
}
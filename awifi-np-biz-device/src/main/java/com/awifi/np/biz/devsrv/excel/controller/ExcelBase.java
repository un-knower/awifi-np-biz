/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月16日 下午2:37:30
* 创建作者：范涌涛
* 文件名称：ExcelBase.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.excel.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Iterator;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.excel.model.EmsSysExcel;
import com.awifi.np.biz.common.excel.model.ExcelType;
import com.awifi.np.biz.common.security.user.model.SessionUser;

public class ExcelBase extends BaseController{
    /**
     * 从 request 获取 文件
     * @param request 请求
     * @param response 响应
     * @param dir 文件路径
     * @return MultipartFile 文件
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年6月14日 下午1:44:55
     */
    public MultipartFile getFile( HttpServletRequest request,HttpServletResponse response ,String dir) throws Exception{
        
        CommonsMultipartResolver multipartResolver  = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartFile file = null;
        if(multipartResolver.isMultipart(request)){
            MultipartHttpServletRequest  multiRequest = (MultipartHttpServletRequest)request;//转换成多个请求
            Iterator<String>  iter = multiRequest.getFileNames();
            while(iter.hasNext()){//遍历文件
                file = multiRequest.getFile((String)iter.next());
                if(file == null){//文件为空跳过
                    continue;
                }
            }
        }
        return file;
    }
    /**
     * 获取文件名
     * @param filename 文件名称 
     * @return String 文件名称
     * @author 伍恰  
     * @date 2017年6月14日 下午1:46:21
     */
    public String getNewFileName(String filename) {
        
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date)+filename.substring(filename.lastIndexOf("."));
    }
    /**
     * 获取文件解析状态
     * @param filename 文件名称
     * @param filePath 文件路径
     * @param type 文件类型
     * @param sessionUser 用户信息
     * @return EmsSysExcel 文件状态
     * @author 伍恰  
     * @date 2017年6月14日 下午1:53:19
     */
    public EmsSysExcel getExcelFileStatus(String filename,String filePath, ExcelType type,SessionUser sessionUser) {
        EmsSysExcel excel=new EmsSysExcel();
        excel.setFilename(filename);
        excel.setFilepath(filePath);
        excel.setType(type.getValue());
        excel.setFilestatus(0);//已上传
        excel.setUploader(sessionUser.getId());
        excel.setUploadname(sessionUser.getUserName());
        excel.setUploadtime(new Date());
        //生成唯一编码
        String uuid = UUID.randomUUID().toString();
        excel.setUuid(uuid);
        return excel;
    }
}

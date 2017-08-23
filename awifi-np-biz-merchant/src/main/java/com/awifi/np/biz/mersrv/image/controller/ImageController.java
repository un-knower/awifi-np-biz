/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月15日 下午2:06:37
* 创建作者：张智威
* 文件名称：ImageController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mersrv.image.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.ms.util.StringUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.ImageUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.PathManager;
import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;

/**
 * 图片上传接口
 *
 * @author 张智威 2017年4月14日 下午2:13:13
 */
@APIs(description = "图片类")
@Controller
@RequestMapping("/mersrv/media/img")
public class ImageController extends BaseController {
    /**
     * 图片上传接口
     *
     * @param request
     * @return
     * @throws Exception
     * @author 张智威
     * @date 2017年4月14日 下午2:12:56
     */
    @API(summary = "图片123base64上传接口(字符串上传模式)", parameters = {
         /*   @Param(name = "data", description = "base64图片数据", dataType = DataType.STRING, required = true),
            @Param(name = "type", description = "类型(head,crousel,wallalbum)", dataType = DataType.STRING, required = false),
            @Param(name = "belongid", description = "所属id(商户id或者用户id)", dataType = DataType.STRING, required = false),*/
    })
    @RequestMapping(value = "/stream/form", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    
    public Map<String, Object> uploadForm(HttpServletRequest request) throws Exception {
        
        String imageData = request.getParameter("data");
        if(StringUtils.isBlank(imageData)){
            throw new BizException("E615191434","参数不能为空");
        }
        String fileName = ImageUtil.saveImage(PathManager.getInstance().getWebRootPath()
                .resolve(SysConfigUtil.getParamValue("image_upload_dir")).toFile().getAbsolutePath(), "", imageData);
        return this.successMsg((Object) (SysConfigUtil.getParamValue("image_upload_dir") + "/" + fileName));
    }
    /**
     * 图片上传接口
     *
     * @param request
     * @return
     * @throws Exception
     * @author 张智威
     * @date 2017年4月14日 下午2:12:56
     */
    @API(summary = "图片base64上传接口(字符串上传模式)", parameters = {
         /*   @Param(name = "data", description = "base64图片数据", dataType = DataType.STRING, required = true),
            @Param(name = "type", description = "类型(head,crousel,wallalbum)", dataType = DataType.STRING, required = false),
            @Param(name = "belongid", description = "所属id(商户id或者用户id)", dataType = DataType.STRING, required = false),*/
    })
    @RequestMapping(value = "/stream", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    
    public Map<String, Object> upload(@RequestBody(required = true) Map<String, Object> bodyParam) throws Exception {
        
        
        String imageData = (String)bodyParam.get("data");//.getParameter("data");
        if(StringUtils.isBlank(imageData)){
            throw new BizException("E615191434","参数不能为空");
        }
        String fileName = ImageUtil.saveImage(PathManager.getInstance().getWebRootPath()
                .resolve(SysConfigUtil.getParamValue("image_upload_dir")).toFile().getAbsolutePath(), "", imageData);
        return this.successMsg((Object) (SysConfigUtil.getParamValue("image_upload_dir") + "/" + fileName));
    }
    /**
     * 图片上传接口
     *
     * @param request
     * @return
     * @throws Exception
     * @author 张智威
     * @date 2017年4月14日 下午2:12:56
     */
    @API(summary = "图片formsubmit上传接口(文件上传接口)", parameters = {
            @Param(name = "file", description = "图片数据", dataType = DataType.FILE, required = true), })
    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map uploadSubmit(@RequestParam(value = "file") MultipartFile image) {
        // 校验文件类型是否正确
        long fileSize = image.getSize();
        logger.debug("上传图片文件大小" + fileSize);
        String type = image.getContentType();
        if (!StringUtil.isBlank(type)) {
            if (type.equals("image/jpeg") || type.equals("image/png") || type.equals("image/bmp")) {
            } else {
                throw new ValidException("E5071010", MessageUtil.getMessage("E5071010", "图片格式不正确!"));
                // return ResultUtil.getResult(0, "图片格式不正确");
            }
        } else {
            throw new ValidException("E5071010", MessageUtil.getMessage("E5071010", "图片格式不正确!"));
            // return ResultUtil.getResult(0, "图片格式不正确");
        }
        type = type.split("/")[1];

        // 如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中
        String uploadpath = PathManager.getInstance().getWebRootPath()
                .resolve(SysConfigUtil.getParamValue("image_upload_dir")).toFile().getAbsolutePath();
        // 这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的
        BufferedImage img = null;
        String fileName = "";
        try {
            img = ImageIO.read(image.getInputStream());
            if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
                throw new ValidException("E5071011", MessageUtil.getMessage("E5071011", "上传的图片为空!"));
                // return ResultUtil.getResult(0, "上传的图片为空");
            }

            // 检查宽高是否符合指定的宽高
            int sizes_index = 0;
            img = ImageUtil.fixSize(img, 600, 300);

            if (image.getSize() > 256 * 1024) {
                throw new BizException("E2017615151509",
                        "图片尺寸大小:" + (new DecimalFormat("####.##").format((image.getSize() / 1024))) + "KB 大于限制"
                                + 256 * 1024 + "kb上传失败");
            }
            // 进行保存到数据库
            fileName = System.currentTimeMillis() + ".jpg";
            ImageIO.write(img, "jpg", new File(uploadpath, fileName));
        } catch (Exception e) {
            // return ResultUtil.getFailResult("123");
            e.printStackTrace();
            return this.successMsg();
        }
        // return ResultUtil.getDataResult(ConfigUtil.getConfig("upload.url") +
        // "/" + fileName);
        return this.successMsg((Object) (SysConfigUtil.getParamValue("image_upload_dir") + "/" + fileName));
    }

}
package com.awifi.np.biz.timebuysrv.web.core.image.controller;

import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.timebuysrv.util.StringUtil;
import com.awifi.np.biz.timebuysrv.web.core.path.PathManager;
import com.awifi.np.biz.timebuysrv.web.log.ResultDTO;
import com.awifi.np.biz.timebuysrv.web.util.ImageUtil;
import com.awifi.np.biz.timebuysrv.web.util.ResultUtil;
import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;

import org.apache.commons.httpclient.util.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;

/**
 * 图片上传接口
 *
 * @author 张智威
 *         2017年4月14日 下午2:13:13
 */
@APIs(description = "图片类")
@Controller
@RequestMapping("/timebuysrv/image")
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
    @API(summary = "图片上传接口(字符串上传模式)", parameters = {
            @Param(name = "imageName", description = "图片名称", dataType = DataType.STRING, required = true),
            @Param(name = "imageData", description = "图片数据", dataType = DataType.STRING, required = true),})
    @RequestMapping(value = "/put", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map upload(HttpServletRequest request) throws Exception {
        String imageName = request.getParameter("imageName");
        String imageData = request.getParameter("imageData");

        // imageData=URLDecoder.decode(imageData);
        // imageData= URLEncoder.encode(imageData);
        ResultDTO result = ImageUtil.saveImage(PathManager.getInstance().getWebRootPath().resolve(SysConfigUtil.getParamValue("upload_dir") + getFoldPath(new Date())).toFile().getAbsolutePath(), "",
                imageData);
//        result.setData(ConfigUtil.getConfig("upload.url") + "/" + result.getData());
//        return result;
        return this.successMsg((Object) (SysConfigUtil.getParamValue("upload_url") + getFoldPath(new Date()) + "/" + result.getData()));
    }

    @API(summary = "图片上传接口(文件上传接口)", parameters = {
            @Param(name = "file", description = "图片数据", dataType = DataType.FILE, required = true),})
    @RequestMapping(value = "/submit", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map uploadSubmit(@RequestParam(value = "file") MultipartFile image) throws Exception{
        int index = 0;
        // 校验文件类型是否正确
        long fileSize = image.getSize();
        String type = image.getContentType();
        if (!StringUtil.isBlank(type)) {
            if (type.equals("image/jpeg") || type.equals("image/png")
                    || type.equals("image/bmp")) {
            } else {
                throw new ValidException("E5071010", MessageUtil.getMessage("E5071010","图片格式不正确!"));
//                return ResultUtil.getResult(0, "图片格式不正确");
            }
        } else {
            throw new ValidException("E5071010", MessageUtil.getMessage("E5071010","图片格式不正确!"));
//            return ResultUtil.getResult(0, "图片格式不正确");
        }
        type = type.split("/")[1];

        // 如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中
        //=================校验上传目录
        File path = PathManager.getInstance().getWebRootPath().resolve(SysConfigUtil.getParamValue("upload_dir")).resolve(getFoldPath(new Date())).toFile();//路径含有年月日
        if(!path.exists()){
            path.mkdirs();
        }
        String uploadpath =path.getAbsolutePath();
        // 这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的
        BufferedImage img = null;
        String fileName = "";
        try {
            img = ImageIO.read(image.getInputStream());
            if (img == null || img.getWidth(null) <= 0
                    || img.getHeight(null) <= 0) {
                throw new ValidException("E5071011", MessageUtil.getMessage("E5071011","上传的图片为空!"));
//                return ResultUtil.getResult(0, "上传的图片为空");
            }

            // 检查宽高是否符合指定的宽高
            int sizes_index = 0;
            img = ImageUtil.fixSize(img, 600, 300);
      /*  if (img.getWidth(null) != sizes[sizes_index][index]) {
            return ResultUtil.getResult(0,"图片" + (index + 1) + "的宽度应该为"
                    + sizes[sizes_index][index] + ",所传图片宽度为"
                    + img.getWidth(null) + ",请修改后重新上传");
        }
        if (img.getHeight(null) != sizes[sizes_index + 1][index]) {
            return ResultUtil.getResult(0,"图片" + (index + 1) + "的高度应该为"
                    + sizes[sizes_index + 1][index] + ",所传图片高度为"
                    + img.getHeight(null) + ",请修改后重新上传");
        }*/

            if (image.getSize() > 256 * 1024) {
                ResultUtil.getResult(0, "图片尺寸大小:" + (new
                        DecimalFormat("####.##").format((image.getSize() / 1024))) + "KB 大于限制" + 256 * 1024 + "kb上传失败");
            }
            // 进行保存到数据库
            fileName = System.currentTimeMillis() + ".jpg";
            ImageIO.write(img, "jpg", new File(uploadpath, fileName));
        } catch (Exception e) {
//            return ResultUtil.getFailResult("123");
            e.printStackTrace();
            return this.successMsg();
        }
//        return ResultUtil.getDataResult(ConfigUtil.getConfig("upload.url") + "/" + fileName);
        return this.successMsg((Object)(SysConfigUtil.getParamValue("upload_url") + getFoldPath(new Date()) + "/" + fileName));
    }
    
    /**
     * 获取上传年月日 /2017/06/22/
     * @param now
     * @return
     * @throws Exception
     * @author 余红伟 
     * @date 2017年6月22日 下午2:58:18
     */
    public static String getFoldPath(Date now) throws Exception {
        String date = DateUtil.formatDate(now, "yyyy/MM/dd");//2016/06/26
        return date;
    }
    public static void main(String[] args) throws Exception{
        System.out.println(getFoldPath(new Date()));
    }
}
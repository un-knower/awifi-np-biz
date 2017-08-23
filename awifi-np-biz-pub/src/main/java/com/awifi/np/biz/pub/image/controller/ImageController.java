/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月27日 上午9:58:47
* 创建作者：余红伟
* 文件名称：ImageController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.pub.image.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.pub.image.model.PubImage;
import com.awifi.np.biz.pub.image.path.PathManager;
import com.awifi.np.biz.pub.image.service.PubImageService;
import com.awifi.np.biz.pub.util.ImageUtil;
import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIs;
import com.cpj.swagger.annotation.DataType;
import com.cpj.swagger.annotation.Param;

@APIs(description = "图片通用上传")
@Controller
@RequestMapping("/pubsrv/image")
public class ImageController extends BaseController{
    /**
     * logger
     */
    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private PubImageService pubImageService;
    /**
     * 图片上传接口
     * @param image
     * @param type
     * @param orginalImageURL
     * @return
     * @throws Exception
     * @author 余红伟 
     * @date 2017年6月28日 下午1:57:58
     */
    @API(summary = "图片上传接口", parameters = {
            @Param(name = "file", description = "图片数据", dataType = DataType.FILE, required = true),
            @Param(name = "type", description = "上传标志", dataType = DataType.STRING,required = false),
            @Param(name = "oldURL", description = "被替换图片地址", dataType = DataType.STRING,required = false),
            @Param(name = "width", description = "需要后台压缩后的宽度", dataType = DataType.INTEGER,required = false),
            @Param(name = "height", description = "需要后台压缩后的高度", dataType = DataType.INTEGER,required = false),
            @Param(name = "sure", description = "是否确定替换,如果确定传值 sure", dataType = DataType.STRING,required = false),
            })
    @RequestMapping(value = "/submit", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String,Object> uploadSubmit(@RequestParam(value = "file",required = true) MultipartFile image,@RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "oldURL", required = false) String oldURL,@RequestParam(value = "width",required = false) Integer width,
            @RequestParam(value = "height", required = false) Integer height,@RequestParam(value = "sure", required = false) String sure) throws Exception{
        // ====  上传时： 单纯上传 ：不需要oldURL和sure参数,保存图片文件, 数据库添加记录，状态为初始 0 ，
        // ============  直接替换图片 : 需要oldURL参数和sure参数(值也为"sure")，  保存图片文件, 数据库添加记录，新图片状态为可以 1，被替换图片状态更改为删除  9
        // ============  (不直接)替换图片 : 需要oldURL参数，  保存图片文件, 数据库添加记录，新图片状态为可以 0. 图片状态进一步更改在 update请求里更改。
        logger.debug("参数 type: " + type +" , 替换图片url: "+ oldURL + "sure参数值: " + sure);
        long fileSize = image.getSize(); //文件大小
        
        // ============= ============== 校验文件类型是否正确
        String contentType = image.getContentType();//文件类型 image/jpeg ...
        if(!StringUtils.isBlank(contentType)){
            if (contentType.equals("image/jpeg") || contentType.equals("image/png")
                    || contentType.equals("image/bmp")) {
            } else {
                throw new ValidException("E3300004", MessageUtil.getMessage("E5071010","图片格式不正确!"));
            }
        } else {
            throw new ValidException("E3300004", MessageUtil.getMessage("E5071010","图片格式不正确!"));
        }
        // ========  从配置项里取图片最大上传限制 KB值，如果没有值 最大2M == 2048KB 
        Integer max_img_size = CastUtil.toInteger(SysConfigUtil.getParamValue("max_img_size"));
        if(max_img_size == null || max_img_size == 0){
            max_img_size = 2 * 1024;
        }
        //如果照片大于配置最大值，限制上传
        if(fileSize > max_img_size*1024){
            throw new ValidException("E3300003", MessageUtil.getMessage("E3300003", max_img_size));
        }
        //如果为空是默认 /service/media
        if(StringUtils.isBlank(type)){
            type = "default"; // upload_dir_default 使用默认地址
        }
        
        //否则是与微站相关兼容的
        String upload_dir = SysConfigUtil.getParamValue("upload_dir_" + type);
        String upload_url = SysConfigUtil.getParamValue("upload_url_" + type);
//        upload_dir = "upload"; //测试
//        upload_url = "upload";
        if(StringUtils.isBlank(upload_dir) || StringUtils.isBlank(upload_url)){
            throw new ValidException("E3300001", MessageUtil.getMessage("E3300001","上传路径或图片显示路径配置错误"));
        }
        //如果不是以/ 结尾 添加斜杠/  连接  2017/06/27  --> /service/media/2017/06/27
        if(!upload_dir.endsWith("/")){
            upload_dir += "/";
        }
        if(!upload_url.endsWith("/")){
            upload_url +="/";
        }
        //当前时间 的 年月日路径    2017/06/27
        String folderpath = getFolderPath(new Date());
        // 如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中
        //=================校验上传目录
//        logger.debug(upload_dir + " " + PathManager.getInstance().getWebRootPath().toFile().getAbsolutePath()); 
        File path = PathManager.getInstance().getWebRootPath().resolve(upload_dir).resolve(folderpath).toFile();
        if(!path.exists()){
            path.mkdirs();
        }
        String uploadpath = path.getAbsolutePath();
        // 这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的
        BufferedImage img = null;
        String fileName = "";
        try {
            img = ImageIO.read(image.getInputStream());
            if (img == null || img.getWidth(null) <= 0
                    || img.getHeight(null) <= 0) {
                throw new ValidException("E3300002", MessageUtil.getMessage("E3300002","上传的图片为空!"));
            }

            img = ImageUtil.compressImage(img, width, height);

            // 图片保存到磁盘
            fileName = System.currentTimeMillis() + rand(4) +".jpg"; //时间戳 + 四位随机数
            ImageIO.write(img, "jpg", new File(uploadpath, fileName));
        } catch (Exception e) {
            logger.error("图片保存到磁盘失败!");
            e.printStackTrace();
            return this.successMsg();
        }
        //图片返回地址
        String url = upload_url + folderpath + "/" + fileName;
        Long oldId = null;
        // ================ 存储到数据库
        //如果有olURL,查出老图片id
        if(!StringUtils.isBlank(oldURL)){
            PubImage oldPubImage = pubImageService.selectByUrl(oldURL);
            if(oldPubImage != null && oldPubImage.getId() != null){
              oldId = oldPubImage.getId();
            }
        }
        PubImage pubImage = new PubImage();
        pubImage.setUrl(url); //现图片返回路径
        pubImage.setOldUrl(oldURL); //替换图片url
        pubImage.setOldId(oldId); //被替换图id
        pubImage.setUploadDir(upload_dir); //配置项
        pubImage.setUploadUrl(upload_url); // 配置项 
        pubImage.setPath(uploadpath + "/" + fileName); //磁盘绝对路径地址
        pubImage.setStatus(0); //临时状态：0 ; 可以 ：1 ; 删除 : 9
        pubImageService.addPubImage(pubImage);
        
        // ===== 重新查询刚上传的图片
        PubImage newPubImage = pubImageService.selectByUrl(url);
        Long id = newPubImage.getId();
        
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("url", url);
        resultMap.put("id", id);
        // 确定是否要更换图片(直接上传), 参数sure不能为空，且oldURL也不能为空
        if(!StringUtils.isBlank(sure) && "sure".equals(sure) && !StringUtils.isBlank(oldURL)){
            //新上传图片状态该为可以 -- 1
            pubImageService.updatePubImageByIdAndStatus(id, 1);
            PubImage oldImage = pubImageService.selectByUrl(oldURL);
            //如果原来老图片存在则更新状态为 删除 -- 9 
            if(oldImage != null && oldImage.getId() != null){
                pubImageService.updatePubImageByIdAndStatus(oldImage.getId(), 9);
            }
        }
        return this.successMsg(resultMap);
    }
    
    
    /**
     * 修改被替换掉的图片状态
     * @param orginalImageURL
     * @return
     * @author 余红伟 
     * @date 2017年6月28日 下午1:59:07
     */
    @API(summary = "图片替换修改状态", parameters = {
            @Param(name = "id", description = "刚上传图片id", dataType = DataType.LONG,required = true)})
    @RequestMapping(value = "update", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String, Object> updateStatus(@RequestParam(value = "id", required = true) Long id) throws Exception{
        logger.debug("图片id : " + id);
        ValidUtil.valid("id", id, "{'required':true,'numeric':true}");
        //更新上传图片状态为1。 0 --> 1 临时 --> 可以,
        pubImageService.updatePubImageByIdAndStatus(id, 1);
        //通过上传图片的oldUrl再去获取被替换图片， 如果存在则更新状态。
        PubImage pubImage = pubImageService.selectById(id);
        if(pubImage != null && pubImage.getOldId() != null){
            
            PubImage oldPubImage = pubImageService.selectByUrl(pubImage.getOldUrl());
            if(oldPubImage != null && oldPubImage.getId()!= null){
                pubImageService.updatePubImageByIdAndStatus(oldPubImage.getId(), 9);
            }
        }
        
        return this.successMsg();
    }
    
    /**
     * 删除图片,状态为9和部分为0的,且删除磁盘图片文件
     * @param request
     * @return
     * @author 余红伟 
     * @date 2017年6月29日 上午10:02:00
     */
    @API(summary = "删除图片")
    @RequestMapping(value = "delete", method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Map<String, Object> delete(HttpServletRequest request){
        //获取需要删除的图片列表 状态为9和部分为0的
        List<PubImage> list = pubImageService.getDeleteList();
        logger.debug(JSON.toJSONString(list));
        
        //图片删除
        File file = null;
        for(PubImage pubImage : list){
            //真实图片文件
            file = new File(pubImage.getPath());
            if(file.exists()){
                pubImageService.deleteById(pubImage.getId());
                file.delete();
            }
        }
        return this.successMsg();
    }
    /**
     * 获取上传年月日 2017/06/27
     * @param now
     * @return
     * @throws Exception
     * @author 余红伟 
     * @date 2017年6月27日 下午2:34:01
     */
    public static String getFolderPath(Date now) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String date = sdf.format(now);
        return date;
    }
    /**
     * 固定位随机数
     * @param count
     * @return
     * @author 余红伟 
     * @date 2017年6月28日 下午2:48:03
     */
    public static String rand(int count){
        StringBuffer sb = new StringBuffer();
        String str = "0123456789";
        Random r = new Random();
        for(int i=0;i<count;i++){
            int num = r.nextInt(str.length());
            sb.append(str.charAt(num));
            str = str.replace((str.charAt(num)+""), "");
        }
        return sb.toString();
    }
    public static void main(String[] args) throws Exception{
        System.out.println(getFolderPath(new Date()));

    }
}

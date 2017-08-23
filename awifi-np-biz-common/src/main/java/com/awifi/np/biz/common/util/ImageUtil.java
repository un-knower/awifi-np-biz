package com.awifi.np.biz.common.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.awifi.np.biz.common.exception.BizException;

/**
 * 图片工具类
 * 
 * @author 张智威 2017年6月15日 下午2:29:21
 */
public class ImageUtil {
    /** 日志 **/
    private static final Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    /**
     * 图像按比例放大
     * 
     * @param originalImage
     * @param times
     * @return
     * @author 张智威
     * @date 2017年6月15日 下午2:22:45
     */
    public static BufferedImage zoomInImage(BufferedImage originalImage, Integer times) {
        int width = originalImage.getWidth() * times;
        int height = originalImage.getHeight() * times;
        BufferedImage newImage = new BufferedImage(width, height, originalImage.getType());
        Graphics g = newImage.getGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return newImage;
    }

    /**
     * 图像固定大小
     * 
     * @param originalImage
     * @param width
     * @param height
     * @return
     * @author 张智威
     * @date 2017年6月15日 下午2:23:21
     */
    public static BufferedImage fixSize(BufferedImage originalImage, int width, int height) {
        BufferedImage newImage = new BufferedImage(width, height, originalImage.getType());
        Graphics g = newImage.getGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return newImage;
    }

    /**
     * 说明:利用base64传输图片
     * 
     * @param path
     * @param imageName
     * @param imageData
     * @return String 文件名称
     * @throws IOException
     * @author dozen.zhang
     * @date 2015年12月20日下午12:52:39
     */
    public static String saveImage(String path, String imageName, String imageData) throws Exception {
        if (null == imageData || imageData.length() < 100) {
            // =========base64数据太短，明显不合理
            throw new BizException("E2017615185633","图片base64数据太短");
        } else {
            // 不同的图片类型 base64格式数据不一样
            if (imageData.startsWith("%2B")) {
                imageData = URLDecoder.decode(imageData, "UTF-8").substring(1);
            } else if (imageData.startsWith("+")) {
                imageData = imageData.substring(1);
            }
            // ======有效数据截取============
            if(imageData.indexOf("iVBO")>0){
                imageData = imageData.substring(imageData.indexOf("iVBO"));
            }
            if(imageData.indexOf("base64")>0){
                imageData = imageData.substring(imageData.indexOf("base64")+7);
            }
            imageData= imageData.replace("data:image/png;base64,","");
            imageData = imageData.replace("base64,","");
            imageData = imageData.replace(" ,","+");
            
            // 去除开头不合理的数据
            // imageData = URLDecoder.decode(imageData, "UTF-8");
            // imageData = imageData.substring(30);
            // int position=imageData.indexOf(",");
            // imageData=imageData.substring(position+1);
            // data:image/jpeg;base64,/9j/4AAQSkZJRgABA
            // data:image/png;base64,iVBORw0KGgoAAAANSUh
            // System.out.println(imageData);
            // =====base64解码后的数据==========
            byte[] data = decode(imageData);
            int len = data.length;
            int len2 = imageData.length();
            if (null == imageName || imageName.length() < 1) {
                imageName = System.currentTimeMillis() + RandomUtil.getRandomNumber(4) + ".png";
            }
            saveImageToDisk(data, path, imageName);
            logger.debug("上传成功,原始base64图像长度:" + len2 + "字符，解析后字节大小:" + len + "字节");
        }
        return imageName;
    }

    /**
     * 文件保存到磁盘
     * 
     * @param data
     * @param path
     * @param imageName
     * @return
     * @throws IOException
     * @author 张智威
     * @date 2017年6月15日 下午2:13:16
     */
    // references: http://blog.csdn.net/remote_roamer/article/details/2979822
    private static void saveImageToDisk(byte[] data, String path, String imageName) throws IOException {
        // int len = data.length;
        // 写入到文件
        
        File file =new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        logger.info("保存后的图片路径:"+path);
        FileOutputStream outputStream = new FileOutputStream(new File(path, imageName));
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * base64解码
     * 
     * @param imageData
     * @return
     * @throws IOException
     * @author 张智威
     * @date 2017年6月15日 下午2:16:22
     */
    private static byte[] decode(String imageData) throws IOException {
        byte[] data = Base64Decoder.decodeReturnByte(imageData);
    /*    for (int i = 0; i < data.length; ++i) {
            if (data[i] < 0) {
                // 调整异常数据
                data[i] += 256;
            }
        }*/
        return data;
    }

}

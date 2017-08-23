package com.awifi.np.biz.timebuysrv.web.util;


import com.awifi.np.biz.timebuysrv.util.Base64Util;
import com.awifi.np.biz.timebuysrv.web.log.ResultDTO;
import com.sun.imageio.plugins.jpeg.JPEGImageWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Path;

/**
 * 二维码生成海报
 *
 * @author dozen.zhang
 */
public class ImageUtil {
    private static final Logger logger = LoggerFactory.getLogger(ImageUtil.class);
    private static BufferedImage templateImage;

    /**
     * 图像放大
     *
     * @param originalImage
     * @param times
     * @return
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
     * 图像放大
     *
     * @param originalImage
     * @return
     */
    public static BufferedImage fixSize(BufferedImage originalImage, int width, int height) {
        BufferedImage newImage = new BufferedImage(width, height, originalImage.getType());
        Graphics g = newImage.getGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return newImage;
    }

    /**
     * 说明:
     *
     * @param path
     * @param imageName
     * @param imageData
     * @return void
     * @throws IOException
     * @author dozen.zhang
     * @date 2015年12月20日下午12:52:39
     */
    public static ResultDTO saveImage(String path, String imageName, String imageData) throws Exception {
        int success = 0;
        String message = "";
        if (null == imageData || imageData.length() < 100) {
            // 数据太短，明显不合理
        	throw new Exception("数据太短");
            
        } else {
            if (imageData.startsWith("%2B")) {
                imageData = URLDecoder.decode(imageData, "UTF-8").substring(1);
            } else if (imageData.startsWith("+")) {
                imageData = imageData.substring(1);
            }
            imageData = imageData.substring(imageData.indexOf("iVBO"));
            // 去除开头不合理的数据
            // imageData = URLDecoder.decode(imageData, "UTF-8");
            // imageData = imageData.substring(30);
            // int position=imageData.indexOf(",");
            // imageData=imageData.substring(position+1);
            // data:image/jpeg;base64,/9j/4AAQSkZJRgABA
            // data:image/png;base64,iVBORw0KGgoAAAANSUh
            // System.out.println(imageData);
            byte[] data = decode(imageData);
            int len = data.length;
            int len2 = imageData.length();
            if (null == imageName || imageName.length() < 1) {
                imageName = System.currentTimeMillis() + ".png";
            }
            saveImageToDisk(data, path, imageName);
            //
            success = 1;
            message = "上传成功,参数长度:" + len2 + "字符，解析文件大小:" + len + "字节";
        }
        return ResultUtil.getResult(0, imageName, "上传成功", null);
    }

    // references: http://blog.csdn.net/remote_roamer/article/details/2979822
    private static boolean saveImageToDisk(byte[] data, String path, String imageName) throws IOException {
        File dir = new File(path );
        if(!dir.exists()){
            dir.mkdirs();
        }
        int len = data.length;
        // 写入到文件
   /*     System.out.println(data);
        for(int i=0;i<data.length;i++){
            System.out.print(data[i]);
        }*/
        FileOutputStream outputStream = new FileOutputStream(new File(path, imageName));
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
        return true;
    }

    private static byte[] decode(String imageData) throws IOException {
       /* sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        return decoder.decodeBuffer(imageData)*/
        ;

        byte[] data = Base64Util.decodeBuffer(imageData);
        for (int i = 0; i < data.length; ++i) {
            if (data[i] < 0) {
                // 调整异常数据

                data[i] += 256;
            }
        }
        return data;
    }

    /**
     * 以JPEG编码保存图片
     *
     * @param dpi             分辨率
     * @param image_to_save   要处理的图像图片
     * @param JPEGcompression 压缩比
     * @param fos             文件输出流
     * @throws IOException
     */
    public static void saveAsJPEG(Integer dpi, BufferedImage image_to_save, float JPEGcompression, FileOutputStream fos)
            throws IOException {

        // useful documentation at
        // http://docs.oracle.com/javase/7/docs/api/javax/imageio/metadata/doc-files/jpeg_metadata.html
        // useful example program at
        // http://johnbokma.com/java/obtaining-image-metadata.html to output
        // JPEG data

        // old jpeg class
        // com.sun.image.codec.jpeg.JPEGImageEncoder jpegEncoder =
        // com.sun.image.codec.jpeg.JPEGCodec.createJPEGEncoder(fos);
        // com.sun.image.codec.jpeg.JPEGEncodeParam jpegEncodeParam =
        // jpegEncoder.getDefaultJPEGEncodeParam(image_to_save);

        // Image writer
        JPEGImageWriter imageWriter = (JPEGImageWriter) ImageIO.getImageWritersBySuffix("jpg").next();
        ImageOutputStream ios = ImageIO.createImageOutputStream(fos);
        imageWriter.setOutput(ios);
        // and metadata
        IIOMetadata imageMetaData = imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(image_to_save), null);

        // if(dpi != null && !dpi.equals("")){
        //
        // //old metadata
        // //jpegEncodeParam.setDensityUnit(com.sun.image.codec.jpeg.JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
        // //jpegEncodeParam.setXDensity(dpi);
        // //jpegEncodeParam.setYDensity(dpi);
        //
        // //new metadata
        // Element tree = (Element)
        // imageMetaData.getAsTree("javax_imageio_jpeg_image_1.0");
        // Element jfif =
        // (Element)tree.getElementsByTagName("app0JFIF").item(0);
        // jfif.setAttribute("Xdensity", Integer.toString(dpi) );
        // jfif.setAttribute("Ydensity", Integer.toString(dpi));
        //
        // }

        if (JPEGcompression >= 0 && JPEGcompression <= 1f) {

            // old compression
            // jpegEncodeParam.setQuality(JPEGcompression,false);

            // new Compression
            JPEGImageWriteParam jpegParams = (JPEGImageWriteParam) imageWriter.getDefaultWriteParam();
            jpegParams.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
            jpegParams.setCompressionQuality(JPEGcompression);

        }

        // old write and clean
        // jpegEncoder.encode(image_to_save, jpegEncodeParam);

        // new Write and clean up
        imageWriter.write(imageMetaData, new IIOImage(image_to_save, null, null), null);
        ios.close();
        imageWriter.dispose();

    }

   
}

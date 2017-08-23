/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月27日 上午10:45:45
* 创建作者：余红伟
* 文件名称：ImageUtil.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.pub.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;

public class ImageUtil {
    
    
    public static BufferedImage fixSize(BufferedImage originalImage, int width, int height) {
        BufferedImage newImage = new BufferedImage(width, height, originalImage.getType());
        Graphics g = newImage.getGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return newImage;
    }
    /**
     * 改变图片大小
     * 配置的最大宽高,传入参数的宽高，图片真实的宽高
     * @param orginalImage
     * @param width
     * @param height
     * @return
     * @author 余红伟 
     * @date 2017年6月28日 下午4:14:08
     */
    public static BufferedImage compressImage(BufferedImage orginalImage, Integer width, Integer height){
        double ratio = new Integer(orginalImage.getWidth()).doubleValue() / orginalImage.getHeight();
        // ===== 配置项的最大宽高
        Integer max_width = CastUtil.toInteger(SysConfigUtil.getParamValue("max_img_width"));
        Integer max_height =CastUtil.toInteger(SysConfigUtil.getParamValue("max_img_height"));
//        max_width = 1000;//测试注释
//        max_height = 1000;
        // ===== 配置不能为空 和 最大宽高不能大于 图片真实宽高 
        if(max_width == null ||max_width > orginalImage.getWidth()){
            max_width = orginalImage.getWidth();
        }
        if(max_height == null ||max_height > orginalImage.getHeight()){
            max_height = orginalImage.getHeight();
        }
        // ======= 如果传入的参数会在 图片宽高之下
        if(width == null || width >max_width){
            width = max_width;
        }
        if(height == null || height > max_height){
            height = max_height;
        }
        // 假设ratio 为2.0 图片正常 800 × 400  传入参数 600 × 200   则对应 600 300 和400 200  这个时候该选择400 200
        int tempHeight = (int) (width / ratio);
        int tempWidth = (int) (height * ratio);
       
        if(tempWidth > width){
            height = tempHeight;
        }
        if(tempHeight > height){
            width = tempWidth;
        }
        
        System.out.println(width + " " + height);
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.SCALE_SMOOTH);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(orginalImage, 0, 0,width,height, null);
        g.dispose();
        return newImage;
    }

}

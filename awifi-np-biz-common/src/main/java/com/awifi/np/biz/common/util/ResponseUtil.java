/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月13日 上午11:19:22
* 创建作者：许小满
* 文件名称：ResponseUtil.java
* 版本：  v1.0
* 功能：向客户端回写数据
* 修改记录：
*/
package com.awifi.np.biz.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.ByteBuffer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ResponseUtil {
    
    /** 日志 */
    private static final Log logger = LogFactory.getLog(ResponseUtil.class);

    /**
     * 回写图片内容
     * @param response 响应对象
     * @param content 图片内容
     * @author 许小满  
     * @date 2017年5月13日 上午11:26:38
     */
    public static void responseImage(HttpServletResponse response, ByteBuffer content) {
        ServletOutputStream os = null;
        try {
            os = response.getOutputStream();
            response.setContentType("image/jpeg; utf-8");
            byte[] imageByteArray = content.array();
            os.write(imageByteArray);
            os.flush();
        } catch (IOException e) {
            ErrorUtil.printException(e, logger);
        } finally {
            close(os);
        }
    }
    
    /**
     * 回写图片内容
     * @param response 响应对象
     * @param filePath 图片路径
     * @author 许小满  
     * @date 2017年5月13日 上午11:44:12
     */
    public static void responseImageByPath(HttpServletResponse response, String filePath){
        FileInputStream is = null;
        ServletOutputStream os = null;
        try {
            File file = new File(filePath);
            response.setContentType("image/jpeg; utf-8");
            os = response.getOutputStream();
            is = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int i = -1;
            while ((i = is.read(buffer)) != -1) {
                os.write(buffer, 0, i);
            }
            os.flush();
        } catch (IOException e) {
            ErrorUtil.printException(e, logger);
        } finally {
            close(is);
            close(os);
        }
    }
    
    /**
     * 回写文本
     * @param response 响应
     * @param text 文本内容
     * @author 许小满  
     * @date 2017年5月13日 下午1:48:01
     */
    public static void responseText(HttpServletResponse response, String text) {
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(text);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(out);
        }
    }

    /**
     * 关闭连接，释放资源
     * @param os 输出流
     * @author 许小满  
     * @date 2017年5月13日 上午11:32:58
     */
    private static void close(OutputStream os) {
        if(os == null){
            return;
        }
        try {
            os.close();
        } catch (IOException e) {
            ErrorUtil.printException(e, logger);
        }
    }
    
    /**
     * 关闭连接，释放资源
     * @param os 输出流
     * @author 许小满  
     * @date 2017年5月13日 上午11:32:58
     */
    private static void close(PrintWriter os) {
        if(os == null){
            return;
        }
        try {
            os.close();
        } catch (Exception e) {
            ErrorUtil.printException(e, logger);
        }
    }
    
    /**
     * 关闭连接，释放资源
     * @param is 输入流
     * @author 许小满  
     * @date 2017年5月13日 上午11:32:58
     */
    private static void close(InputStream is) {
        if(is == null){
            return;
        }
        try {
            is.close();
        } catch (IOException e) {
            ErrorUtil.printException(e, logger);
        }
    }
    
}

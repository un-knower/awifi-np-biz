package com.awifi.np.biz.common.util;

import java.io.File;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 * @Description: 解压缩工具类
 * @Title: ZipUtil.java
 * @Package com.awifi.toe.base.util
 * @author 许小满
 * @date 2015年12月17日 下午2:28:43
 * @version V1.0
 */
public class ZipUtil {

    /** 日志 */
    //private static final Log logger = LogFactory.getLog(ZipUtil.class);
    
    /**
     * 解压缩
     * @param zipSrcfile zip源文件
     * @param unzipFilePath  解压后的文件保存的路径
     * @throws Exception 异常
     * @date 2015年12月17日 下午2:29:43
     */
    public static void unzip(File zipSrcfile, String unzipFilePath) throws Exception {
        ZipFile zipFile = new ZipFile(zipSrcfile);
        zipFile.setFileNameCharset("UTF-8");
        if(!zipFile.isValidZipFile()){
            throw new ZipException("压缩文件不合法，可能被损坏");
        }
        //调用zip4j方法解压当前包
        zipFile.extractAll(unzipFilePath);
    }

}

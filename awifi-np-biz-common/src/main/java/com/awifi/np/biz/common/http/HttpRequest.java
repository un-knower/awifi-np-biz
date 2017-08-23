package com.awifi.np.biz.common.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.InterfaceException;
import com.awifi.np.biz.common.util.ErrorUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:Jan 9, 2017 7:44:44 PM
 * 创建作者：亢燕翔
 * 文件名称：HttpRequest.java
 * 版本：  v1.0
 * 功能：  客户端发送http请求
 * 修改记录：
 */
public class HttpRequest {

    /** 连接超时时间 */
    public static final int CONN_TIMEOUT = 10000;//10秒

    /** 读取数据超时时间 */
    public static final int READ_TIMEOUT = 30000;//30秒
    
    /** utf-8编码 */
    public static final String ENCODE_UTF8 = "UTF-8";
    
    /** 请求方式 GET */
    public static final String METHOD_GET = "GET";
    
    /** 请求方式 PUT  */
    public static final String METHOD_PUT = "PUT";
    
    /** 请求方式 DELETE  */
    public static final String METHOD_DELETE = "DELETE";
    
    /** 请求方式 POST*/
    public static final String METHOD_POST = "POST";
    
    /** logger  */
    private static Log logger = LogFactory.getLog(HttpRequest.class);
    /**
     * 客户端发送GET请求
     *    参数为MAP类型
     * @param path 请求url
     * @param params 请求参数
     * @return byte
     * @author kangyanxiang 
     * @date Jan 5, 2017 4:07:46 PM
     */
    public static ByteBuffer sendGetRequest(String path, String params){
        return SendGetRequest.sendGetRequest(path, params, METHOD_GET);
    }
    
    /**
     * 客户端发送DELETE请求
     *    参数为MAP类型
     * @param path 请求url
     * @param params 请求参数
     * @return byte
     * @author kangyanxiang 
     * @date Jan 5, 2017 4:26:35 PM
     */
    public static ByteBuffer sendDeleteRequest(String path, String params){
        return SendGetRequest.sendGetRequest(path, params, METHOD_DELETE);
    }
    
    /**
     * 客户端发送POST请求
     *      请求体body包含json参数
     * @param path 请求url
     * @param params 请求参数
     * @param bodyParams json数据
     * @return byte
     * @author kangyanxiang 
     * @date Jan 5, 2017 4:33:07 PM
     */
    public static ByteBuffer sendPostRequest(String path, String params, String bodyParams){
        return SendPostRequest.sendPostRequest(path, params, bodyParams, METHOD_POST);
    }
    
    /**
     * 客户端发送PUT请求
     *    不设置超时时间
     *    参数为MAP类型
     * @param path 请求url
     * @param params 请求参数
     * @param bodyParams json数据
     * @return byte
     * @author kangyanxiang 
     * @date Jan 5, 2017 4:26:35 PM
     */
    public static ByteBuffer sendPutRequest(String path, String params, String bodyParams){
        return SendPostRequest.sendPostRequest(path, params, bodyParams, METHOD_PUT);
    }
    
    /**
     * 获取url
     * @param path 请求url
     * @param param 请求参数
     * @return 新url
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date Jan 9, 2017 7:47:25 PM
     */
    public static String getURL(String path, String param) throws Exception{
        if(StringUtils.isBlank(param)){
            return path;
        }
        return (path.indexOf("?") > -1) ? path + "&" + param : path + "?" + param;
    }
    
    /**
     * 获取参数
     * @param paramMap 请求参数
     * @return new paramMap
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date Jan 9, 2017 7:48:19 PM
     */
    public static String getParams(Map<String, String> paramMap){
        if(paramMap == null || paramMap.size()<= 0){
            return StringUtils.EMPTY;
        }
        StringBuilder params = new StringBuilder();
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            String value = StringUtils.defaultString(entry.getValue());
            String key = entry.getKey();
            try {
                params.append(key).append('=').append(URLEncoder.encode(value, ENCODE_UTF8)).append('&');
            } catch (UnsupportedEncodingException e) {
                ErrorUtil.printException(e, logger);
                throw new BizException("E2000019", "UnsupportedEncoding");//未知异常
            }
        }
        params.deleteCharAt(params.length() - 1);
        return params.toString();
    }
    
    /**
     * 将inputStream 读取 成 ByteBuffer。
     * @param is inputStream
     * @return 接口返回值
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date Jan 9, 2017 8:18:35 PM
     */
    public static ByteBuffer parseInputStream(InputStream is) throws Exception{
        if(is == null){
            return null;
        }
        ByteArrayOutputStream bos = null;
        ByteBuffer byteBuffer = null;
        try{
            bos = new ByteArrayOutputStream();
            byte[] byts = new byte[1024];
            int len = 0;
            while ((len = is.read(byts)) >= 0){
                bos.write(byts, 0, len);
            }
            byteBuffer = ByteBuffer.wrap(bos.toByteArray());
        }catch(Exception e){
            throw e;
        }finally{
            try{
                is.close();
                bos.close();
            }catch(Exception e1){}
        }
        return byteBuffer;
    }
    
    /**
     * 针对connection做返回后结果处理
     * @param connection  
     * @return ByteBuffer
     * @author 亢燕翔  
     * @param params 
     * @param path 
     * @throws Exception 
     * @date 2017年1月19日 上午9:01:18
     */
    public static ByteBuffer formatConnection(HttpURLConnection connection, String path, String params) throws Exception {
        Integer responseCode = null;
        try{
            responseCode =  connection.getResponseCode();
        }catch(IOException e){
            throw new InterfaceException(MessageUtil.getMessage("E2000009"), path, params, e);//接口无返回值! 
        }
        if (responseCode.equals(200)) {
            return HttpRequest.parseInputStream(connection.getInputStream());
        } else {
            ByteBuffer buff = HttpRequest.parseInputStream(connection.getErrorStream());
            logger.info("http request error. " + new String(buff.array(), HttpRequest.ENCODE_UTF8));
        }
        return null;
    }
    
    /**
     * 接口日志输出规范话
     * @param method http请求方式
     * @param url 请求url
     * @param params 请求参数
     * @param bodyParams 请求体参数
     * @param returnValue 返回数据
     * @param beginTime 接口调用开始时间
     * @author 许小满  
     * @date 2017年2月22日 上午11:29:01
     */
    public static void log(String method, String url, String params, String bodyParams, ByteBuffer returnValue, long beginTime){
        StringBuffer log = new StringBuffer();
        log.append("提示：接口http请求方式（").append(method).append("），");
        if(StringUtils.isNoneBlank(url)){
            log.append("请求url（").append(url).append("），");
        }
        if(StringUtils.isNoneBlank(params)){
            log.append("请求参数（").append(params).append("），");
        }
        if(StringUtils.isNoneBlank(bodyParams)){
            log.append("请求体参数（").append(bodyParams).append("），");
        }
        if(returnValue != null){
            try {
                String interfaceReturnValue = new String(returnValue.array(), HttpRequest.ENCODE_UTF8);//接口返回值
                if(interfaceReturnValue != null){
                    int maxLength = interfaceReturnValue.length();
                    if(maxLength > 4000){//长度截取，防止日志输出太多
                        interfaceReturnValue = interfaceReturnValue.substring(0, 4000) + "...";
                    }
                }
                log.append("返回数据（").append(interfaceReturnValue).append("），");
            } catch (UnsupportedEncodingException e) {
                ErrorUtil.printException(e, logger);
            }
        }
        log.append("共花费了 ").append(System.currentTimeMillis()-beginTime).append(" ms.");
        logger.info(log.toString());
    }
}

package com.awifi.np.biz.common.http;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;

import org.apache.commons.lang3.StringUtils;

import com.awifi.np.biz.common.exception.InterfaceException;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月11日 下午8:28:27
 * 创建作者：亢燕翔
 * 文件名称：SendPostRequest.java
 * 版本：  v1.0
 * 功能：  发送post请求
 * 修改记录：
 */
public class SendPostRequest {

    /**
     * 客户端发送POST请求
     * @param path 请求url
     * @param params 请求参数
     * @param bodyParams 请求体body数据
     * @param method 
     * @return byte
     * @author kangyanxiang 
     * @date Jan 5, 2017 4:35:06 PM
     */
    public static ByteBuffer sendPostRequest(String path, String params, String bodyParams, String method){
        long beginTime = System.currentTimeMillis();
        ByteBuffer returnValue = null;
        try {
            if(StringUtils.isBlank(bodyParams)){
                returnValue = HttpRequest.formatConnection(sendPostRequest(path, params), path, params);
            } else {
                returnValue = HttpRequest.formatConnection(sendBodyRequest(path, bodyParams, method), path, params);  
            }
        } catch (Exception e) {
            throw new InterfaceException(e.getMessage(), path, params, e);
        } finally {
            HttpRequest.log(method, path, params, bodyParams, returnValue, beginTime);
        }
        return returnValue;
    }
    
    /**
     * 发送post请求
     * @param path 请求url
     * @param params 请求参数
     * @return conn
     * @author kangyanxiang 
     * @throws Exception 
     * @date Jan 5, 2017 7:20:21 PM
     */
    public static HttpURLConnection sendPostRequest(String path, String params) throws Exception{
        byte[] paramData = params.getBytes();
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设定请求的方法为"POST"，默认是GET
        conn.setRequestMethod(HttpRequest.METHOD_POST);
        // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在   
        // http正文内，因此需要设为true, 默认情况下是false;
        conn.setDoOutput(true);
        // 设定传送的内容类型
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(paramData.length));
        // Post 请求不能使用缓存
        conn.setUseCaches(false);
        // 设置 连接主机超时（单位：毫秒）
        conn.setConnectTimeout(HttpRequest.CONN_TIMEOUT);
        // 设置从主机读取数据超时（单位：毫秒）
        conn.setReadTimeout(HttpRequest.READ_TIMEOUT);
        OutputStream outputStream = conn.getOutputStream();
        outputStream.write(paramData);
        outputStream.flush();
        outputStream.close();
        return conn;
    }
    
    /**
     * 客户端发送POST请求
     *    请求体body包含json参数
     * @param path 请求url
     * @param bodyParams 请求体body
     * @param method  
     * @return conn
     * @author kangyanxiang 
     * @throws Exception 
     * @date Jan 5, 2017 7:16:23 PM
     */
    public static HttpURLConnection sendBodyRequest(String path, String bodyParams, String method) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod(method);
        conn.setUseCaches(false);
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setConnectTimeout(HttpRequest.CONN_TIMEOUT);
        conn.setReadTimeout(HttpRequest.READ_TIMEOUT);
        DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
        outputStream.write(bodyParams.getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
        return conn;
    }
    
}

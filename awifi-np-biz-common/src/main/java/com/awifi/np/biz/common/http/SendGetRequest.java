package com.awifi.np.biz.common.http;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;

import com.awifi.np.biz.common.exception.InterfaceException;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月11日 下午8:27:41
 * 创建作者：亢燕翔
 * 文件名称：SendGetRequest.java
 * 版本：  v1.0
 * 功能：  发送get请求
 * 修改记录：
 */
public class SendGetRequest {

    /**
     * 客户端发送GET/DELETE请求
     * @param path 请求url
     * @param params 请求参数
     * @param method 请求方法
     * @return byte
     * @author kangyanxiang 
     * @date Jan 5, 2017 4:06:40 PM
     */
    public static ByteBuffer sendGetRequest(String path, String params, String method){
        long beginTime = System.currentTimeMillis();
        ByteBuffer returnValue = null;
        try {
            String url = HttpRequest.getURL(path, params);
            returnValue = HttpRequest.formatConnection(sendGetRequest(url, method), path, params);
            return returnValue;
        } catch (Exception e) {
            throw new InterfaceException(e.getMessage(), path, params, e);
        }finally{
            HttpRequest.log(HttpRequest.METHOD_GET, path, params, null, returnValue, beginTime);
        }
    }

    /**
     * 向服务端发送http请求
     * @param path 请求url
     * @param method 请求方法类型
     * @return byte
     * @throws Exception 异常
     * @author kangyanxiang 
     * @date Jan 5, 2017 4:19:58 PM
     */
    public static HttpURLConnection sendGetRequest(String path, String method) throws Exception{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 默认情况下是false;
        conn.setDoOutput(false);
        // 设置是否从httpUrlConnection读入，默认情况下是true
        conn.setDoInput(true);
        // Get 请求不能使用缓存
        conn.setUseCaches(false);
        conn.setRequestMethod(method);
        // 设置 连接主机超时（单位：毫秒）
        conn.setConnectTimeout(HttpRequest.CONN_TIMEOUT);
        // 设置从主机读取数据超时（单位：毫秒）
        conn.setReadTimeout(HttpRequest.READ_TIMEOUT);
        return conn;
    }
    
}

package com.awifi.np.biz.api.client.auth.http.util;

import java.net.HttpURLConnection;
import java.nio.ByteBuffer;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.awifi.np.biz.api.client.dbcenter.http.base.OpenTokenBaseHttpRequest;
import com.awifi.np.biz.common.exception.InterfaceException;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.http.SendGetRequest;
import com.awifi.np.biz.common.http.SendPostRequest;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月18日 下午4:28:21
 * 创建作者：亢燕翔
 * 文件名称：CenterHttpRequest.java
 * 版本：  v1.0
 * 功能：调用接入认证http接口，封装token逻辑
 * 修改记录：
 */
@SuppressWarnings("unchecked")
public class AuthHttpRequest {
    
    /**
     * 调用数据中心,get请求
     * @param path path
     * @param params 参数
     * @return ByteBuffer
     * @author 亢燕翔  
     * @date 2017年1月18日 下午4:29:44
     */
    public static Map<String, Object> sendGetRequest(String path, String params){
        long beginTime = System.currentTimeMillis();
        String url = null;
        ByteBuffer byteBuffer = null;
        Map<String, Object> resultMap = null;
        try {
            url = OpenTokenBaseHttpRequest.getNewURL(path, params);//对url增加安全令牌
            HttpURLConnection connection = SendGetRequest.sendGetRequest(url, HttpRequest.METHOD_GET);//发送get请求
            byteBuffer = OpenTokenBaseHttpRequest.priResponse(connection, url, params, HttpRequest.METHOD_GET);//对请求进行校验，判断是否成功（不成功进行二次请求）
            resultMap = formatByteBufferToMap(url, params , byteBuffer);//将buff转为map
        } catch (Exception e) {
            if(e instanceof InterfaceException){
                throw (InterfaceException)e;
            }
            throw new InterfaceException(e.getMessage(), path, params, e);
        } finally {
            HttpRequest.log(HttpRequest.METHOD_GET, path, params, null, byteBuffer, beginTime);
        }
        return resultMap;
    }

    /**
     * 调用数据中心,delete请求
     * @param path path
     * @param params 参数
     * @return ByteBuffer
     * @author 亢燕翔  
     * @date 2017年1月18日 下午8:07:26
     */
    public static Map<String, Object> sendDeleteRequest(String path, String params){
        long beginTime = System.currentTimeMillis();
        String url = null;
        ByteBuffer byteBuffer = null;
        Map<String, Object> resultMap = null;
        try {
            url = OpenTokenBaseHttpRequest.getNewURL(path, params);//对url增加安全令牌
            HttpURLConnection connection = SendGetRequest.sendGetRequest(url, HttpRequest.METHOD_DELETE);//发送delete请求
            byteBuffer = OpenTokenBaseHttpRequest.priResponse(connection, url, params, HttpRequest.METHOD_DELETE);//对请求进行校验，判断是否成功（不成功进行二次请求）
            resultMap = formatByteBufferToMap(url, params , byteBuffer);//将buff转为map
        } catch (Exception e) {
            if(e instanceof InterfaceException){
                throw (InterfaceException)e;
            }
            throw new InterfaceException(e.getMessage(), path, params, e);
        } finally {
            HttpRequest.log(HttpRequest.METHOD_DELETE, path, params, null, byteBuffer, beginTime);
        }
        return resultMap;
    }
    
    /**
     * 调用数据中心,post请求
     * @param path 请求url
     * @param params 请求参数
     * @return ByteBuffer
     * @author 亢燕翔  
     * @date 2017年1月19日 上午8:48:40
     */
    public static Map<String, Object> sendPostRequest(String path, String params){
        long beginTime = System.currentTimeMillis();
        String url = null;
        ByteBuffer byteBuffer = null;
        Map<String, Object> resultMap = null;
        try {
            url = OpenTokenBaseHttpRequest.getNewURL(path,null);//对url增加安全令牌
            HttpURLConnection connection = SendPostRequest.sendBodyRequest(url, params, HttpRequest.METHOD_POST);//发送请求 (body中发送)
            byteBuffer = OpenTokenBaseHttpRequest.priResponse(connection, url, params, HttpRequest.METHOD_POST);//对请求进行校验，判断是否成功（不成功进行二次请求）
            resultMap = formatByteBufferToMap(url, params, byteBuffer);//将buff转为map
        } catch (Exception e) {
            if(e instanceof InterfaceException){
                throw (InterfaceException)e;
            }
            throw new InterfaceException(e.getMessage(), path, params, e);
        } finally {
            HttpRequest.log(HttpRequest.METHOD_POST, path, null, params, byteBuffer, beginTime);
        }
        return resultMap;
    }
    
    /**
     * 调用数据中心,put请求
     * @param path path
     * @param params 参数
     * @return ByteBuffer
     * @throws Exception 
     * @author 亢燕翔  
     * @date 2017年1月18日 下午8:07:26
     */
    public static Map<String, Object> sendPutRequest(String path, String params) throws Exception{
        long beginTime = System.currentTimeMillis();
        String url = null;
        ByteBuffer byteBuffer = null;
        Map<String, Object> resultMap = null;
        try {
            url = OpenTokenBaseHttpRequest.getNewURL(path,null);//对url增加安全令牌
            HttpURLConnection connection = SendPostRequest.sendBodyRequest(url, params, HttpRequest.METHOD_PUT);//发送请求 (body中发送)
            byteBuffer = OpenTokenBaseHttpRequest.priResponse(connection, url, params, HttpRequest.METHOD_PUT);//对请求进行校验，判断是否成功（不成功进行二次请求）
            resultMap = formatByteBufferToMap(url, params, byteBuffer);//将buff转为map
        } catch (Exception e) {
            if(e instanceof InterfaceException){
                throw (InterfaceException)e;
            }
            throw new InterfaceException(e.getMessage(), path, params, e);
        } finally {
            HttpRequest.log(HttpRequest.METHOD_PUT, path, null, params, byteBuffer, beginTime);
        }
        return resultMap;
    }
    
    /**
     * byteBuffer转为map
     * @param interfaceUrl url
     * @param interfaceParam 参数
     * @param byteBuffer buff
     * @return map
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年1月12日 下午2:17:55
     */
    private static Map<String, Object> formatByteBufferToMap(String interfaceUrl, String interfaceParam, ByteBuffer byteBuffer) throws Exception {
        if(byteBuffer == null){
            throw new InterfaceException(MessageUtil.getMessage("E2000009"), interfaceUrl, interfaceParam);//接口无返回值! 
        }
        String returnMessage = new String(byteBuffer.array(), "UTF-8");
        Map<String, Object> resultMap = JsonUtil.fromJson(returnMessage,Map.class);
        if(resultMap == null || resultMap.isEmpty()){//未得到结果
            throw new InterfaceException(MessageUtil.getMessage("E2000010"), interfaceUrl, interfaceParam);//接口返回值转map后为空！
        }
        String resultCode = (String) resultMap.get("resultCode");
        if(StringUtils.isBlank(resultCode) || !resultCode.equals("0")){
            throw new InterfaceException(returnMessage, interfaceUrl, interfaceParam, returnMessage);
        }
        return resultMap;
    }
    
}

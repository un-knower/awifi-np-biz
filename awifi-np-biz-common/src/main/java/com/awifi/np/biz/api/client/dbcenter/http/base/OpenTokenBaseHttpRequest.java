/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月1日 下午8:18:43
* 创建作者：许小满
* 文件名称：OpenTokenHttpRequest.java
* 版本：  v1.0
* 功能：开放平台http接口-须提供token，相关公共代码抽取,目前仅允许在CenterHttpRequest.java、AuthHttpRequest.java中使用
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.http.base;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.ByteBuffer;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.awifi.np.biz.api.client.dbcenter.token.util.TokenClient;
import com.awifi.np.biz.common.exception.InterfaceException;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.http.SendGetRequest;
import com.awifi.np.biz.common.http.SendPostRequest;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;

@SuppressWarnings("unchecked")
public class OpenTokenBaseHttpRequest {
    
    /** 日志 */
    private static final Log logger = LogFactory.getLog(OpenTokenBaseHttpRequest.class);
    
    /**
     * 获取新的url
     * @param path url
     * @param params 参数
     * @return new url
     * @throws Exception 
     * @author 亢燕翔  
     * @date 2017年1月19日 下午8:10:17
     */
    public static String getNewURL(String path, String params) throws Exception {
        if(StringUtils.isBlank(params) && path.indexOf("?") == -1){
            return path + "?access_token=" +TokenClient.getAccessToken();
        } else {
            return HttpRequest.getURL(path, params)+ "&access_token=" + TokenClient.getAccessToken();
        }
    }
    
    /**
     * 第一次发送请求
     * @param connection 链接实例
     * @param path path
     * @param params 请求参数
     * @param method 请求方式
     * @return ByteBuffer buff
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年1月19日 下午8:28:51
     */
    public static ByteBuffer priResponse(HttpURLConnection connection, String path, String params, String method) throws Exception {
        Integer responseCode = null;
        try{
            responseCode =  connection.getResponseCode();//获取状态码
        }catch(IOException e){
            throw new InterfaceException(MessageUtil.getMessage("E2000009"), path, params, e);//接口无返回值! 
        }
        /* "200" 直接返回成功信息 */
        if(responseCode.equals(200)){
            return HttpRequest.parseInputStream(connection.getInputStream());
        }
        /* 400 Bad Request 请求出现语法错误  */
        else if (responseCode.equals(400)){
            throw new InterfaceException(MessageUtil.getMessage("E2000023"), path, params);//参数不符合规范!
        }
        /* 403  Forbidden  资源不可用。服务器理解客户的请求，但拒绝处理它，通常由于服务器上文件或目录的权限设置导致。  */
        else if(responseCode.equals(403)){//针对数据中心token失效
            ByteBuffer buff = HttpRequest.parseInputStream(connection.getErrorStream());
            if(buff == null){
                throw new InterfaceException(MessageUtil.getMessage("E2000009"), path, params);//接口无返回值! 
            }
            //如果message是[token为空]则返回buff
            String returnMessage = new String(buff.array(), "UTF-8");
            if(StringUtils.isBlank(returnMessage)){
                //非token失效，则抛出异常信息
                throw new InterfaceException(MessageUtil.getMessage("E2000009"), path, params);//接口无返回值!
            }
            Map<String,Object> returnMap = JsonUtil.fromJson(returnMessage, Map.class);
            String errorMessage = (String) returnMap.get("errormessage");
            if(StringUtils.isNotBlank(errorMessage) && errorMessage.indexOf("凭证无效") > -1){
                //再次调用数据中心接口
                if(method.equals(HttpRequest.METHOD_POST) || method.equals(HttpRequest.METHOD_PUT)){
                    return sendSecPost(path, params);
                } else {
                    return sendSecGet(path, method);
                }
            } else {
                throw new InterfaceException(MessageUtil.getMessage("E2000018"), path, params, returnMessage);//接口异常!
            }
            
        }
        /* 其它状态码  */
        else {
            logger.info("提示：responseCode=" + responseCode);
            ByteBuffer buff = HttpRequest.parseInputStream(connection.getErrorStream());
            throw new InterfaceException(MessageUtil.getMessage("E2000018"), path, params, buff != null ? new String(buff.array(), "UTF-8") : StringUtils.EMPTY);//接口异常!
        }
    }
    
    /**
     * 针对token失效,进行二次发送get/delete请求
     * @param url url
     * @param method 请求方式
     * @return ByteBuffer
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年1月18日 下午8:15:13
     */
    private static ByteBuffer sendSecGet(String url, String method) throws Exception {
        String oldToken = url.substring(url.indexOf("access_token="), url.length());//获取旧的access_token
        String newToken = TokenClient.resetAccessToken();//创新新的access_token
        String newUrl = url.replaceAll(oldToken, "access_token="+newToken);//创建新url，access_token替换
        HttpURLConnection conn = SendGetRequest.sendGetRequest(newUrl, method);//发送请求
        return checkSecResponse(conn,newUrl,newToken,null);//二次请求的结果进行校验
    }
    
    /**
     * 针对token失效,进行二次发送post请求
     * @param path url
     * @param params 参数
     * @return ByteBuffer
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年1月19日 上午8:50:37
     */
    private static ByteBuffer sendSecPost(String path, String params) throws Exception {
        String oldToken = path.substring(path.indexOf("access_token="), path.length());//获取旧的access_token
        String newToken = TokenClient.resetAccessToken();//创新新的access_token
        String newPath = path.replaceAll(oldToken, "access_token="+newToken);//创建新url，access_token替换
        HttpURLConnection connection = SendPostRequest.sendPostRequest(newPath, params);
        return checkSecResponse(connection,newPath,newToken,params);//二次请求的结果进行校验
    }
    
    /**
     * 对二次请求返回的信息格式化，统一返回
     * @param connection 链接实例
     * @param path path
     * @param token 安全令牌
     * @param params 请求参数
     * @return ByteBuffer buff
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年1月19日 下午6:48:58
     */
    private static ByteBuffer checkSecResponse(HttpURLConnection connection,String path, String token, String params) throws Exception {
        int responseCode;
        try{
            responseCode =  connection.getResponseCode();
        }catch(IOException e){
            throw new InterfaceException(MessageUtil.getMessage("E2000009"), path, params, e);//接口无返回值! 
        }
        /* "200" 直接返回成功信息 */
        if(responseCode == 200){
            return HttpRequest.parseInputStream(connection.getInputStream());
        }
        /* 400 Bad Request 请求出现语法错误 */
        else if (responseCode == 400){
            throw new InterfaceException(MessageUtil.getMessage("E2000023"), path, params);//参数不符合规范!
        }
        /* 403  Forbidden  资源不可用。服务器理解客户的请求，但拒绝处理它，通常由于服务器上文件或目录的权限设置导致。  */
        else if(responseCode == 403){ //"403"
            ByteBuffer buff = HttpRequest.parseInputStream(connection.getErrorStream());
            if(buff == null){
                throw new InterfaceException(MessageUtil.getMessage("E2000009"), path, params);//接口无返回值! 
            }
            //如果message是[token为空]则返回buff
            String returnMessage = new String(buff.array(), "UTF-8");
            if(StringUtils.isBlank(returnMessage)){
                //非token失效，则抛出异常信息
                throw new InterfaceException(MessageUtil.getMessage("E2000009"), path, params);//接口无返回值!
            }
            Map<String,Object> returnMap = JsonUtil.fromJson(returnMessage, Map.class);
            String errorMessage = (String) returnMap.get("errormessage");
            if(StringUtils.isNotBlank(errorMessage) && errorMessage.indexOf("凭证无效") > -1){
                throw new InterfaceException(MessageUtil.getMessage("E2000020",token), path, params, returnMessage);//数据中心access_token[{0}]校验失败!
            }else {
                throw new InterfaceException(MessageUtil.getMessage("E2000018"), path, params, returnMessage);//接口异常!
            }
        }
        /* 其它状态码 */
        else {
            logger.info("提示：responseCode=" + responseCode);
            ByteBuffer buff = HttpRequest.parseInputStream(connection.getErrorStream());
            String returnMessage = buff != null ? new String(buff.array(), "UTF-8") : StringUtils.EMPTY;
            throw new InterfaceException(returnMessage, path, params, returnMessage);//接口异常!
        }
    }
}

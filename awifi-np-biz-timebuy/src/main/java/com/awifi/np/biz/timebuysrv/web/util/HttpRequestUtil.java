package com.awifi.np.biz.timebuysrv.web.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;

import com.awifi.np.biz.common.exception.InterfaceException;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.timebuysrv.util.StringUtil;

public class HttpRequestUtil {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(HttpRequestUtil.class);

    public static void main(String args[]){
        String result = HttpRequestUtil.sendGet("http://192.168.41.49:8085/awifi-oauth-server-web/tokenController/authorizationGrant?oauthCode=msp_tp53");
        System.out.println(result);
    }
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @modificationHistory.
     * @param url
     *            发送请求的URL
     * @param map
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, Map map ) {
        StringBuffer result = new StringBuffer("");
        BufferedReader in = null;
        Long startTime = System.currentTimeMillis();
        try {
            if(map.size()>0){
                String paramStr = MapUtils.join(map,"&");
                if(url.indexOf("?")>0){
                    url+= "&"+paramStr;
                }else{
                    url+= "?"+paramStr;
                }
            }
            return UrlRead(url);
        } catch (Exception e) {
            return "400";
        }

    }
    /**
     * 向指定URL发送GET方法的请求
     *
     * @modificationHistory.
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param,String ip,String port) {


        StringBuffer result = new StringBuffer("");
        Long startTime = System.currentTimeMillis();
        BufferedReader in = null;
        try {
            String urlNameString = url;
            if(url.indexOf("?")>0){
                urlNameString+= "&" + param;
            }else{
                urlNameString+= "?" + param;
            }
           
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("X-Forwarded-For", ip);
            connection.setRequestProperty("X-Forwarded-Port", port==null?"80":port);
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
           /* String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }*/

            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            Long endTime = System.currentTimeMillis();
            logger.info("success to httpget \n"+urlNameString+"  cost time:"+(endTime-startTime)+"\n result:"+result);

        } catch (Exception e) {
            // System.out.println("发送GET请求出现异常！" + e);
            Long endTime = System.currentTimeMillis();
         //   logger.info("fail to httpget \n "+url+"   cost time:"+(endTime-startTime));
           // logger.error("send http get error use url: "+url+"error :"+"cost time:"+(endTime-startTime),e);
            logger.error("send http get error ",e);
            return "400";
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                logger.error("close http request failt:", e2);
                // e2.printStackTrace();
            }
        }
        return result.toString();
    }
    public static String UrlRead(String url){
        StringBuffer result = new StringBuffer("");
        BufferedReader in = null;
        Long startTime = System.currentTimeMillis();
        try{
        URL realUrl = new URL(url);
        // 打开和URL之间的连接
        URLConnection connection = realUrl.openConnection();
        // 设置通用的请求属性
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        // 建立实际的连接
        connection.connect();
        // 定义 BufferedReader输入流来读取URL的响应
        in = new BufferedReader(new InputStreamReader(
                connection.getInputStream(), "UTF-8"));
        String line;
        while ((line = in.readLine()) != null) {
            result.append(line);
        }
        Long endTime = System.currentTimeMillis();
        logger.info("success to httpget \n"+url+"  cost time:"+(endTime-startTime)+"\n result:"+result);
        }
        catch (Exception e) {
            // System.out.println("发送GET请求出现异常！" + e);
            Long endTime = System.currentTimeMillis();
            //logger.info("fail to httpget \n "+url+"   cost time:"+(endTime-startTime));
            //logger.error("send http get error use url: "+url+"error :"+e.getMessage());
           // logger.error("send http get error use url: "+url+"error :"+"cost time:"+(endTime-startTime),e);
            logger.error("send http get error "+url,e);
            return "400";
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result.toString();
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @modificationHistory.
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {


        try {
            if(!StringUtil.isBlank(param)){

                if(url.indexOf("?")>0){
                    url+= "&"+param;
                }else{
                    url+= "?"+param;
                }
            }
            return UrlRead(url);
        } catch (Exception e) {
            return "400";
        }
    }
    /**
     * 向指定URL发送GET方法的请求
     *
     * @modificationHistory.
     * @param url
     *            发送请求的URL
     * @param url
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url) {
        StringBuffer result = new StringBuffer("");
        BufferedReader in = null;
        Long startTime = System.currentTimeMillis();
        try {
            return UrlRead(url);
        } catch (Exception e) {
            return "400";
        }
    }
    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer("");
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl
                    .openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-type", "text/html");
            
            conn.setRequestProperty("Accept-Encoding", "identity");
            
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数
            out.write(param);
            // flush输出流的缓冲
            out.flush();
            out.close();
            // 定义BufferedReader输入流来读取URL的响应
            if (conn.getResponseCode() != 200) {
                return "400";
            }
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            conn.disconnect();
        } catch (Exception e) {
            // //System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
            return "400";
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

    /**
     * 多个参数post
     * 
     * @author syf
     * @creationDate. 2015年7月14日 下午1:49:17
     * @param surl 参数
     * @param params 参数
     * @return String
     * @throws MalformedURLException 异常
     * @throws IOException 抛出IO异常
     * @throws UnsupportedEncodingException 编码异常
     */
    public static String sendPost(String surl, String[] params)
            throws MalformedURLException, IOException,
            UnsupportedEncodingException {
        URL url = new URL(surl);
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
        OutputStreamWriter outputstream = new OutputStreamWriter(
                connection.getOutputStream(), "UTF-8");
        StringBuffer param = new StringBuffer("");
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                param.append(params[i]);
            }
            outputstream.write(param.toString()); // post的关键所在！
        }
        // remember to clean up
        outputstream.flush();
        outputstream.close();
        // 一旦发送成功，用以下方法就可以得到服务器的回应：
        String sCurrentLine = "";
        StringBuffer sTotalString = new StringBuffer("");
        InputStream l_urlStream = connection.getInputStream();
        // 传说中的三层包装阿！
        InputStreamReader reader = new InputStreamReader(l_urlStream);
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(
                l_urlStream));
        while ((sCurrentLine = l_reader.readLine()) != null) {
            sTotalString.append(sCurrentLine + "\r\n");
        }
        reader.close();
        l_reader.close();
        l_urlStream.close();
        // //System.out.println(sTotalString);
        return sTotalString.toString();
    }
    

    /**
     * 执行一个HTTP POST请求，返回请求响应的HTML
     * 
     * @param url
     *            请求的URL地址
     * @param params
     *            请求的查询参数,可以为null
     * @param charset
     *            字符集
     * @param pretty
     *            是否美化
     * @return 返回请求响应的HTML
     */
    public static String sendPost(String urlStr, Map<String, String> params,
            String charset, boolean pretty)throws Exception {
    	String paramStr = MapUtils.join(params, "&");
    	 byte[] paramData = paramStr.getBytes();
         URL url = new URL(urlStr);
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
         
         InputStream inputStream = conn.getInputStream();
         Integer responseCode = null;
         try{
             responseCode =  conn.getResponseCode();
         }catch(IOException e){
             throw new InterfaceException(MessageUtil.getMessage("E2000009"), url.toString(), paramStr, e);//接口无返回值! 
         }
         if (responseCode.equals(200)) {
             ByteBuffer byteBuffer =  parseInputStream(conn.getInputStream());
             return byteBuffer.toString();
         } else {
             ByteBuffer buff = HttpRequest.parseInputStream(conn.getErrorStream());
             logger.error("http request error. " + new String(buff.array(), HttpRequest.ENCODE_UTF8));
         }
         return null;
         
         
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
     * 描述:获取 post 请求的 byte[] 数组
     * <pre>
     * 举例：
     * </pre>
     * @param request
     * @return
     * @throws IOException
     */
    public static byte[] getRequestPostBytes(HttpServletRequest request)
            throws IOException {
        int contentLength = request.getContentLength();
        if(contentLength<0){
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength;) {

            int readlen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }

    /**
     * 描述:获取 post 请求内容
     * <pre>
     * 举例：
     * </pre>
     * @param request
     * @return
     * @throws IOException
     */
    public static String getRequestPostStr(HttpServletRequest request)
            throws IOException {
        byte buffer[] = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }
        return new String(buffer, charEncoding);
    }
   

}

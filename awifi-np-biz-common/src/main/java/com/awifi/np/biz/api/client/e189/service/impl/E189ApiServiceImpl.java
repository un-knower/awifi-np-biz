/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年8月7日 下午2:54:53
* 创建作者：周颖
* 文件名称：E189ApiServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.e189.service.impl;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.e189.service.E189ApiService;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.InterfaceException;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.ErrorUtil;
import com.awifi.np.biz.common.util.FormatUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;

@Service("e189ApiService")
public class E189ApiServiceImpl implements E189ApiService {
    
    /** 日志 */
    private static Log logger = LogFactory.getLog(E189ApiServiceImpl.class);
    
    /**
     * 获取生成二维码的uuid 二维码图片存储内容，失效时间为5分钟
     * @param params 请求参数
     * @return uuid
     * @author 周颖  
     * @date 2017年8月3日 下午5:24:19
     */
    public String getQRUUID(String params){
        String url = SysConfigUtil.getParamValue("esurfing_getuuid_url");
        ByteBuffer byteBuffer = HttpRequest.sendPostRequest(url,params,null);
        Map<String,Object> result = FormatUtil.formatEsurfingByteBuffer(url,params,byteBuffer);
        return (String) result.get("uuid");
    }
    
    /**
     * 获取二维码
     * @param params 入参
     * @return 结果
     * @author 周颖  
     * @throws Exception 
     * @date 2017年8月4日 上午9:20:38
     */
    @SuppressWarnings("unchecked")
    public ByteBuffer getQRCode(String params) throws Exception{
        String url = SysConfigUtil.getParamValue("esurfing_getQRCode_url");
        ByteBuffer byteBuffer = HttpRequest.sendGetRequest(url, params);
        
        if(byteBuffer == null){
            throw new InterfaceException(MessageUtil.getMessage("E2000009"), url, params);//接口无返回值！
        }
        String interfaceReturnValue = new String(byteBuffer.array(), "UTF-8");
        if(interfaceReturnValue.indexOf("result") != -1){
            Map<String, Object> resultMap = JsonUtil.fromJson(interfaceReturnValue, Map.class);
            if(resultMap == null){//未得到结果
                throw new InterfaceException(MessageUtil.getMessage("E2000010"), url, params, interfaceReturnValue);//接口返回值转map后为空！
            }
            String message = (String)resultMap.get("message");
            throw new InterfaceException(message, url, params, interfaceReturnValue);
        }
        return byteBuffer;
    }
    
    /**
     * 获取accessToken
     * @param params 入参
     * @return 结果
     * @author 周颖  
     * @date 2017年8月4日 下午3:44:39
     */
    @SuppressWarnings("unchecked")
    public String getAccessToken(String params){
        String url = SysConfigUtil.getParamValue("esurfing_getaccesstoken_url");
        ByteBuffer byteBuffer = HttpRequest.sendPostRequest(url,params,null);
        if(byteBuffer == null){
            throw new InterfaceException(MessageUtil.getMessage("E2000009"), url, params);//接口无返回值! 
        }
        String returnMessage = null;
        try {
            returnMessage = new String(byteBuffer.array(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            ErrorUtil.printException(e, logger);
            throw new BizException("E2000019", "UnsupportedEncoding");//未知异常
        }
        Map<String, Object> resultMap = JsonUtil.fromJson(returnMessage,Map.class);
        if(resultMap == null || resultMap.isEmpty()){//未得到结果
            throw new InterfaceException(MessageUtil.getMessage("E2000010"), url, params);//接口返回值转map后为空！
        }
        String result = CastUtil.toString(resultMap.get("result"));
        if(result.equals("-1")){//如果登陆失败 返回空
            return StringUtils.EMPTY;
        }else if(result.equals("0")){//如果登陆成功，返回accessToken
            return (String) resultMap.get("accessToken");
        }else{//其余抛异常
            String msg = (String) resultMap.get("msg");
            throw new InterfaceException(result, msg, url, params, returnMessage);
        }
    }
    
    /**
     * 通过accessToken返回手机号
     * @param params 参数
     * @return 手机号
     * @author 周颖  
     * @date 2017年8月4日 上午9:34:51
     */
    public String getCellphone(String params){
        String url = SysConfigUtil.getParamValue("esurfing_getcellphone_url");
        ByteBuffer byteBuffer = HttpRequest.sendPostRequest(url,params,null);
        Map<String,Object> result = FormatUtil.formatEsurfingByteBuffer(url,params,byteBuffer);
        return (String) result.get("mobileName");
    }
}

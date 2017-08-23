/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年8月7日 下午2:43:39
* 创建作者：周颖
* 文件名称：QRCodeController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.auth.qrcode.controller;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awifi.np.biz.api.client.auth.util.AuthClient;
import com.awifi.np.biz.api.client.e189.util.E189Client;
import com.awifi.np.biz.common.base.constants.Constants;
import com.awifi.np.biz.common.base.constants.RedisConstants;
import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.base.controller.BaseController;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.InterfaceException;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.userauth.util.UserAuthUtil;
import com.awifi.np.biz.common.util.Base64Encoder;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.ErrorUtil;
import com.awifi.np.biz.common.util.HMACSHA1Util;
import com.awifi.np.biz.common.util.IPUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.common.util.xxtea.ByteFormat;
import com.awifi.np.biz.common.util.xxtea.XXTeaUtil;

@Controller
@RequestMapping("/pagesrv")
@SuppressWarnings("unchecked")
public class QRCodeController extends BaseController {

    /**
     * 生成二维码接口
     * @param params 入参
     * @param request 请求
     * @return 结果
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年8月7日 下午2:36:14
     */
    @ResponseBody
    @RequestMapping("/qrcode")
    public Map<String,Object> getQRCode(@RequestParam(value="params",required=true)String params,HttpServletRequest request) throws Exception{
        ValidUtil.valid("json格式参数params", params, "required");
        Map<String,Object> paramsMap = JsonUtil.fromJson(params, Map.class);//请求参数 json格式
        //入参
        String logId = (String) paramsMap.get("logId");//日志id，允许为空
        Long merchantId = CastUtil.toLong(paramsMap.get("merchantId"));//商户id，不允许为空，数字
        String deviceId = (String) paramsMap.get("deviceId");//设备id，不允许为空
        String nasName = (String) paramsMap.get("nasName");//NAS设备名称，允许为空
        String devMac = (String) paramsMap.get("devMac");//设备MAC，允许为空，正则规则：^[0-9A-F]{12}$
        String ssid = (String) paramsMap.get("ssid");//SSID，允许为空
        String userMac = (String) paramsMap.get("userMac");//用户MAC，不允许为空，正则规则：^[0-9A-F]{12}$
        String userIp= (String) paramsMap.get("userIp");//用户ip，允许为空
        String platform = (String) paramsMap.get("platform");//省分平台-前缀，允许为空
        //校验入参
        ValidUtil.valid("商户id[merchantId]", merchantId, "required");//商户id
        ValidUtil.valid("设备id[deviceId]", deviceId, "required");//设备id
        ValidUtil.valid("用户MAC[userMac]", userMac, "{'required':true, 'regex':'"+RegexConstants.MAC_PATTERN+"'}");//用户mac地址
        if(StringUtils.isNotBlank(devMac)){
            ValidUtil.valid("设备MAC[devMac]", devMac, "{'regex':'"+RegexConstants.MAC_PATTERN+"'}");//设备mac地址
        }
        
        //1 获取uuid
        String appId = SysConfigUtil.getParamValue("esurfing_appid");//天翼分配的第三方id
        String clientType = "10010";//客户端类型 web 二级分类（使用二级分类）
        String format = StringUtils.EMPTY;//目前仅支持json格式，redirec用于重定向接口的显示说明
        String version = StringUtils.EMPTY;//调用的接口版本号
        String userAgent = request.getHeader("User-Agent");//请求头里面的userAgent
        String plainText = "clientAgent="+userAgent;
        String appSecret = SysConfigUtil.getParamValue("esurfing_appsecret");//秘钥
        //paras = XXTea((a=value1&b=value2&…),appSecret) 除appId、clientType、format、version、sign
        String paras = XXTeaUtil.encrypt(plainText,ByteFormat.toHex(appSecret.getBytes()));
        //签名算法 sign=HMAC-SHA1(appId+clientType+format+version+paras, appSecret)
        String sign = HMACSHA1Util.getSignature(appId+clientType+format+version+paras, appSecret).toUpperCase();
        Map<String,String> e189ParamsMap = new HashMap<String, String>();
        e189ParamsMap.put("appId", appId);
        e189ParamsMap.put("clientType", clientType);
        e189ParamsMap.put("format", format);
        e189ParamsMap.put("version", version);
        e189ParamsMap.put("paras", paras);
        e189ParamsMap.put("sign", sign);
        String e189Params = HttpRequest.getParams(e189ParamsMap);//整理入参
        String uuid = E189Client.getQRUUID(e189Params);//获取uuid
        
        //2 获取二维码图片
        plainText = "uuid="+uuid;//需要加密的参数
        paras = XXTeaUtil.encrypt(plainText, ByteFormat.toHex(appSecret.getBytes()));//XXTea加密
        sign = HMACSHA1Util.getSignature(appId+clientType+format+version+paras, appSecret).toUpperCase();//生成新的签名
        //e189ParamsMap.put("uuid", uuid);
        e189ParamsMap.put("paras", paras);
        e189ParamsMap.put("sign", sign);
        e189Params = HttpRequest.getParams(e189ParamsMap);
        ByteBuffer byteBuffer = E189Client.getQRCode(e189Params);//获取二维码图片
        String qrCode =  Base64Encoder.encode(byteBuffer.array());//base64加密
        //logger.debug(qrCode);
        
        //3 关键参数缓存redis
        String key = RedisConstants.QRCODE + UUID.randomUUID().toString().replace("-", "");//生成redis key np_biz_qrcode_32位小写uuid
        Map<String,Object> redisValue = new HashMap<String, Object>();
        redisValue.put("logId", logId);//日志id
        redisValue.put("merchantId", merchantId);//商户id
        redisValue.put("deviceId", deviceId);//设备id
        redisValue.put("nasName", nasName);//NAS设备名称
        redisValue.put("devMac", devMac);//设备MAC
        redisValue.put("ssid", ssid);//SSID
        redisValue.put("userMac", userMac);//用户MAC
        redisValue.put("userIp", userIp);//用户ip
        redisValue.put("platform", platform);//省分平台-前缀
        redisValue.put("uuid", uuid);//uuid
        RedisUtil.set(key, JsonUtil.toJson(redisValue), RedisConstants.QRCODE_TIME);//保存redis
        
        //4 返回结果
        Map<String,String> resultMap = new HashMap<String, String>();
        resultMap.put("redisKey", key);//接口返回uuid
        resultMap.put("qrCode", "data:image/jpg;base64,"+qrCode);//base64编码的二维码
        
        return this.successMsg(resultMap);
    }
    
    /**
     * 轮循接口
     * @param params 参数
     * @param request 请求
     * @return 结果
     * @throws Exception 异常
     * @author 周颖  
     * @date 2017年8月7日 下午4:44:27
     */
    @ResponseBody
    @RequestMapping("/qrcode/heartbeat")
    public Map<String,Object> heartbeat(@RequestParam(value="params",required=true)String params,HttpServletRequest request) throws Exception{
        ValidUtil.valid("json格式参数params", params, "required");
        Map<String,Object> paramsMap = JsonUtil.fromJson(params, Map.class);//请求参数 json格式
        String redisKey = (String) paramsMap.get("redisKey");
        ValidUtil.valid("redis缓存key", redisKey, "required");
        String redisValue = RedisUtil.get(redisKey);
        Map<String,Object> data = new HashMap<String, Object>();
        if(StringUtils.isBlank(redisValue)){//如果为空 redisKey已失效，返回成功信息，将状态[status]设置为0[redis缓存已失效]，流程结束
            data.put("status", 0);//0代表redis缓存已失效、1代表认证中、2代表认证成功 3 认证失败
            return this.successMsg(data);//返回结果 结束流程
        }
        Map<String,Object> redisValueMap = JsonUtil.fromJson(redisValue, Map.class);//解析redisvalue
        if(redisValueMap == null){
            data.put("status", 0);//0代表redis缓存已失效、1代表认证中、2代表认证成功 3 认证失败
            return this.successMsg(data);
        }
        String uuid = (String) redisValueMap.get("uuid");//redis获取uuid
        String appId = SysConfigUtil.getParamValue("esurfing_appid");//获取天翼第三方id
        String clientType = "10010";//客户端类型 web 二级分类（使用二级分类）
        String format = StringUtils.EMPTY;//目前仅支持json格式，redirec用于重定向接口的显示说明
        String version = StringUtils.EMPTY;//调用的接口版本号
        String plainText = "uuid="+uuid;//需要加密的内容
        String appSecret = SysConfigUtil.getParamValue("esurfing_appsecret");//获取天翼第三方秘钥
        String paras = XXTeaUtil.encrypt(plainText,ByteFormat.toHex(appSecret.getBytes()));//paras = XXTea((a=value1&b=value2&…),appSecret)
        String sign = HMACSHA1Util.getSignature(appId+clientType+format+version+paras, appSecret).toUpperCase();//签名算法 sign=HMAC-SHA1(appId+clientType+format+version+paras, appSecret)
        Map<String,String> e189ParamsMap = new HashMap<String, String>();
        //e189ParamsMap.put("uuid", uuid);
        e189ParamsMap.put("appId", appId);
        e189ParamsMap.put("clientType", clientType);
        e189ParamsMap.put("format", format);
        e189ParamsMap.put("version", version);
        e189ParamsMap.put("paras", paras);
        e189ParamsMap.put("sign", sign);
        String e189Params = HttpRequest.getParams(e189ParamsMap);//整理接口入参
        String accessToken = E189Client.getAccessToken(e189Params);//获取accessToken 如果接口返回登陆失败则为空
        if(StringUtils.isBlank(accessToken)){//如果为空 标识认证中
            data.put("status", 1);//0代表redis缓存已失效、1代表认证中、2代表认证成功 3 认证失败
            return this.successMsg(data);
        }
        e189ParamsMap = new HashMap<String, String>();
        e189ParamsMap.put("accessToken", accessToken);
        e189ParamsMap.put("clientId", appId);
        e189Params = HttpRequest.getParams(e189ParamsMap);
        String cellphone = E189Client.getCellphone(e189Params);//获取手机号
        if(StringUtils.isBlank(cellphone)){//如果手机号为空，返回错误信息[E0000002]；
            throw new BizException("E0000002", MessageUtil.getMessage("E0000002","手机号"));//{0}不允许为空!
        }
        
        String authMsg = auth(redisValueMap,data,cellphone,request);
        return this.successMsg(data, authMsg);
    }
    
    /**
     * 接入认证放行
     * @param redisValueMap redis保存的参数
     * @param data 返回值
     * @param cellphone 手机号
     * @param request 请求
     * @return authMsg
     * @author 周颖  
     * @date 2017年8月10日 上午9:20:46
     */
    private String auth(Map<String,Object> redisValueMap,Map<String,Object> data,String cellphone,HttpServletRequest request){
        String logId = (String)redisValueMap.get("logId");//日志id，允许为空
        //Long merchantId = CastUtil.toLong(redisValueMap.get("merchantId"));//商户id，不允许为空，数字
        String deviceId = (String)redisValueMap.get("deviceId");//设备id，不允许为空
        String nasName = (String)redisValueMap.get("nasName");//NAS设备名称，允许为空
        //String devMac = (String)redisValueMap.get("devMac");//设备MAC，允许为空，正则规则：^[0-9A-F]{12}$
        String ssid = (String)redisValueMap.get("ssid");//SSID，允许为空
        String userMac = (String)redisValueMap.get("userMac");//用户MAC，不允许为空，正则规则：^[0-9A-F]{12}$
        String userIp= (String)redisValueMap.get("userIp");//用户ip，允许为空
        String platform = (String)redisValueMap.get("platform");//省分平台-前缀，允许为空
        
        String userAgent = request.getHeader("User-Agent");//请求头里面的userAgent
        String publicUserIp = IPUtil.getIpAddr(request);//用户公网IP,type=2时必填
        String publicUserPort = IPUtil.getRemotePort(request);//用户公网端口,type=2时且version>=v1.0以上必填
        
        Map<String,String> authMap = new HashMap<String, String>();
        authMap.put("logid", logId);//日志ID
        authMap.put("deviceid", deviceId);//设备ID
        authMap.put("usermac", userMac);//用户MAC
        authMap.put("platform", Constants.NP);//平台名称,toe:商户平台、mws:微站平台、msp:园区平台、portal:页面服务
        authMap.put("authtype", "platform");//认证类型,sms:验证码认证、account:账号密码认证、authed:免认证、green:无感知认证、irv:IVR认证、platform:平台认证、temp:临时放行
        authMap.put("publicuserip", publicUserIp);//用户真实IP,当平台请求时必填（ToE/微站等）
        authMap.put("publicuserport", publicUserPort);//用户真实端口,当平台请求时必填（ToE/微站等）
        authMap.put("mobilephone", cellphone);//手机号：sms、authed、green、ivr认证时必填
        authMap.put("tracetype", "phone");//溯源类型：platform认证必填，[phone|username|passport|identity]
        authMap.put("tracevalue", cellphone);//溯源值：platform认证必填
        authMap.put("userip", userIp);//用户IP：NAS类设备认证必填
        authMap.put("nasname", nasName);//NAS设备名称：NAS类设备认证必填
        authMap.put("ssid", ssid);//无线热点名称（需中文转义）
        Map<String, Object> authResultMap = null;
        String authMsg = null;
        try{//捕捉认证接口异常  返回成功
            authResultMap = AuthClient.auth(authMap, platform, userAgent);//请求认证放行接口
        }catch(InterfaceException e){
            ErrorUtil.printException(e, logger);
            data.put("status", 3);//0代表redis缓存已失效、1代表认证中、2代表认证成功 3 认证失败
            authMsg = e.getMessage();
            return authMsg;
        }
        UserAuthUtil.putToCache(deviceId, cellphone, userMac, Constants.QRCODE, userAgent);//认证记录缓存
        data.put("status", 2);//0代表redis缓存已失效、1代表认证中、2代表认证成功 3 认证失败
        String token = (String)authResultMap.get("data");//胖AP放行token 一并返回
        if(StringUtils.isNotBlank(token)){
            data.put("token", token);//胖AP放行token号
        }
        return null;
    }
}

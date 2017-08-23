package com.awifi.np.biz.timebuysrv.third.access.service;

import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.timebuysrv.util.StringUtil;
import com.awifi.np.biz.timebuysrv.web.module.time.model.WifiRecord;
import com.awifi.np.biz.timebuysrv.web.module.time.service.WifiRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.awifi.np.biz.api.client.auth.util.AuthClient;
import com.awifi.np.biz.common.exception.InterfaceException;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.timebuysrv.third.access.bean.AuthLoginParam;
import com.awifi.np.biz.timebuysrv.third.access.bean.AuthResult;
import com.awifi.np.biz.timebuysrv.web.util.ConfigUtil;
import com.awifi.np.biz.timebuysrv.web.util.HttpRequestUtil;

import java.util.Map;

import javax.annotation.Resource;

/**
 * 接入认证类
 * 
 * @author 张智威 2017年4月14日 下午1:42:16
 */
@Service
public class AccessAuthService {

    /** * 日志打印 . */
    private static Logger logger = LoggerFactory.getLogger(AccessAuthService.class);

    /*
     * @Resource private LogUtilService LogUtil;
     */

    /**
     * WifiRecordService
     */
    @Resource
    private WifiRecordService wifiRecordService;

    /**
     * 测试类
     * 
     * @param args
     *            String[]
     * @author 张智威
     * @date 2017年4月14日 下午1:41:58
     */
    public static void main(String[] args) {
        // StringBuffer param = new StringBuffer();
        // String userName = "13958173965";
        // String version = "1.0.7";
        // String globalKey = "mspauth";
        // String devId =
        // "FatAP_31_20160405b52922b8-5559-4785-886a-c7cdd490b585";
        // String usermac = "B4B676787CBA";
        // param.append("username=").append(userName).append("&version=").append(version).append("&globalKey=")
        // .append(globalKey).append("&globalValue=").append(userName).append("&password=").append("123456")
        // .append("&deviceId=").append(devId).append("&terMac=").append(usermac).append("&callback=")
        // .append("dadfasdf");
        // //
        // System.out.println("http://auth.51awifi.com/auth/accountauth.htm?"+param.toString());
        // /*
        // * String httpresult = HttpRequestUtil.sendGet(
        // * "http://auth.51awifi.com/auth/accountauth.htm", param.toString(),
        // * request); if (!httpresult.equals("400")) {
        // *
        // * }
        // */
    }

    /**
     * get WifiRecord.
     * 
     * @param param
     *            AuthLoginParam
     * @return WifiRecord
     * @author 尤小平
     * @date 2017年5月12日 下午4:12:11
     */
    private WifiRecord getWifiRecord(AuthLoginParam param) {
        WifiRecord record = new WifiRecord();
        if (param.getUserId() != null && param.getUserId() > 0) {
            record.setUserId(param.getUserId());
        }
        if (param.getMerchantId() != null && param.getMerchantId() > 0) {
            record.setMerchantId(param.getMerchantId());
        }
        if (StringUtil.isNotBlank(param.getUsername())) {
            record.setTelphone(param.getUsername());
        }
        if (StringUtil.isNotBlank(param.getDeviceId())) {
            record.setDeviceId(param.getDeviceId());
            if (param.getDeviceId().indexOf("AP") > 0) {
                record.setDevType("AP");
            } else {
                record.setDevType("AC");
            }
        }

        return record;
    }

    /**
     * 接入认证
     * 
     * @param authLoginParam
     *            AuthLoginParam
     * @return AuthResult
     * @throws Exception
     *             异常
     * @author 张智威
     * @date 2017年5月8日 下午8:27:55
     */
    public AuthResult authLogin(AuthLoginParam authLoginParam) throws Exception {
        StringBuffer param = new StringBuffer();
        param.append("logid=").append(authLoginParam.getLogId())//日志ID
                .append("&deviceid=").append(authLoginParam.getDeviceId())//设备ID
                .append("&usermac=").append(authLoginParam.getUsermac())//用户MAC
                .append("&platform=msp")//平台名称,toe:商户平台、mws:微站平台、msp:园区平台、portal:页面服务
                .append("&authtype=platform")//认证类型,sms:验证码认证、account:账号密码认证、authed:免认证、green:无感知认证、irv:IVR认证、platform:平台认证、temp:临时放行
                
                
                .append("&publicuserip=").append(authLoginParam.getPublicuserip())//用户真实IP,当平台请求时必填（ToE/微站等）
                .append("&publicuserport=").append(80)//用户真实端口,当平台请求时必填（ToE/微站等）
                .append("&mobilephone=").append(authLoginParam.getUsername()) 
                .append("&tracetype=phone")//手机号：sms、authed、green、ivr认证时必填
                .append("&tracevalue=").append(authLoginParam.getUsername())//溯源类型：platform认证必填，[phone|username|passport|identi
                .append("&username=").append(authLoginParam.getUsername())
                .append("&userip=" + authLoginParam.getPublicuserip())//用户IP：NAS类设备认证必填
                .append("&password=" + authLoginParam.getPassword());
        
    
        
        
        /*logger.info(ConfigUtil.getConfig("access.account.auth.url") + " param:" + param.toString(),
                "send request to access.account.auth.url start:");*/
        logger.info("begin auth login");
        String httpresult = this.sendAuthLoginGet(authLoginParam, param.toString());
        /*
         * HttpRequestUtil.sendGet(ConfigUtil.getConfig(
         * "access.account.auth.url"), param.toString(),
         * authLoginParam.getPublicuserip(),
         * authLoginParam.getPublicuserport());
         */

        // logger.info("send request to access.account.auth.url return msg:" +
        // httpresult);

        if (httpresult.equals("400")) {
            // logger.error("access.auth.accountauth http fail: " +
            // param.toString());

            throw new InterfaceException("认证接口网络失败:" + httpresult,
                    ConfigUtil.getConfig("access.account.auth.url") + param);

            /*
             * LogUtil.track(serviceCode, 321, param.toString(),
             * "access.auth.accountauth.return: " + httpresult, "");
             */

        } else {
            //创建record
            WifiRecord record = getWifiRecord(authLoginParam);

            // int startIndex = httpresult.indexOf("{");
            // httpresult = (String) httpresult.substring(startIndex);
            AuthResult authResult = JSON.parseObject(httpresult, AuthResult.class);
            if ("0".equals(authResult.getResultCode())) {
                record.setToken(authResult.getData());
                wifiRecordService.save(record);
                return authResult;
            } else {
                record.setErrorInfo(authResult.getMessage());
                wifiRecordService.save(record);
                logger.error("access.auth.accountauth resultCode is not 0: " + param.toString()+JsonUtil.toJson(authResult));
                // 如果不为1 说明有错误
                throw new InterfaceException("放通接口返回" + authResult.getMessage(),
                        ConfigUtil.getConfig("access.account.auth.url"), param.toString());
            }
        }
    }

    /**
     * 踢人下线接口
     * 
     * @param usermac
     *            usermac
     * @return AuthResult
     * @author 张智威
     * @throws Exception 
     * @date 2017年5月9日 上午9:58:16
     */
    public Map<String,Object> kickuser(String usermac, String kickLevel) throws Exception {
        logger.info("usermac: " + usermac + ", kickLevel=" + kickLevel);
        StringBuffer param = new StringBuffer();

        if(StringUtil.isBlank(kickLevel) || (!kickLevel.equals("1") && !kickLevel.equals("2"))) {
            kickLevel="0";
        }

        // param.append("usermac=").append(usermac.toUpperCase()).append("kicklevel=0");
        // String httpresult = sendKickUserOff(param.toString());
        Map<String,Object> authResult = sendKickUserOff(usermac,Integer.valueOf(kickLevel));
        // AuthResult authResult = JSON.parseObject(httpresult,
        // AuthResult.class);
        if ("0".equals(authResult.get("resultCode"))) {
            return authResult;
        }
        logger.error("access.auth.accountauth data fail: " + param.toString() + " httpresult:"
                + JsonUtil.toJson(authResult));
        throw new InterfaceException("踢下线接口返回" + authResult.get("message"),
                SysConfigUtil.getParamValue("auth_kickuser_url"), param.toString());
    }

    /**
     * for testing.
     *
     * @param authLoginParam
     *            AuthLoginParam
     * @param param
     *            String
     * @return String
     * @author 尤小平
     * @date 2017年5月16日 下午8:35:03
     */
    protected String sendAuthLoginGet(AuthLoginParam authLoginParam, String param) {
        return HttpRequestUtil.sendGet(ConfigUtil.getConfig("access.account.auth.url"), param,
                authLoginParam.getPublicuserip(), authLoginParam.getPublicuserport());
    }

    /**
     * for testing.
     * 
     * @param userMac String
     * @param kickLevel int
     * @return Map<String,Object>
     * @author 尤小平
     * @throws Exception 
     * @date 2017年5月17日 上午9:51:41
     */
    protected Map<String,Object> sendKickUserOff(String userMac, int kickLevel) throws Exception {
        return AuthClient.kick(userMac, kickLevel);
    }

    /**
     * for testing only.
     * 
     * @param wifiRecordService
     *            WifiRecordService
     * @author 尤小平
     * @date 2017年5月12日 下午3:57:48
     */
    public void setWifiRecordService(WifiRecordService wifiRecordService) {
        this.wifiRecordService = wifiRecordService;
    }
}

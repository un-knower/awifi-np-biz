/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月13日 下午7:07:42
* 创建作者：许小满
* 文件名称：AuthApiServiceImpl.java
* 版本：  v1.0
* 功能：接口认证相关业务层接口实现类
* 修改记录：
*/
package com.awifi.np.biz.api.client.auth.service.impl;

import java.nio.ByteBuffer;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.auth.http.util.AuthHttpRequest;
import com.awifi.np.biz.api.client.auth.service.AuthApiService;
import com.awifi.np.biz.common.base.service.BaseService;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.redis.util.RedisPubUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.common.util.FormatUtil;

@Service("authApiService")
public class AuthApiServiceImpl extends BaseService implements AuthApiService {
    
    /**
     * 认证放行接口
     * @param parameterMap 接口参数
     * @param platform 省分平台-前缀
     * @param userAgent 请求头里面的userAgent
     * @return 接口结果
     * @author 许小满  
     * @date 2017年5月13日 下午7:22:31
     */
    public Map<String, Object> auth(Map<String, String> parameterMap, String platform, String userAgent){
        String interfaceUrl = this.getUrl(platform, "phoneAuth", "auth_authlogin_url");
        String interfaceParam = HttpRequest.getParams(parameterMap);//接口参数
        ByteBuffer byteBuffer = HttpRequest.sendGetRequest(interfaceUrl, interfaceParam);//发送get请求
        return FormatUtil.formatAuthByteBuffer(interfaceUrl, interfaceParam, byteBuffer);
    }
    
    /**
     * 
     * 获取url,支持省分平台
     * @param platform 省分平台-前缀
     * @param moduleKey 省分平台-功能key
     * @param defaultKey SysConfig中设置的默认key
     * @return 手机号认证url
     * @author 许小满  
     * @date 2016年9月8日 下午5:18:36
     */
    private String getUrl(String platform, String moduleKey, String defaultKey){
        if(StringUtils.isBlank(platform)){//platform为空时，采用默认url
            return SysConfigUtil.getParamValue(defaultKey);
        }
        String redisKey = "PUB.platform." + platform + "." + moduleKey;
        String url = RedisPubUtil.get(redisKey);
        if(StringUtils.isBlank(url)){
            logger.debug("提示：redis中未匹配到对应的省分url，采用默认url.");
            url = SysConfigUtil.getParamValue(defaultKey);
        }
        return url;
    }
    
    
    /**
     * 踢人下线接口
     * @param userMac 用户mac
     * @param kickLevel 踢下线等级 0:只踢下线
     *   1：踢下线并将用户设置为免认证用户
     *   2：踢下线并将用户设置为新用户
     * @return json
     * @author 张智威  
     * @date 2017年6月1日 下午6:46:19
     */
    public Map<String, Object> kick(String userMac,int kickLevel){
        String url = SysConfigUtil.getParamValue("auth_kickuser_url");//获取数据中心行业信息接口地址
        String param = "usermac="+userMac+"&kicklevel="+kickLevel;
        return AuthHttpRequest.sendGetRequest(url, param);
    }
}

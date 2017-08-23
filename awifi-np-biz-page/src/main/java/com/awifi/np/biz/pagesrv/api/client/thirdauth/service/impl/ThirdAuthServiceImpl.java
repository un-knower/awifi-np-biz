package com.awifi.np.biz.pagesrv.api.client.thirdauth.service.impl;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.common.base.service.BaseService;
import com.awifi.np.biz.common.exception.InterfaceException;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.pagesrv.api.client.thirdauth.service.ThirdAuthService;

/**   
 * @Description:  第三方认证-业务层实现类
 * @Title: ThirdAuthServiceImpl.java 
 * @Package com.awifi.toe.inerface.client.thirdauth.service.impl 
 * @author 许小满 
 * @date 2016年10月28日 下午5:57:38
 * @version V1.0   
 */
@Service("thirdAuthService")
public class ThirdAuthServiceImpl extends BaseService implements ThirdAuthService {

    /**
     * 静态用户名认证
     * @param interfaceUrl 接口地址
     * @param userName 用户名
     * @param password 密码
     * @return 接口结果
     * @throws Exception 异常
     * @author 许小满  
     * @date 2016年10月28日 下午6:05:28
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> staticUserAuth(String interfaceUrl, String userName, String password) throws Exception{
        Map<String, String> parameterMap = new HashMap<String, String>();//参数
        parameterMap.put("userName", userName);//用户名
        parameterMap.put("password", password);//密码
        String interfaceParam = HttpRequest.getParams(parameterMap);//接口参数
        
        ByteBuffer byteBuffer = HttpRequest.sendGetRequest(interfaceUrl, interfaceParam);
        if(byteBuffer == null){
            throw new InterfaceException("接口无返回值！", interfaceUrl, interfaceParam);//接口无返回值！
        }
        String interfaceReturnValue = new String(byteBuffer.array(), "UTF-8");//接口返回值
        logger.debug("提示：interfaceReturnValue= " + interfaceReturnValue);
        if(StringUtils.isBlank(interfaceReturnValue)){
            throw new InterfaceException("接口返回值不允许为空！", interfaceUrl, interfaceParam, interfaceReturnValue);//接口返回值不允许为空！
        }
        logger.debug("提示：接口返回值= " + interfaceReturnValue);
        Map<String, Object> resultMap = JsonUtil.fromJson(interfaceReturnValue, Map.class);
        if(resultMap == null){//未得到结果
            throw new InterfaceException("接口返回值转map后为空！", interfaceUrl, interfaceParam, interfaceReturnValue);//接口返回值转map后为空！
        }
        return resultMap;
    }
    
}

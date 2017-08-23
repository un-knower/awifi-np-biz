package com.awifi.np.biz.pagesrv.api.client.thirdauth.util;

import java.util.Map;

import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.pagesrv.api.client.thirdauth.service.ThirdAuthService;

/**   
 * @Description:  第三方认证-工具类
 * @Title: ThirdAuthClient.java 
 * @Package com.awifi.toe.inerface.client.thirdauth.util 
 * @author 许小满 
 * @date 2016年10月28日 下午5:58:27
 * @version V1.0   
 */
public class ThirdAuthClient {

    /** 认证  */
    private static ThirdAuthService thirdAuthService;
    
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
    public static Map<String, Object> staticUserAuth(String interfaceUrl, String userName, String password) throws Exception{
        return getThirdAuthService().staticUserAuth(interfaceUrl, userName, password);
    }
    
    /**
     * 获取 ThirdAuthService bean
     * @return authService
     * @author 许小满  
     * @date 2016年10月28日 下午6:07:36
     */
    private static ThirdAuthService getThirdAuthService(){
        if(thirdAuthService == null){
            thirdAuthService = (ThirdAuthService) BeanUtil.getBean("thirdAuthService");
        }
        return thirdAuthService;
    }
    
}

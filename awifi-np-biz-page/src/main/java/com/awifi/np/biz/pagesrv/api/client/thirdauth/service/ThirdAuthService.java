package com.awifi.np.biz.pagesrv.api.client.thirdauth.service;

import java.util.Map;

/**   
 * @Description:  第三方认证-业务层接口
 * @Title: ThirdAuthService.java 
 * @Package com.awifi.toe.inerface.client.thirdauth.service 
 * @author 许小满 
 * @date 2016年10月28日 下午5:57:05
 * @version V1.0   
 */
public interface ThirdAuthService {

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
    Map<String, Object> staticUserAuth(String interfaceUrl, String userName, String password) throws Exception;
    
}

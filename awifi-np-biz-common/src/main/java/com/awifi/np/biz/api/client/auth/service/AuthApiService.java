/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月13日 下午7:07:21
* 创建作者：许小满
* 文件名称：AuthApiService.java
* 版本：  v1.0
* 功能：接口认证相关业务层接口
* 修改记录：
*/
package com.awifi.np.biz.api.client.auth.service;

import java.util.Map;

public interface AuthApiService {

    /**
     * 认证放行接口
     * @param parameterMap 接口参数
     * @param platform 省分平台-前缀
     * @param userAgent 请求头里面的userAgent
     * @return 接口结果
     * @author 许小满  
     * @date 2017年5月13日 下午7:22:31
     */
    Map<String, Object> auth(Map<String, String> parameterMap, String platform, String userAgent);
    
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
    Map<String, Object> kick(String userMac,int kickLevel);
    
}

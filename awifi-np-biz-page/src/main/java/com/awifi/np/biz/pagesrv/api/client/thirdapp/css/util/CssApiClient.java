/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月13日 上午11:55:38
* 创建作者：许小满
* 文件名称：CssApiClient.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.api.client.thirdapp.css.util;

import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.pagesrv.api.client.thirdapp.css.service.CssApiService;

public class CssApiClient {

    /** css api接口业务层 */
    private static CssApiService cssApiService;
    
    /**
     * 获取css api接口
     * @param redisKey 缓存key
     * @param interfaceUrl 接口路径
     * @return css内容
     * @throws Exception 异常
     * @author 许小满  
     * @date 2017年5月13日 下午12:08:00
     */
    public static String getCss(String redisKey, String interfaceUrl) throws Exception {
        return getCssApiService().getCss(redisKey, interfaceUrl);
    }
    
    /**
     * 获取css api接口实例
     * @return 背景图api接口实例
     * @author 许小满  
     * @date 2017年5月13日 上午11:59:13
     */
    private static CssApiService getCssApiService(){
        if(cssApiService == null){
            cssApiService = (CssApiService)BeanUtil.getBean("cssApiService");
        }
        return cssApiService;
    }
}

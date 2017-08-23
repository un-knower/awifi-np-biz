/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月13日 上午10:42:55
* 创建作者：许小满
* 文件名称：BgApiClient.java
* 版本：  v1.0
* 功能：背景图 api接口 工具类
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.api.client.thirdapp.bgpic.util;

import java.nio.ByteBuffer;

import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.pagesrv.api.client.thirdapp.bgpic.service.BgPicApiService;

public class BgPicApiClient {

    /** 背景图api接口业务层 */
    private static BgPicApiService bgPicApiService;
    
    /**
     * 获取背景图接口
     * @param redisKey 缓存key
     * @param interfaceUrl 接口路径
     * @return 图片内容
     * @throws Exception 异常
     * @author 许小满  
     * @date 2017年5月13日 上午11:06:11
     */
    public static ByteBuffer getBgPic(String redisKey, String interfaceUrl) throws Exception{
        return getBgPicApiService().getBgPic(redisKey, interfaceUrl);
    }
    
    /**
     * 获取背景图api接口实例
     * @return 背景图api接口实例
     * @author 许小满  
     * @date 2017年5月13日 上午11:10:51
     */
    private static BgPicApiService getBgPicApiService(){
        if(bgPicApiService == null){
            bgPicApiService = (BgPicApiService)BeanUtil.getBean("bgPicApiService");
        }
        return bgPicApiService;
    }
    
}

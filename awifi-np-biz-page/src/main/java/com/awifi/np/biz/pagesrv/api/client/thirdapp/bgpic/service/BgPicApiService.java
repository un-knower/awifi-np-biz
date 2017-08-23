/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月13日 上午10:41:32
* 创建作者：许小满
* 文件名称：BgPicApiService.java
* 版本：  v1.0
* 功能：背景图 api接口 业务层接口
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.api.client.thirdapp.bgpic.service;

import java.nio.ByteBuffer;

public interface BgPicApiService {

    /**
     * 获取背景图接口
     * @param redisKey 缓存key
     * @param interfaceUrl 接口路径
     * @return 图片内容
     * @throws Exception 异常
     * @author 许小满  
     * @date 2017年5月13日 上午11:06:11
     */
    ByteBuffer getBgPic(String redisKey, String interfaceUrl) throws Exception;
    
}

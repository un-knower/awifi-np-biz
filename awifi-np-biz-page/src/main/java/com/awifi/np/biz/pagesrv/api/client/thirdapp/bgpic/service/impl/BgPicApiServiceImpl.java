/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月13日 上午10:42:21
* 创建作者：许小满
* 文件名称：BgPicApiServiceImpl.java
* 版本：  v1.0
* 功能：背景图 api接口 业务层接口实现类
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.api.client.thirdapp.bgpic.service.impl;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.common.base.service.BaseService;
import com.awifi.np.biz.common.exception.InterfaceException;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.pagesrv.api.client.thirdapp.bgpic.service.BgPicApiService;

@Service("bgPicApiService")
public class BgPicApiServiceImpl extends BaseService implements BgPicApiService {

    /**
     * 获取背景图接口
     * @param redisKey 缓存key
     * @param interfaceUrl 接口路径
     * @return 图片内容
     * @throws Exception 异常
     * @author 许小满  
     * @date 2017年5月13日 上午11:06:11
     */
    @SuppressWarnings("unchecked")
    public ByteBuffer getBgPic(String redisKey, String interfaceUrl) throws Exception {
        long beginTime = System.currentTimeMillis();
        Map<String, String> parameterMap = new HashMap<String, String>();//参数map
        parameterMap.put("params", redisKey);
        String interfaceParam = HttpRequest.getParams(parameterMap);//接口参数
        ByteBuffer byteBuffer = HttpRequest.sendPostRequest(interfaceUrl, interfaceParam, null);
        if(byteBuffer == null){
            logger.debug("提示：返回byteBuffer为空，此处显示默认图片！");
            throw new InterfaceException(MessageUtil.getMessage("E2000009"), interfaceUrl, interfaceParam);//接口无返回值！
        }
        String interfaceReturnValue = new String(byteBuffer.array(), "UTF-8");
        if(interfaceReturnValue.indexOf("FAIL") != -1){
            logger.debug("提示：返回错误，此处显示默认图片！returnMessage="+interfaceReturnValue);
            Map<String, Object> resultMap = JsonUtil.fromJson(interfaceReturnValue, Map.class);
            if(resultMap == null){//未得到结果
                throw new InterfaceException(MessageUtil.getMessage("E2000010"), interfaceUrl, interfaceParam, interfaceReturnValue);//接口返回值转map后为空！
            }
            if( !"OK".equalsIgnoreCase((String) resultMap.get("result"))){
                String message = (String)resultMap.get("message");
                throw new InterfaceException(message, interfaceUrl, interfaceParam, interfaceReturnValue);
            }
        } 
        logger.debug("提示：营业厅接口（获取背景图源代码） 共花费了 " + (System.currentTimeMillis() - beginTime) + " ms.");
        return byteBuffer;
    }
    
}

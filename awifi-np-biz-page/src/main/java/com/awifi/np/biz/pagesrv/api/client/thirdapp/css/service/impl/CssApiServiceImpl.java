/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月13日 上午11:56:59
* 创建作者：许小满
* 文件名称：CssApiServiceImpl.java
* 版本：  v1.0
* 功能：获取css api 接口实现类
* 修改记录：
*/
package com.awifi.np.biz.pagesrv.api.client.thirdapp.css.service.impl;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.common.base.service.BaseService;
import com.awifi.np.biz.common.exception.InterfaceException;
import com.awifi.np.biz.common.http.HttpRequest;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.pagesrv.api.client.thirdapp.css.service.CssApiService;

@Service("cssApiService")
public class CssApiServiceImpl extends BaseService implements CssApiService {

    /**
     * 获取css api接口
     * @param redisKey 缓存key
     * @param interfaceUrl 接口路径
     * @return css内容
     * @throws Exception 异常
     * @author 许小满  
     * @date 2017年5月13日 下午12:08:00
     */
    @SuppressWarnings("unchecked")
    public String getCss(String redisKey, String interfaceUrl) throws Exception {
        long beginTime = System.currentTimeMillis();
        Map<String, String> parameterMap = new HashMap<String, String>();//参数map
        parameterMap.put("params", redisKey);
        String interfaceParam = HttpRequest.getParams(parameterMap);//接口参数
        ByteBuffer byteBuffer = HttpRequest.sendGetRequest(interfaceUrl, interfaceParam);
        if(byteBuffer == null){
            throw new InterfaceException(MessageUtil.getMessage("E2000009"), interfaceUrl, interfaceParam);//接口无返回值！
        }
        String interfaceReturnValue = new String(byteBuffer.array(), "UTF-8");
        Map<String, Object> resultMap = JsonUtil.fromJson(interfaceReturnValue, Map.class);
        if(resultMap == null){//未得到结果
            throw new InterfaceException(MessageUtil.getMessage("E2000010"), interfaceUrl, interfaceParam, interfaceReturnValue);//接口返回值转map后为空！
        }
        if( !"OK".equalsIgnoreCase((String) resultMap.get("result"))){
            String message = (String)resultMap.get("message");
            throw new InterfaceException(message, interfaceUrl, interfaceParam, interfaceReturnValue);
        }
        logger.debug("提示：营业厅接口（获取CSS源代码） 共花费了 " + (System.currentTimeMillis() - beginTime) + " ms.");
        return (String) resultMap.get("data");
    }
    
}

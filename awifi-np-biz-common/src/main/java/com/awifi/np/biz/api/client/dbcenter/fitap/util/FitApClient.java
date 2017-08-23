/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月24日 下午8:19:20
* 创建作者：范涌涛
* 文件名称：FitApClient.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.fitap.util;


import java.util.Map;

import com.awifi.np.biz.api.client.dbcenter.fitap.service.FitApApiService;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.util.BeanUtil;

@SuppressWarnings({"rawtypes"})
public class FitApClient {
    
    /**
     * 服务类
     */
    private static FitApApiService fitApApiService;
    
    /**
     * 获取bean
     * @return fitap bean
     * @author 范涌涛  
     * @date 2017年6月4日 下午2:41:06
     */
    public static FitApApiService getFitApApiService() {
        if (fitApApiService == null) {
            fitApApiService = (FitApApiService) BeanUtil.getBean("fitApApiService");
        }
        return fitApApiService;
    }
    
    /**
     * 分页查询chinanet瘦AP
     * @param reqParam 查询条件
     * @return 查询结果
     * @throws Exception 异常
     * @author 范涌涛  
     * @date 2017年6月4日 下午2:40:22
     */
    public static Page queryChinanetDevInfoListByParam(Map<String,Object> reqParam) throws Exception{
        return getFitApApiService().queryChinanetDevInfoList(reqParam);
    }
}

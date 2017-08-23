/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月24日 下午8:17:07
* 创建作者：范涌涛
* 文件名称：FitApApiService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.api.client.dbcenter.fitap.service;

import java.util.Map;
import com.awifi.np.biz.common.base.model.Page;

@SuppressWarnings({ "rawtypes" })
public interface FitApApiService {

    /**
     * 条件查询chinanet设备记录数
     * @param reqParam 查询参数
     * @return 记录数
     * @throws Exception 异常
     * @author 范涌涛
     * @date 2017年6月13日 上午10:57:17
     */
    Integer queryChinaNetDevInfoCount(Map<String, Object> reqParam) throws Exception;

    /**
     * 条件查询chinanet设备列表
     * @param reqParam 查询参数
     * @return page
     * @throws Exception 异常
     * @author 范涌涛
     * @date 2017年6月13日 上午10:57:24
     */
    Page queryChinanetDevInfoList(Map<String, Object> reqParam) throws Exception;
}

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月24日 下午7:36:20
* 创建作者：范涌涛
* 文件名称：FitApService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.fitap.service;

import com.awifi.np.biz.common.base.model.Page;

@SuppressWarnings({"rawtypes"})
public interface FitApService {

    void queryListByParam(String reqParam,Page page) throws Exception;
}

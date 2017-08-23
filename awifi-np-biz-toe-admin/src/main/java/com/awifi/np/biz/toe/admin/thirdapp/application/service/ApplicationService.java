/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月16日 下午2:04:56
* 创建作者：许小满
* 文件名称：ApplicationService.java
* 版本：  v1.0
* 功能：第三方应用--业务层接口
* 修改记录：
*/
package com.awifi.np.biz.toe.admin.thirdapp.application.service;

import com.awifi.np.biz.toe.admin.thirdapp.application.model.Application;

public interface ApplicationService {

    /**
     * 通过appid获取应用表信息
     * @param appid 应用ID
     * @return Application
     * @author kangyanxiang 
     * @date Nov 17, 2016 9:41:55 AM
     */
    Application getByAppid(String appid);
    
}

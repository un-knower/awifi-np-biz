/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月16日 下午2:05:15
* 创建作者：许小满
* 文件名称：ApplicationServiceImpl.java
* 版本：  v1.0
* 功能：第三方应用--业务层接口实现类
* 修改记录：
*/
/**
 * 
 */
package com.awifi.np.biz.toe.admin.thirdapp.application.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.common.base.service.BaseService;
import com.awifi.np.biz.toe.admin.thirdapp.application.dao.ApplicationDao;
import com.awifi.np.biz.toe.admin.thirdapp.application.model.Application;
import com.awifi.np.biz.toe.admin.thirdapp.application.service.ApplicationService;

@Service("applicationService")
public class ApplicationServiceImpl extends BaseService implements ApplicationService {

    /** 应用dao */
    @Resource(name="applicationDao")
    private ApplicationDao applicationDao;
    
    /**
     * 通过appid获取应用表信息
     * @param appid 应用ID
     * @return Application
     * @author kangyanxiang 
     * @date Nov 17, 2016 9:41:55 AM
     */
    public Application getByAppid(String appid) {
        return applicationDao.getByAppid(appid);
    }
}

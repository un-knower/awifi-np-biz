package com.awifi.np.admin.platform.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.awifi.np.admin.platform.dao.PlatformDao;
import com.awifi.np.admin.platform.service.PlatformService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月12日 下午2:47:37
 * 创建作者：周颖
 * 文件名称：PlatformServiceImpl.java
 * 版本：  v1.0
 * 功能：平台业务实现类
 * 修改记录：
 */
@Service("platformService")
public class PlatformServiceImpl implements PlatformService {
    
    /**平台dao*/
    @Resource(name = "platformDao")
    private PlatformDao platformDao;

    /**
     * 根据平台id获取平台key
     * @param appId 平台id
     * @return 平台key
     * @author 周颖  
     * @date 2017年1月12日 下午2:59:11
     */
    public String getKeyByAppId(String appId){
        if(StringUtils.isBlank(appId)){//平台id为空
            return StringUtils.EMPTY;//直接返回空
        }
        return platformDao.getKeyByAppId(appId);//返回平台key
    }
}
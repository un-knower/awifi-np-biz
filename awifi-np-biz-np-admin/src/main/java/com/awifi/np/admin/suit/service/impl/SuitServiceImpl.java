package com.awifi.np.admin.suit.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.awifi.np.admin.suit.dao.SuitDao;
import com.awifi.np.admin.suit.service.SuitService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月11日 下午4:55:58
 * 创建作者：周颖
 * 文件名称：SuitServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Service("suitService")
public class SuitServiceImpl implements SuitService {

    /**套dao*/
    @Resource(name = "suitDao")
    private SuitDao suitDao;
    
    /**
     * 根据角色获取套码
     * @param roleIds 角色
     * @return 套码
     * @author 周颖  
     * @date 2017年1月11日 下午8:01:44
     */
    public String getCodeById(Long[] roleIds){
        if(roleIds == null || roleIds.length == 0){
            return StringUtils.EMPTY;
        }
        return suitDao.getCodeById(roleIds);
    }
    
    /**
     * 获取当前登陆账号所有套码
     * @param userId 用户id
     * @return 套码
     * @author 周颖  
     * @date 2017年5月9日 下午2:18:24
     */
    public List<Map<String, Object>> getSuitCodesByUserId(Long userId){
        return suitDao.getSuitCodesByUserId(userId);
    }
}
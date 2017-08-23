package com.awifi.np.biz.common.system.sysconfig.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.common.base.constants.RedisConstants;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.system.sysconfig.dao.SysConfigDao;
import com.awifi.np.biz.common.system.sysconfig.model.SysConfig;
import com.awifi.np.biz.common.system.sysconfig.service.SysConfigService;
import com.awifi.np.biz.common.util.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:Jan 9, 2017 8:28:59 PM
 * 创建作者：亢燕翔
 * 文件名称：SysConfigService.java
 * 版本：  v1.0
 * 功能：  系统参数业务层 
 * 修改记录：
 */
@Service("sysConfigService")
public class SysConfigServiceImpl implements SysConfigService{

	/** 系统参数持久层  */
    @Resource(name = "sysConfigDao")
    private SysConfigDao sysConfigDao;
	
	/**
	 * 通过key获取value
	 * @param paramKey 参数键
	 * @return 参数值
	 * @author 亢燕翔  
	 * @date Jan 10, 2017 8:44:50 AM
	 */
    public String getParamValue(String paramKey){
        /** 缓存中获取  */
        String value = RedisUtil.get(RedisConstants.SYSTEM_CONFIG+paramKey); //从redis中获取对应的value
        if(StringUtils.isNotBlank(value)){      //如果value存在则直接返回
            return value;
        }
        
        /** 数据库中获取  */
        value = sysConfigDao.getParamValue(paramKey);//不存在则查询数据库
        if(StringUtils.isNotBlank(value)){
            RedisUtil.set(RedisConstants.SYSTEM_CONFIG+paramKey, value, RedisConstants.SYSTEM_CONFIG_TIME);//放入缓存中
        }
        return value;
    }
	
    /**
     * 分页查询接口
     * @param page 分页对象
     * @param keywords 关键字查询条件
     * @author 许小满  
     * @date 2017年3月24日 下午7:24:22
     */
    public void getListByParam(Page<SysConfig> page, String keywords){
        int count = sysConfigDao.getCountByParam(keywords);//获取总数
        page.setTotalRecord(count);
        if(count <= 0){//小于等于0 直接返回
            return;
        }
        List<SysConfig> sysConfigList = sysConfigDao.getListByParam(keywords, page.getBegin(), page.getPageSize());
        page.setRecords(sysConfigList);
    }
    
    /**
     * 系统参数配置添加
     * @param aliasName 别名
     * @param paramKey 参数键
     * @param paramValue 参数值
     * @param remark 备注
     * @author 许小满  
     * @date 2017年5月18日 上午12:03:59
     */
    public void add(String aliasName, String paramKey, String paramValue, String remark){
        if(isParamKeyExists(paramKey, null)){
            throw new BizException("E2000060", MessageUtil.getMessage("E2000060", new Object[]{"键", "paramKey", paramKey}));//{0}（{1}[{2}]）已存在!
        }
        sysConfigDao.add(aliasName, paramKey, paramValue, remark);
    }
    
    /**
     * 系统参数配置修改
     * @param id 主键id
     * @param aliasName 别名
     * @param paramKey 参数键
     * @param paramValue 参数值
     * @param remark 备注
     * @author 许小满  
     * @date 2017年5月18日 上午12:03:59
     */
    public void update(Long id, String aliasName, String paramKey, String paramValue, String remark){
        if(isParamKeyExists(paramKey, id)){
            throw new BizException("E2000060", MessageUtil.getMessage("E2000060", new Object[]{"键", "paramKey", paramKey}));//{0}（{1}[{2}]）已存在!
        }
        sysConfigDao.update(id, aliasName, paramKey, paramValue, remark);
    }
    
    /**
     * 系统参数配置修改删除
     * @param id 主键id
     * @author 许小满  
     * @date 2017年5月18日 上午12:24:53
     */
    public void delete(Long id){
        sysConfigDao.delete(id);;
    }
    
    /**
     * 通过id获取配置信息
     * @param id 主键id
     * @return 配置信息
     * @author 许小满  
     * @date 2017年5月18日 上午12:23:45
     */
    public SysConfig getById(Long id){
        SysConfig sysConfig = sysConfigDao.getById(id);
        sysConfig.setId(id);//主键id
        return sysConfig;
    }
    
    /**
     * 判断key是否已存在
     * @param paramKey 参数键
     * @param id 需屏蔽的记录id
     * @return true 已存在、false 不存在
     * @author 许小满  
     * @date 2017年5月18日 上午12:10:37
     */
    private boolean isParamKeyExists(String paramKey, Long id){
        Long num = sysConfigDao.getNumByParamKey(paramKey, id);
        return num > 0;
    }
    
}

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年4月14日 下午2:17:04
 * 创建作者：尤小平
 * 文件名称：UserBaseServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.timebuysrv.user.service.impl;

import com.awifi.np.biz.api.client.dbcenter.user.model.PubUser;
import com.awifi.np.biz.api.client.dbcenter.user.util.UserBaseClient;
import com.awifi.np.biz.common.ms.util.StringUtil;
import com.awifi.np.biz.common.util.DateUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.timebuysrv.user.dao.SysUserDao;
import com.awifi.np.biz.timebuysrv.user.model.SysUser;
import com.awifi.np.biz.timebuysrv.user.service.UserBaseService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service(value = "userBaseService")
public class UserBaseServiceImpl implements UserBaseService {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * SysUserDao
     */
    @Resource
    private SysUserDao sysUserDao;

    /**
     * 根据userId获取用户信息.
     *
     * @param userId userId
     * @return PubUser
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月5日 下午3:01:46
     */
    @Override
    public PubUser getByUseId(Long userId) throws Exception {
        logger.debug("userId=" + userId);

        PubUser pubUser = UserBaseClient.queryByUserId(userId);
        if (pubUser != null && pubUser.getBirthday() != null) {
            pubUser.setBirthdayStr(DateUtil.formatDate(pubUser.getBirthday().getTime()));
        }

        logger.debug("PubUser=" + JsonUtil.toJson(pubUser));

        return pubUser;
    }

    /**
     * 添加用户基础信息.
     *
     * @param pubUser PubUser
     * @return userId
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月17日 下午3:40:39
     */
    @Override
    public Long add(PubUser pubUser) throws Exception {
        logger.debug("pubUser:" + JsonUtil.toJson(pubUser));

        Long userId = UserBaseClient.add(pubUser);
        pubUser.setId(userId);
        logger.debug("userId:" + userId);

        SysUser sysUser = this.getSysUserByUserId(userId);
        logger.debug("local sysUser:" + JsonUtil.toJson(sysUser));

        if (sysUser != null) {
            sysUser = getSysUserFromPubUser(sysUser, pubUser);
            logger.debug("update local sysUser:" + JsonUtil.toJson(sysUser));
            int result = this.updateSysUser(sysUser);
            logger.debug("update local sysUser result:" + result);
        } else {
            sysUser = new SysUser();
            logger.debug("add local sysUser");
            sysUser = getSysUserFromPubUser(sysUser, pubUser);
            int result = this.addSysUser(sysUser);
            logger.debug("add local sysUser result:" + result);
        }
        return userId;
    }
    
    
    
    /**
     * 根据userid更新用户基础信息.
     *
     * @param pubUser PubUser
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月17日 下午3:41:24
     */
    @Override
    public void update(PubUser pubUser) throws Exception {
        logger.debug("pubUser:" + JsonUtil.toJson(pubUser));

        UserBaseClient.update(pubUser);

        SysUser sysUser = this.getSysUserByUserId(pubUser.getId());

        if (sysUser != null) {
            logger.debug("update local sysUser:" + JsonUtil.toJson(sysUser));
            sysUser = getSysUserFromPubUser(sysUser, pubUser);
            int result = this.updateSysUser(sysUser);
            logger.debug("update local sysUser result:" + result);
        } else {
            logger.debug("local sysUser is null");
        }
    }

    /**
     * 本地库添加用户信息.
     *
     * @param sysUser SysUser
     * @return 成功条数
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月17日 下午3:44:18
     */
    public int addSysUser(SysUser sysUser) throws Exception {
        return sysUserDao.insert(sysUser);
    }

    /**
     * 根据userid获取本地库用户信息.
     *
     * @param userId userId
     * @return SysUser
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月17日 下午3:45:35
     */
    private SysUser getSysUserByUserId(Long userId) throws Exception {
        return sysUserDao.selectByUserId(userId);
    }

    /**
     * 根据userid更新本地库用户信息.
     *
     * @param sysUser SysUser
     * @return 更新成功条数
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年4月17日 下午3:44:26
     */
    private int updateSysUser(SysUser sysUser) throws Exception {
        return sysUserDao.updateByUserId(sysUser);
    }

    /**
     * PubUser转SysUser
     *
     * @param sysUser SysUser
     * @param pubUser PubUser
     * @return SysUser
     * @author 尤小平
     * @date 2017年4月17日 下午3:46:08
     */
    public SysUser getSysUserFromPubUser(SysUser sysUser, PubUser pubUser) {
        if (pubUser != null) {
            if (pubUser.getId() != null && pubUser.getId() > 0) {
                sysUser.setOutid(pubUser.getId());
            }
            sysUser.setFace(pubUser.getFaceInfo());
            sysUser.setNkname(pubUser.getUserNick());
            if(pubUser.getSex() != null) {
                if(pubUser.getSex().equals("男")){
                    pubUser.setSex("1");
                }
                if(pubUser.getSex().equals("女")){
                    pubUser.setSex("2");
                }
                sysUser.setSex(Integer.valueOf(pubUser.getSex()));
            }
            sysUser.setBirth(pubUser.getBirthday());
            sysUser.setAddress(pubUser.getAddress());
            sysUser.setTelno(pubUser.getTelphone());
            sysUser.setUsername(pubUser.getUserNick());
            if(StringUtil.isBlank(sysUser.getUsername())){
                sysUser.setUsername(pubUser.getTelphone());
            }
        }
        return sysUser;
    }
    
    /**
     * for testing only.
     * 
     * @param sysUserDao SysUserDao
     * @author 尤小平  
     * @date 2017年5月5日 下午5:08:52
     */
    protected void setSysUserDao(SysUserDao sysUserDao) {
        this.sysUserDao = sysUserDao;
    }
}

package com.awifi.np.biz.devsrv.fitappro.service;

import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubEntity;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.security.user.model.SessionUser;

public interface FitapproService {
    /**
     * 依据条件查询项目型瘦ap类型设备
     * @param page page
     * @param entity 查询条件
     * @param sessionUser 用户信息
     * @return page
     * @throws Exception 异常
     */
    Page<CenterPubEntity> getFitapproList(Page<CenterPubEntity> page,CenterPubEntity entity,SessionUser sessionUser) throws Exception;
    /**
     * 依据id集合删除项目型瘦ap设备
     * @param ids id集合
     * @throws Exception 异常
     */
    void deleteFitapproList(String[] ids)throws Exception;
    /**
     * 根据id 查找 项目型瘦ap设备
     * @param id 
     * @return CenterPubEntity 
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年6月14日 上午10:28:05
     */
    CenterPubEntity getFitapproById(String id)throws Exception;

}

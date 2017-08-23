package com.awifi.np.biz.devsrv.fatap.service;

import java.util.Map;

import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubEntity;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.security.user.model.SessionUser;

@SuppressWarnings("rawtypes")
public interface FatApService {


    /**
     * 定制终端信息查询
     * @param centerPubEntity 查询条件
     * @param createDateB 开始时间
     * @param createDateE 结束时间
     * @param page page
     * @param user 用户信息
     * @return page
     * @throws Exception 异常
     */
    Page getEmsDevBasePassedFatShowList(CenterPubEntity centerPubEntity, String createDateB, String createDateE, Page page,SessionUser user)throws Exception;
    
    /**
     * 定制终端入库信息查询
     * @param centerPubEntity 参数
     * @param createDateB 开始时间
     * @param createDateE 结束时间
     * @param page 分页信息
     * @param user 用户信息
     * @return 分页信息
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年7月20日 上午8:24:02
     */
    Page getEmsDevBaseFatShowList(CenterPubEntity centerPubEntity, String createDateB, String createDateE, Page page,SessionUser user)throws Exception;
    
    /**
     * 根据ids更新定制终端审核情况
     * @param ids id的集合
     * @param status 审核状态
     * @param userName 用户名
     * @param remark 评论
     * @throws Exception 异常
     */
    void updateFlowStsByIds(String[] ids,Integer status ,String userName,String remark) throws Exception;
    
    /**
     * 根据批次号更新定制终端审核情况
     * @param params 参数
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年7月20日 上午8:33:42
     */
    void updateFlowStsByBatch(Map<String, Object> params)throws Exception;
    
    /**
     * 根据批次号删除定制终端
     * @param params 参数
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年7月20日 上午8:36:31
     */
    void deleteEntityByBatch(Map<String, Object> params)throws Exception;
    
    /**
     * 根据ids删除定制终端信息
     * @param ids id的集合
     * @throws Exception 异常
     */
    void deleteAwifiFatAPByIds(String[] ids) throws Exception;

    /**
     * 根据ids更新定制终端信息(除了审核状态)
     * @param entity 更新参数
     * @throws Exception 异常
     */
    void updateFatApById(CenterPubEntity entity)throws Exception;
    /**
     * 根据id查找设备信息
     * @param id 主键
     * @return centerpubentity
     * @throws Exception 异常
     */
    CenterPubEntity queryEntityInfoById(String id)throws Exception;
}

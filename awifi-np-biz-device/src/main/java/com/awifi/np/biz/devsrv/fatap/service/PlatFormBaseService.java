package com.awifi.np.biz.devsrv.fatap.service;

import java.util.Map;

import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubPlatform;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.security.user.model.SessionUser;


public interface PlatFormBaseService {
    
    /**
     * 查询省份平台
     * @param pageNo 页码
     * @param pageSize 记录数
     * @param platformName 平台名称
     * @param sessionUser 用户
     * @return Page
     * @throws Exception 异常
     * @author 伍恰  
     * @date 2017年7月24日 下午9:06:34
     */
    Page<CenterPubPlatform> listPlatForm(Integer pageNo,Integer pageSize,String platformName,SessionUser sessionUser)throws Exception;
    
    /**
     * 根据id查询平台详细信息
     * @param id id
     * @return CenterPubPlatform
     * @throws Exception 异常
     * @author 伍恰  
     * @date 2017年7月24日 下午9:06:34
     */
    Map<String,Object> queryPlatformById(String id)throws Exception;
    
    /**
     * 根据id更新平台
     * @param params 参数
     * @throws Exception 异常
     * @author 伍恰  
     * @date 2017年7月24日 下午9:08:38
     */
    void editPlatForm(Map<String,Object> params)throws Exception;
    
    /**
     * 添加省分平台
     * @param params 参数
     * @throws Exception 异常
     * @author 伍恰  
     * @date 2017年7月24日 下午9:09:34
     */
    void addPlatform(Map<String,Object> params)throws Exception;
    
    /**
     * 根据id删除省分平台信息
     * @param id id 
     * @throws Exception 异常
     * @author 伍恰  
     * @date 2017年7月24日 下午9:10:05
     */
    void deletePlatform(Long id)throws Exception;

}

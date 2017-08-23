package com.awifi.np.biz.api.client.dbcenter.platform.servcie;

import java.util.Map;

public interface PlatFormApiServcie {
    
    /**
     * 根据条件查询平台数量
     * @param params params
     * @return int
     * @throws Exception 异常
     */
    Integer queryPlatformCountByParam(Map<String,Object> params)throws Exception;
    
    /**
     * 根据条件查询平台信息
     * @param params params
     * @return Map
     * @throws Exception exception
     */
    Map<String,Object> queryPlatformListByParam(Map<String,Object> params)throws Exception;
    
    /**
     * 根据id查询平台信息
     * @param param param
     * @return json
     * @throws Exception exception
     */
    Map<String, Object> queryPlatformById(Map<String,Object> param)throws Exception;
    
    /**
     * 根据id更新平台信息
     * @param params params
     * @return Map<String, Object>
     * @throws Exception exception
     */
    Map<String, Object> editPlatForm(Map<String,Object> params)throws Exception;
    
    /**
     * 添加省分平台
     * @param params params
     * @throws Exception exception
     */
    void addPlatForm (Map<String,Object> params)throws Exception;
    
    /**
     * 删除省分平台信息
     * @param id id
     * @throws Exception exception
     */
    void deletePaltform(Long id) throws Exception;


}

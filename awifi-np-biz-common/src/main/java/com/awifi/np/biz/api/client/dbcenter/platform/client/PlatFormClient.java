package com.awifi.np.biz.api.client.dbcenter.platform.client;

import java.util.Map;

import com.awifi.np.biz.api.client.dbcenter.platform.servcie.PlatFormApiServcie;
import com.awifi.np.biz.common.util.BeanUtil;

public class PlatFormClient {
    
    /**
     * 省分平台业务层
     */
    private static PlatFormApiServcie platFormApiServcie;
    
    /**
     * 获得省分平台业务层
     * @return PlatFormApiServcie
     */
    private static PlatFormApiServcie getPlatFormApiServcie(){
        if(platFormApiServcie==null){
            platFormApiServcie=(PlatFormApiServcie) BeanUtil.getBean("platFormApiServcie");
        }
        return platFormApiServcie;
    }
    /**
     * 根据条件查询平台数量
     * @param params params
     * @return count
     * @throws Exception exception
     */
    public static Integer queryPlatformCountByParam(Map<String,Object> params)throws Exception{
        Integer count=getPlatFormApiServcie().queryPlatformCountByParam(params);
        return count;
    }
    
    /**
     * 根据条件查询平台信息
     * @param params params
     * @return json
     * @throws Exception exception
     */
    public static Map<String,Object> queryPlatformListByParam(Map<String,Object> params) throws Exception{
    	Map<String,Object> json= getPlatFormApiServcie().queryPlatformListByParam(params);
        return json;
    }
    
    /**
     * 根据id查询平台信息
     * @param param param
     * @return json
     * @throws Exception exception
     */
    public static Map<String,Object> queryPlatformById(Map<String,Object> param) throws Exception{
        Map<String, Object> json=getPlatFormApiServcie().queryPlatformById(param);
        return json;
    }
    
    /**
     * 根据id编辑平台信息
     * @param params params
     * @throws Exception exception
     */
    public static void editPlatForm(Map<String,Object> params)throws Exception{
        getPlatFormApiServcie().editPlatForm(params);
    }
    
    /**
     * 添加平台信息
     * @param params params
     * @throws Exception exception
     */
    public static void addPlatForm(Map<String,Object> params)throws Exception{
        getPlatFormApiServcie().addPlatForm(params);
    }
    
    /**
     * 省分平台删除操作
     * @param ids 省分平台id的数组
     * @throws Exception exception
     */
    public static void deletePaltform(Long id) throws Exception{
        getPlatFormApiServcie().deletePaltform(id);
    }

}

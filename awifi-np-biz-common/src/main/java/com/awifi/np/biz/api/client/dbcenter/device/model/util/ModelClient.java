package com.awifi.np.biz.api.client.dbcenter.device.model.util;

import java.util.Map;

import com.awifi.np.biz.api.client.dbcenter.device.model.service.ModelApiServcie;
import com.awifi.np.biz.common.util.BeanUtil;

public class ModelClient {
    
    /**
     * 型号service
     */
    private static ModelApiServcie modelApiServcie;
    
    /**
     * 获得modelApiServcie
     * @return ModelApiServcie
     */
    public static ModelApiServcie getModelApiServcie(){
        if(modelApiServcie==null){
            modelApiServcie=(ModelApiServcie) BeanUtil.getBean("modelApiServcie");
        }
        return modelApiServcie;
    }
    
    /**
     * 查询型号数量
     * @param params params
     * @return int
     * @throws Exception exception
     */
    public static int queryCountByParam(Map<String, Object> params) throws Exception{
        return getModelApiServcie().queryCountByParam(params);
    }
    
    /**
     * 查询具体型号信息
     * @param params params
     * @return json
     * @throws Exception exception
     */
    public static String queryListByParam(Map<String,Object> params) throws Exception{
        return getModelApiServcie().queryListByParam(params);
    }
}

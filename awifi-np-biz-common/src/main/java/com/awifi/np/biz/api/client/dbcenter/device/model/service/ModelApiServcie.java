package com.awifi.np.biz.api.client.dbcenter.device.model.service;

import java.util.Map;

public interface ModelApiServcie {
    
    /**
     * 查询型号数量
     * @param params params
     * @return int
     * @throws Exception exception
     */
    int queryCountByParam(Map<String,Object> params) throws Exception;
    
    /**
     * 查询型号
     * @param params params
     * @return json
     * @throws Exception exception
     */
    String queryListByParam(Map<String,Object> params)throws Exception;

}

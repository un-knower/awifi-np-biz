package com.awifi.np.biz.devsrv.model.servcie;

import java.util.Map;

import com.awifi.np.biz.common.excel.model.CenterPubModel;

public interface ModelService {

    /**
     * 查询所有型号
     * @return map
     * @throws Exception excpeiton
     */
    Map<String, CenterPubModel> getAllModelMap(String model) throws Exception;
}

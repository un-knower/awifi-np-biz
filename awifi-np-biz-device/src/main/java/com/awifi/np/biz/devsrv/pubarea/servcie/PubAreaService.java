package com.awifi.np.biz.devsrv.pubarea.servcie;

import com.awifi.np.biz.api.client.dbcenter.location.model.CenterPubArea;

public interface PubAreaService {

    /**
     * 根据条件查询地区信息
     * @param areaName 地区名称
     * @param areaType 地区级别
     * @param parentId 地区父id
     * @return centerpubarea
     */
    public CenterPubArea queryByParam(String areaName, String areaType, Long parentId);
    
    /**
     * 查询所有地区
     */
    public void queryAllArea();
}

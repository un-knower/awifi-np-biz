package com.awifi.np.biz.devsrv.pubarea.servcie.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.location.model.CenterPubArea;
import com.awifi.np.biz.api.client.dbcenter.location.util.LocationClient;
import com.awifi.np.biz.common.exception.ApplicationException;
import com.awifi.np.biz.devsrv.pubarea.servcie.PubAreaService;

@Service("pubAreaService")
public class PubAreaServiceImpl implements PubAreaService{
    
 private static final Log logger = LogFactory.getLog(PubAreaServiceImpl.class);
    /**省集合*/
    private List<CenterPubArea> provinceList = new ArrayList<CenterPubArea>();
    /**市集合*/
    private List<CenterPubArea> cityList = new ArrayList<CenterPubArea>();
    /**区县集合*/
    private List<CenterPubArea> countyList = new ArrayList<CenterPubArea>();
    /**
     * 根据条件查询地区信息
     * @param areaName 地区名称
     * @param areaType 地区级别
     * @param parentId 地区父id
     * @return centerpubarea
     */
    @Override
    public void queryAllArea()
    {
        try
        {
            String type;
            Map<String,Object> locationMap;
            Map<Long,Map<String,Object>> allLocations=LocationClient.getAllLocation();
            for(Map.Entry<Long, Map<String,Object>> location:allLocations.entrySet()){
                CenterPubArea area=new CenterPubArea();
                area.setId(location.getKey());
                locationMap=location.getValue();
                type=(String) locationMap.get("type");
                area.setAreaName((String)locationMap.get("name"));
                area.setAreaType(type);
                area.setParentId((Long)locationMap.get("parentId"));
                area.setAreaFullName((String)locationMap.get("fullName"));
                if(type.equals("PROVINCE")){
                    provinceList.add(area);
                }else if(type.equals("CITY")){
                    cityList.add(area);
                }else if(type.equals("COUNTY")){
                    countyList.add(area);
                }
                
            }
        }
        catch (Exception e)
        {
            logger.debug("查询所有的地区出错:" + e.getMessage());
            throw new ApplicationException("地区数据查询失败!");
        }
    }
    /**
     * 查询所有地区
     */
    @Override
    public CenterPubArea queryByParam(String areaName, String areaType, Long parentId) {
        // TODO Auto-generated method stub

        CenterPubArea centerPubArea = null;
        // 根据省的名称查询地区
        if (areaType.equals("PROVINCE"))
        {
            if (provinceList.size() == 0)
            {
                queryAllArea();
            }
            for (CenterPubArea province : provinceList)
            {
                if (province.getAreaName().equals(areaName))
                {
                    centerPubArea = province;
                    break;
                }
            }
        }
        if (parentId == null)
        {
            return centerPubArea;
        }
        if (areaType.equals("CITY"))
        {// 查询省下面是否有存在该市
            if (cityList.size() == 0)
            {
                queryAllArea();
            }
            for (CenterPubArea city : cityList)
            {
                if (city.getAreaName().equals(areaName)
                        && city.getParentId().longValue() == parentId.longValue())
                {
                    centerPubArea = city;
                    break;
                }
            }
        }
        else if (areaType.equals("COUNTY"))
        {// 查询市下面是否存在该区县
            if (countyList.size() == 0)
            {
                queryAllArea();
            }
            for (CenterPubArea county : countyList)
            {
                if (county.getAreaName().equals(areaName)
                        && county.getParentId().longValue() == parentId.longValue())
                {
                    centerPubArea = county;
                    break;
                }
            }
        }
        return centerPubArea;
    
    }
}

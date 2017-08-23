package com.awifi.np.biz.devsrv.entity.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.awifi.np.biz.common.base.constants.RegexConstants;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.util.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.api.client.dbcenter.device.entity.util.EntityClient;
import com.awifi.np.biz.devsrv.entity.service.EntityService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月8日 上午9:08:15
 * 创建作者：亢燕翔
 * 文件名称：EntityServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Service(value = "entityService")
public class EntityServiceImpl implements EntityService{

    /**
     * 编辑设备
     * @param devid 设备id
     * @param bodyParam 请求体参数
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月10日 下午3:25:08
     */
    public void update(String devid, Map<String, Object> bodyParam) throws Exception {
        Long provinceId = CastUtil.toLong(bodyParam.get("provinceId"));
        Long cityId = CastUtil.toLong(bodyParam.get("cityId"));
        Long areaId = CastUtil.toLong(bodyParam.get("areaId"));
        String address = (String) bodyParam.get("address");
        String remark = (String) bodyParam.get("remark");
        Double latitude = CastUtil.toDouble(bodyParam.get("latitude"));//纬度
        Double longitude = CastUtil.toDouble(bodyParam.get("longitude"));//经度

        if(latitude!=null){
            if(!RegexUtil.match(latitude+"",RegexConstants.DEVICE_LATITUDE)){
                throw new ValidException("E2000016", MessageUtil.getMessage("E2000016","纬度[latitude]"));
            }
        }
        if(longitude!=null){
            if(!RegexUtil.match(longitude+"",RegexConstants.DEVICE_LONGITUDE)){
                throw new ValidException("E2000016", MessageUtil.getMessage("E2000016","经度[longitude]"));
            }
        }

        ValidUtil.valid("省id[provinceId]", provinceId, "required");
        ValidUtil.valid("市id[cityId]", cityId, "required");
        ValidUtil.valid("区县id[areaId]", areaId, "required");
        String params = getDbParams(devid,provinceId,cityId,areaId,address,remark,latitude,longitude);
        EntityClient.update(params);

        //清空设备经纬度
        if(latitude==null || longitude == null){//有一个为空，清空经纬度
            Map<String, Object> dbParams = new HashMap<String, Object>();
            dbParams.put("deviceId", devid);
            String param = JsonUtil.toJson(dbParams);
            DeviceClient.editLatitudeLongitude(param);
        }
    }

    /**
     * 封装请求参数（编辑）
     * @param devid 设备id 
     * @param provinceId 省id
     * @param cityId 市id
     * @param areaId 区县id
     * @param address 地址
     * @param remark 备注
     * @param latitude 维度
     * @param longitude 经度
     * @return map
     * @author 亢燕翔  
     * @date 2017年2月10日 下午3:37:01
     */
    private String getDbParams(String devid, Long provinceId, Long cityId, Long areaId, String address, String remark,Double latitude,Double longitude) {
        Map<String, Object> dbParams = new HashMap<String, Object>();
        dbParams.put("deviceId", devid);
        dbParams.put("province", ObjectUtils.defaultIfNull(provinceId, StringUtils.EMPTY));
        dbParams.put("city", ObjectUtils.defaultIfNull(cityId, StringUtils.EMPTY));
        dbParams.put("county", ObjectUtils.defaultIfNull(areaId, StringUtils.EMPTY));
        dbParams.put("address", StringUtils.defaultString(address));
        dbParams.put("remark", StringUtils.defaultString(remark));
        dbParams.put("latiude", latitude);
        dbParams.put("longitude", longitude);
        return JsonUtil.toJson(dbParams);
    }
}

package com.awifi.np.biz.devsrv.fitappro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubEntity;
import com.awifi.np.biz.api.client.dbcenter.fatap.util.DeviceQueryClient;
import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.enums.DevType;
import com.awifi.np.biz.common.enums.Status;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.devsrv.fitappro.service.FitapproService;

@Service(value="fitapproService")
public class FitapproServiceImpl implements FitapproService{

    /**
     * 依据条件查询项目型瘦ap类型设备
     * @param page page
     * @param entity 查询条件
     * @param sessionUser 用户信息
     * @return page
     * @throws Exception 异常
     */
    @Override
    public Page<CenterPubEntity> getFitapproList(Page<CenterPubEntity> page, CenterPubEntity entity,
            SessionUser sessionUser) throws Exception{
        // TODO Auto-generated method stub
        Map<String,Object> params=new HashMap<>();
        if(entity!=null&&entity.getProvince()!=null){
            params.put("province", entity.getProvince());
            if(entity.getCity()!=null){
                params.put("city", entity.getCity());
            }
            if(entity.getCounty()!=null){
                params.put("county", entity.getCounty());
            }
        }else{
            if(sessionUser!=null&&sessionUser.getProvinceId()!=null){
                params.put("province", sessionUser.getProvinceId());
                if(entity.getCity()!=null){
                    params.put("city", sessionUser.getCityId());
                }
                if(entity.getCounty()!=null){
                    params.put("county", sessionUser.getAreaId());
                }
            }
        }
        if(entity!=null&&StringUtils.isNotBlank(entity.getMacAddr())){
            params.put("macAddr", entity.getMacAddr());
        }
        if(StringUtils.isNotBlank(entity.getParEntityName())){
            params.put("parEntityName", entity.getParEntityName());
        }
        params.put("pageNum", page.getPageNo());
        params.put("pageSize", page.getPageSize());
        params.put("status", Status.normal.getValue());//1是正常数据，9是删除数据
        //新增设备类型参数 项目型瘦ap 是 42
        params.put("entityType",String.valueOf(DevType.pFitap.getValue()));
        int total = DeviceQueryClient.queryEntityCountByParam(params);
        page.setTotalRecord(total);
        List<CenterPubEntity> list=DeviceQueryClient.queryEntityInfoListByParam(params);
        page.setRecords(list);
        return page;
    }
    /**
     * 依据id集合删除项目型瘦ap设备
     * @param ids id集合
     * @throws Exception 异常
     */
    @Override
    public void deleteFitapproList(String[] ids) throws Exception {
        // 该处不能使用 根据id删除设备，因为会影响设备相关数据。
        DeviceClient.deleteDeviceByDeviceIds(ids);
    }
    @Override
    public CenterPubEntity getFitapproById(String id) throws Exception {
        // TODO Auto-generated method stub
        return DeviceQueryClient.queryEntityInfoById(id);
    }

}

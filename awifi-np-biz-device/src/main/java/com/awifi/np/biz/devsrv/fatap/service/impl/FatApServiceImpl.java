package com.awifi.np.biz.devsrv.fatap.service.impl;

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
import com.awifi.np.biz.common.enums.FlowSts;
import com.awifi.np.biz.common.enums.SourceType;
import com.awifi.np.biz.common.enums.Status;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.devsrv.fatap.service.FatApService;

@SuppressWarnings({"rawtypes","unchecked"})
@Service(value="fatApService")
public class FatApServiceImpl implements FatApService{

    /**
     * 定制终端信息查询
     * @param centerPubEntity 查询条件
     * @param createDateB 开始时间
     * @param createDateE 结束时间
     * @param page page
     * @param user 用户信息
     * @return page
     * @throws Exception 异常
     */
    @Override
    public Page getEmsDevBasePassedFatShowList(CenterPubEntity centerPubEntity, String createDateB, String createDateE,Page page, SessionUser user) throws Exception {
        Map<String,Object> map=getFatapMap(centerPubEntity, createDateB, createDateE, page, user);
        //map.put("flowSts", FlowSts.reviewGo.getValue());
        
        //得到总记录数
        int count=DeviceQueryClient.queryEntityCountByParam(map);
        page.setTotalRecord(count);
        //得到具体数据
        List<CenterPubEntity> records=DeviceQueryClient.queryEntityInfoListByParam(map);
        page.setRecords(records);
        return page;
    }


    /**
     * 将查询条件转化为map
     * @param centerPubEntity 主体查询条件
     * @param createDateB 开始时间
     * @param createDateE 结束时间
     * @param page page(主要是页码和记录数)
     * @param user 用户
     * @return map
     */
    public Map<String,Object> getFatapMap(CenterPubEntity centerPubEntity, String createDateB, String createDateE, Page page,SessionUser user){
        Map<String,Object> map=getBaseMap();
        if (centerPubEntity != null && centerPubEntity.getProvince() != null) {
            map.put("province", centerPubEntity.getProvince()+"");
            if(centerPubEntity.getCity() != null) {
                map.put("city", centerPubEntity.getCity()+"");
            }
            if(centerPubEntity.getCounty() != null) {
                map.put("county", centerPubEntity.getCounty()+"");
            }
        }else{
            if(user!=null&&user.getProvinceId()!=null){
                map.put("province", user.getProvinceId()+"");
                if(user.getCityId()!=null){
                    map.put("city", user.getCityId()+"");
                }
                if(user.getAreaId()!=null){
                    map.put("county", user.getAreaId()+"");
                }
            }
        }
        if(StringUtils.isNotEmpty(createDateB)){
            map.put("createDateB", createDateB);
        }
        if(StringUtils.isNotEmpty(createDateE)){
            map.put("createDateE", createDateE);
        }
        if(centerPubEntity!= null){
            if(StringUtils.isNotEmpty(centerPubEntity.getBatchNum())){
                map.put("batchNum", centerPubEntity.getBatchNum());
            }
            if(StringUtils.isNotEmpty(centerPubEntity.getMacAddr())){
                map.put("macAddr", centerPubEntity.getMacAddr());
            }
            if(StringUtils.isNoneEmpty(centerPubEntity.getCorporation())){
                map.put("corporation", centerPubEntity.getCorporation());
            }
            if(StringUtils.isNotEmpty(centerPubEntity.getModel())){
                map.put("model", centerPubEntity.getModel());
            }
            if(centerPubEntity.getFlowSts()!=null){
                map.put("flowSts", centerPubEntity.getFlowSts());
            }else{
                map.put("flowSts", FlowSts.reviewBack.getValue()+","+FlowSts.waitReview.getValue());//仅仅查询等待审核和审核驳回
            }
            if(centerPubEntity.getEntityType()!=null){
                map.put("entityType", centerPubEntity.getEntityType());
            }else{
                //定制终端默认类型 31 FAT_AP 32 GPON 33 GPON_W 34 EPON 35 EPON_W 36 二合一  37 三合一 371 LAN融合 372 GPON融合 373 EPON融合 
                StringBuilder entityType = new StringBuilder();
                entityType.append(DevType.fatap.getValue()+",");
                entityType.append(DevType.gpon.getValue()+",");
                entityType.append(DevType.gponw.getValue()+",");
                entityType.append(DevType.epon.getValue()+",");
                entityType.append(DevType.eponw.getValue()+",");
                entityType.append(DevType.twoForOne.getValue()+",");
                entityType.append(DevType.threeForOne.getValue()+",");
                entityType.append(DevType.lanFuse.getValue()+",");
                entityType.append(DevType.gponFuse.getValue()+",");
                entityType.append(DevType.eponFuse.getValue());
                map.put("entityType", entityType);
            }
        }
        map.put("pageNum", page.getPageNo()+"");
        map.put("pageSize", page.getPageSize()+"");
        return map;
    }

    /**
     * 得到查询的最基本的条件
     * @return map
     */
    public Map<String,Object> getBaseMap(){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("importer", "EMS");
        map.put("outTypeId", SourceType.awifi.displayName());
        map.put("status", Status.normal.getValue()+"");
        return map;
    }

    /**
     * 根据ids更新定制终端审核情况
     * @param ids id的集合
     * @param status 审核状态
     * @param userName 用户名
     * @param remark 评论
     * @throws Exception 异常
     */
    @Override
    public void updateFlowStsByIds(String[] ids, Integer status, String userName, String remark) throws Exception {
        // TODO Auto-generated method stub
        Map<String, Object> umap = new HashMap<String, Object>();
        umap.put("ids", ids);
        umap.put("flowSts", status);//审核状态
        umap.put("flowStsBy", userName);//审核人
        umap.put("remarks", remark == null ? "" : remark);
        DeviceClient.updateFlowStsByIds(umap);
    }

    /**
     * 根据ids删除定制终端信息
     * @param ids id的集合
     * @throws Exception 异常
     */
    @Override
    public void deleteAwifiFatAPByIds(String[] ids) throws Exception{
        // TODO Auto-generated method stub
        DeviceClient.deleteEntityByIds(ids);
    }


    /**
     * 根据ids更新定制终端信息(除了审核状态)
     * @param entity 更新参数
     * @throws Exception 异常
     */
    @Override
    public void updateFatApById(CenterPubEntity entity) throws Exception {
        // TODO Auto-generated method stub
        DeviceClient.updateEntity(entity);
    }


    @Override
    public CenterPubEntity queryEntityInfoById(String id) throws Exception {
        // TODO Auto-generated method stub
        return DeviceQueryClient.queryEntityInfoById(id);
    }

    /**
     * 定制终端入库信息查询
     * @param centerPubEntity 参数
     * @param createDateB 开始时间
     * @param createDateE 结束时间
     * @param page 分页信息
     * @param user 用户信息
     * @return 分页信息
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年7月20日 上午8:24:02
     */
    @Override
    public Page getEmsDevBaseFatShowList(CenterPubEntity centerPubEntity,
            String createDateB, String createDateE, Page page, SessionUser user)
            throws Exception {
        Map<String,Object> map=getFatapMap(centerPubEntity, createDateB, createDateE, page, user);
        //得到总记录数
        int count=DeviceQueryClient.queryEntityCountByParamGroup(map);
        page.setTotalRecord(count);
        //得到具体数据
        List<CenterPubEntity> records=DeviceQueryClient.queryEntityListByParamGroup(map);
        page.setRecords(records);
        return page;
    }

    /**
     * 根据批次号更新定制终端审核情况
     * @param params 参数
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年7月20日 上午8:33:42
     */
    @Override
    public void updateFlowStsByBatch(Map<String, Object> params)
            throws Exception {
        DeviceClient.updateByBatch(params);
    }

    /**
     * 根据批次号删除定制终端
     * @param params 参数
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年7月20日 上午8:36:31
     */
    @Override
    public void deleteEntityByBatch(Map<String, Object> params)
            throws Exception {
        DeviceClient.deleteEntityByBatch(params);
    }

}

package com.awifi.np.biz.devsrv.hot.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.api.client.dbcenter.fatap.util.DeviceQueryClient;
import com.awifi.np.biz.common.util.CastUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.devsrv.hot.service.HotService;

@Service("hotService")
public class HotServiceImpl implements HotService{

    @Override
    public void updateHotarea(Map<String, Object> reqMap) throws Exception{
        String id = CastUtil.toString(reqMap.get("id"));
        String hotareaName = CastUtil.toString(reqMap.get("hotareaName"));
        String pvlan = CastUtil.toString(reqMap.get("pvlan"));
        String cvlan = CastUtil.toString(reqMap.get("cvlan"));
        Map<String,Object> map = modifyDBupdateHotareaParams(reqMap);
        Map<String, Object> oldHotInfo = DeviceQueryClient.queryHotareaInfoById(Long.parseLong(id));
        // 热点名称 pvlan cvlan 没变,则相应字段不传到数据中心 update,否则数据中心报错
        if (hotareaName.equals(oldHotInfo.get("hotareaName"))) {
            map.remove("hotareaName");
        }
        if (pvlan.equals(oldHotInfo.get("pvlan"))) {
            map.remove("pvlan");
        }
        if (cvlan.equals(oldHotInfo.get("cvlan"))) {
            map.remove("cvlan");
        }
        DeviceClient.updateHotarea(map);
    }



    private Map<String,Object> modifyDBupdateHotareaParams(Map<String, Object> reqMap) {
        //9个必填项
        String id = CastUtil.toString(reqMap.get("id"));
        Integer province = CastUtil.toInteger(reqMap.get("province"));
        Integer city = CastUtil.toInteger(reqMap.get("city"));
        Integer county = CastUtil.toInteger(reqMap.get("county"));
        String hotareaName = CastUtil.toString(reqMap.get("hotareaName"));
        String acName = CastUtil.toString(reqMap.get("acName"));
        String brasName = CastUtil.toString(reqMap.get("brasName"));
        String pvlan = CastUtil.toString(reqMap.get("pvlan"));
        String cvlan = CastUtil.toString(reqMap.get("cvlan"));
        ValidUtil.valid("热点id", id, "{'required':true}");
        ValidUtil.valid("省份id", province, "{'required':true,'numeric':true}");
        ValidUtil.valid("市id", city, "{'required':true,'numeric':true}");
        ValidUtil.valid("区县id", county, "{'required':true,'numeric':true}");
        ValidUtil.valid("热点hotareaName", hotareaName, "{'required':true}");
        ValidUtil.valid("热点pvlan", pvlan, "{'required':true}");
        ValidUtil.valid("热点cvlan", cvlan, "{'required':true}");
        ValidUtil.valid("热点acName", acName, "{'required':true}");
        ValidUtil.valid("热点brasName", brasName, "{'required':true}");		
        //27 个非必填项
        String hotareaOutId = CastUtil.toString(reqMap.get("hotareaOutId"));
        String hotareaType = CastUtil.toString(reqMap.get("hotareaType"));
        String hotareaDegree = CastUtil.toString(reqMap.get("hotareaDegree"));
        String repairComp = CastUtil.toString(reqMap.get("repairComp"));
        String segment = CastUtil.toString(reqMap.get("segment"));
        String hotarea = CastUtil.toString(reqMap.get("hotarea"));
        String brasIp = CastUtil.toString(reqMap.get("brasIp"));
        String brasShel = CastUtil.toString(reqMap.get("brasShel"));
        String brasSlot = CastUtil.toString(reqMap.get("brasSlot"));
        String ssid = CastUtil.toString(reqMap.get("ssid"));
        String wlanGanfangNum = CastUtil.toString(reqMap.get("wlanGanfangNum"));
        String nas = CastUtil.toString(reqMap.get("nas"));
        String acount = CastUtil.toString(reqMap.get("acount"));
        String brasPortType = CastUtil.toString(reqMap.get("brasPortType"));
        String brasPort = CastUtil.toString(reqMap.get("brasPort"));
        String owner = CastUtil.toString(reqMap.get("owner"));
        String ownerPhone = CastUtil.toString(reqMap.get("ownerPhone"));
        String accessNo = CastUtil.toString(reqMap.get("accessNo"));
        String landmark = CastUtil.toString(reqMap.get("landmark"));
        String intergrator = CastUtil.toString(reqMap.get("intergrator"));
        String remark = CastUtil.toString(reqMap.get("remark"));
        String vlan = CastUtil.toString(reqMap.get("vlan"));
        Long apNum = CastUtil.toLong(reqMap.get("apNum"));
        Long xpos = CastUtil.toLong(reqMap.get("xpos"));
        Long ypos = CastUtil.toLong(reqMap.get("ypos"));
        Long ishasshifen = CastUtil.toLong(reqMap.get("ishasshifen"));
        Long islineshifen = CastUtil.toLong(reqMap.get("islineshifen"));

        Map<String, Object> dbParams = new HashMap<String, Object>();
        //9个必填
        dbParams.put("id", id);
        dbParams.put("province", province);
        dbParams.put("city", city);
        dbParams.put("county", county);
        dbParams.put("hotareaName", hotareaName);
        dbParams.put("acName", acName);
        dbParams.put("brasName", brasName);
        dbParams.put("pvlan", pvlan);
        dbParams.put("cvlan", cvlan);
        //27个非必填
        dbParams.put("hotareaOutId", StringUtils.defaultString(hotareaOutId));
        dbParams.put("hotareaType", StringUtils.defaultString(hotareaType));
        dbParams.put("hotareaDegree", StringUtils.defaultString(hotareaDegree));
        dbParams.put("repairComp", StringUtils.defaultString(repairComp));
        dbParams.put("segment", StringUtils.defaultString(segment));
        dbParams.put("hotarea", StringUtils.defaultString(hotarea));
        dbParams.put("brasIp", StringUtils.defaultString(brasIp));
        dbParams.put("brasShel", StringUtils.defaultString(brasShel));
        dbParams.put("brasSlot", StringUtils.defaultString(brasSlot));
        dbParams.put("ssid", StringUtils.defaultString(ssid));
        dbParams.put("wlanGanfangNum", StringUtils.defaultString(wlanGanfangNum));
        dbParams.put("nas", StringUtils.defaultString(nas));
        dbParams.put("acount", StringUtils.defaultString(acount));
        dbParams.put("brasPortType", StringUtils.defaultString(brasPortType));
        dbParams.put("brasPort", StringUtils.defaultString(brasPort));
        dbParams.put("owner", StringUtils.defaultString(owner));
        dbParams.put("ownerPhone", StringUtils.defaultString(ownerPhone));
        dbParams.put("accessNo", StringUtils.defaultString(accessNo));
        dbParams.put("landmark", StringUtils.defaultString(landmark));
        dbParams.put("intergrator", StringUtils.defaultString(intergrator));
        dbParams.put("remark", StringUtils.defaultString(remark));
        dbParams.put("vlan", StringUtils.defaultString(vlan));
        dbParams.put("apNum", apNum==null? NumberUtils.LONG_ZERO:apNum);
        dbParams.put("xpos", xpos==null? NumberUtils.DOUBLE_ZERO:xpos);
        dbParams.put("ypos", ypos==null? NumberUtils.DOUBLE_ZERO:ypos);
        dbParams.put("ishasshifen", ishasshifen);//修改后不存在传空的情况，不对null进行处理
        dbParams.put("islineshifen", islineshifen);//修改后不存在传空的情况，不对null进行处理
        return dbParams;
    }
}

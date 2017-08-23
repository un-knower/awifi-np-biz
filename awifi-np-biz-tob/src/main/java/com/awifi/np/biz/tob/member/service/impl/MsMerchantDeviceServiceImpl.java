/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月8日 下午2:23:34
* 创建作者：王冬冬
* 文件名称：MsMerchantDeviceServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.tob.member.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.awifi.np.biz.api.client.dbcenter.device.device.model.Device;
import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.tob.member.dao.MsMerchantDeviceDao;
import com.awifi.np.biz.tob.member.service.MsMerchantDeviceService;


@Service("msMerchantDeviceService")
public class MsMerchantDeviceServiceImpl implements MsMerchantDeviceService {

    
    /**
     * 微站商户设备dao
     */
    @Resource(name="msMerchantDeviceDao")
    private MsMerchantDeviceDao msMerchantDeviceDao;
    
    public void updateAntiRobberSwitch(Long merchantId, Byte status) throws Exception {
        if(merchantId==null || status==null){
            return;
        }
        List<Device> deviceList=null;
        JSONObject jsonObject=null;
        int pageNum=1;
        int pageSize=100;
        jsonObject=new JSONObject();
        jsonObject.put("merchantId",merchantId);
        jsonObject.put("merchantQueryType","this");
        jsonObject.put("status",1);
        jsonObject.put("pageNum",pageNum);//页码
        jsonObject.put("pageSize",pageSize);//页数
//        deviceList=DeviceClient.getListByParam(jsonObject.toString());
        do{
            deviceList=DeviceClient.getListByParam(jsonObject.toJSONString());
            if(deviceList==null||deviceList.isEmpty()){
                break;
            }
            List<String> deviceStrList=new ArrayList<String>();
            Map<String,Object> map=null;
            if(deviceList!=null && deviceList.size()>0){
                deviceStrList=new ArrayList<String>();
                for (Device device : deviceList) {
                    if(device.getDeviceId()!=null){
                        deviceStrList.add(device.getDeviceId());
                    }
                }
                if(deviceStrList.size()>0){
                    map=new HashMap<String,Object>();
                    map.put("deviceList",deviceStrList);
                    map.put("status",status);
                    msMerchantDeviceDao.updateSwitchStatusAll(map);
                }
               }
               pageNum++;
               jsonObject.put("pageNum",pageNum);//页码
        }while(deviceList.size()==pageSize);
    }

    @Override
    public void putAntiRobberCodesToRedis() throws Exception {
       
        
        
        
        
    }

}

package com.awifi.np.biz.devsrv.hotarea.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.device.hotarea.util.HotareaClient;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.ValidUtil;
import com.awifi.np.biz.devsrv.hotarea.service.HotareaService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月10日 上午10:08:29
 * 创建作者：亢燕翔
 * 文件名称：HotareaServiceImpl.java
 * 版本：  v1.0
 * 功能：  
 * 修改记录：
 */
@Service("hotareaService")
public class HotareaServiceImpl implements HotareaService{

    /**
     * 删除热点
     * @param devMacs 设备mac
     * @author 亢燕翔  
     * @throws Exception 异常
     * @date 2017年2月13日 上午10:40:21
     */
    public void deleteByDevMacs(String devMacs) throws Exception {
        String[] strArray= devMacs.split(",");
        ValidUtil.valid("设备mac集合[devMacs]", strArray, "arrayNotBlank");//数组内部是否存在null
        List<String> listDevMac = Arrays.asList(strArray);
        HotareaClient.deleteByDevMacs(JsonUtil.toJson(listDevMac));
    }
    
}

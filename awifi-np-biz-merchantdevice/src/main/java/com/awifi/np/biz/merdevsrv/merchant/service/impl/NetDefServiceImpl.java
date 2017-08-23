/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月19日 下午1:48:28
* 创建作者：王冬冬
* 文件名称：NetDefServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.merdevsrv.merchant.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.common.base.constants.RedisConstants;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;
import com.awifi.np.biz.merdevsrv.merchant.service.NetDefService;
import com.awifi.np.biz.tob.member.dao.MsMerchantDeviceDao;
@Service("netDefService")
public class NetDefServiceImpl implements NetDefService{

    /**
     * 微站商户设备dao
     */
    @Resource(name="msMerchantDeviceDao")
    private MsMerchantDeviceDao msMerchantDeviceDao;
    
    /**
     * 将设备防蹭网码放入redis
     * @throws Exception 异常
     * @author 王冬冬   
     * @date 2017年6月19日 下午1:50:05
     */
    public void addToCache() throws Exception {
        Integer maxSize = Integer.valueOf(SysConfigUtil.getParamValue("xls_export_sheet_max_size")).intValue();//每页最大数量
        Integer effectiveTime = Integer.valueOf(SysConfigUtil.getParamValue("netdef_effective_time")).intValue();//每页最大数量
        List<Map> list=msMerchantDeviceDao.addToCache(maxSize);
        for(Map map:list){
            RedisUtil.set(RedisConstants.NET_DEF+(String)map.get("devid"),(String)map.get("code"), effectiveTime);
        }
    }

}

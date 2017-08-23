/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月26日 上午11:12:14
* 创建作者：王冬冬
* 文件名称：WhiteUserServiceSendlogServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.mws.whiteuser.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.biz.mws.whiteuser.dao.WhiteUserSendLogDao;
import com.awifi.np.biz.mws.whiteuser.model.StationMerchantNamelistSendlog;
import com.awifi.np.biz.mws.whiteuser.service.WhiteUserServiceSendlogService;

@Service("whiteUserServiceSendlogService")
public class WhiteUserSendlogServiceImpl implements WhiteUserServiceSendlogService{

    /**
     * 
     */
    @Resource(name="whiteUserSendLogDao")
    private WhiteUserSendLogDao whiteUserSendLogDao;
    
    /**
     * 根据deviceId查找
     * @param deviceId 设备id
     * @return list
     * @author 王冬冬  
     * @date 2017年4月26日 上午11:09:24
     */
    public List<StationMerchantNamelistSendlog> findByDevId(String deviceId) {
        return whiteUserSendLogDao.findByDevId(deviceId);
    }

    /**
     * 保存白名单下发
     * @param accountId 用户id
     * @param merchantId 商户id
     * @param mobile 手机号
     * @param devId 设备id
     * @param userMac 用户mac
     * @param rs 返回结果
     * @param userId 用户id
     * @param taskId taskId
     * @author 王冬冬  
     * @date 2017年4月26日 下午1:04:17
     */
    public void saveNameListSendLog(Long accountId, Long merchantId, String mobile, String devId, String userMac,
            String rs, Long userId, String taskId) {
        StationMerchantNamelistSendlog record = new StationMerchantNamelistSendlog();
        List<StationMerchantNamelistSendlog> list = findByDevId(devId);
        if (list != null && list.size() > 0) {
            record = list.get(0);
        }
        if (accountId != null) {
            record.setAccountId(Integer.valueOf(accountId.toString()));
        }
        record.setMerchantId(merchantId);
        record.setDeviceId(devId);
        record.setCreateTime((int) (System.currentTimeMillis() / 1000));
        record.setFailReason(rs);
        record.setUserMac(userMac);
        record.setTaskId(taskId);
        if (StringUtils.isNoneBlank(rs)) {
            JSONObject ob = JSONObject.parseObject(rs);
            String message = (String) ob.get("message");
            if (message.equals("ok")) {
                record.setStatus((byte) 0);
            } else {
                record.setStatus((byte) 1);
            }
        } else {
            record.setStatus((byte) 1);
        }
        if (list != null && list.size() > 0) {
            whiteUserSendLogDao.updateByPrimaryKey(record);
        } else {
            whiteUserSendLogDao.insert(record);
        }
    }

}

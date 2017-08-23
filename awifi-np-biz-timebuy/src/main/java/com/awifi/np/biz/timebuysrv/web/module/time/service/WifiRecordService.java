/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年5月11日 上午11:15:04
 * 创建作者：尤小平
 * 文件名称：WifiRecordService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
package com.awifi.np.biz.timebuysrv.web.module.time.service;

import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.timebuysrv.web.module.time.dao.WifiRecordDao;
import com.awifi.np.biz.timebuysrv.web.module.time.model.WifiRecord;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class WifiRecordService {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * WifiRecordDao
     */
    @Resource
    private WifiRecordDao wifiRecordDao;

    /**
     * 根据id获取网络放通日志信息.
     * 
     * @param id id
     * @return WifiRecord
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月11日 下午9:01:47
     */
    public WifiRecord selectById(Long id) throws Exception {
        logger.debug("id=" + id);

        WifiRecord record = wifiRecordDao.selectByPrimaryKey(id);
        logger.debug("record :" + JsonUtil.toJson(record));

        return record;
    }

    /**
     * 根据deviceId和token获取网络放通日志信息.
     *
     * @param params params
     * @return WifiRecord
     * @throws Exception 异常
     * @author 尤小平
     * @date 2017年5月11日 下午9:01:47
     */
    public WifiRecord selectByDevIdAndToken(Map<String, Object> params) throws Exception {
        logger.debug("params =" + JsonUtil.toJson(params));

        WifiRecord record = wifiRecordDao.selectByDevIdAndToken(params);
        logger.debug("record :" + JsonUtil.toJson(record));

        return record;
    }

    /**
     * 添加网络放通日志信息.
     * 
     * @param record WifiRecord
     * @return 主键id
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月11日 下午9:02:17
     */
    public Long save(WifiRecord record) throws Exception {
        if (record == null) {
            return 0L;
        }

        logger.debug("record: " + JsonUtil.toJson(record));
        if (insert(record) && record.getId() != null) {
            return record.getId();
        } else {
            return 0L;
        }
    }

    /**
     * 更新网络放通日志信息.
     * 
     * @param record WifiRecord
     * @return 更新是否成功
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月11日 下午9:03:04
     */
    public boolean update(WifiRecord record) throws Exception {
        boolean result = false;
        
        if (record == null) {
            return result;
        }

        logger.debug("record: " + JsonUtil.toJson(record));
        int num = wifiRecordDao.updateByPrimaryKey(record);
        logger.debug("num=" + num);

        if (num > 0) {
            result = true;
        }

        return result;
    }

    /**
     * 添加网络放通日志信息.
     * 
     * @param record WifiRecord
     * @return 是否成功
     * @throws Exception 异常
     * @author 尤小平  
     * @date 2017年5月11日 下午9:02:59
     */
    private boolean insert(WifiRecord record) throws Exception {
        int num = wifiRecordDao.insert(record);
        
        logger.debug("num=" + num + ", record: " + JsonUtil.toJson(record));

        return num > 0;
    }

    /**
     * for testing only.
     * 
     * @param wifiRecordDao WifiRecordDao
     * @author 尤小平  
     * @date 2017年5月15日 下午2:15:13
     */
    protected void setWifiRecordDao(WifiRecordDao wifiRecordDao) {
        this.wifiRecordDao = wifiRecordDao;
    }
}

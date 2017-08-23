package com.awifi.np.biz.timebuysrv.web.module.time.dao;

import org.apache.ibatis.annotations.InsertProvider;

import com.awifi.np.biz.timebuysrv.web.module.time.dao.sql.CenterOnlineDataSql;
import com.awifi.np.biz.timebuysrv.web.module.time.model.CenterOnlineDataObject;

/**
 * 保存电渠推送数据记录
 * @author zhouwx
 * @since 2016-03-24
 */
public interface CenterOnlineDataDao {
    /**
     * 新增推送数据记录
     * @param onlineData
     * 推送数据对象
     * @return
     */
    @InsertProvider(type = CenterOnlineDataSql.class, method = "add")
    void add(CenterOnlineDataObject onlineData);
}

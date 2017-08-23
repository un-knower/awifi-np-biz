/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月8日 下午3:26:37
* 创建作者：王冬冬
* 文件名称：MsMerchantDeviceSql.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.tob.member.dao.sql;

import java.util.List;
import java.util.Map;

public class MsMerchantDeviceSql {
    
    /**
     * 修改防蹭网开关
     * @param params 参数
     * @return string
     * @author 王冬冬  
     * @date 2017年5月8日 下午3:48:56
     */
    public String updateSwitchStatusAll(Map<String, Object> params){
        StringBuilder sql = new StringBuilder();
        List<String> deviceStrList= (List<String>) params.get("deviceList");
        Byte status= (Byte) params.get("status");
        sql.append("update wii_device_extend set status="+status);
        sql.append(" where devId in ( ");
        for(int i=0;i<deviceStrList.size()-1;i++){
            sql.append("'").append(deviceStrList.get(i)).append("',");
        }
        sql.append("'").append(deviceStrList.get(deviceStrList.size()-1)).append("')");
        return sql.toString();
    }
}

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月19日 下午5:15:33
* 创建作者：余红伟
* 文件名称：CenterOnlineDataSql.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.timebuysrv.web.module.time.dao.sql;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.awifi.np.biz.timebuysrv.web.module.time.model.CenterOnlineDataObject;

public class CenterOnlineDataSql {
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());
    /**
     * 新增推送数据记录
     * @param onlineDataObject
     * @return
     * @author 余红伟 
     * @date 2017年4月21日 下午4:12:22
     */
    public String add(CenterOnlineDataObject onlineDataObject ){
        logger.debug("params:" + JSON.toJSONString(onlineDataObject));
        
        StringBuffer sql = new StringBuffer("insert into center_online_data (");
        
        Object broadbandAccount = onlineDataObject.getBroadbandAccount();
        Object merid = onlineDataObject.getMerid();
        Object objectId = onlineDataObject.getObjectId();
        Object mobile = onlineDataObject.getMobile();
        Object goodsCode = onlineDataObject.getGoodsCode();
        Object pkgPrice = onlineDataObject.getPkgPrice();
        Object amount = onlineDataObject.getAmount();
        Object adddays = onlineDataObject.getAdddays();
        Object pkgDetail = onlineDataObject.getPkgDetail();
        Object inputTime = onlineDataObject.getInputTime();
        Object remark1 = onlineDataObject.getRemark1();
        Object remark2 = onlineDataObject.getRemark2();
        Object remark3 = onlineDataObject.getRemark3();
        Object sign = onlineDataObject.getSign();
        Object identityCode = onlineDataObject.getIdentityCode();
        Object nowTime = onlineDataObject.getNowTime();
        Object proessFlg = onlineDataObject.getProcessFlg();
        Object tel = onlineDataObject.getTel();
        Object startTime = onlineDataObject.getStartTime();
        Object endTime = onlineDataObject.getEndTime();
        //Object createDate = onlineDataObject.getCreateDate();
        Object pkgNum = onlineDataObject.getPkgNum();
        
        if(broadbandAccount != null){
            sql.append("broadband_account, ");
        }
        if(merid != null){
            sql.append("merid, ");
        }
        if(objectId != null){
            sql.append("object_id, ");
        }
        if(mobile != null){
            sql.append("mobile, ");
        }
        if(goodsCode != null){
            sql.append("goods_code, ");
        }
        if(pkgPrice != null){
            sql.append("pkg_price, ");
        }
        if(amount != null){
            sql.append("amount, ");
        }
        if(adddays != null){
            sql.append("adddays, ");
        }
        if(pkgDetail != null){
            sql.append("pkg_detail, ");
        }
        if(inputTime != null){
            sql.append("inputTime, ");
        }
        if(remark1 != null){
            sql.append("remark1, ");
        }
        if(remark2 != null){
            sql.append("remark2, ");
        }
        if(sign !=null){
            sql.append("sign, ");
        }
        if(remark3 != null){
            sql.append("remark3, ");
        }
        if(identityCode != null){
            sql.append("identity_code, ");
        }
        if(nowTime != null){
            sql.append("now_time, ");
        }
        if(proessFlg != null){
            sql.append("process_flg, ");
        }
        if(tel != null){
            sql.append("tel, ");
        }
        if(startTime != null){
            sql.append("start_time, ");
        }
        if(endTime != null){
            sql.append("end_time, ");
        }
        sql.append(" create_date, ");
        if(pkgNum != null){
            sql.append("pkg_num");
        }
        
        sql.append(") values (");
        
        if(broadbandAccount != null){
            sql.append("#{broadbandAccount}, ");
        }
        if(merid != null){
            sql.append("#{merid}, ");
        }
        if(objectId != null){
            sql.append("#{objectId}, ");
        }
        if(mobile != null){
            sql.append("#{mobile}, ");
        }
        if(goodsCode != null){
            sql.append("#{goodsCode}, ");
        }
        if(pkgPrice != null){
            sql.append("#{pkgPrice}, ");
        }
        if(amount != null){
            sql.append("#{amount}, ");
        }
        if(adddays != null){
            sql.append("#{adddays}, ");
        }
        if(pkgDetail != null){
            sql.append("#{pkgDetail}, ");
        }
        if(inputTime != null){
            sql.append("#{inputTime}, ");
        }
        if(remark1 != null){
            sql.append("#{remark1}, ");
        }
        if(remark2 != null){
            sql.append("#{remark2}, ");
        }
        if(remark3 != null){
            sql.append("#{remark3}, ");
        }
        if(sign != null){
            sql.append("#{sign}, ");
        }
        if(identityCode != null){
            sql.append("#{identityCode}, ");
        }
        if(nowTime != null){
            sql.append("#{nowTime}, ");
        }
        if(proessFlg != null){
            sql.append("#{proessFlg}, ");
        }
        if(tel != null){
            sql.append("#{tel}, ");
        }
        if(startTime != null){
            sql.append("#{startTime}, ");
        }
        if(endTime != null){
            sql.append("#{endTime}, ");
        }
        sql.append("now(), ");
        if(pkgNum != null){
            sql.append("#{pkgNum}");
        }
        
        sql.append(")");
        
        logger.debug("sql.toString()=" + sql.toString());
        return sql.toString();
    }
}

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月8日 上午9:42:20
* 创建作者：范涌涛
* 文件名称：ImportMonitorThread.java
* 版本：  v1.0
* 功能：监控redis key(导入情况),导入出错写文件
* 修改记录：
*/
package com.awifi.np.biz.common.excel;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.awifi.np.biz.common.excel.dao.EmsExcelDao;
import com.awifi.np.biz.common.excel.model.ErrorInfo;
import com.awifi.np.biz.common.excel.model.OutExcel;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.EmsExcelUtil;
import com.awifi.np.biz.common.util.JsonUtil;

public class ImportMonitorThread extends Thread {
    
    /**监控的redis ke名*/
    private String redisKey;
    
    /**uuid用于索引导入文件对应的记录*/
    private String uuid;
    
    /**定制终端导入中的厂家*/
    private String corporation;
    
    /**批次号*/
    private String batch;
    
    /**dao层,根据导入情况更新filestatus,batch*/
    private EmsExcelDao emsExcelDao;
    
    /**
     * 导入监控线程
     * @param redisKey 监控的redis key
     * @param corporation 厂家
     * @param batch 批次号
     * @param uuid uuid
     * @param emsExcelDao dao
     * @author 范涌涛  
     * @date 2017年6月12日 上午10:18:47
     */
    public ImportMonitorThread(String redisKey,String corporation,String batch,String uuid,EmsExcelDao emsExcelDao) {
        super();
        
        this.redisKey = redisKey;
        this.corporation=corporation;
        this.batch=batch;
        this.uuid=uuid;
        this.emsExcelDao=emsExcelDao;
    }
    
    /**
     * 监控导入情况线程
     * @author 范涌涛  
     * @date 2017年6月12日 上午10:17:55
     */
    @SuppressWarnings({ "unchecked","rawtypes"})
    @Override
    public void run() {
        List<ErrorInfo> errorInfos = new ArrayList<ErrorInfo>();
        while(true) {
            List<Map<String,String>> maps=RedisUtil.hgetAllBatch(redisKey);
            if(null == maps){
                return;
            }
            Map<String,String> res=maps.get(0);
            TreeMap<String,String> map = new TreeMap(res);
            map.remove("step");
            map.remove("data");
            int count=0;//导入未结束的批数
            
            for(Map.Entry<String, String> entry:map.entrySet()){
                String value=entry.getValue();
                Map<String,String> mapKey=JsonUtil.fromJson(value, Map.class);
                if(mapKey.get("status").equals("0")||mapKey.get("status").equals("1")){ //导入未结束
                    count++;
                    break;
                }
                else if(mapKey.get("status").equals("3")) { //有导入失败的
                    StringBuilder error = new StringBuilder();
                    error.append("第").append(mapKey.get("line")).append("行导入失败：").append(mapKey.get("message"));
                    errorInfos.add(new ErrorInfo(error.toString()));
                }
            }
            
            if(count>0) {//导入未结束，休眠300ms后继续轮询
                try {
                    errorInfos.clear();
                    Thread.sleep(300);
                } catch (Exception e) {
                    e.printStackTrace();
                    return ;
                }
            }
            else {//导入结束
                Date date= new Date();
                if(errorInfos.size()>0) {//存在导入错误
                    String file=produceImportErrorFile(errorInfos);
                    if(errorInfos.size()!=map.size()) {
                        emsExcelDao.setMaxBatchNum(5, date,corporation, batch, file,uuid);//部分导入失败
                    }
                    else {
                        emsExcelDao.setMaxBatchNum(4, date,corporation, null, file,uuid);//全部导入失败
                    }
                    return ;
                }
                else {
                    emsExcelDao.setMaxBatchNum(6, date,corporation, batch,null, uuid);//全部导入成功
                }
                return ;
            }
        }
    }
    
    /**
     * 导入错误输出到excel
     * @param errorInfos 错误信息
     * @return 文件名
     * @author 范涌涛  
     * @date 2017年6月12日 上午10:16:56
     */
    private String produceImportErrorFile(List<ErrorInfo> errorInfos) {
        String dir = EmsExcelUtil.getDeviceFilePath();//获取文件路径
        OutExcel<ErrorInfo> infoFile = new OutExcel<ErrorInfo>(errorInfos, ErrorInfo.class);
        String file = System.currentTimeMillis() + "ImportError.xls";
        infoFile.init(dir + file);
        return file;
    }
}

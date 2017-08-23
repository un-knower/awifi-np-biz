package com.awifi.np.biz.common.excel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubEntity;
import com.awifi.np.biz.common.excel.service.ImportExcelService;
import com.awifi.np.biz.common.exception.InterfaceException;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.common.util.JsonUtil;

@SuppressWarnings({"unchecked"})
public class InitImportExcelThread extends Thread{
    
    /** 开始行数  */
    private int startRow;
    
    /** 结束行数  */
    private int endRow;
    
    /** 批次号Id  */
    private String batchDeviceId;
    /**传输到数据中心的数据*/
    private List<CenterPubEntity> paramList;
    
    /**
     * 具体导入操作service
     */
    private static ImportExcelService importExcelService;
    
    /**上传数据的类型*/
    private String type;
    
    /**
     * 得到ImportExcelService
     * @return ImportExcelService
     */
    private static ImportExcelService getImportExcelService(){
        if(importExcelService==null){
            importExcelService=(ImportExcelService) BeanUtil.getBean("importExcelService");
        }
        return importExcelService;
    }

    public InitImportExcelThread(int startRow, int endRow, String batchDeviceId,List<CenterPubEntity> paramList,String type) {
        super();
        this.startRow = startRow;
        this.endRow = endRow;
        this.batchDeviceId = batchDeviceId;
        this.paramList=paramList;
        this.type=type;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try{
            Thread.currentThread().setName(startRow+"-"+(endRow-1));
            updateImportStatus();
            //开始批量操作数据
            getImportExcelService().addBatchList(paramList);
            //操作完成，更新状态
            updateOkStatus();
        }catch(InterfaceException e){
          //update 数据导入错误
            updateFailStatus(e);
            //导入失败，批次表标示为导入失败
            //加入表示文件失败的表示:为3(就写入库失败)[业务需要改变]
            e.printStackTrace();
        }catch(Exception e){
            updateFailStatus(e);
            e.printStackTrace();
        }
    }

    /**
     * update 数据导入错误
     *      1:开始导入
     */
    private void updateImportStatus() {
        updateStatus(1,"数据开始导入",null);
    }

    /**
     * update 数据导入错误
     * @author kangyanxiang 
     *      3：失败
     * @param e 异常
     */
    private void updateFailStatus(Exception e) {
        //由于错误信息过于长，此处截取前300个字符
        String message = null;
        if(e.getMessage().length()>300){
            message = e.getMessage().substring(0, 300)+"...";
        } else {
            message = e.getMessage();
        }
        updateStatus(3,message,null);
//        batchUpdateStatus(4,"执行结束");
    }
    
    /**
     * update 数据导入成功
     *      2：成功
     * @throws InterruptedException 
     */
    private void updateOkStatus() {
        updateStatus(2,"数据保存成功",null);
    }
    
    /**
     * 状态更新
     * @param status 状态
     * @param message 提示信息
     * @param threadName 线程名称
     */
    private void updateStatus(int status, String message, String threadName) {
        if(StringUtils.isBlank(threadName)){
            threadName = Thread.currentThread().getName();
        }
        String value = RedisUtil.hmget(type+"-"+batchDeviceId, threadName).get(0);
        Map<String, String> resultMap = JsonUtil.fromJson(value, Map.class);
        Map<String, String> mapValue = new HashMap<String, String>();
        mapValue.put("num", resultMap.get("num"));
        mapValue.put("status", status+"");
        mapValue.put("line", threadName);
        mapValue.put("message", message);
        value = JsonUtil.toJson(mapValue);
        RedisUtil.hset(type+"-"+batchDeviceId, threadName, value);
    }
  
//    /**
//     * 批量状态更新（针对状态4）
//     * @param status 状态
//     * @param message 提示信息
//     */
//    public void batchUpdateStatus(int status, String message) {
//        List<Map<String, String>> dataMaps = RedisUtil.hgetAllBatch(type+"-"+batchDeviceId);//结果集MAP
//        Map<String,String> dataMap=dataMaps.get(0);
//        dataMap.remove("step");dataMap.remove("data");
//        for (String key : dataMap.keySet()) {
//            Map<String, Object> mapValue = JsonUtil.fromJson(dataMap.get(key), Map.class);
//            if(mapValue.get("status").equals("0")){
//                updateStatus(status,message,mapValue.get("thread").toString());
//            }
//        }
//    }
    
    

}

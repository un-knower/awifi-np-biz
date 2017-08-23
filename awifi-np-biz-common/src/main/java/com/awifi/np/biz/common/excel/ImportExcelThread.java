package com.awifi.np.biz.common.excel;

import java.util.List;

import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubEntity;
import com.awifi.np.biz.common.excel.service.ImportExcelService;
import com.awifi.np.biz.common.util.BeanUtil;

public class ImportExcelThread extends Thread{
    
    /** 最大线程数  */
    private int maxThread;
    
    /** 最大睡眠时间  */
    private Long maxThreadSleep;
    
    /** 初始化redis线程数量  */
    private int redisSize;
    
    /** 源数据 */
    private List<CenterPubEntity> successList;
    
    /** 数据长度  */
    private int maxSize;
    
    /** 生成的批次号Id  */
    private String batchDeviceId;
    
    /**导入数据的类型(例如:FatAp)*/
    private String type;
    
    
    /**
     * 导入的具体操作
     */
    private static ImportExcelService importExcelService;
    
    /**
     * 获得导入service
     * @return ImportExcelService
     */
    private static ImportExcelService getImportExcelService(){
        if(importExcelService==null){
            importExcelService=(ImportExcelService) BeanUtil.getBean("importExcelService");
        }
        return importExcelService;
    }

    public ImportExcelThread(int maxThread, Long maxThreadSleep, int redisSize, List<CenterPubEntity> successList,
            int maxSize, String batchDeviceId,String type) {
        super();
        this.maxThread = maxThread;
        this.maxThreadSleep = maxThreadSleep;
        this.redisSize = redisSize;
        this.successList = successList;
        this.maxSize = maxSize;
        this.batchDeviceId = batchDeviceId;
        this.type=type;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try{
            if(redisSize>maxThread){
                executeImportThread(1000*maxThread);
                int startRow=0;
                int endRow=0;
                int newMaxThread=0;
                while(true){
                    //开始查找空闲的线程
                    int importingNums=getImportExcelService().getImportingThreadNum(type+"-"+batchDeviceId);
                    if(maxThread>importingNums){
                        startRow=(newMaxThread+maxThread)*1000;
                        endRow=startRow+1000;
                        boolean flag=(newMaxThread+maxThread+1==redisSize);
                        if(flag){
                            endRow=maxSize;
                        }
                        //获取此次执行的数据
                        List<CenterPubEntity> entitys=successList.subList(startRow, endRow);
                        //开启这个线程
                        new InitImportExcelThread(startRow,endRow,batchDeviceId,entitys,type).start();
                        if(flag){
                            return;
                        }
                        newMaxThread++;
                    }
                    try {
                        Thread.sleep(maxThreadSleep);
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                }
                
            }else{//当前线程数处理也足以处理一个文件
                executeImportThread(maxSize);
            }
        }catch(Exception e){
//            successList.clear();
            e.printStackTrace();
        }
        
    }
    
    
    /**
     *执行导入数据的方法，
     * @param initMaxSize 首次操作的数据量
     */
    public void executeImportThread(int initMaxSize){
        for(int i=0;i<initMaxSize;i+=1000){
            int y = i+1000;
            if(y > initMaxSize){
                y = initMaxSize;
            }
            List<CenterPubEntity> entitys=successList.subList(i, y);
            new InitImportExcelThread(i,y,batchDeviceId,entitys,type).start();
        }
    }

}

package com.awifi.np.biz.common.excel.service;

import java.util.List;
import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubEntity;

public interface ImportExcelService {
    /**
     * 在redis中存入各个线程的初始化状态
     * @param key 存入redis中的key值
     * @param maxSize 存入的总条数
     * @return int 线程数
     */
     int initRedisSet(String key,int maxSize);

     /**
      * 得到各个线程的状态(在解析出错时，仅仅是解析出错，而导入情况只能通过这个接口进行查看，所以后期还要加入页面来详细了解这方面情况)
      * @param batchDeviceId 存于redis中的key值(其实是导入生成的批次号)
      * @return map 每个线程状态的集合
      */
     Integer getImportingThreadNum(String batchDeviceId); 
     /**
      * 向开放平台和本地存入信息
      * @param paramList 信息集合
      * @throws Exception 异常
      */
     void addBatchList(List<CenterPubEntity> paramList) throws Exception;
}

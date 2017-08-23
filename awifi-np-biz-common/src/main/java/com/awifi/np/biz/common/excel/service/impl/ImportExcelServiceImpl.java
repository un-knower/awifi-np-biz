package com.awifi.np.biz.common.excel.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

//import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.device.device.util.DeviceClient;
import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubEntity;
//import com.awifi.np.biz.common.excel.ImportMonitorThread;
//import com.awifi.np.biz.common.excel.dao.DeviceImportDao;
import com.awifi.np.biz.common.excel.service.ImportExcelService;
import com.awifi.np.biz.common.redis.util.RedisUtil;
import com.awifi.np.biz.common.util.JsonUtil;

@SuppressWarnings({"unchecked"})
@Service("importExcelService")
public class ImportExcelServiceImpl implements ImportExcelService{

    
    /**
     * 本地数据库导入链路层
     */
//    @Resource(name="deviceImportDao")
//    private DeviceImportDao deviceImportDao;
    /**
     * 在redis中存入各个线程的初始化状态
     * @param key 存入redis中的key值
     * @param maxSize 存入的总条数
     * @return int 线程数
     */
    @Override
    public int initRedisSet(String key, int maxSize) {
        // TODO Auto-generated method stub
        int k=1;//标识线程的总数量(从1开始)
        Map<String,String> map=new HashMap<String,String>();
        map.put("step", 3+"");
        map.put("data", "数据入库");
        for(int i=0;i<maxSize;i=i+1000){
            int y=i+1000;
            if(y>maxSize){
                y=maxSize;
            }
            Map<String,String> mapKey=new HashMap<String,String>();
            mapKey.put("num", k+"");//线程序号
            mapKey.put("line", i+"-"+(y-1));//该线程执行的数据范围
            mapKey.put("status", "0");//等待入库
            mapKey.put("message", "等待入库");
            map.put(i+"-"+(y-1), JsonUtil.toJson(mapKey));
            k++;
        }
        int expireTime=(int) TimeUnit.DAYS.toSeconds(1L);
        if(RedisUtil.exist(key)){
            RedisUtil.del(key);
        }
        RedisUtil.hmset(key, map,expireTime);//TODO:先设置为1天，后续需要再确定
        return k;
    }

    /**
     * 得到各个线程的状态(在解析出错时，仅仅是解析出错，而导入情况只能通过这个接口进行查看，所以后期还要加入页面来详细了解这方面情况)
     * @param batchDeviceId 存于redis中的key值(其实是导入生成的批次号)
     * @return map 每个线程状态的集合
     */
    @Override
    public Integer getImportingThreadNum(String batchDeviceId) {
        // TODO Auto-generated method stub
        List<Map<String,String>> maps=RedisUtil.hgetAllBatch(batchDeviceId);
        Map<String,String> map=maps.get(0);
        map.remove("step");
        map.remove("data");
        int count=0;
        for(Map.Entry<String, String> entry:map.entrySet()){
            String value=entry.getValue();
            Map<String,String> mapKey=JsonUtil.fromJson(value, Map.class);
            if(mapKey.get("status").equals("1")){
                count++;
            }
        }
        return count;
    }
    /**
     * 向开放平台和本地存入信息
     * @param paramList 信息集合
     * @throws Exception 异常
     */
    @Override
    public void addBatchList(List<CenterPubEntity> paramList) throws Exception {
        // TODO Auto-generated method stub
        //存于数据库
        //42是项目型瘦ap
        Integer entityType=paramList.get(0).getEntityType();
        
        if(entityType == null ||entityType.equals("")){ //热点导入没有entityType选项
            DeviceClient.addHotareaBatch(paramList);
            return;
        }
        if(entityType==42){
            DeviceClient.addPFitAPBatch(paramList);
        }
        if((entityType>=31&&entityType<=37)||(entityType>=371&&entityType<=373)){
            DeviceClient.addEntityBatch(paramList);//定制终端的导入操作
        }
        //存于本地的操作
//        for(int i=0;i<paramList.size();i++){
//        deviceImportDao.insertSelective(paramList.get(i));
//        }
    }
    
    
}

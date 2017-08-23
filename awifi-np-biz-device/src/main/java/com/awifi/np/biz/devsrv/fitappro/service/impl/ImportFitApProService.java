package com.awifi.np.biz.devsrv.fitappro.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubEntity;
import com.awifi.np.biz.api.client.dbcenter.fitap.model.ExcelFitApPro;
import com.awifi.np.biz.common.enums.DevType;
import com.awifi.np.biz.common.enums.FlowSts;
import com.awifi.np.biz.common.excel.ImportExcel;
import com.awifi.np.biz.common.excel.ImportExcelThread;
import com.awifi.np.biz.common.excel.ImportMonitorThread;
import com.awifi.np.biz.common.excel.dao.EmsExcelDao;
import com.awifi.np.biz.common.excel.model.InExcelIterator;
import com.awifi.np.biz.common.excel.service.ImportExcelService;
import com.awifi.np.biz.common.exception.ApplicationException;
import com.awifi.np.biz.common.exception.IllegalDataException;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.devsrv.fatap.service.ExcelValidation;
import com.awifi.np.biz.devsrv.pubarea.servcie.PubAreaService;

@Service("FitAPPro")
public class ImportFitApProService extends ImportExcel<ExcelFitApPro>{

    
    /**
     * 日期的定义
     */
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//以后会用到
    /**
     * Excel 导入类型
     */
    private static final String TYPE="FitAPPro";
    
    /**
     * 批量数据存储业务层
     */
    @Resource(name="importExcelService")
    private ImportExcelService importExcelService;
    
    /**
     * 地区查询业务层
     */
    @Resource(name="pubAreaService")
    private PubAreaService pubAreaService;
    /**
     * Excel状态信息
     */
    @Resource(name="emsExcelDao")
    private  EmsExcelDao emsExcelDao;
    @Override
    protected void insertDatas(List<ExcelFitApPro> datas, SessionUser sessionUser, String batchDeviceId,String uuid)
            throws Exception {
        // TODO Auto-generated method stub
        List<CenterPubEntity> centerPubEntityList = new ArrayList<CenterPubEntity>();
        for(ExcelFitApPro data:datas){
            CenterPubEntity centerPubEntity=new CenterPubEntity();
            centerPubEntity.setProvince(Integer.parseInt(data.getProvince()));
            centerPubEntity.setCity(Integer.parseInt(data.getCity()));
            centerPubEntity.setCounty(Integer.parseInt(data.getCounty()));
            centerPubEntity.setMacAddr(data.getMacAddr());
            centerPubEntity.setSsid(data.getSsid());
            centerPubEntity.setParEntityName(data.getAcName());
            centerPubEntity.setAcName(data.getAcName());
            // 补充必要的参数
            centerPubEntity.setImporter("EMS");
            centerPubEntity.setEntityType(DevType.pFitap.getValue());//42
            centerPubEntity.setOutTypeId("AWIFI");
            centerPubEntity.setEntityName("FitAP-" + data.getMacAddr());
            centerPubEntity.setFlowSts(FlowSts.newDev.getValue());
            centerPubEntity.setCreateBy(sessionUser.getUserName());
            centerPubEntity.setCreateDate(new Date());
            //centerPubEntity.setBatchNum(batchDeviceId);//新增加的加入批次号
            centerPubEntityList.add(centerPubEntity);
        }
        //开始导入
//        Integer maxThread=Integer.parseInt(SysConfigUtil.getParamValue("maxThread"));
        Integer maxThread=5;
        Long maxThreadSleep=30L;
//        Long maxThreadSleep=Long.valueOf(SysConfigUtil.getParamValue("maxThreadSleep"));
        int maxSize=centerPubEntityList.size();
        int redisSize=importExcelService.initRedisSet(TYPE+"-"+batchDeviceId, maxSize);
        new ImportMonitorThread(TYPE+"-"+batchDeviceId,null,batchDeviceId,uuid, emsExcelDao).start();
        //插入全部的数据
        new ImportExcelThread(maxThread, maxThreadSleep, redisSize-1, centerPubEntityList, maxSize, batchDeviceId, TYPE).start();
    }

    @Override
    protected Class<ExcelFitApPro> getExcelClass() {
        // TODO Auto-generated method stub
        return ExcelFitApPro.class;
    }

    @Override
    protected List<ExcelFitApPro> readExcel(InExcelIterator<ExcelFitApPro> excel, SessionUser sessionUser)
            throws Exception {
        // TODO Auto-generated method stub
        List<String> errors = new ArrayList<String>();
        ExcelValidation nArea = new ExcelValidation(sessionUser);
        nArea.setPubAreaService(pubAreaService);
        Map<String, Object> map = new HashMap<String, Object>();
        List<ExcelFitApPro> excellist = new ArrayList<ExcelFitApPro>();

        while (excel.hasNext()){
            try{
                ExcelFitApPro data = excel.getNextRow();
                nArea.validate(data);
                // 校验MAC + SSID唯一,acname的唯一性校验已经在数据中心导入时进行校验(这边不需要再进行校验)
                String macssid = data.getMacAddr() + data.getSsid();
                if (map.containsKey(macssid)){
                    errors.add("第" + excel.getRowNu() + "行的mac+ssid在Excel表格中存在重复值!");
                }
                map.put(macssid, data);
                excellist.add(data);
            }
            catch (ApplicationException e){
                errors.add("第" + excel.getRowNu() + "行" + e.getMessage());
            }
            catch (IllegalDataException e){
                errors.addAll(e.getErrors());
            }
        }

        if (errors.size() > 0){
            throw new IllegalDataException(errors);
        }

        return excellist;
    }

    @Override
    protected String getBatchNum(ExcelFitApPro pro) {
        // TODO Auto-generated method stub
        String batchNum=sdf.format(new Date())+"-"+pro.getAcName();
        return batchNum;
    }

}

package com.awifi.np.biz.devsrv.fatap.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import com.alibaba.fastjson.JSONArray;
import com.awifi.np.biz.api.client.dbcenter.corporation.model.Corporation;
import com.awifi.np.biz.api.client.dbcenter.corporation.util.CorporationClient;
import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubEntity;
import com.awifi.np.biz.api.client.dbcenter.fatap.model.ExcelFatAp;
import com.awifi.np.biz.common.enums.FlowSts;
import com.awifi.np.biz.common.enums.SourceType;
import com.awifi.np.biz.common.enums.Status;
import com.awifi.np.biz.common.excel.ImportExcel;
import com.awifi.np.biz.common.excel.ImportExcelThread;
import com.awifi.np.biz.common.excel.ImportMonitorThread;
//import com.awifi.np.biz.common.excel.dao.DeviceImportDao;
import com.awifi.np.biz.common.excel.dao.EmsExcelDao;
import com.awifi.np.biz.common.excel.model.CenterPubCorporation;
import com.awifi.np.biz.common.excel.model.CenterPubModel;
import com.awifi.np.biz.common.excel.model.InExcelIterator;
import com.awifi.np.biz.common.excel.service.ImportExcelService;
import com.awifi.np.biz.common.exception.ApplicationException;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.IllegalDataException;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.util.DateUtil;
import com.awifi.np.biz.common.util.JsonUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.devsrv.fatap.service.ExcelValidation;
import com.awifi.np.biz.devsrv.model.servcie.ModelService;
import com.awifi.np.biz.devsrv.pubarea.servcie.PubAreaService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 胖AP导入
 */
@Service(value="FatAP")
public class ImportFatAPService extends ImportExcel<ExcelFatAp>{
	/**
	 * 日期的定义
	 */
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//以后会用到
    
    /**
     * 类型的定义
     */
    private static final String TYPE="FatAP";
    
    /**
     * 设备查询持久层
     */
    @Resource(name="modelService")
    private ModelService modelService;
    
    /**
     * 批量数据存储业务层
     */
    @Resource(name="importExcelService")
    private ImportExcelService importExcelService;
    
    /**
     * 导入本地数据库持久层
     */
//    @Resource(name="deviceImportDao")
//    private DeviceImportDao deviceImportDao;
    @Resource(name="emsExcelDao")
    private EmsExcelDao emsExcelDao;
    /**
     * 地区查询业务层
     */
    @Resource(name="pubAreaService")
    private PubAreaService pubAreaService;
    
    
    /**
     * 校验批量数据的单循环
     * @param excelFatAp 需要校验的单条数据
     * @param row 该条数据所在excel文件中的行数
     * @param mapCorp 厂家的集合
     * @param mapMac mac集合
     * @param nArea 地区校验
     * @return list
     */
    private List<String> checkData(ExcelFatAp excelFatAp, int row, Map<String, Object> mapCorp, Map<String, Object> mapMac, ExcelValidation nArea){
        List<String> error = new ArrayList<String>();
        String corpModel = excelFatAp.getCorporation() + excelFatAp.getModel();
        excelFatAp.setCorporationText(excelFatAp.getCorporation());//获取厂商中文名
        if (mapCorp.size() == 0 || mapCorp.containsKey(corpModel)){
            // 第一行 或者是相同的厂家型号
            mapCorp.put(corpModel, excelFatAp);
        }
        else{
            error.add("第" + row + " 行开始Excel表格中的‘厂商型号’不相同，一次只能导入相同厂商型号的设备！");
        }
        try{
            // 导入的地区、厂家和用户地区、厂家的校验
            nArea.validate(excelFatAp);
        }
        catch (ApplicationException e){
            error.add("第 " + row + " 行 " + e.getMessage());
        }
        String mac = excelFatAp.getMacaddr();
        if (mapMac.containsKey(mac)){
            error.add("第  " + row + " 行的‘MAC地址’在Excel表格中存在重复值! ");
        }
        return error;
    }

    /**
     * 读取excel文件中的内容，以获取原始批量信息
     * @param excel excel文件
     * @param sessionUser 用户信息
     * @return list
     * @throws Exception 异常
     */
    @Override
    protected List<ExcelFatAp> readExcel(InExcelIterator<ExcelFatAp> excel, SessionUser sessionUser)throws Exception{
        Map<String, Object> mapMac = new HashMap<String, Object>();
        Map<String, Object> mapCorp = new HashMap<String, Object>();
        List<ExcelFatAp> excellist = new ArrayList<ExcelFatAp>();
        ExcelFatAp fatap=excel.getFirstRow();
        if(fatap==null){
            throw new ValidException("E2000030", MessageUtil.getMessage("E2000030"));
        }
        String model=fatap.getModel();
        Map<String, CenterPubCorporation> corpMap = getCorpMap(fatap.getCorporation() );//厂家map
        Map<String,CenterPubModel> mapModel = modelService.getAllModelMap(model);//型号map
        modelMap.putAll(mapModel);
        //查询所有厂商
        Map<String, Map<String, CenterPubModel>> corporationAndmodelMap =getCorporationAndModel(modelMap);
        ExcelValidation nArea = new ExcelValidation(sessionUser);
        nArea.setCorpMap(corpMap);
        nArea.setCorporationAndmodelMap(corporationAndmodelMap);
        nArea.setModelById(modelMap);
        nArea.setPubAreaService(pubAreaService);
        List<String> error = new ArrayList<String>();
        while (excel.hasNext()){
            try{
                ExcelFatAp excelFatAp = excel.getNextRow();
                List<String> errors = checkData(excelFatAp, excel.getRowNu(), mapCorp, mapMac, nArea);
                //如果厂商和型号已经验证过需要将转换后的厂商id进行赋值
                if(nArea.isFlag()){
                    excelFatAp.setCorporation(nArea.getCorporation());
                }
                mapMac.put(excelFatAp.getMacaddr(), excelFatAp);
                if (errors.isEmpty()){
                    excellist.add(excelFatAp);
                }
                else{
                    error.addAll(errors);
                }
            }
            catch (IllegalDataException e){
                for (String errorInfo : e.getErrors()){
                    error.add(errorInfo);
                }
            }
        }
        if (error.size() > 0){
            throw new IllegalDataException(error);
        }
        if (excellist.size() == 0){
            throw new ApplicationException("文件为空！");//文件为空错误
        }
//        CenterPubModel centerPubModel = modelMap.get(excellist.get(0).getModel());
//        long total = centerPubModel.getCollectionNum();
        // 查询当前型号已经入库的入库数量(需求改变，不再需要)
//        Map<String, Object> map = this.getBaseMap();
//        map.put("importer", "EMS");
//        map.put("status", Status.normal.getValue());
//        map.put("model", excellist.get(0).getModel());
//        Long intoTotal = DeviceQueryClient.queryEntityCountByParamGroupByModel(map);
////        Long intoTotal = 100L;
////        long remain = total - intoTotal;
//        long remain=10000L;//仅供测试时使用
//        if (remain < excellist.size()){
//            error.add(0, "导入设备的数量已大于型号集采数量！");//导入设备的数量已大于型号集采数量
//            throw new IllegalDataException(error);
//        }
//        if(sessionUser.getProvinceId()!=null) {
//            map.put("province", sessionUser.getProvinceId());
//            Long provinceNum = DeviceQueryClient.queryEntityCountByParamGroupByModel(map);//获取该省导入设备数量
//            Long provinceMaxNum = 0L;
//            for (CenterPubModelContract i : centerPubModel.getOrigins()) {
//                if (i.getProvince() == sessionUser.getProvinceId()) {
//                    provinceMaxNum = i.getCollectionNum();
//                    break;
//                }
//            }
//            long provinceRemain = provinceMaxNum - provinceNum;
//            if (excellist.size() > provinceRemain) {
//                error.add(0, "导入设备的数量已大于该省最大集采数量！");
//                throw new IllegalDataException(error);
//            }
//        }
        return excellist;
    }

    /**
     * 获取集合
     * @param modelMap 厂家集合(key为厂家+型号)
     * @return map
     */
    private Map<String,Map<String,CenterPubModel>> getCorporationAndModel(Map<String, CenterPubModel> modelMap) {
        Map<String, Map<String, CenterPubModel>> dataMap = new HashMap<String, Map<String, CenterPubModel>>();
        List<CenterPubModel> modelList=new ArrayList<>();
        modelList.addAll(modelMap.values());
        for (CenterPubModel cpm : modelList) {
            Map<String, CenterPubModel> map = new HashMap<String, CenterPubModel>();
            map.put(cpm.getModelName(), cpm);
            dataMap.put(cpm.getCorpId().toString() + cpm.getModelName(), map);
        }
        return dataMap;

    }
    /**
     * 获取批次号
     * @param excelFatAp 解析出的第一行数据
     * @return String
     */
    @Override
    protected String getBatchNum(ExcelFatAp excelFatAp){
        //例子:String maxBatchNum="20170101-HUAWEI-1";
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("corportaion", excelFatAp.getCorporation());
        map.put("createDate", sdf.format(new Date()));
        String maxBatchNum=emsExcelDao.getMaxBatchNum(map);
        //初始化为1
        int batchNo=1;
        if (!StringUtils.isEmpty(maxBatchNum)){
            batchNo = Integer.parseInt(getMaxBatNum(maxBatchNum)) + 1;
        }
        String batchDeviceId=createBatNum(batchNo,excelFatAp.getCorporationText());
        return batchDeviceId;
    }
    /**
     * 批量信息导入数据库和本地数据库的操作
     * @param excellist 需要导入的批量信息
     * @param sessionUser 用户信息
     * @throws Exception 异常
     */
    @Override
    protected void insertDatas(List<ExcelFatAp> excellist, SessionUser sessionUser,String batchDeviceId,String uuid)throws Exception{
       
        List<CenterPubEntity> centerPubEntityList = new ArrayList<CenterPubEntity>();
        for (ExcelFatAp excel : excellist){
            // 数据中心的类
            CenterPubEntity centerPubEntity = new CenterPubEntity();
            centerPubEntity.setImporter("EMS");
            centerPubEntity.setBatchNum(batchDeviceId);
            CenterPubModel c=modelMap.get(excel.getModel());
            if(c==null) {
                throw new BizException("E2301301", MessageUtil.getMessage("E2301301"));//查询不到对应的型号
            }
            else if( c.getEntityType()==null) {
                throw new BizException("E2301308",MessageUtil.getMessage("E2301308",c.getModelName()));//该型号对应entityType为空
            }
                
            centerPubEntity.setEntityType(c.getEntityType());//todo modelService.getEntityType();31-41
//            centerPubEntity.setEntityType(31);//todo modelService.getEntityType();31-41            
            centerPubEntity.setCorporation(excel.getCorporation());
            centerPubEntity.setModel(excel.getModel());
            centerPubEntity.setFwVersion(excel.getFwversion());
            centerPubEntity.setCpVersion(excel.getCpversion());
            centerPubEntity.setMacAddr(excel.getMacaddr());
            centerPubEntity.setPinCode(excel.getPincode());
            centerPubEntity.setProvince(Integer.parseInt(excel.getProvince()));
            centerPubEntity.setCity(Integer.parseInt(excel.getCity()));
            centerPubEntity.setCounty(Integer.parseInt(excel.getCounty()));
            centerPubEntity.setFlowSts(FlowSts.waitReview.getValue());
            centerPubEntity.setStatus(Status.normal.getValue());
            centerPubEntity.setCreateDate(new Date());
            centerPubEntity.setCreateBy(sessionUser.getUserName());
            centerPubEntity.setSalerflag(excel.getOnsiteservice());
            String devName = "FatAp-" + excel.getCorporation() + "-" + excel.getModel() + "-"
                    + excel.getMacaddr();
            centerPubEntity.setEntityName(devName);
            centerPubEntityList.add(centerPubEntity);
        }
        //Integer maxThread=Integer.parseInt(SysConfigUtil.getParamValue("maxThread"));
        //Long maxThreadSleep=Long.valueOf(SysConfigUtil.getParamValue("maxThreadSleep"));
        Integer maxThread=5;
        Long maxThreadSleep=30L;
        int maxSize=centerPubEntityList.size();
        String redisKey = TYPE+"-"+batchDeviceId;
        int redisSize=importExcelService.initRedisSet(redisKey, maxSize);
        new ImportMonitorThread(redisKey,centerPubEntityList.get(0).getCorporation(),batchDeviceId,uuid,emsExcelDao).start();
        //插入全部的数据
        new ImportExcelThread(maxThread, maxThreadSleep, redisSize-1, centerPubEntityList, maxSize, batchDeviceId, TYPE).start();
    }

    /**
     * 生成批次号
     * @param batchNo 当天数量
     * @param corporation 厂家名称
     * @return string
     */
    private static String createBatNum(int batchNo, String corporation) {//批次号生成算法
        return (new StringBuilder().append(DateUtil.formatToString(new Date(),"yyyyMMdd")).append("-").append(corporation).append("-").append(batchNo)).toString();
    }

    /**
     * 获取当天设备数量
     * @param batchMaxNum 之前该公司设备的批次号
     * @return string
     */
    private static String getMaxBatNum(String batchMaxNum) {//获取批次号
        int len = batchMaxNum.lastIndexOf("-");
        return batchMaxNum.substring(len+1, batchMaxNum.length());
    }
    
    /**
     * 获取类class
     * @return class
     */
    @Override
    protected Class<ExcelFatAp> getExcelClass(){
        return ExcelFatAp.class;
    }
    
    /**
     * 得到查询条件组成的基本map
     * @return map
     */
    public Map<String, Object> getBaseMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("importer", "EMS");
        map.put("outTypeId", SourceType.awifi.displayName());
        map.put("status", Status.normal.getValue());
        return map;
    }
    
    /**
     * 
     * @param corporation 厂商 id
     * @return 查询得到所有厂家对应的map
     * @throws Exception 异常/参数
     * @author 伍恰  
     * @date 2017年6月14日 上午9:17:28
     */
    public Map<String, CenterPubCorporation> getCorpMap(String corporation)throws Exception{
        Map<String,Object> map=new HashMap<>();
        map.put("status", 1);
        int total=CorporationClient.queryCountByParam(map);
        map.put("pageNum", 1);
        map.put("pageSize", total);
        map.put("corpName", corporation);
        List<Corporation> list=CorporationClient.queryListByParam(map);
        if(list==null||list.size()==0){
            throw new ApplicationException("Excel 中的厂商:" + corporation + "不存在!"); 
        }
        String json=JsonUtil.toJson(list);
        List<CenterPubCorporation> corporations=JSONArray.parseArray(json, CenterPubCorporation.class);
        Map<String, CenterPubCorporation> corporationMap=new HashMap<>();
        for(CenterPubCorporation centerPubCorporation:corporations){
            corporationMap.put(centerPubCorporation.getCorpName().toString(),centerPubCorporation);
        }
        return corporationMap;
    }

}


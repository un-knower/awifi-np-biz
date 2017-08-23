/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月16日 下午10:15:21
* 创建作者：范涌涛
* 文件名称：ImportHotPointService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.biz.devsrv.hot.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.awifi.np.biz.api.client.dbcenter.fatap.model.CenterPubEntity;
import com.awifi.np.biz.common.enums.FlowSts;
import com.awifi.np.biz.common.enums.SourceType;
import com.awifi.np.biz.common.enums.Status;
import com.awifi.np.biz.common.excel.ImportExcel;
import com.awifi.np.biz.common.excel.ImportExcelThread;
import com.awifi.np.biz.common.excel.ImportMonitorThread;
import com.awifi.np.biz.common.excel.dao.EmsExcelDao;
//import com.awifi.np.biz.common.excel.dao.DeviceImportDao;
import com.awifi.np.biz.common.excel.model.InExcelIterator;
import com.awifi.np.biz.common.excel.service.ImportExcelService;
import com.awifi.np.biz.common.exception.ApplicationException;
import com.awifi.np.biz.common.exception.IllegalDataException;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.util.DateUtil;
import com.awifi.np.biz.devsrv.excel.model.ExcelHot;
import com.awifi.np.biz.devsrv.excel.model.HotType;
import com.awifi.np.biz.devsrv.fatap.service.ExcelValidation;
import com.awifi.np.biz.devsrv.model.servcie.ModelService;
import com.awifi.np.biz.devsrv.pubarea.servcie.PubAreaService;

@Service(value = "Hot")
public class ImportHotService extends ImportExcel<ExcelHot> {
    /**
     * 类型的定义
     */
    private static final String TYPE = "Hot";

    @Resource(name = "emsExcelDao")
    private EmsExcelDao emsExcelDao;
    /**
     * 设备查询持久层
     */
    @Resource(name = "modelService")
    private ModelService modelService;

    /**
     * 批量数据存储业务层
     */
    @Resource(name = "importExcelService")
    private ImportExcelService importExcelService;

    // /**
    // * 导入本地数据库持久层
    // */
    // @Resource(name="deviceImportDao")
    // private DeviceImportDao deviceImportDao;

    /**
     * 地区查询业务层
     */
    @Resource(name = "pubAreaService")
    private PubAreaService pubAreaService;

    /**
     * 热点导入功能,读取excel内容
     * 
     * @param excel
     * @param sessionUser
     * @return
     * @throws Exception
     * @author 范涌涛
     * @date 2017年5月16日 下午10:17:32
     */
    @Override
    protected List<ExcelHot> readExcel(InExcelIterator<ExcelHot> excel, SessionUser sessionUser) throws Exception {
        List<String> error = new ArrayList<String>();
        Map<String, Object> mapx = new HashMap<String, Object>();
        Map<String, Object> nameMap = new HashMap<String, Object>();
        ExcelValidation nArea = new ExcelValidation(sessionUser);
        nArea.setPubAreaService(pubAreaService);
        List<ExcelHot> hotarealist = new ArrayList<ExcelHot>();
        while (excel.hasNext()) {
            try {
                ExcelHot excelHot = excel.getNextRow();
                List<String> checkData = this.checkData(excelHot, excel.getRowNu(), mapx, nameMap, nArea);// 对每一行校验
                String bpc = excelHot.getBrasId() + excelHot.getPvlan() + excelHot.getCvlan();
                mapx.put(bpc, excel);
                String name = excelHot.getHotareaname();
                nameMap.put(name, excel);
                try {
                    // 热点类型的校验
                    String hotareatype = excelHot.getHotareatype();
                    HotType hotareaType = HotType.getHotareaType(hotareatype);
                    excelHot.setHotareatype(hotareaType.getValue() + "");
                } catch (ApplicationException e) {
                    checkData.add("第 " + excel.getRowNu() + " 行 " + e.getMessage());
                }
                if (checkData.isEmpty()) {
                    hotarealist.add(excelHot);
                } else {
                    error.addAll(checkData);
                }
            } catch (IllegalDataException e) {
                error.addAll(e.getErrors());
            }
        }

        if (error.size() > 0) {
            throw new IllegalDataException(error);
        }

        return hotarealist;
    }

    private List<String> checkData(ExcelHot excelHot, int i, Map<String, Object> mapx, Map<String, Object> nameMap,
            ExcelValidation nArea) {
        List<String> error = new ArrayList<String>();
        // 地区的校验
        try {
            nArea.validate(excelHot);
        } catch (ApplicationException e) {
            error.add("Excel中 第  " + i + " 行 " + e.getMessage());
        }
        // B+P+C
        String bpc = excelHot.getBrasId() + excelHot.getPvlan() + excelHot.getCvlan();
        if (mapx.containsKey(bpc)) {
            error.add("Excel中 第 " + i + " 行数据BRASID+PVLAN+CVLAN存在重复值！");
        }
        // name
        String name = excelHot.getHotareaname();
        if (nameMap.containsKey(name)) {
            error.add("Excel中 第 " + i + " 行数据 热点名称 存在重复值！");
        }
        // 判断热点等级是否存在
        String hotareadegree = excelHot.getHotareadegree();
        if (!("A".equals(hotareadegree)) && !("B".equals(hotareadegree)) && !("C".equals(hotareadegree))
                && !("D".equals(hotareadegree))) {
            error.add("Excel中 第 " + i + " 行数据，热点等级错误! 必须是(A,B,C,D)其中的任何一种!");
        }
        return error;
    }

    @Override
    protected void insertDatas(List<ExcelHot> excellist, SessionUser sessionUser, String batchDeviceId, String uuid)
            throws Exception {
        List<CenterPubEntity> hotList = new ArrayList<CenterPubEntity>();
        for (ExcelHot excel : excellist) {
            CenterPubEntity centerPubHotarea = new CenterPubEntity();
            centerPubHotarea.setHotareaName(excel.getHotareaname());
            centerPubHotarea.setHotareaOutId(excel.getHotareaoutid());
            centerPubHotarea.setProvince(Integer.parseInt(excel.getProvinceid()));
            centerPubHotarea.setCity(Integer.parseInt(excel.getCityid()));
            centerPubHotarea.setCounty(Integer.parseInt(excel.getCounty()));
            centerPubHotarea.setXpos(excel.getXpos());
            centerPubHotarea.setYpos(excel.getYpos());
            centerPubHotarea.setHotareaType(excel.getHotareatype());
            centerPubHotarea.setHotareaDegree(excel.getHotareadegree());
            centerPubHotarea.setPvlan(excel.getPvlan());
            centerPubHotarea.setCvlan(excel.getCvlan());
            centerPubHotarea.setVlan(excel.getVlan());
            // baseName
            centerPubHotarea.setBrasName(excel.getBrasId());
            centerPubHotarea.setBrasIp(excel.getBasip());
            centerPubHotarea.setBrasShel(excel.getShel());
            centerPubHotarea.setBrasSlot(excel.getSlot());
            centerPubHotarea.setBrasPort(excel.getHotport());
            centerPubHotarea.setSsid(excel.getSsid());
            centerPubHotarea.setAcount(excel.getAcount());
            centerPubHotarea.setAcName(excel.getBelongac());
            centerPubHotarea.setNas(excel.getNas());
            centerPubHotarea.setSegment(excel.getSegment());
            if (excel.getApnum() != null) {
                centerPubHotarea.setApNum(Long.parseLong(excel.getApnum().toString()));
            }
            centerPubHotarea.setBrasPortType(excel.getBrasporttype());
            centerPubHotarea.setRepairComp(excel.getRepaircomp());
            // WLAN干放数
            if (excel.getWlanganfangnum() != null) {
                centerPubHotarea.setWlanGanfangNum(Long.parseLong(excel.getWlanganfangnum().toString()));
            }
            centerPubHotarea.setOwner(excel.getOwner());
            centerPubHotarea.setOwnerPhone(excel.getOwnerphone());
            centerPubHotarea.setIshasshifen(excel.getIshasshifen());
            centerPubHotarea.setIslineshifen(excel.getIslineshifen());
            centerPubHotarea.setAccessNo(excel.getAccessno());
            centerPubHotarea.setHotarea(excel.getHotarea());
            centerPubHotarea.setRemark(excel.getDesc());
            centerPubHotarea.setLandmark(excel.getLandmark());
            centerPubHotarea.setIntergrator(excel.getIntergrator());
            centerPubHotarea.setRemark(excel.getRemark());

            centerPubHotarea.setImporter("EMS");
            centerPubHotarea.setOutTypeId("EMS_BASE");
            centerPubHotarea.setStatus(Status.normal.getValue());
            centerPubHotarea.setFlowSts(FlowSts.newDev.getValue());
            centerPubHotarea.setOutTypeId(SourceType.awifi.displayName());
            centerPubHotarea.setCreateBy(sessionUser.getUserName());
            centerPubHotarea.setCreateDate(new Date());
            hotList.add(centerPubHotarea);
        }
        // Integer
        // maxThread=Integer.parseInt(SysConfigUtil.getParamValue("maxThread"));
        // Long
        // maxThreadSleep=Long.valueOf(SysConfigUtil.getParamValue("maxThreadSleep"));
        Integer maxThread = 10;// TODO:在数据库中设置
        Long maxThreadSleep = 30L;// TODO:在数据库中设置
        int maxSize = hotList.size();
        int redisSize = importExcelService.initRedisSet(TYPE + "-" + batchDeviceId, maxSize);
        new ImportMonitorThread(TYPE + "-" + batchDeviceId, null, batchDeviceId, uuid, emsExcelDao).start();
        // 插入全部的数据
        new ImportExcelThread(maxThread, maxThreadSleep, redisSize - 1, hotList, maxSize, batchDeviceId, TYPE).start();

    }

    @Override
    protected String getBatchNum(ExcelHot excelHot) {
        return DateUtil.getNow();
    }
    
    @Override
    protected Class<ExcelHot> getExcelClass() {
        return ExcelHot.class;
    }

}

package com.awifi.np.biz.common.util;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月22日 上午8:48:31
 * 创建作者：亢燕翔
 * 文件名称：ExportExcelUtil.java
 * 版本：  v1.0
 * 功能：  导出公共方法
 * 修改记录：
 */
public class ExcelUtil {
    /** 胖ap excel模版列*/
    public static final String[] FATAP_EXCELCOLUMNS=new String[]{"设备mac","宽带账号","商户账号(手机号)","商户名称","一级行业","二级行业","联系人","联系方式","商户地址","设备SSID(例:yx-123)","省(例:浙江)","市(例:杭州)","区(例:西湖区)"};
    /** fitap excel模版列*/
    public static final String[] FITAP_EXCELCOLUMNS=new String[]{"设备MAC","SSID","商户名称","商户账号","一级行业","二级行业","联系人","联系方式","省","市","区","商户地址"};
    /** nas excel模版列*/
    public static final String[] NAS_EXCELCOLUMNS=new String[]{"AC/BAS名称","商户名称","商户账号","一级行业","二级行业","联系人","联系方式","省","市","区","商户地址"};
    /** 热点 excel模版列*/
    public static final String[] HOTAREA_EXCELCOLUMNS=new String[]{"热点名称","商户名称","商户账号","一级行业","二级行业","联系人","联系方式","省","市","区","商户地址"};
    /** 白名单 excel模版列*/
    public static final String[] WHITEUSERS_EXCELCOLUMNS = new String[]{"手机号","mac地址"};
    /** 批量ssid excel模版列*/
    public static final String[] SSID_EXCELCOLUMNS = new String[]{"设备MAC","SSID"};

    /**
     * 文件解析器.获取sheet(仅适用于单个文件)
     * @param request 请求
     * @return Sheet
     * @throws Exception 异常
     * @author 亢燕翔  
     * @date 2017年2月13日 上午9:49:54
     */
    public static Sheet fileResolver(HttpServletRequest request) throws Exception{
        CommonsMultipartResolver multipartResolver  = new CommonsMultipartResolver(request.getSession().getServletContext());
        Sheet sheet = null;
        if(multipartResolver.isMultipart(request)){
            MultipartHttpServletRequest  multiRequest = (MultipartHttpServletRequest)request;//转换成多个请求
            Iterator<String>  iter = multiRequest.getFileNames();
            while(iter.hasNext()){//遍历文件
                MultipartFile file = multiRequest.getFile((String)iter.next());
                if(file == null){//文件为空跳过
                    continue;
                }
                InputStream files = file.getInputStream();
                org.apache.poi.ss.usermodel.Workbook book = WorkbookFactory.create(files);
                sheet = book.getSheetAt(0); //获得工作表对象
                int lastRowNum = sheet.getLastRowNum();//最后一行
                if (lastRowNum < 1) {
                    throw new ValidException("E2000030", MessageUtil.getMessage("E2000030"));//文件内容不允许为空!
                }
                Integer maxSize = Integer.valueOf(SysConfigUtil.getParamValue("xls_import_max_size")).intValue();//导入列表最大数量
                if (lastRowNum > maxSize) {
                    Object[] args = {lastRowNum,maxSize};
                    throw new ValidException("E2000031", MessageUtil.getMessage("E2000030",args));//导入的记录总数[{0}]不允许大于[{1}]!
                }
            }
        }
        if(sheet == null){
            throw new ValidException("E2000069", MessageUtil.getMessage("E2000069"));//请先上传需要导入的Excel文件!
        }
        return sheet;
    }
    
    /**
     * 导入excel字段和模板字段比较
     * @param sheet 表格
     * @param excelColumns 模板表格字段
     * @return 
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年6月20日 下午5:23:08
     */
    public static void cmpTemplateExcel(Sheet sheet,String [] excelColumns) throws Exception{
        Row row=sheet.getRow(0);
        int length=excelColumns.length;
        for(int i=0;i<length;i++){
            Cell cell=row.getCell(i);
            if(!cell.getStringCellValue().equals(excelColumns[i])){
                throw new ValidException("E2000067", MessageUtil.getMessage("E2000067"));//导入excel与模版不一致!
            }
        }
        return; 
    }
    
    /**
     * 向文件中写数据
     * @param book 工作簿
     * @param sheetName sheet名称
     * @param sheetNum sheet编号
     * @param rowName 行名称
     * @param dataList 数据集
     * @author 亢燕翔  
     * @throws Exception 
     * @date 2017年2月8日 下午6:36:41
     */
    public static void fileWriteData(WritableWorkbook book, String sheetName, int sheetNum, String[] rowName, List<Object[]> dataList) throws Exception {
        int rowSize = rowName.length;//列长度
        WritableSheet sheet = book.createSheet(sheetName,sheetNum);// 创建工作表
        
        /*title样式*/
        WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED);
        WritableCellFormat titleFormat = new WritableCellFormat(titleFont);
        titleFormat.setAlignment(jxl.format.Alignment.CENTRE);
        WritableCellFormat textFormat = new WritableCellFormat();
        textFormat.setAlignment(jxl.format.Alignment.CENTRE);
        
        /*设置列名称*/
        for(int rNum=0; rNum<rowSize; rNum++){
            sheet.setColumnView(rNum, 20);//设置每列表格宽度（20）
            sheet.addCell(new Label(rNum, 0, rowName[rNum], titleFormat));//设置title
        }
        
        int dataSize = dataList.size();
        if(dataSize <=0){
            return;
        }
        
        /*设置数据集*/
        for(int i=0; i<dataSize; i++){
            Object[] obj = dataList.get(i);
            int objSize = obj.length;
            for(int k=0; k<objSize; k++){
                Object value = obj[k];
                sheet.addCell(new Label(k,i + 1,value != null ? value.toString() : null,textFormat));
            }
        }
    }

    /**
     * 使用jxl插件导入表格和模板的比较
     * @param sheet 表格
     * @param excelColumns 模板列
     * @author 王冬冬  
     * @date 2017年6月21日 上午9:32:21
     */
    public static void cmpTemplateExcel(jxl.Sheet sheet, String[] excelColumns) {
        jxl.Cell[] row=sheet.getRow(0);
        int length=excelColumns.length;
        for(int i=0;i<length;i++){
            jxl.Cell cell=row[i];
            if(!cell.getContents().equals(excelColumns[i])){
                throw new ValidException("E2000067", MessageUtil.getMessage("E2000067"));//导入excel与模版不一致!
            }
        }
        return; 
        
    }
}

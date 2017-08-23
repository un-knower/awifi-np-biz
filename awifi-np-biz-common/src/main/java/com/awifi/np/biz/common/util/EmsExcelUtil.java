package com.awifi.np.biz.common.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.awifi.np.biz.common.system.sysconfig.util.SysConfigUtil;

import org.apache.poi.ss.usermodel.DateUtil;


@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class EmsExcelUtil {
    

	/**
	 * excel 2003
	 */
    private static final String XLS = "xls";

    /**
     * Excel 2007
     */
    private static final String XLSX = "xlsx";

    /**
     * @Title:readExcel
     * @Description:
     * @param input     input     
     * @param filetype  filetype  
     * @param o         o         
     * @param items     items     
     * @param sheetNum  sheetNum  
     * @param <T> T
     * @return                    
     * @throws Exception Exception
     * @return List<T>
     * @throws 
     */
    @SuppressWarnings("resource")
    public static <T> List<T> readExcel(InputStream input, String filetype, Class<?> o, String[] items,
            int sheetNum) throws Exception{
        Workbook workbook = null;
        List list = new ArrayList();
        try{
            if (filetype.toLowerCase().equals(XLS)){
                workbook = new HSSFWorkbook(input);
            }
            else if (filetype.toLowerCase().equals(XLSX)){
                workbook = new XSSFWorkbook(input);
            }
            else{
                workbook = new XSSFWorkbook(input);// 如果不符合，默认07版Excel
            }
            Sheet sheet = workbook.getSheetAt(sheetNum);
            String sheet_name = sheet.getSheetName();
            int minRowIx = sheet.getFirstRowNum();// 从第二行开始
            int maxRowIx = sheet.getLastRowNum();
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            Row row0 = sheet.getRow(0);
            for (int rowIx = minRowIx; rowIx <= maxRowIx; rowIx++){
                Row row = sheet.getRow(rowIx);
                if (row == null){
                    continue;
                }
                if (rowIx < 1){// 舍去第一行，标题
                    continue;
                }
                Object obj = Class.forName(o.getName()).newInstance();
                for (int colIx = 0; colIx < items.length; colIx++){
                    String field = items[colIx];
                    Class<?> field_type = PropertyUtils.getPropertyType(obj, field);
                    String col_name = row0.getCell(colIx).getStringCellValue();
                    if (field_type.getName().equals("java.util.Date")){
                        Date value = getDateNotNull(row, evaluator, colIx, sheet_name, col_name, rowIx + 1);
                        BeanUtils.setProperty(obj, field, value);
                    }
                    else if (field_type.getName().equals("java.lang.Long")
                            || field_type.getName().equals("java.lang.Integer")
                            || field_type.getName().equals("java.lang.Double")
                            || field_type.getName().equals("java.lang.Short")
                            || field_type.getName().equals("java.lang.Float")){
                        String value = getNumberValueNotNull(row, evaluator, colIx, sheet_name, col_name,
                                rowIx + 1);
                        BeanUtils.setProperty(obj, field, value);
                    }
                    else{
                        String value = getStringOrNumberValueNotNull(row, evaluator, colIx, sheet_name,
                                col_name, rowIx + 1);
                        BeanUtils.setProperty(obj, field, value);
                    }
                }
                list.add(obj);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception("excel读取错误:" + e.getMessage());
        }
        return list;
    }

    /**
     * 获取单元格值 且不为空（文本类型）
     * 
     * @param row         row       
     * @param evaluator   evaluator 
     * @param i           i         
     * @param sheet_name  sheet_name
     * @param row_name    row_name  
     * @param excelrow    excelrow  
     * @return String String
     * @throws Exception Exception
     */
    public static String getStringValueNotNull(Row row, FormulaEvaluator evaluator, int i, String sheet_name,
            String row_name, int excelrow) throws Exception{
        String result = "";
        try{
            Cell celli = row.getCell(new Integer(i));
            CellValue cellValuei = evaluator.evaluate(celli);
            if (cellValuei == null || "".equals(cellValuei)){
                throw new Exception(sheet_name + "第" + excelrow + row_name + "不能为空!请确认！");
            }
            if (cellValuei.getCellType() == 1){// String 类型
                result = cellValuei.getStringValue();
            }
            else{
                throw new Exception(sheet_name + "第" + excelrow + "行" + row_name + "单元格类型有误，请确认！必须为文本类型");
            }
            return result;
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 获取单元格值（文本类型）
     * 
     * @param row         row         
     * @param evaluator   evaluator   
     * @param i           i           
     * @param sheet_name  sheet_name  
     * @param row_name    row_name    
     * @param excelrow    excelrow    
     * @return   String String                      
     * @throws Exception   Exception  
     */
    public static String getStringValue(Row row, FormulaEvaluator evaluator, int i, String sheet_name,
            String row_name, int excelrow) throws Exception{
        String result = "";
        try{
            Cell celli = row.getCell(new Integer(i));
            CellValue cellValuei = evaluator.evaluate(celli);
            if (cellValuei != null && !"".equals(cellValuei)){
                if (cellValuei.getCellType() == 1){// String 类型
                    result = cellValuei.getStringValue();
                }
                else{
                    throw new Exception(sheet_name + "第" + excelrow + "行" + row_name + "单元格类型有误，请确认！必须为文本类型");
                };
            }
            return result;
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 获取单元格值 且不为空（文本类型或numer类型）
     * 
     * @param row         row        
     * @param evaluator   evaluator  
     * @param i           i          
     * @param sheet_name  sheet_name 
     * @param row_name    row_name   
     * @param excelrow    excelrow   
     * @return String String
     * @throws Exception Exception
     */
    public static String getStringOrNumberValueNotNull(Row row, FormulaEvaluator evaluator, int i,
            String sheet_name, String row_name, int excelrow) throws Exception{
        String result = "";
        try{
            Cell celli = row.getCell(new Integer(i));
            CellValue cellValuei = evaluator.evaluate(celli);
            if (cellValuei == null || "".equals(cellValuei)){
                throw new Exception(sheet_name + "第" + excelrow + row_name + "不能为空!请确认！");
            }
            if (cellValuei.getCellType() == 0){// NUMBER 类型
                DecimalFormat df = new DecimalFormat("#");// 保留两位小数且不用科学计数法
                result = df.format(cellValuei.getNumberValue());
            }
            else if (cellValuei.getCellType() == 1){// String 类型
                result = cellValuei.getStringValue();
            }
            else{
                throw new Exception(sheet_name + "第" + excelrow + "行" + row_name + "单元格类型有误，请确认！必须为文本类型");
            };
            return result;
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 获取单元格值（文本类型或numer类型）
     * 
     * @param row         row        
     * @param evaluator   evaluator  
     * @param i           i          
     * @param sheet_name  sheet_name 
     * @param row_name    row_name   
     * @param excelrow    excelrow   
     * @return   String String                    
     * @throws Exception   Exception 
     */
    public static String getStringOrNumberValue(Row row, FormulaEvaluator evaluator, int i,
            String sheet_name, String row_name, int excelrow) throws Exception{
        String result = "";
        try{
            Cell celli = row.getCell(new Integer(i));
            CellValue cellValuei = evaluator.evaluate(celli);
            if (cellValuei != null && !"".equals(cellValuei)){
                if (cellValuei.getCellType() == 0){// NUMBER 类型
                    result = String.valueOf(cellValuei.getNumberValue());
                }
                else if (cellValuei.getCellType() == 1){// String 类型
                    result = cellValuei.getStringValue();
                }
                else{
                    throw new Exception(sheet_name + "第" + excelrow + "行" + row_name
                            + "单元格类型有误，请确认！必须为文本类型或数字类型");
                }
                ;
            }
            return result;
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 获取时间类型 且不为空
     * 
     * @param row          row        
     * @param evaluator    evaluator  
     * @param i            i          
     * @param sheet_name   sheet_name 
     * @param row_name     row_name   
     * @param excelrow     excelrow   
     * @return  Date      Date                 
     * @throws Exception    Exception 
     */
    public static Date getDateNotNull(Row row, FormulaEvaluator evaluator, int i, String sheet_name,
            String row_name, int excelrow) throws Exception{
        Date date = null;
        try{
            Cell celli = row.getCell(new Integer(i));
            CellValue cellValuei = evaluator.evaluate(celli);
            if (cellValuei == null || "".equals(cellValuei)){
                throw new Exception(sheet_name + "第" + excelrow + "行" + row_name + "不能为空!请确认！");
            }
            date = null;
            if (cellValuei.getCellType() == 0){// NUMBER 类型
                date = DateUtil.getJavaDate(cellValuei.getNumberValue());
            }
            else if (cellValuei.getCellType() == 1){// String 类型
                throw new Exception(sheet_name + "第" + excelrow + "行" + row_name + "单元格类型有误，必须为日期格式，请确认！");
            };
            return date;
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * @Title:getNumberValueNotNull
     * @Description:
     * @param row         row        
     * @param evaluator   evaluator  
     * @param i           i          
     * @param sheet_name  sheet_name 
     * @param row_name    row_name   
     * @param excelrow    excelrow   
     * @throws Exception   Exception 
     * @return String      String    
     * @throws 
     */
    public static String getNumberValueNotNull(Row row, FormulaEvaluator evaluator, int i, String sheet_name,
            String row_name, int excelrow) throws Exception{
        String result = "";
        try{
            Cell celli = row.getCell(new Integer(i));
            CellValue cellValuei = evaluator.evaluate(celli);
            if (cellValuei == null || "".equals(cellValuei)){
                throw new Exception(sheet_name + "第" + excelrow + row_name + "不能为空!请确认！");
            }
            if (cellValuei.getCellType() == 0){// NUMBER 类型
                DecimalFormat df = new DecimalFormat("#");// 保留两位小数且不用科学计数法
                result = df.format(cellValuei.getNumberValue());
            }
            else{
                throw new Exception(sheet_name + "第" + excelrow + "行" + row_name + "单元格类型有误，请确认！必须为数值类型");
            };
            return result;
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 根据文件名得到对应的Workbook
     * 
     * @param fileName 路径+文件名
     * @return Workbook
     */
    public static Workbook getWorkbookByFileName(String fileName){
        Workbook workbook = null;
        InputStream ins = null;
        try{
            ins = new FileInputStream(new File(fileName));
            // 根据输入流创建Workbook对象
            workbook = new HSSFWorkbook(ins);
            // workbook = WorkbookFactory.create(ins);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
            throw new RuntimeException("excel文件不存在!", e);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("excel文件读取失败!", e);
        }
        finally{
            if (ins != null){
                try{
                    ins.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return workbook;
    }

    /**
     * 根据输入流得到Workbook
     * 
     * @param ins ins
     * @return Workbook
     */
    public static Workbook createWorkbook(InputStream ins){
        Workbook workbook = null;
        // 根据输入流创建Workbook对象
        try{
            workbook = WorkbookFactory.create(ins);
        }
        catch (InvalidFormatException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally{
            if (ins != null){
                try{
                    ins.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return workbook;
    }

    /**
     * @Title:createWorkbook
     * @param bytes bytes
     * @return Workbook
     */
    public static Workbook createWorkbook(byte[] bytes){
        Workbook workbook = null;
        // 根据输入流创建Workbook对象
        InputStream ins = new BufferedInputStream(new ByteArrayInputStream(bytes));
        try{
            workbook = WorkbookFactory.create(ins);
        }
        catch (InvalidFormatException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally{
            if (ins != null){
                try{
                    ins.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return workbook;
    }

    /**
     * @Title:getSheetByName
     * @param fileName fileName
     * @param sheetName sheetName
     * @return Sheet
     */
    public static Sheet getSheetByName(String fileName, String sheetName){
        Workbook workbook = getWorkbookByFileName(fileName);
        Sheet sheet = workbook.getSheet(sheetName);
        return sheet;
    }

    /**
     * 获取EXCEL文件中所有的Sheet List
     *  
     * @param fileName fileName
     * @return List<Sheet>
     */
    public static List<Sheet> getAllSheet(String fileName){
        Workbook workbook = getWorkbookByFileName(fileName);
        List<Sheet> sheetList = new LinkedList<Sheet>();
        int sheetLength = workbook.getNumberOfSheets();
        for (int i = 0; i < sheetLength; i++){
            Sheet sheet = workbook.getSheetAt(i);
            sheetList.add(sheet);
        }
        return sheetList;
    }

    /**
     * 得到excel文件中的所有sheetName list
     * 
     * @param fileName fileName
     * @return List List
     */
    public static List<String> getAllSheetName(String fileName){
        List<String> sheetNameList = new LinkedList<String>();
        List<Sheet> sheetList = getAllSheet(fileName);
        for (Sheet sheet : sheetList){
            sheetNameList.add(sheet.getSheetName());
        }
        return sheetNameList;
    }

    /**
     * @Title:getSheetContent
     * @Description:
     * @param sheet sheet
     * @return Map<String,List<String>>
     */
    public static Map<String, List<String>> getSheetContent(Sheet sheet){
        Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();
        int firstRowNum = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();
        for (int i = firstRowNum + 1; i <= lastRowNum; i++){
            List<String> dataList = new LinkedList<String>();
            Row row = sheet.getRow(i);
            String indicatorName = row.getCell(0).getStringCellValue();
            for (Cell dataCell : row){
                // cell.getCellType是获得cell里面保存的值的type
                String dataValue = null;
                switch (dataCell.getCellType()){
                    case Cell.CELL_TYPE_BLANK:
                        dataValue = "";
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        // 得到Boolean对象的方法
                        dataValue = String.valueOf(dataCell.getBooleanCellValue());
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        // 先看是否是日期格式
                        if (DateUtil.isCellDateFormatted(dataCell)){
                            // 读取日期格式
                            dataValue = String.valueOf(dataCell.getDateCellValue());
                        }
                        else{
                            // 读取数字
                            dataValue = String.valueOf(dataCell.getNumericCellValue());
                        }
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        try{
                            dataValue = String.valueOf(dataCell.getNumericCellValue());
                        }
                        catch (RuntimeException r){
                            dataValue = "";
                        }
                        break;
                    case Cell.CELL_TYPE_STRING:
                        // 读取String
                        dataValue = dataCell.getStringCellValue();
                        break;
                    default:
                        break;
                }
                dataList.add(dataValue);
            }
            map.put(indicatorName, dataList);
        }
        return map;
    }

    /**
     * 获取多个SHEET的内容
     * 
     * @param sheetList sheetList
     * @return Map<String,Map<String,List<String>>>
     */
    public static Map<String, Map<String, List<String>>> getMutilSheetContent(List<Sheet> sheetList){
        Map<String, Map<String, List<String>>> map = new LinkedHashMap<String, Map<String, List<String>>>();
        for (Sheet sheet : sheetList){
            if (sheet != null){
                Map<String, List<String>> sheetMap = new LinkedHashMap<String, List<String>>();
                String sheetName = sheet.getSheetName();
                int firstRowNum = sheet.getFirstRowNum();
                int lastRowNum = sheet.getLastRowNum();
                for (int i = firstRowNum + 1; i <= lastRowNum; i++){
                    List<String> dataList = new LinkedList<String>();
                    Row row = sheet.getRow(i);
                    String indicatorName = row.getCell(0).getStringCellValue();
                    for (Cell cell : row){
                        // cell.getCellType是获得cell里面保存的值的type
                        String dataValue = null;
                        switch (cell.getCellType()){
                            case Cell.CELL_TYPE_BLANK:
                                dataValue = "";
                                break;
                            case Cell.CELL_TYPE_BOOLEAN:
                                // 得到Boolean对象的方法
                                dataValue = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case Cell.CELL_TYPE_NUMERIC:
                                // 先看是否是日期格式
                                if (DateUtil.isCellDateFormatted(cell)){
                                    // 读取日期格式
                                    dataValue = String.valueOf(cell.getDateCellValue());
                                }
                                else{
                                    // 读取数字
                                    dataValue = String.valueOf(cell.getNumericCellValue());
                                }
                                break;
                            case Cell.CELL_TYPE_FORMULA:
                                try{
                                    dataValue = String.valueOf(cell.getNumericCellValue());
                                }
                                catch (RuntimeException r){
                                    dataValue = "";
                                }
                                break;
                            case Cell.CELL_TYPE_STRING:
                                // 读取String
                                dataValue = cell.getStringCellValue();
                                break;
                            default:
                                break;
                        }
                        if (dataValue == null){
                            dataValue = "";
                        }
                        dataList.add(dataValue);
                    }
                    sheetMap.put(indicatorName, dataList);
                }
                map.put(sheetName, sheetMap);
            }
            else{
                continue;
            }
        }
        return map;
    }

    /**
     * 获取EXCEL文件中所以的Sheet Map
     * 
     * @param fileName fileName
     * @return Map<String,Sheet>
     */
    public static Map<String, Sheet> getAllSheetMap(String fileName){
        Workbook workbook = getWorkbookByFileName(fileName);
        Map<String, Sheet> map = new LinkedHashMap<String, Sheet>();
        int sheetLength = workbook.getNumberOfSheets();
        for (int i = 0; i < sheetLength; i++){
            Sheet sheet = workbook.getSheetAt(i);
            String sheetName = sheet.getSheetName();
            map.put(sheetName, sheet);
        }
        return map;
    }

    /**
     * @Title:isBlankRow
     * @param row row
     * @return boolean
     */
    public static boolean isBlankRow(Row row){
        if (row == null){
            return true;
        }
        boolean result = true;
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++){
            Cell cell = row.getCell(i, Row.RETURN_BLANK_AS_NULL);
            String value = "";
            if (cell != null){
                switch (cell.getCellType()){
                    case Cell.CELL_TYPE_STRING:
                        value = cell.getStringCellValue();
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        value = String.valueOf((int) cell.getNumericCellValue());
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        value = String.valueOf(cell.getBooleanCellValue());
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        value = String.valueOf(cell.getCellFormula());
                        break;
                    // case Cell.CELL_TYPE_BLANK:
                    // break;
                    default:
                        break;
                }

                if (!value.trim().equals("")){
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 获取单元格的值
     * 
     * @param cell 单元格
     * @return String
     */
    public static Object getCellValue(Cell cell){
        if (cell == null){
            return "";
        }
        if (cell.getCellType() == Cell.CELL_TYPE_STRING || cell.getCellType() == Cell.CELL_TYPE_FORMULA){
            String value = cell.getStringCellValue().trim();
            String zhunaHuanCellValue = zhuanHuanGetCellValue(value);
            String cellValue = NumberUtil.getNumber(zhunaHuanCellValue);
            return cellValue;
        }
        else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){
            return cell.getBooleanCellValue();
        }
        else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
            return NumberUtil.getNumber(NumberUtil.formatMaxPointString(cell.getNumericCellValue())); // 去除数字.后面所有的0
        }
        else if (cell.getCellType() == Cell.CELL_TYPE_BLANK){
            return "";
        }
        else if (cell.getCellType() == Cell.CELL_TYPE_ERROR){
            return "";
        }
        return "";
    }

    /**
     * @Title:zhuanHuanGetCellValue
     * @param cellValue 单元格值
     * @return ExcelUtil
     */
    public static String zhuanHuanGetCellValue(String cellValue){
        String regex1 = "\\(|\\（|\\（";
        String regex2 = "\\)|\\）|\\）";
        String regex3 = "\\[|\\【|\\【";
        String regex4 = "\\]|\\】|\\】";
        String regex5 = "\\{|\\｛|\\｛";
        String regex6 = "\\}|\\｝|\\｝";
        String regex7 = ",|，|，";
        String value = cellValue.trim().replaceAll(regex1, "(").replaceAll(regex2, ")")
                .replaceAll(regex3, "[").replaceAll(regex4, "]").replaceAll(regex5, "{")
                .replaceAll(regex6, "}").replaceAll(regex7, ",").replaceAll("\\n", "");
        return value;
    }

    /**
     * @Title:handleTitle
     * @param sheet sheet
     * @param titleList titleList
     */
    public static void handleTitle(HSSFSheet sheet, List<String> titleList){
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < titleList.size(); i++){
            HSSFCell cell_siteId = row.createCell(i);
            cell_siteId.setCellValue(titleList.get(i));
        }
    }

    /**
     * @Title:handleSheetData
     * @param sheet sheet
     * @param list list
     */
    public static void handleSheetData(HSSFSheet sheet, List<List<String>> list){
        for (int i = 0; i < list.size(); i++){
            HSSFRow row = sheet.createRow(i + 1);
            for (int j = 0; j < list.get(i).size(); j++){
                HSSFCell cell_siteId = row.createCell(j);
                cell_siteId.setCellValue(list.get(i).get(j));
            }
        }
    }
    
    /**
     * 获取文件上传,下载路径
     * @return 文件路径
     * @author 伍恰  
     * @date 2017年7月11日 上午10:05:36
     */
    public static String getDeviceFilePath(){
        StringBuffer path = new StringBuffer();
        String resourcesFolderPath = SysConfigUtil.getParamValue("resources_folder_path");//静态资源文件夹路径
        path.append(resourcesFolderPath);
        path.append(File.separator);
        path.append("media");
        path.append(File.separator);
        path.append("np");
        path.append(File.separator);
        path.append("device");
        path.append(File.separator);
        File file = new File(path.toString());
        if(!file.exists()){  
            file.mkdirs();  
        }  
        return path.toString();
    }
}

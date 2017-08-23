package com.awifi.np.biz.common.excel.model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.awifi.np.biz.common.excel.BaseExcel;


/**
 * 导出至Excel
 */
public class OutExcel<T> extends BaseExcel<T>{

    /**
     * 设置全局变量(HSSFWorkBook)xls文件
     */
    private HSSFWorkbook wb = new HSSFWorkbook();
 
    /**
     * 自定义输出时间格式
     */
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 构造函数
     * @param datas 参数
     * @param clz 参数
     */
    public OutExcel(List<T> datas, Class<T> clz){
        super(clz);
        this.clz = clz;
        this.datas = datas;
    }
    /**
     * 初始化方法(得到输出流)
     * @param fileName 输出文件名
     */
    public void init(String fileName){
        FileOutputStream fout = null;
        try{
            Workbook wb = writeDatas();
            fout = new FileOutputStream(fileName);
            wb.write(fout);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally{
            try{
                fout.close();
            }
            catch (IOException e){
            }
        }
    }
    /**
     * 根据输出流得到输出文件
     * @param out 输出流
     */
    public void init(OutputStream out){
        try{
            Workbook wb = writeDatas();
            wb.write(out);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    /**
     * 输出excel文件(格式和数据)
     * @return workbook
     */
    private Workbook writeDatas(){
        CellStyle style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER); // 创建一个居中格式
        if (sheet == null){
            writeSheet(datas, wb, style);
        }
        else{
            Map<Object, List<T>> map = new HashMap<Object, List<T>>();
            try{
                for (T t : datas){
                    Object sheetValue = sheet.getMethod().invoke(t);
                    List<T> ts = map.get(sheetValue);
                    if (ts == null){
                        ts = new ArrayList<T>();
                        map.put(sheetValue, ts);
                    }
                    ts.add(t);
                }
            }
            catch (IllegalAccessException e){
                e.printStackTrace();
            }
            catch (IllegalArgumentException e){
                e.printStackTrace();
            }
            catch (InvocationTargetException e){
                e.printStackTrace();
            }

            for (Object key : map.keySet()){
                writeSheet(map.get(key), wb, key.toString(), style);
            }
        }

        return wb;
    }
    /**
     * 写sheet
     * @param list 数据集合
     * @param wb factory
     * @param style 样式
     */
    private void writeSheet(List<T> list, HSSFWorkbook wb, CellStyle style){
        writeSheet(list, wb, null, style);
    }
    /**
     * 写sheet
     * @param list 输出数据集合
     * @param wb factory
     * @param sheetName sheet名
     * @param style 样式
     */
    private void writeSheet(List<T> list, HSSFWorkbook wb, String sheetName, CellStyle style){
        int row = 1;
        Sheet sheet = sheetName == null ? wb.createSheet() : wb.createSheet(sheetName);
        writeFirstRow(sheet, style);
        boolean tooLong = list.size() > 65000;
        List<T> temp = tooLong ? list.subList(0, 65000) : list;
        for (T t : temp){
            Row r = sheet.createRow(row++);
            writeRow(t, r, style);
        }
        if (tooLong){
            writeSheet(list.subList(65000, list.size()), wb, sheetName, style);
        }
    }
    /**
     * 书写sheet的第一行
     * @param sheet sheet
     * @param style 单元格样式
     */
    private void writeFirstRow(Sheet sheet, CellStyle style){
        Row row = sheet.createRow(0);
        try{
            for (ExcelReflect reflect : reflects){
                ExcelColumn excelColumn = reflect.getExcel();
                Cell cell = row.createCell(excelColumn.columnNum());
                writeCell(cell, style, excelColumn.columnName());
            }
        }
        catch (SecurityException e){
            e.printStackTrace();
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    /**
     * 写每一行
     * @Title:writeRow
     * @param t 数据
     * @param row 行
     * @param style 格式
     */
    private void writeRow(T t, Row row, CellStyle style){
        try{
            for (ExcelReflect reflect : reflects){
                Cell cell = row.createCell(reflect.getExcel().columnNum());
                // 日期特殊处理
                Object value = reflect.getExcel().columnType() == Type.Date ? sdf.format(reflect.getMethod()
                        .invoke(t)) : reflect.getMethod().invoke(t);
                // Object value = reflect.getMethod().invoke(t);
                writeCell(cell, style, value);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 把数据写入Excel
     * @Title:writeCell write
     * @param cell cell
     * @param style style
     * @param value value
     */
    private void writeCell(Cell cell, CellStyle style, Object value){
        if (value != null && !"".equals(value)){
            cell.setCellValue(value.toString());
        }
        else{
            cell.setCellValue("");
        }
        cell.setCellStyle(style);
    }
    /**
     * 获取实体字段的get方法
     */
    @Override
    protected Method getMethod(Field field) throws NoSuchMethodException, SecurityException{
        return clz.getDeclaredMethod("get".concat(super.getMethodName(field.getName())));
    }

}

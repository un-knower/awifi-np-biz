package com.awifi.np.biz.common.excel.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.awifi.np.biz.common.excel.InExcel;
import com.awifi.np.biz.common.exception.IllegalDataException;
import com.awifi.np.biz.common.util.EmsExcelUtil;

public class InExcelIterator<T> extends InExcel<T>{

    /**
     * 将excel的索引及内容放入链表中
     * @author machealli
     *
     */
    private class RowData{
        /**
         * sheet数
         */
        private int sheetNum;
        /**
         * 行数
         */
        private int rowNum;
        /**
         * 下一行
         */
        private RowData next;

        /**
         * 赋值
         * @param sheetNum sheet数 
         * @param rowNum 行数
         */
        RowData(int sheetNum, int rowNum){
            this.sheetNum = sheetNum;
            this.rowNum = rowNum;
        }
    }

    /**
     * book
     */
    private Workbook workbook;
    /**
     * 第一行
     */
    private RowData firstRow;

    /**
     * 当前行
     */
    private Row currentRow;

    /**
     * 当前sheet
     */
    private Sheet currentSheet;

    /**
     * 构造方法
     * @param file 文件路径
     * @param clz 批量信息的实体类
     */
    public InExcelIterator(String file, Class<T> clz){
        super(file, clz);
    }

    @Override
    protected void init(){
        try{
            workbook = EmsExcelUtil.createWorkbook(new FileInputStream(file));
            RowData row = null;
            for (int s = 0; s < workbook.getNumberOfSheets(); s++){
                // 得到sheet
                currentSheet = workbook.getSheetAt(s);
                // 得到数据行数
                int rowNum = currentSheet.getPhysicalNumberOfRows();
                // 为了firstRow
                if (rowNum > 0){
                    firstRow = new RowData(0, 0);
                    row = new RowData(s, 1);
                    firstRow.next = row;
                    for (int i = 2; i < rowNum; i++){
                        RowData r = new RowData(s, i);
                        row.next = r;
                        row = r;
                    }
                    break;
                }
            }
            if (row == null){
                return;
            }
            for (int s = row.sheetNum + 1; s < workbook.getNumberOfSheets(); s++){
                // 得到sheet
                Sheet sheet = workbook.getSheetAt(s);
                // 得到数据行数
                int rowNum = sheet.getPhysicalNumberOfRows();
                for (int i = 1; i < rowNum; i++){
                    RowData r = new RowData(s, i);
                    row.next = r;
                    row = r;
                }
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (SecurityException e){
            e.printStackTrace();
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    /**
     * 判断是否有下一行
     * @return 布尔类型
     */
    public boolean hasNext(){
        int currentSheetNo = firstRow.sheetNum;
        firstRow = firstRow.next;
        if (firstRow == null){
            return false;
        }
        if (firstRow.sheetNum != currentSheetNo){
            currentSheet = workbook.getSheetAt(firstRow.sheetNum);
        }

        return (currentRow = currentSheet.getRow(firstRow.rowNum)) == null ? hasNext() : true;
    }
    
    /**
     * 得到下一行的数据
     * @return 实体类
     * @throws IllegalDataException 异常
     */
    public T getNextRow() throws IllegalDataException{
        return super.getRow(currentSheet, currentRow);
    }
    
    /**
     * 得到第一行数据
     * @return 实体类
     * @throws IllegalDataException 异常
     */
    public T getFirstRow()throws IllegalDataException{
        return super.getFirstRow(currentSheet);
    }
	/**
	 * 得到行数
	 * @return 行数
	 */
    public int getRowNu(){
        return firstRow.rowNum;
    }

    /**
     * 已废弃
     */
    @Override
    @Deprecated
    protected T getRow(Sheet sheet, Row row){
        throw new RuntimeException();
    }
}

package com.awifi.np.biz.common.excel.model;

/**
 *错误信息的放入
 */
public class ErrorInfo{

    // @ExcelColumn(columnName = "错误sheet", columnNum = 0)
    // private int sheetNum;
    //
    // @ExcelColumn(columnName = "错误行", columnNum = 1)
    // private int rowNum;
    //
    // @ExcelColumn(columnName = "错误列", columnNum = 2)
    // private int colNum;
    /**
     * 
     */
    @ExcelColumn(columnName = "错误信息", columnNum = 0)
    private String info;

    // public int getSheetNum() {
    // return sheetNum;
    // }
    //
    // public void setSheetNum(int sheetNum) {
    // this.sheetNum = sheetNum;
    // }
    //
    // public int getRowNum() {
    // return rowNum;
    // }
    //
    // public void setRowNum(int rowNum) {
    // this.rowNum = rowNum;
    // }
    //
    // public int getColNum() {
    // return colNum;
    // }
    //
    // public void setColNum(int colNum) {
    // this.colNum = colNum;
    // }

    /**
     * 
     */
    public ErrorInfo(){
    }

    /**
     * 
     */
    public ErrorInfo(String info){
        this.info = info;
    }

    public String getInfo(){
        return info;
    }

    public void setInfo(String info){
        this.info = info;
    }
}

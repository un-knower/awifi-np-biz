package com.awifi.np.biz.common.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.awifi.np.biz.common.excel.model.ExcelColumn;
import com.awifi.np.biz.common.excel.model.ExcelReflect;
import com.awifi.np.biz.common.excel.model.NumberData;
import com.awifi.np.biz.common.excel.model.RegExpValidator;
import com.awifi.np.biz.common.exception.ApplicationException;
import com.awifi.np.biz.common.exception.DataException;
import com.awifi.np.biz.common.exception.IllegalDataException;
import com.awifi.np.biz.common.util.EmsExcelUtil;
import com.awifi.np.biz.common.util.NumberUtil;
import com.awifi.np.biz.common.excel.model.Type;

public class InExcel<T> extends BaseExcel<T>{
    /**
     * 文件
     */
    protected File file;
    /**
     * 错误信息存放的list
     */
    protected List<String> errors = new ArrayList<String>();

    /**
     *文件总行数
     */
    private long totalRow;

    /**
     * 构造方法
     * @param file 文件
     * @param clz 类型
     */
    public InExcel(String file, Class<T> clz){
        super(clz);
        this.file = new File(file);
        if (!this.file.exists()){
            throw new RuntimeException("file not exist!");
        }
        this.clz = clz;
        init();
    }

    /**
     * 初始化方法，对excel文件进行初步校验和读取
     */
    protected void init(){
        datas = new ArrayList<T>();

        try{
            Workbook workbook = EmsExcelUtil.createWorkbook(new FileInputStream(file));
            for (int s = 0; s < workbook.getNumberOfSheets(); s++){
                // 得到sheet
                Sheet sheet = workbook.getSheetAt(s);
                // 得到数据行数
                int rowNum = sheet.getPhysicalNumberOfRows();
                if (rowNum != 0){
                    // 遍历数据
                    initRows(sheet, rowNum);
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
     * 获取行的内容,并方法datas中
     * @param sheet sheet
     * @param rowNum rowNum
     */
    private void initRows(Sheet sheet, int rowNum){
        for (int i = 0; i <= rowNum; i++){
            if (i == 0){
                continue;
            }
            // 得到第i行
            Row row = sheet.getRow(i);
            if (row == null){
                continue;
            }
            try{
                T t = getRow(sheet, row);
                datas.add(t);
            }
            catch (IllegalDataException e){
                errors.addAll(e.getErrors());
            }
        }
    }


    /**
     * 获取每行的数据(最原始获取,通过反射获取)
     * @param sheet 工作表
     * @param row 行
     * @return t
     * @throws IllegalDataException 异常
     */
    protected T getRow(Sheet sheet, Row row) throws IllegalDataException{
        totalRow++;
        T t = null;
        try{
            t = clz.newInstance();
        }
        catch (InstantiationException e1){
            e1.printStackTrace();
        }
        catch (IllegalAccessException e1){
            e1.printStackTrace();
        }

        ExcelColumn excelColumn = null;
        Object cellValue = null;
        List<String> errors = new ArrayList<String>();
        for (ExcelReflect reflect : reflects){
            try{
                excelColumn = reflect.getExcel();
                // 时间特殊处理
                cellValue = excelColumn.columnType() == Type.Date ? row.getCell(excelColumn.columnNum())
                        : EmsExcelUtil.getCellValue(row.getCell(excelColumn.columnNum()));

                Object value = getCellData(sheet, cellValue, excelColumn, row.getRowNum(),
                        reflect.getField().getType());
                reflect.getMethod().invoke(t, value);
            }
            catch (DataException e){
                errors.add(e.getMessage());
            }
            catch (Exception e){
                errors.add(sheet.getSheetName() + "第" + (row.getRowNum() + 1) + "行" + excelColumn.columnName()
                        + ":" + cellValue + "数据异常!具体错误:" + e.toString());
            }
        }
        if (errors.size() > 0){
            throw new IllegalDataException(errors);
        }

        return t;
    }
    /**
     * 获取Excel 中第一行数据
     * @param sheet 
     * @return T 第一行数据
     * @throws IllegalDataException 异常/参数
     * @author 伍恰  
     * @date 2017年6月16日 下午12:47:35
     */
    protected T getFirstRow(Sheet sheet) throws IllegalDataException{
        Row row=sheet.getRow(1);
        if(row==null){
            errors.add("excel数据为空");
            return null ;
        }
        T t = null;
        try{
            t = clz.newInstance();
        }
        catch (InstantiationException e1){
            e1.printStackTrace();
        }
        catch (IllegalAccessException e1){
            e1.printStackTrace();
        }

        ExcelColumn excelColumn = null;
        Object cellValue = null;
        List<String> errors = new ArrayList<String>();
        for (ExcelReflect reflect : reflects){
            try{
                excelColumn = reflect.getExcel();
                // 时间特殊处理
                cellValue = excelColumn.columnType() == Type.Date ? row.getCell(excelColumn.columnNum())
                        : EmsExcelUtil.getCellValue(row.getCell(excelColumn.columnNum()));

                Object value = getCellData(sheet, cellValue, excelColumn, row.getRowNum(),
                        reflect.getField().getType());
                reflect.getMethod().invoke(t, value);
            }
            catch (DataException e){
                errors.add(e.getMessage());
            }
            catch (Exception e){
                errors.add(sheet.getSheetName() + "第" + (row.getRowNum() + 1) + "行" + excelColumn.columnName()
                        + ":" + cellValue + "数据异常!具体错误:" + e.toString());
            }
        }
        if (errors.size() > 0){
            throw new IllegalDataException(errors);
        }

        return t;
    }


    /**
     * 对数据格式进行初始化校验
     * @param sheet 工作表
     * @param value 值
     * @param excelColumn 实体
     * @param rowNum 行数
     * @param fieldType class
     * @return obj
     * @throws DataException 异常
     */
    private Object getCellData(Sheet sheet, Object value, ExcelColumn excelColumn, int rowNum,Class<?> fieldType) throws DataException{
        String error = sheet.getSheetName() + "第" + (rowNum + 1) + "行第" + (excelColumn.columnNum() + 1) + "列";
        switch (excelColumn.columnType()){
            case String:
        	if (StringUtils.isBlank(value.toString())){
                    if (excelColumn.none()){
                        return value;
                    }
                    else{
                    	String str = error + excelColumn.columnName() + "不能为空！";
                        throw new DataException(str);
                    }
                }
                else{
                    if (RegExpValidator.isLengthAuto(value.toString(), excelColumn.length())){
                        return value.toString();
                    }
                    else{
                        String str = error + excelColumn.columnName() + "长度不能超过" + excelColumn.length()+"或者数据类型错误！";
                        throw new DataException(str);
                    }
                }
            case Version:
            // 可以空
                if (StringUtils.isBlank(value.toString())){
                    if (excelColumn.none()){
                        return value;
                    }
                    else{
                        String str = error + excelColumn.columnName() + "不能为空！";
                        throw new DataException(str);
                    }
                }
                else{
                    if (RegExpValidator.isVersion(value.toString())){
                        return value.toString();
                    }
                    else{
                        throw new DataException(error + excelColumn.columnName() + "必须以Vxx.xx.xx形式！");
                    }
                }
            case NoChinaString:
                if (StringUtils.isBlank(value.toString())){
                    if (excelColumn.none()){
                        return value;
                    }
                    else{
                        String str = error + excelColumn.columnName() + "不能为空！";
                        throw new DataException(str);
                    }
                }
                else{
                    if (RegExpValidator.IsEngAndNumLengthAuto(value.toString(), excelColumn.length())){
                        return value.toString();
                    }
                    else{
                        String str = error + excelColumn.columnName() + "长度不能超过" + excelColumn.length()+ "或者数据中存在中文字符错误！";
                        throw new DataException(str);
                    }
                }
            case Ip:
            // 可以空
                if (StringUtils.isBlank(value.toString())){
                    if (excelColumn.none()){
                        return value;
                    }
                    else{
                        String str = error + excelColumn.columnName() + "不能为空！";
                        throw new DataException(str);
                    }
                }
                else{
                    if (RegExpValidator.isIP(value.toString())){
                        return value.toString();
                    }
                    else{
                        String str = error + excelColumn.columnName() + "数据类型错误！";
                        throw new DataException(str);
                    }
                }
            case NumString:
            // 可以空
                if (StringUtils.isBlank(value.toString())){
                    if (excelColumn.none()){
                        return value;
                    }
                    else{
                        String str = error + excelColumn.columnName() + "不能为空！";
                        throw new DataException(str);
                    }
                }
                else{
                    if (RegExpValidator.isNumLengthAuto(value.toString(), excelColumn.length())){
                        return value.toString();
                    }
                    else{
                        String str = error + excelColumn.columnName() + "长度不能超过" + excelColumn.length()+ "或者数据不全是数字！";
                        throw new DataException(str);
                    }
                }
            case Num:
            // 可以空
                if (StringUtils.isBlank(value.toString())){
                    if (excelColumn.none()){
                        return null;
                    }
                    else{
                        String str = error + excelColumn.columnName() + "不能为空！";
                        throw new DataException(str);
                    }
                }
                else{
                    if (RegExpValidator.isNumLengthAuto(value.toString(), excelColumn.length())){
                        try{
                            NumberData data = new NumberData(value.toString());
                            Method m = data.getClass().getDeclaredMethod(fieldType.getSimpleName());
                            value = m.invoke(data);
                        }
                        catch (NoSuchMethodException e){
                            throw new ApplicationException(error + "内部方法错误！");
                        }
                        catch (IllegalAccessException e){
                            throw new ApplicationException(error + "内部方法权限错误！");
                        }
                        catch (IllegalArgumentException e){
                            throw new ApplicationException(error + "内部参数错误！");
                        }
                        catch (InvocationTargetException e){
                            throw new ApplicationException(error + "内部错误！");
                        }
                        return value;
                    }
                    else{
                        String str = error + excelColumn.columnName() + "必须是数字！";
                        throw new DataException(str);
                    }
                }
            case Mac:
            // 可以空
                if (StringUtils.isBlank(value.toString())){
                    if (excelColumn.none()){
                        return value;
                    }
                    else{
                        String str = error + excelColumn.columnName() + "不能为空！";
                        throw new DataException(str);
                    }
                }
                else{
                    if (RegExpValidator.isLength2(value.toString())){
                        return value;
                    }
                    else{
                        String str = error + excelColumn.columnName() + "数据类型错误！";
                        throw new DataException(str);
                    }
                }
            case Address:
                 // 可以空
                String str = value.toString();
                if (StringUtils.isBlank(str)){
                    if (excelColumn.none()){
                        return str;
                    }
                    else{
                        String s = error + excelColumn.columnName() + "不能为空！";
                        throw new DataException(s);
                    }
                }
                else{
                    if (RegExpValidator.isProvince(str) || RegExpValidator.isCity(str)|| RegExpValidator.isCounty(str)){
                        return value;
                    }
                    else{
                        String s = error + excelColumn.columnName() + "必须包含省或市或区或县！";
                        throw new DataException(s);
                    }
                }
            case Date:
                try{
                    Cell cell = (Cell) value;
                    value = cell.getDateCellValue();
                    if (value == null){
                        if (excelColumn.none()){
                            return null;
                        }
                        else{
                            String s = error + excelColumn.columnName() + "不能为空！";
                            throw new DataException(s);
                        }
                    }
                    else{
                        return value;
                    }
                }catch (DataException e){
                    throw e;
                }
                catch (Exception e){
                    throw new DataException(error + excelColumn.columnName() + "数据单元格式不是日期或输入格式错误！");
                }
            case Memory:
                if (StringUtils.isBlank(value.toString())){
                    if (excelColumn.none()){
                        return value;
                    }
                    else{
                        throw new DataException(error + excelColumn.columnName() + "不能为空！");
                    }
                }
                else{
                    if (RegExpValidator.isMenory(value.toString())){
                        return value;
                    }
                    else{
                        throw new DataException(error + excelColumn.columnName() + "数据类型必须带有m或M或g或G！");
                    }
                }
            case LATI:
                if (StringUtils.isBlank(value.toString())){
                    if (excelColumn.none()){
                        return null;
                    }
                    else{
                        throw new DataException(error + excelColumn.columnName() + "不能为空！");
                    }
                }
                else{
                    if (RegExpValidator.regWD(value.toString())){
                        return new BigDecimal(value.toString());
                    }
                    else{
                        throw new DataException(error + excelColumn.columnName() + "应为-90.00000~+90.00000之间!");
                    }
                }
            case LONGI:
                if (StringUtils.isBlank(value.toString())){
                    if (excelColumn.none()){
                        return null;
                    }
                    else{
                        throw new DataException(error + excelColumn.columnName() + "不能为空！");
                    }
                }
                else{
                    if (RegExpValidator.regJD(value.toString())){
                        return new BigDecimal(value.toString());
                    }
                    else{
                        throw new DataException(error + excelColumn.columnName() + "应为-180.00000~+180.00000之间!");
                    }
                }
            case PHONE:
                if (StringUtils.isBlank(value.toString())){
                    if (excelColumn.none()){
                        return value;
                    }
                    else{
                        throw new DataException(error + excelColumn.columnName() + "不能为空！");
                    }
                }
                else{
                    if (RegExpValidator.IsMobile(value.toString())){
                        return value.toString();
                    }
                    else{
                        throw new DataException(error + excelColumn.columnName() + "不是正确的手机号码!");
                    }
                }
            case ZOROne:
                if (StringUtils.isBlank(value.toString())){
                    if (excelColumn.none()){
                        return null;
                    }
                    else{
                        throw new DataException(error + excelColumn.columnName() + "不能为空！");
                    }
                }
                else{
                    String s = NumberUtil.getNumber(value.toString());
                    if (!s.contains(".") && RegExpValidator.is0or1(s)){
                        return Integer.parseInt(s);
                    }
                    else{
                        throw new DataException(error + excelColumn.columnName() + "只能是0或者1!");
                    }
                }
            case LEVEL:
                if (StringUtils.isBlank(value.toString())){
                    if (excelColumn.none()){
                        return value;
                    }
                    else{
                        throw new DataException(error + excelColumn.columnName() + "不能为空！");
                    }
                }
                else{
                    if (RegExpValidator.isABCD(value.toString())){
                        return value;
                    }
                    else{
                        throw new DataException(error + excelColumn.columnName() + "只能是ABCD!");
                    }
                }
            default:
                break;
        }

        throw new RuntimeException("unknow type");
    }
    /**
     * 得到实体字段的set方法
     * @param field 字段
     * @return method
     * @throws NoSuchMethodException 异常
     * @throws SecurityException 异常
     */
    @Override
    protected Method getMethod(Field field) throws NoSuchMethodException, SecurityException{
        return clz.getDeclaredMethod("set".concat(super.getMethodName(field.getName())), field.getType());
    }

    /**
     * 得到文件
     * @return file
     */
    public File getFile(){
        return file;
    }

    /**
     * 得到数据
     * @return list
     */
    public List<T> getRowDatas(){
        return datas;
    }

    /**
     * 得到excel总行数
     * @return long
     */
    public Long getTotalRow(){
        return totalRow;
    }

    /**
     * 得到错误集合
     * @return list
     */
    public List<String> getErrors(){
        return errors;
    }

}

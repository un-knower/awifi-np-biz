/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.awifi.np.biz.common.excel;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.awifi.np.biz.common.excel.dao.EmsExcelDao;
import com.awifi.np.biz.common.excel.model.CenterPubModel;
import com.awifi.np.biz.common.excel.model.EmsSysExcel;
import com.awifi.np.biz.common.excel.model.ErrorInfo;
import com.awifi.np.biz.common.excel.model.InExcelIterator;
import com.awifi.np.biz.common.excel.model.OutExcel;
import com.awifi.np.biz.common.exception.ApplicationException;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.IllegalDataException;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.util.EmsExcelUtil;
import com.awifi.np.biz.common.util.MessageUtil;



/**
 * @Title:
 * @Description:此处更改为一个线程进行解析和上传
 * @Author:Administrator
 * @Since:2017年4月12日
 * @Version:1.1.0
 */
public abstract class ImportExcel<T>{

	/**
	 * 一个存储作用的table(线程安全，后期可改)
	 */
    protected  Hashtable<String, CenterPubModel> modelMap=new Hashtable<>();
    /**
     * excel本地导入数据的持久层
     */
    @Resource(name="emsExcelDao")
    private EmsExcelDao emsExcelDao;
    /**
     * 解析和导入过程
     * @param info excel文件信息
     * @param sessionUser 用户信息
     * @return map
     */
    public Map<String,String> runTask(EmsSysExcel info,  SessionUser sessionUser){
        Map<String,String> resultMap=null;
        long time = System.currentTimeMillis();
        InExcelIterator<T> excel = null;
        try{
            excel = new InExcelIterator<T>(info.getFilepath(), getExcelClass());
            //此处取出校验的信息
            List<T> datas = readExcel(excel, sessionUser);
            Long total = excel.getTotalRow();
            if (total == 0){
                throw new ValidException("E2000030", MessageUtil.getMessage("E2000030"));
            }
            info.setFilestatus(2);
            info.setRecordnum(datas.size());
            String batchDeviceId=getBatchNum(datas.get(0));
            insertDatas(datas, sessionUser,batchDeviceId,info.getUuid());
            resultMap=new HashMap<>();
            resultMap.put("code", "0");
            resultMap.put("batchNum", batchDeviceId);
            resultMap.put("msg", "文件解析成功");
        } catch (IllegalDataException e){
            List<String> errors = e.getErrors();
            showError(info,errors);
        }
        catch (ApplicationException e){
            writeErrorToExcel(e,info,false);
        }catch (ValidException e){
            writeErrorToExcel(e,info,true);
            throw e ;
        }catch (BizException e){
            writeErrorToExcel(e,info,true);
            throw e ;
        }catch (Exception e){
            writeErrorToExcel(e,info,false);
        }
        if (excel != null){
            info.setTotalnum(excel.getTotalRow().intValue());
        }
        Date date = new Date();
        info.setCompletecosttime(date.getTime() - time);
        info.setCompletetime(date);
        return resultMap;
    }

    /**
     * 将错误信息写入到excel文件中
     * @param errorsInfos 错误信息集合
     * @return string
     */
    private String writeErrorToExcel(List<ErrorInfo> errorsInfos) {
        String dir = EmsExcelUtil.getDeviceFilePath();//获取文件路径
        OutExcel<ErrorInfo> infoFile = new OutExcel<ErrorInfo>(errorsInfos, ErrorInfo.class);
        String file = System.currentTimeMillis() + "error.xls";
        infoFile.init(dir + file);
        return file;
    }
    
    /**
     * 将错误信息写入到excel文件  和 数据库表中
     * @param e 异常
     * @param info 文件信息
     * @param isUpdate 是否需要更新文件状态 
     * @return 异常/参数
     * @author 伍恰  
     * @date 2017年6月16日 下午4:12:33
     */
    private String writeErrorToExcel(Exception e,EmsSysExcel info,boolean isUpdate) {
        List<ErrorInfo> errorInfos = new ArrayList<ErrorInfo>();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String str = sw.toString();
        ErrorInfo errorInfo = new ErrorInfo(str);
        errorInfos.add(errorInfo);
        String file=writeErrorToExcel(errorInfos);
        info.setRemark(str.length() > 500 ? str.substring(0, 500) : str);
        info.setRecordnum(0);
        info.setErrorfile(file);
        info.setFilestatus(3);
        emsExcelDao.updateById(info);
        return file;
    }
    /**
     * 判断错误是否大于10条的操作
     * @param info 文件信息(入本地库)
     * @param errors 错误集合
     */
    private void showError(EmsSysExcel info,List<String> errors){
        List<ErrorInfo> errorsInfos = new ArrayList<ErrorInfo>();
        for (String error : errors){
            errorsInfos.add(new ErrorInfo(error));
        }
        String file=writeErrorToExcel(errorsInfos);
        info.setErrorfile(file);
        
        if(errors.size()<10){
            info.setRemark(errors.toString());
        }
        info.setFilestatus(3);
        info.setRecordnum(0);
    }
    
    /**
     * 批量信息导入数据库和本地数据库的操作
     * @param datas 需要导入的批量信息
     * @param sessionUser 用户信息
     * @param batchDeviceId 设备id
     * @param uuid 
     * @throws Exception 异常
     * @author 伍恰  
     * @date 2017年6月16日 下午4:54:05
     */
    protected abstract void insertDatas(List<T> datas, SessionUser sessionUser,String batchDeviceId,String uuid)throws Exception;

    /**
     * 获取类型
     * @return class
     */
    protected abstract Class<T> getExcelClass();

    /**
     * 读取excel文件中的内容，以获取原始批量信息
     * @param excel excel文件
     * @param sessionUser 用户信息
     * @return list
     * @throws Exception 异常
     */
    protected abstract List<T> readExcel(InExcelIterator<T> excel, SessionUser sessionUser)
            throws Exception;
    
    /**
     * 获取批次号
     * @param t 解析出的第一行数据
     * @return String
     */
    protected abstract String getBatchNum(T t);
}

package com.awifi.np.biz.devsrv.excel.service;

import java.util.Map;

import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.excel.model.EmsSysExcel;
import com.awifi.np.biz.common.excel.model.ExcelType;
import com.awifi.np.biz.common.security.user.model.SessionUser;

public interface EmsExcelService {

    /**
     * 解析和导入文件内容
     * @param file 内容的总体信息
     * @param type 导入信息的类别
     * @param sessionUser 用户
     * @throws Exception 异常
     * @return Map 
     * @author 伍恰  
     * @date 2017年6月14日 下午1:56:41
     */
    Map<String,String> importExcel(EmsSysExcel file,ExcelType type,SessionUser sessionUser) throws Exception;
    
    /**
     * 向本地数据库中插入excel内容信息
     * @param file 内容的总体信息
     * @return int
     * @throws Exception 异常
     */
    int addSelective(EmsSysExcel file)throws Exception;

    /**
     * 根据用户信息查找excel文件解析情况
     * @param page page
     * @param sessionUser 用户
     * @param typeValue 类型
     * @throws Exception 异常
     */
    void showDetail(Page<EmsSysExcel> page, SessionUser sessionUser,String typeValue) throws Exception;

}

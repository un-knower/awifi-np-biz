package com.awifi.np.biz.devsrv.excel.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.awifi.np.biz.common.base.model.Page;
import com.awifi.np.biz.common.excel.ImportExcel;
import com.awifi.np.biz.common.excel.dao.EmsExcelDao;
import com.awifi.np.biz.common.excel.model.EmsSysExcel;
import com.awifi.np.biz.common.excel.model.ExcelType;
import com.awifi.np.biz.common.exception.BizException;
import com.awifi.np.biz.common.exception.ValidException;
import com.awifi.np.biz.common.security.user.model.SessionUser;
import com.awifi.np.biz.common.util.BeanUtil;
import com.awifi.np.biz.common.util.MessageUtil;
import com.awifi.np.biz.devsrv.excel.service.EmsExcelService;

@Service(value="emsExcelService")
public class EmsExcelServiceImpl implements EmsExcelService{
    
	/**
	 * excel本地导入数据的持久层
	 */
    @Resource(name="emsExcelDao")
    private EmsExcelDao emsExcelDao;

    /**
     * 解析和导入文件内容
     * @param file 内容的总体信息
     * @param type 导入信息的类别
     * @param sessionUser 用户
     * @throws Exception 异常
     */
    @Override
    public Map<String,String> importExcel(EmsSysExcel file, ExcelType type, SessionUser sessionUser) throws Exception {
        // TODO Auto-generated method stub
        Map<String,String> result=null;
        ImportExcel<?> importExcel=(ImportExcel<?>) BeanUtil.getBean(type.name());
        if(importExcel!=null){
            result=importExcel.runTask(file, sessionUser);
          //需要更新一下file的情况
            updateById(file);
            if(result==null){
                throw new ValidException("E2301302", MessageUtil.getMessage("E2301302"));//解析出错
            }
            result.put("type", type.name());//加入type类型返还给前端
        }else{
            throw new BizException("E2301303", MessageUtil.getMessage("E2301303", type.name()+"Service"));
        }
        return result;
    }

    /**
     *查找file的情况和插入file(本地数据库) 
     * @param file 该批次的信息
     * @return int
     * @throws Exception 异常
     */
    public int addSelective(EmsSysExcel file)throws Exception{
        return emsExcelDao.addSelective(file);
    }
    
    /**
     * 根据id更新file信息
     * @param file excel文件内容信息
     * @return int
     *@throws Exception 异常
     */
    public int updateById(EmsSysExcel file)throws Exception{
        return emsExcelDao.updateById(file);
    }
    
    /**
     * 根据用户信息查找excel文件解析情况
     * @param page page
     * @param sessionUser 用户
     * @throws Exception 异常
     */
    @Override
    public void showDetail(Page<EmsSysExcel> page, SessionUser sessionUser,String typeValue) throws Exception {
        // TODO Auto-generated method stub
        Map<String,Object> map=new HashMap<String,Object>();
        Integer pageNo=page.getPageNo();
        Integer pageStart=(pageNo-1)*page.getPageSize();
        map.put("pageStart",pageStart);
        map.put("pageSize", page.getPageSize());
        String userName=sessionUser.getUserName();
        if("admin".equals(userName)){
            Long uploader=sessionUser.getId();
            map.put("uploader", uploader);
        }
        map.put("type", typeValue);
        List<EmsSysExcel> list=emsExcelDao.queryExcels(map);
        Integer count = emsExcelDao.queryCount(typeValue);
        page.setTotalRecord(count);
        page.setRecords(list);
    }
    
 
}
